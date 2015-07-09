package com.odcgroup.t24.version.editor.ui.providers;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;

import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.impl.DefaultImpl;

public class DefaultTabTextCellEditingSupport extends CellEditingSupport {

    private DataBindingContext bindingContext=null;
    private UpdateValueStrategy targetModelStrategy=new UpdateValueStrategy();
    
    public DefaultTabTextCellEditingSupport(ColumnViewer viewer,
	    DataBindingContext dbc, CellEditor editor ,VersionDesignerEditor versionEditor) {
	super(viewer, dbc, editor,versionEditor);
	this.bindingContext =dbc ;

    }

    @Override
    protected IObservableValue doCreateElementObservable(final Object element,final ViewerCell cell) {
	int index = cell.getColumnIndex();
	ValueOrAtRoutine valueOrroutine = null;
	IObservableValue emfObserValue =null;
	if(element instanceof DefaultImpl){
	    valueOrroutine =((DefaultImpl)element).getDefaultNewValueOrAtRoutine();
	}
	//get the observablevalue for the sv,mv ,new value ,old value 
	final ValueOrAtRoutine editedValueOrRoutine =valueOrroutine ;
	switch(index){
	case 0:emfObserValue = EMFEditObservables.observeValue(getEditor().getEditingDomain(),(EObject)element,Literals.DEFAULT__MV );
	break;
	case 1: emfObserValue=EMFEditObservables.observeValue(getEditor().getEditingDomain(),(EObject)element,Literals.DEFAULT__SV );
	break;
	case 2: emfObserValue=EMFEditObservables.observeValue(getEditor().getEditingDomain(),(EObject)element,Literals.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS );
	break;
	case 3: emfObserValue= EMFEditObservables.observeValue(getEditor().getEditingDomain(),(EObject)valueOrroutine,Literals.VALUE_OR_AT_ROUTINE__VALUE );
	break;
	}
	emfObserValue.addValueChangeListener(new IValueChangeListener() {
	    @Override
	    public void handleValueChange(ValueChangeEvent event) {
		if( cell.getColumnIndex()==3){
		    editedValueOrRoutine.setAtRoutine(null);
		    ((DefaultImpl)element).setDefaultNewValueOrAtRoutine(editedValueOrRoutine);
		    String newValue =(String)event.diff.getNewValue();
		    cell.setText(newValue);
		    getViewer().update(element,new String[]{"New Value"});
		}
	    }
	});
	return emfObserValue;
    }

    @Override
    protected Binding createBinding(IObservableValue target ,IObservableValue model) {
	if(!((EAttributeImpl)model.getValueType()).getEAttributeType().getInstanceClassName().equals("java.lang.String")){
	    targetModelStrategy.setConverter(StringToNumberConverter.toInteger(true));
	}
	return bindingContext.bindValue(target,model,targetModelStrategy,null);
    }
    
    @Override
    protected IObservableValue doCreateCellEditorObservable( final CellEditor cellEditor) {
	IObservableValue value=super.doCreateCellEditorObservable(cellEditor);
	return value;
    }
    
}
