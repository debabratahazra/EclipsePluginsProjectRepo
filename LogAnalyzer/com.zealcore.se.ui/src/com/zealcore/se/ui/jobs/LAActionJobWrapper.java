package com.zealcore.se.ui.jobs;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

public class LAActionJobWrapper extends Job {

	private ILAActionJob actionJob = null;

	/**
	 * @param name
	 */
	public LAActionJobWrapper(ILAActionJob job) {
		super(job.getJobName());
		this.actionJob = job;
		addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				// Execution of job is done, notify your actionJob about this
				// event.
				actionJob.notifyJobDone();
			}
			
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				//super.aboutToRun(event);
				actionJob.notifyJobStart();
			}		
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {
		IStatus returnValue = Status.OK_STATUS;
		setPriority(Job.INTERACTIVE);
		monitor.beginTask(this.actionJob.getJobDescription(), IProgressMonitor.UNKNOWN);
		try {
			this.actionJob.run(monitor);
		} catch (InvocationTargetException ex) {

		} catch (InterruptedException e) {

		} finally {
			monitor.done();
		}

		if (monitor.isCanceled())
			returnValue = Status.CANCEL_STATUS;

		return returnValue;
	}
}
