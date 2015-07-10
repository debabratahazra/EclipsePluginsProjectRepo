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

package com.ose.system.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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

public class ShowMemoryDialog extends Dialog
{
   private String title;
   private long address;
   private Text addressText;
   private long length;
   private Text lengthText;
   private CLabel errorMessageLabel;

   public ShowMemoryDialog(Shell parent)
   {
      this(parent, "Show Memory");
   }

   public ShowMemoryDialog(Shell parent, String title)
   {
      super(parent);
      if (title == null)
      {
         throw new IllegalArgumentException();
      }
      this.title = title;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText(title);
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
      Label label;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));
      modifyHandler = new ModifyHandler();

      label = new Label(subcomp, SWT.NONE);
      label.setText("Address:");

      addressText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      addressText.setLayoutData(gd);
      addressText.addModifyListener(modifyHandler);

      label = new Label(subcomp, SWT.NONE);
      label.setText("Length:");

      lengthText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      lengthText.setLayoutData(gd);
      lengthText.addModifyListener(modifyHandler);

      errorMessageLabel = new CLabel(subcomp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorMessageLabel.setLayoutData(gd);

      applyDialogFont(comp);
      return comp;
   }

   protected void okPressed()
   {
      try
      {
         address = Long.decode(addressText.getText().trim()).longValue();
      } catch (NumberFormatException ignore) {}
      try
      {
         length = Long.decode(lengthText.getText().trim()).longValue();
      }
      catch (NumberFormatException e)
      {
         length = 0;
      }
      super.okPressed();
   }

   private void updateDialog()
   {
      String errorMessage = isAddressValid(addressText.getText());
      if (errorMessage == null)
      {
         errorMessage = isLengthValid(lengthText.getText());
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
      else
      {
         try
         {
            long value = Long.decode(text.trim()).longValue();
            if (value < 0L)
            {
               errorMessage = "Negative address";
            }
            else if (value > 0xFFFFFFFFL)
            {
               errorMessage = "Too big address";
            }
         }
         catch (NumberFormatException e)
         {
            errorMessage = "Invalid address";
         }
      }
      return errorMessage;
   }

   private String isLengthValid(String text)
   {
      String errorMessage = null;
      if (text.trim().length() > 0)
      {
         try
         {
            long value = Long.decode(text.trim()).longValue();
            if (value < 0L)
            {
               errorMessage = "Negative length";
            }
            else if (value > 0xFFFFFFFFL)
            {
               errorMessage = "Too big length";
            }
         }
         catch (NumberFormatException e)
         {
            errorMessage = "Invalid length";
         }
      }
      return errorMessage;
   }

   public long getAddress()
   {
      return address;
   }

   public long getLength()
   {
      return length;
   }

   class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         updateDialog();
      }
   }
}
