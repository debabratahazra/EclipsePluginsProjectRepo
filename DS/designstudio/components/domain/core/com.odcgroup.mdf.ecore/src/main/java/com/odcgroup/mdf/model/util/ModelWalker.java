package com.odcgroup.mdf.model.util;

import java.util.Collection;
import java.util.Iterator;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class ModelWalker {
	private final ModelVisitor visitor;
	private boolean canceled = false;

	/**
	 * Constructor for ModelWalker
	 */
	public ModelWalker(ModelVisitor visitor) {
		super();
		this.visitor = visitor;
	}

	public void visit(MdfModelElement model) {
		if (visitor.accept(model)) {
		    visitChildren(model);
		} else {
			canceled = true;
		}
	}
	

	public void visitChildren(MdfModelElement model) {
        if (model instanceof MdfDomain) {
            visit(((MdfDomain) model).getClasses());
            visit(((MdfDomain) model).getDatasets());
            visit(((MdfDomain) model).getEnumerations());
            visit(((MdfDomain) model).getBusinessTypes());
        } else if (model instanceof MdfClass) {
            visit(((MdfClass) model).getProperties());
            // associations can have a reverse association
            visitReverseAssociation(((MdfClass) model).getProperties());
        } else if (model instanceof MdfEnumeration) {
            visit(((MdfEnumeration) model).getValues());
        } else if (model instanceof MdfDataset) {
            visit(((MdfDataset) model).getProperties());
        }
    }

    private void visit(Collection children) {
		Iterator it = children.iterator();
		while (it.hasNext() && !canceled) {
			visit((MdfModelElement) it.next());
		}
	}
    
    /*
     * DS-1509
     */
    private void visitReverseAssociation(Collection children) {
		Iterator it = children.iterator();
		while (it.hasNext() && !canceled) {
			Object obj = it.next();
			if (obj instanceof MdfAssociation) {
				MdfAssociation association = (MdfAssociation)obj;
				if (association.getReverse() != null) {
					visit(association.getReverse());
				}
			}
		}
    }
}
