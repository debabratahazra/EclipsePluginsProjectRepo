/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.system;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import com.ose.gateway.Signal;

/**
 * This class contains support for writing OSE dump files. An OSE dump file
 * consists of blocks of different types. The type of blocks are error blocks,
 * text blocks, memory blocks, and signal blocks.
 */
public class DumpFileWriter
{
   private static final int PMD_VERSION = 0x100;

   private static final int BLHD = createIffId('B', 'L', 'H', 'D');
   private static final int BLOC = createIffId('B', 'L', 'O', 'C');
   private static final int DATA = createIffId('D', 'A', 'T', 'A');
   private static final int DESC = createIffId('D', 'E', 'S', 'C');
   private static final int DPID = createIffId('D', 'P', 'I', 'D');
   private static final int ERBL = createIffId('E', 'R', 'B', 'L');
   private static final int ERIN = createIffId('E', 'R', 'I', 'N');
   private static final int FORM = createIffId('F', 'O', 'R', 'M');
   private static final int LIST = createIffId('L', 'I', 'S', 'T');
   private static final int MBL_ = createIffId('M', 'B', 'L', ' ');
   private static final int MHD_ = createIffId('M', 'H', 'D', ' ');
   private static final int PMD_ = createIffId('P', 'M', 'D', ' ');
   private static final int SGBL = createIffId('S', 'G', 'B', 'L');
   private static final int SGHD = createIffId('S', 'G', 'H', 'D');
   private static final int TXBL = createIffId('T', 'X', 'B', 'L');
   private static final int VERS = createIffId('V', 'E', 'R', 'S');
   private static final int ZDAT = createIffId('Z', 'D', 'A', 'T');
   private static final int ZDEF = createIffId('Z', 'D', 'E', 'F');
   private static final int ER64 = createIffId('E', 'R', '6', '4');
   private static final int MBL2 = createIffId('M', 'B', 'L', '2');
   private static final int MHD2 = createIffId('M', 'H', 'D', '2');

   private static final long DUMP_SIZE_POS = 4;
   private static final long BLOCK_LIST_SIZE_POS = 40;

   private final RandomAccessFile file;
   private final Deflater deflater;
   /** Dump file byte order; big endian if true and little endian if false. */
   protected final boolean bigEndian;
   private final int dumpId;

   private boolean open;
   private DeflaterOutputStream deflaterOut;
   private long dumpSize;
   private long blockListSize;
   private long blockNumber;

   private boolean memoryBlock;
   private boolean signalBlock;

   private long descSize;
   private long topFormSize;
   private long formSize;
   private long contentLength;
   private boolean dataCompressed;
   private long zdataSize;
   private long zdefImageLength;
   private long dataSize;
   private long actualDataSize;

   private long topFormSizePos;
   private long formSizePos;
   private long contentStatusPos;
   private long contentLengthPos;
   private long zdataSizePos;
   private long zdefImageLengthPos;
   private long dataSizePos;

   /**
    * Create a new dump file writer object.
    *
    * @param file       the dump file to be created.
    * @param bigEndian  true if the dump file should be written in big endian
    * byte order, false if it should be written in little endian byte order.
    * @param dumpId     the ID of the dump.
    * @throws IOException  if an I/O exception occurred.
    */
   public DumpFileWriter(File file, boolean bigEndian, int dumpId)
      throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      this.file = new RandomAccessFile(file, "rw");
      this.file.setLength(0);
      this.deflater = new Deflater();
      this.bigEndian = bigEndian;
      this.dumpId = dumpId;

      open = true;
      dumpSize = 40;
      blockListSize = 4;
      blockNumber = 0;

      writeIffInt(FORM);
      writeIffInt(dumpSize);
      writeIffInt(PMD_);

      writeIffInt(VERS);
      writeIffInt(4);
      writeRawInt(PMD_VERSION);

      writeIffInt(DPID);
      writeIffInt(4);
      writeRawInt(dumpId);

