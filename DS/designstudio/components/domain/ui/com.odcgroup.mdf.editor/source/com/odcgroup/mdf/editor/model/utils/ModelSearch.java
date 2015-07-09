package com.odcgroup.mdf.editor.model.utils;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;

public class ModelSearch {

	private MdfDomain domain;
	private MdfModelElement result;
	
	public ModelSearch(MdfDomain domain) {
		this.domain = domain;
	}
	
	/**
	 * Search corresponding {@link MdfModelElement} in the {@link MdfDomain}.
	 * 
	 * @param element 
	 * @return the corresponding element, null if not found
	 */
	public MdfModelElement search(final MdfModelElement element) {
		
		ModelVisitor mv = new ModelVisitor() {
            public boolean accept(MdfModelElement candidate) {
                if (candidate.getQualifiedName().equals(element.getQualifiedName())) {
                    result = candidate;
                    return false;
                }
                return true;
            }
        };
        
        ModelWalker mw = new ModelWalker(mv);
        mw.visit(domain);
        return result;
	}
}
