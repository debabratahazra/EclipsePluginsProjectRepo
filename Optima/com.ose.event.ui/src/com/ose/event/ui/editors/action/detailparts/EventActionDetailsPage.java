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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ose.event.ui.editors.action.EditorFormPage;
import com.ose.event.ui.editors.action.EditorListener;
import com.ose.event.ui.editors.action.EventActionFormEditor;
import com.ose.event.ui.editors.action.EventActionStrings;
import com.ose.event.ui.editors.action.UndoableOperation;
import com.ose.xmleditor.model.DocumentChangeEvent;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;

public abstract class EventActionDetailsPage implements IDetailsPage
{
   protected IManagedForm managedForm;

   protected final DocumentModel model;

   private Composite parent;

   private SectionPart sectionPart;

   private Combo eventTypeCombo;

   private Combo fromScopeTypeCombo;

   private Text fromScopeValue;

   private Combo toScopeTypeCombo;

   private Text toScopeValue;

   private Text ignoreCountValue;
   
   private String actionType;

   private Combo actionTypeCombo;

   private Button dontPerform;

   private Text stateValue;

   private Composite newStateParent;

   private Label newStateLabel;

   private Text newStateValue;

   private Text offsetText;

   private Combo sizeCombo;

   private Text dataRangeMinText;

   private Text dataRangeMaxText;

   private Button dataRangeNot;
   
   private Button useDataFilters;
   
   /**
    * TODO Uncomment this code when the inlude line and file function is
    * implemented in RMM.
    */
   // private Button includeLineAndFile;

   private ValidationNode node;

   private boolean dirty;

   private Color RED;

   private Color WHITE;

   /**
    * The editor listeners are stored in this map with the class as key.
    */
   private HashMap listeners;

   public EventActionDetailsPage(DocumentModel model, String actionType)
   {
      this.model = model;
      dirty = false;
      this.actionType = actionType;
      listeners = new HashMap();
   }

   public void createContents(Composite parent)
   {
      this.parent = parent;
      GridLayout layout = new GridLayout();
      layout.marginHeight = 0;
      parent.setLayout(layout);

      FormToolkit toolkit = managedForm.getToolkit();
      Section section = toolkit.createSection(parent, Section.TITLE_BAR);
      section.marginWidth = 10;
      section.marginHeight = 5;

      section.setText("Details");
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.minimumWidth = 300;
      gd.horizontalAlignment = SWT.FILL;
      section.setLayoutData(gd);

      Composite client = toolkit.createComposite(section);
      layout = new GridLayout(3, true);
      client.setLayout(layout);

      createCustomEventContent(toolkit, client);

      Label separator = toolkit.createSeparator(client, SWT.HORIZONTAL);
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.horizontalSpan = 3;
      separator.setLayoutData(gd);

      createActionContent(toolkit, client, actionType);

      section.setClient(client);
      sectionPart = new SectionPart(section);
      managedForm.addPart(sectionPart);

      createCustomActionContent(toolkit, client);

      RED = managedForm.getForm().getDisplay().getSystemColor(SWT.COLOR_RED);
      WHITE = managedForm.getForm().getDisplay()
            .getSystemColor(SWT.COLOR_WHITE);
   }

   public void commit(boolean onSave)
   {
      dirty = false;
   }

   public void initialize(IManagedForm form)
   {
      this.managedForm = form;
   }

   public boolean isDirty()
   {
      return dirty;
   }

   public boolean isStale()
   {
      return false;
   }

   public void setFocus()
   {
   }

   public void dispose()
   {
      sectionPart.getSection().dispose();
      sectionPart.dispose();
      listeners.clear();
   }

