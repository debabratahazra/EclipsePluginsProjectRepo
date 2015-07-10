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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.ose.pmd.tracebuffer.frame.FrameBlock;
import com.ose.pmd.tracebuffer.frame.RegistersFrameBlock;
import com.ose.pmd.tracebuffer.frame.MemoryFrameBlock;
import com.ose.pmd.tracebuffer.frame.VariableFrameBlock;
import com.ose.pmd.tracebuffer.frame.ErrorFrameBlock;
import com.ose.pmd.tracebuffer.frame.TraceframeFormatter;
import com.ose.system.service.pm.ProgramManager;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointActionData;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetActionsReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetStatusReply;
import com.ose.system.service.tracepoint.signal.MonitorTracepointGetVariablesReply;


/* Uses the ExperimentData to create a file in the default GDB file format */

public class TracebufferWriter {
   
   private boolean bigEndian;
   private String architecture = "";
   private int numRegs = 0;
   private int pcOffset = 0;
   private double framesFraction; // created/frames
   
   public TracebufferWriter()
   {
      super();
   }
   
   public TracebufferWriter(String architecture)
   {
      this.architecture = architecture;
   }
   
   public void setBigEndian(boolean bigEndian)
   {
      this.bigEndian = bigEndian;
   }

   public String getArchitecture()
   {
      return architecture;
   }

   public void setArchitecture(String architecture)
   {
      this.architecture = architecture;
   }

