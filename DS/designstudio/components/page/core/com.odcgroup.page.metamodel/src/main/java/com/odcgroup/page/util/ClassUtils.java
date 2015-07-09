package com.odcgroup.page.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;

/**
 * Utility methods for classes.
 * 
 * @author Gary Hayes
 */
public class ClassUtils {	

	/**
	 * Creates a new instance of the specified class. If the class is NOT assignable to the specified Class a
	 * PageException is thrown.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use to create the instance
	 * @param className
	 *            The name of the Class to create
	 * @param assignableClass
	 *            The Class to which the newly created instance must be assignable
	 * @return Object The newly created instance
	 * @throws PageException
	 *             Thrown when an error occurs
	 */
	public static Object newInstance(ClassLoader classLoader, String className, Class assignableClass) {
		return newInstance(classLoader, className, new Object[]{}, assignableClass);
	}
	
	/**
	 * Creates a new instance of the specified class. If the class is NOT assignable to the specified Class a
	 * PageException is thrown.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use to create the instance
	 * @param className
	 *            The name of the Class to create
	 * @param args The arguments to the constructor
	 * @param assignableClass
	 *            The Class to which the newly created instance must be assignable
	 * @return Object The newly created instance
	 * @throws PageException
	 *             Thrown when an error occurs
	 */
	public static Object newInstance(ClassLoader classLoader, String className, Object[] args, Class assignableClass) {
		Object obj = newInstance(classLoader, className, args);
		if (!(org.apache.commons.lang.ClassUtils.isAssignable(obj.getClass(), assignableClass))) {
			Logger.error("Class " + className + " is not a : " + assignableClass.getName());
			throw new PageException("Class " + className + " is not a  " + assignableClass.getName());
		}

		return obj;
	}	

	/**
	 * Creates a new instance of the specified class.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use to create the instance
	 * @param className
	 *            The name of the Class to create
	 * @return Object The newly created instance
	 * @throws PageException
	 *             Thrown when an error occurs
	 */
	public static Object newInstance(ClassLoader classLoader, String className) {
		return newInstance(classLoader, className, new Object[]{});
	}
	
	/**
	 * Creates a new instance of the specified class.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use to create the instance
	 * @param className
	 *            The name of the Class to create
	 * @param arg The argument to the constructor
	 * @return Object The newly created instance
	 * @throws PageException
	 *             Thrown when an error occurs
	 */
	public static Object newInstance(ClassLoader classLoader, String className, Object arg) {
		return newInstance(classLoader, className, new Object[]{arg});
	}
	
	/**
	 * Creates a new instance of the specified class.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use to create the instance
	 * @param className
	 *            The name of the Class to create
	 * @param arg1 The first argument to the constructor
	 * @param arg2 The second argument to the constructor
	 * @return Object The newly created instance
	 * @throws PageException
	 *             Thrown when an error occurs
	 */
	public static Object newInstance(ClassLoader classLoader, String className, Object arg1, Object arg2) {
		return newInstance(classLoader, className, new Object[]{arg1, arg2});
	}	
	
	/**
	 * Creates a new instance of the specified class.
	 * 
	 * @param classLoader
	 *            The ClassLoader to use to create the instance
	 * @param className
	 *            The name of the Class to create
	 * @param args The arguments to the constructor
	 * @return Object The newly created instance
	 * @throws PageException
	 *             Thrown when an error occurs
	 */
	public static Object newInstance(ClassLoader classLoader, String className, Object[] args) {
		
		String errMessage = "Unable to instantiate class: " + className;
		try {
			Class[] parameterTypes = convertToClasses(args);
			Class clazz = classLoader.loadClass(className);
			Constructor c = getConstructor(clazz, parameterTypes);
			return c.newInstance(args);
		} catch (ClassNotFoundException ex) {
			Throwable target = ex.getCause();
			Status status = new Status(IStatus.ERROR, "page.designer", errMessage, target);
			throw new PageException(ex, status);
		} catch (InstantiationException ex) {
			Throwable target = ex.getCause();
			Status status = new Status(IStatus.ERROR, "page.designer", errMessage, target);
			throw new PageException(ex, status);
		} catch (IllegalAccessException ex) {
			Throwable target= ex.getCause();
			Status status = new Status(IStatus.ERROR, "page.designer", errMessage, target);
			throw new PageException(ex, status);
		} catch (InvocationTargetException ex) {
			Throwable target = ex.getTargetException();
			Status status = new Status(IStatus.ERROR, "page.designer", errMessage, target);
			throw new PageException(ex, status);
		}
	}	
	
	/**
	 * Gets the constructor for the Class using the specified ClassLoader. We first
	 * look for an identical match. If no exact match is found we try to find a 
	 * Constructor for which the parameter types are assignable to the constructor
	 * arguments. Note that only public Constructors are searched for assignability.
	 * 
	 * @param clazz The Class
	 * @param parameterTypes The parameter types
	 * @return Constructor The Constructor
	 * @throws PageException Thrown if no Constructor can be found which matches the arguments
	 */
	public static Constructor getConstructor(Class clazz, Class[] parameterTypes)
	{
		try {
			// Look for an exact match
			return clazz.getConstructor(parameterTypes);
		} catch(NoSuchMethodException nsme) {
			// No exact match exists. Look for an assignable match
			Constructor c = getCompatibleConstructor(clazz, parameterTypes);
			if (c != null) {
				return c;
			}
			// Not found
			throw new PageException(nsme);
		}
	}
	
	/**
	 * Return true if the class defined by className in the class loader classloader has a constructor with all the
	 * mentioned parameterType. Otherwise return false.
	 * @param classLoader
	 * @param className
	 * @param parameterTypes
	 * @return
	 */
	public static boolean hasConstructor(ClassLoader classLoader, String className, Class<?>[] parameterTypes) {
		try {
			// Look for an exact match
			Class<?> clazz = classLoader.loadClass(className);
			clazz.getConstructor(parameterTypes);
			return true;
		} catch(NoSuchMethodException nsme) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * Tries to find a constructor for which the parameter types are assignable
	 * to the constructor arguments.
	 * Note that only public Constructors are searched for assignability.
	 * 
	 * @param clazz The Class
	 * @param parameterTypes The parameter types
	 * @return Constructor The Constructor
	 */
	private static Constructor getCompatibleConstructor(Class clazz, Class[] parameterTypes) {
		Constructor[] constructors = clazz.getConstructors();
		for (int i = 0; i < constructors.length; ++i) {
			Constructor c = constructors[i];
			Class[] pts = c.getParameterTypes();
			if (pts.length != parameterTypes.length) {
				// Not the same length
				continue;
			}
			for (int j = 0; j < parameterTypes.length; ++j) {
				Class cp = parameterTypes[j];
				Class ca = pts[j];
				if (! ca.isAssignableFrom(cp)) {
					// Not a match. Try the next constructor
					break;
				}
			}
			
			// Passed both tests. OK
			return c;
		}
		
		// Not found
		return null;
	}
	
	/**
	 * Converts the array of Objects to an array of Classes
	 * 
	 * @param args The array of Objects to convert
	 * @return Class[] The array of Classes
	 */
	private static Class[] convertToClasses(Object[] args) {
		Class[] classes = new Class[args.length];
		for (int i = 0; i < args.length; ++i) {
			classes[i] = args[i].getClass();
		}
		return classes;
	}
}