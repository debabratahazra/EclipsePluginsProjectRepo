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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import com.ose.chart.math.Vector3;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;

/**
 * Abstract base class for all charts with cartesian coordinates in 3D space.
 */
public abstract class CartesianChart3D extends CartesianChart
{

   // --- Start of configurable properties ------------------------------------

   private MutableRange seriesWindow = new MutableRange();

   private String seriesAxisLabel;

   private int backdropPosition = 0;
   
   private float itemWidth = 0.02f;

   private float itemWidthSpace = 0.02f;

   private float itemDepth = 0.02f;

   private float itemDepthSpace = 0.02f;

   private int maxSteps = 10;

   private Vector3 defaultTranslation;

   private Vector3 defaultRotation;
   
   private int supportLineStep = 5;

   private ColorRGBA supportLineColor = new ColorRGBA(0.8f, 0.8f, 0.8f);

   private ColorRGBA selectedSeriesColor = new ColorRGBA(0.7f, 0.7f, 0.7f);
   
   protected DecimalFormat fmt = new DecimalFormat("#####0.0",
         new DecimalFormatSymbols(Locale.US));

   // --- End of configurable properties --------------------------------------

   public CartesianChart3D(String title, ChartContentProvider content,
         ChartColorProvider colors)
   {
      super(title, content, colors, new OrbitCamera());

      disableNotifications();

      // Configurables
      setBackdropPosition(0);
      setItemWidth(0.02f);
      setItemWidthSpace(0.02f);
      setItemDepth(0.02f);
      setItemDepthSpace(0.02f);
      setSeriesAxisLabel("Series");

      setDefaultCameraTranslation(0.0f, 0.0f, -280.0f);
      setDefaultCameraRotation(25.0f, 30.0f);

      enableNotifications();
   }

   public OrbitCamera getMovableCamera()
   {
      return (OrbitCamera) getCamera();
   }

   public void resetCamera()
   {
      OrbitCamera camera = getMovableCamera();
      camera.setTranslation(getDefaultCameraTranslation());
      Vector3 r = getDefaultCameraRotation();
      camera.setRotation(r.x, r.y);
      camera.setPivotPoint(getExtents().multiply(0.5f));
      notifyChartUpdated();
   }

   public Vector3 getDefaultCameraTranslation()
   {
      return new Vector3(defaultTranslation);
   }

   public void setDefaultCameraTranslation(Vector3 translation)
   {
      setDefaultCameraTranslation(translation.x, translation.y, translation.z);
   }

   public void setDefaultCameraTranslation(float x, float y, float z)
   {
      defaultTranslation = new Vector3(x, y, z);
   }

   public Vector3 getDefaultCameraRotation()
   {
      return new Vector3(defaultRotation);
   }

   public void setDefaultCameraRotation(float x, float y)
   {
      defaultRotation = new Vector3(x, y, 0.0f);
   }

   public ColorRGBA getSupportLineColor()
   {
      return supportLineColor;
   }

   public void setSupportLineColor(ColorRGBA color)
   {
      supportLineColor = new ColorRGBA(color);
      notifyChartUpdated();
   }
   
   public ColorRGBA getSelectedSeriesColor()
   {
      return selectedSeriesColor;
   }

   public void setSelectedSeriesColor(ColorRGBA color)
   {
      selectedSeriesColor = new ColorRGBA(color);
      notifyChartUpdated();
   }
   
   public int getSupportLineStep()
   {
      return supportLineStep;
   }

   public void setSupportLineStep(int step)
   {
      supportLineStep = step;
      notifyChartUpdated();
   }
   
   public Range getSeriesWindow()
   {
      return seriesWindow;
   }

   public void setSeriesWindow(int offset, int count)
   {
      if (!(seriesWindow.getOffset() == offset
            && seriesWindow.getCount() == count))
      {
         seriesWindow.setOffset(offset);
         seriesWindow.setCount(count);
         constrainSeriesWindow();
         notifySeriesWindowChanged();
         notifyChartUpdated();
      }
   }

   public void setSeriesWindow(Range range)
   {
      setSeriesWindow(range.getOffset(), range.getCount());
   }

   public String getSeriesAxisLabel()
   {
      return seriesAxisLabel;
   }

   public void setSeriesAxisLabel(String label)
   {
      seriesAxisLabel = label;
      notifyChartUpdated();
   }

   public void scrollSeries(int steps)
   {
      seriesWindow.move(steps);
      constrainSeriesWindow();
      notifySeriesWindowChanged();
      notifyChartUpdated();
   }

   public void moveBackdrop(int steps)
   {
      setBackdropPosition(getBackdropPosition() + steps);
   }

   public int getBackdropPosition()
   {
      return backdropPosition;
   }

