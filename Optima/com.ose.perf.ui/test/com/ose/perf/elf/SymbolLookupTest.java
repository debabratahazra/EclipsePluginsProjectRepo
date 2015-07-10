/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2012 by Enea Software AB.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import junit.framework.TestCase;

/**
 * This test class compares the symbol lookup output from the Optima ELF parser
 * and the Binutils ELF parser with the assumption that the Binutils ELF parser
 * can be trusted to be correct.
 *
 * The actual test looks up every nth address in the code section (".text") of
 * the ELF file being tested using the Optima ELF parser and the specified
 * Binutils addr2line ELF parser and compares the results.
 *
 * An explicit test for testing inline functions is also included.
 */
public class SymbolLookupTest extends TestCase
{
   private static final String FILE_SEP = System.getProperty("file.separator");

   private static final String HOST_LINUX = "linux";
   private static final String HOST_WIN32 = "win32";

   private static final String ARCH_ARM = "arm";
   private static final String ARCH_MIPS = "mips";
   private static final String ARCH_POWERPC = "powerpc";
   private static final String ARCH_X86 = "x86";

   private static final String ADDR2LINE_ARM = "arm-elf-addr2line";
   private static final String ADDR2LINE_MIPS = "mips-elf-addr2line";
   private static final String ADDR2LINE_POWERPC = "powerpc-eabi-addr2line";
   private static final String ADDR2LINE_X86 = "i386-elf-addr2line";

   private static final long ADDRESS_INCREMENT = 32;

   /*
    * The following constants are the addresses of where the inline function
    * add_fibonacci_numbers() in fibonacci.h is inlined in the calling function
    * calc_fibonacci() in sc_prof_c.c in the sc_prof_debug.elf binary for each
    * architecture.
    * When the testdata/<arch>/sc_prof_debug.elf binaries are updated, these
    * constants need to be updated correspondingly; use the following command to
    * determine the new location of where add_fibonacci_numbers() is inlined:
    * <target>-objdump -S testdata/<arch>/sc_prof_debug.elf.
    */
   private static final long INLINE_ADDRESS_ARM_GCC = 0xD0118DD8L;
   private static final long INLINE_ADDRESS_ARM_RVCT = 0;
   private static final long INLINE_ADDRESS_MIPS = 0xA0915524L;
   private static final long INLINE_ADDRESS_POWERPC = 0x01019580L;
   private static final long INLINE_ADDRESS_POWERPC64 = 0;
   private static final long INLINE_ADDRESS_X86 = 0x0000BEACL;

   public SymbolLookupTest(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
   }

   protected void tearDown() throws Exception
   {
   }

   protected void test(String addr2line, File file) throws IOException
   {
      long lookups = 0;
      long skipped = 0;
      long diffs = 0;
      final boolean mips = addr2line.toLowerCase().contains("mips");
      String path = file.getAbsolutePath();
      ElfSectionHeader textSection = getTextSection(path);
      long textStart = textSection.getAddress();
      long textEnd = textStart + textSection.getSize();
      OptimaSymbolLookup optimaSymbolLookup = new OptimaSymbolLookup(path);
      BinutilsSymbolLookup binutilsSymbolLookup =
            new BinutilsSymbolLookup(addr2line, path);

      for (long i = textStart; UnsignedArithmetics.isLessThan(i, textEnd);
           i += ADDRESS_INCREMENT, lookups++)
      {
         SymbolInfo optima = optimaSymbolLookup.lookup(i);
         SymbolInfo binutils = binutilsSymbolLookup.lookup(i);
         if (mips && binutils.getSymbol().startsWith("$L"))
         {
            // Workaround for addr2line not ignoring compiler-generated
            // MIPS local labels, i.e. symbols whose name begin with $L.
            lookups--;
            skipped++;
            continue;
         }
         if (!optima.equals(binutils))
         {
            diffs++;
            System.err.println("Results differ for address 0x" + Long.toHexString(i));
            System.err.println("--------------------------------------------------");
            System.err.println("Optima results:");
            System.err.println(optima);
            System.err.println("--------------------------------------------------");
            System.err.println("Binutils results:");
            System.err.println(binutils);
            System.err.println();
         }
      }

      binutilsSymbolLookup.dispose();
      System.out.println("--------------------------------------------------");
      System.out.println("File: " + path);
      System.out.println("Text section: 0x" + Long.toHexString(textStart) +
            " to 0x" + Long.toHexString(textEnd) + " (" +
            (textSection.getSize() / 1024) + " kB)");
      System.out.println(lookups + " addresses looked up.");
      if (skipped > 0)
      {
         System.out.println(skipped + " address lookups skipped.");
      }
      System.out.println(diffs + " address lookups differed.");
      System.out.println();

      assertTrue(diffs + " of " + lookups + " address lookups differed", (diffs == 0));
   }

