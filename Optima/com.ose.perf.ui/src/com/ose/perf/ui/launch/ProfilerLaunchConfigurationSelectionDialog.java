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

package com.ose.perf.ui.launch;

import java.util.Arrays;
import java.util.List;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.AbstractElementListSelectionDialog;
import com.ose.perf.ui.ProfilerPlugin;

public class ProfilerLaunchConfigurationSelectionDialog
   extends AbstractElementListSelectionDialog
{
   public static final int CREATE_ID = IDialogConstants.CLIENT_ID + 1;

   private final IDebugModelPresentation labelProvider;
   private final Object[] elements;
   private Text filterText;

   public ProfilerLaunchConfigurationSelectionDialog(
         IDebugModelPresentation renderer, List configs)
   {
      super(ProfilerPlugin.getShell(), renderer);
      this.labelProvider = renderer;
      this.elements = configs.toArray();
      setTitle("Launch Configuration Selection");
      setMessage("Select an existing profiling launch configuration or " +
                 "create a new one:");
      setMultipleSelection(false);
   }

   protected void computeResult()
   {
      setResult(Arrays.asList(getSelectedElements()));
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite contents;

      contents = (Composite) super.createDialogArea(parent);
      createMessageArea(contents);
      filterText = createFilterText(contents);
      createFilteredList(contents);
      setListElements(elements);
      setSelection(getInitialElementSelections().toArray());

      return contents;
   }

   protected void createButtonsForButtonBar(Composite parent)
   {
      createButton(parent, IDialogConstants.OK_ID, "Use Selected", true);
      createButton(parent, CREATE_ID, "Create New", false);
      createButton(parent, IDialogConstants.CANCEL_ID,
            IDialogConstants.CANCEL_LABEL, false);
   }

   protected void buttonPressed(int buttonId)
   {
      super.buttonPressed(buttonId);
      if (buttonId == CREATE_ID)
      {
         setReturnCode(CREATE_ID);
         close();
      }
   }

   protected void handleEmptyList()
   {
      filterText.setEnabled(false);
      fFilteredList.setEnabled(false);
      updateOkState();
   }

   public boolean close()
   {
      boolean result = super.close();
      labelProvider.dispose();
      return result;
   }
}
