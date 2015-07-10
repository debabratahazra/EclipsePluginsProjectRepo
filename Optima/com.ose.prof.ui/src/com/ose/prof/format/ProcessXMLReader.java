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

package com.ose.prof.format;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
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
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.CPUProcessReportPoint;

/**
 * This class is used for reading profiled process settings XML files.
 */
public class ProcessXMLReader
{
   /**
    * Read the process report points from the specified profiled process
    * settings XML file.
    *
    * @param file  the profiled process settings XML file to read.
    * @return the process report points (CPUProcessReportPoint) from the
    * specified profiled process settings XML file.
    * @throws IOException  if an I/O exception occurred.
    * @throws ParserConfigurationException  if an XML parser configuration
    * exception occurred.
    * @throws SAXException  if an XML parsing exception occurred.
    */
   public List read(File file)
      throws IOException, ParserConfigurationException, SAXException
   {
      List processes;
      DocumentBuilderFactory factory;
      ProcessXMLErrorHandler errorHandler;
      DocumentBuilder builder;
      Document document;
      String errors;
      Element root;
      NodeList elements;
      String type;
      String identifier;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      processes = new ArrayList();
      factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setIgnoringElementContentWhitespace(true);
      factory.setIgnoringComments(true);
      errorHandler = new ProcessXMLErrorHandler();
      builder = factory.newDocumentBuilder();
      builder.setErrorHandler(errorHandler);
      builder.setEntityResolver(new ProcessXMLEntityResolver());
      document = builder.parse(file);
      errors = errorHandler.getErrors();
      if (errors != null)
      {
         throw new SAXException(errors);
      }
      root = document.getDocumentElement();
      elements = root.getChildNodes();

      for (int i = 0; i < elements.getLength(); i++)
      {
         Element processElement;

         processElement = (Element) elements.item(i);
         if (!processElement.hasChildNodes())
         {
            throw new SAXException("Element 'process' lacks data");
         }
         type = processElement.getAttribute("type");
         identifier = processElement.getFirstChild().getNodeValue();
         processes.add(createCPUProcessReportPoint(type, identifier));
      }

      return processes;
   }

   private static CPUProcessReportPoint createCPUProcessReportPoint(
         String type,
         String identifier)
      throws SAXException
   {
      if (type.equals("id"))
      {
         return new CPUProcessReportPoint(parseU32(identifier));
      }
      else if (type.equals("namePattern"))
      {
         return new CPUProcessReportPoint(identifier);
      }
      else
      {
         throw new SAXException(
               "Invalid process type attribute value '" + type + "'");
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

   private static class ProcessXMLErrorHandler implements ErrorHandler
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

   private static class ProcessXMLEntityResolver implements EntityResolver
   {
      public InputSource resolveEntity(String publicId, String systemId)
         throws SAXException, IOException
      {
         if (systemId.endsWith("process.dtd"))
         {
            URL url = ProfilerPlugin.getDefault().getBundle().getEntry("process.dtd");
            return new InputSource(new BufferedInputStream(url.openStream()));
         }
         else
         {
            return null;
         }
      }
   }
}
