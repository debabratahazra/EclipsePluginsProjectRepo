/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.event.format;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import com.ose.event.ui.EventPlugin;
import com.ose.system.AllocEventActionPoint;
import com.ose.system.BindEventActionPoint;
import com.ose.system.CreateEventActionPoint;
import com.ose.system.ErrorEventActionPoint;
import com.ose.system.EventActionPoint;
import com.ose.system.FreeEventActionPoint;
import com.ose.system.KillEventActionPoint;
import com.ose.system.ReceiveEventActionPoint;
import com.ose.system.SendEventActionPoint;
import com.ose.system.SwapEventActionPoint;
import com.ose.system.TimeoutEventActionPoint;
import com.ose.system.UserEventActionPoint;

/**
 * This class is used for reading event action XML files.
 */
public class EventActionXMLReader
{
   /**
    * Read the event actions from the specified event action XML file.
    *
    * @param file  the event action XML file to read.
    * @return the event actions from the specified event action XML file.
    * @throws IOException  if an I/O exception occurred.
    * @throws ParserConfigurationException  if an XML parser configuration
    * exception occurred.
    * @throws SAXException  if an XML parsing exception occurred.
    */
   public EventActions read(File file)
      throws IOException, ParserConfigurationException, SAXException
   {
      List eventActions;
      int initState;
      DocumentBuilderFactory factory;
      EventActionXMLErrorHandler errorHandler;
      DocumentBuilder builder;
      Document document;
      String errors;
      Element root;
      NodeList elements;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      eventActions = new ArrayList();
      factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setIgnoringElementContentWhitespace(true);
      factory.setIgnoringComments(true);
      errorHandler = new EventActionXMLErrorHandler();
      builder = factory.newDocumentBuilder();
      builder.setErrorHandler(errorHandler);
      builder.setEntityResolver(new EventActionXMLEntityResolver());
      document = builder.parse(file);
      errors = errorHandler.getErrors();
      if (errors != null)
      {
         throw new SAXException(errors);
      }
      root = document.getDocumentElement();
      initState = parseU32(root.getAttribute("initState"));
      elements = root.getChildNodes();
      boolean hasSignalDataFilterTag = false;

      for (int i = 0; i < elements.getLength(); i++)
      {
         Element eventActionElement;
         Element eventElement;
         NodeList eventNodes;
         Element actionElement;
         int state = 0;
         String eventType;
         int fromScopeType = 0;
         int fromScopeId = 0;
         String fromNamePattern = null;
         int toScopeType = 0;
         int toScopeId = 0;
         String toNamePattern = null;
         int ignoreCount = 0;
         int numberMin = 0;
         int numberMax = 0xFFFFFFFF;
         boolean numberRangeNot = false;
         int errorMin = 0;
         int errorMax = 0xFFFFFFFF;
         boolean errorRangeNot = false;
         int extraMin = 0;
         int extraMax = 0xFFFFFFFF;
         boolean extraRangeNot = false;
         boolean onlyKernelErrors = false;
         int actionType = 0;
         boolean actionNot = false;
         boolean fileLineIncluded = false;
         boolean dataIncluded = false;
         int newState = 0;
         boolean useSignalFilter = false;
         long filterDataOffset = 0;
         int filterDataSize = 0;
         boolean filterDataRangeNot = false;
         long filterDataMin = 0;
         long filterDataMax = 0xFFFFFFFFFFFFFFFFL;

         eventActionElement = (Element) elements.item(i);
         state = parseU32(eventActionElement.getAttribute("state"));

         eventElement = (Element) eventActionElement.getFirstChild();
         eventType = eventElement.getAttribute("type");
         eventNodes = eventElement.getChildNodes();
         for (int j = 0; j < eventNodes.getLength(); j++)
         {
            Element child = (Element) eventNodes.item(j);
            String name = child.getNodeName();
            if (name.equals("fromScope"))
            {
               fromScopeType = getScopeType(child.getAttribute("type"));
               if (fromScopeType != EventActionPoint.SCOPE_NAME_PATTERN)
               {
                  fromScopeId = parseU32(child.getAttribute("value"));
               }
               else
               {
                  fromNamePattern = child.getAttribute("value");
               }
            }
            else if (name.equals("toScope"))
            {
               toScopeType = getScopeType(child.getAttribute("type"));
               if (toScopeType != EventActionPoint.SCOPE_NAME_PATTERN)
               {
                  toScopeId = parseU32(child.getAttribute("value"));
               }
               else
               {
                  toNamePattern = child.getAttribute("value");
               }
            }
            else if (name.equals("ignoreCount"))
            {
               ignoreCount = parseU32(child.getFirstChild().getNodeValue());
            }
            else if (name.equals("numberRange"))
            {
               numberMin = parseU32(child.getAttribute("min"));
               numberMax = parseU32(child.getAttribute("max"));
               numberRangeNot = parseBoolean(child.getAttribute("not"));
            }
            else if (name.equals("errorRange"))
            {
               errorMin = parseU32(child.getAttribute("min"));
               errorMax = parseU32(child.getAttribute("max"));
               errorRangeNot = parseBoolean(child.getAttribute("not"));
            }
            else if (name.equals("extraRange"))
            {
               extraMin = parseU32(child.getAttribute("min"));
               extraMax = parseU32(child.getAttribute("max"));
               extraRangeNot = parseBoolean(child.getAttribute("not"));
            }
            else if (name.equals("onlyKernelErrors"))
            {
               onlyKernelErrors = parseBoolean(child.getFirstChild().getNodeValue());
            }
            else if (name.equals("dataFilter"))
            {
               useSignalFilter = parseBoolean(child.getAttribute("useSignalFilter"));
               if (useSignalFilter)
               {
                  hasSignalDataFilterTag = true;
               }
               filterDataOffset = parseU64(child.getAttribute("offset"));
               filterDataSize = parseU32(child.getAttribute("size"));
               filterDataMin = parseU64(child.getAttribute("min"));
               filterDataMax = parseU64(child.getAttribute("max"));
               filterDataRangeNot = parseBoolean(child.getAttribute("not"));
            }
            else
            {
               // We have already validated against the DTD, so if we are
               // here it must be a newer version of the DTD with added tags.
               // Be forgiving and ignore such tags.
            }
         }

         actionElement = (Element) eventActionElement.getLastChild();
         actionType = getAction(actionElement.getAttribute("type"));
         actionNot = parseBoolean(actionElement.getAttribute("not"));
         fileLineIncluded = parseBoolean(actionElement.getAttribute("fileLineIncluded"));
         dataIncluded = parseBoolean(actionElement.getAttribute("dataIncluded"));
         if (actionElement.hasChildNodes())
         {
            Element newStateElement = (Element) actionElement.getFirstChild();
            newState = parseU32(newStateElement.getFirstChild().getNodeValue());
         }

         eventActions.add(createEventActionPoint(state,
                                                 eventType,
                                                 fromScopeType,
                                                 fromScopeId,
                                                 fromNamePattern,
                                                 toScopeType,
                                                 toScopeId,
                                                 toNamePattern,
                                                 ignoreCount,
                                                 numberMin,
                                                 numberMax,
                                                 numberRangeNot,
                                                 errorMin,
                                                 errorMax,
                                                 errorRangeNot,
                                                 extraMin,
                                                 extraMax,
                                                 extraRangeNot,
                                                 onlyKernelErrors,
                                                 actionType,
                                                 actionNot,
                                                 fileLineIncluded,
                                                 dataIncluded,
                                                 newState,
                                                 useSignalFilter,
                                                 filterDataOffset,
                                                 filterDataSize,
                                                 filterDataRangeNot,
                                                 filterDataMin,
                                                 filterDataMax));
      }

      return new EventActions(eventActions, initState, hasSignalDataFilterTag);
   }

