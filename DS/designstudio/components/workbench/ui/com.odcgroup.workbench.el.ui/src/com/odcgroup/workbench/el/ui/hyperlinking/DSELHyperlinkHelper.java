package com.odcgroup.workbench.el.ui.hyperlinking;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkLabelProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class DSELHyperlinkHelper extends HyperlinkHelper {

	@Inject@HyperlinkLabelProvider
	private ILabelProvider labelProvider;

	@Inject
	private Provider<XtextHyperlink> hyperlinkProvider;
	
	@Override
	public void createHyperlinksTo(XtextResource from, Region region,
			EObject to, IHyperlinkAcceptor acceptor) {
		if(from.getURI().isPlatform()) {
			String projectName = from.getURI().segment(1);
			
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if(project!=null) {
				IOfsProject ofsProject = OfsCore.getOfsProject(project);
				if(ofsProject!=null) {
					final ModelURIConverter uriConverter = new ModelURIConverter(ofsProject);
					final String hyperlinkText = labelProvider.getText(to);
					final URI uri = EcoreUtil.getURI(to);
					final URI normalized = uriConverter.toPlatformURI(uri);

					if(normalized!=null) {
						XtextHyperlink result = hyperlinkProvider.get();
						result.setHyperlinkRegion(region);
						result.setURI(normalized);
						result.setHyperlinkText(hyperlinkText);
						acceptor.accept(result);
						return;
					}
				}
			}	
		}
		super.createHyperlinksTo(from, region, to, acceptor);
	}
}
