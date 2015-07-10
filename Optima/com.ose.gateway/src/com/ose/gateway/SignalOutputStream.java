/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.gateway;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A signal output stream is used for writing the fields of an OSE signal. The
 * table below shows the supported OSE data types and their corresponding Java
 * data types.
 * <p>
 * <pre>    OSE      Java
 * ---------  -------
 * S8         byte
 * U8         int
 * S16        short
 * U16        int
 * S32        int
 * U32        long
 * S64        long
 * S8[]       byte[]
 * U8[]       int[]
 * S16[]      short[]
 * U16[]      int[]
 * S32[]      int[]
 * U32[]      long[]
 * S64[]      long[]
 * OSBOOLEAN  boolean
 * char[]     String</pre>
 * <p>
 * A signal output stream is created with a specified byte ordering; big endian
 * or little endian. The data is written with respect to the byte ordering and
 * aligned according to the size of the corresponding OSE data type.
 * After the last data item is written the <code>flush</code> method should be
 * called to pad the signal to the alignment requirements of the most stringent
 * OSE data type written.
 * <p>
 * The following code snippet is an example of using the SignalOutputStream
 * class:
 * <p>
 * <pre> ...
 * ByteArrayOutputStream bout = new ByteArrayOutputStream();
 * SignalOutputStream out =
 *     new SignalOutputStream(bout, SignalOutputStream.BIG_ENDIAN);
 * ...
 * out.writeS32(...);
 * ...
 * out.flush();
 * byte[] signalData = bout.toByteArray();
 * // Send signalData as a signal
 * ...</pre>
 */
public class SignalOutputStream extends FilterOutputStream
{
   /** Constant for big endian byte ordering. */
   public static final boolean BIG_ENDIAN = true;
    
   /** Constant for little endian byte ordering. */
   public static final boolean LITTLE_ENDIAN = false;
    
   /**
    * The next write position in this signal output stream, and thus
    * the number of bytes written to this signal output stream.
    */
   protected int writepos;
    
   /**
    * The alignment requirements of the most stringent data type written.
    */
   protected int signalAlignment;
    
   /**
    * The byte order; big endian if <code>true</code> or little endian if
    * <code>false</code>.
    */
   protected boolean bigEndian;
    
   /**
    * Create a new signal output stream to write data to the specified
    * underlying output stream in big endian byte ordering.
    *
    * @param  out  the underlying output stream.
    */
   public SignalOutputStream(OutputStream out)
   {
      this(out, BIG_ENDIAN);
   }
    
   /**
    * Create a new signal output stream to write data to the specified
    * underlying output stream in the specified byte ordering.
    *
    * @param  out        the underlying output stream.
    * @param  bigEndian  big endian byte order if <code>true</code>, little
    *         endian byte order if <code>false</code>.
    */
   public SignalOutputStream(OutputStream out, boolean bigEndian)
   {
      super(out);
      this.bigEndian = bigEndian;
      // Account for the signal number.
      signalAlignment = 4;
   }
    
   /**
    * Write the specified byte to the underlying output stream.
    *
    * @param      b  the <code>byte</code> to be written.
    * @exception  IOException  if an I/O error occurs.
    */
   public synchronized void write(int b) throws IOException
   {
      out.write(b);
      writepos++;
   }

   /**
    * Write <code>len</code> bytes from the specified byte array
    * <code>b</code> starting at offset <code>off</code> to the underlying
    * output stream.
    *
    * @param      b    the data.
    * @param      off  the start offset in the data.
    * @param      len  the number of bytes to write.
    * @exception  IOException  if an I/O error occurs.
    */
   public synchronized void write(byte[] b, int off, int len) throws IOException
   {
      out.write(b, off, len);
      writepos += len;
   }

