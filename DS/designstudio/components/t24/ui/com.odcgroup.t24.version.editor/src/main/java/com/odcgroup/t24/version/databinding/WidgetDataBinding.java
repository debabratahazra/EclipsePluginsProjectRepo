package com.odcgroup.t24.version.databinding;

import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Widget;

public abstract class WidgetDataBinding {
    private Widget widget;
    private EObject modelObject;
    private EStructuralFeature structualFeature;
    private IChangeListener changeListenr;
    private static DataBindingContext context ; 

    public WidgetDataBinding(DataBindingContext context) {
	this.context =context;
    }
    protected abstract IObservable widgetObservable();
    protected abstract IObservable featureObservable();
    protected  Object targetToModelUpdateStrategy() {
	return null;
    }
    protected  Object modelToTagetUpdateStrategy(){
	return null;
    }

    //public void  
    public void bindObservables(){
	if(widgetObservable() instanceof List ) {
	    context.bindList((IObservableList)widgetObservable(),(IObservableList) featureObservable(),(UpdateListStrategy)targetToModelUpdateStrategy() ,(UpdateListStrategy) modelToTagetUpdateStrategy());
	} else if (widgetObservable() instanceof Set ){
	    context.bindSet((IObservableSet)widgetObservable(),(IObservableSet) featureObservable(),(UpdateSetStrategy)targetToModelUpdateStrategy() ,(UpdateSetStrategy) modelToTagetUpdateStrategy());
	    
	} else {
	    context.bindValue((IObservableValue)widgetObservable(),(IObservableValue) featureObservable(),(UpdateValueStrategy)targetToModelUpdateStrategy() ,(UpdateValueStrategy) modelToTagetUpdateStrategy());
	}
	
    }
    public Widget getWidget() {
        return widget;
    }
   
    public EObject getModelObject() {
        return modelObject;
    }
    
    public EStructuralFeature getStructualFeature() {
        return structualFeature;
    }
    
    public void setWidget(Widget widget) {
       this.widget = widget;
    }
   
    public void setModelObject(EObject modelobject) {
       this.modelObject = modelobject;
    }
    
    public void setStructualFeature(EStructuralFeature feature) {
        structualFeature=feature;
    }
    
   public void setChangeListner(IChangeListener listner){
       changeListenr= listner;
   }
   public IChangeListener getChangeListner(IChangeListener listner){
       return changeListenr;
   }
}
