package com.odcgroup.aaa.ui.internal.page;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.FormWidgetFactory;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;


/**
 * A dialog to select a concept.
 */
public class MdfAssociationSelectionDialog extends Dialog {

    private static final String AAA_ENTITIES_TYPE = "AAAEntities:Type";

    private static final String DEFAULT_WINDOW_TITLE = MdfPlugin.getResourceString("propertySelectionDialog.title");

    private static final String DEFAULT_LABEL = MdfPlugin.getResourceString("propertySelectionDialog.label");

    private TreeViewer propertiesViewer;
    List<MdfClass> input;
    private MdfClass selectedMdfClass;
    private MdfAssociation selectedMdfAssociation;

	private MdfAssociation sourceAssociation;

	/**
     * Constructs a type selection dialog.
     * 
     * @param parent the parent shell.
     * @param context the runnable context.
     */
    public MdfAssociationSelectionDialog(Shell parent, MdfAssociation sourceAssociation) {
        super(parent);
        setShellStyle(getShellStyle() | SWT.RESIZE);
        this.sourceAssociation = sourceAssociation;
    }
    
    protected Point getInitialSize() {
        return new Point(400, 400);
    }


    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    protected Control createDialogArea(Composite parent) {
        WidgetFactory factory = new FormWidgetFactory();

        Composite container = factory.createComposite(parent);
        container.setLayout(new GridLayout(1, false));
        container.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.VERTICAL_ALIGN_BEGINNING));

        Label label = factory.createLabel(container, DEFAULT_LABEL);
        label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_BEGINNING));

        Tree tree = factory.createTree(container, SWT.FLAT | SWT.SINGLE
                | SWT.H_SCROLL | SWT.V_SCROLL);
        propertiesViewer = new TreeViewer(tree);
        propertiesViewer.setUseHashlookup(true);

        propertiesViewer.setContentProvider(new ContentProvider());
        propertiesViewer.setSorter(new MdfElementSorter());

        ILabelDecorator decorator = MdfPlugin.getDefault().getWorkbench().getDecoratorManager().getLabelDecorator();
        propertiesViewer.setLabelProvider(new DecoratingLabelProvider(
                new MdfTreeLabelProvider(), decorator));
        input = createInput();
		propertiesViewer.setInput(input);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.VERTICAL_ALIGN_BEGINNING));
        propertiesViewer.addOpenListener(new IOpenListener() {
            public void open(OpenEvent e) {
                okPressed();
            }
        });

        if (selectedMdfClass != null) {
        	if (selectedMdfAssociation != null) {
        		propertiesViewer.setSelection(new StructuredSelection(selectedMdfAssociation));
        	} else {
        		propertiesViewer.setSelection(new StructuredSelection(selectedMdfClass));
        	}
        }

        factory.dispose();
        return container;
    }

    @SuppressWarnings("unchecked")
	private List<MdfClass> createInput() {
    	List<MdfClass> input = new ArrayList<MdfClass>();
        List<MdfEntity> entities = MdfEcoreUtil.getAllEntities(((EObject)sourceAssociation).eResource());
        for (MdfEntity entity : entities) {
        	if (entity instanceof MdfClass) {
        		MdfClass mdfClass = (MdfClass)entity;
        		propertyLoop: for (MdfProperty property: (List<MdfProperty>)mdfClass.getProperties()) {
        			if (property instanceof MdfAssociation) {
        				MdfAssociation association = (MdfAssociation)property;
        				if (hasEntityTypeType(association)) {
        					input.add(mdfClass);
        					break propertyLoop;
        				}
        			}
        		}
            }
		}
		return input;
	}

	private boolean hasEntityTypeType(MdfAssociation association) {
		return association.getType() != null &&
				association.getType().getQualifiedName() != null &&
				association.getType().getQualifiedName().getQualifiedName().equals(AAA_ENTITIES_TYPE);
	}

	protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DEFAULT_WINDOW_TITLE);
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {
        TreeItem item = propertiesViewer.getTree().getSelection()[0];
        if (item != null) {
        	MdfModelElement mdfElement = (MdfModelElement)item.getData();
        	if (mdfElement instanceof MdfAssociation) {
        		selectedMdfAssociation = (MdfAssociation)mdfElement;
        		selectedMdfClass = selectedMdfAssociation.getParentClass();
        	} else if (mdfElement instanceof MdfClass) {
        		selectedMdfClass = (MdfClass)mdfElement;
        		selectedMdfAssociation = null;
        	} else {
        		selectedMdfClass = null;
        		selectedMdfAssociation = null;
        	}
        } else {
    		selectedMdfClass = null;
    		selectedMdfAssociation = null;
        }
        super.okPressed();
    }

    public MdfClass getSelectedMdfClass() {
		return selectedMdfClass;
	}

	public void setSelectedMdfClass(MdfClass selectedMdfClass) {
		this.selectedMdfClass = selectedMdfClass;
	}

	public MdfAssociation getSelectedMdfAssociation() {
		return selectedMdfAssociation;
	}

	public void setSelectedMdfAssociation(MdfAssociation selectedMdfAssociation) {
		this.selectedMdfAssociation = selectedMdfAssociation;
	}

	private class ContentProvider implements IStructuredContentProvider,
            ITreeContentProvider {

		public ContentProvider() {
        }

        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
		public Object[] getChildren(Object inputElement) {
        	if (inputElement instanceof List) {
        		return ((List)inputElement).toArray();
        	} else if (inputElement instanceof MdfClass) {
            	MdfClass mdfClass = (MdfClass)inputElement;
            	List<MdfAssociation> associations = new ArrayList<MdfAssociation>();
            	for (MdfProperty property: (List<MdfProperty>)mdfClass.getProperties()) {
            		if (property instanceof MdfAssociation) {
            			MdfAssociation association = (MdfAssociation)property;
            			if (hasEntityTypeType(association)) {
            				associations.add(association);
            			}
            		}
            	}
                return associations.toArray();
            }
            return new Object[]{};
        }

        public Object getParent(Object obj) {
        	if (obj instanceof MdfAssociation) {
        		return ((MdfAssociation)obj).getParentClass();
        	}
            return null;
        }

        public boolean hasChildren(Object obj) {
            if (obj instanceof MdfClass) {
                return true;
            } else {
                return false;
            }
        }

    }
}
