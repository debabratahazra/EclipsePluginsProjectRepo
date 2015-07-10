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

package com.ose.cdt.launch.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.system.Gate;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;

public class RunActionDelegate extends LaunchActionDelegate
{
   private Target node;

   public void selectionChanged(IAction action, ISelection selection)
   {
      Object obj = null;
      if (selection instanceof IStructuredSelection)
      {
         obj = ((IStructuredSelection) selection).getFirstElement();
      }
      node = ((obj instanceof Target) ? ((Target) obj) : null);
   }

   protected SystemModelNode getSystemModelNode()
   {
      return node;
   }

   protected String getLaunchMode()
   {
      return ILaunchManager.RUN_MODE;
   }

   protected String getLaunchGroup()
   {
      return IDebugUIConstants.ID_RUN_LAUNCH_GROUP;
   }

   protected ILaunchConfigurationType getLaunchConfigurationType()
   {
      return DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(
            IOSELaunchConfigurationConstants.ID_LAUNCH_LOAD_MODULE);
   }

   protected ILaunchConfiguration editLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         String configTypeId,
         SystemModelNode node)
   {
      ILaunchConfiguration config = null;

      if (!(node instanceof Target))
      {
         return null;
      }

      try
      {
         Target target = (Target) node;
         Gate gate = target.getGate();

         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
               gate.getAddress().getHostAddress());
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
               gate.getPort());
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH,
               target.getHuntPath());
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD,
               true);
         config = wc.doSave();
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      return config;
   }
}
