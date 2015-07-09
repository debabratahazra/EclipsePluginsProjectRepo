package com.odcgroup.page.ui.snippet.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.page.model.util.DatasetAttribute;
import com.odcgroup.page.model.util.SearchDomainObjectUtil;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 *
 * @author pkk
 * @param <T> 
 *
 */
public abstract class AbstractSnippetControl<T> {
	
	/**
	 * @param container
	 */
	public abstract void setInput(T container);
	
	/**
	 * @return t
	 */
	public abstract T getContainer();
	
	/**
	 * Gets the Shell.
	 * 
	 * @return Shell
	 */
	protected Shell getShell() {
	    return Display.getCurrent().getActiveShell();
	}	
	
	
	/**
	 * @param datasetname
	 * @return string[]
	 */
	protected String[] getDatasetAttributes(String datasetname) {
		if (datasetname == null) {
			return null;
		}
		List<DatasetAttribute> attributes = SearchDomainObjectUtil.getDomainAttributes(datasetname, getOfsProject());
		List<String> names = new ArrayList<String>();
		for (DatasetAttribute mdfProperty : attributes) {
			names.add(mdfProperty.getDisplayName());
		}
		return names.toArray(new String[0]);
	}
	

	/**
	 * @return contentAssistProvider
	 */
	protected IContentAssistProvider getDomainObjectProvider() {
		final DomainRepository repo = DomainRepository.getInstance(getOfsProject());
		final IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {
			public MdfModelElement[] getCandidates() {
				final List<MdfDataset> allDatasets = new ArrayList<MdfDataset>();
				Iterator<MdfDomain> it = repo.getDomains().iterator();
				while (it.hasNext()) {
					new ModelWalker(new ModelVisitor() {
						public boolean accept(MdfModelElement model) {
							if (model instanceof MdfDataset) {
								allDatasets.add((MdfDataset) model);
							}
							return true;
						}
					}).visit(it.next());
				}
				return allDatasets.toArray(new MdfModelElement[] {});
			}

			public String getDefaultDomainName() {
				return "";
			}
		};
		return contentAssistProvider;
	} 
	
	/**
	 * @return ofsProject
	 */
	protected IOfsProject getOfsProject() {
		if (getContainer() instanceof EObject) {
			Resource resource = ((EObject) getContainer()).eResource();
			if (resource != null) {
				return OfsResourceHelper.getOfsProject(resource);
			}
		}
		return EclipseUtils.findCurrentProject();
	}

}
