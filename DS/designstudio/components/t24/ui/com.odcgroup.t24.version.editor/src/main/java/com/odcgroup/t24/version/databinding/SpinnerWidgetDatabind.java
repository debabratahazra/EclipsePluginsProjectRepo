package com.odcgroup.t24.version.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;

import com.odcgroup.t24.version.editor.ui.providers.VersionEditorChangeListener;

public class SpinnerWidgetDatabind extends WidgetDataBinding {

    private SpinnerUpdateValueStrategy spinnerStrategy= new SpinnerUpdateValueStrategy();
    public SpinnerWidgetDatabind(DataBindingContext context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected IObservable widgetObservable() {
	IObservableValue observeSelectionSpinne = WidgetProperties.selection().observe(getWidget());
	observeSelectionSpinne.addChangeListener(VersionEditorChangeListener.getInstance());
	return observeSelectionSpinne;
	
    }

    @Override
    protected IObservable featureObservable() {
	IObservableValue featureObserveValue = EMFObservables.observeValue(getModelObject(), getStructualFeature());
	return featureObserveValue;
    }

    @Override
    protected Object targetToModelUpdateStrategy() {
	return spinnerStrategy ;
    }

}
