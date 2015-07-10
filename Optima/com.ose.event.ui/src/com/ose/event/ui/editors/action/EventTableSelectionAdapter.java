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

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class EventTableSelectionAdapter extends SelectionAdapter
{
   private EventActionSorter comparator;

   private TableViewer viewer;

   private int column;

   public EventTableSelectionAdapter(EventActionSorter comparator,
         TableViewer viewer, int column)
   {
      this.viewer = viewer;
      this.comparator = comparator;
      this.column = column;
   }

   @Override
   public void widgetSelected(SelectionEvent event)
   {
      comparator.setSortColumn(column);
      viewer.refresh();
   }
}
