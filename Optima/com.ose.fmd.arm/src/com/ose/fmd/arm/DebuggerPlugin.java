/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.fmd.arm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import com.arm.debug.osext.DebugSessionException;
import com.arm.debug.osext.ExecutionContextKind;
import com.arm.debug.osext.ExecutionContextState;
import com.arm.debug.osext.IAddress;
import com.arm.debug.osext.IAddressSpace;
import com.arm.debug.osext.IDebugSession;
import com.arm.debug.osext.IDebugSessionListener;
import com.arm.debug.osext.IExecutionContext;
import com.arm.debug.osext.IExecutionContextStateEvent;
import com.arm.debug.osext.IExecutionContextStateListener;
import com.arm.debug.osext.IMemoryBlock;
import com.arm.debug.osext.ISessionStateEvent;
import com.arm.debug.osext.ISessionStateListener;
import com.arm.debug.osext.ISymbolsImage;
import com.arm.debug.osext.OSExtensionPlugin;
import com.arm.debug.osext.SessionState;
import com.ose.fmm.FreezeModeMonitor;
import com.ose.fmm.ITargetProxy;
import com.ose.fmm.TargetProxyException;
import com.ose.fmm.ITargetProxyListener;

public class DebuggerPlugin extends AbstractUIPlugin implements IStartup
{
   public static final String PLUGIN_ID = "com.ose.fmd.arm";

   public static final String LAUNCH_ID = "com.arm.debugger.launcher2";

   public static final String ATTR_KERNEL_AWARENESS_LIBRARY =
      PLUGIN_ID + ".KERNEL_AWARENESS_LIBRARY";

   private static DebuggerPlugin plugin;

   private final IDebugSessionListener debugSessionHandler;

   private final ISessionStateListener debugSessionStateHandler;

   private final IExecutionContextStateListener executionContextStateHandler;

   private final Map<IDebugSession,FreezeModeMonitor> monitorMap;

   private final EventDispatcherThread eventDispatcher;

   public DebuggerPlugin()
   {
      plugin = this;
      debugSessionHandler = new DebugSessionHandler();
      debugSessionStateHandler = new DebugSessionStateHandler();
      executionContextStateHandler = new ExecutionContextStateHandler();
      monitorMap = Collections.synchronizedMap(
            new HashMap<IDebugSession,FreezeModeMonitor>());
      eventDispatcher = new EventDispatcherThread();
   }

   public static DebuggerPlugin getDefault()
   {
      return plugin;
   }

   public static String getUniqueIdentifier()
   {
      if (getDefault() == null)
      {
         return PLUGIN_ID;
      }
      else
      {
         return getDefault().getBundle().getSymbolicName();
      }
   }

