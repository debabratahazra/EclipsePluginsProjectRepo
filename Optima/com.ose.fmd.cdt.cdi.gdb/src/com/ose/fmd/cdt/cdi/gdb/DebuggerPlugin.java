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

package com.ose.fmd.cdt.cdi.gdb;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.IBinaryParser.ISymbol;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.model.ICDebugTarget;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchDelegate;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IMemoryBlockExtension;
import org.eclipse.debug.core.model.IMemoryBlockRetrievalExtension;
import org.eclipse.debug.core.model.MemoryByte;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import com.ose.fmm.FreezeModeMonitor;
import com.ose.fmm.ITargetProxy;
import com.ose.fmm.TargetProxyException;
import com.ose.fmm.ITargetProxyListener;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;

public class DebuggerPlugin extends AbstractUIPlugin implements IStartup
{
   public static final String PRODUCT_ID = "com.ose.optima.ide";

   public static final String PLUGIN_ID = "com.ose.fmd.cdt.cdi.gdb";

   public static final String LAUNCH_ID_GDB_LOCAL =
      ICDTLaunchConfigurationConstants.ID_LAUNCH_C_APP;

   public static final String LAUNCH_ID_GDB_JTAG =
      "org.eclipse.cdt.debug.gdbjtag.launchConfigurationType";

   public static final String PREFERRED_LOCAL_LAUNCH_DELEGATE =
      "org.eclipse.cdt.cdi.launch.localCLaunch";

   public static final String PREFERRED_JTAG_LAUNCH_DELEGATE =
      "org.eclipse.cdt.debug.gdbjtag.core.cdiLaunchDelegate";

   public static final String ATTR_KERNEL_AWARENESS_LIBRARY =
      PLUGIN_ID + ".KERNEL_AWARENESS_LIBRARY";

   private static DebuggerPlugin plugin;

   private final IDebugEventSetListener debugEventHandler;

   private final Map monitorMap;

   private final EventDispatcherThread eventDispatcher;

   public DebuggerPlugin()
   {
      plugin = this;
      debugEventHandler = new DebugEventHandler();
      monitorMap = Collections.synchronizedMap(new HashMap());
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
      DebugPlugin.getDefault().addDebugEventListener(debugEventHandler);
      // A bug in the Eclipse platform prevents us from setting the default
      // launch delegates from the plugin_customization.ini file. Instead,
      // we have to do it programatically.
      setDefaultLaunchDelegates();
   }

   public void stop(BundleContext context) throws Exception
   {
      DebugPlugin.getDefault().removeDebugEventListener(debugEventHandler);
      eventDispatcher.shutdown();
      monitorMap.clear();
      super.stop(context);
   }

   private static void setDefaultLaunchDelegates()
   {
      ILaunchManager launchMgr = DebugPlugin.getDefault().getLaunchManager();

      ILaunchConfigurationType localConfigType =
         launchMgr.getLaunchConfigurationType(LAUNCH_ID_GDB_LOCAL);

      ILaunchConfigurationType jtagConfigType =
         launchMgr.getLaunchConfigurationType(LAUNCH_ID_GDB_JTAG);

      Set<String> modes = new HashSet<String>();
      modes.add(ILaunchManager.DEBUG_MODE);

      if (localConfigType != null)
      {
         try
         {
            setDefaultLaunchDelegate(localConfigType,
                                     modes,
                                     PREFERRED_LOCAL_LAUNCH_DELEGATE);
         }
         catch (CoreException e)
         {
            log(createErrorStatus(
               "Error setting preferred local debug launch delegate", e));
         }
      }

      if (jtagConfigType != null)
      {
         try
         {
            setDefaultLaunchDelegate(jtagConfigType,
                                     modes,
                                     PREFERRED_JTAG_LAUNCH_DELEGATE);
         }
         catch (CoreException e)
         {
            log(createErrorStatus(
               "Error setting preferred JTAG debug launch delegate", e));
         }
      }
   }

