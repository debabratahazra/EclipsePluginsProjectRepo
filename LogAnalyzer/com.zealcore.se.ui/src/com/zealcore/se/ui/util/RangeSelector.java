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

package com.zealcore.se.ui.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class RangeSelector extends Canvas
{
   private static final int MARGIN = 20;
   private static final boolean LEFT = false;
   private static final boolean RIGHT = true;

   private final StepRangeButton stepButtonLeft;
   private final StepRangeButton stepButtonRight;
   private final Marker startMarker;
   private final Marker endMarker;
   private final SelectionLine selectionLine;
   private final List selectionListeners;

   private int pixelOffsetLeft;
   private int pixelOffsetRight;
   private int minimum;
   private int maximum;
   private int valueTotalRange;
   private int valueLength;
   private int valueOffsetLeft;
   private int valueOffsetRight;

   private final int position;
   private int modifierKey;

   public RangeSelector(Composite parent, int style)
   {
      super(parent, SWT.DOUBLE_BUFFERED);

      if ((style & SWT.HORIZONTAL) != 0)
      {
         position = SWT.HORIZONTAL;
      }
      else if ((style & SWT.VERTICAL) != 0)
      {
         // Not yet supported.
         position = SWT.VERTICAL;
      }
      else
      {
         position = SWT.HORIZONTAL;
      }

      stepButtonLeft = new StepRangeButton();
      stepButtonRight = new StepRangeButton();
      startMarker = new Marker();
      endMarker = new Marker();
      selectionLine = new SelectionLine();
      selectionListeners = new ArrayList();

      MouseHandler mouseHandler = new MouseHandler();
      addPaintListener(new PaintHandler());
      addControlListener(new ControlHandler());
      addMouseListener(mouseHandler);
      addMouseMoveListener(mouseHandler);
      addKeyListener(new KeyHandler());
   }

   public void addSelectionListener(SelectionListener listener)
   {
      if (listener == null)
      {
         SWT.error(SWT.ERROR_NULL_ARGUMENT);
      }
      selectionListeners.add(listener);
   }

   public void removeSelectionListener(SelectionListener listener)
   {
      if (listener == null)
      {
         SWT.error(SWT.ERROR_NULL_ARGUMENT);
      }
      selectionListeners.remove(listener);
   }

   public Point computeSize(int wHint, int hHint, boolean changed)
   {
      Point point = new Point(0, 0);

      if (wHint == SWT.DEFAULT)
      {
         if (position == SWT.HORIZONTAL)
         {
            point.x = 300;
            point.y = 17;
         }
         else if (position == SWT.VERTICAL)
         {
            //point.x = 17;
            //point.y = 300;
         }
      }
      else
      {
         if (position == SWT.HORIZONTAL)
         {
            point.x = wHint;
            point.y = 17;
         }
         else if (position == SWT.VERTICAL)
         {
            //point.x = 17;
            //point.y = hHint;
         }
      }

      return point;
   }

   public void setEnabled(boolean enabled)
   {
      super.setEnabled(enabled);
      redraw();
   }

   public int getMinimum()
   {
      return minimum;
   }

   public void setMinimum(int min)
   {
      if (min < 0)
      {
         min = 0;
      }
      else
      {
         minimum = min;
         valueTotalRange = maximum - minimum + 1;
      }
   }

   public int getMaximum()
   {
      return maximum;
   }

   public void setMaximum(int max)
   {
      if (max < 0)
      {
         max = 0;
      }
      else
      {
         maximum = max;
         valueTotalRange = maximum - minimum + 1;
      }
   }

   public int getSelectionOffset()
   {
      return valueOffsetLeft + minimum;
   }

   public int getSelectionLength()
   {
      return valueLength;
   }

   public void setSelection(int offset, int length)
   {
      if (offset <= 0)
      {
         valueOffsetLeft = 0;
         pixelOffsetLeft = MARGIN;
      }
      else if (offset >= valueTotalRange)
      {
         valueOffsetLeft = valueTotalRange - 1;
         pixelOffsetLeft = getSize().x - 1 - MARGIN;
      }
      else
      {
         valueOffsetLeft = offset;
         pixelOffsetLeft =
            MARGIN + Math.round((float) valueOffsetLeft * getScaleFactor());
      }

      if (length <= 0)
      {
         length = 1;
      }
      valueOffsetRight = valueOffsetLeft + (length - 1);
      if (valueOffsetRight == 0)
      {
         pixelOffsetRight = MARGIN;
      }
      else if (valueOffsetRight >= (valueTotalRange - 1))
      {
         valueOffsetRight = valueTotalRange - 1;
         pixelOffsetRight = getSize().x - 1 - MARGIN;
      }
      else
      {
         pixelOffsetRight =
            MARGIN + Math.round((float) valueOffsetRight * getScaleFactor());
      }

      setValueLength();
      updateMarkers();
      redraw();
   }

   public int getModifierKey()
   {
      return modifierKey;
   }

   public void setModifierKey(int modifier)
   {
      modifierKey = modifier;
   }

   // ***** DEBUGGING *****

   public int getPixelOffset()
   {
      return pixelOffsetLeft + minimum;
   }

   public int getPixelLength()
   {
      return pixelOffsetRight - pixelOffsetLeft;
   }

   // ***** END ***********

   private int getValueLength()
   {
      return valueLength;
   }

   private void setValueLength()
   {
      valueLength = valueOffsetRight - valueOffsetLeft + 1;
   }

   // Returns the factor that is used to scale to and from input values.
   private float getScaleFactor()
   {
      float pixelsPerValue;
      pixelsPerValue =
         ((float) ((getSize().x - (2 * MARGIN)) - 1) / (float) valueTotalRange);
      pixelsPerValue += (pixelsPerValue / (float) valueTotalRange);
      return pixelsPerValue;
   }

   private void paintControl(PaintEvent e)
   {
      GC gc = e.gc;
      boolean enabled = isEnabled();

      paintBackground(gc, enabled);
      stepButtonLeft.draw(gc, this, LEFT, enabled);
      stepButtonRight.draw(gc, this, RIGHT, enabled);
      selectionLine.draw(gc, startMarker, endMarker, this, enabled);
      if (startMarker.isActive())
      {
         endMarker.draw(gc, this, enabled);
         startMarker.draw(gc, this, enabled);
      }
      else
      {
         startMarker.draw(gc, this, enabled);
         endMarker.draw(gc, this, enabled);
      }
   }

   private void paintBackground(GC gc, boolean enabled)
   {
      Display disp = getDisplay();
      Color c1 = null;
      Color c2 = null;

      if (enabled)
      {
         c1 = disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
         c2 = disp.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
      }
      else
      {
         c1 = disp.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
         c2 = disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
      }
      gc.setLineWidth(1);
      gc.setForeground(c1);
      gc.drawLine(MARGIN, 6, getSize().x -1 - MARGIN, 6);
      gc.drawLine(MARGIN, 6, MARGIN, 11);
      gc.setForeground(c2);
      gc.drawLine(MARGIN, 11, getSize().x -1 - MARGIN, 11);
      gc.drawLine(getSize().x -1 - MARGIN, 6, getSize().x -1 - MARGIN, 11);
   }

   private void updateMarkers()
   {
      float startX;
      float endX;

      startX = (float) pixelOffsetLeft;
      startMarker.setX(Math.round(startX));
      startMarker.setY(9);
      endX = (float) pixelOffsetRight;
      endMarker.setX(Math.round(endX));
      endMarker.setY(9);
   }

   private void initialize()
   {
      if (maximum <= 0)
      {
         maximum = getSize().x;
      }
      if (minimum >= maximum)
      {
         minimum = 0;
      }
      valueTotalRange = maximum - minimum + 1;
      pixelOffsetRight = getSize().x - 1 - MARGIN;
      pixelOffsetLeft = MARGIN;
      updateMarkers();
      valueOffsetLeft = 0;
      valueOffsetRight = valueTotalRange - 1;
      setValueLength();
   }

   private void controlResized(ControlEvent e)
   {
      // Initialize default values.
      if ((pixelOffsetLeft == 0) && (pixelOffsetRight == 0))
      {
         initialize();
      }
      if ((valueOffsetLeft == 0) && (valueOffsetRight == (valueTotalRange - 1)))
      {
         pixelOffsetRight = getSize().x - 1 - MARGIN;
         pixelOffsetLeft = MARGIN;
      }
      else if (valueOffsetLeft == 0)
      {
         pixelOffsetLeft = MARGIN;
         pixelOffsetRight =
            MARGIN + Math.round((float) valueOffsetRight * getScaleFactor());
      }
      else if (valueOffsetRight == (valueTotalRange - 1))
      {
         pixelOffsetRight = getSize().x - 1 - MARGIN;
         pixelOffsetLeft =
            MARGIN + Math.round((float) valueOffsetLeft * getScaleFactor());
      }
      else
      {
         pixelOffsetLeft =
            MARGIN + Math.round((float) valueOffsetLeft * getScaleFactor());
         pixelOffsetRight =
            MARGIN + Math.round((float) valueOffsetRight * getScaleFactor());
      }
      updateMarkers();
      stepButtonLeft.setX(2);
      stepButtonRight.setX(getSize().x - 1 - 11);
   }

   // Call the selection listeners.
   private void notifyWidgetSelected()
   {
      Event e = new Event();
      e.widget = this;
      SelectionEvent se = new SelectionEvent(e);
      for (Iterator i = selectionListeners.iterator(); i.hasNext();)
      {
         ((SelectionListener) i.next()).widgetSelected(se);
      }
   }

   // Input an offset in pixels, scale and add to the valueOffset.
   private void scaleToValueOffset(int offset,
                                   boolean markerLeftOrRight,
                                   boolean direction)
   {
      int tmp = 0;
      if (direction) // Right
      {
         tmp = Math.round((float) offset / getScaleFactor());
         if (markerLeftOrRight) // Right value offset
         {
            valueOffsetRight = tmp;
            if (valueOffsetRight >= (valueTotalRange - 1))
            {
               valueOffsetRight = valueTotalRange - 1;
            }
         }
         else
         {
            valueOffsetLeft = tmp;
            if (valueOffsetLeft >= valueOffsetRight)
            {
               valueOffsetLeft = valueOffsetRight;
            }
            else if (valueOffsetLeft >= (valueTotalRange - 1))
            {
               valueOffsetLeft = valueTotalRange - 1;
            }
         }
      }
      else // Left
      {
         tmp = Math.round((float) offset / getScaleFactor());
         if (markerLeftOrRight) // Right value offset
         {
            valueOffsetRight = tmp;
            if (valueOffsetRight <= 0)
            {
               valueOffsetRight = 0;
            }
         }
         else
         {
            valueOffsetLeft = tmp;
            if (valueOffsetLeft <= 0)
            {
               valueOffsetLeft = 0;
            }
         }
      }
   }

   // Step the selected range.
   private void stepRange(boolean buttonLeftOrRight)
   {
      if (buttonLeftOrRight)
      {
         int rightMargin = getSize().x - 1 - MARGIN;
         if (pixelOffsetRight < rightMargin)
         {
            valueOffsetRight++;
            valueOffsetLeft = valueOffsetRight - (getValueLength() - 1);
            pixelOffsetRight =
               MARGIN + Math.round((float) valueOffsetRight * getScaleFactor());
            pixelOffsetLeft = pixelOffsetRight -
               Math.round((float) (getValueLength() - 1) * getScaleFactor());

            if (valueOffsetRight == (valueTotalRange - 1))
            {
               pixelOffsetRight = rightMargin;
               pixelOffsetLeft = pixelOffsetRight -
                  Math.round((float) (getValueLength() - 1) * getScaleFactor());
               valueOffsetLeft = valueOffsetRight - (getValueLength() - 1);
            }
            else if ((pixelOffsetRight >= rightMargin) &&
                     (valueOffsetRight < (valueTotalRange - 1)))
            {
               pixelOffsetRight = rightMargin;
               pixelOffsetLeft = pixelOffsetRight -
                  Math.round((float) (getValueLength() - 1) * getScaleFactor());
            }
            updateMarkers();
         }
         else if ((pixelOffsetRight == rightMargin) &&
                  (valueOffsetRight < (valueTotalRange - 1)))
         {
            valueOffsetRight++;
            valueOffsetLeft = valueOffsetRight - (getValueLength() - 1);
         }
      }
      else
      {
         if (pixelOffsetLeft > MARGIN)
         {
            valueOffsetLeft--;
            valueOffsetRight = valueOffsetLeft + (getValueLength() - 1);
            pixelOffsetLeft =
               MARGIN + Math.round((float) valueOffsetLeft * getScaleFactor());
            pixelOffsetRight = pixelOffsetLeft +
               Math.round((float) (getValueLength() - 1) * getScaleFactor());

            if (valueOffsetLeft == minimum)
            {
               pixelOffsetLeft = MARGIN;
               pixelOffsetRight = pixelOffsetLeft +
                  Math.round((float) (getValueLength() - 1) * getScaleFactor());
               valueOffsetLeft = valueOffsetRight - (getValueLength() - 1);
            }
            else if ((pixelOffsetLeft <= MARGIN) && (valueOffsetLeft > minimum))
            {
               pixelOffsetLeft = MARGIN;
               pixelOffsetRight = pixelOffsetLeft +
                  Math.round((float) (getValueLength() - 1) * getScaleFactor());
            }
            updateMarkers();
         }
         else if ((pixelOffsetLeft == MARGIN) && (valueOffsetLeft > minimum))
         {
            valueOffsetLeft--;
            valueOffsetRight = valueOffsetLeft + (getValueLength() - 1);
         }
      }
   }

   private void stepEndMarker(boolean moveRight)
   {
      if (moveRight)
      {
         int rightMargin = getSize().x - 1 - MARGIN;

         if (pixelOffsetRight < rightMargin)
         {
            valueOffsetRight++;
            pixelOffsetRight =
               MARGIN + Math.round((float) valueOffsetRight * getScaleFactor());

            if (valueOffsetRight >= (valueTotalRange - 1))
            {
               pixelOffsetRight = rightMargin;
               valueOffsetRight = (valueTotalRange - 1);
            }
            updateMarkers();
         }
         else if ((pixelOffsetRight == rightMargin) &&
                  (valueOffsetRight < (valueTotalRange - 1)))
         {
            valueOffsetRight++;
         }
      }
      else
      {
         if ((valueOffsetRight > valueOffsetLeft) &&
             (pixelOffsetRight == pixelOffsetLeft))
         {
            valueOffsetRight--;
         }
         else if ((pixelOffsetRight > pixelOffsetLeft) &&
                  (valueOffsetRight == valueOffsetLeft))
         {
            pixelOffsetRight = pixelOffsetLeft;
            updateMarkers();
         }
         else if ((valueOffsetRight > valueOffsetLeft) &&
                  (pixelOffsetRight > pixelOffsetLeft))
         {
            valueOffsetRight--;
            pixelOffsetRight =
               MARGIN + Math.round((float) valueOffsetRight * getScaleFactor());
            if (pixelOffsetRight <= pixelOffsetLeft)
            {
               pixelOffsetRight = pixelOffsetLeft;
            }
            updateMarkers();
         }
      }
   }

   private class PaintHandler implements PaintListener
   {
      public void paintControl(PaintEvent event)
      {
         RangeSelector.this.paintControl(event);
      }
   }

   private class ControlHandler extends ControlAdapter
   {
      public void controlResized(ControlEvent event)
      {
         RangeSelector.this.controlResized(event);
      }
   }

   private class MouseHandler implements MouseListener, MouseMoveListener
   {
      private boolean draggingStart;
      private boolean draggingEnd;
      private boolean draggingRange;
      private boolean steppingRangeLeft;
      private boolean steppingRangeRight;
      private int previousX;

      // Set the whole range.
      public void mouseDoubleClick(MouseEvent e)
      {
         Rectangle rectangle = startMarker.getBounds();

         if ((e.x > (startMarker.getX() + rectangle.width)) &&
             (e.x < (endMarker.getX() - rectangle.width)))
         {
            valueOffsetLeft = 0;
            valueOffsetRight = valueTotalRange - 1;
            setValueLength();
            pixelOffsetLeft = MARGIN;
            pixelOffsetRight = getSize().x - 1 - MARGIN;
            updateMarkers();
            redraw();
            notifyWidgetSelected();
         }
      }

      public void mouseDown(MouseEvent e)
      {
         Rectangle rectangle = startMarker.getBounds();

         if (startMarker.contains(e.x, e.y) && startMarker.isActive() &&
             (e.stateMask != SWT.SHIFT))
         {
            draggingStart = true;
         }
         else if (endMarker.contains(e.x, e.y) && endMarker.isActive() &&
                  (e.stateMask != SWT.SHIFT))
         {
            draggingEnd = true;
         }
         else if (startMarker.contains(e.x, e.y) && (e.stateMask != SWT.SHIFT))
         {
            draggingStart = true;
            startMarker.setActive(true);
            endMarker.setActive(false);
            redraw();
         }
         else if (endMarker.contains(e.x, e.y) && (e.stateMask != SWT.SHIFT))
         {
            draggingEnd = true;
            endMarker.setActive(true);
            startMarker.setActive(false);
            redraw();
         }
         else if ((e.x > (startMarker.getX() + rectangle.width)) &&
                  (e.x < (endMarker.getX() - rectangle.width)) ||
                  (e.stateMask == SWT.SHIFT))
         {
            draggingRange = true;
            previousX = e.x;
            startMarker.setActive(false);
            endMarker.setActive(false);
            redraw();
         }
         // Left step button
         else if ((e.x < MARGIN) && (e.stateMask != SWT.SHIFT))
         {
            steppingRangeLeft = true;
            previousX = e.x;
            stepButtonLeft.setActive(true);
            stepRange(LEFT);
            setValueLength();
            redraw();
            notifyWidgetSelected();
         }
         // Right step button
         else if ((e.x > (getSize().x - MARGIN)) && (e.stateMask != SWT.SHIFT))
         {
            steppingRangeRight = true;
            previousX = e.x;
            stepButtonRight.setActive(true);
            stepRange(RIGHT);
            setValueLength();
            redraw();
            notifyWidgetSelected();
         }
         else
         {
            startMarker.setActive(false);
            endMarker.setActive(false);
            redraw();
         }
      }

      public void mouseUp(MouseEvent e)
      {
         if (draggingStart)
         {
            draggingStart = false;
            redraw();
         }
         else if (draggingEnd)
         {
            draggingEnd = false;
            redraw();
         }
         else if (draggingRange)
         {
            draggingRange = false;
            selectionLine.setActive(false);
            redraw();
         }
         else if (steppingRangeRight)
         {
            steppingRangeRight = false;
            stepButtonRight.setActive(false);
            redraw();
         }
         else if (steppingRangeLeft)
         {
            steppingRangeLeft = false;
            stepButtonLeft.setActive(false);
            redraw();
         }
      }

      public void mouseMove(MouseEvent e)
      {
         int rightMargin = getSize().x - 1 - MARGIN;
         int x = e.x;

         if (draggingStart)
         {
            if ((x <= pixelOffsetRight) && (x >= MARGIN))
            {
               pixelOffsetLeft = x;

               valueOffsetLeft = valueOffsetRight -
                  Math.round((float) (pixelOffsetRight - pixelOffsetLeft) / getScaleFactor());
               if (valueOffsetLeft <= minimum)
               {
                  valueOffsetLeft = minimum;
                  pixelOffsetLeft = MARGIN;
               }
               else if ((pixelOffsetLeft == MARGIN) && (valueOffsetLeft > minimum))
               {
                  valueOffsetLeft--;
               }
               updateMarkers();
               setValueLength();
               redraw();
               notifyWidgetSelected();
            }
         }
         else if (draggingEnd)
         {
            if ((x >= pixelOffsetLeft) && (x <= rightMargin))
            {
               pixelOffsetRight = x;
               valueOffsetRight = valueOffsetLeft +
                  Math.round((float) (pixelOffsetRight - pixelOffsetLeft) / getScaleFactor());
               if (valueOffsetRight >= (valueTotalRange - 1))
               {
                  valueOffsetRight = (valueTotalRange - 1);
                  pixelOffsetRight = rightMargin;
               }
               else if ((pixelOffsetRight == rightMargin) &&
                        (valueOffsetRight < (valueTotalRange - 1)))
               {
                  valueOffsetRight++;
               }
               updateMarkers();
               setValueLength();
               redraw();
               notifyWidgetSelected();
            }
         }
         else if (draggingRange)
         {
            selectionLine.setActive(true);
            // Within boundaries.
            if ((pixelOffsetRight <= rightMargin) && (pixelOffsetLeft >= MARGIN))
            {
               if (previousX < x)
               {
                  int dx = x - previousX;
                  if ((pixelOffsetRight + dx) >= rightMargin)
                  {
                     pixelOffsetLeft =
                        pixelOffsetLeft + (rightMargin - pixelOffsetRight);
                     pixelOffsetRight = rightMargin;
                     updateMarkers();
                     valueOffsetRight = (valueTotalRange - 1);
                     valueOffsetLeft = valueOffsetRight - (getValueLength() - 1);
                     setValueLength();
                  }
                  else
                  {
                     pixelOffsetLeft = pixelOffsetLeft + dx;
                     pixelOffsetRight = pixelOffsetRight + dx;
                     updateMarkers();
                     scaleToValueOffset(pixelOffsetLeft - MARGIN, LEFT, RIGHT);
                     valueOffsetRight = valueOffsetLeft +
                        Math.round((float) (pixelOffsetRight - pixelOffsetLeft) / getScaleFactor());
                     setValueLength();
                  }
               }
               else if (previousX > x)
               {
                  int dx = previousX - x;
                  if ((pixelOffsetLeft - dx) <= MARGIN)
                  {
                     pixelOffsetRight =
                        pixelOffsetRight - (pixelOffsetLeft - MARGIN);
                     pixelOffsetLeft = MARGIN;
                     updateMarkers();
                     valueOffsetRight = (getValueLength() - 1);
                     valueOffsetLeft = 0;
                     setValueLength();
                  }
                  else
                  {
                     pixelOffsetLeft = pixelOffsetLeft - dx;
                     pixelOffsetRight = pixelOffsetRight - dx;
                     updateMarkers();
                     scaleToValueOffset(pixelOffsetLeft - MARGIN, LEFT, LEFT);
                     valueOffsetRight = valueOffsetLeft +
                        Math.round((float) (pixelOffsetRight - pixelOffsetLeft) / getScaleFactor());
                     setValueLength();
                  }
               }
               redraw();
               notifyWidgetSelected();
               previousX = x;
            }
            // Outside right boundary.
            else if ((pixelOffsetRight >= rightMargin) &&
                     (pixelOffsetLeft >= MARGIN))
            {
               if (x < previousX)
               {
                  int dx = previousX - x;
                  pixelOffsetLeft = pixelOffsetLeft - dx;
                  pixelOffsetRight = pixelOffsetRight - dx;
                  updateMarkers();
                  redraw();
                  notifyWidgetSelected();
                  previousX = x;
               }
            }
            // Outside left boundary.
            else if ((pixelOffsetLeft <= MARGIN) &&
                     (pixelOffsetRight <= rightMargin))
            {
               if (x > previousX)
               {
                  int dx = x - previousX;
                  pixelOffsetLeft = pixelOffsetLeft + dx;
                  pixelOffsetRight = pixelOffsetRight + dx;
                  updateMarkers();
                  redraw();
                  notifyWidgetSelected();
                  previousX = x;
               }
            }
         }
      }
   }

   private class KeyHandler extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         if (getModifierKey() == e.stateMask)
         {
            switch (e.keyCode)
            {
               case SWT.ARROW_UP:
               {
                  stepEndMarker(RIGHT);
                  setValueLength();
                  redraw();
                  notifyWidgetSelected();
                  break;
               }
               case SWT.ARROW_DOWN:
               {
                  stepEndMarker(LEFT);
                  setValueLength();
                  redraw();
                  notifyWidgetSelected();
                  break;
               }
               case SWT.ARROW_LEFT:
               {
                  stepRange(LEFT);
                  setValueLength();
                  redraw();
                  notifyWidgetSelected();
                  break;
               }
               case SWT.ARROW_RIGHT:
               {
                  stepRange(RIGHT);
                  setValueLength();
                  redraw();
                  notifyWidgetSelected();
                  break;
               }
            }
         }
      }
   }

   private static class StepRangeButton
   {
      private static final int[] ARROW_POINTS = {6, 5, 3, 8, 6, 11};

      private boolean active;

      private Point pos = new Point(0,0);

      public boolean isActive()
      {
         return active;
      }

      public void setActive(boolean active)
      {
         this.active = active;
      }

      public void setX(int x)
      {
         pos.x = x;
      }

      public void setY(int y)
      {
         pos.y = y;
      }

      public void draw(GC gc,
                       RangeSelector rangeSelector,
                       boolean arrowLeftOrRight,
                       boolean activated)
      {
         Display disp = rangeSelector.getDisplay();
         Color activeColor = null;
         Color inactiveColor = null;

         if (activated)
         {
            activeColor = disp.getSystemColor(SWT.COLOR_GRAY);
            inactiveColor = disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
         }
         else
         {
            activeColor = disp.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
            inactiveColor = disp.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
         }
         gc.setLineWidth(0);

         if (active)
         {
            drawArrow(gc, activeColor, ARROW_POINTS, arrowLeftOrRight);
         }
         else
         {
            drawArrow(gc, inactiveColor, ARROW_POINTS, arrowLeftOrRight);
         }
      }

      private void drawArrow(GC gc,
                             Color c1,
                             int[] arrowPoints,
                             boolean arrowLeftOrRight)
      {
         int[] movedPoints = {0,0, 0,0, 0,0};

         gc.setLineWidth(2);

         if (arrowLeftOrRight) // Right arrow
         {
            int[] tmp = (int[]) arrowPoints.clone();
            tmp[0] = tmp[2];
            tmp[2] = tmp[4];
            tmp[4] = tmp[0];
            for (int i = 0; i < tmp.length; i += 2)
            {
               movedPoints[i] = tmp[i] + pos.x;
               movedPoints[i + 1] = tmp[i + 1];
            }
         }
         else // Left arrow
         {
            for (int i = 0; i < arrowPoints.length; i += 2)
            {
               movedPoints[i] = arrowPoints[i] + pos.x;
               movedPoints[i + 1] = arrowPoints[i + 1];
            }
         }
         gc.setForeground(c1);
         gc.drawPolyline(movedPoints);
      }
   }

   private static class Marker
   {
      private static final int[] MARKER_POINTS_BORDER_LEFT_SIDE =
         {5,-7, -5,-7, -5,0};
      private static final int[] MARKER_POINTS_BORDER_RIGHT_SIDE =
         {5,-7, 5,1};
      private static final int[] MARKER_POINTS_BORDER =
         {-6,1, -6,-8, 6,-8, 6,1, 0,7};
      private static final int[] MARKER_POINTS_DECORATION =
         {-3,-5, 1,-5, -3,-2, 1,-2, -2,-4, 2,-4, -2,-1, 2,-1};
      private static final int[] MARKER_POINTS =
         {-5,1, -5,-6, 5,-6, 5,1, 0,7};

      private boolean active;
      private Point pos;
      private Rectangle bounds;

      public Marker()
      {
         pos = new Point(0, 0);
         bounds = new Rectangle(0, 0, 0, 0);
         updateBounds();
      }

      public boolean isActive()
      {
         return active;
      }

      public void setActive(boolean active)
      {
         this.active = active;
      }

      public int getX()
      {
         return pos.x;
      }

      public void setX(int x)
      {
         setPosition(x, pos.y);
      }

      public int getY()
      {
         return pos.y;
      }

      public void setY(int y)
      {
         setPosition(pos.x, y);
      }

      public Point getPosition()
      {
         return new Point(pos.x, pos.y);
      }

      public void setPosition(int x, int y)
      {
         pos.x = x;
         pos.y = y;
         updateBounds();
      }

      public Rectangle getBounds()
      {
         return new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
      }

      public boolean contains(int x, int y)
      {
         return bounds.contains(x, y);
      }

      private void updateBounds()
      {
         int x;
         int y;
         int minX = 999999;
         int minY = 999999;
         int maxX = 0;
         int maxY = 0;

         for (int i = 0; i < MARKER_POINTS.length; i += 2)
         {
            x = MARKER_POINTS[i] + pos.x;
            y = MARKER_POINTS[i + 1] + pos.y;
            if (x < minX)
            {
               minX = x;
            }
            if (x > maxX)
            {
               maxX = x;
            }
            if (y < minY)
            {
               minY = y;
            }
            if (y > maxY)
            {
               maxY = y;
            }
         }

         bounds.x = minX;
         bounds.y = minY;
         bounds.width = maxX - minX;
         bounds.height = maxY - minY;
      }

      private void draw(GC gc, RangeSelector rangeSelector, boolean enabled)
      {
         Display disp = rangeSelector.getDisplay();
         Color markerColorNormalBorder = null;
         Color markerColorHighlightBorder = null;
         Color markerColorFill = null;
         Color borderColor = null;

         gc.setLineWidth(0);

         if (enabled)
         {
            markerColorNormalBorder =
               disp.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
            markerColorHighlightBorder =
               disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
            markerColorFill = disp.getSystemColor(SWT.COLOR_GRAY);
            borderColor = disp.getSystemColor(SWT.COLOR_WIDGET_BORDER);
         }
         else
         {
            markerColorNormalBorder =
               disp.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
            markerColorHighlightBorder =
               disp.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);
            markerColorFill = disp.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
            borderColor = disp.getSystemColor(SWT.COLOR_GRAY);
         }
         drawFillArea(gc, markerColorFill);
         drawBorder(gc, borderColor);
         drawDecoration(gc, borderColor, markerColorHighlightBorder);
         drawLeftSideBorder(gc, markerColorNormalBorder, markerColorHighlightBorder);
         drawRightSideBorder(gc, markerColorHighlightBorder, markerColorNormalBorder);
      }

      private void drawFillArea(GC gc, Color c)
      {
         int[] movedPoints = {0,0, 0,0, 0,0, 0,0, 0,0};

         for (int i = 0; i < MARKER_POINTS.length; i += 2)
         {
            movedPoints[i] = MARKER_POINTS[i] + pos.x;
            movedPoints[i + 1] = MARKER_POINTS[i + 1] + pos.y;
         }

         gc.setBackground(c);
         gc.fillPolygon(movedPoints);
      }

      private void drawBorder(GC gc, Color c)
      {
         int[] movedPoints = {0,0, 0,0, 0,0, 0,0, 0,0};

         gc.setLineWidth(0);

         for (int i = 0; i < MARKER_POINTS_BORDER.length; i += 2)
         {
            movedPoints[i] = MARKER_POINTS_BORDER[i] + pos.x;
            movedPoints[i + 1] = MARKER_POINTS_BORDER[i + 1] + pos.y;
         }

         gc.setForeground(c);
         gc.drawPolygon(movedPoints);
      }

      private void drawDecoration(GC gc, Color c1, Color c2)
      {
         int[] movedPoints = {0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0, 0,0};

         gc.setForeground(c2);
         for (int i = 0; i < MARKER_POINTS_DECORATION.length/2; i += 2)
         {
            movedPoints[i] = MARKER_POINTS_DECORATION[i] + pos.x;
            movedPoints[i + 1] = MARKER_POINTS_DECORATION[i + 1] + pos.y;
            gc.drawPoint(movedPoints[i], movedPoints[i + 1]);
         }
         gc.setForeground(c1);
         for (int i = 8; i < MARKER_POINTS_DECORATION.length; i += 2)
         {
            movedPoints[i] = MARKER_POINTS_DECORATION[i] + pos.x;
            movedPoints[i + 1] = MARKER_POINTS_DECORATION[i + 1] + pos.y;
            gc.drawPoint(movedPoints[i], movedPoints[i + 1]);
         }
      }

      private void drawLeftSideBorder(GC gc, Color c1, Color c2)
      {
         int[] movedPoints = {0,0, 0,0, 0,0};

         gc.setLineWidth(0);

         for (int i = 0; i < MARKER_POINTS_BORDER_LEFT_SIDE.length; i += 2)
         {
            movedPoints[i] = MARKER_POINTS_BORDER_LEFT_SIDE[i] + pos.x;
            movedPoints[i + 1] = MARKER_POINTS_BORDER_LEFT_SIDE[i + 1] + pos.y;
         }

         gc.setForeground(c2);
         gc.drawPolyline(movedPoints);
      }

      private void drawRightSideBorder(GC gc, Color c1, Color c2)
      {
         int[] movedPoints = {0,0, 0,0};

         gc.setLineWidth(0);

         for (int i = 0; i < MARKER_POINTS_BORDER_RIGHT_SIDE.length; i += 2)
         {
            movedPoints[i] = MARKER_POINTS_BORDER_RIGHT_SIDE[i] + pos.x;
            movedPoints[i + 1] = MARKER_POINTS_BORDER_RIGHT_SIDE[i + 1] + pos.y;
         }

         gc.setForeground(c2);
         gc.drawPolyline(movedPoints);
      }
   }

   private static class SelectionLine
   {
      private boolean active;

      public boolean isActive()
      {
         return active;
      }

      public void setActive(boolean active)
      {
         this.active = active;
      }

      public void draw(GC gc,
                       Marker startMarker,
                       Marker endMarker,
                       RangeSelector rangeSelector,
                       boolean enabled)
      {
         Display disp = rangeSelector.getDisplay();
         Color activeColor = null;
         Color inactiveColor = null;
         Color gradientColor = null;

         if (enabled)
         {
            activeColor = disp.getSystemColor(SWT.COLOR_GRAY);
            inactiveColor = disp.getSystemColor(SWT.COLOR_DARK_GRAY);
            gradientColor = disp.getSystemColor(SWT.COLOR_WHITE);

            gc.setLineWidth(2);
            if (active)
            {
               int width = endMarker.getX() - startMarker.getX();
               gc.setForeground(activeColor);
               gc.setBackground(gradientColor);
               gc.fillGradientRectangle(startMarker.getX(), 7, width, 4, true);
            }
            else
            {
               int width = endMarker.getX() - startMarker.getX();
               gc.setForeground(gradientColor);
               gc.setBackground(inactiveColor);
               gc.fillGradientRectangle(startMarker.getX(), 7, width, 4, true);
            }
         }
      }
   }
}
