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

package com.ose.prof.ui.editors.process;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import com.ose.prof.ui.ProfilerPlugin;

public class ProcessFileChangeListener implements IResourceChangeListener
{
   public ProcessFileChangeListener()
   {
      IDecoratorManager decoratorManager =
         ProfilerPlugin.getDefault().getWorkbench().getDecoratorManager();
      if (!decoratorManager.getEnabled(ProfilerPlugin.PROCESS_DECORATOR_ID))
      {
         try
         {
            decoratorManager.setEnabled(ProfilerPlugin.PROCESS_DECORATOR_ID, true);
         }
         catch (CoreException e)
         {
            ProfilerPlugin.log(e);
         }
      }
   }

   public void resourceChanged(IResourceChangeEvent event)
   {
      IResourceDelta resourceDelta = event.getDelta();
      if (resourceDelta != null)
      {
         closeEditorsForRemovedResources(resourceDelta);
         updateDecorations(resourceDelta);
      }
   }

   private void closeEditorsForRemovedResources(IResourceDelta resourceDelta)
   {
      Display display = ProfilerPlugin.getDefault().getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(new CloseEditorsRunner(resourceDelta));
      }
   }

   private void updateDecorations(IResourceDelta resourceDelta)
   {
      List children;
      IResource[] resources;
      Display display;

      children = new ArrayList();
      getAllAffectedResources(children, resourceDelta);
      resources = new IResource[children.size()];
      for (int i = 0; i < children.size(); i++)
      {
         resources[i] = (IResource) children.get(i);
      }

      display = ProfilerPlugin.getDefault().getWorkbench().getDisplay();
      if (!display.isDisposed())
      {
         display.asyncExec(new DecoratorRunner(resources));
      }
   }

   private void getAllAffectedResources(List children, IResourceDelta resourceDelta)
   {
      IResource resource = resourceDelta.getResource();
      children.add(resource);
      IResourceDelta[] affectedChildren = resourceDelta.getAffectedChildren();

      for (int i = 0; i < affectedChildren.length; i++)
      {
         getAllAffectedResources(children, affectedChildren[i]);
      }
   }

   private static class CloseEditorsRunner implements Runnable
   {
      private final IResourceDelta resourceDelta;

      CloseEditorsRunner(IResourceDelta resourceDelta)
      {
         this.resourceDelta = resourceDelta;
      }

      public void run()
      {
         closeEditors(resourceDelta);
      }

      private static void closeEditors(IResourceDelta resourceDelta)
      {
         IResourceDelta[] children = resourceDelta.getAffectedChildren();

         for (int i = 0; i < children.length; i++)
         {
            IResourceDelta child = children[i];

            if (child.getKind() == IResourceDelta.REMOVED)
            {
               IWorkbenchWindow window =
                  PlatformUI.getWorkbench().getActiveWorkbenchWindow();
               if (window != null)
               {
                  IWorkbenchPage page = window.getActivePage();
                  if (page != null)
                  {
                     IResource resource = child.getResource();
                     if (resource instanceof IFile)
                     {
                        IEditorInput editorInput =
                           new FileEditorInput((IFile) resource);
                        IEditorPart editor = getOpenedEditor(page, editorInput);
                        if (editor != null)
                        {
                           page.closeEditor(editor, false);
                        }
                     }
                  }
               }
            }

            closeEditors(child);
         }
      }

      private static IEditorPart getOpenedEditor(IWorkbenchPage page,
                                                 IEditorInput editorInput)
      {
         IEditorReference[] editorRefs = page.findEditors(
               editorInput,
               ProfilerPlugin.PROCESS_EDITOR_ID,
               IWorkbenchPage.MATCH_INPUT | IWorkbenchPage.MATCH_ID);
         return (editorRefs.length > 0) ? editorRefs[0].getEditor(true) : null;
      }
   }

   private static class DecoratorRunner implements Runnable
   {
      private final IResource[] resources;

      DecoratorRunner(IResource[] resources)
      {
         this.resources = resources;
      }

      public void run()
      {
         LabelDecorator.getLabelDecorator().refresh(resources);
      }
   }
}
