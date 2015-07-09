package com.odcgroup.ocs.support.ui.external.tool.installer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;

public class M2EclipseLoggingHelperTest   {

	@SuppressWarnings("unchecked")
	@Test
	public void testListenerToMavenExecution() {
		// Given
		class ProgressMonitorSpy implements IProgressMonitor {
			public int work = 0;
			public boolean subTaskCalled = false;
			public void beginTask(String name, int totalWork) {
			}
			public void done() {
			}
			public void internalWorked(double work) {
			}
			public boolean isCanceled() {
				return false;
			}
			public void setCanceled(boolean value) {
			}
			public void setTaskName(String name) {
			}
			public void subTask(String name) {
				subTaskCalled = true;
			}
			public void worked(int delta) {
				work += delta;
			}
		}
		ProgressMonitorSpy progressMonitorSpy = new ProgressMonitorSpy();
		Logger eclipseLogger = LoggerFactory.getLogger(M2EclipseLoggingHelper.M2E_LOGGER);
		
		// When
		AppenderBase<ILoggingEvent> listenerToMavenExecution = M2EclipseLoggingHelper.addListenerToMavenExecution(progressMonitorSpy);
	
		// Then
		Assert.assertNotNull("The spying appender is not found", ((AppenderAttachable<ILoggingEvent>)eclipseLogger).getAppender(M2EclipseLoggingHelper.APPENDER_NAME));
		
		// When
		eclipseLogger.info(M2EclipseLoggingHelper.PROGRESS + "10");
		eclipseLogger.info(M2EclipseLoggingHelper.PROGRESS + "some file");
		
		// Then
		Assert.assertTrue("The progress bar should have progressed", progressMonitorSpy.work != 0);
		Assert.assertTrue("The subtask should have been updated", progressMonitorSpy.subTaskCalled);
		
		// When
		M2EclipseLoggingHelper.removeListenerToMavenExecution(listenerToMavenExecution);
		
		// Then
		Assert.assertNull("The spying appender is not found", ((AppenderAttachable<ILoggingEvent>)eclipseLogger).getAppender(M2EclipseLoggingHelper.APPENDER_NAME));
	}
}
