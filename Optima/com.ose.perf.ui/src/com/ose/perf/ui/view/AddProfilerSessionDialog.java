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

package com.ose.perf.ui.view;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.perf.ui.ProfilerPlugin;
import com.ose.system.PerformanceCounterEnabledInfo;
import com.ose.system.PerformanceCounterInfo;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.ui.util.StringUtils;

public class AddProfilerSessionDialog extends Dialog
{
   private static final String ALL_EXECUTION_UNITS_STR = "All";

   private List targets;
   private String targetName;

   private Target target;
   private short executionUnit;
   private PerformanceCounterInfo performanceCounter;
   private long performanceCounterValue;
   private int maxReports;

   private Combo targetCombo;
   private Combo executionUnitCombo;
   private Combo performanceCounterCombo;
   private Text performanceCounterValueText;
   private Text maxReportsText;

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
      subComp.setLayout(new GridLayout(2, false));
      validationHandler = new ValidationHandler();

      label = new Label(subComp, SWT.NONE);
      label.setText("Target:");

      targetCombo = new Combo(subComp, SWT.READ_ONLY);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.widthHint = convertHorizontalDLUsToPixels(180);
      targetCombo.setLayoutData(gd);
      for (Iterator i = targets.iterator(); i.hasNext();)
      {
         Target target = (Target) i.next();
         targetCombo.add(target.toString());
      }
      targetCombo.addModifyListener(validationHandler);
      targetCombo.addSelectionListener(new TargetSelectionHandler());

      label = new Label(subComp, SWT.NONE);
      label.setText("Execution Unit:");

      executionUnitCombo = new Combo(subComp, SWT.READ_ONLY);
      executionUnitCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      executionUnitCombo.addModifyListener(validationHandler);

      label = new Label(subComp, SWT.NONE);
      label.setText("Performance Counter:");

      performanceCounterCombo = new Combo(subComp, SWT.READ_ONLY);
      performanceCounterCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      performanceCounterCombo.addModifyListener(validationHandler);
      performanceCounterCombo.addSelectionListener(new PerformanceCounterSelectionHandler());

      label = new Label(subComp, SWT.NONE);
      label.setText("Trigger Value:");

      performanceCounterValueText = new Text(subComp, SWT.SINGLE | SWT.BORDER);
      performanceCounterValueText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      performanceCounterValueText.addModifyListener(validationHandler);

      label = new Label(subComp, SWT.NONE);
      label.setText("Number of Reports on Target:");

      maxReportsText = new Text(subComp, SWT.SINGLE | SWT.BORDER);
      maxReportsText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      maxReportsText.addModifyListener(validationHandler);

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

   private void initContents()
   {
      if (targetName != null)
      {
         targetCombo.setText(targetName);
         targetCombo.select(targetCombo.getSelectionIndex());
         handleTargetComboSelected(false);
      }
      else
      {
         IDialogSettings section = ProfilerPlugin.getDefault()
               .getDialogSettings().getSection("AddProfilerSessionDialog");
         if (section != null)
         {
            targetCombo.setText(section.get("target"));
            targetCombo.select(targetCombo.getSelectionIndex());
            handleTargetComboSelected(true);
         }
      }
   }

