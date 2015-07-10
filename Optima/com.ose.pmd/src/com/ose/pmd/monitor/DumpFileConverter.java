/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.pmd.monitor;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.MemoryBlock;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;
import com.ose.system.DumpFileWriter;

public class DumpFileConverter
{
   private static final int CHUNK_SIZE = 4096;

   /**
    * If needed, convert the given dump file to a compressed or decompressed
    * dump file.
    *
    * @param fromFile  the dump file to be converted.
    * @param toFile  the dump file resulting from the potential conversion.
    * @param compressed  true if the dump file should be compressed,
    * false if it should be decompressed.
    * @return true if a conversion was needed, false otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public boolean convert(File fromFile, File toFile, boolean compressed)
      throws IOException
   {
      DumpFile dumpFile = null;
      DumpFileWriter dumpFileWriter = null;
      boolean needsConversion = false;
      boolean success = false;

      if ((fromFile == null) || (toFile == null))
      {
         throw new IllegalArgumentException();
      }

      try
      {
         dumpFile = new DumpFile(fromFile);
         needsConversion = (isCompressed(dumpFile) != compressed);
         if (needsConversion)
         {
            dumpFileWriter = new DumpFileWriter(toFile,
                                                dumpFile.isBigEndian(),
                                                (int) dumpFile.getId());
            convertErrorBlocks(dumpFile, dumpFileWriter);
            convertTextBlocks(dumpFile, dumpFileWriter);
            convertMemoryBlocks(dumpFile, dumpFileWriter, compressed);
            convertSignalBlocks(dumpFile, dumpFileWriter, compressed);
            success = true;
         }
         return needsConversion;
      }
      finally
      {
         if (dumpFile != null)
         {
            dumpFile.close();
         }
         if (dumpFileWriter != null)
         {
            dumpFileWriter.close();
         }
         if (needsConversion && !success)
         {
            toFile.delete();
         }
      }
   }

   private static boolean isCompressed(DumpFile dumpFile) throws IOException
   {
      List blocks;

      blocks = dumpFile.getMemoryBlocks();
      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         MemoryBlock memoryBlock = (MemoryBlock) i.next();
         if (memoryBlock.isCompressed())
         {
            return true;
         }
      }
      blocks = dumpFile.getSignalBlocks();
      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock = (SignalBlock) i.next();
         if (signalBlock.isCompressed())
         {
            return true;
         }
      }
      return false;
   }

   private static void convertErrorBlocks(DumpFile dumpFile,
                                          DumpFileWriter dumpFileWriter)
      throws IOException
   {
      List errorBlocks = dumpFile.getErrorBlocks();
      for (Iterator i = errorBlocks.iterator(); i.hasNext();)
      {
         ErrorBlock errorBlock = (ErrorBlock) i.next();
         if (!errorBlock.is64Bit())
         {
            dumpFileWriter.writeErrorBlock((errorBlock.getUserCalled() != 0),
                  (int) errorBlock.getErrorCode(), (int) errorBlock.getExtra(),
                  (int) errorBlock.getCurrentProcess(),
                  errorBlock.getDescriptions()[0]);
         }
         else
         {
            dumpFileWriter.writeErrorBlock((errorBlock.getUserCalled() != 0),
                  errorBlock.getErrorCode(), errorBlock.getExtra(),
                  (int) errorBlock.getCurrentProcess(),
                  errorBlock.getDescriptions()[0]);
         }
      }
   }

   private static void convertTextBlocks(DumpFile dumpFile,
                                         DumpFileWriter dumpFileWriter)
      throws IOException
   {
      List textBlocks = dumpFile.getTextBlocks();
      for (Iterator i = textBlocks.iterator(); i.hasNext();)
      {
         TextBlock textBlock = (TextBlock) i.next();
         String[] descriptions = textBlock.getDescriptions();
         StringBuffer sb = new StringBuffer();
         for (int j = 0; j < descriptions.length; j++)
         {
            sb.append(descriptions[j]);
            sb.append('\n');
         }
         dumpFileWriter.writeTextBlock(sb.toString());
      }
   }

   private static void convertMemoryBlocks(DumpFile dumpFile,
                                           DumpFileWriter dumpFileWriter,
                                           boolean compressed)
      throws IOException
   {
      List memoryBlocks = dumpFile.getMemoryBlocks();
      for (Iterator i = memoryBlocks.iterator(); i.hasNext();)
      {
         MemoryBlock memoryBlock;
         DataInputStream in = null;

         memoryBlock = (MemoryBlock) i.next();
         if (!memoryBlock.is64Bit())
         {
            dumpFileWriter.startWritingMemoryBlock(
                  (int) memoryBlock.getStartAddress(),
                  memoryBlock.getDescriptions()[0], compressed);
         }
         else
         {
            dumpFileWriter.startWritingMemoryBlock(
                  memoryBlock.getStartAddress(),
                  memoryBlock.getDescriptions()[0], compressed);
         }
         try
         {
            long length = memoryBlock.getLength();
            in = new DataInputStream(memoryBlock.getInputStream());
            for (long j = 0, remaining = length; j < length;)
            {
               int chunkSize = ((remaining > CHUNK_SIZE) ?
                     CHUNK_SIZE : (int) remaining);
               byte[] buf = new byte[chunkSize];
               in.readFully(buf);
               dumpFileWriter.writeMemory(buf);
               j += chunkSize;
               remaining -= chunkSize;
            }
         }
         finally
         {
            if (in != null)
            {
               try
               {
                  in.close();
               } catch (IOException ignore) {}
            }
         }
         dumpFileWriter.endWritingMemoryBlock();
      }
   }

   private static void convertSignalBlocks(DumpFile dumpFile,
                                           DumpFileWriter dumpFileWriter,
                                           boolean compressed)
      throws IOException
   {
      List signalBlocks = dumpFile.getSignalBlocks();
      for (Iterator i = signalBlocks.iterator(); i.hasNext();)
      {
         SignalBlock signalBlock;
         DataInputStream in = null;

         signalBlock = (SignalBlock) i.next();
         dumpFileWriter.startWritingSignalBlock(
               (int) signalBlock.getRequestSigNo(),
               compressed);
         try
         {
            long length = signalBlock.getLength();
            in = new DataInputStream(signalBlock.getInputStream());
            for (long j = 0, remaining = length; j < length;)
            {
               int chunkSize = ((remaining > CHUNK_SIZE) ?
                     CHUNK_SIZE : (int) remaining);
               byte[] buf = new byte[chunkSize];
               in.readFully(buf);
               dumpFileWriter.writeSignalBlockData(buf);
               j += chunkSize;
               remaining -= chunkSize;
            }
         }
         finally
         {
            if (in != null)
            {
               try
               {
                  in.close();
               } catch (IOException ignore) {}
            }
         }
         dumpFileWriter.endWritingSignalBlock((int) signalBlock.getStatus());
      }
   }
}
