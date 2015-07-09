package com.odcgroup.page.ui.properties.table.controls;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


/**
 *
 * @author pkk
 *
 */
public class PropertyTable {
	
	/** composite for the table control	 */
	private Composite body = null;
	/**	 */
	protected Table table = null;
	/**	 */
	protected Button deleteButton = null;
	/**	 */
	protected Button addButton = null;
	/**	 */
	protected Button modifyButton = null;
	/**	 */
	protected Button moveUpBtn = null;
	/**	 */
	protected Button moveDownBtn = null;
	/**	 */
	protected TableViewer tableViewer = null;
	/**	 */
	private List<PropertyColumn> columns = null;
	/**	 */
	private boolean sortable = true;
	/**  */
	private Composite buttonComposite = null;

	/**
	 * @param parent
	 * @param columns 
	 * @param sortable 
	 */
	public PropertyTable(Composite parent, List<PropertyColumn> columns, boolean sortable) {
		this.columns = columns;
		this.sortable = sortable;
		createTableControl(parent);
	}	
	
	
	/**
	 * @param parent
	 */
	protected void createTableControl(Composite parent) {
		body = new Composite(parent, SWT.FILL);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        body.setLayout(gridLayout);	  
        body.setBackground(parent.getBackground());
		GridData gd = new GridData(GridData.FILL_BOTH);		
		body.setLayoutData(gd);
        
        Composite tableComposite = new Composite(body, SWT.NONE);
        gridLayout = new GridLayout(1, false);
        tableComposite.setLayout(gridLayout);
        gd = new GridData(GridData.FILL_BOTH);
        tableComposite.setLayoutData(gd);
        tableComposite.setBackground(parent.getBackground());
        
        buttonComposite  = new Composite(body, SWT.NONE);        
        gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        buttonComposite.setLayout(gridLayout);
        buttonComposite.setBackground(body.getBackground());
        buttonComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        
        createButtons(buttonComposite, "");
        createTable(tableComposite);
	}
	
	/**
	 * @param tableComposite
	 */
	private void createTable(final Composite tableComposite) {

		tableViewer = new TableViewer(tableComposite, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		table = tableViewer.getTable();
		// Layout the viewer
		GridData gridData = new GridData(GridData.FILL_BOTH);		
		table.setLayoutData(gridData);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				Double dou = new Double(event.gc.getFontMetrics().getHeight() * 1.12);
				event.height = dou.intValue();
			}
		});
		
		TableColumn tColumn = null;
		for (PropertyColumn column : columns) {
			tColumn = new TableColumn(table, SWT.LEFT);
			tColumn.setText(column.getName());
			tColumn.setResizable(column.isResizeable());
		}	
		
		tableComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle area = tableComposite.getClientArea();
				Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				ScrollBar vBar = table.getVerticalBar();
				int width = area.width - table.computeTrim(0,0,0,0).width - vBar.getSize().x;
				if (size.y > area.height + table.getHeaderHeight()) {
					Point vBarSize = vBar.getSize();
					width -= vBarSize.x;
				}
				Point oldSize = table.getSize();
				if (oldSize.x > area.width) {
					TableColumn[] columns = table.getColumns();
					for (TableColumn column : columns) {
						column.setWidth(width/columns.length);
					}
				 	table.setSize(area.width, area.height);
				 } else {
				 	table.setSize(area.width, area.height);
				 	TableColumn[] columns = table.getColumns();
					for (TableColumn column : columns) {
						column.setWidth(width/columns.length);
					}
				}
			}
		});
	}
	
	
	
	/**
	 * @param composite
	 * @param label 
	 */
	private void createButtons(Composite composite, String label) {
		GridData data = new GridData();
		data.heightHint = 25;
		data.widthHint = 60;

		// add button
		addButton = createButton(composite, "Add", "Add" + label, data);

		// modify button
		modifyButton = createButton(composite, "Edit", "Edit selected " + label, data);
		modifyButton.setEnabled(false);

		// delete button
		deleteButton = createButton(composite, "Remove", "Remove selected " + label + "(s)", data);
		deleteButton.setEnabled(false);

		if (isSortable()) {
			// moveup button
			moveUpBtn = createButton(composite, "Up", "Move up selected " + label, data);
			moveUpBtn.setEnabled(false);
	
			// movedown button
			moveDownBtn = createButton(composite, "Down", "Move down selected " + label, data);
			moveDownBtn.setEnabled(false);
		}
	}
	
	/**
	 * @param composite
	 * @param label
	 * @param tooltip
	 * @param data
	 * @return Button
	 */
	private Button createButton(Composite composite, String label, String tooltip, GridData data) {
		Button button = new Button(composite, SWT.NONE);
		button.setText("   "+label+"   ");
		button.setToolTipText(tooltip);
		button.setLayoutData(data);
		return button;
	}
	
	/**
	 * @return add Button
	 */
	public Button getAddButton() {
		return this.addButton;
	}
	
	/**
	 * @return delete button
	 */
	public Button getDeleteButton() {
		return this.deleteButton;
	}
	
	/**
	 * @return edit button
	 */
	public Button getModifyButton() {
		return this.modifyButton;
	}
	
	/**
	 * @return move down button
	 */
	public Button getMoveDownButton() {
		return this.moveDownBtn;
	}
	
	/**
	 * @return move up button
	 */
	public Button getMoveUpButton() {
		return this.moveUpBtn;
	}
	
	/**
	 * @return TableViewer
	 */
	public TableViewer getTableViewer() {
		return this.tableViewer;
	}
		
	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		Control[] controls = buttonComposite.getChildren();
		for (Control control : controls) {
			control.setEnabled(enabled);
		}
		//getTableViewer().getTable().setEnabled(enabled);
	}
	
	/**
	 * 
	 */
	public void refresh() {
		if (tableViewer != null) {
			tableViewer.refresh();
		}
	}

}
