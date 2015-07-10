/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007-2008 by Enea Software AB.
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
import org.eclipse.swt.events.KeyEvent;

class KeyHandlerCartesian2D extends KeyHandler
{   
   public KeyHandlerCartesian2D(ChartCanvas canvas, CartesianChart2D chart)
   {
      super(canvas, chart);
   }
   
   public void keyPressed(KeyEvent e)
   {
      CartesianChart2D chart = (CartesianChart2D)getChart();

      if (e.stateMask == 0)
      {
         if (e.keyCode == SWT.ARROW_LEFT)
         {
            chart.scrollItems(-1);
         }
         else if (e.keyCode == SWT.ARROW_RIGHT)
         {
            chart.scrollItems(1);
         }
      }
      else if (e.stateMask == SWT.CONTROL)
      {
         if (e.keyCode == SWT.ARROW_UP)
         {
            chart.growItemSelection();            
         }
         else if (e.keyCode == SWT.ARROW_DOWN)
         {
            chart.shrinkItemSelection();
         }
         else if (e.keyCode == SWT.ARROW_LEFT)
         {
            chart.stepItemSelection(CartesianChart.NEGATIVE_DIRECTION);
         }
         else if (e.keyCode == SWT.ARROW_RIGHT)
         {
            chart.stepItemSelection(CartesianChart.POSITIVE_DIRECTION);
         }
      }
   }
}
