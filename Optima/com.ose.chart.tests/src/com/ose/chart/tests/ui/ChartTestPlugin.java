/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.chart.tests.ui;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * the plugin class for our package
 *
 */
public class ChartTestPlugin extends AbstractUIPlugin
{
   public static final String PLUGIN_ID = "com.ose.chart.tests";
   public static final String PLUGIN_NAME = "Chart Tests";
   
   public static final String CHART_TEST_EDITOR_ID = "com.ose.chart.tests.ui.editors.ChartTestEditor";
   
   private static ChartTestPlugin plugin;
   
   private ResourceBundle resourceBundle;
   
   public ChartTestPlugin()
   {
      super();
      plugin = this;
      try
      {
         resourceBundle = ResourceBundle.getBundle(
               "com.ose.system.ui.ChartTestPluginResources");
      }
      catch (MissingResourceException e)
      {
         resourceBundle = null;
      }
   }
   
   public static ChartTestPlugin getDefault()
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
   
   public void start(BundleContext context) throws Exception
   {
      super.start(context);
   }

   public void stop(BundleContext context) throws Exception
   {
      super.stop(context);
   }
}
