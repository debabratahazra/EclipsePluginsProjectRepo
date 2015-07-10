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

package com.ose.gateway.serial.launch;

interface ILaunchConfigurationConstants
{
   public static final String OSE_LAUNCH_ID = "com.ose.gateway.serial.launch";

   public static final String ATTR_SERIAL_PORT = OSE_LAUNCH_ID + ".SERIAL_PORT";

   public static final String ATTR_BAUD_RATE = OSE_LAUNCH_ID + ".BAUD_RATE";
}
