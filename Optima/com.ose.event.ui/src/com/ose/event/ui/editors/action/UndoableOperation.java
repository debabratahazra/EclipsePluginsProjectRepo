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

package com.ose.event.ui.editors.action;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Node;
import com.ose.xmleditor.model.DocumentChangeEvent;
import com.ose.xmleditor.model.DocumentModel;

public class UndoableOperation extends AbstractOperation
{
   private final DocumentModel model;

   private final DocumentChangeEvent event;

   public UndoableOperation(String label, IUndoContext undoContext,
         DocumentModel model, DocumentChangeEvent event)
   {
      super(label);
      addContext(undoContext);
      this.model = model;
      this.event = event;
   }

   public IStatus execute(IProgressMonitor monitor, IAdaptable info)
         throws ExecutionException
   {
      // The operation has already been executed.
      return Status.OK_STATUS;
   }

   public IStatus redo(IProgressMonitor monitor, IAdaptable info)
         throws ExecutionException
   {
      undoOperation(false);
      return Status.OK_STATUS;
   }

   public IStatus undo(IProgressMonitor monitor, IAdaptable info)
         throws ExecutionException
   {
      undoOperation(true);
      return Status.OK_STATUS;
   }

   private void undoOperation(boolean undo)
   {
      if (undo)
      {
         switch (event.getType())
         {
            case DocumentChangeEvent.ADD:
               // Undo add event action node.
               model.removeNode(event.getNode());
               break;
            case DocumentChangeEvent.REMOVE:
               // Undo remove event action node.
               model.addToRootNode(event.getNode());
               break;
            case DocumentChangeEvent.CHANGE:
               // Undo the changing of an event action property.
               model.setValue(event.getNode(), event.getAttributeName(),
                     (String) event.getOldValue());
               break;
            case DocumentChangeEvent.REPLACE:
               // Undo the replacing of the action type property.
               model.replaceNode(
                     (Node) event.getNewValue(), (Node) event.getOldValue());
               break;
            default:
               break;
         }
      }
      else
      {
         switch (event.getType())
         {
            case DocumentChangeEvent.ADD:
               // Redo add event action node.
               model.addToRootNode(event.getNode());
               break;
            case DocumentChangeEvent.REMOVE:
               // Redo remove event action node.
               model.removeNode(event.getNode());
               break;
            case DocumentChangeEvent.CHANGE:
               // Redo the changing of an event action property.
               model.setValue(event.getNode(), event.getAttributeName(),
                     (String) event.getNewValue());
               break;
            case DocumentChangeEvent.REPLACE:
               // Redo the replacing of the action type property.
               model.replaceNode(
                     (Node) event.getOldValue(), (Node) event.getNewValue());
            default:
               break;
         }
      }
   }
}
