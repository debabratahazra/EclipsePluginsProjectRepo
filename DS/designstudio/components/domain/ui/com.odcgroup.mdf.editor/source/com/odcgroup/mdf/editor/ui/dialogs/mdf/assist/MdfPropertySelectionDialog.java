package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.actions.DeriveBaseClassUtil;
import com.odcgroup.mdf.editor.ui.dialogs.FormWidgetFactory;
import com.odcgroup.mdf.editor.ui.providers.label.MdfTreeLabelProvider;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;


/**
 * A dialog to select a concept.
 */
public class MdfPropertySelectionDialog extends Dialog {

    private static final String DEFAULT_WINDOW_TITLE = MdfPlugin.getResourceString("propertySelectionDialog.title");

    private static final String DEFAULT_LABEL = MdfPlugin.getResourceString("propertySelectionDialog.label");

    private final MdfDataset rootDataset;
    private final MdfClass rootClass;
    private TreeViewer propertiesViewer;
    private String path;
    
    private String loadPermitted = null;

	/**
     * Constructs a type selection dialog.
     * 
     * @param parent the parent shell.
     * @param context the runnable context.
     */
    public MdfPropertySelectionDialog(Shell parent, MdfDataset rootDataset) {
        super(parent);
        this.rootDataset = rootDataset;
        this.rootClass = rootDataset.getBaseClass();
        setShellStyle(getShellStyle() | SWT.RESIZE);
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
        propertiesViewer.setInput(rootClass);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.VERTICAL_ALIGN_BEGINNING));
        propertiesViewer.addOpenListener(new IOpenListener() {

            public void open(OpenEvent e) {
                okPressed();
            }
        });

        PathVisitor.visitPath(path, new PathVisitor() {

            private MdfEntity type = rootClass;

            public boolean visit(String name) {
                if (type instanceof MdfClass) {
                    MdfProperty prop = ((MdfClass) type).getProperty(name);

                    if (prop == null) {
                        return false;
                    } else {
                        type = prop.getType();
                        propertiesViewer.setSelection(new StructuredSelection(
                                prop));
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });

        factory.dispose();
        return container;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(DEFAULT_WINDOW_TITLE);
    }

    protected Point getInitialSize() {
        return new Point(400, 250);
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {
        StringBuffer buffer = new StringBuffer();

        TreeItem item = propertiesViewer.getTree().getSelection()[0];
        while (item != null) {
            MdfProperty prop = (MdfProperty) item.getData();
			boolean permVal = DeriveBaseClassUtil.hasPermittedValues(prop);
			if (permVal) {
				this.loadPermitted = Boolean.valueOf(permVal).toString();
        	}
            buffer.insert(0, prop.getName());
            item = item.getParentItem();

            if (item != null) {
                buffer.insert(0, '.');
            }
        }

        path = buffer.toString();
        super.okPressed();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

	/**
	 * @return the loadPermitted
	 */
	public String getLoadPermitted() {
		return loadPermitted;
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

        public Object[] getChildren(Object inputElement) {
            List children = Collections.EMPTY_LIST;

            if (inputElement instanceof MdfClass) {
                children = getProperties((MdfClass) inputElement);
            } else if (inputElement instanceof MdfAttribute) {
                // -- No Children
            } else if (inputElement instanceof MdfAssociation) {
                MdfAssociation assoc = (MdfAssociation) inputElement;
                children = getProperties((MdfClass) assoc.getType());
            } else if (inputElement instanceof MdfReverseAssociation) {
                MdfReverseAssociation assoc = (MdfReverseAssociation) inputElement;
                children = getProperties((MdfClass) assoc.getType());
            }

            return children.toArray();
        }

        private List getProperties(MdfClass klass) {
            List props = new ArrayList();
            props.addAll(klass.getProperties(true));
            props.addAll(ModelHelper.getReverseAssociations(
                    rootDataset.getParentDomain(), klass));
            return props;
        }

        public Object getParent(Object obj) {
            return null;
        }

        public boolean hasChildren(Object obj) {
            if (obj instanceof MdfClass) {
                return true;
            } else if (obj instanceof MdfAssociation) {
                return true;
            } else if (obj instanceof MdfReverseAssociation) {
                return true;
            }

            return false;
        }

    }
}
