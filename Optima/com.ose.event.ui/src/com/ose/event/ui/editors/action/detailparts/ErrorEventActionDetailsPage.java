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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.ose.event.ui.editors.action.EventActionStrings;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;

public class ErrorEventActionDetailsPage extends EventActionDetailsPage
{

   public ErrorEventActionDetailsPage(DocumentModel model, String actionType)
   {
      super(model, actionType);
   }

   private Text errorRangeFrom;

   private Text errorRangeTo;

   /**
    * TODO Uncomment this code when the range invert function is implemented in
    * RMM.
    */
//   private Button errorRangeNotValue;
   
   /**
    * TODO Uncomment this code when the extra range is implemented
    * in RMM.
    */
//   private Text extraRangeFrom;
//   private Text extraRangeTo;

   /**
    * TODO Uncomment this code when the range invert function is implemented in
    * RMM.
    */
//   private Button extraRangeNotValue;

   private Button kernelButton;
   private Button nonKernelButton;

   protected void createCustomEventContent(FormToolkit toolkit, Composite parent)
   {
      createEventRow(toolkit, parent, EventActionStrings.EVENTS_GRP3);
      createFromScopeRow(toolkit, parent);
      createIgnoreCountRow(toolkit, parent);
      createErrorCodeRangeRow(toolkit, parent);
      /**
       * TODO Uncomment this code when the extra range is implemented in
       * RMM.
       */
//      createExtraParameterRangeRow(toolkit, parent);
      createKernelErrorsRow(toolkit, parent);
   }

   public void selectionChanged(IFormPart part, ISelection selection)
   {
      super.selectionChanged(part, selection);
   }

   public void refresh()
   {
      super.refresh();

      setIgnoreModifyForListeners(true);

      ValidationNode node = getValidationNode();
      Element eventAction = (Element) node.getNode();
      NodeList nodeList = eventAction.getElementsByTagName("event");
      Element event = (Element) nodeList.item(0);
      nodeList = event.getElementsByTagName("errorRange");
      Element errorRange = (Element) nodeList.item(0);
      nodeList = event.getElementsByTagName("onlyKernelErrors");
      Element onlyKernelErrors = (Element) nodeList.item(0);

      refreshErrorRange(errorRange);
      refreshOnlyKernelErrors(onlyKernelErrors);

      ValidationNode eventNode = node.getElement("event");
      ValidationNode errorRangeNode = eventNode.getElement("errorRange");

      /**
       * TODO Uncomment this code when the extra range is implemented
       * in RMM.
       */
//      ValidationNode extraRangeNode = eventNode.getElement("extraRange");
      ValidationNode kernelOnlyNode = eventNode.getElement("onlyKernelErrors");

      refreshErrorStatus(errorRangeNode, errorRangeFrom, "min");
      refreshErrorStatus(errorRangeNode, errorRangeTo, "max");

      /**
       * TODO Uncomment this code when the range invert function is implemented
       * in RMM.
       */
//      refreshErrorStatus(errorRangeNode, errorRangeNotValue, "not");
      
      /**
       * TODO Uncomment this code when the extra range is implemented
       * in RMM.
       */
//      refreshErrorStatus(extraRangeNode, extraRangeFrom, "min");
//      refreshErrorStatus(extraRangeNode, extraRangeTo, "max");

      /**
       * TODO Uncomment this code when the range invert function is implemented
       * in RMM.
       */
//      refreshErrorStatus(extraRangeNode, extraRangeNotValue, "not");
      refreshErrorStatus(kernelOnlyNode, kernelButton);

      setIgnoreModifyForListeners(false);
   }

   private void refreshErrorRange(Element errorRange)
   {
      setEditorListener(errorRangeFrom, errorRange.getAttribute("min"),
            errorRange, "min", null);

      setEditorListener(errorRangeTo, errorRange.getAttribute("max"),
            errorRange, "max", null);
   }

   private void refreshOnlyKernelErrors(Element onlyKernelErrors)
   {
      NodeList nodeList = onlyKernelErrors.getChildNodes();
      boolean value = nodeList.item(0).getNodeValue().equals("false");
      kernelButton.setSelection(!value);
      nonKernelButton.setSelection(value);
      setEditorListener(kernelButton, nodeList.item(0), null);
   }

   private void createErrorCodeRangeRow(FormToolkit toolkit, Composite parent)
   {
      toolkit.createLabel(parent, "Error Code Range:");

      errorRangeFrom = toolkit.createText(parent, "", SWT.BORDER);
      errorRangeFrom.setData("name", "errorRangeFrom");
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      errorRangeFrom.setLayoutData(gd);

      errorRangeTo = toolkit.createText(parent, "", SWT.BORDER);
      errorRangeTo.setData("name", "errorRangeTo");
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      errorRangeTo.setLayoutData(gd);

      /**
       * TODO Uncomment this code when the range invert function is implemented
       * in RMM.
       */
//      errorRangeNotValue = toolkit.createButton(parent, "Not", SWT.CHECK);
//      gd = new GridData();
//      gd.grabExcessHorizontalSpace = true;
//      gd.horizontalAlignment = SWT.FILL;
//      gd.heightHint = 20;
//      errorRangeNotValue.setLayoutData(gd);
   }

   /**
    * TODO Uncomment this code when the extra range is implemented
    * in RMM.
    */
//   private void createExtraParameterRangeRow(FormToolkit toolkit,
//         Composite parent)
//   {
//      toolkit.createLabel(parent, "Extra Parameter Range:");
//
//      extraRangeFrom = toolkit.createText(parent, "", SWT.BORDER);
//      gd = new GridData();
//      gd.grabExcessHorizontalSpace = true;
//      gd.horizontalAlignment = SWT.FILL;
//      extraRangeFrom.setLayoutData(gd);
//
//      extraRangeTo = toolkit.createText(parent, "", SWT.BORDER);
//      gd = new GridData();
//      gd.grabExcessHorizontalSpace = true;
//      gd.horizontalAlignment = SWT.FILL;
//      extraRangeTo.setLayoutData(gd);

      /**
       * TODO Uncomment this code when the range invert function is implemented
       * in RMM.
       */
//      extraRangeNotValue = toolkit.createButton(parent, "Not", SWT.CHECK);
//      gd = new GridData();
//      gd.grabExcessHorizontalSpace = true;
//      gd.horizontalAlignment = SWT.FILL;
//      gd.heightHint = 20;
//      extraRangeNotValue.setLayoutData(gd);
//   }

   private void createKernelErrorsRow(FormToolkit toolkit, Composite parent)
   {
      toolkit.createLabel(parent, "Error Type:");

      Composite box = toolkit.createComposite(parent);
      GridLayout gridLayout = new GridLayout();
      gridLayout.marginWidth = 0;
      box.setLayout(gridLayout);

      kernelButton = toolkit.createButton(box, "Kernel Reported", SWT.RADIO);
      kernelButton.setData("name", "kernelButton");
      nonKernelButton = toolkit.createButton(box, "Other", SWT.RADIO);
      nonKernelButton.setData("name", "nonKernelButton");
   }

   protected void createCustomActionContent(FormToolkit toolkit,
         Composite parent)
   {
   }
}
