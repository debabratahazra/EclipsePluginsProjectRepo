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

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Node;
import com.ose.xmleditor.model.DocumentModel;

public class EditorListener implements ModifyListener, SelectionListener
{
   private final Node node;

   private final String attributeName;

   private final DocumentModel model;

   public EditorListener(DocumentModel model, Node node, String attributeName)
   {
      this.model = model;
      this.node = node;
      this.attributeName = attributeName;
   }

   public void modifyText(ModifyEvent e)
   {
      Object source = e.getSource();

      if (source instanceof Text)
      {
         Text textBox = (Text) source;
         String text = textBox.getText();
         model.setValue(node, attributeName, text);
      }
   }

   public void widgetSelected(SelectionEvent e)
   {
      Object source = e.getSource();

      if (source instanceof Button)
      {
         Button button = (Button) source;
         model.setValue(node, attributeName,
               button.getSelection() == true ? "true" : "false");
      }
      else if (source instanceof Combo)
      {
         Combo combo = (Combo) source;
         model.setValue(node, attributeName,
               ProfiledProcessStrings.XML_TYPES[combo.getSelectionIndex()]);
      }
   }

   public void widgetDefaultSelected(SelectionEvent e)
   {
   }
}
