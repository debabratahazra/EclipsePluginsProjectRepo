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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

class Dwarf2Buffer
{
   private final ByteBuffer buffer;

   Dwarf2Buffer(ByteBuffer buffer)
   {
      // The correct buffer endianness
      // is assumed to have been set.
      this.buffer = buffer;
   }

   long peekUWord()
   {
      this.buffer.mark();
      long peekedValue = getUWord();
      this.buffer.reset();
      return peekedValue;
   }

   long getUWord()
   {
      return buffer.getInt() & 0xFFFFFFFFL;
   }

   long getUWord64()
   {
      return buffer.getLong();
   }

   int getUHalf()
   {
      return buffer.getShort() & (int) 0xFFFF;
   }

   short getUByte()
   {
      return (short) (buffer.get() & 0xFF);
   }

   byte getSByte()
   {
      return buffer.get();
   }

   boolean getBoolean()
   {
      return buffer.get() > 0;
   }

   long get64BitAddress()
   {
      return buffer.getLong();
   }

   long get32BitAddress()
   {
      return buffer.getInt() & 0xFFFFFFFFL;
   }

   String getNBytes(long length)
   {
      byte[] chars = new byte[(int) length];
      for (int i = 0; i < length; i++)
      {
         chars[i] = buffer.get();
      }
      try
      {
         return new String(chars, "ISO8859-1");
      }
      catch (UnsupportedEncodingException e)
      {
         e.printStackTrace();
         return null;
      }
   }

   String getString()
   {
      // Set a mark here at the beginning of the string,
      // so that we can return to it.
      buffer.mark();

      // Count the number of bytes until we get the null terminator.
      int length = 0;
      while (buffer.get() != 0)
      {
         length++;
      }

      // Now go back to the beginning of the string,
      // where the mark was set.
      buffer.reset();

      // Create a byte array that is sufficiently large to store
      // the string. Then read the same bytes again, into the array.
      byte[] chars = new byte[length];
      for (int i = 0; i < length; i++)
      {
         chars[i] = buffer.get();
      }

      // Read null terminator, just to get past it.
      buffer.get();

      try
      {
         // Create a string using a Latin-1 encoding.
         // FIXME: What encoding should be used?
         // What if the file names are in a non-Latin-1 language?
         return new String(chars, "ISO8859-1");
      }
      catch (UnsupportedEncodingException e)
      {
         e.printStackTrace();
         return null;
      }
   }

   long getULeb128()
   {
      // The calculations are described in the specification.
      long val = 0;
      byte b;
      int shift = 0;

      while (true)
      {
         b = buffer.get();
         val |= (b & 0x7f) << shift;
         if ((b & 0x80) == 0)
         {
            break;
         }
         shift += 7;
      }

      return val;
   }

   long getSLeb128()
   {
      // The calculations are described in the specification.
      long val = 0;
      int shift = 0;
      byte b;
      int size = 8 << 3;

      while (true)
      {
         b = buffer.get ();
         val |= (b & 0x7f) << shift;
         shift += 7;
         if ((b & 0x80) == 0)
         {
            break;
         }
      }

      if (shift < size && (b & 0x40) != 0)
      {
         val |= -(1 << shift);
      }

      return val;
   }

   Dwarf2Buffer getSubBuffer(int limit)
   {
      // Create a new buffer from current position to the limit.
      ByteBuffer subBuffer = buffer.slice();
      subBuffer.limit(limit);

      // Skip past the extracted part.
      buffer.position(buffer.position() + limit);

      return new Dwarf2Buffer(subBuffer);
   }

   long position()
   {
      return buffer.position();
   }

   void setPosition(long pos)
   {
      buffer.position((int) pos);
   }

   boolean hasMoreData()
   {
      return buffer.remaining() > 0;
   }
}
