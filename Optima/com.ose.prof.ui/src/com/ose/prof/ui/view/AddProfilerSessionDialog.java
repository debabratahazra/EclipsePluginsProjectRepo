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

package com.ose.prof.ui.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.CPUBlockReportsEnabledInfo;
import com.ose.system.CPUPriorityReportsEnabledInfo;
import com.ose.system.CPUProcessReportsEnabledInfo;
import com.ose.system.CPUReportsEnabledInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.UserReportsEnabledInfo;
import com.ose.system.ui.util.StringUtils;

public class AddProfilerSessionDialog extends Dialog
{
   public static final String PROFILER_TYPE_CPU = "CPU Usage";
   public static final String PROFILER_TYPE_CPU_PRIORITY = "CPU Usage / Priority";
   public static final String PROFILER_TYPE_CPU_PROCESS = "CPU Usage / Process";
   public static final String PROFILER_TYPE_CPU_BLOCK = "CPU Usage / Block";
   public static final String PROFILER_TYPE_HEAP = "Turnover Heap Usage / Process";
   public static final String PROFILER_TYPE_ACCUMULATED_HEAP_PROCESS = "Accumulated Heap Usage / Process";
   public static final String PROFILER_TYPE_ACCUMULATED_HEAP = "Accumulated Heap Usage / Heap";
   public static final String PROFILER_TYPE_USER = "Custom Profiling";

   private static final String ALL_EXECUTION_UNITS_STR = "All";

   private List targets;
   private String targetName;

   private Target target;
   private String profilerType;
   private int userReportNumber;
   private short executionUnit;
   private int integrationInterval;
   private int maxReports;
   private int maxValuesReport;
   private int priorityLimit;
   private boolean profilingProcesses;

   private Combo targetCombo;
   private Combo profilerTypeCombo;
   private Text userReportNumberText;
   private Combo executionUnitCombo;
   private FormToolkit toolkit;
   private Section profilerSection;
   private ProfilerDelegate profilerDelegate;
   private CLabel errorMessageLabel;

   public AddProfilerSessionDialog(Shell parent, List targets)
   {
      this(parent, targets, null);
   }

