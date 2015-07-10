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

package com.ose.system.ui.views.system;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SetExecutionUnitDialog extends Dialog
{
   private final short numExecutionUnits;
   private short executionUnit;
   private Combo executionUnitCombo;

   public SetExecutionUnitDialog(Shell parent,
                                 short numExecutionUnits,
                                 short executionUnit)
   {
      super(parent);
      this.numExecutionUnits = (numExecutionUnits == 0) ? 1 : numExecutionUnits;
      this.executionUnit = executionUnit;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Set Execution Unit");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      executionUnitCombo.setText(Short.toString(executionUnit));
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subcomp;
      Label label;
      GridData gd;

      comp = (Composite) super.createDialogArea(parent);
      subcomp = new Composite(comp, SWT.NONE);
      subcomp.setLayout(new GridLayout(2, false));

      label = new Label(subcomp, SWT.NONE);
      label.setText("Execution Unit:");

      executionUnitCombo = new Combo(subcomp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(80);
      executionUnitCombo.setLayoutData(gd);
      for (short i = 0; i < numExecutionUnits; i++)
      {
         executionUnitCombo.add(Short.toString(i));
      }
      executionUnitCombo.setVisibleItemCount(executionUnitCombo.getItemCount());

      applyDialogFont(comp);
      return comp;
   }

   protected void okPressed()
   {
      try
      {
         executionUnit = Short.parseShort(executionUnitCombo.getText());
      } catch (NumberFormatException ignore) {}
      super.okPressed();
   }

   public short getExecutionUnit()
   {
      return executionUnit;
   }
}
