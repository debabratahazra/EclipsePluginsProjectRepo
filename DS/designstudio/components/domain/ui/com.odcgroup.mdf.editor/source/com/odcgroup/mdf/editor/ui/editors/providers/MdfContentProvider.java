package com.odcgroup.mdf.editor.ui.editors.providers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;

/**
 * content provider for the MdfModel Elements
 * 
 * @author snn
 * 
 */
public class MdfContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	public MdfContentProvider() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object inputElement) {
		List<?> children = Collections.EMPTY_LIST;
		if (inputElement instanceof MdfClass) {
			children = getProperties((MdfClass) inputElement);
		} else if (inputElement instanceof MdfAssociation) {
			MdfAssociation association = (MdfAssociation) inputElement;
			if (association.getContainment() == MdfContainment.BY_VALUE) {
				children = getProperties((MdfClass) association.getType());
			}
		}
		return children.toArray();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getProperties(MdfClass klass) {
		List props = new ArrayList();
		props.addAll(klass.getProperties(true));
		/*if(((EObject)klass).eContainer() !=null) {
		   MdfDomain domain = (MdfDomain)((EObject)klass).eContainer();
	       props.addAll(ModelHelper.getReverseAssociations(domain, klass));
		}*/
		return props;
	}

	@Override
	public boolean hasChildren(Object obj) {		
		return getChildren(obj).length > 0;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

}
