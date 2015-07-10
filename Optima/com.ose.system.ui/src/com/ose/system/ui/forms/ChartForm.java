/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2006-2007 by Enea Software AB.
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

package com.ose.system.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import com.ose.system.ui.charts.ChartModel;
import com.ose.system.ui.charts.ChartViewer;

public class ChartForm implements IForm
{
   private FormToolkit toolkit;
   private ScrolledForm form;
   private String title;
   private Button xAxisButton;
   private Button yAxisButton;
   private Button ascendingButton;
   private Button descendingButton;
   private ChartViewer chartViewer;
   private Label filterLabel;
   private ChartModel chartModel;
   private Object filter;

   public ChartForm(ChartModel chartModel, String title, Object filter)
   {
      if ((chartModel == null) || (title == null))
      {
         throw new NullPointerException();
      }
      this.chartModel = chartModel;
      this.title = title;
      this.filter = filter;
   }

   public void createContents(Composite parent)
   {
      SortHandler sortHandler;

      // Create form.
      toolkit = new FormToolkit(parent.getDisplay());
      form = toolkit.createScrolledForm(parent);
      form.setText(title);
      form.getBody().setLayout(new GridLayout(2, false));

      // Create sort handler.
      sortHandler = new SortHandler();

      // Create sort section.
      createSortSection(sortHandler);

      // Create chart section.
      createChartSection();

      // Create order section.
      createOrderSection(sortHandler);

      // Create filter section.
      createFilterSection();

      // Refresh form contents.
      refresh();
   }

   private void createSortSection(SortHandler sortHandler)
   {
      Section section;
      Composite client;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Sort");
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      gd.verticalAlignment = SWT.BEGINNING;
      section.setLayoutData(gd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      xAxisButton = toolkit.createButton(client, "X-Axis", SWT.RADIO);
      xAxisButton.addSelectionListener(sortHandler);

      yAxisButton = toolkit.createButton(client, "Y-Axis", SWT.RADIO);
      yAxisButton.addSelectionListener(sortHandler);
      yAxisButton.setSelection(true);
   }

   private void createChartSection()
   {
      Section section;
      Composite client;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText(chartModel.getTitle());
      gd = new GridData(GridData.FILL_BOTH);
      gd.verticalSpan = 3;
      section.setLayoutData(gd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      client.setLayoutData(new GridData(GridData.FILL_BOTH));
      toolkit.paintBordersFor(client);
      section.setClient(client);

      chartViewer = new ChartViewer(client, SWT.NONE);
      chartViewer.setLayoutData(new GridData(GridData.FILL_BOTH));
      chartViewer.setChartModel(chartModel);
   }

   private void createOrderSection(SortHandler sortHandler)
   {
      Section section;
      Composite client;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Order");
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      gd.verticalAlignment = SWT.BEGINNING;
      section.setLayoutData(gd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      ascendingButton = toolkit.createButton(client, "Ascending", SWT.RADIO);
      ascendingButton.addSelectionListener(sortHandler);

      descendingButton = toolkit.createButton(client, "Descending", SWT.RADIO);
      descendingButton.addSelectionListener(sortHandler);
      descendingButton.setSelection(true);
   }

   private void createFilterSection()
   {
      Section section;
      Composite client;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION | Section.TWISTIE);
      section.setText("Filter");
      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      gd.verticalAlignment = SWT.BEGINNING;
      section.setLayoutData(gd);

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      filterLabel = toolkit.createLabel(client, "", SWT.WRAP);
      gd = new GridData();
      gd.widthHint = 120;
      filterLabel.setLayoutData(gd);
   }

   public void dispose()
   {
      toolkit.dispose();
   }

   public void setFocus()
   {
      // Nothing to be done.
   }

   public void refresh()
   {
      if (!form.isDisposed())
      {
         refreshChart();
         filterLabel.setText((filter != null) ? filter.toString() : "");
         form.reflow(true);
      }
   }

   private void refreshChart()
   {
      if (xAxisButton.getSelection())
      {
         chartModel.sortXAxis(descendingButton.getSelection());
      }
      else
      {
         chartModel.sortYAxis(descendingButton.getSelection());
      }
   }

   public Control getControl()
   {
      return form;
   }

   public void setFilter(Object filter)
   {
      this.filter = filter;
   }

   private class SortHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         refreshChart();
      }
   }
}
