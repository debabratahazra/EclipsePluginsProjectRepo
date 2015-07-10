/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.dump;

public abstract class AbstractBlock
{
   private final long dumpId;
   private final long blockNo;
   private final long seconds;
   private final long microSeconds;
   private final long size;

   protected AbstractBlock(BLHDChunk blhdChunk) throws IffException
   {
      this.dumpId = blhdChunk.getDumpId();
      this.blockNo = blhdChunk.getBlockNo();
      this.seconds = blhdChunk.getSeconds();
      this.microSeconds = blhdChunk.getMicroSeconds();
      this.size = blhdChunk.getSize();
   }

   protected AbstractBlock(long dumpId,
                           long blockNo,
                           long seconds,
                           long microSeconds,
                           long size)
   {
      this.dumpId = dumpId;
      this.blockNo = blockNo;
      this.seconds = seconds;
      this.microSeconds = microSeconds;
      this.size = size;
   }

   public long getDumpId()
   {
      return dumpId;
   }

   public long getBlockNo()
   {
      return blockNo;
   }

   public long getSeconds()
   {
      return seconds;
   }

   public long getMicroSeconds()
   {
      return microSeconds;
   }

   public long getSize()
   {
      return size;
   }
}
