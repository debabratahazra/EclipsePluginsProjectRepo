package com.odcgroup.workbench.core.internal.resources;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Document me!
 *
 * @author kkr
 *
 */
public class ProxyResolutionJobQueue {
	static final private Logger logger = LoggerFactory.getLogger(ProxyResolutionJobQueue.class);
	
	final protected static List<ProxyResolutionJob> queue = new LinkedList<ProxyResolutionJob>();
	
	static protected Thread runnable; 
	
	public static synchronized void queue(ProxyResolutionJob job) {
		// if the job manager is suspended, we do not queue this job as it might never be executed
		if(Job.getJobManager().isSuspended()) return;
		
		queue.add(job);
		// process the queue asynchronously, so that the call returns immediately
		if(runnable==null || !runnable.isAlive()) {
			runnable = new Thread("ProxyResolutionJobQueueManager") {
	            @Override
	            public void run() {
					processQueue();
	            }			
			};
			runnable.start();
		}
	}

	private static void processQueue() {
		while(!queue.isEmpty()) {
			if(Job.getJobManager().find(ProxyResolutionJob.FAMILY_PROXY_RESOLUTION_JOB).length==0) {
				Job job = queue.get(0);
				job.schedule();
				try {
					job.join();
				} catch (InterruptedException e) {
					// ignore
				}
				queue.remove(0);
			} else {
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e) {
					// ignore
				}
			}
		}
	}
}
