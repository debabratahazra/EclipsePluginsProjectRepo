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

package com.ose.pmd.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import com.ose.pmd.dump.AbstractBlock;
import com.ose.pmd.dump.Chunk;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.dump.MemoryBlock;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;

/**
 * This class is a read-only CLI dump editor.
 * <p>
 * This class is not intended to be sub-classed.
 */
public class DumpEditor
{
   private static final String TYPE_ERROR_BLOCK = "Error";
   private static final String TYPE_TEXT_BLOCK = "Text";
   private static final String TYPE_MEMORY_BLOCK = "Memory";
   private static final String TYPE_SIGNAL_BLOCK = "Signal";

   private static final String LABEL_DUMP_ID        = "Dump ID   : ";
   private static final String LABEL_DUMP_FILE      = "Dump File : ";
   private static final String LABEL_DUMP_SIZE      = "Size      : ";
   private static final String LABEL_DUMP_VERSION   = "Version   : ";
   private static final String LABEL_DUMP_ENDIAN    = "Byte Order: ";
   private static final String LABEL_DUMP_TIMESTAMP = "Timestamp : ";

   private static final String LABEL_BLOCK_NUMBER       = "Block Number   : ";
   private static final String LABEL_BLOCK_TYPE         = "Block Type     : ";
   private static final String LABEL_BLOCK_TIMESTAMP    = "Timestamp      : ";
   private static final String LABEL_BLOCK_SIZE         = "Size           : ";
   private static final String LABEL_BLOCK_SIZE_IN_FILE = "Size in File   : ";
   private static final String LABEL_BLOCK_DESCRIPTION  = "Description    : ";
   private static final String LABEL_BLOCK_PROCESS      = "Process        : ";
   private static final String LABEL_BLOCK_DETECTOR     = "Detected by    : ";
   private static final String LABEL_BLOCK_ERROR        = "Error Code     : ";
   private static final String LABEL_BLOCK_EXTRA        = "Extra Parameter: ";
   private static final String LABEL_BLOCK_ERROR_MSG    = "Error Message  : ";
   private static final String LABEL_BLOCK_ADDRESS      = "Address        : ";
   private static final String LABEL_BLOCK_SIGNAME      = "Signal Name    : ";
   private static final String LABEL_BLOCK_SIGNO        = "Signal Number  : ";
   private static final String LABEL_BLOCK_STATUS       = "Status         : ";
   private static final String LABEL_BLOCK_INDENT       = "                 ";

   private static final int LABEL_PRINT_WIDTH = 60;

   private static final String COLUMN_HEADERS =
      "Number  Type    Status     Address     Size    Size in File Description";
   private static final String COLUMN_SEPARATOR = " ";

   private static final int COLUMN_BLOCK_NUMBER_LENGTH = 6;
   private static final int COLUMN_BLOCK_TYPE_LENGTH = 6;
   private static final int COLUMN_STATUS_LENGTH = 10;
   private static final int COLUMN_ADDRESS_LENGTH = 10;
   private static final int COLUMN_SIZE_LENGTH = 10;
   private static final int COLUMN_SIZE_IN_FILE_LENGTH = 12;
   private static final int COLUMN_DESCRIPTION_LENGTH = 25;

   private static final int TABLE_PRINT_WIDTH = COLUMN_BLOCK_NUMBER_LENGTH +
                                                COLUMN_BLOCK_TYPE_LENGTH +
                                                COLUMN_STATUS_LENGTH +
                                                COLUMN_ADDRESS_LENGTH +
                                                COLUMN_SIZE_LENGTH +
                                                COLUMN_SIZE_IN_FILE_LENGTH +
                                                COLUMN_DESCRIPTION_LENGTH +
                                                6;

   private static final String HELP_MSG =
      "Usage: com.ose.pmd.editor.DumpEditor [[[-p] [-l] [-d]] | [[-h] | [-b <no>] |\n" +
      "       [-t e|t|m|s] | [-m <addr1> <addr2>] | [-s <signo>]]] [-x]\n" +
      "       [-f <file>] [-fm <file>] [-fs <file>] <file>\n" +
      "Options:\n" +
      "    -help               print help message.\n" +
      "    -p                  print dump overview.\n" +
      "    -l                  list all blocks.\n" +
      "    -d                  print all blocks with content.\n" +
      "    -h                  print hierarchical dump overview.\n" +
      "    -b <no>             print block number <no> with content.\n" +
      "    -t e|t|m|s          list blocks of a certain type;\n" +
      "                        e (error), t (text), m (memory), or s (signal).\n" +
      "    -m <addr1> <addr2>  list memory blocks between address <addr1> and <addr2>.\n" +
      "    -s <signo>          list signal blocks with signal number <signo>.\n" +
      "    -x                  use hex formatter as default when printing signal blocks.\n" +
      "    -f <file>           deprecated, use -fm <file> instead.\n" +
      "    -fm <file>          path of the memory block formatters properties file.\n" +
      "    -fs <file>          path of the signal block formatters properties file.\n" +
      "    <file>              path of the dump file.";

