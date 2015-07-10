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

public class ZDEFChunk extends Chunk
{
   public static final int NONE = 0;
   public static final int DEFLATED = 1;

   private static final int COMPRESSION_OFFSET = 8;
   private static final int PARAMETERS_OFFSET = 12;
   private static final int NUM_PARAMETERS = 4;
   private static final int IMAGE_LENGTH_OFFSET = 28;

   protected ZDEFChunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public long getCompression() throws IffException
   {
      return readU32(COMPRESSION_OFFSET);
   }

   public long[] getParameters() throws IffException
   {
      long parameters[] = new long[NUM_PARAMETERS];

      for (int i = 0; i < NUM_PARAMETERS; i++)
      {
         parameters[i] = readU32(PARAMETERS_OFFSET + 4 * i);
      }
      return parameters;
   }

   public long getImageLength() throws IffException
   {
      return readU32(IMAGE_LENGTH_OFFSET);
   }

   protected void describeChunkData(PrintStream out, String indent)
         throws IffException
   {
      long compression = getCompression();
      long parameters[] = getParameters();
      long imageLength = getImageLength();

      out.println(indent + "Compression : " + formatU32ToHex(compression));
      for (int i = 0; i < NUM_PARAMETERS; i++)
      {
         out.println(indent + "Parameter " + i + " : " + formatU32ToHex(parameters[i]));
      }
      out.println(indent + "Image length: " + formatU32ToHex(imageLength));
   }
}
