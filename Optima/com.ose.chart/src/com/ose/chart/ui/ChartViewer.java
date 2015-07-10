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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Slider;

import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;
import com.ose.system.ui.util.RangeSelector;

public class ChartViewer extends Composite
{
   private ChartCanvas canvas;
   private Slider seriesSlider;
   private Slider itemSlider;
   private ChartUpdateHandler chartUpdateHandler;
   private RangeSelector itemSelector;
   
   private boolean seriesSliderShown;
   private boolean itemSliderShown;
   private boolean itemSelectorShown;
   
   public ChartViewer(Composite parent, int style)
   {
      super(parent, style);
      
      setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
                  
      GridLayout layout;
      
      layout = new GridLayout(2, false);
      layout.marginWidth = 2;
      layout.marginHeight = 2;
      layout.horizontalSpacing = 2;
      layout.verticalSpacing = 2;
      setLayout(layout);
      
      canvas = createChartCanvas(this);
      
      enableItemSelector();
      
      updateLayout();
      
      chartUpdateHandler = new ChartUpdateHandler();
   }
      
   public void setChart(Chart chart)
   {
      if (canvas.hasChart())
      {
         // If already attached to a chart, detach by removing listener
         chart.removeChartUpdateListener(chartUpdateHandler);
      }
      
      canvas.setChart(chart);
      
      if (canvas.hasChart())
      {
         chart.addChartUpdateListener(chartUpdateHandler);
      }
      
      updateSliders();
      
      updateItemSelector();
   }
   
   public Chart getChart()
   {
      return canvas.getChart();
   }
   
   public boolean hasChart()
   {
      return canvas.hasChart();
   }
   
   public ChartCanvas getCanvas()
   {
      return canvas;
   }
   
   public void refresh()
   {
      canvas.refresh();
   }
   
   public void hideSeriesSlider()
   {
      seriesSlider.setEnabled(false);
      seriesSlider.setVisible(false);
      seriesSliderShown = false;
      updateLayout();
   }
   
   public void showSeriesSlider()
   {
      seriesSlider.setVisible(true);      
      seriesSlider.setEnabled(true);
      seriesSliderShown = true;
      updateLayout();
   }
   
   public void hideItemSlider()
   {
      itemSlider.setEnabled(false);
      itemSlider.setVisible(false);
      itemSliderShown = false;
      updateLayout();
   }
   
   public void showItemSlider()
   {
      itemSlider.setVisible(true);
      itemSlider.setEnabled(true);
      itemSliderShown = true;
      updateLayout();
   }
   
   public void hideItemSelector()
   {
      itemSelector.setVisible(false);
      itemSelectorShown = false;
      updateLayout();
   }
   
   public void showItemSelector()
   {
      itemSelector.setVisible(true);
      itemSelectorShown = true;
      updateLayout();
   }
   
   public void enableItemSelector()
   {
      itemSelector.setEnabled(true);
      updateItemSelector();
   }
   
   public void disableItemSelector()
   {
      itemSelector.setEnabled(false);
      getChart().clearSelections();
      updateItemSelector();
   }
   
   private void updateLayout()
   {
      GridData data;

      data = (GridData)seriesSlider.getLayoutData();
      data.exclude = !seriesSliderShown;
      if (itemSelectorShown)
      {
         data.verticalSpan = 2;
      }
      else
      {
         data.verticalSpan = 1;            
      }
      data.horizontalSpan = 1;
      
      data = (GridData)itemSlider.getLayoutData();
      data.exclude = !itemSliderShown;
      data.verticalSpan = 1;
      if (seriesSliderShown)
      {
         data.horizontalSpan = 1;
      }
      else
      {
         data.horizontalSpan = 2;
      }
      
      data = (GridData)itemSelector.getLayoutData();
      data.exclude = !itemSelectorShown;
      data.verticalSpan = 1;
      if (seriesSliderShown)
      {
         data.horizontalSpan = 1;
      }
      else
      {
         data.horizontalSpan = 2;
      }
            
      data = (GridData)canvas.getLayoutData();
      data.verticalSpan = 1;
      data.horizontalSpan = seriesSliderShown ? 1 : 2;
      
      layout();
   }
   
