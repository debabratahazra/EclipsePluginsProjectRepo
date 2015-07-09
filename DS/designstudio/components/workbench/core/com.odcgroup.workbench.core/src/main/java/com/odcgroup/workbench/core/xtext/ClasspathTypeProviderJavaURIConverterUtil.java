package com.odcgroup.workbench.core.xtext;

import java.lang.reflect.Field;

import org.eclipse.emf.ecore.resource.URIConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Util to be able to access the "existing" field of the private inner
 * ClasspathTypeProvider.JavaURIConverter Xtext class, needed in
 * OfsResourceHelper.getOfsProject(Resource) to "fish out" the
 * URIConverter that delegates to.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=433226
 * @see http://rd.oams.com/browse/DS-7338
 * 
 * @author Michael Vorburger
 */
public class ClasspathTypeProviderJavaURIConverterUtil {
	private static final Logger logger = LoggerFactory.getLogger(ClasspathTypeProviderJavaURIConverterUtil.class);	

	private static Class<? extends URIConverter> classpathTypeProviderJavaURIConverterClass;
	private static Field classpathTypeProviderJavaURIConverterExistingField;
	
	/**
	 * Unwrap.
	 *  
	 * @param original an URIConverter, which MAY be an org.eclipse.xtext.common.types.access.impl.ClasspathTypeProvider.JavaURIConverter, or not
	 * @return the "existing" (delegating to) original URIConverter inside the JavaURIConverter - or the original, if it wasn't a JavaURIConverter
	 */
	public static URIConverter getUnderlyingExistingURIConverterOrSame(URIConverter original) {
		Preconditions.checkNotNull(original, "original == null");
		Class<? extends URIConverter> clazz = original.getClass();
		if (isClasspathTypeProviderJavaURIConverterClass(clazz)) {
			Object existing = getExisting(original);
			return (URIConverter) existing;
		} else {
			return original;
		}
	}

	private static Object getExisting(URIConverter original) {
		try {
			return classpathTypeProviderJavaURIConverterExistingField.get(original);
		} catch (Exception e) {
			final String msg = "Unexpected Exception when trying to use Reflection to get private field named 'existing', programming error; this should never have happened?! clazz: " + original.getClass().toString();
			logger.error(msg, e);
			throw new IllegalStateException(msg, e);
		}
	}

	private static boolean isClasspathTypeProviderJavaURIConverterClass(Class<? extends URIConverter> clazz) {
		if (classpathTypeProviderJavaURIConverterClass != null) {
			return classpathTypeProviderJavaURIConverterClass.equals(clazz);
		} else {
			try {
				classpathTypeProviderJavaURIConverterExistingField = clazz.getDeclaredField("existing");
				classpathTypeProviderJavaURIConverterExistingField.setAccessible(true);
				classpathTypeProviderJavaURIConverterClass = clazz;
				return true;
			} catch (NoSuchFieldException e) {
				// Nope, it's not a ClasspathTypeProvider.JavaURIConverter
				return false;
			} catch (RuntimeException e) {
				final String msg = "Unexpected Exception when trying to use Reflection to getDeclaredField() & setAccessible(true), programming error; this should never have happened?! clazz: " + clazz.toString();
				logger.error(msg, e);
				throw new IllegalStateException(msg, e);
			}
		}
		
	}
}
