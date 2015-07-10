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

import java.io.File;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.ui.util.FileDialogAdapter;

public class InstallLoadModuleDialog extends Dialog
{
   private String filename;

   private File loadModule;
   private String installHandle;
   private int httpServerPort;
   private String httpVmHuntPath;
   private boolean persistent;
   private boolean absolute;

   private Text loadModuleText;
   private Text installHandleText;
   private Text httpServerPortText;
   private Text httpVmHuntPathText;
   private Button persistentCheckbox;
   private Button absoluteCheckbox;

   private CLabel errorMessageLabel;

   public InstallLoadModuleDialog(Shell parent)
   {
      super(parent);
      setShellStyle(getShellStyle() | SWT.RESIZE);
   }

   public InstallLoadModuleDialog(Shell parent, String filename)
   {
      this(parent);
      this.filename = filename;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Install Load Module");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      initContents();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      ValidationHandler validationHandler;
      Label label;
      GridData gd;
      Button browseButton;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(3, false));
      subcomp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      validationHandler = new ValidationHandler();

      label = new Label(subcomp, SWT.WRAP);
      label.setText(
            "The specified load module is loaded from a HTTP server on your " +
            "host using the specified port (0 means any free port) and " +
            "installed with the specified installation handle on the selected " +
            "target. If your target has no HTTP volume manager, you must " +
            "specify a hunt path from your target to another, reachable " +
            "target that has a HTTP volume manager with access to your host " +
            "network.");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      gd.widthHint = 500;
      label.setLayoutData(gd);

      // Create vertical spacer.
      label = new Label(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      label = new Label(subcomp, SWT.NONE);
      label.setText("Load Module:");

      loadModuleText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      loadModuleText.setLayoutData(gd);
      loadModuleText.addModifyListener(validationHandler);

      browseButton = new Button(subcomp, SWT.PUSH);
      browseButton.setText("Browse...");
      browseButton.addSelectionListener(new BrowseButtonHandler());

      label = new Label(subcomp, SWT.NONE);
      label.setText("Installation Handle:");

      installHandleText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      installHandleText.setLayoutData(gd);
      installHandleText.addModifyListener(validationHandler);

      label = new Label(subcomp, SWT.NONE);
      label.setText("HTTP Server Port:");

      httpServerPortText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      httpServerPortText.setLayoutData(gd);
      httpServerPortText.addModifyListener(validationHandler);

      label = new Label(subcomp, SWT.NONE);
      label.setText("HTTP Volume Manager Hunt Path:");

      httpVmHuntPathText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      httpVmHuntPathText.setLayoutData(gd);
      httpVmHuntPathText.addModifyListener(validationHandler);

      // Create vertical spacer.
      label = new Label(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      persistentCheckbox = new Button(subcomp, SWT.CHECK);
      persistentCheckbox.setText("Persistent");
      gd = new GridData();
      gd.horizontalSpan = 3;
      persistentCheckbox.setLayoutData(gd);

      absoluteCheckbox = new Button(subcomp, SWT.CHECK);
      absoluteCheckbox.setText("Absolute Linked");
      gd = new GridData();
      gd.horizontalSpan = 3;
      absoluteCheckbox.setLayoutData(gd);

      // Create error message label.
      errorMessageLabel = new CLabel(comp, SWT.NONE);
      errorMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void initContents()
   {
      if (filename != null)
      {
         loadModuleText.setText(filename);
         installHandleText.setText((new File(filename)).getName());
      }
      httpServerPortText.setText("0");
      validateDialog();
   }

   private void validateDialog()
   {
      String errorMessage = null;
      String text;

      text = loadModuleText.getText().trim();
      if (text.length() == 0)
      {
         errorMessage = "Load module not specified";
      }
      else if (!(new File(text)).isFile())
      {
         errorMessage = "Load module does not exist";
      }
      else if (installHandleText.getText().trim().length() == 0)
      {
         errorMessage = "Installation handle not specified";
      }
      else
      {
         text = httpServerPortText.getText().trim();
         if (text.length() == 0)
         {
            errorMessage = "HTTP server port not specified";
         }
         else
         {
            try
            {
               Integer.parseInt(text);
            }
            catch (NumberFormatException e)
            {
               errorMessage = "Invalid HTTP server port number";
            }
         }
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
      loadModule = new File(loadModuleText.getText().trim());
      installHandle = installHandleText.getText().trim();
      httpServerPort = Integer.parseInt(httpServerPortText.getText().trim());
      httpVmHuntPath = httpVmHuntPathText.getText().trim();
      persistent = persistentCheckbox.getSelection();
      absolute = absoluteCheckbox.getSelection();
      super.okPressed();
   }

   public File getLoadModule()
   {
      return loadModule;
   }

   public String getInstallHandle()
   {
      return installHandle;
   }

   public int getHttpServerPort()
   {
      return httpServerPort;
   }

   public String getHttpVmHuntPath()
   {
      return httpVmHuntPath;
   }

   public boolean isPersistent()
   {
      return persistent;
   }

   public boolean isAbsolute()
   {
      return absolute;
   }

   class BrowseButtonHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
         dialog.setFileName(loadModuleText.getText());
         FileDialogAdapter adapter = new FileDialogAdapter(getShell(), dialog);
         String text = adapter.open();
         if (text != null)
         {
            loadModuleText.setText(text);
            if (installHandleText.getText().trim().length() == 0)
            {
               File file = new File(text);
               installHandleText.setText(file.getName());
            }
         }
      }
   }

   class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }
}
