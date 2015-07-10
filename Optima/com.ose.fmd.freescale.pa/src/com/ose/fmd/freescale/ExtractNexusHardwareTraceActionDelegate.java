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

package com.ose.fmd.freescale;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This class is used when the "Extract Nexus Hardware Trace" action is chosen
 * on a PMD file. It uses PmdToDatFileConverter to extract the Nexus hardware
 * trace data from the PMD file and save it to a DAT file.
 */
public class ExtractNexusHardwareTraceActionDelegate implements
      IObjectActionDelegate
{
   private final List<IFile> files = new ArrayList<IFile>();

   public void setActivePart(IAction action, IWorkbenchPart targetPart)
   {
      // Nothing to do.
   }

   public void selectionChanged(IAction action, ISelection selection)
   {
      files.clear();

      if (selection instanceof IStructuredSelection)
      {
         IStructuredSelection sel = (IStructuredSelection) selection;

         for (Iterator i = sel.iterator(); i.hasNext();)
         {
            Object obj = i.next();
            if (obj instanceof IFile)
            {
               IFile file = (IFile) obj;
               files.add(file);
            }
         }
      }
   }

   public void run(IAction action)
   {
      for (Iterator<IFile> i = files.iterator(); i.hasNext();)
      {
         IFile pmdFile;
         File fromFile;
         File toFile;
         String fromFileName;
         String toFileName;

         pmdFile = i.next();
         fromFile = pmdFile.getLocation().toFile();
         fromFileName = fromFile.getName();

         if (!PmdToDatFileConverter.hasHardwareTrace(fromFile))
         {
            IWorkbenchWindow window =
               PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            if (window != null)
            {
               MessageDialog.openInformation(window.getShell(), "Information",
                  "The PMD file " + fromFileName +
                  " does not contain any Nexus hardware trace.");
            }
            return;
         }

         int j = fromFileName.lastIndexOf('.');
         if ((j > 0) && (j < fromFileName.length() - 1))
         {
            toFileName = fromFileName.substring(0, j) + ".dat";
         }
         else
         {
            toFileName = fromFileName + ".dat";
         }
         String toFilePath = getSaveFilePath(fromFile.getParent(), toFileName);
         if (toFilePath == null)
         {
            return;
         }
         toFile = new File(toFilePath);

         try
         {
            PmdToDatFileConverter converter = new PmdToDatFileConverter();
            converter.convert(fromFile, toFile);
            try
            {
               pmdFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
            } catch (CoreException ignore) {}
         }
         catch (Exception e)
         {
            toFile.delete();
            DebuggerPlugin.errorDialog(null,
               "Error extracting Nexus hardware trace from PMD file " +
               fromFileName, e);
         }
      }
   }

   private static String getSaveFilePath(String parent, String file)
   {
      Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
      FileDialog dialog = new FileDialog(shell, SWT.SAVE | SWT.APPLICATION_MODAL);
      dialog.setFilterPath(parent);
      dialog.setFileName(file);
      dialog.setOverwrite(true);
      return dialog.open();
   }
}
