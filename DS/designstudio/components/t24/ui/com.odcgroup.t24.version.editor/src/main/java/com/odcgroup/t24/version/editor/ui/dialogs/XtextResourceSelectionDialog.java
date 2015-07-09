package com.odcgroup.t24.version.editor.ui.dialogs;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch;
import org.eclipse.xtext.ui.search.XtextEObjectSearchDialog;

import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;

/**
 * Resource Search Dialog, searching the Components by indexing.
 * @author manilkapoor
 *
 */
public class XtextResourceSelectionDialog extends XtextEObjectSearchDialog implements IPropertySelectionDialog {

	private String initialTypeText;
	private boolean typePatternEditable = true;

	public XtextResourceSelectionDialog(Shell parent, IXtextEObjectSearch searchEngine, ILabelProvider labelProvider) {
		super(parent, searchEngine, labelProvider);
	}

	public XtextResourceSelectionDialog(Shell parent, IXtextEObjectSearch searchEngine, ILabelProvider labelProvider, boolean enableStyledLabels) {
		super(parent, searchEngine, labelProvider, enableStyledLabels);
	}
	
	public void setInitialTypePattern(String text) {
		setInitialTypePattern(text, true);
	}

	public void setInitialTypePattern(String text, boolean editable) {
		this.initialTypeText = text;
		this.typePatternEditable = editable;
	}
	
	@Override
	protected Label createMessageArea(Composite composite) {
		Label label = super.createMessageArea(composite);
		typeSearchControl.setText(initialTypeText);
		typeSearchControl.setEditable(typePatternEditable);
		return label;
	}

	@Override
	public Object getSelection() {
        Object[] result = getResult();
        if (result == null || result.length == 0) {
			return null;
		}
        IEObjectDescription eobj = (IEObjectDescription) result[0];
		return eobj/*.getEObjectURI().toString()*/;
	}

	@Override
	public Object getResultByProperty(IPropertyFeature property) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
