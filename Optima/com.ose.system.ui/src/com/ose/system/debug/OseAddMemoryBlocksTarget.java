/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.system.debug;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.actions.IAddMemoryBlocksTarget;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;
import com.ose.system.OseObject;
import com.ose.system.Target;
import com.ose.system.ui.dialogs.ShowMemoryDialog;
import com.ose.system.ui.util.AbstractShowMemoryAction;

public class OseAddMemoryBlocksTarget extends AbstractShowMemoryAction
   implements IAddMemoryBlocksTarget
{
   private final OseDebugTarget debugTarget;

   public OseAddMemoryBlocksTarget(OseDebugTarget debugTarget)
   {
      super("Add Memory Monitor");
      if (debugTarget == null)
      {
         throw new IllegalArgumentException();
      }
      this.debugTarget = debugTarget;
   }

   public boolean supportsAddMemoryBlocks(IWorkbenchPart part)
   {
      Target target = debugTarget.getTarget();
      return (target.isConnected() && target.hasMemorySupport());
   }

   public boolean canAddMemoryBlocks(IWorkbenchPart part, ISelection selection)
      throws CoreException
   {
      OseObject object = getActiveObject();
      return ((object != null) && !object.isKilled());
   }

   public void addMemoryBlocks(IWorkbenchPart part, ISelection selection)
      throws CoreException
   {
      OseObject object = getActiveObject();

      if ((object != null) && !object.isKilled())
      {
         ShowMemoryDialog dialog =
            new ShowMemoryDialog(part.getSite().getShell(), "Add Memory Monitor");
         int result = dialog.open();
         if (result == Window.OK)
         {
            showMemory(debugTarget.getTarget(),
                       object.getId(),
                       dialog.getAddress(),
                       dialog.getLength());
         }
      }
   }

   private OseObject getActiveObject()
   {
      OseObject object = null;
      Object debugContextProvider;

      // Requires Eclipse 3.2 or later.
      debugContextProvider = debugTarget.getDebugContextProvider();
      if (debugContextProvider != null)
      {
         if (debugContextProvider instanceof OseDebugContextProvider)
         {
            object = ((OseDebugContextProvider) debugContextProvider)
               .getActiveObject();
         }
      }

      return object;
   }
}
