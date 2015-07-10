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

interface ReportXMLTags
{
   public static final String DTD_PATH = "report.dtd";

   public static final String STYLESHEET_PATH = "report.xsl";
   public static final String STYLESHEET_TYPE = "text/xsl";

   public static final String TAG_REPORTS = "reports";
   public static final String TAG_CPU_REPORTS = "cpuReports";
   public static final String TAG_CPU_PRIO_REPORTS = "cpuPriorityReports";
   public static final String TAG_CPU_PROC_REPORTS = "cpuProcessReports";
   public static final String TAG_CPU_BLOCK_REPORTS = "cpuBlockReports";
   public static final String TAG_USER_REPORTS = "userReports";
   public static final String TAG_PROCESS = "process";
   public static final String TAG_BLOCK = "block";
   public static final String TAG_CPU_REPORT = "cpuReport";
   public static final String TAG_CPU_PRIO_REPORT = "cpuPriorityReport";
   public static final String TAG_CPU_PROC_REPORT = "cpuProcessReport";
   public static final String TAG_CPU_BLOCK_REPORT = "cpuBlockReport";
   public static final String TAG_USER_REPORT = "userReport";
   public static final String TAG_TICK = "tick";
   public static final String TAG_MICRO_TICK = "microTick";
   public static final String TAG_INTERVAL = "interval";
   public static final String TAG_ID = "id";
   public static final String TAG_SUM = "sum";
   public static final String TAG_MAX = "max";
   public static final String TAG_MIN = "min";
   public static final String TAG_SUM_OTHER = "sumOther";
   public static final String TAG_MAX_OTHER = "maxOther";
   public static final String TAG_MIN_OTHER = "minOther";
   public static final String TAG_SUM_INTERRUPT = "sumInterrupt";
   public static final String TAG_SUM_BACKGROUND = "sumBackground";
   public static final String TAG_SUM_PRIORITIZED = "sumPrioritized";
   public static final String TAG_SUM_PROCESS = "sumProcess";
   public static final String TAG_SUM_BLOCK ="sumBlock";
   public static final String TAG_VALUE = "value";