   /**
    * Write a <code>boolean</code> to the underlying output stream as a
    * 1-byte value. The value <code>true</code> is written out as the value
    * <code>(byte)1</code>; the value <code>false</code> is written out as
    * the value <code>(byte)0</code>.
    *
    * @param      v  a <code>boolean</code> value to be written.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeOSBOOLEAN(boolean v) throws IOException
   {
      out.write(v ? 1 : 0);
      writepos++;
   }

   /**
    * Write a <code>boolean</code> to the underlying output stream as a
    * 4-byte value with respect to the byte ordering. The value
    * <code>true</code> is written out as the value <code>(int)1</code>;
    * the value <code>false</code> is written out as the value
    * <code>(int)0</code>. The written value is aligned on a 4-byte boundary.
    *
    * @param      v  a <code>boolean</code> value to be written.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeBoolean(boolean v) throws IOException
   {
      writeS32(v ? 1 : 0);
   }

   /**
    * Write a <code>byte</code> interpreted as a S8 to the underlying
    * output stream as one byte.
    *
    * @param      v  a <code>byte</code> interpreted as a S8.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS8(byte v) throws IOException
   {
      out.write(v);
      writepos++;
   }
    
   /**
    * Write an <code>int</code> interpreted as an U8 to the underlying
    * output stream as one byte.
    *
    * @param      v  an <code>int</code> interpreted as an U8.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeU8(int v) throws IOException
   {
      out.write(v);
      writepos++;
   }

   /**
    * Write a <code>short</code> interpreted as a S16 to the underlying
    * output stream as two bytes with respect to the byte ordering. The
    * written S16 is aligned on a 2-byte boundary.
    *
    * @param      v  a <code>short</code> interpreted as a S16.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS16(short v) throws IOException
   {
      align(2);
      if (bigEndian)
      {
         out.write((v >>> 8) & 0xFF);
         out.write((v >>> 0) & 0xFF);
      }
      else
      {
         out.write((v >>> 0) & 0xFF);
         out.write((v >>> 8) & 0xFF);
      }
      writepos += 2;
   }
    
   /**
    * Write an <code>int</code> interpreted as an U16 to the underlying
    * output stream as two bytes with respect to the byte ordering. The
    * written U16 is aligned on a 2-byte boundary.
    *
    * @param      v  an <code>int</code> interpreted as an U16.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeU16(int v) throws IOException
   {
      writeS16((short) v);
   }
    
   /**
    * Write an <code>int</code> interpreted as a S32 to the underlying
    * output stream as four bytes with respect to the byte ordering. The
    * written S32 is aligned on a 4-byte boundary.
    *
    * @param      v  an <code>int</code> interpreted as a S32.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS32(int v) throws IOException
   {
      align(4);
      if (bigEndian)
      {
         out.write((v >>> 24) & 0xFF);
         out.write((v >>> 16) & 0xFF);
         out.write((v >>>  8) & 0xFF);
         out.write((v >>>  0) & 0xFF);
      }
      else
      {
         out.write((v >>>  0) & 0xFF);
         out.write((v >>>  8) & 0xFF);
         out.write((v >>> 16) & 0xFF);
         out.write((v >>> 24) & 0xFF);
      }
      writepos += 4;
   }
    
   /**
    * Write a <code>long</code> interpreted as an U32 to the underlying
    * output stream as four bytes with respect to the byte ordering. The
    * written U32 is aligned on a 4-byte boundary.
    *
    * @param      v  a <code>long</code> interpreted as an U32.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeU32(long v) throws IOException
   {
      writeS32((int) v);
   }

   /**
    * Write a <code>long</code> interpreted as a S64 to the underlying
    * output stream as eight bytes with respect to the byte ordering. The
    * written S64 is aligned on a 8-byte boundary.
    *
    * @param      v  a <code>long</code> interpreted as a S64.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS64(long v) throws IOException
   {
      align(8);
      if (bigEndian)
      {
         out.write(((int) (v >>> 56)) & 0xFF);
         out.write(((int) (v >>> 48)) & 0xFF);
         out.write(((int) (v >>> 40)) & 0xFF);
         out.write(((int) (v >>> 32)) & 0xFF);
         out.write(((int) (v >>> 24)) & 0xFF);
         out.write(((int) (v >>> 16)) & 0xFF);
         out.write(((int) (v >>>  8)) & 0xFF);
         out.write(((int) (v >>>  0)) & 0xFF);
      }
      else
      {
         out.write(((int) (v >>>  0)) & 0xFF);
         out.write(((int) (v >>>  8)) & 0xFF);
         out.write(((int) (v >>> 16)) & 0xFF);
         out.write(((int) (v >>> 24)) & 0xFF);
         out.write(((int) (v >>> 32)) & 0xFF);
         out.write(((int) (v >>> 40)) & 0xFF);
         out.write(((int) (v >>> 48)) & 0xFF);
         out.write(((int) (v >>> 56)) & 0xFF);
      }
      writepos += 8;
      if (signalAlignment < 8)
      {
         signalAlignment = 8;
      }
   }

   /**
    * Write a string to the underlying output stream as a null-terminated
    * sequence of bytes. Each character in the string is written out, in
    * sequence, by discarding its high eight bits.
    *
    * @param      s  a string of bytes to be written.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeString(String s) throws IOException
   {
      int len = s.length();
      for (int i = 0; i < len; i++)
      {
         out.write((byte) s.charAt(i));
      }
      out.write(0);
      writepos += (len + 1);
   }

   /**
    * Write a string to the underlying output stream as a null-terminated
    * sequence of bytes. Each character in the string is written out, in
    * sequence, by discarding its high eight bits.
    * <p>
    * If the length of the string plus a terminating null byte is less than
    * the specified size then extra null bytes are written after the string
    * so that exactly <code>size</code> bytes are written.
    * <p>
    * If the length of the string plus a terminating null byte is greater than
    * the specified size then the string is truncated at the end so that exactly
    * <code>size</code> bytes are written including a terminating null byte.
    *
    * @param      s     a string of bytes to be written.
    * @param      size  the total number of bytes to be written.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeString(String s, int size) throws IOException
   {
      int len = s.length();

      if (size < 1) return;
      for (int i = 0; i < (size - 1); i++)
      {
         if (i < len)
         {
            out.write((byte) s.charAt(i));
         }
         else
         {
            out.write(0);
         }
      }
      out.write(0);
      writepos += size;
   }

   /**
    * Write a <code>byte</code> array interpreted as a S8 array
    * to the underlying output stream. Each S8 value is written as
    * one byte.
    *
    * @param      v  a <code>byte</code> array interpreted as a S8 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS8Array(byte[] v) throws IOException
   {
      out.write(v);
      writepos += v.length;
   }
    
   /**
    * Write an <code>int</code> array interpreted as an U8 array
    * to the underlying output stream. Each U8 value is written as
    * one byte.
    *
    * @param      v  an <code>int</code> array interpreted as an U8 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeU8Array(int[] v) throws IOException
   {
      for (int i = 0; i < v.length; i++)
      {
         writeU8(v[i]);
      }
   }
    
   /**
    * Write a <code>short</code> array interpreted as a S16 array
    * to the underlying output stream. Each S16 value is written as
    * two bytes with respect to the byte ordering and aligned on a
    * 2-byte boundary.
    *
    * @param      v  a <code>short</code> array interpreted as a S16 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS16Array(short[] v) throws IOException
   {
      for (int i = 0; i < v.length; i++)
      {
         writeS16(v[i]);
      }
   }
    
   /**
    * Write an <code>int</code> array interpreted as an U16 array
    * to the underlying output stream. Each U16 value is written as
    * two bytes with respect to the byte ordering and aligned on a
    * 2-byte boundary.
    *
    * @param      v  an <code>int</code> array interpreted as an U16 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeU16Array(int[] v) throws IOException
   {
      for (int i = 0; i < v.length; i++)
      {
         writeU16(v[i]);
      }
   }
    
   /**
    * Write an <code>int</code> array interpreted as a S32 array
    * to the underlying output stream. Each S32 value is written as
    * four bytes with respect to the byte ordering and aligned on a
    * 4-byte boundary.
    *
    * @param      v  an <code>int</code> array interpreted as a S32 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS32Array(int[] v) throws IOException
   {
      for (int i = 0; i < v.length; i++)
      {
         writeS32(v[i]);
      }
   }
    
   /**
    * Write a <code>long</code> array interpreted as an U32 array
    * to the underlying output stream. Each U32 value is written as
    * four bytes with respect to the byte ordering and aligned on a
    * 4-byte boundary.
    *
    * @param      v  a <code>long</code> array interpreted as an U32 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeU32Array(long[] v) throws IOException
   {
      for (int i = 0; i < v.length; i++)
      {
         writeU32(v[i]);
      }
   }

   /**
    * Write a <code>long</code> array interpreted as a S64 array
    * to the underlying output stream. Each S64 value is written as
    * eight bytes with respect to the byte ordering and aligned on a
    * 8-byte boundary.
    *
    * @param      v  a <code>long</code> array interpreted as a S64 array.
    * @exception  IOException  if an I/O error occurs.
    */
   public void writeS64Array(long[] v) throws IOException
   {
      for (int i = 0; i < v.length; i++)
      {
         writeS64(v[i]);
      }
   }

