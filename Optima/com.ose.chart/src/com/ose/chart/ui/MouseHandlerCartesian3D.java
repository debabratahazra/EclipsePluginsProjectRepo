/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.chart.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import com.ose.chart.math.Vector3;

class MouseHandlerCartesian3D extends MouseHandler
{
   private static final float rotationRate = 0.5f;
   private static float translationRate = 1.0f;
   private static float zoomRate = 0.8f;

   private static final int NO_BUTTON = 0;
   private static final int ORBIT_BUTTON = 1;
   private static final int PAN_BUTTON = 3;

   private OrbitCamera camera;
   
   private int buttonPressed;
   private Point startPoint;

   public Vector3 rotation = new Vector3();
   public Vector3 translation = new Vector3();

   private Vector3 startRotation;
   private Vector3 startTranslation;

   public MouseHandlerCartesian3D(ChartCanvas chartCanvas,
                                  CartesianChart3D chart)
   {
      super(chartCanvas, chart);
      
      buttonPressed = NO_BUTTON;
      startPoint = new Point(0, 0);

      camera = chart.getMovableCamera();
      camera.getRotation(rotation);
      camera.getTranslation(translation);
      
      startRotation = new Vector3(0, 0, 0);
      startTranslation = new Vector3(0, 0, 0);
   }

   @Override
   public void handleEvent(Event event)
   {
      camera.zoom(event.count * zoomRate);
      getChartCanvas().refresh();
   }

   @Override
   public void mouseDown(MouseEvent e)
   {
      if (buttonPressed == NO_BUTTON)
      {
         buttonPressed = e.button;
         startPoint.x = e.x;
         startPoint.y = e.y;

         switch (buttonPressed)
         {
         case ORBIT_BUTTON:
            camera.getRotation(rotation);
            startRotation.set(rotation);
            setCursor((Control)e.widget, SWT.CURSOR_HAND);
            getChartCanvas().startRefreshing();
            break;
         case PAN_BUTTON:
            camera.getTranslation(translation);
            startTranslation.set(translation);
            setCursor((Control)e.widget, SWT.CURSOR_SIZEALL);
            getChartCanvas().startRefreshing();
            break;
         default:
            // Ignore other buttons
            buttonPressed = NO_BUTTON;
            break;
         }
      }
   }

   @Override
   public void mouseUp(MouseEvent e)
   {
      if (e.button == buttonPressed)
      {
         buttonPressed = NO_BUTTON;
         setCursor((Control)e.widget, SWT.CURSOR_ARROW);
         getChartCanvas().stopRefreshing();
      }
   }

   @Override
   public void mouseMove(MouseEvent e)
   {
      if (buttonPressed != NO_BUTTON)
      {
         Point p = ((Control) e.widget).getSize();
         int dx = e.x - startPoint.x;
         int dy = e.y - startPoint.y;

         switch (buttonPressed)
         {
         case ORBIT_BUTTON:
            rotation.x = startRotation.x + dy * rotationRate;
            rotation.y = startRotation.y + dx * rotationRate;
            rotation.z = startRotation.z;
            camera.setRotation(rotation.x, rotation.y);
            break;
         case PAN_BUTTON:
            translation.x = startTranslation.x - (translation.z + 1.0f) * dx * translationRate / p.x;
            translation.y = startTranslation.y + (translation.z + 1.0f) * dy * translationRate / p.y;
            translation.z = translation.z;
            camera.setTranslation(translation);
            break;
         default:
               // Ignore other buttons
               break;
         }
      }
   }
   
   private static final void setCursor(Control control, int cursor)
   {
      control.setCursor(control.getDisplay().getSystemCursor(cursor));
   }
}
