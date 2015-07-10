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

package com.ose.event.ui.editors.action.detailparts;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import com.ose.event.ui.editors.action.EventActionStrings;
import com.ose.xmleditor.model.DocumentModel;

public class BufferEventActionDetailsPage extends EventActionDetailsPage
{
   
   public BufferEventActionDetailsPage(DocumentModel model, String actionType)
   {
      super(model, actionType);
   }

   protected void createCustomEventContent(FormToolkit toolkit, Composite parent)
   {
      createEventRow(toolkit, parent, EventActionStrings.EVENTS_GRP5);
      createFromScopeRow(toolkit, parent);
      createIgnoreCountRow(toolkit, parent);
      createDataFilterGroupRow(toolkit, parent);
   }

   public void selectionChanged(IFormPart part, ISelection selection)
   {
      super.selectionChanged(part, selection);
   }

   protected void createCustomActionContent(FormToolkit toolkit, Composite parent)
   {
   }

}
