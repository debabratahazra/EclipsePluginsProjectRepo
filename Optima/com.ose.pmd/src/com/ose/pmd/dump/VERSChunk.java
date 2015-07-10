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

public class VERSChunk extends Chunk
{
   private static final int VERSION_OFFSET = 8;

   protected VERSChunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public boolean isBigEndian() throws IffException
   {
      long version = readIffU32(VERSION_OFFSET);
      return ((version & 0xFFFFL) > 0);
   }

   public long getVersion() throws IffException
   {
      return readU32(VERSION_OFFSET);
   }

   protected void describeChunkData(PrintStream out, String indent)
         throws IffException
   {
      out.println(indent + "Version: " + formatU32ToHex(getVersion()));
   }
}
