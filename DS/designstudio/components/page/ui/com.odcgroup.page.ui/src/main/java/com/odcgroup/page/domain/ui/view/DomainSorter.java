package com.odcgroup.page.domain.ui.view;

import org.eclipse.jface.viewers.ViewerSorter;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

/**
 * @author atr
 */
public class DomainSorter extends ViewerSorter {
	
    /**
     * @param element 
     * @return int
     */
	public int category(Object element) {
        MdfEntity type = ((MdfDatasetProperty)element).getType();
        return (type instanceof MdfPrimitive ? 10 : 20);
	}	

}