   private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();

   private final PrintWriter out;
   private final PrintWriter err;
   private final List blocks;
   private final Properties memoryBlockFormatters;
   private final Properties signalBlockFormatters;
   private boolean bigEndian;
   private boolean useHexAsDefaultForSignalBlocks;

   DumpEditor(PrintStream out, PrintStream err)
   {
      if ((out == null) || (err == null))
      {
         throw new IllegalArgumentException();
      }
      this.out = new PrintWriter(out, true);
      this.err = new PrintWriter(err, true);
      blocks = new ArrayList();
      memoryBlockFormatters = new Properties();
      signalBlockFormatters = new Properties();
   }

   int print(String[] args)
   {
      boolean printDumpMetaData = false;
      boolean printBlockTable = false;
      boolean printBlockContents = false;
      boolean printHierarchy = false;
      boolean printBlock = false;
      boolean printBlocksOfType = false;
      boolean printMemoryBlocks = false;
      boolean printSignalBlocks = false;
      long blockNumber = 0;
      String blockType = null;
      long address1 = 0;
      long address2 = 0;
      long signalNumber = 0;
      String filename = null;
      DumpFile dumpFile = null;
      boolean dumpFileOK = false;

      if (args == null)
      {
         throw new IllegalArgumentException();
      }

      // Determine the options.
      for (int i = 0; i < args.length; i++)
      {
         String arg = args[i];

         if (arg.equals("-help"))
         {
            out.println(HELP_MSG);
            return 0;
         }
         else if (arg.equals("-p"))
         {
            printDumpMetaData = true;
         }
         else if (arg.equals("-l"))
         {
            printBlockTable = true;
         }
         else if (arg.equals("-d"))
         {
            printBlockContents = true;
         }
         else if (arg.equals("-h"))
         {
            printHierarchy = true;
         }
         else if (arg.equals("-b"))
         {
            if ((i + 1) < args.length)
            {
               try
               {
                  blockNumber = parseLong(args[i + 1]);
                  printBlock = true;
                  i++;
               }
               catch (NumberFormatException e)
               {
                  err.println("Invalid block number.");
                  return -1;
               }
            }
            else
            {
               err.println("Missing block number.");
               return -1;
            }
         }
         else if (arg.equals("-t"))
         {
            if ((i + 1) < args.length)
            {
               blockType = parseBlockType(args[i + 1]);
               if (blockType == null)
               {
                  err.println("Invalid block type.");
                  return -1;
               }
               printBlocksOfType = true;
               i++;
            }
            else
            {
               err.println("Missing block type.");
               return -1;
            }
         }
         else if (arg.equals("-m"))
         {
            if ((i + 2) < args.length)
            {
               try
               {
                  address1 = parseLong(args[i + 1]);
                  address2 = parseLong(args[i + 2]);
                  printMemoryBlocks = true;
                  i += 2;
               }
               catch (NumberFormatException e)
               {
                  err.println("Invalid start/end address.");
                  return -1;
               }
            }
            else
            {
               err.println("Missing start/end address.");
               return -1;
            }
         }
         else if (arg.equals("-s"))
         {
            if ((i + 1) < args.length)
            {
               try
               {
                  signalNumber = parseLong(args[i + 1]);
                  printSignalBlocks = true;
                  i++;
               }
               catch (NumberFormatException e)
               {
                  err.println("Invalid signal number.");
                  return -1;
               }
            }
            else
            {
               err.println("Missing signal number.");
               return -1;
            }
         }
         else if (arg.equals("-x"))
         {
            useHexAsDefaultForSignalBlocks = true;
         }
         else if (arg.equals("-f") || arg.equals("-fm"))
         {
            if ((i + 1) < args.length)
            {
               FileInputStream in = null;
               try
               {
                  in = new FileInputStream(args[i + 1]);
                  memoryBlockFormatters.load(in);
                  i++;
               }
               catch (IllegalArgumentException e)
               {
                  err.println("Memory block formatters properties file parse error: " +
                              e.getMessage());
                  return -1;
               }
               catch (FileNotFoundException e)
               {
                  err.println("Memory block formatters properties file not found: " +
                              e.getMessage());
                  return -1;
               }
               catch (IOException e)
               {
                  err.println("Memory block formatters properties file read error: " +
                              e.getMessage());
                  return -1;
               }
               finally
               {
                  if (in != null)
                  {
                     try
                     {
                        in.close();
                     }
                     catch (IOException ignore) {}
                  }
               }
            }
            else
            {
               err.println("Missing memory block formatters properties file.");
               return -1;
            }
         }
         else if (arg.equals("-fs"))
         {
            if ((i + 1) < args.length)
            {
               FileInputStream in = null;
               try
               {
                  in = new FileInputStream(args[i + 1]);
                  signalBlockFormatters.load(in);
                  i++;
               }
               catch (IllegalArgumentException e)
               {
                  err.println("Signal block formatters properties file parse error: " +
                              e.getMessage());
                  return -1;
               }
               catch (FileNotFoundException e)
               {
                  err.println("Signal block formatters properties file not found: " +
                              e.getMessage());
                  return -1;
               }
               catch (IOException e)
               {
                  err.println("Signal block formatters properties file read error: " +
                              e.getMessage());
                  return -1;
               }
               finally
               {
                  if (in != null)
                  {
                     try
                     {
                        in.close();
                     }
                     catch (IOException ignore) {}
                  }
               }
            }
            else
            {
               err.println("Missing signal block formatters properties file.");
               return -1;
            }
         }
         else if (arg.toLowerCase().endsWith(".pmd"))
         {
            filename = arg;
         }
         else
         {
            err.println("Invalid option.");
            err.println(HELP_MSG);
            return -1;
         }
      }

      // Bail out if no dump file specified.
      if (filename == null)
      {
         err.println("No dump file specified.");
         err.println(HELP_MSG);
         return -1;
      }

      // Use default options if none specified.
      if (!printDumpMetaData && !printBlockTable && !printBlockContents &&
          !printHierarchy && !printBlock && !printBlocksOfType &&
          !printMemoryBlocks && !printSignalBlocks)
      {
         printDumpMetaData = true;
         printBlockTable = true;
      }

      // Retrieve dump file information.
      try
      {
         dumpFile = new DumpFile(new File(filename));
         blocks.addAll(dumpFile.getErrorBlocks());
         blocks.addAll(dumpFile.getTextBlocks());
         blocks.addAll(dumpFile.getMemoryBlocks());
         blocks.addAll(dumpFile.getSignalBlocks());
         Collections.sort(blocks, new BlockComparator());
         bigEndian = dumpFile.isBigEndian();
         dumpFileOK = true;
      }
      catch (IffException e)
      {
         err.println("Dump file parse error: " + e.getMessage());
         return -1;
      }
      catch (FileNotFoundException e)
      {
         err.println("Dump file not found: " + e.getMessage());
         return -1;
      }
      catch (IOException e)
      {
         err.println("Dump file read error: " + e.getMessage());
         return -1;
      }
      finally
      {
         if ((dumpFile != null) && !dumpFileOK)
         {
            dumpFile.close();
         }
      }

      // Execute the options.
      try
      {
         if (printDumpMetaData || printBlockTable || printBlockContents)
         {
            if (printDumpMetaData)
            {
               printDumpMetaData(dumpFile);
            }

            if (printBlockTable)
            {
               printBlockTable(blocks, "BLOCK OVERVIEW");
            }

            if (printBlockContents)
            {
               printBlocks();
            }
         }
         else if (printHierarchy)
         {
            try
            {
               dumpFile.getTopChunk().describeContents(System.out);
            }
            catch (IffException e)
            {
               err.println("Dump file parse error: " + e.getMessage());
               return -1;
            }
         }
         else if (printBlock)
         {
            printBlock(blockNumber);
         }
         else if (printBlocksOfType)
         {
            List blocks = getBlockTypeSubset(blockType);
            printBlockTable(blocks, blockType.toUpperCase() + " BLOCK OVERVIEW");
         }
         else if (printMemoryBlocks)
         {
            List memoryBlocks = getMemoryBlockSubset(address1, address2);
            printBlockTable(memoryBlocks, "MEMORY BLOCK OVERVIEW");
         }
         else if (printSignalBlocks)
         {
            List signalBlocks = getSignalBlockSubset(signalNumber);
            printBlockTable(signalBlocks, "SIGNAL BLOCK OVERVIEW");
         }
      }
      finally
      {
         if (dumpFile != null)
         {
            dumpFile.close();
         }
      }

      return 0;
   }