   private static int getScopeType(String scopeType) throws SAXException
   {
      if (scopeType.equals("system"))
      {
         return EventActionPoint.SCOPE_SYSTEM;
      }
      else if (scopeType.equals("segment"))
      {
         return EventActionPoint.SCOPE_SEGMENT_ID;
      }
      else if (scopeType.equals("block"))
      {
         return EventActionPoint.SCOPE_BLOCK_ID;
      }
      else if (scopeType.equals("process"))
      {
         return EventActionPoint.SCOPE_ID;
      }
      else if (scopeType.equals("namePattern"))
      {
         return EventActionPoint.SCOPE_NAME_PATTERN;
      }
      else
      {
         throw new SAXException(
               "Invalid scope type attribute value '" + scopeType + "'");
      }
   }

   private static int getAction(String action) throws SAXException
   {
      if (action.equals("trace"))
      {
         return EventActionPoint.ACTION_TRACE;
      }
      else if (action.equals("notify"))
      {
         return EventActionPoint.ACTION_NOTIFY;
      }
      else if (action.equals("intercept"))
      {
         return EventActionPoint.ACTION_INTERCEPT;
      }
      else if (action.equals("setState"))
      {
         return EventActionPoint.ACTION_SET_STATE;
      }
      else if (action.equals("enableTrace"))
      {
         return EventActionPoint.ACTION_ENABLE_TRACE;
      }
      else if (action.equals("disableTrace"))
      {
         return EventActionPoint.ACTION_DISABLE_TRACE;
      }
      else if (action.equals("user"))
      {
         return EventActionPoint.ACTION_USER;
      }
      else if (action.equals("enableHWTrace"))
      {
         return EventActionPoint.ACTION_ENABLE_HW_TRACE;
      }
      else if (action.equals("disableHWTrace"))
      {
         return EventActionPoint.ACTION_DISABLE_HW_TRACE;
      }
      else
      {
         throw new SAXException(
               "Invalid action type attribute value '" + action + "'");
      }
   }

