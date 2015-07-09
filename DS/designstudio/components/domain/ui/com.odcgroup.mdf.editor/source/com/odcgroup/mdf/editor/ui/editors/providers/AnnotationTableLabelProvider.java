package com.odcgroup.mdf.editor.ui.editors.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;

public class AnnotationTableLabelProvider implements ITableLabelProvider {

    

    @Override
    public void addListener(ILabelProviderListener listener) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
	if(element instanceof MdfAnnotationProperty){
	    if( columnIndex ==0){
	    return ((MdfAnnotationProperty)element).getName();
	    }else if( columnIndex ==1){
		  return ((MdfAnnotationProperty)element).getValue();
	    }
	}
	
	return null;
    }

}
