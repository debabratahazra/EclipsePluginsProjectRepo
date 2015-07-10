/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.launch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.perf.ui.ProfilerPlugin;
import com.ose.perf.ui.SharedImages;
import com.ose.system.LoadModuleInfo;

public class MainTab extends AbstractLaunchConfigurationTab
{
   private Text reportFileText;
   private TableViewer binaryViewer;
   private List binaryList;
   private Button addButton;
   private Button editButton;
   private Button removeButton;

   public void createControl(Composite parent)
   {
      Composite comp;
      Label label;
      Button button;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      comp.setLayout(new GridLayout(3, false));
      setControl(comp);

      label = new Label(comp, SWT.NONE);
      label.setText("Profiling Report File:");
      reportFileText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      reportFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      reportFileText.addModifyListener(new ReportFileTextHandler());
      button = createPushButton(comp, "Browse...", null);
      button.addSelectionListener(new BrowseButtonHandler());

      label = new Label(comp, SWT.NONE);
      label.setText("C/C++ Binaries:");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      createTable(comp);
      createTableButtons(comp);
   }

   private void createTable(Composite parent)
   {
      Composite comp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      gd = new GridData(GridData.FILL_BOTH);
      gd.horizontalSpan = 2;
      comp.setLayoutData(gd);
      layout = new TableColumnLayout();
      comp.setLayout(layout);

      table = new Table(comp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
            | SWT.MULTI | SWT.FULL_SELECTION);
      table.setHeaderVisible(true);
      table.setLinesVisible(true);

      column = new TableColumn(table, SWT.LEFT);
      column.setText("File");
      column.setMoveable(true);
      layout.setColumnData(column, new ColumnWeightData(2, 100));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Installation Handle");
      column.setMoveable(true);
      layout.setColumnData(column, new ColumnWeightData(1, 100));

      binaryViewer = new TableViewer(table);
      binaryViewer.setContentProvider(new BinaryTableContentProvider());
      binaryViewer.setLabelProvider(new BinaryTableLabelProvider());
      binaryViewer.addSelectionChangedListener(new BinaryTableSelectionHandler());
      binaryViewer.addDoubleClickListener(new BinaryTableDoubleClickHandler());
   }

   private void createTableButtons(Composite parent)
   {
      Composite comp;
      GridLayout layout;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING
            | GridData.HORIZONTAL_ALIGN_END);
      gd.horizontalSpan = 1;
      comp.setLayoutData(gd);
      layout = new GridLayout(1, false);
      layout.marginWidth = 0;
      layout.marginHeight = 0;
      comp.setLayout(layout);

      addButton = createPushButton(comp, "Add...", null);
      addButton.addSelectionListener(new AddButtonHandler());

      editButton = createPushButton(comp, "Edit...", null);
      editButton.addSelectionListener(new EditButtonHandler());
      editButton.setEnabled(false);

