/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2012-2013 by Enea Software AB.
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

package com.ose.pmd.tracebuffer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalRegistry;
import com.ose.gateway.UnregisteredSignal;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.tracebuffer.frame.TraceframeFormatter;
import com.ose.system.service.monitor.signal.MonitorConnectReply;
import com.ose.system.service.monitor.signal.MonitorConnectRequest;
import com.ose.system.service.monitor.signal.MonitorGetConnectedClientsEndmark;
import com.ose.system.service.monitor.signal.MonitorGetConnectedClientsReply;
import com.ose.system.service.monitor.signal.MonitorGetConnectedClientsRequest;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoRequest;
import com.ose.system.service.pm.signal.PmProgramInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetAnnotationsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetAnnotationsRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetStatusReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetStatusRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracebufferRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetTracepointsRequest;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesEndmark;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesRequest;

/* Loads a pmd file and creates the ExperimentData */

public class TracebufferReader {
   
   private boolean bigEndian;

   private SignalRegistry registry;
   
   public TracebufferReader()
   {
      super();
      bigEndian = false;
      registry = new SignalRegistry();
   }
   
   public boolean isBigEndian()
   {
      return bigEndian;
   }
   
   public ExperimentData parseInputFile(String fileName)
   {
      File inputFile;
      DumpFile dumpFile = null;
      ExperimentData experimentData = null;
      
      try
      {
         inputFile = new File(fileName);
         dumpFile = new DumpFile(inputFile);
         bigEndian = dumpFile.isBigEndian();
         
         // Add signals to the signal registry.
         registry.add(MonitorConnectRequest.class);
         registry.add(MonitorConnectReply.class);
         registry.add(MonitorGetConnectedClientsRequest.class);
         registry.add(MonitorGetConnectedClientsReply.class);
         registry.add(MonitorGetConnectedClientsEndmark.class);
         registry.add(PmProgramInfoRequest.class);
         registry.add(PmProgramInfoReply.class);
         registry.add(PmLoadModuleSectionInfoRequest.class);
         registry.add(PmLoadModuleSectionInfoReply.class);
         registry.add(MonitorTracepointGetVariablesRequest.class);
         registry.add(MonitorTracepointGetVariablesReply.class);
         registry.add(MonitorTracepointGetVariablesEndmark.class);
         registry.add(MonitorTracepointGetTracepointsRequest.class);
         registry.add(MonitorTracepointGetTracepointsReply.class);
         registry.add(MonitorTracepointGetTracepointsEndmark.class);
         registry.add(MonitorTracepointGetActionsRequest.class);
         registry.add(MonitorTracepointGetActionsReply.class);
         registry.add(MonitorTracepointGetActionsEndmark.class);
         registry.add(MonitorTracepointGetAnnotationsRequest.class);
         registry.add(MonitorTracepointGetAnnotationsReply.class);         
         registry.add(MonitorTracepointGetStatusRequest.class);
         registry.add(MonitorTracepointGetStatusReply.class);
         registry.add(MonitorTracepointGetTracebufferRequest.class);
         registry.add(TraceframeFormatter.class);
         registry.add(MonitorTracepointGetTracebufferEndmark.class);
       
         List<SignalBlock> signalBlocks;
         signalBlocks = (List<SignalBlock>)dumpFile.getSignalBlocks();
         experimentData = new ExperimentData();
         experimentData = parseBlocks(signalBlocks);
         
      }
      catch (NullPointerException e)
      {
         System.err.println("No file path was given");
         //e.printStackTrace();
      }
      catch (IffException e)
      {
         System.err.println("Invalid file format. Not an Iff file.");
         //e.printStackTrace();
      }
      catch (IOException e)
      {
         System.err.println("Could not open file");
         //e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (dumpFile != null && dumpFile.isOpen())
         {
            dumpFile.close();
         }
      }
      return experimentData;
   }
   
      
   ExperimentData parseBlocks(List<SignalBlock> signalBlocks)
   {
      ExperimentData experimentData = new ExperimentData();
      ListIterator<SignalBlock> signalBlocksIterator = signalBlocks.listIterator();
      
      while(signalBlocksIterator.hasNext())
      {
         SignalBlock currentBlock = (SignalBlock)signalBlocksIterator.next();
         int requestSignal = (int)currentBlock.getRequestSigNo();
         
         switch (requestSignal)
         {
            case MonitorConnectRequest.SIG_NO:
               setCpuType(experimentData, currentBlock);
               break;
            case MonitorGetConnectedClientsRequest.SIG_NO:
               setConnectedClients(experimentData, currentBlock);
               break;
            case PmProgramInfoRequest.SIG_NO:
               setProgramInfo(experimentData, currentBlock);
               break;
            case PmLoadModuleSectionInfoRequest.SIG_NO:
               setSectionInfos(experimentData, currentBlock);
               break;
            case MonitorTracepointGetVariablesRequest.SIG_NO:
               setTracepointVariables(experimentData, currentBlock);
               break;
            case MonitorTracepointGetTracepointsRequest.SIG_NO:
               setTracepoints(experimentData, currentBlock);
               break;
            case MonitorTracepointGetActionsRequest.SIG_NO:
               setTracepointActions(experimentData, currentBlock);
               break;
            case MonitorTracepointGetAnnotationsRequest.SIG_NO:
               setTracepointAnnotations(experimentData, currentBlock);
               break;
            case MonitorTracepointGetStatusRequest.SIG_NO:
               setExperimentStatus(experimentData, currentBlock);
               break;
            case MonitorTracepointGetTracebufferRequest.SIG_NO:
               setTracebufferEntries(experimentData, currentBlock);
               break;
         }
         
      }
      
      return experimentData;
   }
  
