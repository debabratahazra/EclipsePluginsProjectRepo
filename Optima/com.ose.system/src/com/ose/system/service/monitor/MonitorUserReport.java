/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.system.service.monitor;

public class MonitorUserReport
{
   public int tick;              // U32
   public int ntick;             // U32
   public int interval;          // U32
   public int sumOther;          // S32
   public int maxOther;          // S32
   public int minOther;          // S32
   public int reportValuesCount; // U32
   public MonitorUserReportValue[] reportValues; // MonitorUserReportValue[]

   public static class MonitorUserReportValue
   {
      public int id;  // U32
      public int sum; // S32
   }

   public static class MonitorMaxMinUserReportValue extends MonitorUserReportValue
   {
      public int max; // S32
      public int min; // S32
   }
}
