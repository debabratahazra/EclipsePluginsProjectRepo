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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import com.ose.cdt.debug.mi.core.IOSEMILaunchConfigurationConstants;
import com.ose.cdt.launch.internal.ui.LaunchUIPlugin;
import com.ose.launch.LaunchConfigurationSelectionDialog;
import com.ose.system.SystemModelNode;

public abstract class LaunchActionDelegate implements IViewActionDelegate
{
   private String debugScope =
      IOSEMILaunchConfigurationConstants.VALUE_DEBUG_SCOPE_PROCESS;

   public void init(IViewPart view)
   {
      // Nothing to do.
   }

   protected abstract SystemModelNode getSystemModelNode();

   protected abstract String getLaunchMode();

   protected abstract String getLaunchGroup();

   protected abstract ILaunchConfigurationType getLaunchConfigurationType();

   protected final String getDebugScope()
   {
      return debugScope;
   }

   public void run(IAction action)
   {
      SystemModelNode node = getSystemModelNode();
      if ((node != null) && !node.isKilled())
      {
         String launchMode;
         ILaunchConfigurationType configType;
         List configs;
         LaunchConfigurationSelectionDialog dialog;
         int result;
         ILaunchConfiguration config = null;

         // FIXME: Return with error dialog for debugging of soft kernels
         // (i.e. targets without runmode debug support)?

         // Determine launch mode.
         launchMode = getLaunchMode();

         // Determine launch configuration type.
         configType = getLaunchConfigurationType();

         // Find existing compatible launch configurations.
         configs = findLaunchConfigurations(configType, launchMode);

         // Show launch configuration selection dialog.
         dialog = new LaunchConfigurationSelectionDialog(
               DebugUITools.newDebugModelPresentation(), configs, launchMode);
         result = dialog.open();
         debugScope = dialog.getDebugScope();
         if (result == Window.OK)
         {
            // Use an existing launch configuration and update it.
            config = (ILaunchConfiguration) dialog.getFirstResult();
            if (config != null)
            {
               config = updateLaunchConfiguration(config, node);
            }
         }
         else if (result == LaunchConfigurationSelectionDialog.CREATE_ID)
         {
            // Create a new launch configuration.
            config = createLaunchConfiguration(configType, node);
         }
         else
         {
            // Launching cancelled.
            return;
         }

         // Open launch configuration dialog with
         // the launch configuration to be used.
         if (config != null)
         {
            DebugUITools.openLaunchConfigurationDialogOnGroup(
                  LaunchUIPlugin.getShell(),
                  new StructuredSelection(config),
                  getLaunchGroup());
         }
      }
   }

   protected List findLaunchConfigurations(ILaunchConfigurationType configType,
                                           String launchMode)
   {
      List candidateConfigs = Collections.EMPTY_LIST;

      try
      {
         ILaunchManager launchManager;
         ILaunchConfiguration[] configs;

         launchManager = DebugPlugin.getDefault().getLaunchManager();
         configs = launchManager.getLaunchConfigurations(configType);
         candidateConfigs = new ArrayList(configs.length);
         for (int i = 0; i < configs.length; i++)
         {
            ILaunchConfiguration config = configs[i];
            if (config.supportsMode(launchMode) &&
                !DebugUITools.isPrivate(config))
            {
               candidateConfigs.add(config);
            }
         }
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      return candidateConfigs;
   }

   protected ILaunchConfiguration createLaunchConfiguration(
         ILaunchConfigurationType configType, SystemModelNode node)
   {
      ILaunchConfiguration config = null;

      try
      {
         ILaunchManager launchManager;
         String name;
         ILaunchConfigurationWorkingCopy wc;

         launchManager = DebugPlugin.getDefault().getLaunchManager();
         name = launchManager.generateUniqueLaunchConfigurationNameFrom(
               "New_configuration");
         wc = configType.newInstance(null, name);
         config = editLaunchConfiguration(wc, configType.getIdentifier(), node);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      return config;
   }

   protected ILaunchConfiguration updateLaunchConfiguration(
         ILaunchConfiguration config, SystemModelNode node)
   {
      try
      {
         config = editLaunchConfiguration(config.getWorkingCopy(),
               config.getType().getIdentifier(), node);
      }
      catch (CoreException e)
      {
         LaunchUIPlugin.log(e);
      }
      return config;
   }

   protected abstract ILaunchConfiguration editLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         String configTypeId,
         SystemModelNode node);
}