   private static int parseU32(String s) throws SAXException
   {
      long value;

      if (s == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         value = Long.decode(s.trim()).longValue();
      }
      catch (NumberFormatException e)
      {
         throw new SAXException(
               "Invalid element or attribute integer value '" + s + "'");
      }
      if ((value < 0L) || (value > 0xFFFFFFFFL))
      {
         throw new SAXException(
               "Invalid element or attribute integer value '" + s + "'");
      }
      return (int) (0xFFFFFFFFL & value);
   }

   private static long parseU64(String s) throws SAXException
   {
      long value;
      BigInteger bi = null;
      if (s == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         int radix = 10;
         if (s.startsWith("0x") || s.startsWith("0X"))
         {
            radix = 16;
         }
         if (radix == 16)
         {
            bi = new BigInteger(s.substring(2, s.length()), radix);
         }
         else
         {
            bi = new BigInteger(s);
         }
         value = bi.longValue();
      }
      catch (NumberFormatException e)
      {
         throw new SAXException("Invalid element or attribute long value '" + s
               + "'");
      }
      BigInteger max = new BigInteger("FFFFFFFFFFFFFFFF", 16);
      BigInteger min = new BigInteger("0", 16);
      if ((bi.compareTo(min) < 0) || (bi.compareTo(max) > 0))
      {
         throw new SAXException("Invalid element or attribute long value '" + s
               + "'");
      }
      return (0xFFFFFFFFFFFFFFFFL & value);
   }

   private static boolean parseBoolean(String s)
   {
      return Boolean.valueOf(s).booleanValue();
   }

