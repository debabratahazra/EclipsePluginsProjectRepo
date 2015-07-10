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
import com.ose.system.Process;
import com.ose.system.SignalInfo;
import com.ose.system.ui.util.ActionMultiplexer;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.util.TableCopyAction;
import com.ose.system.ui.util.TableSelectAllAction;

public class ProcessForm implements IForm
{
   private static final String[] SIGSELECT_TABLE_HEADERS = {"Value"};
   private static final int SIGSELECT_TABLE_COLUMN_WIDTH = 80;
   private static final int SIGSELECT_TABLE_HEIGHT = 280;

   private static final String[] SIGNAL_QUEUE_TABLE_HEADERS =
      {"Sig No", "Sender", "Addressee", "Size", "Address"};
   private static final int SIGNAL_QUEUE_TABLE_COLUMN_WIDTH = 70;
   private static final int SIGNAL_QUEUE_TABLE_HEIGHT = 280;

   private static final String[] ENV_VARS_TABLE_HEADERS = {"Name", "Value"};
   private static final int ENV_VARS_TABLE_COLUMN_WIDTH = 150;
   private static final int ENV_VARS_TABLE_HEIGHT = 280;

   private IEditorPart editor;
   private FormToolkit toolkit;
   private ScrolledForm form;
   private Process process;

   // TODO: Add sidText, creatorText, supervisorText, supervisorStackSizeText,
   // timeSliceText, vectorText, numSignalsAttachedText, errorHandlerText,
   // and signalsAttachedTable when supported.

   private Text killedText;
   private Text nameText;
   private Text idText;
   private Text bidText;
   private Text uidText;
   private Text typeText;
   private Text stateText;
   private Text priorityText;
   private Text numSignalsInQueueText;
   private Text entrypointText;
   private Text semaphoreValueText;
   private Text numSignalsOwnedText;
   private Text fileNameText;
   private Text lineNumberText;
   private Text stackSizeText;
   private Text executionUnitText;
   private Table sigselectTable;
   private Table signalQueueTable;
   private Table envVarsTable;

   private Action copyAction;
   private Action selectAllAction;

   public ProcessForm(IEditorPart editor, Process process)
   {
      if ((editor == null) || (process == null))
      {
         throw new NullPointerException();
      }
      this.editor = editor;
      this.process = process;
   }

   public void createContents(Composite parent)
   {
      TableWrapLayout layout;
      ActionMultiplexer copyActionMux;
      ActionMultiplexer selectAllActionMux;

      // Create form.
      toolkit = new FormToolkit(parent.getDisplay());
      form = toolkit.createScrolledForm(parent);
      layout = new TableWrapLayout();
      layout.numColumns = 4;
      form.getBody().setLayout(layout);

      // Create global actions.
      copyActionMux = new ActionMultiplexer();
      selectAllActionMux = new ActionMultiplexer();

      // Create information section.
      createInfoSection();

      // Create signal select section.
      createSigselectSection();
      copyActionMux.addAction(new TableCopyAction(sigselectTable));
      selectAllActionMux.addAction(new TableSelectAllAction(sigselectTable));

      // Create signal queue section.
      createSignalQueueSection();
      copyActionMux.addAction(new TableCopyAction(signalQueueTable));
      selectAllActionMux.addAction(new TableSelectAllAction(signalQueueTable));

      if (process.getTarget().hasGetEnvVarSupport())
      {
         // Create environment variables section.
         createEnvVarsSection();
         copyActionMux.addAction(new TableCopyAction(envVarsTable));
         selectAllActionMux.addAction(new TableSelectAllAction(envVarsTable));
      }

      // Store global actions.
      copyAction = copyActionMux;
      selectAllAction = selectAllActionMux;

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
      section.setText("Process Information");
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

      toolkit.createLabel(client, "Process ID:");
      idText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      idText.setLayoutData(twd);

      if (process.getTarget().hasBlockSupport())
      {
         toolkit.createLabel(client, "Block ID:");
         bidText = toolkit.createText(client, "", SWT.READ_ONLY);
         twd = new TableWrapData(TableWrapData.FILL_GRAB);
         bidText.setLayoutData(twd);
      }

      toolkit.createLabel(client, "User Number:");
      uidText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      uidText.setLayoutData(twd);

      toolkit.createLabel(client, "Type:");
      typeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      typeText.setLayoutData(twd);

      toolkit.createLabel(client, "State:");
      stateText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      stateText.setLayoutData(twd);

      toolkit.createLabel(client, "Priority:");
      priorityText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      priorityText.setLayoutData(twd);

      toolkit.createLabel(client, "Signals in Queue:");
      numSignalsInQueueText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      numSignalsInQueueText.setLayoutData(twd);

      toolkit.createLabel(client, "Entrypoint:");
      entrypointText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      entrypointText.setLayoutData(twd);

      toolkit.createLabel(client, "Fast Semaphore:");
      semaphoreValueText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      semaphoreValueText.setLayoutData(twd);

      toolkit.createLabel(client, "Owned Signals:");
      numSignalsOwnedText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      numSignalsOwnedText.setLayoutData(twd);

      toolkit.createLabel(client, "Filename:");
      fileNameText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      fileNameText.setLayoutData(twd);

      toolkit.createLabel(client, "Line Number:");
      lineNumberText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      lineNumberText.setLayoutData(twd);

      toolkit.createLabel(client, "Stack Size:");
      stackSizeText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      stackSizeText.setLayoutData(twd);

      toolkit.createLabel(client, "Execution Unit:");
      executionUnitText = toolkit.createText(client, "", SWT.READ_ONLY);
      twd = new TableWrapData(TableWrapData.FILL_GRAB);
      executionUnitText.setLayoutData(twd);
   }

