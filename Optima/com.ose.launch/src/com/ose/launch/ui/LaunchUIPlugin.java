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

package com.ose.launch.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class LaunchUIPlugin extends AbstractUIPlugin
{
   public static final String PLUGIN_ID = "com.ose.launch";

   public static final String PLUGIN_NAME = "Launching Support";

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
   }

   public void stop(BundleContext context) throws Exception
   {
      super.stop(context);
   }
}
