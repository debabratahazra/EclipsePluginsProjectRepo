package com.odcgroup.t24.mdf.editor.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.MdfEcoreUtil;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;

public class T24MdfDatasetDerivedPropertyTypeProvider implements IContentAssistProvider {
	
	private MdfDatasetDerivedProperty initialProperty; 
	
	@Override
	public String getDefaultDomainName() {
		return MdfUtility.getDomain(initialProperty).getName();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MdfModelElement[] getCandidates() {
        List candidates = new ArrayList();

        // -- Add core primitives first
        Iterator it = PrimitivesDomain.DOMAIN.getEntities().iterator();
        while (it.hasNext()) {
            MdfEntity entity = (MdfEntity) it.next();
            if (entity instanceof MdfPrimitive) {
                candidates.add(entity);
            }
        }
        
        // add all other primitive types from the current domain & other referenced domains
        List<MdfEntity> entities = MdfEcoreUtil.getAllEntities(((EObject)initialProperty).eResource());
        for (MdfEntity entity : entities) {
            if (entity instanceof MdfPrimitive) {
                candidates.add(entity);
            }
        }

        return (MdfModelElement[]) candidates.toArray(new MdfModelElement[candidates.size()]);
	}

	public T24MdfDatasetDerivedPropertyTypeProvider(MdfDatasetDerivedProperty property) {
		this.initialProperty = property;
	}

}
