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

package com.ose.prof.ui.editors.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
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
import com.ose.prof.ui.editors.process.detailparts.ProcessDetailsPageProvider;
import com.ose.xmleditor.model.DocumentChangeEvent;
import com.ose.xmleditor.model.DocumentChangeListener;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;
import com.ose.xmleditor.validation.Validator;

public class ProcessMasterDetailsBlock extends MasterDetailsBlock implements
      DocumentChangeListener
{
   private final FormPage page;

   private SectionPart sectionPart;

   private TableViewer viewer;

   private final Validator validator;

   public ProcessMasterDetailsBlock(FormPage page)
   {
      this.page = page;
      validator = new Validator(new ProcessConditionProvider());
   }

   public void dispose()
   {
      sectionPart.dispose();
      ProcessFormEditor editor = (ProcessFormEditor) page.getEditor();
      editor.getModel().removeDocumentChangedListener(this);
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
      ProcessFormEditor editor;
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
      section.setText("Profiled Processes");
      section.marginWidth = 5;
      section.marginHeight = 5;

      sectionClient = toolkit.createComposite(section);
      layout = new GridLayout();
      layout.numColumns = 2;
      layout.marginWidth = 2;
      layout.marginHeight = 2;
      sectionClient.setLayout(layout);

      section.setClient(sectionClient);
      sectionPart = new SectionPart(section);
      managedForm.addPart(sectionPart);

      editor = (ProcessFormEditor) page.getEditor();
      viewer = new TableViewer(createTable(toolkit, sectionClient));
      viewer.addSelectionChangedListener(new SelectionHandler(managedForm));
      viewer.setContentProvider(new MasterContentProvider());
      viewer.setLabelProvider(new MasterLabelProvider());
      viewer.setUseHashlookup(true);
      viewer.setInput(editor.getModel());
      editor.getModel().addDocumentChangedListener(this);

      buttons = createMasterButtons(toolkit, sectionClient);
      gd = new GridData();
      gd.verticalAlignment = SWT.FILL;
      buttons.setLayoutData(gd);
   }

   protected void createToolBarActions(IManagedForm managedForm)
   {
   }

   protected void registerPages(DetailsPart detailsPart)
   {
      ProcessFormEditor editor = (ProcessFormEditor) page.getEditor();
      detailsPart.setPageProvider(new ProcessDetailsPageProvider(editor.getModel()));

      // Select the first process when the editor is opened.
      if (viewer.getTable().getItemCount() > 0)
      {
         viewer.getTable().setSelection(0);
         page.getManagedForm().fireSelectionChanged(sectionPart,
               viewer.getSelection());
      }
   }

   private Table createTable(FormToolkit toolkit, Composite parent)
   {
      Composite comp;
      TableColumnLayout layout;
      Table table;
      TableColumn column;

      comp = new Composite(parent, SWT.NONE);
      comp.setLayoutData(new GridData(GridData.FILL_BOTH));
      layout = new TableColumnLayout();
      comp.setLayout(layout);

      table = toolkit.createTable(comp,
                                  SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);
      table.setHeaderVisible(true);

      column = new TableColumn(table, SWT.LEFT);
      column.setText("Process");
      layout.setColumnData(column, new ColumnWeightData(1, 10, false));

      return table;
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

   private class SelectionHandler implements ISelectionChangedListener
   {
      private final IManagedForm managedForm;

      SelectionHandler(IManagedForm managedForm)
      {
         this.managedForm = managedForm;
      }

      public void selectionChanged(SelectionChangedEvent event)
      {
         managedForm.fireSelectionChanged(sectionPart, event.getSelection());
      }
   }

   private class RemoveHandler implements SelectionListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         ProcessFormEditor editor = (ProcessFormEditor) page.getEditor();
         DocumentModel model = editor.getModel();
         IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();

         for (Iterator i = selection.iterator(); i.hasNext();)
         {
            ValidationNode node = (ValidationNode) i.next();
            model.removeNode(node.getNode());
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
         ProcessFormEditor editor = (ProcessFormEditor) page.getEditor();
         editor.getModel().createElement("id", "");
      }

      public void widgetDefaultSelected(SelectionEvent event)
      {
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
            NodeList nodeList = document.getElementsByTagName("process");
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

   private class MasterLabelProvider extends LabelProvider implements
         ITableLabelProvider
   {
      public String getColumnText(Object element, int columnIndex)
      {
         if (element instanceof ValidationNode)
         {
            ValidationNode validationNode = (ValidationNode) element;
            Element process = (Element) validationNode.getNode();

            switch (columnIndex)
            {
               case 0:
                  NodeList children = process.getChildNodes();
                  Node child = children.item(0);
                  return ((child != null) ? child.getNodeValue() : null);
            }
         }

         return null;
      }

      public Image getColumnImage(Object element, int columnIndex)
      {
         Image image = null;

         if ((columnIndex == 0) && (element instanceof ValidationNode))
         {
            ValidationNode node = (ValidationNode) element;
            if (node.hasErrors())
            {
               image = PlatformUI.getWorkbench().getSharedImages().getImage(
                     ISharedImages.IMG_OBJS_ERROR_TSK);
            }
         }

         return image;
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
