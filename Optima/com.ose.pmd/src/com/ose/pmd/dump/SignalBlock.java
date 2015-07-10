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

public class SignalBlock extends AbstractBlock
{
   private final long requestSigNo;
   private final long status;
   private final long length;
   private final long compressedLength;
   private final boolean compressed;
   private final ZDEFChunk zdefChunk;
   private final DATAChunk dataChunk;

   protected SignalBlock(BLHDChunk blhdChunk,
                         SGHDChunk sghdChunk,
                         DATAChunk dataChunk)
      throws IffException
   {
      super(blhdChunk);
      this.requestSigNo = sghdChunk.getRequestSigNo();
      this.status = sghdChunk.getStatus();
      this.length = sghdChunk.getLength();
      this.compressedLength = dataChunk.getDataSize();
      this.compressed = false;
      this.zdefChunk = null;
      this.dataChunk = dataChunk;
   }

   protected SignalBlock(BLHDChunk blhdChunk,
                         SGHDChunk sghdChunk,
                         ZDEFChunk zdefChunk,
                         DATAChunk dataChunk)
      throws IffException
   {
      super(blhdChunk);
      this.requestSigNo = sghdChunk.getRequestSigNo();
      this.status = sghdChunk.getStatus();
      this.length = sghdChunk.getLength();
      this.compressedLength =
         ((zdefChunk == null) ? dataChunk.getDataSize() : zdefChunk.getImageLength());
      this.compressed =
         ((zdefChunk != null) && (zdefChunk.getCompression() != ZDEFChunk.NONE));
      this.zdefChunk = zdefChunk;
      this.dataChunk = dataChunk;
   }

   public long getRequestSigNo()
   {
      return requestSigNo;
   }

   public long getStatus()
   {
      return status;
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
}
