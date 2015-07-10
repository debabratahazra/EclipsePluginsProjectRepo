package com.ose.pmd.dump;

import java.io.PrintStream;

public class ER64Chunk extends Chunk
{

   private static final int ERROR_CODE_OFFSET = 8;

   private static final int EXTRA_OFFSET = 16;

   private long userCalled;
   
   private long currentProcess;

   protected ER64Chunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public long getUserCalled() throws IffException
   {
      return userCalled;
   }

   public long getErrorCode() throws IffException
   {
      return readU64(ERROR_CODE_OFFSET);
   }

   public long getExtra() throws IffException
   {
      return readU64(EXTRA_OFFSET);
   }

   public long getCurrentProcess() throws IffException
   {
      return currentProcess;
   }

   public void setUserCalled(long userCalled)
   {
      this.userCalled = userCalled;
   }
   
   public void setCurrentProcess(long currentProcess)
   {
      this.currentProcess = currentProcess;
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
