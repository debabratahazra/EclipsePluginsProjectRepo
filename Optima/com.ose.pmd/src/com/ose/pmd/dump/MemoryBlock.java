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

import java.io.IOException;
import java.io.InputStream;

public class MemoryBlock extends AbstractBlock
{
   private final String[] descs;
   private final long startAddress;
   private final long length;
   private final long compressedLength;
   private final boolean compressed;
   private final ZDEFChunk zdefChunk;
   private final DATAChunk dataChunk;
   private final boolean is64BitFormat;

   protected MemoryBlock(BLHDChunk blhdChunk,
                         String[] descs,
                         MHDChunk mhdChunk,
                         DATAChunk dataChunk)
      throws IffException
   {
      super(blhdChunk);
      this.is64BitFormat = false;
      this.descs = descs;
      this.startAddress = mhdChunk.getStartAddress();
      this.length = mhdChunk.getLength();
      this.compressedLength = dataChunk.getDataSize();
      this.compressed = false;
      this.zdefChunk = null;
      this.dataChunk = dataChunk;
   }

   protected MemoryBlock(BLHDChunk blhdChunk,
                         String[] descs,
                         MHDChunk mhdChunk,
                         ZDEFChunk zdefChunk,
                         DATAChunk dataChunk)
      throws IffException
   {
      super(blhdChunk);
      this.is64BitFormat = false;
      this.descs = descs;
      this.startAddress = mhdChunk.getStartAddress();
      this.length = mhdChunk.getLength();
      this.compressedLength =
         ((zdefChunk == null) ? dataChunk.getDataSize() : zdefChunk.getImageLength());
      this.compressed =
         ((zdefChunk != null) && (zdefChunk.getCompression() != ZDEFChunk.NONE));
      this.zdefChunk = zdefChunk;
      this.dataChunk = dataChunk;
   }

   protected MemoryBlock(BLHDChunk blhdChunk,
           				 String[] descs,
           				 MHD2Chunk mhd2Chunk,
           				 DATAChunk dataChunk)
	   throws IffException
	{
      super(blhdChunk);
      this.is64BitFormat = true;
      this.descs = descs;
      this.startAddress = mhd2Chunk.getStartAddress();
      this.length = mhd2Chunk.getLength();
      this.compressedLength = dataChunk.getDataSize();
      this.compressed = false;
      this.zdefChunk = null;
      this.dataChunk = dataChunk;
	}

   protected MemoryBlock(BLHDChunk blhdChunk,
           				String[] descs,
           				MHD2Chunk mhd2Chunk,
           				ZDEFChunk zdefChunk,
           				DATAChunk dataChunk)
	   throws IffException
	{
      super(blhdChunk);
      this.is64BitFormat = true;
      this.descs = descs;
      this.startAddress = mhd2Chunk.getStartAddress();
      this.length = mhd2Chunk.getLength();
      this.compressedLength = ((zdefChunk == null) ? dataChunk.getDataSize()
            : zdefChunk.getImageLength());
      this.compressed = ((zdefChunk != null) && (zdefChunk.getCompression() != ZDEFChunk.NONE));
      this.zdefChunk = zdefChunk;
      this.dataChunk = dataChunk;
	}
   
   public String[] getDescriptions()
   {
      return descs;
   }

   public long getStartAddress()
   {
      return startAddress;
   }

   public long getLength()
   {
      return length;
   }

   public long getCompressedLength()
   {
      return compressedLength;
   }

   public boolean isCompressed()
   {
      return compressed;
   }

   public InputStream getInputStream() throws IffException
   {
      if (zdefChunk == null)
      {
         return dataChunk.getUncompressedInputStream();
      }
      else
      {
         switch ((int) zdefChunk.getCompression())
         {
            case ZDEFChunk.DEFLATED:
               return dataChunk.getInflaterInputStream();
            case ZDEFChunk.NONE:
               return dataChunk.getUncompressedInputStream();
            default:
               throw new IffException("Unsupported compression method");
         }
      }
   }

   public int read(byte[] b, int srcOffset, int len)
      throws IOException, IffException
   {
      if (zdefChunk == null)
      {
         return dataChunk.read(b, DATAChunk.DATA_OFFSET + srcOffset, len);
      }
      else
      {
         switch ((int) zdefChunk.getCompression())
         {
            case ZDEFChunk.DEFLATED:
               return dataChunk.inflate(b, srcOffset, len);
            case ZDEFChunk.NONE:
               return dataChunk.read(b, DATAChunk.DATA_OFFSET + srcOffset, len);
            default:
               throw new IffException("Unsupported compression method");
         }
      }
   }
   
   public boolean is64Bit()
   {
	   return is64BitFormat;
   }
   
}
