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

package com.ose.system.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import com.ose.system.SystemModel;

public class SystemBrowserPlugin extends AbstractUIPlugin
{
   public static final String PLUGIN_ID = "com.ose.system.ui";

   public static final String PLUGIN_NAME = "System Browser";

   public static final String SYSTEM_VIEW_ID =
      "com.ose.system.ui.views.system.SystemView";

   public static final String SYSTEM_VIEW_NAME = "System Browser";

   public static final String LOAD_MODULE_VIEW_ID =
      "com.ose.system.ui.views.loadmodule.LoadModuleView";

   public static final String LOAD_MODULE_VIEW_NAME = "Load Modules";

   public static final String POOL_VIEW_ID =
      "com.ose.system.ui.views.pool.PoolView";

   public static final String POOL_VIEW_NAME = "Pool Browser";

   public static final String POOL_OPTIMIZER_VIEW_ID =
      "com.ose.system.ui.views.pooloptimizer.PoolOptimizerView";

   public static final String POOL_OPTIMIZER_VIEW_NAME = "Pool Optimizer";

   public static final String HEAP_VIEW_ID =
      "com.ose.system.ui.views.heap.HeapView";

   public static final String HEAP_VIEW_NAME = "Heap Browser";

   public static final String BLOCK_VIEW_ID =
      "com.ose.system.ui.views.block.BlockView";

   public static final String BLOCK_VIEW_NAME = "Block List";

   public static final String PROCESS_VIEW_ID =
      "com.ose.system.ui.views.process.ProcessView";

   public static final String PROCESS_VIEW_NAME = "Process List";

   public static final String SYSTEM_MODEL_NODE_EDITOR_ID =
      "com.ose.system.ui.editors.SystemModelNodeFormEditor";

   public static final String CHART_MODEL_EDITOR_ID =
      "com.ose.system.ui.editors.ChartModelFormEditor";

   private static SystemBrowserPlugin plugin;

   private ResourceBundle resourceBundle;

   private SystemModelPreferenceHandler systemModelPreferenceHandler;

   public SystemBrowserPlugin()
   {
      super();
      plugin = this;
      try
      {
         resourceBundle = ResourceBundle.getBundle(
               "com.ose.system.ui.SystemBrowserPluginResources");
      }
      catch (MissingResourceException e)
      {
         resourceBundle = null;
      }
      systemModelPreferenceHandler = new SystemModelPreferenceHandler();
   }

   public static SystemBrowserPlugin getDefault()
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
      systemModelPreferenceHandler.init();
      getDefault().getPreferenceStore().addPropertyChangeListener(
            systemModelPreferenceHandler);
   }

   public void stop(BundleContext context) throws Exception
   {
      getDefault().getPreferenceStore().removePropertyChangeListener(
            systemModelPreferenceHandler);
      super.stop(context);
   }

   static class SystemModelPreferenceHandler implements IPropertyChangeListener
   {
      public void init()
      {
         IPreferenceStore prefs;
         SystemModel sm;

         prefs = getDefault().getPreferenceStore();
         sm = SystemModel.getInstance();
         sm.setBroadcastPort(
            prefs.getInt(SystemModelPreferencePage.PREF_GATE_BROADCAST_PORT));
         sm.setBroadcastTimeout(
            prefs.getInt(SystemModelPreferencePage.PREF_GATE_BROADCAST_TIMEOUT));
         sm.setPingTimeout(
            prefs.getInt(SystemModelPreferencePage.PREF_GATE_PING_TIMEOUT));
      }

      public void propertyChange(PropertyChangeEvent event)
      {
         String property;
         IPreferenceStore prefs;
         SystemModel sm;

         property = event.getProperty();
         prefs = getDefault().getPreferenceStore();
         sm = SystemModel.getInstance();
         if (property.equals(SystemModelPreferencePage.PREF_GATE_BROADCAST_PORT))
         {
            sm.setBroadcastPort(
               prefs.getInt(SystemModelPreferencePage.PREF_GATE_BROADCAST_PORT));
         }
         else if (property.equals(SystemModelPreferencePage.PREF_GATE_BROADCAST_TIMEOUT))
         {
            sm.setBroadcastTimeout(
               prefs.getInt(SystemModelPreferencePage.PREF_GATE_BROADCAST_TIMEOUT));
         }
         else if (property.equals(SystemModelPreferencePage.PREF_GATE_PING_TIMEOUT))
         {
            sm.setPingTimeout(
               prefs.getInt(SystemModelPreferencePage.PREF_GATE_PING_TIMEOUT));
         }
      }
   }
}
