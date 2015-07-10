/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.prof.ui.editors.profiler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import com.ose.prof.ui.ProfilerPlugin;

public class SynchronizedScrollPartController
{
   private static final SynchronizedScrollPartController INSTANCE =
      new SynchronizedScrollPartController();

   // 10 days
   private static final long MAX_TIME_DIFF = 10L * 24L * 3600L * 1000000000L;
   
   private final List entries;

   private final SynchronizedScrollPartListener synchronizedScrollPartHandler;
   
   private long firstGlobalTime;
   
   private long lastGlobalTime;

   private SynchronizedScrollPartController()
   {
      enableDecorator();
      entries = Collections.synchronizedList(new ArrayList());
      synchronizedScrollPartHandler = new SynchronizedScrollPartHandler();
      Display.getDefault().asyncExec(new AddEditorPartListenerRunner());
   }

   public static SynchronizedScrollPartController getInstance()
   {
      return INSTANCE;
   }

   public boolean isFileSynchronized(IFile file)
   {
      return (getEntry(file) != null);
   }

   public boolean isEditorSynchronized(IEditorPart editor)
   {
      return (getEntry(editor) != null);
   }

   public void addFile(IFile file)
      throws UnsupportedOperationException, PartInitException
   {
      IWorkbenchPage page;
      IEditorInput editorInput;
      IEditorPart editor;

      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      // Return if the file is already synchronized.
      if (isFileSynchronized(file))
      {
         return;
      }

      // Return if the workbench is not available, should not happen though.
      page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      if (page == null)
      {
         return;
      }

      // Open corresponding editor if not already opened.
      editorInput = new FileEditorInput(file);
      editor = getOpenedEditor(page, editorInput);
      if (editor == null)
      {
         // This method never returns null, instead an instance
         // of ErrorEditorPart is returned in case of failure.
         editor = page.openEditor(editorInput,
            ProfilerPlugin.PROFILER_EDITOR_PROXY_ID,
            true, IWorkbenchPage.MATCH_INPUT | IWorkbenchPage.MATCH_ID);
      }

      /*
       * If a profiler editor has been newly opened it is not sure that
       * it has yet read all of the profiling reports from its file. We
       * cannot add the synchronized file/editor entry until the editor
       * is fully populated and ready. We do this by adding a property
       * listener on the editor to be informed when the editor is ready.
       * The property listener will add the synchronized file/editor entry
       * when it is informed that the editor is ready. However, if the
       * editor gets ready before we add our property listener, we must
       * add the synchronized file/editor entry ourselves.
       */
      if (editor instanceof ProfilerEditorProxy)
      {
         ProfilerEditorProxy editorProxy;
         EditorPropertyHandler editorPropertyHandler;
         int editorStatus;

         editorProxy = (ProfilerEditorProxy) editor;
         editorPropertyHandler = new EditorPropertyHandler();
         editor.addPropertyListener(editorPropertyHandler);
         editorStatus = editorProxy.getEditorStatus();
         if (editorStatus != ProfilerEditorProxy.EDITOR_STATUS_NOT_READY)
         {
            editorPropertyHandler.propertyChanged(editor, editorStatus);
         }
      }
      else
      {
         page.closeEditor(editor, true);
         throw new UnsupportedOperationException(
            "No suitable editor found for file " + file.getName());
      }
   }

