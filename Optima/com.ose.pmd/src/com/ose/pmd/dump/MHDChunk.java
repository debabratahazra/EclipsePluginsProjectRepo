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

public class MHDChunk extends Chunk
{
   private static final int START_ADDRESS_OFFSET = 8;
   private static final int LENGTH_OFFSET = 12;

   protected MHDChunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public long getStartAddress() throws IffException
   {
      return readU32(START_ADDRESS_OFFSET);
   }

   public long getLength() throws IffException
   {
      return readU32(LENGTH_OFFSET);
   }

   protected void describeChunkData(PrintStream out, String indent)
         throws IffException
   {
      out.println(indent + "Start address: " + formatU32ToHex(getStartAddress()));
      out.println(indent + "Length       : " + formatU32ToHex(getLength()));
   }
}
