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

package com.ose.system;

/**
 * This class contains information about OSE target reports.
 */
public abstract class TargetReports
{
   private final Target target;
   private final boolean enabled;
   TargetReport[] reports;

   /**
    * Create a new target reports object.
    *
    * @param target   the target.
    * @param enabled  the enablement status.
    */
   TargetReports(Target target, boolean enabled)
   {
      if (target == null)
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.enabled = enabled;
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
    * Determine whether reporting is enabled or not.
    *
    * @return true if reporting is enabled, false otherwise.
    */
   public boolean isEnabled()
   {
      return enabled;
   }

   /**
    * Return an array of the reports (never null).
    *
    * @return an array of the reports.
    */
   public TargetReport[] getReports()
   {
      return reports;
   }
}
