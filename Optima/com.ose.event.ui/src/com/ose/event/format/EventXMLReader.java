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
import java.net.URL;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import com.ose.event.ui.EventPlugin;
import com.ose.system.AllocEvent;
import com.ose.system.BindEvent;
import com.ose.system.CreateEvent;
import com.ose.system.ErrorEvent;
import com.ose.system.FreeEvent;
import com.ose.system.KillEvent;
import com.ose.system.LossEvent;
import com.ose.system.ReceiveEvent;
import com.ose.system.ResetEvent;
import com.ose.system.SendEvent;
import com.ose.system.SwapEvent;
import com.ose.system.TargetEvent;
import com.ose.system.TimeoutEvent;
import com.ose.system.TargetEvent.ProcessInfo;
import com.ose.system.UserEvent;

/**
 * This class is used for reading event XML files.
 */
public class EventXMLReader
{
   private static final byte[] EMPTY_BINARY_DATA = new byte[0];

   private final EventReaderClient client;

   /**
    * Create a new event XML reader object.
    *
    * @param client  the event reader client.
    */
   public EventXMLReader(EventReaderClient client)
   {
      if (client == null)
      {
         throw new IllegalArgumentException();
      }
      this.client = client;
   }

   /**
    * Read the events from the specified event XML file. The events read will
    * be reported to the event reader client specified when this event XML
    * reader was created.
    *
    * @param file  the event XML file to read.
    * @throws IOException  if an I/O exception occurred.
    * @throws SAXException  if an XML parsing exception occurred.
    * @throws ParserConfigurationException  if an XML parser configuration
    * exception occurred.
    */
   public void read(File file) throws IOException,
                                      SAXException,
                                      ParserConfigurationException
   {
      SAXParserFactory parserFactory;
      SAXParser parser;
      EventXMLHandler handler;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      parserFactory = SAXParserFactory.newInstance();
      parserFactory.setNamespaceAware(true);
      parserFactory.setValidating(true);
      parser = parserFactory.newSAXParser();
      handler = new EventXMLHandler(client);
      parser.parse(file, handler);
   }

   private static class EventXMLHandler extends DefaultHandler
   {
      private final EventReaderClient client;
      private final EventParams params;
      private final HashMap processMap;

      private int tickLength;
      private StringBuffer textBuffer;
      private StringBuffer structBuffer;
      private int structDepth;

      EventXMLHandler(EventReaderClient client)
      {
         this.client = client;
         params = new EventParams();
         processMap = new HashMap();
      }

      public InputSource resolveEntity(String publicId, String systemId)
         throws SAXException
      {
         if (systemId.endsWith(EventXMLTags.DTD_PATH))
         {
            URL url = EventPlugin.getDefault().getBundle().getEntry(
                  EventXMLTags.DTD_PATH);
            try
            {
               return new InputSource(new BufferedInputStream(url.openStream()));
            }
            catch (IOException e)
            {
               throw new SAXException(e);
            }
         }
         else
         {
            return null;
         }
      }

