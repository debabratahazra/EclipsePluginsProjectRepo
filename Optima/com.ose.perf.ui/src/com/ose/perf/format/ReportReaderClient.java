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

package com.ose.perf.format;

import com.ose.system.LoadModuleInfo;
import com.ose.system.PerformanceCounterReport;

/**
 * This interface should be implemented by clients that want to read source
 * profiling report files.
 */
public interface ReportReaderClient
{
   /**
    * This method is called once initially when the target settings are read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param cpuType  the CPU type of the target, i.e. one of the Target.CPU_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    */
   public void targetSettingsRead(String target,
                                  boolean bigEndian,
                                  int osType,
                                  int cpuType,
                                  int numExecutionUnits,
                                  int tickLength,
                                  int microTickFrequency);

   /**
    * This method is called each time a load module is read.
    *
    * @param loadModule  the load module that was read.
    */
   public void loadModuleRead(LoadModuleInfo loadModule);

   /**
    * This method is called each time a process is read.
    *
    * @param id  the ID of the process that was read.
    * @param name  the name of the process that was read.
    */
   public void processRead(int id, String name);

   /**
    * This method is called each time information for a performance counter of
    * the specified type is read.
    *
    * @param type  the type of the performance counter that was read.
    * @param name  the human-readable name of this performance counter type.
    */
   public void performanceCounterRead(int type, String name);

   /**
    * This method is called each time enablement information for a performance
    * counter of the specified type is read.
    *
    * @param executionUnit  the execution unit where the specified type of
    * performance counter was enabled.
    * @param type  the type of the enabled performance counter.
    * @param value  the trigger value of the enabled performance counter.
    * @param maxReports  the maximum number of performance counter reports kept
    * in the target for the specified type of performance counter and execution
    * unit.
    */
   public void performanceCounterEnablementRead(short executionUnit,
                                                int type,
                                                long value,
                                                int maxReports);

   /**
    * This method is called each time a batch of performance counter reports of
    * the specified type and for the specified execution unit is read.
    *
    * @param executionUnit  the execution unit from where the performance
    * counter reports originates.
    * @param type  the type of the performance counter reports.
    * @param reports  the performance counter reports that were read.
    */
   public void reportsRead(short executionUnit,
                           int type,
                           PerformanceCounterReport[] reports);

   /**
    * This method is called each time a reported loss of performance counter
    * reports of the specified type and for the specified execution unit is
    * read.
    *
    * The sum of all reportsLossRead() calls for the specified performance
    * counter type and execution unit represents the total number of lost
    * reports during the session when the performance counter reports of the
    * specified performance counter type and execution unit in this file were
    * collected.
    *
    * @param executionUnit  the execution unit from where the performance
    * counter reports were lost.
    * @param type  the performance counter type of the lost performance counter
    * reports.
    * @param numReportsLost  the reported loss of performance counter reports
    * that was read.
    */
   public void reportsLossRead(short executionUnit,
                               int type,
                               int numReportsLost);
}
