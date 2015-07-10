/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2012 by Enea Software AB.
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

package com.ose.perf.ui.launch;

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
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleInfoRequest;

public class LoadModuleReader
{
   private static final SignalRegistry SIGNAL_REGISTRY;

   static
   {
      SIGNAL_REGISTRY = new SignalRegistry();
      SIGNAL_REGISTRY.add(PmLoadModuleInfoRequest.class);
      SIGNAL_REGISTRY.add(PmLoadModuleInfoReply.class);
   }

   public List<LoadModuleInfo> getLoadModules(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      DumpFile dumpFile = new DumpFile(file);

      try
      {
         return parseDumpFile(dumpFile);
      }
      finally
      {
         dumpFile.close();
      }
   }

   private List<LoadModuleInfo> parseDumpFile(DumpFile dumpFile)
      throws IOException
   {
      List<LoadModuleInfo> loadModules = new ArrayList<LoadModuleInfo>();

      for (Iterator i = dumpFile.getSignalBlocks().iterator(); i.hasNext();)
      {
         SignalBlock signalBlock = (SignalBlock) i.next();
         int reqSigNo = (int) signalBlock.getRequestSigNo();

         if (reqSigNo == PmLoadModuleInfoRequest.SIG_NO)
         {
            PmLoadModuleInfoReply reply = readSignalBlock(
                  signalBlock, dumpFile.isBigEndian());
            LoadModuleInfo loadModule = LoadModuleInfo.getInstance(
                  reply.install_handle,
                  reply.entrypoint,
                  reply.options,
                  reply.text_base,
                  reply.text_size,
                  reply.data_base,
                  reply.data_size,
                  reply.no_of_sections,
                  reply.no_of_instances,
                  reply.file_format,
                  reply.file_name);
            loadModules.add(loadModule);
         }
      }

      return loadModules;
   }

   private PmLoadModuleInfoReply readSignalBlock(SignalBlock signalBlock,
                                                 boolean bigEndian)
      throws IOException
   {
      SignalInputStream in = null;
      PmLoadModuleInfoReply loadModuleInfoReply;

      try
      {
         in = new SignalInputStream(signalBlock.getInputStream(), bigEndian);
         Signal request = readSignal(in, bigEndian);
         if (!(request instanceof PmLoadModuleInfoRequest))
         {
            throw new IffException("Invalid request signal in " +
               "PmLoadModuleInfoRequest signal block in dump file");
         }

         Signal reply = readSignal(in, bigEndian);
         if (!(reply instanceof PmLoadModuleInfoReply))
         {
            throw new IffException("Invalid reply signal in " +
               "PmLoadModuleInfoRequest + signal block in dump file");
         }
         loadModuleInfoReply = (PmLoadModuleInfoReply) reply;
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

      return loadModuleInfoReply;
   }

   private static Signal readSignal(SignalInputStream in, boolean bigEndian)
      throws IOException
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
      return createSignal(0, 0, sigSize, sigNo, sigData, bigEndian);
   }

   private static Signal createSignal(int sender,
                                      int addressee,
                                      int sigSize,
                                      int sigNo,
                                      byte[] sigData,
                                      boolean bigEndian)
      throws IOException
   {
      Class sigClass;
      Signal sig;

      if ((sigSize == 0) || (sigData == null))
      {
         throw new IllegalArgumentException();
      }

      // Find the signal class in the signal registry.
      sigClass = SIGNAL_REGISTRY.getSignalClass(sigNo);

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