package com.odcgroup.workbench.editors.properties.internal;

import java.util.List;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.workbench.editors.properties.item.PropertyTableColumn;

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
	private List<PropertyTableColumn> columns = null;
	/**	 */
	private boolean sortable = true;
	/**  */
	private Composite buttonComposite = null;
	/** */
	@SuppressWarnings("unused")
	private boolean propertyView = true;

	/**
	 * @param parent
	 * @param columns 
	 * @param sortable 
	 */
	public PropertyTable(Composite parent, List<PropertyTableColumn> columns, boolean sortable) {
		this(parent, columns, sortable, true);
	}
	
	/**
	 * @param parent
	 * @param columns
	 * @param sortable
	 * @param propertyView
	 */
	public PropertyTable(Composite parent, List<PropertyTableColumn> columns, boolean sortable, boolean propertyView) {
		this.columns = columns;
		this.sortable = sortable;
		this.propertyView = propertyView;
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
        
        TableLayoutComposite tableComposite = new TableLayoutComposite(body, SWT.FILL);
        tableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        buttonComposite  = new Composite(body, SWT.FILL);        
        gridLayout = new GridLayout(1, false);
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        buttonComposite.setLayout(gridLayout);
        buttonComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        buttonComposite.setBackground(body.getBackground());
        createButtons(buttonComposite);
        createTable(tableComposite);
	}
	
	/**
	 * @param tableComposite
	 */
	private void createTable(TableLayoutComposite tableComposite) {

		table = new Table(tableComposite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION
				| SWT.LINE_SOLID);
		GridData gd = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gd);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				Double dou = new Double(event.gc.getFontMetrics().getHeight() * 1.4);
				event.height = dou.intValue();				
				int clientWidth = table.getClientArea().width;
				event.width = clientWidth * 2;
			}
		});
		
		TableColumn tColumn = null;
		for (PropertyTableColumn column : columns) {
			tColumn = new TableColumn(table, SWT.LEFT);
			tColumn.setText(firstLetterUpper(column.getName()));
			tColumn.setResizable(false);
			tColumn.setMoveable(false);
			tableComposite.addColumnData(new ColumnWeightData(column.getWeight(), 0, false));
		}	
		tableViewer = new TableViewer(table);		
	}
	
	/**
	 * @param value
	 * @return
	 */
	private String firstLetterUpper(String value) {
		if (value != null && value.trim().length() > 0) {
			return value.substring(0,1).toUpperCase()+value.substring(1);
		}
		return "";
	}	
	
	/**
	 * @param composite
	 * @param label 
	 */
	private void createButtons(Composite composite) {
		GridData data = new GridData();

		// add button
		addButton = createButton(composite, "Add", "Add", data);
		// modify button
		modifyButton = createButton(composite, "Edit", "Edit selection", data);
		modifyButton.setEnabled(false);
		// delete button
		deleteButton = createButton(composite, "Remove", "Remove selection", data);
		deleteButton.setEnabled(false);
		if (isSortable()) {
			// moveup button
			moveUpBtn = createButton(composite, "Up", "Move up selection", data);
			moveUpBtn.setEnabled(false);	
			// movedown button
			moveDownBtn = createButton(composite, "Down", "Move down selection", data);
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
		getTableViewer().getTable().setEnabled(enabled);
	}

}
