package com.odcgroup.mdf.editor.ui.dialogs.mdf.assist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;

/**
 *
 * @author satishnangi
 *
 */
public class MdfEntityContentAssistProvider implements IContentAssistProvider {

	private Resource resource;
	
	public MdfEntityContentAssistProvider(Resource resource) {
		this.resource = resource;
	}
	
	@Override
	public String getDefaultDomainName() {		
		return "";
	}

	@Override
	public MdfModelElement[] getCandidates() {
		List<MdfEntity> candidates = new ArrayList<MdfEntity>();
		if(resource !=null){
			candidates.addAll(DomainRepositoryUtil.getMdfClassesWithPK(resource));
		}
		return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
	}

}