   private static void setDefaultLaunchDelegate(
         ILaunchConfigurationType configType,
         Set modes,
         String defaultDelegate)
      throws CoreException
   {
      if (configType.getPreferredDelegate(modes) == null)
      {
         ILaunchDelegate[] delegates = configType.getDelegates(modes);
         for (ILaunchDelegate delegate : delegates)
         {
            if (delegate.getId().equals(defaultDelegate))
            {
               configType.setPreferredDelegate(modes, delegate);
               break;
            }
         }
      }
   }

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private static boolean isValidDebugTarget(DebugEvent event)
   {
      ICDebugTarget target;

      if (event.getSource() instanceof ICDebugTarget)
      {
         target = (ICDebugTarget) event.getSource();
      }
      else
      {
         return false;
      }

      ILaunch launch = target.getLaunch();
      if ((launch == null) ||
          !launch.getLaunchMode().equals(ILaunchManager.DEBUG_MODE))
      {
         return false;
      }

      ILaunchConfiguration config = launch.getLaunchConfiguration();
      if (config == null)
      {
         return false;
      }

      try
      {
         ILaunchConfigurationType type = config.getType();
         String typeId = type.getIdentifier();
         return (typeId.equals(LAUNCH_ID_GDB_LOCAL) ||
                 typeId.equals(LAUNCH_ID_GDB_JTAG));
      }
      catch (CoreException e)
      {
         log(e);
         return false;
      }
   }

   FreezeModeMonitor getMonitor(IDebugTarget target)
   {
      return (FreezeModeMonitor) monitorMap.get(target.getLaunch());
   }

