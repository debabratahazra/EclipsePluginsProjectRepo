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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;
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
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.TargetEvent;
import com.ose.system.TimeoutEvent;
import com.ose.system.UserEvent;
import com.ose.system.TargetEvent.ProcessInfo;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorEventInfoSignal;
import com.ose.system.service.monitor.signal.MonitorGetAllocTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetBindTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetCreateTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetErrorTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetFreeBufTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetKillTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetLossTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetReceiveTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetResetTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSendTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSwapTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTimeoutTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTraceEndmark;
import com.ose.system.service.monitor.signal.MonitorGetTraceRequest;
import com.ose.system.service.monitor.signal.MonitorGetUserTraceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;

/**
 * This class is used for reading event dump files.
 */
public class EventDumpReader
{
   private static final int PROCESS_CACHE_SIZE =
      SystemModelDumpFileWriter.PROCESS_CACHE_SIZE + 1;
   private static final String EVENT_INFO = "EVENT INFO";
   private static final Object TARGET = new Object();

   private final EventReaderClient client;
   private DumpFile dumpFile;
   private boolean bigEndian;
   private SignalRegistry registry;
   private ProcessInfoCache processCache;

   private int osType;
   private int numExecutionUnits;
   private int tickLength;
   private int microTickFrequency;

   /**
    * Create a new event dump reader object.
    *
    * @param client  the event reader client.
    */
   public EventDumpReader(EventReaderClient client)
   {
      if (client == null)
      {
         throw new IllegalArgumentException();
      }
      this.client = client;
   }