   public void setBackdropPosition(int position)
   {
      int seriesCount = getSeriesWindow().getCount();

      if (position < 0 || seriesCount == 0)
      {
         backdropPosition = 0;
      }
      else if (position >= seriesCount)
      {
         backdropPosition = seriesCount - 1;
      }
      else
      {
         backdropPosition = position;
      }
      notifyChartUpdated();
   }
   
   public Vector3 getExtents()
   {
      int itemCount = getItemWindow().getCount();
      int seriesCount = getSeriesWindow().getCount();
      Vector3 extents = new Vector3();

      // FIXME: The literal numbers should be variables/constants
      extents.x = 100.0f * (float) itemCount * (itemWidth + itemWidthSpace);
      extents.y = 0.5f;
      extents.z = -100.0f * (float) seriesCount * (itemDepth + itemDepthSpace);

      return extents;
   }

   public void contentChanged(ChartContentProvider provider)
   {
      constrainSeriesWindow();
      super.contentChanged(provider);
   }

   protected float getItemWidthSpace()
   {
      return itemWidthSpace;
   }

   protected void setItemWidthSpace(float space)
   {
      this.itemWidthSpace = space;
      notifyChartUpdated();
   }

   protected float getItemWidth()
   {
      return itemWidth;
   }

   protected void setItemWidth(float width)
   {
      this.itemWidth = width;
      notifyChartUpdated();
   }

   protected float getItemDepthSpace()
   {
      return itemDepthSpace;
   }

   protected void setItemDepthSpace(float space)
   {
      this.itemDepthSpace = space;
      notifyChartUpdated();
   }

   protected float getItemDepth()
   {
      return itemWidth;
   }

   protected void setItemDepth(float depth)
   {
      this.itemDepth = depth;
      notifyChartUpdated();
   }

   protected void constrainSeriesWindow()
   {
      seriesWindow.constrainPosition(getContentProvider().getSeriesRange());
   }

   protected double getAbsMaxValue()
   {
      double maxValue = Math.abs(getMaxValue());
      double minValue = Math.abs(getMinValue());

      return Math.max(maxValue, minValue);
   }

   protected void drawItemTickLabels(GraphicsContext g, int width, int height)
   {
      ChartContentProvider content = getContentProvider();
      int datasetOffset = content.getLayerRange().getOffset();
      int seriesOffset = getSeriesWindow().getOffset();
      int itemOffset = getItemWindow().getOffset();
      int itemCount = getItemWindow().getCount();
      int itemTickStep = getItemTickStep();
      ColorRGBA textColor = getTextColor();
      ColorRGBA deselectedTextColor = getDeselectedTextColor();
      
      Range itemSelection = getItemSelection();

      for (int i = itemTickStep - 1 - itemOffset % itemTickStep; i < itemCount; i += itemTickStep)
      {
         String label = content.getItemLabel(datasetOffset, seriesOffset, i
               + itemOffset);

         if (itemSelection.contains(itemOffset + i))
         {
            g.setTextColor(textColor);
         }
         else
         {
            g.setTextColor(deselectedTextColor);
         }
         
         // FIXME: The literal numbers should be variables/constants
         g.drawString3D(label, itemWidth, i * (itemWidth + itemWidthSpace), 0f,
               0.002f);         
      }
   }

   protected void drawSeriesTickLabels(GraphicsContext g, int width, int height)
   {
      ChartContentProvider content = getContentProvider();
      int seriesCount = getSeriesWindow().getCount();
      int seriesOffset = getSeriesWindow().getOffset();
      int itemCount = getItemWindow().getCount();

      for (int s = 0; s < seriesCount; s++)
      {
         String label = content.getSeriesLabel(0, s + seriesOffset);
         Rectangle2D bounds = g.getTextBounds(label);

         // FIXME: The literal numbers should be variables/constants
         g.drawString3D(label, -itemWidth - 0.002f * (float) bounds.getWidth(),
               s * (itemDepth + itemDepthSpace), 0f, 0.002f);

         // FIXME: The literal numbers should be variables/constants
         g.drawString3D(label, (float) itemCount * (itemWidth + itemWidthSpace)
               + (0.5f * itemWidthSpace), s * (itemDepth + itemDepthSpace), 0f,
               0.002f);
      }
   }

