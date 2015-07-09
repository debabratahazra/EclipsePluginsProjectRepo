package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableDecorator {

    private static TableViewer tableViewer;
    private int NUM_COLUMNS;
    private int TEXT_MARGIN = 3;
    private String[] titles;
    private Composite composite;
    private int[] bounds;

    public TableDecorator(Composite composite, String[] data_titles, int dataNumColumns, int[] data_bounds) {
        this.composite = composite;
        this.NUM_COLUMNS = dataNumColumns;
        this.titles = data_titles;
        this.bounds = data_bounds;
    }

    public TableViewer createNonEditableTableViewer() {
        Table table = createTable();
        tableViewer = createTableViewer(table);
        return tableViewer;
    }

    private Table createTable() {
        GridData data = new GridData();
        data.horizontalSpan = 15;
        data.heightHint = 190;
        Table table = new Table(composite, SWT.WRAP | SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.SCROLL_LINE);
        table.setEnabled(true);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(data);
        table.addListener(SWT.MeasureItem, new Listener() {

            public void handleEvent(Event event) {
                TableItem item = (TableItem) event.item;
                String text = item.getText(event.index);
                Point size = event.gc.textExtent(text);
                event.width = size.x + 2 * TEXT_MARGIN;
                event.height = Math.max(event.height, size.y + TEXT_MARGIN);
            }
        });
        table.addListener(SWT.EraseItem, new Listener() {

            public void handleEvent(Event event) {
                event.detail &= ~SWT.FOREGROUND;
            }
        });
        table.addListener(SWT.PaintItem, new Listener() {

            public void handleEvent(Event event) {
                TableItem item = (TableItem) event.item;
                String text = item.getText(event.index);
                int yOffset = 0;
                if (event.index == 1) {
                    Point size = event.gc.textExtent(text);
                    yOffset = Math.max(0, (event.height - size.y) / 2);
                }
                event.gc.drawText(text, event.x + TEXT_MARGIN, event.y + yOffset, true);
            }
        });
        return table;
    }

    private TableViewer createTableViewer(Table table) {
        TableViewer tableViewer = new TableViewer(table);
        for (int colCnt = 0; colCnt < NUM_COLUMNS; colCnt++) {
            TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.WRAP | SWT.MULTI | SWT.BORDER);
            column.getColumn().setText(titles[colCnt]);
            column.getColumn().setWidth(bounds[colCnt]);
        }
        return tableViewer;
    }
}
