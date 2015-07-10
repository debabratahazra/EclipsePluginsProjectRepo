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

/**
 * This class contains enablement information for a performance counter type.
 * A PerformanceCounterEnabledInfo object is immutable and its content is a
 * snapshot at the time of the retrieving call.
 *
 * @see com.ose.system.Target#getPerformanceCounterEnabled(IProgressMonitor, short, int)
 */
public class PerformanceCounterEnabledInfo
{
   private final Target target;

   private final boolean enabled;

   private final short executionUnit;

   private final int type;

   private final long value;

   private final int maxReports;

   /**
    * Create a new performance counter enablement info object.
    *
    * @param target        the target.
    * @param enabled       the enablement status.
    * @param euId          the execution unit.
    * @param counterType   the performance counter type.
    * @param counterValue  the trigger value of the performance counter type.
    * @param maxReports    the max reports limit.
    */
   PerformanceCounterEnabledInfo(Target target,
                                 boolean enabled,
                                 short euId,
                                 int counterType,
                                 long counterValue,
                                 int maxReports)
   {
      if (target == null)
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.enabled = enabled;
      this.executionUnit = euId;
      this.type = counterType;
      this.value = counterValue;
      this.maxReports = maxReports;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Determine whether this type of performance counter is enabled or not.
    *
    * @return true if this type of performance counter is enabled, false
    * otherwise.
    */
   public boolean isEnabled()
   {
      return enabled;
   }

   /**
    * Return the execution unit where this type of performance counter is
    * enabled or Target.ALL_EXECUTION_UNITS if it is enabled for all execution
    * units.
    *
    * @return the execution unit where this type of performance counter is
    * enabled or Target.ALL_EXECUTION_UNITS if it is enabled for all execution
    * units.
    * @see com.ose.system.Target#ALL_EXECUTION_UNITS
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }

   /**
    * Return the type of this performance counter.
    *
    * @return the type of this performance counter.
    */
   public int getType()
   {
      return type;
   }

   /**
    * Return the trigger value of this performance counter.
    *
    * @return the trigger value of this performance counter.
    */
   public long getValue()
   {
      return value;
   }

   /**
    * Return the maximum number of performance counter reports kept in the
    * target for this type of performance counter.
    *
    * @return the maximum number of performance counter reports kept in the
    * target for this type of performance counter.
    */
   public int getMaxReports()
   {
      return maxReports;
   }
}