   public DataOutputStream generateHeaderAndTextSections(String output_file, ExperimentData experimentData)
   {
      System.out.println("Converting to the gdb file format");
      
      File outputFile = new File(output_file);
      DataOutputStream outputStream = null;
      
      try 
      {
         outputStream = new DataOutputStream(new FileOutputStream(outputFile));
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
      
      if (outputStream != null)
      {
         writeHeader(outputStream);
         writeTextSection(outputStream, experimentData);
      }
      
      
      return outputStream;
   }
   
   
   // Write the file header
   public void writeHeader(DataOutputStream out)
   {
      byte hex = 0x7f;
      try
      {
         out.write(hex);
         out.writeBytes(TracebufferUtil.header);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
   
   
   // Write the text section
   public void writeTextSection(DataOutputStream out, ExperimentData experimentData)
   {
      writeRegisterSetSize(out, experimentData.getCpuType());
      writeRelocations(out, experimentData.getSectionInfos());
      writeStatus(out, experimentData.getExperimentStatus(), experimentData.getLastFrameTracepointId(), experimentData.getStopErrorMessage());
      writeTraceVariables(out, experimentData.getTraceVariables());
      writeTracepointsData(out, experimentData.getTracepointData());
      // write end of section
      try
      {
         out.writeBytes("\n");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
            
   }
   
   
   // Write the registers entry
   public void writeRegisterSetSize(DataOutputStream out, int cpuType)
   {
      computeRegisterSetSize(cpuType);
      if (numRegs == 0)
      {
         System.err.println("Failed to compute the register set size");
      }
      try
      {
         out.writeBytes('R' + " ");
         out.writeBytes(Integer.toHexString(numRegs));
         out.writeBytes("\n");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public void computeRegisterSetSize(int cpuType)
   {
      // If user specified the architecture, convert to that.
      if (architecture.compareTo("E500") == 0)
      {
         numRegs = TracebufferUtil.numRegsE500;
         pcOffset = TracebufferUtil.pcOffsetE500;
      }
      else if (architecture.compareTo("E500MC") == 0)
      {
         numRegs = TracebufferUtil.numRegsE500MC;
         pcOffset = TracebufferUtil.pcOffsetE500MC;
      }
      else if (architecture.compareTo("E600") == 0)
      {
         numRegs = TracebufferUtil.numRegsE600;
         pcOffset = TracebufferUtil.pcOffsetE600;
      }
      
      // Else check the PVR value (cpuType)
      if (numRegs == 0)
      {
         if ((cpuType & 0xFFFF0000) == TracebufferUtil.POWERPC_E500V1 || 
             (cpuType & 0xFFFF0000) == TracebufferUtil.POWERPC_E500V2)
         {
            numRegs = TracebufferUtil.numRegsE500;
            pcOffset = TracebufferUtil.pcOffsetE500;
         }
         else if ((cpuType & 0xFFFF0000) == TracebufferUtil.POWERPC_E500MC)
         {
            numRegs = TracebufferUtil.numRegsE500MC;
            pcOffset = TracebufferUtil.pcOffsetE500MC;
         }
         else if ((cpuType & 0xFFFF0000) == TracebufferUtil.POWERPC_E600)
         {
            numRegs = TracebufferUtil.numRegsE600;
            pcOffset = TracebufferUtil.pcOffsetE600;
         }
      }
      // Else default to E500MC register set site
      if (numRegs == 0)
      {
         numRegs = TracebufferUtil.numRegsE500MC;
         pcOffset = TracebufferUtil.pcOffsetE500MC;
      }
   }

   
   // write the relocations entry
   public void writeRelocations(DataOutputStream out, PmLoadModuleSectionInfoReply sections)
   {
      try
      {
         out.writeBytes("relocation section");
         for (int i = 0; i < sections.sections.length; i++)
         {
            if ((sections.sections[i].section_options & ProgramManager.PM_SECTION_LOAD) == ProgramManager.PM_SECTION_LOAD)
            {
               out.writeBytes(" " + Integer.toHexString((int)sections.sections[i].section_base));
            }
         }
         out.writeBytes("\n");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
   
   public void writeStatus(DataOutputStream out, MonitorTracepointGetStatusReply experimentStatus, int lastTracepointId, String error_description)
   {
      try
      {
         int totalFrames = 0;
         int totalCreated = 0;
         int mostUsedCore = 0;
         int circular = 0;
         
         // running can only be 0.
         out.writeBytes("status 0;");
         switch (experimentStatus.traceStatus)
         {
            case TracebufferUtil.TRACEPOINT_RUNNING:
               out.writeBytes(TracebufferUtil.TRACEPOINT_RUNNING_GDB_STRING);
               break;
            case TracebufferUtil.TRACEPOINT_NOTRUN:
               out.writeBytes(TracebufferUtil.TRACEPOINT_NOTRUN_GDB_STRING);
               break;
            case TracebufferUtil.TRACEPOINT_STOP:
               out.writeBytes(TracebufferUtil.TRACEPOINT_STOP_GDB_STRING);
               break;
            case TracebufferUtil.TRACEPOINT_FULL:
               out.writeBytes(TracebufferUtil.TRACEPOINT_FULL_GDB_STRING);
               break;
            case TracebufferUtil.TRACEPOINT_PASSCOUNT:
               out.writeBytes(TracebufferUtil.TRACEPOINT_PASSCOUNT_GDB_STRING);
               break;
            case TracebufferUtil.TRACEPOINT_ERROR:
               if (error_description == null || error_description.isEmpty())
               {
                  System.err.println("Missing error description string message");
                  break;
               }
               out.writeBytes(TracebufferUtil.TRACEPOINT_ERROR_GDB_STRING);
               out.writeBytes(":" + bin2hex(error_description.getBytes()));
               break;
            default:
               System.err.println("Invalid experiment status");
         }
         out.writeBytes(":" + Integer.toHexString(lastTracepointId));
         
         // Calculate the status values
         for (int i = 0; i < experimentStatus.cores; i++)
         {
            totalFrames += experimentStatus.perCoreStatus[i].frames;
            totalCreated += experimentStatus.perCoreStatus[i].created;
            if (experimentStatus.perCoreStatus[i].free < experimentStatus.perCoreStatus[mostUsedCore].free)
            {
               mostUsedCore = i;
            }
         }
         if (experimentStatus.perCoreStatus[mostUsedCore].bufferType == TracebufferUtil.MONITOR_TRACEPOINT_BUFFER_CIRCULAR)
         {
            circular = 1;
         }
         
         framesFraction = totalCreated/totalFrames;
         
         out.writeBytes(";tframes:" + Integer.toHexString(totalFrames));
         out.writeBytes(";tcreated:" + Integer.toHexString(totalCreated));
         out.writeBytes(";tfree:" + Long.toHexString(experimentStatus.perCoreStatus[mostUsedCore].free));
         out.writeBytes(";tsize:" + Long.toHexString(experimentStatus.perCoreStatus[mostUsedCore].size));
         out.writeBytes(";circular:" + Integer.toHexString(circular) + "\n");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
   
   public void writeTraceVariables(DataOutputStream out, List<MonitorTracepointGetVariablesReply> traceVariables)
   {
      try
      {
         MonitorTracepointGetVariablesReply traceVariable;
         Iterator<MonitorTracepointGetVariablesReply> traceVariablesIterator = traceVariables.iterator();
         while(traceVariablesIterator.hasNext())
         {
            traceVariable = traceVariablesIterator.next();
            out.writeBytes("tsv " + Integer.toHexString(traceVariable.id) + ":");
            // TODO -> double check, GDB uses here the phex_nz call
            out.writeBytes(Long.toHexString(traceVariable.initialValue) + ":");
            // built in variable field is always 0 for OSE
            out.writeBytes(Integer.toHexString(0) + ":");
            // Default variable names
            out.writeBytes(bin2hex((TracebufferUtil.TRACE_STATE_VARIABLES_DEFAULT_PREFIX + traceVariable.id).getBytes()) + "\n");
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
   
   public void writeTracepointsData(DataOutputStream out, List<TracepointData> tracepointData)
   {
      try
      {
         TracepointData tpData;
         Iterator<TracepointData> tracepointDataIterator = tracepointData.iterator();
         while(tracepointDataIterator.hasNext())
         {
            tpData = tracepointDataIterator.next();
            // Write the tp T entry
            out.writeBytes("tp T" + Integer.toHexString(tpData.tracepoint.id) + ":");
            
            out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":");
            
            if (tpData.tracepoint.enabled != 0)
            {
               out.writeBytes(TracebufferUtil.TRACEPOINT_ENABLED + ":");
            }
            else
            {
               out.writeBytes(TracebufferUtil.TRACEPOINT_DISABLED + ":");
            }
            
            // The step count of the while-stepping action.
            out.writeBytes(Integer.toHexString(tpData.getStepCount()) + ":");
            
            out.writeBytes(Integer.toHexString(tpData.tracepoint.passCount));
            
            if (tpData.tracepoint.tracepointType == TracebufferUtil.MONITOR_TRACEPOINT_FAST_TRACEPOINT)
            {
               out.writeBytes(":F" + Integer.toHexString(TracebufferUtil.FAST_TRACEPOINT_DISPLACEMENT));
            }
            
            if (tpData.tracepoint.byteCodeLength != 0)
            {
               // TODO -> need to doublecheck length and format
               out.writeBytes(":X" + Integer.toHexString((bin2hex(tpData.tracepoint.byteCode).length() + 1) / 2) + 
                              "," + bin2hex(tpData.tracepoint.byteCode));
            }
            out.writeBytes("\n");
            
            // Write the tp A entires
            MonitorTracepointGetActionsReply action;
            Iterator<MonitorTracepointGetActionsReply> tracepointActionsIterator = tpData.tracepointActions.iterator();
            while (tracepointActionsIterator.hasNext())
            {
               action = tracepointActionsIterator.next();
               
               // while-stepping action is not written, there is a default one
               if (action.actionType != MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_WHILE_STEPPING && 
                   action.whileSteppingId != tpData.getWhileSteppingId())
               {
                  out.writeBytes("tp A" + Integer.toHexString(tpData.tracepoint.id) + ":");
                  out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":");
                  // action in GDB string/packet form, depending on action type
                  switch(action.actionType)
                  {
                     case MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_SAVE_MEMORY:
                        out.writeBytes("M");
                        if (action.actionData.memory.isRegister == 1)
                        {
                           out.writeBytes("-" + Long.toHexString(action.actionData.memory.base) + Long.toHexString(TracebufferUtil.MEMORY_IS_REGISTER_DEFAULT));
                        }
                        else
                        {
                           out.writeBytes(Long.toHexString(TracebufferUtil.MEMORY_IS_NOT_REGISTER_DEFAULT) + Long.toHexString(action.actionData.memory.base));
                        }
                        out.writeBytes(Integer.toHexString(action.actionData.memory.length) + "\n");
                        break;
                        
                     case MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_SAVE_REGISTER:
                        // TODO -> there seem to be other values then those specified, need to check gdbstub code
                        out.writeBytes("R");
                        if (numRegs == TracebufferUtil.numRegsE500)
                        {
                           out.writeBytes(TracebufferUtil.REGISTER_MASK_E500);
                        }
                        else if (numRegs == TracebufferUtil.numRegsE500MC)
                        {
                           out.writeBytes(TracebufferUtil.REGISTER_MASK_E500MC);
                        }
                        else if (numRegs == TracebufferUtil.numRegsE600)
                        {
                           out.writeBytes(TracebufferUtil.REGISTER_MASK_E600);
                        }
                        else
                        {
                           System.err.println("Invalid register set size");
                        }
                        out.writeBytes("\n");
                        break;
                        
                     case MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_EXPRESSION:
                        
                        out.writeBytes("X");
                        for (int i = 0; i < 8 - (Integer.toHexString(action.actionData.expression.byteCodeLength)).length(); i++)
                        {
                           out.writeBytes("0");
                        }
                        out.writeBytes(Integer.toHexString(action.actionData.expression.byteCodeLength).toUpperCase());
                        out.writeBytes("," + bin2hex(action.actionData.expression.byteCode));
                        out.writeBytes("\n");
                        break;
                  }
               }
            }
            
                     
            // Write the tp S entires - note that GDB (version_080 is bugged here)
            Iterator<MonitorTracepointGetActionsReply> tracepointWhileSteppingActionsIterator = tpData.tracepointActions.iterator();
            while (tracepointWhileSteppingActionsIterator.hasNext())
            {
               action = tracepointWhileSteppingActionsIterator.next();
               
               // while-stepping action is not written, there is a default one
               if (action.actionType != MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_WHILE_STEPPING && 
                   action.whileSteppingId == tpData.getWhileSteppingId())
               {
                  out.writeBytes("tp S" + Integer.toHexString(tpData.tracepoint.id) + ":");
                  out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":");
                  // action in GDB string/packet form, depending on action type
                  switch(action.actionType)
                  {
                     case MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_SAVE_MEMORY:
                        out.writeBytes("M");
                        if (action.actionData.memory.isRegister == 1)
                        {
                           out.writeBytes("-" + Long.toHexString(action.actionData.memory.base) + Long.toHexString(TracebufferUtil.MEMORY_IS_REGISTER_DEFAULT));
                        }
                        else
                        {
                           out.writeBytes(Long.toHexString(TracebufferUtil.MEMORY_IS_NOT_REGISTER_DEFAULT) + Long.toHexString(action.actionData.memory.base));
                        }
                        out.writeBytes(Integer.toHexString(action.actionData.memory.length) + "\n");
                        break;
                        
                     case MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_SAVE_REGISTER:
                        // We dont use register masks in stub and OSE, always use all registers mask.
                        // TODO -> identify all registers masks for E500MC and E600
                        out.writeBytes("R");
                        if (numRegs == TracebufferUtil.numRegsE500)
                        {
                           out.writeBytes(TracebufferUtil.REGISTER_MASK_E500);
                        }
                        else if (numRegs == TracebufferUtil.numRegsE500MC)
                        {
                           out.writeBytes(TracebufferUtil.REGISTER_MASK_E500MC);
                        }
                        else if (numRegs == TracebufferUtil.numRegsE600)
                        {
                           out.writeBytes(TracebufferUtil.REGISTER_MASK_E600);
                        }
                        else
                        {
                           System.err.println("Invalid register set size");
                        }
                        out.writeBytes("\n");
                        break;
                        
                     case MonitorTracepointActionData.MONITOR_TRACEPOINT_ACTION_EXPRESSION:  
                        out.writeBytes("X");
                        for (int i = 0; i < 8 - (Integer.toHexString(action.actionData.expression.byteCodeLength)).length(); i++)
                        {
                           out.writeBytes("0");
                        }
                        out.writeBytes(Integer.toHexString(action.actionData.expression.byteCodeLength).toUpperCase());
                        out.writeBytes("," + bin2hex(action.actionData.expression.byteCode));
                        out.writeBytes("\n");
                        break;
                  }
               }
            }
            
            // Write the tp Z entries
            int cmd_index = 1;
            
            out.writeBytes("tp Z" + Integer.toHexString(tpData.tracepoint.id) + ":");
            out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":at:0:");
            out.writeBytes(Integer.toHexString((tpData.tracepointAnnotations.annotations[0]).length()) + ":");
            out.writeBytes(bin2hex(tpData.tracepointAnnotations.annotations[0].getBytes()) + "\n");
            
            if (tpData.tracepoint.byteCodeLength != 0)
            {
               cmd_index++;
               out.writeBytes("tp Z" + Integer.toHexString(tpData.tracepoint.id) + ":");
               out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":cond:0:");
               out.writeBytes(Integer.toHexString((tpData.tracepointAnnotations.annotations[1]).length()) + ":");
               out.writeBytes(bin2hex(tpData.tracepointAnnotations.annotations[1].getBytes()) + "\n");
            }
            
            for (; cmd_index < tpData.tracepointAnnotations.annotations.length; cmd_index++)
            {
               out.writeBytes("tp Z" + Integer.toHexString(tpData.tracepoint.id) + ":");
               out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":cmd:0:");
               out.writeBytes(Integer.toHexString((tpData.tracepointAnnotations.annotations[cmd_index]).length()) + ":");
               out.writeBytes(bin2hex(tpData.tracepointAnnotations.annotations[cmd_index].getBytes()) + "\n");               
            }
            
            // Write the tp V entry
            out.writeBytes("tp V" + Integer.toHexString(tpData.tracepoint.id) + ":");
            out.writeBytes(Long.toHexString(tpData.tracepoint.address) + ":");
            // Aproximate the number of tracepoint hits (existing frames for the TP * created / frames)
            out.writeBytes(Integer.toHexString((int)(tpData.hitCount * framesFraction)) + ":");
            out.writeBytes(Integer.toHexString(tpData.usedBuffer) + "\n");
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
   
   
   public void writeTracebufferEntrie(DataOutputStream out, TraceframeFormatter frame)
   {
      frame.prepareGdbRegisterBlocks(this.numRegs, this.pcOffset);
      
      
      Iterator<FrameBlock> frameBlocksIterator = frame.frameBlocks.iterator();
      
      // write the entries as raw binary, header and all frame blocks for each tracebuffer entry
      
      // header
      byte[] tpNumber = new byte[2];
      byte[] frameSize = new byte[4];
      if (bigEndian)
      {
         tpNumber[0] = (byte)((frame.tracepointId & 0x0000FF00) >> 8);
         tpNumber[1] = (byte)(frame.tracepointId & 0x000000FF);
         frameSize[0] = (byte)((frame.dataLength & 0xFF000000) >> 32);
         frameSize[1] = (byte)((frame.dataLength & 0x00FF0000) >> 16);
         frameSize[2] = (byte)((frame.dataLength & 0x0000FF00) >> 8);
         frameSize[3] = (byte)(frame.dataLength & 0x000000FF);
      }
      else
      {
         tpNumber[1] = (byte)((frame.tracepointId & 0x0000FF00) >> 8);
         tpNumber[0] = (byte)(frame.tracepointId & 0x000000FF);
         frameSize[3] = (byte)((frame.dataLength & 0xFF000000) >> 32);
         frameSize[2] = (byte)((frame.dataLength & 0x00FF0000) >> 16);
         frameSize[1] = (byte)((frame.dataLength & 0x0000FF00) >> 8);
         frameSize[0] = (byte)(frame.dataLength & 0x000000FF);               
      }
      
      try
      {
         out.write(tpNumber);
         out.write(frameSize);
         
         // frame blocks
         while (frameBlocksIterator.hasNext())
         {
            FrameBlock frameBlock = frameBlocksIterator.next();
            
            out.write(frameBlock.getFrameType());
            switch (frameBlock.getFrameType())
            {
               case 'R':
                  out.write(((RegistersFrameBlock)frameBlock).getGdbRegisters());
                  break;
               case 'M':
                  out.write(((MemoryFrameBlock)frameBlock).getAddress());
                  out.write(((MemoryFrameBlock)frameBlock).getLength());
                  out.write(((MemoryFrameBlock)frameBlock).getData());
                  break;
               case 'V':
                  out.write(((VariableFrameBlock)frameBlock).getId());
                  out.write(((VariableFrameBlock)frameBlock).getValue());
                  break;
               case 'E':
                  out.write(((ErrorFrameBlock)frameBlock).getErrorCode());
                  out.write(((ErrorFrameBlock)frameBlock).geteCodeExtra());
                  break;
               default:
                  break;
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
   
   public void writeEndOfData(DataOutputStream out)
   {
      try
      {
         // mark end of trace data
         out.writeInt(0);
         out.flush();
         out.close();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
         
   private String bin2hex(byte[] bytes)
   {
      /*
       * StringBuilder str = new StringBuilder(); for(int i = 0; i < ba.length;
       * i++) str.append(String.format("%x", ba[i])); return str.toString();
       */

      final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f' };
      char[] hexChars = new char[bytes.length * 2];
      int v;
      for (int j = 0; j < bytes.length; j++)
      {
         v = bytes[j] & 0xFF;
         hexChars[j * 2] = hexArray[v >>> 4];
         hexChars[j * 2 + 1] = hexArray[v & 0x0F];
      }
      return new String(hexChars);
   }

   
}
