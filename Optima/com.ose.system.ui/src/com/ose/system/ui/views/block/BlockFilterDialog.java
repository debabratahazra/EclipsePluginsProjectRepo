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

package com.ose.system.ui.views.block;

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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.Block;
import com.ose.system.BlockFilter;
import com.ose.system.Segment;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;

public class BlockFilterDialog extends Dialog
{
   private static final String EXCLUDE_STR = "Not";

   private static final String SCOPE_SYSTEM_STR = "System";
   private static final String SCOPE_SEGMENT_STR = "Segment";
   private static final String SCOPE_BLOCK_STR = "Block";

   private List targets;
   private String targetName;
   private String segmentId;
   private String blockId;

   private Target target;
   private int scopeType;
   private int scopeId;
   private BlockFilter blockFilter;

   private Combo targetCombo;
   private Combo scopeTypeCombo;
   private Text scopeIdText;

   private Text nameText;
   // TODO: Uncomment when supported.
   //private Combo supervisorCombo;
   private Text uidFromText;
   private Text uidToText;
   private Text maxSigSizeFromText;
   private Text maxSigSizeToText;
   private Text sigPoolIdText;
   private Text stackPoolIdText;
   private Text euFromText;
   private Text euToText;
   // TODO: Uncomment when supported.
   //private Text errorHandlerText;
   // TODO: Uncomment when supported.
   //private Text signalsAttachedFromText;
   //private Text signalsAttachedToText;

   private Button nameCheckbox;
   // TODO: Uncomment when supported.
   //private Button supervisorCheckbox;
   private Button uidCheckbox;
   private Button maxSigSizeCheckbox;
   private Button sigPoolIdCheckbox;
   private Button stackPoolIdCheckbox;
   private Button euCheckbox;
   // TODO: Uncomment when supported.
   //private Button errorHandlerCheckbox;
   // TODO: Uncomment when supported.
   //private Button signalsAttachedCheckbox;

   private CLabel errorMessageLabel;
   private Button clearButton;

