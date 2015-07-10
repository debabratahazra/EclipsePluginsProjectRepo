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

package com.ose.prof.ui.editors.process;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class EditorFormPage extends FormPage
{
   public static final String ID = "com.ose.prof.ui.editors.process.EditorFormPage";
   
   private ProcessMasterDetailsBlock block;
   
   public EditorFormPage(FormEditor editor)
   {
      super(editor, ID, "Editor");
      block = new ProcessMasterDetailsBlock(this);
   }
   
   protected void createFormContent(final IManagedForm managedForm) {
      block.createContent(managedForm);
  }
}
