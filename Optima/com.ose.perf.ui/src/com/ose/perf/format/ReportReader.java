/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.format;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.SignalBlock;
import com.ose.system.LoadModuleInfo;
import com.ose.system.PerformanceCounterReport;
import com.ose.system.service.monitor.MonitorCounterReport;
import com.ose.system.service.monitor.MonitorCounterReportExt;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorCounterReportsLossNotify;
import com.ose.system.service.monitor.signal.MonitorCounterReportsNotify;
import com.ose.system.service.monitor.signal.MonitorGetCounterTypeEnabledReply;
import com.ose.system.service.monitor.signal.MonitorGetCounterTypeEnabledRequest;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoEndmark;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoLongReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessInfoRequest;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesEndmark;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesReply;
import com.ose.system.service.monitor.signal.MonitorGetSupportedCounterTypesRequest;
import com.ose.system.service.monitor.signal.MonitorInterfaceReply;
import com.ose.system.service.monitor.signal.MonitorInterfaceRequest;
import com.ose.system.service.monitor.signal.MonitorNameReply;
import com.ose.system.service.monitor.signal.MonitorNameRequest;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;

/**
 * This class is used for reading source profiling report files.
 */
public class ReportReader
{
   private final ReportReaderClient client;
   private DumpFile dumpFile;
   private boolean bigEndian;
   private SignalRegistry registry;

   private String target;
   private int osType;
   private int cpuType;
   private int numExecutionUnits;
   private int tickLength;
   private int microTickFrequency;

   /**
    * Create a new report reader object.
    *
    * @param client  the report reader client.
    */
   public ReportReader(ReportReaderClient client)
   {
      if (client == null)
      {
         throw new IllegalArgumentException();
      }
      this.client = client;
   }

