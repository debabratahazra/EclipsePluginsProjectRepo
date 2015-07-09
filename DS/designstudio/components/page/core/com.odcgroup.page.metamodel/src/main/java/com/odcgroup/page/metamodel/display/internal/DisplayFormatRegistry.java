package com.odcgroup.page.metamodel.display.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.osgi.framework.Bundle;

import com.odcgroup.page.metamodel.PageMetamodelActivator;
import com.odcgroup.page.metamodel.display.DisplayFormat;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * The Display Format Registry.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class DisplayFormatRegistry {
	
	/** */
	static final String PROPERTY_STORE_ID = "com.odcgroup.page.ocs.display";

	/** Binds a project to DisplayFormat */
	private Map<IProject, AbstractDisplayFormat> map;
	
	/** The gloable DisplayFormat instance */
	AbstractDisplayFormat globalDisplayFormat;
	
	/** */
	private Bundle bundle;

	/** Unbind a project when it is closed */
	private IResourceChangeListener projectChangeListener = new IResourceChangeListener() {
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getSource() instanceof IWorkspace) {
				if (IResourceChangeEvent.PRE_CLOSE == event.getType()) {
					IResource resource = event.getResource();
					if (IResource.PROJECT == resource.getType()) {
						AbstractDisplayFormat acd = map.remove((IProject)resource);
						if (acd != null) acd.dispose();
					}
				} 
			}
		}
	};
	
	/**
	 * @return Bundle
	 */
	protected final Bundle getBundle() {
		return this.bundle;
	}
	
	/**
	 * @return IPreferenceStore for the plug-in
	 * @param project 
	 */
	protected ProjectPreferences getPreferenceStore(IProject project) {
        return new ProjectPreferences(project, PageMetamodelActivator.PLUGIN_ID);
	}
	
	/**
	 * Create a new DisplayFormat given a project. Subclass can override this method
	 * to provide specific DisplayFormat instance. 
	 * 
	 * @param project
	 * @return AbstractDisplayFormat
	 */
	protected AbstractDisplayFormat createDisplayFormat(IProject project) {
		return new DisplayFormatImpl(getBundle(), getPreferenceStore(project));
	}
	
	/**
	 * Disposes internal data structures
	 */
	public void dispose() {
		
		ResourcesPlugin.getWorkspace()
		   .removeResourceChangeListener(projectChangeListener);	
		
		for (Iterator<IProject> it=map.keySet().iterator();it.hasNext();){
			AbstractDisplayFormat acd = map.remove(it.next());
			acd.dispose();
		}

		map.clear();
	}

	/**
	 * Gets the DisplayFormat given a project
	 * @param project
	 * @return DisplayFormat
	 */
	public synchronized DisplayFormat getDisplayFormat(IProject project) {
		DisplayFormat df = null;
		if (project == null) {
			if (globalDisplayFormat == null) {
				globalDisplayFormat = createDisplayFormat(null);
			}
			df = globalDisplayFormat;
		} else {
			df = map.get(project);
			if (df == null) {
				AbstractDisplayFormat adf = createDisplayFormat(project); 
				map.put(project, adf);
				df = adf;
			}
		}
		return df;
	}

	/**
	 * Provides only static services
	 * @param bundle
	 */
	public DisplayFormatRegistry(Bundle bundle) {
		
		this.bundle = bundle;
		
		map = new ConcurrentHashMap<IProject, AbstractDisplayFormat>();

		ResourcesPlugin.getWorkspace()
					   .addResourceChangeListener(projectChangeListener, IResourceChangeEvent.PRE_CLOSE);

	}

}