      public void startElement(String namespaceURI,
                               String localName,
                               String qName,
                               Attributes attrs)
         throws SAXException
      {
         if (localName.equals(EventXMLTags.TAG_EVENTS))
         {
            String target = "None";
            String byteOrder = "bigEndian";
            int osType = 0;
            int numExecutionUnits = 0;
            int microTickFrequency = 0;
            String scope = "None";
            String eventActions = "None";

            for (int i = 0; i < attrs.getLength(); i++)
            {
               String attrName = attrs.getLocalName(i);

               if (attrName.equals(EventXMLTags.ATTR_EVENTS_TARGET))
               {
                  target = attrs.getValue(i);
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_BYTE_ORDER))
               {
                  byteOrder = attrs.getValue(i);
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_OS_TYPE))
               {
                  osType = parseU32(attrs.getValue(i));
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_NUM_EXECUTION_UNITS))
               {
                  numExecutionUnits = parseU32(attrs.getValue(i));
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_TICK_LENGTH))
               {
                  tickLength = parseU32(attrs.getValue(i));
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_NTICK_FREQUENCY))
               {
                  microTickFrequency = parseU32(attrs.getValue(i));
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_SCOPE))
               {
                  scope = attrs.getValue(i);
               }
               else if (attrName.equals(EventXMLTags.ATTR_EVENTS_EVENT_ACTIONS))
               {
                  eventActions = attrs.getValue(i);
               }
            }

            client.commonAttributesRead(target,
                                        byteOrder.equals("bigEndian"),
                                        osType,
                                        numExecutionUnits,
                                        tickLength,
                                        microTickFrequency,
                                        scope,
                                        eventActions);
         }
         else if (localName.equals(EventXMLTags.TAG_EVENT))
         {
            params.setDefaults();
            for (int i = 0; i < attrs.getLength(); i++)
            {
               if (attrs.getLocalName(i).equals(EventXMLTags.ATTR_EVENT_TYPE))
               {
                  params.type = attrs.getValue(i);
               }
            }
         }
         else
         {
            textBuffer = new StringBuffer();

            if (localName.equals(EventXMLTags.TAG_STRUCT))
            {
               if (structDepth == 0)
               {
                  structBuffer = new StringBuffer();
                  structDepth = 0;
               }

               for (int i = 0; i < attrs.getLength(); i++)
               {
                  if (attrs.getLocalName(i).equals(EventXMLTags.ATTR_STRUCT_NAME))
                  {
                     indentBuffer(structBuffer, structDepth);
                     structBuffer.append(attrs.getValue(i));
                     structBuffer.append("\n");
                     indentBuffer(structBuffer, structDepth);
                     structBuffer.append("{\n");
                  }
               }

               structDepth++;
            }
            else if (localName.equals(EventXMLTags.TAG_MEMBER))
            {
               for (int i = 0; i < attrs.getLength(); i++)
               {
                  if (attrs.getLocalName(i).equals(EventXMLTags.ATTR_MEMBER_NAME))
                  {
                     indentBuffer(structBuffer, structDepth);
                     structBuffer.append(attrs.getValue(i));
                     structBuffer.append(" = ");
                  }
               }
            }
         }
      }

      public void endElement(String namespaceURI,
                             String localName,
                             String qName)
         throws SAXException
      {
         if (localName.equals(EventXMLTags.TAG_EVENTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(EventXMLTags.TAG_EVENT))
         {
            TargetEvent event = createEvent(params);
            if (event != null)
            {
               client.eventRead(event);
            }
         }
         else if (localName.equals(EventXMLTags.TAG_REFERENCE))
         {
            params.reference = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_TICK))
         {
            params.tick = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_MICRO_TICK))
         {
            params.microTick = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_SECONDS))
         {
            params.seconds = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_SECONDS_TICK))
         {
            params.secondsTick = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_FILE))
         {
            params.file = textBuffer.toString();
         }
         else if (localName.equals(EventXMLTags.TAG_LINE))
         {
            params.line = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_FROM))
         {
            String value = textBuffer.toString();
            params.from = parseProcessId(value);
            params.fromName = parseProcessName(value);
         }
         else if (localName.equals(EventXMLTags.TAG_TO))
         {
            String value = textBuffer.toString();
            params.to = parseProcessId(value);
            params.toName = parseProcessName(value);
         }
         else if (localName.equals(EventXMLTags.TAG_FROM_EXECUTION_UNIT))
         {
            params.fromExecUnit = parseU16(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_TO_EXECUTION_UNIT))
         {
            params.toExecUnit = parseU16(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_NUMBER))
         {
            params.number = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_ADDRESS))
         {
            params.address = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_SIZE))
         {
            params.size = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_ACTUAL_SIZE))
         {
            params.actualSize = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_DATA))
         {
            // Nothing to do.
         }
         else if (localName.equals(EventXMLTags.TAG_BINARY))
         {
            params.binaryData = parseBinaryData(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_STRUCT))
         {
            structDepth--;
            indentBuffer(structBuffer, structDepth);
            structBuffer.append("}\n");
            if (structDepth == 0)
            {
               params.formattedData = structBuffer.toString();
            }
         }
         else if (localName.equals(EventXMLTags.TAG_MEMBER))
         {
            structBuffer.append(textBuffer.toString().trim());
            structBuffer.append("\n");
         }
         else if (localName.equals(EventXMLTags.TAG_TIMEOUT))
         {
            params.timeout = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_TIMEOUT_SOURCE))
         {
            params.timeoutSource = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_KERNEL_CALLED))
         {
            params.kernelCalled = parseBoolean(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_ERROR))
         {
            params.error = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_EXTRA))
         {
            params.extra = parseU32(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_WARM_RESET))
         {
            params.warmReset = parseBoolean(textBuffer.toString());
         }
         else if (localName.equals(EventXMLTags.TAG_LOST_SIZE))
         {
            params.lostSize = parseU32(textBuffer.toString());
         }
         else
         {
            // Ignore unknown tags.
         }
      }

      public void characters(char[] ch, int start, int length)
         throws SAXException
      {
         if (textBuffer != null)
         {
            textBuffer.append(ch, start, length);
         }
      }

      public void error(SAXParseException e) throws SAXException
      {
         throw e;
      }

      private TargetEvent createEvent(EventParams params)
      {
         if (params.type.equals("send"))
         {
            ProcessInfo from;
            ProcessInfo to;

            from = createProcess(params.target, params.from, params.fromName);
            to = createProcess(params.target, params.to, params.toName);

            return SendEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         params.number,
                                         from,
                                         to,
                                         params.size,
                                         params.address,
                                         0,
                                         params.line,
                                         params.fromExecUnit,
                                         params.file,
                                         params.binaryData,
                                         params.formattedData);
         }
         else if (params.type.equals("receive"))
         {
            ProcessInfo from;
            ProcessInfo to;

            from = createProcess(params.target, params.from, params.fromName);
            to = createProcess(params.target, params.to, params.toName);

            return ReceiveEvent.getInstance(params.target,
                                            tickLength,
                                            params.reference,
                                            params.tick,
                                            params.microTick,
                                            params.seconds,
                                            params.secondsTick,
                                            params.number,
                                            from,
                                            to,
                                            params.size,
                                            params.address,
                                            0,
                                            params.line,
                                            params.fromExecUnit,
                                            params.file,
                                            params.binaryData,
                                            params.formattedData);
         }
         else if (params.type.equals("swap"))
         {
            ProcessInfo from;
            ProcessInfo to;

            from = createProcess(params.target, params.from, params.fromName);
            to = createProcess(params.target, params.to, params.toName);

            return SwapEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         from,
                                         to,
                                         params.line,
                                         params.fromExecUnit,
                                         params.file);
         }
         else if (params.type.equals("create"))
         {
            ProcessInfo from;
            ProcessInfo to;

            from = ((params.from != 0) ?
                    createProcess(params.target, params.from, params.fromName) :
                    null);
            to = createProcess(params.target, params.to, params.toName);

            return CreateEvent.getInstance(params.target,
                                           tickLength,
                                           params.reference,
                                           params.tick,
                                           params.microTick,
                                           params.seconds,
                                           params.secondsTick,
                                           from,
                                           to,
                                           params.line,
                                           params.fromExecUnit,
                                           params.file);
         }
         else if (params.type.equals("kill"))
         {
            ProcessInfo from;
            ProcessInfo to;

            from = ((params.from != 0) ?
                    createProcess(params.target, params.from, params.fromName) :
                    null);
            to = createProcess(params.target, params.to, params.toName);

            return KillEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         from,
                                         to,
                                         params.line,
                                         params.fromExecUnit,
                                         params.file);
         }
         else if (params.type.equals("error"))
         {
            ProcessInfo from;

            from = createProcess(params.target, params.from, params.fromName);

            return ErrorEvent.getInstance(params.target,
                                          tickLength,
                                          params.reference,
                                          params.tick,
                                          params.microTick,
                                          params.seconds,
                                          params.secondsTick,
                                          from,
                                          params.kernelCalled,
                                          params.error,
                                          params.extra,
                                          params.line,
                                          params.fromExecUnit,
                                          params.file);
         }
         else if (params.type.equals("bind"))
         {
            ProcessInfo from;

            from = createProcess(params.target, params.from, params.fromName);

            return BindEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         from,
                                         params.fromExecUnit,
                                         params.toExecUnit,
                                         params.line,
                                         params.file);
         }
         else if (params.type.equals("alloc"))
         {
            ProcessInfo from;

            from = createProcess(params.target, params.from, params.fromName);

            return AllocEvent.getInstance(params.target,
                                          tickLength,
                                          params.reference,
                                          params.tick,
                                          params.microTick,
                                          params.seconds,
                                          params.secondsTick,
                                          from,
                                          params.number,
                                          params.size,
                                          params.address,
                                          0,
                                          params.line,
                                          params.fromExecUnit,
                                          params.file);
         }
         else if (params.type.equals("free"))
         {
            ProcessInfo from;

            from = createProcess(params.target, params.from, params.fromName);

            return FreeEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         from,
                                         params.number,
                                         params.size,
                                         params.address,
                                         0,
                                         params.line,
                                         params.fromExecUnit,
                                         params.file);
         }
         else if (params.type.equals("timeout"))
         {
            ProcessInfo from;

            from = createProcess(params.target, params.from, params.fromName);

            return TimeoutEvent.getInstance(params.target,
                                            tickLength,
                                            params.reference,
                                            params.tick,
                                            params.microTick,
                                            params.seconds,
                                            params.secondsTick,
                                            params.timeout,
                                            params.timeoutSource,
                                            from,
                                            params.line,
                                            params.fromExecUnit,
                                            params.file);
         }
         else if (params.type.equals("user"))
         {
            ProcessInfo from;

            from = createProcess(params.target, params.from, params.fromName);

            return UserEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         from,
                                         params.number,
                                         params.size,
                                         params.line,
                                         params.fromExecUnit,
                                         params.file,
                                         params.binaryData,
                                         params.formattedData);
         }
         else if (params.type.equals("reset"))
         {
            return ResetEvent.getInstance(params.target,
                                          tickLength,
                                          params.reference,
                                          params.tick,
                                          params.microTick,
                                          params.seconds,
                                          params.secondsTick,
                                          params.warmReset,
                                          params.line,
                                          params.file);
         }
         else if (params.type.equals("loss"))
         {
            return LossEvent.getInstance(params.target,
                                         tickLength,
                                         params.reference,
                                         params.tick,
                                         params.microTick,
                                         params.seconds,
                                         params.secondsTick,
                                         params.lostSize);
         }
         else
         {
            return null;
         }
      }

      private ProcessInfo createProcess(Object target, int id, String name)
      {
         Integer idKey;
         ProcessInfo process;

         // Look for process info in hashmap of previously created process infos.
         idKey = new Integer(id);
         process = (ProcessInfo) processMap.get(idKey);
         if (process == null)
         {
            process = ProcessInfo.getInstance(
                  target, id, 0, 0, ProcessInfo.TYPE_UNKNOWN, 0, 0, name);
            processMap.put(idKey, process);
         }

         return process;
      }

      private static void indentBuffer(StringBuffer buffer, int depth)
      {
         for (int i = 0; i < depth; i++)
         {
            buffer.append("   ");
         }
      }

      private static short parseU16(String s) throws SAXException
      {
         int value;

         if (s == null)
         {
            throw new IllegalArgumentException();
         }

         try
         {
            value = Integer.decode(s.trim()).intValue();
         }
         catch (NumberFormatException e)
         {
            throw new SAXException(
               "Invalid element or attribute 16-bit integer value '" + s + "'");
         }
         if ((value < 0) || (value > 0xFFFF))
         {
            throw new SAXException(
               "Invalid element or attribute 16-bit integer value '" + s + "'");
         }
         return (short) (0xFFFF & value);
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
               "Invalid element or attribute 32-bit integer value '" + s + "'");
         }
         if ((value < 0L) || (value > 0xFFFFFFFFL))
         {
            throw new SAXException(
               "Invalid element or attribute 32-bit integer value '" + s + "'");
         }
         return (int) (0xFFFFFFFFL & value);
      }

      private static boolean parseBoolean(String s)
      {
         if (s == null)
         {
            throw new IllegalArgumentException();
         }
         return s.equalsIgnoreCase("true");
      }

      // Input string should be of the form "xx xx xx ...",
      // where x is a hexadecimal digit.
      private static byte[] parseBinaryData(String s) throws SAXException
      {
         String text = s.trim();
         int count = text.length() + 1;
         byte[] binaryData;

         if (count == 1)
         {
            return EMPTY_BINARY_DATA;
         }

         if ((count % 3) != 0)
         {
            throw new SAXException("Invalid data for element <binary>: " + s);
         }

         binaryData = new byte[count / 3];

         for (int i = 0; i < count; i += 3)
         {
            int hiInt = Character.digit(s.charAt(i), 16);
            int loInt = Character.digit(s.charAt(i + 1), 16);

            if ((hiInt == -1) || (loInt == -1))
            {
               throw new SAXException("Invalid data for element <binary>: " + s);
            }

            binaryData[i / 3] = (byte) (((hiInt << 4) + loInt) & 0xFF);
         }

         return binaryData;
      }

      // Input string should be of the form "<name> (0x<id>)".
      private static int parseProcessId(String s) throws SAXException
      {
         int length = s.length();
         int i = s.lastIndexOf(" (0x");
         int j = s.lastIndexOf(")");

         if ((i > 0) && (i + 4 < length) && (j > i + 4) && (j < length))
         {
            return parseU32(s.substring(i + 2, j));
         }
         else
         {
            throw new SAXException("Invalid process name/id '" + s + "'");
         }
      }

      // Input string should be of the form "<name> (0x<id>)".
      private static String parseProcessName(String s) throws SAXException
      {
         int i = s.lastIndexOf(" (0x");

         if (i > 0)
         {
            return s.substring(0, i).trim();
         }
         else
         {
            throw new SAXException("Invalid process name/id '" + s + "'");
         }
      }

      private static class EventParams
      {
         private static final Object DEFAULT_TARGET = new Object();

         public Object target;
         public String type;
         public int reference;
         public int tick;
         public int microTick;
         public int seconds;
         public int secondsTick;
         public String file;
         public int line;
         public int from;
         public String fromName;
         public int to;
         public String toName;
         public short fromExecUnit;
         public short toExecUnit;
         public int number;
         public int address;
         public int size;
         public int actualSize;
         public byte[] binaryData;
         public String formattedData;
         public int timeout;
         public int timeoutSource;
         public boolean kernelCalled;
         public int error;
         public int extra;
         public boolean warmReset;
         public int lostSize;

         EventParams()
         {
            setDefaults();
         }

         public void setDefaults()
         {
            target = DEFAULT_TARGET;
            type = "unknown";
            reference = 0;
            tick = 0;
            microTick = 0;
            seconds = 0;
            secondsTick = 0;
            file = "";
            line = 0;
            from = 0;
            fromName = "";
            to = 0;
            toName = "";
            fromExecUnit = 0;
            toExecUnit = 0;
            number = 0;
            address = 0;
            size = 0;
            actualSize = 0;
            binaryData = EMPTY_BINARY_DATA;
            formattedData = null;
            timeout = 0;
            timeoutSource = 0;
            kernelCalled = true;
            error = 0;
            extra = 0;
            warmReset = false;
            lostSize = 0;
         }
      }
   }
}
