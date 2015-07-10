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

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
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
import com.ose.cdt.debug.mi.core.GDBDebugger;
import com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.launch.IOSELaunchConfigurationConstants;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Process;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;

public class DebugActionDelegate extends LaunchActionDelegate
{
   private Process node;

   public void selectionChanged(IAction action, ISelection selection)
   {
      Object obj = null;
      if (selection instanceof IStructuredSelection)
      {
         obj = ((IStructuredSelection) selection).getFirstElement();
      }
      node = ((obj instanceof Process) ? ((Process) obj) : null);
   }

   protected SystemModelNode getSystemModelNode()
   {
      return node;
   }

   protected String getLaunchMode()
   {
      return ILaunchManager.DEBUG_MODE;
   }

   protected String getLaunchGroup()
   {
      return IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP;
   }

   protected ILaunchConfigurationType getLaunchConfigurationType()
   {
      String configTypeId;
      ILaunchConfigurationType configType;

      if (node == null)
      {
         throw new IllegalStateException();
      }

      configTypeId = (node.getTarget().isPostMortemMonitor() ?
                      IOSELaunchConfigurationConstants.ID_LAUNCH_DUMP :
                      (node.getBlock().getSid() == 0) ?
                      IOSELaunchConfigurationConstants.ID_LAUNCH_CORE_MODULE :
                      IOSELaunchConfigurationConstants.ID_LAUNCH_LOAD_MODULE);
      configType = DebugPlugin.getDefault().getLaunchManager()
         .getLaunchConfigurationType(configTypeId);
      return configType;
   }

   protected ILaunchConfiguration editLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         String configTypeId,
         SystemModelNode node)
   {
      ILaunchConfiguration config = null;

      if (!(node instanceof Process))
      {
         return null;
      }

      try
      {
         Process process = (Process) node;
         Block block = process.getBlock();
         Target target = block.getTarget();
         Gate gate = target.getGate();

         wc.setAttribute(
               IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
               gate.getAddress().getHostAddress());
         wc.setAttribute(
               IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
               gate.getPort());
         wc.setAttribute(
               IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH,
               target.getHuntPath());
         if (configTypeId.equals(IOSELaunchConfigurationConstants.ID_LAUNCH_CORE_MODULE))
         {
            wc.setAttribute(
                  IOSELaunchConfigurationConstants.ATTR_BOOT_DOWNLOAD,
                  false);
         }
         else if (configTypeId.equals(IOSELaunchConfigurationConstants.ID_LAUNCH_LOAD_MODULE))
         {
            wc.setAttribute(
                  IOSELaunchConfigurationConstants.ATTR_LM_DOWNLOAD,
                  false);
         }
         else if (configTypeId.equals(IOSELaunchConfigurationConstants.ID_LAUNCH_DUMP))
         {
            wc.setAttribute(
                  IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED,
                  false);
         }
         wc.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID,
               IOSEMILaunchConfigurationConstants.OSE_DEBUGGER_ID);
         if (target.isPostMortemMonitor())
         {
            wc.setAttribute(
                  ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
                  ICDTLaunchConfigurationConstants.DEBUGGER_MODE_CORE);
         }
         else
         {
            wc.setAttribute(
                  ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
                  ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN);
         }
         wc.setAttribute(
               ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN,
               false);
         wc.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUGGER_PROTOCOL,
               "mi");
         if (wc.getAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
               (String) null) == null)
         {
            wc.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
                  getGDB(process));
         }
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_DEBUG_SCOPE,
               getDebugScope());
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_SEGMENT_ID,
               "0x" + Integer.toHexString(block.getSid()).toUpperCase());
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_BLOCK_ID,
               "0x" + Integer.toHexString(process.getBid()).toUpperCase());
         wc.setAttribute(IOSEMILaunchConfigurationConstants.ATTR_PROCESS_ID,
               "0x" + Integer.toHexString(process.getId()).toUpperCase());
         config = wc.doSave();
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      return config;
   }

   private static String getGDB(Process process)
   {
      String gdbName;
      String gdbPath;

      switch (process.getTarget().getCpuType())
      {
         case Target.CPU_ARM:
            gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_ARM;
            break;
         case Target.CPU_MIPS:
            gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_MIPS;
            break;
         case Target.CPU_PPC:
            gdbName = IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_POWERPC;
            break;
         default:
            return IOSEMILaunchConfigurationConstants.VALUE_DEBUG_NAME_NATIVE;
      }

      gdbPath = GDBDebugger.findGDB(gdbName);
      return ((gdbPath != null) ? gdbPath : gdbName);
   }
}
