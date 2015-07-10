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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.ose.chart.math.Vector2;
import com.ose.chart.model.ChartContentProvider;

public class PieChart2D extends PolarChart2D
{
   // Number of triangles in the pie chart circle approximation
   private static final int TRIANGLE_COUNT = 1000;
      
   // Radius of the pie chart in the unscaled coordinate space
   private static final float RADIUS = 0.5f;
   
   private static final ColorRGBA REMAINING_COLOR =
         new ColorRGBA(0.95f, 0.95f, 0.95f);
   
   private static final ColorRGBA REMAINING_LABEL_COLOR =
         new ColorRGBA(0.65f, 0.65f, 0.65f);
   
   private final DecimalFormat oneDecimalFormatter = new DecimalFormat(
         "#####0.0", new DecimalFormatSymbols(Locale.US));

   // Precalculated pie circle vertices
   private final Vertex[] vertices = new Vertex[TRIANGLE_COUNT];
   
   // List of slices to draw. Contents recreated every time the chart is drawn.
   private final List slices = new ArrayList();
      
   public PieChart2D(String title,
                     ChartContentProvider contentProvider,
                     ChartColorProvider colorProvider)
   {
      super(title, contentProvider, colorProvider);
      
      createVertices();
   }
   
   @Override
   public String getToolTip(int x, int y)
   { 
      Slice slice = getSliceAt(x, y);
      String toolTip = "";
      
      if (slice != null)
      {
         StringBuilder toolTipBuilder = new StringBuilder(30);
   
         if (slice.isRemainingSlice())
         {
            toolTipBuilder.append("Remaining");
         }
         else
         {
            toolTipBuilder.append(getContentProvider()
                           .getSeriesLabel(0, slice.getSeries()));
         }
         toolTipBuilder.append(": ");
         toolTipBuilder.append(oneDecimalFormatter.format(slice.getPercent()));
         toolTipBuilder.append("%");
         toolTip = toolTipBuilder.toString();
      }
      
      return toolTip;
   }
   
   public int getSeriesAt(int x, int y)
   {
      Slice slice = getSliceAt(x, y);
      
      if (slice != null)
      {
         return slice.getSeries();
      }
      
      return -1;
   }
   
   private Slice getSliceAt(int x, int y)
   {
      Vector2 center = getCenter();
      Vector2 v = new Vector2(x - center.x, y - center.y);
      double alpha = 0;
            
      // Is the point outside the pie chart radius?
      if (v.length() > RADIUS * getScale())
      {
         // The point is not within the pie chart.
         return null;
      }
      
      // Find the unit circle angle of the given point 
      if (v.x != 0.0f)
      {
         alpha = -(Math.atan(v.y / -v.x) - (Math.PI / 2.0));
         if (v.x < 0.0f)
         {
            alpha += Math.PI;
         }
      }
      else
      {
         if (v.y < 0.0f)
         {
            alpha = 0.0;
         }
         else if (v.y > 0.0f)
         {
            alpha = Math.PI;
         }
         else
         {
            alpha = 0.0;
         }
      }
      
      // Translate the angle into a slice index
      double radiansPerSlice = 2.0 * Math.PI / (double) TRIANGLE_COUNT;
      int sliceIndex = (int) (alpha / radiansPerSlice);
      
      // Loop over the list of drawn slices until the slice 
      // matching the slice index is found and return it.
      Iterator iterator = slices.iterator();
      while (iterator.hasNext())
      {
         Slice slice = (Slice)iterator.next();
         if (sliceIndex >= slice.getVertexOffset()
             && sliceIndex < slice.getVertexOffset() + slice.getVertexCount())
         {
            return slice;
         }
      }

      return null;
   }
      
   @Override
   protected void drawValueLabels(GraphicsContext g, int width, int height)
   {
      float scale = getScale();
      Vector2 center = getCenter();
      float threshold = Math.max((725.0f - scale) / 125.0f, 1.0f);
      
      Iterator iterator = slices.iterator();
      while (iterator.hasNext())
      {
         Slice slice = (Slice)iterator.next();
       
         if (slice.isRemainingSlice())
         {
            g.setForeground(REMAINING_LABEL_COLOR);
         }
         else
         {
            g.setForeground(ColorRGBA.BLACK);
         }
         
         if (slice.getPercent() >= threshold)
         {
            String str = oneDecimalFormatter.format(slice.getPercent()) + "%";
            Rectangle2D r = g.getTextBounds(str);
            int x = (int) ((center.x + 0.8f * slice.getLabelX() * scale)
                           - ((float) r.getWidth() / 4.0f));
            int y = (int) ((center.y + 0.8f * slice.getLabelY() * scale)
                           - ((float) r.getHeight() / 2.0f));
            g.drawString2D(str, x, y);
         }
      }
   }
   
