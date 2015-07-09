package com.odcgroup.mdf.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class ModelVisitorsList implements ModelVisitor {
	private List<ModelVisitor> visitors = new ArrayList();

	/**
	 * Constructor for ModelVisitorsList
	 */
	public ModelVisitorsList() {
		super();
	}

	public void add(ModelVisitor visitor) {
		visitors.add(visitor);		
	}

	/**
	 * @see com.odcgroup.mdf.editor.model.ModelVisitor#accept(com.odcgroup.mdf.metamodel.MdfModelElement)
	 */
	public boolean accept(MdfModelElement model) {
		Iterator it = visitors.iterator();
		while (it.hasNext()) {
			ModelVisitor visitor = (ModelVisitor) it.next();
			if (!visitor.accept(model)) {
				it.remove();
			}
		}
		
		return !visitors.isEmpty();
	}
	
	/**
	 * @return
	 */
	public List<ModelVisitor> getModelVisitors() {
		return visitors;
	}

}
