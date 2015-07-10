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

package com.ose.system.ui.views.pooloptimizer;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import com.ose.system.Gate;
import com.ose.system.Pool;
import com.ose.system.PoolBufferSizeInfo;
import com.ose.system.ServiceException;
import com.ose.system.SignalInfo;
import com.ose.system.StackInfo;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;

public class PoolOptimizerView extends ViewPart
{
   private OptimizerAction optimizerAction;

   private Font font;

   private Text currentSignalBufferSizesText;

   private Text currentSignalMemoryUsageText;

   private Text currentSignalSlackText;

   private Text newSignalBufferSizesText;

   private Text newSignalMemoryUsageText;

   private Text gainSignalText;

   private Text newSignalSlackText;

   private Text currentStackBufferSizesText;

   private Text currentStackMemoryUsageText;

   private Text currentStackSlackText;

   private Text newStackBufferSizesText;

   private Text newStackMemoryUsageText;

   private Text gainStackText;

   private Text newStackSlackText;

   public void createPartControl(Composite parent)
   {
      Color white;
      FontData fontData;
      GridLayout gridLayout;
      GridData gd;
      Label label;
      ScrolledComposite scrolledComp;
      Composite subComp;
      Group signalGroup;
      Group stackGroup;

      white = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);
      fontData = new FontData();
      fontData.setHeight(10);
      fontData.setStyle(SWT.BOLD);
      font = new Font(parent.getDisplay(), fontData);

      parent.setBackground(white);

      scrolledComp = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
      scrolledComp.setBackground(white);
      scrolledComp.setExpandHorizontal(true);
      scrolledComp.setExpandVertical(true);
      scrolledComp.setLayout(new FillLayout());

      subComp = new Composite(scrolledComp, SWT.NONE);
      subComp.setBackground(white);
      gridLayout = new GridLayout(2, true);
      gridLayout.horizontalSpacing = 20;
      subComp.setLayout(gridLayout);

      signalGroup = new Group(subComp, SWT.NONE);
      signalGroup.setText("Signal Buffer Analysis");
      signalGroup.setBackground(white);
      signalGroup.setFont(font);
      signalGroup.setLayout(new GridLayout(3, false));
      signalGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Current Configured Buffer Sizes:");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      currentSignalBufferSizesText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      currentSignalBufferSizesText.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      currentSignalBufferSizesText.setLayoutData(gd);

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Usage:");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Slack:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      currentSignalMemoryUsageText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      currentSignalMemoryUsageText.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      currentSignalMemoryUsageText.setLayoutData(gd);