   /**
    * Align the write position in this signal output stream to a position
    * that is a multiple of the specified even alignment requirement. Any
    * skipped positions are padded with null bytes.
    *
    * @param      n  the even alignment requirement.
    * @exception  IOException  if an I/O error occurs.
    */
   public void align(int n) throws IOException
   {
      if ((n <= 0) || ((n & 1) != 0))
      {
         return;
      }
      // Account for the signal number as well.
      int padding = (n - (writepos + 4) & (n - 1)) % n;
      for (int i = 0; i < padding; i++)
      {
         out.write(0);
         writepos++;
      }
   }

   /**
    * Flush this signal output stream. This forces any buffered output bytes
    * to be written out to the stream.
    * <p>
    * The <code>flush</code> method of <code>SignalOutputStream</code> pads
    * the signal according to the alignment requirements of the most stringent
    * OSE data type written, and then calls the <code>flush</code> method of
    * its underlying output stream. It is very important to call this method
    * after the last data item has been written.
    *
    * @exception  IOException  if an I/O error occurs.
    */
   public void flush() throws IOException
   {
      align(signalAlignment);
      out.flush();
   }
    
   /**
    * Return the number of bytes written to this signal output stream.
    *
    * @return  the value of the <code>writepos</code> field.
    */
   public int size()
   {
      return writepos;
   }
    
   /**
    * Determine whether this signal output stream uses big endian or little
    * endian byte ordering.
    *
    * @return  <code>true</code> if big endian byte order,
    <code>false</code> if little endian byte order.
   */
   public boolean isBigEndian()
   {
      return bigEndian;
   }
}
