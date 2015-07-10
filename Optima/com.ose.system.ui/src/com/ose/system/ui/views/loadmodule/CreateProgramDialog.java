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

package com.ose.system.ui.views.loadmodule;

import java.util.Map;
import java.util.Properties;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import com.ose.system.Parameter;
import com.ose.system.Target;
import com.ose.system.ui.dialogs.SetEnvVarDialog;
import com.ose.system.ui.util.StringUtils;

public class CreateProgramDialog extends Dialog
{
   private static final int ENV_VARS_TABLE_WIDTH = 320;
   private static final int ENV_VARS_TABLE_HEIGHT = 140;

   private final Target target;
   private String arguments;
   private Properties envVars;

   private Combo executionUnitCombo;
   private Text argumentsText;
   private TableViewer envVarsTableViewer;
   private Button newButton;
   private Button editButton;
   private Button removeButton;

   public CreateProgramDialog(Shell parent, Target target)
   {
      super(parent);
      setShellStyle(getShellStyle() | SWT.RESIZE);
      this.target = target;
      envVars = new Properties();
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Create Program");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      updateButtons();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      GridLayout layout;
      TabFolder tabFolder;

      comp = (Composite) super.createDialogArea(parent);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new GridLayout(1, false);
      layout.marginWidth = 10;
      layout.marginHeight = 10;
      comp.setLayout(layout);

      createExecutionUnitCombo(comp);

      tabFolder = new TabFolder(comp, SWT.NONE);
      tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
      createArgumentsTab(tabFolder);
      createEnvVarsTab(tabFolder);

      applyDialogFont(comp);
      return comp;
   }

   private void createExecutionUnitCombo(Composite parent)
   {
      Composite comp;
      GridLayout layout;
      Label label;
      int numExecutionUnits;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      layout = new GridLayout(2, false);
      layout.marginWidth = 0;
      comp.setLayout(layout);

      label = new Label(comp, SWT.NONE);
      label.setText("Execution Unit:");

      executionUnitCombo = new Combo(comp, SWT.READ_ONLY);
      executionUnitCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      numExecutionUnits = 1;
      if (target.getNumExecutionUnits() > 0)
      {
         numExecutionUnits = target.getNumExecutionUnits() & 0xFFFF;
      }
      for (int i = 0; i < numExecutionUnits; i++)
      {
         executionUnitCombo.add(Integer.toString(i));
      }
      executionUnitCombo.setVisibleItemCount(executionUnitCombo.getItemCount());
      executionUnitCombo.select(0);
   }

