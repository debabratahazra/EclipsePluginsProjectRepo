/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.ui.charts;

import java.util.TooManyListenersException;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.core.runtime.Platform;

public abstract class ChartModel
{
   public static final String MONOSPACE_FONT_NAME;
   public static final int MONOSPACE_FONT_SIZE;
   public static final int LABEL_FONT_SIZE;
   public static final int LEGEND_FONT_SIZE;

   private String title;
   private String description;
   private Chart chart;
   private ChartModelListener listener;
   private int window;

   static
   {
      String ws = Platform.getWS();
      if (ws.equals(Platform.WS_WIN32))
      {
         MONOSPACE_FONT_NAME = "Lucida Console";
         MONOSPACE_FONT_SIZE = 8;
         LABEL_FONT_SIZE = 7;
         LEGEND_FONT_SIZE = 7;
      }
      else if (ws.equals(Platform.WS_GTK))
      {
         MONOSPACE_FONT_NAME = "Lucida Console";
         MONOSPACE_FONT_SIZE = 8;
         LABEL_FONT_SIZE = 8;
         LEGEND_FONT_SIZE = 7;
      }
      else if (ws.equals(Platform.WS_MOTIF))
      {
         MONOSPACE_FONT_NAME = "Lucida Console";
         MONOSPACE_FONT_SIZE = 9;
         LABEL_FONT_SIZE = 10;
         LEGEND_FONT_SIZE = 10;
      }
      else
      {
         MONOSPACE_FONT_NAME = "";
         MONOSPACE_FONT_SIZE = 8;
         LABEL_FONT_SIZE = 7;
         LEGEND_FONT_SIZE = 7;
      }
   }

   public ChartModel(String title, String description)
   {
      this.title = title;
      this.description = description;
      chart = createChart();
      window = 4;
   }

   protected abstract Chart createChart();

   public void addChartModelListener(ChartModelListener listener)
      throws TooManyListenersException
   {
      if (this.listener != null)
      {
         throw new TooManyListenersException();
      }
      this.listener = listener;
   }

   public void removeChartModelListener(ChartModelListener listener)
   {
      this.listener = null;
   }

   protected void fireChartModelChanged()
   {
      if (listener != null)
      {
         listener.chartModelChanged(this);
      }
   }

   protected void fireChartModelScrolled()
   {
      if (listener != null)
      {
         listener.chartModelScrolled(this);
      }
   }

   public String getTitle()
   {
      return title;
   }

   public String getDescription()
   {
      return description;
   }

   protected Chart getChart()
   {
      return chart;
   }

   protected int getWindow()
   {
      return window;
   }

   protected void setWindow(int pixels)
   {
      // 'units' is number of bars and 'pixels' is window width in pixels
      // units = (window_width - margin_widths) / bar_plus_slack_width
      int units = (pixels - 2 * 50) / (2 * 60);
      window = ((units > 0) ? units : 1);
   }

   public abstract int getLength();

   public abstract void update(Object input);

   public abstract void sortXAxis(boolean descending);

   public abstract void sortYAxis(boolean descending);

   public abstract void scroll(int selection);
}