   /**
    * Read the events from the specified event dump file. The events read will
    * be reported to the event reader client specified when this event dump
    * reader was created.
    *
    * @param file  the event dump file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void read(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      // Create the dump file representation and determine its byte order.
      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();

      // Initialize the signal registry.
      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetTraceRequest.class);
      registry.add(MonitorGetProcessTraceReply.class);
      registry.add(MonitorGetSendTraceReply.class);
      registry.add(MonitorGetReceiveTraceReply.class);
      registry.add(MonitorGetSwapTraceReply.class);
      registry.add(MonitorGetCreateTraceReply.class);
      registry.add(MonitorGetKillTraceReply.class);
      registry.add(MonitorGetErrorTraceReply.class);
      registry.add(MonitorGetResetTraceReply.class);
      registry.add(MonitorGetUserTraceReply.class);
      registry.add(MonitorGetBindTraceReply.class);
      registry.add(MonitorGetAllocTraceReply.class);
      registry.add(MonitorGetFreeBufTraceReply.class);
      registry.add(MonitorGetTimeoutTraceReply.class);
      registry.add(MonitorGetLossTraceReply.class);
      registry.add(MonitorGetTraceEndmark.class);

      // Create the process cache.
      processCache = new ProcessInfoCache(PROCESS_CACHE_SIZE);

      try
      {
         List signalBlocks;
         List textBlocks;
         TextBlock eventInfoTextBlock = null;
         SignalBlock eventTraceSignalBlock = null;

         // Parse the interface signal block.
         signalBlocks = dumpFile.getSignalBlocks();
         parseInterfaceSignalBlock(signalBlocks);

         // Parse the connection signal block.
         parseConnectSignalBlock(signalBlocks);

         // Get the last event info text block.
         textBlocks = dumpFile.getTextBlocks();
         for (Iterator i = textBlocks.iterator(); i.hasNext();)
         {
            TextBlock textBlock = (TextBlock) i.next();
            String[] descriptions = textBlock.getDescriptions();

            if ((descriptions.length > 0) &&
                descriptions[0].startsWith(EVENT_INFO))
            {
               eventInfoTextBlock = textBlock;
            }
         }

         // Parse the last event info text block.
         if (eventInfoTextBlock != null)
         {
            parseEventInfoTextBlock(eventInfoTextBlock);
         }
         else
         {
            client.commonAttributesRead("unknown",
                  bigEndian,
                  osType,
                  numExecutionUnits,
                  tickLength,
                  microTickFrequency,
                  "unknown",
                  "unknown");
         }

         // Get the last event trace signal block.
         for (Iterator i = signalBlocks.iterator(); i.hasNext();)
         {
            SignalBlock signalBlock = (SignalBlock) i.next();
            int reqSigNo = (int) signalBlock.getRequestSigNo();

            if (reqSigNo == MonitorGetTraceRequest.SIG_NO)
            {
               eventTraceSignalBlock = signalBlock;
            }
         }

         // Parse the last event trace signal block, if any.
         if (eventTraceSignalBlock != null)
         {
            parseEventTraceSignalBlock(eventTraceSignalBlock);
         }
      }
      finally
      {
         dumpFile.close();
      }
   }

   private void parseInterfaceSignalBlock(List signalBlocks) throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorInterfaceRequest.SIG_NO)
         {
            MonitorInterfaceReply reply = (MonitorInterfaceReply)
               readSignalBlock(signalBlock,
                               MonitorInterfaceRequest.class,
                               MonitorInterfaceReply.class);
            osType = reply.osType;
            break;
         }
      }
   }

   private void parseConnectSignalBlock(List signalBlocks) throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorConnectRequest.SIG_NO)
         {
            MonitorConnectReply reply = (MonitorConnectReply)
               readSignalBlock(signalBlock,
                               MonitorConnectRequest.class,
                               MonitorConnectReply.class);
            numExecutionUnits = 0xFFFF & reply.euCount;
            tickLength = reply.tickLength;
            microTickFrequency = reply.ntickFrequency;
            break;
         }
      }
   }

   private void parseEventInfoTextBlock(TextBlock textBlock) throws IOException
   {
      String[] descriptions;
      String target = null;
      String scope = null;
      String eventActions = null;

      if (textBlock == null)
      {
         throw new IllegalArgumentException();
      }

      descriptions = textBlock.getDescriptions();
      for (int i = 0; i < descriptions.length; i++)
      {
         String[] lines = descriptions[i].split("\n");
         for (int j = 0; j < lines.length; j++)
         {
            String line = lines[j];
            String value;

            if ((value = parseAttribute(line, "target")) != null)
            {
               target = value;
            }
            else if ((value = parseAttribute(line, "scope")) != null)
            {
               scope = value;
            }
            else if ((value = parseAttribute(line, "eventActions")) != null)
            {
               eventActions = value;
            }
         }
      }

      if ((target != null) && (scope != null) && (eventActions != null))
      {
         client.commonAttributesRead(target,
                                     bigEndian,
                                     osType,
                                     numExecutionUnits,
                                     tickLength,
                                     microTickFrequency,
                                     scope,
                                     eventActions);
      }
      else
      {
         throw new IOException("Incomplete event info text block");
      }
   }

   private static String parseAttribute(String line, String name)
   {
      if ((line == null) || (name == null))
      {
         throw new IllegalArgumentException();
      }

      if (line.trim().startsWith(name))
      {
         int separator = line.indexOf('=');
         if ((separator > 0) && (separator < line.length() - 1))
         {
            String value = line.substring(separator + 1).trim();
            return ((value.length() > 0) ? value : null);
         }
         else
         {
            return null;
         }
      }
      else
      {
         return null;
      }
   }

   private void parseEventTraceSignalBlock(SignalBlock signalBlock)
      throws IOException
   {
      SignalInputStream in = null;

      if (signalBlock == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         Signal signal;
         boolean endmark = false;

         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         if (!(signal instanceof MonitorGetTraceRequest))
         {
            throw new IffException("Invalid request signal in " +
                  "MonitorGetTraceRequest signal block in dump file");
         }

         do
         {
            signal = readSignal(in);
            if (signal instanceof MonitorGetProcessTraceReply)
            {
               createProcess((MonitorGetProcessTraceReply) signal);
            }
            else if (signal instanceof MonitorEventInfoSignal)
            {
               TargetEvent event = createEvent(signal);
               if (event != null)
               {
                  client.eventRead(event);
               }
            }
            else if (signal instanceof MonitorGetTraceEndmark)
            {
               endmark = true;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                     "MonitorGetTraceRequest signal block in dump file");
            }
         }
         while ((in.available() > 0) && !endmark);

         if (!endmark)
         {
            throw new IffException("Missing MonitorGetTraceEndmark " +
                  "in MonitorGetTraceRequest signal block in dump file");
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
      }
   }

   private void createProcess(MonitorGetProcessTraceReply signal)
   {
      ProcessInfo process = ProcessInfo.getInstance(TARGET,
                                                    signal.pid,
                                                    signal.bid,
                                                    signal.sid,
                                                    signal.type,
                                                    signal.entrypoint,
                                                    signal.properties,
                                                    signal.name);
      processCache.put(signal.pid, process);
   }

   private TargetEvent createEvent(Signal signal)
   {
      if (signal instanceof MonitorGetSendTraceReply)
      {
         return createSendEvent((MonitorGetSendTraceReply) signal);
      }
      else if (signal instanceof MonitorGetReceiveTraceReply)
      {
         return createReceiveEvent((MonitorGetReceiveTraceReply) signal);
      }
      else if (signal instanceof MonitorGetSwapTraceReply)
      {
         return createSwapEvent((MonitorGetSwapTraceReply) signal);
      }
      else if (signal instanceof MonitorGetCreateTraceReply)
      {
         return createCreateEvent((MonitorGetCreateTraceReply) signal);
      }
      else if (signal instanceof MonitorGetKillTraceReply)
      {
         return createKillEvent((MonitorGetKillTraceReply) signal);
      }
      else if (signal instanceof MonitorGetErrorTraceReply)
      {
         return createErrorEvent((MonitorGetErrorTraceReply) signal);
      }
      else if (signal instanceof MonitorGetResetTraceReply)
      {
         return createResetEvent((MonitorGetResetTraceReply) signal);
      }
      else if (signal instanceof MonitorGetUserTraceReply)
      {
         return createUserEvent((MonitorGetUserTraceReply) signal);
      }
      else if (signal instanceof MonitorGetBindTraceReply)
      {
         return createBindEvent((MonitorGetBindTraceReply) signal);
      }
      else if (signal instanceof MonitorGetAllocTraceReply)
      {
         return createAllocEvent((MonitorGetAllocTraceReply) signal);
      }
      else if (signal instanceof MonitorGetFreeBufTraceReply)
      {
         return createFreeEvent((MonitorGetFreeBufTraceReply) signal);
      }
      else if (signal instanceof MonitorGetTimeoutTraceReply)
      {
         return createTimeoutEvent((MonitorGetTimeoutTraceReply) signal);
      }
      else if (signal instanceof MonitorGetLossTraceReply)
      {
         return createLossEvent((MonitorGetLossTraceReply) signal);
      }
      else
      {
         return null;
      }
   }

   private SendEvent createSendEvent(MonitorGetSendTraceReply signal)
   {
      return SendEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   signal.signalNumber,
                                   processCache.get(signal.signalSender),
                                   processCache.get(signal.signalAddressee),
                                   signal.signalSize,
                                   signal.signalAddress,
                                   signal.signalId,
                                   signal.lineNumber,
                                   signal.euId,
                                   signal.fileName,
                                   signal.signalData,
                                   null);
   }

   private ReceiveEvent createReceiveEvent(MonitorGetReceiveTraceReply signal)
   {
      return ReceiveEvent.getInstance(TARGET,
                                      tickLength,
                                      signal.reference,
                                      signal.tick,
                                      signal.ntick,
                                      signal.sec,
                                      signal.sectick,
                                      signal.signalNumber,
                                      processCache.get(signal.signalSender),
                                      processCache.get(signal.signalAddressee),
                                      signal.signalSize,
                                      signal.signalAddress,
                                      signal.signalId,
                                      signal.lineNumber,
                                      signal.euId,
                                      signal.fileName,
                                      signal.signalData,
                                      null);
   }

   private SwapEvent createSwapEvent(MonitorGetSwapTraceReply signal)
   {
      return SwapEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   processCache.get(signal.from),
                                   processCache.get(signal.to),
                                   signal.lineNumber,
                                   signal.euId,
                                   signal.fileName);
   }

   private CreateEvent createCreateEvent(MonitorGetCreateTraceReply signal)
   {
      return CreateEvent.getInstance(TARGET,
                                     tickLength,
                                     signal.reference,
                                     signal.tick,
                                     signal.ntick,
                                     signal.sec,
                                     signal.sectick,
                                     processCache.get(signal.from),
                                     processCache.get(signal.to),
                                     signal.lineNumber,
                                     signal.euId,
                                     signal.fileName);
   }

   private KillEvent createKillEvent(MonitorGetKillTraceReply signal)
   {
      return KillEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   processCache.get(signal.from),
                                   processCache.get(signal.to),
                                   signal.lineNumber,
                                   signal.euId,
                                   signal.fileName);
   }

   private ErrorEvent createErrorEvent(MonitorGetErrorTraceReply signal)
   {
      return ErrorEvent.getInstance(TARGET,
                                    tickLength,
                                    signal.reference,
                                    signal.tick,
                                    signal.ntick,
                                    signal.sec,
                                    signal.sectick,
                                    processCache.get(signal.from),
                                    signal.exec,
                                    signal.error,
                                    signal.extra,
                                    signal.lineNumber,
                                    signal.euId,
                                    signal.fileName);
   }

   private ResetEvent createResetEvent(MonitorGetResetTraceReply signal)
   {
      return ResetEvent.getInstance(TARGET,
                                    tickLength,
                                    signal.reference,
                                    signal.tick,
                                    signal.ntick,
                                    signal.sec,
                                    signal.sectick,
                                    signal.warm,
                                    signal.lineNumber,
                                    signal.fileName);
   }

   private UserEvent createUserEvent(MonitorGetUserTraceReply signal)
   {
      return UserEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   processCache.get(signal.from),
                                   signal.eventNumber,
                                   signal.eventSize,
                                   signal.lineNumber,
                                   signal.euId,
                                   signal.fileName,
                                   signal.eventData,
                                   null);
   }

   private BindEvent createBindEvent(MonitorGetBindTraceReply signal)
   {
      return BindEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   processCache.get(signal.from),
                                   signal.fromEuId,
                                   signal.toEuId,
                                   signal.lineNumber,
                                   signal.fileName);
   }

   private AllocEvent createAllocEvent(MonitorGetAllocTraceReply signal)
   {
      return AllocEvent.getInstance(TARGET,
                                    tickLength,
                                    signal.reference,
                                    signal.tick,
                                    signal.ntick,
                                    signal.sec,
                                    signal.sectick,
                                    processCache.get(signal.from),
                                    signal.signalNumber,
                                    signal.signalSize,
                                    signal.signalAddress,
                                    signal.signalId,
                                    signal.lineNumber,
                                    signal.euId,
                                    signal.fileName);
   }

   private FreeEvent createFreeEvent(MonitorGetFreeBufTraceReply signal)
   {
      return FreeEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   processCache.get(signal.from),
                                   signal.signalNumber,
                                   signal.signalSize,
                                   signal.signalAddress,
                                   signal.signalId,
                                   signal.lineNumber,
                                   signal.euId,
                                   signal.fileName);
   }

   private TimeoutEvent createTimeoutEvent(MonitorGetTimeoutTraceReply signal)
   {
      return TimeoutEvent.getInstance(TARGET,
                                      tickLength,
                                      signal.reference,
                                      signal.tick,
                                      signal.ntick,
                                      signal.sec,
                                      signal.sectick,
                                      signal.timeout,
                                      signal.tmoSource,
                                      processCache.get(signal.from),
                                      signal.lineNumber,
                                      signal.euId,
                                      signal.fileName);
   }

   private LossEvent createLossEvent(MonitorGetLossTraceReply signal)
   {
      return LossEvent.getInstance(TARGET,
                                   tickLength,
                                   signal.reference,
                                   signal.tick,
                                   signal.ntick,
                                   signal.sec,
                                   signal.sectick,
                                   signal.lostSize);
   }

   private Signal readSignalBlock(SignalBlock signalBlock,
                                  Class requestClass,
                                  Class replyClass)
      throws IOException
   {
      SignalInputStream in = null;
      Signal request;
      Signal reply;

      try
      {
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
               requestClass.getName() + " signal block in dump file");
         }

         reply = readSignal(in);
         if (!replyClass.isInstance(reply))
         {
            throw new IffException("Invalid reply signal in " +
               requestClass.getName() + " signal block in dump file");
         }
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            } catch (IOException ignore) {}
         }
      }

      return reply;
   }

   private Signal readSignal(SignalInputStream in) throws IOException
   {
      int sigSize;
      int sigNo;
      byte[] sigData;

      sigSize = in.readS32();
      if (sigSize < 4)
      {
         throw new IffException("Invalid signal size in dump file");
      }
      sigNo = in.readS32();
      sigData = new byte[sigSize - 4];
      in.readFully(sigData);
      in.align(4);
      return createSignal(0, 0, sigSize, sigNo, sigData, registry, bigEndian);
   }

   private static Signal createSignal(int sender,
                                      int addressee,
                                      int sigSize,
                                      int sigNo,
                                      byte[] sigData,
                                      SignalRegistry registry,
                                      boolean bigEndian)
      throws IOException
   {
      Class sigClass;
      Signal sig;

      if ((sigSize == 0) || (sigData == null) || (registry == null))
      {
         throw new IllegalArgumentException();
      }

      // Find the signal class in the signal registry.
      sigClass = registry.getSignalClass(sigNo);

      if (sigClass != null)
      {
         // Create an object of the found signal class.
         try
         {
            sig = (Signal) sigClass.newInstance();
         }
         catch (InstantiationException e)
         {
            // The signal class did not have a default constructor.
            throw new RuntimeException("Default constructor missing in " +
                                       sigClass.getName());
         }
         catch (IllegalAccessException e)
         {
            // The signal class did not have a public default constructor.
            throw new RuntimeException("Default constructor not public in " +
                                       sigClass.getName());
         }
         catch (ClassCastException e)
         {
            // The signal class did not extend Signal.
            throw new RuntimeException(sigClass.getName() +
                                       " do not extend com.ose.gateway.Signal");
         }
      }
      else
      {
         // Did not find a registered signal class.
         sig = new UnregisteredSignal(sigNo);
      }

      // Extract common data from the native signal to the Java signal.
      sig.init(sender, addressee, sigSize);

      // Extract user data from the native signal to the Java signal.
      sig.oseToJava(sigData, bigEndian);

      return sig;
   }

   /**
    * This class provides a process info cache of a specified maximum size
    * where the oldest entry is removed if the size limit is threatened to
    * be exceeded.
    *
    * @see java.util.LinkedHashMap
    */
   private static class ProcessInfoCache extends LinkedHashMap
   {
      private static final long serialVersionUID = 1L;

