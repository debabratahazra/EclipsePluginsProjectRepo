/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.fmd.ti;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.eclipse.cdt.core.IAddress;
import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.Query;
import org.eclipse.cdt.dsf.datamodel.DMContexts;
import org.eclipse.cdt.dsf.datamodel.IDMContext;
import org.eclipse.cdt.dsf.debug.service.IMemory;
import org.eclipse.cdt.dsf.debug.service.IRunControl;
import org.eclipse.cdt.dsf.debug.service.IMemory.IMemoryDMContext;
import org.eclipse.cdt.dsf.debug.service.IModules.ISymbolDMContext;
import org.eclipse.cdt.dsf.debug.service.IRunControl.IContainerDMContext;
import org.eclipse.cdt.dsf.debug.service.IRunControl.IExecutionDMContext;
import org.eclipse.cdt.dsf.debug.service.IRunControl.IExitedDMEvent;
import org.eclipse.cdt.dsf.debug.service.IRunControl.IResumedDMEvent;
import org.eclipse.cdt.dsf.debug.service.IRunControl.ISuspendedDMEvent;
import org.eclipse.cdt.dsf.service.DsfServiceEventHandler;
import org.eclipse.cdt.dsf.service.DsfServicesTracker;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.cdt.dsf.ui.viewmodel.datamodel.IDMVMContext;
import org.eclipse.cdt.utils.Addr32;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.MemoryByte;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
import com.ti.ccstudio.debug.dsf.extensions.service.IModules2;
import com.ti.ccstudio.debug.dsf.extensions.service.IModules2.ISymbolData;

public class DebuggerPlugin extends AbstractUIPlugin implements IStartup
{
   public static final String PLUGIN_ID = "com.ose.fmd.ti";

   public static final String LAUNCH_ID =
      "com.ti.ccstudio.debug.launchType.device.debugging";

   public static final String ATTR_KERNEL_AWARENESS_LIBRARY =
      PLUGIN_ID + ".KERNEL_AWARENESS_LIBRARY";

   public static final String ATTR_TARGET_BIG_ENDIAN =
      PLUGIN_ID + ".TARGET_BIG_ENDIAN";

   private static DebuggerPlugin plugin;

   private final IDebugContextListener debugContextHandler;

   private final ServiceEventHandler serviceEventHandler;

   private final Map monitorMap;

   private final EventDispatcherThread eventDispatcher;

   public DebuggerPlugin()
   {
      plugin = this;
      debugContextHandler = new DebugContextHandler();
      serviceEventHandler = new ServiceEventHandler();
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
      DebugUITools.getDebugContextManager().addDebugContextListener(
            debugContextHandler);
   }

   public void stop(BundleContext context) throws Exception
   {
      DebugUITools.getDebugContextManager().removeDebugContextListener(
            debugContextHandler);
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

   private static ILaunch getLaunch(IDMContext dmContext)
   {
      if (dmContext instanceof IAdaptable)
      {
         IAdaptable adaptable = (IAdaptable) dmContext;
         ILaunch launch = (ILaunch) adaptable.getAdapter(ILaunch.class);
         return launch;
      }
      else
      {
         return null;
      }
   }

   private static IExecutionDMContext getDebugTarget(IDMContext dmContext)
   {
      IExecutionDMContext target =
         DMContexts.getAncestorOfType(dmContext, IExecutionDMContext.class);
      // Filter out virtual core groups.
      return (target instanceof IContainerDMContext) ? null : target;
   }

   private static boolean isValidDebugTarget(IExecutionDMContext target)
   {
      if (target == null)
      {
         return false;
      }

      ILaunch launch = getLaunch(target);
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
         return typeId.equals(LAUNCH_ID);
      }
      catch (CoreException e)
      {
         log(e);
         return false;
      }
   }

   private static boolean hasRunControlService(IExecutionDMContext target)
   {
      DsfServicesTracker servicesTracker = new DsfServicesTracker(
         getDefault().getBundle().getBundleContext(), target.getSessionId());
      IRunControl runControlService = servicesTracker.getService(IRunControl.class);
      servicesTracker.dispose();
      return (runControlService != null);
   }

   private FreezeModeMonitor getMonitor(IExecutionDMContext target)
   {
      return (FreezeModeMonitor) monitorMap.get(target);
   }

