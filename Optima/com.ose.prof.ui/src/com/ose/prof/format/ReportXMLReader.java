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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;
import com.ose.system.CPUProcessReport.CPUProcessLoad;
import com.ose.system.CPUReport;
import com.ose.system.UserReport;
import com.ose.system.UserReport.MaxMinUserReportValue;
import com.ose.system.UserReport.UserReportValue;

/**
 * This class is used for reading profiling report XML files.
 */
public class ReportXMLReader
{
   private final ReportReaderClient client;

   /**
    * Create a new report XML reader object.
    *
    * @param client  the report reader client.
    */
   public ReportXMLReader(ReportReaderClient client)
   {
      if (client == null)
      {
         throw new IllegalArgumentException();
      }
      this.client = client;
   }

   /**
    * Read the reports from the specified report XML file. The reports read will
    * be reported to the report reader client specified when this report XML
    * reader was created.
    *
    * @param file  the report XML file to read.
    * @throws IOException  if an I/O exception occurred.
    * @throws SAXException  if an XML parsing exception occurred.
    * @throws ParserConfigurationException  if an XML parser configuration
    * exception occurred.
    */
   public void read(File file) throws IOException,
                                      SAXException,
                                      ParserConfigurationException
   {
      SAXParserFactory parserFactory;
      SAXParser parser;
      ReportXMLHandler handler;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      parserFactory = SAXParserFactory.newInstance();
      parserFactory.setNamespaceAware(true);
      parserFactory.setValidating(true);
      parser = parserFactory.newSAXParser();
      handler = new ReportXMLHandler(client);
      parser.parse(file, handler);
   }

   private static class ReportXMLHandler extends DefaultHandler
   {
      private final ReportReaderClient client;
      private final ReportParams params;

      private boolean firstReportRead;
      private int tickLength;
      private int microTickFrequency;
      private int seconds;
      private int secondsTick;
      private int secondsNTick;
      private StringBuffer textBuffer;
      private boolean maxMin;
      private boolean userReport;

      ReportXMLHandler(ReportReaderClient client)
      {
         this.client = client;
         params = new ReportParams();
      }

      public InputSource resolveEntity(String publicId, String systemId)
         throws SAXException
      {
         if (systemId.endsWith(ReportXMLTags.DTD_PATH))
         {
            URL url = ProfilerPlugin.getDefault().getBundle().getEntry(
                  ReportXMLTags.DTD_PATH);
            try
            {
               return new InputSource(new BufferedInputStream(url.openStream()));
            }
            catch (IOException e)
            {
               throw new SAXException(e);
            }
         }
         else
         {
            return null;
         }
      }

