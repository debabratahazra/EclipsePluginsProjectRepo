package com.odcgroup.process.model.translation;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.Task;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;

/**
 * @author atr
 */
public class ProcessModelVisitor implements ITranslationModelVisitor {
	
	/** */
	private ITranslationManager manager;
	/** */
	private Process process;
	
	private void visit(ITranslationModelVisitorHandler handler, Task task) {
		ITranslation translation = manager.getTranslation(task);
		if (translation != null) {
			handler.notify(translation);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void visit(ITranslationModelVisitorHandler handler) {
		ITranslation translation = manager.getTranslation(process);
		if (translation != null) {
			handler.notify(translation);
		}
		for (Pool pool : (List<Pool>)process.getPools()) {
			for (Task task : (List<Task>)pool.getTasks()) {
				visit(handler, task);
			}
		}
		
	}

	/**
	 * @param project
	 * @param process
	 */
	public ProcessModelVisitor(IProject project, Process process) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.process = process;
	}
}
