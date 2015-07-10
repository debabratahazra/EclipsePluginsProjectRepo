/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class SetPriorityDialog extends Dialog
{
   private int priority;
   private Text priorityText;
   private CLabel errorMessageLabel;

   public SetPriorityDialog(Shell parent, int currentPriority)
   {
      super(parent);
      priority = currentPriority;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Set Priority");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      priorityText.setText(Integer.toString(priority));
      priorityText.selectAll();
      updateDialog();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      GridData gd;
      Label label;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));

      label = new Label(subcomp, SWT.NONE);
      label.setText("Priority:");

      priorityText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.ENTRY_FIELD_WIDTH);
      priorityText.setLayoutData(gd);
      priorityText.addModifyListener(new ModifyHandler());

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
         priority = Integer.parseInt(priorityText.getText().trim());
      }
      catch (NumberFormatException ignore) {}
      super.okPressed();
   }

   private void updateDialog()
   {
      String errorMessage = isPriorityValid(priorityText.getText().trim());
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

   private String isPriorityValid(String text)
   {
      String errorMessage = null;
      if (text.length() == 0)
      {
         errorMessage = "Priority not specified";
      }
      else
      {
         try
         {
            int prio = Integer.parseInt(text);
            if ((prio < 0) || (prio > 31))
            {
               errorMessage = "Invalid priority";
            }
         }
         catch (NumberFormatException e)
         {
            errorMessage = "Invalid priority";
         }
      }
      return errorMessage;
   }

   public int getPriority()
   {
      return priority;
   }

   private class ModifyHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         updateDialog();
      }
   }
}
