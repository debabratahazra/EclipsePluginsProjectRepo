/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.system.service.tracepoint.signal;

public class MonitorTracepointActionData
{

   public static final int MONITOR_TRACEPOINT_ACTION_SAVE_REGISTER = 0;
   public static final int MONITOR_TRACEPOINT_ACTION_EXPRESSION = 1;
   public static final int MONITOR_TRACEPOINT_ACTION_SAVE_MEMORY = 2;
   public static final int MONITOR_TRACEPOINT_ACTION_WHILE_STEPPING = 3;

   public MonitorTracepointActionExpression expression;
   public MonitorTracepointActionSaveMemory memory;
   public MonitorTracepointActionWhileStepping whileStepping;

   public class MonitorTracepointActionExpression
   {
      public int byteCodeLength;
      public byte[] byteCode;
   }

   public class MonitorTracepointActionSaveMemory
   {
      public int isRegister;
      public int length;
      public long base;
   }

   public class MonitorTracepointActionWhileStepping
   {
      public int stepCount;
   }
}
