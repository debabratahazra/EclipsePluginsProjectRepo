package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
@SuppressWarnings("restriction")
public class DecisionActionSelectionDialog extends FilteredTypesSelectionDialog implements IPropertySelectionDialog {

	/**
	 * @param parent
	 * @param multi
	 * @param context
	 * @param scope
	 * @param elementKinds
	 */
	public DecisionActionSelectionDialog(Shell parent, EObject element) {
		super(parent, false, PlatformUI.getWorkbench().getProgressService(), null, IJavaSearchConstants.TYPE);
		setTitle("Select Action Class");
		setMessage("Select a Decision Action");
		if (element != null && element instanceof DecisionAction) {
			DecisionAction action = (DecisionAction) element;
			setInitialPattern(Signature.getSimpleName(action.getUri()));
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog#getResultByProperty(com.odcgroup.workbench.editors.properties.model.IPropertyFeature)
	 */
	@Override
	public Object getResultByProperty(IPropertyFeature property) {
		IType result = (IType)getFirstResult();
		if (property.getStructuralFeature() == PageflowPackage.eINSTANCE.getAction_Name()) {
			return result.getElementName();
		} else if (property.getStructuralFeature() == PageflowPackage.eINSTANCE.getAction_Uri()) {
			return result.getFullyQualifiedName();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog#getSelection()
	 */
	@Override
	public Object getSelection() {
		return getFirstResult();
	}

}
