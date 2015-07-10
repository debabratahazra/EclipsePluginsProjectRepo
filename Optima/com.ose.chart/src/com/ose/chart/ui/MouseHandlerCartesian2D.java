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

import org.eclipse.swt.events.MouseEvent;

class MouseHandlerCartesian2D extends MouseHandler
{
   public MouseHandlerCartesian2D(ChartCanvas canvas, CartesianChart2D chart)
   {
      super(canvas, chart);
   }
   
   @Override
   public void mouseHover(MouseEvent e)
   {
      // It would be nice if we could have tooltips when
      // the user points at a bar in a bar chart
   }
}