   private static EventActionPoint createEventActionPoint(
         int state,
         String eventType,
         int fromScopeType,
         int fromScopeId,
         String fromNamePattern,
         int toScopeType,
         int toScopeId,
         String toNamePattern,
         int ignoreCount,
         int numberMin,
         int numberMax,
         boolean numberRangeNot,
         int errorMin,
         int errorMax,
         boolean errorRangeNot,
         int extraMin,
         int extraMax,
         boolean extraRangeNot,
         boolean onlyKernelErrors,
         int actionType,
         boolean actionNot,
         boolean fileLineIncluded,
         boolean dataIncluded,
         int newState,
         boolean useSignalFilter,
         long filterDataOffset,
         int filterDataSize,
         boolean filterDataRangeNot,
         long filterDataMin,
         long filterDataMax)
      throws SAXException
   {
      if (eventType.equals("send"))
      {
         return new SendEventActionPoint(state,
                                         fromScopeType,
                                         fromScopeId,
                                         fromNamePattern,
                                         toScopeType,
                                         toScopeId,
                                         toNamePattern,
                                         0,
                                         0,
                                         null,
                                         actionNot,
                                         actionType,
                                         fileLineIncluded,
                                         dataIncluded,
                                         newState,
                                         numberRangeNot,
                                         numberMin,
                                         numberMax,
                                         ignoreCount,
                                         0,
                                         useSignalFilter,
                                         filterDataOffset,
                                         filterDataSize,
                                         filterDataRangeNot,
                                         filterDataMin,
                                         filterDataMax);
      }
      else if (eventType.equals("receive"))
      {
         return new ReceiveEventActionPoint(state,
                                            fromScopeType,
                                            fromScopeId,
                                            fromNamePattern,
                                            toScopeType,
                                            toScopeId,
                                            toNamePattern,
                                            0,
                                            0,
                                            null,
                                            actionNot,
                                            actionType,
                                            fileLineIncluded,
                                            dataIncluded,
                                            newState,
                                            numberRangeNot,
                                            numberMin,
                                            numberMax,
                                            ignoreCount,
                                            0,
                                            useSignalFilter,
                                            filterDataOffset,
                                            filterDataSize,
                                            filterDataRangeNot,
                                            filterDataMin,
                                            filterDataMax);
      }
      else if (eventType.equals("swap"))
      {
         return new SwapEventActionPoint(state,
                                         fromScopeType,
                                         fromScopeId,
                                         fromNamePattern,
                                         toScopeType,
                                         toScopeId,
                                         toNamePattern,
                                         0,
                                         0,
                                         null,
                                         actionNot,
                                         actionType,
                                         fileLineIncluded,
                                         newState,
                                         ignoreCount,
                                         0);
      }
      else if (eventType.equals("create"))
      {
         return new CreateEventActionPoint(state,
                                           fromScopeType,
                                           fromScopeId,
                                           fromNamePattern,
                                           toScopeType,
                                           toScopeId,
                                           toNamePattern,
                                           0,
                                           0,
                                           null,
                                           actionNot,
                                           actionType,
                                           fileLineIncluded,
                                           newState,
                                           ignoreCount,
                                           0);
      }
      else if (eventType.equals("kill"))
      {
         return new KillEventActionPoint(state,
                                         fromScopeType,
                                         fromScopeId,
                                         fromNamePattern,
                                         toScopeType,
                                         toScopeId,
                                         toNamePattern,
                                         0,
                                         0,
                                         null,
                                         actionNot,
                                         actionType,
                                         fileLineIncluded,
                                         newState,
                                         ignoreCount,
                                         0);
      }
      else if (eventType.equals("error"))
      {
         return new ErrorEventActionPoint(state,
                                          fromScopeType,
                                          fromScopeId,
                                          fromNamePattern,
                                          0,
                                          0,
                                          null,
                                          actionNot,
                                          actionType,
                                          fileLineIncluded,
                                          newState,
                                          onlyKernelErrors,
                                          errorRangeNot,
                                          errorMin,
                                          errorMax,
                                          extraRangeNot,
                                          extraMin,
                                          extraMax,
                                          ignoreCount,
                                          0);
      }
      else if (eventType.equals("bind"))
      {
         return new BindEventActionPoint(state,
                                         fromScopeType,
                                         fromScopeId,
                                         fromNamePattern,
                                         0,
                                         0,
                                         null,
                                         actionNot,
                                         actionType,
                                         fileLineIncluded,
                                         newState,
                                         ignoreCount,
                                         0);
      }
      else if (eventType.equals("alloc"))
      {
         return new AllocEventActionPoint(state,
                                          fromScopeType,
                                          fromScopeId,
                                          fromNamePattern,
                                          0,
                                          0,
                                          null,
                                          actionNot,
                                          actionType,
                                          fileLineIncluded,
                                          newState,
                                          ignoreCount,
                                          0);
      }
      else if (eventType.equals("free"))
      {
         return new FreeEventActionPoint(state,
                                         fromScopeType,
                                         fromScopeId,
                                         fromNamePattern,
                                         0,
                                         0,
                                         null,
                                         actionNot,
                                         actionType,
                                         fileLineIncluded,
                                         newState,
                                         ignoreCount,
                                         0,
                                         useSignalFilter,
                                         filterDataOffset,
                                         filterDataSize,
                                         filterDataRangeNot,
                                         filterDataMin,
                                         filterDataMax);
      }
      else if (eventType.equals("timeout"))
      {
         return new TimeoutEventActionPoint(state,
                                            fromScopeType,
                                            fromScopeId,
                                            fromNamePattern,
                                            0,
                                            0,
                                            null,
                                            actionNot,
                                            actionType,
                                            fileLineIncluded,
                                            newState,
                                            ignoreCount,
                                            0);
      }
      else if (eventType.equals("user"))
      {
         return new UserEventActionPoint(state,
                                         fromScopeType,
                                         fromScopeId,
                                         fromNamePattern,
                                         0,
                                         0,
                                         null,
                                         actionNot,
                                         actionType,
                                         fileLineIncluded,
                                         dataIncluded,
                                         newState,
                                         numberRangeNot,
                                         numberMin,
                                         numberMax,
                                         ignoreCount,
                                         0,
                                         useSignalFilter,
                                         filterDataOffset,
                                         filterDataSize,
                                         filterDataRangeNot,
                                         filterDataMin,
                                         filterDataMax);
      }
      else
      {
         throw new SAXException(
               "Invalid event type attribute value '" + eventType + "'");
      }
   }

