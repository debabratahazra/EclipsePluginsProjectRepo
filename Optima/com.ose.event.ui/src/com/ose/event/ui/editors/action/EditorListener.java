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

import java.util.Arrays;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.ose.event.ui.editors.action.detailparts.EventActionDetailsPage;
import com.ose.xmleditor.model.DocumentChangeEvent;
import com.ose.xmleditor.model.DocumentModel;

public class EditorListener implements ModifyListener, SelectionListener,
      FocusListener
{
   private final Node node;

   private final String attributeName;

   private final DocumentModel model;

   private boolean dirty;

   private String oldValue;

   private String value;

   private final EventActionDetailsPage page;

   private boolean ignoreModify;

   private IUndoableOperation operation;

   public EditorListener(DocumentModel model, Node node, String attributeName,
         EventActionDetailsPage page)
   {
      this.model = model;
      this.node = node;
      this.attributeName = attributeName;
      this.page = page;
      if (attributeName == null)
      {
         oldValue = node.getNodeValue();
      }
      else
      {
         oldValue = ((Element) node).getAttribute(attributeName);
      }
      value = oldValue;
   }

   public void modifyText(ModifyEvent e)
   {
      Object source = e.getSource();

      if (source instanceof Text)
      {
         Text textBox = (Text) source;
         value = textBox.getText();
         markDirty();
         commit();
      }
   }

   public void widgetSelected(SelectionEvent e)
   {
      Object source = e.getSource();

      if (source instanceof Button)
      {
         Button button = (Button) source;
         value = (button.getSelection() == true) ? "true" : "false";
         markDirty();
         commit();
      }
      else if (source instanceof Combo)
      {
         Combo combo = (Combo) source;
         String[] items = combo.getItems();
         int selectionIndex = combo.getSelectionIndex();
         if (Arrays.equals(items, EventActionStrings.SCOPE))
         {
            value = EventActionStrings.XML_SCOPE[selectionIndex];
            markDirty();
            commit();
         }
      }

      operation = null;
      oldValue = value;
   }

   public void widgetDefaultSelected(SelectionEvent e)
   {
   }

   public void focusGained(FocusEvent e)
   {
   }

   public void focusLost(FocusEvent e)
   {
      operation = null;
      oldValue = value;
   }

   private void markDirty()
   {
      if (ignoreModify)
      {
         return;
      }
      if ((value == null)
            || ((attributeName == null) && (node.getNodeValue().equals(value)))
            || ((attributeName != null) && ((Element) node).getAttribute(
                  attributeName).equals(value)))
      {
         return;
      }
      dirty = true;
      page.markEditorDirty();
   }

   private void commit()
   {
      if (dirty && (value != null))
      {
         model.setValue(node, attributeName, value);
         DocumentChangeEvent event = new DocumentChangeEvent(
               DocumentChangeEvent.CHANGE, node, attributeName, oldValue, value);
         if (operation == null)
         {
            operation = page.addUndoableOperation(model, event);
         }
         else
         {
            operation = page.replaceUndoableOperation(model, event, operation);
         }
      }
      dirty = false;
   }

   public void setIgnoreModify(boolean ignoreModify)
   {
      this.ignoreModify = ignoreModify;
   }
}
