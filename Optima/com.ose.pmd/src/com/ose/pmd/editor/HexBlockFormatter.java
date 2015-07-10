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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import com.ose.pmd.dump.Chunk;

/**
 * Formats a block as one or more lines in the following manner:
 * <p>
 * 00000000  00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................\n
 * <p>
 * The first column is the address in hexadecimal form, the following 16 columns
 * are the 16 bytes in hexadecimal form starting at that address, and the last
 * 16 dots are the ASCII characters representing those bytes. Each line is 76
 * characters long including line terminator. If the base address is not 16-byte
 * aligned, the first line is padded out to the nearest smaller 16-byte aligned
 * address.
 */
public class HexBlockFormatter implements IBlockFormatter
{
   // Multiple of bytes per row.
   private static final int CHUNK_SIZE = 4096;
   
   private static final int ADDRESS_SIZE_FOR_32BIT = 8;

   private static final int ADDRESS_SIZE_FOR_64BIT = 16;

   private static final char[] NIBBLE_TO_HEX_CHAR =
   {
      '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
   };

   /*
    * @see com.ose.pmd.editor.IBlockFormatter#format(java.io.InputStream,
    * java.io.PrintWriter, long, long, boolean)
    */
   public void format(InputStream in, PrintWriter out, long address, long size, boolean bigEndian)
      throws IOException
   {
      byte[] bytes;
      char[] chars;
      int length;
      int addressSize = ADDRESS_SIZE_FOR_32BIT;
      boolean is64Bit = false;
      
      if (is64BitAddress(address, size))
      {
    	  addressSize = ADDRESS_SIZE_FOR_64BIT;
    	  is64Bit = true;
      }
      
      // Handle first line if starting at odd address.
      if ((address % 16) != 0)
      {
         int numPadBytes;
         int numRealBytes;
         int j = 0;

         numPadBytes = (int) (address % 16);
         numRealBytes = 16 - numPadBytes;
         bytes = new byte[numRealBytes];
         chars = new char[addressSize + 2 + 32 + 15 + 2 + 16 + 1];
         Arrays.fill(chars, ' ');
         address &= 0xFFFFFFF0L;

         length = readFully(in, bytes);
         for (int i = 0; i < length; i++)
         {
            byte b;
            char c;

            if (i == 0)
            {
               writeAddress(chars, j, address, is64Bit);
               address += 16;
               j += addressSize + 2 + numPadBytes * 3;
            }

            b = bytes[i];
            c = (char) (b & 0xFF);
            chars[j] = NIBBLE_TO_HEX_CHAR[(b >> 4) & 0x0F];
            chars[j + 1] = NIBBLE_TO_HEX_CHAR[b & 0x0F];
            chars[j + 32 + 15 + 2 - ((i + numPadBytes) % 16) * 2] =
               (isPrintable(c) ? c : '.');
            j += 3;

            if (i == (length - 1))
            {
               j = chars.length - 1;
               chars[j++] = '\n';
            }
         }

         out.write(chars, 0, j);
      }

      // Handle all other lines.
      bytes = new byte[CHUNK_SIZE];
      chars = new char[(CHUNK_SIZE / 16) * (addressSize + 2 + 32 + 15 + 2 + 16 + 1)];
      Arrays.fill(chars, ' ');

      while ((length = readFully(in, bytes)) != -1)
      {
         int j = 0;
         int remainder;

         for (int i = 0; i < length; i++)
         {
            byte b;
            char c;

            if ((i % 16) == 0)
            {
               writeAddress(chars, j, address, is64Bit);
               address += 16;
               j += addressSize + 2;
            }

            b = bytes[i];
            c = (char) (b & 0xFF);
            chars[j] = NIBBLE_TO_HEX_CHAR[(b >> 4) & 0x0F];
            chars[j + 1] = NIBBLE_TO_HEX_CHAR[b & 0x0F];
            chars[j + 32 + 15 + 2 - (i % 16) * 2] = (isPrintable(c) ? c : '.');
            j += 3;

            if (((i + 1) % 16) == 0)
            {
               j += 2 - 1 + 16;
               chars[j++] = '\n';
            }
         }

         remainder = length % 16;
         if (remainder != 0)
         {
            // Clear trail of non-full last line.
            Arrays.fill(chars, j, j + (16 - remainder) * 3, ' ');
            Arrays.fill(chars, j + (16 - remainder) * 3 - 1 + 2 + remainder,
                        j + (16 - remainder) * 3 - 1 + 2 + 16, ' ');
            j += (16 - remainder) * 3 - 1 + 2 + 16;
            chars[j++] = '\n';
         }

         out.write(chars, 0, j);
      }
   }

   private static int readFully(InputStream in, byte[] buf) throws IOException
   {
      int length = buf.length;
      int n = 0;

      while (n < length)
      {
         int count = in.read(buf, n, length - n);
         if (count < 0)
         {
            return ((n == 0) ? -1 : n);
         }
         n += count;
      }

      return n;
   }

   private static void writeAddress(char[] buf, int offset, long address, boolean is64Bit)
   {
	  if (is64Bit)
	  {
		  write64BitAddress(buf, offset, address);
		  return;
	  }
      int i = (int) address;
      buf[offset + 0] = NIBBLE_TO_HEX_CHAR[(i >>> 28) & 0x0F];
      buf[offset + 1] = NIBBLE_TO_HEX_CHAR[(i >>> 24) & 0x0F];
      buf[offset + 2] = NIBBLE_TO_HEX_CHAR[(i >>> 20) & 0x0F];
      buf[offset + 3] = NIBBLE_TO_HEX_CHAR[(i >>> 16) & 0x0F];
      buf[offset + 4] = NIBBLE_TO_HEX_CHAR[(i >>> 12) & 0x0F];
      buf[offset + 5] = NIBBLE_TO_HEX_CHAR[(i >>>  8) & 0x0F];
      buf[offset + 6] = NIBBLE_TO_HEX_CHAR[(i >>>  4) & 0x0F];
      buf[offset + 7] = NIBBLE_TO_HEX_CHAR[(i >>>  0) & 0x0F];
   }

   private static void write64BitAddress(char[] buf, int offset, long address)
   {
      BigInteger unsigned = new BigInteger(1, Chunk.longToByteArray(address));
      char[] chars = unsigned.toString(16).toUpperCase().toCharArray();
      int length = chars.length;
      int i = 0;
      while (length < 16)
      {
         buf[offset + i] = '0';
         length++;
         i++;
      }
      int k = 0;
      for (int j = i; j < 16; j++)
      {
         buf[offset + j] = chars[k];
         k++;
      }
   }

   private static boolean isPrintable(char c)
   {
      return ((c >= 0x20) && (c <= 0x7E));
   }

   private static boolean is64BitAddress(long address, long size)
   {
      byte input[] = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
      BigInteger unsigned = new BigInteger(1, input);
      BigInteger uAddress = new BigInteger(1, Chunk.longToByteArray(address));
      BigInteger uSize = new BigInteger(1, Chunk.longToByteArray(size));
      if (unsigned.compareTo(uAddress.add(uSize)) < 0)
      {
         return true;
      }
      return false;
   }
}
