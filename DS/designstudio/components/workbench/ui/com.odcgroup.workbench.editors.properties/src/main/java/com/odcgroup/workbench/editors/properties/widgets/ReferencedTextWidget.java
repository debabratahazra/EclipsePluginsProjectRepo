package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;

/**
 *
 * @author pkk
 *
 */
public class ReferencedTextWidget extends AbstractPropertyReferenceWidget {
	
	private EAttribute attribute;
	private SimpleTextWidget textWidget;
	private boolean fillHorizontal = false;

	/**
	 * @param reference
	 * @param label
	 * @param tooltip
	 */
	public ReferencedTextWidget(EReference reference, EAttribute attribute, String label, String tooltip) {
		super(reference, label, tooltip);
		this.attribute = attribute;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		textWidget = new SimpleTextWidget(attribute, getLabel(), getTooltip());
		textWidget.setFillHorizontal(fillHorizontal);
		textWidget.createPropertyControl(body);	
		textWidget.addPropertyFeatureChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {		
		textWidget.initiateWidget(getFeatureInput(), getRootElement());		
	}
	
	/**
	 * @param fillHorizontal the fillHorizontal to set
	 */
	public void setFillHorizontal(boolean fillHorizontal) {
		this.fillHorizontal = fillHorizontal;
	}
	
}
