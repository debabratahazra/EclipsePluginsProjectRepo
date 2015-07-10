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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DomHelper
{

   public static Document parse(File file, EntityResolver entityResolver)
         throws ParserConfigurationException, SAXException, IOException
   {
      DocumentBuilderFactory factory;
      XMLErrorHandler errorHandler;
      DocumentBuilder builder;
      String errors;
      Document document = null;

      factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setIgnoringElementContentWhitespace(true);
      errorHandler = new XMLErrorHandler();

      builder = factory.newDocumentBuilder();
      builder.setErrorHandler(errorHandler);
      builder.setEntityResolver(entityResolver);
      document = builder.parse(file);

      errors = errorHandler.getErrors();

      if (errors != null)
      {
         throw new SAXException(errors);
      }

      return document;
   }

   public static Document createNewDocument(EntityResolver entityResolver)
         throws ParserConfigurationException
   {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setIgnoringElementContentWhitespace(true);
      XMLErrorHandler errorHandler = new XMLErrorHandler();
      Document document = null;

      DocumentBuilder builder;
      builder = factory.newDocumentBuilder();
      builder.setErrorHandler(errorHandler);
      builder.setEntityResolver(entityResolver);

      document = builder.newDocument();
      return document;
   }

   public static Result transform(Document document, OutputStream outputStream,
         String dtd) throws TransformerException
   {
      String version = System.getProperty("java.version");
      Source source = new DOMSource(document);
      Result result = new StreamResult(outputStream);

      TransformerFactory factory;
      Transformer transformer;
      factory = TransformerFactory.newInstance();

      if (version.startsWith("1.4"))
      {
         transformer = factory.newTransformer();
         transformer.setOutputProperty(
               "{http://xml.apache.org/xalan}indent-amount", "4");
      }
      else
      {
         try
         {
            factory.setAttribute("indent-number", new Integer(4));
            
            // This is a workaround for bug ID:6519088.
            // Transformer resets the "indent" info if the "result" is a
            // StreamResult with
            // an OutputStream.
            // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6519088
            result = new StreamResult(new BufferedWriter(
                  new OutputStreamWriter(outputStream, "ISO-8859-1")));
         }
         catch (Exception e)
         {
            throw new TransformerException(e);
         }

         transformer = factory.newTransformer();
      }

      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

      if (dtd != null)
      {
         transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtd);
      }
      else
      {
         DocumentType docType = document.getDoctype();
         transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, docType
               .getSystemId());
      }

      transformer.transform(source, result);

      return result;
   }

   public static Result transform(Document document, OutputStream outputStream)
         throws TransformerException
   {
      return transform(document, outputStream, null);
   }

   private static class XMLErrorHandler implements ErrorHandler
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
            message = "Error: URI=" + systemId + " Line=" + e.getLineNumber()
                  + ": " + e.getMessage() + "\n";
            errors.append(message);
            errorCount++;
         }
      }

      public void fatalError(SAXParseException e) throws SAXException
      {
         String systemId;
         String message;

         systemId = e.getSystemId();
         if (systemId == null)
         {
            systemId = "null";
         }
         message = "Error: URI=" + systemId + " Line=" + e.getLineNumber()
               + ": " + e.getMessage() + "\n";
         errors.append(message);
         errorCount++;
         throw (e);
      }

      public String getErrors()
      {
         return ((errors.length() > 0) ? errors.toString() : null);
      }
   }
}