   public BlockFilterDialog(Shell parent, List targets)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
   }

   public BlockFilterDialog(Shell parent,
                            List targets,
                            Target target,
                            Segment segment,
                            Block block)
   {
      this(parent, targets);
      if (target == null)
      {
         throw new NullPointerException();
      }
      this.targetName = target.toString();
      if (segment != null)
      {
         this.segmentId = StringUtils.toHexString(segment.getId());
      }
      if (block != null)
      {
         this.blockId = StringUtils.toHexString(block.getId());
      }
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Block Filter");
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
      ValidationHandler validationHandler;
      Label label;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new GridLayout(1, false));
      validationHandler = new ValidationHandler();

      // Create scope group.
      createScopeGroup(subComp, validationHandler);

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create properties group.
      createPropsGroup(subComp, validationHandler);

      // Create error message label.
      errorMessageLabel = new CLabel(subComp, SWT.NONE);
      errorMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void createScopeGroup(Composite comp,
                                 ValidationHandler validationHandler)
   {
      Group scopeGroup;
      GridData gd;
      Label label;
      TargetSelectionHandler targetSelectionHandler;
      ScopeTypeSelectionHandler scopeTypeSelectionHandler;

      scopeGroup = new Group(comp, SWT.SHADOW_NONE);
      scopeGroup.setText("Scope");
      scopeGroup.setLayout(new GridLayout(2, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      scopeGroup.setLayoutData(gd);

      label = new Label(scopeGroup, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(scopeGroup, SWT.READ_ONLY);
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

      label = new Label(scopeGroup, SWT.NONE);
      label.setText("Scope Type:");

      scopeTypeCombo = new Combo(scopeGroup, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      scopeTypeCombo.setLayoutData(gd);
      populateScopeTypeCombo();
      scopeTypeSelectionHandler = new ScopeTypeSelectionHandler();
      scopeTypeCombo.addSelectionListener(scopeTypeSelectionHandler);
      scopeTypeCombo.addModifyListener(scopeTypeSelectionHandler);

      label = new Label(scopeGroup, SWT.NONE);
      label.setText("Scope ID:");

      scopeIdText = new Text(scopeGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      scopeIdText.setLayoutData(gd);
      scopeIdText.addModifyListener(validationHandler);
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
      }
      scopeTypeCombo.setVisibleItemCount(scopeTypeCombo.getItemCount());
      scopeTypeCombo.select(0);
   }

   private void createPropsGroup(Composite comp,
                                 ValidationHandler validationHandler)
   {
      Group propsGroup;
      GridData gd;
      Label label;

      propsGroup = new Group(comp, SWT.SHADOW_NONE);
      propsGroup.setText("Properties");
      propsGroup.setLayout(new GridLayout(4, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      propsGroup.setLayoutData(gd);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Name:");

      nameText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      nameText.setLayoutData(gd);

      nameCheckbox = new Button(propsGroup, SWT.CHECK);
      nameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(nameText, nameCheckbox);

      /* TODO: Uncomment when supported.
      label = new Label(propsGroup, SWT.NONE);
      label.setText("Supervisor Mode:");

      supervisorCombo = new Combo(propsGroup, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      supervisorCombo.setLayoutData(gd);
      supervisorCombo.add("");
      supervisorCombo.add("Yes");
      supervisorCombo.add("No");

      supervisorCheckbox = new Button(propsGroup, SWT.CHECK);
      supervisorCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(supervisorCombo, supervisorCheckbox);
      */

      label = new Label(propsGroup, SWT.NONE);
      label.setText("User Number:");

      uidFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      uidFromText.setLayoutData(gd);
      uidFromText.addModifyListener(validationHandler);
      uidToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      uidToText.setLayoutData(gd);
      uidToText.addModifyListener(validationHandler);

      uidCheckbox = new Button(propsGroup, SWT.CHECK);
      uidCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(uidFromText, uidToText, uidCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Max Signal Size:");

      maxSigSizeFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      maxSigSizeFromText.setLayoutData(gd);
      maxSigSizeFromText.addModifyListener(validationHandler);
      maxSigSizeToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      maxSigSizeToText.setLayoutData(gd);
      maxSigSizeToText.addModifyListener(validationHandler);

      maxSigSizeCheckbox = new Button(propsGroup, SWT.CHECK);
      maxSigSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(maxSigSizeFromText, maxSigSizeToText, maxSigSizeCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Signal Pool ID:");

      sigPoolIdText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      sigPoolIdText.setLayoutData(gd);
      sigPoolIdText.addModifyListener(validationHandler);

      sigPoolIdCheckbox = new Button(propsGroup, SWT.CHECK);
      sigPoolIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(sigPoolIdText, sigPoolIdCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Stack Pool ID:");

      stackPoolIdText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      stackPoolIdText.setLayoutData(gd);
      stackPoolIdText.addModifyListener(validationHandler);

      stackPoolIdCheckbox = new Button(propsGroup, SWT.CHECK);
      stackPoolIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackPoolIdText, stackPoolIdCheckbox);

      label = new Label(propsGroup, SWT.NONE);
      label.setText("Execution Unit:");

      euFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      euFromText.setLayoutData(gd);
      euFromText.addModifyListener(validationHandler);
      euToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      euToText.setLayoutData(gd);
      euToText.addModifyListener(validationHandler);

      euCheckbox = new Button(propsGroup, SWT.CHECK);
      euCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(euFromText, euToText, euCheckbox);

      /*
      label = new Label(propsGroup, SWT.NONE);
      label.setText("Error Handler:");

      errorHandlerText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorHandlerText.setLayoutData(gd);
      errorHandlerText.addModifyListener(validationHandler);

      errorHandlerCheckbox = new Button(propsGroup, SWT.CHECK);
      errorHandlerCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(errorHandlerText, errorHandlerCheckbox);
      */

      /* TODO: Uncomment when supported.
      label = new Label(propsGroup, SWT.NONE);
      label.setText("Attached Signals:");

      signalsAttachedFromText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsAttachedFromText.setLayoutData(gd);
      signalsAttachedFromText.addModifyListener(validationHandler);
      signalsAttachedToText = new Text(propsGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsAttachedToText.setLayoutData(gd);
      signalsAttachedToText.addModifyListener(validationHandler);

      signalsAttachedCheckbox = new Button(propsGroup, SWT.CHECK);
      signalsAttachedCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalsAttachedFromText, signalsAttachedToText,
            signalsAttachedCheckbox);
      */
   }

   protected void createButtonsForButtonBar(Composite parent)
   {
      Label spacer;
      GridLayout layout;

      parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      clearButton =
         createButton(parent, IDialogConstants.CLIENT_ID + 1, "Clear", false);
      clearButton.addSelectionListener(new ClearHandler());

      spacer = new Label(parent, SWT.NONE);
      spacer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      layout = (GridLayout) parent.getLayout();
      layout.numColumns++;
      layout.makeColumnsEqualWidth = false;

      super.createButtonsForButtonBar(parent);
   }

   private void initContents()
   {
      if ((targetName != null) && (segmentId == null) && (blockId == null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         scopeTypeCombo.setText(SCOPE_SYSTEM_STR);
      }
      else if ((targetName != null) && (segmentId != null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         scopeTypeCombo.setText(SCOPE_SEGMENT_STR);
         scopeIdText.setText(segmentId);
      }
      else if ((targetName != null) && (blockId != null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         scopeTypeCombo.setText(SCOPE_BLOCK_STR);
         scopeIdText.setText(blockId);
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

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.getSection("BlockFilterDialog");
      if (section == null)
      {
         clearDialog();
         return;
      }

      targetCombo.setText(section.get("target"));
      scopeTypeCombo.setText(section.get("scopeType"));
      scopeTypeCombo.select(scopeTypeCombo.getSelectionIndex());
      scopeIdText.setText(section.get("scopeId"));

      nameText.setText(section.get("name"));
      // TODO: Uncomment when supported.
      //supervisorCombo.setText(section.get("supervisor"));
      uidFromText.setText(section.get("uidFrom"));
      uidToText.setText(section.get("uidTo"));
      maxSigSizeFromText.setText(section.get("maxSigSizeFrom"));
      maxSigSizeToText.setText(section.get("maxSigSizeTo"));
      sigPoolIdText.setText(section.get("sigPoolId"));
      stackPoolIdText.setText(section.get("stackPoolId"));
      euFromText.setText(section.get("euFrom"));
      euToText.setText(section.get("euTo"));
      // TODO: Uncomment when supported.
      //errorHandlerText.setText(section.get("errorHandler"));
      // TODO: Uncomment when supported.
      //signalsAttachedFromText.setText(section.get("signalsAttachedFrom"));
      //signalsAttachedToText.setText(section.get("signalsAttachedTo"));

      nameCheckbox.setSelection(section.getBoolean("nameExclude"));
      // TODO: Uncomment when supported.
      //supervisorCheckbox.setSelection(section.getBoolean("supervisorExclude"));
      uidCheckbox.setSelection(section.getBoolean("uidExclude"));
      maxSigSizeCheckbox.setSelection(section.getBoolean("maxSigSizeExclude"));
      sigPoolIdCheckbox.setSelection(section.getBoolean("sigPoolIdExclude"));
      stackPoolIdCheckbox.setSelection(section.getBoolean("stackPoolIdExclude"));
      euCheckbox.setSelection(section.getBoolean("euExclude"));
      // TODO: Uncomment when supported.
      //errorHandlerCheckbox.setSelection(section.getBoolean("errorHandlerExclude"));
      // TODO: Uncomment when supported.
      //signalsAttachedCheckbox.setSelection(section.getBoolean("signalsAttachedExclude"));
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("BlockFilterDialog");

      section.put("target", targetCombo.getText());
      section.put("scopeType", scopeTypeCombo.getText());
      section.put("scopeId", scopeIdText.getText().trim());

      section.put("name", nameText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("supervisor", supervisorCombo.getText());
      section.put("uidFrom", uidFromText.getText().trim());
      section.put("uidTo", uidToText.getText().trim());
      section.put("maxSigSizeFrom", maxSigSizeFromText.getText().trim());
      section.put("maxSigSizeTo", maxSigSizeToText.getText().trim());
      section.put("sigPoolId", sigPoolIdText.getText().trim());
      section.put("stackPoolId", stackPoolIdText.getText().trim());
      section.put("euFrom", euFromText.getText().trim());
      section.put("euTo", euToText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("errorHandler", errorHandlerText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("signalsAttachedFrom", signalsAttachedFromText.getText().trim());
      //section.put("signalsAttachedTo", signalsAttachedToText.getText().trim());

      section.put("nameExclude", nameCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("supervisorExclude", supervisorCheckbox.getSelection());
      section.put("uidExclude", uidCheckbox.getSelection());
      section.put("maxSigSizeExclude", maxSigSizeCheckbox.getSelection());      
      section.put("sigPoolIdExclude", sigPoolIdCheckbox.getSelection());
      section.put("stackPoolIdExclude", stackPoolIdCheckbox.getSelection());
      section.put("euExclude", euCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("errorHandlerExclude", errorHandlerCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("signalsAttachedExclude", signalsAttachedCheckbox.getSelection());
   }

   private void handleTargetComboSelected()
   {
      populateScopeTypeCombo();
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

      nameText.setText("");
      // TODO: Uncomment when supported.
      //supervisorCombo.select(0);
      uidFromText.setText("");
      uidToText.setText("");
      maxSigSizeFromText.setText("");
      maxSigSizeToText.setText("");
      sigPoolIdText.setText("");
      stackPoolIdText.setText("");
      euFromText.setText("");
      euToText.setText("");
      // TODO: Uncomment when supported.
      //errorHandlerText.setText("");
      // TODO: Uncomment when supported.
      //signalsAttachedFromText.setText("");
      //signalsAttachedToText.setText("");

      nameCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //supervisorCheckbox.setSelection(false);
      uidCheckbox.setSelection(false);
      maxSigSizeCheckbox.setSelection(false);
      sigPoolIdCheckbox.setSelection(false);
      stackPoolIdCheckbox.setSelection(false);
      euCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //errorHandlerCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //signalsAttachedCheckbox.setSelection(false);
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
      if (errorMessage == null)
      {
         errorMessage = isValidU32(uidFromText.getText(),
               "Invalid user number min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(uidToText.getText(),
               "Invalid user number max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(maxSigSizeFromText.getText(),
               "Invalid max signal size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(maxSigSizeToText.getText(),
               "Invalid max signal size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(sigPoolIdText.getText(),
               "Invalid signal pool ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackPoolIdText.getText(),
               "Invalid stack pool ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(euFromText.getText(),
               "Invalid execution unit min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(euToText.getText(),
               "Invalid execution unit max value");
      }
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(errorHandlerText.getText(),
               "Invalid error handler address");
      }
      */
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalsAttachedFromText.getText(),
               "Invalid attached signals min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalsAttachedToText.getText(),
               "Invalid attached signals max value");
      }
      */
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
         blockFilter = createBlockFilter();
      }
      catch (NumberFormatException ignore) {}
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

   private BlockFilter createBlockFilter() throws NumberFormatException
   {
      BlockFilter filter;
      boolean exclude;
      String str;
      String strFrom;
      String strTo;
      int num;
      int from;
      int to;

      filter = new BlockFilter();

      str = nameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = nameCheckbox.getSelection();
         filter.filterName(exclude, str);
      }

      /* TODO: Uncomment when supported.
      str = supervisorCombo.getText();
      if (str.length() > 0)
      {
         exclude = supervisorCheckbox.getSelection();
         num = str.equals("Yes") ? 1 : 0;
         filter.filterSupervisor(exclude, num);
      }
      */

      strFrom = uidFromText.getText().trim();
      strTo = uidToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = uidCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterUid(exclude, from, to);
      }

      strFrom = maxSigSizeFromText.getText().trim();
      strTo = maxSigSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = maxSigSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterMaxSigSize(exclude, from, to);
      }

      str = sigPoolIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = sigPoolIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterSigPoolId(exclude, num);
      }

      str = stackPoolIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = stackPoolIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterStackPoolId(exclude, num);
      }

      strFrom = euFromText.getText().trim();
      strTo = euToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = euCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterExecutionUnit(exclude, from, to);
      }

      /*
      str = errorHandlerText.getText().trim();
      if (str.length() > 0)
      {
         exclude = errorHandlerCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterErrorHandler(exclude, num);
      }
      */

      /*
      strFrom = signalsAttachedFromText.getText().trim();
      strTo = signalsAttachedToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalsAttachedCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSignalsAttached(exclude, from, to);
      }
      */

      return filter;
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

   public BlockFilter getBlockFilter()
   {
      return blockFilter;
   }

   class TargetSelectionHandler extends SelectionAdapter implements ModifyListener
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

   class ScopeTypeSelectionHandler extends SelectionAdapter implements ModifyListener
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

   class CheckboxHandler implements ModifyListener
   {
      private Control control1;
      private Control control2;
      private Button checkbox;

      CheckboxHandler(Control control, Button checkbox)
      {
         this(control, null, checkbox);
      }

      CheckboxHandler(Control control1, Control control2, Button checkbox)
      {
         if (control1 instanceof Text)
         {
            ((Text) control1).addModifyListener(this);
         }
         else if (control1 instanceof Combo)
         {
            ((Combo) control1).addModifyListener(this);
         }
         else
         {
            throw new IllegalArgumentException();
         }

         if (control2 instanceof Text)
         {
            ((Text) control2).addModifyListener(this);
         }
         else if (control2 instanceof Combo)
         {
            ((Combo) control2).addModifyListener(this);
         }
         else if (control2 != null)
         {
            throw new IllegalArgumentException();
         }

         this.control1 = control1;
         this.control2 = control2;
         this.checkbox = checkbox;
      }

      public void modifyText(ModifyEvent event)
      {
         boolean filledIn = isFilledIn();
         if (!filledIn)
         {
            checkbox.setSelection(false);
         }
         checkbox.setEnabled(filledIn);
      }

      private boolean isFilledIn()
      {
         String text1 = "";
         String text2 = "";

         if (control1 instanceof Text)
         {
            text1 = ((Text) control1).getText();
         }
         else if (control1 instanceof Combo)
         {
            text1 = ((Combo) control1).getText();
         }

         if (control2 instanceof Text)
         {
            text2 = ((Text) control2).getText();
         }
         else if (control2 instanceof Combo)
         {
            text2 = ((Combo) control2).getText();
         }

         if (control2 == null)
         {
            return (text1.length() > 0);
         }
         else
         {
            return ((text1.length() > 0) || (text2.length() > 0));
         }
      }
   }

   class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }

   class ClearHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         clearDialog();
         saveDialogSettings();
      }
   }
}
