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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class TableCopyAction extends Action
{
   private static final String ROW_SEP = System.getProperty("line.separator");

   private static final String COLUMN_SEP = "\t";

   private final Table table;

   private final TableViewer tableViewer;

   private final EnablementHandler enablementHandler;

   public TableCopyAction(Table table)
   {
      super("Copy");

      if (table == null)
      {
         throw new IllegalArgumentException();
      }

      this.table = table;
      tableViewer = null;
      enablementHandler = new EnablementHandler();

      setToolTipText("Copy");
      setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
      setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
      setEnabled(false);

      table.addSelectionListener(enablementHandler);
      table.addFocusListener(enablementHandler);
   }

   public TableCopyAction(TableViewer tableViewer)
   {
      super("Copy");

      if (tableViewer == null)
      {
         throw new IllegalArgumentException();
      }

      this.table = tableViewer.getTable();
      this.tableViewer = tableViewer;
      enablementHandler = new EnablementHandler();

      setToolTipText("Copy");
      setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
      setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
            .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
      setEnabled(false);

      tableViewer.addSelectionChangedListener(enablementHandler);
      table.addFocusListener(enablementHandler);
   }

   public void dispose()
   {
      if (!table.isDisposed())
      {
         if (tableViewer == null)
         {
            table.removeSelectionListener(enablementHandler);
         }
         else
         {
            tableViewer.removeSelectionChangedListener(enablementHandler);
         }
         table.removeFocusListener(enablementHandler);
      }
   }

   public void run()
   {
      if (!table.isDisposed())
      {
         copyTable();
      }
   }

   protected void copyTable()
   {
      int numColumns;
      TableColumn[] columns;
      boolean firstDummyColumn;
      TableItem[] items;
      StringBuffer sb;
      Clipboard clipboard;

      if (!hasSelection())
      {
         setEnabled(false);
         return;
      }

      numColumns = table.getColumnCount();
      columns = table.getColumns();
      firstDummyColumn = (numColumns > 1) && (columns[0].getWidth() == 0);
      items = table.getSelection();
      sb = new StringBuffer((firstDummyColumn ? numColumns - 1 : numColumns)
            * (items.length + 1) * 10);

      for (int i = (firstDummyColumn ? 1 : 0); i < numColumns; i++)
      {
         TableColumn column = columns[i];
         sb.append(column.getText());
         sb.append((i < (numColumns - 1)) ? COLUMN_SEP : ROW_SEP);
      }

      for (int i = 0; i < items.length; i++)
      {
         TableItem item = items[i];
         for (int j = (firstDummyColumn ? 1 : 0); j < numColumns; j++)
         {
            sb.append(item.getText(j));
            sb.append((j < (numColumns - 1)) ? COLUMN_SEP : ROW_SEP);
         }
      }

      clipboard = new Clipboard(table.getDisplay());
      clipboard.setContents(new Object[] {sb.toString()},
                            new Transfer[] {TextTransfer.getInstance()});
      clipboard.dispose();
   }

   protected boolean hasSelection()
   {
      return !table.isDisposed() && (table.getSelectionCount() > 0);
   }

   private class EnablementHandler implements
      SelectionListener, ISelectionChangedListener, FocusListener
   {
      public void widgetSelected(SelectionEvent event)
      {
         setEnabled(hasSelection());
      }

      public void widgetDefaultSelected(SelectionEvent event)
      {
         setEnabled(hasSelection());
      }

      public void selectionChanged(SelectionChangedEvent event)
      {
         setEnabled(hasSelection());
      }

      public void focusGained(FocusEvent event)
      {
         setEnabled(hasSelection());
      }

      public void focusLost(FocusEvent event)
      {
         setEnabled(false);
      }
   }
}
