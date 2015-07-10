package com.ose.pmd.dump;

import java.io.PrintStream;

public class MHD2Chunk extends Chunk
{

   private static final int START_ADDRESS_OFFSET = 8;

   private static final int LENGTH_OFFSET = 16;

   protected MHD2Chunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public long getStartAddress() throws IffException
   {
      return readU64(START_ADDRESS_OFFSET);
   }

   public long getLength() throws IffException
   {
      return readU64(LENGTH_OFFSET);
   }

   protected void describeChunkData(PrintStream out, String indent)
         throws IffException
   {
      out.println(indent + "Start address: " + formatU32ToHex(getStartAddress()));
      out.println(indent + "Length       : " + formatU32ToHex(getLength()));
   }

}
