/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.debug;

import java.io.IOException;
import java.math.BigInteger;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IMemoryBlockExtension;
import org.eclipse.debug.core.model.IMemoryBlockRetrieval;
import org.eclipse.debug.core.model.IMemoryBlockRetrievalExtension;
import org.eclipse.debug.core.model.MemoryByte;
import com.ose.system.ServiceException;
import com.ose.system.Target;

public class OseMemoryBlock extends DebugElement implements IMemoryBlockExtension
{
   private final OseDebugTarget debugTarget;
   private final int pid;
   private final long baseAddress;
   private final long length;
   private final String expression;

   protected OseMemoryBlock(OseDebugTarget debugTarget, int pid, long baseAddress)
   {
      this(debugTarget, pid, baseAddress, 0);
   }

   protected OseMemoryBlock(OseDebugTarget debugTarget,
                            int pid,
                            long baseAddress,
                            long length)
   {
      super(debugTarget);
      this.debugTarget = debugTarget;
      this.pid = pid;
      // If needed, 4-byte align the base address downwards
      // and increase the length correspondingly.
      this.baseAddress = baseAddress & ~3;
      this.length = (length != 0) ? length + (baseAddress & 3) : 0;
      expression = "0x" + Integer.toHexString(pid).toUpperCase() + ":" +
                   "0x" + Long.toHexString(this.baseAddress).toUpperCase();
   }

   public String getExpression()
   {
      return expression;
   }

   public BigInteger getBigBaseAddress() throws DebugException
   {
      return BigInteger.valueOf(baseAddress);
   }

   public BigInteger getMemoryBlockStartAddress() throws DebugException
   {
      return ((length != 0) ? BigInteger.valueOf(baseAddress) : null);
   }

   public BigInteger getMemoryBlockEndAddress() throws DebugException
   {
      return ((length != 0) ? BigInteger.valueOf(baseAddress + length - 1) : null);
   }

   public BigInteger getBigLength() throws DebugException
   {
      return BigInteger.valueOf((length != 0) ? length : -1L);
   }

   public int getAddressSize() throws DebugException
   {
      return 4;
   }

   public boolean supportBaseAddressModification() throws DebugException
   {
      return false;
   }

   public boolean supportsChangeManagement()
   {
      return false;
   }

   public void setBaseAddress(BigInteger address) throws DebugException
   {
   }

   public MemoryByte[] getBytesFromOffset(BigInteger unitOffset,
                                          long addressableUnits)
         throws DebugException
   {
      return getBytesFromAddress(
            BigInteger.valueOf(baseAddress).add(unitOffset), addressableUnits);
   }

   public MemoryByte[] getBytesFromAddress(BigInteger address, long units)
         throws DebugException
   {
      Target target;
      MemoryByte[] memory;
      long reqAddress;
      long reqLength;
      int numPreUnreadable = 0;

      target = debugTarget.getTarget();
      if (target.isKilled())
      {
         requestFailed("Target is disconnected", null);
      }

      memory = new MemoryByte[(int) units];
      reqAddress = address.longValue();
      reqLength = units;

      // For a bounded memory block we have to take special
      // care if bytes are requested outside of its bounds.
      if (length != 0)
      {
         if (reqAddress < baseAddress)
         {
            if ((reqAddress + reqLength) > baseAddress)
            {
               numPreUnreadable = (int) (baseAddress - reqAddress);
               reqAddress = baseAddress;
               reqLength -= numPreUnreadable;
               fillUnreadableBytes(memory, 0, numPreUnreadable);
            }
            else
            {
               return fillUnreadableBytes(memory, 0, memory.length);
            }
         }
         else if ((reqAddress + reqLength) > (baseAddress + length))
         {
            if (reqAddress < (baseAddress + length))
            {
               reqLength = baseAddress + length - reqAddress;
            }
            else
            {
               return fillUnreadableBytes(memory, 0, memory.length);
            }
         }
      }

      // For PMD targets, which typically have many small non-contiguous memory
      // chunks, we read memory defensively in chunks of four bytes.
      // For real targets, we try to read all requested bytes in one chunk to
      // get better performance.
      if (target.isPostMortemMonitor())
      {
         int reqStart = (int) reqAddress;
         int reqSize = (int) reqLength;
         int reqEnd = reqStart + reqSize;
         int numPostUnreadable;

         for (int i = reqStart, j = 0; i < reqEnd; i += 4, j += 4)
         {
            try
            {
               byte[] bytes =
                  target.getMemory(new NullProgressMonitor(), pid, i, 4);
               fillReadableBytes(
                  memory, numPreUnreadable + j, bytes, target.isBigEndian());
               if (bytes.length < 4)
               {
                  fillUnreadableBytes(
                     memory, numPreUnreadable + j + bytes.length, 4 - bytes.length);
               }
            }
            catch (ServiceException e)
            {
               fillUnreadableBytes(memory, numPreUnreadable + j, 4);
            }
            catch (IOException e)
            {
               requestFailed("Reading memory failed", e);
            }
         }
         numPostUnreadable = memory.length - numPreUnreadable - reqSize;
         if (numPostUnreadable > 0)
         {
            fillUnreadableBytes(
               memory, numPreUnreadable + reqSize, numPostUnreadable);
         }
      }
      else
      {
         try
         {
            byte[] bytes;
            int numPostUnreadable;

            bytes = target.getMemory(
               new NullProgressMonitor(), pid, (int) reqAddress, (int) reqLength);
            fillReadableBytes(memory, numPreUnreadable, bytes, target.isBigEndian());
            numPostUnreadable = memory.length - numPreUnreadable - bytes.length;
            if (numPostUnreadable > 0)
            {
               fillUnreadableBytes(
                  memory, numPreUnreadable + bytes.length, numPostUnreadable);
            }
         }
         catch (ServiceException e)
         {
            return fillUnreadableBytes(memory, 0, memory.length);
         }
         catch (IOException e)
         {
            requestFailed("Reading memory failed", e);
         }
      }

      return memory;
   }