      writeIffInt(LIST);
      writeIffInt(blockListSize);
      writeIffInt(BLOC);
   }

   /**
    * Write an error block. An error block contains information about an OSE
    * error.
    *
    * @param userCalled      true if the error was user called, false if it was
    * kernel called.
    * @param errorCode       the error code.
    * @param extra           the extra parameter.
    * @param currentProcess  the ID of the faulting process.
    * @param description     the error description or null if none.
    * @throws IOException    if an I/O exception occurred.
    */
   public synchronized void writeErrorBlock(boolean userCalled,
                                            int errorCode,
                                            int extra,
                                            int currentProcess,
                                            String description)
      throws IOException
   {
      checkState();

      if (memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      descSize = getDescSize(description);

      writeIffInt(FORM);
      writeIffInt(64 + descSize);
      writeIffInt(BLOC);

      writeBlockHead();
      writeIffInt(FORM);
      writeIffInt(28 + descSize);
      writeIffInt(ERBL);
      if (descSize > 0)
      {
         writeDesc(description);
      }
      writeIffInt(ERIN);
      writeIffInt(16);
      writeRawInt(userCalled ? 1 : 0);
      writeRawInt(errorCode);
      writeRawInt(extra);
      writeRawInt(currentProcess);

      updateSizes(72 + descSize);
   }

   /**
    * Write an error block. An error block contains information about an OSE
    * error.
    *
    * @param userCalled      true if the error was user called, false if it was
    * kernel called.
    * @param errorCode       the error code.
    * @param extra           the extra parameter.
    * @param currentProcess  the ID of the faulting process.
    * @param description     the error description or null if none.
    * @throws IOException    if an I/O exception occurred.
    */
   public synchronized void writeErrorBlock(boolean userCalled,
                                            long errorCode,
                                            long extra,
                                            int currentProcess,
                                            String description)
      throws IOException
   {
      checkState();

      if (memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      descSize = getDescSize(description);

      writeIffInt(FORM);
      writeIffInt(64 + 24 + descSize);
      writeIffInt(BLOC);

      writeBlockHead();
      writeIffInt(FORM);
      writeIffInt(28 + 24 + descSize);
      writeIffInt(ERBL);
      if (descSize > 0)
      {
         writeDesc(description);
      }
      writeIffInt(ERIN);
      writeIffInt(16);
      writeRawInt(userCalled ? 1 : 0);
      writeRawInt((int)errorCode);
      writeRawInt((int)extra);
      writeRawInt(currentProcess);
      
      writeIffInt(ER64);
      writeIffInt(16);
      writeRawLong(errorCode);
      writeRawLong(extra);

      updateSizes(72 + 24 + descSize);
   }
   /**
    * Write a text block. A text block contains textual information.
    *
    * @param description   the text description.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeTextBlock(String description)
      throws IOException
   {
      if (description == null)
      {
         throw new IllegalArgumentException();
      }

      checkState();

      if (memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      descSize = getDescSize(description);

      writeIffInt(FORM);
      writeIffInt(40 + descSize);
      writeIffInt(BLOC);

      writeBlockHead();
      writeIffInt(FORM);
      writeIffInt(4 + descSize);
      writeIffInt(TXBL);
      if (descSize > 0)
      {
         writeDesc(description);
      }

      updateSizes(48 + descSize);
   }

   /**
    * Start writing a memory block. Should be followed by one or more
    * writeMemory() calls and ended with one endWritingMemoryBlock() call.
    * <p>
    * A memory block contains a contiguous chunk of memory.
    *
    * @param address       the base address of the memory block.
    * @param description   the description of the memory block or null if none.
    * @param compressed    true if the memory block should be compressed, false
    * otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingMemoryBlock(int address,
                                                    String description,
                                                    boolean compressed)
      throws IOException
   {
      checkState();

      if (memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      descSize = getDescSize(description);
      dumpSize += 72 + descSize;
      blockListSize += 72 + descSize;
      memoryBlock = true;
      topFormSize = 64 + descSize;
      formSize = 28 + descSize;
      contentLength = 0;
      dataCompressed = compressed;
      zdataSize = 48;
      zdefImageLength = 0;
      dataSize = 0;
      actualDataSize = 0;

      if (dataCompressed)
      {
         dumpSize += zdataSize;
         blockListSize += zdataSize;
         topFormSize += zdataSize;
         formSize += zdataSize;
      }

      writeIffInt(FORM);
      topFormSizePos = file.getFilePointer();
      writeIffInt(topFormSize);
      writeIffInt(BLOC);

      writeBlockHead();
      writeIffInt(FORM);
      formSizePos = file.getFilePointer();
      writeIffInt(formSize);
      writeIffInt(MBL_);
      if (descSize > 0)
      {
         writeDesc(description);
      }

      writeIffInt(MHD_);
      writeIffInt(8);
      writeRawInt(address);
      contentLengthPos = file.getFilePointer();
      writeRawInt(contentLength);

      if (dataCompressed)
      {
         writeIffInt(FORM);
         zdataSizePos = file.getFilePointer();
         writeIffInt(zdataSize);
         writeIffInt(ZDAT);
         writeIffInt(ZDEF);
         writeIffInt(28);
         writeRawInt(1);
         writeRawInt(0);
         writeRawInt(0);
         writeRawInt(0);
         writeRawInt(0);
         zdefImageLengthPos = file.getFilePointer();
         writeRawInt(zdefImageLength);
         writeRawInt(0); // reserved
         writeIffInt(DATA);
         dataSizePos = file.getFilePointer();
         writeIffInt(dataSize);
         deflater.reset();
         deflaterOut = new DeflaterOutputStream(
               new RandomAccessFileOutputStream(file, file.getFilePointer()),
               deflater);
      }
      else
      {
         writeIffInt(DATA);
         dataSizePos = file.getFilePointer();
         writeIffInt(dataSize);
      }
   }

   /**
    * Start writing a memory block. Should be followed by one or more
    * writeMemory() calls and ended with one endWritingMemoryBlock() call.
    * <p>
    * A memory block contains a contiguous chunk of memory.
    *
    * @param address       the base address of the memory block.
    * @param description   the description of the memory block or null if none.
    * @param compressed    true if the memory block should be compressed, false
    * otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingMemoryBlock(long address,
                                                    String description,
                                                    boolean compressed)
      throws IOException
   {
      checkState();

      if (memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      descSize = getDescSize(description);
      dumpSize += 80 + descSize;
      blockListSize += 80 + descSize;
      memoryBlock = true;
      topFormSize = 72 + descSize;
      formSize = 36 + descSize;
      contentLength = 0;
      dataCompressed = compressed;
      zdataSize = 48;
      zdefImageLength = 0;
      dataSize = 0;
      actualDataSize = 0;

      if (dataCompressed)
      {
         dumpSize += zdataSize;
         blockListSize += zdataSize;
         topFormSize += zdataSize;
         formSize += zdataSize;
      }

      writeIffInt(FORM);
      topFormSizePos = file.getFilePointer();
      writeIffInt(topFormSize);
      writeIffInt(BLOC);

      writeBlockHead();
      writeIffInt(FORM);
      formSizePos = file.getFilePointer();
      writeIffInt(formSize);
      writeIffInt(MBL2);
      if (descSize > 0)
      {
         writeDesc(description);
      }

      writeIffInt(MHD2);
      writeIffInt(8);
      writeRawLong(address);
      contentLengthPos = file.getFilePointer();
      writeRawInt(contentLength);

      if (dataCompressed)
      {
         writeIffInt(FORM);
         zdataSizePos = file.getFilePointer();
         writeIffInt(zdataSize);
         writeIffInt(ZDAT);
         writeIffInt(ZDEF);
         writeIffInt(28);
         writeRawInt(1);
         writeRawInt(0);
         writeRawInt(0);
         writeRawInt(0);
         writeRawInt(0);
         zdefImageLengthPos = file.getFilePointer();
         writeRawInt(zdefImageLength);
         writeRawInt(0); // reserved
         writeIffInt(DATA);
         dataSizePos = file.getFilePointer();
         writeIffInt(dataSize);
         deflater.reset();
         deflaterOut = new DeflaterOutputStream(
               new RandomAccessFileOutputStream(file, file.getFilePointer()),
               deflater);
      }
      else
      {
         writeIffInt(DATA);
         dataSizePos = file.getFilePointer();
         writeIffInt(dataSize);
      }
   }

   /**
    * Write a chunk of contiguous memory in a memory block.
    *
    * @param data          the memory chunk to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeMemory(byte[] data) throws IOException
   {
      if (data == null)
      {
         throw new IllegalArgumentException();
      }

      checkState();

      if (!memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      if (dataCompressed)
      {
         deflaterOut.write(data);
      }
      else
      {
         writeBytes(data);
      }

      actualDataSize += data.length;
   }

   /**
    * End writing a memory block.
    *
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void endWritingMemoryBlock() throws IOException
   {
      long compressedDataSize;
      int padding;

      checkState();

      if (!memoryBlock || signalBlock)
      {
         throw new IllegalStateException();
      }

      if (dataCompressed)
      {
         deflaterOut.close();
         compressedDataSize = deflater.getTotalOut();
         deflaterOut = null;
      }
      else
      {
         compressedDataSize = actualDataSize;
      }

      padding = getPadding(actualDataSize);

      for (int i = 0; i < padding; i++)
      {
         writeByte(0);
      }

      updateSizes(actualDataSize, compressedDataSize, padding);

      memoryBlock = false;
   }

   /**
    * Start writing a signal block. Should be followed by one or more
    * writeSignal() or writeSignalBlockData() calls and ended with one
    * endWritingSignalBlock() call.
    * <p>
    * A signal block contains an OSE signal transaction; typically one request
    * signal, followed by one or more reply signals and possibly ended with one
    * endmark signal.
    *
    * @param requestSigno  the signal number of the request signal for the
    * signal block.
    * @param compressed    true if the signal block should be compressed, false
    * otherwise.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void startWritingSignalBlock(int requestSigno,
                                                    boolean compressed)
      throws IOException
   {
      checkState();

      if (signalBlock || memoryBlock)
      {
         throw new IllegalStateException();
      }

      dumpSize += 76;
      blockListSize += 76;
      signalBlock = true;
      topFormSize = 68;
      formSize = 32;
      contentLength = 0;
      dataCompressed = compressed;
      zdataSize = 48;
      zdefImageLength = 0;
      dataSize = 0;
      actualDataSize = 0;

      if (dataCompressed)
      {
         dumpSize += zdataSize;
         blockListSize += zdataSize;
         topFormSize += zdataSize;
         formSize += zdataSize;
      }

      writeIffInt(FORM);
      topFormSizePos = file.getFilePointer();
      writeIffInt(topFormSize);
      writeIffInt(BLOC);

      writeBlockHead();
      writeIffInt(FORM);
      formSizePos = file.getFilePointer();
      writeIffInt(formSize);
      writeIffInt(SGBL);

      writeIffInt(SGHD);
      writeIffInt(12);
      writeRawInt(requestSigno);
      contentStatusPos = file.getFilePointer();
      writeRawInt(0); // status
      contentLengthPos = file.getFilePointer();
      writeRawInt(contentLength);

      if (dataCompressed)
      {
         writeIffInt(FORM);
         zdataSizePos = file.getFilePointer();
         writeIffInt(zdataSize);
         writeIffInt(ZDAT);
         writeIffInt(ZDEF);
         writeIffInt(28);
         writeRawInt(1);
         writeRawInt(0);
         writeRawInt(0);
         writeRawInt(0);
         writeRawInt(0);
         zdefImageLengthPos = file.getFilePointer();
         writeRawInt(zdefImageLength);
         writeRawInt(0); // reserved
         writeIffInt(DATA);
         dataSizePos = file.getFilePointer();
         writeIffInt(dataSize);
         deflater.reset();
         deflaterOut = new DeflaterOutputStream(
               new RandomAccessFileOutputStream(file, file.getFilePointer()),
               deflater);
      }
      else
      {
         writeIffInt(DATA);
         dataSizePos = file.getFilePointer();
         writeIffInt(dataSize);
      }
   }

   /**
    * Write a signal.
    *
    * @param signal        the bytes of the signal to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeSignal(byte[] signal) throws IOException
   {
      int padding;

      if (signal == null)
      {
         throw new IllegalArgumentException();
      }

      checkState();

      if (!signalBlock || memoryBlock)
      {
         throw new IllegalStateException();
      }

      padding = getPadding(signal.length);

      if (dataCompressed)
      {
         deflateRawInt(signal.length);
         deflaterOut.write(signal);
         for (int i = 0; i < padding; i++)
         {
            deflaterOut.write(0);
         }
      }
      else
      {
         writeRawInt(signal.length);
         writeBytes(signal);
         for (int i = 0; i < padding; i++)
         {
            writeByte(0);
         }
      }

      actualDataSize += 4 + signal.length + padding;
   }

   /**
    * Write a signal.
    *
    * @param signal        the signal object to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeSignal(Signal signal) throws IOException
   {
      byte[] signalData;
      int padding;

      if (signal == null)
      {
         throw new IllegalArgumentException();
      }

      checkState();

      if (!signalBlock || memoryBlock)
      {
         throw new IllegalStateException();
      }

      signalData = signal.javaToOse(bigEndian);
      padding = getPadding(signalData.length);

      if (dataCompressed)
      {
         deflateRawInt(4 + signalData.length);
         deflateRawInt(signal.getSigNo());
         deflaterOut.write(signalData);
         for (int i = 0; i < padding; i++)
         {
            deflaterOut.write(0);
         }
      }
      else
      {
         writeRawInt(4 + signalData.length);
         writeRawInt(signal.getSigNo());
         writeBytes(signalData);
         for (int i = 0; i < padding; i++)
         {
            writeByte(0);
         }
      }

      actualDataSize += 8 + signalData.length + padding;
   }

   /**
    * Write a chunk of contiguous data in a signal block.
    *
    * @param data          the signal block data to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void writeSignalBlockData(byte[] data) throws IOException
   {
      if (data == null)
      {
         throw new IllegalArgumentException();
      }

      checkState();

      if (!signalBlock || memoryBlock)
      {
         throw new IllegalStateException();
      }

      if (dataCompressed)
      {
         deflaterOut.write(data);
      }
      else
      {
         writeBytes(data);
      }

      actualDataSize += data.length;
   }

   /**
    * End writing a signal block.
    *
    * @param status        the overall signal block status, 0 means success.
    * @throws IOException  if an I/O exception occurred.
    */
   public synchronized void endWritingSignalBlock(int status) throws IOException
   {
      long compressedDataSize;
      int padding;
      long position;

      checkState();

      if (!signalBlock || memoryBlock)
      {
         throw new IllegalStateException();
      }

      if (dataCompressed)
      {
         deflaterOut.close();
         compressedDataSize = deflater.getTotalOut();
         deflaterOut = null;
      }
      else
      {
         compressedDataSize = actualDataSize;
      }

      padding = getPadding(actualDataSize);

      for (int i = 0; i < padding; i++)
      {
         writeByte(0);
      }

      updateSizes(actualDataSize, compressedDataSize, padding);

      position = file.getFilePointer();
      file.seek(contentStatusPos);
      writeRawInt(status);
      file.seek(position);

      signalBlock = false;
   }

   /**
    * Close this dump file writer and release any resources associated with it.
    */
   public synchronized void close()
   {
      if (open)
      {
         deflater.end();
         try
         {
            file.close();
         } catch (IOException ignore) {}
         open = false;
      }
   }

   private void checkState() throws IllegalStateException
   {
      if (!open)
      {
         throw new IllegalStateException("DumpFileWriter is closed");
      }
   }

   private void updateSizes(long size) throws IOException
   {
      long position;

      position = file.getFilePointer();

      dumpSize += size;
      file.seek(DUMP_SIZE_POS);
      writeIffInt(dumpSize);

      blockListSize += size;
      file.seek(BLOCK_LIST_SIZE_POS);
      writeIffInt(blockListSize);

      file.seek(position);
   }

   private void updateSizes(long uncompressedDataSize,
                            long compressedDataSize,
                            int padding)
      throws IOException
   {
      long position;

      position = file.getFilePointer();

      dumpSize += compressedDataSize + padding;
      file.seek(DUMP_SIZE_POS);
      writeIffInt(dumpSize);

      blockListSize += compressedDataSize + padding;
      file.seek(BLOCK_LIST_SIZE_POS);
      writeIffInt(blockListSize);

      topFormSize += compressedDataSize + padding;
      file.seek(topFormSizePos);
      writeIffInt(topFormSize);

      formSize += compressedDataSize + padding;
      file.seek(formSizePos);
      writeIffInt(formSize);

      contentLength += uncompressedDataSize;
      file.seek(contentLengthPos);
      writeRawInt(contentLength);

      if (dataCompressed)
      {
         zdataSize += compressedDataSize + padding;
         file.seek(zdataSizePos);
         writeIffInt(zdataSize);

         zdefImageLength += compressedDataSize;
         file.seek(zdefImageLengthPos);
         writeRawInt(zdefImageLength);
      }

      dataSize += compressedDataSize + padding;
      file.seek(dataSizePos);
      writeIffInt(dataSize);

      file.seek(position);
   }

   private void writeBlockHead() throws IOException
   {
      writeIffInt(BLHD);
      writeIffInt(16);
      writeRawInt(dumpId);
      writeRawInt(blockNumber++);
      writeRawInt(System.currentTimeMillis() / 1000);
      writeRawInt(0);
   }

   private void writeDesc(String text) throws IOException
   {
      int textSize =
         (((text != null) && (text.length() > 0)) ? text.length() + 1 : 0);

      if (textSize > 0)
      {
         int padding = getPadding(textSize);
         writeIffInt(DESC);
         writeIffInt(textSize + padding);
         writeString(text);
         for (int i = 0; i < padding; i++)
         {
            writeByte(0);
         }
      }
   }

   private void writeIffInt(int value) throws IOException
   {
      file.writeInt(value);
   }

   private void writeIffInt(long value) throws IOException
   {
      file.writeInt((int) value);
   }

   private void writeRawInt(int value) throws IOException
   {
      if (bigEndian)
      {
         file.writeInt(value);
      }
      else
      {
         file.write((value >>>  0) & 0xFF);
         file.write((value >>>  8) & 0xFF);
         file.write((value >>> 16) & 0xFF);
         file.write((value >>> 24) & 0xFF);
      }
   }

   private void writeRawLong(long value) throws IOException
   {
      if (bigEndian)
      {
         file.writeLong(value);
      }
      else
      {
         file.write((int) ((value >>>  0) & 0xFF));
         file.write((int) ((value >>>  8) & 0xFF));
         file.write((int) ((value >>> 16) & 0xFF));
         file.write((int) ((value >>> 24) & 0xFF));
         file.write((int) ((value >>> 32) & 0xFF));
         file.write((int) ((value >>> 40) & 0xFF));
         file.write((int) ((value >>> 48) & 0xFF));
         file.write((int) ((value >>> 56) & 0xFF));
      }
   }

   private void writeRawInt(long value) throws IOException
   {
      writeRawInt((int) value);
   }

   private void writeString(String s) throws IOException
   {
      file.writeBytes(s);
      file.write(0);
   }

   private void writeByte(int b) throws IOException
   {
      file.write(b);
   }

   private void writeBytes(byte[] b) throws IOException
   {
      file.write(b);
   }

   private void deflateRawInt(int value) throws IOException
   {
      if (bigEndian)
      {
         deflaterOut.write((value >>> 24) & 0xFF);
         deflaterOut.write((value >>> 16) & 0xFF);
         deflaterOut.write((value >>>  8) & 0xFF);
         deflaterOut.write((value >>>  0) & 0xFF);
      }
      else
      {
         deflaterOut.write((value >>>  0) & 0xFF);
         deflaterOut.write((value >>>  8) & 0xFF);
         deflaterOut.write((value >>> 16) & 0xFF);
         deflaterOut.write((value >>> 24) & 0xFF);
      }
   }

   private static long getDescSize(String text)
   {
      if ((text != null) && (text.length() > 0))
      {
         int textSize = text.length() + 1;
         return 8 + textSize + getPadding(textSize);
      }
      else
      {
         return 0;
      }
   }

   private static int getPadding(int size)
   {
      return (4 - (size & 3)) % 4;
   }

   private static int getPadding(long size)
   {
      return getPadding((int) size);
   }

   private static int createIffId(char a, char b, char c, char d)
   {
      return (((int) a << 24) + ((int) b << 16) + ((int) c << 8) + ((int) d));
   }

   private static class RandomAccessFileOutputStream extends OutputStream
   {
      private final RandomAccessFile file;

      RandomAccessFileOutputStream(RandomAccessFile file, long pos)
         throws IOException
      {
         if (file == null)
         {
            throw new IllegalArgumentException();
         }
         if (pos < 0)
         {
            throw new IllegalArgumentException();
         }
         this.file = file;
         file.seek(pos);
      }

      public void write(int b) throws IOException
      {
         file.write(b);
      }

      public void write(byte[] b) throws IOException
      {
         file.write(b);
      }

      public void write(byte[] b, int off, int len) throws IOException
      {
         file.write(b, off, len);
      }
   }
}
