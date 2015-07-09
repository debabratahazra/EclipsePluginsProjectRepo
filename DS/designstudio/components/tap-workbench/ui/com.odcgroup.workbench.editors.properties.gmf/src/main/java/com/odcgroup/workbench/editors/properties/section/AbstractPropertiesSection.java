package com.odcgroup.workbench.editors.properties.section;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.workbench.editors.properties.model.IPropertyContainer;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.widgets.ISWTWidget;

/**
 *
 * @author pkk
 *
 */
public abstract class AbstractPropertiesSection extends AbstractBasePropertySection implements IPropertyContainer {
	
	private List<IPropertyFeature> properties = new ArrayList<IPropertyFeature>();
	private int numColumns = 1;
	private boolean useThreeFourthLayout = false;
	private Composite body = null;

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.internal.properties.AbstractBasePropertySection#createControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createControls(Composite parent) {
		configureProperties();
		body = getWidgetFactory().createComposite(parent);
		GridData data = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(data);
		if (numColumns == 1 && useThreeFourthLayout) {
			numColumns = 2;
		}
		GridLayout layout = new GridLayout(numColumns, false);
		body.setLayout(layout);
		
		if (!properties.isEmpty()) {
			createPropertyControls(body);
		}
		
		if (useThreeFourthLayout) {
			Composite filler = getWidgetFactory().createComposite(body);
			data = new GridData();
			data.grabExcessHorizontalSpace = false;
			data.widthHint = 150;
			filler.setLayoutData(data);
		}
		
	}
	
	/**
	 * 
	 */
	protected abstract void configureProperties();

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyContainer#getPropertyFeatures()
	 */
	public List<IPropertyFeature> getPropertyFeatures() {
		return properties;
	}
	
	/**
	 * @param body
	 */
	protected void createPropertyControls(Composite body) {
		for (IPropertyFeature property : properties) {
			((ISWTWidget) property).createPropertyControl(body);
			property.addPropertyFeatureChangeListener(this);
		}
	}
	
	/**
	 * @param propertyItem
	 */
	public void addPropertyFeature(IPropertyFeature propertyFeature) {
		if (propertyFeature != null) {
			properties.add(propertyFeature);
		}
	}

	/**
	 * @param numColumns the numColumns to set
	 */
	public void setNumColumns(int numColumns) {
		if (numColumns > 0)
			this.numColumns = numColumns;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.gmf.editors.internal.properties.AbstractBasePropertySection#initiateControls()
	 */
	@Override
	protected void initiateControls() {
		if (getEObject() != null) {
			for (IPropertyFeature widget : properties) {
				widget.initiateWidget(getEObject(), getEObject());
			}
			if (getPart() instanceof IEditorPart) {
				IEditorInput editInput = ((IEditorPart)getPart()).getEditorInput();
				if (editInput instanceof FileEditorInput) {
					body.setEnabled(true);
				} else {
					body.setEnabled(false);
				}
			}
		}		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeListener#propertyChanged(com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeEvent)
	 */
	public void propertyChanged(IPropertyFeatureChangeEvent event) {
		Object val = event.getValue();
		EStructuralFeature feature = event.getPropertyFeature().getStructuralFeature();
		ICommand cmd = null;
		if (!event.isValueFromClipboard()) {
			cmd = CommandUtil.create(getEditingDomain(), getEObject(), feature, val);
		} else {
			cmd = CommandUtil.createElementsFromStringCommand(getEditingDomain(), getEObject(), feature, (String) val);
		}
		if (cmd != null) {
			executeChanges(cmd);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}	

	/**
	 * @return the useThreeFourthLayout
	 */
	public boolean isUseThreeFourthLayout() {
		return useThreeFourthLayout;
	}

	/**
	 * @param useThreeFourthLayout the useThreeFourthLayout to set
	 */
	public void setUseThreeFourthLayout(boolean useThreeFourthLayout) {
		this.useThreeFourthLayout = useThreeFourthLayout;
	}


}
