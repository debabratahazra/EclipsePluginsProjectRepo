package com.odcgroup.workbench.tap.validation.internal.provider;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.internal.xtend.xtend.ast.Check;
import org.eclipse.xtend.expression.ExecutionContext;
import org.eclipse.xtend.typesystem.Type;

import com.odcgroup.workbench.tap.validation.ValidationCore;


public class CheckConstraintDescriptor extends AbstractConstraintDescriptor {

	private Check check;
	private String nsURI;
	private ExecutionContext eCtx;
	private EPackage ePackage;

	/**
	 * @param check
	 * @param nsURI
	 * @param eCtx
	 */
	public CheckConstraintDescriptor(Check check, String nsURI, ExecutionContext eCtx) {
		this.check = check;
		this.nsURI = nsURI;
		this.eCtx = eCtx;
		this.ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
	}

	/**
	 * @return the nsURI
	 */
	public String getNsURI() {
		return nsURI;
	}

	/**
	 * @return the check
	 */
	public Check getCheck() {
		return check;
	}

	/**
	 * @return
	 */
	public ExecutionContext getExecutionContext() {
		return this.eCtx;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getBody()
	 */
	public String getBody() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getDescription()
	 */
	public String getDescription() {
		return "This is a constraint provided through a oAW check file, which evaluates the expression '" +
			check.getConstraint().toString() + "'";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getEvaluationMode()
	 */
	public EvaluationMode<?> getEvaluationMode() {
		return EvaluationMode.BATCH;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getId()
	 */
	public String getId() {
		return check.getFileName() + ", line " + String.valueOf(check.getLine());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getMessagePattern()
	 */
	public String getMessagePattern() {
		return check.getMsg().toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getName()
	 */
	public String getName() {
		return check.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getPluginId()
	 */
	public String getPluginId() {
		return ValidationCore.PLUGIN_ID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getSeverity()
	 */
	public ConstraintSeverity getSeverity() {
		return check.isErrorCheck() ? ConstraintSeverity.ERROR : ConstraintSeverity.WARNING;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#getStatusCode()
	 */
	public int getStatusCode() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#targetsEvent(org.eclipse.emf.common.notify.Notification)
	 */
	public boolean targetsEvent(Notification notification) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.service.IConstraintDescriptor#targetsTypeOf(org.eclipse.emf.ecore.EObject)
	 */
	public boolean targetsTypeOf(EObject object) {
		if(object==null || object.eClass()==null) return false;
		if(!ePackage.equals(object.eClass().getEPackage())) return false;

		Type checkType = eCtx.getTypeForName(check.getType().toString()); // DS-8759: toString() used to be getValue() before Xtext upgrade (incl. OLD Xtend..).. hope this is right and covered by a test - I'm not 100% what I'm doing here!
		Type type = eCtx.getType(object);

		boolean result = checkType.isAssignableFrom(type);
		return result;
	}

}
