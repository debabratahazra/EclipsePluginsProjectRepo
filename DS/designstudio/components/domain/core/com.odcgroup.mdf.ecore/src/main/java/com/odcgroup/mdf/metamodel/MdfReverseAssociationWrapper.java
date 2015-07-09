package com.odcgroup.mdf.metamodel;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

@SuppressWarnings("serial")
public class MdfReverseAssociationWrapper extends MinimalEObjectImpl.Container implements MdfReverseAssociation {
	
	private MdfReverseAssociation reverse;
	
	public MdfReverseAssociationWrapper(MdfReverseAssociation reverse) {
		super();
		this.reverse = reverse;
	}
	
	public MdfReverseAssociation getInnerReverse() {
		return reverse;
	}

	
	public MdfAssociation getAssociation() {
		return reverse.getAssociation();
	}

	
	public int getMultiplicity() {
		return reverse.getMultiplicity();
	}

	
	public MdfClass getParentClass() {
		return reverse.getParentClass();
	}

	
	public MdfEntity getType() {
		return reverse.getType();
	}

	
	public boolean isBusinessKey() {
		return reverse.isBusinessKey();
	}

	
	public boolean isPrimaryKey() {
		return reverse.isPrimaryKey();
	}

	
	public boolean isRequired() {
		return reverse.isRequired();
	}

	
	public MdfAnnotation getAnnotation(String arg0, String arg1) {
		return reverse.getAnnotation(arg0, arg1);
	}

	
	public List getAnnotations() {
		return reverse.getAnnotations();
	}

	
	public List getAnnotations(String arg0) {
		return reverse.getAnnotations(arg0);
	}

	

	public String getDocumentation() {
		return reverse.getDocumentation();
	}

	
	public String getName() {
		return reverse.getName();
	}

	
	public MdfName getQualifiedName() {
		return reverse.getQualifiedName();
	}

	
	public boolean isDeprecated() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
    public int getVisibility() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
	public EObject getDeprecationInfo() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
	public int getAccess() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
	public List getComments() {  throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
	public Object getExtension(String arg0) { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }
	public Map getExtensions() { throw new UnsupportedOperationException("Removed during the mdf-api/core split"); }

}
