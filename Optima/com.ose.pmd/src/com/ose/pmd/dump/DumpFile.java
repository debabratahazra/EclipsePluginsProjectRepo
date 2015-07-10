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

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This class encapsulates dump contents from a file as IFF data.
 */
public final class DumpFile
{
   private final RandomAccessFile file;
   private final long id;
   private final String name;
   private final long size;
   private final long version;
   private final boolean bigEndian;
   private final long seconds;
   private final long microSeconds;
   private volatile boolean open;

   private final Object extractContentsLock;
   private volatile boolean contentsExtracted;

   private final List errorBlocks;
   private final List textBlocks;
   private final List memoryBlocks;
   private final List signalBlocks;

   public DumpFile(File f) throws IOException, IffException
   {
      if (f == null)
      {
         throw new NullPointerException();
      }

      try
      {
         Chunk topChunk;
         VERSChunk versChunk;
         BLHDChunk topBlhdChunk;

         file = new RandomAccessFile(f, "r");
         topChunk = getTopChunk();
         versChunk = getVersChunk(topChunk);
         bigEndian = versChunk.isBigEndian();
         topBlhdChunk = getTopBlhdChunk(topChunk);
         id = topBlhdChunk.getDumpId();
         name = f.getName();
         size = topChunk.getSize();
         version = versChunk.getVersion();
         seconds = topBlhdChunk.getSeconds();
         microSeconds = topBlhdChunk.getMicroSeconds();
         extractContentsLock = new Object();
         contentsExtracted = false;
         errorBlocks = new ArrayList();
         textBlocks = new ArrayList();
         memoryBlocks = new ArrayList();
         signalBlocks = new ArrayList();
         validate();
         open = true;
      }
      finally
      {
         if (!open)
         {
            close();
         }
      }
   }

   public boolean isOpen()
   {
      return open;
   }

   public long getId()
   {
      return id;
   }

   public String getName()
   {
      return name;
   }

   public long getSize()
   {
      return size;
   }

   public long getVersion()
   {
      return version;
   }

   public boolean isBigEndian()
   {
      return bigEndian;
   }

   public long getSeconds()
   {
      return seconds;
   }

   public long getMicroSeconds()
   {
      return microSeconds;
   }

   public List getErrorBlocks() throws IffException
   {
      extractContents();
      return Collections.unmodifiableList(errorBlocks);
   }

   public List getTextBlocks() throws IffException
   {
      extractContents();
      return Collections.unmodifiableList(textBlocks);
   }

   public List getMemoryBlocks() throws IffException
   {
      extractContents();
      return Collections.unmodifiableList(memoryBlocks);
   }

   public List getSignalBlocks() throws IffException
   {
      extractContents();
      return Collections.unmodifiableList(signalBlocks);
   }

   public Chunk getTopChunk() throws IffException
   {
      return Chunk.getInstanceAt(this, 0);
   }

   public int read(int offset) throws IOException
   {
      file.seek(offset);
      return file.read();
   }

   public int read(byte b[], int offset) throws IOException
   {
      file.seek(offset);
      return file.read(b);
   }

   public int read(byte[] b, int offset, int len) throws IOException
   {
      file.seek(offset);
      return file.read(b, 0, len);
   }

   public int readIffS32(int offset) throws IOException
   {
      file.seek(offset);
      return file.readInt();
   }

   public long readIffU32(int offset) throws IOException
   {
      file.seek(offset);
      return (file.readInt() & 0xFFFFFFFFL);
   }

   public int readS32(int offset) throws IOException
   {
      byte[] b = new byte[4];
      int length;
      int result;

      length = read(b, offset, b.length);
      if (length < 0)
      {
         throw new EOFException();
      }
      if (bigEndian)
      {
         result = ((b[0] & 0xFF) << 24) + ((b[1] & 0xFF) << 16) +
                  ((b[2] & 0xFF) <<  8) + ((b[3] & 0xFF) <<  0);
      }
      else
      {
         result = ((b[0] & 0xFF) <<  0) + ((b[1] & 0xFF) <<  8) +
                  ((b[2] & 0xFF) << 16) + ((b[3] & 0xFF) << 24);
      }
      return result;
   }

