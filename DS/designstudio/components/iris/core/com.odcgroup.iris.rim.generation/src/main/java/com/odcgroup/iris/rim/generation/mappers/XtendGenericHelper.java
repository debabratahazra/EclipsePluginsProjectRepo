package com.odcgroup.iris.rim.generation.mappers;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;

public class XtendGenericHelper {

	public static Object getNamedEObject(ModelLoader ml, EObject context, String pattern, EClass eClass) {
		Object o =  ml.getNamedEObject(context, pattern, eClass);
		return o;
	}

	
	public static Object getNamedEObject(ModelLoader ml, EObject context, String pattern, EClass eClass, final boolean ignoreCase, final boolean endsOnly ) {
		Object o = null /*DS-6675*/;// ml.getNamedEObject(context, pattern, eClass, ignoreCase, endsOnly);
		return o;
	}
	
	public static Object getNamedEObjectOrProxy(ModelLoader ml, EObject context, String pattern, EClass eClass, final boolean ignoreCase, final boolean endsOnly ) {
		Object o =  ml.getNamedEObjectOrProxy(context, pattern, eClass, ignoreCase, endsOnly);
		return o;
	}
	
}