   public void removeFile(IFile file)
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }

      synchronized (entries)
      {
         FileEditorEntry entry = getEntry(file);
         if (entry != null)
         {
            removeEntry(entry);
         }
      }
   }

   private void addEntry(FileEditorEntry entry)
   {
      if (hasEmptyTimeRange(entry))
      {
         ProfilerPlugin.errorDialog(null,
               "The file \"" + entry.getFile().getName()
               + "\" does not contain any reports to synchronize.",
               (Throwable)null);         
         return;
      }
      
      if (!isInReasonableTimeRange(entry))
      {
         ProfilerPlugin.errorDialog(null,
               "The time span of \"" + entry.getFile().getName()
               + "\" differs from the synchronized time span "
               + "by more than the allowed limit of ten days.",
               (Throwable)null);
         return;
      }
      
      // Add the synchronized file/editor entry.
      entries.add(entry);

      // Register as synchronized scroll part listener.
      entry.getSynchronizedScrollPart().
         addSynchronizedScrollPartListener(synchronizedScrollPartHandler);

      // Recalculate first and last global time stamps and inform all editors.
      setGlobalScrollWindowRange();

      // Decorate file and editor.
      updateDecorations(entry);
   }

   private void removeEntry(FileEditorEntry entry)
   {
      // Remove the synchronized file/editor entry.
      entries.remove(entry);

      // Unregister as synchronized scroll part listener.
      entry.getSynchronizedScrollPart().
         removeSynchronizedScrollPartListener(synchronizedScrollPartHandler);

      // Recalculate first and last global time stamps and inform all editors.
      setGlobalScrollWindowRange();

      // Undecorate file and editor.
      updateDecorations(entry);
   }

   private FileEditorEntry getEntry(IFile file)
   {
      synchronized (entries)
      {
         for (Iterator i = entries.iterator(); i.hasNext();)
         {
            FileEditorEntry entry = (FileEditorEntry) i.next();
            if (entry.getFile().equals(file))
            {
               return entry;
            }
         }
      }

      return null;
   }

   private FileEditorEntry getEntry(IEditorPart editor)
   {
      synchronized (entries)
      {
         for (Iterator i = entries.iterator(); i.hasNext();)
         {
            FileEditorEntry entry = (FileEditorEntry) i.next();
            if (entry.getEditor().equals(editor))
            {
               return entry;
            }
         }
      }

      return null;
   }

   private static IEditorPart getOpenedEditor(IWorkbenchPage page,
                                              IEditorInput editorInput)
   {
      IEditorReference[] editorRefs;

      editorRefs = page.findEditors(
            editorInput,
            ProfilerPlugin.PROFILER_EDITOR_PROXY_ID,
            IWorkbenchPage.MATCH_INPUT | IWorkbenchPage.MATCH_ID);

      return (editorRefs.length > 0) ? editorRefs[0].getEditor(true) : null;
   }

   private boolean hasEmptyTimeRange(FileEditorEntry entry)
   {
      SynchronizedScrollPart part = entry.getSynchronizedScrollPart();
      
      return part.getFirstLocalTime() == 0 && part.getLastLocalTime() == 0;
   }
   
   private boolean isInReasonableTimeRange(FileEditorEntry entry)
   {
      SynchronizedScrollPart part = entry.getSynchronizedScrollPart();
      
      if (entries.size() == 0)
      {
         return true;
      }
      
      if ((part.getFirstLocalTime() - lastGlobalTime > MAX_TIME_DIFF)
          || (firstGlobalTime - part.getLastLocalTime() > MAX_TIME_DIFF))
      {
         return false;
      }
      else
      {
         return true;
      }
   }
   
   private void setGlobalScrollWindowRange()
   {
      FileEditorEntry entry;
      SynchronizedScrollPart part;

      if (entries.size() == 0)
      {
         return;
      }

      entry = (FileEditorEntry) entries.get(0);
      part = entry.getSynchronizedScrollPart();
      firstGlobalTime = part.getFirstLocalTime();
      lastGlobalTime = part.getLastLocalTime();

      for (Iterator i = entries.iterator(); i.hasNext();)
      {
         entry = (FileEditorEntry) i.next();
         part = entry.getSynchronizedScrollPart();
         firstGlobalTime = Math.min(firstGlobalTime, part.getFirstLocalTime());
         lastGlobalTime = Math.max(lastGlobalTime, part.getLastLocalTime());
      }

      for (Iterator i = entries.iterator(); i.hasNext();)
      {
         entry = (FileEditorEntry) i.next();
         part = entry.getSynchronizedScrollPart();
         part.setScrollWindowRange(firstGlobalTime, lastGlobalTime);
         part.scrollTo(firstGlobalTime);
      }
   }

   private static void enableDecorator()
   {
      IDecoratorManager decoratorManager =
         PlatformUI.getWorkbench().getDecoratorManager();
      if (!decoratorManager.getEnabled(ProfilerPlugin.SYNCHRONIZED_EDITOR_DECORATOR_ID))
      {
         try
         {
            decoratorManager.setEnabled(ProfilerPlugin.SYNCHRONIZED_EDITOR_DECORATOR_ID, true);
         }
         catch (CoreException e)
         {
            ProfilerPlugin.log(e);
         }
      }
   }

   private void updateDecorations(FileEditorEntry entry)
   {
      SynchronizedScrollPartDecorator decorator =
         SynchronizedScrollPartDecorator.getManagedInstance();

      if (decorator != null)
      {
         ProfilerEditorProxy editor;

         decorator.refresh(entry.getFile());
         editor = entry.getEditor();
         editor.setTitleImage(
            isEditorSynchronized(editor) ? decorator.getDecoratedImage() : null);
      }
   }

   private static class FileEditorEntry
   {
      private final IFile file;

      private final ProfilerEditorProxy editor;

      FileEditorEntry(IFile file, ProfilerEditorProxy editor)
      {
         this.file = file;
         this.editor = editor;
      }

      public IFile getFile()
      {
         return file;
      }

      public ProfilerEditorProxy getEditor()
      {
         return editor;
      }

      public SynchronizedScrollPart getSynchronizedScrollPart()
      {
         return (SynchronizedScrollPart) editor.getEditor();
      }
   }

   // Listen to scroll events from the editors and inform the other editors
   // when one editor reports being scrolled.
   private class SynchronizedScrollPartHandler
      implements SynchronizedScrollPartListener
   {
      public void scrolledTo(SynchronizedScrollPart part, long time)
      {
         synchronized (entries)
         {
            for (Iterator i = entries.iterator(); i.hasNext();)
            {
               FileEditorEntry entry = (FileEditorEntry) i.next();
               SynchronizedScrollPart otherPart = entry.getSynchronizedScrollPart();
               if (!otherPart.equals(part))
               {
                  otherPart.scrollTo(time);
               }
            }
         }
      }
   }

   private class AddEditorPartListenerRunner implements Runnable
   {
      public void run()
      {
         PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().
            addPartListener(new EditorPartHandler());
      }
   }

   private class EditorPartHandler implements IPartListener
   {
      public void partOpened(IWorkbenchPart part) {}

      public void partActivated(IWorkbenchPart part) {}

      public void partDeactivated(IWorkbenchPart part) {}

      public void partBroughtToTop(IWorkbenchPart part) {}

      public void partClosed(IWorkbenchPart part)
      {
         if (part instanceof IEditorPart)
         {
            synchronized (entries)
            {
               FileEditorEntry entry = getEntry((IEditorPart) part);
               if (entry != null)
               {
                  removeEntry(entry);
               }
            }
         }
      }
   }

   private class EditorPropertyHandler implements IPropertyListener
   {
      public void propertyChanged(Object source, int propertyId)
      {
         if (propertyId == ProfilerEditorProxy.EDITOR_STATUS_READY)
         {
            ProfilerEditorProxy editor;

            // Success: Create and add the synchronized file/editor entry.
            editor = (ProfilerEditorProxy) source;
            editor.removePropertyListener(this);
            synchronized (entries)
            {
               if (!isEditorSynchronized(editor))
               {
                  FileEditorInput editorInput;
                  FileEditorEntry entry;

                  editorInput = (FileEditorInput) editor.getEditorInput();
                  entry = new FileEditorEntry(editorInput.getFile(), editor);
                  addEntry(entry);
               }
            }
         }
         else if (propertyId == ProfilerEditorProxy.EDITOR_STATUS_FAILED)
         {
            ProfilerEditorProxy editor;

            // Failure: Close the failed editor.
            editor = (ProfilerEditorProxy) source;
            editor.removePropertyListener(this);
            editor.getEditorSite().getPage().closeEditor(editor, true);
         }
      }
   }
}
