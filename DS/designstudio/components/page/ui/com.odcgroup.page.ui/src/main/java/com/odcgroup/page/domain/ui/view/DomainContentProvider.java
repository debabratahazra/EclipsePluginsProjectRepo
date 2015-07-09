package com.odcgroup.page.domain.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfName;

/**
 * @author atr
 */
public class DomainContentProvider implements ITreeContentProvider {

	/**  */
	private DomainRepository repository;
	
	private boolean acceptChildrenOfDatasetMappedProperty = false;
	
	/**
	 * @param repository
	 */
	public DomainContentProvider(DomainRepository repository) {
		this(repository, false);
	}

	public DomainContentProvider(DomainRepository repository, boolean acceptChildrenOfDatasetMappedProperty) {
		this.repository = repository;
		this.acceptChildrenOfDatasetMappedProperty = acceptChildrenOfDatasetMappedProperty;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof MdfDataset) {
			MdfDataset dataset = (MdfDataset)parentElement;
			return dataset.getProperties().toArray();
		} else if (parentElement instanceof MdfDatasetMappedProperty) {
	        MdfDatasetMappedPropertyImpl dmp = (MdfDatasetMappedPropertyImpl) parentElement;
	        // collect only 1st level.
	        List<Object> list = new ArrayList<Object>();
	        for (Object obj : dmp.getLinkDataset().getProperties()) {
	        	if (obj instanceof MdfDatasetProperty) {
	        		if (obj instanceof MdfDatasetMappedProperty) {
	        	        MdfDatasetMappedPropertyImpl dmp2 = (MdfDatasetMappedPropertyImpl) obj;
	        	        if (dmp2.isTypeDataset()) continue;
	        		}
	        	}
	        	list.add(obj);
	        }
	        return list.toArray();
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
        if (element instanceof MdfDatasetMappedProperty) {
            if(acceptChildrenOfDatasetMappedProperty){
            	MdfDatasetMappedProperty datasetMappedProperty = (MdfDatasetMappedProperty) element;
            	return datasetMappedProperty.isTypeDataset();
            }
            return false;
        } else if (element instanceof MdfDataset) {
        	return true;
        }
        return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		Object[] elements = {};
		if (inputElement instanceof MdfName) {
			MdfDataset dataset = repository.getDataset((MdfName)inputElement);
			if (dataset != null) {
				elements = new Object[]{dataset};
			} 
		}
		return elements;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		repository = null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
	public void setAcceptChildrenOfDatasetMappedProperty(boolean acceptChildrenOfDatasetMappedProperty) {
		this.acceptChildrenOfDatasetMappedProperty = acceptChildrenOfDatasetMappedProperty;
	}

}
