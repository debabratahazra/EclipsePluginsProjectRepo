package com.odcgroup.t24.version.translation;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;

/**
 * @author atr
 */
public class VersionTranslationModelVisitor implements ITranslationModelVisitor {
	
	/** */
	private ITranslationManager manager;
	/** */
	private Version version;
	
	private void visit(ITranslationModelVisitorHandler handler, Field field) {
		ITranslation translation = manager.getTranslation(field);
		if (translation != null) {
			handler.notify(translation);
		}
	}
	
	@Override
	public void visit(ITranslationModelVisitorHandler handler) {
		ITranslation translation = manager.getTranslation(version);
		if (translation != null) {
			handler.notify(translation);
		}
		for (Field field : (List<Field>)version.getFields()) {
				visit(handler, field);
		}
		
	}

	/**
	 * @param project
	 * @param version
	 */
	public VersionTranslationModelVisitor(IProject project, Version version) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.version = version;
	}
}
