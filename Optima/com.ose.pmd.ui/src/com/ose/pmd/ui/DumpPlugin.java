/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class DumpPlugin extends AbstractUIPlugin
{
   public static final String PLUGIN_ID = "com.ose.pmd.ui";

   public static final String DUMP_VIEW_ID =
      "com.ose.pmd.ui.view.DumpView";

   public static final String DUMP_EDITOR_ID =
      "com.ose.pmd.ui.editor.DumpFormEditor";

   private static DumpPlugin plugin;

   private ResourceBundle resourceBundle;

   public DumpPlugin()
   {
      super();
      plugin = this;
      try
      {
         resourceBundle = ResourceBundle.getBundle(
               "com.ose.pmd.ui.DumpPluginResources");
      }
      catch (MissingResourceException e)
      {
         resourceBundle = null;
      }
   }

   public static DumpPlugin getDefault()
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
}
