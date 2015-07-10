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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class DATAChunk extends Chunk
{
   public static final int DATA_OFFSET = 8;

   private InputStream inflaterInputStream;
   private long inflaterInputStreamPos;

   protected DATAChunk(Chunk genericChunk)
   {
      super(genericChunk.getDumpFile(), genericChunk.getOffset());
   }

   public byte[] getData() throws IffException
   {
      int dataSize = (int) getDataSize();
      byte[] data = new byte[dataSize];

      read(data, DATA_OFFSET, dataSize);
      return data;
   }

   public synchronized int inflate(byte[] b, int srcOffset, int len)
         throws IOException
   {
      int readBytes = 0;

      if ((inflaterInputStream == null) || (srcOffset < inflaterInputStreamPos))
      {
         inflaterInputStream = getInflaterInputStream();
         inflaterInputStreamPos = 0;
      }

      inflaterInputStreamPos +=
         inflaterInputStream.skip(srcOffset - inflaterInputStreamPos);
      readBytes = inflaterInputStream.read(b, 0, len);
      inflaterInputStreamPos += readBytes;
      return readBytes;
   }

   public InputStream getInflaterInputStream()
   {
      return new BufferedInputStream(new InflaterInputStream(new DATAInputStream()));
   }

   public InputStream getUncompressedInputStream()
   {
      return new BufferedInputStream(new DATAInputStream());
   }

   private class DATAInputStream extends InputStream
   {
      private long pos = 0;

      public int read() throws IOException
      {
         try
         {
            if (pos >= getDataSize())
            {
               return -1;
            }
            else
            {
               byte b[] = new byte[1];
               int ret;

               DATAChunk.this.read(b, (int) pos + DATA_OFFSET, 1);
               pos++;

               ret = b[0];
               if (ret < 0)
               {
                  ret += 256;
               }
               return ret;
            }
         }
         catch (IffException e)
         {
            throw new IOException(e.getMessage());
         }
      }

      public int read(byte[] b, int off, int len) throws IOException
      {
         if (off < 0 || len < 0 || (off + len > b.length))
         {
            throw new IndexOutOfBoundsException();
         }

         try
         {
            int newLen;
            byte[] b1;

            if (pos >= getDataSize())
            {
               return -1;
            }

            newLen = (int) Math.min(len, getDataSize() - pos);
            b1 = new byte[newLen];

            DATAChunk.this.read(b1, (int) pos + DATA_OFFSET, newLen);
            pos += newLen;

            System.arraycopy(b1, 0, b, off, newLen);

            return newLen;
         }
         catch (IffException e)
         {
            throw new IOException(e.getMessage());
         }
      }

      public int available() throws IOException
      {
         try
         {
            return (int) (getDataSize() - pos);
         }
         catch (IffException e)
         {
            throw new IOException(e.getMessage());
         }
      }
   }
}
