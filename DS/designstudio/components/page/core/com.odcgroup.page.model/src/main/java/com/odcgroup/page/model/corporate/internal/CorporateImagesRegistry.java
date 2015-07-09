package com.odcgroup.page.model.corporate.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import com.odcgroup.page.model.corporate.CorporateImages;
import com.odcgroup.page.model.corporate.CorporateImagesConstants;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * The Registry of Corporate Images.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class CorporateImagesRegistry {
	
	/** Binds a project to corporate images  */
	private Map<IProject, AbstractCorporateImages> map;

	/** Unbind a project when it is closed */
	private IResourceChangeListener projectChangeListener = new IResourceChangeListener() {
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getSource() instanceof IWorkspace) {
				if (IResourceChangeEvent.PRE_CLOSE == event.getType()) {
					IResource resource = event.getResource();
					if (IResource.PROJECT == resource.getType()) {
						AbstractCorporateImages acd = map.remove((IProject)resource);
						if (acd != null) acd.dispose();
					}
				} 
			}
		}
	};
	
	/**
	 * Create a new corporate  given a project. Subclass can override this method
	 * to provide specific corporate  instance. 
	 * 
	 * @param ofsProject  cannot be null
	 * @return AbstractCorporateImages
	 */
	protected AbstractCorporateImages createCorporateImages(IOfsProject ofsProject) {
		return new CorporateImagesImpl(ofsProject, CorporateImagesConstants.PROPERTY_STORE_ID);
	}
	
	/**
	 * Allows this registry to dispose internal data structure
	 */
	public void dispose() {
		
		ResourcesPlugin.getWorkspace()
		   .removeResourceChangeListener(projectChangeListener);	
		
		for (Iterator<IProject> it=map.keySet().iterator();it.hasNext();){
			AbstractCorporateImages acd = map.remove(it.next());
			acd.dispose();
		}

		map.clear();
	}
	
	/**
	 * @return active CorporateImages
	 */
	public CorporateImages[] getCorporateImages() {
		return map.values().toArray(new CorporateImages[]{});
	}

	/**
	 * Gets the corporate  given a project
	 * @param ofsProject
	 * @return CorporateImages
	 */
	public synchronized CorporateImages getCorporateImages(IOfsProject ofsProject) {
		IProject project = ofsProject.getProject();
		CorporateImages cd = map.get(project);
		if (cd == null) {
			AbstractCorporateImages acd = createCorporateImages(ofsProject); 
			map.put(project, acd);
			cd = acd;
		}
		return cd;
	}
	
	/**
	 * Provides only static services
	 */
	public CorporateImagesRegistry() {
		
		map = new ConcurrentHashMap<IProject, AbstractCorporateImages>();

		ResourcesPlugin.getWorkspace()
					   .addResourceChangeListener(projectChangeListener, 
							                      IResourceChangeEvent.PRE_CLOSE);

	}
}
