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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddTargetDialog extends Dialog
{
   private String huntPath;
   private Text huntPathText;

   public AddTargetDialog(Shell parent, String huntPath)
   {
      super(parent);
      this.huntPath = huntPath;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Add Target");
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      GridData gd;
      Label descLabel;
      Label huntPathLabel;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));

      descLabel = new Label(subcomp, SWT.WRAP);
      if (huntPath != null)
      {
         descLabel.setText("Specify the link hunt path expression for one or " +
               "more targets that are accessible from the selected target. " +
               "A regular expression can be used for adding several targets; " +
               "where a '*' matches any string of zero or more characters " +
               "and a '?' matches any single character. Example: " +
               "<linkname-1>/<linkname-2>/ adds the specified target and */ " +
               "adds all targets with a direct link connection to the " +
               "selected target.");
      }
      else
      {
         descLabel.setText("Specify the link hunt path for a target that is " +
               "accessible from the selected gate, e.g. " +
               "<linkname-1>/<linkname-2>/. Use an empty hunt path if the " +
               "target runs on the same node as the selected gate.");
      }
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      descLabel.setLayoutData(gd);

      huntPathLabel = new Label(subcomp, SWT.NONE);
      huntPathLabel.setText("Hunt path:");
      gd = new GridData();
      gd.verticalIndent = 5;
      huntPathLabel.setLayoutData(gd);

      huntPathText = new Text(subcomp, SWT.SINGLE | SWT.BORDER);
      if (huntPath != null)
      {
         huntPathText.setText(huntPath);
      }
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.verticalIndent = 5;
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      huntPathText.setLayoutData(gd);

      applyDialogFont(comp);
      return comp;
   }

   protected void okPressed()
   {
      huntPath = huntPathText.getText().trim();
      super.okPressed();
   }

   public String getHuntPath()
   {
      return huntPath;
   }
}