      private final int processCacheCount;

      /**
       * Create a new process info cache object.
       *
       * @param processCacheCount  the maximum size of this process info
       * cache.
       */
      ProcessInfoCache(int processCacheCount)
      {
         super(processCacheCount);
         if (processCacheCount < 1)
         {
            throw new IllegalArgumentException();
         }
         this.processCacheCount = processCacheCount;
      }

      /**
       * Return the process info object with the specified process ID or null
       * if there is no mapping for the specified process ID.
       *
       * @param id  the process ID.
       * @return the corresponding process info object or null if there is no
       * mapping for the specified process ID.
       */
      public ProcessInfo get(int id)
      {
         ProcessInfo process;

         process = (ProcessInfo) get(new Integer(id));
         return process;
      }

      /**
       * Associate the specified process ID with the specified process info
       * object. If there is a previous mapping for the specified process ID,
       * the old process info object is removed.
       *
       * @param id  the process ID.
       * @param process  the corresponding process info object.
       */
      public void put(int id, ProcessInfo process)
      {
         Integer key;

         if (process == null)
         {
            throw new IllegalArgumentException();
         }
         key = new Integer(id);
         if (containsKey(key))
         {
            // Remove the old mapping to update its insertion order.
            remove(key);
         }
         put(key, process);
      }

      /**
       * This method limits the maximum size of the process info cache to
       * processCacheCount entries and removes the oldest entry when a new
       * entry is added that threatens the size limit of the process info
       * cache to be exceeded.
       *
       * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
       */
      protected boolean removeEldestEntry(Map.Entry eldest)
      {
         return (size() > processCacheCount);
      }
   }
}
