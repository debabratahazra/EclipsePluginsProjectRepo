/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.ui.tests.utils;

public class EventActionStrings
{
   public static final String SYSTEM = "System";

   public static final String BLOCK = "Block";

   public static final String PROCESS = "Process";

   public static final String NAME_PATTERN = "Name Pattern";

   public static final int SEND_EVENT = 1;

   public static final int RECEIVE_EVENT = 2;

   public static final int SWAP_EVENT = 3;

   public static final int CREATE_EVENT = 4;

   public static final int KILL_EVENT = 5;

   public static final int ERROR_EVENT = 6;

   public static final int BIND_EVENT = 7;

   public static final int ALLOC_EVENT = 8;

   public static final int FREE_EVENT = 9;

   public static final int TIMEOUT_EVENT = 10;

   public static final int USER_EVENT = 11;

   public static final String[] EVENTS = {"Send", "Receive", "Swap", "Create",
         "Kill", "Error", "Bind", "Alloc", "Free", "Timeout", "User"};

   public static final int TRACE_ACTION = 1;

   public static final int NOTIFY_ACTION = 2;

   public static final int INTERCEPT_ACTION = 3;

   public static final int SET_STATE_ACTION = 4;

   public static final int ENABLE_TRACE_ACTION = 5;

   public static final int DISABLE_TRACE_ACTION = 6;

   public static final int USER_ACTION = 7;

   public static final int ENABLE_HARDWARE_TRACE = 8;

   public static final int DISABLE_HARDWARE_TRACE = 9;

   public static final String[] ACTIONS = {"Trace", "Notify", "Intercept",
         "Set State", "Enable Trace", "Disable Trace", "User",
         "Enable Hardware Trace", "Disable Hardware Trace"};

   public static final String FROM_SCOPE_VALUE = "0x1000";

   public static final String TO_SCOPE_VALUE = "0xFFEE";

   public static final String ERROR_CODE_RANGE_VALUE[] = {"0x00100", "0xFFFFEE"};

   public static final String SIGNAL_NUMBER_RANGE_VALUE[] = {"0x00200",
         "0x0077FF"};

   public static final String USER_NUMBER_RANGE_VALUE[] = {"0x00500",
         "0x00EEFF"};

   public static final String SIGNAL_FILTER[] = {"0x1234", "4", "0x123456789", "0xABCDEF012"};
   
   public static final String IGNORE_COUNT_VALUE = "10";

   public static String getEventName(int eventID)
   {
      return EVENTS[eventID - 1];
   }

   public static String getActionName(int actionID)
   {
      return ACTIONS[actionID - 1];
   }
}
