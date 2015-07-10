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

package com.ose.event.format;

interface EventXMLTags
{
   public static final String DTD_PATH = "event.dtd";

   public static final String STYLESHEET_PATH = "event.xsl";
   public static final String STYLESHEET_TYPE = "text/xsl";

   public static final String TAG_EVENTS = "events";
   public static final String TAG_EVENT = "event";
   public static final String TAG_REFERENCE = "reference";
   public static final String TAG_TICK = "tick";
   public static final String TAG_MICRO_TICK = "microTick";
   public static final String TAG_SECONDS = "seconds";
   public static final String TAG_SECONDS_TICK = "secondsTick";
   public static final String TAG_FILE = "file";
   public static final String TAG_LINE = "line";
   public static final String TAG_FROM = "from";
   public static final String TAG_TO = "to";
   public static final String TAG_FROM_EXECUTION_UNIT = "fromExecutionUnit";
   public static final String TAG_TO_EXECUTION_UNIT = "toExecutionUnit";
   public static final String TAG_NUMBER = "number";
   public static final String TAG_ADDRESS = "address";
   public static final String TAG_SIZE = "size";
   public static final String TAG_ACTUAL_SIZE = "actualSize";
   public static final String TAG_DATA = "data";
   public static final String TAG_BINARY = "binary";
   public static final String TAG_STRUCT = "struct";
   public static final String TAG_MEMBER = "member";
   public static final String TAG_TIMEOUT = "timeout";
   public static final String TAG_TIMEOUT_SOURCE = "timeoutSource";
   public static final String TAG_KERNEL_CALLED = "kernelCalled";
   public static final String TAG_ERROR = "error";
   public static final String TAG_EXTRA = "extra";
   public static final String TAG_WARM_RESET = "warmReset";
   public static final String TAG_LOST_SIZE = "lostSize";

   public static final String ATTR_EVENTS_TARGET = "target";
   public static final String ATTR_EVENTS_BYTE_ORDER = "byteOrder";
   public static final String ATTR_EVENTS_OS_TYPE = "osType";
   public static final String ATTR_EVENTS_NUM_EXECUTION_UNITS = "numExecutionUnits";
   public static final String ATTR_EVENTS_TICK_LENGTH = "tickLength";
   public static final String ATTR_EVENTS_NTICK_FREQUENCY = "microTickFrequency";
   public static final String ATTR_EVENTS_SCOPE = "scope";
   public static final String ATTR_EVENTS_EVENT_ACTIONS = "eventActions";
   public static final String ATTR_EVENT_TYPE = "type";
   public static final String ATTR_STRUCT_NAME = "name";
   public static final String ATTR_MEMBER_NAME = "name";
   public static final String ATTR_MEMBER_VALUE = "value";
}
