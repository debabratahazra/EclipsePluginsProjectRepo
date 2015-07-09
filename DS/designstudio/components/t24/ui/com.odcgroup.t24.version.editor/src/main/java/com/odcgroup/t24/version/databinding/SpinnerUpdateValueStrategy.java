package com.odcgroup.t24.version.databinding;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;

public class SpinnerUpdateValueStrategy extends UpdateValueStrategy {

    protected IStatus doSet(IObservableValue observableValue, Object value) {
    	int primitiveValue = 0;
		if(value != null) {
			primitiveValue = ((Integer) value).intValue();
		}
		return super.doSet(observableValue, primitiveValue);
    }

   

}
