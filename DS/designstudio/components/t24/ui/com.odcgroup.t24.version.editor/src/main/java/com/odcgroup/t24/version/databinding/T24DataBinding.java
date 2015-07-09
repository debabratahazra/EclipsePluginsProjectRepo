package com.odcgroup.t24.version.databinding;



import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Widget;
/**
 * DataBinding class to provide the binding to the widgets. 
 * @author snn
 *
 */
public class T24DataBinding {
 
    private static DataBindingContext bindingContext =  new DataBindingContext();
    private static T24DataBinding t4Databinding;
    
    public static T24DataBinding getInstance() {
	if(t4Databinding==null){
	    t4Databinding = new T24DataBinding() ;
	}
	return t4Databinding;
    }
   
    public Binding bindTextWidget(Widget widget , EObject modelObject, EStructuralFeature eStructuralFeature ,UpdateValueStrategy targetToModel,UpdateValueStrategy modelTotarget) {
	IObservableValue observeTextWidget = WidgetProperties.text(SWT.Modify).observe(widget);
	IObservableValue observeValue = EMFObservables.observeValue(modelObject, eStructuralFeature);
	return bindingContext.bindValue(observeTextWidget,observeValue,targetToModel , modelTotarget);
	
	
    }
   
    public Binding bindSpinnerWidgte(Widget widget , EObject modelObject, EStructuralFeature eStructuralFeature ,UpdateValueStrategy modelTotarget) {
	IObservableValue observeSpinnerWidget = WidgetProperties.selection().observe(widget);
	IObservableValue observeValue = EMFObservables.observeValue(modelObject, eStructuralFeature);
	return bindingContext.bindValue(observeSpinnerWidget,observeValue,new SpinnerUpdateValueStrategy() , modelTotarget);
	
    }
    
    public void bindListWidget(Widget widget , EObject modelObject, EStructuralFeature eStructuralFeature ,UpdateListStrategy targetToModel,UpdateListStrategy modelTotarget){
	IObservableList observeListWidget = WidgetProperties.items().observe(widget);
	IObservableList observeListValue = EMFObservables.observeList(modelObject, eStructuralFeature);
	bindingContext.bindList(observeListWidget,observeListValue,targetToModel , modelTotarget);
	
    }
    
    public void bindTableWidget() {
	
    }
    
    public  UpdateValueStrategy getUpdateValueStrategy(){
        UpdateValueStrategy targetToModel = new UpdateValueStrategy(); 
        
	return targetToModel;
   }

   public static DataBindingContext getDataBindingContext() {
        return bindingContext ;
   }
  
   
}
