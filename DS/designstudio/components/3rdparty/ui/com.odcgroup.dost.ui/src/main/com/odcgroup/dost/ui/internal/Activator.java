package com.odcgroup.dost.ui.internal;

import java.io.IOException;
import java.net.URL;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.dost.builder";

	// The shared instance
	private static Activator plugin;

	private Object lock = new Object();
	private Templates topicPreviewTemplates;
	private Templates mapPreviewTemplates;

	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/**
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Job loadTemplatesJob = new Job("Loading templates") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				synchronized (lock) {
					try {
						Bundle dost = Platform.getBundle("com.odcgroup.dost");
						URL topicStylesheet = dost
								.getEntry("/xsl/dita2xhtmlpreview.xsl");
						topicStylesheet = FileLocator.resolve(topicStylesheet);
						topicStylesheet = FileLocator
								.toFileURL(topicStylesheet);

						URL mapStylesheet = dost
								.getEntry("/xsl/map2xhtmtoc.xsl");
						mapStylesheet = FileLocator.resolve(mapStylesheet);
						mapStylesheet = FileLocator
								.toFileURL(mapStylesheet);

						TransformerFactory factory = TransformerFactory
								.newInstance();

						topicPreviewTemplates = factory
								.newTemplates(new StreamSource(topicStylesheet
										.toString()));
						mapPreviewTemplates = factory
								.newTemplates(new StreamSource(mapStylesheet
										.toString()));
					} catch (TransformerConfigurationException e) {
						newStatus(IStatus.ERROR, e);
					} catch (IOException e) {
						newStatus(IStatus.ERROR, e);
					} finally {
						monitor.done();
					}
				}

				return Status.OK_STATUS;
			}

		};

		loadTemplatesJob.setPriority(Job.LONG);
		loadTemplatesJob.setSystem(true);
		loadTemplatesJob.schedule();
	}

	public IStatus newStatus(int severity, Exception e) {
		return new Status(severity, PLUGIN_ID, e.getLocalizedMessage(), e);
	}

	public void log(int severity, Exception e) {
		getLog().log(newStatus(severity, e));
	}

	/**
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public Templates getTopicPreviewTemplates() throws TransformerException,
			IOException {
		synchronized (lock) {
			return topicPreviewTemplates;
		}
	}

	public Templates getMapPreviewTemplates() throws TransformerException,
			IOException {
		synchronized (lock) {
			return mapPreviewTemplates;
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
