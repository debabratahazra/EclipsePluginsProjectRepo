package com.odcgroup.t24.applicationimport;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.OfsCore;

public class ApplicationsImportProblemsException extends Exception {

	private static final long serialVersionUID = 1L;
	private final List<Problem> problems = new ArrayList<Problem>();

	public ApplicationsImportProblemsException addProblem(String failedThing, Throwable cause) {
		Problem problem = new Problem();
		problem.failedThing = failedThing;
		problem.cause = cause;
		problems.add(problem);
		return this;
	}

	public ApplicationsImportProblemsException addProblem(String failure) {
		Problem problem = new Problem();
		problem.failedThing = failure;
		problems.add(problem);
		return this;
	}

	@Override
	public String getMessage() {
		StringBuilder message = new StringBuilder();
		for (int i = 0; i < problems.size(); i++) {
			message.append(i + 1);
			message.append(". ");
			Problem problem = problems.get(i);
			if (problem.cause != null)
				message.append(problem.failedThing +problem.cause);
			else
				message.append(problem.failedThing);
			message.append('\n');
		}
		return message.toString();
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		for (int i = 0; i < problems.size(); i++) {
			s.print(i + 1);
			s.print(". ");
			Problem problem = problems.get(i);
			if (problem.cause != null) {
				s.println(problem.failedThing + " failed because of: \n");
				problem.cause.printStackTrace(s);
			}
			else
				s.println(problem.failedThing);
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		for (int i = 0; i < problems.size(); i++) {
			s.print(i + 1);
			s.print(". ");
			Problem problem = problems.get(i);
			if (problem.cause != null) {
				s.println(problem.failedThing + " failed because of: \n");
				problem.cause.printStackTrace(s);
			}
			else
				s.println(problem.failedThing);
		}
	}
	
	public List<Problem> getProblems() {
		return problems;
	}

	private static class Problem {
		String failedThing;
		Throwable cause;
	}

	@Override
	public Throwable getCause() {
		// NO! throw new UnsupportedOperationException();
		return null;
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		throw new UnsupportedOperationException();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		// NO! throw new UnsupportedOperationException();
		return new StackTraceElement[0];
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		throw new UnsupportedOperationException();
	}
	/**
	 * get the status for each problem and add to the multiStatus
	 * @return MultiStatus.
	 */
	public MultiStatus getErrorStatus(){
	    MultiStatus multiStatus =new MultiStatus(OfsCore.PLUGIN_ID, Status.OK,"" , null);
	    int i=1;
	    for (Problem problem: problems) {
		multiStatus.add(new Status(Status.ERROR, ApplicationIntrospectionActivator.PLUGIN_ID, Status.OK,i+++"."+problem.failedThing + " failed because of:" , problem.cause));
	    }
	    return multiStatus;       
	}

}
