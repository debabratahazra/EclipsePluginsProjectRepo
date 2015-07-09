package com.odcgroup.workbench.core.repository;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

public interface ILanguageRepository {

	/**
	 * @return the language name
	 */
	String getLanguageName();

	/**
	 * This method should be called to add specific user data to an indexed
	 * object (see XText). This method is called when Xtext attempt to index an
	 * object (see subclasses of DefaultResourceDescriptionStrategy)
	 * 
	 * @param eObject
	 *            the object to be indexed
	 * @param userData
	 *            the map that contains specific user data
	 */
	void createUserData(EObject eObject, Map<String, String> userData);

	/**
	 * @return all elements which match the given type. May not be
	 *         <code>null</code>.
	 */
	Iterable<IEObjectDescription> getExportedObjectsByType(EClass eClass);
	
	/**
	 * @return all elements which match the given type. May not be
	 *         <code>null</code>.
	 */
	Iterable<IEObjectDescription> getExportedObjectsByType(EObject context, EReference reference);

	/**
	 * @return all elements which match the given type and predicate. May not be
	 *         <code>null</code>.
	 */
	Iterable<IEObjectDescription> getExportedObjectsByType(EClass eClass, Function1<IEObjectDescription, Boolean> _predicate);
	
	/**
	 * @param type type of class
	 * @param name qualifiedName of the exported objects to find
	 * 
	 * @return all the elements matched by the given type and the qualifiedName
	 */
	Iterable<IEObjectDescription> getExportedObjects(final EClass type, final QualifiedName name);
	
	/**
	 * @return all elements which match the given type. May not be
	 *         <code>null</code>.
	 */
	Iterable<IEObjectDescription> getExportedObjectsByType(EObject context, EClass type);
	
	/**
	 * @return all elements which match the given type. May not be
	 *         <code>null</code>.
	 */
	Iterable<IEObjectDescription> getExportedObjectsByType(Resource resource, EClass type);
	
	/**
	 * @return all elements which match the given type and qualified name. May not be
	 *         <code>null</code>.
	 */
	Iterable<IEObjectDescription> getExportedObjects(Resource resource, EClass type, QualifiedName name, boolean ignoreCase);

}
