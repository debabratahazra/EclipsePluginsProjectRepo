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

package com.ose.fmd.cdt.cdi.gdb;

import java.io.File;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.fmm.Fmi;

public class AddLoadModuleDialog extends Dialog
{
   private final Fmi.PfmiLoadModuleInfo[] loadModules;

   private String file;
   private Fmi.PfmiLoadModuleInfo loadModule;

   private Text fileText;
   private ListViewer loadModuleViewer;
   private CLabel errorMessageLabel;

   public AddLoadModuleDialog(Shell parent, Fmi.PfmiLoadModuleInfo[] loadModules)
   {
      super(parent);
      setShellStyle(getShellStyle() | SWT.RESIZE);
      this.loadModules = loadModules;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Add Load Module");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      validateDialog();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      Label label;
      Button browseButton;
      List list;
      GridData gd;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(3, false));
      subcomp.setLayoutData(new GridData(GridData.FILL_BOTH));

      label = new Label(subcomp, SWT.NONE);
      label.setText("Load module file:");

      fileText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      fileText.addModifyListener(new ModifyHandler());

      browseButton = new Button(subcomp, SWT.PUSH);
      browseButton.setText("Browse...");
      browseButton.addSelectionListener(new BrowseButtonHandler());

      // Create vertical spacer.
      label = new Label(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      label = new Label(subcomp, SWT.WRAP);
      label.setText(
         "Select the load module installation handle that corresponds to " +
         "the specified load module file:");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      list = new List(subcomp, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
      gd = new GridData(GridData.FILL_BOTH);
      gd.horizontalSpan = 3;
      gd.widthHint = 400;
      gd.heightHint = 200;
      list.setLayoutData(gd);

      loadModuleViewer = new ListViewer(list);
      loadModuleViewer.setContentProvider(new LoadModuleContentProvider());
      loadModuleViewer.setLabelProvider(new LoadModuleLabelProvider());
      loadModuleViewer.setInput(loadModules);
      loadModuleViewer.addSelectionChangedListener(new SelectionChangedHandler());

      // Create error message label.
      errorMessageLabel = new CLabel(comp, SWT.NONE);
      errorMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void validateDialog()
   {
      String errorMessage = null;
      String text;

      text = fileText.getText().trim();
      if (text.length() == 0)
      {
         errorMessage = "Load module file not specified";
      }
      else if (!(new File(text)).isFile())
      {
         errorMessage = "Load module file does not exist";
      }
      else if (loadModuleViewer.getSelection().isEmpty())
      {
         errorMessage = "No load module installation handle selected";
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

   protected void okPressed()
   {
      file = fileText.getText().trim();
      ISelection selection = loadModuleViewer.getSelection();
      Object obj = ((IStructuredSelection) selection).getFirstElement();
      loadModule = (Fmi.PfmiLoadModuleInfo) obj;
      super.okPressed();
   }

   public String getFile()
   {
      return file;
   }

   public Fmi.PfmiLoadModuleInfo getLoadModule()
   {
      return loadModule;
   }

   private class BrowseButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
         dialog.setFileName(fileText.getText());
         String text = dialog.open();
         if (text != null)
         {
            fileText.setText(text);
            // Guess the corresponding installation handle.
            String filename = (new File(text)).getName();
            for (int i = 0; i < loadModules.length; i++)
            {
               Fmi.PfmiLoadModuleInfo loadModule = loadModules[i];
               if (loadModule.install_handle.contains(filename))
               {
                  loadModuleViewer.setSelection(
                        new StructuredSelection(loadModule), true);
                  break;
               }
            }
         }
      }
   }

   private class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }

   private class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         validateDialog();
      }
   }

   private static class LoadModuleContentProvider implements IStructuredContentProvider
   {
      private Fmi.PfmiLoadModuleInfo[] loadModules;

      public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
         if (newInput instanceof Fmi.PfmiLoadModuleInfo[])
         {
            loadModules = (Fmi.PfmiLoadModuleInfo[]) newInput;
         }
      }

      public void dispose()
      {
         // Nothing to be done.
      }

      public Object[] getElements(Object parent)
      {
         return ((loadModules != null) ? loadModules : new Fmi.PfmiLoadModuleInfo[0]);
      }
   }

   private static class LoadModuleLabelProvider extends LabelProvider
   {
      public String getText(Object obj)
      {
         if (obj instanceof Fmi.PfmiLoadModuleInfo)
         {
            return ((Fmi.PfmiLoadModuleInfo) obj).install_handle;
         }
         else
         {
            return null;
         }
      }

      public Image getImage(Object obj)
      {
         return null;
      }
   }
}
