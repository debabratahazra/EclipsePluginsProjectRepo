/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.cdt.launch.ui;

import org.eclipse.cdt.launch.ui.CArgumentsTab;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

public class ArgumentsTab extends CArgumentsTab
{
   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   public String getId()
   {
      return "com.ose.cdt.launch.ui.argumentsTab";
   }
}