   private void updateSliders()
   {
      Chart chart = getChart();
      Range itemWindow = chart.getItemWindow();
      Range seriesWindow = chart.getSeriesWindow();
      ChartContentProvider content = chart.getContentProvider(); 
      Range itemRange = content.getItemRange();
      Range seriesRange = content.getSeriesRange();
      
      if (!itemWindow.contains(itemRange))
      {
         // If itemRange is NOT fully contained in itemWindow,
         // then a scrollbar is needed
         itemSlider.setEnabled(true);
      }
      else
      {
         itemSlider.setEnabled(false);
      }
      
      if (itemSlider.isEnabled())
      {
         itemSlider.setIncrement(1);
         itemSlider.setPageIncrement(itemWindow.getCount());
         itemSlider.setMinimum(itemRange.getOffset());
         itemSlider.setMaximum(itemRange.getOffset() + itemRange.getCount());
         itemSlider.setThumb(itemWindow.getCount());
         itemSlider.setSelection(itemWindow.getOffset());
      }

      if (!seriesWindow.contains(seriesRange))
      {
         // If seriesRange is NOT fully contained in seriesWindow,
         // then a scrollbar is needed
         seriesSlider.setEnabled(true);
      }
      else
      {
         seriesSlider.setEnabled(false);
      }
      
      if (seriesSlider.isEnabled())
      {
         seriesSlider.setIncrement(1);
         seriesSlider.setPageIncrement(seriesWindow.getCount());
         seriesSlider.setMinimum(seriesRange.getOffset());
         seriesSlider.setMaximum(seriesRange.getOffset() + seriesRange.getCount());
         seriesSlider.setThumb(seriesWindow.getCount());
         int value = seriesRange.getOffset() + seriesRange.getCount()
                     - seriesWindow.getOffset() - seriesWindow.getCount();
         seriesSlider.setSelection(value);
      }
   }
   
   private void updateItemSelector()
   {
      if (itemSelector.isEnabled() && hasChart())
      {
         Range range = getChart().getContentProvider().getItemRange();
         itemSelector.setMinimum(range.getOffset());
         itemSelector.setMaximum(range.getOffset() + range.getCount() - 1);

         Range selection = getChart().getItemSelection();
         if (!selection.equals(itemSelector.getSelectionOffset(), itemSelector.getSelectionLength()))
         {
            itemSelector.setSelection(selection.getOffset(), selection.getCount());
         }
      }
      else
      {
         itemSelector.setMinimum(0);
         itemSelector.setMaximum(1);
         itemSelector.setSelection(0, 2);
      }
   }

   ChartCanvas createChartCanvas(Composite parent)
   {
      GridData data;
      
      // Chart canvas
      ChartCanvas canvas = new ChartCanvas(parent, SWT.NULL);
      data = new GridData(GridData.FILL_BOTH);
      data.grabExcessHorizontalSpace = true;
      data.grabExcessVerticalSpace = true;
      data.minimumWidth = 300;
      data.minimumHeight = 200;
      canvas.setLayoutData(data);

      // Vertical slider
      seriesSlider = new Slider(parent, SWT.VERTICAL);
      data = new GridData(GridData.FILL_VERTICAL);
      data.grabExcessVerticalSpace = true;
      seriesSlider.setLayoutData(data);
      seriesSlider.addSelectionListener(new SeriesSliderHandler());
      
      // Item range selector
      itemSelector = new RangeSelector(parent, SWT.HORIZONTAL);
      data = new GridData(GridData.FILL_HORIZONTAL);
      data.grabExcessHorizontalSpace = true;
      itemSelector.setLayoutData(data);
      itemSelector.addSelectionListener(new ItemRangeSelectorHandler());
      itemSelector.setBackground(itemSelector.getDisplay().getSystemColor(SWT.COLOR_WHITE));
      itemSelector.setModifierKey(SWT.CONTROL);
      
      // Horizontal slider
      itemSlider = new Slider(parent, SWT.HORIZONTAL);
      data = new GridData(GridData.FILL_HORIZONTAL);
      data.grabExcessHorizontalSpace = true;
      itemSlider.setLayoutData(data);
      itemSlider.addSelectionListener(new ItemSliderHandler());
      
      seriesSliderShown = false;
      seriesSlider.setVisible(false);
      seriesSlider.setEnabled(false);
      data = (GridData)seriesSlider.getLayoutData();
      data.exclude = true;
      data.horizontalSpan = 0;
      data.verticalSpan = 1;
      
      itemSelectorShown = true;
      data = (GridData)itemSelector.getLayoutData();
      data.exclude = false;
      data.horizontalSpan = 1;
      data.verticalSpan = 0;

      itemSliderShown = false;
      itemSlider.setVisible(false);
      itemSlider.setEnabled(false);
      data = (GridData)itemSlider.getLayoutData();
      data.exclude = true;
      data.horizontalSpan = 1;
      data.verticalSpan = 0;

      data = (GridData)canvas.getLayoutData();
      data.horizontalSpan = 1;
      data.verticalSpan = 1;
            
      canvas.setFocus();
      
      return canvas;
   }

   private class ChartUpdateHandler implements ChartUpdateListener
   {
      public void chartUpdated(Chart chart)
      {
         updateSliders();
         updateItemSelector();
      }
   }
   
   class ItemSliderHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         int value = ((Slider)e.getSource()).getSelection();
         Chart chart = getChart();
         chart.setItemWindow(value, chart.getItemWindow().getCount());
      }         
   }

   class SeriesSliderHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         Slider slider = (Slider)e.getSource();
         int value = slider.getMaximum() - slider.getSelection() - slider.getThumb();
         Chart chart = getChart();
         chart.setSeriesWindow(value, chart.getSeriesWindow().getCount());
      }         
   }
   
   class ItemRangeSelectorHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         if (hasChart())
         {
            RangeSelector selector = (RangeSelector)e.getSource();
            getChart().setItemSelection(selector.getSelectionOffset(), selector.getSelectionLength());
         }
      }
   }

}
