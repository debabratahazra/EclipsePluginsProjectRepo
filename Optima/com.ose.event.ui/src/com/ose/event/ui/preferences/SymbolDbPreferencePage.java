/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.event.ui.preferences;

import java.io.File;
import java.io.IOException;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import com.ose.event.ui.EventPlugin;
import com.ose.sigdb.io.BinaryFile;
import com.ose.system.ui.util.FileDialogAdapter;

public abstract class SymbolDbPreferencePage extends PreferencePage implements
   IWorkbenchPreferencePage
{
   private static final String[] EXTENSIONS = {"*.o", "*.a"};

   private SymbolDbContentProvider contentProvider;
   private CheckboxTableViewer viewer;

   private Button addButton;
   private Button editButton;
   private Button removeButton;
   private Button upButton;
   private Button downButton;

   public SymbolDbPreferencePage()
   {
      setPreferenceStore(EventPlugin.getDefault().getPreferenceStore());
      setDescription("Add, remove, and edit " + getSymbolType() +
            " database references.");
      noDefaultAndApplyButton();
   }

   protected Control createContents(Composite parent)
   {
      String type;
      Composite comp;
      GridData gd;
      Composite tableComp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;
      Composite buttonComp;
      Label spacer;

      type = getSymbolType();

      comp = new Composite(parent, SWT.NONE);
      comp.setLayout(new GridLayout(2, false));
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));

      tableComp = new Composite(comp, SWT.NONE);
      tableComp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new TableColumnLayout();
      tableComp.setLayout(layout);
      table = new Table(tableComp, SWT.CHECK | SWT.BORDER | SWT.SINGLE |
            SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
      column = new TableColumn(table, SWT.LEFT);
      column.setText("File");
      layout.setColumnData(column, new ColumnWeightData(20, 200));

      contentProvider = new SymbolDbContentProvider();
      viewer = new CheckboxTableViewer(table);
      viewer.setContentProvider(contentProvider);
      viewer.setLabelProvider(new SymbolDbLabelProvider());
      viewer.addSelectionChangedListener(new SelectionHandler());
      viewer.addCheckStateListener(new ActivationHandler());

      buttonComp = new Composite(comp, SWT.NONE);
      buttonComp.setLayout(new GridLayout(1, false));
      buttonComp.setLayoutData(new GridData(GridData.FILL_VERTICAL));

      addButton = new Button(buttonComp, SWT.NONE);
      addButton.setText("Add...");
      addButton.setToolTipText("Add a new " + type + " database");
      addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      addButton.addSelectionListener(new AddHandler());

      editButton = new Button(buttonComp, SWT.NONE);
      editButton.setText("Edit...");
      editButton.setToolTipText("Edit selected " + type + " database");
      editButton.setEnabled(false);
      editButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      editButton.addSelectionListener(new EditHandler());

      removeButton = new Button(buttonComp, SWT.NONE);
      removeButton.setText("Remove");
      removeButton.setToolTipText("Remove selected " + type + " database");
      removeButton.setEnabled(false);
      removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      removeButton.addSelectionListener(new RemoveHandler());

      spacer = new Label(buttonComp, SWT.NONE);
      gd = new GridData(GridData.FILL_BOTH);
      gd.minimumWidth = 80;
      spacer.setLayoutData(gd);

      upButton = new Button(buttonComp, SWT.NONE);
      upButton.setText("Up");
      upButton.setToolTipText("Move selected " + type
            + " database up in the list");
      upButton.setEnabled(false);
      upButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      upButton.addSelectionListener(new UpHandler());

      downButton = new Button(buttonComp, SWT.NONE);
      downButton.setText("Down");
      downButton.setToolTipText("Move selected " + type
            + " database down in the list");
      downButton.setEnabled(false);
      downButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      downButton.addSelectionListener(new DownHandler());

      loadPreferences();
      return comp;
   }

   public void init(IWorkbench workbench)
   {
      // Nothing to do.
   }

   abstract String getSymbolType();

   public boolean performOk()
   {
      storePreferences();
      return true;
   }

   private boolean isSupportedFile(String path)
   {
      BinaryFile binaryFile;
      boolean supportedFileType = false;
      boolean supportedVersion = false;

      binaryFile = new BinaryFile();
      try
      {
         binaryFile.init(new File(path));
      }
      catch (IOException e)
      {
         return false;
      }
      supportedFileType = binaryFile.supportedFileType();
      if (supportedFileType)
      {
         supportedVersion = binaryFile.supportedVersion();
      }

      return (supportedFileType && supportedVersion);
   }

   private void loadPreferences()
   {
      IPreferenceStore prefs = getPreferenceStore();

      if (prefs != null)
      {
         String type;
         int size;
         SymbolDbReference[] references;

         type = getSymbolType();
         size = prefs.getInt(type + "_ref_size");

         for (int i = 0; i < size; i++)
         {
            String path = prefs.getString(type + "_ref_" + i + "_path");
            boolean active = prefs.getBoolean(type + "_ref_" + i + "_active");
            if (path.length() > 0)
            {
               SymbolDbReference reference =
                  new SymbolDbReference(new File(path), active);
               contentProvider.addReference(reference);
            }
         }
         viewer.setInput(viewer);

         references = (SymbolDbReference[]) contentProvider.getElements(null);
         for (int i = 0; i < references.length; i++)
         {
            SymbolDbReference reference = (SymbolDbReference) references[i];
            viewer.setChecked(reference, reference.isActive());
         }
      }
   }

   private void storePreferences()
   {
      IPreferenceStore prefs = getPreferenceStore();

      if (prefs != null)
      {
         String type;
         SymbolDbReference[] references;

         type = getSymbolType();
         references = (SymbolDbReference[]) contentProvider.getElements(null);

         prefs.setValue(type + "_ref_size", references.length);
         for (int i = 0; i < references.length; i++)
         {
            SymbolDbReference reference = references[i];
            prefs.setValue(type + "_ref_" + i + "_path",
                           reference.getFile().getAbsolutePath());
            prefs.setValue(type + "_ref_" + i + "_active",
                           reference.isActive());
         }

         EventPlugin.getDefault().savePluginPreferences();
      }
   }

   private class SelectionHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         boolean hasSelection = !event.getSelection().isEmpty();
         editButton.setEnabled(hasSelection);
         removeButton.setEnabled(hasSelection);
         upButton.setEnabled(hasSelection);
         downButton.setEnabled(hasSelection);
      }
   }

   private class ActivationHandler implements ICheckStateListener
   {
      public void checkStateChanged(CheckStateChangedEvent event)
      {
         boolean checked = event.getChecked();
         SymbolDbReference reference = (SymbolDbReference) event.getElement();
         reference.setActive(checked);
         viewer.setChecked(reference, checked);
      }
   }

   private class AddHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         FileDialog dialog;
         String path;

         dialog = new FileDialog(getShell(), SWT.OPEN | SWT.APPLICATION_MODAL);
         dialog.setText("Select file");
         dialog.setFilterExtensions(EXTENSIONS);
         FileDialogAdapter adapter = new FileDialogAdapter(getShell(), dialog);
         path = adapter.open();

         if (path != null)
         {
            if (isSupportedFile(path))
            {
               SymbolDbReference reference =
                  new SymbolDbReference(new File(path), true);
               contentProvider.addReference(reference);
               viewer.refresh();
               viewer.setChecked(reference, true);
            }
            else
            {
               EventPlugin.errorDialog("Error", "Unsupported file type",
                  EventPlugin.createErrorStatus(path + " is not a " +
                     getSymbolType() + " database file."));
            }
         }
      }

      public void widgetDefaultSelected(SelectionEvent event) {}
   }

   private class EditHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) viewer.getSelection();

         if (!selection.isEmpty())
         {
            FileDialog dialog;
            String path;

            dialog = new FileDialog(getShell(), SWT.OPEN | SWT.APPLICATION_MODAL);
            dialog.setText("Select file");
            dialog.setFilterExtensions(EXTENSIONS);
            FileDialogAdapter adapter = new FileDialogAdapter(getShell(), dialog);
            path = adapter.open();

            if (path != null)
            {
               if (isSupportedFile(path))
               {
                  SymbolDbReference reference =
                     (SymbolDbReference) selection.getFirstElement();
                  reference.setFile(new File(path));
                  viewer.refresh();
               }
               else
               {
                  EventPlugin.errorDialog("Error", "Unsupported file type",
                     EventPlugin.createErrorStatus(path + " is not a " +
                        getSymbolType() + " database file."));
               }
            }
         }
      }

      public void widgetDefaultSelected(SelectionEvent event) {}
   }

   private class RemoveHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) viewer.getSelection();

         if (!selection.isEmpty())
         {
            SymbolDbReference reference =
               (SymbolDbReference) selection.getFirstElement();
            contentProvider.removeReference(reference);
            viewer.refresh();
         }
      }

      public void widgetDefaultSelected(SelectionEvent event) {}
   }

   private class UpHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) viewer.getSelection();

         if (!selection.isEmpty())
         {
            SymbolDbReference reference =
               (SymbolDbReference) selection.getFirstElement();
            contentProvider.moveUpReference(reference);
            viewer.refresh();
         }
      }

      public void widgetDefaultSelected(SelectionEvent event) {}
   }

   private class DownHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         IStructuredSelection selection =
            (IStructuredSelection) viewer.getSelection();

         if (!selection.isEmpty())
         {
            SymbolDbReference reference =
               (SymbolDbReference) selection.getFirstElement();
            contentProvider.moveDownReference(reference);
            viewer.refresh();
         }
      }

      public void widgetDefaultSelected(SelectionEvent event) {}
   }
}
