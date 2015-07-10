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

import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class SharedImages
{
   private static ImageRegistry imageRegistry = new ImageRegistry();

   private static URL iconBaseURL =
      Platform.getBundle(ProfilerPlugin.getUniqueIdentifier()).getEntry("/icons/");

   private static final String NAME_PREFIX = ProfilerPlugin.PLUGIN_ID + '.';

   private static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();

   public static final String IMG_OBJ_BINARY_FILE =
      NAME_PREFIX + "bin_obj.gif";

   public static final String IMG_OBJ_FUNCTION =
      NAME_PREFIX + "function_obj.gif";

   public static final String IMG_OBJ_LINE =
      NAME_PREFIX + "line_obj.gif";

   public static final String IMG_OBJ_ADDRESS =
      NAME_PREFIX + "address_obj.gif";

   public static final String IMG_VIEW_MAIN_TAB =
      NAME_PREFIX + "main_tab.gif";

   public static final ImageDescriptor DESC_OBJ_BINARY_FILE =
      createManaged("obj16/", IMG_OBJ_BINARY_FILE);

   public static final ImageDescriptor DESC_OBJ_FUNCTION =
      createManaged("obj16/", IMG_OBJ_FUNCTION);

   public static final ImageDescriptor DESC_OBJ_LINE =
      createManaged("obj16/", IMG_OBJ_LINE);

   public static final ImageDescriptor DESC_OBJ_ADDRESS =
      createManaged("obj16/", IMG_OBJ_ADDRESS);

   public static final ImageDescriptor DESC_VIEW_MAIN_TAB =
      createManaged("view16/", IMG_VIEW_MAIN_TAB);

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
         ProfilerPlugin.log(e);
      }
      descriptor = ImageDescriptor.createFromURL(iconFileURL);
      imageRegistry.put(name, descriptor);
      return descriptor;
   }
}
