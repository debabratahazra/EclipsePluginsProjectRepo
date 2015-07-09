package com.odcgroup.workbench.editors.properties.widgets;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget;

/**
 *
 * @author pkk
 *
 */
public class CheckboxPropertyWidget extends AbstractPropertyWidget {
	
	protected Button checkBox;	
	private Object selectionVal;
	private Object defaultVal;
	private CLabel label;
	private boolean fillBoth = true;
	private boolean trail = false;
	
	private boolean isEnum = false;

	/**
	 * @param feature
	 * @param label
	 * @param selectionVal
	 */
	public CheckboxPropertyWidget(EStructuralFeature feature, String label, Object selectionVal) {
		this(feature, label, null, selectionVal);
	}
	
	
	public CheckboxPropertyWidget(EStructuralFeature feature, String label, boolean isEnum,  Object selectionVal, Object defaultVal) {
		this(feature, label, null, selectionVal);
		this.isEnum = isEnum;
		this.defaultVal = defaultVal;
	}
	
	public CheckboxPropertyWidget(boolean trail, EStructuralFeature feature, String label, Object selectionVal) {
		this(feature, label, null, selectionVal);
		this.trail  = trail;
	}
	
	public CheckboxPropertyWidget(EStructuralFeature feature, String label, Object selectionVal, boolean fillBoth) {
		this(feature, label, null, selectionVal);
		this.fillBoth = fillBoth;
	}

	/**
	 * @param body
	 * @param property
	 */
	public CheckboxPropertyWidget(EStructuralFeature feature, String label, String tooltip, Object selectionVal) {
		super(feature, label, tooltip);
		this.selectionVal = selectionVal;
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		
		if(trail) {
			checkBox = new Button(body, SWT.CHECK | SWT.COLOR_WIDGET_DARK_SHADOW);
			checkBox.setBackground(body.getBackground());
			label=SWTWidgetFactory.createSimpleLabel(body, getLabel());
		}
		else {
		// create label
		label=SWTWidgetFactory.createSimpleLabel(body, getLabel());
		checkBox = new Button(body, SWT.CHECK | SWT.COLOR_WIDGET_DARK_SHADOW);
		checkBox.setBackground(body.getBackground());
		}
		if(!fillBoth) {
			GridData gd = new GridData();
			gd.grabExcessHorizontalSpace = false;
			gd.grabExcessVerticalSpace = false;
			body.setLayoutData(gd);
		}
		
		checkBox.addSelectionListener(new SelectionAdapter(){

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object val = null;
				if(checkBox.getSelection()) {
					val = getSelectionVal();
				}
				if (val == null) {
					if (isEnum && defaultVal != null) {
						val = defaultVal;
					} else {
						val = Boolean.FALSE;
					}
				}
				notifyPropertyFeatureChange(val);
			}
			
		});
		
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyAttributeWidget#initPropertyControls()
	 */
	@Override
	public void initPropertyControls() {
		if (getElement() != null) {
			Object data = getElement().eGet(getStructuralFeature());			
			if (data.equals(getSelectionVal())) {
				checkBox.setSelection(true);
			} else {
				checkBox.setSelection(false);
			}
		}		
	}	
	
	/**
	 * @return the checkBox
	 */
	public Button getCheckBox() {
		return checkBox;
	}
	
	/**
	 * @return the selectionVal
	 */
	public Object getSelectionVal() {
		return selectionVal;
	}
    /**
     * enable the CheckboxPropertyWidget.
     * @param enable
     */
	public void setEnable(boolean enabled){
		checkBox.setEnabled(enabled);
		label.setEnabled(enabled);
		if(!enabled){
	     label.setForeground(label.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		}else{
			label.setForeground(label.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}
		
	}
}
