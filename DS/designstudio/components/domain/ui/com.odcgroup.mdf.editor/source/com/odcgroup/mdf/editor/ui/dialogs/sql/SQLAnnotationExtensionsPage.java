package com.odcgroup.mdf.editor.ui.dialogs.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.odcgroup.mdf.ecore.MdfAnnotationImpl;
import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.editor.ui.dialogs.annotations.AnnotationPropertyComposite;
import com.odcgroup.mdf.editor.ui.dialogs.annotations.AnnotationPropertyEditDialog;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class SQLAnnotationExtensionsPage extends DialogPage implements SelectionListener {

    private Map<String, SQLAnnotationProperty> sqlAnnotations = new LinkedHashMap<String, SQLAnnotationProperty>();
    private static String VALUE = "value";
    private static String INDEX = "Index";
    private static String INDEX_NAME_PREFIX = "name";
   
   
    private final MdfModelElement element;
    private TreeViewer treeViewer;
    private AnnotationPropertyComposite tableComp;
    private IAction deleteAction;
    private List annotations;


    /**
     * @param element
     */
    public SQLAnnotationExtensionsPage(MdfModelElement element) {
        super();
        setTitle("SQL");
        setDescription("Edits the SQL extensions of this element.");
        this.element = element;
        preparePossibleAnnotations(element);
    }
    
    /**
     * 
     */
    private void preparePossibleAnnotations(MdfModelElement element) {
    	sqlAnnotations.put(SQLAspect.SQL_NAME, SQLAnnotationProperty.SINGLE_PROPERTY_INSANCE(VALUE , false));
    	
    	// Those annotations are not available on domain
    	if (!(element instanceof MdfDomain)) {
			sqlAnnotations.put(SQLAspect.SQL_TYPE, SQLAnnotationProperty.SINGLE_PROPERTY_INSANCE(VALUE, false));     	
			sqlAnnotations.put(INDEX, SQLAnnotationProperty.MULTIPLE_PROPERTY_INSTANCE(INDEX_NAME_PREFIX));
			sqlAnnotations.put(SQLAspect.SQL_INHERITANCE, SQLAnnotationProperty.SINGLE_PROPERTY_ENUMVAL_INSTANCE(VALUE, false, SQLAspect.SQL_INHERITANCE_POSSIBLE_VALUES));
			sqlAnnotations.put(SQLAspect.SQL_DISCRIMINATOR_VALUE, SQLAnnotationProperty.SINGLE_PROPERTY_INSANCE(VALUE, false));
			sqlAnnotations.put(SQLAspect.SQL_VERSION_OPTIMISTIC_LOCKING, SQLAnnotationProperty.SINGLE_PROPERTY_ENUMVAL_INSTANCE(VALUE, false, SQLAspect.SQL_VERSION_OPTIMISTIC_LOCKING_POSSIBLE_VALUES));
			
			sqlAnnotations.put(SQLAspect.IGNORE, SQLAnnotationProperty.SINGLE_PROPERTY_BOOLEANVAL_INSTANCE(VALUE, false));     	
			//sqlAnnotations.put(SQLAspect.JOIN_SPECIFICATIONS, SQLAnnotationProperty.MULTIPLE_PROPERTY_INSTANCE(""));
    	}
    }

    /**
     * @see IDialogPage#createControl(Composite)
     */
    public void createControl(Composite parent) {
        WidgetFactory factory = getWidgetFactory();

        Composite container = factory.createComposite(parent);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		container.setLayoutData(gd);      
        GridLayout gridLayout = new GridLayout(2, false);
        container.setLayout(gridLayout);
        // create the tree viewer that lists the annotations
        createTreeViewer(container);
        // create the table viewer that lists the annotation properties
        tableComp = new AnnotationPropertyComposite(container, factory);
        tableComp.getAddButton().addSelectionListener(this);
        tableComp.getEditButton().addSelectionListener(this);
        tableComp.getRemoveButton().addSelectionListener(this);
        
        initialize();
        setControl(container);
    }
    
    /* (non-Javadoc)
     * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#setEditMode(int)
     */
    @Override
    public void setEditMode(int editMode) {
    	super.setEditMode(editMode);
    	if(editMode==MODE_READ_ONLY) {
    		getControl().setEnabled(true);
    	    treeViewer.getTree().setMenu(new MenuManager().createContextMenu(treeViewer.getTree()));
    	    tableComp.getAddButton().setVisible(false);
    	    tableComp.getEditButton().setVisible(false);
    	    tableComp.getRemoveButton().setVisible(false);
    	}
    }

    /**
     * @param parent
     */
    private void createTreeViewer(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.SINGLE);
		GridData gridData = new GridData(GridData.FILL_VERTICAL);
		gridData.verticalIndent = 2;
		treeViewer.getTree().setLayoutData(gridData);
    	treeViewer.setLabelProvider(new ColumnLabelProvider() {
            public String getText(Object element) {
            	MdfAnnotation annot = (MdfAnnotation)element;
                return annot.getName();
            }
        });
    	treeViewer.setContentProvider(new MdfAnnotationTreeContentProvider());
    	treeViewer.getTree().addSelectionListener(new SelectionAdapter(){			
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] items = ((Tree)e.widget).getSelection();
				if (items.length > 0 && items[0] != null) {
					deleteAction.setEnabled(true);
					tableComp.setAnnotation((MdfAnnotation)items[0].getData());
		            tableComp.getRemoveButton().setEnabled(false);
		            tableComp.getEditButton().setEnabled(false);
					if (getSelectedAnnotationProperty() == null ||
							(!getSelectedAnnotationProperty().isMultiple() && checkPropertyDefined())){
			    		tableComp.getAddButton().setEnabled(false);        		
		        	} else {
		    			tableComp.getAddButton().setEnabled(true);	        		
		        	}        
				}
			}    		
    	});
    	hookMenus();
    }
    
    /**
     * 
     */
    private void hookMenus() {
		
		MenuManager popupMenu = new MenuManager();		
	    
	    Set<String> keys = sqlAnnotations.keySet();
	    List<IAction> menuEntries = new ArrayList<IAction>(keys.size());
	    IAction action = null;
	    for (String key : keys) {
	    	final String annotationName = key;
	    	SQLAnnotationProperty property = sqlAnnotations.get(key);
			action = new Action() {
		    	public void run() {
		    		MdfAnnotation annotation =
		                ModelFactory.INSTANCE.createMdfAnnotation(
		                    SQLAspect.NAMESPACE_URI, annotationName);
	                ModelFactory.INSTANCE.addAnnotation(element, annotation);
	                setDirty(true);
	                treeViewer.refresh();
	                int i = treeViewer.getTree().getItems().length;
	                selectElementInTree(i-1);
	                refreshTreeViewerMenu();
	    			tableComp.getTableViewer().refresh();
	                tableComp.getAddButton().setEnabled(true);
		    	}				
			};
			action.setText("Add "+key);
			menuEntries.add(action);
			property.setAction(action);
		}
	    
	    deleteAction = new Action() {
	    	public void run() {
	    		TreeItem item = treeViewer.getTree().getSelection()[0];
	    		MdfAnnotation annotation = (MdfAnnotation)item.getData();
	    		String message = "Are you sure you want to delete annotation ["
						+ annotation.getName()
						+ "]?";
	    		boolean confirm = MessageDialog.openConfirm(getShell(), MdfPlugin.
	    				getResourceString("annotations.confirm.remove"), message);
	    		if (confirm) {
	    			Iterator it = element.getAnnotations().iterator();
	    	        while (it.hasNext()) {
	    	            MdfAnnotation annot = (MdfAnnotation) it.next();
	    	            if (annot.getNamespace().equals(annotation.getNamespace())
	    	                    && annot.getName().equals(annotation.getName())) {
	    	                it.remove();
	    	            }
	    	        }
	    			setDirty(true);
	    			treeViewer.refresh();
	                int i = treeViewer.getTree().getItems().length;
	    			selectElementInTree(i-1);
	    			enableTreeViewerMenuItem(annotation.getName());
	    			tableComp.getTableViewer().refresh();
	    		}
	    	}
	    };
	    
	    // add the entries to the menuManager
	    for (IAction entry : menuEntries) {
	    	popupMenu.add(entry);
	    }
	    popupMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	    deleteAction.setText(MdfPlugin.getResourceString("annotations.contextmenu.delete"));
	    popupMenu.add(deleteAction);
	    deleteAction.setEnabled(false);
	    Menu menu = popupMenu.createContextMenu(treeViewer.getTree());
	    treeViewer.getTree().setMenu(menu);
		
	}
    
    /**
     * 
     */
    private void initialize() {
    	treeViewer.setInput(element);
    	selectElementInTree(0);
    	treeViewer.refresh();
    	refreshTreeViewerMenu();
    }
    
    /**
     * @param annotationName
     */
    private void enableTreeViewerMenuItem(String annotationName) {
    	IAction action = sqlAnnotations.get(annotationName).getAction();
		if (action != null) {
			action.setEnabled(true);
		}
    }
    
    /**
     * 
     */
    private void refreshTreeViewerMenu() {
    	for (int i = 0; i < annotations.size();i++) {
			MdfAnnotation annot = (MdfAnnotation) annotations.get(i);
			if (annot.getNamespace().equals(SQLAspect.NAMESPACE_URI)
					&& sqlAnnotations.keySet().contains(annot.getName())) {
				IAction action = sqlAnnotations.get(annot.getName()).getAction();
				if (action != null) {
					action.setEnabled(false);
				}
			}
    	}
    }
    
    /**
     * @param index
     */
    private void selectElementInTree(int index) {
    	if (annotations.size()>0) {
    		treeViewer.getTree().setSelection(treeViewer.getTree().getItems()[index]);
    		TreeItem item = treeViewer.getTree().getSelection()[0];
			tableComp.setAnnotation((MdfAnnotation)item.getData());
			if (getSelectedAnnotationProperty() == null || 
					(!getSelectedAnnotationProperty().isMultiple() && checkPropertyDefined())){
	    		tableComp.getAddButton().setEnabled(false);        		
        	} else {
    			tableComp.getAddButton().setEnabled(true);	        		
        	}        
    	} else {
    		tableComp.getAddButton().setEnabled(false);
    	}
    }
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#doSave(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public void doSave(MdfModelElement element) {		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
        widgetSelected(e);		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {

        if (e.widget == tableComp.getAddButton()) {        	
            onAdd();
        } else if (e.widget == tableComp.getEditButton()) {
            onEdit();
        } else if (e.widget == tableComp.getRemoveButton()) {
            onRemove();
        } 
	}
	
	/**
	 * 
	 */
	private void onAdd() {
		SQLAnnotationProperty annotProp = getSelectedAnnotationProperty();
		AnnotationPropertyEditDialog dialog = null;
		if (annotProp.isMultiple()) {
			dialog = new AnnotationPropertyEditDialog(getShell(), true,
					annotProp.getPropertyName());
		} else if (annotProp.isEnumValue()) {
			dialog = new AnnotationPropertyEditDialog(
					getShell(), 
					annotProp.getPropertyName(), 
					annotProp.isNameOptional(), 
					annotProp.getPossibleValues());
		} else {
			dialog = new AnnotationPropertyEditDialog(getShell(), annotProp
					.getPropertyName(), annotProp.isNameOptional(), annotProp.isBooleanValue());
		}
		dialog.setCDataCheck(false);
        dialog.setWindowTitle(MdfPlugin
				.getResourceString("annotationsPro.editDialog.addTitle"));

        TreeItem tItem = treeViewer.getTree().getSelection()[0];
        MdfAnnotation annotation = (MdfAnnotation)tItem.getData();
        dialog.setDescription(MdfPlugin
				.getResourceString("annotationsPro.editDialog.description." 
						+ annotation.getName()));
        
        if (dialog.open() == Dialog.OK) {
            String name = dialog.getAnnotationName();
            MdfAnnotationProperty property = ModelFactory.INSTANCE
					.createMdfAnnotationProperty(annotation, name, dialog
							.getAnnotationValue(), false);
            element.getAnnotation(annotation.getNamespace(),
					annotation.getName()).getProperties().add(property);
            setDirty(true);
            if (!getSelectedAnnotationProperty().isMultiple()) {
            	tableComp.getAddButton().setEnabled(false);
            }
            tableComp.getTableViewer().refresh();
        }
		
	}
	
	/**
	 * @return
	 */
	private SQLAnnotationProperty getSelectedAnnotationProperty() {
		return sqlAnnotations.get(getSelectedAnnotation().getName());
	}
	
	private boolean checkPropertyDefined() {
		return getSelectedAnnotation().getProperties().size()>0;
	}
	
	private MdfAnnotation getSelectedAnnotation() {
		IStructuredSelection selection = (IStructuredSelection)treeViewer.getSelection();
		MdfAnnotationImpl annotation = (MdfAnnotationImpl)selection.getFirstElement();
		return annotation;
	}
	
	/**
	 * 
	 */
	private void onEdit() {
		IStructuredSelection selection = (IStructuredSelection)tableComp.getTableViewer().getSelection();
		MdfAnnotationPropertyImpl pr = (MdfAnnotationPropertyImpl)selection.getFirstElement();
		SQLAnnotationProperty annotProp = getSelectedAnnotationProperty();
		AnnotationPropertyEditDialog dialog = null;
		
		if (annotProp == null) {
			// No UI provided for this annoatation
			return;
		}
		
		if (annotProp.isMultiple()) {
			dialog = new AnnotationPropertyEditDialog(getShell(), true,
					annotProp.getPropertyName());
		} else if (annotProp.isEnumValue()) {
			dialog = new AnnotationPropertyEditDialog(
					getShell(), 
					annotProp.getPropertyName(), 
					annotProp.isNameOptional(), 
					annotProp.getPossibleValues());
		} else {			
			dialog = new AnnotationPropertyEditDialog(getShell(), annotProp
					.getPropertyName(), annotProp.isNameOptional(), annotProp.isBooleanValue());
		}
		dialog.setCDataCheck(false);
        dialog.setAnnotationName(pr.getName());
        dialog.setAnnotationValue(pr.getValue());
        dialog.setCDataEnabled(false);

        dialog.setWindowTitle(
            MdfPlugin.getResourceString("annotationsPro.editDialog.editTitle"));

        TreeItem tItem = treeViewer.getTree().getSelection()[0];
        MdfAnnotation annotation = (MdfAnnotation)tItem.getData();
        dialog.setDescription(MdfPlugin
				.getResourceString("annotationsPro.editDialog.description." 
						+ annotation.getName()));

        if (dialog.open() == Dialog.OK) {
        	pr.setName(dialog.getAnnotationName());
        	pr.setValue(dialog.getAnnotationValue());
        	pr.setCDATA(dialog.isCDataEnabled());
        	element.getAnnotation(annotation.getNamespace(), annotation.getName()).getProperties().add(pr);
            setDirty(true);
            tableComp.getTableViewer().refresh();
        }
	}
	
	/**
	 * 
	 */
	private void onRemove() {
		IStructuredSelection selection = (IStructuredSelection)tableComp.getTableViewer().getSelection();
		MdfAnnotationPropertyImpl pr = (MdfAnnotationPropertyImpl)selection.getFirstElement();
		String message = MdfPlugin.getResourceString("annotationsPro.confirm.msg");
		boolean confirm = MessageDialog.openConfirm(getShell(), MdfPlugin.getResourceString("annotationsPro.confirm.remove"), message);
		if (confirm) {
            TreeItem tItem = treeViewer.getTree().getSelection()[0];
            MdfAnnotation annotation = (MdfAnnotation)tItem.getData();
			Iterator iterator = annotation.getProperties().iterator();
			while (iterator.hasNext()) {
				MdfAnnotationProperty property = (MdfAnnotationProperty) iterator.next();
				if (property.getName().equals(pr.getName()) 
						&& property.getValue().equals(property.getValue())) {
					iterator.remove();
				}
			}
			element.getAnnotations().add(annotation);
			setDirty(true);
			tableComp.getEditButton().setEnabled(false);
			tableComp.getRemoveButton().setEnabled(false);
			tableComp.getTableViewer().refresh();
		}
		
	}

    private class MdfAnnotationTreeContentProvider implements ITreeContentProvider {
		
		public Object[] getChildren(Object parentElement) {
			return null;
		}
		
		public Object getParent(Object element) {
			return null;
		}
		
		public boolean hasChildren(Object element) {
			return false;
		}
		
		public Object[] getElements(Object inputElement) {
			MdfModelElement mElement = (MdfModelElement) inputElement;
			annotations = new ArrayList();
			annotations.addAll(mElement.getAnnotations());
			Iterator iter = mElement.getAnnotations().iterator();
			while(iter.hasNext()) {
				MdfAnnotation ann = (MdfAnnotation)iter.next();
				if (!ann.getNamespace().equals(SQLAspect.NAMESPACE_URI)){
					annotations.remove(ann);
				}
			}
            return annotations.toArray(new MdfAnnotation[] {});
		}
		
		public void dispose() {			
		}
		
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {			
		}
    	
    }
    

   
    
}