   public void refresh()
   {
      // Disable the property change listeners.
      setIgnoreModifyForListeners(true);

      Element eventAction = (Element) node.getNode();
      NodeList nodeList = eventAction.getElementsByTagName("event");
      Element event = (Element) nodeList.item(0);
      nodeList = eventAction.getElementsByTagName("action");
      Element action = (Element) nodeList.item(0);
      actionType = action.getAttribute("type");

      refreshEventTypeCombo(event);
      refreshFromScope(event);
      refreshToScope(event);
      if (offsetText != null)
      {
         refreshDataFilter(event, event.getAttribute("type"));
      }
      refreshIgnoreCount(event);
      refreshActionType(eventAction);

      ValidationNode eventNode = node.getElement("event");
      ValidationNode fromScopeNode = eventNode.getElement("fromScope");
      ValidationNode toScopeNode = eventNode.getElement("toScope");
      ValidationNode dataFilterNode = eventNode.getElement("dataFilter");
      ValidationNode ignoreCountNode = eventNode.getElement("ignoreCount");
      ValidationNode actionNode = node.getElement("action");
      ValidationNode newStateNode = actionNode.getElement("newState");

      refreshErrorStatus(node, stateValue, "state");
      refreshErrorStatus(eventNode, eventTypeCombo, "type");
      refreshErrorStatus(fromScopeNode, fromScopeTypeCombo, "type");
      refreshErrorStatus(fromScopeNode, fromScopeValue, "value");

      if (toScopeTypeCombo != null)
      {
         refreshErrorStatus(toScopeNode, toScopeTypeCombo, "type");
      }
      if (toScopeValue != null)
      {
         refreshErrorStatus(toScopeNode, toScopeValue, "value");
      }
      if (offsetText != null)
      {
         refreshErrorStatus(dataFilterNode, offsetText, "offset");
         refreshErrorStatus(dataFilterNode, dataRangeMinText, "min");
         refreshErrorStatus(dataFilterNode, dataRangeMaxText, "max");
      }
      refreshErrorStatus(ignoreCountNode, ignoreCountValue);
      refreshErrorStatus(actionNode, actionTypeCombo, "type");
      refreshErrorStatus(actionNode, dontPerform, "not");

      /**
       * TODO Uncomment this code when the inlude line and file function is
       * implemented in RMM.
       */
      // refreshErrorStatus(actionNode, includeLineAndFile, "fileLineIncluded");
      if (actionType.equals("setState"))
      {
         refreshErrorStatus(newStateNode, newStateValue);
      }

      // Enable the property change listeners.
      setIgnoreModifyForListeners(false);
   }

   protected void setIgnoreModifyForListeners(boolean ignoreModify)
   {
      if (listeners != null)
      {
         for (Iterator<EditorListener> i = listeners.values().iterator(); i.hasNext();)
         {
            EditorListener listener = i.next();
            listener.setIgnoreModify(ignoreModify);
         }
      }
   }

   protected ValidationNode getValidationNode()
   {
      return node;
   }

   protected void refreshErrorStatus(ValidationNode node, Control control,
         String attributeName)
   {
      if (node != null && attributeName != null)
      {
         if (node.hasAttributeErrors(attributeName))
         {
            control.setBackground(RED);
         }
         else
         {
            control.setBackground(WHITE);
         }
      }
   }

   protected void refreshErrorStatus(ValidationNode node, Control control)
   {
      if (node != null)
      {
         if (node.hasChildErrors(0))
         {
            control.setBackground(RED);
         }
         else
         {
            control.setBackground(WHITE);
         }
      }
   }

   public boolean setFormInput(Object input)
   {
      return false;
   }

   public void selectionChanged(IFormPart part, ISelection selection)
   {
      if (selection instanceof IStructuredSelection)
      {
         IStructuredSelection structuredSelection = (IStructuredSelection) selection;
         Object selected = structuredSelection.getFirstElement();

         if (selected instanceof ValidationNode)
         {
            node = (ValidationNode) selected;
            refresh();
            parent.layout();
         }
      }
   }

   private void refreshEventTypeCombo(Element event)
   {
      String eventType = event.getAttribute("type");
      String[] evtGrp = null;
      for (int i = 0; i < EventActionStrings.NUM_EVENT_GRP; i++)
      {
         switch (i)
         {
            case 0:
               evtGrp = EventActionStrings.XML_EVENTS_GRP1;
               break;
            case 1:
               evtGrp = EventActionStrings.XML_EVENTS_GRP2;
               break;
            case 2:
               evtGrp = EventActionStrings.XML_EVENTS_GRP3;
               break;
            case 3:
               evtGrp = EventActionStrings.XML_EVENTS_GRP4;
               break;
            case 4:
               evtGrp = EventActionStrings.XML_EVENTS_GRP5;
               break;
            case 5:
               evtGrp = EventActionStrings.XML_EVENTS_GRP6;
               break;
            case 6:
               evtGrp = EventActionStrings.XML_EVENTS_GRP7;
               break;
         }
         if (setEventTypeCombo(evtGrp, eventType))
         {
            break;
         }
      }
   }

   private void refreshFromScope(Element event)
   {
      NodeList nodeList = event.getElementsByTagName("fromScope");
      Element fromScope = (Element) nodeList.item(0);
      String fromScopeType = fromScope.getAttribute("type");
      for (int i = 0; i < EventActionStrings.XML_SCOPE.length; i++)
      {
         if (EventActionStrings.XML_SCOPE[i].equals(fromScopeType))
         {
            fromScopeTypeCombo.select(i);
         }
      }
      if (fromScopeTypeCombo.getSelectionIndex() == 0)
      {
         fromScopeValue.setEnabled(false);
         setEditorListener(fromScopeValue, "0x0", fromScope, "value", null);
      }
      else
      {
         fromScopeValue.setEnabled(true);
         setEditorListener(fromScopeValue, fromScope.getAttribute("value"),
               fromScope, "value", null);
      }
   }

