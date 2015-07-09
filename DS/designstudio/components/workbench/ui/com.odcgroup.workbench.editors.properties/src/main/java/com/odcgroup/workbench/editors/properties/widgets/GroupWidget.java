package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;

/**
 *
 * @author pkk
 *
 */
public class GroupWidget extends AbstractPropertyReferenceWidget {
	
	private Composite group = null;
	private boolean fillBoth = true;
	private boolean grouped = true;
	private int columns = 2;
	private boolean equalCols = false;
	
	/**
	 * @param reference
	 * @param label
	 */
	public GroupWidget(EReference reference, String label) {
		this(reference, label, true, 2);
	}
	
	
	/**
	 * @param reference
	 * @param label
	 */
	public GroupWidget(EReference reference, String label, boolean grouped, int columns) {
		super(reference, label, null);
		this.grouped = grouped;
		this.columns = columns;
	}
	
	/**
	 * @param reference
	 * @param grouped
	 * @param columns
	 */
	public GroupWidget(EReference reference, boolean grouped, int columns) {
		this(reference, null, grouped, columns);
	}
	
	/**
	 * @param grouped
	 * @param columns
	 */
	public GroupWidget(boolean grouped, int columns) {
		this(null, null, grouped, columns);
	}

	/**
	 * @param body
	 * @param property
	 */
	public GroupWidget(EReference reference, String label, String tooltip) {
		super(reference, label, tooltip);
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		if (grouped) {
			group = SWTWidgetFactory.createGroup(body, columns, getLabel(), isFillBoth(), equalCols);
		} else {
			group = SWTWidgetFactory.createComposite(body, isFillBoth(), columns);
			if (isFillBoth()) {
				group.setLayoutData(new GridData(GridData.FILL_BOTH));
			}
		}
		for (IPropertyFeature feature : getPropertyFeatures()) {
			((ISWTWidget) feature).createPropertyControl(group);
			if (getStructuralFeature() != null)
				feature.addPropertyFeatureChangeListener(this);
		}	
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

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {
		for (IPropertyFeature feature : getPropertyFeatures()) {
			feature.initiateWidget(getFeatureInput(), getRootElement());
		}		
	}


	public void setEqualColumns(boolean value) {
		this.equalCols  = value;
	}

}
