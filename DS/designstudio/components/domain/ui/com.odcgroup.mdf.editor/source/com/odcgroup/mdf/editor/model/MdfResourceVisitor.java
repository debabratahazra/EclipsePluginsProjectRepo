package com.odcgroup.mdf.editor.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class MdfResourceVisitor implements IResourceVisitor, IResourceDeltaVisitor {
	private final ModelFileVisitor visitor;

	/**
	 * Constructor for MdfResourceVisitor
	 */
	public MdfResourceVisitor(ModelFileVisitor visitor) {
		super();
		this.visitor = visitor;
	}

	/**
	 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
	 */
	public boolean visit(IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;					
			if (ModelFactory.INSTANCE.isModelFile(file)) {
				return visitor.accept(file);
			}
		}

		return true;
	}

	/**
	 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
	 */
	public boolean visit(IResourceDelta delta) throws CoreException {
		return visit(delta.getResource());
	}

}
