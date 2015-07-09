package com.odcgroup.page.transformmodel.namespaces.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import com.odcgroup.page.transformmodel.namespaces.NamespaceFacility;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * The Corporate Design Registry.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NamespaceFacilityRegistry {
	
	/** */
	static final String PROPERTY_STORE_ID = "com.odcgroup.page.transformmodel.namespaces";

	/** Binds a project to  corporate design */
	private Map<IProject, AbstractNamespaceFacility> map;

	/** Unbind a project when it is closed */
	private IResourceChangeListener projectChangeListener = new IResourceChangeListener() {
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getSource() instanceof IWorkspace) {
				switch (event.getType()) {
				case IResourceChangeEvent.PRE_CLOSE:
					removeProject(event);
					break;
				case IResourceChangeEvent.PRE_DELETE:
					removeProject(event);
					break;
				} 
			}
		}

		private void removeProject(IResourceChangeEvent event) {
			IResource resource = event.getResource();
			if (IResource.PROJECT == resource.getType()) {
				AbstractNamespaceFacility acd = map.remove((IProject)resource);
				if (acd != null) acd.dispose();
			}
		}
	};
	
	/**
	 * Create a new corporate design given a project. Subclass can override this method
	 * to provide specific NamespaceFacility instance. 
	 * 
	 * @param project
	 * @return AbstractNamespaceFacility
	 */
	protected AbstractNamespaceFacility createNamespaceFacility(IProject project) {
        ProjectPreferences preferences = new ProjectPreferences(project, PROPERTY_STORE_ID);
		return new NamespaceFacilityImpl(preferences);
	}
	
	/**
	 * Allows this registry to dispose internal data structure
	 */
	public void dispose() {
		
		ResourcesPlugin.getWorkspace()
		   .removeResourceChangeListener(projectChangeListener);	
		
		for (Iterator<IProject> it=map.keySet().iterator();it.hasNext();){
			AbstractNamespaceFacility anf = map.remove(it.next());
			anf.dispose();
		}

		map.clear();
	}

	/**
	 * Gets the corporate design given a project
	 * @param project
	 * @return CorporateDesign
	 */
	public synchronized NamespaceFacility getNamespaceFacility(IProject project) {
		NamespaceFacility nf = map.get(project);
		if (nf == null) {
			AbstractNamespaceFacility anf = createNamespaceFacility(project); 
			map.put(project, anf);
			nf = anf;
		}
		return nf;
	}
	
	/**
	 * Provides only static services
	 */
	public NamespaceFacilityRegistry() {
		
		map = new ConcurrentHashMap<IProject, AbstractNamespaceFacility>();

		ResourcesPlugin.getWorkspace()
					   .addResourceChangeListener(projectChangeListener, 
							                      IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE);

	}
}
