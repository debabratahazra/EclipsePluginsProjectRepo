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

package com.ose.launch;

public interface IOSELaunchConfigurationConstants
{
   /*
    * XXX: The word "cdt" in the following constants has been kept to preserve
    * compatibility with core module and load module launch configurations that
    * were created prior to the breakout of the generic launch functionality to
    * the com.ose.launch plugin.
    */

   public static final String OSE_LAUNCH_ID = "com.ose.cdt.launch";

   public static final String ID_LAUNCH_CORE_MODULE = "com.ose.cdt.launch.coreModuleLaunch";

   public static final String ID_LAUNCH_LOAD_MODULE = "com.ose.cdt.launch.loadModuleLaunch";

   public static final String ID_LAUNCH_DUMP = "com.ose.launch.dumpLaunch";

   public static final String ATTR_GATE_ADDRESS = OSE_LAUNCH_ID + ".GATE_ADDRESS";

   public static final String ATTR_GATE_PORT = OSE_LAUNCH_ID + ".GATE_PORT";

   public static final String ATTR_TARGET_HUNT_PATH = OSE_LAUNCH_ID + ".TARGET_HUNT_PATH";

   public static final String ATTR_HTTP_SERVER_PORT = OSE_LAUNCH_ID + ".HTTP_SERVER_PORT";

   public static final String ATTR_HTTP_VM_HUNT_PATH = OSE_LAUNCH_ID + ".HTTP_VM_HUNT_PATH";

   public static final String ATTR_BOOT_DOWNLOAD = OSE_LAUNCH_ID + ".BOOT_DOWNLOAD";

   public static final String ATTR_BOOT_LOADER = OSE_LAUNCH_ID + ".BOOT_LOADER";

   public static final String ATTR_BOOT_PROMPT = OSE_LAUNCH_ID + ".BOOT_PROMPT";

   public static final String ATTR_LM_DOWNLOAD = OSE_LAUNCH_ID + ".LM_DOWNLOAD";

   public static final String ATTR_LM_FILE = OSE_LAUNCH_ID + ".LM_FILE";

   public static final String ATTR_LM_ABSOLUTE = OSE_LAUNCH_ID + ".LM_ABSOLUTE";

   public static final String ATTR_LM_CPU_ID = OSE_LAUNCH_ID + ".LM_CPU_ID";

   public static final String ATTR_DUMP_MONITOR_MANAGED = OSE_LAUNCH_ID + ".DUMP_MONITOR_MANAGED";

   public static final String ATTR_DUMP_FILE = OSE_LAUNCH_ID + ".DUMP_FILE";

   public static final String VALUE_BOOT_POLO = "OSE POLO";

   public static final int DEFAULT_VALUE_GATE_PORT = 21768;
}
