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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import com.ose.perf.ui.ProfilerPlugin;
import com.ose.perf.ui.editor.ProfilerEditorInput;

public class ProfilerLaunchConfigurationDelegate
   implements ILaunchConfigurationDelegate
{
   public void launch(ILaunchConfiguration config,
                      String mode,
                      ILaunch launch,
                      IProgressMonitor monitor)
      throws CoreException
   {
      if (monitor == null)
      {
         monitor = new NullProgressMonitor();
      }

      if (mode.equals(ILaunchManager.PROFILE_MODE))
      {
         monitor.beginTask("Launching Source Profiling Editor",
               IProgressMonitor.UNKNOWN);

         // Get the path of the user specified PMD file.
         String dumpFile = config.getAttribute(
               IProfilerLaunchConfigurationConstants.ATTR_DUMP_FILE, "");

         // Get the user specified binary files.
         List binaryFileEntries = config.getAttribute(
               IProfilerLaunchConfigurationConstants.ATTR_BINARY_FILES,
               (List) null);
         List binaryFiles = new ArrayList();
         for (Iterator i = binaryFileEntries.iterator(); i.hasNext();)
         {
            BinaryFileContainer binaryFile = new BinaryFileContainer(
                  (String) i.next());
            binaryFiles.add(binaryFile);
         }

         // Create the input object to pass to the editor.
         ProfilerEditorInput input =
            new ProfilerEditorInput(launch, dumpFile, binaryFiles);

         // Create dummy launch process, required for being able to terminate
         // the source profiler launch.
         new LaunchProcess(launch, dumpFile);

         // Open the editor asynchronously on the UI thread.
         Display.getDefault().asyncExec(new OpenEditorRunner(input));

         monitor.done();
      }
   }

   private static class OpenEditorRunner implements Runnable
   {
      private final ProfilerEditorInput input;

      OpenEditorRunner(ProfilerEditorInput input)
      {
         this.input = input;
      }

      public void run()
      {
         IWorkbenchPage page = PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getActivePage();

         if (page != null)
         {
            try
            {
               page.openEditor(input, "com.ose.perf.ui.editor.ProfilerEditor",
                     true, IWorkbenchPage.MATCH_NONE);
            }
            catch (PartInitException e)
            {
               ProfilerPlugin.log(e);
            }
         }
      }
   }
}