      currentSignalSlackText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      currentSignalSlackText.setBackground(white);
      currentSignalSlackText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(signalGroup, SWT.NONE);
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Suggested Configured Buffer Sizes:");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      newSignalBufferSizesText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      newSignalBufferSizesText.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      newSignalBufferSizesText.setLayoutData(gd);

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Usage:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Gain:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Slack:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      newSignalMemoryUsageText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      newSignalMemoryUsageText.setBackground(white);
      newSignalMemoryUsageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      gainSignalText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      gainSignalText.setBackground(white);
      gainSignalText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      newSignalSlackText = new Text(signalGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      newSignalSlackText.setBackground(white);
      newSignalSlackText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(signalGroup, SWT.NONE);
      label.setText("Note: Use of set_sigsize() may invalidate the result");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      stackGroup = new Group(subComp, SWT.NONE);
      stackGroup.setText("Stack Buffer Analysis");
      stackGroup.setBackground(white);
      stackGroup.setFont(font);
      stackGroup.setLayout(new GridLayout(3, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.verticalAlignment = SWT.BEGINNING;
      stackGroup.setLayoutData(gd);

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Current Configured Buffer Sizes:");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      currentStackBufferSizesText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      currentStackBufferSizesText.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      currentStackBufferSizesText.setLayoutData(gd);

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Usage:");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Slack:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      currentStackMemoryUsageText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      currentStackMemoryUsageText.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      currentStackMemoryUsageText.setLayoutData(gd);

      currentStackSlackText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      currentStackSlackText.setBackground(white);
      currentStackSlackText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(stackGroup, SWT.NONE);
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Suggested Configured Buffer Sizes:");
      label.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      label.setLayoutData(gd);

      newStackBufferSizesText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      newStackBufferSizesText.setBackground(white);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      newStackBufferSizesText.setLayoutData(gd);

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Usage:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Gain:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(stackGroup, SWT.NONE);
      label.setText("Slack:");
      label.setBackground(white);
      label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      newStackMemoryUsageText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      newStackMemoryUsageText.setBackground(white);
      newStackMemoryUsageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      gainStackText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      gainStackText.setBackground(white);
      gainStackText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      newStackSlackText = new Text(stackGroup,
            SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);
      newStackSlackText.setBackground(white);
      newStackSlackText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      subComp.pack();
      scrolledComp.setMinSize(subComp.getSize());
      scrolledComp.setContent(subComp);

      createActions();
      contributeToActionBars();
      setContentDescription("Target: None, Pool: None");
   }

   private void createActions()
   {
      optimizerAction = new OptimizerAction();
   }

   private void contributeToActionBars()
   {
      IActionBars bars = getViewSite().getActionBars();
      fillLocalToolBar(bars.getToolBarManager());
   }

   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(optimizerAction);
   }

   public void dispose()
   {
      font.dispose();
      super.dispose();
   }

   public void setFocus()
   {
      // Nothing to de done.
   }

   public void show(Pool pool)
   {
      optimizerAction.run(pool);
   }

   private static void asyncExec(Runnable runnable)
   {
      Display display = PlatformUI.getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(runnable);
      }
   }

   private class OptimizerAction extends Action
   {
      OptimizerAction()
      {
         super("Run Pool Optimizer");
         setToolTipText("Run Pool Optimizer");
         setImageDescriptor(SharedImages.DESC_TOOL_OPTIMIZE);
      }

      public void run()
      {
         Job job;

         setEnabled(false);
         job = new GetTargetsJob();
         job.schedule();
      }

      public void run(Pool pool)
      {
         Job job;

         setEnabled(false);
         job = new GetTargetsJob(pool);
         job.schedule();
      }
   }

   private class GetTargetsJob extends Job
   {
      private final Pool pool;

      GetTargetsJob()
      {
         this(null);
      }

      GetTargetsJob(Pool pool)
      {
         super("Finding Targets");
         setPriority(SHORT);
         setUser(true);
         this.pool = pool;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            SystemModel systemModel;
            List livingTargets;
            Gate[] gates;
            OptimizerDialogRunner dialogRunner;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            systemModel = SystemModel.getInstance();
            livingTargets = new ArrayList();
            gates = systemModel.getGates();
            for (int i = 0; i < gates.length; i++)
            {
               Gate gate = gates[i];
               if (!gate.isKilled())
               {
                  Target[] targets = gate.getTargets();
                  for (int j = 0; j < targets.length; j++)
                  {
                     Target t = targets[j];
                     if (!t.isKilled() && t.hasPoolSupport())
                     {
                        livingTargets.add(t);
                     }
                  }
               }
            }

            if (pool != null)
            {
               dialogRunner = new OptimizerDialogRunner(livingTargets,
                                                        pool.getTarget(),
                                                        pool);
            }
            else
            {
               dialogRunner = new OptimizerDialogRunner(livingTargets);
            }

            asyncExec(dialogRunner);

            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (RuntimeException e)
         {
            optimizerAction.setEnabled(true);
            throw e;
         }
         finally
         {
            monitor.done();
         }
      }
   }

   private class OptimizerDialogRunner implements Runnable
   {
      private final List targets;
      private final Target target;
      private final Pool pool;

      OptimizerDialogRunner(List targets)
      {
         this(targets, null, null);
      }

      OptimizerDialogRunner(List targets, Target target, Pool pool)
      {
         this.targets = targets;
         this.target = target;
         this.pool = pool;
      }

