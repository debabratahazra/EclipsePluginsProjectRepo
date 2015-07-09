/**
 * 
 */
package com.odcgroup.workbench.ui.preferences.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Table field editor
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableFieldEditor extends FieldEditor {

	/** */
	private ITableFieldDescriptor tableDescriptor;
	/** */
	private ITableFieldDataProvider dataProvider;
	/** */
	private TableFieldViewer viewer;
	/** */
	private Object[] data;
	
	
	/**
	 * @param parent
	 * @return TableViewer
	 */
	public TableViewer getViewer(Composite parent) {
		
		if (viewer == null) {
			
			getLabelControl(parent);
			
			viewer = new TableFieldViewer(parent, tableDescriptor) {

				protected ICellModifier getTableCellModifier() {
					return new DefaultTableCellModifier();
				}

				protected IContentProvider getTableContentProvider() {
					return new DefaultTableContentProvider();
				}

				protected IBaseLabelProvider getTableLabelProvider() {
					return new DefaultTableLabelProvider();
				}

			};
			
		}
		return viewer;
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	protected void adjustForNumColumns(int numColumns) {
		((GridData) this.viewer.getTable().getLayoutData()).horizontalSpan = 1;
	}
	
	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite,int)
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = tableDescriptor.getHeightHint();
		getViewer(parent).getTable().setLayoutData(gd);
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	protected void doLoad() {
		this.data = dataProvider.load(getPreferenceName());
		viewer.setInput(this.data);
		viewer.setSorter(viewer.getTableFieldSorter());
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	protected void doLoadDefault() {
		this.data = dataProvider.loadDefault(getPreferenceName());
		viewer.setInput(this.data);
		viewer.setSorter(viewer.getTableFieldSorter());
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	protected void doStore() {
		dataProvider.store(getPreferenceName(), this.data);
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	public int getNumberOfControls() {
		return 1;
	}
	
	/**
	 * Adds a new item to the table
	 * @param item
	 */
	@SuppressWarnings("unchecked")
	public void addItem(Object item) {
		ArrayList list = new ArrayList(Arrays.asList(data));
        list.add(item);
        data = new Object[list.size()];
        list.toArray(data);
	}
	
	/**
	 * Adds a collection of new items to the table
	 * @param items
	 */
	@SuppressWarnings("unchecked")
	public void addItems(List items) {
		ArrayList list = new ArrayList(Arrays.asList(data));
        list.addAll(items);
        data = new Object[list.size()];
        list.toArray(data);
        viewer.refresh();	
	}
	
	/**
	 * Removes all selected items from the table 
	 */
	@SuppressWarnings("unchecked")
	public Object[] removeSelectedItems() {
		ArrayList list = new ArrayList();
        TableItem [] sel = viewer.getTable().getSelection();
        for (int i = 0; i < sel.length; i++) {
            list.add(sel[i].getData());
        }
        List items = new ArrayList(Arrays.asList(data));
        items.removeAll(list);
        data = new Object[items.size()];
        items.toArray(data);
        return list.toArray();
    }
	
	/**
	 * @param actions
	 */
	public void installContextMenu(IAction[] actions) {
		MenuManager popupMenu = new MenuManager();
		for (IAction action : actions) {
			popupMenu.add(action);
		}
		Table table = viewer.getTable();
		Menu menu = popupMenu.createContextMenu(table);
		table.setMenu(menu);
	}
	
	/**
	 * @return the current selection
	 */
	public ISelection getSelection() {
		return viewer.getSelection();
	}

	/**
	 * 
	 */
	public void refresh() {
		if (viewer != null) {
			viewer.refresh();
		}
		
	}
	
	/**
	 * Creates a new field editor.
	 * 
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param tableDescriptor
	 *            the table descriptor
	 * @param parent
	 *            the parent of the field editor's control
	 */
	public TableFieldEditor(String name, String labelText, TableFieldDescriptor tableDescriptor, Composite parent) {
		init(name, labelText);
		this.tableDescriptor = tableDescriptor;
		this.dataProvider = tableDescriptor.getDataProvider();
		createControl(parent);
	}

	/**
	 * Default Table Cell Modifier
	 */
	public class DefaultTableCellModifier implements ICellModifier {

		/**
		 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,java.lang.String)
		 */
		public boolean canModify(Object element, String property) {
			return tableDescriptor.getColumn(property).isEditable();
		}

		/**
		 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,java.lang.String)
		 */
		public Object getValue(Object element, String property) {
			if (element instanceof TableItem) {
				element = ((TableItem) element).getData();
			}
			Object value = dataProvider.getValue(element, property);
			return value;
		}

		/**
		 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,java.lang.String, java.lang.Object)
		 */
		public void modify(Object element, String property, Object value) {
			if (value == null) {
				// failed validation
				return;
			}
			if (element instanceof TableItem) {
				element = ((TableItem) element).getData();
			}
			dataProvider.setValue(element, property, value);
			viewer.refresh();
		}
	}

	/**
	 * Default Table Label Provider
	 */
	public class DefaultTableLabelProvider implements ITableLabelProvider/*, IColorProvider*/ {

		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof TableItem) {
				element = ((TableItem) element).getData();
			}
			Image image = (Image)dataProvider.getImage(element, tableDescriptor.getColumns()[columnIndex].getName());
			return image;
		}

		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof TableItem) {
				element = ((TableItem) element).getData();
			}
			Object value = dataProvider.getValue(element, tableDescriptor.getColumns()[columnIndex].getName());
			return value != null ? value.toString() : ""; //$NON-NLS-1$
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 */
		public void addListener(ILabelProviderListener listener) {
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		public void dispose() {
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,java.lang.String)
		 */
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 */
		public void removeListener(ILabelProviderListener listener) {
		}

//		/**
//		 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
//		 */
//		public Color getBackground(Object element) {
//			return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
//		}
//
//		/**
//		 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
//		 */
//		public Color getForeground(Object element) {
//			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
//		}
	}

	/**
	 * Default Table Content Provider
	 */
	public class DefaultTableContentProvider implements IStructuredContentProvider {
		
		/**
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			return data;
		}

		/**
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/**
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

}
