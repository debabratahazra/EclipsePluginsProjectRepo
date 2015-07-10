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

package com.ose.chart.ui;

import com.ose.chart.math.Vector2;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;

public abstract class PolarChart2D extends PolarChart
{
   private float scale;
   
   private final Vector2 center = new Vector2();
   
   // --- Start of configurable properties ------------------------------------

   private Margin margin = new Margin();

   // --- End of configurable properties --------------------------------------
   
   public PolarChart2D(String title,
                       ChartContentProvider content,
                       ChartColorProvider colors)
   {
      super(title, content, colors, new OrthoCamera());

      disableNotifications();
      
      // Configurables
      setMargin(75, 50, 50, 60);
      
      enableNotifications();
   }
   
   public Margin getMargin()
   {
      return margin;
   }

   public void setMargin(Margin margin)
   {
      this.margin.set(margin);
      notifyChartUpdated();
   }

   public void setMargin(int left, int top, int right, int bottom)
   {
      margin.set(left, top, right, bottom);
      notifyChartUpdated();
   }
   
   @Override
   public Range getSeriesWindow()
   {
      ChartContentProvider provider = getContentProvider();
      if (provider == null)
      {
         return null;
      }
      else
      {
         return getContentProvider().getSeriesRange();
      }
   }
   
   protected float getScale()
   {
      return scale;
   }
   
   protected Vector2 getCenter()
   {
      return center;
   }
   
   protected void drawLabels(GraphicsContext g, int width, int height)
   {
      g.beginText2D(GraphicsContext.FONT_LABEL, width, height);
      g.setForeground(ColorRGBA.BLACK);
      g.setTextColor(getTextColor());
      drawValueLabels(g, width, height);
      g.endText2D();
   }
      
   protected abstract void drawValueLabels(GraphicsContext g, int width, int height);
   
   protected abstract void drawValues(GraphicsContext g, int width, int height);
   
   @Override
   protected void drawChart(GraphicsContext g, int width, int height)
   {
      Margin margin = getMargin();
      int innerWidth = width - margin.getLeft() - margin.getRight();
      int innerHeight = height - margin.getTop() - margin.getBottom();
      scale = Math.min(innerWidth, innerHeight);
      
      center.set(0.5f * (float)width, 0.5f * (float)height);
      
      g.setTexturing(false);
      g.setLighting(false);
      g.setLineSmoothing(false);
      g.setBackfaceCulling(false);
      g.setDepthTesting(false);
      g.setDepthWriting(false);
      
      g.pushTransform();
      g.translate(center.x, center.y, 0.0f);
      g.scale(scale, scale, 1.0f);
      drawValues(g, innerWidth, innerHeight);      
      g.popTransform();
      
      g.pushTransform();
      drawLabels(g, width, height);
      g.popTransform();
   }
}
