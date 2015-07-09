package com.odcgroup.t24.enquiry.properties.dialogs;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch;
import org.eclipse.xtext.ui.search.XtextEObjectSearchDialog;

import com.odcgroup.workbench.ui.xtext.search.LanguageXtextEObjectSearch;

/**
 * @author atr
 *
 */
public class ResourceSelectionDialogCreator {

	private LanguageXtextEObjectSearch eObjectSearch;
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;
	private String initialTypePattern;
	private ResourceSelectionFilter filter = null;
	
	public class XtextResourceSelectionDialog extends XtextEObjectSearchDialog  {

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

		
		public Object getSelection() {
	        Object[] result = getResult();
	        if (result == null || result.length == 0) {
				return null;
			}
	        IEObjectDescription eobj = (IEObjectDescription) result[0];
			return eobj;
		}
		
		@Override
		protected void startSizeCalculation(Iterable<IEObjectDescription> matches) {
			if (filter != null) {
				super.startSizeCalculation(filter.filter(matches));
			} else {
				super.startSizeCalculation(matches);
			}
		}
		
	}
	
	protected final String getInitialTypePattern() {
		return this.initialTypePattern;
	}
	
	public ResourceSelectionDialogCreator(LanguageXtextEObjectSearch eObjectSearch, GlobalDescriptionLabelProvider globalDescriptionLabelProvider, String initialTypePattern,  ResourceSelectionFilter filter) {
		this.initialTypePattern = initialTypePattern;
		this.eObjectSearch = eObjectSearch;
		this.globalDescriptionLabelProvider = globalDescriptionLabelProvider;
		this.filter = filter;
	}
	
	
	public XtextResourceSelectionDialog createDialog(Shell shell) {
		XtextResourceSelectionDialog dialog = new XtextResourceSelectionDialog(shell, eObjectSearch, globalDescriptionLabelProvider);
		dialog.setInitialTypePattern(getInitialTypePattern(), false);
		return dialog;
	}

}
