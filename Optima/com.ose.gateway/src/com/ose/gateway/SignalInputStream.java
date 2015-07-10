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

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * A signal input stream is used for reading the fields of an OSE signal. The
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
 * A signal input stream is created with a specified byte ordering; big endian
 * or little endian. The data is read with respect to the byte ordering and the
 * alignment requirements of the corresponding OSE data type.
 * <p>
 * The following code snippet is an example of using the SignalInputStream
 * class:
 * <pre> ...
 * byte[] signalData;
 * // Receive a signal and assign it to signalData
 * ByteArrayInputStream bin = new ByteArrayInputStream(signalData);
 * SignalInputStream in =
 *     new SignalInputStream(bin, SignalInputStream.BIG_ENDIAN);
 * ...
 * int someValue = in.readS32();
 * ...</pre>
 */
public class SignalInputStream extends FilterInputStream
{
   /** Constant for big endian byte ordering. */
   public static final boolean BIG_ENDIAN = true;
    
   /** Constant for little endian byte ordering. */
   public static final boolean LITTLE_ENDIAN = false;

   /** EOFException error message. */
   protected static final String EOF_ERROR_MSG =
      "Premature end of signal input stream.";

   /**
    * The next read position in this signal input stream, and thus
    * the number of bytes read from this signal input stream.
    */
   protected int readpos;
    
   /**
    * The mark position.
    */
   protected int markpos;
    
   /**
    * The byte order; big endian if <code>true</code> and little endian if
    * <code>false</code>.
    */
   protected boolean bigEndian;
    
   /**
    * Create a new signal input stream to read data from the specified input
    * stream in big endian byte ordering.
    *
    * @param  in  the input stream.
    */
   public SignalInputStream(InputStream in)
   {
      this(in, BIG_ENDIAN);
   }
    
   /**
    * Create a new signal input stream to read data from the specified input
    * stream in the specified byte ordering.
    *
    * @param  in  the input stream.
    * @param  bigEndian  big endian byte order if <code>true</code>, little
    *         endian byte order if <code>false</code>.
    */
   public SignalInputStream(InputStream in, boolean bigEndian)
   {
      super(in);
      this.bigEndian = bigEndian;
   }

   /**
    * Read the next byte of data from this signal input stream. The byte is
    * returned as an <code>int</code> in the range <code>0</code> to
    * <code>255</code>. If no byte is available because the end of the stream
    * has been reached, the value <code>-1</code> is returned. This method
    * blocks until either input data is available, the end of the stream is
    * detected, or an exception is thrown.
    * <p>
    * The <code>read</code> method of <code>SignalInputStream</code> calls the
    * <code>read</code> method of its underlying input stream and returns
    * whatever value that method returns.
    *
    * @return     the next byte of data, or <code>-1</code> if the end of the
    *             stream is reached.
    * @exception  IOException  if an I/O error occurs.
    */
   public int read() throws IOException
   {
      int result = in.read();
      if (result != -1)
      {
         readpos++;
      }
      return result;
   }
    
   /**
    * Read up to <code>b.length</code> bytes of data from this signal input
    * stream into an array of bytes. This method blocks until some input is
    * available.
    * <p>
    * The <code>read</code> method of <code>SignalInputStream</code> calls the
    * <code>read</code> method of its underlying input stream with the three
    * arguments <code>b</code>, <code>0</code>, and <code>b.length</code> and
    * returns whatever value that method returns.
    *
    * @param      b  the buffer into which the data is read.
    * @return     the total number of bytes read into the buffer, or
    *             <code>-1</code> if there is no more data because the
    *             end of the stream has been reached.
    * @exception  IOException  if an I/O error occurs.
    */
   public int read(byte[] b) throws IOException
   {
      int count = in.read(b, 0, b.length);
      if (count > 0)
      {
         readpos += count;
      }
      return count;
   }

   /**
    * Read up to <code>len</code> bytes of data from this signal input stream
    * into an array of bytes. This method blocks until some input is
    * available.
    * <p>
    * The <code>read</code> method of <code>SignalInputStream</code> calls the
    * <code>read</code> method of its underlying input stream with the same
    * arguments and returns whatever value that method returns.
    *
    * @param      b    the buffer into which the data is read.
    * @param      off  the start offset of the data.
    * @param      len  the maximum number of bytes read.
    * @return     the total number of bytes read into the buffer, or
    *             <code>-1</code> if there is no more data because the
    *             end of the stream has been reached.
    * @exception  IOException  if an I/O error occurs.
    */
   public int read(byte[] b, int off, int len) throws IOException
   {
      int count = in.read(b, off, len);
      if (count > 0)
      {
         readpos += count;
      }
      return count;
   }

