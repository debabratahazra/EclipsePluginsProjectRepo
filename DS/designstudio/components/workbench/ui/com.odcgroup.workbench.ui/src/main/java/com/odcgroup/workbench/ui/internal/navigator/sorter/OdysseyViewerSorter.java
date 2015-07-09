package com.odcgroup.workbench.ui.internal.navigator.sorter;

import java.text.Collator;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class OdysseyViewerSorter extends ViewerSorter {

	public OdysseyViewerSorter() {
	}

	public OdysseyViewerSorter(Collator collator) {
		super(collator);
	}

	/** This is the same implementation as of the parent class, it only
	 *  has a special handling for decorated texts in order to fix DS-2132
	 */
	@SuppressWarnings("unchecked")
	@Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        int cat1 = category(e1);
        int cat2 = category(e2);

        if (cat1 != cat2) {
			return cat1 - cat2;
		}
    	
        String name1;
        String name2;

        if (viewer == null || !(viewer instanceof ContentViewer)) {
            name1 = e1.toString();
            name2 = e2.toString();
        } else {
            IBaseLabelProvider prov = ((ContentViewer) viewer)
                    .getLabelProvider();
            if (prov instanceof ILabelProvider) {
            	
                ILabelProvider lprov = (ILabelProvider) prov;
                name1 = lprov.getText(e1);
                name2 = lprov.getText(e2);
            } else {
                name1 = e1.toString();
                name2 = e2.toString();
            }
        }
        if (name1 == null) {
			name1 = "";//$NON-NLS-1$
		}
        if (name2 == null) {
			name2 = "";//$NON-NLS-1$
		}

        // START fix for DS-2132
        if (name1.startsWith("> ")) name1 = name1.substring(2);
        if (name2.startsWith("> ")) name2 = name2.substring(2);
        // END fix for DS-2132
        
        // use the comparator to compare the strings
        return getComparator().compare(name1, name2);
	}
}
