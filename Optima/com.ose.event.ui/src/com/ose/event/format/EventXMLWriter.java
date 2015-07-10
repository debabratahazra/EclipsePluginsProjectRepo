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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import com.ose.event.ui.EventPlugin;
import com.ose.event.ui.SymbolManager;
import com.ose.sigdb.Attribute;
import com.ose.sigdb.CompositeAttribute;
import com.ose.sigdb.SignalAttribute;
import com.ose.sigdb.TruncatedAttribute;
import com.ose.sigdb.util.EndianConstants;
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
 * This class is used for writing event XML files.
 */
public class EventXMLWriter
{
   private static final String NEW_LINE;

   private final Object lock;

   private final PrintStream out;

   private final boolean bigEndian;

   private final boolean trace;

   private final SymbolManager symbolManager;

   private boolean open;

   private int referenceCount;

   static
   {
      NEW_LINE = System.getProperty("line.separator", "\n");
   }

   /**
    * Create a new event XML writer object.
    *
    * @param file  the event XML file to write.
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param scope  the event scope string.
    * @param eventActions  the name of the event action settings file.
    * @param trace  true if event tracing, false if event notification.
    * @throws IOException  if an I/O exception occurred.
    */
   public EventXMLWriter(File file,
                         String target,
                         boolean bigEndian,
                         int osType,
                         int numExecutionUnits,
                         int tickLength,
                         int microTickFrequency,
                         String scope,
                         String eventActions,
                         boolean trace)
      throws IOException
   {
      if ((file == null) || (target == null) ||
          (scope == null) || (eventActions == null))
      {
         throw new IllegalArgumentException();
      }

      lock = new Object();
      out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
      this.bigEndian = bigEndian;
      this.trace = trace;
      symbolManager = EventPlugin.getSymbolManager();
      open = true;
      referenceCount = 0;

      writeXMLDeclaration("ISO-8859-1");

      writeStylesheet(EventXMLTags.STYLESHEET_TYPE, EventXMLTags.STYLESHEET_PATH);

      writeDoctype(EventXMLTags.TAG_EVENTS, EventXMLTags.DTD_PATH);

      writeLine("<" + EventXMLTags.TAG_EVENTS + " "
                + EventXMLTags.ATTR_EVENTS_TARGET + "=\""
                + target + "\" "
                + EventXMLTags.ATTR_EVENTS_BYTE_ORDER + "=\""
                + (bigEndian ? "bigEndian" : "littleEndian") + "\" "
                + EventXMLTags.ATTR_EVENTS_OS_TYPE + "=\""
                + toU32String(osType) + "\" "
                + EventXMLTags.ATTR_EVENTS_NUM_EXECUTION_UNITS + "=\""
                + toU32String(numExecutionUnits) + "\" "
                + EventXMLTags.ATTR_EVENTS_TICK_LENGTH + "=\""
                + toU32String(tickLength) + "\" "
                + EventXMLTags.ATTR_EVENTS_NTICK_FREQUENCY + "=\""
                + toU32String(microTickFrequency) + "\" "
                + EventXMLTags.ATTR_EVENTS_SCOPE + "=\""
                + scope + "\" "
                + EventXMLTags.ATTR_EVENTS_EVENT_ACTIONS + "=\""
                + eventActions + "\">");
   }

