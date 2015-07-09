package com.odcgroup.mdf.editor.ui.editors.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;

public class MdfAnnotationPropertyContentProvider implements IStructuredContentProvider {


    @Override
    public Object[] getElements(Object inputElement) {
	List<MdfAnnotationProperty> children = new ArrayList<MdfAnnotationProperty>();
	if(inputElement instanceof List<?>) {
	     List<?> annotationList =(List<?>)inputElement;
	    for(Object input :annotationList){
		if(input instanceof MdfAnnotation){
		List<MdfAnnotationProperty> propertiesList =((MdfAnnotation)input).getProperties();   
		 children.addAll(propertiesList);
		}
	    }
	    
	}
	return children.toArray();
    }
   
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	    
    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub

    }

}