   protected void drawValueTickLabels(GraphicsContext g, int width, int height)
   {
      int seriesCount = getSeriesWindow().getCount();
      int itemCount = getItemWindow().getCount();

      double minValue = getMinValue();
      double absMaxValue = getAbsMaxValue();

      double tickStep = getStepSize(absMaxValue, maxSteps);
      float dy = (float) (tickStep / absMaxValue);

      g.pushTransform();
      g.scale(1, 2, 1);

      double tickValue = tickStep;

      for (float y = dy; y <= 1.01f; y += dy)
      {
         String str = Integer.toString((int) tickValue);
         tickValue += tickStep;
         Rectangle2D bounds = g.getTextBounds(str);
         // FIXME: The literal numbers should be variables/constants
         g.drawString3D(str, -itemWidth - 0.001f * (float) bounds.getWidth(),
               0.5f * (y - 0.002f * 0.5f * (float) bounds.getHeight()),
               (-seriesCount + backdropPosition) * (itemDepth + itemDepthSpace)
                     + 0.01f, 0.001f);
         // FIXME: The literal numbers should be variables/constants
         g.drawString3D(str, itemCount * (itemWidth + itemWidthSpace),
               0.5f * (y - 0.002f * 0.5f * (float) bounds.getHeight()),
               (-seriesCount + backdropPosition) * (itemDepth + itemDepthSpace)
                     + 0.01f, 0.001f);
      }

      if (minValue < 0.0)
      {
         tickValue = -tickStep;
         for (float y = -dy; y >= -1.01f; y -= dy)
         {
            String str = Integer.toString((int) tickValue);
            tickValue -= tickStep;
            Rectangle2D bounds = g.getTextBounds(str);
            // FIXME: The literal numbers should be variables/constants
            g.drawString3D(str,
                  -itemWidth - 0.001f * (float) bounds.getWidth(),
                  0.5f * (y - 0.002f * 0.5f * (float) bounds.getHeight()),
                  (-seriesCount + backdropPosition)
                        * (itemDepth + itemDepthSpace) + 0.01f, 0.001f);
            // FIXME: The literal numbers should be variables/constants
            g.drawString3D(str, itemCount * (itemWidth + itemWidthSpace),
                  0.5f * (y - 0.002f * 0.5f * (float) bounds.getHeight()),
                  (-seriesCount + backdropPosition)
                        * (itemDepth + itemDepthSpace) + 0.01f, 0.001f);
         }
      }

      g.popTransform();
   }
   
   protected String getBackdropValueLabel(int layer, int series, int item)
   {
      ChartContentProvider content = getContentProvider();
      return content.getValueLabel(layer, series, item);
   }
   
   protected void drawBackdropValueLabels(GraphicsContext g, int width,
         int height)
   {
      ChartContentProvider content = getContentProvider();
      int layerOffset = content.getLayerRange().getOffset();
      int seriesOffset = getSeriesWindow().getOffset();
      int seriesCount = getSeriesWindow().getCount();
      int itemOffset = getItemWindow().getOffset();
      int itemCount = getItemWindow().getCount();
      Range itemSelection = getItemSelection();
      ColorRGBA textColor = getTextColor();
      ColorRGBA deselectedTextColor = getDeselectedTextColor();
      Rectangle2D bounds;
      
      // Draw series label above upper left corner of backdrop
      String label = content.getSeriesLabel(layerOffset, 
                           seriesOffset + seriesCount - backdropPosition - 1);
      float fontScale = 0.0008f;
      g.pushTransform();
      g.scale(1, 2, 1);
      g.translate(-0.02f, 0.5f + 0.04f, -(seriesCount - backdropPosition)
            * (itemDepth + itemDepthSpace) + 0.01f);
      g.setTextColor(getSelectedSeriesColor());
      bounds = g.getTextBounds(label);
      g.drawString3D(label, -fontScale * (float) bounds.getWidth(), 2.0f
            * itemWidth * (float) 0, 0, fontScale);
      g.popTransform();
      
      // Draw value labels above backdrop
      for (int i = 0; i < itemCount; i++)
      {
         label = getBackdropValueLabel(layerOffset,
                           seriesOffset + seriesCount - backdropPosition - 1,
                           i + itemOffset);
         // FIXME: The literal numbers should be variables/constants
         bounds = g.getTextBounds(label);
         fontScale = 0.0008f;
         g.pushTransform();
         g.scale(1, 2, 1);
         g.translate(0, 0.5f + 0.01f, -(seriesCount - backdropPosition)
               * (itemDepth + itemDepthSpace) + 0.01f);
         g.rotateZ(-90.0f);
         g.setTextColor(0, 0, 0);
         
         if (itemSelection.contains(itemOffset + i))
         {
            g.setTextColor(textColor);
         }
         else
         {
            g.setTextColor(deselectedTextColor);
         }

         g.drawString3D(label, -fontScale * (float) bounds.getWidth(), 2.0f
               * itemWidth * (float) i, 0, fontScale);
         g.popTransform();
      }
   }

   protected void drawLabels(GraphicsContext g, int width, int height)
   {
      g.setBackfaceCulling(false);

      g.beginText3D(GraphicsContext.FONT_LABEL);
      g.setTextColor(getTextColor());
      g.pushTransform();
      g.rotateX(-90.0f);
      drawSeriesTickLabels(g, width, height);
      g.rotateZ(-90.0f);
      drawItemTickLabels(g, width, height);
      g.popTransform();
      g.endText3D();

      g.beginText3D(GraphicsContext.FONT_BIG_LABEL);
      g.setTextColor(getTextColor());
      drawValueTickLabels(g, width, height);
      drawBackdropValueLabels(g, width, height);
      g.endText3D();

      g.setBackfaceCulling(true);
   }

