package com.odcgroup.workbench.generation.cartridge.ng;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess;

/**
 * New <a href="https://www.google.ch/search?q=tng ">Next Generation</a> ;) CodeGenerator API.
 * 
 * <p>Implementations must be stateless - do not "hang on" to resources between
 * invocations. This is enforced by the implementation by re-creating a new
 * instance of this for every model resource (which is also needed to inject
 * the correct model type specific context).  And, please, though shall not
 * not use trickery and resort to hacks using "static", or else... <a 
 * href="https://www.google.ch/search?q=dead+terrorist">"I'll kill you",
 * says Achmed</a>.</p>
 * 
 * <p>For backward compatibility reasons, until we'll change over completely,
 * CodeGenerator2 implementations will also have to implement the classic old
 * CodeGenerator interface. However, in NG mode (FeatureSwitches.newCodeGen),
 * their CodeGenerator.run(IProject, Collection<IOfsModelResource>, IFolder)
 * will never be called anymore.</p>
 * 
 * <p>Advantages of CodeGenerator2 NG over our "classic" CodeGenerator:<ul>
 *
 * <li>independent from IOfsProject & IOfsModelResource (and thus ModelResourceSet).
 * This makes it possible to test CodeGenerator2 using plain SE (i.e. non-OSGi) JUnits. 
 * It may also enable us to, later, run such cartridges as pure Maven plug-ins, 
 * outside of headless Eclipse.
 *
 * <li>actually works properly with Xtext; i.e. in these Generators you
 * actually CAN now finally follow references around resources, without any low level
 * tricks; like the infamous ModelURIConverter replaceDSResourceURIByStandardPlatformURI
 * (because here we use of standard platform:// URIs instead of old
 * proprietary resource:// URI scheme)
 *
 * <li>based on a per-model instead of a Collection contract; this: a) makes
 * it possible for the framework to run things in a different order than before
 * (each Cartridge, model by model; instead of all models cartridge by cartridge;
 * later possibly parallelized..), b) automatically takes care of monitor progress
 * incl. isCanceled(); c) enforces statelessness.
 * 
 * <li>supports Dependency @Inject-ion (bienvenue DI in DS - first use!). Note
 * that the correct language specific dependencies will be @Inject'ed.
 * 
 * <li>uses IFileSystemAccess (from Xtext) instead of IFolder (which is easier
 * and more obvious how to use, particularly for DS contributors with SE but
 * no Eclipse API know-how)
 * 
 * <li>throws Exception - most cartridges don't want to and shouldn't have to
 * deal with exception handling themselves.
 * 
 * <p>This API is obviously inspired by org.eclipse.xtext.generator.IGenerator but
 * is based on an URI + ModelLoader instead of a Resource, to avoid pre-loading
 * and make it clear that a Code Generator will typically check if it wants to
 * even handle a model (e.g. by checking the extension of the Resource), so
 * avoiding unnecessary loading.  (This would also be possible by handing
 * in an unloaded Resource, but like this, the contract is much clearer.)
 * 
 * <p>If you already have an existing IGenerator, note that it's trivial
 * to @Inject it into a 3 liner CodeGenerator2 implementation; see e.g.
 * the DSNGCodeGenerator2SampleImpl for a how-to.</p>
 * 
 * <p>PS: Contrary to Xtext's IGenerator, this API also isn't necessarily receiving
 * an EMF Resource as input - the URI, hypothetically, could represent e.g.
 * a plain *.properties text file or something like that as well (even
 * though this is not currently used in practice in DS).</p>
 * 
 * @see ResourceGenerator2 which, contrary to this, is for generating from one given resource
 * 
 * @author Michael Vorburger
 */
public interface CodeGenerator2 {

	/**
	 * Generate your.. stuff.
	 * 
	 * @param input an Eclipse platform:// URI (guaranteed; not a DS resource:// URI, like IOfsModelResource's) 
	 * @param loader helper make it easy to get actual models objects from the input URI (use only if Generator is going to actually consume the URI..) 
	 * @param fsa utility to write out what this Generator produces
	 */
	void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception;
	
}
