package com.odcgroup.aaa.core.internal.properties;

import org.eclipse.core.expressions.PropertyTester;

import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;

public class AAAPackagePropertyTester extends PropertyTester {
	
	/* 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {

		if (!"packageName".equals(property)) {
			return false;
		}

		if (receiver instanceof IOfsModelPackage) {
			IOfsModelPackage ofsPackage = (IOfsModelPackage)receiver;
			// Full path ?
			if (expectedValue instanceof String && 
					((String)expectedValue).contains(".")) {
				String fullPackage = getFullPackage(ofsPackage);
				return fullPackage.equals(expectedValue);
			} else { // Relative path
				return ofsPackage.getName().equals(expectedValue);
			}
		}
		
		if (receiver instanceof IOfsModelResource) {
			IOfsModelResource ofsModelResource = (IOfsModelResource)receiver;
			String resourcePath = ofsModelResource.getFullPath().toPortableString();
			int lastSlash = resourcePath.lastIndexOf("/");
			if (lastSlash == -1) {
				return false;
			}
			String fullPath = resourcePath.substring(0, lastSlash);
			String lastPackage = fullPath.substring(fullPath.lastIndexOf("/")+1);
			return lastPackage.equals(expectedValue);
		}
		
		return false;
	}
	
	/**
	 * @param ofsPackage
	 * @return
	 */
	private String getFullPackage(IOfsModelPackage ofsPackage) {
		String fullPackage = ofsPackage.getName();
		IOfsModelPackage currentOfsPackage = ofsPackage;
		while (currentOfsPackage.getParent() != null && 
				currentOfsPackage.getParent() instanceof IOfsModelPackage) {
			currentOfsPackage = (IOfsModelPackage)currentOfsPackage.getParent();
			fullPackage =  currentOfsPackage.getName() + "." + fullPackage;
		}
		return fullPackage;
		
	}

	public AAAPackagePropertyTester() {
		
	}

}
