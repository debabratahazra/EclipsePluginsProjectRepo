package com.odcgroup.mdf.model.util;

import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface ModelVisitor {
	public boolean accept(MdfModelElement model);
}
