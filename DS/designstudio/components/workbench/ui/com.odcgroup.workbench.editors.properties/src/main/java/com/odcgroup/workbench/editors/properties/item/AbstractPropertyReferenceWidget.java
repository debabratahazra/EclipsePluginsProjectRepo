package com.odcgroup.workbench.editors.properties.item;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;
import com.odcgroup.workbench.editors.properties.model.IPropertyReference;
import com.odcgroup.workbench.editors.properties.model.impl.PropertyFeatureChangeEvent;

/**
 *
 * @author pkk
 *
 */
public abstract class AbstractPropertyReferenceWidget extends AbstractPropertyWidget implements IPropertyReference {
	
	private List<IPropertyFeature> features = new ArrayList<IPropertyFeature>();
	
	
	/**
	 * @param reference
	 * @param label
	 * @param tooltip
	 */
	public AbstractPropertyReferenceWidget(EReference reference, String label, String tooltip) {
		super(reference, label, tooltip);
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyReference#getReference()
	 */
	public EReference getReference() {
		return (EReference) getStructuralFeature();
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyReference#addPropertyFeature
	 * (com.odcgroup.workbench.editors.properties.model.IPropertyFeature)
	 */
	public void addPropertyFeature(IPropertyFeature propertyFeature) {
		features.add(propertyFeature);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyContainer#getPropertyFeatures()
	 */
	public List<IPropertyFeature> getPropertyFeatures() {
		return features;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener#
	 * propertyChanged(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent)
	 */
	public void propertyChanged(IPropertyFeatureChangeEvent event) {
		IPropertyFeature feature = event.getPropertyFeature();
		EObject input = getFeatureInput();
		input.eSet(feature.getStructuralFeature(), event.getValue());
		notifyPropertyFeatureChange(input);		
	}	
	
	/**
	 * @param value
	 * @param feature
	 */
	public void notifyPropertyFeatureChange(Object value, IPropertyFeature feature) {
		IPropertyFeatureChangeEvent changeEvent =  new PropertyFeatureChangeEvent(feature, value, false);
		for (IPropertyFeatureChangeListener listener : feature.getPropertyFeatureChangeListeners()) {
			listener.propertyChanged(changeEvent);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#addPropertyFeatureChangeListener
	 * (com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.addPropertyFeatureChangeListener(propertyChangeListener);
		for (IPropertyFeature widget : getPropertyFeatures()) {
			if (getStructuralFeature() == null)
				widget.addPropertyFeatureChangeListener(propertyChangeListener);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#removePropertyFeatureChangeListener
	 * (com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void removePropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.removePropertyFeatureChangeListener(propertyChangeListener);
		for (IPropertyFeature widget : getPropertyFeatures()) {
			if (getStructuralFeature() == null)
				widget.removePropertyFeatureChangeListener(propertyChangeListener);
		}
	}
	
	/**
	 * @return
	 */
	protected EObject getFeatureInput() {
		if (getElement() == null) {
			return null;
		}
		if (getReference() == null) {
			return getElement();
		}
		EObject input = (EObject) getElement().eGet(getReference());
		if (input == null) {
			input = EcoreUtil.create(getReference().getEReferenceType());
		} else {
			input = EcoreUtil.copy(input);
		}
		return input;
	}

}
