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

package com.ose.system.ui.views.pool;

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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.Pool;
import com.ose.system.SignalFilter;
import com.ose.system.SignalInfo;
import com.ose.system.StackFilter;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;

public class PoolFilterDialog extends Dialog
{
   private static final String EXCLUDE_STR = "Not";

   private static final String SIGNAL_STATUS_UNKNOWN_STR = "Unknown";
   private static final String SIGNAL_STATUS_VALID_STR = "Valid";
   private static final String SIGNAL_STATUS_ENDMARK_STR = "Broken Endmark";
   private static final String SIGNAL_STATUS_ADMINISTRATION_STR = "Broken Admin Block";
   private static final String SIGNAL_STATUS_NOT_A_SIGNAL_STR = "Not a Signal";
   private static final String SIGNAL_STATUS_NOT_A_POOL_STR = "Not in a Pool";
   private static final String SIGNAL_STATUS_FREE_STR = "Free";

   private List targets;
   private String targetName;
   private String poolName;

   private Target target;
   private int poolId;
   private SignalFilter signalFilter;
   private StackFilter stackFilter;

   private Combo targetCombo;
   private Text poolIdText;
   private TabFolder tabFolder;

   private Text signalNumberFromText;
   private Text signalNumberToText;
   private Text signalOwnerIdText;
   private Text signalOwnerNameText;
   private Text signalSenderIdText;
   private Text signalSenderNameText;
   private Text signalAddresseeIdText;
   private Text signalAddresseeNameText;
   private Text signalSizeFromText;
   private Text signalSizeToText;
   private Text signalBufferSizeFromText;
   private Text signalBufferSizeToText;
   private Text signalAddressFromText;
   private Text signalAddressToText;
   private Combo signalStatusCombo;

   private Button signalNumberCheckbox;
   private Button signalOwnerIdCheckbox;
   private Button signalOwnerNameCheckbox;
   private Button signalSenderIdCheckbox;
   private Button signalSenderNameCheckbox;
   private Button signalAddresseeIdCheckbox;
   private Button signalAddresseeNameCheckbox;
   private Button signalSizeCheckbox;
   private Button signalBufferSizeCheckbox;
   private Button signalAddressCheckbox;
   private Button signalStatusCheckbox;

   private Text stackOwnerIdText;
   private Text stackOwnerNameText;
   private Text stackSizeFromText;
   private Text stackSizeToText;
   private Text stackBufferSizeFromText;
   private Text stackBufferSizeToText;
   private Text stackAddressFromText;
   private Text stackAddressToText;
   private Text stackUsedFromText;
   private Text stackUsedToText;
   private Text stackUnusedFromText;
   private Text stackUnusedToText;

   private Button stackOwnerIdCheckbox;
   private Button stackOwnerNameCheckbox;
   private Button stackSizeCheckbox;
   private Button stackBufferSizeCheckbox;
   private Button stackAddressCheckbox;
   private Button stackUsedCheckbox;
   private Button stackUnusedCheckbox;

   private CLabel errorMessageLabel;
   private Button clearButton;

