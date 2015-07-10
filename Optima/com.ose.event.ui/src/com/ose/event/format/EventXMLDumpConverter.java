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

package com.ose.event.format;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.xml.sax.SAXException;
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.TargetEvent;

/**
 * This class is used for converting an event XML file to an event dump file.
 */
public class EventXMLDumpConverter
{
   /**
    * Convert an event XML file to an event dump file.
    *
    * @param monitor  the progress monitor used for cancellation.
    * @param from  the input event XML file.
    * @param to  the output event dump file.
    * @param trace  true if event tracing, false if event notification.
    * @throws IOException  if an I/O exception occurred.
    * @throws SAXException  if an XML parsing exception occurred.
    * @throws ParserConfigurationException  if an XML parser configuration
    * exception occurred.
    */
   public void convert(IProgressMonitor monitor,
                       File from,
                       File to,
                       boolean trace)
      throws IOException, SAXException, ParserConfigurationException
   {
      EventReaderHandler eventReaderHandler;
      EventXMLReader eventXMLReader;
      boolean success = false;

      if ((monitor == null) || (from == null) || (to == null))
      {
         throw new IllegalArgumentException();
      }

      if (to.isFile() && !to.canWrite())
      {
         throw new IOException("File " + to.getAbsolutePath() + " is not writable");
      }

      eventReaderHandler = new EventReaderHandler(monitor, to, trace);
      eventXMLReader = new EventXMLReader(eventReaderHandler);
      try
      {
         eventXMLReader.read(from);
         success = true;
      }
      catch (OperationCanceledException e)
      {
         success = true;
         throw e;
      }
      finally
      {
         try
         {
            eventReaderHandler.close();
         }
         catch (IOException e)
         {
            success = false;
            throw e;
         }
         finally
         {
            if (!success)
            {
               to.delete();
            }
         }
      }
   }

   private static class EventReaderHandler implements EventReaderClient
   {
      private final IProgressMonitor monitor;
      private final File file;
      private final boolean trace;
      private SystemModelDumpFileWriter eventDumpWriter;

      EventReaderHandler(IProgressMonitor monitor, File file, boolean trace)
      {
         this.monitor = monitor;
         this.file = file;
         this.trace = trace;
      }

      public void commonAttributesRead(String target,
                                       boolean bigEndian,
                                       int osType,
                                       int numExecutionUnits,
                                       int tickLength,
                                       int microTickFrequency,
                                       String scope,
                                       String eventActions)
      {
         try
         {
            eventDumpWriter = new SystemModelDumpFileWriter(file,
                                                            bigEndian,
                                                            hashCode());
            eventDumpWriter.writeSystemInfo(osType,
                                            0,
                                            null,
                                            numExecutionUnits,
                                            tickLength,
                                            microTickFrequency,
                                            true);
            eventDumpWriter.startWritingEvents(target,
                                               scope,
                                               eventActions,
                                               trace,
                                               true);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
      }

      public void eventRead(TargetEvent event)
      {
         try
         {
            eventDumpWriter.writeEvent(event);
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      public void close() throws IOException
      {
         if (eventDumpWriter != null)
         {
            try
            {
               eventDumpWriter.endWritingEvents(0);
            }
            finally
            {
               eventDumpWriter.close();
            }
         }
      }
   }
}
