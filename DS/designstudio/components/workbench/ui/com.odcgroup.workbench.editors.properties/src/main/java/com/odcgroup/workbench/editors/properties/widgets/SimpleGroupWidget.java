package com.odcgroup.workbench.editors.properties.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget;
import com.odcgroup.workbench.editors.properties.model.IPropertyContainer;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;

/**
 * A Simple Group Widget, which contains other input widgets
 *
 * @author pkk
 *
 */
public class SimpleGroupWidget extends AbstractPropertyWidget implements IPropertyContainer {
	
	private Group group = null;
	private boolean fillBoth = true;
	private List<IPropertyFeature> features = new ArrayList<IPropertyFeature>();

	/**
	 * @param label
	 */
	public SimpleGroupWidget(String label) {
		this(label, null);
	}

	/**
	 * @param feature
	 * @param label
	 * @param tooltip
	 */
	public SimpleGroupWidget(String label, String tooltip) {
		super(null, label, tooltip);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.ISWTWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		group = SWTWidgetFactory.createGroup(body, getLabel(), isFillBoth());	
		for (IPropertyFeature feature : getPropertyFeatures()) {
			((ISWTWidget) feature).createPropertyControl(group);
		}			
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {
		for (IPropertyFeature feature : getPropertyFeatures()) {
			feature.initiateWidget(getElement(), getRootElement());
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyContainer#addPropertyFeature(com.odcgroup.workbench.editors.properties.model.IPropertyFeature)
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
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#addPropertyFeatureChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.addPropertyFeatureChangeListener(propertyChangeListener);
		for (IPropertyFeature feature : getPropertyFeatures()) {
			feature.addPropertyFeatureChangeListener(propertyChangeListener);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#removePropertyFeatureChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void removePropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.removePropertyFeatureChangeListener(propertyChangeListener);
		for (IPropertyFeature feature : getPropertyFeatures()) {
			feature.removePropertyFeatureChangeListener(propertyChangeListener);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener#propertyChanged(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent)
	 */
	public void propertyChanged(IPropertyFeatureChangeEvent event) {
	}
	
	/**
	 * @return the fillBoth
	 */
	public boolean isFillBoth() {
		return fillBoth;
	}

	/**
	 * @param fillBoth the fillBoth to set
	 */
	public void setFillBoth(boolean fillBoth) {
		this.fillBoth = fillBoth;
	}


}