   protected void drawAxesLines(GraphicsContext g, int width, int height)
   {
      int seriesCount = getSeriesWindow().getCount();
      int itemOffset = getItemWindow().getOffset();
      int itemCount = getItemWindow().getCount();

      double absMaxValue = getAbsMaxValue();
      double minValue = getMinValue();

      g.setLighting(false);

      // Draw backdrop plane
      g.setForeground(getGridColor());
      g.fillRectangleXY(0, 1.0f, itemCount * (itemWidth + itemWidthSpace)
            - itemWidth, 0.0f, (-seriesCount + backdropPosition)
            * (itemDepth + itemDepthSpace) + 0.5f * itemDepthSpace);

      // Draw negative backdrop plane
      if (minValue < 0.0)
      {
         g.fillRectangleXY(0, 0.0f, itemCount * (itemWidth + itemWidthSpace)
               - itemWidth, -1.0f, (-seriesCount + backdropPosition)
               * (itemDepth + itemDepthSpace) + 0.5f * itemDepthSpace);
      }

      g.setLineSmoothing(false);
      g.setLineWidth(1.0f);

      // Avoid z-fighting for lines on backdrop plane
      g.setDepthTesting(false);

      float dy = (float) (getStepSize(absMaxValue, maxSteps) / absMaxValue);
      float z = (-seriesCount + backdropPosition)
                * (itemDepth + itemDepthSpace) + 0.5f * itemDepthSpace;
      float x1 = itemCount * (itemWidth + itemWidthSpace) 
                 - 0.5f * itemWidthSpace;
      
      // Draw support lines
      g.setForeground(getSupportLineColor());
      int supportLineStep = getSupportLineStep();
      float startX = itemWidth * 0.5f 
                     + (itemWidth + itemWidthSpace)
                     * (float)((supportLineStep - 
                                (itemOffset % supportLineStep))
                                % supportLineStep);
      // FIXME: The literal numbers should be variables/constants
      g.drawGridX(startX, (itemWidth + itemWidthSpace) * (float)supportLineStep,
                  itemCount * (itemWidth + itemWidthSpace),
                  1.0f, z);
      if (minValue < 0.0)
      {
         // Draw negative support lines
         g.drawGridX(startX, (itemWidth + itemWidthSpace) * (float)supportLineStep,
               itemCount * (itemWidth + itemWidthSpace),
               -1.0f, z);
      }
      
      g.setForeground(getAxisColor());
      // FIXME: The literal numbers should be variables/constants
      g.drawGridY(0.0f, dy, 1.01f, x1, z);

      if (minValue < 0.0)
      {
         // Scale should extend into negative value space
         // FIXME: The literal numbers should be variables/constants
         g.drawGridY(0.0f, -dy, -1.01f, x1, z);
      }

      g.setDepthTesting(true);
   }

   protected void drawAxesTicks(GraphicsContext g, int width, int height)
   {
      int seriesCount = getSeriesWindow().getCount();

      double minValue = getMinValue();
      double absMaxValue = getAbsMaxValue();

      g.setForeground(getAxisColor());
      float dy = (float) (getStepSize(absMaxValue, maxSteps) / absMaxValue);

      // FIXME: The literal numbers should be variables/constants
      g.drawTicksY(0.0f, dy, 1.01f, 0.01f, (-seriesCount + backdropPosition)
            * (itemDepth + itemDepthSpace) + 0.5f * itemDepthSpace);

      if (minValue < 0.0)
      {
         // FIXME: The literal numbers should be variables/constants
         g.drawTicksY(0.0f, -dy, -1.01f, 0.01f,
               (-seriesCount + backdropPosition) * (itemDepth + itemDepthSpace)
                     + 0.5f * itemDepthSpace);
      }

   }

   protected void drawAxes(GraphicsContext g, int width, int height)
   {
      // The scales and the lines should be two sided,
      // so temporarily disable backface culling.
      g.setBackfaceCulling(false);
      drawAxesLines(g, width, height);
      drawAxesTicks(g, width, height);
      g.setBackfaceCulling(true);
   }

   protected abstract void drawValues(GraphicsContext g, int width, int height);

   protected void drawChart(GraphicsContext g, int width, int height)
   {
      g.pushTransform();
      // FIXME: The literal numbers should be variables/constants
      g.scale(100.0f, 50.0f, 100.0f);
      drawAxes(g, width, height);
      g.setLighting(false);
      g.setTexturing(false);
      drawValues(g, width, height);
      drawLabels(g, width, height);
      g.popTransform();
   }
}
