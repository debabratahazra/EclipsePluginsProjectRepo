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

package com.ose.system.ui.forms;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import com.ose.system.Parameter;
import com.ose.system.Target;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class TargetForm implements IForm
{
   private static final String[] SYS_PARAMS_TABLE_HEADERS = {"Name", "Value"};
   private static final int[] SYS_PARAMS_TABLE_COLUMN_WIDTHS = {160, 550};
   private static final int SYS_PARAMS_TABLE_HEIGHT = 500;

   private IEditorPart editor;
   private FormToolkit toolkit;
   private ScrolledForm form;
   private Target target;

   private Text killedText;
   private Text nameText;
   private Text huntPathText;
   private Text byteOrderText;
   private Text osTypeText;
   private Text cpuTypeText;
   private Text numExecutionUnitsText;
   private Text tickLengthText;
   private Text ntickFrequencyText;
   private Table sysParamsTable;

   private Action copyAction;
   private Action selectAllAction;

   public TargetForm(IEditorPart editor, Target target)
   {
      if ((editor == null) || (target == null))
      {
         throw new NullPointerException();
      }
      this.editor = editor;
      this.target = target;
   }

   public void createContents(Composite parent)
   {
      TableWrapLayout layout;

      // Create form.
      toolkit = new FormToolkit(parent.getDisplay());
      form = toolkit.createScrolledForm(parent);
      layout = new TableWrapLayout();
      layout.numColumns = 2;
      form.getBody().setLayout(layout);

      // Create information section.
      createInfoSection();

      if (target.hasGetSysParamSupport())
      {
         // Create system parameters section.
         createSysParamsSection();

         // Create global actions.
         copyAction = new TableCopyAction(sysParamsTable);
         selectAllAction = new TableSelectAllAction(sysParamsTable);
      }

      // Refresh form contents.
      refresh();
   }

   private void createInfoSection()
   {
      Section section;
      TableWrapLayout layout;
      TableWrapData twd;
      Composite client;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Target Information");
      twd = new TableWrapData(TableWrapData.FILL);
      section.setLayoutData(twd);

      client = toolkit.createComposite(section);
      layout = new TableWrapLayout();
      layout.numColumns = 2;
      client.setLayout(layout);
      toolkit.paintBordersFor(client);
      section.setClient(client);

      toolkit.createLabel(client, "Killed:");
      killedText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      killedText.setLayoutData(twd);

      toolkit.createLabel(client, "Name:");
      nameText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      nameText.setLayoutData(twd);

      toolkit.createLabel(client, "Hunt Path:");
      huntPathText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      huntPathText.setLayoutData(twd);

      toolkit.createLabel(client, "Byte Order:");
      byteOrderText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      byteOrderText.setLayoutData(twd);

      toolkit.createLabel(client, "OS:");
      osTypeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      osTypeText.setLayoutData(twd);

      toolkit.createLabel(client, "CPU:");
      cpuTypeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      cpuTypeText.setLayoutData(twd);

      toolkit.createLabel(client, "Execution Units:");
      numExecutionUnitsText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      numExecutionUnitsText.setLayoutData(twd);

      toolkit.createLabel(client, "Tick Length (\u00B5s):");
      tickLengthText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      tickLengthText.setLayoutData(twd);

      toolkit.createLabel(client, "Micro Tick Frequency (Hz):");
      ntickFrequencyText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      ntickFrequencyText.setLayoutData(twd);
   }

   private void createSysParamsSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("System Parameters");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      sysParamsTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      sysParamsTable.setHeaderVisible(true);
      sysParamsTable.setLinesVisible(true);
      layout = new TableLayout();
      sysParamsTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = SYS_PARAMS_TABLE_COLUMN_WIDTHS[0] +
                     SYS_PARAMS_TABLE_COLUMN_WIDTHS[1];
      gd.heightHint = SYS_PARAMS_TABLE_HEIGHT;
      sysParamsTable.setLayoutData(gd);

      column = new TableColumn(sysParamsTable, SWT.LEFT);
      column.setText(SYS_PARAMS_TABLE_HEADERS[0]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SYS_PARAMS_TABLE_COLUMN_WIDTHS[0]));

      column = new TableColumn(sysParamsTable, SWT.LEFT);
      column.setText(SYS_PARAMS_TABLE_HEADERS[1]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SYS_PARAMS_TABLE_COLUMN_WIDTHS[1]));
   }

   public void dispose()
   {
      toolkit.dispose();
   }

   public void setFocus()
   {
      updateActionBars();
   }

   private void updateActionBars()
   {
      IActionBars bars = editor.getEditorSite().getActionBars();
      bars.setGlobalActionHandler(ActionFactory.COPY.getId(), copyAction);
      bars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(), selectAllAction);
      bars.updateActionBars();
   }

   public void refresh()
   {
      // Refresh form title.
      form.setText(target.getName());

      // Refresh information section.
      refreshInfoSection();

      if (target.hasGetSysParamSupport())
      {
         // Refresh system parameters section.
         refreshSysParamsSection();
      }

      form.reflow(true);
   }

   private void refreshInfoSection()
   {
      killedText.setText(StringUtils.toKilledString(target.isKilled()));
      nameText.setText(target.getName());
      huntPathText.setText(target.getHuntPath());
      byteOrderText.setText(StringUtils.toEndianString(target.isBigEndian()));
      osTypeText.setText(StringUtils.toOsTypeString(target.getOsType()));
      cpuTypeText.setText(StringUtils.toCpuTypeString(target.getCpuType()));
      numExecutionUnitsText.setText(Short.toString(target.getNumExecutionUnits()));
      tickLengthText.setText(Integer.toString(target.getTickLength()));
      ntickFrequencyText.setText(Integer.toString(target.getNTickFrequency()));
   }

   private void refreshSysParamsSection()
   {
      sysParamsTable.setRedraw(false);
      sysParamsTable.removeAll();
      Parameter[] sysParams = target.getSysParams();
      for (int i = 0; i < sysParams.length; i++)
      {
         Parameter sysParam = sysParams[i];
         TableItem item = new TableItem(sysParamsTable, SWT.NONE);
         item.setText(0, sysParam.getName());
         item.setText(1, sysParam.getValue());
      }
      sysParamsTable.setRedraw(true);
   }

   public Control getControl()
   {
      return form;
   }
}
