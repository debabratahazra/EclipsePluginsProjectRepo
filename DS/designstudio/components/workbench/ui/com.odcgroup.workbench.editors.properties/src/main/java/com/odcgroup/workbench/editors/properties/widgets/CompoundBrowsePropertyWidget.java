package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialogCreator;

/**
 * A Compound Widget with a browse control and other control which rely on browse selection
 *
 * @author pkk
 *
 */
public class CompoundBrowsePropertyWidget extends AbstractPropertyReferenceWidget {
	
	
	private BrowsePropertyWidget browseWidget = null;
	
	/**
	 * @param label
	 * @param tooltip
	 */
	public CompoundBrowsePropertyWidget(String label, String tooltip) {
		super(null, label, tooltip);
	}

	/**
	 * @param body
	 * @param property
	 * @param propertyWidgetFactory
	 */
	public CompoundBrowsePropertyWidget(EReference reference, String label) {
		super(reference, label, null);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {	
		if (getBrowseWidget() == null) {
			return;
		}
		
		Group group = SWTWidgetFactory.createGroup(body, getLabel(), true);
		// create label
		getBrowseWidget().createPropertyControl(group);
		if (getStructuralFeature() != null)
			getBrowseWidget().addPropertyFeatureChangeListener(this);
		
		for (IPropertyFeature feature : getPropertyFeatures()) {
			((AbstractPropertyWidget) feature).createPropertyControl(group);
			if (getStructuralFeature() != null)
				feature.addPropertyFeatureChangeListener(this);
		}
		
		getBrowseWidget().getBrowse().removeSelectionListener(getBrowseWidget());
		
		getBrowseWidget().getBrowse().addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				IPropertySelectionDialogCreator selectionDialogCreator = getBrowseWidget().getSelectionDialogCreator();
				if (selectionDialogCreator != null) {
					IPropertySelectionDialog dialog = selectionDialogCreator.createDialog(getShell(), getElement());
					final int resultCode = dialog.open();
					if (resultCode == IDialogConstants.OK_ID) {	
						Object value = dialog.getResultByProperty(getBrowseWidget());
						if (value == null) {
							return;
						}
						notifyPropertyFeatureChange(value, getBrowseWidget());
						for (IPropertyFeature feature : getPropertyFeatures()) {
							Object obj = dialog.getResultByProperty(feature);
							if (obj != null) {
								notifyPropertyFeatureChange(obj, feature);
							}
						}
					}
				}
				initiateWidget(getElement(), getRootElement());
			}
			
		});
	}
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget#addPropertyFeatureChangeListener
	 * (com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.addPropertyFeatureChangeListener(propertyChangeListener);
		if (getStructuralFeature() == null) {
			getBrowseWidget().addPropertyFeatureChangeListener(propertyChangeListener);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget#removePropertyFeatureChangeListener
	 * (com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void removePropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.removePropertyFeatureChangeListener(propertyChangeListener);
		if (getStructuralFeature() == null) {
			getBrowseWidget().removePropertyFeatureChangeListener(propertyChangeListener);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {		
		browseWidget.initiateWidget(getFeatureInput(), getRootElement());		
		for (IPropertyFeature widget : getPropertyFeatures()) {
			widget.initiateWidget(getFeatureInput(), getRootElement());
		}		
	}	
	
	/**
	 * @return the browseWidget
	 */
	public BrowsePropertyWidget getBrowseWidget() {
		return browseWidget;
	}

	/**
	 * @param browseWidget the browseWidget to set
	 */
	public void setBrowseWidget(BrowsePropertyWidget browseWidget) {
		this.browseWidget = browseWidget;
	}

}
