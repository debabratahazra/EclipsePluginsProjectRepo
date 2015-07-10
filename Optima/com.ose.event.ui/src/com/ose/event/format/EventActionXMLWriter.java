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
 * This class is used for writing event action XML files.
 */
public class EventActionXMLWriter
{
   private final Object lock;

   private final PrintStream out;

   private boolean open;

   /**
    * Create a new event action XML writer object.
    *
    * @param file  the event action XML file to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public EventActionXMLWriter(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      lock = new Object();
      out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
      open = true;

      writeXMLDeclaration("ISO-8859-1");
      writeDoctype("eventActions", "eventaction.dtd");
      writeStartTag(0, "eventActions", "initState", "0");
   }

   /**
    * Write an event action.
    *
    * @param eventActionPoint  the event action to write.
    */
   public void write(EventActionPoint eventActionPoint)
   {
      synchronized (lock)
      {
         checkState();
   
         if (eventActionPoint instanceof SendEventActionPoint)
         {
            writeSendEventActionPoint((SendEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof ReceiveEventActionPoint)
         {
            writeReceiveEventActionPoint((ReceiveEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof SwapEventActionPoint)
         {
            writeSwapEventActionPoint((SwapEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof CreateEventActionPoint)
         {
            writeCreateEventActionPoint((CreateEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof KillEventActionPoint)
         {
            writeKillEventActionPoint((KillEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof ErrorEventActionPoint)
         {
            writeErrorEventActionPoint((ErrorEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof BindEventActionPoint)
         {
            writeBindEventActionPoint((BindEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof AllocEventActionPoint)
         {
            writeAllocEventActionPoint((AllocEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof FreeEventActionPoint)
         {
            writeFreeEventActionPoint((FreeEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof TimeoutEventActionPoint)
         {
            writeTimeoutEventActionPoint((TimeoutEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint instanceof UserEventActionPoint)
         {
            writeUserEventActionPoint((UserEventActionPoint) eventActionPoint);
         }
         else if (eventActionPoint == null)
         {
            throw new IllegalArgumentException();
         }
         else
         {
            // Ignore unknown event action point types.
         }
      }
   }

   /**
    * Close this event action XML writer and release any resources associated
    * with it.
    */
   public void close()
   {
      synchronized (lock)
      {
         if (open)
         {
            writeEndTag(0, "eventActions");
            out.close();
            open = false;
         }
      }
   }

   private void checkState() throws IllegalStateException
   {
      if (!open)
      {
         throw new IllegalStateException("EventActionXMLWriter is closed");
      }
   }

   private void writeXMLDeclaration(String encoding)
   {
      writeLine("<?xml version=\"1.0\" encoding=\"", encoding, "\"?>");
   }

   private void writeDoctype(String rootElement, String dtd)
   {
      writeLine("<!DOCTYPE ", rootElement, " SYSTEM \"", dtd, "\">");
      writeLine();
   }

   private void writeSendEventActionPoint(SendEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();
      int min = eventActionPoint.getNumberMin();
      int max = eventActionPoint.getNumberMax();
      boolean not = eventActionPoint.isNumberRangeInverted();
      
      boolean useSignalFilter = eventActionPoint.isUseSignalFilter();
      long offset = eventActionPoint.getFilterDataOffset();
      int size = eventActionPoint.getFilterDataSize();
      boolean dataRangeNot = eventActionPoint.isFilterDataRangeInverted();
      long dataRangeMin = eventActionPoint.getFilterDataMin();
      long dataRangeMax = eventActionPoint.getFilterDataMax();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "send");
      writeCommonEventTags(3, eventActionPoint, true);
      writeRangeTag(3, "numberRange", min, max, not);
      writeFilterDataRangeTag(3, "dataFilter", offset, size, dataRangeMin,
            dataRangeMax, dataRangeNot, useSignalFilter);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeReceiveEventActionPoint(ReceiveEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();
      int min = eventActionPoint.getNumberMin();
      int max = eventActionPoint.getNumberMax();
      boolean not = eventActionPoint.isNumberRangeInverted();

      boolean useSignalFilter = eventActionPoint.isUseSignalFilter();
      long offset = eventActionPoint.getFilterDataOffset();
      int size = eventActionPoint.getFilterDataSize();
      boolean dataRangeNot = eventActionPoint.isFilterDataRangeInverted();
      long dataRangeMin = eventActionPoint.getFilterDataMin();
      long dataRangeMax = eventActionPoint.getFilterDataMax();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "receive");
      writeCommonEventTags(3, eventActionPoint, true);
      writeRangeTag(3, "numberRange", min, max, not);
      writeFilterDataRangeTag(3, "dataFilter", offset, size, dataRangeMin,
            dataRangeMax, dataRangeNot, useSignalFilter);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeSwapEventActionPoint(SwapEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "swap");
      writeCommonEventTags(3, eventActionPoint, true);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeCreateEventActionPoint(CreateEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "create");
      writeCommonEventTags(3, eventActionPoint, true);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeKillEventActionPoint(KillEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "kill");
      writeCommonEventTags(3, eventActionPoint, true);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeErrorEventActionPoint(ErrorEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();
      int errorMin = eventActionPoint.getErrorMin();
      int errorMax = eventActionPoint.getErrorMax();
      boolean errorNot = eventActionPoint.isErrorRangeInverted();
      int extraMin = eventActionPoint.getExtraMin();
      int extraMax = eventActionPoint.getExtraMax();
      boolean extraNot = eventActionPoint.isExtraRangeInverted();
      boolean executive = eventActionPoint.isExecutive();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "error");
      writeCommonEventTags(3, eventActionPoint, false);
      writeRangeTag(3, "errorRange", errorMin, errorMax, errorNot);
      writeRangeTag(3, "extraRange", extraMin, extraMax, extraNot);
      writeTag(3, "onlyKernelErrors", Boolean.toString(executive));
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeBindEventActionPoint(BindEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "bind");
      writeCommonEventTags(3, eventActionPoint, false);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeAllocEventActionPoint(AllocEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "alloc");
      writeCommonEventTags(3, eventActionPoint, false);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeFreeEventActionPoint(FreeEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();
      boolean useSignalFilter = eventActionPoint.isUseSignalFilter();
      long offset = eventActionPoint.getFilterDataOffset();
      int size = eventActionPoint.getFilterDataSize();
      boolean dataRangeNot = eventActionPoint.isFilterDataRangeInverted();
      long dataRangeMin = eventActionPoint.getFilterDataMin();
      long dataRangeMax = eventActionPoint.getFilterDataMax();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "free");
      writeCommonEventTags(3, eventActionPoint, false);
      writeFilterDataRangeTag(3, "dataFilter", offset, size, dataRangeMin,
            dataRangeMax, dataRangeNot, useSignalFilter);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeTimeoutEventActionPoint(TimeoutEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "timeout");
      writeCommonEventTags(3, eventActionPoint, false);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeUserEventActionPoint(UserEventActionPoint eventActionPoint)
   {
      int state = eventActionPoint.getState();
      int min = eventActionPoint.getNumberMin();
      int max = eventActionPoint.getNumberMax();
      boolean not = eventActionPoint.isNumberRangeInverted();

      boolean useSignalFilter = eventActionPoint.isUseSignalFilter();
      long offset = eventActionPoint.getFilterDataOffset();
      int size = eventActionPoint.getFilterDataSize();
      boolean dataRangeNot = eventActionPoint.isFilterDataRangeInverted();
      long dataRangeMin = eventActionPoint.getFilterDataMin();
      long dataRangeMax = eventActionPoint.getFilterDataMax();

      writeStartTag(1, "eventAction", "state", toU32String(state));
      writeStartTag(2, "event", "type", "user");
      writeCommonEventTags(3, eventActionPoint, false);
      writeRangeTag(3, "numberRange", min, max, not);
      writeFilterDataRangeTag(3, "dataFilter", offset, size, dataRangeMin,
            dataRangeMax, dataRangeNot, useSignalFilter);
      writeEndTag(2, "event");
      writeActionTag(2, eventActionPoint);
      writeEndTag(1, "eventAction");
   }

   private void writeTag(int indentLevel, String tag, String text)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, ">", text, "</", tag, ">");
   }

   private void writeTag(int indentLevel,
                         String tag,
                         String attribute1, String value1,
                         String attribute2, String value2)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ",
                attribute1, "=\"", value1, "\" ",
                attribute2, "=\"", value2, "\"/>");
   }

   private void writeTag(int indentLevel,
                         String tag,
                         String attribute1, String value1,
                         String attribute2, String value2,
                         String attribute3, String value3)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ",
                attribute1, "=\"", value1, "\" ",
                attribute2, "=\"", value2, "\" ",
                attribute3, "=\"", value3, "\"/>");
   }

   private void writeStartTag(int indentLevel,
                              String tag,
                              String attribute,
                              String value)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ", attribute, "=\"", value, "\">");
   }

   private void writeStartTag(int indentLevel,
                              String tag,
                              String attribute1,
                              String value1,
                              String attribute2,
                              String value2,
                              String attribute3,
                              String value3)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ",
      attribute1, "=\"", value1, "\" ",
      attribute2, "=\"", value2, "\" ",
      attribute3, "=\"", value3, "\">");
   }

   private void writeTag(int indentLevel,
                              String tag,
                              String attribute1,
                              String value1,
                              String attribute2,
                              String value2,
                              String attribute3,
                              String value3,
                              String attribute4,
                              String value4,
                              String attribute5,
                              String value5,
                              String attribute6,
                              String value6)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ",
                attribute1, "=\"", value1, "\" ",
                attribute2, "=\"", value2, "\" ",
                attribute3, "=\"", value3, "\" ",
                attribute4, "=\"", value4, "\" ",
                attribute5, "=\"", value5, "\" ",
                attribute6, "=\"", value6, "\"/>");
   }

   private void writeStartTag(int indentLevel, String tag, String attribute1,
         String value1, String attribute2, String value2, String attribute3,
         String value3, String attribute4, String value4)
   {
      writeIndent(indentLevel);
      writeLine("<", tag, " ",
            attribute1, "=\"", value1, "\" ",
            attribute2, "=\"", value2, "\" ",
            attribute3, "=\"", value3, "\" ",
            attribute4, "=\"", value4, "\">");
   }

   private void writeEndTag(int indentLevel, String tag)
   {
      writeIndent(indentLevel);
      writeLine("</", tag, ">");
   }

   private void writeCommonEventTags(int indentLevel,
                                     EventActionPoint eventActionPoint,
                                     boolean hasToScope)
   {
      int fromScopeType = eventActionPoint.getFromScopeType();
      int fromScopeId = eventActionPoint.getFromScopeId();
      String fromNamePattern = eventActionPoint.getFromNamePattern();
      int toScopeType = eventActionPoint.getToScopeType();
      int toScopeId = eventActionPoint.getToScopeId();
      String toNamePattern = eventActionPoint.getToNamePattern();
      int numIgnore = eventActionPoint.getNumIgnore();

      writeScopeTag(indentLevel, "fromScope",
                    fromScopeType, fromScopeId, fromNamePattern);
      if (hasToScope)
      {
         writeScopeTag(indentLevel, "toScope",
                       toScopeType, toScopeId, toNamePattern);
      }
      if (numIgnore != 0)
      {
         writeTag(indentLevel, "ignoreCount", toU32String(numIgnore));
      }
   }

   private void writeScopeTag(int indentLevel,
                              String tag,
                              int scopeType,
                              int scopeId,
                              String namePattern)
   {
      String type;
      String value;

      switch (scopeType)
      {
         case EventActionPoint.SCOPE_SYSTEM:
            type = "system";
            break;
         case EventActionPoint.SCOPE_SEGMENT_ID:
            type = "segment";
            break;
         case EventActionPoint.SCOPE_BLOCK_ID:
            type = "block";
            break;
         case EventActionPoint.SCOPE_ID:
            type = "process";
            break;
         case EventActionPoint.SCOPE_NAME_PATTERN:
            type = "namePattern";
            break;
         default:
            throw new IllegalArgumentException();
      }

      value = ((scopeType != EventActionPoint.SCOPE_NAME_PATTERN) ?
               toU32HexString(scopeId) : toCDataString(namePattern));

      writeTag(indentLevel, tag, "type", type, "value", value);
   }

   private void writeRangeTag(int indentLevel,
                              String tag,
                              int min,
                              int max,
                              boolean not)
   {
      String minStr = toU32String(min);
      String maxStr = toU32String(max);
      String notStr = Boolean.toString(not);

      writeTag(indentLevel, tag, "min", minStr, "max", maxStr, "not", notStr);
   }

   private void writeFilterDataRangeTag(int indentLevel, String tag,
         long offset, int size, long min, long max, boolean not, boolean useSignalFilter)
   {
      String offsetStr = toU64String(offset);
      String sizeStr = toU32String(size);
      String minStr = toU64String(min);
      String maxStr = toU64String(max);
      String notStr = Boolean.toString(not);
      String  useSFStr = Boolean.toString(useSignalFilter);

      writeTag(indentLevel, tag, "offset", offsetStr, "size", sizeStr, "min",
            minStr, "max", maxStr, "not", notStr, "useSignalFilter", useSFStr);
   }

   private void writeActionTag(int indentLevel, EventActionPoint eventActionPoint)
   {
      int action = eventActionPoint.getAction();
      String type;
      String not = Boolean.toString(eventActionPoint.isNot());
      String fileLineIncluded = Boolean.toString(eventActionPoint.isFileAndLineIncluded());
      boolean dataSupported;
      boolean dataIncluded;

      switch (action)
      {
         case EventActionPoint.ACTION_TRACE:
            type = "trace";
            break;
         case EventActionPoint.ACTION_NOTIFY:
            type = "notify";
            break;
         case EventActionPoint.ACTION_INTERCEPT:
            type = "intercept";
            break;
         case EventActionPoint.ACTION_SET_STATE:
            type = "setState";
            break;
         case EventActionPoint.ACTION_ENABLE_TRACE:
            type = "enableTrace";
            break;
         case EventActionPoint.ACTION_DISABLE_TRACE:
            type = "disableTrace";
            break;
         case EventActionPoint.ACTION_USER:
            type = "user";
            break;
         case EventActionPoint.ACTION_ENABLE_HW_TRACE:
             type = "enableHWTrace";
             break;
         case EventActionPoint.ACTION_DISABLE_HW_TRACE:
             type = "disableHWTrace";
             break;
         default:
            throw new IllegalArgumentException();
      }

      if (eventActionPoint instanceof SendEventActionPoint)
      {
         dataSupported = true;
         dataIncluded =
            ((SendEventActionPoint) eventActionPoint).isSignalDataIncluded();
      }
      else if (eventActionPoint instanceof ReceiveEventActionPoint)
      {
         dataSupported = true;
         dataIncluded =
            ((ReceiveEventActionPoint) eventActionPoint).isSignalDataIncluded();
      }
      else if (eventActionPoint instanceof UserEventActionPoint)
      {
         dataSupported = true;
         dataIncluded =
            ((UserEventActionPoint) eventActionPoint).isEventDataIncluded();
      }
      else
      {
         dataSupported = false;
         dataIncluded = false;
      }

      if (dataSupported)
      {
         writeStartTag(indentLevel,
                       "action",
                       "type", type,
                       "not", not,
                       "fileLineIncluded", fileLineIncluded,
                       "dataIncluded", Boolean.toString(dataIncluded));
      }
      else
      {
         writeStartTag(indentLevel,
                       "action",
                       "type", type,
                       "not", not,
                       "fileLineIncluded", fileLineIncluded);
      }
      if (action == EventActionPoint.ACTION_SET_STATE)
      {
         String newState = toU32String(eventActionPoint.getNewState());
         writeTag(indentLevel + 1, "newState", newState);
      }
      writeEndTag(indentLevel, "action");
   }

   private void writeLine()
   {
      out.println();
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

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5,
                          String part6,
                          String part7,
                          String part8,
                          String part9,
                          String part10,
                          String part11)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.print(part7);
      out.print(part8);
      out.print(part9);
      out.print(part10);
      out.println(part11);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5,
                          String part6,
                          String part7,
                          String part8,
                          String part9,
                          String part10,
                          String part11,
                          String part12,
                          String part13,
                          String part14,
                          String part15)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.print(part7);
      out.print(part8);
      out.print(part9);
      out.print(part10);
      out.print(part11);
      out.print(part12);
      out.print(part13);
      out.print(part14);
      out.println(part15);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5,
                          String part6,
                          String part7,
                          String part8,
                          String part9,
                          String part10,
                          String part11,
                          String part12,
                          String part13,
                          String part14,
                          String part15,
                          String part16,
                          String part17,
                          String part18,
                          String part19)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.print(part7);
      out.print(part8);
      out.print(part9);
      out.print(part10);
      out.print(part11);
      out.print(part12);
      out.print(part13);
      out.print(part14);
      out.print(part15);
      out.print(part16);
      out.print(part17);
      out.print(part18);
      out.println(part19);
   }

   private void writeLine(String part1,
                          String part2,
                          String part3,
                          String part4,
                          String part5,
                          String part6,
                          String part7,
                          String part8, 
                          String part9, 
                          String part10, 
                          String part11, 
                          String part12, 
                          String part13, 
                          String part14, 
                          String part15, 
                          String part16, 
                          String part17, 
                          String part18, 
                          String part19, 
                          String part20, 
                          String part21, 
                          String part22, 
                          String part23,
                          String part24, 
                          String part25,
                          String part26, 
                          String part27)
   {
      out.print(part1);
      out.print(part2);
      out.print(part3);
      out.print(part4);
      out.print(part5);
      out.print(part6);
      out.print(part7);
      out.print(part8);
      out.print(part9);
      out.print(part10);
      out.print(part11);
      out.print(part12);
      out.print(part13);
      out.print(part14);
      out.print(part15);
      out.print(part16);
      out.print(part17);
      out.print(part18);
      out.println(part19);
      out.print(part20);
      out.print(part21);
      out.print(part22);
      out.println(part23);
      out.print(part24);
      out.print(part25);
      out.print(part26);
      out.println(part27);
   }

   private void writeIndent(int indentLevel)
   {
      for (int i = 0; i < indentLevel; i++)
      {
         out.print("   ");
      }
   }

   private static String toU32String(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }

   private static String toU64String(long i)
   {
      return "0x" + Long.toHexString(i).toUpperCase();
   }

   private static String toU32HexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }

   private static String toCDataString(String s)
   {
      return s.replace("&", "&amp;").replace("<", "&lt;");
   }
}
