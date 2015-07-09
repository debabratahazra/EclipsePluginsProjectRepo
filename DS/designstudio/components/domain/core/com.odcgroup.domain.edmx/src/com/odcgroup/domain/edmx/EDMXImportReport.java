package com.odcgroup.domain.edmx;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class EDMXImportReport {

	
	private Map<EDMXDomainDetail, List<String>> errors = new LinkedHashMap<EDMXDomainDetail, List<String>>();
	
	private List<String> globalErrors = new ArrayList<String>();
	
	private int getNumberOfModelsWithErrors() {
		int count = 0;
		for (Map.Entry<EDMXDomainDetail, List<String>> entry : errors.entrySet()) {
			if (entry.getValue().size() > 0) {
				count++;
			}
		}
		return count;
	}
	
	private String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	protected String separator() {
		final int MAX = 90;
		StringBuffer b = new StringBuffer(MAX);
		for (int cx = 0; cx < MAX; cx++) {
			b.append('-');
		}
		return b.toString();
	}
	
	
	public void add(EDMXDomainDetail model) {
		errors.put(model, new ArrayList<String>());
	}

	
	public void error(Exception ex) {
		String message = ex.getMessage();
		if (StringUtils.isBlank(message)) {
			error(getStackTrace(ex));
		} else {
			error(ex.getMessage());
		}
	}

	
	public void error(String text) {
		globalErrors.add(text);
	}

	
	public void error(EDMXDomainDetail model, Exception ex) {
		String message = ex.getMessage();
		if (StringUtils.isBlank(message)) {
			error(model, getStackTrace(ex));
		} else {
			error(model,ex.getMessage());
		}
	}
	
	
	public void error(EDMXDomainDetail model, String text) {
		List<String> list = errors.get(model);
		if (list == null) {
			list = new ArrayList<String>();
			errors.put(model,  list);
		}
		list.add(text);
	}

	
	public String getErrors() {
		StringBuffer b = new StringBuffer(8192);
		for (Map.Entry<EDMXDomainDetail, List<String>> entry : errors.entrySet()) {
			EDMXDomainDetail v = entry.getKey();
			List<String> messages = entry.getValue();
			if (messages.size() > 0) {
				b.append("\n Domain : "); //$NON-NLS-1$
				b.append("Model Name:["); //$NON-NLS-1$
				b.append(v.getName());
				b.append("]\n"); //$NON-NLS-1$
				for (String msg : messages) {
					b.append("\t"); //$NON-NLS-1$
					b.append(msg);
					b.append("\n"); //$NON-NLS-1$
				}
				b.append(separator());
			}
		}
		return b.toString();
	}
	
	
	public String getSuccess() {
		StringBuffer b = new StringBuffer(8192);
		for (Map.Entry<EDMXDomainDetail, List<String>> entry : errors.entrySet()) {
			EDMXDomainDetail v = entry.getKey();
			List<String> messages = entry.getValue();
			if (messages.size() == 0) {
				b.append("\n Domain"); //$NON-NLS-1$
				b.append(": "); //$NON-NLS-1$
				b.append(v.getName());
				b.append("\n"); //$NON-NLS-1$
				b.append(separator());
			}
		}
		return b.toString();
	}
	
	
	public String getSummaryMessage() {
		StringBuffer b = new StringBuffer(8192);
		int size = errors.size();
		int nbErrors = getNumberOfModelsWithErrors();
		if (globalErrors.size()>0) {
			b.append("\nGeneral Error(s):"); //$NON-NLS-1$
			b.append("\n"); //$NON-NLS-1$
			for (String msg : globalErrors) {
				b.append("\t");
				b.append(msg);
				b.append("\n"); //$NON-NLS-1$
			}
		}
		b.append("\n"); //$NON-NLS-1$
		b.append("Models successfully Imported: "); //$NON-NLS-1$
		b.append(size-nbErrors);
		b.append("\n"); //$NON-NLS-1$
		b.append("Models with errors: "); //$NON-NLS-1$
		b.append(nbErrors);
		return b.toString();
	}

	
	public boolean hasErrors() {
		return globalErrors.size() > 0 || getNumberOfModelsWithErrors() > 0;
	}	

}