   private void printDumpMetaData(DumpFile dumpFile)
   {
      boolean errorBlocksFound = false;

      out.println("POST MORTEM DUMP");
      out.println();
      out.println(LABEL_DUMP_ID + toHexString(dumpFile.getId()));
      out.println(LABEL_DUMP_FILE + dumpFile.getName());
      out.println(LABEL_DUMP_SIZE + dumpFile.getSize());
      out.println(LABEL_DUMP_VERSION + toHexString(dumpFile.getVersion()));
      out.println(LABEL_DUMP_ENDIAN +
            (dumpFile.isBigEndian() ? "Big endian" : "Little endian"));
      out.println(LABEL_DUMP_TIMESTAMP +
            getTimestamp(dumpFile.getSeconds(), dumpFile.getMicroSeconds()));
      out.println();
      out.println("ERRORS");
      out.println();
      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         AbstractBlock ab = (AbstractBlock) i.next();
         if (ab instanceof ErrorBlock)
         {
            printErrorMetaData((ErrorBlock) ab);
            errorBlocksFound = true;
         }
      }
      if (!errorBlocksFound)
      {
         out.println("No errors found.");
      }
   }

   private void printErrorMetaData(ErrorBlock eb)
   {
      String[] descriptions;
      String description;
      String errorMessage;

      descriptions = eb.getDescriptions();
      description = (descriptions.length > 0) ? descriptions[0].trim() : "";
      errorMessage = OseError.getErrorMessage((eb.getUserCalled() != 0),
                                              eb.getErrorCode(),
                                              eb.getExtra());
      if (errorMessage != null)
      {
         errorMessage =
            rowBreakString(errorMessage, LABEL_BLOCK_INDENT, LABEL_PRINT_WIDTH);
      }
      out.println(LABEL_BLOCK_DESCRIPTION + description);
      out.println(LABEL_BLOCK_TIMESTAMP + getTimestamp(eb));
      out.println(LABEL_BLOCK_PROCESS + toHexString(eb.getCurrentProcess()));
      out.println(LABEL_BLOCK_DETECTOR +
            ((eb.getUserCalled() != 0) ? "User" : "Kernel"));
      out.println(LABEL_BLOCK_ERROR + toHexString(eb.getErrorCode()));
      out.println(LABEL_BLOCK_EXTRA + toHexString(eb.getExtra()));
      out.println(LABEL_BLOCK_ERROR_MSG +
            ((errorMessage != null) ? errorMessage : ""));
      out.println();
   }

   private void printBlockTable(List blocks, String header)
   {
      out.println(header);
      out.println();

      if (blocks.size() == 0)
      {
         out.println("No blocks found.");
         out.println();
         return;
      }

      // Print the block table headers.
      out.println(COLUMN_HEADERS);
      for (int i = 0; i < TABLE_PRINT_WIDTH; i++)
      {
         out.print("-");
      }
      out.println();

      // Print the block table rows.
      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         AbstractBlock ab = (AbstractBlock) i.next();
         String[] descriptions;
         String description;

         if (ab instanceof ErrorBlock)
         {
            ErrorBlock eb = (ErrorBlock) ab;
            descriptions = eb.getDescriptions();
            description = (descriptions.length > 0) ? descriptions[0].trim() : "";
            printBlockTableRow(eb.getBlockNo(),
                               TYPE_ERROR_BLOCK,
                               0,
                               -1,
                               eb.getLength(),
                               eb.getLength(),
                               description);
         }
         else if (ab instanceof TextBlock)
         {
            TextBlock tb = (TextBlock) ab;
            descriptions = tb.getDescriptions();
            description = (descriptions.length > 0) ? descriptions[0] : "";
            if (description.length() > 160)
            {
               description = description.substring(0, 160).trim();
            }
            printBlockTableRow(tb.getBlockNo(),
                               TYPE_TEXT_BLOCK,
                               0,
                               -1,
                               tb.getLength(),
                               tb.getLength(),
                               description);
         }
         else if (ab instanceof MemoryBlock)
         {
            MemoryBlock mb = (MemoryBlock) ab;
            descriptions = mb.getDescriptions();
            description = (descriptions.length > 0) ? descriptions[0].trim() : "";
            printBlockTableRow(mb.getBlockNo(),
                               TYPE_MEMORY_BLOCK,
                               0,
                               mb.getStartAddress(),
                               mb.getLength(),
                               mb.getCompressedLength(),
                               description);
         }
         else if (ab instanceof SignalBlock)
         {
            SignalBlock sb = (SignalBlock) ab;
            long reqSigNo = sb.getRequestSigNo();
            String signalName = SignalBlockFormatter.getSignalName((int) reqSigNo);
            description = ((signalName != null) ? signalName + " " : "") +
               toU32String(reqSigNo);
            printBlockTableRow(sb.getBlockNo(),
                               TYPE_SIGNAL_BLOCK,
                               sb.getStatus(),
                               -1,
                               sb.getLength(),
                               sb.getCompressedLength(),
                               description);
         }
      }
      out.println();
   }

   private void printBlockTableRow(long blockNumber,
                                   String blockType,
                                   long status,
                                   long address,
                                   long size,
                                   long sizeInFile,
                                   String description)
   {
      String blockNumberStr;
      String statusStr;
      String addressStr;
      String sizeStr;
      String sizeInFileStr;
      int blockNumberLen;
      int blockTypeLen;
      int statusLen;
      int addressLen;
      int sizeLen;
      int sizeInFileLen;
      int descriptionLen;

      blockNumberStr = Long.toString(blockNumber);
      statusStr = (status != 0) ? toHexString(status) : "";
      addressStr = (address >= 0) ? toHexString(address) : "";
      sizeStr = Long.toString(size);
      sizeInFileStr = Long.toString(sizeInFile);

      blockNumberLen = blockNumberStr.length();
      blockTypeLen = blockType.length();
      statusLen = statusStr.length();
      addressLen = addressStr.length();
      sizeLen = sizeStr.length();
      sizeInFileLen = sizeInFileStr.length();
      descriptionLen = description.length();

      if (blockNumberLen <= COLUMN_BLOCK_NUMBER_LENGTH)
      {
         out.print(padString(blockNumberLen, COLUMN_BLOCK_NUMBER_LENGTH));
      }
      out.print(blockNumberStr);
      out.print(COLUMN_SEPARATOR);

      out.print(blockType);
      if (blockTypeLen <= COLUMN_BLOCK_TYPE_LENGTH)
      {
         out.print(padString(blockTypeLen, COLUMN_BLOCK_TYPE_LENGTH));
      }
      out.print(COLUMN_SEPARATOR);

      if (statusLen <= COLUMN_STATUS_LENGTH)
      {
         out.print(padString(statusLen, COLUMN_STATUS_LENGTH));
      }
      out.print(statusStr);
      out.print(COLUMN_SEPARATOR);

      if (addressLen <= COLUMN_ADDRESS_LENGTH)
      {
         out.print(padString(addressLen, COLUMN_ADDRESS_LENGTH));
      }
      out.print(addressStr);
      out.print(COLUMN_SEPARATOR);

      if (sizeLen <= COLUMN_SIZE_LENGTH)
      {
         out.print(padString(sizeLen, COLUMN_SIZE_LENGTH));
      }
      out.print(sizeStr);
      out.print(COLUMN_SEPARATOR);

      if (sizeInFileLen <= COLUMN_SIZE_IN_FILE_LENGTH)
      {
         out.print(padString(sizeInFileLen, COLUMN_SIZE_IN_FILE_LENGTH));
      }
      out.print(sizeInFileStr);
      out.print(COLUMN_SEPARATOR);

      if (descriptionLen <= COLUMN_DESCRIPTION_LENGTH)
      {
         out.print(description);
         out.print(padString(descriptionLen, COLUMN_DESCRIPTION_LENGTH));
      }
      else
      {
         out.print(description.substring(0, COLUMN_DESCRIPTION_LENGTH - 3) + "...");
      }

      out.println();
   }

   private void printBlocks()
   {
      out.println("BLOCKS");
      out.println();

      if (blocks.size() > 0)
      {
         for (Iterator i = blocks.iterator(); i.hasNext();)
         {
            AbstractBlock ab = (AbstractBlock) i.next();

            if (ab instanceof ErrorBlock)
            {
               printErrorBlock((ErrorBlock) ab);
            }
            else if (ab instanceof TextBlock)
            {
               printTextBlock((TextBlock) ab);
            }
            else if (ab instanceof MemoryBlock)
            {
               printMemoryBlock((MemoryBlock) ab);
            }
            else if (ab instanceof SignalBlock)
            {
               printSignalBlock((SignalBlock) ab);
            }
         }
      }
      else
      {
         out.println("No blocks found.");
         out.println();
      }
   }

   private void printBlock(long blockNumber)
   {
      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         AbstractBlock ab = (AbstractBlock) i.next();

         if (ab.getBlockNo() == blockNumber)
         {
            if (ab instanceof ErrorBlock)
            {
               printErrorBlock((ErrorBlock) ab);
            }
            else if (ab instanceof TextBlock)
            {
               printTextBlock((TextBlock) ab);
            }
            else if (ab instanceof MemoryBlock)
            {
               printMemoryBlock((MemoryBlock) ab);
            }
            else if (ab instanceof SignalBlock)
            {
               printSignalBlock((SignalBlock) ab);
            }
            break;
         }
      }
   }

   private void printErrorBlock(ErrorBlock eb)
   {
      String[] descriptions;
      StringBuffer descriptionsBuffer;
      String descriptionsString;

      descriptions = eb.getDescriptions();
      descriptionsBuffer = new StringBuffer(160);
      for (int i = 0; i < descriptions.length; i++)
      {
         descriptionsBuffer.append(descriptions[i].trim());
      }
      descriptionsString = rowBreakString(descriptionsBuffer.toString(),
                                          LABEL_BLOCK_INDENT,
                                          LABEL_PRINT_WIDTH);

      out.println(LABEL_BLOCK_NUMBER + eb.getBlockNo());
      out.println(LABEL_BLOCK_TYPE + TYPE_ERROR_BLOCK);
      out.println(LABEL_BLOCK_TIMESTAMP + getTimestamp(eb));
      out.println(LABEL_BLOCK_SIZE + eb.getLength());
      out.println(LABEL_BLOCK_SIZE_IN_FILE + eb.getLength());
      out.println(LABEL_BLOCK_PROCESS + toHexString(eb.getCurrentProcess()));
      out.println(LABEL_BLOCK_DETECTOR +
            ((eb.getUserCalled() != 0) ? "User" : "Kernel"));
      out.println(LABEL_BLOCK_ERROR + toHexString(eb.getErrorCode()));
      out.println(LABEL_BLOCK_EXTRA + toHexString(eb.getExtra()));
      out.println(LABEL_BLOCK_DESCRIPTION + descriptionsString);
      out.println();
   }

   private void printTextBlock(TextBlock tb)
   {
      String[] descriptions;
      String description;

      descriptions = tb.getDescriptions();
      description = (descriptions.length > 0) ? descriptions[0] : "";
      if (description.length() > 160)
      {
         description = description.substring(0, 160) + "...";
      }
      description = rowBreakString(description,
                                   LABEL_BLOCK_INDENT,
                                   LABEL_PRINT_WIDTH);

      out.println(LABEL_BLOCK_NUMBER + tb.getBlockNo());
      out.println(LABEL_BLOCK_TYPE + TYPE_TEXT_BLOCK);
      out.println(LABEL_BLOCK_TIMESTAMP + getTimestamp(tb));
      out.println(LABEL_BLOCK_SIZE + tb.getLength());
      out.println(LABEL_BLOCK_SIZE_IN_FILE + tb.getLength());
      out.println(LABEL_BLOCK_DESCRIPTION + description);
      out.println();
      for (int i = 0; i < descriptions.length; i++)
      {
         out.println(descriptions[i]);
      }
      out.println();
   }

   private void printMemoryBlock(MemoryBlock mb)
   {
      String[] descriptions;
      String description;
      StringBuffer descriptionsBuffer;
      String descriptionsString;
      InputStream in = null;

      descriptions = mb.getDescriptions();
      description = (descriptions.length > 0) ? descriptions[0].trim() : null;
      descriptionsBuffer = new StringBuffer(160);
      for (int i = 0; i < descriptions.length; i++)
      {
         descriptionsBuffer.append(descriptions[i].trim());
      }
      descriptionsString = rowBreakString(descriptionsBuffer.toString(),
                                          LABEL_BLOCK_INDENT,
                                          LABEL_PRINT_WIDTH);

      out.println(LABEL_BLOCK_NUMBER + mb.getBlockNo());
      out.println(LABEL_BLOCK_TYPE + TYPE_MEMORY_BLOCK);
      out.println(LABEL_BLOCK_TIMESTAMP + getTimestamp(mb));
      out.println(LABEL_BLOCK_SIZE + mb.getLength());
      out.println(LABEL_BLOCK_SIZE_IN_FILE + mb.getCompressedLength());
      out.println(LABEL_BLOCK_ADDRESS + toHexString(mb.getStartAddress()));
      out.println(LABEL_BLOCK_DESCRIPTION + descriptionsString);
      out.println();
      try
      {
         IBlockFormatter blockFormatter = getMemoryBlockFormatter(description);
         in = mb.getInputStream();
         blockFormatter.format(in, out, mb.getStartAddress(), mb.getLength(), bigEndian);
      }
      catch (IOException e)
      {
         out.println("Error formatting memory block number " + mb.getBlockNo() +
                     ": " + e.getMessage());
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore) {}
         }
      }
      out.println();
   }

   private void printSignalBlock(SignalBlock sb)
   {
      InputStream in = null;

      out.println(LABEL_BLOCK_NUMBER + sb.getBlockNo());
      out.println(LABEL_BLOCK_TYPE + TYPE_SIGNAL_BLOCK);
      out.println(LABEL_BLOCK_TIMESTAMP + getTimestamp(sb));
      out.println(LABEL_BLOCK_SIZE + sb.getLength());
      out.println(LABEL_BLOCK_SIZE_IN_FILE + sb.getCompressedLength());
      out.println(LABEL_BLOCK_SIGNAME +
         SignalBlockFormatter.getSignalName((int) sb.getRequestSigNo()));
      out.println(LABEL_BLOCK_SIGNO + toU32String(sb.getRequestSigNo()));
      out.println(LABEL_BLOCK_STATUS + toHexString(sb.getStatus()));
      out.println();
      try
      {
         IBlockFormatter blockFormatter = getSignalBlockFormatter(sb.getRequestSigNo());
         in = sb.getInputStream();
         blockFormatter.format(in, out, 0, sb.getLength(), bigEndian);
      }
      catch (IOException e)
      {
         out.println("Error formatting signal block number " + sb.getBlockNo() +
                     ": " + e.getMessage());
         e.printStackTrace();
      }
      finally
      {
         if (in != null)
         {
            try
            {
               in.close();
            }
            catch (IOException ignore) {}
         }
      }
      out.println();
   }

   private List getBlockTypeSubset(String blockType)
   {
      List subset = new ArrayList();

      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         AbstractBlock ab = (AbstractBlock) i.next();

         if (ab instanceof ErrorBlock)
         {
            if (blockType.equals(TYPE_ERROR_BLOCK))
            {
               subset.add(ab);
            }
         }
         else if (ab instanceof TextBlock)
         {
            if (blockType.equals(TYPE_TEXT_BLOCK))
            {
               subset.add(ab);
            }
         }
         else if (ab instanceof MemoryBlock)
         {
            if (blockType.equals(TYPE_MEMORY_BLOCK))
            {
               subset.add(ab);
            }
         }
         else if (ab instanceof SignalBlock)
         {
            if (blockType.equals(TYPE_SIGNAL_BLOCK))
            {
               subset.add(ab);
            }
         }
      }

      return subset;
   }

   private List getMemoryBlockSubset(long startAddress, long endAddress)
   {
      List subset = new ArrayList();
      BigInteger uStartAddress = new BigInteger(1,
            Chunk.longToByteArray(startAddress));
      BigInteger uEndAddress = new BigInteger(1,
            Chunk.longToByteArray(endAddress));

      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         AbstractBlock ab = (AbstractBlock) i.next();

         if (ab instanceof MemoryBlock)
         {
            MemoryBlock mb = (MemoryBlock) ab;
            BigInteger uAddress1 = new BigInteger(1, Chunk.longToByteArray(mb
                  .getStartAddress()));
            BigInteger uAddress2 = uAddress1.add(new BigInteger(1, Chunk
                  .longToByteArray(mb.getLength() - 1)));

            if ((uAddress1.compareTo(uStartAddress) >= 0)
                  && (uAddress1.compareTo(uEndAddress) <= 0)
                  && (uAddress2.compareTo(uStartAddress) >= 0)
                  && (uAddress2.compareTo(uEndAddress) <= 0))
            {
               subset.add(mb);
            }
         }
      }

      return subset;
   }

   private List getSignalBlockSubset(long sigNo)
   {
      List subset = new ArrayList();

      for (Iterator i = blocks.iterator(); i.hasNext();)
      {
         AbstractBlock ab = (AbstractBlock) i.next();
         if (ab instanceof SignalBlock)
         {
            SignalBlock sb = (SignalBlock) ab;
            if (sb.getRequestSigNo() == sigNo)
            {
               subset.add(sb);
            }
         }
      }

      return subset;
   }

   private IBlockFormatter getMemoryBlockFormatter(String description)
   {
      String blockFormatterClass;

      blockFormatterClass = ((description != null) ?
                             memoryBlockFormatters.getProperty(description) :
                             null);
      if (blockFormatterClass == null)
      {
         blockFormatterClass = memoryBlockFormatters.getProperty("");
      }

      if (blockFormatterClass == null)
      {
         return new HexBlockFormatter();
      }
      else
      {
         try
         {
            return (IBlockFormatter)
               Class.forName(blockFormatterClass).newInstance();
         }
         catch (Exception e)
         {
            err.println("Error creating memory block formatter " +
                        blockFormatterClass + ": " + e.getMessage());
            err.println("Falling back to default memory block formatter.");
            return new HexBlockFormatter();
         }
      }
   }

   private IBlockFormatter getSignalBlockFormatter(long sigNo)
   {
      String blockFormatterClass;

      blockFormatterClass = signalBlockFormatters.getProperty(toU32String(sigNo));
      if (blockFormatterClass == null)
      {
         blockFormatterClass = signalBlockFormatters.getProperty("");
      }

      if (blockFormatterClass == null)
      {
         return (useHexAsDefaultForSignalBlocks ? new HexBlockFormatter() :
            (IBlockFormatter) new SignalBlockFormatter());
      }
      else
      {
         try
         {
            return (IBlockFormatter)
               Class.forName(blockFormatterClass).newInstance();
         }
         catch (Exception e)
         {
            err.println("Error creating signal block formatter " +
                        blockFormatterClass + ": " + e.getMessage());
            err.println("Falling back to default signal block formatter.");
            return (useHexAsDefaultForSignalBlocks ? new HexBlockFormatter() :
               (IBlockFormatter) new SignalBlockFormatter());
         }
      }
   }

   private static long parseLong(String value) throws NumberFormatException
   {
      return Long.decode(value).longValue();
   }

   private static String parseBlockType(String value)
   {
      if (value.equals("e"))
      {
         return TYPE_ERROR_BLOCK;
      }
      else if (value.equals("t"))
      {
         return TYPE_TEXT_BLOCK;
      }
      else if (value.equals("m"))
      {
         return TYPE_MEMORY_BLOCK;
      }
      else if (value.equals("s"))
      {
         return TYPE_SIGNAL_BLOCK;
      }
      else
      {
         return null;
      }
   }

   private static String toHexString(long value)
   {
      BigInteger unsigned = new BigInteger(1, Chunk.longToByteArray(value));
      return "0x" + unsigned.toString(16).toUpperCase();    
   }

   private static String toU32String(long value)
   {
      return Long.toString(value & 0xFFFFFFFFL);
   }

   private static String getTimestamp(long seconds, long microSeconds)
   {
      Date date = new Date(seconds * 1000);
      return DATE_FORMAT.format(date) + " " + microSeconds + " us";
   }

   private static String getTimestamp(AbstractBlock ab)
   {
      return getTimestamp(ab.getSeconds(), ab.getMicroSeconds());
   }

   private static String padString(int realLength, int desiredLength)
   {
      StringBuffer sb = new StringBuffer();

      if (realLength < desiredLength)
      {
         for (int i = 0; i < desiredLength - realLength; i++)
         {
            sb.append(" ");
         }
      }

      return sb.toString();
   }

   private static String rowBreakString(String string, String indent, int width)
   {
      int stringLength;

      string = string.replaceAll("\r", "");
      string = string.replaceAll("\n", " ");
      string = string.trim();
      stringLength = string.length();

      if (stringLength > width)
      {
         StringBuffer sb = new StringBuffer(stringLength);

         for (int i = 0; i < stringLength;)
         {
            if (i + width < stringLength)
            {
               int breakIndex;
               String row;

               breakIndex = string.substring(i, i + width).lastIndexOf(" ");
               breakIndex = (breakIndex == -1) ? width : breakIndex;
               row = string.substring(i, i + breakIndex).trim();
               sb.append(indent);
               sb.append(row);
               sb.append("\n");
               i += breakIndex;
            }
            else
            {
               String lastRow = string.substring(i).trim();
               sb.append(indent);
               sb.append(lastRow);
               break;
            }
         }

         string = sb.substring(indent.length());
      }

      return string;
   }

   /**
    * Invoke the CLI dump editor with the specified arguments.
    *
    * @param args  the arguments to the CLI dump editor.
    */
   public static void main(String[] args)
   {
      DumpEditor dumpEditor;
      int status;

      dumpEditor = new DumpEditor(System.out, System.err);
      status = dumpEditor.print(args);
      System.exit(status);
   }
}
