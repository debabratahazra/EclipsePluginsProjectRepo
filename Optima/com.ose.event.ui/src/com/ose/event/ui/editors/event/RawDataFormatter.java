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

package com.ose.event.ui.editors.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Formats event data as one or more lines in the following manner:
 *
 * 00000000  00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00  ................\n
 *
 * The first column is the address in hexadecimal form, the following 16 columns
 * are the 16 bytes in hexadecimal form starting at that address, and the last
 * 16 dots are the ASCII characters representing those bytes. Each line is 76
 * characters long including line terminator. If the base address is not 16-byte
 * aligned, the first line is padded out to the nearest smaller 16-byte aligned
 * address.
 */
public class RawDataFormatter
{
   // Multiple of bytes per row.
   private static final int CHUNK_SIZE = 4096;

   private static final char[] NIBBLE_TO_HEX_CHAR =
   {
      '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
   };

   public void format(InputStream in, PrintWriter out, long address)
      throws IOException
   {
      byte[] bytes;
      char[] chars;
      int length;

      // Handle first line if starting at odd address.
      if ((address % 16) != 0)
      {
         int numPadBytes;
         int numRealBytes;
         int j = 0;

         numPadBytes = (int) (address % 16);
         numRealBytes = 16 - numPadBytes;
         bytes = new byte[numRealBytes];
         chars = new char[8 + 2 + 32 + 15 + 2 + 16 + 1];
         Arrays.fill(chars, ' ');
         address &= 0xFFFFFFF0L;

         length = readFully(in, bytes);
         for (int i = 0; i < length; i++)
         {
            byte b;
            char c;

            if (i == 0)
            {
               writeAddress(chars, j, address);
               address += 16;
               j += 8 + 2 + numPadBytes * 3;
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
      chars = new char[(CHUNK_SIZE / 16) * (8 + 2 + 32 + 15 + 2 + 16 + 1)];
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
               writeAddress(chars, j, address);
               address += 16;
               j += 8 + 2;
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

   private static void writeAddress(char[] buf, int offset, long address)
   {
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

   private static boolean isPrintable(char c)
   {
      return ((c >= 0x20) && (c <= 0x7E));
   }
}
