package com.odcgroup.mdf.editor.model;

import org.eclipse.core.resources.IFile;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public interface ModelFileVisitor {
	public boolean accept(IFile file);
}
