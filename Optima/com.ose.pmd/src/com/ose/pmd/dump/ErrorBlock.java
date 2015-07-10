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

public class ErrorBlock extends AbstractBlock
{
   private final String[] descs;
   private final long userCalled;
   private final long errorCode;
   private final long extra;
   private final long currentProcess;
   private final long length;
   private final boolean is64BitFormat;

   protected ErrorBlock(BLHDChunk blhdChunk, String[] descs, ERINChunk erinChunk)
         throws IffException
   {
      super(blhdChunk);
      this.is64BitFormat = false;
      this.descs = descs;
      this.userCalled = erinChunk.getUserCalled();
      this.errorCode = erinChunk.getErrorCode();
      this.extra = erinChunk.getExtra();
      this.currentProcess = erinChunk.getCurrentProcess();
      int len = 0;
      for (int i = 0; i < descs.length; i++)
      {
         len += descs[i].length();
      }
      len += erinChunk.getDataSize();
      this.length = len;
   }

   protected ErrorBlock(BLHDChunk blhdChunk, String[] descs, ER64Chunk er64Chunk)
         throws IffException 
   {
      super(blhdChunk);
      this.is64BitFormat = true;
      this.descs = descs;
      this.userCalled = er64Chunk.getUserCalled();
      this.errorCode = er64Chunk.getErrorCode();
      this.extra = er64Chunk.getExtra();
      this.currentProcess = er64Chunk.getCurrentProcess();
      int len = 0;
      for (int i = 0; i < descs.length; i++) 
      {
         len += descs[i].length();
      }
      len += er64Chunk.getDataSize();
      this.length = len;
   }

   public String[] getDescriptions()
   {
      return descs;
   }

   public long getUserCalled()
   {
      return userCalled;
   }

   public long getErrorCode()
   {
      return errorCode;
   }

   public long getExtra()
   {
      return extra;
   }

   public long getCurrentProcess()
   {
      return currentProcess;
   }

   public long getLength()
   {
      return length;
   }
   
   public boolean is64Bit()
   {
	   return is64BitFormat;
   }
}
