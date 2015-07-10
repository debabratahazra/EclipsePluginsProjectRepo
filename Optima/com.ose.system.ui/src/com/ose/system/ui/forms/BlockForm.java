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
import com.ose.system.Block;
import com.ose.system.Parameter;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class BlockForm implements IForm
{
   private static final String[] ENV_VARS_TABLE_HEADERS = {"Name", "Value"};
   private static final int ENV_VARS_TABLE_COLUMN_WIDTH = 150;
   private static final int ENV_VARS_TABLE_HEIGHT = 280;

   private IEditorPart editor;
   private FormToolkit toolkit;
   private ScrolledForm form;
   private Block block;

   // TODO: Add supervisorText, numSignalsAttachedText, errorHandlerText, and
   // signalsAttachedTable when supported.

   private Text killedText;
   private Text nameText;
   private Text idText;
   private Text sidText;
   private Text uidText;
   private Text maxSignalSizeText;
   private Text signalPoolIdText;
   private Text stackPoolIdText;
   private Text executionUnitText;
   private Table envVarsTable;

   private Action copyAction;
   private Action selectAllAction;

   public BlockForm(IEditorPart editor, Block block)
   {
      if ((editor == null) || (block == null))
      {
         throw new NullPointerException();
      }
      this.editor = editor;
      this.block = block;
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

      if (block.getTarget().hasGetEnvVarSupport())
      {
         // Create environment variables section.
         createEnvVarsSection();

         // Create global actions.
         copyAction = new TableCopyAction(envVarsTable);
         selectAllAction = new TableSelectAllAction(envVarsTable);
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
      section.setText("Block Information");
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

      toolkit.createLabel(client, "Block ID:");
      idText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      idText.setLayoutData(twd);

      toolkit.createLabel(client, "Segment ID:");
      sidText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      sidText.setLayoutData(twd);

      toolkit.createLabel(client, "User Number:");
      uidText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      uidText.setLayoutData(twd);

      toolkit.createLabel(client, "Max Signal Size:");
      maxSignalSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      maxSignalSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Signal Pool ID:");
      signalPoolIdText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      signalPoolIdText.setLayoutData(twd);

      toolkit.createLabel(client, "Stack Pool ID:");
      stackPoolIdText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      stackPoolIdText.setLayoutData(twd);

      toolkit.createLabel(client, "Execution Unit:");
      executionUnitText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      executionUnitText.setLayoutData(twd);
   }

   private void createEnvVarsSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Environment Variables");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      envVarsTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      envVarsTable.setHeaderVisible(true);
      envVarsTable.setLinesVisible(true);
      layout = new TableLayout();
      envVarsTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = ENV_VARS_TABLE_COLUMN_WIDTH * ENV_VARS_TABLE_HEADERS.length;
      gd.heightHint = ENV_VARS_TABLE_HEIGHT;
      envVarsTable.setLayoutData(gd);

      column = new TableColumn(envVarsTable, SWT.LEFT);
      column.setText(ENV_VARS_TABLE_HEADERS[0]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(ENV_VARS_TABLE_COLUMN_WIDTH));

      column = new TableColumn(envVarsTable, SWT.LEFT);
      column.setText(ENV_VARS_TABLE_HEADERS[1]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(ENV_VARS_TABLE_COLUMN_WIDTH));
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
      form.setText(block.getName());

      // Refresh information section.
      refreshInfoSection();

      if (block.getTarget().hasGetEnvVarSupport())
      {
         // Refresh environment variables section.
         refreshEnvVarsSection();
      }

      form.reflow(true);
   }

   private void refreshInfoSection()
   {
      killedText.setText(StringUtils.toKilledString(block.isKilled()));
      nameText.setText(block.getName());
      idText.setText(StringUtils.toHexString(block.getId()));
      sidText.setText(StringUtils.toHexString(block.getSid()));
      uidText.setText(Integer.toString(block.getUid()));
      maxSignalSizeText.setText(Integer.toString(block.getMaxSignalSize()));
      signalPoolIdText.setText(StringUtils.toHexString(block.getSignalPoolId()));
      stackPoolIdText.setText(StringUtils.toHexString(block.getStackPoolId()));
      executionUnitText.setText(Short.toString(block.getExecutionUnit()));
   }

   private void refreshEnvVarsSection()
   {
      envVarsTable.setRedraw(false);
      envVarsTable.removeAll();
      Parameter[] envVars = block.getEnvVars();
      for (int i = 0; i < envVars.length; i++)
      {
         Parameter envVar = envVars[i];
         TableItem item = new TableItem(envVarsTable, SWT.NONE);
         item.setText(0, envVar.getName());
         item.setText(1, envVar.getValue());
      }
      envVarsTable.setRedraw(true);
   }

   public Control getControl()
   {
      return form;
   }
}
