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

package com.ose.perf.ui.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ProfilerEditorInput implements IEditorInput
{
   private final ILaunch launch;
   private final File resultsFile;
   private final List binaryFiles;

   public ProfilerEditorInput(ILaunch launch, String resultsFilePath, List binaries)
   {
      this.launch = launch;
      this.resultsFile = new File(resultsFilePath);
      this.binaryFiles = new ArrayList(binaries);
   }

   public boolean exists()
   {
      return true;
   }

   public ImageDescriptor getImageDescriptor()
   {
      return null;
   }

   public String getName()
   {
      return resultsFile.getName();
   }

   public IPersistableElement getPersistable()
   {
      return null;
   }

   public String getToolTipText()
   {
      try
      {
         return resultsFile.getCanonicalPath();
      }
      catch (IOException e)
      {
         return resultsFile.getAbsolutePath();
      }
   }

   public Object getAdapter(Class adapter)
   {
      return null;
   }

   public ILaunch getLaunch()
   {
      return launch;
   }

   public ISourceLookupDirector getSourceLookupDirector()
   {
      return (ISourceLookupDirector) launch.getSourceLocator();
   }

   public File getResultsFile()
   {
      return resultsFile;
   }

   public List getBinaryFiles()
   {
      return binaryFiles;
   }

   public boolean equals(Object obj)
   {
      if (obj instanceof ProfilerEditorInput)
      {
         ProfilerEditorInput otherInput = (ProfilerEditorInput) obj;
         return getResultsFile().equals(otherInput.getResultsFile());
      }
      return false;
   }

   public int hashCode()
   {
      int result = getResultsFile().hashCode();
      result = 73 * result + getResultsFile().getAbsolutePath().length();
      return result;
   }
}