   /**
    * Read the performance counter reports and related information from the
    * specified report file. The performance counter reports read will be
    * reported to the report reader client specified when this report reader
    * was created.
    *
    * @param file  the report file to read.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void read(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      dumpFile = new DumpFile(file);
      bigEndian = dumpFile.isBigEndian();
      target = "";

      registry = new SignalRegistry();
      registry.add(MonitorInterfaceRequest.class);
      registry.add(MonitorInterfaceReply.class);
      registry.add(MonitorNameRequest.class);
      registry.add(MonitorNameReply.class);
      registry.add(MonitorConnectRequest.class);
      registry.add(MonitorConnectReply.class);
      registry.add(MonitorGetProcessInfoRequest.class);
      registry.add(MonitorGetProcessInfoReply.class);
      registry.add(MonitorGetProcessInfoLongReply.class);
      registry.add(MonitorGetProcessInfoEndmark.class);
      registry.add(MonitorGetSupportedCounterTypesRequest.class);
      registry.add(MonitorGetSupportedCounterTypesReply.class);
      registry.add(MonitorGetSupportedCounterTypesEndmark.class);
      registry.add(MonitorGetCounterTypeEnabledRequest.class);
      registry.add(MonitorGetCounterTypeEnabledReply.class);
      registry.add(MonitorCounterReportsNotify.class);
      registry.add(MonitorCounterReportsLossNotify.class);
      registry.add(PmLoadModuleInfoRequest.class);
      registry.add(PmLoadModuleInfoReply.class);

      try
      {
         List signalBlocks = dumpFile.getSignalBlocks();
         parseInterfaceSignalBlock(signalBlocks);
         parseNameSignalBlock(signalBlocks);
         parseConnectSignalBlock(signalBlocks);
         parseLoadModuleInfoSignalBlocks(signalBlocks);
         parseProcessInfoSignalBlocks(signalBlocks);
         parseSupportedCounterTypesSignalBlock(signalBlocks);
         parseCounterTypeEnabledSignalBlocks(signalBlocks);
         parseCounterReportsSignalBlocks(signalBlocks);
      }
      finally
      {
         dumpFile.close();
      }
   }

   private void parseInterfaceSignalBlock(List signalBlocks) throws IOException
   {
      boolean found = false;

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
            cpuType = reply.cpuClass;
            found = true;
            break;
         }
      }

      if (!found)
      {
         throw new IllegalArgumentException(
            "Missing MonitorInterfaceRequest signal block");
      }
   }

   private void parseNameSignalBlock(List signalBlocks) throws IOException
   {
      boolean found = false;

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

         if (reqSigNo == MonitorNameRequest.SIG_NO)
         {
            MonitorNameReply reply = (MonitorNameReply)
               readSignalBlock(signalBlock,
                               MonitorNameRequest.class,
                               MonitorNameReply.class);
            target = reply.systemName;
            found = true;
            break;
         }
      }

      if (!found)
      {
         throw new IllegalArgumentException(
            "Missing MonitorNameRequest signal block");
      }
   }

   private void parseConnectSignalBlock(List signalBlocks) throws IOException
   {
      boolean found = false;

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
            found = true;
            break;
         }
      }

      if (!found)
      {
         throw new IllegalArgumentException(
            "Missing MonitorConnectRequest signal block");
      }

      client.targetSettingsRead(target,
                                bigEndian,
                                osType,
                                cpuType,
                                numExecutionUnits,
                                tickLength,
                                microTickFrequency);
   }

   private void parseLoadModuleInfoSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == PmLoadModuleInfoRequest.SIG_NO)
         {
            PmLoadModuleInfoReply reply = (PmLoadModuleInfoReply)
               readSignalBlock(signalBlock,
                               PmLoadModuleInfoRequest.class,
                               PmLoadModuleInfoReply.class);
            handleLoadModule(reply);
         }
      }
   }

   private void parseProcessInfoSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         List signals;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetProcessInfoRequest.SIG_NO)
         {
            signals = readSignalBlock(signalBlock,
                                      MonitorGetProcessInfoRequest.class,
                                      MonitorGetProcessInfoReply.class,
                                      MonitorGetProcessInfoLongReply.class,
                                      MonitorGetProcessInfoEndmark.class);
            handleProcess(signals);
         }
      }
   }

   private void parseSupportedCounterTypesSignalBlock(List signalBlocks)
      throws IOException
   {
      boolean found = false;

      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (int i = signalBlocks.size() - 1; i >= 0; i--)
      {
         SignalBlock signalBlock;
         int reqSigNo;
         List signals;

         signalBlock = (SignalBlock) signalBlocks.get(i);
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetSupportedCounterTypesRequest.SIG_NO)
         {
            signals = readSignalBlock(
               signalBlock,
               MonitorGetSupportedCounterTypesRequest.class,
               MonitorGetSupportedCounterTypesReply.class,
               MonitorGetSupportedCounterTypesEndmark.class);
            for (Iterator j = signals.iterator(); j.hasNext();)
            {
               MonitorGetSupportedCounterTypesReply counter =
                  (MonitorGetSupportedCounterTypesReply) j.next();
               client.performanceCounterRead(counter.counterType,
                                             counter.name);
            }
            found = true;
            break;
         }
      }

      if (!found)
      {
         throw new IllegalArgumentException(
            "Missing MonitorGetSupportedCounterTypesRequest signal block");
      }
   }

   private void parseCounterTypeEnabledSignalBlocks(List signalBlocks)
      throws IOException
   {
      boolean found = false;

      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorGetCounterTypeEnabledRequest.SIG_NO)
         {
            MonitorGetCounterTypeEnabledReply reply =
               (MonitorGetCounterTypeEnabledReply) readSignalBlock(
                     signalBlock,
                     MonitorGetCounterTypeEnabledRequest.class,
                     MonitorGetCounterTypeEnabledReply.class);
            client.performanceCounterEnablementRead(reply.euId,
                                                    reply.counterType,
                                                    reply.counterValue,
                                                    reply.maxReports);
            found = true;
         }
      }

      if (!found)
      {
         throw new IllegalArgumentException(
            "Missing MonitorGetCounterTypeEnabledRequest signal block");
      }
   }

   private void parseCounterReportsSignalBlocks(List signalBlocks)
      throws IOException
   {
      if (signalBlocks == null)
      {
         throw new IllegalArgumentException();
      }

      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         int reqSigNo;

         signalBlock = (SignalBlock) i.next();
         reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == MonitorCounterReportsNotify.SIG_NO)
         {
            MonitorCounterReportsNotify signal =
               (MonitorCounterReportsNotify) readSignalBlock(
                     signalBlock, MonitorCounterReportsNotify.class);
            PerformanceCounterReport[] perfCounterReports =
               new PerformanceCounterReport[signal.reports.length];
            for (int j = 0; j < signal.reports.length; j++)
            {
               MonitorCounterReport monitorReport = signal.reports[j];
               int pid = (monitorReport instanceof MonitorCounterReportExt)
                  ? ((MonitorCounterReportExt) monitorReport).pid : 0;
               PerformanceCounterReport report =
                  PerformanceCounterReport.getInstance(monitorReport.pc, pid);
               perfCounterReports[j] = report;
            }
            client.reportsRead(signal.euId, signal.counterType, perfCounterReports);
         }
         else if (reqSigNo == MonitorCounterReportsLossNotify.SIG_NO)
         {
            MonitorCounterReportsLossNotify signal =
               (MonitorCounterReportsLossNotify) readSignalBlock(
                  signalBlock, MonitorCounterReportsLossNotify.class);
            client.reportsLossRead(signal.euId, signal.counterType, signal.reportsLost);
         }
      }
   }

   private void handleLoadModule(PmLoadModuleInfoReply signal)
   {
      LoadModuleInfo loadModule = LoadModuleInfo.getInstance(
            signal.install_handle,
            signal.entrypoint,
            signal.options,
            signal.text_base,
            signal.text_size,
            signal.data_base,
            signal.data_size,
            signal.no_of_sections,
            signal.no_of_instances,
            signal.file_format,
            signal.file_name);
      client.loadModuleRead(loadModule);
   }

   private void handleProcess(List signals)
   {
      for (Iterator i = signals.iterator(); i.hasNext();)
      {
         Object obj = i.next();

         if (obj instanceof MonitorGetProcessInfoReply)
         {
            MonitorGetProcessInfoReply process =
               (MonitorGetProcessInfoReply) obj;
            client.processRead(process.pid, process.name);
         }
         else if (obj instanceof MonitorGetProcessInfoLongReply)
         {
            MonitorGetProcessInfoLongReply process =
               (MonitorGetProcessInfoLongReply) obj;
            client.processRead(process.pid, process.processName);
         }
      }
   }

   private Signal readSignalBlock(SignalBlock signalBlock, Class notifyClass)
      throws IOException
   {
      SignalInputStream in = null;
      Signal notify;

      try
      {
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         notify = readSignal(in);
         if (!notifyClass.isInstance(notify))
         {
            throw new IffException("Invalid notify signal in " +
               notifyClass.getName() + " signal block in dump file");
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

      return notify;
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

   private List readSignalBlock(SignalBlock signalBlock,
                                Class requestClass,
                                Class replyClass,
                                Class endmarkClass)
      throws IOException
   {
      List signals;
      SignalInputStream in = null;
      Signal request;
      Signal reply;
      boolean endmark = false;

      try
      {
         signals = new ArrayList();
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
               requestClass.getName() + " signal block in dump file");
         }

         do
         {
            reply = readSignal(in);
            if (replyClass.isInstance(reply))
            {
               signals.add(reply);
            }
            else if (endmarkClass.isInstance(reply))
            {
               endmark = true;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                  requestClass.getName() + " signal block in dump file");
            }
         }
         while ((in.available() > 0) && !endmark);

         if (!endmark)
         {
            throw new IffException("Missing " + endmarkClass.getName() +
               " in " + requestClass.getName() + " signal block in dump file");
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

      return signals;
   }

   private List readSignalBlock(SignalBlock signalBlock,
                                Class requestClass,
                                Class replyClass,
                                Class longReplyClass,
                                Class endmarkClass)
      throws IOException
   {
      List signals;
      SignalInputStream in = null;
      Signal request;
      Signal reply;
      boolean endmark = false;

      try
      {
         signals = new ArrayList();
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         request = readSignal(in);
         if (!requestClass.isInstance(request))
         {
            throw new IffException("Invalid request signal in " +
               requestClass.getName() + " signal block in dump file");
         }

         do
         {
            reply = readSignal(in);
            if (replyClass.isInstance(reply))
            {
               signals.add(reply);
            }
            else if (longReplyClass.isInstance(reply))
            {
               signals.add(reply);
            }
            else if (endmarkClass.isInstance(reply))
            {
               endmark = true;
            }
            else
            {
               throw new IffException("Invalid reply signal in " +
                  requestClass.getName() + " signal block in dump file");
            }
         }
         while ((in.available() > 0) && !endmark);

         if (!endmark)
         {
            throw new IffException("Missing " + endmarkClass.getName() +
               " in " + requestClass.getName() + " signal block in dump file");
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

      return signals;
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
}