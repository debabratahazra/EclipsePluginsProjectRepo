/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.prof.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import com.ose.plugin.control.LicenseException;
import com.ose.plugin.control.LicenseManager;
import com.ose.prof.ui.editors.process.ProcessFileChangeListener;

public class ProfilerPlugin extends AbstractUIPlugin
{
   public static final String PLUGIN_ID = "com.ose.prof.ui";

   public static final String PLUGIN_NAME = "System Profiler";

   public static final String PROFILER_VIEW_ID =
      "com.ose.prof.ui.view.ProfilerView";

   public static final String PROFILER_EDITOR_PROXY_ID =
      "com.ose.prof.ui.editors.profiler.ProfilerEditorProxy";

   public static final String CPU_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.CPUProfilerEditor";

   public static final String CPU_PRIORITY_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.CPUPriorityProfilerEditor";

   public static final String CPU_PROCESS_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.CPUProcessProfilerEditor";

   public static final String CPU_BLOCK_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.CPUBlockProfilerEditor";

   public static final String HEAP_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.HeapProfilerEditor";

   public static final String ACCUMULATED_HEAP_PROCESS_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.AccumulatedHeapProcessProfilerEditor";

   public static final String ACCUMULATED_HEAP_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.AccumulatedHeapProfilerEditor";

   public static final String USER_PROFILER_EDITOR_ID =
      "com.ose.prof.ui.editors.profiler.UserProfilerEditor";

   public static final String SYNCHRONIZED_EDITOR_DECORATOR_ID =
      "com.ose.prof.ui.editors.profiler.SynchronizedScrollPartDecorator";

   public static final String PROCESS_EDITOR_ID =
      "com.ose.prof.ui.editors.process.ProcessFormEditor";

   public static final String PROCESS_DECORATOR_ID =
      "com.ose.prof.ui.editors.process.decorator";

   private static ProfilerPlugin plugin;

   private ResourceBundle resourceBundle;

   private ProcessFileChangeListener processFileChangeHandler;

   public ProfilerPlugin()
   {
      super();
      plugin = this;
      try
      {
         resourceBundle = ResourceBundle.getBundle(
               "com.ose.prof.ui.ProfilerPluginResources");
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

      processFileChangeHandler = new ProcessFileChangeListener();
      ResourcesPlugin.getWorkspace().addResourceChangeListener(processFileChangeHandler);
   }

   public void stop(BundleContext context) throws Exception
   {
      ResourcesPlugin.getWorkspace().removeResourceChangeListener(processFileChangeHandler);
      LicenseManager.getInstance().unregisterPlugin(PLUGIN_ID);
      super.stop(context);
   }
}
