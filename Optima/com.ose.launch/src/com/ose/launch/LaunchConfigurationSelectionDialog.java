/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.launch;

import java.util.Arrays;
import java.util.List;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.AbstractElementListSelectionDialog;
import com.ose.launch.ui.LaunchUIPlugin;

public class LaunchConfigurationSelectionDialog
   extends AbstractElementListSelectionDialog
{
   public static final int CREATE_ID = IDialogConstants.CLIENT_ID + 1;

   // XXX: The following constants are copied from
   // com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants since the
   // com.ose.launch plugin is not allowed to depend on CDT. We have to manually
   // ensure that these two sets of constants are identical.
   public static final String VALUE_DEBUG_SCOPE_SEGMENT = "Segment";
   public static final String VALUE_DEBUG_SCOPE_BLOCK = "Block";
   public static final String VALUE_DEBUG_SCOPE_PROCESS = "Process";

   private static final String[] DEBUG_SCOPES =
   {
      VALUE_DEBUG_SCOPE_SEGMENT,
      VALUE_DEBUG_SCOPE_BLOCK,
      VALUE_DEBUG_SCOPE_PROCESS
   };

   private final String launchMode;
   private final IDebugModelPresentation labelProvider;
   private final Object[] elements;
   private String debugScope;
   private Text filterText;
   private Combo scopeCombo;

   public LaunchConfigurationSelectionDialog(
         IDebugModelPresentation renderer, List configs, String launchMode)
   {
      super(LaunchUIPlugin.getShell(), renderer);
      this.launchMode = launchMode;
      this.labelProvider = renderer;
      this.elements = configs.toArray();
      setTitle("Launch Configuration Selection");
      if (launchMode.equals(ILaunchManager.RUN_MODE))
      {
         setMessage("Select an existing run launch configuration or " +
                    "create a new one:");
      }
      else
      {
         setMessage("Select an existing debug launch configuration or " +
                    "create a new one:");
      }
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

      if (launchMode.equals(ILaunchManager.DEBUG_MODE))
      {
         Composite scopeComp;
         Label scopeLabel;

         scopeComp = new Composite(contents, SWT.NONE);
         scopeComp.setLayout(new GridLayout(2, false));
         scopeComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

         scopeLabel = new Label(scopeComp, SWT.NONE);
         scopeLabel.setText("Debug scope:");

         scopeCombo = new Combo(scopeComp, SWT.DROP_DOWN | SWT.READ_ONLY);
         scopeCombo.setItems(DEBUG_SCOPES);
         scopeCombo.setVisibleItemCount(scopeCombo.getItemCount());
         scopeCombo.setText(VALUE_DEBUG_SCOPE_PROCESS);
         scopeCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      }

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
      if (launchMode.equals(ILaunchManager.DEBUG_MODE))
      {
         debugScope = scopeCombo.getText();
      }
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

   public String getDebugScope()
   {
      return (((debugScope != null) && (debugScope.length() > 0)) ? debugScope
              : VALUE_DEBUG_SCOPE_PROCESS);
   }

   public boolean close()
   {
      boolean result = super.close();
      labelProvider.dispose();
      return result;
   }
}