   public PoolFilterDialog(Shell parent, List targets)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
   }

   public PoolFilterDialog(Shell parent, List targets, Pool pool)
   {
      this(parent, targets);
      if (pool == null)
      {
         throw new NullPointerException();
      }
      this.targetName = pool.getTarget().toString();
      this.poolName = StringUtils.toHexString(pool.getId());
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Pool Filter");
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

      // Create pool group.
      createPoolGroup(subComp, validationHandler);

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create tab folder.
      tabFolder = new TabFolder(subComp, SWT.NONE);
      tabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      createSignalTab(tabFolder, validationHandler);
      createStackTab(tabFolder, validationHandler);

      // Create error message label.
      errorMessageLabel = new CLabel(subComp, SWT.NONE);
      errorMessageLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void createPoolGroup(Composite comp,
                                ValidationHandler validationHandler)
   {
      Group poolGroup;
      GridData gd;
      Label label;

      poolGroup = new Group(comp, SWT.SHADOW_NONE);
      poolGroup.setText("Pool");
      poolGroup.setLayout(new GridLayout(2, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      poolGroup.setLayoutData(gd);

      label = new Label(poolGroup, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(poolGroup, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(180);
      targetCombo.setLayoutData(gd);
      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         Target target = (Target) i.next();
         targetCombo.add(target.toString());
      }
      targetCombo.addModifyListener(validationHandler);

      label = new Label(poolGroup, SWT.NONE);
      label.setText("Pool ID:");

      poolIdText = new Text(poolGroup, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      poolIdText.setLayoutData(gd);
      poolIdText.addModifyListener(validationHandler);
   }

   private void createSignalTab(TabFolder tabFolder,
                                ValidationHandler validationHandler)
   {
      Composite signalComp;
      TabItem signalTabItem;
      GridData gd;
      Label label;

      signalComp = new Composite(tabFolder, SWT.NONE);
      signalComp.setLayout(new GridLayout(4, false));
      signalTabItem = new TabItem(tabFolder, SWT.NONE);
      signalTabItem.setText("Signal Properties");
      signalTabItem.setControl(signalComp);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Signal Number:");

      signalNumberFromText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalNumberFromText.setLayoutData(gd);
      signalNumberFromText.addModifyListener(validationHandler);
      signalNumberToText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalNumberToText.setLayoutData(gd);
      signalNumberToText.addModifyListener(validationHandler);

      signalNumberCheckbox = new Button(signalComp, SWT.CHECK);
      signalNumberCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalNumberFromText,
                          signalNumberToText,
                          signalNumberCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Owner ID:");

      signalOwnerIdText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalOwnerIdText.setLayoutData(gd);
      signalOwnerIdText.addModifyListener(validationHandler);

      signalOwnerIdCheckbox = new Button(signalComp, SWT.CHECK);
      signalOwnerIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalOwnerIdText, signalOwnerIdCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Owner Name:");

      signalOwnerNameText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalOwnerNameText.setLayoutData(gd);
      signalOwnerNameText.addModifyListener(validationHandler);

      signalOwnerNameCheckbox = new Button(signalComp, SWT.CHECK);
      signalOwnerNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalOwnerNameText, signalOwnerNameCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Sender ID:");

      signalSenderIdText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalSenderIdText.setLayoutData(gd);
      signalSenderIdText.addModifyListener(validationHandler);

      signalSenderIdCheckbox = new Button(signalComp, SWT.CHECK);
      signalSenderIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalSenderIdText, signalSenderIdCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Sender Name:");

      signalSenderNameText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalSenderNameText.setLayoutData(gd);
      signalSenderNameText.addModifyListener(validationHandler);

      signalSenderNameCheckbox = new Button(signalComp, SWT.CHECK);
      signalSenderNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalSenderNameText, signalSenderNameCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Addressee ID:");

      signalAddresseeIdText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalAddresseeIdText.setLayoutData(gd);
      signalAddresseeIdText.addModifyListener(validationHandler);

      signalAddresseeIdCheckbox = new Button(signalComp, SWT.CHECK);
      signalAddresseeIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalAddresseeIdText, signalAddresseeIdCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Addressee Name:");

      signalAddresseeNameText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalAddresseeNameText.setLayoutData(gd);
      signalAddresseeNameText.addModifyListener(validationHandler);

      signalAddresseeNameCheckbox = new Button(signalComp, SWT.CHECK);
      signalAddresseeNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalAddresseeNameText, signalAddresseeNameCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Size:");

      signalSizeFromText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalSizeFromText.setLayoutData(gd);
      signalSizeFromText.addModifyListener(validationHandler);
      signalSizeToText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalSizeToText.setLayoutData(gd);
      signalSizeToText.addModifyListener(validationHandler);

      signalSizeCheckbox = new Button(signalComp, SWT.CHECK);
      signalSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalSizeFromText,
                          signalSizeToText,
                          signalSizeCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Buffer Size:");

      signalBufferSizeFromText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalBufferSizeFromText.setLayoutData(gd);
      signalBufferSizeFromText.addModifyListener(validationHandler);
      signalBufferSizeToText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalBufferSizeToText.setLayoutData(gd);
      signalBufferSizeToText.addModifyListener(validationHandler);

      signalBufferSizeCheckbox = new Button(signalComp, SWT.CHECK);
      signalBufferSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalBufferSizeFromText,
                          signalBufferSizeToText,
                          signalBufferSizeCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Address:");

      signalAddressFromText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalAddressFromText.setLayoutData(gd);
      signalAddressFromText.addModifyListener(validationHandler);
      signalAddressToText = new Text(signalComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalAddressToText.setLayoutData(gd);
      signalAddressToText.addModifyListener(validationHandler);

      signalAddressCheckbox = new Button(signalComp, SWT.CHECK);
      signalAddressCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalAddressFromText,
                          signalAddressToText,
                          signalAddressCheckbox);

      label = new Label(signalComp, SWT.NONE);
      label.setText("Status:");

      signalStatusCombo = new Combo(signalComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      signalStatusCombo.setLayoutData(gd);
      signalStatusCombo.add("");
      signalStatusCombo.add(SIGNAL_STATUS_VALID_STR);
      signalStatusCombo.add(SIGNAL_STATUS_ENDMARK_STR);
      signalStatusCombo.add(SIGNAL_STATUS_ADMINISTRATION_STR);
      signalStatusCombo.add(SIGNAL_STATUS_NOT_A_SIGNAL_STR);
      signalStatusCombo.add(SIGNAL_STATUS_NOT_A_POOL_STR);
      signalStatusCombo.add(SIGNAL_STATUS_FREE_STR);
      signalStatusCombo.add(SIGNAL_STATUS_UNKNOWN_STR);
      signalStatusCombo.setVisibleItemCount(signalStatusCombo.getItemCount());

      signalStatusCheckbox = new Button(signalComp, SWT.CHECK);
      signalStatusCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalStatusCombo, signalStatusCheckbox);
   }

   private void createStackTab(TabFolder tabFolder,
                               ValidationHandler validationHandler)
   {
      Composite stackComp;
      TabItem stackTabItem;
      GridData gd;
      Label label;

      stackComp = new Composite(tabFolder, SWT.NONE);
      stackComp.setLayout(new GridLayout(4, false));
      stackTabItem = new TabItem(tabFolder, SWT.NONE);
      stackTabItem.setText("Stack Properties");
      stackTabItem.setControl(stackComp);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Owner ID:");

      stackOwnerIdText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      stackOwnerIdText.setLayoutData(gd);
      stackOwnerIdText.addModifyListener(validationHandler);

      stackOwnerIdCheckbox = new Button(stackComp, SWT.CHECK);
      stackOwnerIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackOwnerIdText, stackOwnerIdCheckbox);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Owner Name:");

      stackOwnerNameText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      stackOwnerNameText.setLayoutData(gd);
      stackOwnerNameText.addModifyListener(validationHandler);

      stackOwnerNameCheckbox = new Button(stackComp, SWT.CHECK);
      stackOwnerNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackOwnerNameText, stackOwnerNameCheckbox);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Size:");

      stackSizeFromText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackSizeFromText.setLayoutData(gd);
      stackSizeFromText.addModifyListener(validationHandler);
      stackSizeToText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackSizeToText.setLayoutData(gd);
      stackSizeToText.addModifyListener(validationHandler);

      stackSizeCheckbox = new Button(stackComp, SWT.CHECK);
      stackSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackSizeFromText, stackSizeToText, stackSizeCheckbox);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Buffer Size:");

      stackBufferSizeFromText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackBufferSizeFromText.setLayoutData(gd);
      stackBufferSizeFromText.addModifyListener(validationHandler);
      stackBufferSizeToText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackBufferSizeToText.setLayoutData(gd);
      stackBufferSizeToText.addModifyListener(validationHandler);

      stackBufferSizeCheckbox = new Button(stackComp, SWT.CHECK);
      stackBufferSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackBufferSizeFromText,
                          stackBufferSizeToText,
                          stackBufferSizeCheckbox);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Address:");

      stackAddressFromText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackAddressFromText.setLayoutData(gd);
      stackAddressFromText.addModifyListener(validationHandler);
      stackAddressToText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackAddressToText.setLayoutData(gd);
      stackAddressToText.addModifyListener(validationHandler);

      stackAddressCheckbox = new Button(stackComp, SWT.CHECK);
      stackAddressCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackAddressFromText,
                          stackAddressToText,
                          stackAddressCheckbox);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Used:");

      stackUsedFromText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackUsedFromText.setLayoutData(gd);
      stackUsedFromText.addModifyListener(validationHandler);
      stackUsedToText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackUsedToText.setLayoutData(gd);
      stackUsedToText.addModifyListener(validationHandler);

      stackUsedCheckbox = new Button(stackComp, SWT.CHECK);
      stackUsedCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackUsedFromText, stackUsedToText, stackUsedCheckbox);

      label = new Label(stackComp, SWT.NONE);
      label.setText("Unused:");

      stackUnusedFromText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackUnusedFromText.setLayoutData(gd);
      stackUnusedFromText.addModifyListener(validationHandler);
      stackUnusedToText = new Text(stackComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackUnusedToText.setLayoutData(gd);
      stackUnusedToText.addModifyListener(validationHandler);

      stackUnusedCheckbox = new Button(stackComp, SWT.CHECK);
      stackUnusedCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackUnusedFromText,
                          stackUnusedToText,
                          stackUnusedCheckbox);
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
      if ((targetName != null) && (poolName == null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         poolIdText.setText("");
      }
      else if ((targetName != null) && (poolName != null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         poolIdText.setText(poolName);
      }
      else
      {
         loadDialogSettings();
      }
   }

   private void loadDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.getSection("PoolFilterDialog");
      if (section == null)
      {
         clearDialog();
         return;
      }

      targetCombo.setText(section.get("target"));
      poolIdText.setText(section.get("poolId"));
      tabFolder.setSelection(section.getInt("tab"));

      signalNumberFromText.setText(section.get("signalNumberFrom"));
      signalNumberToText.setText(section.get("signalNumberTo"));
      signalOwnerIdText.setText(section.get("signalOwnerId"));
      signalOwnerNameText.setText(section.get("signalOwnerName"));
      signalSenderIdText.setText(section.get("signalSenderId"));
      signalSenderNameText.setText(section.get("signalSenderName"));
      signalAddresseeIdText.setText(section.get("signalAddresseeId"));
      signalAddresseeNameText.setText(section.get("signalAddresseeName"));
      signalSizeFromText.setText(section.get("signalSizeFrom"));
      signalSizeToText.setText(section.get("signalSizeTo"));
      signalBufferSizeFromText.setText(section.get("signalBufferSizeFrom"));
      signalBufferSizeToText.setText(section.get("signalBufferSizeTo"));
      signalAddressFromText.setText(section.get("signalAddressFrom"));
      signalAddressToText.setText(section.get("signalAddressTo"));
      signalStatusCombo.setText(section.get("signalStatus"));

      signalNumberCheckbox.setSelection(section.getBoolean("signalNumberExclude"));
      signalOwnerIdCheckbox.setSelection(section.getBoolean("signalOwnerIdExclude"));
      signalOwnerNameCheckbox.setSelection(section.getBoolean("signalOwnerNameExclude"));
      signalSenderIdCheckbox.setSelection(section.getBoolean("signalSenderIdExclude"));
      signalSenderNameCheckbox.setSelection(section.getBoolean("signalSenderNameExclude"));
      signalAddresseeIdCheckbox.setSelection(section.getBoolean("signalAddresseeIdExclude"));
      signalAddresseeNameCheckbox.setSelection(section.getBoolean("signalAddresseeNameExclude"));
      signalSizeCheckbox.setSelection(section.getBoolean("signalSizeExclude"));
      signalBufferSizeCheckbox.setSelection(section.getBoolean("signalBufferSizeExclude"));
      signalAddressCheckbox.setSelection(section.getBoolean("signalAddressExclude"));
      signalStatusCheckbox.setSelection(section.getBoolean("signalStatusExclude"));

      stackOwnerIdText.setText(section.get("stackOwnerId"));
      stackOwnerNameText.setText(section.get("stackOwnerName"));
      stackSizeFromText.setText(section.get("stackSizeFrom"));
      stackSizeToText.setText(section.get("stackSizeTo"));
      stackBufferSizeFromText.setText(section.get("stackBufferSizeFrom"));
      stackBufferSizeToText.setText(section.get("stackBufferSizeTo"));
      stackAddressFromText.setText(section.get("stackAddressFrom"));
      stackAddressToText.setText(section.get("stackAddressTo"));
      stackUsedFromText.setText(section.get("stackUsedFrom"));
      stackUsedToText.setText(section.get("stackUsedTo"));
      stackUnusedFromText.setText(section.get("stackUnusedFrom"));
      stackUnusedToText.setText(section.get("stackUnusedTo"));

      stackOwnerIdCheckbox.setSelection(section.getBoolean("stackOwnerIdExclude"));
      stackOwnerNameCheckbox.setSelection(section.getBoolean("stackOwnerNameExclude"));
      stackSizeCheckbox.setSelection(section.getBoolean("stackSizeExclude"));
      stackBufferSizeCheckbox.setSelection(section.getBoolean("stackBufferSizeExclude"));
      stackAddressCheckbox.setSelection(section.getBoolean("stackAddressExclude"));
      stackUsedCheckbox.setSelection(section.getBoolean("stackUsedExclude"));
      stackUnusedCheckbox.setSelection(section.getBoolean("stackUnusedExclude"));
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("PoolFilterDialog");

      section.put("target", targetCombo.getText());
      section.put("poolId", poolIdText.getText().trim());
      section.put("tab", tabFolder.getSelectionIndex());

      section.put("signalNumberFrom", signalNumberFromText.getText().trim());
      section.put("signalNumberTo", signalNumberToText.getText().trim());
      section.put("signalOwnerId", signalOwnerIdText.getText().trim());
      section.put("signalOwnerName", signalOwnerNameText.getText().trim());
      section.put("signalSenderId", signalSenderIdText.getText().trim());
      section.put("signalSenderName", signalSenderNameText.getText().trim());
      section.put("signalAddresseeId", signalAddresseeIdText.getText().trim());
      section.put("signalAddresseeName", signalAddresseeNameText.getText().trim());
      section.put("signalSizeFrom", signalSizeFromText.getText().trim());
      section.put("signalSizeTo", signalSizeToText.getText().trim());
      section.put("signalBufferSizeFrom", signalBufferSizeFromText.getText().trim());
      section.put("signalBufferSizeTo", signalBufferSizeToText.getText().trim());
      section.put("signalAddressFrom", signalAddressFromText.getText().trim());
      section.put("signalAddressTo", signalAddressToText.getText().trim());
      section.put("signalStatus", signalStatusCombo.getText());

      section.put("signalNumberExclude", signalNumberCheckbox.getSelection());
      section.put("signalOwnerIdExclude", signalOwnerIdCheckbox.getSelection());
      section.put("signalOwnerNameExclude", signalOwnerNameCheckbox.getSelection());
      section.put("signalSenderIdExclude", signalSenderIdCheckbox.getSelection());
      section.put("signalSenderNameExclude", signalSenderNameCheckbox.getSelection());
      section.put("signalAddresseeIdExclude", signalAddresseeIdCheckbox.getSelection());
      section.put("signalAddresseeNameExclude", signalAddresseeNameCheckbox.getSelection());
      section.put("signalSizeExclude", signalSizeCheckbox.getSelection());
      section.put("signalBufferSizeExclude", signalBufferSizeCheckbox.getSelection());
      section.put("signalAddressExclude", signalAddressCheckbox.getSelection());
      section.put("signalStatusExclude", signalStatusCheckbox.getSelection());

      section.put("stackOwnerId", stackOwnerIdText.getText().trim());
      section.put("stackOwnerName", stackOwnerNameText.getText().trim());
      section.put("stackSizeFrom", stackSizeFromText.getText().trim());
      section.put("stackSizeTo", stackSizeToText.getText().trim());
      section.put("stackBufferSizeFrom", stackBufferSizeFromText.getText().trim());
      section.put("stackBufferSizeTo", stackBufferSizeToText.getText().trim());
      section.put("stackAddressFrom", stackAddressFromText.getText().trim());
      section.put("stackAddressTo", stackAddressToText.getText().trim());
      section.put("stackUsedFrom", stackUsedFromText.getText().trim());
      section.put("stackUsedTo", stackUsedToText.getText().trim());
      section.put("stackUnusedFrom", stackUnusedFromText.getText().trim());
      section.put("stackUnusedTo", stackUnusedToText.getText().trim());

      section.put("stackOwnerIdExclude", stackOwnerIdCheckbox.getSelection());
      section.put("stackOwnerNameExclude", stackOwnerNameCheckbox.getSelection());
      section.put("stackSizeExclude", stackSizeCheckbox.getSelection());
      section.put("stackBufferSizeExclude", stackBufferSizeCheckbox.getSelection());
      section.put("stackAddressExclude", stackAddressCheckbox.getSelection());
      section.put("stackUsedExclude", stackUsedCheckbox.getSelection());
      section.put("stackUnusedExclude", stackUnusedCheckbox.getSelection());
   }

   private void clearDialog()
   {
      poolIdText.setText("");

      signalNumberFromText.setText("");
      signalNumberToText.setText("");
      signalOwnerIdText.setText("");
      signalOwnerNameText.setText("");
      signalSenderIdText.setText("");
      signalSenderNameText.setText("");
      signalAddresseeIdText.setText("");
      signalAddresseeNameText.setText("");
      signalSizeFromText.setText("");
      signalSizeToText.setText("");
      signalBufferSizeFromText.setText("");
      signalBufferSizeToText.setText("");
      signalAddressFromText.setText("");
      signalAddressToText.setText("");
      signalStatusCombo.select(0);

      signalNumberCheckbox.setSelection(false);
      signalOwnerIdCheckbox.setSelection(false);
      signalOwnerNameCheckbox.setSelection(false);
      signalSenderIdCheckbox.setSelection(false);
      signalSenderNameCheckbox.setSelection(false);
      signalAddresseeIdCheckbox.setSelection(false);
      signalAddresseeNameCheckbox.setSelection(false);
      signalSizeCheckbox.setSelection(false);
      signalBufferSizeCheckbox.setSelection(false);
      signalAddressCheckbox.setSelection(false);
      signalStatusCheckbox.setSelection(false);

      stackOwnerIdText.setText("");
      stackOwnerNameText.setText("");
      stackSizeFromText.setText("");
      stackSizeToText.setText("");
      stackBufferSizeFromText.setText("");
      stackBufferSizeToText.setText("");
      stackAddressFromText.setText("");
      stackAddressToText.setText("");
      stackUsedFromText.setText("");
      stackUsedToText.setText("");
      stackUnusedFromText.setText("");
      stackUnusedToText.setText("");

      stackOwnerIdCheckbox.setSelection(false);
      stackOwnerNameCheckbox.setSelection(false);
      stackSizeCheckbox.setSelection(false);
      stackBufferSizeCheckbox.setSelection(false);
      stackAddressCheckbox.setSelection(false);
      stackUsedCheckbox.setSelection(false);
      stackUnusedCheckbox.setSelection(false);
   }

   private void validateDialog()
   {
      String errorMessage = null;

      if (targetCombo.getText().length() == 0)
      {
         errorMessage = "Target not specified";
      }
      else if (poolIdText.getText().trim().length() == 0)
      {
         errorMessage = "Pool ID not specified";
      }
      else
      {
         errorMessage = isValidU32(poolIdText.getText(), "Invalid pool ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalNumberFromText.getText(),
               "Invalid signal number min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalNumberToText.getText(),
               "Invalid signal number max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalOwnerIdText.getText(),
               "Invalid signal owner ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalSenderIdText.getText(),
               "Invalid signal sender ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalAddresseeIdText.getText(),
               "Invalid signal addressee ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalSizeFromText.getText(),
               "Invalid signal size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalSizeToText.getText(),
               "Invalid signal size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalBufferSizeFromText.getText(),
               "Invalid signal buffer size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalBufferSizeToText.getText(),
               "Invalid signal buffer size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalAddressFromText.getText(),
               "Invalid signal address min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalAddressToText.getText(),
               "Invalid signal address max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackOwnerIdText.getText(),
               "Invalid stack owner ID");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackSizeFromText.getText(),
               "Invalid stack size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackSizeToText.getText(),
               "Invalid stack size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackBufferSizeFromText.getText(),
               "Invalid stack buffer size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackBufferSizeToText.getText(),
               "Invalid stack buffer size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackAddressFromText.getText(),
               "Invalid stack address min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackAddressToText.getText(),
               "Invalid stack address max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackUsedFromText.getText(),
               "Invalid stack used size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackUsedToText.getText(),
               "Invalid stack used size max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackUnusedFromText.getText(),
               "Invalid stack unused size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(stackUnusedToText.getText(),
               "Invalid stack unused size max value");
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
      try
      {
         poolId = createPoolId();
         signalFilter = createSignalFilter();
         stackFilter = createStackFilter();
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

   private int createPoolId() throws NumberFormatException
   {
      String str = poolIdText.getText().trim();
      int result = (str.length() > 0) ? StringUtils.parseU32(str) : 0;
      return result;
   }

   private SignalFilter createSignalFilter() throws NumberFormatException
   {
      SignalFilter filter;
      boolean exclude;
      String str;
      String strFrom;
      String strTo;
      int num;
      int from;
      int to;

      filter = new SignalFilter();

      strFrom = signalNumberFromText.getText().trim();
      strTo = signalNumberToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalNumberCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterNumber(exclude, from, to);
      }

      str = signalOwnerIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = signalOwnerIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterOwnerId(exclude, num);
      }

      str = signalOwnerNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = signalOwnerNameCheckbox.getSelection();
         filter.filterOwnerName(exclude, str);
      }

      str = signalSenderIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = signalSenderIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterSenderId(exclude, num);
      }

      str = signalSenderNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = signalSenderNameCheckbox.getSelection();
         filter.filterSenderName(exclude, str);
      }

      str = signalAddresseeIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = signalAddresseeIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterAddresseeId(exclude, num);
      }

      str = signalAddresseeNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = signalAddresseeNameCheckbox.getSelection();
         filter.filterAddresseeName(exclude, str);
      }

      strFrom = signalSizeFromText.getText().trim();
      strTo = signalSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSize(exclude, from, to);
      }

      strFrom = signalBufferSizeFromText.getText().trim();
      strTo = signalBufferSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalBufferSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterBufferSize(exclude, from, to);
      }

      strFrom = signalAddressFromText.getText().trim();
      strTo = signalAddressToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalAddressCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterAddress(exclude, from, to);
      }

      str = signalStatusCombo.getText();
      if (str.length() > 0)
      {
         exclude = signalStatusCheckbox.getSelection();
         if (str.equals(SIGNAL_STATUS_VALID_STR))
         {
            num = SignalInfo.STATUS_VALID;
         }
         else if (str.equals(SIGNAL_STATUS_ENDMARK_STR))
         {
            num = SignalInfo.STATUS_ENDMARK;
         }
         else if (str.equals(SIGNAL_STATUS_ADMINISTRATION_STR))
         {
            num = SignalInfo.STATUS_ADMINISTRATION;
         }
         else if (str.equals(SIGNAL_STATUS_NOT_A_SIGNAL_STR))
         {
            num = SignalInfo.STATUS_NOT_A_SIGNAL;
         }
         else if (str.equals(SIGNAL_STATUS_NOT_A_POOL_STR))
         {
            num = SignalInfo.STATUS_NOT_A_POOL;
         }
         else if (str.equals(SIGNAL_STATUS_FREE_STR))
         {
            num = SignalInfo.STATUS_FREE;
         }
         else
         {
            num = SignalInfo.STATUS_UNKNOWN;
         }
         filter.filterStatus(exclude, num);
      }

      return filter;
   }

   private StackFilter createStackFilter() throws NumberFormatException
   {
      StackFilter filter;
      boolean exclude;
      String str;
      String strFrom;
      String strTo;
      int num;
      int from;
      int to;

      filter = new StackFilter();

      str = stackOwnerIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = stackOwnerIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterOwnerId(exclude, num);
      }

      str = stackOwnerNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = stackOwnerNameCheckbox.getSelection();
         filter.filterOwnerName(exclude, str);
      }

      strFrom = stackSizeFromText.getText().trim();
      strTo = stackSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = stackSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSize(exclude, from, to);
      }

      strFrom = stackBufferSizeFromText.getText().trim();
      strTo = stackBufferSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = stackBufferSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterBufferSize(exclude, from, to);
      }

      strFrom = stackAddressFromText.getText().trim();
      strTo = stackAddressToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = stackAddressCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterAddress(exclude, from, to);
      }

      strFrom = stackUsedFromText.getText().trim();
      strTo = stackUsedToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = stackUsedCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterUsed(exclude, from, to);
      }

      strFrom = stackUnusedFromText.getText().trim();
      strTo = stackUnusedToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = stackUnusedCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterUnused(exclude, from, to);
      }

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

   public int getPoolId()
   {
      return poolId;
   }

   public SignalFilter getSignalFilter()
   {
      return signalFilter;
   }

   public StackFilter getStackFilter()
   {
      return stackFilter;
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