   /**
    * Write an event.
    *
    * @param event  the event to write.
    */
   public void write(TargetEvent event)
   {
      synchronized (lock)
      {
         checkState();
   
         if (event instanceof SendEvent)
         {
            writeSendEvent((SendEvent) event);
         }
         else if (event instanceof ReceiveEvent)
         {
            writeReceiveEvent((ReceiveEvent) event);
         }
         else if (event instanceof SwapEvent)
         {
            writeSwapEvent((SwapEvent) event);
         }
         else if (event instanceof CreateEvent)
         {
            writeCreateEvent((CreateEvent) event);
         }
         else if (event instanceof KillEvent)
         {
            writeKillEvent((KillEvent) event);
         }
         else if (event instanceof ErrorEvent)
         {
            writeErrorEvent((ErrorEvent) event);
         }
         else if (event instanceof BindEvent)
         {
            writeBindEvent((BindEvent) event);
         }
         else if (event instanceof AllocEvent)
         {
            writeAllocEvent((AllocEvent) event);
         }
         else if (event instanceof FreeEvent)
         {
            writeFreeEvent((FreeEvent) event);
         }
         else if (event instanceof TimeoutEvent)
         {
            writeTimeoutEvent((TimeoutEvent) event);
         }
         else if (event instanceof UserEvent)
         {
            writeUserEvent((UserEvent) event);
         }
         else if (event instanceof ResetEvent)
         {
            writeResetEvent((ResetEvent) event);
         }
         else if (event instanceof LossEvent)
         {
            writeLossEvent((LossEvent) event);
         }
         else if (event == null)
         {
            throw new IllegalArgumentException();
         }
         else
         {
            // Ignore unknown event types.
         }
      }
   }

   /**
    * Close this event XML writer and release any resources associated with it.
    */
   public void close()
   {
      synchronized (lock)
      {
         if (open)
         {
            writeLine("</", EventXMLTags.TAG_EVENTS, ">");
            out.close();
            open = false;
         }
      }
   }

   private void checkState() throws IllegalStateException
   {
      if (!open)
      {
         throw new IllegalStateException("EventXMLWriter is closed");
      }
   }

   private void writeXMLDeclaration(String encoding)
   {
      writeLine("<?xml version=\"1.0\" encoding=\"", encoding, "\"?>");
   }

   private void writeStylesheet(String type, String stylesheet)
   {
      writeLine("<?xml-stylesheet type=\"", type, "\" href=\"", stylesheet, "\"?>");
   }

   private void writeDoctype(String rootElement, String dtd)
   {
      writeLine("<!DOCTYPE ", rootElement, " SYSTEM \"", dtd, "\">");
      writeLine();
   }

   private void writeCommonTags(int indentLevel, TargetEvent event)
   {
      String reference = (trace ? toU32String(event.getReference())
            : toU32String(referenceCount++));
      String tick = toU32String(event.getTick());
      String nTick = toU32String(event.getNTick());
      String seconds = toU32String(event.getSeconds());
      String secondsTick = toU32String(event.getSecondsTick());
      int lineNumber = event.getLineNumber();

      writeTag(indentLevel, EventXMLTags.TAG_REFERENCE, reference);
      writeTag(indentLevel, EventXMLTags.TAG_TICK, tick);
      writeTag(indentLevel, EventXMLTags.TAG_MICRO_TICK, nTick);
      writeTag(indentLevel, EventXMLTags.TAG_SECONDS, seconds);
      writeTag(indentLevel, EventXMLTags.TAG_SECONDS_TICK, secondsTick);
      if (lineNumber != 0)
      {
         writeCDataTag(indentLevel, EventXMLTags.TAG_FILE, event.getFileName(), false);
         writeTag(indentLevel, EventXMLTags.TAG_LINE, toU32String(lineNumber));
      }
   }

