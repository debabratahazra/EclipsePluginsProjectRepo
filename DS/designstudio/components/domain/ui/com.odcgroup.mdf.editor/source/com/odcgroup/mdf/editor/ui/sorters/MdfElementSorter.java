package com.odcgroup.mdf.editor.ui.sorters;

import java.text.Collator;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfReverseAssociationWrapper;

/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfElementSorter extends ViewerSorter {
	/**
     * Constructor for MdfElementSorter
     */
    public MdfElementSorter() {
        super();
    }

    /**
     * Constructor for MdfElementSorter
     */
    public MdfElementSorter(Collator collator) {
        super(collator);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
     */
    public int category(Object element) {    	
        if (element instanceof MdfModelElement) {
            if (element instanceof MdfDomain) {
                return 1;
            } else if (element instanceof MdfEntity) {
                return 2;
            } else if (element instanceof MdfAttribute) {
                MdfAttribute attr = (MdfAttribute) element;
                return (attr.isPrimaryKey()) ? 3 : 4;
            } else if (element instanceof MdfDatasetProperty) {
                MdfEntity type = null;
                if (element instanceof MdfDatasetMappedProperty) {
                	type = ((MdfDatasetMappedProperty) element).getType();
                } else if (element instanceof MdfDatasetDerivedProperty) {
                	type = ((MdfDatasetDerivedProperty) element).getType();
                }
                return (type instanceof MdfPrimitive ? 10 : 11);
            } else if (element instanceof MdfEnumValue) {
                return 50;
            } else if (element instanceof MdfAssociation 
            		|| element instanceof MdfReverseAssociationWrapper){
                return 10;
            } else {
            	return 100;
            }
        } else {
            return 0;
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public int compare(Viewer viewer, Object e1, Object e2) {
    	IBaseLabelProvider prov = ((ContentViewer) viewer).getLabelProvider();
    	if(MdfPlugin.getDefault().getPreferenceStore().getBoolean(MdfPlugin.SORT_KEY)){
    		if (prov instanceof DecoratingLabelProvider) {    		
        		// get the nested label provider, as the ViewerComparator compares the decorated label
        		ILabelProvider nestedProvider = ((DecoratingLabelProvider)prov).getLabelProvider();    		
        		return getComparator().compare(getDisplayText(e1, nestedProvider), getDisplayText(e2, nestedProvider));
        	}
    	}
    	int cat1 = category(e1);
    	int cat2 = category(e2);
    	// donot compare incase of MdfEnumValue, maintain the order
    	if (category(e1) == 50 && category(e2)==50){
    		return 0;
    	}

        if (cat1 != cat2) {
    		return cat1 - cat2;
    	}
        
    	if (prov instanceof DecoratingLabelProvider) {    		
    		// get the nested label provider, as the ViewerComparator compares the decorated label
    		ILabelProvider nestedProvider = ((DecoratingLabelProvider)prov).getLabelProvider();    		
    		return getComparator().compare(getDisplayText(e1, nestedProvider), getDisplayText(e2, nestedProvider));
    	} else {
        	return super.compare(viewer, e1, e2);
    	}
    }
    
	/**
     * @param obj
     * @param provider
     * @return
     */
    public String getDisplayText(Object obj, ILabelProvider provider) {
    	if (obj instanceof MdfReverseAssociationWrapper) {
    		return ((MdfReverseAssociationWrapper)obj).getName();
    	} else {
    		return provider.getText(obj);
    	}
    }
}
