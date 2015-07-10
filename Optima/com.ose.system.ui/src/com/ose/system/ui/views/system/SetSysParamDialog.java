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

package com.ose.system.ui.views.system;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class SetSysParamDialog extends Dialog
{
   private String name;
   private String value;
   private boolean reconfigure;
   private Text nameText;
   private Text valueText;
   private Button reconfigureCheckbox;
   private CLabel errorMessageLabel;

   public SetSysParamDialog(Shell parent)
   {
      super(parent);
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Set System Parameter");
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
      Composite subcomp;
      ModifyHandler modifyHandler;
      GridData gd;
      Label nameLabel;
      Label valueLabel;
      Label spacer;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));
      modifyHandler = new ModifyHandler();

      nameLabel = new Label(subcomp, SWT.NONE);
      nameLabel.setText("Name:");

      nameText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      nameText.setLayoutData(gd);
      nameText.addModifyListener(modifyHandler);

      valueLabel = new Label(subcomp, SWT.NONE);
      valueLabel.setText("Value:");

      valueText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      valueText.setLayoutData(gd);
      valueText.addModifyListener(modifyHandler);

      // Create vertical spacer.
      spacer = new Label(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      spacer.setLayoutData(gd);

      reconfigureCheckbox = new Button(subcomp, SWT.CHECK);
      reconfigureCheckbox.setText("If the system parameter is used by the " +
            "target monitor, force the target monitor to re-read it");
      gd = new GridData();
      gd.horizontalSpan = 2;
      reconfigureCheckbox.setLayoutData(gd);

      // Create vertical spacer.
      spacer = new Label(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      spacer.setLayoutData(gd);

      errorMessageLabel = new CLabel(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorMessageLabel.setLayoutData(gd);

      applyDialogFont(comp);
      return comp;
   }

   protected void okPressed()
   {
      name = nameText.getText().trim();
      value = valueText.getText().trim();
      reconfigure = reconfigureCheckbox.getSelection();
      super.okPressed();
   }

   private void updateDialog()
   {
      String errorMessage = isNameValid(nameText.getText());
      if (errorMessage == null)
      {
         errorMessage = isValueValid(valueText.getText());
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

   private String isNameValid(String text)
   {
      String errorMessage = null;
      String str = text.trim();
      if (str.length() == 0)
      {
         errorMessage = "Name not specified";
      }
      else if (str.indexOf(' ') != -1)
      {
         errorMessage = "Name cannot contain spaces";
      }
      return errorMessage;
   }

   private String isValueValid(String text)
   {
      return null;
   }

   public String getName()
   {
      return name;
   }

   public String getValue()
   {
      return value;
   }

   public boolean isReconfigure()
   {
      return reconfigure;
   }

   class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         updateDialog();
      }
   }
}
