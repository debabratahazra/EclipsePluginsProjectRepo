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

package com.ose.system;

import java.util.EventListener;

// TODO: Remove this class and replace its usage with
// org.eclipse.core.runtime.ListenerList when moving to Eclipse 3.2.
// The org.eclipse.core.runtime.ListenerList implementation is standard,
// thread-safe, and more efficient.

/**
 * This class is used to maintain a list of event listeners. It is a fairly
 * lightweight object, occupying minimal space when no event listeners are
 * registered.
 * <p>
 * Note that the <code>add</code> method checks for and eliminates duplicates
 * based on identity (not equality).  Likewise, the <code>remove</code> method
 * compares based on identity.
 * </p>
 * <p>
 * Use the <code>getListeners</code> method when notifying listeners. Note that
 * no garbage is created if no listeners are registered. The recommended code
 * sequence for notifying all registered listeners of say,
 * <code>FooListener.eventHappened</code>, is:
 * <pre>
 * EventListener[] listeners = myListenerList.getListeners();
 * for (int i = 0; i < listeners.length; i++)
 * {
 *    ((FooListener) listeners[i]).eventHappened(event);
 * }
 * </pre>
 * </p>
 */
class EventListenerList
{
   /**
    * The empty array singleton instance, returned by getListeners() when size ==
    * 0.
    */
   private static final EventListener[] EMPTY_ARRAY = new EventListener[0];

   /**
    * The list of listeners. Initially <code>null</code> but initialized to an
    * array of size capacity the first time a listener is added. Maintains
    * invariant: listeners != null IFF size != 0.
    */
   private EventListener[] listeners = null;

   /**
    * The initial capacity of the list. Always >= 1.
    */
   private int capacity;

   /**
    * The current number of listeners. Maintains invariant: 0 <= size <=
    * listeners.length.
    */
   private int size;

   /**
    * Creates a listener list with an initial capacity of 1.
    */
   public EventListenerList()
   {
      this(1);
   }

   /**
    * Creates a listener list with the given initial capacity.
    *
    * @param capacity the number of listeners which this list can initially
    *                 accept without growing its internal representation; must be
    *                 at least 1.
    */
   public EventListenerList(int capacity)
   {
      if (capacity < 1)
      {
         throw new IllegalArgumentException();
      }
      this.capacity = capacity;
   }

   /**
    * Adds the given listener to this list. Has no effect if an identical
    * listener is already registered.
    *
    * @param listener the listener.
    */
   public void add(EventListener listener)
   {
      if (listener == null)
      {
         throw new NullPointerException();
      }
      if (size == 0)
      {
         listeners = new EventListener[capacity];
      }
      else
      {
         // Check for duplicates using identity.
         for (int i = 0; i < size; i++)
         {
            if (listeners[i] == listener)
            {
               return;
            }
         }

         // Grow array if necessary.
         if (listeners.length == size)
         {
            System.arraycopy(listeners, 0,
                             listeners = new EventListener[size * 2 + 1], 0,
                             size);
         }
      }

      listeners[size] = listener;
      size++;
   }

   /**
    * Removes the given listener from this list. Has no effect if an identical
    * listener was not already registered.
    *
    * @param listener the listener.
    */
   public void remove(EventListener listener)
   {
      if (listener == null)
      {
         throw new NullPointerException();
      }
      for (int i = 0; i < size; i++)
      {
         if (listeners[i] == listener)
         {
            if (size == 1)
            {
               listeners = null;
               size = 0;
            }
            else
            {
               System.arraycopy(listeners, i + 1, listeners, i, --size - i);
               listeners[size] = null;
            }
            return;
         }
      }
   }

   /**
    * Removes all listeners from this list.
    */
   public void clear()
   {
      listeners = null;
      size = 0;
   }

   /**
    * Returns an array containing all the registered listeners, in the order in
    * which they were added.
    * <p>
    * The resulting array is unaffected by subsequent adds or removes. If there
    * are no listeners registered, the result is an empty array singleton
    * instance (no garbage is created). Use this method when notifying
    * listeners, so that any modifications to the listener list during the
    * notification will have no effect on the notification itself.
    * </p>
    *
    * @return the list of registered listeners.
    */
   public EventListener[] getListeners()
   {
      if (size == 0)
      {
         return EMPTY_ARRAY;
      }
      else
      {
         EventListener[] result = new EventListener[size];
         System.arraycopy(listeners, 0, result, 0, size);
         return result;
      }
   }

   /**
    * Returns whether this listener list is empty.
    *
    * @return <code>true</code> if there are no registered listeners, and
    *         <code>false</code> otherwise.
    */
   public boolean isEmpty()
   {
      return (size == 0);
   }

   /**
    * Returns the number of registered listeners.
    *
    * @return the number of registered listeners.
    */
   public int size()
   {
      return size;
   }

    /**
     * Returns a string representation of the EventListenerList.
     *
     * @return a string representation of the EventListenerList.
     */
   public String toString()
   {
      EventListener[] list = listeners;
      String string = "EventListenerList: " + size + " listeners: ";
      for (int i = 0 ; i < size; i++)
      {
         string += " listener " + list[i];
      }
      return string;
   }
}
