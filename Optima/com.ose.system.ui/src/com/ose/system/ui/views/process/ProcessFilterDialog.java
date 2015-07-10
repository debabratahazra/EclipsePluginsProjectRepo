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

package com.ose.system.ui.views.process;

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
import com.ose.system.Block;
import com.ose.system.Process;
import com.ose.system.ProcessFilter;
import com.ose.system.Segment;
import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;

public class ProcessFilterDialog extends Dialog
{
   private static final String EXCLUDE_STR = "Not";

   private static final String SCOPE_SYSTEM_STR = "System";
   private static final String SCOPE_SEGMENT_STR = "Segment";
   private static final String SCOPE_BLOCK_STR = "Block";
   private static final String SCOPE_PROCESS_STR = "Process";

   private static final String TYPE_PRIORITIZED_STR = "Prioritized";
   private static final String TYPE_BACKGROUND_STR = "Background";
   private static final String TYPE_INTERRUPT_STR = "Interrupt";
   private static final String TYPE_TIMER_INTERRUPT_STR = "Timer Interrupt";
   private static final String TYPE_PHANTOM_STR = "Phantom";
   private static final String TYPE_SIGNAL_PORT_STR = "Signal Port";
   private static final String TYPE_IDLE_STR = "Idle";

   private static final String STATE_RECEIVE_STR = "Receive";
   private static final String STATE_DELAY_STR = "Delay";
   private static final String STATE_SEMAPHORE_STR = "Semaphore";
   private static final String STATE_FAST_SEMAPHORE_STR = "Fast Semaphore";
   private static final String STATE_REMOTE_STR = "Remote";
   private static final String STATE_STOPPED_STR = "Stopped";
   private static final String STATE_INTERCEPTED_STR = "Intercepted";
   private static final String STATE_MUTEX_STR = "Mutex";
   private static final String STATE_RUNNING_STR = "Running";
   private static final String STATE_READY_STR = "Ready";

   private List targets;
   private String targetName;
   private String segmentId;
   private String blockId;
   private String processId;

   private Target target;
   private int scopeType;
   private int scopeId;
   private ProcessFilter processFilter;

   private Combo targetCombo;
   private Combo scopeTypeCombo;
   private Text scopeIdText;

   private Text nameText;
   private Combo typeCombo;
   private Text entrypointFromText;
   private Text entrypointToText;
   // TODO: Uncomment when supported.
   //private Text creatorIdText;
   private Text uidFromText;
   private Text uidToText;
   // TODO: Uncomment when supported.
   //private Text timeSliceFromText;
   //private Text timeSliceToText;
   // TODO: Uncomment when supported.
   //private Text vectorFromText;
   //private Text vectorToText;
   private Text stackSizeFromText;
   private Text stackSizeToText;
   // TODO: Uncomment when supported.
   //private Text supervisorStackSizeFromText;
   //private Text supervisorStackSizeToText;

   private Combo stateCombo;
   // TODO: Uncomment when supported.
   //private Combo supervisorCombo;
   // TODO: Uncomment when supported.
   //private Text errorHandlerText;
   private Text fileNameText;
   private Text lineNumberFromText;
   private Text lineNumberToText;
   private Text priorityFromText;
   private Text priorityToText;
   private Text semaphoreValueFromText;
   private Text semaphoreValueToText;
   private Text euFromText;
   private Text euToText;

   private Text signalsInQueueFromText;
   private Text signalsInQueueToText;
   private Text signalsOwnedFromText;
   private Text signalsOwnedToText;
   // TODO: Uncomment when supported.
   //private Text signalsAttachedFromText;
   //private Text signalsAttachedToText;
   private Text sigselectCountFromText;
   private Text sigselectCountToText;

   private Button nameCheckbox;
   private Button typeCheckbox;
   private Button entrypointCheckbox;
   // TODO: Uncomment when supported.
   //private Button creatorIdCheckbox;
   private Button uidCheckbox;
   // TODO: Uncomment when supported.
   //private Button timeSliceCheckbox;
   // TODO: Uncomment when supported.
   //private Button vectorCheckbox;
   private Button stackSizeCheckbox;
   // TODO: Uncomment when supported.
   //private Button supervisorStackSizeCheckbox;

   private Button stateCheckbox;
   // TODO: Uncomment when supported.
   //private Button supervisorCheckbox;
   // TODO: Uncomment when supported.
   //private Button errorHandlerCheckbox;
   private Button fileNameCheckbox;
   private Button lineNumberCheckbox;
   private Button priorityCheckbox;
   private Button semaphoreValueCheckbox;
   private Button euCheckbox;

   private Button signalsInQueueCheckbox;
   private Button signalsOwnedCheckbox;
   // TODO: Uncomment when supported.
   //private Button signalsAttachedCheckbox;
   private Button sigselectCountCheckbox;

   private CLabel errorMessageLabel;
   private Button clearButton;