   protected void testInlineFunction(String addr2line, File file, long address)
      throws IOException
   {
      String path = file.getAbsolutePath();

      OptimaSymbolLookup optimaSymbolLookup =
            new OptimaSymbolLookup(path);
      BinutilsSymbolLookup binutilsSymbolLookup =
            new BinutilsSymbolLookup(addr2line, path);

      SymbolInfo optima = optimaSymbolLookup.lookup(address);
      SymbolInfo binutils = binutilsSymbolLookup.lookup(address);
      binutilsSymbolLookup.dispose();

      if (!optima.equals(binutils))
      {
         System.err.println("File: " + path);
         System.err.println("Inline function results differ for address 0x" +
            Long.toHexString(address));
         System.err.println("--------------------------------------------------");
         System.err.println("Optima results:");
         System.err.println(optima);
         System.err.println("--------------------------------------------------");
         System.err.println("Binutils results:");
         System.err.println(binutils);
         System.err.println();
      }

      assertTrue("Result differs between Optima and Binutils",
                 optima.equals(binutils));
   }

   public void test_sc_prof_arm_gcc()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_ARM);
         file = getTestFile(ARCH_ARM, "sc_prof_debug.elf");
         test(binutils, file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   // TODO: Implement when there is an ARM RVCT sc_prof binary available.
   public void test_sc_prof_arm_rvct()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_ARM);
         file = getTestFile(ARCH_ARM, "sc_prof_debug_rvct.elf");
         test(binutils, file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_sc_prof_mips()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_MIPS);
         file = getTestFile(ARCH_MIPS, "sc_prof_debug.elf");
         test(binutils, file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_sc_prof_powerpc()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_POWERPC);
         file = getTestFile(ARCH_POWERPC, "sc_prof_debug.elf");
         test(binutils, file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   // TODO: Implement when there is a 64-bit PowerPC sc_prof binary available.
   public void test_sc_prof_powerpc64()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_POWERPC);
         file = getTestFile(ARCH_POWERPC, "sc_prof_debug_powerpc64.elf");
         test(binutils, file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_sc_prof_x86()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_X86);
         file = getTestFile(ARCH_X86, "sc_prof_debug.elf");
         test(binutils, file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_inline_arm_gcc()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_ARM);
         file = getTestFile(ARCH_ARM, "sc_prof_debug.elf");
         testInlineFunction(binutils, file, INLINE_ADDRESS_ARM_GCC);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   // TODO: Implement when there is an ARM RVCT sc_prof binary available.
   public void test_inline_arm_rvct()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_ARM);
         file = getTestFile(ARCH_ARM, "sc_prof_debug_rvct.elf");
         testInlineFunction(binutils, file, INLINE_ADDRESS_ARM_RVCT);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_inline_mips()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_MIPS);
         file = getTestFile(ARCH_MIPS, "sc_prof_debug.elf");
         testInlineFunction(binutils, file, INLINE_ADDRESS_MIPS);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_inline_powerpc()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_POWERPC);
         file = getTestFile(ARCH_POWERPC, "sc_prof_debug.elf");
         testInlineFunction(binutils, file, INLINE_ADDRESS_POWERPC);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   // TODO: Implement when there is a 64-bit PowerPC sc_prof binary available.
   public void test_inline_powerpc64()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_POWERPC);
         file = getTestFile(ARCH_POWERPC, "sc_prof_debug_powerpc64.elf");
         testInlineFunction(binutils, file, INLINE_ADDRESS_POWERPC64);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   public void test_inline_x86()
   {
      File file = null;

      try
      {
         String binutils = getBinutilsCommand(ARCH_X86);
         file = getTestFile(ARCH_X86, "sc_prof_debug.elf");
         testInlineFunction(binutils, file, INLINE_ADDRESS_X86);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (file != null)
         {
            file.delete();
         }
      }
   }

   private static ElfSectionHeader getTextSection(String path) throws IOException
   {
      ElfFile elfFile = new ElfFile(path);
      elfFile.open();
      ElfSectionHeader textSection = elfFile.getSection(".text");
      elfFile.close();
      return textSection;
   }

   private static String getBinutilsCommand(String arch)
   {
      String cwd = System.getProperty("user.dir");
      String os = getHostOs();
      String path = cwd + FILE_SEP + "testcmds" + FILE_SEP + os + FILE_SEP;
      String ext = os.equals(HOST_WIN32) ? ".exe" : "";

      if (arch.equals(ARCH_ARM))
      {
         return path + ADDR2LINE_ARM + ext;
      }
      else if (arch.equals(ARCH_MIPS))
      {
         return path + ADDR2LINE_MIPS + ext;
      }
      else if (arch.equals(ARCH_POWERPC))
      {
         return path + ADDR2LINE_POWERPC + ext;
      }
      else if (arch.equals(ARCH_X86))
      {
         return path + ADDR2LINE_X86 + ext;
      }
      else
      {
         throw new RuntimeException("Unsupported target arch for Binutils");
      }
   }

   private static String getHostOs()
   {
      String os = System.getProperty("os.name").toLowerCase();

      if (os.contains("linux"))
      {
         return HOST_LINUX;
      }
      else if (os.contains("win"))
      {
         return HOST_WIN32;
      }
      else
      {
         throw new RuntimeException("Unsupported host OS for Binutils");
      }
   }

   /*
    * This method copies the specified ELF file in the plugin directory to a
    * temporary directory in the local file system and returns it.
    * The reason for this is that if the plugin resides in ClearCase, reading
    * the ELF file is a magnitude or more slower than if it is located outside
    * of ClearCase.
    */
   private static File getTestFile(String arch, String path) throws IOException
   {
      File file = createTestFile(arch, path);
      File tmpFile = createTemporyTestFile(arch, path);
      copyFile(file, tmpFile);
      return tmpFile;
   }

   private static File createTestFile(String arch, String path)
   {
      String cwd = System.getProperty("user.dir");
      return new File(cwd + FILE_SEP + "testdata" + FILE_SEP + arch + FILE_SEP + path);
   }

   private static File createTemporyTestFile(String arch, String path)
   {
      String tmpDir = System.getProperty("java.io.tmpdir");
      return new File(tmpDir + FILE_SEP + arch + "_" +
         System.currentTimeMillis() + "_" + path);
   }

   private static void copyFile(File sourceFile, File destFile)
         throws IOException
   {
      FileInputStream in = null;
      FileOutputStream out = null;

      if (!destFile.exists())
      {
         destFile.createNewFile();
      }

      try
      {
         in = new FileInputStream(sourceFile);
         out = new FileOutputStream(destFile);
         FileChannel source = in.getChannel();
         FileChannel destination = out.getChannel();
         source.transferTo(0, source.size(), destination);
      }
      finally
      {
         try
         {
            if (in != null)
            {
               in.close();
            }
         } catch (IOException e) {}
         try
         {
            if (out != null)
            {
               out.close();
            }
         } catch (IOException e) {}
      }
   }
}
