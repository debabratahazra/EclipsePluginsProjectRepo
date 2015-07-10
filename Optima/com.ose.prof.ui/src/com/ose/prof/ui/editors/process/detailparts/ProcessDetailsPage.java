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

package com.ose.prof.ui.editors.process.detailparts;

import java.util.HashMap;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
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
import com.ose.prof.ui.editors.process.EditorListener;
import com.ose.prof.ui.editors.process.ProfiledProcessStrings;
import com.ose.xmleditor.model.DocumentModel;
import com.ose.xmleditor.validation.ValidationNode;

public class ProcessDetailsPage implements IDetailsPage
{
   protected IManagedForm managedForm;

   protected final DocumentModel model;

   private Combo processTypeCombo;

   private Text processTypeValue;
   
   private ValidationNode node;

   private boolean dirty;
   
   private Color RED;
   
   private Color WHITE;
   
   /**
    * The editor listeners are stored in this map with the class as key.
    */
   private HashMap listeners;

   public ProcessDetailsPage(DocumentModel model)
   {
      this.model = model;
      dirty = false;
      listeners = new HashMap();
   }

   public void createContents(Composite parent)
   {
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
      gd.horizontalAlignment = SWT.FILL;
      section.setLayoutData(gd);

      Composite client = toolkit.createComposite(section);
      layout = new GridLayout(3, true);
      client.setLayout(layout);

      toolkit.createLabel(client, "Process:");

      processTypeCombo = new Combo(client, SWT.DROP_DOWN | SWT.READ_ONLY);
      processTypeCombo.setItems(ProfiledProcessStrings.TYPES);
      processTypeCombo.setVisibleItemCount(processTypeCombo.getItemCount());
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      processTypeCombo.setLayoutData(gd);

      processTypeValue = toolkit.createText(client, "", SWT.BORDER);
      gd = new GridData();
      gd.grabExcessHorizontalSpace = true;
      gd.horizontalAlignment = SWT.FILL;
      processTypeValue.setLayoutData(gd);

      section.setClient(client);
      final SectionPart sectionPart = new SectionPart(section);
      managedForm.addPart(sectionPart);
      
      RED = managedForm.getForm().getDisplay().getSystemColor(SWT.COLOR_RED);
      WHITE = managedForm.getForm().getDisplay().getSystemColor(SWT.COLOR_WHITE);
   }

   public void commit(boolean onSave)
   {
      dirty = false;
   }

   public void dispose()
   {
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

   public void refresh()
   {
      if(node.hasAttributeErrors("type"))
      {
         processTypeCombo.setBackground(RED);
      }
      else
      {
         processTypeCombo.setBackground(WHITE);
      }
      
      if(node.hasChildErrors(0))
      {
         processTypeValue.setBackground(RED);
         processTypeValue.setFocus();
      }
      else
      {
         processTypeValue.setBackground(WHITE);
      }
   }

   public void setFocus()
   {
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
            Element process = (Element) node.getNode();

            String type = process.getAttribute("type");
            for (int i = 0; i < ProfiledProcessStrings.XML_TYPES.length; i++)
            {
               if (ProfiledProcessStrings.XML_TYPES[i].equals(type))
               {
                  processTypeCombo.select(i);
               }
            }
            
            setEditorListener(processTypeCombo, process, "type");

            NodeList children = process.getChildNodes();
            Node child = children.item(0);
            if (child == null)
            {
               child = model.getDocument().createTextNode("");
               process.appendChild(child);
            }
            String id = child.getNodeValue();
            processTypeValue.setText(id);
            
            setEditorListener(processTypeValue, child, null);
            
            refresh();
         }
      }
   }

   protected void setEditorListener(Combo combo, Node node, String attributeName)
   {
      EditorListener listener = (EditorListener) listeners.get(node
            .getNodeName()
            + attributeName);
      if (listener != null)
      {
         combo.removeSelectionListener(listener);
         listeners.remove(node.getNodeName() + attributeName);
      }

      listener = new EditorListener(model, node, attributeName);
      listeners.put(node.getNodeName() + attributeName, listener);

      combo.addSelectionListener(listener);
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
         listeners.remove(node.getNodeName() + attributeName);
      }

      listener = new EditorListener(model, node, attributeName);
      listeners.put(node.getNodeName() + attributeName, listener);

      button.addSelectionListener(listener);
   }

   protected void setEditorListener(Text text, Node node, String attributeName)
   {
      EditorListener listener = (EditorListener) listeners.get(node
            .getNodeName()
            + attributeName);
      if (listener != null)
      {
         text.removeModifyListener(listener);
         listeners.remove(node.getNodeName() + attributeName);
      }

      listener = new EditorListener(model, node, attributeName);
      listeners.put(node.getNodeName() + attributeName, listener);

      text.addModifyListener(listener);
   }
}
