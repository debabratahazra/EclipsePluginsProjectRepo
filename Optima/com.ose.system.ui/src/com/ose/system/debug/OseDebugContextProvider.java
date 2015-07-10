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

package com.ose.system.debug;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.contexts.DebugContextEvent;
import org.eclipse.debug.ui.contexts.IDebugContextListener;
import org.eclipse.debug.ui.contexts.IDebugContextProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import com.ose.system.Block;
import com.ose.system.Heap;
import com.ose.system.OseObject;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.Target;

public class OseDebugContextProvider implements IDebugContextProvider
{
   private final ListenerList listeners;
   private final IWorkbenchPart workbenchPart;
   private final ISelectionProvider selectionProvider;
   private final ISelectionChangedListener selectionChangedHandler;
   private ISelection context;
   private OseObject object;

   public OseDebugContextProvider(IWorkbenchPart workbenchPart,
                                  ISelectionProvider selectionProvider)
   {
      if ((workbenchPart == null) || (selectionProvider == null))
      {
         throw new NullPointerException();
      }
      this.listeners = new ListenerList();
      this.workbenchPart = workbenchPart;
      this.selectionProvider = selectionProvider;
      this.selectionChangedHandler = new SelectionChangedHandler();
      selectionProvider.addSelectionChangedListener(selectionChangedHandler);
      DebugUITools.getDebugContextManager().getContextService(workbenchPart
         .getSite().getWorkbenchWindow()).addDebugContextProvider(this);
   }

   public void dispose()
   {
      DebugUITools.getDebugContextManager().getContextService(workbenchPart
         .getSite().getWorkbenchWindow()).removeDebugContextProvider(this);
      selectionProvider.removeSelectionChangedListener(selectionChangedHandler);
      listeners.clear();
      context = null;
      object = null;
      OseDebugTarget.terminateAll();
   }

   public IWorkbenchPart getPart()
   {
      return workbenchPart;
   }

   public void addDebugContextListener(IDebugContextListener listener)
   {
      listeners.add(listener);
   }

   public void removeDebugContextListener(IDebugContextListener listener)
   {
      listeners.remove(listener);
   }

   public synchronized ISelection getActiveContext()
   {
      return context;
   }

   public synchronized OseObject getActiveObject()
   {
      return object;
   }

   private synchronized void fire(ISelection selection, Object source)
   {
      Object[] listenerArray;

      context = selection;
      if (source instanceof Segment)
      {
         object = (Segment) source;
      }
      else if (source instanceof Block)
      {
         object = (Block) source;
      }
      else if (source instanceof Process)
      {
         object = (Process) source;
      }
      else
      {
         object = null;
      }

      listenerArray = listeners.getListeners();
      for (int i = 0; i < listenerArray.length; i++)
      {
         final IDebugContextListener listener =
            (IDebugContextListener) listenerArray[i];
         final DebugContextEvent activatedEvent =
            new DebugContextEvent(this, context, DebugContextEvent.ACTIVATED);

         SafeRunner.run(new ISafeRunnable()
         {
            public void run() throws Exception
            {
               listener.debugContextChanged(activatedEvent);
            }

            public void handleException(Throwable exception) {}
         });
      }
   }

   private class SelectionChangedHandler implements ISelectionChangedListener
   {
      public void selectionChanged(SelectionChangedEvent event)
      {
         ISelection selection;
         Object obj;
         Target target;
         OseDebugTarget debugTarget;
         ISelection activeContext;

         selection = event.getSelection();
         obj = ((IStructuredSelection) selection).getFirstElement();

         if (obj instanceof Target)
         {
            target = (Target) obj;
         }
         else if (obj instanceof Segment)
         {
            target = ((Segment) obj).getTarget();
         }
         else if (obj instanceof Pool)
         {
            target = ((Pool) obj).getTarget();
         }
         else if (obj instanceof Heap)
         {
            target = ((Heap) obj).getTarget();
         }
         else if (obj instanceof Block)
         {
            target = ((Block) obj).getTarget();
         }
         else if (obj instanceof Process)
         {
            target = ((Process) obj).getTarget();
         }
         else
         {
            target = null;
         }

         debugTarget = ((target != null) ?
            OseDebugTarget.createDebugTarget(OseDebugContextProvider.this, target) :
            null);
         activeContext = ((debugTarget != null) ?
            new StructuredSelection(debugTarget) :
            StructuredSelection.EMPTY);
         fire(activeContext, obj);
      }
   }
}
