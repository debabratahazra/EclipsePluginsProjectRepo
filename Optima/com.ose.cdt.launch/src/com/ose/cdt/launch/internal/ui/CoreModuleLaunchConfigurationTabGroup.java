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

package com.ose.cdt.launch.internal.ui;

import com.ose.cdt.launch.ui.CoreModuleDownloadTab;
import com.ose.cdt.launch.ui.DebuggerTab;
import com.ose.cdt.launch.ui.MainTab;
import com.ose.launch.ui.ConnectionTab;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

public class CoreModuleLaunchConfigurationTabGroup
   extends AbstractLaunchConfigurationTabGroup
{
   public void createTabs(ILaunchConfigurationDialog dialog, String mode)
   {
      ILaunchConfigurationTab[] tabs;

      if (mode.equals(ILaunchManager.DEBUG_MODE))
      {
         tabs = new ILaunchConfigurationTab[]
            {
               new MainTab(),
               new ConnectionTab(),
               // FIXME: Uncomment when support for loading core modules is revived
               //new CoreModuleDownloadTab(),
               new DebuggerTab(),
               new SourceLookupTab(),
               new CommonTab()
            };
      }
      else
      {
         tabs = new ILaunchConfigurationTab[]
            {
               new MainTab(),
               new CoreModuleDownloadTab(),
               new CommonTab()
            };
      }
      setTabs(tabs);
   }

   public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
   {
      super.setDefaults(configuration);
      configuration.setAttribute(
         ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_VARIABLE_BOOKKEEPING,
         false);
      configuration.setAttribute(
         IMILaunchConfigurationConstants.ATTR_DEBUGGER_PROTOCOL,
         "mi");
   }
}
