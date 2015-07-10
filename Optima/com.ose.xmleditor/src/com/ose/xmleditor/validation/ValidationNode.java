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

package com.ose.xmleditor.validation;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ose.xmleditor.validation.errors.AttributeError;
import com.ose.xmleditor.validation.errors.Error;
import com.ose.xmleditor.validation.errors.NodeError;

public class ValidationNode
{
   private final Node node;

   private List errors;

   private List elements;

   public ValidationNode(Node node)
   {
      errors = new ArrayList();
      elements = new ArrayList();
      this.node = node;

      if (node.hasChildNodes())
      {
         NodeList nodeList = node.getChildNodes();
         for (int i = 0; i < nodeList.getLength(); i++)
         {
            if (nodeList.item(i) instanceof Element)
            {
               elements.add(new ValidationNode(nodeList.item(i)));
            }
         }
      }
   }

   public Node getNode()
   {
      return node;
   }

   public String getNodeName()
   {
      return node.getNodeName();
   }
   
   public List getElements()
   {
      return elements;
   }
   
   public ValidationNode getElement(String name)
   {
      for(int i = 0; i < elements.size(); i++)
      {
         ValidationNode node = (ValidationNode) elements.get(i);
         if(name.equals(node.getNodeName()))
         {
            return node;
         }
      }
      
      return null;
   }

   public void addError(Error error)
   {
      errors.add(error);
   }

   public List getErrors()
   {
      List allErrors = new ArrayList();
      
      allErrors.addAll(errors);
      for (int i = 0; i < elements.size(); i++)
      {
         ValidationNode node = (ValidationNode) elements.get(i);
         allErrors.addAll(node.getErrors());
      }
      
      return allErrors;
   }
   
   public void clearErrors()
   {
      errors.clear();
   }

   public boolean hasErrors()
   {
      if (getErrors().size() > 0)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   public boolean hasChildErrors(int index)
   {
      for (int i = 0; i < errors.size(); i++)
      {
         Error error = (Error) errors.get(i);
         if (error instanceof NodeError)
         {
            NodeError nodeError = (NodeError) error;
            if (nodeError.getIndex() == index)
            {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasAttributeErrors(String attributeName)
   {
      for (int i = 0; i < errors.size(); i++)
      {
         Error error = (Error) errors.get(i);
         if (error instanceof AttributeError)
         {
            AttributeError attrError = (AttributeError) error;
            if (attrError.getAttributeName().equals(attributeName))
            {
               return true;
            }
         }
      }

      return false;
   }

   public String toString()
   {
      try
      {
         Source source = new DOMSource(node);
         StringWriter stringWriter = new StringWriter();
         Result result = new StreamResult(stringWriter);
         TransformerFactory factory = TransformerFactory.newInstance();
         factory.setAttribute("indent-number", new Integer(4));
         Transformer transformer = factory.newTransformer();

         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
         transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(source, result);
         return stringWriter.toString();
      }
      catch (TransformerException e)
      {
         throw new RuntimeException(
            "XML transformation error in ValidationNode.toString()", e);
      }
   }
}
