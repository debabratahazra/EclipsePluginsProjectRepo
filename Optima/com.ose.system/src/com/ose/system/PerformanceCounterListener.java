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

package com.ose.system;

import java.util.EventListener;

/**
 * PerformanceCounterListener defines the interface for a client object that
 * wants to retrieve performance counter reports from a target system.
 *
 * @see java.util.EventListener
 * @see com.ose.system.PerformanceCounterReports
 */
public interface PerformanceCounterListener extends EventListener
{
   /**
    * Invoked each time a batch of performance counter reports of the specified
    * type and for the specified execution unit is retrieved from the target.
    *
    * @param reports  the retrieved performance counter reports.
    */
   public void reportsRetrieved(PerformanceCounterReports reports);

   /**
    * Invoked each time the target has thrown away a number of performance
    * counter reports of the specified type and for the specified execution unit
    * due to flow control.
    *
    * @param executionUnit   the execution unit from where the performance
    * counter reports were lost.
    * @param type            the performance counter type of the lost
    * performance counter reports.
    * @param numReportsLost  the number of lost performance counter reports.
    */
   public void reportsLost(short executionUnit, int type, int numReportsLost);
}
