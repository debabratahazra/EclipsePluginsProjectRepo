package com.odcgroup.domain.ui.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IContributedContentsView;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.odcgroup.domain.ui.DomainDSLEditor;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.section.AbstractModelPropertySection;

public class AbstractDSLPropertySection extends AbstractModelPropertySection {
	
	 @Override
	public void propertyChanged(final IPropertyFeatureChangeEvent event) {
		 IWorkbenchPart part = getPart();
		 DomainDSLEditor editor = null;
		 if(part instanceof ContentOutline) {
			 IContributedContentsView view = (IContributedContentsView)(((ContentOutline)part).getAdapter(IContributedContentsView.class));
			 editor = (DomainDSLEditor)(view.getContributingPart());
		 }
		 if(part instanceof DomainDSLEditor) {
			 editor = (DomainDSLEditor)part;
		 }
		 IXtextDocument doc = editor.getDocument();
		 doc.modify(new IUnitOfWork.Void<XtextResource>() {
				@Override
				public void process(XtextResource state) throws Exception {
					EObject eObject = getInput();
					Object value = event.getValue();
					eObject.eSet(event.getPropertyFeature().getStructuralFeature(), value);
				}
			});
	}

	@Override
	protected void configureProperties() {
		
	}

}
