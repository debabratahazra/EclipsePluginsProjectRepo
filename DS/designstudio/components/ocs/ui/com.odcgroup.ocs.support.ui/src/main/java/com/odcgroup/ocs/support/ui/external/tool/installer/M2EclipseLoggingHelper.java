package com.odcgroup.ocs.support.ui.external.tool.installer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;

public class M2EclipseLoggingHelper {

	static final String M2E_LOGGER = "org.eclipse.m2e.core.internal.embedder.EclipseLogger";
	static final String PROGRESS = "PROGRESS: ";
	static final String APPENDER_NAME = "ds.m2e.log.listener";

	@SuppressWarnings("unchecked")
	public static AppenderBase<ILoggingEvent> addListenerToMavenExecution(final IProgressMonitor progressMonitor) {
		
		// Listener which follows maven command progress
		AppenderBase<ILoggingEvent> appender = new AppenderBase<ILoggingEvent>() {
			private int total;
			private int current;
			private int currentPercent;
			@Override
			protected void append(ILoggingEvent eventObject) {
				String message = eventObject.getFormattedMessage();
				if (message.startsWith(PROGRESS)) {
					message = StringUtils.remove(message, PROGRESS);
					if (total == 0) {
						total = Integer.parseInt(message);
					} else {
						current++;
						int newCurrentPercent = 100 * current / total;
						int delta = newCurrentPercent - currentPercent;
						currentPercent = newCurrentPercent;
						
						progressMonitor.subTask("Extracting " + message);
						progressMonitor.worked(delta);
					}
				}
			}
		};
		appender.setName(APPENDER_NAME);
		Logger eclipseLogger = LoggerFactory.getLogger(M2E_LOGGER);
		if (eclipseLogger instanceof AppenderAttachable<?>) {
			((AppenderAttachable<ILoggingEvent>)eclipseLogger).addAppender(appender);
			PatternLayoutEncoder encoder = new PatternLayoutEncoder(); 
			ch.qos.logback.classic.Logger loggerImpl = (ch.qos.logback.classic.Logger) eclipseLogger;
			encoder.setContext(loggerImpl.getLoggerContext());
			encoder.setPattern("%marker %level %msg%n");
			encoder.start();
			appender.start();
			return appender;
		} else {
			// This should only happened if the DS logging is broken.
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void removeListenerToMavenExecution(AppenderBase<ILoggingEvent> appender) {
		if (appender == null)
			return;
		Logger eclipseLogger = LoggerFactory.getLogger(M2E_LOGGER);
		((AppenderAttachable<ILoggingEvent>)eclipseLogger).detachAppender(appender);
		appender.stop();
	}

}
