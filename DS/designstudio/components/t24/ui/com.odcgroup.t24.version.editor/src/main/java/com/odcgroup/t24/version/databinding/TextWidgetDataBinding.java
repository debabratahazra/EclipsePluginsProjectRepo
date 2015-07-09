package com.odcgroup.t24.version.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;

public class TextWidgetDataBinding extends WidgetDataBinding {

    public TextWidgetDataBinding(DataBindingContext context){
	super(context);
    }

    @Override
    protected IObservable widgetObservable() {
	IObservableValue observableValue =WidgetProperties.text(SWT.Modify).observe(getWidget());
	return observableValue;
    }

    @Override
    protected IObservable featureObservable() {
	IObservableValue featureObserValue =EMFProperties.value(getStructualFeature()).observe(getModelObject());
	return featureObserValue;
    }

}
