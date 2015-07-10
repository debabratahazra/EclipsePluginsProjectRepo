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

public class FileLineInfo
{
   private final long address;
   private final String path;
   private final String filename;
   private final String fullPath;
   private final long line;
   private final char pathSeparator;

   FileLineInfo(long address, String path, String filename, long line)
   {
      this.address = address;
      this.path = path;
      this.filename = filename;
      this.line = line;

      // To be able to join the path and the filename,
      // attempt to use the same path separator as in
      // the rest of the path. Fall back to "/" if
      // there is no separator in the path already.
      if (path != null && path.contains("\\"))
      {
         pathSeparator = '\\';
      }
      else
      {
         pathSeparator = '/';
      }

      if (path == null)
      {
         fullPath = filename;
      }
      else
      {
         fullPath = path + pathSeparator + filename;
      }
   }

   public long getAddress()
   {
      return address;
   }

   public String getPath()
   {
      return path;
   }

   public String getFilename()
   {
      return filename;
   }

   public String getFullPath()
   {
      return fullPath;
   }

   public long getLine()
   {
      return line;
   }

   public String toString()
   {
      return "Path: " + path + " File: " + filename + " Line: " + line;
   }
}
