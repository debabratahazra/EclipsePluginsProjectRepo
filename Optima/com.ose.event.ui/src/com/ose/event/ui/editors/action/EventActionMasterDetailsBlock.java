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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ose.event.ui.editors.action.detailparts.EventActionDetailsPageProvider;
import com.ose.xmleditor.model.DocumentChangeEvent;
import com.ose.xmleditor.model.DocumentChangeListener;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;
import com.ose.xmleditor.validation.Validator;

public class EventActionMasterDetailsBlock extends MasterDetailsBlock implements
      DocumentChangeListener
{
   public static final int STATE = 0;

   public static final int EVENT = 1;

   public static final int ACTION = 2;

   private final FormPage page;

   private SectionPart sectionPart;

   private Text initStateText;

   private TableViewer viewer;

   private final Validator validator;

   private Action cutAction;

   private Action copyAction;

   private Action pasteAction;

   private Action deleteAction;

   private InitStateHandler initStateHandler;

   private EventActionSorter comparator;

   public EventActionMasterDetailsBlock(FormPage page)
   {
      this.page = page;
      validator = new Validator(new EventActionConditionProvider());
   }

   public void dispose()
   {
      sectionPart.dispose();
      getEditor().getModel().removeDocumentChangedListener(this);
   }

   public void documentChanged(DocumentChangeEvent event)
   {
      DocumentModel model;
      MasterContentProvider contentProvider;
      Object[] elements;

      model = (DocumentModel) viewer.getInput();
      if (event.getType() == DocumentChangeEvent.ADD)
      {
         Table table;
         ValidationNode node;

         viewer.setInput(model);
         table = viewer.getTable();
         node = (ValidationNode) viewer.getElementAt(table.getItemCount() - 1);
         viewer.setSelection(new TableSelection(node), true);
      }
      else if (event.getType() == DocumentChangeEvent.REMOVE)
      {
         viewer.setInput(model);
      }
      else if (event.getType() == DocumentChangeEvent.REPLACE)
      {
         // ValidationNode node;

         // Reselect the replaced event action node to recreate its details
         // page. Give the event action table focus to avoid an otherwise
         // strange focus behavior in the details page when it is recreated.
         // viewer.setInput(model);
         // node = (ValidationNode) viewer.getElementAt(selectionIndex);
         // viewer.setSelection(new TableSelection(node), true);
         // viewer.getTable().setFocus();
      }
      else if (event.getType() == DocumentChangeEvent.CHANGE)
      {
         Element root = model.getDocument().getDocumentElement();
         String initStateValue = root.getAttribute("initState");
         initStateHandler.setIgnoreModify(true);
         Point point = null;
         if (initStateText.isFocusControl())
         {
            point = initStateText.getSelection();
         }
         initStateText.setText(initStateValue);
         if (point != null)
         {
            initStateText.setSelection(point.x, point.y);
         }
         validateTextBox(root, "initState", initStateText);
         initStateHandler.setIgnoreModify(false);
      }

      contentProvider = (MasterContentProvider) viewer.getContentProvider();
      elements = contentProvider.getElements(null);
      validator.validate(elements);
      detailsPart.refresh();
      viewer.refresh();
   }

   protected void createMasterPart(IManagedForm managedForm, Composite parent)
   {
      FormToolkit toolkit;
      GridData gd;
      Section section;
      Composite sectionClient;
      GridLayout layout;
      EventActionFormEditor editor;
      DocumentModel model;
      Element root;
      Label initStateLabel;
      Composite buttons;

      toolkit = managedForm.getToolkit();
      sashForm.setBackground(toolkit.getColors().getColor(IFormColors.TB_BG));

      gd = new GridData();
      gd.horizontalAlignment = SWT.FILL;
      gd.grabExcessHorizontalSpace = true;
      gd.minimumWidth = 600;
      gd.verticalAlignment = SWT.FILL;
      gd.grabExcessVerticalSpace = true;
      parent.setLayoutData(gd);

      section = toolkit.createSection(parent, Section.TITLE_BAR);
      section.setText("Event Actions");
      section.marginWidth = 5;
      section.marginHeight = 5;

      sectionClient = toolkit.createComposite(section);
      layout = new GridLayout();
      layout.numColumns = 3;
      layout.marginWidth = 2;
      layout.marginHeight = 2;
      sectionClient.setLayout(layout);

      section.setClient(sectionClient);
      sectionPart = new SectionPart(section);
      managedForm.addPart(sectionPart);

      editor = getEditor();
      model = editor.getModel();
      root = model.getDocument().getDocumentElement();

      initStateLabel = toolkit.createLabel(sectionClient, "Initial State:");
      initStateLabel.setLayoutData(new GridData());

      initStateText = toolkit.createText(sectionClient, "", SWT.BORDER);
      initStateText.setText(root.getAttribute("initState"));
      initStateText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      initStateHandler = new InitStateHandler(model, root, "initState");
      initStateText.addModifyListener(initStateHandler);
      initStateText.addFocusListener(initStateHandler);
      validateTextBox(root, "initState", initStateText);

      comparator = new EventActionSorter();

      Composite tableContainer = new Composite(sectionClient, SWT.NONE);
      GridData tableData = new GridData(GridData.FILL_BOTH);
      tableData.horizontalSpan = 2;
      tableContainer.setLayoutData(tableData);
      TableColumnLayout tableLayout = new TableColumnLayout();
      tableContainer.setLayout(tableLayout);

      Table table = createTable(toolkit, tableContainer);
      viewer = new TableViewer(table);
      createColumns(table, tableLayout);
      viewer.addSelectionChangedListener(new SelectionHandler(managedForm));
      viewer.setContentProvider(new MasterContentProvider());
      viewer.setLabelProvider(new MasterLabelProvider());
      viewer.setUseHashlookup(true);
      viewer.setInput(model);

      viewer.setComparator(comparator);
      model.addDocumentChangedListener(this);

      buttons = createMasterButtons(toolkit, sectionClient);
      gd = new GridData();
      gd.verticalAlignment = SWT.FILL;
      buttons.setLayoutData(gd);

      cutAction = new CutAction();
      copyAction = new CopyAction();
      pasteAction = new PasteAction();
      deleteAction = new DeleteAction();
      viewer.getTable().addFocusListener(new ActionEnablementHandler());
   }

   protected void createToolBarActions(IManagedForm managedForm)
   {
   }

   protected void registerPages(DetailsPart detailsPart)
   {
      EventActionFormEditor editor = getEditor();
      detailsPart.setPageProvider(new EventActionDetailsPageProvider(editor
            .getModel()));

      // Select the first event action when the editor is opened.
      if (viewer.getTable().getItemCount() > 0)
      {
         viewer.getTable().setSelection(0);
         page.getManagedForm().fireSelectionChanged(sectionPart,
               viewer.getSelection());
      }
   }

   private Table createTable(FormToolkit toolkit, Composite parent)
   {
      Table table = toolkit.createTable(parent, SWT.FULL_SELECTION | SWT.MULTI
            | SWT.BORDER);
      table.setHeaderVisible(true);
      return table;
   }

   private void createColumns(Table table, TableColumnLayout layout)
   {
      TableColumn column;

      column = new TableColumn(table, SWT.LEFT);
      column.setText("State");
      column.setMoveable(true);
      column.addSelectionListener(new EventTableSelectionAdapter(comparator,
            viewer, EventActionMasterDetailsBlock.STATE));
      layout.setColumnData(column, new ColumnWeightData(1, 20, true));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Event");
      column.setMoveable(true);
      column.addSelectionListener(new EventTableSelectionAdapter(comparator,
            viewer, EventActionMasterDetailsBlock.EVENT));
      layout.setColumnData(column, new ColumnWeightData(5, 200, true));

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Action");
      column.setMoveable(true);
      column.addSelectionListener(new EventTableSelectionAdapter(comparator,
            viewer, EventActionMasterDetailsBlock.ACTION));
      layout.setColumnData(column, new ColumnWeightData(2, 100, true));
   }

   private Composite createMasterButtons(FormToolkit toolkit, Composite parent)
   {
      Composite composite;
      Button button;

      composite = toolkit.createComposite(parent);
      composite.setLayout(new GridLayout());

      button = toolkit.createButton(composite, "Add...", SWT.PUSH);
      button.setLayoutData(new GridData());
      button.addSelectionListener(new CreateHandler());

      button = toolkit.createButton(composite, "Remove", SWT.PUSH);
      button.setLayoutData(new GridData());
      button.addSelectionListener(new RemoveHandler());

      return composite;
   }

   private EventActionFormEditor getEditor()
   {
      return (EventActionFormEditor) page.getEditor();
   }

   private IUndoableOperation addUndoableOperation(DocumentModel model,
         DocumentChangeEvent event)
   {
      IUndoableOperation operation = getUndoableOperation(model, event);
      EventActionFormEditor.getOperationHistory().add(operation);
      return operation;
   }

   private IUndoableOperation replaceUndoableOperation(DocumentModel model,
         DocumentChangeEvent event, IUndoableOperation replacedOperation)
   {
      IUndoableOperation operation = getUndoableOperation(model, event);
      EventActionFormEditor.getOperationHistory().replaceOperation(
            replacedOperation, new IUndoableOperation[] {operation});
      return operation;
   }

   private IUndoableOperation getUndoableOperation(DocumentModel model,
         DocumentChangeEvent event)
   {
      return new UndoableOperation(EventActionStrings.getUndoActionLabel(event
            .getType()), getEditor().getUndoContext(), model, event);
   }

   private void validateTextBox(Node node, String attributeName, Text textBox)
   {
      ValidationNode validationNode = new ValidationNode(node);
      validator.validate(new Object[] {validationNode});
      if (validationNode.hasAttributeErrors(attributeName))
      {
         textBox.setBackground(textBox.getDisplay().getSystemColor(
               SWT.COLOR_RED));
      }
      else
      {
         textBox.setBackground(textBox.getDisplay().getSystemColor(
               SWT.COLOR_WHITE));
      }
   }

   private class SelectionHandler implements ISelectionChangedListener
   {
      private final IManagedForm managedForm;

      SelectionHandler(IManagedForm managedForm)
      {
         this.managedForm = managedForm;
      }

      public void selectionChanged(SelectionChangedEvent event)
      {
         // if (viewer.getTable().getSelectionIndex() != -1)
         // {
         // selectionIndex = viewer.getTable().getSelectionIndex();
         // }
         managedForm.fireSelectionChanged(sectionPart, event.getSelection());
      }
   }

   private class InitStateHandler implements ModifyListener, FocusListener
   {
      private final DocumentModel model;

      private final Node node;

      private final String attributeName;

      private String oldText;

      private String text;

      private boolean dirty;

      private IUndoableOperation operation;

      private boolean ignoreModify;

      public InitStateHandler(DocumentModel model, Node node,
            String attributeName)
      {
         this.model = model;
         this.node = node;
         this.attributeName = attributeName;
         oldText = ((Element) node).getAttribute(attributeName);
         oldText = (oldText != null) ? oldText : "";
      }

      public void modifyText(ModifyEvent event)
      {
         Text textBox = (Text) event.getSource();
         text = textBox.getText();
         markDirty(textBox);
      }

      public void focusGained(FocusEvent event)
      {
      }

      public void focusLost(FocusEvent event)
      {
         operation = null;
      }

      private void markDirty(Text textBox)
      {
         if (isIgnoreModify())
         {
            return;
         }
         dirty = true;
         getEditor().markDirty(true);
         commit(textBox);
      }

      private void commit(Text textBox)
      {
         if (dirty && ((text != "") && (!oldText.equals(text))))
         {
            model.setValue(node, attributeName, text);
            DocumentChangeEvent e = new DocumentChangeEvent(
                  DocumentChangeEvent.CHANGE, node, attributeName, oldText,
                  text);
            if (operation == null)
            {
               operation = addUndoableOperation(model, e);
            }
            else
            {
               operation = replaceUndoableOperation(model, e, operation);
            }
            validateTextBox(node, attributeName, textBox);
         }
         dirty = false;
      }

      public boolean isIgnoreModify()
      {
         return ignoreModify;
      }

      public void setIgnoreModify(boolean ignoreModify)
      {
         this.ignoreModify = ignoreModify;
      }
   }

   private class RemoveHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         DocumentModel model = getEditor().getModel();
         IStructuredSelection selection = (IStructuredSelection) viewer
               .getSelection();

         for (Iterator i = selection.iterator(); i.hasNext();)
         {
            ValidationNode node = (ValidationNode) i.next();
            model.removeNode(node.getNode());
            DocumentChangeEvent e = new DocumentChangeEvent(
                  DocumentChangeEvent.REMOVE, node.getNode());
            addUndoableOperation(model, e);
         }
      }

      public void widgetDefaultSelected(SelectionEvent event)
      {
      }
   }

   private class CreateHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         CreateNewEventActionDialog dialog;
         int result;

         dialog = new CreateNewEventActionDialog(viewer.getControl().getShell());
         result = dialog.open();

         if (result == Window.OK)
         {
            EventActionModel model = (EventActionModel) getEditor().getModel();
            Element element = model.getElement(dialog.getSelectedEvent(),
                  dialog.getSelectedAction());
            Node newNode = model.addToRootNode(element);
            DocumentChangeEvent e = new DocumentChangeEvent(
                  DocumentChangeEvent.ADD, newNode);
            addUndoableOperation(model, e);
         }
      }

      public void widgetDefaultSelected(SelectionEvent event)
      {
      }
   }

   private class ActionEnablementHandler implements FocusListener
   {
      public void focusGained(FocusEvent event)
      {
         IActionBars actionBars = getEditor().getEditorSite().getActionBars();
         actionBars
               .setGlobalActionHandler(ActionFactory.CUT.getId(), cutAction);
         actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
               copyAction);
         actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
               pasteAction);
         actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
               deleteAction);
      }

      public void focusLost(FocusEvent event)
      {
         IActionBars actionBars = getEditor().getEditorSite().getActionBars();
         actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(), null);
         actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(), null);
         actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), null);
         actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), null);
      }
   }

   private class CutAction extends Action
   {
      public void run()
      {
         IStructuredSelection selection = (IStructuredSelection) viewer
               .getSelection();

         if (!selection.isEmpty())
         {
            DocumentModel model;
            String contents;
            Clipboard clipboard;

            model = getEditor().getModel();

            // Copy
            contents = "<!DOCTYPE eventActions SYSTEM \"eventaction.dtd\">\n";
            contents += "<eventActions>\n";
            for (Iterator i = selection.iterator(); i.hasNext();)
            {
               ValidationNode node = (ValidationNode) i.next();
               contents += node.toString();
            }
            contents += "</eventActions>";

            clipboard = new Clipboard(page.getSite().getShell().getDisplay());
            clipboard.setContents(new Object[] {contents},
                  new Transfer[] {TextTransfer.getInstance()});
            clipboard.dispose();

            // Delete
            for (Iterator i = selection.iterator(); i.hasNext();)
            {
               ValidationNode node = (ValidationNode) i.next();
               model.removeNode(node.getNode());
               DocumentChangeEvent e = new DocumentChangeEvent(
                     DocumentChangeEvent.REMOVE, node.getNode());
               addUndoableOperation(model, e);
            }
         }
      }
   }

   private class CopyAction extends Action
   {
      public void run()
      {
         IStructuredSelection selection = (IStructuredSelection) viewer
               .getSelection();

         if (!selection.isEmpty())
         {
            String contents;
            Clipboard clipboard;

            contents = "<!DOCTYPE eventActions SYSTEM \"eventaction.dtd\">\n";
            contents += "<eventActions>\n";
            for (Iterator i = selection.iterator(); i.hasNext();)
            {
               ValidationNode node = (ValidationNode) i.next();
               contents += node.toString();
            }
            contents += "</eventActions>";

            clipboard = new Clipboard(page.getSite().getShell().getDisplay());
            clipboard.setContents(new Object[] {contents},
                  new Transfer[] {TextTransfer.getInstance()});
            clipboard.dispose();
         }
      }
   }

   private class PasteAction extends Action
   {
      public void run()
      {
         Clipboard clipboard;
         String contents;

         clipboard = new Clipboard(page.getSite().getShell().getDisplay());
         contents = (String) clipboard.getContents(TextTransfer.getInstance());
         clipboard.dispose();

         // Ignore clipboard content that are not strings
         // or obviously not event action XML strings.
         if ((contents != null) && contents.contains("eventActions"))
         {
            EventActionModel model = (EventActionModel) getEditor().getModel();
            List<Element> elements = model.getElements(contents);
            for (Iterator<Element> i = elements.iterator(); i.hasNext();)
            {
               Element element = i.next();
               Node newNode = model.addToRootNode(element);
               DocumentChangeEvent e = new DocumentChangeEvent(
                     DocumentChangeEvent.ADD, newNode);
               addUndoableOperation(model, e);
            }
         }
      }
   }

   private class DeleteAction extends Action
   {
      public void run()
      {
         IStructuredSelection selection = (IStructuredSelection) viewer
               .getSelection();

         if (!selection.isEmpty())
         {
            DocumentModel model = getEditor().getModel();

            for (Iterator i = selection.iterator(); i.hasNext();)
            {
               ValidationNode node = (ValidationNode) i.next();
               model.removeNode(node.getNode());
               DocumentChangeEvent e = new DocumentChangeEvent(
                     DocumentChangeEvent.REMOVE, node.getNode());
               addUndoableOperation(model, e);
            }
         }
      }
   }

   private class MasterContentProvider implements IStructuredContentProvider
   {
      private Object[] elements;

      public Object[] getElements(Object inputElement)
      {
         return elements;
      }

      public void dispose()
      {
      }

      public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
         List nodes = new ArrayList();

         if (newInput instanceof DocumentModel)
         {
            DocumentModel model = (DocumentModel) newInput;
            Document document = model.getDocument();
            NodeList nodeList = document.getElementsByTagName("eventAction");
            if (nodeList != null)
            {
               for (int i = 0; i < nodeList.getLength(); i++)
               {
                  ValidationNode node = new ValidationNode(nodeList.item(i));
                  nodes.add(node);
               }
            }
            elements = nodes.toArray();
            validator.validate(elements);
         }
      }
   }

   private class TableSelection implements IStructuredSelection
   {
      private List selections;

      public TableSelection(ValidationNode node)
      {
         selections = new ArrayList();
         selections.add(node);
      }

      public Object getFirstElement()
      {
         return selections.get(0);
      }

      public Iterator iterator()
      {
         return selections.iterator();
      }

      public int size()
      {
         return selections.size();
      }

      public Object[] toArray()
      {
         return selections.toArray();
      }

      public List toList()
      {
         return selections;
      }

      public boolean isEmpty()
      {
         return selections.isEmpty();
      }
   }
}
