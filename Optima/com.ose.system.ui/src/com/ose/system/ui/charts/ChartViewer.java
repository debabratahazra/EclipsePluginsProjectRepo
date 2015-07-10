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
import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.factory.RunTimeContext;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.PlatformUI;
import com.ibm.icu.util.ULocale;

public class ChartViewer extends Canvas
{
   private final IDeviceRenderer deviceRenderer;

   private final ChartModelListener chartModelHandler;

   private ChartModel chartModel;

   private GeneratedChartState chartState;

   public ChartViewer(Composite parent, int style)
   {
      super(parent, style | SWT.H_SCROLL | SWT.NO_BACKGROUND);

      try
      {
         deviceRenderer = PluginSettings.instance().getDevice("dv.SWT");
      }
      catch (ChartException e)
      {
         throw new RuntimeException("Invalid graphics device: SWT is required.");
      }

      addPaintListener(new PaintHandler());
      addControlListener(new ResizeHandler());
      getHorizontalBar().addSelectionListener(new ScrollHandler());
      chartModelHandler = new ChartModelHandler();
   }

   public ChartModel getChartModel()
   {
      return chartModel;
   }

   public void setChartModel(ChartModel chartModel)
   {
      if (this.chartModel != null)
      {
         this.chartModel.removeChartModelListener(chartModelHandler);
      }
      this.chartModel = chartModel;
      chartState = null;
      try
      {
         this.chartModel.addChartModelListener(chartModelHandler);
      }
      catch (TooManyListenersException e)
      {
         throw new RuntimeException(
               "Too many listeners registered on given chart model.");
      }
      refreshChartModel();
   }

   private void refreshChartModel()
   {
      if (chartModel != null)
      {
         chartModel.setWindow(getClientArea().width);
         chartModel.scroll(getHorizontalBar().getSelection());
      }
   }

   private void createChartState()
   {
      Rectangle rect;
      Bounds bounds;

      if (chartModel == null)
      {
         return;
      }

      rect = getClientArea();
      bounds = BoundsImpl.create(rect.x + 2,
                                 rect.y + 2,
                                 rect.width - 4,
                                 rect.height - 4);
      bounds.scale(72d / deviceRenderer.getDisplayServer().getDpiResolution());

      try
      {
         Generator generator;
         RunTimeContext rtc;

         generator = Generator.instance();
         rtc = generator.prepare(chartModel.getChart(),
                                 null,
                                 null,
                                 ULocale.getDefault());
         chartState = generator.build(deviceRenderer.getDisplayServer(),
                                      chartModel.getChart(),
                                      bounds,
                                      null,
                                      rtc,
                                      null);
      }
      catch (ChartException e)
      {
         e.printStackTrace();
      }
   }

   private void resetScrollbar(int max, int window)
   {
      ScrollBar scrollBar;
      boolean scrollBarNeeded;

      scrollBar = getHorizontalBar();
      scrollBar.setMinimum(0);
      scrollBar.setMaximum(max - (window - 1));
      scrollBar.setSelection(0);
      scrollBarNeeded = (max > window);
      scrollBar.setEnabled(scrollBarNeeded);
      scrollBar.setVisible(scrollBarNeeded);
   }

   private void updateScrollbar(int max, int window)
   {
      ScrollBar scrollBar;
      boolean scrollBarNeeded;

      scrollBar = getHorizontalBar();
      scrollBar.setMaximum(max - (window - 1));
      scrollBarNeeded = (max > window);
      scrollBar.setEnabled(scrollBarNeeded);
      scrollBar.setVisible(scrollBarNeeded);
   }

   class PaintHandler implements PaintListener
   {
      public void paintControl(PaintEvent event)
      {
         Image image;
         GC gc;

         if (chartModel == null)
         {
            event.gc.fillRectangle(getBounds());
            return;
         }

         image = new Image(PlatformUI.getWorkbench().getDisplay(), getBounds());
         gc = new GC(image);
         deviceRenderer.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gc);

         if (chartState == null)
         {
            createChartState();
         }

         try
         {
            Generator generator = Generator.instance();
            generator.render(deviceRenderer, chartState);
         }
         catch (ChartException e)
         {
            e.printStackTrace();
         }

         event.gc.drawImage(image, 0, 0);
         gc.dispose();
         image.dispose();
      }
   }

   class ResizeHandler extends ControlAdapter
   {
      public void controlResized(ControlEvent event)
      {
         refreshChartModel();
      }
   }

   class ScrollHandler extends SelectionAdapter
   {
      ScrollHandler()
      {
         ScrollBar scrollBar = getHorizontalBar();
         scrollBar.setMinimum(0);
         scrollBar.setIncrement(1);
         scrollBar.setMaximum(1);
         scrollBar.setSelection(0);
         scrollBar.setEnabled(false);
         scrollBar.setVisible(false);
      }

      public void widgetSelected(SelectionEvent event)
      {
         if (chartModel != null)
         {
            chartModel.scroll(getHorizontalBar().getSelection());
         }
      }
   }

   class ChartModelHandler implements ChartModelListener
   {
      public void chartModelChanged(ChartModel chartModel)
      {
         if (!isDisposed())
         {
            resetScrollbar(chartModel.getLength(), chartModel.getWindow());
            chartState = null;
            redraw();
         }
      }

      public void chartModelScrolled(ChartModel chartModel)
      {
         if (!isDisposed())
         {
            updateScrollbar(chartModel.getLength(), chartModel.getWindow());
            chartState = null;
            redraw();
         }
      }
   }
}
