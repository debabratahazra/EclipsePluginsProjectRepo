/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.cdt.debug.mi.core;

public interface IOSEMILaunchConfigurationConstants
{
   public static final String OSE_MI_LAUNCH_ID = "com.ose.cdt.debug.mi.core";

   public static final String OSE_DEBUGGER_ID = OSE_MI_LAUNCH_ID + ".CDebugger";

   public static final String ATTR_DEBUG_SCOPE = OSE_MI_LAUNCH_ID + ".DEBUG_SCOPE";

   public static final String ATTR_SEGMENT_ID = OSE_MI_LAUNCH_ID + ".SEGMENT_ID";

   public static final String ATTR_BLOCK_ID = OSE_MI_LAUNCH_ID + ".BLOCK_ID";

   public static final String ATTR_PROCESS_ID = OSE_MI_LAUNCH_ID + ".PROCESS_ID";

   public static final String VALUE_DEBUG_NAME_NATIVE = "gdb";

   public static final String VALUE_DEBUG_NAME_ARM = "arm-ose-gdb";

   public static final String VALUE_DEBUG_NAME_MIPS = "mips-ose-gdb";

   public static final String VALUE_DEBUG_NAME_POWERPC = "powerpc-ose-gdb";

   public static final String VALUE_DEBUG_SCOPE_SEGMENT = "Segment";

   public static final String VALUE_DEBUG_SCOPE_BLOCK = "Block";

   public static final String VALUE_DEBUG_SCOPE_PROCESS = "Process";
}
