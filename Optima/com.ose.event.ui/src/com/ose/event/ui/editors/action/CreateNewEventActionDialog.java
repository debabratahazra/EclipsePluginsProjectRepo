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

package com.ose.event.ui.editors.action;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class CreateNewEventActionDialog extends Dialog
{
   private static int selectedEvent;

   private static int selectedAction;

   public CreateNewEventActionDialog(Shell parent)
   {
      super(parent);
      setShellStyle(getShellStyle());
   }

   protected void configureShell(Shell shell)
   {
      super.configureShell(shell);
      shell.setText("New Event Action");
   }

   protected Control createContents(Composite parent)
   {
      Control contents;
      contents = super.createContents(parent);
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Composite comp;
      GridData gd;

      comp = (Composite) super.createDialogArea(parent);
      comp.setLayout(new GridLayout(2, true));

      Label eventLabel = new Label(comp, SWT.NONE);
      eventLabel.setText("Event");

      Label actionLabel = new Label(comp, SWT.NONE);
      actionLabel.setText("Action");

      List event = new List(comp, SWT.BORDER);
      event.setItems(EventActionStrings.EVENTS);
      event.setSelection(selectedEvent);
      event.addSelectionListener(new SelectionListener()
      {
         public void widgetDefaultSelected(SelectionEvent e)
         {
         }

         public void widgetSelected(SelectionEvent e)
         {
            List list = (List) e.getSource();
            selectedEvent = list.getSelectionIndex();
         }

      });
      gd = new GridData();
      gd.minimumWidth = 100;
      gd.minimumHeight = 150;
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.grabExcessVerticalSpace = true;
      gd.verticalAlignment = SWT.FILL;
      event.setLayoutData(gd);

      List action = new List(comp, SWT.BORDER);
      action.setItems(EventActionStrings.ACTIONS);
      action.setSelection(selectedAction);
      action.addSelectionListener(new SelectionListener()
      {
         public void widgetDefaultSelected(SelectionEvent e)
         {
         }

         public void widgetSelected(SelectionEvent e)
         {
            List list = (List) e.getSource();
            selectedAction = list.getSelectionIndex();
         }
      });

      gd = new GridData();
      gd.minimumWidth = 100;
      gd.minimumHeight = 150;
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.grabExcessVerticalSpace = true;
      gd.verticalAlignment = SWT.FILL;
      action.setLayoutData(gd);

      applyDialogFont(comp);

      return comp;
   }

   public String getSelectedEvent()
   {
      return EventActionStrings.XML_EVENTS[selectedEvent];
   }

   public String getSelectedAction()
   {
      return EventActionStrings.XML_ACTIONS[selectedAction];
   }
}