   private static class EventActionXMLErrorHandler implements ErrorHandler
   {
      private static final int ERROR_COUNT_LIMIT = 10;

      private int errorCount = 0;

      private StringBuffer errors = new StringBuffer();

      public void warning(SAXParseException e) throws SAXException
      {
         // Do nothing.
      }

      public void error(SAXParseException e) throws SAXException
      {
         if (errorCount < ERROR_COUNT_LIMIT)
         {
            String systemId;
            String message;

            systemId = e.getSystemId();
            if (systemId == null)
            {
               systemId = "null";
            }
            message = "Error: URI=" + systemId + " Line=" + e.getLineNumber() +
                      ": " + e.getMessage() + "\n";
            errors.append(message);
            errorCount++;
         }
      }

      public void fatalError(SAXParseException e) throws SAXException
      {
         throw e;
      }

      public String getErrors()
      {
         return ((errors.length() > 0) ? errors.toString() : null);
      }
   }

   private static class EventActionXMLEntityResolver implements EntityResolver
   {
      public InputSource resolveEntity(String publicId, String systemId)
         throws SAXException, IOException
      {
         if (systemId.endsWith("eventaction.dtd"))
         {
            URL url = EventPlugin.getDefault().getBundle().getEntry("eventaction.dtd");
            return new InputSource(new BufferedInputStream(url.openStream()));
         }
         else
         {
            return null;
         }
      }
   }
}