   @Override
   protected void drawValues(GraphicsContext g, int width, int height)
   {
      ChartColorProvider colors = getColorProvider();
            
      createSlices();
                  
      g.beginFan();
      Iterator iterator = slices.iterator();
      while (iterator.hasNext())
      {
         Slice slice = (Slice)iterator.next();
         
         if (slice.isRemainingSlice())
         {
            g.fillFanAroundOrigin(vertices, slice.getVertexOffset(),
                                  slice.getVertexCount(), REMAINING_COLOR);
         }
         else
         {
            ColorRGBA color = colors.getSeriesColor(0, 
                                        getSourceSeries(slice.getSeries()));
            g.fillFanAroundOrigin(vertices, slice.getVertexOffset(), 
                                  slice.getVertexCount(), color);
         }
      }
      g.endFan();      
   }
      
   /**
    * Precalculates the vertices of the pie chart circle.
    */
   private void createVertices()
   {  
      for (int i = 0; i < vertices.length; i++)
      {
         vertices[i] = new Vertex(Vertex.POSITION);
         vertices[i].position.set(
               -RADIUS * (float)Math.cos((double)i * 2.0 * Math.PI
                     / (double)TRIANGLE_COUNT + Math.PI / 2.0),
               RADIUS * (float)Math.sin((double)i * 2.0 * Math.PI
                     / (double)TRIANGLE_COUNT + Math.PI / 2.0),
               0.0f);
      }
   }
   
   /**
    * Calculates the pie chart slices each time the chart is drawn.
    */
   private void createSlices()
   {
      ChartContentProvider content = getContentProvider();
      int seriesOffset = getSeriesWindow().getOffset();
      int seriesCount = getSeriesWindow().getCount();
      int selectionOffset = getItemSelection().getOffset();
      int selectionCount = getItemSelection().getCount();
      
      int vertexOffset = 0;
      
      double remainingPercent = 100.0;
      
      slices.clear();
      
      if (selectionCount > 0)
      {
         for (int s = 0; s < seriesCount; s++)
         {
            double sum = 0.0;
            
            // Calculate the average percentage for this
            // series over the selection interval.
            for (int i = 0; i < selectionCount; i++)
            {
               double value = content.getValue(0,
                                               s + seriesOffset,
                                               i + selectionOffset);
               if (!Double.isNaN(value))
               {
                  sum += value;
               }
            }
            
            double percent = (selectionCount > 0) ? sum / (double) selectionCount
                                                  : 0;
                     
            // Quantize the value into a number of triangles (fan vertices)
            int vertexCount = (int)Math.round((double)TRIANGLE_COUNT
                                              * percent * 0.01);
            
            // If erroneous data is fed to the pie chart,
            // do not draw any slices.
            if (vertexOffset + vertexCount > TRIANGLE_COUNT + 100)
            {
               slices.clear();
               return;
            }
            
            // Only create a slice if it contains at least one triangle
            if (vertexCount > 0)
            {
               Slice slice = new Slice(s + seriesOffset,
                                       percent,
                                       vertexOffset,
                                       vertexCount);
               
               remainingPercent -= percent;
                  
               vertexOffset += slice.getVertexCount();
               
               slices.add(slice);
            }
         }
      }
      
      if (vertexOffset < TRIANGLE_COUNT - 1)
      {
         // Create a slice to fill out the pie if the slices
         // do not already occupy 100% of the pie.
         Slice slice = new Slice(Slice.REMAINING_SERIES, remainingPercent,
                                 vertexOffset, TRIANGLE_COUNT - vertexOffset);
         
         slices.add(slice);
      }      
   }
      
   /**
    * Represents a pie chart slice.
    */
   private class Slice
   {
      static final int REMAINING_SERIES = -1;

      private int series;
      
      private double percent;
            
      private int vertexOffset;
      
      private int vertexCount;
            
      private float labelX;
      
      private float labelY;
            
      Slice(int series, double percent, int vertexOffset, int vertexCount)
      {
         this.series = series;
         this.percent = percent;
         this.vertexOffset = vertexOffset;
         this.vertexCount = vertexCount;
         
         calculateSliceLabelPosition();
      }
      
      boolean isRemainingSlice()
      {
         return series == REMAINING_SERIES;
      }

      int getSeries()
      {
         return series;
      }
      
      double getPercent()
      {
         return percent;
      }
      
      double getValue()
      {
         return percent * 0.01;
      }
      
      int getVertexOffset()
      {
         return vertexOffset;
      }
      
      int getVertexCount()
      {
         return vertexCount;
      }
      
      float getLabelX()
      {
         return labelX;
      }
      
      void setLabelX(float x)
      {
         labelX = x;
      }
      
      float getLabelY()
      {
         return labelY;
      }
      
      void setLabelY(float y)
      {
         labelY = y;
      }
      
      void calculateSliceLabelPosition()
      {
         // Find the midpoint between the two "middle-most" vertices
         int midVertexIndex = getVertexOffset() + (getVertexCount() / 2);
         Vertex vertex0 = vertices[midVertexIndex % TRIANGLE_COUNT];
         Vertex vertex1 = vertices[(midVertexIndex + 1) % TRIANGLE_COUNT];
         setLabelX((vertex0.position.x + vertex1.position.x) / 2f);
         setLabelY((vertex0.position.y + vertex1.position.y) / 2f);
      }
   }
}