      public void run()
      {
         Shell shell;
         PoolOptimizerDialog dialog;
         int result;

         shell = getSite().getShell().getDisplay().getActiveShell();
         if (target != null)
         {
            dialog = new PoolOptimizerDialog(shell, targets, target, pool);
         }
         else
         {
            dialog = new PoolOptimizerDialog(shell, targets);
         }
         result = dialog.open();
         if (result == Window.OK)
         {
            Target poolTarget = dialog.getTarget();
            if (poolTarget != null)
            {
               Job job = new OptimizerJob(poolTarget,
                                          dialog.getPoolId(),
                                          dialog.getLevels());
               IWorkbenchSiteProgressService siteService =
                  (IWorkbenchSiteProgressService)
                     getSite().getAdapter(IWorkbenchSiteProgressService.class);
               siteService.schedule(job, 0, true);
            }
         }
         else
         {
            optimizerAction.setEnabled(true);
         }
      }
   }

   private class OptimizerJob extends Job
   {
      private final Target target;
      private final int poolId;
      private final int levels;

      OptimizerJob(Target target, int poolId, int levels)
      {
         super("Running pool optimizing algorithm");
         setPriority(SHORT);
         setUser(true);
         this.target = target;
         this.poolId = poolId;
         this.levels = levels;
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         try
         {
            Pool[] pools;
            Pool pool = null;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);

            target.connect(monitor);

            pools = target.getPools();
            for (int i = 0; i < pools.length; i++)
            {
               Pool p = pools[i];
               if (p.getId() == poolId)
               {
                  pool = p;
                  break;
               }
            }

            if (pool == null)
            {
               return Status.CANCEL_STATUS;
            }

            // FIXME: Check for a feature instead of OS type.
            if (target.getOsType() == Target.OS_OSE_CK)
            {
               jointSignalsStacks(monitor, pool);
            }
            else
            {
               separateSignalsStacks(monitor, pool);
            }

            return (monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error reported from target when optimizing the pool for target " + target, e);
         }
         catch (IOException e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error communicating with target when optimizing the pool for target " + target, e);
         }
         catch (Exception e)
         {
            return SystemBrowserPlugin.createErrorStatus(
               "Error when optimizing the pool for target " + target, e);
         }
         finally
         {
            monitor.done();
            optimizerAction.setEnabled(true);
         }
      }

      private void separateSignalsStacks(IProgressMonitor monitor, Pool pool)
         throws IOException
      {
         PoolOptimizerAlgorithm optimizer;

         SignalInfo[] signals;
         PoolBufferSizeInfo[] signalBufferSizes;
         int largestSignalBufferSize;
         Integer[] signalSizes;
         int signalUsedMemory = 0;
         int signalSlackMemory = 0;
         PoolOptimizerResult signalOptimizerResult;

         StackInfo[] stacks;
         PoolBufferSizeInfo[] stackBufferSizes;
         int largestStackBufferSize;
         Integer[] stackSizes;
         int stackUsedMemory = 0;
         int stackSlackMemory = 0;
         PoolOptimizerResult stackOptimizerResult;

         optimizer = new ExplorerLevelOptimization(levels);

         // Signal optimization

         monitor.subTask("Optimizing signals");

         signals = pool.getFilteredPoolSignals(monitor, null);

         signalBufferSizes = pool.getSignalSizes();
         largestSignalBufferSize =
            signalBufferSizes[signalBufferSizes.length - 1].getSize();

         signalSizes = new Integer[signals.length];
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal;
            int signalSize;
            int signalBufferSize;

            signal = signals[i];
            signalSize = signal.getSize();
            signalBufferSize = signal.getBufferSize();
            signalUsedMemory += signalBufferSize;
            signalSlackMemory +=
               signalBufferSize - pool.getSignalAdmSize() - signalSize;
            signalSizes[i] = new Integer(signalSize);
         }

         signalOptimizerResult = optimizer.suggestBufferSizes(
               monitor,
               signalSizes,
               largestSignalBufferSize,
               pool.getSignalAlignment(),
               pool.getSignalAdmSize());

         // Stack optimization

         monitor.subTask("Optimizing stacks");

         stacks = pool.getFilteredPoolStacks(monitor, null);

         stackBufferSizes = pool.getStackSizes();
         largestStackBufferSize =
            stackBufferSizes[stackBufferSizes.length - 1].getSize();