   /**
    * Read <code>b.length</code> bytes of data from this signal input stream
    * into an array of bytes. This method reads repeatedly from the underlying
    * input stream until all the bytes are read. This method blocks until
    * either all the bytes are read, the end of the stream is detected, or an
    * exception is thrown.
    *
    * @param      b  the buffer into which the data is read.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading all the bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public void readFully(byte[] b) throws IOException
   {
      readFully(b, 0, b.length);
   }

   /**
    * Read <code>len</code> bytes of data from this signal input stream into
    * an array of bytes. This method reads repeatedly from the underlying
    * input stream until all the bytes are read. This method blocks until
    * either all the bytes are read, the end of the stream is detected, or an
    * exception is thrown.
    *
    * @param      b    the buffer into which the data is read.
    * @param      off  the start offset of the data.
    * @param      len  the number of bytes to read.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading all the bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public void readFully(byte[] b, int off, int len) throws IOException
   {
      int n = 0;
      while (n < len)
      {
         int count = in.read(b, off + n, len - n);
         if (count < 0)
         {
            throw new EOFException(EOF_ERROR_MSG);
         }
         n += count;
      }
      readpos += len;
   }

   /**
    * Read a <code>boolean</code> value from this signal input stream. This
    * method reads one byte from the underlying input stream. A value of
    * <code>0</code> represents <code>false</code>. Any other value represents
    * <code>true</code>.
    * <p>
    * This method blocks until either the byte is read, the
    * end of the stream is detected, or an exception is thrown.
    *
    * @return     the <code>boolean</code> value read.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public boolean readOSBOOLEAN() throws IOException
   {
      int ch = in.read();
      if (ch < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos++;
      return (ch != 0);
   }

   /**
    * Read a <code>boolean</code> value from this signal input stream. This
    * method reads four bytes from the underlying input stream with respect
    * to the byte ordering and a 4-byte boundary alignment. A value of
    * <code>0</code> represents <code>false</code>. Any other value represents
    * <code>true</code>.
    * <p>
    * This method blocks until either the four bytes are read, the
    * end of the stream is detected, or an exception is thrown.
    *
    * @return     the <code>boolean</code> value read.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public boolean readBoolean() throws IOException
   {
      int v = readS32();
      return (v != 0);
   }

   /**
    * Read a S8 from this signal input stream. This method reads one
    * byte from the underlying input stream and returns the result as
    * a <code>byte</code>.
    * <p>
    * This method blocks until either the byte is read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next byte of this input stream, interpreted as a
    *             S8 and returned as a <code>byte</code>.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public byte readS8() throws IOException
   {
      int ch = in.read();
      if (ch < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos++;
      return ((byte) ch);
   }

   /**
    * Read an U8 from this signal input stream. This method reads one
    * byte from the underlying input stream and returns the result as
    * an <code>int</code> to be interpreted as an U8.
    * <p>
    * This method blocks until either the byte is read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next byte of this input stream, interpreted as an
    *             U8 and returned as an <code>int</code>.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public int readU8() throws IOException
   {
      int ch = in.read();
      if (ch < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos++;
      return ch;
   }

   /**
    * Read a S16 from this signal input stream. This method reads two bytes
    * from the underlying input stream with respect to the byte ordering and a
    * 2-byte boundary alignment and returns the result as a <code>short</code>.
    * <p>
    * This method blocks until either the two bytes are read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next two bytes of this input stream, interpreted as a
    *             S16 and returned as a <code>short</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading two bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public short readS16() throws IOException
   {
      align(2);
      int ch1 = in.read();
      int ch2 = in.read();
      if ((ch1 | ch2) < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos += 2;
      short result;
      if (bigEndian)
      {
         result = (short) ((ch1 << 8) + (ch2 << 0));
      }
      else
      {
         result = (short) ((ch1 << 0) + (ch2 << 8));
      }
      return result;
   }

   /**
    * Read an U16 from this signal input stream. This method reads two bytes
    * from the underlying input stream with respect to the byte ordering and a
    * 2-byte boundary alignment and returns the result as an <code>int</code>
    * to be interpreted as an U16.
    * <p>
    * This method blocks until either the two bytes are read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next two bytes of this input stream, interpreted as an
    *             U16 and returned as an <code>int</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading two bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public int readU16() throws IOException
   {
      align(2);
      int ch1 = in.read();
      int ch2 = in.read();
      if ((ch1 | ch2) < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos += 2;
      int result;
      if (bigEndian)
      {
         result = (ch1 << 8) + (ch2 << 0);
      }
      else
      {
         result = (ch1 << 0) + (ch2 << 8);
      }
      return result;
   }

   /**
    * Read a S32 from this signal input stream. This method reads four bytes
    * from the underlying input stream with respect to the byte ordering and a
    * 4-byte boundary alignment and returns the result as an <code>int</code>.
    * <p>
    * This method blocks until either the four bytes are read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next four bytes of this input stream, interpreted as a
    *             S32 and returned as an <code>int</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading four bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public int readS32() throws IOException
   {
      align(4);
      int ch1 = in.read();
      int ch2 = in.read();
      int ch3 = in.read();
      int ch4 = in.read();
      if ((ch1 | ch2 | ch3 | ch4) < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos += 4;
      int result;
      if (bigEndian)
      {
         result = (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
      }
      else
      {
         result = (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24);
      }
      return result;
   }
    
   /**
    * Read an U32 from this signal input stream. This method reads four bytes
    * from the underlying input stream with respect to the byte ordering and a
    * 4-byte boundary alignment and returns the result as a <code>long</code>
    * to be interpreted as an U32.
    * <p>
    * This method blocks until either the four bytes are read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next four bytes of this input stream, interpreted as an
    *             U32 and returned as a <code>long</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading four bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public long readU32() throws IOException
   {
      return (((long) readS32()) & 0xFFFFFFFFL);
   }

   /**
    * Read a S64 from this signal input stream. This method reads eight bytes
    * from the underlying input stream with respect to the byte ordering and a
    * 8-byte boundary alignment and returns the result as a <code>long</code>.
    * <p>
    * This method blocks until either the eight bytes are read, the end of the
    * stream is detected, or an exception is thrown.
    *
    * @return     the next eight bytes of this input stream, interpreted as a
    *             S64 and returned as a <code>long</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *             reading eight bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public long readS64() throws IOException
   {
      align(8);
      long ch1 = in.read();
      long ch2 = in.read();
      long ch3 = in.read();
      long ch4 = in.read();
      long ch5 = in.read();
      long ch6 = in.read();
      long ch7 = in.read();
      long ch8 = in.read();
      if ((ch1 | ch2 | ch3 | ch4 | ch5 | ch6 | ch7 | ch8) < 0L)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos += 8;
      long result;
      if (bigEndian)
      {
         result = (ch1 << 56) + (ch2 << 48) + (ch3 << 40) + (ch4 << 32) +
                  (ch5 << 24) + (ch6 << 16) + (ch7 << 8) + (ch8 << 0);
      }
      else
      {
         result = (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24) +
                  (ch5 << 32) + (ch6 << 40) + (ch7 << 48) + (ch8 << 56);
      }
      return result;
   }

   /**
    * Read a null-terminated sequence of bytes from the underlying input
    * stream and return a corresponding <code>String</code> object.
    * The bytes are encoded according to the ISO-Latin-1 (ISO-8859-1)
    * character encoding.
    *
    * @return     the resulting <code>String</code>.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public String readString() throws IOException
   {
      byte[] v = new byte[50];
      int len = 0;
      for (int i = 0; ; i++)
      {
         int ch = in.read();
         if (ch == -1)
         {
            throw new EOFException(EOF_ERROR_MSG);
         }
         if (len >= v.length)
         {
            byte[] t = new byte[v.length + 50];
            System.arraycopy(v, 0, t, 0, v.length);
            v = t;
         }
         v[i] = (byte) ch;
         len++;
         if (ch == 0)
         {
            break;
         }
      }
      readpos += len;
      return (new String(v, 0, len - 1, "8859_1"));
   }

   /**
    * Read a null-terminated sequence of bytes from the underlying input
    * stream and return a corresponding <code>String</code> object.
    * The bytes are encoded according to the ISO-Latin-1 (ISO-8859-1)
    * character encoding.
    * <p>
    * If the length of the null-terminated sequence of bytes is less than
    * the specified size then the remaining bytes are read but skipped so
    * that exactly <code>size</code> number of bytes will be read.
    * <p>
    * If no null-terminating byte is encountered before size bytes has been
    * read then the sequence of bytes will be truncated at the end so that
    * exactly <code>size</code> number of bytes will be read.
    *
    * @param      size  the total number of bytes to be read.
    * @return     the resulting <code>String</code>.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public String readString(int size) throws IOException
   {
      byte[] v;
      int len = 0;
      boolean skip = false;

      if (size < 0) return null;
      v = new byte[size];
      for (int i = 0; i < size; i++)
      {
         int ch = in.read();
         if (ch == -1)
         {
            throw new EOFException(EOF_ERROR_MSG);
         }
         else if (ch == 0)
         {
            skip = true;
         }
         else if (!skip)
         {
            v[i] = (byte) ch;
            len++;
         }
      }
      readpos += size;
      return (new String(v, 0, len, "8859_1"));
   }

   /**
    * Read a S8 array from the underlying input stream. Each S8
    * value is read as one byte, and is put in a <code>byte</code>.
    *
    * @param      len  the length of the array to be read.
    * @return     a <code>byte</code> array containing S8 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public byte[] readS8Array(int len) throws IOException
   {
      byte[] v = new byte[len];
      int count = in.read(v, 0, len);
      if (count < 0)
      {
         throw new EOFException(EOF_ERROR_MSG);
      }
      readpos += count;
      return v;
   }
    
   /**
    * Read an U8 array from the underlying input stream. Each U8
    * value is read as one byte, and is put in an <code>int</code>
    * to be intrepreted as an U8.
    *
    * @param      len  the length of the array to be read.
    * @return     an <code>int</code> array containing U8 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public int[] readU8Array(int len) throws IOException
   {
      int[] v = new int[len];
      for (int i = 0; i < len; i++)
      {
         v[i] = readU8();
      }
      return v;
   }
    
   /**
    * Read a S16 array from the underlying input stream. Each
    * S16 value is read as two bytes with respect to the byte
    * ordering and a 2-byte boundary alignment, and is put in
    * a <code>short</code>.
    *
    * @param      len  the length of the array to be read.
    * @return     a <code>short</code> array containing S16 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public short[] readS16Array(int len) throws IOException
   {
      short[] v = new short[len];
      for (int i = 0; i < len; i++)
      {
         v[i] = readS16();
      }
      return v;
   }
    
   /**
    * Read an U16 array from the underlying input stream. Each
    * U16 value is read as two bytes with respect to the byte
    * ordering and a 2-byte boundary alignment, and is put in
    * an <code>int</code> to be intrepreted as an U16.
    *
    * @param      len  the length of the array to be read.
    * @return     an <code>int</code> array containing U16 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public int[] readU16Array(int len) throws IOException
   {
      int[] v = new int[len];
      for (int i = 0; i < len; i++)
      {
         v[i] = readU16();
      }
      return v;
   }
    
   /**
    * Read a S32 array from the underlying input stream. Each
    * S32 value is read as four bytes with respect to the byte
    * ordering and a 4-byte boundary alignment, and is put in
    * an <code>int</code>.
    *
    * @param      len  the length of the array to be read.
    * @return     an <code>int</code> array containing S32 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public int[] readS32Array(int len) throws IOException
   {
      int[] v = new int[len];
      for (int i = 0; i < len; i++)
      {
         v[i] = readS32();
      }
      return v;
   }
    
   /**
    * Read an U32 array from the underlying input stream. Each
    * U32 value is read as four bytes with respect to the byte
    * ordering and a 4-byte boundary alignment, and is put in
    * a <code>long</code> to be intrepreted as an U32.
    *
    * @param      len  the length of the array to be read.
    * @return     a <code>long</code> array containing U32 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public long[] readU32Array(int len) throws IOException
   {
      long[] v = new long[len];
      for (int i = 0; i < len; i++)
      {
         v[i] = readU32();
      }
      return v;
   }

   /**
    * Read a S64 array from the underlying input stream. Each
    * S64 value is read as eight bytes with respect to the byte
    * ordering and a 8-byte boundary alignment, and is put in
    * a <code>long</code>.
    *
    * @param      len  the length of the array to be read.
    * @return     a <code>long</code> array containing S64 values.
    * @exception  EOFException  if this input stream has reached the end.
    * @exception  IOException   if an I/O error occurs.
    */
   public long[] readS64Array(int len) throws IOException
   {
      long[] v = new long[len];
      for (int i = 0; i < len; i++)
      {
         v[i] = readS64();
      }
      return v;
   }

