package com.odcgroup.workbench.dsl.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.impl.DefaultResourceServiceProvider;

/**
 *
 * @author phanikumark
 *
 */
public class CommonDSLResourceServiceProvider extends DefaultResourceServiceProvider {

	@Override
	public boolean canHandle(URI uri) {
		if (uri.isPlatform()) {
			if (uri.segmentCount() > 3) {
				if ("target".equals(uri.segment(2))) {
					return false;
				}
			}
		}
		return super.canHandle(uri);
	}
}
