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

public class BLHDChunk extends Chunk
{
   private static final int DUMP_ID_OFFSET = 8;
   private static final int BLOCK_NO_OFFSET = 12;
   private static final int SECONDS_OFFSET = 16;
   private static final int MICRO_SECONDS_OFFSET = 20;

   protected BLHDChunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public long getDumpId() throws IffException
   {
      return readU32(DUMP_ID_OFFSET);
   }

   public long getBlockNo() throws IffException
   {
      return readU32(BLOCK_NO_OFFSET);
   }

   public long getSeconds() throws IffException
   {
      return readU32(SECONDS_OFFSET);
   }

   public long getMicroSeconds() throws IffException
   {
      return readU32(MICRO_SECONDS_OFFSET);
   }

   protected void describeChunkData(PrintStream out, String indent)
      throws IffException
   {
      out.println(indent + "Dump id     : " + formatU32ToHex(getDumpId()));
      out.println(indent + "Block number: " + formatU32ToHex(getBlockNo()));
      out.println(indent + "Seconds     : " + formatU32ToHex(getSeconds()));
      out.println(indent + "Microseconds: " + formatU32ToHex(getMicroSeconds()));
   }
}