   private void handleDebugTargetCreated(IExecutionDMContext target)
   {
      FreezeModeMonitor monitor = getMonitor(target);
      if (monitor == null)
      {
         // If a kernel awareness library was specified in the launch
         // configuration, create a freeze mode monitor instance and
         // pass it the kernel awareness library path and target proxy.
         try
         {
            ILaunch launch = getLaunch(target);
            ILaunchConfiguration config = launch.getLaunchConfiguration();
            String libraryPath =
               config.getAttribute(ATTR_KERNEL_AWARENESS_LIBRARY, "").trim();
            boolean bigEndian = config.getAttribute(ATTR_TARGET_BIG_ENDIAN, true);
            if (libraryPath.length() > 0)
            {
               // Create the freeze mode monitor instance.
               TargetProxy targetProxy = new TargetProxy(target, bigEndian);
               monitor = new FreezeModeMonitor(libraryPath, targetProxy, true);
               monitorMap.put(target, monitor);

               // Start listening on events from the debugger.
               // Note: If this launch contains multiple debug targets, the
               // debug target that is created first will add the common service
               // event listener object. When the other debug targets in the
               // same launch are created, they will also try to add the common
               // service event listener object but that will have no effect
               // since it has already been added.
               DsfSession session = DsfSession.getSession(target.getSessionId());
               if (session != null)
               {
                  session.addServiceEventListener(serviceEventHandler, null);
               }

               // Inform the freeze mode monitor instance if the target is
               // suspended.
               if (targetProxy.isSuspended())
               {
                  targetProxy.fireTargetSuspended();
               }
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

   private void handleDebugTargetTerminated(IExecutionDMContext target)
   {
      FreezeModeMonitor monitor = getMonitor(target);
      if (monitor != null)
      {
         // Note: We don't remove the common service event listener object from
         // the session object of the launch since we don't know if there are
         // any other living debug targets in the launch left. When the session
         // is terminated, the common service event listener object will be
         // disposed anyway.

         // Stop listening on events from the debugger.
         //DsfSession session = DsfSession.getSession(target.getSessionId());
         //if (session != null)
         //{
         //   session.removeServiceEventListener(serviceEventHandler);
         //}

         // Terminate the freeze mode monitor instance.
         monitorMap.remove(target);
         monitor.close();
         TargetProxy targetProxy = (TargetProxy) monitor.getTargetProxy();
         targetProxy.dispose();
      }
   }

   private void handleDebugTargetSuspended(IExecutionDMContext target)
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

   private void handleDebugTargetResumed(IExecutionDMContext target)
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

   private class DebugContextHandler implements IDebugContextListener
   {
      public void debugContextChanged(DebugContextEvent event)
      {
         ISelection sel = event.getContext();
         if (!sel.isEmpty() && (event.getFlags() == DebugContextEvent.ACTIVATED))
         {
            Object obj = ((StructuredSelection) sel).getFirstElement();
            IDMVMContext dmvmContext = null;

            if (obj instanceof IDMVMContext)
            {
               dmvmContext = (IDMVMContext) obj;
            }
            else if (obj instanceof IAdaptable)
            {
               dmvmContext = (IDMVMContext)
                     ((IAdaptable) obj).getAdapter(IDMVMContext.class);
            }

            if (dmvmContext != null)
            {
               final IExecutionDMContext target =
                  getDebugTarget(dmvmContext.getDMContext());
               // Sometimes when a target has been terminated, this method
               // can still be called just after its termination. A terminated
               // target can be detected by not having a run control service
               // connected with it any longer.
               if (isValidDebugTarget(target) && hasRunControlService(target))
               {
                  FreezeModeMonitor monitor = getMonitor(target);
                  if (monitor == null)
                  {
                     eventDispatcher.addEvent(new Runnable()
                     {
                        public void run()
                        {
                           handleDebugTargetCreated(target);
                        }
                     });
                  }
               }
            }
         }
      }
   }

   public class ServiceEventHandler
   {
      @DsfServiceEventHandler
      public void handleExitEvent(IExitedDMEvent event)
      {
         final IExecutionDMContext target = getDebugTarget(event.getDMContext());
         if (isValidDebugTarget(target))
         {
            eventDispatcher.addEvent(new Runnable()
            {
               public void run()
               {
                  handleDebugTargetTerminated(target);
               }
            });
         }
      }

      @DsfServiceEventHandler
      public void handleSuspendEvent(ISuspendedDMEvent event)
      {
         final IExecutionDMContext target = getDebugTarget(event.getDMContext());
         if (isValidDebugTarget(target))
         {
            eventDispatcher.addEvent(new Runnable()
            {
               public void run()
               {
                  handleDebugTargetSuspended(target);
               }
            });
         }
      }

      @DsfServiceEventHandler
      public void handleResumeEvent(IResumedDMEvent event)
      {
         final IExecutionDMContext target = getDebugTarget(event.getDMContext());
         if (isValidDebugTarget(target))
         {
            eventDispatcher.addEvent(new Runnable()
            {
               public void run()
               {
                  handleDebugTargetResumed(target);
               }
            });
         }
      }
   }

   private static class TargetProxy implements ITargetProxy
   {
      private final IExecutionDMContext target;

      private final boolean bigEndian;

      private final ISymbolDMContext symbolContext;

      private final IMemoryDMContext memoryContext;

      private final DsfServicesTracker servicesTracker;

      private final IRunControl runControlService;

      private final IModules2 modulesService;

      private final IMemory memoryService;

      private final DsfSession session;

      private final ListenerList listenerList;

      TargetProxy(IExecutionDMContext target, boolean bigEndian)
      {
         this.target = target;
         this.bigEndian = bigEndian;
         symbolContext =
            DMContexts.getAncestorOfType(target, ISymbolDMContext.class);
         if (symbolContext == null)
         {
            throw new IllegalArgumentException("No symbol support in debugger");
         }
         memoryContext =
            DMContexts.getAncestorOfType(target, IMemoryDMContext.class);
         if (memoryContext == null)
         {
            throw new IllegalArgumentException("No memory support in debugger");
         }
         servicesTracker = new DsfServicesTracker(
            getDefault().getBundle().getBundleContext(), target.getSessionId());
         runControlService = servicesTracker.getService(IRunControl.class);
         if (runControlService == null)
         {
            throw new IllegalArgumentException("No run control support in debugger");
         }
         modulesService = servicesTracker.getService(IModules2.class);
         if (modulesService == null)
         {
            throw new IllegalArgumentException("No module support in debugger");
         }
         memoryService = servicesTracker.getService(IMemory.class);
         if (memoryService == null)
         {
            throw new IllegalArgumentException("No memory support in debugger");
         }
         session = DsfSession.getSession(target.getSessionId());
         if (session == null)
         {
            throw new IllegalArgumentException("Invalid debugging session");
         }
         listenerList = new ListenerList();
      }

      public boolean isBigEndian()
      {
         return bigEndian;
         // The following code is unfortunately unreliable on TI CCS v5 beta.
         /*
         try
         {
            long address = getAddress("ose_version");
            MemoryQuery memoryQuery = new MemoryQuery(address, 4);
            session.getExecutor().submit(memoryQuery);
            MemoryByte[] memoryBytes = memoryQuery.get();
            if (memoryBytes[0].isEndianessKnown())
            {
               return memoryBytes[0].isBigEndian();
            }
            else
            {
               log("Target byte order is unknown (defaulting to big endian)");
               return true;
            }
         }
         catch (Exception e)
         {
            log("Error determining target byte order (defaulting to big endian)", e);
            return true;
         }
         */
      }

      public boolean isSuspended()
      {
         return runControlService.isSuspended(target);
      }

      public String getName()
      {
         return target.toString();
      }

      public long getAddress(String symbol) throws TargetProxyException
      {
         AddressQuery addressQuery = new AddressQuery(symbol);
         session.getExecutor().submit(addressQuery);
         try
         {
            ISymbolData symbolData = addressQuery.get();
            return symbolData.getAddress().getValue().longValue();
         }
         catch (Exception e)
         {
            throw new TargetProxyException("Error looking up symbol " + symbol, e);
         }
      }

      public byte[] getMemory(long address, int length)
         throws TargetProxyException
      {
         MemoryQuery memoryQuery = new MemoryQuery(address, length);
         session.getExecutor().submit(memoryQuery);
         try
         {
            MemoryByte[] memoryBytes = memoryQuery.get();
            byte[] bytes = new byte[memoryBytes.length];
            for (int i = 0; i < memoryBytes.length; i++)
            {
               bytes[i] = memoryBytes[i].getValue();
            }
            return bytes;
         }
         catch (Exception e)
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
         servicesTracker.dispose();
         listenerList.clear();
      }

      private class AddressQuery extends Query<ISymbolData>
      {
         private final String symbol;

         AddressQuery(String symbol)
         {
            this.symbol = symbol;
         }

         @Override
         protected void execute(DataRequestMonitor<ISymbolData> drm)
         {
            modulesService.findGlobalSymbol(symbolContext, symbol, drm);
         }
      }

      private class MemoryQuery extends Query<MemoryByte[]>
      {
         private final long address;

         private final int length;

         MemoryQuery(long address, int length)
         {
            this.address = address;
            this.length = length;
         }

         @Override
         protected void execute(DataRequestMonitor<MemoryByte[]> drm)
         {
            IAddress addr = new Addr32(address);
            int addressableSizeInBytes = 4;
            int units = (length / addressableSizeInBytes) +
               (((length % addressableSizeInBytes) != 0) ? 1 : 0);
            memoryService.getMemory(memoryContext,
                                    addr,
                                    0,
                                    addressableSizeInBytes,
                                    units,
                                    drm);
         }
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
