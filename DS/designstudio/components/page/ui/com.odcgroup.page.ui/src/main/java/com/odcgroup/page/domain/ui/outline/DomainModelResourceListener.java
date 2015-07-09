package com.odcgroup.page.domain.ui.outline;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * @author atr
 */
class DomainModelResourceListener implements IResourceChangeListener {
	
    /**
     * Visitor for resource changes.
     */
    private class ResourceDeltaVisitor implements IResourceDeltaVisitor {
  	
        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         */
        public boolean visit(final IResourceDelta delta) throws CoreException {
        
            if (delta.getFlags() != IResourceDelta.MARKERS
                    && delta.getResource().getType() == IResource.FILE) {
                if ((delta.getKind() & (IResourceDelta.CHANGED | IResourceDelta.REMOVED)) != 0) {
                	String path = delta.getFullPath().toString();
                	if (path.endsWith(".domain")) {
                		notifyChange(path);
                	}
                }
            }

            return true;
        }    
    }    	
    
	/**
	 * @param path
	 */
	protected void notifyChange(String path) {
	}    
	
    /**
     * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event) {
        IResourceDelta delta = event.getDelta();
        try {
			delta.accept(new ResourceDeltaVisitor());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
		}
    }

}