   private void refreshToScope(Element event)
   {
      if (toScopeTypeCombo != null)
      {
         NodeList nodeList = event.getElementsByTagName("toScope");
         Element toScope = (Element) nodeList.item(0);
         String toScopeType = toScope.getAttribute("type");
         for (int i = 0; i < EventActionStrings.XML_SCOPE.length; i++)
         {
            if (EventActionStrings.XML_SCOPE[i].equals(toScopeType))
            {
               toScopeTypeCombo.select(i);
            }
         }
         if (toScopeTypeCombo.getSelectionIndex() == 0)
         {
            toScopeValue.setEnabled(false);
            setEditorListener(toScopeValue, "0x0", toScope, "value", null);
         }
         else
         {
            toScopeValue.setEnabled(true);
            setEditorListener(toScopeValue, toScope.getAttribute("value"),
                  toScope, "value", null);
         }
      }
   }

   private void refreshDataFilter(Element event, String eventType)
   {
      if (offsetText != null)
      {
         sizeCombo.select(0);
         NodeList nodeList = event.getElementsByTagName("dataFilter");
         Element dataFilter = (Element) nodeList.item(0);
         if (dataFilter == null)
         {
            if (eventType.equals("free"))
            {
               dataFilter = updateDataFilterElement("false");
            }
            else
            {
               useDataFilters.setEnabled(false);
               enableDataFilterControls(false);
               return;
            }
         }
         String useSignalFilter = dataFilter.getAttribute("useSignalFilter");
         boolean useSF = (useSignalFilter == null) ? false :
            (useSignalFilter.equals("false") ? false : true);
         useDataFilters.setSelection(useSF);
         boolean enable = true;
         if (eventType.equals("alloc"))
         {
            enable = false;
            useDataFilters.setEnabled(false);
         }
         else
         {
            useDataFilters.setEnabled(true);  
         }
         if (!useSF)
         {
            enable = false;
         }
         enableDataFilterControls(enable);
         String offset = dataFilter.getAttribute("offset");
         String size = dataFilter.getAttribute("size");
         String min = dataFilter.getAttribute("min");
         String max = dataFilter.getAttribute("max");
         String not = dataFilter.getAttribute("not");
         for (int i = 0; i < EventActionStrings.DATA_FILTER_NUMBER_SIZE.length; i++)
         {
            if (EventActionStrings.DATA_FILTER_NUMBER_SIZE[i].equals(size))
            {
               sizeCombo.select(i);
            }
         }
         dataRangeNot.setSelection(not.equals("false") ? false : true);
         setEditorListener(offsetText, offset, dataFilter, "offset", null);
         setEditorListener(dataRangeMinText, min, dataFilter, "min", null);
         setEditorListener(dataRangeMaxText, max, dataFilter, "max", null);
         setEditorListener(dataRangeNot, dataFilter, "not");
         //validateDataFilter();
      }
   }
   
   private void refreshIgnoreCount(Element event)
   {
      NodeList nodeList = event.getElementsByTagName("ignoreCount");
      Element ignoreCount = (Element) nodeList.item(0);
      nodeList = ignoreCount.getChildNodes();
      Node ignoreCountNode = (Node) nodeList.item(0);
      if (ignoreCountNode == null)
      {
         ignoreCountNode = model.getDocument().createTextNode("");
         ignoreCount.appendChild(ignoreCountNode);
      }
      setEditorListener(ignoreCountValue, ignoreCountNode.getNodeValue(),
            ignoreCountNode, null, "ignoreCount");
   }

   private void refreshActionType(Element eventAction)
   {
      String oldValue = actionTypeCombo.getText();
      for (int i = 0; i < EventActionStrings.ACTIONS.length; i++)
      {
         if (EventActionStrings.ACTIONS[i].equals(oldValue))
         {
            oldValue = EventActionStrings.XML_ACTIONS[i];
            break;
         }
      }
      NodeList nodeList = eventAction.getElementsByTagName("action");
      Element action = (Element) nodeList.item(0);
      String actionType = action.getAttribute("type");
      for (int i = 0; i < EventActionStrings.XML_ACTIONS.length; i++)
      {
         if (EventActionStrings.XML_ACTIONS[i].equals(actionType))
         {
            actionTypeCombo.select(i);
            break;
         }
      }
      if (!oldValue.equals(actionType))
      {
         actionTypeChanged(managedForm.getToolkit(), newStateParent,
               oldValue, actionType);
         newStateParent.layout();
         parent.layout();
      }

      dontPerform
            .setSelection(action.getAttribute("not").equals("false") ? false
                  : true);
      setEditorListener(dontPerform, action, "not");

      setEditorListener(stateValue, eventAction.getAttribute("state"),
            eventAction, "state", null);

      /**
       * TODO Uncomment this code when the inlude line and file function
       * is implemented in RMM.
       */
      // includeLineAndFile.setSelection(action.getAttribute(
      // "fileLineIncluded").equals("false") ? false : true);
      // setEditorListener(includeLineAndFile, action,
      // "fileLineIncluded");

      if (actionType.equalsIgnoreCase("setState"))
      {
         nodeList = action.getElementsByTagName("newState");
         Element newState = (Element) nodeList.item(0);
         if (newState == null)
         {
            Element element = model.getDocument().createElement(
                  "newState");
            element.appendChild(model.getDocument().createTextNode("0"));
            eventAction.appendChild(element);
            newState = (Element) nodeList.item(0);
         }
         nodeList = newState.getChildNodes();
         Node newStateNode = (Node) nodeList.item(0);
         if (newStateValue == null)
         {
            createNewStateRow(newStateNode.getNodeValue());
         }
         setEditorListener(newStateValue, newStateNode.getNodeValue(),
               newStateNode, null, "newState");
      }
      else if (newStateValue != null)
      {
         disposeNewStateRow();
      }
   }

