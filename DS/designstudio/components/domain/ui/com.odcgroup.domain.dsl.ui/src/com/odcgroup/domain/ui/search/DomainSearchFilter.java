package com.odcgroup.domain.ui.search;

import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.search.IXtextSearchFilter;

import com.google.inject.Inject;

public class DomainSearchFilter implements IXtextSearchFilter {
	
	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Override
	public boolean reject(IEObjectDescription element) {
		return fileExtensionProvider.isValid(element.getEObjectURI().fileExtension())
				&& element.getEObjectOrProxy() instanceof JvmDeclaredType;
	}

}
