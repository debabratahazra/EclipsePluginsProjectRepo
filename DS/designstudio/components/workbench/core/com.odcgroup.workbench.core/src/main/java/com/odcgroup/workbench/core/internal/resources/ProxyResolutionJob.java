package com.odcgroup.workbench.core.internal.resources;

import org.eclipse.core.runtime.jobs.Job;

/**
 * This is a special job used for EMF proxy resolution.
 * We extend the normal Job as we need to introduce a family, in order to wait for
 * the end of such jobs.
 * 
 * @author Kai Kreuzer
 * 
 */
abstract public class ProxyResolutionJob extends Job {

	/** The job family for artifact jobs. */
	public static final Object FAMILY_PROXY_RESOLUTION_JOB = new Object();

	public ProxyResolutionJob() {
		this("Resolving EMF proxies...");
	}

	/**
	 * Creates a new job.
	 * 
	 * @param name
	 *            the jobs name
	 */
	public ProxyResolutionJob(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
	 */
	@Override
	public boolean belongsTo(Object family) {
		return FAMILY_PROXY_RESOLUTION_JOB == family;
	}
}