   private static MemoryByte[] fillReadableBytes(MemoryByte[] memory,
                                                 int offset,
                                                 byte[] bytes,
                                                 boolean bigEndian)
   {
      byte flags;

      if ((memory == null) || (bytes == null))
      {
         throw new IllegalArgumentException();
      }
      if (offset < 0)
      {
         throw new ArrayIndexOutOfBoundsException(offset);
      }
      if ((offset + bytes.length) > memory.length)
      {
         throw new ArrayIndexOutOfBoundsException(offset + bytes.length);
      }

      flags = MemoryByte.READABLE | MemoryByte.ENDIANESS_KNOWN;
      if (bigEndian)
      {
         flags |= MemoryByte.BIG_ENDIAN;
      }

      for (int i = 0; i < bytes.length; i++)
      {
         memory[offset + i] = new MemoryByte(bytes[i], flags);
      }

      return memory;
   }

   private static MemoryByte[] fillUnreadableBytes(MemoryByte[] memory,
                                                   int offset,
                                                   int count)
   {
      if (memory == null)
      {
         throw new IllegalArgumentException();
      }
      if (offset < 0)
      {
         throw new ArrayIndexOutOfBoundsException(offset);
      }
      if (count < 0)
      {
         throw new ArrayIndexOutOfBoundsException(count);
      }
      if ((offset + count) > memory.length)
      {
         throw new ArrayIndexOutOfBoundsException(offset + count);
      }

      for (int i = offset; i < (offset + count); i++)
      {
         memory[i] = new MemoryByte((byte) 0, (byte) 0);
      }

      return memory;
   }

   public void setValue(BigInteger offset, byte[] bytes) throws DebugException
   {
   }

   public void connect(Object client)
   {
   }

   public void disconnect(Object client)
   {
   }

   public Object[] getConnections()
   {
      return new Object[0];
   }

   public void dispose() throws DebugException
   {
   }

   public IMemoryBlockRetrieval getMemoryBlockRetrieval()
   {
      return debugTarget;
   }

   public int getAddressableSize() throws DebugException
   {
      return 1;
   }

   public long getStartAddress()
   {
      return baseAddress;
   }

   public long getLength()
   {
      return length;
   }

   public byte[] getBytes() throws DebugException
   {
      Target target;
      byte[] bytes = null;

      target = debugTarget.getTarget();
      if (target.isKilled())
      {
         requestFailed("Target is disconnected", null);
      }

      try
      {
         bytes = target.getMemory(new NullProgressMonitor(),
                                  pid,
                                  (int) baseAddress,
                                  (int) length);
      }
      catch (IOException e)
      {
         requestFailed("Reading memory failed", e);
      }

      return bytes;
   }

   public boolean supportsValueModification()
   {
      return false;
   }

   public void setValue(long offset, byte[] bytes) throws DebugException
   {
   }

   public String getModelIdentifier()
   {
      return debugTarget.getModelIdentifier();
   }

   public Object getAdapter(Class adapter)
   {
      if (IMemoryBlockRetrievalExtension.class.equals(adapter))
      {
         return getDebugTarget();
      }
      else
      {
         return super.getAdapter(adapter);
      }
   }
}