   public ProcessFilterDialog(Shell parent, List targets)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
   }

   public ProcessFilterDialog(Shell parent, List targets, Target target,
                              Segment segment, Block block, Process process)
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
      if (process != null)
      {
         this.processId = StringUtils.toHexString(process.getId());
      }
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Process Filter");
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
      TabFolder tabFolder;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayout(new GridLayout(1, false));
      validationHandler = new ValidationHandler();

      // Create scope group.
      createScopeGroup(subComp, validationHandler);

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      // Create tab folder.
      tabFolder = new TabFolder(subComp, SWT.NONE);
      tabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      createPropsTab(tabFolder, validationHandler);
      createStateTab(tabFolder, validationHandler);
      createSignalsTab(tabFolder, validationHandler);

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
         if (selectedTarget.hasProcessSupport())
         {
            scopeTypeCombo.add(SCOPE_PROCESS_STR);
         }
      }
      scopeTypeCombo.setVisibleItemCount(scopeTypeCombo.getItemCount());
      scopeTypeCombo.select(0);
   }

   private void createPropsTab(TabFolder tabFolder,
                               ValidationHandler validationHandler)
   {
      Composite propsComp;
      TabItem propsTabItem;
      GridData gd;
      Label label;

      propsComp = new Composite(tabFolder, SWT.NONE);
      propsComp.setLayout(new GridLayout(4, false));
      propsTabItem = new TabItem(tabFolder, SWT.NONE);
      propsTabItem.setText("Properties");
      propsTabItem.setControl(propsComp);

      label = new Label(propsComp, SWT.NONE);
      label.setText("Name:");

      nameText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      nameText.setLayoutData(gd);

      nameCheckbox = new Button(propsComp, SWT.CHECK);
      nameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(nameText, nameCheckbox);

      label = new Label(propsComp, SWT.NONE);
      label.setText("Type:");

      typeCombo = new Combo(propsComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      typeCombo.setLayoutData(gd);
      typeCombo.add("");
      typeCombo.add(TYPE_PRIORITIZED_STR);
      typeCombo.add(TYPE_BACKGROUND_STR);
      typeCombo.add(TYPE_INTERRUPT_STR);
      typeCombo.add(TYPE_TIMER_INTERRUPT_STR);
      typeCombo.add(TYPE_PHANTOM_STR);
      typeCombo.add(TYPE_SIGNAL_PORT_STR);
      typeCombo.add(TYPE_IDLE_STR);
      typeCombo.setVisibleItemCount(typeCombo.getItemCount());

      typeCheckbox = new Button(propsComp, SWT.CHECK);
      typeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(typeCombo, typeCheckbox);

      label = new Label(propsComp, SWT.NONE);
      label.setText("Entrypoint:");

      entrypointFromText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      entrypointFromText.setLayoutData(gd);
      entrypointFromText.addModifyListener(validationHandler);
      entrypointToText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      entrypointToText.setLayoutData(gd);
      entrypointToText.addModifyListener(validationHandler);

      entrypointCheckbox = new Button(propsComp, SWT.CHECK);
      entrypointCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(entrypointFromText, entrypointToText, entrypointCheckbox);

      /* TODO: Uncomment when supported.
      label = new Label(propsComp, SWT.NONE);
      label.setText("Creator ID:");

      creatorIdText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      creatorIdText.setLayoutData(gd);
      creatorIdText.addModifyListener(validationHandler);

      creatorIdCheckbox = new Button(propsComp, SWT.CHECK);
      creatorIdCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(creatorIdText, creatorIdCheckbox);
      */

      label = new Label(propsComp, SWT.NONE);
      label.setText("User Number:");

      uidFromText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      uidFromText.setLayoutData(gd);
      uidFromText.addModifyListener(validationHandler);
      uidToText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      uidToText.setLayoutData(gd);
      uidToText.addModifyListener(validationHandler);

      uidCheckbox = new Button(propsComp, SWT.CHECK);
      uidCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(uidFromText, uidToText, uidCheckbox);

      /* TODO: Uncomment when supported.
      label = new Label(propsComp, SWT.NONE);
      label.setText("Time Slice:");

      timeSliceFromText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      timeSliceFromText.setLayoutData(gd);
      timeSliceFromText.addModifyListener(validationHandler);
      timeSliceToText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      timeSliceToText.setLayoutData(gd);
      timeSliceToText.addModifyListener(validationHandler);

      timeSliceCheckbox = new Button(propsComp, SWT.CHECK);
      timeSliceCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(timeSliceFromText, timeSliceToText, timeSliceCheckbox);
      */

      /* TODO: Uncomment when supported.
      label = new Label(propsComp, SWT.NONE);
      label.setText("Vector:");

      vectorFromText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      vectorFromText.setLayoutData(gd);
      vectorFromText.addModifyListener(validationHandler);
      vectorToText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      vectorToText.setLayoutData(gd);
      vectorToText.addModifyListener(validationHandler);

      vectorCheckbox = new Button(propsComp, SWT.CHECK);
      vectorCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(vectorFromText, vectorToText, vectorCheckbox);
      */

      label = new Label(propsComp, SWT.NONE);
      label.setText("Stack Size:");

      stackSizeFromText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackSizeFromText.setLayoutData(gd);
      stackSizeFromText.addModifyListener(validationHandler);
      stackSizeToText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      stackSizeToText.setLayoutData(gd);
      stackSizeToText.addModifyListener(validationHandler);

      stackSizeCheckbox = new Button(propsComp, SWT.CHECK);
      stackSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stackSizeFromText, stackSizeToText, stackSizeCheckbox);

      /* TODO: Uncomment when supported.
      label = new Label(propsComp, SWT.NONE);
      label.setText("Supervisor Stack Size:");

      supervisorStackSizeFromText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      supervisorStackSizeFromText.setLayoutData(gd);
      supervisorStackSizeFromText.addModifyListener(validationHandler);
      supervisorStackSizeToText = new Text(propsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      supervisorStackSizeToText.setLayoutData(gd);
      supervisorStackSizeToText.addModifyListener(validationHandler);

      supervisorStackSizeCheckbox = new Button(propsComp, SWT.CHECK);
      supervisorStackSizeCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(supervisorStackSizeFromText, supervisorStackSizeToText,
            supervisorStackSizeCheckbox);
      */
   }

   private void createStateTab(TabFolder tabFolder,
                               ValidationHandler validationHandler)
   {
      Composite stateComp;
      TabItem stateTabItem;
      GridData gd;
      Label label;

      stateComp = new Composite(tabFolder, SWT.NONE);
      stateComp.setLayout(new GridLayout(4, false));
      stateTabItem = new TabItem(tabFolder, SWT.NONE);
      stateTabItem.setText("State");
      stateTabItem.setControl(stateComp);

      label = new Label(stateComp, SWT.NONE);
      label.setText("State:");

      stateCombo = new Combo(stateComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      stateCombo.setLayoutData(gd);
      stateCombo.add("");
      stateCombo.add(STATE_RECEIVE_STR);
      stateCombo.add(STATE_DELAY_STR);
      stateCombo.add(STATE_SEMAPHORE_STR);
      stateCombo.add(STATE_FAST_SEMAPHORE_STR);
      stateCombo.add(STATE_REMOTE_STR);
      stateCombo.add(STATE_STOPPED_STR);
      stateCombo.add(STATE_INTERCEPTED_STR);
      stateCombo.add(STATE_MUTEX_STR);
      stateCombo.add(STATE_RUNNING_STR);
      stateCombo.add(STATE_READY_STR);
      stateCombo.setVisibleItemCount(stateCombo.getItemCount());

      stateCheckbox = new Button(stateComp, SWT.CHECK);
      stateCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(stateCombo, stateCheckbox);

      /* TODO: Uncomment when supported.
      label = new Label(stateComp, SWT.NONE);
      label.setText("Supervisor Mode:");

      supervisorCombo = new Combo(stateComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      supervisorCombo.setLayoutData(gd);
      supervisorCombo.add("");
      supervisorCombo.add("Yes");
      supervisorCombo.add("No");

      supervisorCheckbox = new Button(stateComp, SWT.CHECK);
      supervisorCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(supervisorCombo, supervisorCheckbox);
      */

      /* TODO: Uncomment when supported.
      label = new Label(stateComp, SWT.NONE);
      label.setText("Error Handler:");

      errorHandlerText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      errorHandlerText.setLayoutData(gd);
      errorHandlerText.addModifyListener(validationHandler);

      errorHandlerCheckbox = new Button(stateComp, SWT.CHECK);
      errorHandlerCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(errorHandlerText, errorHandlerCheckbox);
      */

      label = new Label(stateComp, SWT.NONE);
      label.setText("Filename:");

      fileNameText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      fileNameText.setLayoutData(gd);

      fileNameCheckbox = new Button(stateComp, SWT.CHECK);
      fileNameCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(fileNameText, fileNameCheckbox);

      label = new Label(stateComp, SWT.NONE);
      label.setText("Line Number:");

      lineNumberFromText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      lineNumberFromText.setLayoutData(gd);
      lineNumberFromText.addModifyListener(validationHandler);
      lineNumberToText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      lineNumberToText.setLayoutData(gd);
      lineNumberToText.addModifyListener(validationHandler);

      lineNumberCheckbox = new Button(stateComp, SWT.CHECK);
      lineNumberCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(lineNumberFromText, lineNumberToText, lineNumberCheckbox);

      label = new Label(stateComp, SWT.NONE);
      label.setText("Priority:");

      priorityFromText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      priorityFromText.setLayoutData(gd);
      priorityFromText.addModifyListener(validationHandler);
      priorityToText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      priorityToText.setLayoutData(gd);
      priorityToText.addModifyListener(validationHandler);

      priorityCheckbox = new Button(stateComp, SWT.CHECK);
      priorityCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(priorityFromText, priorityToText, priorityCheckbox);

      label = new Label(stateComp, SWT.NONE);
      label.setText("Fast Semaphore:");

      semaphoreValueFromText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      semaphoreValueFromText.setLayoutData(gd);
      semaphoreValueFromText.addModifyListener(validationHandler);
      semaphoreValueToText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      semaphoreValueToText.setLayoutData(gd);
      semaphoreValueToText.addModifyListener(validationHandler);

      semaphoreValueCheckbox = new Button(stateComp, SWT.CHECK);
      semaphoreValueCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(semaphoreValueFromText, semaphoreValueToText,
            semaphoreValueCheckbox);

      label = new Label(stateComp, SWT.NONE);
      label.setText("Execution Unit:");

      euFromText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      euFromText.setLayoutData(gd);
      euFromText.addModifyListener(validationHandler);
      euToText = new Text(stateComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      euToText.setLayoutData(gd);
      euToText.addModifyListener(validationHandler);

      euCheckbox = new Button(stateComp, SWT.CHECK);
      euCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(euFromText, euToText, euCheckbox);
   }

   private void createSignalsTab(TabFolder tabFolder,
                                 ValidationHandler validationHandler)
   {
      Composite signalsComp;
      TabItem signalsTabItem;
      GridData gd;
      Label label;

      signalsComp = new Composite(tabFolder, SWT.NONE);
      signalsComp.setLayout(new GridLayout(4, false));
      signalsTabItem = new TabItem(tabFolder, SWT.NONE);
      signalsTabItem.setText("Signals");
      signalsTabItem.setControl(signalsComp);

      label = new Label(signalsComp, SWT.NONE);
      label.setText("Signals in Queue:");

      signalsInQueueFromText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsInQueueFromText.setLayoutData(gd);
      signalsInQueueFromText.addModifyListener(validationHandler);
      signalsInQueueToText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsInQueueToText.setLayoutData(gd);
      signalsInQueueToText.addModifyListener(validationHandler);

      signalsInQueueCheckbox = new Button(signalsComp, SWT.CHECK);
      signalsInQueueCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalsInQueueFromText, signalsInQueueToText,
            signalsInQueueCheckbox);

      label = new Label(signalsComp, SWT.NONE);
      label.setText("Owned Signals:");

      signalsOwnedFromText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsOwnedFromText.setLayoutData(gd);
      signalsOwnedFromText.addModifyListener(validationHandler);
      signalsOwnedToText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsOwnedToText.setLayoutData(gd);
      signalsOwnedToText.addModifyListener(validationHandler);

      signalsOwnedCheckbox = new Button(signalsComp, SWT.CHECK);
      signalsOwnedCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalsOwnedFromText, signalsOwnedToText,
            signalsOwnedCheckbox);

      /* TODO: Uncomment when supported.
      label = new Label(signalsComp, SWT.NONE);
      label.setText("Attached Signals:");

      signalsAttachedFromText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsAttachedFromText.setLayoutData(gd);
      signalsAttachedFromText.addModifyListener(validationHandler);
      signalsAttachedToText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      signalsAttachedToText.setLayoutData(gd);
      signalsAttachedToText.addModifyListener(validationHandler);

      signalsAttachedCheckbox = new Button(signalsComp, SWT.CHECK);
      signalsAttachedCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(signalsAttachedFromText, signalsAttachedToText,
            signalsAttachedCheckbox);
      */

      label = new Label(signalsComp, SWT.NONE);
      label.setText("Sigselect Count:");

      sigselectCountFromText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      sigselectCountFromText.setLayoutData(gd);
      sigselectCountFromText.addModifyListener(validationHandler);
      sigselectCountToText = new Text(signalsComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      sigselectCountToText.setLayoutData(gd);
      sigselectCountToText.addModifyListener(validationHandler);

      sigselectCountCheckbox = new Button(signalsComp, SWT.CHECK);
      sigselectCountCheckbox.setText(EXCLUDE_STR);
      new CheckboxHandler(sigselectCountFromText, sigselectCountToText,
            sigselectCountCheckbox);
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
      if ((targetName != null) && (segmentId == null) &&
          (blockId == null) && (processId == null))
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
      else if ((targetName != null) && (processId != null))
      {
         clearDialog();
         targetCombo.setText(targetName);
         scopeTypeCombo.setText(SCOPE_PROCESS_STR);
         scopeIdText.setText(processId);
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
      section = settings.getSection("ProcessFilterDialog");
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
      typeCombo.setText(section.get("type"));
      entrypointFromText.setText(section.get("entrypointFrom"));
      entrypointToText.setText(section.get("entrypointTo"));
      // TODO: Uncomment when supported.
      //creatorIdText.setText(section.get("creatorId"));
      uidFromText.setText(section.get("uidFrom"));
      uidToText.setText(section.get("uidTo"));
      // TODO: Uncomment when supported.
      //timeSliceFromText.setText(section.get("timeSliceFrom"));
      //timeSliceToText.setText(section.get("timeSliceTo"));
      // TODO: Uncomment when supported.
      //vectorFromText.setText(section.get("vectorFrom"));
      //vectorToText.setText(section.get("vectorTo"));
      stackSizeFromText.setText(section.get("stackSizeFrom"));
      stackSizeToText.setText(section.get("stackSizeTo"));
      // TODO: Uncomment when supported.
      //supervisorStackSizeFromText.setText(section.get("supervisorStackSizeFrom"));
      //supervisorStackSizeToText.setText(section.get("supervisorStackSizeTo"));
      stateCombo.setText(section.get("state"));
      // TODO: Uncomment when supported.
      //supervisorCombo.setText(section.get("supervisor"));
      // TODO: Uncomment when supported.
      //errorHandlerText.setText(section.get("errorHandler"));
      fileNameText.setText(section.get("fileName"));
      lineNumberFromText.setText(section.get("lineNumberFrom"));
      lineNumberToText.setText(section.get("lineNumberTo"));
      priorityFromText.setText(section.get("priorityFrom"));
      priorityToText.setText(section.get("priorityTo"));
      semaphoreValueFromText.setText(section.get("semaphoreValueFrom"));
      semaphoreValueToText.setText(section.get("semaphoreValueTo"));
      euFromText.setText(section.get("euFrom"));
      euToText.setText(section.get("euTo"));
      signalsInQueueFromText.setText(section.get("signalsInQueueFrom"));
      signalsInQueueToText.setText(section.get("signalsInQueueTo"));
      signalsOwnedFromText.setText(section.get("signalsOwnedFrom"));
      signalsOwnedToText.setText(section.get("signalsOwnedTo"));
      // TODO: Uncomment when supported.
      //signalsAttachedFromText.setText(section.get("signalsAttachedFrom"));
      //signalsAttachedToText.setText(section.get("signalsAttachedTo"));
      sigselectCountFromText.setText(section.get("sigselectCountFrom"));
      sigselectCountToText.setText(section.get("sigselectCountTo"));

      nameCheckbox.setSelection(section.getBoolean("nameExclude"));
      typeCheckbox.setSelection(section.getBoolean("typeExclude"));
      entrypointCheckbox.setSelection(section.getBoolean("entrypointExclude"));
      // TODO: Uncomment when supported.
      //creatorIdCheckbox.setSelection(section.getBoolean("creatorIdExclude"));
      uidCheckbox.setSelection(section.getBoolean("uidExclude"));
      // TODO: Uncomment when supported.
      //timeSliceCheckbox.setSelection(section.getBoolean("timeSliceExclude"));
      // TODO: Uncomment when supported.
      //vectorCheckbox.setSelection(section.getBoolean("vectorExclude"));
      stackSizeCheckbox.setSelection(section.getBoolean("stackSizeExclude"));
      // TODO: Uncomment when supported.
      //supervisorStackSizeCheckbox.setSelection(section.getBoolean("supervisorStackSizeExclude"));
      stateCheckbox.setSelection(section.getBoolean("stateExclude"));
      // TODO: Uncomment when supported.
      //supervisorCheckbox.setSelection(section.getBoolean("supervisorExclude"));
      // TODO: Uncomment when supported.
      //errorHandlerCheckbox.setSelection(section.getBoolean("errorHandlerExclude"));
      fileNameCheckbox.setSelection(section.getBoolean("fileNameExclude"));
      lineNumberCheckbox.setSelection(section.getBoolean("lineNumberExclude"));
      priorityCheckbox.setSelection(section.getBoolean("priorityExclude"));
      semaphoreValueCheckbox.setSelection(section.getBoolean("semaphoreValueExclude"));
      euCheckbox.setSelection(section.getBoolean("euExclude"));
      signalsInQueueCheckbox.setSelection(section.getBoolean("signalsInQueueExclude"));
      signalsOwnedCheckbox.setSelection(section.getBoolean("signalsOwnedExclude"));
      // TODO: Uncomment when supported.
      //signalsAttachedCheckbox.setSelection(section.getBoolean("signalsAttachedExclude"));
      sigselectCountCheckbox.setSelection(section.getBoolean("sigselectCountExclude"));
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = SystemBrowserPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("ProcessFilterDialog");

      section.put("target", targetCombo.getText());
      section.put("scopeType", scopeTypeCombo.getText());
      section.put("scopeId", scopeIdText.getText().trim());

      section.put("name", nameText.getText().trim());
      section.put("type", typeCombo.getText());
      section.put("entrypointFrom", entrypointFromText.getText().trim());
      section.put("entrypointTo", entrypointToText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("creatorId", creatorIdText.getText().trim());
      section.put("uidFrom", uidFromText.getText().trim());
      section.put("uidTo", uidToText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("timeSliceFrom", timeSliceFromText.getText().trim());
      //section.put("timeSliceTo", timeSliceToText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("vectorFrom", vectorFromText.getText().trim());
      //section.put("vectorTo", vectorToText.getText().trim());
      section.put("stackSizeFrom", stackSizeFromText.getText().trim());
      section.put("stackSizeTo", stackSizeToText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("supervisorStackSizeFrom", supervisorStackSizeFromText.getText().trim());
      //section.put("supervisorStackSizeTo", supervisorStackSizeToText.getText().trim());
      section.put("state", stateCombo.getText());
      // TODO: Uncomment when supported.
      //section.put("supervisor", supervisorCombo.getText());
      // TODO: Uncomment when supported.
      //section.put("errorHandler", errorHandlerText.getText().trim());
      section.put("fileName", fileNameText.getText().trim());
      section.put("lineNumberFrom", lineNumberFromText.getText().trim());
      section.put("lineNumberTo", lineNumberToText.getText().trim());
      section.put("priorityFrom", priorityFromText.getText().trim());
      section.put("priorityTo", priorityToText.getText().trim());
      section.put("semaphoreValueFrom", semaphoreValueFromText.getText().trim());
      section.put("semaphoreValueTo", semaphoreValueToText.getText().trim());
      section.put("euFrom", euFromText.getText().trim());
      section.put("euTo", euToText.getText().trim());
      section.put("signalsInQueueFrom", signalsInQueueFromText.getText().trim());
      section.put("signalsInQueueTo", signalsInQueueToText.getText().trim());
      section.put("signalsOwnedFrom", signalsOwnedFromText.getText().trim());
      section.put("signalsOwnedTo", signalsOwnedToText.getText().trim());
      // TODO: Uncomment when supported.
      //section.put("signalsAttachedFrom", signalsAttachedFromText.getText().trim());
      //section.put("signalsAttachedTo", signalsAttachedToText.getText().trim());
      section.put("sigselectCountFrom", sigselectCountFromText.getText().trim());
      section.put("sigselectCountTo", sigselectCountToText.getText().trim());

      section.put("nameExclude", nameCheckbox.getSelection());
      section.put("typeExclude", typeCheckbox.getSelection());
      section.put("entrypointExclude", entrypointCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("creatorIdExclude", creatorIdCheckbox.getSelection());
      section.put("uidExclude", uidCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("timeSliceExclude", timeSliceCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("vectorExclude", vectorCheckbox.getSelection());
      section.put("stackSizeExclude", stackSizeCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("supervisorStackSizeExclude", supervisorStackSizeCheckbox.getSelection());
      section.put("stateExclude", stateCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("supervisorExclude", supervisorCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("errorHandlerExclude", errorHandlerCheckbox.getSelection());
      section.put("fileNameExclude", fileNameCheckbox.getSelection());
      section.put("lineNumberExclude", lineNumberCheckbox.getSelection());
      section.put("priorityExclude", priorityCheckbox.getSelection());
      section.put("semaphoreValueExclude", semaphoreValueCheckbox.getSelection());
      section.put("euExclude", euCheckbox.getSelection());
      section.put("signalsInQueueExclude", signalsInQueueCheckbox.getSelection());
      section.put("signalsOwnedExclude", signalsOwnedCheckbox.getSelection());
      // TODO: Uncomment when supported.
      //section.put("signalsAttachedExclude", signalsAttachedCheckbox.getSelection());
      section.put("sigselectCountExclude", sigselectCountCheckbox.getSelection());
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
      typeCombo.select(0);
      entrypointFromText.setText("");
      entrypointToText.setText("");
      // TODO: Uncomment when supported.
      //creatorIdText.setText("");
      uidFromText.setText("");
      uidToText.setText("");
      // TODO: Uncomment when supported.
      //timeSliceFromText.setText("");
      //timeSliceToText.setText("");
      // TODO: Uncomment when supported.
      //vectorFromText.setText("");
      //vectorToText.setText("");
      stackSizeFromText.setText("");
      stackSizeToText.setText("");
      // TODO: Uncomment when supported.
      //supervisorStackSizeFromText.setText("");
      //supervisorStackSizeToText.setText("");
      stateCombo.select(0);
      // TODO: Uncomment when supported.
      //supervisorCombo.select(0);
      // TODO: Uncomment when supported.
      //errorHandlerText.setText("");
      fileNameText.setText("");
      lineNumberFromText.setText("");
      lineNumberToText.setText("");
      priorityFromText.setText("");
      priorityToText.setText("");
      semaphoreValueFromText.setText("");
      semaphoreValueToText.setText("");
      euFromText.setText("");
      euToText.setText("");
      signalsInQueueFromText.setText("");
      signalsInQueueToText.setText("");
      signalsOwnedFromText.setText("");
      signalsOwnedToText.setText("");
      // TODO: Uncomment when supported.
      //signalsAttachedFromText.setText("");
      //signalsAttachedToText.setText("");
      sigselectCountFromText.setText("");
      sigselectCountToText.setText("");

      nameCheckbox.setSelection(false);
      typeCheckbox.setSelection(false);
      entrypointCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //creatorIdCheckbox.setSelection(false);
      uidCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //timeSliceCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //vectorCheckbox.setSelection(false);
      stackSizeCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //supervisorStackSizeCheckbox.setSelection(false);
      stateCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //supervisorCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //errorHandlerCheckbox.setSelection(false);
      fileNameCheckbox.setSelection(false);
      lineNumberCheckbox.setSelection(false);
      priorityCheckbox.setSelection(false);
      semaphoreValueCheckbox.setSelection(false);
      euCheckbox.setSelection(false);
      signalsInQueueCheckbox.setSelection(false);
      signalsOwnedCheckbox.setSelection(false);
      // TODO: Uncomment when supported.
      //signalsAttachedCheckbox.setSelection(false);
      sigselectCountCheckbox.setSelection(false);
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
         errorMessage = isValidU32(entrypointFromText.getText(),
               "Invalid entrypoint address min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(entrypointToText.getText(),
               "Invalid entrypoint address max value");
      }
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(creatorIdText.getText(),
               "Invalid creator ID");
      }
      */
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
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(timeSliceFromText.getText(),
               "Invalid time slice min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(timeSliceToText.getText(),
               "Invalid time slice max value");
      }
      */
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(vectorFromText.getText(),
               "Invalid vector min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(vectorToText.getText(),
               "Invalid vector max value");
      }
      */
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
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(supervisorStackSizeFromText.getText(),
               "Invalid supervisor stack size min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(supervisorStackSizeToText.getText(),
               "Invalid supervisor stack size max value");
      }
      */
      /* TODO: Uncomment when supported.
      if (errorMessage == null)
      {
         errorMessage = isValidU32(errorHandlerText.getText(),
               "Invalid error handler address");
      }
      */
      if (errorMessage == null)
      {
         errorMessage = isValidU32(lineNumberFromText.getText(),
               "Invalid line number min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(lineNumberToText.getText(),
               "Invalid line number max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(priorityFromText.getText(),
               "Invalid priority min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(priorityToText.getText(),
               "Invalid priority max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidS32(semaphoreValueFromText.getText(),
               "Invalid fast semaphore min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidS32(semaphoreValueToText.getText(),
               "Invalid fast semaphore max value");
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
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalsInQueueFromText.getText(),
               "Invalid signals in queue min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalsInQueueToText.getText(),
               "Invalid signals in queue max value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalsOwnedFromText.getText(),
               "Invalid owned signals min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidU32(signalsOwnedToText.getText(),
               "Invalid owned signals max value");
      }
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
      if (errorMessage == null)
      {
         errorMessage = isValidS32(sigselectCountFromText.getText(),
               "Invalid sigselect count min value");
      }
      if (errorMessage == null)
      {
         errorMessage = isValidS32(sigselectCountToText.getText(),
               "Invalid sigselect count max value");
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
         processFilter = createProcessFilter();
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

   private ProcessFilter createProcessFilter() throws NumberFormatException
   {
      ProcessFilter filter;
      boolean exclude;
      String str;
      String strFrom;
      String strTo;
      int num;
      int from;
      int to;

      filter = new ProcessFilter();

      str = nameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = nameCheckbox.getSelection();
         filter.filterName(exclude, str);
      }

      str = typeCombo.getText();
      if (str.length() > 0)
      {
         exclude = typeCheckbox.getSelection();
         if (str.equals(TYPE_PRIORITIZED_STR))
         {
            num = Process.TYPE_PRIORITIZED;
         }
         else if (str.equals(TYPE_BACKGROUND_STR))
         {
            num = Process.TYPE_BACKGROUND;
         }
         else if (str.equals(TYPE_INTERRUPT_STR))
         {
            num = Process.TYPE_INTERRUPT;
         }
         else if (str.equals(TYPE_TIMER_INTERRUPT_STR))
         {
            num = Process.TYPE_TIMER_INTERRUPT;
         }
         else if (str.equals(TYPE_PHANTOM_STR))
         {
            num = Process.TYPE_PHANTOM;
         }
         else if (str.equals(TYPE_SIGNAL_PORT_STR))
         {
            num = Process.TYPE_SIGNAL_PORT;
         }
         else if (str.equals(TYPE_IDLE_STR))
         {
            num = Process.TYPE_IDLE;
         }
         else
         {
            num = Process.TYPE_UNKNOWN;
         }
         filter.filterType(exclude, num);
      }

      strFrom = entrypointFromText.getText().trim();
      strTo = entrypointToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = entrypointCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterEntrypoint(exclude, from, to);
      }

      /* TODO: Uncomment when supported.
      str = creatorIdText.getText().trim();
      if (str.length() > 0)
      {
         exclude = creatorIdCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterCreatorId(exclude, num);
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

      /* TODO: Uncomment when supported.
      strFrom = timeSliceFromText.getText().trim();
      strTo = timeSliceToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = timeSliceCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterTimeSlice(exclude, from, to);
      }
      */

      /* TODO: Uncomment when supported.
      strFrom = vectorFromText.getText().trim();
      strTo = vectorToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = vectorCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterVector(exclude, from, to);
      }
      */

      strFrom = stackSizeFromText.getText().trim();
      strTo = stackSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = stackSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterStackSize(exclude, from, to);
      }

      /* TODO: Uncomment when supported.
      strFrom = supervisorStackSizeFromText.getText().trim();
      strTo = supervisorStackSizeToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = supervisorStackSizeCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSupervisorStackSize(exclude, from, to);
      }
      */

      str = stateCombo.getText();
      if (str.length() > 0)
      {
         exclude = stateCheckbox.getSelection();
         if (str.equals(STATE_RECEIVE_STR))
         {
            num = Process.STATE_RECEIVE;
         }
         else if (str.equals(STATE_DELAY_STR))
         {
            num = Process.STATE_DELAY;
         }
         else if (str.equals(STATE_SEMAPHORE_STR))
         {
            num = Process.STATE_SEMAPHORE;
         }
         else if (str.equals(STATE_FAST_SEMAPHORE_STR))
         {
            num = Process.STATE_FAST_SEMAPHORE;
         }
         else if (str.equals(STATE_REMOTE_STR))
         {
            num = Process.STATE_REMOTE;
         }
         else if (str.equals(STATE_STOPPED_STR))
         {
            num = Process.STATE_STOPPED;
         }
         else if (str.equals(STATE_INTERCEPTED_STR))
         {
            num = Process.STATE_INTERCEPTED;
         }
         else if (str.equals(STATE_MUTEX_STR))
         {
            num = Process.STATE_MUTEX;
         }
         else if (str.equals(STATE_RUNNING_STR))
         {
            num = Process.STATE_RUNNING;
         }
         else if (str.equals(STATE_READY_STR))
         {
            num = Process.STATE_READY;
         }
         else
         {
            num = 0;
         }
         filter.filterState(exclude, num);
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

      /* TODO: Uncomment when supported.
      str = errorHandlerText.getText().trim();
      if (str.length() > 0)
      {
         exclude = errorHandlerCheckbox.getSelection();
         num = StringUtils.parseU32(str);
         filter.filterErrorHandler(exclude, num);
      }
      */

      str = fileNameText.getText().trim();
      if (str.length() > 0)
      {
         exclude = fileNameCheckbox.getSelection();
         filter.filterFileName(exclude, str);
      }

      strFrom = lineNumberFromText.getText().trim();
      strTo = lineNumberToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = lineNumberCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterLineNumber(exclude, from, to);
      }

      strFrom = priorityFromText.getText().trim();
      strTo = priorityToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = priorityCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 32;
         filter.filterPriority(exclude, from, to);
      }

      strFrom = semaphoreValueFromText.getText().trim();
      strTo = semaphoreValueToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = semaphoreValueCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseS32(strFrom) : Integer.MIN_VALUE;
         to = (strTo.length() > 0) ? StringUtils.parseS32(strTo) : Integer.MAX_VALUE;
         filter.filterSemaphoreValue(exclude, from, to);
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

      strFrom = signalsInQueueFromText.getText().trim();
      strTo = signalsInQueueToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalsInQueueCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSignalsInQueue(exclude, from, to);
      }

      strFrom = signalsOwnedFromText.getText().trim();
      strTo = signalsOwnedToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = signalsOwnedCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseU32(strFrom) : 0;
         to = (strTo.length() > 0) ? StringUtils.parseU32(strTo) : 0xFFFFFFFF;
         filter.filterSignalsOwned(exclude, from, to);
      }

      /* TODO: Uncomment when supported.
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

      strFrom = sigselectCountFromText.getText().trim();
      strTo = sigselectCountToText.getText().trim();
      if ((strFrom.length() > 0) || (strTo.length() > 0))
      {
         exclude = sigselectCountCheckbox.getSelection();
         from = (strFrom.length() > 0) ? StringUtils.parseS32(strFrom) : Integer.MIN_VALUE;
         to = (strTo.length() > 0) ? StringUtils.parseS32(strTo) : Integer.MAX_VALUE;
         filter.filterSigselectCount(exclude, from, to);
      }

      return filter;
   }

   private static String isValidS32(String text, String errorMessage)
   {
      String msg = null;
      if (text.trim().length() > 0)
      {
         try
         {
            StringUtils.parseS32(text);
         }
         catch (NumberFormatException e)
         {
            msg = errorMessage;
         }
      }
      return msg;
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

   public ProcessFilter getProcessFilter()
   {
      return processFilter;
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
