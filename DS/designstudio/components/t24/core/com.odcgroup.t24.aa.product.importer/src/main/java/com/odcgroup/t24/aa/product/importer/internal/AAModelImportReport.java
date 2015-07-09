package com.odcgroup.t24.aa.product.importer.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IModelDetail;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAModelImportReport implements IImportModelReport {
	
	private Map<IModelDetail, List<String>> errors = new LinkedHashMap<IModelDetail, List<String>>();
	private IFolder folder;
	
	private List<String> globalErrors = new ArrayList<String>();
	
	// total number of models to be imported 
	private int size = 0;
	
	private int getNumberOfModelsWithErrors() {
		int count = 0;
		for (Map.Entry<IModelDetail, List<String>> entry : errors.entrySet()) {
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
	
	@Override
	public void add(IModelDetail model) {
		errors.put(model, new ArrayList<String>());
	}

	@Override
	public void error(Exception ex) {
		String message = ex.getMessage();
		if (StringUtils.isBlank(message)) {
			error(getStackTrace(ex));
		} else {
			error(ex.getMessage());
		}
	}

	@Override
	public void error(String text) {
		globalErrors.add(text);
	}

	@Override
	public void error(IModelDetail model, Exception ex) {
		String message = ex.getMessage();
		if (StringUtils.isBlank(message)) {
			error(model, getStackTrace(ex));
		} else {
			error(model,ex.getMessage());
		}
	}
	
	@Override
	public void error(IModelDetail model, String text) {
		List<String> list = errors.get(model);
		if (list == null) {
			list = new ArrayList<String>();
			errors.put(model,  list);
		}
		list.add(text);
	}

	@Override
	public String getErrors() {
		StringBuffer b = new StringBuffer(8192);
		for (Map.Entry<IModelDetail, List<String>> entry : errors.entrySet()) {
			IModelDetail v = entry.getKey();
			List<String> messages = entry.getValue();
			if (messages.size() > 0) {
				b.append("\n"+v.getModelType()+": "); //$NON-NLS-1$
				b.append(v.getDescription());
				b.append("\n"); //$NON-NLS-1$
				b.append("Model Name:["); //$NON-NLS-1$
				b.append(v.getModelName());
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
	
	@Override
	public String getSuccess() {
		StringBuffer b = new StringBuffer(8192);
		for (Map.Entry<IModelDetail, List<String>> entry : errors.entrySet()) {
			IModelDetail v = entry.getKey();
			List<String> messages = entry.getValue();
			if (messages.size() == 0) {
				b.append("\n"); //$NON-NLS-1$
				b.append(v.getModelType());
				b.append(v.getDescription());
				b.append("\n"); //$NON-NLS-1$
				b.append(separator());
			}
		}
		return b.toString();
	}
	
	@Override
	public String getSummaryMessage() {
		StringBuffer b = new StringBuffer(8192);
		int nbErrors = getNumberOfModelsWithErrors();
		b.append("Models selected for import: "); //$NON-NLS-1$
		b.append(size);
		b.append("\n"); //$NON-NLS-1$
		b.append("Destination folder: "); //$NON-NLS-1$
		b.append(folder.getFullPath().toString());
		b.append("\n"); //$NON-NLS-1$
		b.append("Models successfully Imported: "); //$NON-NLS-1$
		b.append(size-nbErrors);
		b.append("\n"); //$NON-NLS-1$
		b.append("Models with errors: "); //$NON-NLS-1$
		b.append(nbErrors);
		if (globalErrors.size()>0) {
			b.append("\nGeneral Error(s):"); //$NON-NLS-1$
		}
		b.append("\n"); //$NON-NLS-1$
		for (String msg : globalErrors) {
			b.append("\t");
			b.append(msg);
			b.append("\n"); //$NON-NLS-1$
		}
		return b.toString();
	}

	@Override
	public final int modelCount() {
		return size;
	}

	@Override
	public boolean hasErrors() {
		return getNumberOfModelsWithErrors() > 0;
	}

	/**
	 * @param size
	 * @param destinationFolder
	 */
	public AAModelImportReport(int size, IFolder destinationFolder) {
		this.size = size;
		this.folder = destinationFolder;
	}

}