   private void createSigselectSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Signal Select");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      sigselectTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      sigselectTable.setHeaderVisible(true);
      sigselectTable.setLinesVisible(true);
      layout = new TableLayout();
      sigselectTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = SIGSELECT_TABLE_COLUMN_WIDTH * SIGSELECT_TABLE_HEADERS.length;
      gd.heightHint = SIGSELECT_TABLE_HEIGHT;
      sigselectTable.setLayoutData(gd);

      // Dummy first column to get real first column right-aligned on Windows.
      column = new TableColumn(sigselectTable, SWT.NONE);
      column.setText("");
      layout.addColumnData(new ColumnPixelData(0));

      column = new TableColumn(sigselectTable, SWT.RIGHT);
      column.setText(SIGSELECT_TABLE_HEADERS[0]);
      layout.addColumnData(new ColumnPixelData(SIGSELECT_TABLE_COLUMN_WIDTH));
   }

   private void createSignalQueueSection()
   {
      Section section;
      Composite client;
      TableLayout layout;
      TableColumn column;
      GridData gd;

      section = toolkit.createSection(form.getBody(),
            Section.TITLE_BAR | Section.DESCRIPTION);
      section.setText("Signal Queue");
      section.setLayoutData(new TableWrapData());

      client = toolkit.createComposite(section);
      client.setLayout(new GridLayout());
      toolkit.paintBordersFor(client);
      section.setClient(client);

      signalQueueTable = toolkit.createTable(client,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      signalQueueTable.setHeaderVisible(true);
      signalQueueTable.setLinesVisible(true);
      layout = new TableLayout();
      signalQueueTable.setLayout(layout);

      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = SIGNAL_QUEUE_TABLE_COLUMN_WIDTH * SIGNAL_QUEUE_TABLE_HEADERS.length;
      gd.heightHint = SIGNAL_QUEUE_TABLE_HEIGHT;
      signalQueueTable.setLayoutData(gd);

      // Dummy first column to get real first column right-aligned on Windows.
      column = new TableColumn(signalQueueTable, SWT.NONE);
      column.setText("");
      layout.addColumnData(new ColumnPixelData(0));

      column = new TableColumn(signalQueueTable, SWT.RIGHT);
      column.setText(SIGNAL_QUEUE_TABLE_HEADERS[0]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SIGNAL_QUEUE_TABLE_COLUMN_WIDTH));

      column = new TableColumn(signalQueueTable, SWT.RIGHT);
      column.setText(SIGNAL_QUEUE_TABLE_HEADERS[1]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SIGNAL_QUEUE_TABLE_COLUMN_WIDTH));

      column = new TableColumn(signalQueueTable, SWT.RIGHT);
      column.setText(SIGNAL_QUEUE_TABLE_HEADERS[2]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SIGNAL_QUEUE_TABLE_COLUMN_WIDTH));

      column = new TableColumn(signalQueueTable, SWT.RIGHT);
      column.setText(SIGNAL_QUEUE_TABLE_HEADERS[3]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SIGNAL_QUEUE_TABLE_COLUMN_WIDTH));

      column = new TableColumn(signalQueueTable, SWT.RIGHT);
      column.setText(SIGNAL_QUEUE_TABLE_HEADERS[4]);
      column.setMoveable(true);
      layout.addColumnData(new ColumnPixelData(SIGNAL_QUEUE_TABLE_COLUMN_WIDTH));
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
      form.setText(process.getName());

      // Refresh information section.
      refreshInfoSection();

      // Refresh signal select section.
      refreshSigselectSection();

      // Refresh signal queue section.
      refreshSignalQueueSection();

      if (process.getTarget().hasGetEnvVarSupport())
      {
         // Refresh environment variables section.
         refreshEnvVarsSection();
      }

      form.reflow(true);
   }

   private void refreshInfoSection()
   {
      killedText.setText(StringUtils.toKilledString(process.isKilled()));
      nameText.setText(process.getName());
      idText.setText(StringUtils.toHexString(process.getId()));
      if (process.getTarget().hasBlockSupport())
      {
         bidText.setText(StringUtils.toHexString(process.getBid()));
      }
      uidText.setText(Integer.toString(process.getUid()));
      typeText.setText(StringUtils.toProcessTypeString(process.getType()));
      stateText.setText(StringUtils.toProcessStateString(process.getState()));
      priorityText.setText(Integer.toString(process.getPriority()));
      numSignalsInQueueText.setText(Integer.toString(process.getNumSignalsInQueue()));
      entrypointText.setText(StringUtils.toHexString(process.getEntrypoint()));
      semaphoreValueText.setText(Integer.toString(process.getSemaphoreValue()));
      numSignalsOwnedText.setText(Integer.toString(process.getNumSignalsOwned()));
      fileNameText.setText((process.getFileName().length() > 0) ?
            process.getFileName() : StringUtils.NOT_AVAILABLE);
      lineNumberText.setText((process.getLineNumber() > 0) ?
            Integer.toString(process.getLineNumber()) : StringUtils.NOT_AVAILABLE);
      stackSizeText.setText(Integer.toString(process.getStackSize()));
      executionUnitText.setText(Short.toString(process.getExecutionUnit()));
   }

   private void refreshSigselectSection()
   {
      sigselectTable.setRedraw(false);
      sigselectTable.removeAll();
      int[] sigselect = process.getSigselect();
      if (sigselect.length > 0)
      {
         String s = ((sigselect[0] == 0) ? StringUtils.ALL : Integer.toString(sigselect[0]));
         TableItem item = new TableItem(sigselectTable, SWT.NONE);
         item.setText(1, s);
      }
      for (int i = 1; i < sigselect.length; i++)
      {
         TableItem item = new TableItem(sigselectTable, SWT.NONE);
         item.setText(1, StringUtils.toU32String(sigselect[i]));
      }
      sigselectTable.setRedraw(true);
   }

   private void refreshSignalQueueSection()
   {
      signalQueueTable.setRedraw(false);
      signalQueueTable.removeAll();
      SignalInfo[] signalQueue = process.getSignalQueue();
      for (int i = 0; i < signalQueue.length; i++)
      {
         SignalInfo sig = signalQueue[i];
         TableItem item = new TableItem(signalQueueTable, SWT.NONE);
         item.setText(1, StringUtils.toU32String(sig.getSigNo()));
         item.setText(2, StringUtils.toHexString(sig.getSender()));
         item.setText(3, StringUtils.toHexString(sig.getAddressee()));
         item.setText(4, Integer.toString(sig.getSize()));
         item.setText(5, StringUtils.toHexString(sig.getAddress()));
      }
      signalQueueTable.setRedraw(true);
   }

   private void refreshEnvVarsSection()
   {
      envVarsTable.setRedraw(false);
      envVarsTable.removeAll();
      Parameter[] envVars = process.getEnvVars();
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
