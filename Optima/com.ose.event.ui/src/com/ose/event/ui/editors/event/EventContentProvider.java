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

package com.ose.event.ui.editors.event;

import java.util.LinkedList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import com.ose.system.TargetEvent;

class EventContentProvider implements IStructuredContentProvider
{
   private static final TargetEvent[] EVENT_ARRAY_TYPE = new TargetEvent[0];

   private final int maxEvents;

   private final LinkedList events = new LinkedList();

   private TableViewer viewer;

   private int numTotalElements;

   EventContentProvider(int maxEvents)
   {
      if (maxEvents <= 0)
      {
         throw new IllegalArgumentException();
      }
      this.maxEvents = maxEvents;
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      this.viewer = (TableViewer) viewer;

      if (newInput instanceof TargetEvent[])
      {
         TargetEvent[] newEvents = (TargetEvent[]) newInput;
         int numOverflownEvents = 0;

         numTotalElements = newEvents.length;
         if (newEvents.length > maxEvents)
         {
            numOverflownEvents = newEvents.length - maxEvents;
         }
         events.clear();
         for (int i = numOverflownEvents; i < newEvents.length; i++)
         {
            events.addLast(newEvents[i]);
         }
      }
   }

   public void dispose()
   {
      events.clear();
   }

   public Object[] getElements(Object parent)
   {
      return events.toArray(EVENT_ARRAY_TYPE);
   }

   public int getNumElements()
   {
      return events.size();
   }

   public int getNumTotalElements()
   {
      return numTotalElements;
   }

   public void addEvent(TargetEvent event)
   {
      if (event == null)
      {
         throw new IllegalArgumentException();
      }

      numTotalElements++;
      if (events.size() + 1 > maxEvents)
      {
         events.removeFirst();
      }

      events.addLast(event);
   }

   public void addEvents(TargetEvent[] addEvents)
   {
      Object[] removeEvents = null;
      int numOverflownEvents;

      if ((addEvents == null) || (addEvents.length > maxEvents))
      {
         throw new IllegalArgumentException();
      }

      numTotalElements += addEvents.length;
      numOverflownEvents = events.size() + addEvents.length - maxEvents;
      if (numOverflownEvents > 0)
      {
         removeEvents = events.subList(0, numOverflownEvents).toArray(EVENT_ARRAY_TYPE);
         for (int i = 0; i < numOverflownEvents; i++)
         {
            events.removeFirst();
         }
      }

      for (int i = 0; i < addEvents.length; i++)
      {
         events.addLast(addEvents[i]);
      }

      if (!viewer.getTable().isDisposed())
      {
         viewer.getTable().setRedraw(false);
         if (removeEvents != null)
         {
            viewer.remove(removeEvents);
         }
         viewer.add(addEvents);
         if (addEvents.length > 0)
         {
            viewer.reveal(addEvents[addEvents.length - 1]);
         }
         viewer.getTable().setRedraw(true);
      }
   }
}
