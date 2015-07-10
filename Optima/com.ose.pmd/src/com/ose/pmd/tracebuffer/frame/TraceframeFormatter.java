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

package com.ose.pmd.tracebuffer.frame;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.pmd.tracebuffer.TracebufferUtil;

public class TraceframeFormatter extends Signal
{

   public static final int SIG_NO = 40766;

   public int snapshotId; // U32
   public int pid; // U32
   public int core; // U32
   public long timeStamp; // U64
   public int errorCode; // U32
   public int eCodeExtra; // U32
   public int sigLeft; // U32
   public int eCodeExtraHi;// U32
   public int tracepointId;// U32
   public int dataLength; // U32
   public List<FrameBlock> frameBlocks;

   
   public TraceframeFormatter()
   {
      super(SIG_NO);
      frameBlocks = new LinkedList<FrameBlock>();
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int dataCursor = 0;
      snapshotId = in.readS32();
      pid = in.readS32();
      core = in.readS32();
      timeStamp = in.readS64();
      errorCode = in.readS32();
      eCodeExtra = in.readS32();
      sigLeft = in.readS32();
      eCodeExtraHi = in.readS32();
      in.readS16(); // Unused U16 reserved0
      in.readS16(); // Unused U16 reserved1
      tracepointId = in.readS32();
      dataLength = in.readS32();
      
      
      while (dataCursor < dataLength)
      {
         FrameBlock frame;
         byte blockType = in.readS8();
         dataCursor++;
         
         switch (blockType)
         {
            case 'R':
               frame = new RegistersFrameBlock();
               ((RegistersFrameBlock)frame).oseRegisters = in.readS8Array(TracebufferUtil.oseRegisters);
               dataCursor += TracebufferUtil.oseRegisters;
               frameBlocks.add(frame);
               break;
            case 'M':
               int memLength = 0;
               frame = new MemoryFrameBlock();
               ((MemoryFrameBlock)frame).address = in.readS8Array(8);
               ((MemoryFrameBlock)frame).length = in.readS8Array(2);
               if (in.isBigEndian())
               {
                  memLength = ( ( (int)(((MemoryFrameBlock)frame).length[0]) ) << 8) + ((MemoryFrameBlock)frame).length[1];
               }
               else
               {
                  memLength = ( ( (int)(((MemoryFrameBlock)frame).length[1]) ) << 8) + ((MemoryFrameBlock)frame).length[0];
               }
               
               ((MemoryFrameBlock)frame).data = new byte[memLength];               
               ((MemoryFrameBlock)frame).data = in.readS8Array(memLength);
               dataCursor += 8 + 2 + memLength;
               frameBlocks.add(frame);
               break;
            case 'V':
               frame = new VariableFrameBlock();
               ((VariableFrameBlock)frame).id = in.readS8Array(4);
               ((VariableFrameBlock)frame).value = in.readS8Array(8);
               dataCursor += 4 + 8;
               frameBlocks.add(frame);
               break;
            case 'E':
               frame = new ErrorFrameBlock();
               ((ErrorFrameBlock)frame).errorCode = in.readS8Array(4);
               ((ErrorFrameBlock)frame).eCodeExtra = in.readS8Array(4);
               dataCursor += 4 + 4;
               frameBlocks.add(frame);
               break;
            default:
               System.err.println("InvalidFrameBlockType");
               dataCursor = dataLength;
               break;
         }
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      // Not needed
   }
   
   public void prepareGdbRegisterBlocks(int regSize, int pcOffset)
   {
      Iterator<FrameBlock> frameBlocksIterator = frameBlocks.iterator();
      while (frameBlocksIterator.hasNext())
      {
         FrameBlock currentBlock = frameBlocksIterator.next();
         switch (currentBlock.frameType)
         {
            case 'R':
               RegistersFrameBlock registersBlock = (RegistersFrameBlock)currentBlock;
               registersBlock.gdbRegisters = new byte[regSize];
               
               // Add padding
               for (int i = 0; i < registersBlock.gdbRegisters.length; i++)
               {
                  registersBlock.gdbRegisters[i] = 0;
               }
               // Add general purpose registers (GPR r0..r31)
               for (int i = 0; i < 32 * 4; i++)
               {
                  registersBlock.gdbRegisters[i] = registersBlock.oseRegisters[i];
               }
               // Set the special purpose registers
               for (int i = 0; i < 4; i ++)
               {
                  registersBlock.gdbRegisters[pcOffset + i]      = registersBlock.oseRegisters[32 * 4 + 5 * 4 + i]; // pc
                  registersBlock.gdbRegisters[pcOffset + 4 + i]  = registersBlock.oseRegisters[32 * 4 + 6 * 4 + i]; // msr
                  registersBlock.gdbRegisters[pcOffset + 8 + i]  = registersBlock.oseRegisters[32 * 4 + 0 * 4 + i]; // cr
                  registersBlock.gdbRegisters[pcOffset + 12 + i] = registersBlock.oseRegisters[32 * 4 + 2 * 4 + i]; // lr
                  registersBlock.gdbRegisters[pcOffset + 16 + i] = registersBlock.oseRegisters[32 * 4 + 3 * 4 + i]; // ctr
                  registersBlock.gdbRegisters[pcOffset + 20 + i] = registersBlock.oseRegisters[32 * 4 + 1 * 4 + i]; // xer
               }
               
               // Update dataLength
               dataLength += (regSize - TracebufferUtil.oseRegisters);
               break;
            case 'M':
            case 'V':
            case 'E':
            default:
               break;
         }
      }
   }

   public int computeSizeInOseFramebuffer(boolean bigEndian)
   {
      // size of the Tp entry (one for each frame)
      // sizeof(Tp) + sizeof(TraceEntryHead
      int total_size = 24 + 32;
      Iterator<FrameBlock> frameBlocksIterator = frameBlocks.iterator();
      while(frameBlocksIterator.hasNext())
      {
         FrameBlock currentBlock = frameBlocksIterator.next();
         switch (currentBlock.frameType)
         {
            case 'R':
               // allign to 64 bits (sizeof(Collect) + sizeof (cpu_core)) and add sizeof(TraceEntryHead)
               total_size += 24 + (TracebufferUtil.oseRegisters + 7) - (TracebufferUtil.oseRegisters + 7) % 8 + 32;
               break;
            case 'M':
               // allign to 64 bits (sizeof(Collect) + saveMemory.length and add sizeof(TraceEntryHead)
               int memLength = 0;
               if (bigEndian)
               {
                  memLength = ( ( (int)(((MemoryFrameBlock)currentBlock).length[0]) ) << 8) + ((MemoryFrameBlock)currentBlock).length[1];
               }
               else
               {
                  memLength = ( ( (int)(((MemoryFrameBlock)currentBlock).length[1]) ) << 8) + ((MemoryFrameBlock)currentBlock).length[0];
               }
               total_size += 24 + (memLength + 7) - (memLength + 7) % 8 + 32;
               break;
            case 'V':
               // allign to 8 Bytes (sizeof(Collect) and add sizeof(TraceEntryHead)
               total_size += 24 + 32;
               break;
            case 'E':
               // allign to 64 bits (sizeof(Collect)) and add sizeof(TraceEntryHead)
               total_size += 24 + 32;
               break;
            default:
               break;
         }
      }
      
      return total_size;
   }
   
}
