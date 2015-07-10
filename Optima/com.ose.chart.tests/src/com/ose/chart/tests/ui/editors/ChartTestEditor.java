/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.chart.tests.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import com.ose.chart.ui.CartesianChart3D;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.ChartViewer;

/**
 * editor for the charts
 *
 */
public class ChartTestEditor extends EditorPart
{
   private ChartViewer chartViewer;
   private Label testDescription;
   
   static final float ZOOM_STEP = 10.0f;

   Slider gaugeSlider;
   
   public void doSave(IProgressMonitor monitor)
   {
      // editor is only used to view the charts
   }

   public void doSaveAs()
   {
      // editor is only used to view the charts
   }

   public void init(IEditorSite site, IEditorInput input)
         throws PartInitException
   {
      setSite(site);
      setInput(input);
      setPartName(input.getName());
   }

   public boolean isDirty()
   {
      // this editor is only used to view the charts
      return false;
   }

   public boolean isSaveAsAllowed()
   {
      // this editor does not have save capabilities
      return false;
   }

   public void createPartControl(Composite parent)
   {
      System.gc();
      Display display;
      GridLayout layout;
      GridData data;
      
      Composite chartControl;
      int chartViewerSpan = 1;
      
      display = parent.getDisplay();
      parent.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
      layout = new GridLayout(2, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      parent.setLayout(layout);

      IEditorInput input = this.getEditorInput();
      if (!(input instanceof ChartTestEditorInput))
         return;
      
      ChartTestEditorInput testChartInput = (ChartTestEditorInput)input;
    
      testDescription = new Label(parent, SWT.NONE);
      testDescription.setBackground(parent.getBackground());
      data = new GridData(GridData.FILL_HORIZONTAL);
      data.horizontalSpan = 2;
      testDescription.setLayoutData(data);
      
      testDescription.setText(testChartInput.getTestDescription());
      
      Chart chart = testChartInput.getChart();
      
      if (chart instanceof CartesianChart3D)
      {
         chartControl = createChartControl(parent, chart.getSeriesWindow().getCount());
         if (chartControl != null)
         {
            data = new GridData(GridData.FILL_VERTICAL);
            data.horizontalSpan = 1;
            chartControl.setLayoutData(data);
            
            chartViewerSpan = 1;
         }
         else
         {
            chartViewerSpan = 2;
         }
      }
      else
      {
         chartViewerSpan = 2;
      }

      /*
       * the chartViewer will always display an itemSlider and in addition, 
       * a seriesSlider will be present if we have more then 20 Series 
       */      
      chartViewer = new ChartViewer(parent, SWT.NONE);      
      chartViewer.showItemSlider();
      if (chart instanceof CartesianChart3D && chart.getContentProvider().getSeriesRange().getCount() > 20)
         chartViewer.showSeriesSlider();
      chartViewer.setChart(chart);
      
      data = new GridData(GridData.FILL_BOTH);
      data.horizontalSpan = chartViewerSpan;
      chartViewer.setLayoutData(data);
   }
   
   Composite createChartControl(Composite parent, int sliderSteps)
   {
      Group group;
      GridLayout layout;
      GridData data;
      Label label;
      Button button;
      ChartFocusHandler focus;
            
      focus = new ChartFocusHandler();
            
      group = new Group(parent, SWT.SHADOW_NONE);
      group.setBackground(group.getDisplay().getSystemColor(SWT.COLOR_WHITE));
      group.setLayoutData(new GridData(GridData.FILL_VERTICAL));
      layout = new GridLayout(2, false);
      group.setLayout(layout);
      group.setText("Chart Parameters");

      button = new Button(group, SWT.PUSH);
      data = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1);
      button.setLayoutData(data);
      button.setText("Reset");
      button.addSelectionListener(new ResetCameraButtonSelectionHandler());

      label = new Label(group, SWT.HORIZONTAL | SWT.LEFT | SWT.SHADOW_NONE);
      label.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false, 2, 1));
      label.setBackground(parent.getBackground());
      label.setText("Zoom:");      

      button = new Button(group, SWT.ARROW);
      button.setAlignment(SWT.DOWN);
      button.addSelectionListener(new ZoomOutButtonSelectionHandler());
      
      button = new Button(group, SWT.ARROW);
      button.setAlignment(SWT.UP);
      button.addSelectionListener(new ZoomInButtonSelectionHandler());
      
      label = new Label(group, SWT.HORIZONTAL | SWT.LEFT | SWT.SHADOW_NONE);
      label.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false, 2, 1));
      label.setBackground(parent.getBackground());
      label.setText("Gauge:");

      gaugeSlider = new Slider(group, SWT.HORIZONTAL);
      data = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1);
      data.widthHint = 0;
      gaugeSlider.setLayoutData(data);
      gaugeSlider.setIncrement(1);
      gaugeSlider.setPageIncrement(1);
      gaugeSlider.setMinimum(0);
      gaugeSlider.setMaximum(sliderSteps);
      gaugeSlider.setThumb(1);
      gaugeSlider.setSelection(0);
      gaugeSlider.addSelectionListener(new GaugeSliderSelectionHandler());
      gaugeSlider.addFocusListener(focus);
          
      return group;
   }

   public void setFocus()
   {
      // TODO not implemented yet
   }

   class ChartFocusHandler extends FocusAdapter
   {
      public void focusGained(FocusEvent e)
      {
         chartViewer.setFocus();
      }      
   }
   
   class ResetCameraButtonSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         ((CartesianChart3D)chartViewer.getChart()).resetCamera();
      }
   }

   class ZoomInButtonSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         ((CartesianChart3D)chartViewer.getChart()).getMovableCamera().zoom(ZOOM_STEP);
         chartViewer.refresh();
      }      
   }
   
   class ZoomOutButtonSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         ((CartesianChart3D)chartViewer.getChart()).getMovableCamera().zoom(-ZOOM_STEP);
         chartViewer.refresh();
      }      
   }
   
   class GaugeSliderSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent e)
      {
         int value = ((Slider)e.getSource()).getSelection();
         
         ((CartesianChart3D)chartViewer.getChart()).setBackdropPosition(value);
      }         
   }
}
