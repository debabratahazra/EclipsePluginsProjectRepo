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

package com.ose.pmd.ui.editor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.TextChangeListener;

// Only for blocks formatted with HexBlockFormatter.
public class BlockFileStyledTextContent implements StyledTextContent, CharSequence
{
   private static final int LINE_LENGTH = 76;

   private final File file;
   private final RandomAccessFile raFile;
   private final Object readLock = new Object();
   private final int charCount;
   private final int lineCount;

   public BlockFileStyledTextContent(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      this.file = file;
      raFile = new RandomAccessFile(file, "r");
      charCount = (int) raFile.length();
      lineCount =
         (charCount / LINE_LENGTH) + (((charCount % LINE_LENGTH) != 0) ? 1 : 0);
   }

   public void dispose()
   {
      try
      {
         raFile.close();
      } catch (IOException ignore) {}
   }

   public File getFile()
   {
      return file;
   }

   public void addTextChangeListener(TextChangeListener listener)
   {
      // Not supported.
   }

   public void removeTextChangeListener(TextChangeListener listener)
   {
      // Not supported.
   }

   public int getCharCount()
   {
      return charCount;
   }

   public String getLine(int lineIndex)
   {
      int offset = lineIndex * LINE_LENGTH;

      if (offset < charCount)
      {
         try
         {
            byte[] buffer = new byte[LINE_LENGTH - 1];
            read(offset, buffer);
            return new String(buffer, "ISO-8859-1");
         }
         catch (IOException e)
         {
            throw new RuntimeException("Error reading block", e);
         }
      }
      else
      {
         return "";
      }
   }

   public int getLineAtOffset(int offset)
   {
      return ((offset >= charCount) ? lineCount : (offset / LINE_LENGTH));
   }

   public int getLineCount()
   {
      return lineCount + 1;
   }

   public String getLineDelimiter()
   {
      return "\n";
   }

   public int getOffsetAtLine(int lineIndex)
   {
      return ((lineIndex < lineCount) ? (lineIndex * LINE_LENGTH) : charCount);
   }

   public String getTextRange(int start, int length)
   {
      if (start < charCount)
      {
         try
         {
            byte[] buffer = new byte[((start + length) <= charCount) ?
                  length : charCount - start];
            read(start, buffer);
            return new String(buffer, "ISO-8859-1");
         }
         catch (IOException e)
         {
            throw new RuntimeException("Error reading block", e);
         }
      }
      else
      {
         return "";
      }
   }

   public void replaceTextRange(int start, int replaceLength, String text)
   {
      // Not supported.
   }

   public void setText(String text)
   {
      // Not supported.
   }

   // Methods for CharSequence.

   public int length()
   {
      return charCount;
   }

   // FIXME: Cache a chunk of characters for improved performance.
   public char charAt(int index)
   {
      if ((index < 0) || (index >= charCount))
      {
         throw new IndexOutOfBoundsException();
      }

      try
      {
         int value = read(index);
         return (char) value;
      }
      catch (IOException e)
      {
         throw new RuntimeException("Error reading block", e);
      }
   }

   public CharSequence subSequence(int start, int end)
   {
      if ((start < 0) || (end > charCount) || (start > end))
      {
         throw new IndexOutOfBoundsException();
      }

      if ((start == 0) && (end == charCount))
      {
         return this;
      }

      try
      {
         byte[] buffer = new byte[end - start];
         read(start, buffer);
         return new String(buffer, "ISO-8859-1");
      }
      catch (IOException e)
      {
         throw new RuntimeException("Error reading block", e);
      }
   }

   public String toString()
   {
      try
      {
         byte[] buffer = new byte[charCount];
         read(0, buffer);
         return new String(buffer, "ISO-8859-1");
      }
      catch (IOException e)
      {
         throw new RuntimeException("Error reading block", e);
      }
   }

   private int read(int offset) throws IOException
   {
      synchronized (readLock)
      {
         raFile.seek(offset);
         return raFile.read();
      }
   }

   private void read(int offset, byte[] buffer) throws IOException
   {
      synchronized (readLock)
      {
         raFile.seek(offset);
         raFile.readFully(buffer);
      }
   }
}
