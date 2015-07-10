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

package com.ose.system.ui.util;

import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

public abstract class BackgroundImageHandler
{
   private final Composite comp;

   private final ResizeHandler resizeHandler;

   private final boolean linesVisible;

   public BackgroundImageHandler(Composite comp)
   {
      this.comp = comp;
      resizeHandler = new ResizeHandler();
      if (comp instanceof Table)
      {
         Table table = (Table) comp;
         linesVisible = table.getLinesVisible();
         table.setLinesVisible(false);
      }
      else if (comp instanceof Tree)
      {
         Tree tree = (Tree) comp;
         linesVisible = tree.getLinesVisible();
         tree.setLinesVisible(false);
      }
      else
      {
         linesVisible = false;
      }
      comp.addControlListener(resizeHandler);
   }

   public void dispose()
   {
      comp.removeControlListener(resizeHandler);
      Image image = comp.getBackgroundImage();
      if (image != null)
      {
         image.dispose();
         comp.setBackgroundImage(null);
      }
      if (comp instanceof Table)
      {
         Table table = (Table) comp;
         table.setLinesVisible(linesVisible);
      }
      else if (comp instanceof Tree)
      {
         Tree tree = (Tree) comp;
         tree.setLinesVisible(linesVisible);
      }
   }

   private Image createBackgroundImage(Display display, Rectangle rect)
   {
      if (rect.isEmpty())
      {
         return null;
      }
      Image image = new Image(display, rect.width, rect.height);
      GC gc = new GC(image);
      gc.setBackground(comp.getBackground());
      gc.setForeground(comp.getForeground());
      gc.setFont(comp.getFont());
      drawBackgroundImage(image, gc, rect.x, rect.y);
      gc.dispose();
      return image;
   }

   protected abstract void drawBackgroundImage(Image image, GC gc, int xOffset,
         int yOffset);

   private class ResizeHandler extends ControlAdapter
   {
      public void controlResized(ControlEvent event)
      {
         Image image = comp.getBackgroundImage();
         if (image != null)
         {
            image.dispose();
         }
         Rectangle rect = comp.getClientArea();
         if (comp instanceof Table)
         {
            Table table = (Table) comp;
            rect.y += table.getHeaderHeight();
         }
         else if (comp instanceof Tree)
         {
            Tree tree = (Tree) comp;
            rect.y += tree.getHeaderHeight();
         }
         image = createBackgroundImage(comp.getDisplay(), rect);
         comp.setBackgroundImage(image);
      }
   }
}