   public static final String ATTR_CPU_REPORTS_TARGET = "target";
   public static final String ATTR_CPU_REPORTS_BYTE_ORDER = "byteOrder";
   public static final String ATTR_CPU_REPORTS_OS_TYPE = "osType";
   public static final String ATTR_CPU_REPORTS_NUM_EXECUTION_UNITS = "numExecutionUnits";
   public static final String ATTR_CPU_REPORTS_TICK_LENGTH = "tickLength";
   public static final String ATTR_CPU_REPORTS_NTICK_FREQUENCY = "microTickFrequency";
   public static final String ATTR_CPU_REPORTS_EXECUTION_UNIT = "executionUnit";
   public static final String ATTR_CPU_REPORTS_INTERVAL = "integrationInterval";
   public static final String ATTR_CPU_REPORTS_MAX_REPORTS = "maxReports";
   public static final String ATTR_CPU_REPORTS_PRIORITY_LIMIT = "priorityLimit";
   public static final String ATTR_CPU_REPORTS_SECONDS = "seconds";
   public static final String ATTR_CPU_REPORTS_SECONDS_TICK = "secondsTick";
   public static final String ATTR_CPU_REPORTS_SECONDS_NTICK = "secondsNTick";
   public static final String ATTR_CPU_PRIO_REPORTS_TARGET = "target";
   public static final String ATTR_CPU_PRIO_REPORTS_BYTE_ORDER = "byteOrder";
   public static final String ATTR_CPU_PRIO_REPORTS_OS_TYPE = "osType";
   public static final String ATTR_CPU_PRIO_REPORTS_NUM_EXECUTION_UNITS = "numExecutionUnits";
   public static final String ATTR_CPU_PRIO_REPORTS_TICK_LENGTH = "tickLength";
   public static final String ATTR_CPU_PRIO_REPORTS_NTICK_FREQUENCY = "microTickFrequency";
   public static final String ATTR_CPU_PRIO_REPORTS_EXECUTION_UNIT = "executionUnit";
   public static final String ATTR_CPU_PRIO_REPORTS_INTERVAL = "integrationInterval";
   public static final String ATTR_CPU_PRIO_REPORTS_MAX_REPORTS = "maxReports";
   public static final String ATTR_CPU_PRIO_REPORTS_SECONDS = "seconds";
   public static final String ATTR_CPU_PRIO_REPORTS_SECONDS_TICK = "secondsTick";
   public static final String ATTR_CPU_PRIO_REPORTS_SECONDS_NTICK = "secondsNTick";
   public static final String ATTR_CPU_PROC_REPORTS_TARGET = "target";
   public static final String ATTR_CPU_PROC_REPORTS_BYTE_ORDER = "byteOrder";
   public static final String ATTR_CPU_PROC_REPORTS_OS_TYPE = "osType";
   public static final String ATTR_CPU_PROC_REPORTS_NUM_EXECUTION_UNITS = "numExecutionUnits";
   public static final String ATTR_CPU_PROC_REPORTS_TICK_LENGTH = "tickLength";
   public static final String ATTR_CPU_PROC_REPORTS_NTICK_FREQUENCY = "microTickFrequency";
   public static final String ATTR_CPU_PROC_REPORTS_EXECUTION_UNIT = "executionUnit";
   public static final String ATTR_CPU_PROC_REPORTS_INTERVAL = "integrationInterval";
   public static final String ATTR_CPU_PROC_REPORTS_MAX_REPORTS = "maxReports";
   public static final String ATTR_CPU_PROC_REPORTS_MAX_PROCS_REPORT = "maxProcessesPerReport";
   public static final String ATTR_CPU_PROC_REPORTS_PROFILED_PROCS = "profiledProcesses";
   public static final String ATTR_CPU_PROC_REPORTS_SECONDS = "seconds";
   public static final String ATTR_CPU_PROC_REPORTS_SECONDS_TICK = "secondsTick";
   public static final String ATTR_CPU_PROC_REPORTS_SECONDS_NTICK = "secondsNTick";
   public static final String ATTR_CPU_BLOCK_REPORTS_TARGET = "target";
   public static final String ATTR_CPU_BLOCK_REPORTS_BYTE_ORDER = "byteOrder";
   public static final String ATTR_CPU_BLOCK_REPORTS_OS_TYPE = "osType";
   public static final String ATTR_CPU_BLOCK_REPORTS_NUM_EXECUTION_UNITS = "numExecutionUnits";
   public static final String ATTR_CPU_BLOCK_REPORTS_TICK_LENGTH = "tickLength";
   public static final String ATTR_CPU_BLOCK_REPORTS_NTICK_FREQUENCY = "microTickFrequency";
   public static final String ATTR_CPU_BLOCK_REPORTS_EXECUTION_UNIT = "executionUnit";
   public static final String ATTR_CPU_BLOCK_REPORTS_INTERVAL = "integrationInterval";
   public static final String ATTR_CPU_BLOCK_REPORTS_MAX_REPORTS = "maxReports";
   public static final String ATTR_CPU_BLOCK_REPORTS_MAX_BLOCKS_REPORT = "maxBlocksPerReport";
   public static final String ATTR_CPU_BLOCK_REPORTS_SECONDS = "seconds";
   public static final String ATTR_CPU_BLOCK_REPORTS_SECONDS_TICK = "secondsTick";
   public static final String ATTR_CPU_BLOCK_REPORTS_SECONDS_NTICK = "secondsNTick";
   public static final String ATTR_USER_REPORTS_TARGET = "target";
   public static final String ATTR_USER_REPORTS_BYTE_ORDER = "byteOrder";
   public static final String ATTR_USER_REPORTS_OS_TYPE = "osType";
   public static final String ATTR_USER_REPORTS_NUM_EXECUTION_UNITS = "numExecutionUnits";
   public static final String ATTR_USER_REPORTS_TICK_LENGTH = "tickLength";
   public static final String ATTR_USER_REPORTS_NTICK_FREQUENCY = "microTickFrequency";
   public static final String ATTR_USER_REPORTS_INTERVAL = "integrationInterval";
   public static final String ATTR_USER_REPORTS_MAX_REPORTS = "maxReports";
   public static final String ATTR_USER_REPORTS_MAX_VALUES_REPORT = "maxValuesPerReport";
   public static final String ATTR_USER_REPORTS_REPORT_NUMBER = "reportNumber";
   public static final String ATTR_USER_REPORTS_CONTINUOUS = "continuous";
   public static final String ATTR_USER_REPORTS_MAX_MIN = "maxMin";
   public static final String ATTR_USER_REPORTS_MULTIPLE = "multiple";
   public static final String ATTR_USER_REPORTS_SECONDS = "seconds";
   public static final String ATTR_USER_REPORTS_SECONDS_TICK = "secondsTick";
   public static final String ATTR_USER_REPORTS_SECONDS_NTICK = "secondsNTick";
   public static final String ATTR_PROCESS_ID = "id";
   public static final String ATTR_PROCESS_NAME = "name";
   public static final String ATTR_BLOCK_ID = "id";
   public static final String ATTR_BLOCK_NAME = "name";
}
