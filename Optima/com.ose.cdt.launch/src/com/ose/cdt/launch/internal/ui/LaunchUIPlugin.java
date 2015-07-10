/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.cdt.launch.internal.ui;

import com.ose.cdt.launch.internal.DumpLaunchConfigurationDelegate;
import com.ose.cdt.launch.internal.LoadModuleLaunchConfigurationDelegate;
import com.ose.cdt.launch.internal.ProgramProcess;
import com.ose.launch.IOSELaunchConfigurationConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class LaunchUIPlugin extends AbstractUIPlugin
   implements IDebugEventSetListener
{
   public static final String PLUGIN_ID = "com.ose.cdt.launch";

   public static final String PLUGIN_NAME =
      "OSE C/C++ Development Tools Launching Support";

   private static LaunchUIPlugin plugin;

   private static Shell debugDialogShell;

   public LaunchUIPlugin()
   {
      super();
      plugin = this;
   }

   public static LaunchUIPlugin getDefault()
   {
      return plugin;
   }

   public static String getUniqueIdentifier()
   {
      if (getDefault() == null)
      {
         // If the default instance is not yet initialized,
         // return a static identifier. This identifier must
         // match the plugin id defined in plugin.xml.
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

   public static void setDialogShell(Shell shell)
   {
      debugDialogShell = shell;
   }

   public static Shell getShell()
   {
      Shell shell = getActiveWorkbenchShell();
      if (shell != null)
      {
         return shell;
      }
      else
      {
         if (debugDialogShell != null)
         {
            if (!debugDialogShell.isDisposed())
            {
               return debugDialogShell;
            }
            else
            {
               debugDialogShell = null;
            }
         }
         IWorkbenchWindow[] windows =
            getDefault().getWorkbench().getWorkbenchWindows();
         return windows[0].getShell();
      }
   }

   public static IWorkbenchWindow getActiveWorkbenchWindow()
   {
      return getDefault().getWorkbench().getActiveWorkbenchWindow();
   }

   public static IWorkbenchPage getActivePage()
   {
      IWorkbenchWindow window = getActiveWorkbenchWindow();
      return (window != null) ? window.getActivePage() : null;
   }

   public static Shell getActiveWorkbenchShell()
   {
      IWorkbenchWindow window = getActiveWorkbenchWindow();
      return (window != null) ? window.getShell() : null;
   }

   public static void errorDialog(String title, String message, IStatus status)
   {
      log(status);

      Shell shell = getActiveWorkbenchShell();
      if (shell != null)
      {
         title = (title != null) ? title : "Error";
         ErrorDialog.openError(shell, title, message, status);
      }
   }

   public static void errorDialog(String title, String message, Throwable t)
   {
      errorDialog(title, message, createErrorStatus(t));
   }

   public void start(BundleContext context) throws Exception
   {
      super.start(context);
      DebugPlugin.getDefault().addDebugEventListener(this);
   }

   public void stop(BundleContext context) throws Exception
   {
      DebugPlugin.getDefault().removeDebugEventListener(this);
      super.stop(context);
   }

   public void handleDebugEvents(DebugEvent[] events)
   {
      for (int i = 0; i < events.length; i++)
      {
         if (events[i].getKind() == DebugEvent.TERMINATE)
         {
            Object source = events[i].getSource();
            if ((source instanceof IDebugTarget) ||
                (source instanceof ProgramProcess))
            {
               ILaunch launch = null;
               ILaunchConfiguration config = null;

               if (source instanceof IDebugTarget)
               {
                  launch = ((IDebugTarget) source).getLaunch();
               }
               else if (source instanceof ProgramProcess)
               {
                  launch = ((ProgramProcess) source).getLaunch();
               }

               if (launch != null)
               {
                  config = launch.getLaunchConfiguration();
               }

               if (config != null)
               {
                  try
                  {
                     ILaunchConfigurationType type = config.getType();
                     if (type.getIdentifier().equals(
                         IOSELaunchConfigurationConstants.ID_LAUNCH_LOAD_MODULE))
                     {
                        ILaunchConfigurationDelegate delegate =
                           type.getDelegate(ILaunchManager.DEBUG_MODE);
                        if (delegate instanceof LoadModuleLaunchConfigurationDelegate)
                        {
                           LoadModuleLaunchConfigurationDelegate lmDelegate;
                           TerminateProgramRunner programTerminator;

                           lmDelegate =
                              (LoadModuleLaunchConfigurationDelegate) delegate;
                           programTerminator =
                              new TerminateProgramRunner(lmDelegate, config);
                           getShell().getDisplay().asyncExec(programTerminator);
                        }
                     }
                     else if (type.getIdentifier().equals(
                              IOSELaunchConfigurationConstants.ID_LAUNCH_DUMP))
                     {
                        ILaunchConfigurationDelegate delegate =
                           type.getDelegate(ILaunchManager.DEBUG_MODE);
                        if (delegate instanceof DumpLaunchConfigurationDelegate)
                        {
                           DumpLaunchConfigurationDelegate dumpDelegate =
                              (DumpLaunchConfigurationDelegate) delegate;
                           dumpDelegate.terminate(launch);
                        }
                     }
                  }
                  catch (CoreException e)
                  {
                     log(e);
                  }
               }
            }
         }
      }
   }

   static class TerminateProgramRunner implements Runnable
   {
      private LoadModuleLaunchConfigurationDelegate delegate;
      private ILaunchConfiguration config;
      private boolean uninstall;

      public TerminateProgramRunner(LoadModuleLaunchConfigurationDelegate delegate,
                                    ILaunchConfiguration config)
      {
         this.delegate = delegate;
         this.config = config;
         try
         {
            uninstall = config.getAttribute(
               IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD, false);
         } catch (CoreException e) {}
      }

      public void run()
      {
         TerminateProgramDialog dialog;
         int result;

         dialog = new TerminateProgramDialog(getShell());
         result = dialog.open();

         if (result == 0)
         {
            Job job = new TerminateProgramJob();
            job.schedule();
         }
      }

      class TerminateProgramDialog extends MessageDialog
      {
         public TerminateProgramDialog(Shell parent)
         {
            super(parent,
                  "Terminate Program",
                  null,
                  "Do you want to kill this program?\n" +
                  "If yes, should its load module be uninstalled?",
                  MessageDialog.QUESTION,
                  new String[]
                     {IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL},
                  0);
         }

         protected Control createCustomArea(Composite parent)
         {
            Composite comp = new Composite(parent, SWT.NONE);
            comp.setLayout(new GridLayout());

            Button checkbox = new Button(comp, SWT.CHECK);
            checkbox.setFont(parent.getFont());
            checkbox.setText("Uninstall load module");
            checkbox.setSelection(uninstall);
            checkbox.addSelectionListener(new SelectionAdapter()
            {
               public void widgetSelected(SelectionEvent e)
               {
                  Button checkbox = (Button) e.widget;
                  uninstall = checkbox.getSelection();
               }
            });

            return comp;
         }
      }

      class TerminateProgramJob extends Job
      {
         public TerminateProgramJob()
         {
            super("Terminating Load Module");
            setPriority(SHORT);
         }

         protected IStatus run(IProgressMonitor monitor)
         {
            try
            {
               delegate.terminate(config, monitor, uninstall);
               return Status.OK_STATUS;
            }
            catch (CoreException e)
            {
               return e.getStatus();
            }
         }
      }
   }
}
