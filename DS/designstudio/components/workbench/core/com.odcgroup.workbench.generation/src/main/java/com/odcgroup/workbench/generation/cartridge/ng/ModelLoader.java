package com.odcgroup.workbench.generation.cartridge.ng;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Get to your models, e.g. from the CodeGenerator2's input URI (or other things found indirectly in the input).
 * 
 * Intentionally not related to/extending ResourceSet (albeit somewhat similar), to distinguish the slightly different idea.
 *
 * @author Michael Vorburger
 */
public interface ModelLoader {
	
	/**
	 * Obtain the EMF Resource, given the URI.
	 * 
	 * @param uri model URI
	 * @return EMF Resource, never null (throws Exception in case of loading problem)
	 * @throws Exception if anything went wrong
	 */
	Resource getResource(URI uri) throws Exception;

	/**
	 * Obtain the "root model object", given the URI.
	 * Just a strongly typed convenience wrapper; internally this will get the Resource, and then the root object from it.
	 * Will abstract and transparently handle particular special kinds of EMF Resources (think EFactory/ESON). 
	 * 
	 * @param uri model URI
	 * @param klass Gen. EMF Java interface (extends EObject)
	 * @return instance of gen. EMF Java *Impl, or null if EMF Resource at uri is empty (throws Exception in case of loading problem), never an EMF proxy
	 * @throws Exception if anything went wrong
	 */
	// This really should be <T extends EObject> instead of just <T>, but cannot because Mdf* doesn't extend EObject 
	<T> T getRootEObject(URI uri, Class<T> klass) throws Exception;
	
	/**
	 * Look-up a model object given it's (global!) name, and type.
	 * This uses the Xtext index, same as Navigate > Open Model Element.
	 * 
	 * @param context used to correctly resolve dependencies (typically your "current" model, from which what you are looking for can be "reached")
	 * @param qName fully qualified name (the one returned by IQualifiedNameProvider.
	 * @param eClass Gen. EMF Java interface (extends EObject) (see <MyDsl>Package.Literals.*)
	 * @return instance of gen. EMF Java *Impl, never an EMF proxy, or null if no such element was found
	 * @throws IllegalArgumentException if invalid argument was passed
	 * @throws IllegalStateException if something in internal data structures is unexpected 
	 */
	// This really should be <T extends EObject> instead of just <T>, but cannot because Mdf* doesn't extend EObject 
	<T> T getNamedEObject(EObject context, QualifiedName qName, EClass eClass);
	
	/**
	 * Like the non-orProxy() method variant, but (may.. if not pre-loaded)
	 * return an EMF Proxy. This (may) be faster, and should be used if all you
	 * need the return value for is to set...() it as reference into some other
	 * object. If you need to access any of the fields on the returned object,
	 * then do not use this (as they may be null).
	 */
	<T> T getNamedEObjectOrProxy(EObject context, QualifiedName qName, EClass eClass);
	
	/**
	 * Look-up a model object given a partial qualifiedName name as String, and an EClass of the expected returned object. 
	 * This uses the Xtext index, same as Navigate > Open Model Element.
	 * 
	 * @param context used to correctly resolve dependencies (typically your "current" model, from which what you are looking for can be "reached")
	 * @param partialQualifiedName partial qualified name (see XText IQualifiedNameProvider).
	 * @param eClass EMF EClass (see <MyDsl>Package.Literals.*)
	 * @return instance of gen. EMF Java *Impl, never an EMF proxy, or null if no such element was found
	 * @throws IllegalArgumentException if invalid argument was passed
	 * @throws IllegalStateException if something in internal data structures is unexpected 
	 */
	// This really should be <T extends EObject> instead of just <T>, but cannot because Mdf* doesn't extend EObject 
	<T> T getNamedEObject(EObject context, String partialQualifiedName, EClass eClass);

	/**
	 * Like the non-orProxy() method variant, but (may.. if not pre-loaded)
	 * return an EMF Proxy. This (may) be faster, and should be used if all you
	 * need the return value for is to set...() it as reference into some other
	 * object. If you need to access any of the fields on the returned object,
	 * then do not use this (as they may be null).
	 */
	<T> T getNamedEObjectOrProxy(EObject context, String partialQualifiedName, EClass eClass);
	
	/**
	 * Look-up a model object given a partial qualifiedName name, and an EClass of the expected returned object.
	 * This uses the Xtext index, same as Navigate > Open Model Element.
	 * 
	 * @param context used to correctly resolve dependencies (typically your "current" model, from which what you are looking for can be "reached")
	 * @param partialQualifiedName partial qualified name (see XText IQualifiedNameProvider)
	 * @param eClass EMF EClass (see <MyDsl>Package.Literals.*)
	 * @param ignorecase true if the partialQualifiedName search should not be case sensitive
	 * @param endsOnly true if the partialQualifiedName search should endsWith instead of contains 
	 * @return instance of gen. EMF Java *Impl, never an EMF proxy, or null if no such element was found
	 * @throws IllegalArgumentException if invalid argument was passed
	 * @throws IllegalStateException if something in internal data structures is unexpected 
	 */
	// This really should be <T extends EObject> instead of just <T>, but cannot because Mdf* doesn't extend EObject 
	<T> T getNamedEObject(EObject context, String partialQualifiedName, EClass eClass, boolean ignorecase, boolean endsOnly);

	/**
	 * Like the non-orProxy() method variant, but (may.. if not pre-loaded)
	 * return an EMF Proxy. This (may) be faster, and should be used if all you
	 * need the return value for is to set...() it as reference into some other
	 * object. If you need to access any of the fields on the returned object,
	 * then do not use this (as they may be null).
	 */
	<T> T getNamedEObjectOrProxy(EObject context, String partialQualifiedName, EClass eClass, boolean ignorecase, boolean endsOnly);
	
	/**
	 * Same as getNamedEObjectOrProxy() but looks for the full name. 
	 * We can pass multipple full names. The first EObject having a name matching exactelly one of the
	 * full names passed in the array will be returned.
	 */
	<T> T getEObjectByFullName(EObject context, final String[] fullyQualifiedName, final EClass type, boolean resolveProxy);
	

}
