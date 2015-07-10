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

import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * This class provides all the icon images used by the ChartViewer.
 *
 */
public class ChartTestImages
{
   private static ImageRegistry imageRegistry = new ImageRegistry();
   
   private static URL iconBaseURL =
      Platform.getBundle(ChartTestPlugin.getUniqueIdentifier()).getEntry("/icons/");

   private static final String NAME_PREFIX = ChartTestPlugin.PLUGIN_ID + '.';

   private static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();
   
   // Image keys
   public static final String IMG_OBJ_CHART_TYPE = 
      NAME_PREFIX + "chart_type.gif";

   public static final String IMG_OBJ_CHART_TYPE_FAILED = 
      NAME_PREFIX + "chart_type_failed.gif";
   
   public static final String IMG_OBJ_CHART_TYPE_VALIDATED =
      NAME_PREFIX + "chart_type_validated.gif";
   
   public static final String IMG_OBJ_CHART_TEST = 
      NAME_PREFIX + "chart_test.gif";
   
   public static final String IMG_OBJ_CHART_TEST_FAILED =
      NAME_PREFIX + "chart_test_failed.gif";
   
   public static final String IMG_OBJ_CHART_TEST_VALIDATED =
      NAME_PREFIX + "chart_test_validated.gif";
   
   public static final String IMG_OBJ_VALIDATE =
      NAME_PREFIX + "validate.gif";
   
   public static final String IMG_OBJ_FAIL =
      NAME_PREFIX + "fail.gif";
   
   public static final String IMG_OBJ_OPEN_TESTS_STATE =
      NAME_PREFIX + "open_tests_state.gif";
   
   public static final String IMG_OBJ_SAVE_TESTS_STATE =
      NAME_PREFIX + "save_tests_state.gif";

   public static final String IMG_OBJ_SAVE_TESTS_STATE_AS =
      NAME_PREFIX + "save_tests_state_as.gif";
   
   // ImageDescriptors
   public static final ImageDescriptor DESC_OBJ_CHART_TYPE =
      createManaged("", IMG_OBJ_CHART_TYPE);
   
   public static final ImageDescriptor DESC_OBJ_CHART_TYPE_FAILED =
      createManaged("", IMG_OBJ_CHART_TYPE_FAILED);

   public static final ImageDescriptor DESC_OBJ_CHART_TYPE_VALIDATED =
      createManaged("", IMG_OBJ_CHART_TYPE_VALIDATED);
   
   public static final ImageDescriptor DESC_OBJ_CHART_TEST =
      createManaged("", IMG_OBJ_CHART_TEST);
   
   public static final ImageDescriptor DESC_OBJ_CHART_TEST_FAILED =
      createManaged("", IMG_OBJ_CHART_TEST_FAILED);
   
   public static final ImageDescriptor DESC_OBJ_CHART_TEST_VALIDATED =
      createManaged("", IMG_OBJ_CHART_TEST_VALIDATED);

   public static final ImageDescriptor DESC_OBJ_VALIDATE =
      createManaged("", IMG_OBJ_VALIDATE);
   
   public static final ImageDescriptor DESC_OBJ_FAIL =
      createManaged("", IMG_OBJ_FAIL);

   public static final ImageDescriptor DESC_OBJ_OPEN_TESTS_STATE =
      createManaged("", IMG_OBJ_OPEN_TESTS_STATE);
   
   public static final ImageDescriptor DESC_OBJ_SAVE_TESTS_STATE =
      createManaged("", IMG_OBJ_SAVE_TESTS_STATE);
   
   public static final ImageDescriptor DESC_OBJ_SAVE_TESTS_STATE_AS =
      createManaged("", IMG_OBJ_SAVE_TESTS_STATE_AS);
   
   // method definitions
   
   public static Image get(String key)
   {
      return imageRegistry.get(key);
   }

   private static ImageDescriptor createManaged(String prefix, String name)
   {
      URL iconFileURL = null;
      ImageDescriptor descriptor;

      try
      {
         iconFileURL =
            new URL(iconBaseURL, prefix + name.substring(NAME_PREFIX_LENGTH));
      }
      catch (MalformedURLException e)
      {
      }

      descriptor = ImageDescriptor.createFromURL(iconFileURL);
      imageRegistry.put(name, descriptor);
      return descriptor;
   }
}
