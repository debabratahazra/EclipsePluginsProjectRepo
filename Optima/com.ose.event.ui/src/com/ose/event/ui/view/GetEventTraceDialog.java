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

import java.io.IOException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import com.ose.event.ui.EventPlugin;
import com.ose.system.ServiceException;
import com.ose.system.Target;
import com.ose.system.TargetEvent;

public class GetEventTraceDialog extends Dialog
{
   private final Target target;
   private final int length;
   private int from;
   private int to;
   private Slider slider;
   private Label minLabel;
   private Label maxLabel;
   private Spinner spinner;
   private Label fromLabel;
   private Label toLabel;

   public GetEventTraceDialog(Shell parent, Target target, int length)
   {
      super(parent);
      setShellStyle(getShellStyle() | SWT.RESIZE);
      if ((target == null) || (length <= 0))
      {
         throw new IllegalArgumentException();
      }
      this.target = target;
      this.length = length;
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("Read Event Trace");
   }

   protected Control createContents(Composite parent)
   {
      Control contents;
      Job job;

      contents = super.createContents(parent);
      getButton(IDialogConstants.OK_ID).setEnabled(false);
      job = new GetEventTraceJob();
      job.schedule();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      Composite subComp;
      GridData gd;
      Label label;

      comp = (Composite) super.createDialogArea(parent);
      subComp = new Composite(comp, SWT.NONE);
      subComp.setLayoutData(new GridData(GridData.FILL_BOTH));
      subComp.setLayout(new GridLayout(2, false));

      slider = new Slider(subComp, SWT.HORIZONTAL);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      gd.widthHint = convertHorizontalDLUsToPixels(
            IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
      slider.setLayoutData(gd);
      slider.addSelectionListener(new SliderSelectionHandler());

      minLabel = new Label(subComp, SWT.LEFT);
      gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
      gd.widthHint = 50;
      minLabel.setLayoutData(gd);

      maxLabel = new Label(subComp, SWT.RIGHT);
      maxLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(subComp, SWT.NONE);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 2;
      label.setLayoutData(gd);

      label = new Label(subComp, SWT.NONE);
      label.setText("Number of Events:");

      spinner = new Spinner(subComp, SWT.BORDER);
      gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
      gd.widthHint = 50;
      spinner.setLayoutData(gd);
      spinner.addSelectionListener(new SpinnerSelectionHandler());

      label = new Label(subComp, SWT.NONE);
      label.setText("From:");

      fromLabel = new Label(subComp, SWT.LEFT);
      fromLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      label = new Label(subComp, SWT.NONE);
      label.setText("To:");

      toLabel = new Label(subComp, SWT.LEFT);
      toLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      applyDialogFont(comp);
      return comp;
   }

   protected void okPressed()
   {
      from = slider.getSelection();
      to = from + slider.getThumb();
      super.okPressed();
   }

   private void updateFromToLabels()
   {
      int selection = slider.getSelection();
      fromLabel.setText(Integer.toString(selection));
      toLabel.setText(Integer.toString(selection + slider.getThumb() - 1));
   }

   public int getFrom()
   {
      return from;
   }

   public int getTo()
   {
      return to;
   }

   private class SliderSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         updateFromToLabels();
      }
   }

   private class SpinnerSelectionHandler extends SelectionAdapter
   {
      public void widgetSelected(SelectionEvent event)
      {
         slider.setThumb(spinner.getSelection());
         updateFromToLabels();
      }
   }

   private class GetEventTraceJob extends Job
   {
      GetEventTraceJob()
      {
         super("Retrieving event trace tail");
         setPriority(SHORT);
      }

      protected IStatus run(IProgressMonitor monitor)
      {
         Shell shell = getShell();

         try
         {
            boolean eventTraceWasEnabled = false;
            TargetEvent[] events;
            int max;

            monitor.beginTask(getName(), IProgressMonitor.UNKNOWN);
            if (target.hasEventTraceEnabledSupport())
            {
               eventTraceWasEnabled = target.isEventTraceEnabled(monitor);
            }
            events = target.getEventTrace(monitor, 0xFFFFFFFE, 0xFFFFFFFF);
            if (target.hasEventTraceEnabledSupport() && eventTraceWasEnabled)
            {
               target.setEventTraceEnabled(monitor, true);
            }
            max = ((events.length > 0) ?
                  events[events.length - 1].getReference() + 1 : 0);
            if ((shell != null) && !shell.isDisposed())
            {
               shell.getDisplay().asyncExec(new UpdateRunner(max));
            }
            return (monitor.isCanceled() ?
                    Status.CANCEL_STATUS : Status.OK_STATUS);
         }
         catch (OperationCanceledException e)
         {
            if ((shell != null) && !shell.isDisposed())
            {
               shell.getDisplay().asyncExec(new CloseRunner());
            }
            return Status.CANCEL_STATUS;
         }
         catch (ServiceException e)
         {
            if ((shell != null) && !shell.isDisposed())
            {
               shell.getDisplay().asyncExec(new CloseRunner());
            }
            return EventPlugin.createErrorStatus(
               "Error reported from target when retrieving the event trace tail", e);
         }
         catch (IOException e)
         {
            if ((shell != null) && !shell.isDisposed())
            {
               shell.getDisplay().asyncExec(new CloseRunner());
            }
            return EventPlugin.createErrorStatus(
               "Error communicating with target when retrieving the event trace tail", e);
         }
         catch (Exception e)
         {
            if ((shell != null) && !shell.isDisposed())
            {
               shell.getDisplay().asyncExec(new CloseRunner());
            }
            return EventPlugin.createErrorStatus(
               "Error when retrieving the event trace tail", e);
         }
         finally
         {
            monitor.done();
         }
      }
   }

   private class UpdateRunner implements Runnable
   {
      private final boolean maxValid;
      private final int max;

      UpdateRunner(int max)
      {
         this.maxValid = (max != 0);
         this.max = ((max < 0) ? Integer.MAX_VALUE : ((max == 0) ? 1 : max));
      }

      public void run()
      {
         if (!getShell().isDisposed())
         {
            int size = Math.min(max, length);
            slider.setMinimum(0);
            slider.setMaximum(max);
            slider.setThumb(size);
            minLabel.setText("0");
            maxLabel.setText(Integer.toString(max - 1));
            if (size > 1)
            {
               spinner.setMinimum(1);
               spinner.setMaximum(size);
               spinner.setSelection(size);
            }
            else
            {
               spinner.setMinimum(0);
               spinner.setMaximum(size);
               spinner.setSelection(maxValid ? size : 0);
               spinner.setEnabled(false);
            }
            updateFromToLabels();
            getButton(IDialogConstants.OK_ID).setEnabled(maxValid);
         }
      }
   }

   private class CloseRunner implements Runnable
   {
      public void run()
      {
         if (!getShell().isDisposed())
         {
            setReturnCode(CANCEL);
            close();
         }
      }
   }
}
