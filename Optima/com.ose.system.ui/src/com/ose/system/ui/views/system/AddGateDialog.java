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

package com.ose.system.ui.views.system;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.ui.SystemBrowserPlugin;

public class AddGateDialog extends Dialog
{
   private String address;
   private int port;
   private Text addressText;
   private Text portText;
   private CLabel errorMessageLabel;

   public AddGateDialog(Shell parent)
   {
      super(parent);
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Add Gate");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      loadDialogSettings();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      ModifyHandler modifyHandler;
      GridData gd;
      Label addressLabel;
      Label portLabel;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));
      modifyHandler = new ModifyHandler();

      addressLabel = new Label(subcomp, SWT.NONE);
      addressLabel.setText("Address:");

      addressText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      addressText.setLayoutData(gd);
      addressText.addModifyListener(modifyHandler);

      portLabel = new Label(subcomp, SWT.NONE);
      portLabel.setText("Port:");

      portText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      portText.setLayoutData(gd);
      portText.addModifyListener(modifyHandler);

      errorMessageLabel = new CLabel(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorMessageLabel.setLayoutData(gd);

      applyDialogFont(comp);
      return comp;
   }

   private void loadDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.getSection("AddGateDialog");
      if (section == null)
      {
         addressText.setText("");
         portText.setText("21768");
      }
      else
      {
         addressText.setText(section.get("address"));
         portText.setText(section.get("port"));
         addressText.selectAll();
      }
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("AddGateDialog");

      section.put("address", addressText.getText().trim());
      section.put("port", portText.getText().trim());
   }

   protected void okPressed()
   {
      address = addressText.getText().trim();
      try
      {
         port = Integer.parseInt(portText.getText().trim());
      }
      catch (NumberFormatException ignore) {}
      saveDialogSettings();
      super.okPressed();
   }

   private void updateDialog()
   {
      String errorMessage = isAddressValid(addressText.getText());
      if (errorMessage == null)
      {
         errorMessage = isPortValid(portText.getText());
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

   private String isAddressValid(String text)
   {
      String errorMessage = null;
      if (text.trim().length() == 0)
      {
         errorMessage = "Address not specified";
      }
      return errorMessage;
   }

   private String isPortValid(String text)
   {
      String errorMessage = null;
      if (text.trim().length() == 0)
      {
         errorMessage = "Port not specified";
      }
      else
      {
         try
         {
            Integer.parseInt(text);
         }
         catch (NumberFormatException e)
         {
            errorMessage = "Invalid port number";
         }
      }
      return errorMessage;
   }

   public String getAddress()
   {
      return address;
   }

   public int getPort()
   {
      return port;
   }

   class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         updateDialog();
      }
   }
}
