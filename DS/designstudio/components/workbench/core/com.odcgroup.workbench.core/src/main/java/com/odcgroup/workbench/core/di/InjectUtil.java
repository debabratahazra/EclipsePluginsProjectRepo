package com.odcgroup.workbench.core.di;

import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util for Dependence Injection (UI) helpers (currently Xtext's Guice).
 * 
 * You should try to minimize use of this util.. this only exists because our
 * code base isn't fully DI, yet. Eventually, this should not be needed anymore.
 * If you use it more than once in a class, it's a good indication that you are
 * doing something wrong and didn't completely understand DI yet. Ask for help.
 * 
 * If you are in DI and need something language specific, using @Inject
 * ILangSpecificProvider p instead of this is what you want to do.
 * 
 * @author Michael Vorburger
 */
public class InjectUtil {
	private static final Logger logger = LoggerFactory.getLogger(InjectUtil.class);

	/**
	 * Use this if you have a URI of a model resource.
	 * 
	 * @param uri model URI, to determine the Xtext Language for which you want to @Inject dependencies
	 * @return instance of this util
	 */
	public static InjectUtil givenURI(URI uri) {
		return new InjectUtil(uri);
	}

	// NOTE We COULD have givenLang(String ext) as well here, BUT if we don't
	// need it, it be better & clearer for future to avoid it - I'm sure people
	// will misunderstand and abuse it! :(
	
	private final URI uri;
	
	private InjectUtil(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Get Instance from a DI container.
	 * 
	 * If at all feasible, avoid using this method, in favor of using @Inject ILangSpecificProvider.
     *
	 * @see com.odcgroup.workbench.core.di.ILangSpecificProviderImpl.get(URI)
	 */
	public <T> T getInstance(Class<T> klass) {
		return new ILangSpecificProviderImpl<T>(klass).get(uri);
	}

	@Override
	public String toString() {
		return "InjectUtil IResourceServiceProvider [from uri=" + uri + "]";
	}

	// NOTE We COULD have injectMembers() as well here, BUT we should do
	// everything we can not to ever need that. Normally, you can just use
	// @Inject and obtain the entire instance of your class from getInstance().
	// An injectMembers() would ONLY be needed for cases where existing instances,
	// not created by our code, need to be DI-augmented.  This should be rare.
	// Normally, it's much better to change (our) code which created the instance
	// of the class where you would want to use injectMembers() to use getInstance().
	
}
