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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ose.event.ui.editors.action.EventActionStrings;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;

public class UserEventActionDetailsPage extends EventActionDetailsPage
{
   private Composite parent;

   private Text eventRangeFrom;

   private Text eventRangeTo;

   /**
    * TODO Uncomment this code when the range invert function is implemented in
    * RMM.
    */
   // private Button notValue;

   private Label includeDataLabel;

   private Button includeData;

   public UserEventActionDetailsPage(DocumentModel model, String actionType)
   {
      super(model, actionType);
   }

   protected void createCustomEventContent(FormToolkit toolkit, Composite parent)
   {
      this.parent = parent;
      createEventRow(toolkit, parent, EventActionStrings.EVENTS_GRP7);
      createFromScopeRow(toolkit, parent);
      createIgnoreCountRow(toolkit, parent);
      createSignalRangeRow(toolkit, parent);
      createDataFilterGroupRow(toolkit, parent);
   }

   public void selectionChanged(IFormPart part, ISelection selection)
   {
      super.selectionChanged(part, selection);
   }

   public void refresh()
   {
      super.refresh();

      setIgnoreModifyForListeners(true);

      ValidationNode validationNode = getValidationNode();
      Element eventAction = (Element) validationNode.getNode();
      NodeList nodeList = eventAction.getElementsByTagName("event");
      Element event = (Element) nodeList.item(0);
      nodeList = event.getElementsByTagName("numberRange");
      Element eventRange = (Element) nodeList.item(0);
      nodeList = eventAction.getElementsByTagName("action");
      Element action = (Element) nodeList.item(0);

      refreshEventRange(eventRange);
      refreshIncludeData(action);

      ValidationNode node = getValidationNode();
      ValidationNode eventNode = node.getElement("event");
      ValidationNode numberRangeNode = eventNode.getElement("numberRange");
      ValidationNode actionNode = node.getElement("action");

      refreshErrorStatus(numberRangeNode, eventRangeFrom, "min");
      refreshErrorStatus(numberRangeNode, eventRangeTo, "max");
      /**
       * TODO Uncomment this code when the range invert function is implemented
       * in RMM.
       */
      // refreshErrorStatus(numberRangeNode, notValue, "not");
      if (isDataRelevant(getActionType()))
      {
         refreshErrorStatus(actionNode, includeData, "dataIncluded");
      }

      setIgnoreModifyForListeners(false);
   }

   private void refreshEventRange(Element eventRange)
   {
      setEditorListener(eventRangeFrom, eventRange.getAttribute("min"),
            eventRange, "min", null);

      setEditorListener(eventRangeTo, eventRange.getAttribute("max"),
            eventRange, "max", null);
   }

   private void refreshIncludeData(Element action)
   {
      if (isDataRelevant(getActionType()))
      {
         if (includeData == null)
         {
            createIncludeUserDataRow(managedForm.getToolkit(), parent);
         }
         includeData.setSelection(action.getAttribute("dataIncluded").equals(
               "false") ? false : true);
         setEditorListener(includeData, action, "dataIncluded");
      }
      else
      {
         if (includeData != null)
         {
            disposeIncludeUserDataRow();
         }
      }
   }

   private void createSignalRangeRow(FormToolkit toolkit, Composite parent)
   {
      toolkit.createLabel(parent, "User Number Range:");

      eventRangeFrom = toolkit.createText(parent, "", SWT.BORDER);
      eventRangeFrom.setData("name", "eventRangeFrom");
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      eventRangeFrom.setLayoutData(gd);

      eventRangeTo = toolkit.createText(parent, "", SWT.BORDER);
      eventRangeTo.setData("name", "eventRangeTo");
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      eventRangeTo.setLayoutData(gd);

      /**
       * TODO Uncomment this code when the range invert function is implemented
       * in RMM.
       */
      // notValue = toolkit.createButton(parent, "Not", SWT.CHECK);
      // gd = new GridData();
      // gd.grabExcessHorizontalSpace = true;
      // gd.horizontalAlignment = SWT.FILL;
      // gd.heightHint = 20;
      // notValue.setLayoutData(gd);
   }

   protected void createCustomActionContent(FormToolkit toolkit,
         Composite parent)
   {
      if (isDataRelevant(getActionType()))
      {
         createIncludeUserDataRow(toolkit, parent);
      }
   }

   private void createIncludeUserDataRow(FormToolkit toolkit, Composite parent)
   {
      if (includeData != null)
      {
         return;
      }

      includeDataLabel = toolkit.createLabel(parent, "Include User Data:");

      includeData = toolkit.createButton(parent, "", SWT.CHECK);
      includeData.setData("name", "includeData");
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.horizontalSpan = 2;
      gd.heightHint = 20;
      includeData.setLayoutData(gd);

      parent.layout();
      parent.getParent().layout();
   }

   private void disposeIncludeUserDataRow()
   {
      if (includeDataLabel != null)
      {
         removeListener("actiondataIncluded", includeData);
         includeDataLabel.dispose();
         includeData.dispose();
         includeDataLabel = null;
         includeData = null;
      }
   }

   private boolean isDataRelevant(String actionType)
   {
      return actionType.equals("trace") || actionType.equals("notify")
         || actionType.equals("user");
   }

   protected void actionTypeChanged(FormToolkit toolkit, Composite parent,
         String oldValue, String newValue)
   {
      super.actionTypeChanged(toolkit, parent, oldValue, newValue);
      if (isDataRelevant(oldValue) && isDataRelevant(newValue))
      {
         return;
      }
      if (isDataRelevant(newValue))
      {
         createIncludeUserDataRow(toolkit, parent);
      }
      else
      {
         disposeIncludeUserDataRow();
      }
   }
}
