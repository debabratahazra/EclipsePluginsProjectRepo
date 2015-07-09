package com.odcgroup.t24.version.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;

public class ListWidgetDataBinding extends WidgetDataBinding {

    public ListWidgetDataBinding(DataBindingContext context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected IObservable widgetObservable() {
	IObservableList observableList = WidgetProperties.items().observe(getWidget());
	return observableList;
    }

    @Override
    protected IObservable featureObservable() {
	IObservableList featureList = EMFProperties.list(getStructualFeature()).observe(getModelObject());
	return featureList;
    }

}