   /**
    * Skip over and discards <code>n</code> bytes of data from this signal
    * input stream. The <code>skip</code> method may, for a variety of
    * reasons, end up skipping over some smaller number of bytes, possibly
    * <code>0</code>. The actual number of bytes skipped is returned.
    * <p>
    * The <code>skip</code>method of <code>SignalInputStream</code> calls
    * the <code>skip</code> method of its underlying input stream with the
    * same argument, and returns whatever value that method does.
    *
    * @param      n  the number of bytes to be skipped.
    * @return     the actual number of bytes skipped.
    * @exception  EOFException  if this input stream reaches the end before
    *             skipping all the bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public long skip(long n) throws IOException
   {
      long skipped = in.skip(n);
      readpos += (int) skipped;
      return skipped;
   }
    
   /**
    * Skip exactly <code>n</code> bytes of input in the underlying input
    * stream. This method blocks until either all the bytes are skipped, the
    * end of the stream is detected, or an exception is thrown.
    *
    * @param      n  the number of bytes to be skipped.
    * @return     the number of bytes skipped, which is always <code>n</code>.
    * @exception  EOFException  if this input stream reaches the end before
    *             skipping all the bytes.
    * @exception  IOException   if an I/O error occurs.
    */
   public int skipBytes(int n) throws IOException
   {
      for (int i = 0; i < n; i += (int) in.skip(n - i));
      readpos += n;
      return n;
   }
    
