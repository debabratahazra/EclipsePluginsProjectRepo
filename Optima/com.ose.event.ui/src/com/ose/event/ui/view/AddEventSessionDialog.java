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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.event.ui.EventPlugin;
import com.ose.system.Block;
import com.ose.system.OseObject;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.Target;
import com.ose.system.ui.util.StringUtils;

public class AddEventSessionDialog extends Dialog
{
   private static final String SCOPE_SYSTEM_STR = "System";
   private static final String SCOPE_SEGMENT_STR = "Segment";
   private static final String SCOPE_BLOCK_STR = "Block";
   private static final String SCOPE_PROCESS_STR = "Process";

   private List targets;
   private String targetName;
   private String objectType;
   private String objectId;

   private Target target;
   private int scopeType;
   private int scopeId;
   private boolean persistentTraceEventActions;

   private Combo targetCombo;
   private Combo scopeTypeCombo;
   private Text scopeIdText;
   private Button persistentTraceEventActionsCheckbox;

   private CLabel errorMessageLabel;

   public AddEventSessionDialog(Shell parent, List targets)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
   }

   public AddEventSessionDialog(Shell parent,
                                List targets,
                                Target target,
                                OseObject object)
   {
      this(parent, targets);
      if (target != null)
      {
         this.targetName = target.toString();
      }
      if (object instanceof Segment)
      {
         objectType = SCOPE_SEGMENT_STR;
         objectId = StringUtils.toHexString(object.getId());
      }
      else if (object instanceof Block)
      {
         objectType = SCOPE_BLOCK_STR;
         objectId = StringUtils.toHexString(object.getId());
      }
      else if (object instanceof Process)
      {
         objectType = SCOPE_PROCESS_STR;
         objectId = StringUtils.toHexString(object.getId());
      }
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Target and Scope Selection");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      initContents();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subComp;
      Label label;
      GridData gd;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new GridLayout(2, false));

      // Create content composite.
      createContentComposite(subComp);

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      // Create error message label.
      errorMessageLabel = new CLabel(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorMessageLabel.setLayoutData(gd);

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void createContentComposite(Composite comp)
   {
      ValidationHandler validationHandler;
      TargetSelectionHandler targetSelectionHandler;
      ScopeTypeSelectionHandler scopeTypeSelectionHandler;
      GridData gd;
      Label label;

      validationHandler = new ValidationHandler();

      label = new Label(comp, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(comp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(180);
      targetCombo.setLayoutData(gd);
      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         Target target = (Target) i.next();
         targetCombo.add(target.toString());
      }
      targetSelectionHandler = new TargetSelectionHandler();
      targetCombo.addSelectionListener(targetSelectionHandler);
      targetCombo.addModifyListener(targetSelectionHandler);
      targetCombo.addModifyListener(validationHandler);

      label = new Label(comp, SWT.NONE);
      label.setText("Scope Type:");

      scopeTypeCombo = new Combo(comp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      scopeTypeCombo.setLayoutData(gd);
      populateScopeTypeCombo();
      scopeTypeSelectionHandler = new ScopeTypeSelectionHandler();
      scopeTypeCombo.addSelectionListener(scopeTypeSelectionHandler);
      scopeTypeCombo.addModifyListener(scopeTypeSelectionHandler);

      label = new Label(comp, SWT.NONE);
      label.setText("Scope ID:");

      scopeIdText = new Text(comp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      scopeIdText.setLayoutData(gd);
      scopeIdText.addModifyListener(validationHandler);

      // Create vertical spacer.
      label = new Label(comp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      persistentTraceEventActionsCheckbox = new Button(comp, SWT.CHECK);
      persistentTraceEventActionsCheckbox.setText("Persistent Event Trace Actions");
      gd = new GridData();
      gd.horizontalSpan = 2;
      persistentTraceEventActionsCheckbox.setLayoutData(gd);
   }

   private void populateScopeTypeCombo()
   {
      Target selectedTarget;

      selectedTarget = createTarget();
      scopeTypeCombo.removeAll();
      scopeTypeCombo.add(SCOPE_SYSTEM_STR);
      if (selectedTarget != null)
      {
         if (selectedTarget.hasSegmentSupport())
         {
            scopeTypeCombo.add(SCOPE_SEGMENT_STR);
         }
         if (selectedTarget.hasBlockSupport())
         {
            scopeTypeCombo.add(SCOPE_BLOCK_STR);
         }
         if (selectedTarget.hasProcessSupport())
         {
            scopeTypeCombo.add(SCOPE_PROCESS_STR);
         }
      }
      scopeTypeCombo.setVisibleItemCount(scopeTypeCombo.getItemCount());
      scopeTypeCombo.select(0);
   }

   private void initContents()
   {
      if ((targetName != null) && (objectType == null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         scopeTypeCombo.setText(SCOPE_SYSTEM_STR);
      }
      else if ((targetName != null) && (objectType != null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         scopeTypeCombo.setText(objectType);
         scopeIdText.setText(objectId);
      }
      else
      {
         loadDialogSettings();
      }

      // On Windows, selection event listeners are not called when a combobox
      // is programatically selected. Therefore, we have to call a combobox's
      // selection event handler explicitly.
      handleScopeTypeComboSelected();
   }

   private void loadDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = EventPlugin.getDefault().getDialogSettings();
      section = settings.getSection("AddEventSessionDialog");
      if (section == null)
      {
         clearDialog();
         return;
      }

      targetCombo.setText(section.get("target"));
      scopeTypeCombo.setText(section.get("scopeType"));
      scopeTypeCombo.select(scopeTypeCombo.getSelectionIndex());
      scopeIdText.setText(section.get("scopeId"));
      persistentTraceEventActionsCheckbox.setSelection(
            section.getBoolean("persistentTraceEventActions"));
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = EventPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("AddEventSessionDialog");

      section.put("target", targetCombo.getText());
      section.put("scopeType", scopeTypeCombo.getText());
      section.put("scopeId", scopeIdText.getText().trim());
      section.put("persistentTraceEventActions",
            persistentTraceEventActionsCheckbox.getSelection());
   }

   private void handleTargetComboSelected()
   {
      Target selectedTarget;
      boolean notPostMortemMonitor;

      selectedTarget = createTarget();
      notPostMortemMonitor =
         (selectedTarget == null) || !selectedTarget.isPostMortemMonitor();
      populateScopeTypeCombo();
      scopeTypeCombo.setEnabled(notPostMortemMonitor);
      scopeIdText.setEnabled(notPostMortemMonitor);
      persistentTraceEventActionsCheckbox.setEnabled(notPostMortemMonitor);
      if (!notPostMortemMonitor)
      {
         clearDialog();
      }
      handleScopeTypeComboSelected();
   }

   private void handleScopeTypeComboSelected()
   {
      String scopeType = scopeTypeCombo.getText();
      if (scopeType.equals(SCOPE_SYSTEM_STR))
      {
         scopeIdText.setText("");
      }
      scopeIdText.setEnabled(!scopeType.equals(SCOPE_SYSTEM_STR));
      validateDialog();
   }

   private void clearDialog()
   {
      scopeTypeCombo.select(0);
      scopeIdText.setText("");
      persistentTraceEventActionsCheckbox.setSelection(false);
   }

   private void validateDialog()
   {
      String errorMessage = null;

      if (targetCombo.getText().length() == 0)
      {
         errorMessage = "Target not specified";
      }
      else if (scopeTypeCombo.getText().length() == 0)
      {
         errorMessage = "Scope type not specified";
      }
      else if ((scopeIdText.getText().trim().length() == 0) &&
               !scopeTypeCombo.getText().equals(SCOPE_SYSTEM_STR))
      {
         errorMessage = "Scope ID not specified";
      }
      else
      {
         errorMessage = isValidU32(scopeIdText.getText(),
               "Invalid scope ID");
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

   protected void okPressed()
   {
      target = createTarget();
      scopeType = createScopeType();
      try
      {
         scopeId = createScopeId();
      }
      catch (NumberFormatException ignore) {}
      persistentTraceEventActions = persistentTraceEventActionsCheckbox.getSelection();
      saveDialogSettings();
      super.okPressed();
   }

   private Target createTarget()
   {
      Target result = null;
      int selection = targetCombo.getSelectionIndex();
      if (selection > -1)
      {
         result = (Target) targets.get(selection);
      }
      return result;
   }

   private int createScopeType()
   {
      int result;
      String str = scopeTypeCombo.getText();
      if (str.equals(SCOPE_SYSTEM_STR))
      {
         result = Target.SCOPE_SYSTEM;
      }
      else if (str.equals(SCOPE_SEGMENT_STR))
      {
         result = Target.SCOPE_SEGMENT_ID;
      }
      else if (str.equals(SCOPE_BLOCK_STR))
      {
         result = Target.SCOPE_BLOCK_ID;
      }
      else if (str.equals(SCOPE_PROCESS_STR))
      {
         result = Target.SCOPE_ID;
      }
      else
      {
         result = Target.SCOPE_SYSTEM;
      }
      return result;
   }

   private int createScopeId() throws NumberFormatException
   {
      String str = scopeIdText.getText().trim();
      int result = (str.length() > 0) ? StringUtils.parseU32(str) : 0;
      return result;
   }

   private static String isValidU32(String text, String errorMessage)
   {
      String msg = null;
      if (text.trim().length() > 0)
      {
         try
         {
            StringUtils.parseU32(text);
         }
         catch (NumberFormatException e)
         {
            msg = errorMessage;
         }
      }
      return msg;
   }

   public Target getTarget()
   {
      return target;
   }

   public int getScopeType()
   {
      return scopeType;
   }

   public int getScopeId()
   {
      return scopeId;
   }

   public boolean isPersistentTraceEventActions()
   {
      return persistentTraceEventActions;
   }

   private class TargetSelectionHandler
      extends SelectionAdapter implements ModifyListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleTargetComboSelected();
      }

      public void modifyText(ModifyEvent event)
      {
         handleTargetComboSelected();
      }
   }

   private class ScopeTypeSelectionHandler
      extends SelectionAdapter implements ModifyListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleScopeTypeComboSelected();
      }

      public void modifyText(ModifyEvent event)
      {
         handleScopeTypeComboSelected();
      }
   }

   private class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }
}
