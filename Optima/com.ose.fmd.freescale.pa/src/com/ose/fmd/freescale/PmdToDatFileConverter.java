/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.fmd.freescale;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.MemoryBlock;

public class PmdToDatFileConverter
{
   private static final String NEXUS_HARDWARE_TRACE = "NEXUS HARDWARE TRACE";

   public static boolean hasHardwareTrace(File pmdFile)
   {
      DumpFile dumpFile = null;

      if (pmdFile == null)
      {
         throw new IllegalArgumentException();
      }

      try
      {
         List memoryBlocks;
         boolean hardwareTrace = false;

         dumpFile = new DumpFile(pmdFile);
         memoryBlocks = dumpFile.getMemoryBlocks();

         for (int i = memoryBlocks.size() - 1; i >= 0; i--)
         {
            MemoryBlock memoryBlock = (MemoryBlock) memoryBlocks.get(i);
            if (memoryBlock.getDescriptions()[0].equals(NEXUS_HARDWARE_TRACE))
            {
               hardwareTrace = true;
               break;
            }
         }

         return hardwareTrace;
      }
      catch (IOException e)
      {
         return false;
      }
      finally
      {
         if (dumpFile != null)
         {
            dumpFile.close();
         }
      }
   }

   /*
    * This method takes as input a PMD file and a DAT file and extracts the
    * memory block with the description 'NEXUS HARDWARE TRACE' from the PMD file
    * and writes the contents of that memory block to the given DAT file.
    * The generated DAT file can later be imported in CodeWarrior along with
    * the corresponding ELF file as input.
    */
   public void convert(File pmdFile, File datFile) throws IOException
   {
      DumpFile dumpFile = null;

      if ((pmdFile == null) || (datFile == null))
      {
         throw new IllegalArgumentException();
      }

      try
      {
         List memoryBlocks;
         MemoryBlock traceMemoryBlock = null;
         InputStream in = null;
         OutputStream out = null;

         dumpFile = new DumpFile(pmdFile);
         memoryBlocks = dumpFile.getMemoryBlocks();

         for (int i = memoryBlocks.size() - 1; i >= 0; i--)
         {
            MemoryBlock memoryBlock = (MemoryBlock) memoryBlocks.get(i);
            if (memoryBlock.getDescriptions()[0].equals(NEXUS_HARDWARE_TRACE))
            {
               traceMemoryBlock = memoryBlock;
               break;
            }
         }

         if (traceMemoryBlock == null)
         {
            throw new IOException("No '" + NEXUS_HARDWARE_TRACE +
                  "' memory block in PMD file " + pmdFile.getName());
         }

         try
         {
            byte[] buffer = new byte[4096];
            int length;

            in = traceMemoryBlock.getInputStream();
            out = new BufferedOutputStream(new FileOutputStream(datFile));
            while ((length = in.read(buffer)) != -1)
            {
               out.write(buffer, 0, length);
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
            if (out != null)
            {
               try
               {
                  out.close();
               } catch (IOException ignore) {}
            }
         }
      }
      finally
      {
         if (dumpFile != null)
         {
            dumpFile.close();
         }
      }
   }
}
