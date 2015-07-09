package com.odcgroup.mdf.editor.ui.dialogs.annotations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;

public class AnnotationPropertyComposite {

	protected Composite parent;
	private WidgetFactory factory;
	protected Table table;
	protected TableViewer tableViewer;
	protected String[] columnLabels;
	protected boolean cData = false;
    private Button addButton = null;
    private Button editButton = null;
    private Button removeButton = null;
    private boolean grouped = false;
    

	/**
	 * @param parent
	 * @param factory
	 */
	public AnnotationPropertyComposite(Composite parent, WidgetFactory factory) {
		this(parent, factory, false);

	}
	
	public AnnotationPropertyComposite(Composite parent, WidgetFactory factory, boolean cData) {
		this.parent =  parent;
		this.factory = factory;
		this.cData = cData;
		this.grouped = cData;
		configureLabels(cData);
		createPropertiesTable(parent);
	}
	
	private void configureLabels(boolean cData) {
		 List<String> columns = new ArrayList<String>(); 
		 columns.add(MdfPlugin.getResourceString("annotationsPro.tablecolumn.name"));
		 columns.add(MdfPlugin.getResourceString("annotationsPro.tablecolumn.value"));
		 if (cData) {
			 columns.add(MdfPlugin.getResourceString("annotationsPro.tablecolumn.cdata"));
		 }
		 columnLabels = columns.toArray(new String[0]);
	}
	
	/**
	 * @param element
	 */
	public void setAnnotation(MdfAnnotation element) {
		tableViewer.setInput(element);
		tableViewer.refresh();
	}

	/**
	 * @param composite
	 */
	private Composite createPropertiesTable(Composite body) {

		Composite group = null;
		if (grouped) {
			group = factory.createGroup(body, " Annotation Properties ");
		} else {
			group = factory.createComposite(body);
		}
		
		group.setBackground(body.getBackground());
		int cols = 2;
		GridLayout gridLayout = new GridLayout(cols, false);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 200;
		gridData.grabExcessHorizontalSpace = true;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);

		TableLayoutComposite tableComposite = new TableLayoutComposite(group,
				SWT.FILL);
		tableComposite.setBackground(group.getBackground());
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 200;
		gridData.grabExcessHorizontalSpace = true;
		tableComposite.setLayoutData(gridData);

		// create table control
		table = new Table(tableComposite, SWT.BORDER | SWT.V_SCROLL
				| SWT.SINGLE | SWT.FULL_SELECTION | SWT.LINE_SOLID);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gridData.heightHint = 120;
		gridData.widthHint = 200;
		table.setLayoutData(gd);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setText(columnLabels[0]);
		tableComposite.addColumnData(new ColumnWeightData(20, 40, false));
		column.setResizable(true);
		column.setMoveable(false);

		column = new TableColumn(table, SWT.LEFT);
		column.setText(columnLabels[1]);
		tableComposite.addColumnData(new ColumnWeightData(50, 160, false));
		column.setResizable(true);
		column.setMoveable(false);
		
		if (cData) {
			column = new TableColumn(table, SWT.LEFT);
			column.setText(columnLabels[2]);
			tableComposite.addColumnData(new ColumnWeightData(10, 20, false));
			column.setResizable(true);
			column.setMoveable(false);
		}

		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				Double dou = new Double(
						event.gc.getFontMetrics().getHeight() * 1.12);
				event.height = dou.intValue();
			}
		});

		createEditButtons(group);
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MdfAnnotationProperty aProperty = (MdfAnnotationProperty) ((TableItem) e.item).getData();
				if (aProperty != null) {
					editButton.setEnabled(true);
					removeButton.setEnabled(true);
				}
			}
		});
		
		// create table viewer
		createTableViewer(table);
		return group;
	}

	/**
	 * @param body
	 */
	private void createEditButtons(Composite body) {
		Composite buttons = factory.createComposite(body);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		buttons.setLayout(new GridLayout(1, true));

		addButton = factory.createButton(buttons, MdfPlugin
				.getResourceString("annotations.addButton.text"), SWT.PUSH);
		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		editButton = factory.createButton(buttons, MdfPlugin
				.getResourceString("annotations.editButton.text"), SWT.PUSH);
		editButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		editButton.setEnabled(false);

		removeButton = factory.createButton(buttons, MdfPlugin
				.getResourceString("annotations.removeButton.text"), SWT.PUSH);
		removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeButton.setEnabled(false);
	}
	

	/**
	 * @param property
	 * @return the display of the property
	 */
	protected String getDisplayName(MdfAnnotationProperty property) {
		return property.getName();
	}
	
	/**
	 * @param table
	 */
	private void createTableViewer(Table table) {
		
		tableViewer = new TableViewer(table);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				// enable the delete button
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection != null) {
				}
			}
		});

		tableViewer.setColumnProperties(columnLabels);

		// contentProvider
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((MdfAnnotation) inputElement).getProperties().toArray(new MdfAnnotationProperty[] {});
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer1, Object obj, Object obj1) {
			}

		});

		// labelprovider
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				MdfAnnotationProperty property = (MdfAnnotationProperty) element;
				if (columnIndex ==0) {
					return getDisplayName(property); 
				} else if (columnIndex == 1) {
					return property.getValue();
				} else {
					return property.isCDATA()+"";
				}
			}
			
			public void addListener(
					ILabelProviderListener ilabelproviderlistener) {
				tableViewer.refresh();
			}
			
			public void dispose() {
			}
			
			public boolean isLabelProperty(Object element, String property) {
				return true;
			}
			
			public void removeListener(
					ILabelProviderListener ilabelproviderlistener) {
			}

		});


	}

	public Button getAddButton() {
		return addButton;
	}

	public Button getEditButton() {
		return editButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void addMouseListener(
			CustomAnnotationsExtensionsPage customAnnotationsExtensionsPage) {
		table.addMouseListener(customAnnotationsExtensionsPage);
	}
	
	

}
