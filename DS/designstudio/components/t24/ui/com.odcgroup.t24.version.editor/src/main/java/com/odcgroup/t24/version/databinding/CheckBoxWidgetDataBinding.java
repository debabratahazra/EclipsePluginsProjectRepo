package com.odcgroup.t24.version.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;

public class CheckBoxWidgetDataBinding extends WidgetDataBinding {

    public CheckBoxWidgetDataBinding(DataBindingContext context) {
	super(context);
	
    }

    @Override
    protected IObservable widgetObservable() {
	IObservableValue value = WidgetProperties.selection().observe(getWidget());
	return value;
    }

    @Override
    protected IObservable featureObservable() {
	return EMFProperties.value(getStructualFeature()).observe(getModelObject());
	
    }

}