      public void startElement(String namespaceURI,
                               String localName,
                               String qName,
                               Attributes attrs)
         throws SAXException
      {
         if (localName.equals(ReportXMLTags.TAG_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_REPORTS))
         {
            handleCPUReportsAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_PRIO_REPORTS))
         {
            handleCPUPriorityReportsAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_PROC_REPORTS))
         {
            handleCPUProcessReportsAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_BLOCK_REPORTS))
         {
            handleCPUBlockReportsAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_USER_REPORTS))
         {
            handleUserReportsAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_PROCESS))
         {
            handleProcessAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_BLOCK))
         {
            handleBlockAttributes(attrs);
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_REPORT) ||
                  localName.equals(ReportXMLTags.TAG_CPU_PRIO_REPORT) ||
                  localName.equals(ReportXMLTags.TAG_CPU_PROC_REPORT) ||
                  localName.equals(ReportXMLTags.TAG_CPU_BLOCK_REPORT) ||
                  localName.equals(ReportXMLTags.TAG_USER_REPORT))
         {
            params.setDefaults();
            userReport = localName.equals(ReportXMLTags.TAG_USER_REPORT);
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_PRIORITIZED) ||
                  localName.equals(ReportXMLTags.TAG_SUM_PROCESS) ||
                  localName.equals(ReportXMLTags.TAG_SUM_BLOCK) ||
                  localName.equals(ReportXMLTags.TAG_VALUE))
         {
            // Nothing to do.
         }
         else
         {
            textBuffer = new StringBuffer();
         }
      }

      public void endElement(String namespaceURI,
                             String localName,
                             String qName)
         throws SAXException
      {
         if (localName.equals(ReportXMLTags.TAG_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_PRIO_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_PROC_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_BLOCK_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_USER_REPORTS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_PROCESS))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_BLOCK))
         {
            // Nothing to do.
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_REPORT))
         {
            if (!firstReportRead)
            {
               adjustTimeReference(params.tick, params.microTick);
               firstReportRead = true;
            }
            client.cpuReportRead(createCPUReport(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_PRIO_REPORT))
         {
            if (!firstReportRead)
            {
               adjustTimeReference(params.tick, params.microTick);
               firstReportRead = true;
            }
            client.cpuPriorityReportRead(createCPUPriorityReport(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_PROC_REPORT))
         {
            if (!firstReportRead)
            {
               adjustTimeReference(params.tick, params.microTick);
               firstReportRead = true;
            }
            client.cpuProcessReportRead(createCPUProcessReport(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_CPU_BLOCK_REPORT))
         {
            if (!firstReportRead)
            {
               adjustTimeReference(params.tick, params.microTick);
               firstReportRead = true;
            }
            client.cpuBlockReportRead(createCPUBlockReport(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_USER_REPORT))
         {
            if (!firstReportRead)
            {
               adjustTimeReference(params.tick, params.microTick);
               firstReportRead = true;
            }
            client.userReportRead(createUserReport(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_TICK))
         {
            params.tick = parseU32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_MICRO_TICK))
         {
            params.microTick = parseU32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_INTERVAL))
         {
            params.interval = parseU32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_ID))
         {
            params.id = parseU32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM))
         {
            params.sum = (userReport ?
                          parseS32(textBuffer.toString()) :
                          parseU32(textBuffer.toString()));
         }
         else if (localName.equals(ReportXMLTags.TAG_MAX))
         {
            params.max = parseS32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_MIN))
         {
            params.min = parseS32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_OTHER))
         {
            params.sumOther = (userReport ?
                               parseS32(textBuffer.toString()) :
                               parseU32(textBuffer.toString()));
         }
         else if (localName.equals(ReportXMLTags.TAG_MAX_OTHER))
         {
            params.maxOther = parseS32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_MIN_OTHER))
         {
            params.minOther = parseS32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_INTERRUPT))
         {
            params.sumInterrupt = parseU32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_BACKGROUND))
         {
            params.sumBackground = parseU32(textBuffer.toString());
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_PRIORITIZED))
         {
            params.addSumPrioritized(params.id, params.sum);
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_PROCESS))
         {
            params.addSumProcess(createCPUProcessLoad(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_SUM_BLOCK))
         {
            params.addSumBlock(createCPUBlockLoad(params));
         }
         else if (localName.equals(ReportXMLTags.TAG_VALUE))
         {
            params.addValue(createUserReportValue(params));
         }
         else
         {
            // Ignore unknown tags.
         }
      }

      public void characters(char[] ch, int start, int length)
         throws SAXException
      {
         if (textBuffer != null)
         {
            textBuffer.append(ch, start, length);
         }
      }

      public void error(SAXParseException e) throws SAXException
      {
         throw e;
      }

      private void handleCPUReportsAttributes(Attributes attrs)
         throws SAXException
      {
         String target = "";
         boolean bigEndian = true;
         int osType = 0;
         int numExecutionUnits = 0;
         short executionUnit = 0;
         int integrationInterval = 0;
         int maxReports = 0;
         int priorityLimit = 0;

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_TARGET))
            {
               target = attrs.getValue(i);
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_BYTE_ORDER))
            {
               bigEndian = attrs.getValue(i).equals("bigEndian");
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_OS_TYPE))
            {
               osType = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_NUM_EXECUTION_UNITS))
            {
               numExecutionUnits = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_TICK_LENGTH))
            {
               tickLength = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_NTICK_FREQUENCY))
            {
               microTickFrequency = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_EXECUTION_UNIT))
            {
               executionUnit = parseU16(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_INTERVAL))
            {
               integrationInterval = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_MAX_REPORTS))
            {
               maxReports = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_PRIORITY_LIMIT))
            {
               priorityLimit = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_SECONDS))
            {
               seconds = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_SECONDS_TICK))
            {
               secondsTick = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_REPORTS_SECONDS_NTICK))
            {
               secondsNTick = parseU32(attrs.getValue(i));
            }
         }

         client.cpuReportSettingsRead(target,
                                      bigEndian,
                                      osType,
                                      numExecutionUnits,
                                      tickLength,
                                      microTickFrequency,
                                      executionUnit,
                                      integrationInterval,
                                      maxReports,
                                      priorityLimit,
                                      seconds,
                                      secondsTick,
                                      secondsNTick);
      }

      private void handleCPUPriorityReportsAttributes(Attributes attrs)
         throws SAXException
      {
         String target = "";
         boolean bigEndian = true;
         int osType = 0;
         int numExecutionUnits = 0;
         short executionUnit = 0;
         int integrationInterval = 0;
         int maxReports = 0;

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_TARGET))
            {
               target = attrs.getValue(i);
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_BYTE_ORDER))
            {
               bigEndian = attrs.getValue(i).equals("bigEndian");
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_OS_TYPE))
            {
               osType = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_NUM_EXECUTION_UNITS))
            {
               numExecutionUnits = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_TICK_LENGTH))
            {
               tickLength = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_NTICK_FREQUENCY))
            {
               microTickFrequency = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_EXECUTION_UNIT))
            {
               executionUnit = parseU16(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_INTERVAL))
            {
               integrationInterval = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_MAX_REPORTS))
            {
               maxReports = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_SECONDS))
            {
               seconds = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_SECONDS_TICK))
            {
               secondsTick = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PRIO_REPORTS_SECONDS_NTICK))
            {
               secondsNTick = parseU32(attrs.getValue(i));
            }
         }

         client.cpuPriorityReportSettingsRead(target,
                                              bigEndian,
                                              osType,
                                              numExecutionUnits,
                                              tickLength,
                                              microTickFrequency,
                                              executionUnit,
                                              integrationInterval,
                                              maxReports,
                                              seconds,
                                              secondsTick,
                                              secondsNTick);
      }

      private void handleCPUProcessReportsAttributes(Attributes attrs)
         throws SAXException
      {
         String target = "";
         boolean bigEndian = true;
         int osType = 0;
         int numExecutionUnits = 0;
         short executionUnit = 0;
         int integrationInterval = 0;
         int maxReports = 0;
         int maxProcessesPerReport = 0;
         String profiledProcesses = "";

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_TARGET))
            {
               target = attrs.getValue(i);
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_BYTE_ORDER))
            {
               bigEndian = attrs.getValue(i).equals("bigEndian");
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_OS_TYPE))
            {
               osType = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_NUM_EXECUTION_UNITS))
            {
               numExecutionUnits = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_TICK_LENGTH))
            {
               tickLength = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_NTICK_FREQUENCY))
            {
               microTickFrequency = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_EXECUTION_UNIT))
            {
               executionUnit = parseU16(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_INTERVAL))
            {
               integrationInterval = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_MAX_REPORTS))
            {
               maxReports = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_MAX_PROCS_REPORT))
            {
               maxProcessesPerReport = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_PROFILED_PROCS))
            {
               profiledProcesses = attrs.getValue(i);
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_SECONDS))
            {
               seconds = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_SECONDS_TICK))
            {
               secondsTick = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_PROC_REPORTS_SECONDS_NTICK))
            {
               secondsNTick = parseU32(attrs.getValue(i));
            }
         }

         client.cpuProcessReportSettingsRead(target,
                                             bigEndian,
                                             osType,
                                             numExecutionUnits,
                                             tickLength,
                                             microTickFrequency,
                                             executionUnit,
                                             integrationInterval,
                                             maxReports,
                                             maxProcessesPerReport,
                                             profiledProcesses,
                                             seconds,
                                             secondsTick,
                                             secondsNTick);
      }

      private void handleCPUBlockReportsAttributes(Attributes attrs)
         throws SAXException
      {
         String target = "";
         boolean bigEndian = true;
         int osType = 0;
         int numExecutionUnits = 0;
         short executionUnit = 0;
         int integrationInterval = 0;
         int maxReports = 0;
         int maxBlocksPerReport = 0;

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_TARGET))
            {
               target = attrs.getValue(i);
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_BYTE_ORDER))
            {
               bigEndian = attrs.getValue(i).equals("bigEndian");
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_OS_TYPE))
            {
               osType = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_NUM_EXECUTION_UNITS))
            {
               numExecutionUnits = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_TICK_LENGTH))
            {
               tickLength = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_NTICK_FREQUENCY))
            {
               microTickFrequency = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_EXECUTION_UNIT))
            {
               executionUnit = parseU16(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_INTERVAL))
            {
               integrationInterval = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_MAX_REPORTS))
            {
               maxReports = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_MAX_BLOCKS_REPORT))
            {
               maxBlocksPerReport = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_SECONDS))
            {
               seconds = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_SECONDS_TICK))
            {
               secondsTick = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_CPU_BLOCK_REPORTS_SECONDS_NTICK))
            {
               secondsNTick = parseU32(attrs.getValue(i));
            }
         }

         client.cpuBlockReportSettingsRead(target,
                                           bigEndian,
                                           osType,
                                           numExecutionUnits,
                                           tickLength,
                                           microTickFrequency,
                                           executionUnit,
                                           integrationInterval,
                                           maxReports,
                                           maxBlocksPerReport,
                                           seconds,
                                           secondsTick,
                                           secondsNTick);
      }

      private void handleUserReportsAttributes(Attributes attrs)
         throws SAXException
      {
         String target = "";
         boolean bigEndian = true;
         int osType = 0;
         int numExecutionUnits = 0;
         int integrationInterval = 0;
         int maxReports = 0;
         int maxValuesPerReport = 0;
         int reportNumber = 0;
         boolean continuous = false;
         boolean multiple = false;

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_TARGET))
            {
               target = attrs.getValue(i);
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_BYTE_ORDER))
            {
               bigEndian = attrs.getValue(i).equals("bigEndian");
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_OS_TYPE))
            {
               osType = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_NUM_EXECUTION_UNITS))
            {
               numExecutionUnits = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_TICK_LENGTH))
            {
               tickLength = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_NTICK_FREQUENCY))
            {
               microTickFrequency = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_INTERVAL))
            {
               integrationInterval = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_MAX_REPORTS))
            {
               maxReports = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_MAX_VALUES_REPORT))
            {
               maxValuesPerReport = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_REPORT_NUMBER))
            {
               reportNumber = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_CONTINUOUS))
            {
               continuous = parseBoolean(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_MAX_MIN))
            {
               maxMin = parseBoolean(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_MULTIPLE))
            {
               multiple = parseBoolean(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_SECONDS))
            {
               seconds = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_SECONDS_TICK))
            {
               secondsTick = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_USER_REPORTS_SECONDS_NTICK))
            {
               secondsNTick = parseU32(attrs.getValue(i));
            }
         }

         client.userReportSettingsRead(target,
                                       bigEndian,
                                       osType,
                                       numExecutionUnits,
                                       tickLength,
                                       microTickFrequency,
                                       integrationInterval,
                                       maxReports,
                                       maxValuesPerReport,
                                       reportNumber,
                                       continuous,
                                       maxMin,
                                       multiple,
                                       seconds,
                                       secondsTick,
                                       secondsNTick);
      }

      private void handleProcessAttributes(Attributes attrs) throws SAXException
      {
         int id = 0;
         String name = "";

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_PROCESS_ID))
            {
               id = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_PROCESS_NAME))
            {
               name = attrs.getValue(i);
            }
         }

         client.processRead(id, name);
      }

      private void handleBlockAttributes(Attributes attrs) throws SAXException
      {
         int id = 0;
         String name = "";

         for (int i = 0; i < attrs.getLength(); i++)
         {
            String attrName = attrs.getLocalName(i);

            if (attrName.equals(ReportXMLTags.ATTR_BLOCK_ID))
            {
               id = parseU32(attrs.getValue(i));
            }
            else if (attrName.equals(ReportXMLTags.ATTR_BLOCK_NAME))
            {
               name = attrs.getValue(i);
            }
         }

         client.blockRead(id, name);
      }

      private CPUReport createCPUReport(ReportParams params)
      {
         CPUReport report = CPUReport.getInstance(params.tick,
                                                  params.microTick,
                                                  params.interval,
                                                  params.sum);
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         return report;
      }

      private CPUPriorityReport createCPUPriorityReport(ReportParams params)
      {
         CPUPriorityReport report = CPUPriorityReport.getInstance(
               params.tick,
               params.microTick,
               params.interval,
               params.sumInterrupt,
               params.sumBackground,
               params.getSumPrioritized());
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         return report;
      }

      private CPUProcessReport createCPUProcessReport(ReportParams params)
      {
         CPUProcessReport report = CPUProcessReport.getInstance(
               params.tick,
               params.microTick,
               params.interval,
               params.sumOther,
               params.getSumProcesses());
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         return report;
      }

      private CPUBlockReport createCPUBlockReport(ReportParams params)
      {
         CPUBlockReport report = CPUBlockReport.getInstance(
               params.tick,
               params.microTick,
               params.interval,
               params.sumOther,
               params.getSumBlocks());
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         return report;
      }

      private UserReport createUserReport(ReportParams params)
      {
         UserReport report = UserReport.getInstance(params.tick,
                                                    params.microTick,
                                                    params.interval,
                                                    params.sumOther,
                                                    params.maxOther,
                                                    params.minOther,
                                                    params.getValues());
         report.setNanoSeconds(tickLength,
                               microTickFrequency,
                               seconds,
                               secondsTick,
                               secondsNTick);
         return report;
      }

      private CPUProcessLoad createCPUProcessLoad(ReportParams params)
      {
         return CPUProcessLoad.getInstance(params.id, params.sum);
      }

      private CPUBlockLoad createCPUBlockLoad(ReportParams params)
      {
         return CPUBlockLoad.getInstance(params.id, params.sum);
      }

      private UserReportValue createUserReportValue(ReportParams params)
      {
         if (maxMin)
         {
            return MaxMinUserReportValue.getInstance(params.id,
                                                     params.sum,
                                                     params.max,
                                                     params.min);
         }
         else
         {
            return UserReportValue.getInstance(params.id, params.sum);
         }
      }

      private void adjustTimeReference(int tick, int ntick)
      {
         TimeReference timeRef = new TimeReference(tickLength, microTickFrequency);
         timeRef.set(seconds, secondsTick, secondsNTick);
         if (timeRef.adjust(tick, ntick))
         {
            seconds = timeRef.getSeconds();
            secondsTick = timeRef.getTick();
            secondsNTick = timeRef.getNTick();
         }
      }

      private static short parseU16(String s) throws SAXException
      {
         int value;

         if (s == null)
         {
            throw new IllegalArgumentException();
         }

         try
         {
            value = Integer.decode(s.trim()).intValue();
         }
         catch (NumberFormatException e)
         {
            throw new SAXException(
               "Invalid element or attribute 16-bit integer value '" + s + "'");
         }
         if ((value < 0) || (value > 0xFFFF))
         {
            throw new SAXException(
               "Invalid element or attribute 16-bit integer value '" + s + "'");
         }
         return (short) (0xFFFF & value);
      }

      private static int parseS32(String s) throws SAXException
      {
         int value;

         if (s == null)
         {
            throw new IllegalArgumentException();
         }

         try
         {
           value = Integer.decode(s.trim()).intValue();
         }
         catch (NumberFormatException e)
         {
            throw new SAXException(
               "Invalid element or attribute 32-bit integer value '" + s + "'");
         }

         return value;
      }

      private static int parseU32(String s) throws SAXException
      {
         long value;

         if (s == null)
         {
            throw new IllegalArgumentException();
         }

         try
         {
            value = Long.decode(s.trim()).longValue();
         }
         catch (NumberFormatException e)
         {
            throw new SAXException(
               "Invalid element or attribute 32-bit integer value '" + s + "'");
         }
         if ((value < 0L) || (value > 0xFFFFFFFFL))
         {
            throw new SAXException(
               "Invalid element or attribute 32-bit integer value '" + s + "'");
         }

         return (int) (0xFFFFFFFFL & value);
      }

      private static boolean parseBoolean(String s)
      {
         if (s == null)
         {
            throw new IllegalArgumentException();
         }
         return s.equalsIgnoreCase("true");
      }

      private static class ReportParams
      {
         private static final CPUProcessLoad[] CPU_PROCESS_LOAD_TYPE =
            new CPUProcessLoad[0];
         private static final CPUBlockLoad[] CPU_BLOCK_LOAD_TYPE =
            new CPUBlockLoad[0];
         private static final UserReportValue[] USER_REPORT_VALUE_TYPE =
            new UserReportValue[0];

         public int tick;
         public int microTick;
         public int interval;
         public int id;
         public int sum;
         public int max;
         public int min;
         public int sumOther;
         public int maxOther;
         public int minOther;
         public int sumInterrupt;
         public int sumBackground;
         private int[] sumPrioritized;
         private List sumProcesses;
         private List sumBlocks;
         private List values;

         ReportParams()
         {
            sumPrioritized = new int[32];
            sumProcesses = new ArrayList();
            sumBlocks = new ArrayList();
            values = new ArrayList();
         }

         public void setDefaults()
         {
            tick = 0;
            microTick = 0;
            interval = 0;
            id = 0;
            sum = 0;
            max = 0;
            min = 0;
            sumOther = 0;
            maxOther = 0;
            minOther = 0;
            sumInterrupt = 0;
            sumBackground = 0;
            Arrays.fill(sumPrioritized, 0);
            sumProcesses.clear();
            sumBlocks.clear();
            values.clear();
         }

         public void addSumPrioritized(int id, int sum) throws SAXException
         {
            try
            {
               sumPrioritized[id] = sum;
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
               throw new SAXException("Invalid priority level id in " +
                     ReportXMLTags.TAG_SUM_PRIORITIZED + " element");
            }
         }

         public void addSumProcess(CPUProcessLoad sumProcess)
         {
            sumProcesses.add(sumProcess);
         }

         public void addSumBlock(CPUBlockLoad sumBlock)
         {
            sumBlocks.add(sumBlock);
         }

         public void addValue(UserReportValue value)
         {
            values.add(value);
         }

         public int[] getSumPrioritized()
         {
            int[] sumPrioritizedCopy = new int[sumPrioritized.length];
            System.arraycopy(sumPrioritized, 0, sumPrioritizedCopy, 0,
                             sumPrioritized.length);
            return sumPrioritizedCopy;
         }

         public CPUProcessLoad[] getSumProcesses()
         {
            return (CPUProcessLoad[]) sumProcesses.toArray(CPU_PROCESS_LOAD_TYPE);
         }

         public CPUBlockLoad[] getSumBlocks()
         {
            return (CPUBlockLoad[]) sumBlocks.toArray(CPU_BLOCK_LOAD_TYPE);
         }

         public UserReportValue[] getValues()
         {
            return (UserReportValue[]) values.toArray(USER_REPORT_VALUE_TYPE);
         }
      }
   }
}
