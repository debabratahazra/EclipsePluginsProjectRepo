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

package com.ose.system.ui.dialogs;

import java.util.Collections;
import java.util.List;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;
import com.ose.system.Target;
import com.ose.system.ui.SharedImages;

public class TargetSelectionDialog extends ListDialog
{
   public TargetSelectionDialog(Shell parent, List targets)
   {
      this(parent, targets, null);
   }

   public TargetSelectionDialog(Shell parent, List targets, Target target)
   {
      super(parent);
      if (targets == null)
      {
         throw new NullPointerException();
      }
      setTitle("Target Selection");
      setMessage("Select a target:");
      setAddCancelButton(true);
      setContentProvider(new TargetContentProvider());
      setLabelProvider(new TargetLabelProvider());
      setInput((Target[]) targets.toArray(new Target[0]));
      if (target != null)
      {
         setInitialElementSelections(Collections.singletonList(target));
      }
      else if (!targets.isEmpty())
      {
         setInitialElementSelections(Collections.singletonList(targets.get(0)));
      }
   }

   protected Control createContents(Composite parent)
   {
      Control contents = super.createContents(parent);
      validateDialog();
      return contents;
   }

   protected Control createDialogArea(Composite parent)
   {
      Control control = super.createDialogArea(parent);
      getTableViewer().addSelectionChangedListener(new TargetSelectionHandler());
      return control;
   }

   private void validateDialog()
   {
      IStructuredSelection selection =
         (IStructuredSelection) getTableViewer().getSelection();
      getOkButton().setEnabled(!selection.isEmpty());
   }

   public Target getTarget()
   {
      Object[] result = getResult();
      return (((result != null) && (result.length > 0)) ? (Target) result[0] : null);
   }

   class TargetSelectionHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         validateDialog();
      }
   }

   static class TargetContentProvider implements IStructuredContentProvider
   {
      private Target[] targets;

      public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
      {
         if (newInput instanceof Target[])
         {
            targets = (Target[]) newInput;
         }
      }

      public void dispose()
      {
         // Nothing to be done.
      }

      public Object[] getElements(Object input)
      {
         return ((targets != null) ? targets : new Target[0]);
      }
   }

   static class TargetLabelProvider extends LabelProvider
   {
      public String getText(Object element)
      {
         return ((element instanceof Target) ? ((Target) element).toString() : null);
      }

      public Image getImage(Object element)
      {
         return ((element instanceof Target) ?
                 SharedImages.get(SharedImages.IMG_OBJ_TARGET) :
                 null);
      }
   }
}
