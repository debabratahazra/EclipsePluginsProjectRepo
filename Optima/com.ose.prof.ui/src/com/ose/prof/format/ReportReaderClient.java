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

import com.ose.system.CPUBlockReport;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUReport;
import com.ose.system.UserReport;

/**
 * This interface should be implemented by clients that want to read profiling
 * report XML/dump files.
 */
public interface ReportReaderClient
{
   /**
    * This method is called once initially when the CPU load report settings are
    * read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU load reports
    * were retrieved from.
    * @param integrationInterval  the CPU load reporting integration interval in
    * ticks.
    * @param maxReports  the maximum number of CPU load reports kept in the
    * target.
    * @param priorityLimit  the CPU load reporting priority limit.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU load reporting enablement info was retrieved or 0 if unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    */
   public void cpuReportSettingsRead(String target,
                                     boolean bigEndian,
                                     int osType,
                                     int numExecutionUnits,
                                     int tickLength,
                                     int microTickFrequency,
                                     short executionUnit,
                                     int integrationInterval,
                                     int maxReports,
                                     int priorityLimit,
                                     int seconds,
                                     int secondsTick,
                                     int secondsNTick);

   /**
    * This method is called once initially when the CPU priority level load
    * report settings are read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU priority level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU priority level load reporting
    * integration interval in ticks.
    * @param maxReports  the maximum number of CPU priority level load reports
    * kept in the target.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU priority level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    */
   public void cpuPriorityReportSettingsRead(String target,
                                             boolean bigEndian,
                                             int osType,
                                             int numExecutionUnits,
                                             int tickLength,
                                             int microTickFrequency,
                                             short executionUnit,
                                             int integrationInterval,
                                             int maxReports,
                                             int seconds,
                                             int secondsTick,
                                             int secondsNTick);

   /**
    * This method is called once initially when the CPU process level load
    * report settings are read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU process level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU process level load reporting
    * integration interval in ticks.
    * @param maxReports  the maximum number of CPU process level load reports
    * kept in the target.
    * @param maxProcessesPerReport  the maximum number of processes per CPU
    * process level load report.
    * @param profiledProcesses  the name of the profiled processes settings
    * file.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU process level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    */
   public void cpuProcessReportSettingsRead(String target,
                                            boolean bigEndian,
                                            int osType,
                                            int numExecutionUnits,
                                            int tickLength,
                                            int microTickFrequency,
                                            short executionUnit,
                                            int integrationInterval,
                                            int maxReports,
                                            int maxProcessesPerReport,
                                            String profiledProcesses,
                                            int seconds,
                                            int secondsTick,
                                            int secondsNTick);

   /**
    * This method is called once initially when the CPU block level load report
    * settings are read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param executionUnit  the execution unit from where the CPU block level
    * load reports were retrieved from.
    * @param integrationInterval  the CPU block level load reporting integration
    * interval in ticks.
    * @param maxReports  the maximum number of CPU block level load reports kept
    * in the target.
    * @param maxBlocksPerReport  the maximum number of blocks per CPU block
    * level load report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * CPU block level load reporting enablement info was retrieved or 0 if
    * unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    */
   public void cpuBlockReportSettingsRead(String target,
                                          boolean bigEndian,
                                          int osType,
                                          int numExecutionUnits,
                                          int tickLength,
                                          int microTickFrequency,
                                          short executionUnit,
                                          int integrationInterval,
                                          int maxReports,
                                          int maxBlocksPerReport,
                                          int seconds,
                                          int secondsTick,
                                          int secondsNTick);

   /**
    * This method is called once initially when the user report settings are
    * read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param integrationInterval  the user reporting integration interval in
    * ticks.
    * @param maxReports  the maximum number of user reports kept in the target.
    * @param maxValuesPerReport  the maximum number of values per user report.
    * @param reportNumber  the user report number.
    * @param continuous  true if the user reporting is continuous, false
    * otherwise.
    * @param maxMin  true if the maximum and minimum user report measurement
    * values are tracked during each integration interval, false otherwise.
    * @param multiple  true if this user report type supports multiple user
    * report measurement values per user report, false if it supports only a
    * single user report measurement value per user report.
    * @param seconds  the number of seconds since 00:00:00 1 jan 1970 GMT when
    * user reporting enablement info was retrieved or 0 if unavailable.
    * @param secondsTick  the number of ticks at the time of seconds or 0 if
    * unavailable.
    * @param secondsNTick  the number of timer steps at the time of seconds or 0
    * if unavailable.
    */
   public void userReportSettingsRead(String target,
                                      boolean bigEndian,
                                      int osType,
                                      int numExecutionUnits,
                                      int tickLength,
                                      int microTickFrequency,
                                      int integrationInterval,
                                      int maxReports,
                                      int maxValuesPerReport,
                                      int reportNumber,
                                      boolean continuous,
                                      boolean maxMin,
                                      boolean multiple,
                                      int seconds,
                                      int secondsTick,
                                      int secondsNTick);

   /**
    * This method is called each time a process is read. Note that this method
    * is called before the cpuProcessReportRead() or userReportRead() method is
    * called.
    *
    * @param id  the ID of the process that was read.
    * @param name  the name of the process that was read.
    */
   public void processRead(int id, String name);

   /**
    * This method is called each time a block is read. Note that this method is
    * called before the cpuBlockReportRead() method is called.
    *
    * @param id  the ID of the block that was read.
    * @param name  the name of the block that was read.
    */
   public void blockRead(int id, String name);

   /**
    * This method is called each time a CPU load report is read.
    *
    * @param report  the CPU load report that was read.
    */
   public void cpuReportRead(CPUReport report);

   /**
    * This method is called each time a CPU priority level load report is read.
    *
    * @param report  the CPU priority level load report that was read.
    */
   public void cpuPriorityReportRead(CPUPriorityReport report);

   /**
    * This method is called each time a CPU process level load report is read.
    *
    * @param report  the CPU process level load report that was read.
    */
   public void cpuProcessReportRead(CPUProcessReport report);

   /**
    * This method is called each time a CPU block level load report is read.
    *
    * @param report  the CPU block level load report that was read.
    */
   public void cpuBlockReportRead(CPUBlockReport report);

   /**
    * This method is called each time a user report is read.
    *
    * @param report  the user report that was read.
    */
   public void userReportRead(UserReport report);
}
