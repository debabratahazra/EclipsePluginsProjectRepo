package com.odcgroup.page.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;

/**
 * @author atr
 */
public class PageTranslationModelVisitor implements ITranslationModelVisitor {
	
	/** */
	private ITranslationManager manager;
	/** */
	private Model model;
	
	/**
	 * @param handler
	 * @param widget
	 */
	protected void visit(ITranslationModelVisitorHandler handler, Event event) {
		ITranslation translation = manager.getTranslation(event);
		if (translation != null) {
			handler.notify(translation);
		}
	}

	/**
	 * @param handler
	 * @param widget
	 */
	protected void visit(ITranslationModelVisitorHandler handler, Widget widget) {
		if (widget.getType().translationSupported()) { // optimization
			ITranslation translation = manager.getTranslation(widget);
			if (translation != null) {
				handler.notify(translation);
			}
		}
		for (Event event : widget.getEvents()) {
			visit(handler, event);
		}
		for (Widget w : widget.getContents()) {
			visit(handler, w);
		}
		//visit the include xtooltip fragment.  
		Property includeProperty=widget.findProperty(PropertyTypeConstants.XTOOLTIP_INCLUDE_FRAGEMENT);
		if(includeProperty !=null) {
			Model model = includeProperty.getModel();
			if(model !=null && model.getWidget() !=null) {
				visit(handler,model.getWidget());
			}

		}
	}

	@Override
	public void visit(ITranslationModelVisitorHandler handler) {
		Widget root = model.getWidget();
		visit(handler, root);
	}
	
	
	/**
	 * @param project
	 * @param model
	 */
	public PageTranslationModelVisitor(IProject project, Model model) {
		this.manager = TranslationCore.getTranslationManager(project);
		this.model = model;
	}

}
