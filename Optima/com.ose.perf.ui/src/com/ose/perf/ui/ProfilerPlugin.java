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

package com.ose.perf.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;

public class ProfilerPlugin extends AbstractUIPlugin
{
   public static final String PLUGIN_ID = "com.ose.perf.ui";

   public static final String PLUGIN_NAME = "Source Profiler";

   public static final String PROFILER_VIEW_ID =
      "com.ose.perf.ui.view.ProfilerView";

   public static final String PROFILER_EDITOR_ID =
      "com.ose.perf.ui.editor.ProfilerEditor";

   private static ProfilerPlugin plugin;

   private ResourceBundle resourceBundle;

   public ProfilerPlugin()
   {
      super();
      plugin = this;
      try
      {
         resourceBundle = ResourceBundle.getBundle(
               "com.ose.perf.ui.ProfilerPluginResources");
      }
      catch (MissingResourceException e)
      {
         resourceBundle = null;
      }
   }

   public static ProfilerPlugin getDefault()
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
            (((t != null) && (t.getMessage() != null)) ? t.getMessage() : "Error"),
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

   public static IStatus createErrorStatus(int code, String message, Throwable t)
   {
      return new Status(IStatus.ERROR,
            getUniqueIdentifier(),
            code,
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

   public static String getResourceString(String key)
   {
      ResourceBundle bundle = getDefault().getResourceBundle();
      try
      {
         return ((bundle != null) ? bundle.getString(key) : key);
      }
      catch (MissingResourceException e)
      {
         return key;
      }
   }

   public ResourceBundle getResourceBundle()
   {
      return resourceBundle;
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
         IWorkbenchWindow[] windows =
            getDefault().getWorkbench().getWorkbenchWindows();
         return (windows.length > 0) ? windows[0].getShell() : null;
      }
   }

   public static Shell getActiveWorkbenchShell()
   {
      IWorkbenchWindow window =
         getDefault().getWorkbench().getActiveWorkbenchWindow();
      return (window != null) ? window.getShell() : null;
   }

   public static void errorDialog(String title, String message, IStatus status)
   {
      IWorkbenchWindow window;
      Shell shell = null;

      log(status);
      window = getDefault().getWorkbench().getActiveWorkbenchWindow();
      if (window != null)
      {
         shell = window.getShell();
      }
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

      try
      {
         LicenseManager lm = LicenseManager.getInstance();
         long cookie = (long) (Math.random() * Long.MAX_VALUE);
         long r = lm.registerPlugin(PLUGIN_ID, "1.000", cookie);
         long t = System.currentTimeMillis() / 10000;
         if ((r != (cookie ^ t)) && (r != (cookie ^ (t - 1))))
         {
            throw new LicenseException("Incorrect response value");
         }
      }
      catch (LicenseException e)
      {
         errorDialog("License Checkout Error",
                     "Could not check out a FLEXlm license for " +
                     PLUGIN_NAME + " plugin (" + PLUGIN_ID + ")", e);
         throw e;
      }
   }

   public void stop(BundleContext context) throws Exception
   {
      LicenseManager.getInstance().unregisterPlugin(PLUGIN_ID);
      super.stop(context);
   }
}
