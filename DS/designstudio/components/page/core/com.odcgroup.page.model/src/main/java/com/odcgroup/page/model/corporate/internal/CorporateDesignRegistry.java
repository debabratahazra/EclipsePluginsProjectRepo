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

import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.page.model.corporate.CorporateDesignConstants;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * The Corporate Design Registry.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class CorporateDesignRegistry {
	
	/** Binds a project to  corporate design */
	private Map<IProject, AbstractCorporateDesign> map;

	/** Unbind a project when it is closed */
	private IResourceChangeListener projectChangeListener = new IResourceChangeListener() {
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getSource() instanceof IWorkspace) {
				if (IResourceChangeEvent.PRE_CLOSE == event.getType() || IResourceChangeEvent.PRE_DELETE == event.getType()) {
					IResource resource = event.getResource();
					if (IResource.PROJECT == resource.getType()) {
						synchronized(map) {
							AbstractCorporateDesign acd = map.remove((IProject)resource);
							if (acd != null) acd.dispose();
						}
					}
				} 
			}
		}
	};
	
	/**
	 * Create a new corporate design given a project. Subclass can override this method
	 * to provide specific corporate design instance. 
	 * 
	 * @param project
	 * @return AbstractCorporateDesign
	 */
	protected AbstractCorporateDesign createCorporateDesign(IProject project) {
        ProjectPreferences preferences = new ProjectPreferences(project, CorporateDesignConstants.PROPERTY_STORE_ID);
		return new CorporateDesignImpl(preferences);
	}
	
	/**
	 * Allows this registry to dispose internal data structure
	 */
	public void dispose() {
		synchronized(map) {
			ResourcesPlugin.getWorkspace()
			   .removeResourceChangeListener(projectChangeListener);	
			
			for (Iterator<IProject> it=map.keySet().iterator();it.hasNext();){
				AbstractCorporateDesign acd = map.remove(it.next());
				acd.dispose();
			}
		
			map.clear();
		}
	}

	/**
	 * Gets the corporate design given a project
	 * @param project
	 * @return CorporateDesign
	 */
	public CorporateDesign getCorporateDesign(IProject project) {
		synchronized(map) {
		CorporateDesign cd = map.get(project);
			if (cd == null) {
				AbstractCorporateDesign acd = createCorporateDesign(project); 
				map.put(project, acd);
				cd = acd;
			}
			return cd;
		}
	}
	
	/**
	 * Provides only static services
	 */
	public CorporateDesignRegistry() {
		
		map = new ConcurrentHashMap<IProject, AbstractCorporateDesign>();

		ResourcesPlugin.getWorkspace()
					   .addResourceChangeListener(projectChangeListener, 
							                      IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE);

	}
}