      removeButton = createPushButton(comp, "Remove", null);
      removeButton.addSelectionListener(new RemoveButtonHandler());
      removeButton.setEnabled(false);
   }

   private void handleTableSelectionChanged(SelectionChangedEvent event)
   {
      int size = ((IStructuredSelection) event.getSelection()).size();
      editButton.setEnabled(size == 1);
      removeButton.setEnabled(size > 0);
   }

   private void handleAddButtonSelected()
   {
      BinaryFileDialog dialog = new BinaryFileDialog(getShell(),
            reportFileText.getText().trim());
      if (dialog.open() == Window.OK)
      {
         BinaryFileContainer binary = new BinaryFileContainer(
               dialog.getBinaryFile(), dialog.getInstallHandle());
         binaryList.add(binary.toString());
         binaryViewer.refresh();
         updateLaunchConfigurationDialog();
      }
   }

   private void handleEditButtonSelected()
   {
      IStructuredSelection sel = (IStructuredSelection) binaryViewer.getSelection();
      if (!sel.isEmpty())
      {
         String entry = (String) sel.getFirstElement();
         BinaryFileContainer binary = new BinaryFileContainer(entry);
         BinaryFileDialog dialog = new BinaryFileDialog(getShell(),
            reportFileText.getText().trim(), binary.getPath(),
            binary.getInstallHandle());

         if (dialog.open() == Window.OK)
         {
            binary.setPath(dialog.getBinaryFile());
            binary.setInstallHandle(dialog.getInstallHandle());
            if (!entry.equals(binary.toString()))
            {
               binaryList.set(binaryList.indexOf(entry), binary.toString());
            }
            binaryViewer.refresh();
            updateLaunchConfigurationDialog();
         }
      }
   }

   private void handleRemoveButtonSelected()
   {
      IStructuredSelection sel = (IStructuredSelection) binaryViewer.getSelection();
      for (Iterator i = sel.iterator(); i.hasNext();)
      {
         String entry = (String) i.next();
         binaryList.remove(entry);
      }
      binaryViewer.refresh();
      updateLaunchConfigurationDialog();
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
   {
      configuration.setAttribute(
         IProfilerLaunchConfigurationConstants.ATTR_DUMP_FILE, "");
      configuration.setAttribute(
         IProfilerLaunchConfigurationConstants.ATTR_BINARY_FILES, new ArrayList());
   }

   public void initializeFrom(ILaunchConfiguration configuration)
   {
      String reportFile = "";
      List binaries = null;

      try
      {
         reportFile = configuration.getAttribute(
            IProfilerLaunchConfigurationConstants.ATTR_DUMP_FILE, "");
         binaries = configuration.getAttribute(
            IProfilerLaunchConfigurationConstants.ATTR_BINARY_FILES, (List) null);
      }
      catch (CoreException e)
      {
         ProfilerPlugin.log(e);
      }

      reportFileText.setText(reportFile);
      binaryList = new ArrayList();
      if (binaries != null)
      {
         binaryList.addAll(binaries);
      }
      binaryViewer.setInput(binaryList);
   }

   public void activated(ILaunchConfigurationWorkingCopy configuration)
   {
   }

   public void performApply(ILaunchConfigurationWorkingCopy configuration)
   {
      configuration.setAttribute(
         IProfilerLaunchConfigurationConstants.ATTR_DUMP_FILE,
         reportFileText.getText().trim());
      configuration.setAttribute(
         IProfilerLaunchConfigurationConstants.ATTR_BINARY_FILES,
         binaryList);
   }

   public boolean isValid(ILaunchConfiguration configuration)
   {
      setErrorMessage(null);
      setMessage(null);

      String text = reportFileText.getText().trim();
      if (text.length() == 0)
      {
         setErrorMessage("Profiling report file not specified");
         return false;
      }
      else if (!(new File(text)).isFile())
      {
         setErrorMessage("Profiling report file does not exist");
         return false;
      }

      return true;
   }

   public String getName()
   {
      return "Main";
   }

   public Image getImage()
   {
      return SharedImages.get(SharedImages.IMG_VIEW_MAIN_TAB);
   }

   public String getId()
   {
      return "com.ose.perf.ui.launch.MainTab";
   }

   private class ReportFileTextHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         updateLaunchConfigurationDialog();
      }
   }

   private class BrowseButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
         dialog.setFilterExtensions(new String[] {"*.pmd"});
         dialog.setFileName(reportFileText.getText().trim());
         String file = dialog.open();
         if (file != null)
         {
            reportFileText.setText(file);
         }
      }
   }

   private class AddButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleAddButtonSelected();
      }
   }

   private class EditButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleEditButtonSelected();
      }
   }

   private class RemoveButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleRemoveButtonSelected();
      }
   }

   private class BinaryTableSelectionHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         handleTableSelectionChanged(event);
      }
   }

   private class BinaryTableDoubleClickHandler implements IDoubleClickListener
   {
      public void doubleClick(DoubleClickEvent event)
      {
         if (!binaryViewer.getSelection().isEmpty())
         {
            handleEditButtonSelected();
         }
      }
   }

   private static class BinaryTableContentProvider
      implements IStructuredContentProvider
   {
      public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
      }

      public void dispose()
      {
      }

      public Object[] getElements(Object inputElement)
      {
         List binaries = (List) inputElement;
         return binaries.toArray();
      }
   }

   private static class BinaryTableLabelProvider extends LabelProvider
      implements ITableLabelProvider
   {
      public String getColumnText(Object element, int columnIndex)
      {
         BinaryFileContainer binary = new BinaryFileContainer((String) element);

         switch (columnIndex)
         {
            case 0:
               return binary.getPath();
            case 1:
               return binary.getInstallHandle();
            default:
               return null;
         }
      }

      public Image getColumnImage(Object element, int columnIndex)
      {
         return null;
      }
   }

   private static class BinaryFileDialog extends Dialog
   {
      private final File reportFile;
      private String binaryFile;
      private String installHandle;

      private Text binaryFileText;
      private Combo installHandleCombo;
      private CLabel errorMessageLabel;

      BinaryFileDialog(Shell parent, String reportFile)
      {
         this(parent, reportFile, null, null);
      }

      BinaryFileDialog(Shell parent, String reportFile,
                       String binaryFile, String installHandle)
      {
         super(parent);
         setShellStyle(getShellStyle() | SWT.RESIZE);
         this.reportFile = new File(reportFile);
         this.binaryFile = binaryFile;
         this.installHandle = installHandle;
      }

      public String getBinaryFile()
      {
         return binaryFile;
      }

      public String getInstallHandle()
      {
         return installHandle;
      }

      protected void configureShell(Shell shell)
      {
         super.configureShell(shell);
         shell.setText((binaryFile != null) ? "Edit Binary" : "Add Binary");
      }

      protected Control createContents(Composite parent)
      {
         Control contents = super.createContents(parent);
         // Force initial validation.
         updateDialog();
         return contents;
      }

      protected Control createDialogArea(Composite parent)
      {
         Composite comp;
         Label label;
         Button button;
         Label spacer;
         GridData gd;

         comp = (Composite) super.createDialogArea(parent);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(3, false));

         label = new Label(comp, SWT.NONE);
         label.setText("File:");

         binaryFileText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         if (binaryFile != null)
         {
            binaryFileText.setText(binaryFile);
         }
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.widthHint = 300;
         binaryFileText.setLayoutData(gd);
         binaryFileText.addModifyListener(new ModifyHandler());

         button = new Button(comp, SWT.PUSH);
         button.setText("Browse...");
         button.addSelectionListener(new BrowseButtonHandler());

         label = new Label(comp, SWT.NONE);
         label.setText("Installation Handle:");

         installHandleCombo = new Combo(comp, SWT.NONE);
         populateInstallHandleCombo();
         if (installHandle != null)
         {
            installHandleCombo.setText(installHandle);
         }
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         installHandleCombo.setLayoutData(gd);

         spacer = new Label(comp, SWT.NONE);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 3;
         spacer.setLayoutData(gd);

         errorMessageLabel = new CLabel(comp, SWT.NONE);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 3;
         errorMessageLabel.setLayoutData(gd);

         return comp;
      }

      protected void okPressed()
      {
         binaryFile = binaryFileText.getText().trim();
         installHandle = installHandleCombo.getText();
         super.okPressed();
      }

      private void populateInstallHandleCombo()
      {
         List<LoadModuleInfo> loadModules = null;

         try
         {
            LoadModuleReader loadModuleReader = new LoadModuleReader();
            loadModules = loadModuleReader.getLoadModules(reportFile);
         }
         catch (IOException e)
         {
            ProfilerPlugin.log(e);
         }

         installHandleCombo.removeAll();
         installHandleCombo.add("");
         if (loadModules != null)
         {
            for (LoadModuleInfo loadModule : loadModules)
            {
               int options = loadModule.getOptions();
               if ((options & LoadModuleInfo.LOAD_MODULE_ABSOLUTE) == 0)
               {
                  installHandleCombo.add(loadModule.getInstallHandle());
               }
            }
         }
         installHandleCombo.setVisibleItemCount((installHandleCombo
               .getItemCount() < 20) ? installHandleCombo.getItemCount() : 20);
         installHandleCombo.select(0);
      }

      private void updateInstallHandleCombo(String path)
      {
         boolean found = false;
         String filename = (new File(path)).getName();
         String[] installHandles = installHandleCombo.getItems();

         for (int i = 0; i < installHandles.length; i++)
         {
            if (installHandles[i].contains(filename))
            {
               installHandleCombo.select(i);
               found = true;
               break;
            }
         }

         if (!found)
         {
            installHandleCombo.select(0);
         }
      }

      private void updateDialog()
      {
         String errorMessage = null;
         String text = binaryFileText.getText().trim();
         if (text.length() == 0)
         {
            errorMessage = "Binary file not specified";
         }
         else if (!(new File(text)).isFile())
         {
            errorMessage = "Binary file does not exist";
         }         
         setErrorMessage(errorMessage);
      }

      private void setErrorMessage(String errorMessage)
      {
         errorMessageLabel.setText((errorMessage == null) ? "" : errorMessage);
         errorMessageLabel.setImage((errorMessage == null) ? null :
            PlatformUI.getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
         getButton(IDialogConstants.OK_ID).setEnabled(errorMessage == null);
         getShell().update();
      }

      private class ModifyHandler implements ModifyListener
      {
         public void modifyText(ModifyEvent event)
         {
            updateDialog();
         }
      }

      private class BrowseButtonHandler extends SelectionAdapter
      {
         public void widgetSelected(SelectionEvent event)
         {
            FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
            dialog.setFileName(binaryFileText.getText().trim());
            String file = dialog.open();
            if (file != null)
            {
               binaryFileText.setText(file);
               updateInstallHandleCombo(file);
            }
         }
      }
   }
}
