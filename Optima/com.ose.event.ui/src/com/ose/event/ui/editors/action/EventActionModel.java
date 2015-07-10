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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import com.ose.xmleditor.model.DocumentModel;

public class EventActionModel extends DocumentModel
{
   public EventActionModel(Document document)
   {
      complement(document);
      init(document);
   }

   /* Create an element with default values. */
   public void createElement(String eventType, String actionType)
   {
      Element root = document.getDocumentElement();
      Element eventAction = getElement(eventType, actionType);
      root.appendChild(eventAction);

      fireElementAdded(eventAction);
   }

   /* Create an element with default values. */
   public Element getElement(String eventType, String actionType)
   {
      Element eventAction = document.createElement("eventAction");
      eventAction.setAttribute("state", "0");

      Element event = document.createElement("event");
      event.setAttribute("type", eventType);
      eventAction.appendChild(event);

      Element fromScope = document.createElement("fromScope");
      fromScope.setAttribute("type", "system");
      fromScope.setAttribute("value", "0x0");
      event.appendChild(fromScope);
      complementEvent(document, event);
      
      Element action = document.createElement("action");
      action.setAttribute("type", actionType);
      action.setAttribute("fileLineIncluded", "false");

      if (eventType.equalsIgnoreCase("send")
            || eventType.equalsIgnoreCase("receive")
            || eventType.equalsIgnoreCase("user"))
      {
         action.setAttribute("dataIncluded", "false");
      }

      action.setAttribute("not", "false");

      complementAction(document, action);
      eventAction.appendChild(action);

      return eventAction;
   }

