package com.odcgroup.domain.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.folding.DefaultFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionAcceptor;
import org.eclipse.xtext.util.ITextRegion;

import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumeration;

public class DomainDSLFoldingRegionProvider extends
		DefaultFoldingRegionProvider {
	@Override
	protected void computeObjectFolding(EObject eObject,
			IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor) {
		if (eObject instanceof MdfClass || eObject instanceof MdfDomain
				|| eObject instanceof MdfDataset
				|| eObject instanceof MdfBusinessType
				|| eObject instanceof MdfEnumeration) {
			
			ILocationInFileProvider provider = getLocationInFileProvider();
			ITextRegion region = provider.getFullTextRegion(eObject);
			if (region != null) {
				ITextRegion significant = provider.getSignificantTextRegion(eObject);
				if (significant == null)
					throw new NullPointerException("significant region may not be null");
				int offset = region.getOffset();
				foldingRegionAcceptor.accept(offset, region.getLength()-1, significant);
			}
		}
	}
	
	
}
