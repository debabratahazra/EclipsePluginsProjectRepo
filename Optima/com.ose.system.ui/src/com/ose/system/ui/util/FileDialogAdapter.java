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
package com.ose.system.ui.util;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileDialogAdapter
{
   private FileDialog dialog;
   private InputDialog wtDialog;
   
   public FileDialogAdapter(Shell shell, FileDialog dialog) 
   {
      if(Boolean.getBoolean("com.ose.ui.tests.testDialog")) {
         wtDialog = new InputDialog(shell,"Custom FileDialog", "Enter filename", null, null);
      }
      this.dialog = dialog;
   }
   
   public String open() {
      if(wtDialog != null) {
         if(wtDialog.open() == InputDialog.OK) {
            return wtDialog.getValue();
         }
         return null;
      }
      return dialog.open();
   }
}
