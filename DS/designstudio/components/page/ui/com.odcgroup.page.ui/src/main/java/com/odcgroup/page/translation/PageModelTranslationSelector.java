package com.odcgroup.page.translation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.editor.DesignEditor;
import com.odcgroup.page.ui.editor.PageDesignerEditor;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;

/**
 * 
 * @author pkk
 * 
 */
public class PageModelTranslationSelector implements ITranslationOwnerSelector {

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector#select
	 * (org.eclipse.ui.IEditorPart, java.lang.Object)
	 */
	@Override
	public void select(IEditorPart editorPart, ITranslation translation) {
		if (editorPart != null && editorPart instanceof PageDesignerEditor) {
			PageDesignerEditor editor = (PageDesignerEditor) editorPart;
			Object editorPage = editor.getSelectedPage();
			if (editorPage instanceof DesignEditor) {
				DesignEditor dEditor = (DesignEditor) editorPage;
				Object owner = translation.getOwner();
				if (owner instanceof EObject) {
					Widget widget = findWidget((EObject) owner);
					EditPart ep = (EditPart) dEditor.getViewer().getEditPartRegistry().get(widget);
					if (ep!=null) {
						dEditor.getViewer().select(ep);
						dEditor.getViewer().reveal(ep);
					}
				}				
			}
		}
	}

	/**
	 * find the widget associated with the model element
	 * 
	 * @param item
	 * @return widget
	 */
	private Widget findWidget(EObject item) {
		Widget widget = null;
		if (item instanceof Property) {
			Property p = (Property) item;
			widget = p.getWidget();
		} else if (item instanceof Widget) {
			widget = (Widget) item;
		} else if (item instanceof Event) {
			Event e = (Event) item;
			widget = e.getWidget();
		} else if (item instanceof Parameter) {
			Parameter p = (Parameter) item;
			widget = p.getEvent().getWidget();
		} else if (item instanceof Model) {
			Model m = (Model) item;
			widget = m.getWidget();
		}

		return widget;
	}

}