   public DumpFile getDumpFile(String file_name)
   {
      File inputFile = null;
      DumpFile dumpFile = null;
      
      try
      {
         inputFile = new File(file_name);
         dumpFile = new DumpFile(inputFile);
      }
      catch (IffException e)
      {
         System.err.println("Invalid file format. Not an Iff file.");
         e.printStackTrace();
      }
      catch (IOException e)
      {
         System.err.println("Could not open file");
         e.printStackTrace();
      }
      return dumpFile;
   }
   
   public SignalBlock getTracebufferFramesBlock(DumpFile dumpFile)
   {
      List<SignalBlock> signalBlocks;
      SignalBlock framesBlock = null;
      try
      {
         signalBlocks = (List<SignalBlock>)dumpFile.getSignalBlocks();
         framesBlock = findFramesBlock(signalBlocks);
      }
      catch (IffException e)
      {
         System.err.println("Invalid file format. Not an Iff file.");
         e.printStackTrace();
      }
      return framesBlock;
   }   

   
   private SignalBlock findFramesBlock(List<SignalBlock> signalBlocks)
   {
      ListIterator<SignalBlock> signalBlocksIterator = signalBlocks.listIterator();
      
      while(signalBlocksIterator.hasNext())
      {
         SignalBlock currentBlock = (SignalBlock)signalBlocksIterator.next();
         if (currentBlock.getRequestSigNo() == MonitorTracepointGetTracebufferRequest.SIG_NO)
         {
            return currentBlock;
         }
      }
      return null;
   }
   
