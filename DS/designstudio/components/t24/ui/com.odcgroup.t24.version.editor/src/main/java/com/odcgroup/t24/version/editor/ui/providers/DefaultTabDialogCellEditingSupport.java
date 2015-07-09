package com.odcgroup.t24.version.editor.ui.providers;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.masterdetail.MasterDetailObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.RoutineDialogCellEditor;
import com.odcgroup.t24.version.versionDSL.AtRoutine;
import com.odcgroup.t24.version.versionDSL.JBCRoutine;
import com.odcgroup.t24.version.versionDSL.JavaRoutine;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.impl.DefaultImpl;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;
/**
 * Default Table Dialog cell editing support.
 * @author snn
 *
 */
public class DefaultTabDialogCellEditingSupport extends CellEditingSupport {

    private DataBindingContext bindingContext;
    
    public DefaultTabDialogCellEditingSupport(ColumnViewer viewer,DataBindingContext dbc, CellEditor editor ,VersionDesignerEditor versionEditor) {
	super(viewer, dbc, editor ,versionEditor);
	bindingContext =dbc ;
    }
    
    @Override
    protected IObservableValue doCreateElementObservable(final Object element,final ViewerCell cell) {
	ValueOrAtRoutine valueOrroutine = null;
	AtRoutine routine =null;
	if(element instanceof DefaultImpl){
	    valueOrroutine =((DefaultImpl)element).getDefaultNewValueOrAtRoutine();
	    routine = valueOrroutine.getAtRoutine();
	}
	//routine is null create a routine.
	if(routine ==null){
	    routine =VersionDSLFactoryImpl.eINSTANCE.createRoutine();
	}
	WritableValue viewerObservable = new WritableValue(routine,null);
	IObservableFactory elementSetFactory = new IObservableFactory() {
	    public IObservable createObservable(Object target) {
		return EMFEditProperties.value(getEditor().getEditingDomain(), Literals.ROUTINE__NAME).observe(target);
	    }
	};
	final IObservableValue routineNamevalueobservable = MasterDetailObservables.detailValue(viewerObservable,elementSetFactory, null);
	routineNamevalueobservable.addValueChangeListener(new  IValueChangeListener() {
	    @Override
	    public void handleValueChange(ValueChangeEvent event) {
		ValueOrAtRoutine valueOrroutine =((DefaultImpl)element).getDefaultNewValueOrAtRoutine();
		AtRoutine routine = valueOrroutine.getAtRoutine();
		String newRoutineName=(String)event.diff.getNewValue();
		if(newRoutineName.endsWith("java")){
		    routine = VersionDSLFactoryImpl.eINSTANCE.createJavaRoutine();
		    ((JavaRoutine)routine).setName(newRoutineName.split(".java")[0]);
		}else {
		    routine = VersionDSLFactoryImpl.eINSTANCE.createJBCRoutine();
		    ((JBCRoutine)routine).setName(newRoutineName.split(".b")[0]);
		}
		cell.setText(newRoutineName);
		valueOrroutine.setValue(null);
		valueOrroutine.setAtRoutine(routine);
		((DefaultImpl)element).setDefaultNewValueOrAtRoutine(valueOrroutine);
		getViewer().update(element,new String[]{"Routines"});
	    }
	});
	return routineNamevalueobservable;
    }
    @Override
    protected Binding createBinding(IObservableValue target,IObservableValue model) {
	UpdateValueStrategy valueStrategy = new UpdateValueStrategy();
	bindingContext.bindValue(target,model,valueStrategy ,null);
	return bindingContext.bindValue(target,model);

    }
    @Override
    protected IObservableValue doCreateCellEditorObservable(CellEditor cellEditor) {
	IObservableValue value = null;
	if (cellEditor instanceof RoutineDialogCellEditor) {
	    Control[] childern=((Composite)cellEditor.getControl()).getChildren();
	    value = SWTObservables.observeText(childern[0],SWT.Modify);
	}
	return value;
    }
}
