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

import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

public class MainTab extends CMainTab
{
   public void activated(ILaunchConfigurationWorkingCopy config)
   {
   }

   /*
    * This method is a lighter version of CMainTab.isValid() that avoids
    * parsing the binary file, which can be very slow if I/O-latency is high.
    */
   public boolean isValid(ILaunchConfiguration config)
   {
      String name;
      IProject project;
      IPath exePath;

      setErrorMessage(null);
      setMessage(null);

      name = fProjText.getText().trim();
      if (name.length() == 0)
      {
         setErrorMessage("Project not specified");
         return false;
      }

      project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
      if (!project.exists())
      {
         setErrorMessage("Project does not exist");
         return false;
      }
      else if (!project.isOpen())
      {
         setErrorMessage("Project must be opened");
         return false;
      }

      name = fProgText.getText().trim();
      if (name.length() == 0)
      {
         setErrorMessage("Program not specified");
         return false;
      }

      exePath = new Path(name);
      if (!exePath.isAbsolute())
      {
         if (!project.getFile(name).exists())
         {
            setErrorMessage("Program does not exist");
            return false;
         }
      }
      else if (!exePath.toFile().isFile())
      {
         setErrorMessage("Program does not exist");
         return false;
      }

      return true;
   }

   public String getId()
   {
      return "com.ose.cdt.launch.ui.mainTab";
   }
}
