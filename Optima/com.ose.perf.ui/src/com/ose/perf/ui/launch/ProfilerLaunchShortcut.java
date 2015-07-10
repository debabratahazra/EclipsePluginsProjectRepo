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

package com.ose.perf.ui.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IURIEditorInput;
import com.ose.perf.ui.ProfilerPlugin;

public class ProfilerLaunchShortcut implements ILaunchShortcut
{
   public void launch(ISelection selection, String mode)
   {
      if (selection instanceof IStructuredSelection)
      {
         launch(((IStructuredSelection) selection).getFirstElement(), mode);
      }
   }

   public void launch(IEditorPart editor, String mode)
   {
      launch(editor.getEditorInput(), mode);
   }

   private void launch(Object object, String mode)
   {
      File file = null;

      if (object instanceof File)
      {
         file = (File) object;
      }
      else if (object instanceof IFile)
      {
         IPath path = ((IFile) object).getRawLocation();
         if (path != null)
         {
            file = path.toFile();
         }
         else
         {
            errorDialog("Unsupported file type");
            return;
         }
      }
      else if (object instanceof IURIEditorInput)
      {
         file = new File(((IURIEditorInput) object).getURI());
      }
      else
      {
         errorDialog("Unsupported file type");
         return;
      }

      if (mode.equals(ILaunchManager.PROFILE_MODE))
      {
         try
         {
            launch(file, mode);
         }
         catch (Exception e)
         {
            errorDialog("Launching failed", e);
         }
      }
      else
      {
         errorDialog("Unsupported launch mode");
      }
   }

   private void launch(File file, String mode) throws CoreException
   {
      ILaunchManager launchManager;
      ILaunchConfigurationType configType;
      List configs;
      ProfilerLaunchConfigurationSelectionDialog dialog;
      int result;
      ILaunchConfiguration config;

      // Get launch configuration type.
      launchManager = DebugPlugin.getDefault().getLaunchManager();
      configType = launchManager.getLaunchConfigurationType(
            IProfilerLaunchConfigurationConstants.ID_LAUNCH_SOURCE_PROFILER);

      // Find existing compatible launch configurations.
      configs = findLaunchConfigurations(configType, mode);

      // Show launch configuration selection dialog.
      dialog = new ProfilerLaunchConfigurationSelectionDialog(
            DebugUITools.newDebugModelPresentation(), configs);
      result = dialog.open();
      if (result == Window.OK)
      {
         // Use an existing launch configuration and update it.
         config = (ILaunchConfiguration) dialog.getFirstResult();
         if (config != null)
         {
            config = updateLaunchConfiguration(config, file);
         }
      }
      else if (result == ProfilerLaunchConfigurationSelectionDialog.CREATE_ID)
      {
         // Create a new launch configuration.
         config = createLaunchConfiguration(configType, file);
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
               ProfilerPlugin.getShell(),
               new StructuredSelection(config),
               IDebugUIConstants.ID_PROFILE_LAUNCH_GROUP);
      }
   }

   private List findLaunchConfigurations(ILaunchConfigurationType configType,
                                         String mode)
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
            if (config.supportsMode(mode) &&
                !DebugUITools.isPrivate(config))
            {
               candidateConfigs.add(config);
            }
         }
      }
      catch (CoreException e)
      {
         ProfilerPlugin.log(e);
      }

      return candidateConfigs;
   }

   private ILaunchConfiguration createLaunchConfiguration(
         ILaunchConfigurationType configType,
         File file)
      throws CoreException
   {
      ILaunchManager launchManager;
      String name;
      ILaunchConfigurationWorkingCopy wc;
      ILaunchConfiguration config;

      launchManager = DebugPlugin.getDefault().getLaunchManager();
      name = launchManager.generateUniqueLaunchConfigurationNameFrom(
            "New_configuration");
      wc = configType.newInstance(null, name);
      config = editLaunchConfiguration(wc, file);
      return config;
   }

   private ILaunchConfiguration updateLaunchConfiguration(
         ILaunchConfiguration config,
         File file)
      throws CoreException
   {
      ILaunchConfigurationWorkingCopy wc;

      wc = config.getWorkingCopy();
      config = editLaunchConfiguration(wc, file);
      return config;
   }

   private ILaunchConfiguration editLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         File file)
      throws CoreException
   {
      ILaunchConfiguration config;

      wc.setAttribute(IProfilerLaunchConfigurationConstants.ATTR_DUMP_FILE,
            file.getAbsolutePath());
      config = wc.doSave();
      return config;
   }

   private void errorDialog(String message)
   {
      IStatus status = ProfilerPlugin.createErrorStatus(message);
      ProfilerPlugin.errorDialog("Error Launching Source Profiler Editor", message, status);
   }

   private void errorDialog(String message, Throwable t)
   {
      IStatus status = ProfilerPlugin.createErrorStatus(message, t);
      ProfilerPlugin.errorDialog("Error Launching Source Profiler Editor", message, status);
   }
}
