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

package com.ose.prof.format;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import com.ose.system.CPUProcessReportPoint;

/**
 * This class is used for writing profiled process settings XML files.
 */
public class ProcessXMLWriter
{
   private final Object lock;

   private final PrintStream out;

   private boolean open;

   /**
    * Create a new profiled process settings XML writer object.
    *
    * @param file  the profiled process settings XML file to write.
    * @throws IOException  if an I/O exception occurred.
    */
   public ProcessXMLWriter(File file) throws IOException
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      lock = new Object();
      out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
      open = true;

      out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
      out.println("<!DOCTYPE processes SYSTEM \"process.dtd\">");
      out.println();
      out.println("<processes>");
   }

   /**
    * Write a process report point.
    *
    * @param reportPoint  the process report point to write.
    */
   public void write(CPUProcessReportPoint reportPoint)
   {
      if (reportPoint == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (lock)
      {
         checkState();
         writeCPUProcessReportPoint(reportPoint);
      }
   }

   /**
    * Close this profiled process settings XML writer and release any resources
    * associated with it.
    */
   public void close()
   {
      synchronized (lock)
      {
         if (open)
         {
            out.println("</processes>");
            out.close();
            open = false;
         }
      }
   }

   private void checkState() throws IllegalStateException
   {
      if (!open)
      {
         throw new IllegalStateException("ProcessXMLWriter is closed");
      }
   }

   private void writeCPUProcessReportPoint(CPUProcessReportPoint reportPoint)
   {
      String scopeType;
      String scopeValue;

      if (reportPoint.getScopeType() == CPUProcessReportPoint.SCOPE_ID)
      {
         scopeType = "id";
         scopeValue = toHexString(reportPoint.getScopeId());
      }
      else
      {
         scopeType = "namePattern";
         scopeValue = toCDataString(reportPoint.getNamePattern().trim());
      }

      out.print("   <process type=\"");
      out.print(scopeType);
      out.print("\">");
      out.print(scopeValue);
      out.println("</process>");
   }

   private static String toHexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }

   private static String toCDataString(String s)
   {
      return "<![CDATA[" + s + "]]>";
   }
}
