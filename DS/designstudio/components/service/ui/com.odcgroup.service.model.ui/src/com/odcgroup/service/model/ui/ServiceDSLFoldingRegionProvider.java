package com.odcgroup.service.model.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.ui.editor.folding.DefaultFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionAcceptor;
import org.eclipse.xtext.util.ITextRegion;

import com.odcgroup.service.model.component.Component;
import com.odcgroup.service.model.component.Constant;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.Property;
import com.odcgroup.service.model.component.Table;

public class ServiceDSLFoldingRegionProvider extends
		DefaultFoldingRegionProvider {
	@Override
	protected void computeObjectFolding(EObject eObject,
			IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor) {
		if (eObject instanceof Component || eObject instanceof Method || eObject instanceof Table || eObject instanceof Constant || eObject instanceof Property) {
			
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
