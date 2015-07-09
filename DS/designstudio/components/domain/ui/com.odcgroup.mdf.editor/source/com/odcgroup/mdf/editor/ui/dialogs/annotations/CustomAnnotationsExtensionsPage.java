package com.odcgroup.mdf.editor.ui.dialogs.annotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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

import com.odcgroup.mdf.ecore.MdfAnnotationPropertyImpl;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.DialogPage;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;



/**
 * @author pkk
 */
public class CustomAnnotationsExtensionsPage extends DialogPage implements SelectionListener, MouseListener {
   
	protected static final String AnnotationsAspect_NAMESPACE_URI = "http://www.odcgroup.com/mdf/ext/annotations";
	protected static final String AnnotationsAspect_GENERIC_ANNOTATIONS = "GenericAnnotations";
	
    private MdfModelElement element;
    private TreeViewer treeViewer;
    private AnnotationPropertyComposite tableComp;
    private IAction newAnnotationAction;
    private IAction deleteAction;
    private IAction editAction;
    private IAction copyAction;
    private IAction pasteAction;
    private List annotations;
    private static MdfAnnotation copiedAnnotation;

    /**
     * @param element
     */
    public CustomAnnotationsExtensionsPage(MdfModelElement element) {
        super();
        setTitle(MdfPlugin.getResourceString("annotations.page.title"));
        setDescription(MdfPlugin.getResourceString("annotations.page.description"));
        this.element = element;
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
        tableComp = new AnnotationPropertyComposite(container, factory, true);
        tableComp.getAddButton().addSelectionListener(this);
        tableComp.getEditButton().addSelectionListener(this);
        tableComp.getRemoveButton().addSelectionListener(this);
        tableComp.addMouseListener(this);
        
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
                return annot.getName()+" ("+annot.getNamespace()+")";
            }
        });
    	treeViewer.setContentProvider(new MdfAnnotationTreeContentProvider());
    	treeViewer.addFilter(new ViewerFilter(){
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				if (element instanceof MdfAnnotation) {
					MdfAnnotation annot = (MdfAnnotation) element;
					List list = new ArrayList();
					Collections.addAll(list, FILTERED_NAMESPACES);
					if (list.contains(annot.getNamespace())) {
						return false;
					}
				}
				return true;
			}
    		
    	});
    	treeViewer.getTree().addSelectionListener(new SelectionAdapter(){			
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] items = ((Tree)e.widget).getSelection();
				if (items.length > 0 && items[0] != null) {
					MdfAnnotation annot = (MdfAnnotation) items[0].getData();
					if (!annot.getNamespace().equals(AnnotationsAspect_NAMESPACE_URI)) {
						editAction.setEnabled(true);
					} else {
						editAction.setEnabled(false);
					}
					copyAction.setEnabled(true);
					deleteAction.setEnabled(true);
					tableComp.setAnnotation(annot);
		            tableComp.getRemoveButton().setEnabled(false);
		            tableComp.getEditButton().setEnabled(false);
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
		
		newAnnotationAction = new Action(){
	    	public void run() {
	            AnnotationEditDialog dialog = new AnnotationEditDialog(getShell(), element);
	            dialog.setWindowTitle(MdfPlugin.getResourceString("annotations.editDialog.addTitle"));

	            if (dialog.open() == Dialog.OK) {
	                String name = dialog.getAnnotationName();
	                String nameSpace = dialog.getAnnotationNameSpace();
	                MdfAnnotation annotation = ModelFactory.INSTANCE.createMdfAnnotation(nameSpace, name);
	                ModelFactory.INSTANCE.addAnnotation(element, annotation);
	                treeViewer.refresh();
	                tableComp.getAddButton().setEnabled(true);
	                setDirty(true);
	            }
	    	}
	    };
	    
	    editAction = new Action() {
	    	public void run() {
	    		TreeItem item = treeViewer.getTree().getSelection()[0];
	    		MdfAnnotation annot = (MdfAnnotation)item.getData();
	            AnnotationEditDialog dialog = new AnnotationEditDialog(getShell(), element);
	            dialog.setAnnotationName(annot.getName());
	            dialog.setAnnotationNameSpace(annot.getNamespace());
	            final List properties = annot.getProperties();
	            dialog.setWindowTitle(MdfPlugin.getResourceString("annotations.contextmenu.edit"));

	            if (dialog.open() == Dialog.OK) {
	            	element.getAnnotations().remove(annot);
	                String name = dialog.getAnnotationName();
	                String nameSpace = dialog.getAnnotationNameSpace();
	                MdfAnnotation annotation = ModelFactory.INSTANCE.createMdfAnnotation(nameSpace, name);
	                annotation.getProperties().addAll(properties);
	                ModelFactory.INSTANCE.addAnnotation(element, annotation);
	                treeViewer.refresh();
	                tableComp.getAddButton().setEnabled(true);
	                setDirty(true);
	            }
	    	}	    	
	    };

		copyAction = new Action() {
			public void run() {
				TreeItem item = treeViewer.getTree().getSelection()[0];
				copiedAnnotation = (MdfAnnotation) item.getData();
				tableComp.getAddButton().setEnabled(true);
				pasteAction.setEnabled(true);
			}
		};

		pasteAction = new Action() {
			public void run() {
				if (copiedAnnotation != null) {
					String name = copiedAnnotation.getName();
					String nameSpace = copiedAnnotation.getNamespace();
					MdfAnnotation annotation = ModelFactory.INSTANCE.createMdfAnnotation(nameSpace, name);
					ModelFactory.INSTANCE.addAnnotation(element, annotation);
					ListIterator<MdfAnnotationProperty> listIterator = copiedAnnotation.getProperties().listIterator();
					List<MdfAnnotationProperty> properties = new ArrayList<MdfAnnotationProperty>();
					while (listIterator.hasNext()) {
						MdfAnnotationProperty property = (MdfAnnotationProperty) listIterator.next();
						MdfAnnotationProperty newProperty = ModelFactory.INSTANCE.createMdfAnnotationProperty(annotation, property.getName(),
								property.getValue(), property.isCDATA());
						properties.add(newProperty);
					}
					annotation.getProperties().addAll(properties);
					treeViewer.refresh();
					tableComp.getAddButton().setEnabled(true);
					setDirty(true);
				} else {
					String message = "Missing copied annotation.";
					MessageDialog
							.openError(getShell(), MdfPlugin.getResourceString("annotations.error.paste"), message);
					pasteAction.setEnabled(false);
				}
			}
		};

	    deleteAction = new Action() {
	    	public void run() {
	    		TreeItem item = treeViewer.getTree().getSelection()[0];
	    		MdfAnnotation annotation = (MdfAnnotation)item.getData();
	    		String message = "Are you sure you want to delete annotation ["+annotation.getName()+" ("+annotation.getNamespace()+")]?";
	    		boolean confirm = MessageDialog.openConfirm(getShell(), MdfPlugin.
	    				getResourceString("annotations.confirm.remove"), message);
	    		if (confirm) {
	    			Iterator it = element.getAnnotations().iterator();
	    	        while (it.hasNext()) {
	    	            MdfAnnotation annot = (MdfAnnotation) it.next();
	    	            if (annot.getNamespace().equals(annotation.getNamespace())
	    	                    && annot.getName().equals(annotation.getName())) {
	    	                it.remove();
	    	                break;
	    	            }
	    	        }
	    			setDirty(true);
	    			treeViewer.refresh();
	    			tableComp.getTableViewer().refresh();
	    	    	selectFirstElementInTree();
	    		}
	    	}
	    };
	    
	    newAnnotationAction.setText(MdfPlugin.getResourceString("annotations.contextmenu.addCustom"));
	    editAction.setText(MdfPlugin.getResourceString("annotations.contextmenu.edit"));
	    copyAction.setText(MdfPlugin.getResourceString("annotations.contextmenu.copy"));
	    pasteAction.setText(MdfPlugin.getResourceString("annotations.contextmenu.paste"));
	    deleteAction.setText(MdfPlugin.getResourceString("annotations.contextmenu.delete"));
	    popupMenu.add(newAnnotationAction);
	    popupMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	    popupMenu.add(editAction);
	    popupMenu.add(copyAction);
	    popupMenu.add(pasteAction);
	    popupMenu.add(deleteAction);
	    deleteAction.setEnabled(true);
	    Menu menu = popupMenu.createContextMenu(treeViewer.getTree());
	    treeViewer.getTree().setMenu(menu);
		
	}
    
    /**
     * 
     */
    private void initialize() {
    	annotations = element.getAnnotations();
    	treeViewer.setInput(element);
    	selectFirstElementInTree();
    	treeViewer.refresh();
    }
    
    /**
     * 
     */
    private void selectFirstElementInTree() {
    	if (annotations.size()>0 && treeViewer.getTree().getItems().length > 0) {
    		treeViewer.getTree().setSelection(treeViewer.getTree().getItems()[0]);
    		TreeItem item = treeViewer.getTree().getSelection()[0];
			tableComp.setAnnotation((MdfAnnotation)item.getData());
			tableComp.getAddButton().setEnabled(true);
			deleteAction.setEnabled(true);
    	} else {
    		deleteAction.setEnabled(false);
    		editAction.setEnabled(false);
    		copyAction.setEnabled(false);
    		pasteAction.setEnabled(false);
    		tableComp.getAddButton().setEnabled(false);
    	}

    	if(copiedAnnotation != null) {
    		pasteAction.setEnabled(true);
    	} else {
    		pasteAction.setEnabled(false);
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
		AnnotationPropertyEditDialog dialog = new AnnotationPropertyEditDialog(getShell());
        dialog.setWindowTitle(MdfPlugin.getResourceString("annotationsPro.editDialog.addTitle"));

        if (dialog.open() == Dialog.OK) {
            String name = dialog.getAnnotationName();
            TreeItem tItem = treeViewer.getTree().getSelection()[0];
            MdfAnnotation annotation = (MdfAnnotation)tItem.getData();
            MdfAnnotationProperty property = ModelFactory.INSTANCE.createMdfAnnotationProperty(
            		annotation, name, dialog.getAnnotationValue(), dialog.isCDataEnabled());
            element.getAnnotation(annotation.getNamespace(), annotation.getName()).getProperties().add(property);
            setDirty(true);
            tableComp.tableViewer.refresh();
        }
		
	}
	
	/**
	 * 
	 */
	private void onEdit() {
		IStructuredSelection selection = (IStructuredSelection)tableComp.getTableViewer().getSelection();
		MdfAnnotationPropertyImpl pr = (MdfAnnotationPropertyImpl)selection.getFirstElement();
		
        AnnotationPropertyEditDialog dialog = new AnnotationPropertyEditDialog(getShell());
        dialog.setAnnotationName(pr.getName());
        dialog.setAnnotationValue(pr.getValue());
        dialog.setCDataEnabled(pr.isCDATA());

        dialog.setWindowTitle(
            MdfPlugin.getResourceString("annotationsPro.editDialog.editTitle"));

        if (dialog.open() == Dialog.OK) {
            TreeItem tItem = treeViewer.getTree().getSelection()[0];
            MdfAnnotation annotation = (MdfAnnotation)tItem.getData();
        	pr.setName(dialog.getAnnotationName());
        	pr.setValue(dialog.getAnnotationValue());
        	pr.setCDATA(dialog.isCDataEnabled());
        	element.getAnnotation(annotation.getNamespace(), annotation.getName()).getProperties().add(pr);
            setDirty(true);
            tableComp.tableViewer.refresh();
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
			tableComp.getRemoveButton().setEnabled(false);
			tableComp.getEditButton().setEnabled(false);
			tableComp.tableViewer.refresh();
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
            return annotations.toArray(new MdfAnnotation[] {});
		}
		
		public void dispose() {			
		}
		
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {			
		}
    	
    }
    
    /* (non-Javadoc)
	 * @see com.odcgroup.mdf.editor.ui.dialogs.DialogPage#dispose()
	 */
	@Override
	public void dispose() {
		this.element = null;
		super.dispose();
	}

	/**
     * filtered annotations from the custom list
     */
    private static final String[] FILTERED_NAMESPACES = {
    	"http://www.odcgroup.com/mdf/sql", 
    	//"http://www.odcgroup.com/mdf/customization",
    	"http://www.odcgroup.com/querybuilder",
    	"http://www.odcgroup.com/aaa",
    	"http://www.odcgroup.com/java",
    	"http://www.odcgroup.com/translation"
    	};


	@Override
	public void mouseDoubleClick(MouseEvent e) {
		onEdit();
	}

	@Override
	public void mouseDown(MouseEvent e) {
    
	}

	@Override
	public void mouseUp(MouseEvent e) {
		
	}

    
}
