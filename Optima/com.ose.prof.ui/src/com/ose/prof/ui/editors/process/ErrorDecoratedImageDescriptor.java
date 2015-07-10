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

package com.ose.prof.ui.editors.process;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class ErrorDecoratedImageDescriptor extends CompositeImageDescriptor
{
   private final ImageDescriptor baseImageDescriptor;

   public ErrorDecoratedImageDescriptor(Image baseImage)
   {
      baseImageDescriptor = ImageDescriptor.createFromImage(baseImage);
   }

   protected void drawCompositeImage(int width, int height)
   {
      ImageDescriptor decorationDescriptor = ImageCache.createImageDescriptor("obj16/",
      "error_co.gif");
      drawImage(baseImageDescriptor.getImageData(), 0, 0);
      drawImage(decorationDescriptor.getImageData(), 0, 8);
   }

   protected Point getSize()
   {
      return new Point(16, 16);
   }
}
