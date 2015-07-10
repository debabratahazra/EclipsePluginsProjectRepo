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

package com.ose.system.ui.views.loadmodule;

import java.io.File;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;

public class LoadModuleDropAdapter extends ViewerDropAdapter
{
   private LoadModuleView view;

   private TransferData currentTransfer;

   public LoadModuleDropAdapter(LoadModuleView view)
   {
      super(view.getViewer());
      setFeedbackEnabled(false);
      this.view = view;
   }

   protected TransferData getCurrentTransfer()
   {
      return currentTransfer;
   }

   public void dragEnter(DropTargetEvent event)
   {
      // Default to copy.
      if ((event.detail == DND.DROP_DEFAULT) &&
          ((event.operations & DND.DROP_COPY) != 0))
      {
         event.detail = DND.DROP_COPY;
      }
      super.dragEnter(event);
   }

   public boolean validateDrop(Object target,
                               int operation,
                               TransferData transferType)
   {
      currentTransfer = transferType;
      return FileTransfer.getInstance().isSupportedType(transferType);
   }

   public boolean performDrop(Object data)
   {
      boolean result = false;

      if (FileTransfer.getInstance().isSupportedType(getCurrentTransfer()) &&
          (data instanceof String[]))
      {
         String[] filenames = (String[]) data;
         for (int i = 0; i < filenames.length; i++)
         {
            String filename = filenames[i];
            if ((new File(filename)).isFile())
            {
               view.loadModuleDropped(filename);
               result = true;
            }
         }
      }
      return result;
   }
}
