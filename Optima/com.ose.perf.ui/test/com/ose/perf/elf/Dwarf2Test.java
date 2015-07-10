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

package com.ose.perf.elf;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.Test;

public class Dwarf2Test
{
   @Test
   public void testGetFileLineInfo()
   {
      String currentDirectory = System.getProperty("user.dir");
      String pathSeparator = System.getProperty("file.separator");
      String debugSectionPath = currentDirectory + pathSeparator
         + "testdata" + pathSeparator + "powerpc" + pathSeparator
         + "debug_line_section_ppc32.bin";
      RandomAccessFile file = null;

      try
      {
         file = new RandomAccessFile(debugSectionPath, "r");
      }
      catch (FileNotFoundException e)
      {
         fail("Debug line section file not found.");
      }

      FileChannel channel = file.getChannel();

      MappedByteBuffer byteBuffer = null;

      try
      {
         byteBuffer = channel.map(
               FileChannel.MapMode.READ_ONLY,
               0, file.length());
      }
      catch (IOException e1)
      {
         fail("Unable to map debug line section byte buffer.");
      }

      byteBuffer.order(ByteOrder.BIG_ENDIAN);
      Dwarf2 dwarf2 = new Dwarf2(false);

      assertTrue(matches(dwarf2, byteBuffer, 0x20c328L,
            "obj/rtose_debug/../../drivers/dda/dd_ser_16550.c", 1301));

      try
      {
         channel.close();
      }
      catch (IOException e1)
      {
         fail("Unable to close debug line section file channel.");
      }

      try
      {
         file.close();
      }
      catch (IOException e)
      {
         fail("Unable to close debug line section file.");
      }
   }

   @Test
   public void testGetFileLineInfo64Bit()
   {
      String currentDirectory = System.getProperty("user.dir");
      String pathSeparator = System.getProperty("file.separator");
      String debugSectionPath = currentDirectory + pathSeparator
         + "testdata" + pathSeparator + "mips" + pathSeparator
         + "debug_line_section_mips64.bin";
      RandomAccessFile file = null;

      try
      {
         file = new RandomAccessFile(debugSectionPath, "r");
      }
      catch (FileNotFoundException e)
      {
         fail("Debug line section file not found.");
      }

      FileChannel channel = file.getChannel();

      MappedByteBuffer byteBuffer = null;

      try
      {
         byteBuffer = channel.map(
               FileChannel.MapMode.READ_ONLY,
               0, file.length());
      }
      catch (IOException e1)
      {
         fail("Unable to map debug line section byte buffer.");
      }

      byteBuffer.order(ByteOrder.BIG_ENDIAN);
      Dwarf2 dwarf2 = new Dwarf2(true);

      assertTrue(matches(dwarf2, byteBuffer, 0x4082469cL,
            "/vobs/odo_tools/mips64netlogic/libraries/hal/nlm_hal_macros.h", 293));

      try
      {
         channel.close();
      }
      catch (IOException e1)
      {
         fail("Unable to close debug line section file channel.");
      }

      try
      {
         file.close();
      }
      catch (IOException e)
      {
         fail("Unable to close debug line section file.");
      }
   }

   private boolean matches(Dwarf2 dwarf2, MappedByteBuffer byteBuffer, long address,
         String path, long line)
   {
      FileLineInfo info = dwarf2.getFileLineInfo(byteBuffer, address);
      return info != null && info.getFullPath().equals(path) && (info.getLine() == line);
   }
}