   public AddProfilerSessionDialog(Shell parent, List targets, Target target)
   {
      super(parent);
      setShellStyle(getShellStyle() | SWT.RESIZE);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      this.targets = targets;
      if (target != null)
      {
         this.targetName = target.toString();
      }
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Target and Profiling Parameters");
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      // If the dialog is opened with the user profiling type preselected and
      // thus the profiling parameters expanded, the initial size of the dialog
      // has to be set prior to initializing the contents of the dialog,
      // otherwise the dialog will, for some reason, fill the whole screen.
      initSize();
      initContents();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subComp;
      ValidationHandler validationHandler;
      Label label;
      GridData gd;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayoutData(new GridData(GridData.FILL_BOTH));
      subComp.setLayout(new GridLayout(3, false));
      validationHandler = new ValidationHandler();

      label = new Label(subComp, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(subComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      targetCombo.setLayoutData(gd);
      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         Target target = (Target) i.next();
         targetCombo.add(target.toString());
      }
      targetCombo.addModifyListener(validationHandler);
      targetCombo.addSelectionListener(new TargetSelectionHandler());

      label = new Label(subComp, SWT.NONE);
      label.setText("Type of Profiling:");

      profilerTypeCombo = new Combo(subComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      profilerTypeCombo.setLayoutData(gd);
      profilerTypeCombo.addModifyListener(validationHandler);
      profilerTypeCombo.addSelectionListener(new ProfilerTypeSelectionHandler());

      userReportNumberText = new Text(subComp, SWT.SINGLE | SWT.BORDER);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      userReportNumberText.setLayoutData(gd);
      userReportNumberText.addModifyListener(validationHandler);

      label = new Label(subComp, SWT.NONE);
      label.setText("Execution Unit:");

      executionUnitCombo = new Combo(subComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      executionUnitCombo.setLayoutData(gd);
      executionUnitCombo.addModifyListener(validationHandler);

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      toolkit = new FormToolkit(parent.getDisplay());
      toolkit.getColors().initializeSectionToolBarColors();
      toolkit.getColors().setBackground(parent.getBackground());

      profilerSection = toolkit.createSection(subComp,
            Section.TITLE_BAR | Section.TWISTIE);
      profilerSection.setText("Profiling Parameters");
      gd = new GridData(GridData.FILL_BOTH);
      gd.horizontalSpan = 3;
      gd.widthHint = convertHorizontalDLUsToPixels(400);
      gd.heightHint = convertVerticalDLUsToPixels(30);
      profilerSection.setLayoutData(gd);
      profilerSection.setLayout(new GridLayout(1, false));
      profilerSection.addExpansionListener(new ExpansionHandler());

      // Create vertical spacer.
      label = new Label(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      // Create error message label.
      errorMessageLabel = new CLabel(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      errorMessageLabel.setLayoutData(gd);

      // Create separator line.
      label = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   private void initSize()
   {
      Point size = getInitialSize();
      Point location = getInitialLocation(size);
      getShell().setBounds(getConstrainedShellBounds(
            new Rectangle(location.x, location.y, size.x, size.y)));
   }

   private void initContents()
   {
      if (targetName != null)
      {
         targetCombo.setText(targetName);
         targetCombo.select(targetCombo.getSelectionIndex());
         handleTargetComboSelected(true);
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

      settings = ProfilerPlugin.getDefault().getDialogSettings();
      section = settings.getSection("AddProfilerSessionDialog");
      if (section == null)
      {
         return;
      }

      targetCombo.setText(section.get("target"));
      targetCombo.select(targetCombo.getSelectionIndex());
      handleTargetComboSelected(false);
      profilerTypeCombo.setText(section.get("profilerType"));
      userReportNumberText.setText(section.get("userReportNumber"));
      profilerTypeCombo.select(profilerTypeCombo.getSelectionIndex());
      handleProfilerTypeComboSelected(false);
      setExecutionUnit((short) section.getInt("executionUnit"));
      if (profilerDelegate != null)
      {
         profilerDelegate.setIntegrationInterval(section.getInt("integrationInterval"));
         profilerDelegate.setMaxReports(section.getInt("maxReports"));
         profilerDelegate.setMaxValuesReport(section.getInt("maxValuesReport"));
         profilerDelegate.setPriorityLimit(section.getInt("priorityLimit"));
         profilerDelegate.setProfilingProcesses(section.getBoolean("profilingProcesses"));
      }
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = ProfilerPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("AddProfilerSessionDialog");

      section.put("target", targetCombo.getText());
      section.put("profilerType", profilerTypeCombo.getText());
      section.put("userReportNumber", userReportNumberText.getText().trim());
      section.put("executionUnit", createExecutionUnit());
      section.put("integrationInterval", profilerDelegate.getIntegrationInterval());
      section.put("maxReports", profilerDelegate.getMaxReports());
      section.put("maxValuesReport", profilerDelegate.getMaxValuesReport());
      section.put("priorityLimit", profilerDelegate.getPriorityLimit());
      section.put("profilingProcesses", profilerDelegate.isProfilingProcesses());
   }

   private void handleTargetComboSelected(boolean restore)
   {
      Target oldTarget = target;
      target = createTarget();
      if (target != oldTarget)
      {
         profilerTypeCombo.removeAll();
         if (target != null)
         {
            if (target.hasGetCPUReportsSupport())
            {
               profilerTypeCombo.add(PROFILER_TYPE_CPU);
            }
            if (target.hasGetCPUPriorityReportsSupport())
            {
               profilerTypeCombo.add(PROFILER_TYPE_CPU_PRIORITY);
            }
            if (target.hasGetCPUProcessReportsSupport())
            {
               profilerTypeCombo.add(PROFILER_TYPE_CPU_PROCESS);
            }
            if (target.hasGetCPUBlockReportsSupport())
            {
               profilerTypeCombo.add(PROFILER_TYPE_CPU_BLOCK);
            }
            if (target.hasGetUserReportsSupport())
            {
               profilerTypeCombo.add(PROFILER_TYPE_HEAP);
               profilerTypeCombo.add(PROFILER_TYPE_ACCUMULATED_HEAP_PROCESS);
               profilerTypeCombo.add(PROFILER_TYPE_ACCUMULATED_HEAP);
               profilerTypeCombo.add(PROFILER_TYPE_USER);
            }
            profilerTypeCombo.setVisibleItemCount(profilerTypeCombo.getItemCount());
            profilerTypeCombo.select(0);
            handleProfilerTypeComboSelected(restore);
         }

         populateExecutionUnitCombo();
      }
   }

   private void handleProfilerTypeComboSelected(boolean restore)
   {
      String profilerType;

      profilerType = profilerTypeCombo.getText();
      removeProfilerDelegate();
      if (profilerType.equals(PROFILER_TYPE_CPU))
      {
         profilerDelegate = new CPUProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_CPU_PRIORITY))
      {
         profilerDelegate = new CPUPriorityProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_CPU_PROCESS))
      {
         profilerDelegate = new CPUProcessProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_CPU_BLOCK))
      {
         profilerDelegate = new CPUBlockProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_HEAP))
      {
         profilerDelegate = new HeapProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_ACCUMULATED_HEAP_PROCESS))
      {
         profilerDelegate = new AccumulatedHeapProcessProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_ACCUMULATED_HEAP))
      {
         profilerDelegate = new AccumulatedHeapProfilerDelegate(profilerSection);
      }
      else if (profilerType.equals(PROFILER_TYPE_USER))
      {
         profilerDelegate = new UserProfilerDelegate(profilerSection);
         // User profiling should have the profiling parameters expanded.
         resizeDialog(true);
         profilerSection.setExpanded(true);
      }
      else
      {
         profilerDelegate = null;
      }
      profilerSection.layout(true);

      if (!profilerType.equals(PROFILER_TYPE_USER))
      {
         userReportNumberText.setText("");
      }
      userReportNumberText.setEnabled(profilerType.equals(PROFILER_TYPE_USER));

      executionUnitCombo.setEnabled(!profilerType.equals(PROFILER_TYPE_HEAP) &&
                                    !profilerType.equals(PROFILER_TYPE_ACCUMULATED_HEAP_PROCESS) &&
                                    !profilerType.equals(PROFILER_TYPE_ACCUMULATED_HEAP) &&
                                    !profilerType.equals(PROFILER_TYPE_USER));
      if (!executionUnitCombo.isEnabled())
      {
         executionUnitCombo.select(0);
      }

      if ((profilerDelegate != null) && restore)
      {
         profilerDelegate.restore();
      }

      validateDialog();
   }

   private void populateExecutionUnitCombo()
   {
      short numExecutionUnits = 1;
      boolean hasAllExecutionUnitsSupport = false;

      if ((target != null) && (target.getNumExecutionUnits() > 0))
      {
         numExecutionUnits = target.getNumExecutionUnits();
         hasAllExecutionUnitsSupport = target.hasAllExecutionUnitsSupport();
      }

      executionUnitCombo.removeAll();

      for (short i = 0; i < numExecutionUnits; i++)
      {
         executionUnitCombo.add(toU16String(i));
      }
      if ((numExecutionUnits > 1) && hasAllExecutionUnitsSupport)
      {
         executionUnitCombo.add(ALL_EXECUTION_UNITS_STR);
      }

      executionUnitCombo.setVisibleItemCount(executionUnitCombo.getItemCount());
      executionUnitCombo.select(0);
   }

   private void removeProfilerDelegate()
   {
      if (profilerDelegate != null)
      {
         Composite comp = profilerDelegate.getComposite();
         if ((comp != null) && !comp.isDisposed())
         {
            comp.dispose();
         }
      }
   }

   private void resizeDialog(boolean expand)
   {
      Shell shell = getShell();
      int width = shell.getSize().x;
      int height = shell.getSize().y;
      int extraHeight = convertVerticalDLUsToPixels(200);
      if (expand && !profilerSection.isExpanded())
      {
         height += extraHeight;
      }
      else if (!expand && profilerSection.isExpanded())
      {
         height -= extraHeight;
      }
      shell.setSize(width, height);
   }

   private void validateDialog()
   {
      String errorMessage = null;
      boolean profilerValid = false;

      if (targetCombo.getText().length() == 0)
      {
         errorMessage = "Target not specified";
      }
      else if (profilerTypeCombo.getText().length() == 0)
      {
         errorMessage = "Type of profiler not specified";
      }
      else if (profilerTypeCombo.getText().equals(PROFILER_TYPE_USER))
      {
         String text = userReportNumberText.getText().trim();
         if (text.length() == 0)
         {
            errorMessage = "User report number not specified";
         }
         else
         {
            errorMessage = isValidU32(text, "Invalid user report number");
         }
      }

      profilerValid = (errorMessage == null);
      if (profilerValid && (profilerDelegate != null))
      {
         errorMessage = profilerDelegate.validate();
      }
      setErrorMessage(errorMessage);
      if (profilerDelegate != null)
      {
         profilerDelegate.setRestoreEnabled(profilerValid);
      }
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
      profilerType = profilerTypeCombo.getText();
      userReportNumber = createUserReportNumber();
      executionUnit = createExecutionUnit();
      integrationInterval = profilerDelegate.getIntegrationInterval();
      maxReports = profilerDelegate.getMaxReports();
      maxValuesReport = profilerDelegate.getMaxValuesReport();
      priorityLimit = profilerDelegate.getPriorityLimit();
      profilingProcesses = profilerDelegate.isProfilingProcesses();
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

   private int createUserReportNumber()
   {
      int result = 0;
      try
      {
         String str = userReportNumberText.getText().trim();
         result = ((str.length() > 0) ? StringUtils.parseU32(str) : 0);
      }
      catch (NumberFormatException ignore) {}
      return result;
   }

   private short createExecutionUnit()
   {
      String str = executionUnitCombo.getText();
      if (str.equals(ALL_EXECUTION_UNITS_STR))
      {
         return Target.ALL_EXECUTION_UNITS;
      }
      else
      {
         return ((str.length() > 0) ? StringUtils.parseU16(str) : 0);
      }
   }

   private void setExecutionUnit(short value)
   {
      if (value == Target.ALL_EXECUTION_UNITS)
      {
         executionUnitCombo.setText(ALL_EXECUTION_UNITS_STR);
      }
      else
      {
         executionUnitCombo.setText(toU16String(value));
      }
   }

   private static String toU16String(short s)
   {
      return Integer.toString(0xFFFF & s);
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

   private static String isValidU32(String text, int min, String errorMessage)
   {
      String msg = null;
      if (text.trim().length() > 0)
      {
         try
         {
            int value = StringUtils.parseU32(text);
            if ((0xFFFFFFFFL & value) < (0xFFFFFFFFL & min))
            {
               msg = errorMessage;
            }
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

   public String getProfilerType()
   {
      return profilerType;
   }

   public int getUserReportNumber()
   {
      return userReportNumber;
   }

   public short getExecutionUnit()
   {
      return executionUnit;
   }

   public int getIntegrationInterval()
   {
      return integrationInterval;
   }

   public int getMaxReports()
   {
      return maxReports;
   }

   public int getMaxValuesReport()
   {
      return maxValuesReport;
   }

   public int getPriorityLimit()
   {
      return priorityLimit;
   }

   public boolean isProfilingProcesses()
   {
      return profilingProcesses;
   }

   public boolean close()
   {
      if (toolkit != null)
      {
         toolkit.dispose();
      }
      return super.close();
   }

   private class TargetSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleTargetComboSelected(true);
      }
   }

   private class ProfilerTypeSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleProfilerTypeComboSelected(true);
      }
   }

   private class ExpansionHandler extends ExpansionAdapter
   {
      public void expansionStateChanging(ExpansionEvent event)
      {
         resizeDialog(event.getState());
      }
   }

   private class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }

   private abstract class ProfilerDelegate
   {
      static final String INTEGRATION_INTERVAL_HELP =
         "The integration interval determines how often a profiling report " +
         "is generated. Decreasing this value improves the resolution of the " +
         "profiling but also increases the required frequency of target to " +
         "host data transfers and the risk of losing reports.";

      static final String MAX_REPORTS_HELP =
         "The number of reports on target determines how many profiling " +
         "reports can be stored in the target's circular report buffer. " +
         "Increasing this value decreases the required frequency of target " +
         "to host data transfers and the risk of losing reports.";

      protected Composite comp;
      protected Text integrationIntervalText;
      protected Text maxReportsText;
      protected Text maxValuesReportText;
      protected Text priorityLimitText;
      protected Button profilingProcessesCheckbox;
      protected Button restoreButton;

      public Composite getComposite()
      {
         return comp;
      }

      public void clear()
      {
         if (integrationIntervalText != null)
         {
            integrationIntervalText.setText("");
         }
         if (maxReportsText != null)
         {
            maxReportsText.setText("");
         }
         if (maxValuesReportText != null)
         {
            maxValuesReportText.setText("");
         }
         if (priorityLimitText != null)
         {
            priorityLimitText.setText("");
         }
         if (profilingProcessesCheckbox != null)
         {
            profilingProcessesCheckbox.setSelection(false);
         }
      }

      public String validate()
      {
         String errorMessage = null;
         String text;

         if (integrationIntervalText != null)
         {
            text = integrationIntervalText.getText().trim();
            if (text.length() == 0)
            {
               errorMessage = "Integration interval not specified";
            }
            else
            {
               errorMessage = isValidU32(text, 1, "Invalid integration interval");
            }
         }

         if ((maxReportsText != null) && (errorMessage == null))
         {
            text = maxReportsText.getText().trim();
            if (text.length() == 0)
            {
               errorMessage = "Number of reports on target not specified";
            }
            else
            {
               errorMessage = isValidU32(text, 3, "Invalid number of reports on target");
            }
         }

         if ((maxValuesReportText != null) && (errorMessage == null))
         {
            text = maxValuesReportText.getText().trim();
            if (text.length() == 0)
            {
               errorMessage = "Processes/values per report not specified";
            }
            else
            {
               errorMessage = isValidU32(text, "Invalid processes/values per report");
            }
         }

         if ((priorityLimitText != null) && (errorMessage == null))
         {
            text = priorityLimitText.getText().trim();
            if (text.length() == 0)
            {
               errorMessage = "Priority limit not specified";
            }
            else
            {
               errorMessage = isValidU32(text, "Invalid priority limit");
            }
         }

         return errorMessage;
      }

      public void restore()
      {
         if (restoreButton.isEnabled())
         {
            Target target = createTarget();
            if (target != null)
            {
               Job job = new RestoreJob(target, createUserReportNumber());
               job.schedule();
            }
         }
      }

      protected abstract void restore(IProgressMonitor monitor,
                                      Target target,
                                      int userReportNumber) throws IOException;

      public int getIntegrationInterval()
      {
         if (integrationIntervalText != null)
         {
            String str = integrationIntervalText.getText().trim();
            return ((str.length() > 0) ? StringUtils.parseU32(str) : 0);
         }
         else
         {
            return 0;
         }
      }

      public void setIntegrationInterval(int value)
      {
         if (integrationIntervalText != null)
         {
            integrationIntervalText.setText(toU32String(value));
         }
      }

      public int getMaxReports()
      {
         if (maxReportsText != null)
         {
            String str = maxReportsText.getText().trim();
            return ((str.length() > 0) ? StringUtils.parseU32(str) : 0);
         }
         else
         {
            return 0;
         }
      }

      public void setMaxReports(int value)
      {
         if (maxReportsText != null)
         {
            maxReportsText.setText(toU32String(value));
         }
      }

      public int getMaxValuesReport()
      {
         if (maxValuesReportText != null)
         {
            String str = maxValuesReportText.getText().trim();
            return ((str.length() > 0) ? StringUtils.parseU32(str) : 0);
         }
         else
         {
            return 0;
         }
      }

      public void setMaxValuesReport(int value)
      {
         if (maxValuesReportText != null)
         {
            maxValuesReportText.setText(toU32String(value));
         }
      }

      public int getPriorityLimit()
      {
         if (priorityLimitText != null)
         {
            String str = priorityLimitText.getText().trim();
            return ((str.length() > 0) ? StringUtils.parseU32(str) : 0);
         }
         else
         {
            return 0;
         }
      }

      public void setPriorityLimit(int value)
      {
         if (priorityLimitText != null)
         {
            priorityLimitText.setText(toU32String(value));
         }
      }

      public boolean isProfilingProcesses()
      {
         return ((profilingProcessesCheckbox != null) ?
                 profilingProcessesCheckbox.getSelection() : false);
      }

      public void setProfilingProcesses(boolean value)
      {
         if (profilingProcessesCheckbox != null)
         {
            profilingProcessesCheckbox.setSelection(value);
         }
      }

      public void setRestoreEnabled(boolean enabled)
      {
         restoreButton.setEnabled(enabled);
      }

      private String toU32String(int i)
      {
         return Long.toString(0xFFFFFFFFL & i);
      }

      class RestoreHandler extends SelectionAdapter
      {
         public void widgetSelected(SelectionEvent event)
         {
            restore();
         }
      }

      class RestoreJob extends Job
      {
         private final Target target;
         private final int userReportNumber;

         RestoreJob(Target target, int userReportNumber)
         {
            super("Restoring profiling parameters");
            setUser(true);
            setPriority(SHORT);
            this.target = target;
            this.userReportNumber = userReportNumber;
         }

         protected IStatus run(IProgressMonitor monitor)
         {
            try
            {
               monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
               restore(monitor, target, userReportNumber);
               return (monitor.isCanceled() ?
                       Status.CANCEL_STATUS : Status.OK_STATUS);
            }
            catch (OperationCanceledException e)
            {
               return Status.CANCEL_STATUS;
            }
            catch (ServiceException e)
            {
               return ProfilerPlugin.createErrorStatus(
                  "Error reported from target when restoring the profiling parameters", e);
            }
            catch (IOException e)
            {
               return ProfilerPlugin.createErrorStatus(
                  "Error communicating with target when restoring the profiling parameters", e);
            }
            catch (Exception e)
            {
               return ProfilerPlugin.createErrorStatus(
                  "Error when restoring the profiling parameters", e);
            }
            finally
            {
               monitor.done();
            }
         }
      }

      class UpdateRunner implements Runnable
      {
         private final int integrationInterval;
         private final int maxReports;
         private final int maxValuesReport;
         private final int priorityLimit;
         private final boolean profilingProcesses;

         UpdateRunner(int integrationInterval,
                      int maxReports,
                      int maxValuesReport,
                      int priorityLimit,
                      boolean profilingProcesses)
         {
            this.integrationInterval = integrationInterval;
            this.maxReports = maxReports;
            this.maxValuesReport = maxValuesReport;
            this.priorityLimit = priorityLimit;
            this.profilingProcesses = profilingProcesses;
         }

         public void run()
         {
            if (!comp.isDisposed())
            {
               setIntegrationInterval(integrationInterval);
               setMaxReports(maxReports);
               setMaxValuesReport(maxValuesReport);
               setPriorityLimit(priorityLimit);
               setProfilingProcesses(profilingProcesses);
            }
         }
      }
   }

   private class CPUProfilerDelegate extends ProfilerDelegate
   {
      private static final String PRIORITY_LIMIT_HELP =
         "Only processes with a priority higher (i.e. lower value) than the " +
         "priority limit will be included in the profiling. Priority limit " +
         "33 includes background processes, prioritized processes with " +
         "priority 0 - 31, and interrupt processes. Priority limit 0 " +
         "includes only interrupt processes.";

      public CPUProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetCPUReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetCPUReportsEnabledSupport =
            (target != null) && target.hasSetCPUReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetCPUReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetCPUReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(PRIORITY_LIMIT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Priority Limit:");

         priorityLimitText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         priorityLimitText.setEnabled(hasSetCPUReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         priorityLimitText.setLayoutData(gd);
         priorityLimitText.addModifyListener(validationHandler);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         CPUReportsEnabledInfo enabledInfo = target.getCPUReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  0,
                  enabledInfo.getPriority(),
                  false);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class CPUPriorityProfilerDelegate extends ProfilerDelegate
   {
      public CPUPriorityProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetCPUPriorityReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetCPUPriorityReportsEnabledSupport =
            (target != null) && target.hasSetCPUPriorityReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetCPUPriorityReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetCPUPriorityReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         CPUPriorityReportsEnabledInfo enabledInfo =
            target.getCPUPriorityReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  0,
                  0,
                  false);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class CPUProcessProfilerDelegate extends ProfilerDelegate
   {
      private static final String MAX_PROCS_PER_REPORT_HELP =
         "The number of processes per report determines how many processes " +
         "are reported individually in a profiling report. Increasing this " +
         "value increases the chances that all relevant processes can be " +
         "reported individually instead of included in the \"Others\" category.";

      public CPUProcessProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetCPUProcessReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetCPUProcessReportsEnabledSupport =
            (target != null) && target.hasSetCPUProcessReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetCPUProcessReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetCPUProcessReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_PROCS_PER_REPORT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Processes per Report:");

         maxValuesReportText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxValuesReportText.setEnabled(hasSetCPUProcessReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxValuesReportText.setLayoutData(gd);
         maxValuesReportText.addModifyListener(validationHandler);

         profilingProcessesCheckbox = new Button(comp, SWT.CHECK);
         profilingProcessesCheckbox.setText("Translate profiled IDs to process names");
         gd = new GridData();
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         profilingProcessesCheckbox.setLayoutData(gd);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         CPUProcessReportsEnabledInfo enabledInfo =
            target.getCPUProcessReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  enabledInfo.getMaxProcessesPerReport(),
                  0,
                  true);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class CPUBlockProfilerDelegate extends ProfilerDelegate
   {
      private static final String MAX_BLOCKS_PER_REPORT_HELP =
         "The number of blocks per report determines how many blocks are " +
         "reported individually in a profiling report. Increasing this value " +
         "increases the chances that all relevant blocks can be reported " +
         "individually instead of included in the \"Others\" category.";

      public CPUBlockProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetCPUBlockReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetCPUBlockReportsEnabledSupport =
            (target != null) && target.hasSetCPUBlockReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetCPUBlockReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetCPUBlockReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_BLOCKS_PER_REPORT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Blocks per Report:");

         maxValuesReportText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxValuesReportText.setEnabled(hasSetCPUBlockReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxValuesReportText.setLayoutData(gd);
         maxValuesReportText.addModifyListener(validationHandler);

         profilingProcessesCheckbox = new Button(comp, SWT.CHECK);
         profilingProcessesCheckbox.setText("Translate profiled IDs to block names");
         gd = new GridData();
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         profilingProcessesCheckbox.setLayoutData(gd);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         CPUBlockReportsEnabledInfo enabledInfo =
            target.getCPUBlockReportsEnabled(monitor);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  enabledInfo.getMaxBlocksPerReport(),
                  0,
                  true);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class HeapProfilerDelegate extends ProfilerDelegate
   {
      private static final int HEAP_USER_REPORT_NUMBER = 51;

      private static final String MAX_PROCS_PER_REPORT_HELP =
         "The number of processes per report determines how many processes " +
         "are reported individually in a profiling report. Increasing this " +
         "value increases the chances that all relevant processes can be " +
         "reported individually instead of included in the \"Others\" category.";

      public HeapProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetUserReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetUserReportsEnabledSupport =
            (target != null) && target.hasSetUserReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_PROCS_PER_REPORT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Processes per Report:");

         maxValuesReportText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxValuesReportText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxValuesReportText.setLayoutData(gd);
         maxValuesReportText.addModifyListener(validationHandler);

         profilingProcessesCheckbox = new Button(comp, SWT.CHECK);
         profilingProcessesCheckbox.setText("Translate profiled IDs to process names");
         gd = new GridData();
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         profilingProcessesCheckbox.setLayoutData(gd);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         UserReportsEnabledInfo enabledInfo =
            target.getUserReportsEnabled(monitor, HEAP_USER_REPORT_NUMBER);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  enabledInfo.getMaxValuesPerReport(),
                  0,
                  true);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class AccumulatedHeapProcessProfilerDelegate extends ProfilerDelegate
   {
      private static final int ACCUMULATED_HEAP_PROCESS_USER_REPORT_NUMBER = 53;

      private static final String MAX_PROCS_PER_REPORT_HELP =
         "The number of processes per report determines how many processes " +
         "are reported individually in a profiling report. Increasing this " +
         "value increases the chances that all relevant processes can be " +
         "reported individually instead of included in the \"Others\" category.";

      public AccumulatedHeapProcessProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetUserReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetUserReportsEnabledSupport =
            (target != null) && target.hasSetUserReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_PROCS_PER_REPORT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Processes per Report:");

         maxValuesReportText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxValuesReportText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxValuesReportText.setLayoutData(gd);
         maxValuesReportText.addModifyListener(validationHandler);

         profilingProcessesCheckbox = new Button(comp, SWT.CHECK);
         profilingProcessesCheckbox.setText("Translate profiled IDs to process names");
         gd = new GridData();
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         profilingProcessesCheckbox.setLayoutData(gd);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         UserReportsEnabledInfo enabledInfo =
            target.getUserReportsEnabled(monitor, ACCUMULATED_HEAP_PROCESS_USER_REPORT_NUMBER);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  enabledInfo.getMaxValuesPerReport(),
                  0,
                  true);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class AccumulatedHeapProfilerDelegate extends ProfilerDelegate
   {
      private static final int ACCUMULATED_HEAP_USER_REPORT_NUMBER = 52;

      private static final String MAX_HEAPS_PER_REPORT_HELP =
         "The number of heaps per report determines how many heaps " +
         "are reported individually in a profiling report. Increasing this " +
         "value increases the chances that all relevant heaps can be " +
         "reported individually instead of included in the \"Others\" category.";

      public AccumulatedHeapProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetUserReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetUserReportsEnabledSupport =
            (target != null) && target.hasSetUserReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_HEAPS_PER_REPORT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Heaps per Report:");

         maxValuesReportText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxValuesReportText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxValuesReportText.setLayoutData(gd);
         maxValuesReportText.addModifyListener(validationHandler);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         UserReportsEnabledInfo enabledInfo =
            target.getUserReportsEnabled(monitor, ACCUMULATED_HEAP_USER_REPORT_NUMBER);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  enabledInfo.getMaxValuesPerReport(),
                  0,
                  false);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }

   private class UserProfilerDelegate extends ProfilerDelegate
   {
      private static final String USER_REPORT_NUMBER_HELP =
         "After entering a user report number, press the \"Restore from " +
         "Target\" button to retrieve the default profiling paramaters.";

      private static final String MAX_VALUES_PER_REPORT_HELP =
         "The number of values per report determines how many values are " +
         "reported individually in a profiling report. Increasing this value " +
         "increases the chances that all relevant values can be reported " +
         "individually instead of included in the \"Others\" category.";

      public UserProfilerDelegate(ExpandableComposite parent)
      {
         boolean hasSetUserReportsEnabledSupport;
         ValidationHandler validationHandler;
         GridData gd;
         Label label;

         hasSetUserReportsEnabledSupport =
            (target != null) && target.hasSetUserReportsEnabledSupport();
         validationHandler = new ValidationHandler();
         comp = new Composite(parent, SWT.BORDER);
         comp.setLayoutData(new GridData(GridData.FILL_BOTH));
         comp.setLayout(new GridLayout(2, false));
         toolkit.adapt(comp);
         parent.setClient(comp);

         label = new Label(comp, SWT.WRAP);
         label.setText(USER_REPORT_NUMBER_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.WRAP);
         label.setText(INTEGRATION_INTERVAL_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Integration Interval (ticks):");

         integrationIntervalText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         integrationIntervalText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         integrationIntervalText.setLayoutData(gd);
         integrationIntervalText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_REPORTS_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Number of Reports on Target:");

         maxReportsText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxReportsText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxReportsText.setLayoutData(gd);
         maxReportsText.addModifyListener(validationHandler);

         label = new Label(comp, SWT.WRAP);
         label.setText(MAX_VALUES_PER_REPORT_HELP);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         label.setLayoutData(gd);

         label = new Label(comp, SWT.NONE);
         label.setText("Values per Report:");

         maxValuesReportText = new Text(comp, SWT.SINGLE | SWT.BORDER);
         maxValuesReportText.setEnabled(hasSetUserReportsEnabledSupport);
         gd = new GridData(GridData.FILL_HORIZONTAL);
         maxValuesReportText.setLayoutData(gd);
         maxValuesReportText.addModifyListener(validationHandler);

         profilingProcessesCheckbox = new Button(comp, SWT.CHECK);
         profilingProcessesCheckbox.setText("Translate profiled IDs to process names");
         gd = new GridData();
         gd.horizontalSpan = 2;
         gd.verticalIndent = 5;
         profilingProcessesCheckbox.setLayoutData(gd);

         restoreButton = new Button(comp, SWT.PUSH);
         restoreButton.setText("Restore from Target");
         gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_END);
         gd.horizontalSpan = 2;
         gd.grabExcessVerticalSpace = true;
         restoreButton.setLayoutData(gd);
         restoreButton.addSelectionListener(new RestoreHandler());
      }

      protected void restore(IProgressMonitor monitor,
                             Target target,
                             int userReportNumber)
         throws IOException
      {
         UserReportsEnabledInfo enabledInfo =
            target.getUserReportsEnabled(monitor, userReportNumber);
         if (enabledInfo != null)
         {
            UpdateRunner updateRunner = new UpdateRunner(
                  enabledInfo.getInterval(),
                  enabledInfo.getMaxReports(),
                  enabledInfo.getMaxValuesPerReport(),
                  0,
                  false);
            if (!comp.isDisposed())
            {
               comp.getDisplay().asyncExec(updateRunner);
            }
         }
      }
   }
}