   public long readS64(int offset) throws IOException
   {
      byte[] b = new byte[8];
      int length;

      length = read(b, offset, b.length);
      if (length < 0)
      {
         throw new EOFException();
      }
      if (!bigEndian)
      {
         for (int i = 0; i < (b.length/2); i++)
         {
            byte temp = b[i];
            b[i] = b[b.length - 1 - i];
            b[b.length - 1 - i] = temp;
         }
      }
      BigInteger value = new BigInteger(1, b);
      return value.longValue();
   }

   public long readU32(int offset) throws IOException
   {
      return (readS32(offset) & 0xFFFFFFFFL);
   }

   public long readU64(int offset) throws IOException
   {
      return (readS64(offset) & 0xFFFFFFFFFFFFFFFFL);
   }

   public void validate() throws IffException
   {
      getTopChunk().validate();
   }

   public void close()
   {
      try
      {
         open = false;
         if (file != null)
         {
            file.close();
         }
      }
      catch (IOException ignore) {}
   }

   private static VERSChunk getVersChunk(Chunk topChunk) throws IffException
   {
      IffId[] spec;
      List list;

      // List all version chunks.
      spec = new IffId[] {IffId.FORM, Chunk.PMD_, Chunk.VERS};
      list = topChunk.getMatchingChunks(spec);

      if (list.size() > 0)
      {
         Object obj = list.get(0);
         if (obj instanceof VERSChunk)
         {
            // Get the first version chunk.
            VERSChunk versChunk = (VERSChunk) obj;
            return versChunk;
         }
         else
         {
            throw new IffException("Invalid IFF PMD format: Missing VERS chunk.");
         }
      }
      else
      {
         throw new IffException("Invalid IFF PMD format: Missing VERS chunk.");
      }
   }

   private static BLHDChunk getTopBlhdChunk(Chunk topChunk) throws IffException
   {
      IffId[] spec;
      List list;

      // List all block header chunks.
      spec = new IffId[] {IffId.FORM, Chunk.PMD_, IffId.LIST, Chunk.BLOC,
                          IffId.FORM, Chunk.BLOC, Chunk.BLHD};
      list = topChunk.getMatchingChunks(spec);

      if (list.size() > 0)
      {
         // Get the first block header chunk.
         Object obj = list.get(0);
         if (obj instanceof BLHDChunk)
         {
            BLHDChunk blhdChunk = (BLHDChunk) obj;
            return blhdChunk;
         }
         else
         {
            throw new IffException("Invalid IFF PMD format: Missing BLHD chunk.");
         }
      }
      else
      {
         throw new IffException("Invalid IFF PMD format: Missing BLHD chunk.");
      }
   }

   private void extractContents() throws IffException
   {
      synchronized (extractContentsLock)
      {
         if (!contentsExtracted)
         {
            doExtractContents();
         }
      }
   }

