package com.odcgroup.t24.enquiry.properties.descriptors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 *
 * @author satishnangi
 *
 */
public class AbstractPropertyDescriptorWrapper implements IPropertyDescriptor {
	
	private IPropertyDescriptor descriptor ;
	public AbstractPropertyDescriptorWrapper(IPropertyDescriptor descriptor){
		this.descriptor = descriptor;
	}
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		
		return descriptor.createPropertyEditor(parent);
	}

	@Override
	public String getCategory() {
		return descriptor.getCategory();
	}

	@Override
	public String getDescription() {
		return descriptor.getDescription();
	}

	@Override
	public String getDisplayName() {
		return descriptor.getDisplayName();
	}

	@Override
	public String[] getFilterFlags() {
		return descriptor.getFilterFlags();
	}

	@Override
	public Object getHelpContextIds() {
		return descriptor.getHelpContextIds();
	}

	@Override
	public Object getId() {
		return descriptor.getId();
	}

	@Override
	public ILabelProvider getLabelProvider() {
		return descriptor.getLabelProvider();
	}

	@Override
	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
		return descriptor.isCompatibleWith(anotherProperty);
	}

}