   private boolean setEventTypeCombo(String[] evtGrp, String evtType)
   {
      for (int i = 0; i < evtGrp.length; i++)
      {
         if (evtGrp[i].equals(evtType))
         {
            eventTypeCombo.select(i);
            return true;
         }
      }
      return false;
   }

   protected abstract void createCustomEventContent(FormToolkit toolkit,
         Composite parent);

   protected abstract void createCustomActionContent(FormToolkit toolkit,
         Composite parent);

   /* Create the event type label and combo. */
   protected void createEventRow(FormToolkit toolkit, Composite parent, String[] eventTypes)
   {
      toolkit.createLabel(parent, "Event:");

      eventTypeCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
      eventTypeCombo.setData("name", "eventTypeCombo");
      eventTypeCombo.setItems(eventTypes);
      eventTypeCombo.setVisibleItemCount(eventTypeCombo.getItemCount());
      EventTypeSelectionHandler eventTypeHandler = new EventTypeSelectionHandler();
      eventTypeCombo.addSelectionListener(eventTypeHandler);
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.horizontalSpan = 2;
      eventTypeCombo.setLayoutData(gd);
   }

   protected void createFromScopeRow(FormToolkit toolkit, Composite parent)
   {
      toolkit.createLabel(parent, "From Scope:");

      fromScopeTypeCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
      fromScopeTypeCombo.setData("name", "fromScopeTypeCombo");
      fromScopeTypeCombo.setItems(EventActionStrings.SCOPE);
      fromScopeTypeCombo.setVisibleItemCount(fromScopeTypeCombo.getItemCount());
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      fromScopeTypeCombo.setLayoutData(gd);

      fromScopeValue = toolkit.createText(parent, "", SWT.BORDER);
      fromScopeValue.setData("name", "fromScopeText");
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      fromScopeValue.setLayoutData(gd);

      ScopeSelectionHandler scopeHandler = new ScopeSelectionHandler(
            fromScopeValue, "fromScope");
      fromScopeTypeCombo.addSelectionListener(scopeHandler);
   }

   protected void createToScopeRow(FormToolkit toolkit, Composite parent)
   {
      toolkit.createLabel(parent, "To Scope:");

      toScopeTypeCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
      toScopeTypeCombo.setData("name", "toScopeTypeCombo");
      toScopeTypeCombo.setItems(EventActionStrings.SCOPE);
      toScopeTypeCombo.setVisibleItemCount(toScopeTypeCombo.getItemCount());
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      toScopeTypeCombo.setLayoutData(gd);

      toScopeValue = toolkit.createText(parent, "", SWT.BORDER);
      toScopeValue.setData("name", "toScopeValue");
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      toScopeValue.setLayoutData(gd);

      ScopeSelectionHandler scopeHandler = new ScopeSelectionHandler(
            toScopeValue, "toScope");
      toScopeTypeCombo.addSelectionListener(scopeHandler);
   }