   private void doExtractContents() throws IffException
   {
      IffId[] formBlocSpec;
      List formBlocList;

      contentsExtracted = true;
      formBlocSpec = new IffId[]
      {
         IffId.FORM,
         Chunk.PMD_,
         IffId.LIST,
         Chunk.BLOC,
         IffId.FORM,
         Chunk.BLOC
      };
      formBlocList = getTopChunk().getMatchingChunks(formBlocSpec);

      // Loop over all FORM BLOC chunks.
      for (Iterator i = formBlocList.iterator(); i.hasNext();)
      {
         BLHDChunk blhdChunk = null;
         Chunk formBlocChunk = (Chunk) i.next();

         // Loop over all block chunks.
         List blockChunks = formBlocChunk.getChunks();
         for (Iterator j = blockChunks.iterator(); j.hasNext();)
         {
            Chunk blockChunk = (Chunk) j.next();
            if (blockChunk instanceof BLHDChunk)
            {
               blhdChunk = (BLHDChunk) blockChunk;
            }
            else if (blockChunk instanceof FORMChunk)
            {
               IffId[] descSpec;
               List descList;
               String[] descs;

               descSpec = new IffId[] {IffId.FORM, blockChunk.getFormType(), Chunk.DESC};
               descList = blockChunk.getMatchingChunks(descSpec);
               descs = updateComments(new String[0], descList.listIterator());

               if (blockChunk.getFormType().equals(Chunk.ERBL))
               {
                  List chunks = blockChunk.getChunks();
                  for (Iterator k = chunks.iterator(); k.hasNext();)
                  {
                     Chunk chunk = (Chunk) k.next();
                     if (chunk instanceof ERINChunk)
                     {
                        Chunk chunk2 = null;
                        boolean isER64ChunkExists = false;
                        if (k.hasNext())
                        {
                           chunk2 = (Chunk) k.next();
                           if (chunk2 instanceof ER64Chunk)
                           {
                              isER64ChunkExists = true;
                           }
                        }
                        if (isER64ChunkExists)
                        {
                           ERINChunk erinChunk = (ERINChunk) chunk;
                           ER64Chunk er64Chunk = (ER64Chunk) chunk2;
                           er64Chunk.setUserCalled(erinChunk.getUserCalled());
                           er64Chunk.setCurrentProcess(erinChunk.getCurrentProcess());
                           errorBlocks.add(new ErrorBlock(blhdChunk, descs, er64Chunk));
                        }
                        else
                        {
                           ERINChunk erinChunk = (ERINChunk) chunk;
                           errorBlocks.add(new ErrorBlock(blhdChunk, descs, erinChunk));
                        }
                     }
                  }
               }
               else if (blockChunk.getFormType().equals(Chunk.TXBL))
               {
                  List chunks = blockChunk.getChunks();
                  for (Iterator k = chunks.iterator(); k.hasNext();)
                  {
                     // Ignore, only DESC's should be found here.
                     k.next();
                     textBlocks.add(new TextBlock(blhdChunk, descs));
                  }
               }
               else if (blockChunk.getFormType().equals(Chunk.MBL_))
               {
                  List chunks = blockChunk.getChunks();
                  for (Iterator k = chunks.iterator(); k.hasNext();)
                  {
                     Chunk chunk = (Chunk) k.next();
                     if (chunk instanceof MHDChunk)
                     {
                        MHDChunk mhdChunk = (MHDChunk) chunk;

                        // Look for DATA or ZDAT.
                        if (!k.hasNext())
                        {
                           throw new IffException("Invalid IFF PMD format: " +
                                 "Missing DATA/ZDAT chunk in MBL form.");
                        }
                        chunk = (Chunk) k.next();
                        if (chunk instanceof DATAChunk)
                        {
                           DATAChunk dataChunk = (DATAChunk) chunk;
                           memoryBlocks.add(new MemoryBlock(blhdChunk,
                                                            descs,
                                                            mhdChunk,
                                                            dataChunk));
                        }
                        else if ((chunk instanceof FORMChunk) &&
                                 chunk.getFormType().equals(Chunk.ZDAT))
                        {
                           IffId[] zdefSpec;
                           IffId[] dataSpec;
                           List zdefList;
                           List dataList;

                           zdefSpec = new IffId[] {IffId.FORM, Chunk.ZDAT, Chunk.ZDEF};
                           dataSpec = new IffId[] {IffId.FORM, Chunk.ZDAT, Chunk.DATA};
                           zdefList = chunk.getMatchingChunks(zdefSpec);
                           dataList = chunk.getMatchingChunks(dataSpec);

                           if ((zdefList.size() == 1) && (dataList.size() == 1))
                           {
                              ZDEFChunk zdefChunk = (ZDEFChunk) zdefList.get(0);
                              DATAChunk dataChunk = (DATAChunk) dataList.get(0);
                              memoryBlocks.add(new MemoryBlock(blhdChunk,
                                                               descs,
                                                               mhdChunk,
                                                               zdefChunk,
                                                               dataChunk));
                           }
                        }
                     }
                  }
               }
               else if (blockChunk.getFormType().equals(Chunk.MBL2))
               {
                  List chunks = blockChunk.getChunks();
                  for (Iterator k = chunks.iterator(); k.hasNext();)
                  {
                     Chunk chunk = (Chunk) k.next();
                     if (chunk instanceof MHD2Chunk)
                     {
                        MHD2Chunk mhd2Chunk = (MHD2Chunk) chunk;

                        // Look for DATA or ZDAT.
                        if (!k.hasNext())
                        {
                           throw new IffException("Invalid IFF PMD format: "
                                 + "Missing DATA/ZDAT chunk in MBL form.");
                        }
                        chunk = (Chunk) k.next();
                        if (chunk instanceof DATAChunk)
                        {
                           DATAChunk dataChunk = (DATAChunk) chunk;
                           memoryBlocks.add(new MemoryBlock(blhdChunk,
                                                            descs,
                                                            mhd2Chunk,
                                                            dataChunk));
                        }
                        else if ((chunk instanceof FORMChunk)&&
                                 chunk.getFormType().equals(Chunk.ZDAT))
                        {
                           IffId[] zdefSpec;
                           IffId[] dataSpec;
                           List zdefList;
                           List dataList;

                           zdefSpec = new IffId[] {IffId.FORM, Chunk.ZDAT, Chunk.ZDEF};
                           dataSpec = new IffId[] {IffId.FORM, Chunk.ZDAT, Chunk.DATA};
                           zdefList = chunk.getMatchingChunks(zdefSpec);
                           dataList = chunk.getMatchingChunks(dataSpec);

                           if ((zdefList.size() == 1) && (dataList.size() == 1))
                           {
                              ZDEFChunk zdefChunk = (ZDEFChunk) zdefList.get(0);
                              DATAChunk dataChunk = (DATAChunk) dataList.get(0);
                              memoryBlocks.add(new MemoryBlock(blhdChunk,
                                                               descs,
                                                               mhd2Chunk,
                                                               zdefChunk,
                                                               dataChunk));
                           }
                        }
                     }
                  }
               }
               else if (blockChunk.getFormType().equals(Chunk.SGBL))
               {
                  List chunks = blockChunk.getChunks();
                  for (Iterator k = chunks.iterator(); k.hasNext();)
                  {
                     Chunk chunk = (Chunk) k.next();
                     if (chunk instanceof SGHDChunk)
                     {
                        SGHDChunk sghdChunk = (SGHDChunk) chunk;

                        // Look for DATA or ZDAT.
                        if (!k.hasNext())
                        {
                           throw new IffException("Invalid IFF PMD format: " +
                                 "Missing DATA/ZDAT chunk in SGBL form.");
                        }
                        chunk = (Chunk) k.next();
                        if (chunk instanceof DATAChunk)
                        {
                           DATAChunk dataChunk = (DATAChunk) chunk;
                           signalBlocks.add(new SignalBlock(blhdChunk,
                                                            sghdChunk,
                                                            dataChunk));
                        }
                        else if ((chunk instanceof FORMChunk) &&
                                 chunk.getFormType().equals(Chunk.ZDAT))
                        {
                           IffId[] zdefSpec;
                           IffId[] dataSpec;
                           List zdefList;
                           List dataList;

                           zdefSpec = new IffId[] {IffId.FORM, Chunk.ZDAT, Chunk.ZDEF};
                           dataSpec = new IffId[] {IffId.FORM, Chunk.ZDAT, Chunk.DATA};
                           zdefList = chunk.getMatchingChunks(zdefSpec);
                           dataList = chunk.getMatchingChunks(dataSpec);

                           if ((zdefList.size() == 1) && (dataList.size() == 1))
                           {
                              ZDEFChunk zdefChunk = (ZDEFChunk) zdefList.get(0);
                              DATAChunk dataChunk = (DATAChunk) dataList.get(0);
                              signalBlocks.add(new SignalBlock(blhdChunk,
                                                               sghdChunk,
                                                               zdefChunk,
                                                               dataChunk));
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   // Update the comment list if following chunks are of type DESC.
   private static String[] updateComments(String[] descs, ListIterator i)
         throws IffException
   {
      List newDescs = null;

      while (i.hasNext())
      {
         Chunk chunk = (Chunk) i.next();
         if (chunk instanceof DESCChunk)
         {
            if (newDescs == null)
            {
               newDescs = new ArrayList();
            }
            newDescs.addAll(Arrays.asList(chunk.readChunkDataAsText()));
         }
         else
         {
            i.previous();
            break;
         }
      }

      if (newDescs != null)
      {
         return (String[]) newDescs.toArray(new String[0]);
      }
      else
      {
         return descs;
      }
   }
}
