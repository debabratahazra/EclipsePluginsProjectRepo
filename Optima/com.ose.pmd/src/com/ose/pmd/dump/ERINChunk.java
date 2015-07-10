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

import java.io.PrintStream;

public class ERINChunk extends Chunk
{
   private static final int USER_CALLED_OFFSET = 8;

   private static final int ERROR_CODE_OFFSET = 12;

   private static final int EXTRA_OFFSET = 16;

   private static final int CURRENT_PROCESS_OFFSET = 20;

   protected ERINChunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public long getUserCalled() throws IffException
   {
      return readU32(USER_CALLED_OFFSET);
   }

   public long getErrorCode() throws IffException
   {
      return readU32(ERROR_CODE_OFFSET);
   }

   public long getExtra() throws IffException
   {
      return readU32(EXTRA_OFFSET);
   }

   public long getCurrentProcess() throws IffException
   {
      return readU32(CURRENT_PROCESS_OFFSET);
   }

   protected void describeChunkData(PrintStream out, String indent)
         throws IffException
   {
      out.println(indent + "User called    : " + formatU32ToHex(getUserCalled()));
      out.println(indent + "Error code     : " + formatU32ToHex(getErrorCode()));
      out.println(indent + "Extra          : " + formatU32ToHex(getExtra()));
      out.println(indent + "Current process: " + formatU32ToHex(getCurrentProcess()));
   }
}