   private void loadDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = ProfilerPlugin.getDefault().getDialogSettings();
      section = settings.getSection("AddProfilerSessionDialog");
      if (section != null)
      {
         setExecutionUnit((short) section.getInt("executionUnit"));
         performanceCounterCombo.setText(section.get("performanceCounter"));
         performanceCounterCombo.select(performanceCounterCombo.getSelectionIndex());
         performanceCounterValueText.setText(section.get("performanceCounterValue"));
         maxReportsText.setText(section.get("maxReports"));
      }
   }

   private void saveDialogSettings()
   {
      IDialogSettings settings;
      IDialogSettings section;

      settings = ProfilerPlugin.getDefault().getDialogSettings();
      section = settings.addNewSection("AddProfilerSessionDialog");

      section.put("target", targetCombo.getText());
      section.put("executionUnit", createExecutionUnit());
      section.put("performanceCounter", performanceCounterCombo.getText());
      section.put("performanceCounterValue", performanceCounterValueText.getText().trim());
      section.put("maxReports", maxReportsText.getText().trim());
   }

   private void handleTargetComboSelected(boolean loadDialogSettings)
   {
      Target oldTarget = target;
      target = createTarget();
      if (target != oldTarget)
      {
         populateExecutionUnitCombo();
         if ((target != null) && target.hasPerformanceCounterSupport())
         {
            Job job = loadDialogSettings ? new LoadDialogSettingsJob(target)
                  : new GetPerformanceCountersJob(target);
            job.schedule();
         }
      }
   }

   private void handlePerformanceCounterComboSelected()
   {
      if ((target != null) && target.hasGetPerformanceCounterEnabledSupport())
      {
         PerformanceCounterInfo pc = createPerformanceCounter();
         short eu = createExecutionUnit();
         Job job = new GetPerformanceCounterParametersJob(target, eu, pc);
         job.schedule();
      }
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

   private void populatePerformanceCounterCombo(
         PerformanceCounterInfo[] performanceCounters)
   {
      performanceCounterCombo.removeAll();
      for (int i = 0; i < performanceCounters.length; i++)
      {
         PerformanceCounterInfo pc = performanceCounters[i];
         performanceCounterCombo.add(pc.getName());
         performanceCounterCombo.setData(Integer.toString(i), pc);
      }
      performanceCounterCombo.setVisibleItemCount(
         (performanceCounters.length < 20) ? performanceCounters.length : 20);
      performanceCounterCombo.select(0);
   }

   private void validateDialog()
   {
      String errorMessage = null;

      if (targetCombo.getText().length() == 0)
      {
         errorMessage = "Target not specified";
      }
      else if (executionUnitCombo.getText().length() == 0)
      {
         errorMessage = "Execution unit not specified";
      }
      else if (performanceCounterCombo.getText().length() == 0)
      {
         errorMessage = "Performance counter not specified";
      }

      if (errorMessage == null)
      {
         if (performanceCounterValueText.getText().trim().length() == 0)
         {
            errorMessage = "Trigger value not specified";
         }
         else
         {
            errorMessage = isValidU64(performanceCounterValueText.getText(),
                                      "Invalid trigger value");
         }
      }

      if (errorMessage == null)
      {
         if (maxReportsText.getText().trim().length() == 0)
         {
            errorMessage = "Number of reports on target not specified";
         }
         else
         {
            errorMessage = isValidU32(maxReportsText.getText(),
                                      "Invalid number of reports on target");
         }
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
      executionUnit = createExecutionUnit();
      performanceCounter = createPerformanceCounter();
      performanceCounterValue = createPerformanceCounterValue();
      maxReports = createMaxReports();
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

   private PerformanceCounterInfo createPerformanceCounter()
   {
      PerformanceCounterInfo pc = null;
      int selection = performanceCounterCombo.getSelectionIndex();
      if (selection > -1)
      {
         pc = (PerformanceCounterInfo) performanceCounterCombo.getData(
               Integer.toString(selection));
      }
      return pc;
   }

   private long createPerformanceCounterValue()
   {
      long result = 0;
      try
      {
         String str = performanceCounterValueText.getText().trim();
         result = ((str.length() > 0) ? StringUtils.parseU64(str) : 0);
      } catch (NumberFormatException ignore) {}
      return result;
   }

   private int createMaxReports()
   {
      int result = 0;
      try
      {
         String str = maxReportsText.getText().trim();
         result = ((str.length() > 0) ? StringUtils.parseU32(str) : 0);
      } catch (NumberFormatException ignore) {}
      return result;
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

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private static String toU16String(short s)
   {
      return Integer.toString(0xFFFF & s);
   }

   private static String toU32String(int i)
   {
      return Long.toString(0xFFFFFFFFL & i);
   }

   private static String toU64String(long l)
   {
      return (l < 0) ? "0x" + Long.toHexString(l).toUpperCase() : Long.toString(l);
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

   private static String isValidU64(String text, String errorMessage)
   {
      String msg = null;
      if (text.trim().length() > 0)
      {
         try
         {
            StringUtils.parseU64(text);
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

   public short getExecutionUnit()
   {
      return executionUnit;
   }

   public PerformanceCounterInfo getPerformanceCounter()
   {
      return performanceCounter;
   }

   public long getPerformanceCounterValue()
   {
      return performanceCounterValue;
   }

   public int getMaxReports()
   {
      return maxReports;
   }

   private class TargetSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handleTargetComboSelected(false);
      }
   }

   private class PerformanceCounterSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         handlePerformanceCounterComboSelected();
      }
   }

   private class ValidationHandler implements ModifyListener
   {
      public void modifyText(ModifyEvent event)
      {
         validateDialog();
      }
   }

   private abstract class AbstractGetPerformanceCountersJob extends Job
   {
      private final Target target;

      AbstractGetPerformanceCountersJob(Target target)
      {
         super("Retrieving performance counters");
         setUser(true);
         setPriority(SHORT);
         this.target = target;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            PerformanceCounterInfo[] performanceCounters =
               target.getPerformanceCounters(monitor);
            updateUI(performanceCounters);
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
               "Error reported from target when retrieving the performance counters", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error communicating with target when retrieving the performance counters", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when retrieving the performance counters", e);
         }
         finally
         {
            monitor.done();
         }
      }

      abstract void updateUI(PerformanceCounterInfo[] performanceCounters);
   }

   private class LoadDialogSettingsJob
      extends AbstractGetPerformanceCountersJob
   {
      LoadDialogSettingsJob(Target target)
      {
         super(target);
      }

      void updateUI(PerformanceCounterInfo[] performanceCounters)
      {
         LoadDialogSettingsRunner loadRunner =
            new LoadDialogSettingsRunner(performanceCounters);
         asyncExec(loadRunner);
      }
   }

   private class GetPerformanceCountersJob
      extends AbstractGetPerformanceCountersJob
   {
      GetPerformanceCountersJob(Target target)
      {
         super(target);
      }

      void updateUI(PerformanceCounterInfo[] performanceCounters)
      {
         UpdatePerformanceCountersRunner updateRunner =
            new UpdatePerformanceCountersRunner(performanceCounters);
         asyncExec(updateRunner);
      }
   }

   private class GetPerformanceCounterParametersJob extends Job
   {
      private final Target target;
      private final short eu;
      private final PerformanceCounterInfo pc;

      GetPerformanceCounterParametersJob(Target target,
                                         short eu,
                                         PerformanceCounterInfo pc)
      {
         super("Retrieving performance counter information");
         setUser(true);
         setPriority(SHORT);
         this.target = target;
         this.eu = eu;
         this.pc = pc;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            PerformanceCounterEnabledInfo enabledInfo =
               target.getPerformanceCounterEnabled(monitor, eu, pc.getType());
            UpdatePerformanceCounterParametersRunner updateRunner =
               new UpdatePerformanceCounterParametersRunner(
                     enabledInfo.getValue(), enabledInfo.getMaxReports());
            asyncExec(updateRunner);
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return ProfilerPlugin.createErrorStatus("Error reported from " +
               "target when retrieving performance counter information", e);
         }
         catch (IOException e)
         {
            return ProfilerPlugin.createErrorStatus("Error communicating " +
               "with target when retrieving performance counter information", e);
         }
         catch (Exception e)
         {
            return ProfilerPlugin.createErrorStatus(
               "Error when retrieving performance counter information", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   private class LoadDialogSettingsRunner implements Runnable
   {
      private final PerformanceCounterInfo[] performanceCounters;

      LoadDialogSettingsRunner(PerformanceCounterInfo[] performanceCounters)
      {
         this.performanceCounters = performanceCounters;
      }

      public void run()
      {
         populatePerformanceCounterCombo(performanceCounters);
         loadDialogSettings();
         validateDialog();
      }
   }

   private class UpdatePerformanceCountersRunner implements Runnable
   {
      private final PerformanceCounterInfo[] performanceCounters;

      UpdatePerformanceCountersRunner(PerformanceCounterInfo[] performanceCounters)
      {
         this.performanceCounters = performanceCounters;
      }

      public void run()
      {
         populatePerformanceCounterCombo(performanceCounters);
         handlePerformanceCounterComboSelected();
      }
   }

   private class UpdatePerformanceCounterParametersRunner implements Runnable
   {
      private final long value;
      private final int maxReports;

      UpdatePerformanceCounterParametersRunner(long value, int maxReports)
      {
         this.value = value;
         this.maxReports = maxReports;
      }

      public void run()
      {
         performanceCounterValueText.setText(toU64String(value));
         maxReportsText.setText(toU32String(maxReports));
         validateDialog();
      }
   }
}
