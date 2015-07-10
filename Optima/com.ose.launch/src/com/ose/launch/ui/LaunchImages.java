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

import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class LaunchImages
{
   private static ImageRegistry imageRegistry = new ImageRegistry();

   private static URL iconBaseURL =
      Platform.getBundle(LaunchUIPlugin.getUniqueIdentifier()).getEntry("/icons/");

   private static final String NAME_PREFIX = LaunchUIPlugin.PLUGIN_ID + '.';

   private static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();

   public static final String IMG_VIEW_CONNECTION_TAB =
      NAME_PREFIX + "connection_tab.gif";

   public static final ImageDescriptor DESC_TAB_CONNECTION =
      createManaged("view16/", IMG_VIEW_CONNECTION_TAB);

   public static final String IMG_VIEW_DOWNLOAD_TAB =
      NAME_PREFIX + "download_tab.gif";

   public static final ImageDescriptor DESC_TAB_DOWNLOAD =
      createManaged("view16/", IMG_VIEW_DOWNLOAD_TAB);

   public static final String IMG_VIEW_DUMP_TAB =
      NAME_PREFIX + "dump_tab.gif";

   public static final ImageDescriptor DESC_TAB_DUMP =
      createManaged("view16/", IMG_VIEW_DUMP_TAB);

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
         LaunchUIPlugin.log(e);
      }
      descriptor = ImageDescriptor.createFromURL(iconFileURL);
      imageRegistry.put(name, descriptor);
      return descriptor;
   }
}
