package com.odcgroup.mdf.editor.ui.providers.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;


/**
 *
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfStructuredContentProvider implements
        IStructuredContentProvider {

    /**
     * Constructor for MdfStructuredContentProvider
     */
    public MdfStructuredContentProvider() {
        super();
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        Collection children = null;

        if (inputElement instanceof Object[]) {
            return (Object[]) inputElement;
        } else if (inputElement instanceof Collection) {
            children = (Collection) inputElement; 
        } else if (inputElement instanceof MdfProjectRepository) {
            children = ((MdfProjectRepository) inputElement).getProjects();
        } else if (inputElement instanceof ResourceSet) {
            children = ((ResourceSet) inputElement).getResources();
        } else if (inputElement instanceof Resource) {
            children = ((Resource) inputElement).getContents();
        } else if (inputElement instanceof MdfDomain) {
            children = new ArrayList();
            children.addAll(((MdfDomain) inputElement).getClasses());
            children.addAll(((MdfDomain) inputElement).getEnumerations());
            children.addAll(((MdfDomain) inputElement).getBusinessTypes());
            children.addAll(((MdfDomain) inputElement).getDatasets());
        } else if (inputElement instanceof MdfClass) {
        	// Filter out reverse associations
            List props = ((MdfClass) inputElement).getProperties();
            children = new ArrayList(props.size());
            Iterator it = props.iterator();
            while (it.hasNext()) {
				MdfProperty p = (MdfProperty) it.next();
				if (!(p instanceof MdfReverseAssociation)) {
					children.add(p);
				}
			}
        } else if (inputElement instanceof MdfEnumeration) {
            children = ((MdfEnumeration) inputElement).getValues();
        } else if (inputElement instanceof MdfAssociation) {
        	MdfReverseAssociation rev = ((MdfAssociation) inputElement).getReverse();
            if (rev != null) children = Collections.singletonList(rev);
        } else if (inputElement instanceof MdfDataset) {
            children = ((MdfDataset) inputElement).getProperties();
        }

        return (children == null) ? new Object[0] : children.toArray();
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
