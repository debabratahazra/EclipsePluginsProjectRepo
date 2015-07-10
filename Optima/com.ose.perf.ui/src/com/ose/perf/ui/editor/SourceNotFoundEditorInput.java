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

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.ui.sourcelookup.CommonSourceNotFoundEditorInput;

public class SourceNotFoundEditorInput extends CommonSourceNotFoundEditorInput
{
   private final ILaunch launch;
   private final String missingFile;

   public SourceNotFoundEditorInput(Object artifact, ILaunch launch,
         String missingFile)
   {
      super(artifact);
      this.launch = launch;
      this.missingFile = missingFile;
   }

   public ILaunch getLaunch()
   {
      return launch;
   }

   public String getName()
   {
      return missingFile;
   }

   public boolean equals(Object obj)
   {
      if (obj instanceof SourceNotFoundEditorInput)
      {
         SourceNotFoundEditorInput other = (SourceNotFoundEditorInput) obj;
         return super.equals(other) || this.getName().equals(other.getName());
      }
      return super.equals(obj);
   }
}