   private void createArgumentsTab(TabFolder tabFolder)
   {
      Composite argumentsComp;
      TabItem argumentsTabItem;
      Label argumentsLabel;
      GridData gd;

      argumentsComp = new Composite(tabFolder, SWT.NONE);
      argumentsComp.setLayout(new GridLayout(1, false));
      argumentsTabItem = new TabItem(tabFolder, SWT.NONE);
      argumentsTabItem.setText("Arguments");
      argumentsTabItem.setControl(argumentsComp);

      argumentsLabel = new Label(argumentsComp, SWT.NONE);
      argumentsLabel.setText("Command Line Arguments:");

      argumentsText = new Text(argumentsComp,
            SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
      gd = new GridData(GridData.FILL_BOTH);
      argumentsText.setLayoutData(gd);
   }

   private void createEnvVarsTab(TabFolder tabFolder)
   {
      Composite envVarsComp;
      TabItem envVarsTabItem;
      Label envVarsLabel;
      GridData gd;

      envVarsComp = new Composite(tabFolder, SWT.NONE);
      envVarsComp.setLayout(new GridLayout(2, false));
      envVarsTabItem = new TabItem(tabFolder, SWT.NONE);
      envVarsTabItem.setText("Environment");
      envVarsTabItem.setControl(envVarsComp);

      envVarsLabel = new Label(envVarsComp, SWT.NONE);
      envVarsLabel.setText("Environment Variables:");
      gd = new GridData();
      gd.horizontalSpan = 2;
      envVarsLabel.setLayoutData(gd);

      envVarsTableViewer = new TableViewer(createEnvVarsTable(envVarsComp));
      envVarsTableViewer.setContentProvider(new EnvVarsContentProvider());
      envVarsTableViewer.setLabelProvider(new EnvVarsLabelProvider());
      envVarsTableViewer.addSelectionChangedListener(new EnvVarsSelectionHandler());
      envVarsTableViewer.addDoubleClickListener(new EnvVarsDoubleClickHandler());
      envVarsTableViewer.setInput(envVars);

      createEnvVarsButtons(envVarsComp);
   }

   private Table createEnvVarsTable(Composite parent)
   {
      Table table;
      TableLayout layout;
      TableColumn column;
      GridData gd;

      table = new Table(parent,
            SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);
      layout = new TableLayout();
      table.setLayout(layout);
      gd = new GridData(GridData.FILL_BOTH);
      gd.widthHint = ENV_VARS_TABLE_WIDTH;
      gd.heightHint = ENV_VARS_TABLE_HEIGHT;
      table.setLayoutData(gd);

      column = new TableColumn(table, SWT.NONE);
      column.setText("Name");
      column.setMoveable(true);
      layout.addColumnData(new ColumnWeightData(10));

      column = new TableColumn(table, SWT.NONE);
      column.setText("Value");
      column.setMoveable(true);
      layout.addColumnData(new ColumnWeightData(10));

      return table;
   }

   private void createEnvVarsButtons(Composite parent)
   {
      Composite comp;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
      comp.setLayout(new GridLayout(1, true));

      newButton = new Button(comp, SWT.PUSH);
      newButton.setText("New...");
      gd = new GridData(GridData.FILL_BOTH);
      newButton.setLayoutData(gd);
      newButton.addSelectionListener(new NewButtonHandler());

      editButton = new Button(comp, SWT.PUSH);
      editButton.setText("Edit...");
      gd = new GridData(GridData.FILL_BOTH);
      editButton.setLayoutData(gd);
      editButton.addSelectionListener(new EditButtonHandler());

      removeButton = new Button(comp, SWT.PUSH);
      removeButton.setText("Remove");
      gd = new GridData(GridData.FILL_BOTH);
      removeButton.setLayoutData(gd);
      removeButton.addSelectionListener(new RemoveButtonHandler());
   }

   private void newEnvVar()
   {
      SetEnvVarDialog dialog =
         new SetEnvVarDialog(getShell(), "New Environment Variable");
      int result = dialog.open();
      if (result == Window.OK)
      {
         envVars.setProperty(dialog.getName(), dialog.getValue());
         envVarsTableViewer.refresh();
         updateButtons();
      }
   }

   private void editEnvVar(Map.Entry entry)
   {
      SetEnvVarDialog dialog = new SetEnvVarDialog(
            getShell(), "Edit Environment Variable",
            entry.getKey().toString(), entry.getValue().toString());
      int result = dialog.open();
      if (result == Window.OK)
      {
         envVars.remove(entry.getKey());
         envVars.setProperty(dialog.getName(), dialog.getValue());
         envVarsTableViewer.refresh();
         updateButtons();
      }
   }

   private void removeEnvVar()
   {
      IStructuredSelection selection =
         (IStructuredSelection) envVarsTableViewer.getSelection();
      Object[] elements = selection.toArray();
      for (int i = 0; i < elements.length; i++)
      {
          envVars.remove(((Map.Entry) elements[i]).getKey());
      }
      envVarsTableViewer.refresh();
      updateButtons();
   }

   private void updateButtons()
   {
      IStructuredSelection selection =
         (IStructuredSelection) envVarsTableViewer.getSelection();
      editButton.setEnabled(selection.size() == 1);
      removeButton.setEnabled(selection.size() > 0);
   }

   protected void okPressed()
   {
      arguments = argumentsText.getText().trim().replaceAll("\\s{2,}", " ");

      // Add execution unit to environment variables.
      String executionUnitStr = executionUnitCombo.getText();
      short executionUnit = 0;
      try
      {
         executionUnit = StringUtils.parseU16(executionUnitStr);
      } catch (NumberFormatException ignore) {}
      if (executionUnit > 0)
      {
         envVars.setProperty(Parameter.OSE_LM_CPU_ID, executionUnitStr);
      }

      super.okPressed();
   }

   public String getArguments()
   {
      return arguments;
   }

   public Map getEnvVars()
   {
      return envVars;
   }

   class NewButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         newEnvVar();
      }
   }

   class EditButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) envVarsTableViewer.getSelection();
         if (selection.size() == 1)
         {
            editEnvVar((Map.Entry) selection.getFirstElement());
         }
      }
   }

   class RemoveButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         removeEnvVar();
      }
   }

   class EnvVarsSelectionHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         updateButtons();
      }
   }

   class EnvVarsDoubleClickHandler implements IDoubleClickListener
   {
      public void doubleClick(DoubleClickEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) event.getSelection();
         if (selection.size() == 1)
         {
            editEnvVar((Map.Entry) selection.getFirstElement());
         }
      }
   }

   static class EnvVarsContentProvider implements IStructuredContentProvider
   {
      private Properties envVars;

      public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
         if (newInput instanceof Properties)
         {
            envVars = (Properties) newInput;
         }
      }

      public void dispose()
      {
         // Nothing to be done.
      }

      public Object[] getElements(Object parent)
      {
         return ((envVars != null) ? envVars.entrySet().toArray() : new Object[0]);
      }
   }

   static class EnvVarsLabelProvider extends LabelProvider implements
         ITableLabelProvider
   {
      public String getColumnText(Object element, int index)
      {
         if (element instanceof Map.Entry)
         {
            switch (index)
            {
               case 0:
                  return ((Map.Entry) element).getKey().toString();
               case 1:
                  return ((Map.Entry) element).getValue().toString();
               default:
                  return null;
            }
         }
         else
         {
            return null;
         }
      }

      public Image getColumnImage(Object element, int index)
      {
         return null;
      }
   }
}
