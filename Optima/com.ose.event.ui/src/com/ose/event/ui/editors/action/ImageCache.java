/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.event.ui.editors.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.ose.event.ui.EventPlugin;

public class ImageCache
{
   private static final URL IMAGE_BASE_URL = Platform.getBundle(
         EventPlugin.getUniqueIdentifier()).getEntry("/icons/");

   private final Map imageMap = new HashMap();

   public static ImageDescriptor createImageDescriptor(String prefix,
         String name)
   {
      URL imageFileURL = null;

      try
      {
         imageFileURL = new URL(IMAGE_BASE_URL, prefix + name);
      }
      catch (MalformedURLException e)
      {
         EventPlugin.log(e);
      }
      return ImageDescriptor.createFromURL(imageFileURL);
   }

   public Image getImage(ImageDescriptor descriptor)
   {
      Image image;

      if (descriptor == null)
      {
         return null;
      }
      image = (Image) imageMap.get(descriptor);
      if (image == null)
      {
         image = descriptor.createImage();
         imageMap.put(descriptor, image);
      }
      return image;
   }

   public void dispose()
   {
      for (Iterator i = imageMap.values().iterator(); i.hasNext();)
      {
         ((Image) i.next()).dispose();
      }
      imageMap.clear();
   }
}