         stackSizes = new Integer[stacks.length];
         for (int i = 0; i < stacks.length; i++)
         {
            StackInfo stack;
            int stackSize;
            int stackBufferSize;

            stack = stacks[i];
            stackSize = stack.getSize();
            stackBufferSize = stack.getBufferSize();
            stackUsedMemory += stackBufferSize;
            stackSlackMemory +=
               stackBufferSize - pool.getStackAdmSize() - stackSize;
            stackSizes[i] = new Integer(stackSize);
         }

         stackOptimizerResult = optimizer.suggestBufferSizes(
               monitor,
               stackSizes,
               largestStackBufferSize,
               pool.getStackAlignment(),
               pool.getStackAdmSize());

         // Display result

         asyncExec(new UpdateUIRunner(
               target,
               pool,
               signalUsedMemory,
               signalSlackMemory,
               signalOptimizerResult.getBufferSizes(),
               signalOptimizerResult.getUsedMemory(),
               signalOptimizerResult.getSlackMemory(),
               stackUsedMemory,
               stackSlackMemory,
               stackOptimizerResult.getBufferSizes(),
               stackOptimizerResult.getUsedMemory(),
               stackOptimizerResult.getSlackMemory()));
      }

      private void jointSignalsStacks(IProgressMonitor monitor, Pool pool)
         throws IOException
      {
         PoolOptimizerAlgorithm optimizer;
         SignalInfo[] signals;
         StackInfo[] stacks;
         PoolBufferSizeInfo[] confBufferSizes;
         int largestConfBufferSize;
         Integer[] signalStackSizes;
         int usedMemory = 0;
         int slackMemory = 0;
         PoolOptimizerResult optimizerResult;

         optimizer = new ExplorerLevelOptimization(levels);

         // Signal and stack optimization

         monitor.subTask("Optimizing signals and stacks");

         signals = pool.getFilteredPoolSignals(monitor, null);
         stacks = pool.getFilteredPoolStacks(monitor, null);
         if ((signals.length + stacks.length) == 0)
         {
            throw new RuntimeException(
               "The pool doesn't contain any buffers and cannot be optimized");
         }

         // Configured buffer sizes, same sizes for signals and stacks
         confBufferSizes = pool.getSignalSizes();
         largestConfBufferSize =
            confBufferSizes[confBufferSizes.length - 1].getSize();

         signalStackSizes = new Integer[signals.length + stacks.length];
         for (int i = 0; i < (signals.length + stacks.length); i++)
         {
            int size;
            int bufferSize;

            if (i < signals.length)
            {
               SignalInfo signal = signals[i];
               size = signal.getSize();
               bufferSize = signal.getBufferSize();
            }
            else
            {
               StackInfo stack = stacks[i - signals.length];
               size = stack.getSize();
               bufferSize = stack.getBufferSize();
            }
            usedMemory += bufferSize;
            slackMemory += bufferSize - pool.getSignalAdmSize() - size;
            signalStackSizes[i] = new Integer(size);
         }

         optimizerResult = optimizer.suggestBufferSizes(
               monitor,
               signalStackSizes,
               largestConfBufferSize,
               pool.getSignalAlignment(),
               pool.getSignalAdmSize());

         // Display result

         asyncExec(new UpdateUIRunner(
               target,
               pool,
               usedMemory,
               slackMemory,
               optimizerResult.getBufferSizes(),
               optimizerResult.getUsedMemory(),
               optimizerResult.getSlackMemory(),
               usedMemory,
               slackMemory,
               optimizerResult.getBufferSizes(),
               optimizerResult.getUsedMemory(),
               optimizerResult.getSlackMemory()));
      }
   }

   private class UpdateUIRunner implements Runnable
   {
      private final Target target;
      private final Pool pool;
      private final int currentSignalUsedMemory;
      private final int currentSignalSlackMemory;
      private final int[] newSignalBufferSizes;
      private final int newSignalUsedMemory;
      private final int newSignalSlackMemory;
      private final int currentStackUsedMemory;
      private final int currentStackSlackMemory;
      private final int[] newStackBufferSizes;
      private final int newStackUsedMemory;
      private final int newStackSlackMemory;
      private final NumberFormat percent;

      UpdateUIRunner(Target target,
                     Pool pool,
                     int currentSignalUsedMemory,
                     int currentSignalSlackMemory,
                     int[] newSignalBufferSizes,
                     int newSignalUsedMemory,
                     int newSignalSlackMemory,
                     int currentStackUsedMemory,
                     int currentStackSlackMemory,
                     int[] newStackBufferSizes,
                     int newStackUsedMemory,
                     int newStackSlackMemory)
      {
         this.target = target;
         this.pool = pool;
         this.currentSignalUsedMemory = currentSignalUsedMemory;
         this.currentSignalSlackMemory = currentSignalSlackMemory;
         this.newSignalBufferSizes = newSignalBufferSizes;
         this.newSignalUsedMemory = newSignalUsedMemory;
         this.newSignalSlackMemory = newSignalSlackMemory;
         this.currentStackUsedMemory = currentStackUsedMemory;
         this.currentStackSlackMemory = currentStackSlackMemory;
         this.newStackBufferSizes = newStackBufferSizes;
         this.newStackUsedMemory = newStackUsedMemory;
         this.newStackSlackMemory = newStackSlackMemory;
         percent = NumberFormat.getPercentInstance(Locale.US);
         percent.setMinimumFractionDigits(2);
         percent.setMaximumFractionDigits(2);
      }

      public void run()
      {
         currentSignalBufferSizesText.setText(
            toBufferSizesString(pool.getSignalSizes()));
         currentSignalMemoryUsageText.setText(
            toBytesString(currentSignalUsedMemory));
         currentSignalSlackText.setText(
            toPercentString(currentSignalSlackMemory, currentSignalUsedMemory));
         newSignalBufferSizesText.setText(
            toBufferSizesString(newSignalBufferSizes));
         newSignalMemoryUsageText.setText(
            toBytesString(newSignalUsedMemory));
         gainSignalText.setText(
            toGainString(currentSignalUsedMemory, newSignalUsedMemory));
         newSignalSlackText.setText(
            toPercentString(newSignalSlackMemory, newSignalUsedMemory));
         currentStackBufferSizesText.setText(
            toBufferSizesString(pool.getStackSizes()));
         currentStackMemoryUsageText.setText(
            toBytesString(currentStackUsedMemory));
         currentStackSlackText.setText(
            toPercentString(currentStackSlackMemory, currentStackUsedMemory));
         newStackBufferSizesText.setText(
            toBufferSizesString(newStackBufferSizes));
         newStackMemoryUsageText.setText(
            toBytesString(newStackUsedMemory));
         gainStackText.setText(
            toGainString(currentStackUsedMemory, newStackUsedMemory));
         newStackSlackText.setText(
            toPercentString(newStackSlackMemory, newStackUsedMemory));

         setContentDescription("Target: " + target + ", Pool: "
               + StringUtils.toHexString(pool.getId()));
      }

      private String toBufferSizesString(PoolBufferSizeInfo[] bufferSizes)
      {
         StringBuffer sb = new StringBuffer();

         for (int i = 0; i < bufferSizes.length; i++)
         {
            sb.append(bufferSizes[i].getSize());
            if (i < (bufferSizes.length - 1))
            {
               sb.append(", ");
            }
         }

         return sb.toString();
      }

      private String toBufferSizesString(int[] bufferSizes)
      {
         StringBuffer sb = new StringBuffer();

         for (int i = 0; i < bufferSizes.length; i++)
         {
            sb.append(bufferSizes[i]);
            if (i < (bufferSizes.length - 1))
            {
               sb.append(", ");
            }
         }

         return sb.toString();
      }

      private String toBytesString(int i)
      {
         return i + " bytes";
      }

      private String toPercentString(double dividend, double divisor)
      {
         double value = ((divisor != 0) ? (dividend / divisor) : 0);
         return percent.format(value);
      }

      private String toGainString(double oldValue, double newValue)
      {
         return percent.format(1 - newValue / oldValue);
      }
   }
}
