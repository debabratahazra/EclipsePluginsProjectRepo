/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.ui.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import com.ose.pmd.dump.AbstractBlock;
import com.ose.pmd.dump.DumpFile;
import com.ose.pmd.dump.IffException;
import com.ose.pmd.editor.BlockComparator;

public class DumpFormEditor extends EditorPart
{
   private DumpFile dumpFile;
   private AbstractBlock[] blocks;
   private DumpForm dumpForm;

   public void init(IEditorSite site, IEditorInput input)
      throws PartInitException
   {
      boolean dumpOK = false;

      if (!(input instanceof IURIEditorInput))
      {
         throw new PartInitException("Invalid editor input");
      }

      try
      {
         File file;
         List blockList;

         file = new File(((IURIEditorInput) input).getURI());
         dumpFile = new DumpFile(file);
         blockList = new ArrayList();
         blockList.addAll(dumpFile.getErrorBlocks());
         blockList.addAll(dumpFile.getTextBlocks());
         blockList.addAll(dumpFile.getMemoryBlocks());
         blockList.addAll(dumpFile.getSignalBlocks());
         Collections.sort(blockList, new BlockComparator());
         blocks = (AbstractBlock[]) blockList.toArray(new AbstractBlock[0]);
         dumpOK = true;
      }
      catch (IffException e)
      {
         throw new PartInitException("Invalid dump file", e);
      }
      catch (IOException e)
      {
         throw new PartInitException("Error reading dump file", e);
      }
      finally
      {
         if ((dumpFile != null) && !dumpOK)
         {
            dumpFile.close();
         }
      }

      setSite(site);
      setInput(input);
      setPartName(input.getName());
   }

   public void createPartControl(Composite parent)
   {
      dumpForm = new DumpForm(this, dumpFile, blocks);
      dumpForm.createContents(parent);
   }

   public void dispose()
   {
      if (dumpForm != null)
      {
         dumpForm.dispose();
      }
      if (dumpFile != null)
      {
         dumpFile.close();
      }
      super.dispose();
   }

   public void setFocus()
   {
      dumpForm.setFocus();
   }

   public boolean isDirty()
   {
      return false;
   }

   public void doSave(IProgressMonitor monitor)
   {
      // Not supported.
   }

   public void doSaveAs()
   {
      // Not supported.
   }

   public boolean isSaveAsAllowed()
   {
      return false;
   }

   public Object getAdapter(Class adapter)
   {
      if (IFindReplaceTarget.class.equals(adapter))
      {
         return ((dumpForm != null) ? dumpForm.getFindTargetHandler() : null);
      }
      else
      {
         return super.getAdapter(adapter);
      }
   }
}