   private void writeSendEvent(SendEvent event)
   {
      String sender = event.getSenderProcess().toString();
      String addressee = event.getAddresseeProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String signalNumber = toU32HexString(event.getSignalNumber());
      String signalAddress = toU32HexString(event.getSignalAddress());
      String signalSize = toU32String(event.getSignalSize());
      byte[] signalData = event.getSignalData();
      String actualSize = toU32String(signalData.length);
      int byteOrder = (bigEndian ?
         EndianConstants.BIG_ENDIAN : EndianConstants.LITTLE_ENDIAN);
      CompositeAttribute signal;

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "send");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, sender);
      writeCDataTag(2, EventXMLTags.TAG_TO, addressee);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_NUMBER, signalNumber);
      writeTag(2, EventXMLTags.TAG_ADDRESS, signalAddress);
      writeTag(2, EventXMLTags.TAG_SIZE, signalSize);
      writeTag(2, EventXMLTags.TAG_ACTUAL_SIZE, actualSize);
      signal = symbolManager.getSignal(event.getSignalNumber(), signalData, byteOrder);
      if ((signalData.length > 0) || (signal != null))
      {
         writeStartTag(2, EventXMLTags.TAG_DATA);
         if (signal != null)
         {
            writeInterpretedData(3, signal);
         }
         writeBinaryData(3, signalData);
         writeEndTag(2, EventXMLTags.TAG_DATA);
      }
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeReceiveEvent(ReceiveEvent event)
   {
      String sender = event.getSenderProcess().toString();
      String addressee = event.getAddresseeProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String signalNumber = toU32HexString(event.getSignalNumber());
      String signalAddress = toU32HexString(event.getSignalAddress());
      String signalSize = toU32String(event.getSignalSize());
      byte[] signalData = event.getSignalData();
      String actualSize = toU32String(signalData.length);
      int byteOrder = (bigEndian ?
         EndianConstants.BIG_ENDIAN : EndianConstants.LITTLE_ENDIAN);
      CompositeAttribute signal;

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "receive");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, sender);
      writeCDataTag(2, EventXMLTags.TAG_TO, addressee);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_NUMBER, signalNumber);
      writeTag(2, EventXMLTags.TAG_ADDRESS, signalAddress);
      writeTag(2, EventXMLTags.TAG_SIZE, signalSize);
      writeTag(2, EventXMLTags.TAG_ACTUAL_SIZE, actualSize);
      signal = symbolManager.getSignal(event.getSignalNumber(), signalData, byteOrder);
      if ((signalData.length > 0) || (signal != null))
      {
         writeStartTag(2, EventXMLTags.TAG_DATA);
         if (signal != null)
         {
            writeInterpretedData(3, signal);
         }
         writeBinaryData(3, signalData);
         writeEndTag(2, EventXMLTags.TAG_DATA);
      }
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeSwapEvent(SwapEvent event)
   {
      String swappedOut = event.getSwappedOutProcess().toString();
      String swappedIn = event.getSwappedInProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "swap");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, swappedOut);
      writeCDataTag(2, EventXMLTags.TAG_TO, swappedIn);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeCreateEvent(CreateEvent event)
   {
      ProcessInfo creator = event.getCreatorProcess();
      String created = event.getCreatedProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "create");
      writeCommonTags(2, event);
      if (creator != null)
      {
         writeCDataTag(2, EventXMLTags.TAG_FROM, creator.toString());
      }
      writeCDataTag(2, EventXMLTags.TAG_TO, created);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeKillEvent(KillEvent event)
   {
      ProcessInfo killer = event.getKillerProcess();
      String killed = event.getKilledProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "kill");
      writeCommonTags(2, event);
      if (killer != null)
      {
         writeCDataTag(2, EventXMLTags.TAG_FROM, killer.toString());
      }
      writeCDataTag(2, EventXMLTags.TAG_TO, killed);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeErrorEvent(ErrorEvent event)
   {
      String process = event.getProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String executive = Boolean.toString(event.isExecutive());
      String error = toU32HexString(event.getError());
      String extra = toU32HexString(event.getExtra());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "error");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, process);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_KERNEL_CALLED, executive);
      writeTag(2, EventXMLTags.TAG_ERROR, error);
      writeTag(2, EventXMLTags.TAG_EXTRA, extra);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeBindEvent(BindEvent event)
   {
      String process = event.getProcess().toString();
      String fromExecutionUnit = toU16String(event.getFromExecutionUnit());
      String toExecutionUnit = toU16String(event.getToExecutionUnit());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "bind");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, process);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, fromExecutionUnit);
      writeTag(2, EventXMLTags.TAG_TO_EXECUTION_UNIT, toExecutionUnit);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeAllocEvent(AllocEvent event)
   {
      String process = event.getProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String signalNumber = toU32HexString(event.getSignalNumber());
      String signalAddress = toU32HexString(event.getSignalAddress());
      String signalSize = toU32String(event.getSignalSize());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "alloc");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, process);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_NUMBER, signalNumber);
      writeTag(2, EventXMLTags.TAG_ADDRESS, signalAddress);
      writeTag(2, EventXMLTags.TAG_SIZE, signalSize);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeFreeEvent(FreeEvent event)
   {
      String process = event.getProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String signalNumber = toU32HexString(event.getSignalNumber());
      String signalAddress = toU32HexString(event.getSignalAddress());
      String signalSize = toU32String(event.getSignalSize());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "free");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, process);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_NUMBER, signalNumber);
      writeTag(2, EventXMLTags.TAG_ADDRESS, signalAddress);
      writeTag(2, EventXMLTags.TAG_SIZE, signalSize);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeTimeoutEvent(TimeoutEvent event)
   {
      String process = event.getProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String timeout = toU32String(event.getTimeout());
      String timeoutSource = toU32String(event.getSystemCall());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "timeout");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, process);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_TIMEOUT, timeout);
      writeTag(2, EventXMLTags.TAG_TIMEOUT_SOURCE, timeoutSource);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeUserEvent(UserEvent event)
   {
      String process = event.getProcess().toString();
      String executionUnit = toU16String(event.getExecutionUnit());
      String eventNumber = toU32HexString(event.getEventNumber());
      String eventDataSize = toU32String(event.getEventDataSize());
      byte[] eventData = event.getEventData();
      String actualSize = toU32String(eventData.length);
      int byteOrder = (bigEndian ?
         EndianConstants.BIG_ENDIAN : EndianConstants.LITTLE_ENDIAN);
      CompositeAttribute struct;

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "user");
      writeCommonTags(2, event);
      writeCDataTag(2, EventXMLTags.TAG_FROM, process);
      writeTag(2, EventXMLTags.TAG_FROM_EXECUTION_UNIT, executionUnit);
      writeTag(2, EventXMLTags.TAG_NUMBER, eventNumber);
      writeTag(2, EventXMLTags.TAG_SIZE, eventDataSize);
      writeTag(2, EventXMLTags.TAG_ACTUAL_SIZE, actualSize);
      struct = symbolManager.getEvent(event.getEventNumber(), eventData, byteOrder);
      if ((eventData.length > 0) || (struct != null))
      {
         writeStartTag(2, EventXMLTags.TAG_DATA);
         if (struct != null)
         {
            writeInterpretedData(3, struct);
         }
         writeBinaryData(3, eventData);
         writeEndTag(2, EventXMLTags.TAG_DATA);
      }
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeResetEvent(ResetEvent event)
   {
      String warmReset = Boolean.toString(event.isWarmReset());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "reset");
      writeCommonTags(2, event);
      writeTag(2, EventXMLTags.TAG_WARM_RESET, warmReset);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeLossEvent(LossEvent event)
   {
      String lostSize = toU32String(event.getLostSize());

      writeStartTag(1, EventXMLTags.TAG_EVENT, EventXMLTags.ATTR_EVENT_TYPE, "loss");
      writeCommonTags(2, event);
      writeTag(2, EventXMLTags.TAG_LOST_SIZE, lostSize);
      writeEndTag(1, EventXMLTags.TAG_EVENT);
   }

   private void writeTag(int indentLevel, String tag, String text)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, ">", text, "</", tag, ">");
   }

   private void writeStartTag(int indentLevel, String tag)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, ">");
   }

   private void writeStartTag(int indentLevel,
                              String tag,
                              String attribute,
                              String value)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ", attribute, "=\"", value, "\">");
   }

   private void writeEndTag(int indentLevel, String tag)
   {
      writeIndent(indentLevel);
      writeLine("</", tag, ">");
   }

   private void writeCDataTag(int indentLevel, String tag, String text)
   {
      writeIndent(indentLevel);
      write("<", tag, ">");
      write("<![CDATA[", text, "]]>");
      writeLine("</", tag, ">");
   }

   private void writeCDataTag(int indentLevel,
                              String tag,
                              String text,
                              boolean enclosingNewLines)
   {
      if (enclosingNewLines)
      {
         writeStartTag(indentLevel, tag);
         writeLine("<![CDATA[", NEW_LINE, text, NEW_LINE, "]]>");
         writeEndTag(indentLevel, tag);
      }
      else
      {
         writeIndent(indentLevel);
         write("<", tag, ">");
         write("<![CDATA[", text, "]]>");
         writeLine("</", tag, ">");
      }
   }

   private void writeCDataTag(int indentLevel,
                              String tag,
                              String attribute,
                              String value,
                              String text,
                              boolean enclosingNewLines)
   {
      if (enclosingNewLines)
      {
         writeStartTag(indentLevel, tag, attribute, value);
         writeLine("<![CDATA[", NEW_LINE, text, NEW_LINE, "]]>");
         writeEndTag(indentLevel, tag);
      }
      else
      {
         writeIndent(indentLevel);
         write("<", tag, " ", attribute, "=\"", value, "\">");
         write("<![CDATA[", text, "]]>");
         writeLine("</", tag, ">");
      }
   }

   private void writeBinaryData(int indentLevel, byte[] data)
   {
      writeIndent(indentLevel);
      write("<", EventXMLTags.TAG_BINARY, ">");
      for (int i = 0; i < data.length; i++)
      {
         write(toU8HexString(data[i]));
         if (i < (data.length - 1))
         {
            write(" ");
         }
      }
      writeLine("</", EventXMLTags.TAG_BINARY, ">");
   }

   private void writeInterpretedData(int indentLevel, CompositeAttribute signal)
   {
      writeStartTag(indentLevel,
                    EventXMLTags.TAG_STRUCT,
                    EventXMLTags.ATTR_STRUCT_NAME,
                    signal.getName());

      for (Iterator i = signal.getAttributes().iterator(); i.hasNext();)
      {
         Attribute attribute = (Attribute) i.next();

         if (attribute instanceof CompositeAttribute)
         {
            writeInterpretedData(indentLevel + 1, (CompositeAttribute) attribute);
         }
         else if (attribute instanceof SignalAttribute)
         {
            writeCDataTag(indentLevel + 1,
                          EventXMLTags.TAG_MEMBER,
                          EventXMLTags.ATTR_MEMBER_NAME,
                          attribute.getName(),
                          attribute.getValue(),
                          true);
         }
         else if (attribute instanceof TruncatedAttribute)
         {
            // FIXME: Somehow indicate that the struct data is truncated.
         }
         else
         {
            // Ignore unknown attributes.
         }
      }

      writeEndTag(indentLevel, EventXMLTags.TAG_STRUCT);
   }

   private void write(String text)
   {
      out.print(text);
   }

   private void write(String part1, String part2, String part3)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
   }

   private void write(String part1,
                      String part2,
                      String part3,
                      String part4,
                      String part5,
                      String part6,
                      String part7)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.print(part7);
   }

   private void writeLine()
   {
      out.println();
   }

   private void writeLine(String text)
   {
      out.println(text);
   }

   private void writeLine(String part1, String part2, String part3)
   {
      out.print(part1);
      out.print(part2);
      out.println(part3);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.println(part5);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5,
                          String part6,
                          String part7)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.println(part7);
   }

   private void writeIndent(int indentLevel)
   {
      for (int i = 0; i < indentLevel; i++)
      {
         out.print("   ");
      }
   }

   private static String toU8HexString(byte b)
   {
      String s = Integer.toHexString(b & 0xFF).toUpperCase();
      if (s.length() < 2)
      {
         s = "0" + s;
      }
      return s;
   }

   private static String toU16String(short s)
   {
      return Integer.toString(s & 0xFFFF);
   }

   private static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   private static String toU32HexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }
}
