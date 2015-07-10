/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Table;

public class TableSelectAllAction extends Action
{
   private final Table table;

   private final TableViewer tableViewer;

   private final EnablementHandler enablementHandler;

   public TableSelectAllAction(Table table)
   {
      super("Select All");

      if (table == null)
      {
         throw new IllegalArgumentException();
      }

      this.table = table;
      tableViewer = null;
      enablementHandler = new EnablementHandler();

      setToolTipText("Select All");
      setEnabled(false);
      table.addFocusListener(enablementHandler);
   }

   public TableSelectAllAction(TableViewer tableViewer)
   {
      super("Select All");

      if (tableViewer == null)
      {
         throw new IllegalArgumentException();
      }

      this.table = tableViewer.getTable();
      this.tableViewer = tableViewer;
      enablementHandler = new EnablementHandler();

      setToolTipText("Select All");
      setEnabled(false);
      table.addFocusListener(enablementHandler);
   }

   public void dispose()
   {
      if (!table.isDisposed())
      {
         table.removeFocusListener(enablementHandler);
      }
   }

   public void run()
   {
      if (!table.isDisposed())
      {
         selectAllTable();
      }
   }

   protected void selectAllTable()
   {
      table.selectAll();
      if (tableViewer != null)
      {
         // Force table viewer selection changed event.
         tableViewer.setSelection(tableViewer.getSelection());
      }
   }

   private class EnablementHandler implements FocusListener
   {
      public void focusGained(FocusEvent event)
      {
         setEnabled(true);
      }

      public void focusLost(FocusEvent event)
      {
         setEnabled(false);
      }
   }
}
