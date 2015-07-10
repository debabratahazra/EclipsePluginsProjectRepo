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

package com.ose.event.ui.editors.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Manages the installation/deinstallation of global actions for multi-page
 * editors. Responsible for the redirection of global actions to the active
 * editor. Multi-page contributor replaces the contributors for the individual
 * editors in the multi-page editor.
 */
public class MultiPageEditorContributor extends
      MultiPageEditorActionBarContributor
{
   private IEditorPart activeEditorPart;

   /**
    * Creates a multi-page contributor.
    */
   public MultiPageEditorContributor()
   {
      super();
   }

   /**
    * Returns the action registed with the given text editor.
    * 
    * @return IAction or null if editor is null.
    */
   protected IAction getAction(ITextEditor editor, String actionID)
   {
      return (editor == null ? null : editor.getAction(actionID));
   }

   public void setActivePage(IEditorPart part)
   {
      if (activeEditorPart == part)
         return;

      activeEditorPart = part;

      IActionBars actionBars = getActionBars();
      if (actionBars != null)
      {

         ITextEditor editor = (part instanceof ITextEditor) ? (ITextEditor) part
               : null;

         actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
               getAction(editor, ITextEditorActionConstants.DELETE));
         actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
               getAction(editor, ITextEditorActionConstants.UNDO));
         actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
               getAction(editor, ITextEditorActionConstants.REDO));
         actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),
               getAction(editor, ITextEditorActionConstants.CUT));
         actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
               getAction(editor, ITextEditorActionConstants.COPY));
         actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
               getAction(editor, ITextEditorActionConstants.PASTE));
         actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
               getAction(editor, ITextEditorActionConstants.SELECT_ALL));
         actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),
               getAction(editor, ITextEditorActionConstants.FIND));
         actionBars.setGlobalActionHandler(IDEActionFactory.BOOKMARK.getId(),
               getAction(editor, IDEActionFactory.BOOKMARK.getId()));
         actionBars.updateActionBars();
      }
   }
}