   public static IStatus createErrorStatus(Throwable t)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            IStatus.ERROR,
            ((t != null) ? t.getMessage() : ""),
            t);
   }

   public static IStatus createErrorStatus(String message)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            IStatus.ERROR,
            message,
            null);
   }

   public static IStatus createErrorStatus(String message, Throwable t)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            IStatus.ERROR,
            message,
            t);
   }

   public static void log(IStatus status)
   {
      getDefault().getLog().log(status);
   }

   public static void log(Throwable t)
   {
      log(createErrorStatus(t));
   }

   public static void log(String message)
   {
      log(createErrorStatus(message));
   }

   public static void log(String message, Throwable t)
   {
      log(createErrorStatus(message, t));
   }

   public static void errorDialog(String title, String message, IStatus status)
   {
      log(status);

      IWorkbenchWindow window =
         getDefault().getWorkbench().getActiveWorkbenchWindow();
      if (window != null)
      {
         title = (title != null) ? title : "Error";
         ErrorDialog.openError(window.getShell(), title, message, status);
      }
   }

   public static void errorDialog(String title, String message, Throwable t)
   {
      errorDialog(title, message, createErrorStatus(t));
   }

   public void earlyStartup()
   {
      // Force early startup of this plugin.
   }

   public void start(BundleContext context) throws Exception
   {
      super.start(context);
      eventDispatcher.start();
      OSExtensionPlugin.getSessionManager().addSessionListener(debugSessionHandler);
   }

   public void stop(BundleContext context) throws Exception
   {
      OSExtensionPlugin.getSessionManager().removeSessionListener(debugSessionHandler);
      eventDispatcher.shutdown();
      monitorMap.clear();
      super.stop(context);
   }

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private static boolean isValidDebugTarget(IDebugSession debugSession)
   {
      try
      {
         ILaunchConfiguration config = debugSession.getLaunchConfiguration();
         ILaunchConfigurationType type = config.getType();
         return (type.supportsMode(ILaunchManager.DEBUG_MODE) &&
                 type.getIdentifier().equals(LAUNCH_ID));
      }
      catch (CoreException e)
      {
         log(e);
         return false;
      }
   }

   private static IExecutionContext getExecutionContext(
         Collection<IExecutionContext> executionContexts)
   {
      /*
       * If the target system is multi-core, we assume that it is
       * an SMP system and then we're only interested in core 0.
       * Return null if core 0 is not found.
       */
      for (IExecutionContext executionContext : executionContexts)
      {
         if ((executionContext.getKind() == ExecutionContextKind.CORE) &&
             (executionContext.getIdentifier() == 1))
         {
            return executionContext;
         }
      }
      return null;
   }

   private FreezeModeMonitor getMonitor(IDebugSession debugSession)
   {
      return monitorMap.get(debugSession);
   }

   private void handleDebugTargetCreated(IDebugSession debugSession)
   {
      FreezeModeMonitor monitor = getMonitor(debugSession);
      if (monitor == null)
      {
         // If a kernel awareness library was specified in the launch
         // configuration, create a freeze mode monitor instance and
         // pass it the kernel awareness library path and target proxy.
         try
         {
            ILaunchConfiguration config = debugSession.getLaunchConfiguration();
            String libraryPath =
               config.getAttribute(ATTR_KERNEL_AWARENESS_LIBRARY, "").trim();
            if (libraryPath.length() > 0)
            {
               // Create the freeze mode monitor instance.
               TargetProxy targetProxy = new TargetProxy(debugSession);
               monitor = new FreezeModeMonitor(libraryPath, targetProxy, false);
               monitorMap.put(debugSession, monitor);

               // Start listening on execution context state changes in the debug session.
               debugSession.addExecutionContextStateChangeListener(executionContextStateHandler);
            }
         }
         catch (CoreException e)
         {
            asyncExec(new ShowErrorDialogRunner("Error reading kernel awareness library path", e));
         }
         catch (Throwable t)
         {
            asyncExec(new ShowErrorDialogRunner("Error creating freeze mode monitor", t));
         }
      }
   }

   private void handleDebugTargetTerminated(IDebugSession debugSession)
   {
      FreezeModeMonitor monitor = getMonitor(debugSession);
      if (monitor != null)
      {
         // Note: Unfortunately, we can't remove the execution context state
         // listener from the debug session when it's disconnecting. However,
         // when the debug session has disconnected, our execution context state
         // listener will be removed anyway.

         // Stop listening on execution context state changes in the debug session.
         /*
         try
         {
            debugSession.removeExecutionContextStateChangeListener(executionContextStateHandler);
         }
         catch (DebugSessionException e)
         {
            log("Error removing execution context state change listener", e);
         }
         */

         // Terminate the freeze mode monitor instance.
         monitorMap.remove(debugSession);
         monitor.close();
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.dispose();
      }
   }

   private void handleDebugTargetSuspended(IDebugSession debugSession)
   {
      // Inform the freeze mode monitor instance that the target has been
      // suspended.
      FreezeModeMonitor monitor = getMonitor(debugSession);
      if (monitor != null)
      {
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.fireTargetSuspended();
      }
   }

   private void handleDebugTargetResumed(IDebugSession debugSession)
   {
      // Inform the freeze mode monitor instance that the target has been
      // resumed.
      FreezeModeMonitor monitor = getMonitor(debugSession);
      if (monitor != null)
      {
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.fireTargetResumed();
      }
   }

   private class DebugSessionHandler implements IDebugSessionListener
   {
      public void sessionStarted(IDebugSession debugSession)
      {
         if (isValidDebugTarget(debugSession))
         {
            debugSession.addSessionStateListener(debugSessionStateHandler);
         }
      }

      public void sessionDisposed(IDebugSession debugSession)
      {
         if (isValidDebugTarget(debugSession))
         {
            debugSession.removeSessionStateListener(debugSessionStateHandler);
         }
      }

      public void currentSessionChanged(IDebugSession newDebugSession)
      {
      }
   }

   private class DebugSessionStateHandler implements ISessionStateListener
   {
      public void sessionStateChanged(final ISessionStateEvent event)
      {
         SessionState state = event.getNewState();
         if (state == SessionState.CONNECTED)
         {
            eventDispatcher.addEvent(new Runnable()
            {
               public void run()
               {
                  handleDebugTargetCreated(event.getSession());
               }
            });
         }
         else if (state == SessionState.DISCONNECTED)
         {
            eventDispatcher.addEvent(new Runnable()
            {
               public void run()
               {
                  handleDebugTargetTerminated(event.getSession());
               }
            });
         }
      }
   }

   private class ExecutionContextStateHandler implements IExecutionContextStateListener
   {
      public void executionContextStateChanged(IExecutionContextStateEvent event)
      {
         IExecutionContext executionContext =
               getExecutionContext(event.getExecutionContexts());
         if (executionContext != null)
         {
            final IDebugSession debugSession = event.getSession();
            if (executionContext.getState() == ExecutionContextState.RUNNING)
            {
               eventDispatcher.addEvent(new Runnable()
               {
                  public void run()
                  {
                     handleDebugTargetResumed(debugSession);
                  }
               });
            }
            else
            {
               eventDispatcher.addEvent(new Runnable()
               {
                  public void run()
                  {
                     handleDebugTargetSuspended(debugSession);
                  }
               });
            }
         }
      }
   }

   private static class TargetProxy implements ITargetProxy
   {
      private final IDebugSession debugSession;

      private final IAddressSpace addressSpace;

      private final ListenerList listenerList;

      TargetProxy(IDebugSession debugSession)
      {
         this.debugSession = debugSession;
         listenerList = new ListenerList();
         try
         {
            Collection<ISymbolsImage> images = debugSession.listSymbolsImages();
            if (images.isEmpty())
            {
               throw new RuntimeException("No symbol images found");
            }
            ISymbolsImage[] symbolsImages = images.toArray(new ISymbolsImage[0]);
            addressSpace = symbolsImages[0].getOffset().getAddressSpace();
         }
         catch (DebugSessionException e)
         {
            throw new RuntimeException("Error retrieving symbol images", e);
         }
      }

      public boolean isBigEndian()
      {
         try
         {
            IExecutionContext executionContext =
                  getExecutionContext(debugSession.listExecutionContexts());
            return debugSession.getMemoryByteOrder(executionContext).isBigEndianCode();
         }
         catch (Exception e)
         {
            log("Error retrieving byte order", e);
            return false;
         }
      }

      public boolean isSuspended()
      {
         try
         {
            IExecutionContext executionContext =
                  getExecutionContext(debugSession.listExecutionContexts());
            return (executionContext.getState() != ExecutionContextState.RUNNING);
         }
         catch (Exception e)
         {
            log("Error retrieving execution context state", e);
            return true;
         }
      }

      public String getName()
      {
         try
         {
            IExecutionContext executionContext =
                  getExecutionContext(debugSession.listExecutionContexts());
            return executionContext.getName();
         }
         catch (Exception e)
         {
            log("Error retrieving execution context name", e);
            return "Unknown";
         }
      }

      public long getAddress(String symbol) throws TargetProxyException
      {
         try
         {
            IAddress address = debugSession.lookupSymbolAddress(symbol);
            if (address == null)
            {
               throw new TargetProxyException("Cannot find symbol " + symbol);
            }
            return address.getLinearAddress();
         }
         catch (DebugSessionException e)
         {
            throw new TargetProxyException("Error looking up symbol " + symbol, e);
         }
      }

      public byte[] getMemory(long address, int length)
         throws TargetProxyException
      {
         try
         {
            IExecutionContext executionContext =
                  getExecutionContext(debugSession.listExecutionContexts());
            if (executionContext == null)
            {
               throw new TargetProxyException("No execution context found, " +
                  "could not read memory at address " + Long.toHexString(address));
            }
            IAddress addr = addressSpace.createAddress(address);
            IMemoryBlock memoryBlock =
                  debugSession.readMemory(executionContext, addr, length, 0);
            return memoryBlock.getBytes();
         }
         catch (DebugSessionException e)
         {
            throw new TargetProxyException("Error reading memory at address "
                  + Long.toHexString(address), e);
         }
      }

      public void addTargetProxyListener(ITargetProxyListener listener)
      {
         listenerList.add(listener);
      }

      public void removeTargetProxyListener(ITargetProxyListener listener)
      {
         listenerList.remove(listener);
      }

      void fireTargetSuspended()
      {
         Object[] listeners = listenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ITargetProxyListener listener = (ITargetProxyListener) listeners[i];
            listener.targetSuspended(this);
         }
      }

      void fireTargetResumed()
      {
         Object[] listeners = listenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ITargetProxyListener listener = (ITargetProxyListener) listeners[i];
            listener.targetResumed(this);
         }
      }

      void dispose()
      {
         listenerList.clear();
      }
   }

   private static class EventDispatcherThread extends Thread
   {
      private static final int QUEUE_CAPACITY = 1000;

      private final BlockingQueue<Runnable> queue;

      private volatile boolean open;

      EventDispatcherThread()
      {
         super("Debugger Event Dispatcher Thread");
         setDaemon(true);
         queue = new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY);
         open = true;
      }

      public void run()
      {
         while (open)
         {
            try
            {
               Runnable r = queue.take();
               r.run();
            }
            catch (InterruptedException e)
            {
               break;
            }
            catch (Throwable t)
            {
               asyncExec(new ShowErrorDialogRunner(t.getMessage(), t));
            }
         }
      }

      public void addEvent(Runnable r)
      {
         boolean added = queue.offer(r);
         if (!added)
         {
            log("Warning: Debugger event lost in " + getUniqueIdentifier());
         }
      }

      public void shutdown()
      {
         open = false;
         interrupt();
         queue.clear();
      }
   }

   private static class ShowErrorDialogRunner implements Runnable
   {
      private final String message;
      private final Throwable t;

      ShowErrorDialogRunner(String message, Throwable t)
      {
         if (message == null)
         {
            throw new IllegalArgumentException();
         }
         this.message = message;
         this.t = t;
      }

      public void run()
      {
         if (t != null)
         {
            errorDialog(null, message, t);
         }
         else
         {
            MessageDialog.openError(null, "Error", message);
         }
      }
   }
}
