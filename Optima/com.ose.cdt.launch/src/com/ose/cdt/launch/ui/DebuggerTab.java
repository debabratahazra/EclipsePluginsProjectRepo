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

import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.launch.ui.CDebuggerTab;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants;

public class DebuggerTab extends CDebuggerTab
{
   public DebuggerTab()
   {
      super(false);
   }

   protected void loadDebuggerComboBox(ILaunchConfiguration config,
                                       String selection)
   {
      ICDebugConfiguration debugConfig;
      String debugConfigId;
      boolean noSelection;
      String defaultSelection;

      try
      {
         debugConfig = CDebugCorePlugin.getDefault().getDebugConfiguration(
               IOSEMILaunchConfigurationConstants.OSE_DEBUGGER_ID);
         debugConfigId = debugConfig.getID();
      }
      catch (CoreException e)
      {
         CDebugCorePlugin.log(e);
         return;
      }

      noSelection = ((selection == null) || (selection.length() == 0) ||
                     !selection.equals(debugConfigId));
      if (noSelection)
      {
         defaultSelection = debugConfigId;
      }
      else
      {
         defaultSelection = selection;
      }

      setInitializeDefault(noSelection);
      loadDebuggerCombo(new ICDebugConfiguration[] {debugConfig},
                        defaultSelection);
   }

   /*
    * This method is a dummy version of CDebuggerTab.validateCPU() that avoids
    * parsing the binary file, which can be very slow if I/O-latency is high.
    */
   protected boolean validateCPU(ILaunchConfiguration config,
                                 ICDebugConfiguration debugConfig)
   {
      return true;
   }

   public String getId()
   {
      return "com.ose.cdt.launch.ui.debuggerTab";
   }
}
