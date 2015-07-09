package com.odcgroup.t24.version.editor.ui.controls;

import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.mdf.editor.ui.dialogs.MdfAttributeWithRef;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.versionDSL.Field;

/**
 * Abstract tab control class for Field Tabs
 * 
 * @author snn
 * 
 */
public abstract class AbstractFieldTabControl extends AbstractVersionTabControl
		implements ISelectionListener {

	private Field tabInput;
	
	 private TreeViewer viewer =null;

	public AbstractFieldTabControl(Composite parent,
			VersionDesignerEditor editor, DataBindingContext context,TreeViewer viewer) {
		super(parent, editor, context);
		ISelectionService selectionService = editor.getSite().getWorkbenchWindow().getSelectionService();
		selectionService.addPostSelectionListener(this);
		this.viewer = (viewer);
	}

	/**
	 * set the input for the tab.
	 * 
	 * @param input
	 */
	public void setTabInput(Field input) {
		this.tabInput = input;
	}

	/**
	 * return the input for the tabs.
	 */
	public Field getTabInput() {
		return this.tabInput;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection).getFirstElement();
			if (selectedObject instanceof MdfProperty) {
				Field field = getField((MdfProperty) selectedObject);
				setTabInput(field);
			}
		}
	}
	
	protected Field getField(MdfModelElement property) {
		String name = T24Aspect.getT24Name(property);
		if (name == null) {
			name = property.getName();
			name = name.replace("_", ".");
		}
		List<Field> fields = getEditedVersion().getFields();
		if(name.equals("*")){
			return (Field) ((MdfAttributeWithRef)property).getRefObj();
		}
		for (Field field : fields) {
			String fname = field.getName().replace("_", ".");
			if (name.equals(fname)) {
				return field;
			}
		}
		return null;
	}

	public TreeViewer getViewer() {
		return viewer;
	}

}
