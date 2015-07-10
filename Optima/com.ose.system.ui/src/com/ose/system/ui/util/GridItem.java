/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;

public class GridItem extends Canvas
{
   private static final int TEXT_X = 5;
   private static final int TEXT_Y = 5;
   private static final int IMAGE_X = 5;
   private static final int IMAGE_Y = 25;
   private static final int FRAME_INSET = 2;
   private static final int FRAME_LINE_WIDTH = 2;

   private String text = "";
   private String toolTipText;
   private Image image;
   private Image grayImage;
   private Font font;
   private Cursor cursor;

   private boolean selected;

   public GridItem(Grid parent, int style)
   {
      super(parent, style);
      GridData gd;
      FontData fd;

      setLayout(new GridLayout());
      gd = new GridData();
      gd.horizontalAlignment = SWT.BEGINNING;
      gd.verticalAlignment = SWT.TOP;
      gd.grabExcessHorizontalSpace = true;
      gd.grabExcessVerticalSpace = true;
      setLayoutData(gd);

      fd = new FontData();
      fd.setStyle(SWT.BOLD);
      fd.setHeight(8);
      font = new Font(getDisplay(), fd);
      cursor = new Cursor(getDisplay(), SWT.CURSOR_HAND);

      setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
      addPaintListener(new PaintHandler());
      addMouseListener(new MouseHandler());
      addMouseTrackListener(new MouseTrackHandler());
   }

   public void dispose()
   {
      grayImage.dispose();
      font.dispose();
      cursor.dispose();
      super.dispose();
   }

   public String getText()
   {
      return text;
   }

   public void setText(String text)
   {
      GC gc;
      int width;
      GridData gd;

      if (text == null)
      {
         SWT.error(SWT.ERROR_NULL_ARGUMENT);
      }
      this.text = text;

      gc = new GC(getDisplay());
      gc.setFont(font);
      width = gc.stringExtent(text).x + 2 * TEXT_X + 1;
      gc.dispose();
      gd = (GridData) getLayoutData();
      if (width > gd.widthHint)
      {
         gd.widthHint = width;
      }
   }

   public String getDefaultToolTipText()
   {
      return toolTipText;
   }

   public void setDefaultToolTipText(String toolTipText)
   {
      this.toolTipText = toolTipText;
   }

   public Image getImage()
   {
      return image;
   }

   public void setImage(Image image)
   {
      int width;
      GridData gd;

      if (image == null)
      {
         SWT.error(SWT.ERROR_NULL_ARGUMENT);
      }

      this.image = image;
      grayImage = new Image(getDisplay(), image, SWT.IMAGE_GRAY);

      width = image.getBounds().width + 2 * IMAGE_X + 1;
      gd = (GridData) getLayoutData();
      if (width > gd.widthHint)
      {
         gd.widthHint = width;
      }
      gd.heightHint = image.getBounds().height + IMAGE_Y + FRAME_INSET + 1;
   }

   public boolean isSelected()
   {
      return selected;
   }

   public void setSelected(boolean selected)
   {
      this.selected = selected;
      redraw();
   }

   private boolean isInsideImage(int x, int y)
   {
      Rectangle imageBounds = image.getBounds();
      return (x >= IMAGE_X) &&
             (x < (IMAGE_X + imageBounds.width)) &&
             (y >= IMAGE_Y) &&
             (y < (IMAGE_Y + imageBounds.height));
   }

   private class PaintHandler implements PaintListener
   {
      public void paintControl(PaintEvent event)
      {
         if (getParent().isEnabled())
         {
            if (selected)
            {
               GC gc = event.gc;
               Rectangle imageBounds = image.getBounds();
               gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
               gc.setLineWidth(FRAME_LINE_WIDTH);
               gc.drawRectangle(IMAGE_X - FRAME_INSET,
                                IMAGE_Y - FRAME_INSET,
                                imageBounds.width + 2 * FRAME_INSET,
                                imageBounds.height + 2 * FRAME_INSET);
            }
            draw(event, image);
         }
         else
         {
            draw(event, grayImage);
         }
      }

      private void draw(PaintEvent event, Image img)
      {
         GC gc = event.gc;
         gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
         gc.setFont(font);
         gc.drawText(text, TEXT_X, TEXT_Y);
         gc.drawImage(img, IMAGE_X, IMAGE_Y);
      }
   }

   private class MouseHandler extends MouseAdapter
   {
      public void mouseUp(MouseEvent event)
      {
         if (isInsideImage(event.x, event.y) && (event.button == 1))
         {
            Grid grid = (Grid) getParent();
            grid.fireItemSelected(GridItem.this);
         }
      }
   }

   private class MouseTrackHandler extends MouseTrackAdapter
   {
      public void mouseHover(MouseEvent event)
      {
         if (isInsideImage(event.x, event.y) && getParent().isEnabled())
         {
            setToolTipText(toolTipText);
            setCursor(cursor);
         }
         else
         {
            setToolTipText(null);
            setCursor(null);
         }
      }
   }
}
