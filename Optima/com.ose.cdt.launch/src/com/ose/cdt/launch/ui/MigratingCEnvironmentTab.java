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

package com.ose.cdt.launch.ui;

import java.util.Map;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.EnvironmentTab;

/**
 * Temporary class until CDT launch configurations
 * are fully migrated to new EnvironmentTab.
 */
public class MigratingCEnvironmentTab extends EnvironmentTab
{
   public void initializeFrom(ILaunchConfiguration config)
   {
      if (config instanceof ILaunchConfigurationWorkingCopy)
      {
         ILaunchConfigurationWorkingCopy wc = (ILaunchConfigurationWorkingCopy) config;
         try
         {
            Map map = wc.getAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ENVIROMENT_MAP, (Map) null);
            if (map != null)
            {
               wc.setAttribute(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, map);
               wc.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_ENVIROMENT_MAP, (Map) null);
            }
         }
         catch (CoreException e)
         {
            LaunchUIPlugin.log(e);
         }
      }
      super.initializeFrom(config);
   }
}