   protected void createDataFilterGroupRow(FormToolkit toolkit, Composite parent)
   {
      GridData gd;
      useDataFilters = toolkit.createButton(parent, "Use Data Filter", SWT.CHECK);
      useDataFilters.setData("name", "useDataFilters");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      useDataFilters.setLayoutData(gd);
      useDataFilters.addSelectionListener(new SelectionListener()
      {
         
         public void widgetSelected(SelectionEvent e)
         {
            enableDataFilterControls(useDataFilters.getSelection());
            if (useDataFilters.getSelection())
            {
               Element dataFilter = updateDataFilterElement("true");
               DocumentChangeEvent dce = new DocumentChangeEvent(
                     DocumentChangeEvent.CHANGE, dataFilter, "useSignalFilter", "false",
                     "true");
               addUndoableOperation(model, dce);
            }
            else
            {
               Element dataFilter = updateDataFilterElement("false");
               DocumentChangeEvent dce = new DocumentChangeEvent(
                     DocumentChangeEvent.CHANGE, dataFilter, "useSignalFilter", "true",
                     "false");
               addUndoableOperation(model, dce);
            }
            dirty = true;
            markEditorDirty();
         }
         
         public void widgetDefaultSelected(SelectionEvent e) {}
      });
      
      Group propsGroup;
      Label label;
      propsGroup = new Group(parent, SWT.SHADOW_NONE);
      propsGroup
            .setText("Filter based on a integer variable present in the data");
      propsGroup.setLayout(new GridLayout(4, false));
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 3;
      propsGroup.setLayoutData(gd);

      label = toolkit.createLabel(propsGroup, "Integer offset in the data:");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 1;
      label.setLayoutData(gd);

      offsetText = toolkit.createText(propsGroup, "", SWT.BORDER);
      offsetText.setData("name", "offsetText");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      offsetText.setLayoutData(gd);

      /*offsetText.addModifyListener(new ModifyListener()
      {

         public void modifyText(ModifyEvent e)
         {
            validateDataFilter();
         }
      });*/
      label = toolkit.createLabel(propsGroup, "Size of the Integer (bytes):");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 1;
      label.setLayoutData(gd);

      sizeCombo = new Combo(propsGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
      sizeCombo.setData("name", "sizeCombo");
      sizeCombo.setItems(EventActionStrings.DATA_FILTER_NUMBER_SIZE);
      sizeCombo.setVisibleItemCount(sizeCombo.getItemCount());
      DataFilterNumberSizeSelectionHandler sizeHandler 
      = new DataFilterNumberSizeSelectionHandler();
      sizeCombo.addSelectionListener(sizeHandler);
      gd = new GridData(GridData.FILL_HORIZONTAL);
      gd.horizontalSpan = 1;
      gd.horizontalAlignment = GridData.BEGINNING;
      sizeCombo.setLayoutData(gd);

      label = toolkit.createLabel(propsGroup, "Integer Range:");

      dataRangeMinText = toolkit.createText(propsGroup, "", SWT.BORDER);
      dataRangeMinText.setData("name", "dataRangeMinText");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      dataRangeMinText.setLayoutData(gd);
      dataRangeMaxText = toolkit.createText(propsGroup, "", SWT.BORDER);
      dataRangeMaxText.setData("name", "dataRangeMaxText");
      gd = new GridData(GridData.FILL_HORIZONTAL);
      dataRangeMaxText.setLayoutData(gd);

      dataRangeNot = toolkit.createButton(propsGroup, "Not", SWT.CHECK);
      dataRangeNot.setData("name", "dataRangeNot");
      enableDataFilterControls(false);
   }

   
   private void enableDataFilterControls(boolean enable)
   {
      offsetText.setEnabled(enable);
      sizeCombo.setEnabled(enable);
      dataRangeMinText.setEnabled(enable);
      dataRangeMaxText.setEnabled(enable);
      dataRangeNot.setEnabled(enable);

   }
   
   private Element updateDataFilterElement(String status)
   {
      Element eventAction = (Element) node.getNode();
      NodeList nodeList = eventAction.getElementsByTagName("event");
      Element event = (Element) nodeList.item(0);
      nodeList = event.getElementsByTagName("dataFilter");
      Element dataFilter = (Element) nodeList.item(0);
      if (dataFilter == null)
      {
         dataFilter = model.getDocument().createElement("dataFilter");
         dataFilter.setAttribute("useSignalFilter", status);
         dataFilter.setAttribute("offset", "0x0");
         dataFilter.setAttribute("size", "1");
         dataFilter.setAttribute("min", "0x0");
         dataFilter.setAttribute("max", "0xFFFFFFFFFFFFFFFF");
         dataFilter.setAttribute("not", "false");
         event.appendChild(dataFilter);
      }
      else
      {
         dataFilter.setAttribute("useSignalFilter", status);
      }
      return dataFilter;
   }
   
   protected void createIgnoreCountRow(FormToolkit toolkit, Composite parent)
   {
      toolkit.createLabel(parent, "Ignore Count:");

      ignoreCountValue = toolkit.createText(parent, "", SWT.BORDER);
      ignoreCountValue.setData("name", "ignoreCountValue");
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.horizontalSpan = 2;
      ignoreCountValue.setLayoutData(gd);
   }

   private void createActionContent(FormToolkit toolkit, Composite parent,
         String actionType)
   {
      toolkit.createLabel(parent, "Action:");

      actionTypeCombo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
      actionTypeCombo.setData("name", "actionTypeCombo");
      actionTypeCombo.setItems(EventActionStrings.ACTIONS);
      actionTypeCombo.setVisibleItemCount(actionTypeCombo.getItemCount());
      ActionTypeSelectionHandler actionTypeHandler = new ActionTypeSelectionHandler();
      actionTypeCombo.addSelectionListener(actionTypeHandler);
      GridData gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.horizontalSpan = 2;
      actionTypeCombo.setLayoutData(gd);

      toolkit.createLabel(parent, "Do not Perform Action:");

      dontPerform = toolkit.createButton(parent, "", SWT.CHECK);
      dontPerform.setData("name", "dontPerform");
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      gd.horizontalSpan = 2;
      dontPerform.setLayoutData(gd);

      toolkit.createLabel(parent, "State:");

      stateValue = toolkit.createText(parent, "", SWT.BORDER);
      stateValue.setData("name", "stateValue");
      gd = new GridData();
      gd.horizontalSpan = 2;
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      stateValue.setLayoutData(gd);

      newStateParent = parent;
      if (actionType.equals("setState"))
      {
         createNewStateRow(getNewStateValue());
      }

      /**
       * TODO Uncomment this code when the inlude line and file function is
       * implemented in RMM.
       */
      // toolkit.createLabel(parent, "Include File and Line:");
      // includeLineAndFile = toolkit.createButton(parent, "", SWT.CHECK);
      // gd = new GridData();
      // gd.grabExcessHorizontalSpace = true;
      // gd.horizontalAlignment = SWT.FILL;
      // gd.horizontalSpan = 2;
      // includeLineAndFile.setLayoutData(gd);
   }

   private void createNewStateRow(String text)
   {
      if (newStateLabel != null)
      {
         return;
      }

      FormToolkit toolkit = managedForm.getToolkit();
      newStateLabel = toolkit.createLabel(newStateParent, "New State:");

      newStateValue = toolkit.createText(newStateParent, "", SWT.BORDER);
      newStateValue.setData("name", "newStateValue");
      GridData gd = new GridData();
      gd.horizontalSpan = 2;
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      newStateValue.setLayoutData(gd);
      newStateValue.setText((text != null) ? text : "0");

      newStateParent.layout();
   }

   private void disposeNewStateRow()
   {
      if (newStateLabel != null)
      {
         removeListener("#textnewState", newStateValue);
         newStateLabel.dispose();
         newStateValue.dispose();
         newStateLabel = null;
         newStateValue = null;
      }
   }

   private String getNewStateValue()
   {
      if (node == null)
      {
         return "0";
      }
      Element eventAction = (Element) node.getNode();
      NodeList nodeList = eventAction.getElementsByTagName("action");
      Element action = (Element) nodeList.item(0);
      nodeList = action.getElementsByTagName("newState");
      Element newState = (Element) nodeList.item(0);
      if (newState == null)
      {
         return "0";
      }
      nodeList = newState.getChildNodes();
      Node newStateNode = (Node) nodeList.item(0);
      if (newStateNode == null)
      {
         newStateNode = model.getDocument().createTextNode("");
         newState.appendChild(newStateNode);
      }
      return newStateNode.getNodeValue();
   }

   protected void actionTypeChanged(FormToolkit toolkit, Composite parent,
         String oldValue, String newValue)
   {
      if (oldValue.equals("setState") && !newValue.equals("setState"))
      {
         disposeNewStateRow();
      }
      else if (!oldValue.equals("setState") && newValue.equals("setState"))
      {
         createNewStateRow(getNewStateValue());
      }
   }

   protected void setEditorListener(Button button, Node node,
         String attributeName)
   {
      EditorListener listener = (EditorListener) listeners.get(node
            .getNodeName()
            + attributeName);
      if (listener != null)
      {
         button.removeSelectionListener(listener);
         button.removeFocusListener(listener);
         listeners.remove(node.getNodeName() + attributeName);
      }

      listener = new EditorListener(model, node, attributeName, this);
      listeners.put(node.getNodeName() + attributeName, listener);

      button.addSelectionListener(listener);
   }

   protected void setEditorListener(Text text, String value, Node node,
         String attributeName, String key)
   {
      Point point = null;
      if (text.isFocusControl())
      {
         point = text.getSelection();
      }
      text.setText(value);
      EditorListener listener = (EditorListener) listeners.get(node
            .getNodeName()
            + (attributeName != null ? attributeName : key));
      if (listener == null)
      {
         listener = new EditorListener(model, node, attributeName, this);
         listeners.put(node.getNodeName()
               + (attributeName != null ? attributeName : key), listener);

         text.addModifyListener(listener);
         text.addFocusListener(listener);
      }
      else
      {
         if (point != null)
         {
            text.setSelection(point.x, point.y);
         }
      }
   }

   public void removeListener(String key, Control control)
   {
      EditorListener listener = (EditorListener) listeners.get(key);
      if (listener != null)
      {
         if (control instanceof Text)
         {
            ((Text) control).removeModifyListener(listener);
         }
         else if (control instanceof Button)
         {
            ((Button) control).removeSelectionListener(listener);
         }
         control.removeFocusListener(listener);
         listeners.remove(key);
      }
   }
   
   public String getActionType()
   {
      return actionType;
   }

   private EventActionFormEditor getEditor()
   {
      return (EventActionFormEditor)
            ((EditorFormPage) managedForm.getContainer()).getEditor();
   }

   public void markEditorDirty()
   {
      getEditor().markDirty(true);
   }

   public IUndoableOperation addUndoableOperation(DocumentModel model,
         DocumentChangeEvent event)
   {
      IUndoableOperation operation = getUndoableOperation(model, event);
      EventActionFormEditor.getOperationHistory().add(operation);
      return operation;
   }

   public IUndoableOperation replaceUndoableOperation(DocumentModel model,
         DocumentChangeEvent event, IUndoableOperation replacedOperation)
   {
      IUndoableOperation operation = getUndoableOperation(model, event);
      EventActionFormEditor.getOperationHistory().replaceOperation(
            replacedOperation, new IUndoableOperation[] { operation });
      return operation;
   }

   private IUndoableOperation getUndoableOperation(DocumentModel model,
         DocumentChangeEvent event)
   {
      return new UndoableOperation(
            EventActionStrings.getUndoActionLabel(event.getType()),
            getEditor().getUndoContext(),
            model,
            event);
   }

   private class EventTypeSelectionHandler implements SelectionListener
   {
      private String lastEventType;

      private boolean dirty;

      public void widgetSelected(SelectionEvent e)
      {
         Combo combo = (Combo) e.getSource();
         String[] items = combo.getItems();
         int selectionIndex = combo.getSelectionIndex();
         String eventType = null;

         if (Arrays.equals(items, EventActionStrings.EVENTS_GRP1))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP1[selectionIndex];
         }
         else if (Arrays.equals(items, EventActionStrings.EVENTS_GRP2))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP2[selectionIndex];
         }
         else if (Arrays.equals(items, EventActionStrings.EVENTS_GRP3))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP3[selectionIndex];
         }
         else if (Arrays.equals(items, EventActionStrings.EVENTS_GRP4))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP4[selectionIndex];
         }
         else if (Arrays.equals(items, EventActionStrings.EVENTS_GRP5))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP5[selectionIndex];
         }
         else if (Arrays.equals(items, EventActionStrings.EVENTS_GRP6))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP6[selectionIndex];
         }
         else if (Arrays.equals(items, EventActionStrings.EVENTS_GRP7))
         {
            eventType = EventActionStrings.XML_EVENTS_GRP7[selectionIndex];
         }

         Element eventAction = (Element) node.getNode();
         Element event = (Element)
               eventAction.getElementsByTagName("event").item(0);
         lastEventType = event.getAttribute("type");
         if (lastEventType.equals(eventType))
         {
            return;
         }
         lastEventType = eventType;
         dirty = true;
         markEditorDirty();
         commit();
      }

      public void widgetDefaultSelected(SelectionEvent e)
      {
      }

      private void commit()
      {
         if (dirty)
         {
            Element eventAction = (Element) node.getNode();
            Element event =
                  (Element) eventAction.getElementsByTagName("event").item(0);
            String eventType = event.getAttribute("type");
            if (!lastEventType.equals(eventType))
            {
               // Update the event type attribute of the event node. Since
               // changes between events types are only allowed for related
               // event types, all of the other event attributes can be left
               // intact.
               model.setValue(event, "type", lastEventType);
               DocumentChangeEvent e = new DocumentChangeEvent(
                     DocumentChangeEvent.CHANGE, event, "type", eventType,
                     lastEventType);
               addUndoableOperation(model, e);
            }
         }
         dirty = false;
      }
   }

   private class ActionTypeSelectionHandler implements SelectionListener
   {
      private String lastActionType;

      private boolean dirty;

      public void widgetSelected(SelectionEvent e)
      {
         Combo combo = (Combo) e.getSource();
         actionType = EventActionStrings.XML_ACTIONS[combo.getSelectionIndex()];
         Element eventAction = (Element) node.getNode();
         Element action =
               (Element) eventAction.getElementsByTagName("action").item(0);
         lastActionType = action.getAttribute("type");

         // GUI changes required if the action type has been changed.
         if (!lastActionType.equals(actionType))
         {
            actionTypeChanged(managedForm.getToolkit(), newStateParent,
                  lastActionType, actionType);
            lastActionType = actionType;
            newStateParent.layout();
            parent.layout();
            dirty = true;
            markEditorDirty();
            commit();
         }
      }

      public void widgetDefaultSelected(SelectionEvent e)
      {
      }

      private void commit()
      {
         if (dirty)
         {
            Element eventAction = (Element) node.getNode();
            Element action = (Element)
                  eventAction.getElementsByTagName("action").item(0);
            // Replace the old action node with a new action node if the action
            // type has been changed.
            if (!action.getAttribute("type").equals(actionType))
            {
               Element newAction = (Element) action.cloneNode(true);
               newAction.setAttribute("type", actionType);
               if (action.getAttribute("type").equals("setState") &&
                   !actionType.equals("setState"))
               {
                  newAction.removeChild(newAction.getChildNodes().item(0));
               }
               else if (!action.getAttribute("type").equals("setState") &&
                        actionType.equals("setState"))
               {
                  Element element = model.getDocument().createElement("newState");
                  element.appendChild(model.getDocument().createTextNode("0"));
                  newAction.appendChild(element);
               }
               Node replacedNode = model.replaceNode(action, newAction);
               DocumentChangeEvent e = new DocumentChangeEvent(
                     DocumentChangeEvent.REPLACE, null, "type", action,
                     replacedNode);
               addUndoableOperation(model, e);
            }
         }
         dirty = false;
      }
   }

   private class ScopeSelectionHandler implements SelectionListener
   {
      private final Text scopeValue;

      private final String name;

      private String lastScope;

      private boolean dirty;

      public ScopeSelectionHandler(Text scopeValue, String name)
      {
         this.scopeValue = scopeValue;
         this.name = name;
      }

      public void widgetSelected(SelectionEvent e)
      {
         Combo combo = (Combo) e.getSource();
         int selected = combo.getSelectionIndex();

         Element scope = getScopeByName(name);
         lastScope = scope.getAttribute("type");
         String fromScopeType = EventActionStrings.XML_SCOPE[selected];
         if (!lastScope.equals(fromScopeType))
         {
            lastScope = fromScopeType;
            dirty = true;
            markEditorDirty();
            commit();
         }

         if (selected != 0)
         {
            scopeValue.setEnabled(true);
         }
         else
         {
            // System scope is selected
            scopeValue.setEnabled(false);
            scopeValue.setText("0x0");
         }
      }

      public void widgetDefaultSelected(SelectionEvent e)
      {
      }

      private void commit()
      {
         if (dirty)
         {
            Element scope = getScopeByName(name);
            String scopeType = scope.getAttribute("type");
            String value = scope.getAttribute("value");
            if (!scopeType.equals(lastScope))
            {
               model.setValue(scope, "type", lastScope);
               DocumentChangeEvent e = new DocumentChangeEvent(
                     DocumentChangeEvent.CHANGE, scope,
                     "type", scopeType, lastScope);
               addUndoableOperation(model, e);
               if (!scopeValue.getText().equals(value))
               {
                  model.setValue(scope, "value", scopeValue.getText());
                  e = new DocumentChangeEvent(
                        DocumentChangeEvent.CHANGE, scope, "value", value,
                        scopeValue.getText());
                  addUndoableOperation(model, e);
               }
            }
         }
         dirty = false;
      }

      private Element getScopeByName(String name)
      {
         Element eventAction = (Element) node.getNode();
         Element event = (Element)
               eventAction.getElementsByTagName("event").item(0);
         return (Element) event.getElementsByTagName(name).item(0);
      }
   }
   
   private class DataFilterNumberSizeSelectionHandler implements
         SelectionListener
   {
      private String lastSize;

      private boolean dirty;

      public void widgetSelected(SelectionEvent e)
      {
         Combo combo = (Combo) e.getSource();
         int selectionIndex = combo.getSelectionIndex();
         String size = null;
         size = EventActionStrings.DATA_FILTER_NUMBER_SIZE[selectionIndex];

         Element eventAction = (Element) node.getNode();
         NodeList nodeList = eventAction.getElementsByTagName("event");
         Element event = (Element) nodeList.item(0);
         nodeList = event.getElementsByTagName("dataFilter");
         Element dataFilter = (Element) nodeList.item(0);
         if (dataFilter != null)
         {
            lastSize = dataFilter.getAttribute("size");
            if (lastSize.equals(size))
            {
               return;
            }
            lastSize = size;
            dirty = true;
            markEditorDirty();
            commit();
         }
      }

      public void widgetDefaultSelected(SelectionEvent e)
      {
      }

      private void commit()
      {
         if (dirty)
         {
            Element eventAction = (Element) node.getNode();
            NodeList nodeList = eventAction.getElementsByTagName("event");
            Element event = (Element) nodeList.item(0);
            nodeList = event.getElementsByTagName("dataFilter");
            Element dataFilter = (Element) nodeList.item(0);
            if (dataFilter != null)
            {
               String size = dataFilter.getAttribute("size");
               if (!lastSize.equals(size))
               {
                  model.setValue(dataFilter, "size", lastSize);
                  DocumentChangeEvent e = new DocumentChangeEvent(
                        DocumentChangeEvent.CHANGE, dataFilter, "size", size,
                        lastSize);
                  addUndoableOperation(model, e);
               }
            }
            dirty = false;
         }
      }
   }
}
