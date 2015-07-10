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

package com.ose.event.ui.view;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class UnsupportedEventActionsDialog extends MessageDialog
{
   private static final String MESSAGE =
      "Some of the requested event actions are not supported " +
      "by this target and will be ignored if you continue.\n" +
      "\n" +
      "Do you want to continue anyway?\n" +
      "\n" +
      "Non-supported event action types:";

   private final String[] unsupportedEventActions;

   public UnsupportedEventActionsDialog(Shell parent,
                                        String[] unsupportedEventActions)
   {
      super(parent,
            "Warning",
            null,
            MESSAGE,
            WARNING,
            new String[] {"Continue", IDialogConstants.CANCEL_LABEL},
            0);

      if (unsupportedEventActions == null)
      {
         throw new IllegalArgumentException();
      }
      this.unsupportedEventActions = unsupportedEventActions;
      setShellStyle(getShellStyle() | SWT.RESIZE);
   }

   protected Control createCustomArea(Composite parent)
   {
      Composite comp;
      Label spacer;
      List list;
      GridData gd;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayout(new GridLayout(2, false));
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));

      spacer = new Label(comp, SWT.NONE);
      gd = new GridData(GridData.FILL_VERTICAL);
      gd.widthHint = getImage().getBounds().width;
      spacer.setLayoutData(gd);

      list = new List(comp, SWT.BORDER);
      gd = new GridData(GridData.FILL_BOTH);
      gd.minimumHeight = 100;
      list.setLayoutData(gd);
      list.setItems(unsupportedEventActions);

      spacer = new Label(comp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      spacer.setLayoutData(gd);

      return comp;
   }
}
