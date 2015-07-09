package com.odcgroup.workbench.dsl.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.parser.IEncodingProvider;

import com.google.inject.Provider;

public class DSLEncodingProviderProvider implements Provider<IEncodingProvider> {

	@Override
	public IEncodingProvider get() {
		return new IEncodingProvider() {
			public String getEncoding(URI uri) {
				return "UTF-8";
			}
		};
	}

}
