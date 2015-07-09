package com.odcgroup.t24.mdf.editor.ui.attribute.sorter;

import java.text.Collator;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.sorters.MdfElementSorter;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;

public class T24MdfElementSorter extends MdfElementSorter {

	public T24MdfElementSorter() {
	}

	public T24MdfElementSorter(Collator collator) {
		super(collator);
	}

	@SuppressWarnings("unchecked")
	public int compare(Viewer viewer, Object e1, Object e2) {
		if(!MdfPlugin.getDefault().getPreferenceStore().getBoolean(MdfPlugin.SORT_KEY) && (e1 instanceof MdfAttribute || e2 instanceof MdfAttribute)) {
			return 0;
		}
		if((e1 instanceof MdfAttribute || e2 instanceof MdfAttribute)) {
			if (e1 instanceof MdfClass) {
				return -1;
			} else if (e2 instanceof MdfClass) {
				return 1;
			}
		IBaseLabelProvider prov = ((ContentViewer) viewer).getLabelProvider();
    		if (prov instanceof DecoratingLabelProvider) {    		
        		ILabelProvider nestedProvider = ((DecoratingLabelProvider)prov).getLabelProvider();    		
        		return getComparator().compare(getDisplayText(e1, nestedProvider), getDisplayText(e2, nestedProvider));
        	}
    	}
		return super.compare(viewer, e1, e2);
	}
}
