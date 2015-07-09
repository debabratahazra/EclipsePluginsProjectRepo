package com.odcgroup.workbench.editors.properties.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
public class RadioGroupWidget extends AbstractPropertyWidget implements SelectionListener {
	
	List<PropertyRadio> radios = new ArrayList<PropertyRadio>();
	
	private Composite group = null;
	protected List<Button> radioButtons = new ArrayList<Button>();
	private boolean grouped = true;

	private int columns = 2;

	private boolean fillHorizontal = false;
	
	protected static final String KEY = "SEL_VAL";

	/**
	 * @param body
	 * @param property
	 */
	public RadioGroupWidget(EStructuralFeature feature, String label, String tooltip) {
		super(feature, label, tooltip);
	}
	
	/**
	 * @param feature
	 * @param label
	 * @param grouped
	 */
	public RadioGroupWidget(EStructuralFeature feature, String label, boolean grouped) {
		this(feature, label, null);
		this.grouped = grouped;
	}
	
	/**
	 * @param feature
	 * @param label
	 * @param grouped
	 */
	public RadioGroupWidget(EStructuralFeature feature, String label, boolean grouped, int columns, boolean fillHorizontal) {
		this(feature, label, null);
		this.grouped = grouped;
		this.columns = columns;
		this.fillHorizontal  = fillHorizontal;
	}
	
	/**
	 * @param feature
	 */
	public RadioGroupWidget(EStructuralFeature feature, boolean grouped) {
		this(feature, null, grouped);
	}
	
	/**
	 * @param feature
	 */
	public RadioGroupWidget(EStructuralFeature feature) {
		this(feature, true);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		if (grouped) {
			if(columns != 2) {
				group = SWTWidgetFactory.createGroup(body, getLabel(), false ,columns);
			} else {
				group = SWTWidgetFactory.createGroup(body, getLabel(), false);
			}
		} else {
			if (getLabel() != null) {
				SWTWidgetFactory.createSimpleLabel(body, "");
				group = SWTWidgetFactory.createComposite(body, false);
			} else {
				group = SWTWidgetFactory.createComposite(body, false);
			}
		}
		
		GridData gd = new GridData();
		if (!grouped && getLabel() == null) {
			gd.horizontalSpan = 2;
		}
		
		if(fillHorizontal) {
			gd.horizontalAlignment = SWT.FILL;
		}
		group.setLayoutData(gd);
		
		Button radioBtn = null;
		for (PropertyRadio radio : getRadios()) {
			radioBtn = new Button(group, SWT.RADIO);
			radioBtn.setText(radio.getLabel());
			radioBtn.setData(KEY, radio.getSelectionValue());
			radioBtn.addSelectionListener(this);
			radioBtn.setBackground(group.getBackground());
			radioButtons.add(radioBtn);
		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {
		Object data = getElement().eGet(getStructuralFeature());			
		if (data != null) {
			boolean found = false;
			if (data instanceof Enumerator) {
				for(Button btn : radioButtons) {
					btn.setEnabled(true);
					if (data.equals(btn.getData(KEY))) {
						found = true;
						btn.setSelection(true);
					}
				}
			} else if (data instanceof Boolean) {
				for(Button btn : radioButtons) {
					btn.setEnabled(true);
					if (data.equals(btn.getData(KEY))) {
						found = true;
						btn.setSelection(true);
					}
				}
			}
			if (!found){
				for(Button btn : radioButtons) {
					btn.setSelection(false);
					btn.setEnabled(false);
				}
			}
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		Button radio = (Button)e.widget;
		if (radio.getSelection()) {
			Object selection = radio.getData(KEY);
			notifyPropertyFeatureChange(selection);
		}
		
	}
	
	/**
	 * @param label
	 * @param value
	 */
	public void addRadio(String label, Object value) {
		radios.add(new PropertyRadio(label, value));
	}
	
	/**
	 * @return
	 */
	public List<PropertyRadio> getRadios() {
		return radios;
	}
	
	public class PropertyRadio {
		
		String label;
		Object selectionValue;

		public PropertyRadio(String label, Object selectionValue) {
			this.label = label;
			this.selectionValue = selectionValue;
		}
		
		/**
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * @return the selectionValue
		 */
		public Object getSelectionValue() {
			return selectionValue;
		}
		
		
	}

}
