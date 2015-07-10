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

package com.ose.system.ui.util;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

public class Grid extends Composite
{
   private final ListenerList listenerList;

   private GridItem selectedItem;

   public Grid(Composite parent, int style)
   {
      super(parent, style);
      listenerList = new ListenerList();
      setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
   }

   public void dispose()
   {
      listenerList.clear();
      super.dispose();
   }

   public void addSelectionListener(SelectionListener listener)
   {
      listenerList.add(listener);
   }

   public void removeSelectionListener(SelectionListener listener)
   {
      listenerList.remove(listener);
   }

   public void setEnabled(boolean enabled)
   {
      Control[] items;

      super.setEnabled(enabled);
      setRedraw(false);
      items = getChildren();
      for (int i = 0; i < items.length; i++)
      {
         GridItem item = (GridItem) items[i];
         item.redraw();
      }
      setRedraw(true);
   }

   public GridItem getSelectedItem()
   {
      return selectedItem;
   }

   public boolean setSelectedItem(GridItem item)
   {
      boolean wasSelected = false;
      Control[] items;

      if (item == null)
      {
         SWT.error(SWT.ERROR_NULL_ARGUMENT);
      }

      items = getChildren();
      for (int i = 0; i < items.length; i++)
      {
         GridItem anItem = (GridItem) items[i];
         if (anItem.equals(item))
         {
            anItem.setSelected(true);
            selectedItem = anItem;
            wasSelected = true;
         }
         else
         {
            anItem.setSelected(false);
         }
      }

      return wasSelected;
   }

   public GridItem getItemMatchingData(Object data)
   {
      GridItem matchingItem = null;
      Control[] items;

      if (data == null)
      {
         SWT.error(SWT.ERROR_NULL_ARGUMENT);
      }

      items = getChildren();
      for (int i = 0; i < items.length; i++)
      {
         GridItem anItem = (GridItem) items[i];
         if (data.equals(anItem.getData()))
         {
            matchingItem = anItem;
            break;
         }
      }

      return matchingItem;
   }

   void fireItemSelected(GridItem item)
   {
      if (setSelectedItem(item))
      {
         Event event;
         SelectionEvent selectionEvent;
         Object[] listeners;

         event = new Event();
         event.display = getDisplay();
         event.widget = this;
         event.type = SWT.Selection;
         event.detail = SWT.SELECTED;
         event.item = item;
         selectionEvent = new SelectionEvent(event);

         listeners = listenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            SelectionListener listener = (SelectionListener) listeners[i];
            listener.widgetSelected(selectionEvent);
         }
      }
   }
}
