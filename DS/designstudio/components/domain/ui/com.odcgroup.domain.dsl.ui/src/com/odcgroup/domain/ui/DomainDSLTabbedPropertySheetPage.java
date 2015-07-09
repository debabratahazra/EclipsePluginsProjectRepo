package com.odcgroup.domain.ui;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.xtext.nodemodel.BidiIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;
import com.odcgroup.workbench.editors.properties.AdvancedTabbedPropertySheetPage;

public class DomainDSLTabbedPropertySheetPage extends
		AdvancedTabbedPropertySheetPage {

	DomainDSLEditor editor = null;

	public DomainDSLTabbedPropertySheetPage(
			ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor) {
		super(tabbedPropertySheetPageContributor);
		this.editor = (DomainDSLEditor) tabbedPropertySheetPageContributor;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, ISelection selection) {
		if(!selection.isEmpty() && selection instanceof ITreeSelection) {
			final ITreeSelection treeSel = (ITreeSelection) selection;
			if(treeSel.getFirstElement() instanceof EObjectNode) {
				EObjectNode node = (EObjectNode) treeSel.getFirstElement();
				IResource res = editor.getResource();
				XtextResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, res.getProject());
				URI uri = node.getEObjectURI();
				uri = ModelURIConverter.toResourceURI(uri);
				Resource emfres = rs.getEObject(uri, true).eResource();
				EObject eObj = ((EObjectNode) (treeSel.getFirstElement())).getEObject(emfres);
				doselect(part, new StructuredSelection(eObj));
			}
		}
		else if (!selection.isEmpty() && selection instanceof ITextSelection) {
			final ITextSelection textSel = (ITextSelection) selection;
			editor.getDocument().readOnly(
					new IUnitOfWork.Void<XtextResource>() {
						public void process(XtextResource resource)
								throws Exception {
							IParseResult parseResult = resource
									.getParseResult();
							if (parseResult == null)
								return;
							ICompositeNode rootNode = parseResult.getRootNode();
							int offset = textSel.getOffset();
							BidiIterator<INode> iterator = rootNode
									.getChildren().iterator();
							while (iterator.hasNext()) {
								INode node = iterator.next();
								Iterator<ILeafNode> nodes = node.getLeafNodes().iterator();
								while(nodes.hasNext()) {
									ILeafNode leafNode = nodes.next();
									if(offset <= (leafNode.getOffset() + leafNode.getText().length())) {
										EObject object = leafNode.getSemanticElement();
										if(object != null) {
											doselect(part, new StructuredSelection(object));
											break;
										}
									}
								}
								if (offset <= (node.getOffset() + node.getText().length())) {
									EObject object = node.getSemanticElement();
									doselect(part, new StructuredSelection(object));
									break;
								}
							}
						}
					});
		}
		
	}

	protected void doselect(IWorkbenchPart part, StructuredSelection selection) {
		if(selection != null)
			super.selectionChanged(part, selection);
	}
}
