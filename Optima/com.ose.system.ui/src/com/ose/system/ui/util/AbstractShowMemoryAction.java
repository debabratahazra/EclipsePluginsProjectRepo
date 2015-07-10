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

package com.ose.system.ui.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.memory.IMemoryRendering;
import org.eclipse.debug.ui.memory.IMemoryRenderingContainer;
import org.eclipse.debug.ui.memory.IMemoryRenderingSite;
import org.eclipse.debug.ui.memory.IMemoryRenderingType;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import com.ose.system.Target;
import com.ose.system.debug.OseDebugTarget;

public abstract class AbstractShowMemoryAction extends Action
{
   protected static final String ID_MEMORY_VIEW =
      "org.eclipse.debug.ui.MemoryView";

   protected static final String ID_RENDERING_RAW_MEMORY =
      "org.eclipse.debug.ui.rendering.raw_memory";

   protected static final String ID_RENDERING_VIEW_PANE =
      "org.eclipse.debug.ui.MemoryView.RenderingViewPane.1";

   public AbstractShowMemoryAction(String text)
   {
      super(text);
   }

   protected void showMemory(Target target, int pid, long address, long length)
      throws CoreException
   {
      OseDebugTarget debugTarget = OseDebugTarget.createDebugTarget(target);
      if (debugTarget != null)
      {
         IMemoryBlock memoryBlock =
            debugTarget.getExtendedMemoryBlock(pid, address, length);
         DebugPlugin.getDefault().getMemoryBlockManager().
            addMemoryBlocks(new IMemoryBlock[] {memoryBlock});

         IWorkbenchPage page =
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
         if (page != null)
         {
            IViewPart view = page.showView(ID_MEMORY_VIEW);
            if (view instanceof IMemoryRenderingSite)
            {
               IMemoryRenderingType renderingType = DebugUITools
                  .getMemoryRenderingManager().getRenderingType(
                     ID_RENDERING_RAW_MEMORY);
               if (renderingType != null)
               {
                  IMemoryRenderingContainer renderingContainer =
                     ((IMemoryRenderingSite) view).getContainer(ID_RENDERING_VIEW_PANE);
                  if (renderingContainer != null)
                  {
                     IMemoryRendering rendering = renderingType.createRendering();
                     rendering.init(renderingContainer, memoryBlock);
                     renderingContainer.addMemoryRendering(rendering);
                  }
               }
            }
         }
      }
   }
}
