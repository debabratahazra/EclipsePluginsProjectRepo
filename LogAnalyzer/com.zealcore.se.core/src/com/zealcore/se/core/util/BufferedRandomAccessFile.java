package com.zealcore.se.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This class extends java.io.RandomAccessFile with support for buffered
 * reading and should only be used when reading and not writing a file.
 */
public class BufferedRandomAccessFile extends RandomAccessFile {
    private static final int BUF_SIZE = 65536;

    private byte[] buf;

    private int bufpos;

    private int buflen;

    public BufferedRandomAccessFile(String name, String mode)
            throws FileNotFoundException {
        this((name != null) ? new File(name) : null, mode);
    }

    public BufferedRandomAccessFile(File file, String mode)
            throws FileNotFoundException {
        super(file, mode);
        buf = new byte[BUF_SIZE];
        bufpos = 0;
        buflen = 0;
    }

    @Override
    public int read() throws IOException {
        if (bufpos >= buflen) {
            buflen = readChunk();
            if (buflen == -1) {
                return -1;
            }
        }
        return buf[bufpos++] & 0xFF;
    }

    @Override
    public int read(byte[] b, int offset, int length)
           throws IOException {
       int leftover = buflen - bufpos;
       if (length <= leftover) {
            System.arraycopy(buf, bufpos, b, offset, length);
            bufpos += length;
            return length;
       }
       for (int i = 0; i < length; i++) {
          int c = read();
          if (c != -1) {
             b[offset + i] = (byte) c;
          } else {
             return i;
          }
       }
       return length;
    }

    @Override
    public int read(byte[] b) throws IOException {
       return read(b, 0, b.length);
    }

    @Override
    public long getFilePointer() throws IOException {
        return super.getFilePointer() + bufpos;
    }

    @Override
    public void seek(long pos) throws IOException {
        if ((buflen != -1) && (pos > super.getFilePointer())
                && (pos < (super.getFilePointer() + buflen))) {
            long diff = pos - super.getFilePointer();
            if (diff < Integer.MAX_VALUE) {
                bufpos = (int) diff;
            } else {
                throw new IOException("Error in BufferedRandomAccessFile.seek()");
            }
        } else {
            bufpos = 0;
            super.seek(pos);
            buflen = readChunk();
        }
    }

    private int readChunk() throws IOException {
        long pos = super.getFilePointer() + bufpos;
        super.seek(pos);
        int read = super.read(buf);
        super.seek(pos);
        bufpos = 0;
        return read;
    }

    @Override
    public void close() throws IOException {
        buf = null;
        super.close();
    }
}