   /* Create an element with provided values. */
   public void createElement(String contents)
   {
      Node root = document.getDocumentElement();
      List<Element> elements = getElements(contents);
      for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext();)
      {
         Element element = iterator.next();
         root.appendChild(element);
         fireElementAdded(element);
      }
   }

   /* Create an element with provided values. */
   public List<Element> getElements(String contents)
   {
      try
      {
         List<Element> elements = new ArrayList<Element>();
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setValidating(true);
         factory.setIgnoringElementContentWhitespace(true);
         factory.setIgnoringComments(true);
         DocumentBuilder builder = factory.newDocumentBuilder();
         builder.setEntityResolver(new EventActionEntityResolver());
         builder.setErrorHandler(new NullErrorHandler());
         Document doc = builder.parse(new InputSource(new StringReader(contents)));
         Element eventActions = doc.getDocumentElement();
         NodeList eventActionList = eventActions.getElementsByTagName("eventAction");
         for (int i = 0; i < eventActionList.getLength(); i++)
         {
            Element eventAction = (Element) eventActionList.item(i);
            Element event = (Element)
               eventAction.getElementsByTagName("event").item(0);
            Element action = (Element)
               eventAction.getElementsByTagName("action").item(0);
            complementEvent(doc, event);
            complementAction(doc, action);
            eventAction = (Element) document.importNode(eventAction, true);
            elements.add(eventAction);
         }
         return elements;
      }
      catch (Exception e)
      {
         throw new RuntimeException(
            "XML parsing error in EventActionModel.getElements()", e);
      }
   }

   private void complement(Document document)
   {
      Element root = document.getDocumentElement();
      if (root.getAttributeNode("initState") == null)
      {
         root.setAttribute("initState", "0");
      }

      NodeList eventActionList = root.getElementsByTagName("eventAction");
      for (int i = 0; i < eventActionList.getLength(); i++)
      {
         Element eventAction = (Element) eventActionList.item(i);
         
         NodeList eventList = eventAction.getElementsByTagName("event");
         Element event = (Element) eventList.item(0);
         complementEvent(document, event);
         
         NodeList actionList = eventAction.getElementsByTagName("action");
         Element action = (Element) actionList.item(0);
         complementAction(document, action);
      }
   }

   private void complementEvent(Document document, Element event)
   {
      String eventType = event.getAttribute("type");
      Element fromScope = null;
      Element toScope = null;
      Element ignoreCount = null;
      Element dataFilter = null;
      Element numberRange = null;
      Element errorRange = null;
      Element extraRange = null;
      Element onlyKernelErrors = null;

      NodeList elements = event.getElementsByTagName("fromScope");
      fromScope = getFirstElement(elements);
      elements = event.getElementsByTagName("toScope");
      toScope = getFirstElement(elements);
      elements = event.getElementsByTagName("dataFilter");
      dataFilter = getFirstElement(elements);
      elements = event.getElementsByTagName("ignoreCount");
      ignoreCount = getFirstElement(elements);
      elements = event.getElementsByTagName("numberRange");
      numberRange = getFirstElement(elements);
      elements = event.getElementsByTagName("errorRange");
      errorRange = getFirstElement(elements);
      elements = event.getElementsByTagName("extraRange");
      extraRange = getFirstElement(elements);
      elements = event.getElementsByTagName("onlyKernelErrors");
      onlyKernelErrors = getFirstElement(elements);

      if (eventType.equals("send") || eventType.equals("receive")
            || eventType.equals("swap") || eventType.equals("create")
            || eventType.equals("kill"))
      {
         if (toScope == null)
         {
            toScope = document.createElement("toScope");
            toScope.setAttribute("type", "system");
            toScope.setAttribute("value", "0x0");
            insertElementAfter(event, fromScope, toScope);
         }
      }

      if (ignoreCount == null)
      {
         ignoreCount = document.createElement("ignoreCount");
         ignoreCount.appendChild(document.createTextNode("0"));
         if (toScope != null)
         {
            insertElementAfter(event, toScope, ignoreCount);
         }
         else
         {
            insertElementAfter(event, fromScope, ignoreCount);
         }
      }

      if (eventType.equals("send") || eventType.equals("receive")
            || eventType.equals("user"))
      {
         if (numberRange == null)
         {
            numberRange = document.createElement("numberRange");
            numberRange.setAttribute("min", "0x0");
            numberRange.setAttribute("max", "0xFFFFFFFF");
            numberRange.setAttribute("not", "false");
            insertElementAfter(event, ignoreCount, numberRange);
         }
      }

      if (eventType.equals("error"))
      {
         if (errorRange == null)
         {
            errorRange = document.createElement("errorRange");
            errorRange.setAttribute("min", "0x0");
            errorRange.setAttribute("max", "0xFFFFFFFF");
            errorRange.setAttribute("not", "false");
            insertElementAfter(event, ignoreCount, errorRange);
         }
         if (extraRange == null)
         {
            extraRange = document.createElement("extraRange");
            extraRange.setAttribute("min", "0x0");
            extraRange.setAttribute("max", "0xFFFFFFFF");
            extraRange.setAttribute("not", "false");
            insertElementAfter(event, errorRange, extraRange);
         }
         if (onlyKernelErrors == null)
         {
            onlyKernelErrors = document.createElement("onlyKernelErrors");
            onlyKernelErrors.appendChild(document.createTextNode("false"));
            insertElementAfter(event, extraRange, onlyKernelErrors);
         }
      }
      
      if (eventType.equals("send") || eventType.equals("receive")
            || eventType.equals("free") || eventType.equals("user"))
      {
         if (dataFilter == null)
         {
            dataFilter = document.createElement("dataFilter");
            dataFilter.setAttribute("useSignalFilter", "false");
            dataFilter.setAttribute("offset", "0x0");
            dataFilter.setAttribute("size", "1");
            dataFilter.setAttribute("min", "0x0");
            dataFilter.setAttribute("max", "0xFFFFFFFFFFFFFFFF");
            dataFilter.setAttribute("not", "false");
            event.appendChild(dataFilter);
         }
      }

   }

   private void complementAction(Document document, Element action)
   {
      String actionType = action.getAttribute("type");
      
      if(actionType.equals("setState"))
      {
         NodeList newStates = action.getElementsByTagName("newState");
         Element newState = getFirstElement(newStates);
         
         if(newState == null)
         {
            newState = document.createElement("newState");
            newState.appendChild(document.createTextNode("0"));
            insertElementAfter(action, null, newState);
         }
      }
   }
   
   private Element getFirstElement(NodeList nodeList)
   {
      return nodeList.getLength() > 0 ? (Element) nodeList.item(0) : null;
   }

   private void insertElementAfter(Element parent, Element reference,
         Element insert)
   {
      if (reference != null && (reference = (Element) reference.getNextSibling()) != null)
      {
         parent.insertBefore(insert, reference);
      }
      else
      {
         parent.appendChild(insert);
      }
   }

   private static class NullErrorHandler implements ErrorHandler
   {
      public void warning(SAXParseException exception) throws SAXException
      {
      }

      public void error(SAXParseException exception) throws SAXException
      {
      }

      public void fatalError(SAXParseException exception) throws SAXException
      {
      }
   }
}
