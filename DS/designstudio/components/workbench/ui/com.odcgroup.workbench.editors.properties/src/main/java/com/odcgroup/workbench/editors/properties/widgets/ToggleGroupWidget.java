package com.odcgroup.workbench.editors.properties.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;

/**
 *
 * @author pkk
 *
 */
public abstract class ToggleGroupWidget extends AbstractPropertyWidget implements SelectionListener {

	private List<AbstractPropertyReferenceWidget> referenceWidgets = new ArrayList<AbstractPropertyReferenceWidget>();
	private int defaultIndex = 0;
	private int selectionIndex = 0;
	private Composite radioGroup = null;
	private Composite widgetBody = null;
	private List<Button> radioButtons = new ArrayList<Button>();
	private Group parent = null;
	private boolean toggled = false;
	private String label = null;

	/**
	 * @param label
	 * @param tooltip
	 */
	public ToggleGroupWidget(String label) {
		this(null, label, null);
	}
	
	/**
	 * @param label
	 * @param tooltip
	 */
	public ToggleGroupWidget(String label, String tooltip) {
		this(null, label, tooltip);
	}

	/**
	 * @param reference
	 * @param label
	 * @param tooltip
	 */
	public ToggleGroupWidget(EReference reference, String label) {
		this(reference, label, null);
	}
	
	/**
	 * @param reference
	 * @param label
	 * @param tooltip
	 */
	public ToggleGroupWidget(EReference reference, String label, String tooltip) {
		super(reference, label, tooltip);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.ISWTWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {
		parent = createParentGroup(body, 1, null);
		Composite head = SWTWidgetFactory.createComposite(parent, false);
		SWTWidgetFactory.createSimpleLabel(head, getLabel());
		radioGroup = SWTWidgetFactory.createRowComposite(head, true);
		Button radioBtn = null;
		for (AbstractPropertyReferenceWidget refWidget : referenceWidgets) {
			radioBtn = new Button(radioGroup, SWT.RADIO);
			radioBtn.setBackground(radioGroup.getBackground());
			radioBtn.setText(refWidget.getLabel());
			radioBtn.addSelectionListener(this);
			radioButtons.add(radioBtn);
		}
		widgetBody = SWTWidgetFactory.createComposite(parent, true);
		
	}
	
	public void setLabel(String label) {
		this.label  = label;
	}
	
	protected Group createParentGroup(Composite parent, int columns, String label) {
		return SWTWidgetFactory.createGroup(parent, 1, null, true);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {
		if(this.label!=null)
			this.parent.setText(this.label);
//		else {
//			this.parent.setText("Page Flow");
//		}
		if (getElement() != null && !toggled) {
			int ii = getSelection(getElement());
			if (ii != -1) {
				defaultIndex = ii;
			}else {
				defaultIndex = 0;
			}
		}

		for (Button button : radioButtons) {
			button.setSelection(false);
		}

		radioButtons.get(defaultIndex).setSelection(true);

		toggleGroup(false);
	}

	/**
	 * @param refWidget
	 */
	public void addReferenceWidget(AbstractPropertyReferenceWidget refWidget) {
		referenceWidgets.add(refWidget);
	}

	/**
	 * @param refWidget
	 * @param defaultSelected
	 */
	public void addReferenceWidget(AbstractPropertyReferenceWidget refWidget, boolean defaultSelected) {
		this.addReferenceWidget(refWidget);
		if (defaultSelected) {
			defaultIndex = referenceWidgets.indexOf(refWidget);
		}
	}

	/**
	 * @param element
	 * @return
	 */
	protected abstract int getSelection(EObject element);


	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		Button widget = (Button) e.widget;
		if(widget.getSelection()) {
			boolean reset = false;
			boolean okpressed = true;
			setSelectionIndex(getRadioButtonIndex(widget));
			if (getConfirmToggleMessage(getElement()) != null) {
				okpressed = MessageDialog.openConfirm(getShell(), "Confirm",
						getConfirmToggleMessage(getElement()));
			}
			if (okpressed) {
				setDefaultIndex(getRadioButtonIndex(widget));
				reset = true;
			} else {
				widget.setSelection(false);
			}
			toggleGroup(reset);
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#addPropertyFeatureChangeListener
	 * (com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.addPropertyFeatureChangeListener(propertyChangeListener);
		for (AbstractPropertyReferenceWidget refWidget : referenceWidgets) {
			refWidget.addPropertyFeatureChangeListener(propertyChangeListener);
		}
	}
	
	protected int getRadioButtonIndex(Button widget) {
		return radioButtons.indexOf(widget);
	}
	
	protected void setDefaultIndex(int index) {
		this.defaultIndex = index;
	}

	protected void setSelectionIndex(int index) {
		this.selectionIndex = index;
	}

	/**
	 * @return
	 */
	public int getSelectionIndex() {
		return selectionIndex;
	}

	/**
	 * @param index
	 */
	protected void toggleGroup(boolean reset) {
		if (reset) {
			toggled = true;
		}
		radioButtons.get(defaultIndex).setSelection(true);
		AbstractPropertyReferenceWidget refWidget = referenceWidgets.get(defaultIndex);
		Composite parent = widgetBody.getParent();
		widgetBody.dispose();
		widgetBody = SWTWidgetFactory.createComposite(parent, true);
		widgetBody.setLayoutData(new GridData(GridData.FILL_BOTH));
		refWidget.createPropertyControl(widgetBody);
		refWidget.initiateWidget(getElement(), getRootElement());
		if (reset) {
			for (AbstractPropertyReferenceWidget rWidget : referenceWidgets) {
				rWidget.notifyPropertyFeatureChange(null);
			}
		}
		parent.layout(true);
	}

	/**
	 * @return the confirmToggleMessage
	 */
	public abstract String getConfirmToggleMessage(EObject element);


}