   private void setCpuType(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
     
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorConnectRequest)
         {
            signal = readSignal(in);
            if (signal instanceof MonitorConnectReply)
            {
               experiment.setCpuType(((MonitorConnectReply)signal).cpuType);
            }
            else
            {
               System.err.println("Invalid reply signal in MonitorConnectRequest signal block");
            }
         }
         else
         {
            System.err.println("Invalid request signal in MonitorConnectRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorConnectRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   
   private void setConnectedClients(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
      boolean endmark = false;
      
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorGetConnectedClientsRequest)
         {
            do
            {
               signal = readSignal(in);
               
               if (signal instanceof MonitorGetConnectedClientsReply)
               {
                  experiment.addConnectedClient((MonitorGetConnectedClientsReply)signal);
               }
               else if (signal instanceof MonitorGetConnectedClientsEndmark)
               {
                  endmark = true;
               }
               else
               {
                  System.err.println("Invalid reply signal in MonitorGetConnectedClientsRequest signal block");
               }
               
            } while ((in.available() > 0) && !endmark);
         }
         else
         {
            System.err.println("Invalid request signal in MonitorGetConnectedClientsRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorConnectedClientsRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   
   private void setProgramInfo(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
     
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof PmProgramInfoRequest)
         {
            signal = readSignal(in);
            if (signal instanceof PmProgramInfoReply)
            {
               experiment.setProgramInfo((PmProgramInfoReply)signal);
            }
            else
            {
               System.err.println("Invalid reply signal in PmProgramInfoRequest signal block");
            }
         }
         else
         {
            System.err.println("Invalid request signal in PmProgramInfoRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid PmProgramInfoRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   private void setSectionInfos(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
     
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof PmLoadModuleSectionInfoRequest)
         {
            signal = readSignal(in);
            if (signal instanceof PmLoadModuleSectionInfoReply)
            {
               experiment.addSectionInfos((PmLoadModuleSectionInfoReply)signal);
            }
            else
            {
               System.err.println("Invalid reply signal in PmLoadModuleSectionInfoRequest signal block");
            }
         }
         else
         {
            System.err.println("Invalid request signal in PmLoadModuleSectionInfoRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid PmLoadModuleSectionInfoRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   private void setTracepoints(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
      boolean endmark = false;
      
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorTracepointGetTracepointsRequest)
         {
            do
            {
               signal = readSignal(in);
               
               if (signal instanceof MonitorTracepointGetTracepointsReply)
               {
                  experiment.addTracepoint((MonitorTracepointGetTracepointsReply)signal);
               }
               else if (signal instanceof MonitorTracepointGetTracepointsEndmark)
               {
                  endmark = true;
               }
               else
               {
                  System.err.println("Invalid reply signal in MonitorTracepointGetTracepointsRequest signal block");
               }
            } while ((in.available() > 0) && !endmark);
         }
         else
         {
            System.err.println("Invalid request signal in MonitorTracepointGetTracepointsRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorTracepointGetTracepointsRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   private void setTracepointActions(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
      boolean endmark = false;
      int tracepointId = 0;
      
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorTracepointGetActionsRequest)
         {
            tracepointId = ((MonitorTracepointGetActionsRequest)signal).tracepointId;
            do
            {
               signal = readSignal(in);
               
               if (signal instanceof MonitorTracepointGetActionsReply)
               {
                  experiment.addTracepointAction(tracepointId, (MonitorTracepointGetActionsReply)signal);
               }
               else if (signal instanceof MonitorTracepointGetActionsEndmark)
               {
                  endmark = true;
               }
               else
               {
                  System.err.println("Invalid reply signal in MonitorTracepointGetActionsRequest signal block for tracepoint " +
                                     tracepointId);
               }
               
            } while ((in.available() > 0) && !endmark);
         }
         else
         {
            System.err.println("Invalid request signal in MonitorTracepointGetActionsRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorTracepointGetActionsRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   private void setTracepointAnnotations(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
      int tracepointId;
     
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorTracepointGetAnnotationsRequest)
         {
            tracepointId = ((MonitorTracepointGetAnnotationsRequest)signal).tracepointId;
            signal = readSignal(in);
            if (signal instanceof MonitorTracepointGetAnnotationsReply)
            {
               experiment.addTracepointAnnotation(tracepointId, (MonitorTracepointGetAnnotationsReply)signal);
            }
            else
            {
               System.err.println("Invalid reply signal in MonitorTracepointGetAnnotationsRequest signal block for" +
                                  "tracepiont" + tracepointId);
            }
         }
         else
         {
            System.err.println("Invalid request signal in MonitorTracepointGetAnnotationsRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorTracepointGetAnnotationsRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }     
   }
   
   private void setTracepointVariables(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
      boolean endmark = false;
      
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorTracepointGetVariablesRequest)
         {
            do
            {
               signal = readSignal(in);
               
               if (signal instanceof MonitorTracepointGetVariablesReply)
               {
                  experiment.addTraceVariable((MonitorTracepointGetVariablesReply)signal);
               }
               else if (signal instanceof MonitorTracepointGetVariablesEndmark)
               {
                  endmark = true;
               }
               else
               {
                  System.err.println("Invalid reply signal in MonitorTracepointGetVariablesRequest signal block");
               }
               
            } while ((in.available() > 0) && !endmark);
         }
         else
         {
            System.err.println("Invalid request signal in MonitorTracepointGetVariablesRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorTracepointGetVariablesRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   private void setExperimentStatus(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
     
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorTracepointGetStatusRequest)
         {
            signal = readSignal(in);
            if (signal instanceof MonitorTracepointGetStatusReply)
            {
               experiment.setExperimentStatus((MonitorTracepointGetStatusReply)signal);
            }
            else
            {
               System.err.println("Invalid reply signal in MonitorTracepointGetStatusRequest signal block");
            }
         }
         else
         {
            System.err.println("Invalid request signal in MonitorTracepointGetStatusRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorTracepointGetStatusRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }     
   }
   
   private void setTracebufferEntries(ExperimentData experiment, SignalBlock currentBlock)
   {
      SignalInputStream in = null;
      Signal signal;
      boolean endmark = false;
      
      try
      {
         in = new SignalInputStream(currentBlock.getInputStream(), bigEndian);
         signal = readSignal(in);
         
         if (signal instanceof MonitorTracepointGetTracebufferRequest)
         {
            do
            {
               signal = readSignal(in);
               
               if (signal instanceof TraceframeFormatter)
               {
                  experiment.computeTracebufferEntrie((TraceframeFormatter)signal, bigEndian);
               }
               else if (signal instanceof MonitorTracepointGetTracebufferEndmark)
               {
                  endmark = true;
               }
               else
               {
                  System.err.println("Invalid reply signal in MonitorTracepointGetTracebufferRequest signal block");
               }
               
            } while ((in.available() > 0) && !endmark);
         }
         else
         {
            System.err.println("Invalid request signal in MonitorTracepointGetTracebufferRequest signal block");
         }
      }
      catch (IffException e)
      {
         System.err.println("Invalid MonitorTracepointGetTracebufferRequest signal block");
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore)
            {
            }
         }
      }
   }
   
   public Signal readSignal(SignalInputStream in) throws IffException, IOException
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
   
   
   private static Signal createSignal(int sender, int addressee, int sigSize,
         int sigNo, byte[] sigData, SignalRegistry registry, boolean bigEndian)
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
            throw new RuntimeException("Default constructor missing in "
                  + sigClass.getName());
         }
         catch (IllegalAccessException e)
         {
            // The signal class did not have a public default constructor.
            throw new RuntimeException("Default constructor not public in "
                  + sigClass.getName());
         }
         catch (ClassCastException e)
         {
            // The signal class did not extend Signal.
            throw new RuntimeException(sigClass.getName()
                  + " do not extend com.ose.gateway.Signal");
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

