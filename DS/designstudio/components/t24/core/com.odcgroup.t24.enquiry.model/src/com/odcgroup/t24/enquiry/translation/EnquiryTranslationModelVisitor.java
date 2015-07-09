package com.odcgroup.t24.enquiry.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;

/**
 * @author atr
 */
public class EnquiryTranslationModelVisitor implements ITranslationModelVisitor {
	
	/** */
	private ITranslationManager manager;
	/** */
	private Enquiry enquiry;
	
//	private void visit(ITranslationModelVisitorHandler handler, Field field) {
//		ITranslation translation = manager.getTranslation(field);
//		if (translation != null) {
//			handler.notify(translation);
//		}
//	}
	
	@Override
	public void visit(ITranslationModelVisitorHandler handler) {
		ITranslation translation = manager.getTranslation(enquiry);
		if (translation != null) {
			handler.notify(translation);
		}
//		for (Field field : (List<Field>)version.getFields()) {
//				visit(handler, field);
//		}
		
	}

	/**
	 * @param project
	 * @param enquiry
	 */
	public EnquiryTranslationModelVisitor(IProject project, Enquiry enquiry) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.enquiry = enquiry;
	}
}
