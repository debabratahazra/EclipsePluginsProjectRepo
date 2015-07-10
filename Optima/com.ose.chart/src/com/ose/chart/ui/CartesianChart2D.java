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

import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;

/**
 * Abstract base class for all charts with cartesian coordinates in 2D space.
 */
public abstract class CartesianChart2D extends CartesianChart
{
   protected static final int MAX_NUM_STEPS = 20;

   protected boolean horizontalGridLines = false;

   protected boolean verticalGridLines = false;
   
   // --- Start of configurable properties ------------------------------------
   
   private float itemWidth;
   
   private Margin margin = new Margin();
   
   // --- End of configurable properties --------------------------------------

   public CartesianChart2D(String title,
                           ChartContentProvider content,
                           ChartColorProvider colors)
   {
      super(title, content, colors, new OrthoCamera());
           
      disableNotifications();
      
      // Configurables
      setMargin(75, 50, 50, 60);
      
      setItemWidth(24.0f);
      
      enableNotifications();
   }
   
   public float getItemWidth()
   {
      return itemWidth;
   }
   
   public void setItemWidth(float width)
   {
      itemWidth = width;
      notifyChartUpdated();
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
   
   protected int getIntervalCount()
   {
      return getItemWindow().getCount();
   }
   
   protected float getValueScale()
   {
      return (float)(1.0 / (getMaxValue() - getMinValue()));
   }

   protected float getInnerScale(int height)
   {
      Margin margin = getMargin();
      float innerScale = height - margin.getTop() - margin.getBottom();
      return innerScale;
   }
   
   protected double getAdaptiveStepSize(double max, int height, double threshold)
   {
      double innerScale = (double)getInnerScale(height);
      double valueScale = (double)getValueScale();
            
      double step = 0.0;
      
      for (int maxSteps = MAX_NUM_STEPS; maxSteps >= 1; maxSteps /= 2)
      {
         step = getStepSize(max, maxSteps);
         if (step * innerScale * valueScale > threshold)
         {
            break;
         }
      }
      
      return step;
   }
   
   protected void drawValueTickLabels(GraphicsContext g,
                                      int width,
                                      int height,
                                      float innerScale)
   {
      final String boundsTestString = "0";
      Margin margin = getMargin();
      double maxValue = getMaxValue();
      double minValue = getMinValue();

      float base = (float)-minValue * getValueScale();
      
      float dy;
      float dv;
      double threshold = g.getTextBounds(boundsTestString).getHeight();
      
      if (maxValue >= Math.abs(minValue))
      {
         double stepSize = getAdaptiveStepSize(maxValue, height, threshold); 
         dy = (float)(stepSize * getValueScale());
         dv = (float)stepSize;
      }
      else
      {
         double stepSize = getAdaptiveStepSize(Math.abs(minValue), height, threshold); 
         dy = (float)(stepSize * getValueScale());
         dv = (float)stepSize;
      }

      ColorRGBA textColor = getTextColor();
      g.setTextColor(textColor.getRed(), 
                     textColor.getGreen(),
                     textColor.getBlue(),
                     1.0f);
      
      if (maxValue > 0)
      {
         for (float y = base, v = 0.0f; y <= 1.01f; y += dy, v += dv)
         {
            String str = Integer.toString((int)v);
            Rectangle2D r = g.getTextBounds(str);
            // FIXME: The 8 and the 0.2f should be variables/constants
            g.drawString2D(str,
                           margin.getLeft() - (int)r.getWidth() - 8,
                           (int)((float)margin.getBottom()
                                  + y * innerScale
                                  - 0.2f * (float)r.getHeight()));
         }
      }

      if (minValue < 0)
      {
         for (float y = base, v = 0.0f; y >= -0.01f; y -= dy, v -= dv)
         {
            String str = Integer.toString((int)v);
            Rectangle2D r = g.getTextBounds(str);
            // FIXME: The 8 and the 0.2f should be variables/constants
            g.drawString2D(str,
                           margin.getLeft() - (int)r.getWidth() - 8,
                           (int)((float)margin.getBottom()
                                  + y * innerScale
                                  - 0.2f * (float)r.getHeight()));
         }
      }
      
      g.setTextColor(textColor.getRed(),
                     textColor.getGreen(),
                     textColor.getBlue(),
                     1.0f);
   }

   
   protected void drawItemTickLabels(GraphicsContext g,
                                     int width,
                                     int height,
                                     float innerScale)
   {
      ChartContentProvider content = getContentProvider(); 
      float itemWidth = getItemWidth();
      int itemTickStep = getItemTickStep();
      int layerOffset = content.getLayerRange().getOffset();
      int seriesOffset = content.getSeriesRange().getOffset();
      int itemOffset = getItemWindow().getOffset();
      float y = (float)-getMinValue() * getValueScale() * innerScale;
      ColorRGBA textColor = getTextColor();
      ColorRGBA deselectedTextColor = getDeselectedTextColor();

      Range itemSelection = getItemSelection();
      
      for (int i = itemTickStep - 1 - itemOffset % itemTickStep;
           i < getItemWindow().getCount();
           i += itemTickStep)
      {
         String str = content.getItemLabel(layerOffset,
                                           seriesOffset,
                                           i + itemOffset);
         
         if (itemSelection.contains(itemOffset + i))
         {
            g.setTextColor(textColor);            
         }
         else
         {
            g.setTextColor(deselectedTextColor);            
         }
         
         // FIXME: The literal numbers should be variables/constants         
         g.pushTransform();
         g.translate(itemWidth * 0.5f + itemWidth * (float)i, y - 13.0f, 0);
         g.rotateZ(-30.0f);
         g.drawString3D(str, 0.0f, 0.0f, 0.0f, 0.54f);
         g.popTransform();
      }
   }

   protected void drawItemAxisLabel(GraphicsContext g,
                                    int width,
                                    int height,
                                    float innerScale)
   {
      Margin margin = getMargin();
      float itemWidth = getItemWidth();
      String str = getItemAxisLabel();
      Rectangle2D r = g.getTextBounds(str);
      float y = (float)-getMinValue() * getValueScale() * innerScale;

      // FIXME: The 4.0f and the 0.3f should be variables/constants
      g.drawString2D(str,
                     margin.getLeft()
                     + (int)(itemWidth * (float)getIntervalCount()
                     + 4.0f),
                     (int)(y + (float)margin.getBottom() - 0.3f * r.getHeight()));
   }

   protected void drawValueAxisLabel(GraphicsContext g,
                                     int width,
                                     int height,
                                     float innerScale)
   {
      Margin margin = getMargin();
      String str = getValueAxisLabel();
      Rectangle2D r = g.getTextBounds(str);
      g.drawString2D(str,
                     margin.getLeft() - (int)r.getWidth() / 2,
                     height - margin.getTop() + (int)r.getHeight());
   }

   protected void drawLabels(GraphicsContext g, int width, int height)
   {
      Margin margin = getMargin();
      float innerScale = height - margin.getTop() - margin.getBottom();

      g.beginText2D(GraphicsContext.FONT_LABEL, width, height);
      g.setForeground(ColorRGBA.BLACK);
      g.setTextColor(getTextColor());
      drawValueTickLabels(g, width, height, innerScale);
      drawItemAxisLabel(g, width, height, innerScale);
      drawValueAxisLabel(g, width, height, innerScale);
      g.endText2D();
      g.pushTransform();
      g.scale(1.0f, 1.0f / innerScale, 1.0f);
      g.beginText3D(GraphicsContext.FONT_SMALL_LABEL);
      drawItemTickLabels(g, width, height, innerScale);
      g.endText3D();
      g.popTransform();
   }
   
   protected void drawGrid(GraphicsContext g, int width, int height)
   {
      int itemOffset = getItemWindow().getOffset();
      int itemTickStep = getItemTickStep();
      float itemWidth = getItemWidth();
      float xOffset = 0.5f * itemWidth + (itemTickStep - 1 - itemOffset % itemTickStep) * itemWidth;
      float xStep = itemWidth * (float)itemTickStep;
      float xLimit = itemWidth * (float)getIntervalCount();
      
      g.setLineWidth(1.0f);
      g.setForeground(getGridColor());
      
      if (horizontalGridLines)
      {
         g.drawGridX(xOffset, xStep, xLimit, 1.0f);
      }
      
      if (verticalGridLines)
      {      
         double maxValue = getMaxValue();
         double minValue = getMinValue();
         float base = (float)-minValue * getValueScale();
         float yStep;
         
         final String boundsTestString = "0";
         double threshold = g.getTextBounds2D(GraphicsContext.FONT_LABEL, boundsTestString).getHeight();
         
         if (maxValue >= Math.abs(minValue))
         {
            yStep = (float)(getAdaptiveStepSize(maxValue, height, threshold) * getValueScale());
         }
         else
         {
            yStep = (float)(getAdaptiveStepSize(Math.abs(minValue), height, threshold) * getValueScale());         
         }
               
         if (getMaxValue() > 0)
         {
            // Draw positive Y grid lines
            g.drawGridY(base, yStep, 1.01f, xLimit);
         }
         
         if (getMinValue() < 0)
         {
            // Draw negative Y grid lines
            g.drawGridY(base, -yStep, -0.01f, xLimit);
         }
      }
   }
   
   protected void drawAxesTicks(GraphicsContext g, int width, int height)
   {
      int itemOffset = getItemWindow().getOffset();
      int valueTickCount = getValueTickCount();
      int itemTickStep = getItemTickStep();
      float itemWidth = getItemWidth();

      float xOffset = (itemTickStep - 1 - itemOffset % itemTickStep) * itemWidth;
      float xStep = itemWidth * (float)itemTickStep;
      float xLimit = itemWidth * getIntervalCount();
      
      g.drawTicksX(xOffset, xStep, xLimit, 6.0f / getInnerScale(height), 0.0f);
      g.drawTicksY(1.0f / (float)valueTickCount, valueTickCount);
   }

   protected void drawAxesLines(GraphicsContext g, int width, int height)
   {
      float itemWidth = getItemWidth();
      double minValue = getMinValue();
      float y;
      
      y = (float)-minValue * getValueScale();
      
      g.setLighting(false);
      g.setLineWidth(1.0f);
      g.setForeground(getAxisColor());
      
      g.drawLine(0, y, 0, itemWidth * (float)getIntervalCount(), y, 0);
      g.drawLine(0, 0, 0, 0, 1, 0);
      g.setLineWidth(1.0f);
   }

   protected void drawAxes(GraphicsContext g, int width, int height)
   {
      drawAxesLines(g, width, height);
      drawAxesTicks(g, width, height);
   }
   
   protected abstract void drawValues(GraphicsContext g, int width, int height);
   
   protected void drawChart(GraphicsContext g, int width, int height)
   {
      Margin margin = getMargin();
      int innerWidth = width - margin.getLeft() - margin.getRight();
      int innerHeight = height - margin.getTop() - margin.getBottom();
      float verticalScale = innerHeight;

      int itemCount = (int)Math.floor((float)(innerWidth - 30) / getItemWidth());

      setItemWindow(getItemWindow().getOffset(), itemCount);
      
      g.setTexturing(false);
      g.setLighting(false);
      g.setLineSmoothing(false);
      g.setBackfaceCulling(false);
      g.setDepthTesting(false);
      g.setDepthWriting(false);

      g.pushTransform();

      g.translate(margin.getLeft(), margin.getBottom(), 0.0f);
      g.scale(1.0f, verticalScale, 1.0f);

      g.setTexturing(false);

      drawGrid(g, width, height);
      drawValues(g, width, height);
      drawAxes(g, width, height);
      drawLabels(g, width, height);

      g.popTransform();
   }
}
