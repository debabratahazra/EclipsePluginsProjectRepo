package com.odcgroup.domain.annotations;

import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfModelElement;

public class JavaAspectDS extends JavaAspect {
	
    public static void setPackage(MdfModelElement model, String javaPackageName) {
    	MdfAnnotationsUtil.setAnnotationValue(model, NAMESPACE_URI, PACKAGE, javaPackageName);
    }
  
}