   /**
    * Align the read position in this signal input stream to a position
    * that is a multiple of the specified even alignment requirement.
    *
    * @param      n  the even alignment requirement.
    * @exception  EOFException  if this input stream reaches the end before
    *             making the specified alignment.
    * @exception  IOException   if an I/O error occurs.
    */
   public void align(int n) throws IOException
   {
      if ((n <= 0) || ((n & 1) != 0))
      {
         return;
      }
      // Account for the signal number as well.
      int skipped = (n - (readpos + 4) & (n - 1)) % n;
      for (int i = 0; i < skipped; i += (int) in.skip(skipped - i));
      readpos += skipped;
   }
    
   /**
    * Mark the current position in this input stream. A subsequent call to
    * the <code>reset</code> method repositions this input stream at the last
    * marked position so that subsequent reads re-read the same bytes.
    * <p>
    * The <code>readlimit</code> argument tells this input stream to allow
    * that many bytes to be read before the mark position gets invalidated.
    * <p>
    * The <code>mark</code> method of <code>SignalInputStream</code> calls the
    * <code>mark</code> method of its underlying input stream with the
    * <code>readlimit</code> argument and notes the mark position internally.
    *
    * @param  readlimit  the maximum limit of bytes that can be read before
    *                    the mark position becomes invalid.
    */
   public synchronized void mark(int readlimit)
   {
      if (in.markSupported())
      {
         in.mark(readlimit);
         markpos = readpos;
      }
   }

   /**
    * Reposition this input stream to the position at the time the
    * <code>mark</code> method was last called on this input stream.
    * Throws an IOException if the input stream has not been marked
    * or if the mark has been invalidated.
    * <p>
    * The <code>reset</code> method of <code>SignalInputStream</code> calls
    * the <code>reset</code> method of its underlying input stream.
    *
    * @exception  IOException  if the input stream has not been marked or if
    *             the mark has been invalidated.
    */
   public synchronized void reset() throws IOException
   {
      in.reset();
      readpos = markpos;
   }

   /**
    * Return the next read position in this signal input stream, and thus the
    * number of bytes read from this signal input stream.
    *
    * @return  the next read position in this signal input stream, and thus the
    * number of bytes read from this signal input stream.
    */
   public int getReadPosition()
   {
      return readpos;
   }

   /**
    * Determine whether this signal input stream uses big endian or little
    * endian byte ordering.
    *
    * @return  <code>true</code> if big endian byte order,
    *          <code>false</code> if little endian byte order.
    */
   public boolean isBigEndian()
   {
      return bigEndian;
   }
}
