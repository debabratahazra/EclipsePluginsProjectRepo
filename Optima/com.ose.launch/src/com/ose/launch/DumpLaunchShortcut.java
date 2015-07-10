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

package com.ose.launch;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
import com.ose.launch.ui.LaunchUIPlugin;

public class DumpLaunchShortcut implements ILaunchShortcut
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

      if (object instanceof IFile)
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

      if (mode.equals(ILaunchManager.RUN_MODE) ||
          mode.equals(ILaunchManager.DEBUG_MODE))
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
      LaunchConfigurationSelectionDialog dialog;
      int result;
      String debugScope;
      ILaunchConfiguration config;

      // Get launch configuration type.
      launchManager = DebugPlugin.getDefault().getLaunchManager();
      configType = launchManager.getLaunchConfigurationType(
            IOSELaunchConfigurationConstants.ID_LAUNCH_DUMP);

      // Find existing compatible launch configurations.
      configs = findLaunchConfigurations(configType, mode);

      // Show launch configuration selection dialog.
      dialog = new LaunchConfigurationSelectionDialog(
            DebugUITools.newDebugModelPresentation(), configs, mode);
      result = dialog.open();
      debugScope = dialog.getDebugScope();
      if (result == Window.OK)
      {
         // Use an existing launch configuration and update it.
         config = (ILaunchConfiguration) dialog.getFirstResult();
         if (config != null)
         {
            config = updateLaunchConfiguration(config, mode, file, debugScope);
         }
      }
      else if (result == LaunchConfigurationSelectionDialog.CREATE_ID)
      {
         // Create a new launch configuration.
         config = createLaunchConfiguration(configType, mode, file, debugScope);
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
         String launchGroup = (mode.equals(ILaunchManager.RUN_MODE) ?
                               IDebugUIConstants.ID_RUN_LAUNCH_GROUP :
                               IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP);

         DebugUITools.openLaunchConfigurationDialogOnGroup(
               LaunchUIPlugin.getShell(),
               new StructuredSelection(config),
               launchGroup);
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
         LaunchUIPlugin.log(e);
      }

      return candidateConfigs;
   }

   private ILaunchConfiguration createLaunchConfiguration(
         ILaunchConfigurationType configType,
         String mode,
         File file,
         String debugScope)
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
      config = (mode.equals(ILaunchManager.RUN_MODE) ?
                editRunLaunchConfiguration(wc, file) :
                editDebugLaunchConfiguration(wc, file, debugScope));
      return config;
   }

   private ILaunchConfiguration updateLaunchConfiguration(
         ILaunchConfiguration config,
         String mode,
         File file,
         String debugScope)
      throws CoreException
   {
      ILaunchConfigurationWorkingCopy wc;

      wc = config.getWorkingCopy();
      config = (mode.equals(ILaunchManager.RUN_MODE) ?
                editRunLaunchConfiguration(wc, file) :
                editDebugLaunchConfiguration(wc, file, debugScope));
      return config;
   }

   private ILaunchConfiguration editRunLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         File file)
      throws CoreException
   {
      try
      {
         ILaunchConfiguration config;

         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_ADDRESS,
               InetAddress.getLocalHost().getHostAddress());
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_GATE_PORT,
               0);
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_TARGET_HUNT_PATH,
               "");
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_DUMP_MONITOR_MANAGED,
               true);
         wc.setAttribute(IOSELaunchConfigurationConstants.ATTR_DUMP_FILE,
               file.getAbsolutePath());
         config = wc.doSave();
         return config;
      }
      catch (UnknownHostException e)
      {
         throw new CoreException(LaunchUIPlugin.createErrorStatus(e));
      }
   }

   protected ILaunchConfiguration editDebugLaunchConfiguration(
         ILaunchConfigurationWorkingCopy wc,
         File file,
         String debugScope)
      throws CoreException
   {
      return null;
   }

   private void errorDialog(String message)
   {
      IStatus status = LaunchUIPlugin.createErrorStatus(message);
      LaunchUIPlugin.errorDialog("Error Launching Dump", message, status);
   }

   private void errorDialog(String message, Throwable t)
   {
      IStatus status = LaunchUIPlugin.createErrorStatus(message, t);
      LaunchUIPlugin.errorDialog("Error Launching Dump", message, status);
   }
}
