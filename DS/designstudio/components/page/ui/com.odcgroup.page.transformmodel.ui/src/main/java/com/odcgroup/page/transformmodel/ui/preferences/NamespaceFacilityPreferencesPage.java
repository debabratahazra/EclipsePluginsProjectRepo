package com.odcgroup.page.transformmodel.ui.preferences;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelFactory;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacility;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityConstants;
import com.odcgroup.page.transformmodel.namespaces.NamespaceFacilityUtils;
import com.odcgroup.page.transformmodel.util.TransformModelRegistry;
import com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider;
import com.odcgroup.workbench.ui.preferences.table.TableColumnDescriptor;
import com.odcgroup.workbench.ui.preferences.table.TableFieldDescriptor;
import com.odcgroup.workbench.ui.preferences.table.TableFieldEditor;

/**
 * Preferences page for the namespaces
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NamespaceFacilityPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPropertyPage {

	/** The prefix column string constant */
	private final static String PREFIX_COLUMN_NAME = "Prefix";

	/** The uri column string constant */
	private final static String URI_COLUMN_NAME = "Uri";

	/** */
	static final String PROPERTY_STORE_ID = "com.odcgroup.page.transformmodel.namespaces";

	/** Stores owning element of properties */
	private IAdaptable element;
	
	/**
	 * @param editor
	 */
	private void addUserDefinedNamespace(TableFieldEditor editor) {
		TransformModelFactory tmf = TransformModelFactory.eINSTANCE;
		Namespace ns = tmf.createNamespace();
		ns.setOrigin(Namespace.USER_DEFINED_NAMESPACES);
		Dialog dlg = new EditNamespaceDialog(getShell(), ns);
		if (Dialog.OK == dlg.open()) {
			editor.addItem(ns);
			editor.refresh();
		}
	}

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	protected void createFieldEditors() {

		TableFieldDescriptor stdTableDescriptor = new TableFieldDescriptor(
				new TableColumnDescriptor[] {
						new TableColumnDescriptor(PREFIX_COLUMN_NAME, 110), 
						new TableColumnDescriptor(URI_COLUMN_NAME, 300) 
				});
		stdTableDescriptor.setHeightHint(150);
		stdTableDescriptor.setStyle(SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.HIDE_SELECTION);
		stdTableDescriptor.setDataProvider(new StandardNamespacesProvider());

		addField(new TableFieldEditor(
				NamespaceFacilityConstants.PROPERTY_STANDARD_DEFINED_NAMESPACE,
				"Standard Namespaces:", 
				stdTableDescriptor, 
				getFieldEditorParent()));

		TableFieldDescriptor usrTableDescriptor = new TableFieldDescriptor(
				new TableColumnDescriptor[] {
						new TableColumnDescriptor(PREFIX_COLUMN_NAME, 110, true),
						new TableColumnDescriptor(URI_COLUMN_NAME, 300, true) 
				});
		usrTableDescriptor.setHeightHint(100);
		usrTableDescriptor.setStyle(SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		usrTableDescriptor.setDataProvider(new UserDefinedNamespacesProvider());

		final TableFieldEditor usrFieldEditor = 
			new TableFieldEditor(
					NamespaceFacilityConstants.PROPERTY_USER_DEFINED_NAMESPACE,
					"User Defined Namespaces:",
					usrTableDescriptor,
					getFieldEditorParent());
		addField(usrFieldEditor);

		// install actions
		IAction [] actions = {
				new Action("Add Namespace") {
					public void run() {
						addUserDefinedNamespace(usrFieldEditor);
					}
				},
				new Action("Delete Namespace") {
					public void run() {
						usrFieldEditor.removeSelectedItems();
						usrFieldEditor.refresh();

					}
				}};
		usrFieldEditor.installContextMenu(actions);
		
		Composite hbox = new Composite(getFieldEditorParent(), SWT.NONE);
		hbox.setLayout(new GridLayout(2, true));

		Button btnEdit= new Button(hbox, SWT.NONE);
		GridData gd = new GridData();
		gd.widthHint = 50;
		btnEdit.setLayoutData(gd);
		btnEdit.setText("Add");
		btnEdit.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				addUserDefinedNamespace(usrFieldEditor);
			}
		});

		Button btnRemove = new Button(hbox, SWT.NONE);
		gd = new GridData();
		gd.widthHint = 50;
		btnRemove.setLayoutData(gd);
		btnRemove.setText("Delete");
		btnRemove.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event) {
				usrFieldEditor.removeSelectedItems();
				usrFieldEditor.refresh();
			}
		});
		
	}

    /**
     * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
     */
    protected IPreferenceStore doGetPreferenceStore() {
		IProject project = (IProject) getElement().getAdapter(IProject.class);
        return new ScopedPreferenceStore(new ProjectScope(project), PROPERTY_STORE_ID);
    }

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	public IAdaptable getElement() {
		return element;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	public void setElement(IAdaptable element) {
		this.element = element;
	}

	/**
	 * 
	 */
	public NamespaceFacilityPreferencesPage() {
		super("Namespaces Settings", FieldEditorPreferencePage.GRID);
	}
	
	
	/**
	 * BaseNamespacesProvider
	 */
	private abstract class BaseNamespacesProvider implements ITableFieldDataProvider {

		/**
		 * @see com.odcgroup.workbench.ui.preferences.table.ITableFieldDataProvider#getImage(java.lang.Object, java.lang.String)
		 */
		public Image getImage(Object data, String columnName) {
			return null;
		}

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#getValue(java.lang.Object, java.lang.String)
		 */
		public Object getValue(Object data, String columnName) {
			Object value = null;
			if (data instanceof Namespace) {
				if (columnName.equals(PREFIX_COLUMN_NAME)) {
					value = ((Namespace) data).getPrefix();
				} else if (columnName.equals(URI_COLUMN_NAME)) {
					value = ((Namespace) data).getUri();
				}
			}
			return value;
		}

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#setValue(java.lang.Object, java.lang.String, java.lang.Object)
		 */
		public void setValue(Object data, String columnName, Object value) {
			if (data instanceof Namespace) {
				Namespace ns = (Namespace) data;
				if (columnName.equals(PREFIX_COLUMN_NAME)) {
					ns.setPrefix((String)value);
				} else if (columnName.equals(URI_COLUMN_NAME)) {
					ns.setUri((String)value);
				}
			}
		}

	}
	
	/**
	 * StandardNamespacesProvider
	 */
	private class StandardNamespacesProvider extends BaseNamespacesProvider {

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#load(java.lang.String)
		 */
		public Object[] load(String name) {
			TransformModel tm = TransformModelRegistry.getTransformModel();
			return tm.findNamespacesByOrigin(Namespace.DEFAULT_NAMESPACES).toArray();
		}

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#loadDefault(java.lang.String)
		 */
		public Object[] loadDefault(String preferenceName) {
			TransformModel tm = TransformModelRegistry.getTransformModel();
			return tm.findNamespacesByOrigin(Namespace.DEFAULT_NAMESPACES).toArray();
		}

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#store(java.lang.String, java.lang.Object[])
		 */
		public void store(String preferenceName, Object[] data) {
		}
	}
	
	/**
	 * UserDefinedNamespacesProvider
	 */
	private class UserDefinedNamespacesProvider extends BaseNamespacesProvider {

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#load(java.lang.String)
		 */
		public Object[] load(String name) {
			IProject project = (IProject) getElement().getAdapter(IProject.class);
			NamespaceFacility facility = NamespaceFacilityUtils.getNamespaceFacility(project);
			return facility.getUserDefinedNamespaces().toArray();
		}

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#loadDefault(java.lang.String)
		 */
		public Object[] loadDefault(String preferenceName) {
			return new Namespace[]{};
		}

		/**
		 * @see com.odcgroup.page.transformmodel.ui.preferences.ITableFieldDataProvider#store(java.lang.String, java.lang.Object[])
		 */
		public void store(String preferenceName, Object[] data) {
			IProject project = (IProject) getElement().getAdapter(IProject.class);
			NamespaceFacility facility = NamespaceFacilityUtils.getNamespaceFacility(project);
			ArrayList<Namespace> namespaces = new ArrayList<Namespace>();
			for (Object o : data) {
				namespaces.add((Namespace)o);
			}
			facility.setUserDefinedNamespaces(namespaces);
		}
	}
	
		

}