   private void handleDebugTargetCreated(IDebugTarget target)
   {
      // Check out FLEXlm license.
      IProduct product = Platform.getProduct();
      if ((product != null) && product.getId().equals(PRODUCT_ID))
      {
         try
         {
            LicenseManager lm = LicenseManager.getInstance();
            long cookie = (long) (Math.random() * Long.MAX_VALUE);
            long r = lm.registerPlugin("com.ose.cdt.launch", "1.100", cookie);
            long t = System.currentTimeMillis() / 10000;
            if ((r != (cookie ^ t)) && (r != (cookie ^ (t - 1))))
            {
               throw new LicenseException("Incorrect response value");
            }
         }
         catch (LicenseException e)
         {
            // Report the error and terminate the debug session.
            asyncExec(new ShowErrorDialogRunner("License Checkout Error",
               "Could not check out a FLEXlm license for OSE C/C++ Development " +
               "Tools Launching Support plugin (com.ose.cdt.launch).", e));
            try
            {
               target.terminate();
            } catch (DebugException ignore) {}
         }
      }

      FreezeModeMonitor monitor = getMonitor(target);
      if (monitor == null)
      {
         // If a kernel awareness library was specified in the launch
         // configuration, create a freeze mode monitor instance and
         // pass it the kernel awareness library path and target proxy.
         try
         {
            ILaunch launch = target.getLaunch();
            ILaunchConfiguration config = launch.getLaunchConfiguration();
            String libraryPath =
               config.getAttribute(ATTR_KERNEL_AWARENESS_LIBRARY, "").trim();
            if (libraryPath.length() > 0)
            {
               TargetProxy targetProxy = new TargetProxy(target);
               monitor = new FreezeModeMonitor(libraryPath, targetProxy, true);
               monitorMap.put(launch, monitor);
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

   private void handleDebugTargetTerminated(IDebugTarget target)
   {
      // Terminate the freeze mode monitor instance.
      FreezeModeMonitor monitor = getMonitor(target);
      if (monitor != null)
      {
         monitorMap.remove(target.getLaunch());
         monitor.close();
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.dispose();
      }

      // Check in FLEXlm license.
      // XXX: Due to a bug in the FLEXlm Java client library on Solaris, we
      // cannot check in the FLEXlm license without hanging the whole JVM when
      // checking out the FLEXlm license again.
      /*
      IProduct product = Platform.getProduct();
      if ((product != null) && product.getId().equals(PRODUCT_ID))
      {
         LicenseManager.getInstance().unregisterPlugin("com.ose.cdt.launch");
      }
      */
   }

   private void handleDebugTargetSuspended(IDebugTarget target)
   {
      // Inform the freeze mode monitor instance that the target has been
      // suspended.
      FreezeModeMonitor monitor = getMonitor(target);
      if (monitor != null)
      {
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.fireTargetSuspended();
      }
   }

   private void handleDebugTargetResumed(IDebugTarget target)
   {
      // Inform the freeze mode monitor instance that the target has been
      // resumed.
      FreezeModeMonitor monitor = getMonitor(target);
      if (monitor != null)
      {
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.fireTargetResumed();
      }
   }

   private class DebugEventHandler implements IDebugEventSetListener
   {
      public void handleDebugEvents(DebugEvent[] events)
      {
         for (int i = 0; i < events.length; i++)
         {
            DebugEvent event = events[i];

            if (isValidDebugTarget(event))
            {
               final IDebugTarget target = (IDebugTarget) event.getSource();

               switch (event.getKind())
               {
                  case DebugEvent.CREATE:
                     eventDispatcher.addEvent(new Runnable()
                     {
                        public void run()
                        {
                           handleDebugTargetCreated(target);
                        }
                     });
                     break;
                  case DebugEvent.TERMINATE:
                     eventDispatcher.addEvent(new Runnable()
                     {
                        public void run()
                        {
                           handleDebugTargetTerminated(target);
                        }
                     });
                     break;
                  case DebugEvent.SUSPEND:
                     eventDispatcher.addEvent(new Runnable()
                     {
                        public void run()
                        {
                           handleDebugTargetSuspended(target);
                        }
                     });
                     break;
                  case DebugEvent.RESUME:
                     eventDispatcher.addEvent(new Runnable()
                     {
                        public void run()
                        {
                           handleDebugTargetResumed(target);
                        }
                     });
                     break;
                  default:
                     break;
               }
            }
         }
      }
   }

   private static class TargetProxy implements ITargetProxy
   {
      private final ICDebugTarget target;

      private final ListenerList listenerList;

      TargetProxy(IDebugTarget target)
      {
         if (!(target instanceof ICDebugTarget))
         {
            throw new IllegalArgumentException();
         }
         this.target = (ICDebugTarget) target;
         listenerList = new ListenerList();
      }

      public boolean isBigEndian()
      {
         return !target.isLittleEndian();
      }

      public boolean isSuspended()
      {
         return target.isSuspended();
      }

      public String getName()
      {
         try
         {
            return target.getName();
         }
         catch (DebugException e)
         {
            return "nameless";
         }
      }

      public long getAddress(String symbol) throws TargetProxyException
      {
         IBinaryObject binary = target.getExecFile();

         if (binary != null)
         {
            ISymbol[] symbols = binary.getSymbols();
            for (int i = 0; i < symbols.length; i++)
            {
               ISymbol sym = symbols[i];
               if (sym.getName().equals(symbol) &&
                   (sym.getType() == ISymbol.VARIABLE))
               {
                  return sym.getAddress().getValue().longValue();
               }
            }
         }

         throw new TargetProxyException("Cannot find symbol " + symbol);
      }

      public byte[] getMemory(long address, int length)
         throws TargetProxyException
      {
         IMemoryBlockRetrievalExtension memoryBlockRetrieval =
            getMemoryBlockRetrievalExtension();
         if (memoryBlockRetrieval != null)
         {
            return getMemoryExtended(memoryBlockRetrieval, address, length);
         }
         else
         {
            try
            {
               long size = 0xFFFFFFFFL & length;
               IMemoryBlock memoryBlock = target.getMemoryBlock(address, size);
               return memoryBlock.getBytes();
            }
            catch (DebugException e)
            {
               throw new TargetProxyException("Error reading memory at address "
                  + Long.toHexString(address), e);
            }
         }
      }

      private IMemoryBlockRetrievalExtension getMemoryBlockRetrievalExtension()
      {
         if (target instanceof IMemoryBlockRetrievalExtension)
         {
            return (IMemoryBlockRetrievalExtension) target;
         }
         else
         {
            return (IMemoryBlockRetrievalExtension)
               target.getAdapter(IMemoryBlockRetrievalExtension.class);
         }
      }

      private byte[] getMemoryExtended(
            IMemoryBlockRetrievalExtension memoryBlockRetrieval,
            long address,
            int length)
         throws TargetProxyException
      {
         try
         {
            String expression = "0x" + Long.toHexString(address).toUpperCase();
            IMemoryBlockExtension memoryBlock =
               memoryBlockRetrieval.getExtendedMemoryBlock(expression, target);
            int addressableSizeInBytes = memoryBlock.getAddressableSize();
            long bytesSize = 0xFFFFFFFFL & length;
            long units = (bytesSize / addressableSizeInBytes) +
               (((bytesSize % addressableSizeInBytes) != 0) ? 1 : 0);
            MemoryByte[] memoryBytes = memoryBlock.getBytesFromAddress(
               BigInteger.valueOf(address), units);
            memoryBlock.dispose();
            byte[] bytes = new byte[memoryBytes.length];
            for (int i = 0; i < memoryBytes.length; i++)
            {
               bytes[i] = memoryBytes[i].getValue();
            }
            return bytes;
         }
         catch (DebugException e)
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
      private final String title;
      private final String message;
      private final Throwable t;

      ShowErrorDialogRunner(String message, Throwable t)
      {
         this(null, message, t);
      }

      ShowErrorDialogRunner(String title, String message, Throwable t)
      {
         if (message == null)
         {
            throw new IllegalArgumentException();
         }
         this.title = title;
         this.message = message;
         this.t = t;
      }

      public void run()
      {
         if (t != null)
         {
            errorDialog(title, message, t);
         }
         else
         {
            MessageDialog.openError(
                  null, (title != null) ? title : "Error", message);
         }
      }
   }
}
