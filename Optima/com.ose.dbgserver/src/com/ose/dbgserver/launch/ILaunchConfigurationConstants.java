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

package com.ose.dbgserver.launch;

interface ILaunchConfigurationConstants
{
   public static final String OSE_LAUNCH_ID = "com.ose.dbgserver.launch";

   public static final String ATTR_DEBUG_SERVER_ADDRESS =
      OSE_LAUNCH_ID + ".DEBUG_SERVER_ADDRESS";

   public static final String ATTR_DEBUG_SERVER_PORT =
      OSE_LAUNCH_ID + ".DEBUG_SERVER_PORT";

   public static final int DEFAULT_VALUE_DEBUG_SERVER_PORT = 1024;
}
