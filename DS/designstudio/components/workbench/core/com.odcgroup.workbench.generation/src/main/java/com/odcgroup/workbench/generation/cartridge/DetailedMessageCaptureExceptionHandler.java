package com.odcgroup.workbench.generation.cartridge;

import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.internal.xtend.expression.ast.SyntaxElement;
import org.eclipse.xtend.expression.EvaluationException;
import org.eclipse.xtend.expression.ExceptionHandler;
import org.eclipse.xtend.expression.ExecutionContext;

import com.odcgroup.workbench.generation.GenerationCore;

/**
 * Exception handler which captures the details of mwe generation error<br>
 * 
 * @author yan
 */
public class DetailedMessageCaptureExceptionHandler implements ExceptionHandler {
	
	/** Last exception handler used */
	private static DetailedMessageCaptureExceptionHandler lastExceptionHandler;
	
	public static void setLastExceptionHandler(DetailedMessageCaptureExceptionHandler lastExceptionHandler) {
		DetailedMessageCaptureExceptionHandler.lastExceptionHandler = lastExceptionHandler;
	}

	/** Cause captured, usually non mwe exception */
	private Throwable lowLevelCause;
	
	/** Last evaluation exception, means the more detailed */
	private EvaluationException lastEvaluationException;
	
	/**
	 * Create a message capture exception handler (and store a reference for further retrieval)
	 */
	public DetailedMessageCaptureExceptionHandler() {
		setLastExceptionHandler(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.transaction.ExceptionHandler#handleException(java.lang.Exception)
	 */
	public void handleRuntimeException(RuntimeException ex,
			SyntaxElement element, ExecutionContext ctx,
			Map<String, Object> additionalContextInfo) {
		// Store the details
		if (ex instanceof EvaluationException) {
			// Keep only the last EvaluationException (as it contains
			// more information)
			lastEvaluationException = (EvaluationException)ex;
		} else {
			// Keep only the first exception
			if (this.lowLevelCause == null) {
				if (ex.getCause() != null) {
					this.lowLevelCause = ex.getCause(); 
				} else {
					this.lowLevelCause = ex;
				}
			}
		}
		
		// Rethrow the exception (otherwise the workflow will succeed)
		throw ex;
	}

	/**
	 * Retrieved the detailed message message captured by this exception handler 
	 * @return Retrieved the detailed message message captured by this exception handler
	 */
	public static MultiStatus retreiveDetailedMessageStatus() {
		if (lastExceptionHandler == null) {
			return null;
		}
		
		// Retrieve the cause and the evaluation evaluation exception
		Throwable cause = lastExceptionHandler.lowLevelCause;
		EvaluationException evaluationException = lastExceptionHandler.lastEvaluationException;
		
		// Reset the capture instance
		lastExceptionHandler = null;

		// Build the status message
		MultiStatus multiStatus = null;
		if (cause != null) {
			multiStatus = new MultiStatus(
					GenerationCore.PLUGIN_ID, 0, cause.getMessage(), null);
		} else if (evaluationException != null) {
			multiStatus = new MultiStatus(
					GenerationCore.PLUGIN_ID, 0, evaluationException.getMessage(), null);
		} else {
			// No details available
			return null;
		}

		// Build the details of the status message
		if (evaluationException != null) {
			StringTokenizer st = new StringTokenizer(evaluationException.toString(), "\n");
			while (st.hasMoreTokens()) {
				String message = st.nextToken();
				multiStatus.add(new Status(IStatus.ERROR, 
						GenerationCore.PLUGIN_ID, 
						message));
			}
		}
		
		if (evaluationException != null && cause != null) {
			multiStatus.add(new Status(IStatus.ERROR, 
					GenerationCore.PLUGIN_ID, 
					"caused by:"));
		}
		
		if (cause != null) {
			StringTokenizer st = new StringTokenizer(cause.toString(), "\n");
			while (st.hasMoreTokens()) {
				String message = st.nextToken();
				multiStatus.add(new Status(IStatus.ERROR, 
						GenerationCore.PLUGIN_ID, 
						message));
			}
		}
		
		return multiStatus;
	}

}
