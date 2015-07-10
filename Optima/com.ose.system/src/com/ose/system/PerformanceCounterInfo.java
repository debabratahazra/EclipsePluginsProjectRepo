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
 * This class contains information about a performance counter type.
 * A PerformanceCounterInfo object is immutable and its content is a
 * snapshot at the time of the retrieving call.
 *
 * @see com.ose.system.Target#getPerformanceCounters(IProgressMonitor)
 */
public class PerformanceCounterInfo
{
   private final int type;

   private final String name;

   /**
    * Create a new performance counter info object.
    *
    * @param counterType  the performance counter type.
    * @param name         the name of the performance counter type.
    */
   PerformanceCounterInfo(int counterType, String name)
   {
      this.type = counterType;
      this.name = name;
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
    * Return the name of this type of performance counter.
    *
    * @return the name of this type of performance counter.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Return a string representation of this performance counter info object.
    * The returned string is of the form "&lt;performance-counter-name&gt;".
    *
    * @return a string representation of this performance counter info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return name;
   }
}
