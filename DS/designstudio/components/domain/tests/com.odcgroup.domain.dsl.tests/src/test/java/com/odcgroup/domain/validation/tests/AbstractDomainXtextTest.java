package com.odcgroup.domain.validation.tests;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.tests.util.AbstractXtextTest;

public abstract class AbstractDomainXtextTest extends AbstractXtextTest { 
	
    protected URI getURI(String filename) {
    	URL url = getClass().getResource(filename);
    	URI uri = null;
    	if (url != null) {
			try {
				URL resolvedUrl = FileLocator.resolve(url);
                uri = URI.createURI(resolvedUrl.toString(), true);
			} catch (IOException e) {
				uri = URI.createURI(resourceRoot+ "/" + filename);
			} 
		}
    	return uri;
    }
    
    public AbstractDomainXtextTest(String resourceRoot) {
    	super(resourceRoot);
    }

}
