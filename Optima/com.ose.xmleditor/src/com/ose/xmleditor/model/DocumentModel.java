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

package com.ose.xmleditor.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class DocumentModel
{
   protected Document document;
   
   private List documentChangeListeners;
   
   public Document getDocument()
   {
      return document;
   }

   public void addDocumentChangedListener(DocumentChangeListener listener)
   {
      documentChangeListeners.add(listener);
   }

   public void removeDocumentChangedListener(DocumentChangeListener listener)
   {
      documentChangeListeners.remove(listener);
   }

   public void setValue(Node node, String attributeName, String value)
   {
      if (node instanceof Element)
      {
         Element element = (Element) node;
         String oldValue = element.getAttribute(attributeName);

         if (!oldValue.equals(value))
         {
            element.setAttribute(attributeName, value);
            fireValueChanged(element, attributeName, value);
         }
      }
      else
      {
         String oldValue = node.getNodeValue();

         if (!oldValue.equals(value))
         {
            node.setNodeValue(value);
            fireValueChanged(node, value);
         }
      }
   }

   public abstract void createElement(String argument_1, String argument_2);

   public abstract void createElement(String argument);

   public Node addToRootNode(Node node)
   {
      if (node != null)
      {
         Node newNode = document.getDocumentElement().appendChild(node);
         fireElementAdded((Element) newNode);
         return newNode;
      }
      return null;
   }

   public void removeNode(Node node)
   {
      if (node != null)
      {
         Node parent = (Node) node.getParentNode();
         parent.removeChild(node);
         fireNodeRemoved(node);
      }
   }

   public Node replaceNode(Node oldNode, Node newNode)
   {
      if ((oldNode != null) && (newNode != null))
      {
         Node parent = (Node) oldNode.getParentNode();
         parent.removeChild(oldNode);
         Node replaceNode = parent.appendChild(newNode);
         fireNodeReplaced(newNode);
         return replaceNode;
      }
      return null;
   }

   protected void init(Document document)
   {
      this.document = document;
      documentChangeListeners = new ArrayList();
   }
   
   protected void fireValueChanged(Node node, String value)
   {
      fireEvent(new DocumentChangeEvent(DocumentChangeEvent.CHANGE, node));
   }

   protected void fireValueChanged(Element element, String attributeName,
         String value)
   {
      fireEvent(new DocumentChangeEvent(DocumentChangeEvent.CHANGE, element));
   }

   protected void fireElementAdded(Element element)
   {
      fireEvent(new DocumentChangeEvent(DocumentChangeEvent.ADD, element));
   }

   protected void fireNodeRemoved(Node node)
   {
      fireEvent(new DocumentChangeEvent(DocumentChangeEvent.REMOVE, node));
   }

   protected void fireNodeReplaced(Node node)
   {
      fireEvent(new DocumentChangeEvent(DocumentChangeEvent.REPLACE, node));
   }

   private void fireEvent(DocumentChangeEvent event)
   {
      for (Iterator iter = documentChangeListeners.iterator(); iter.hasNext();)
      {
         DocumentChangeListener listener = (DocumentChangeListener) iter.next();
         listener.documentChanged(event);
      }
   }
}
