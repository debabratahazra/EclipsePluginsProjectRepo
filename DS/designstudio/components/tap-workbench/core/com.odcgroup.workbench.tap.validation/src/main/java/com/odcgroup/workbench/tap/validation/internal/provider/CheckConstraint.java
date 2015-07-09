package com.odcgroup.workbench.tap.validation.internal.provider;

import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.issues.IssuesImpl;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.internal.xtend.xtend.ast.Check;
import org.eclipse.xtend.expression.ExecutionContext;

public class CheckConstraint implements IModelConstraint {

	private Check check;
	private String nsURI;
	private ExecutionContext eCtx;
	private IConstraintDescriptor descriptor;

	/**
	 * @param check
	 * @param nsURI
	 * @param checkFile
	 */
	public CheckConstraint(IConstraintDescriptor descriptor, ExecutionContext eCtx, Check check, String nsURI) {
		this.eCtx = eCtx;
		this.check = check;
		this.nsURI = nsURI;
		this.descriptor = descriptor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		Issues issues = new IssuesImpl();
        check.validate(eCtx, Collections.singleton(eObj), issues, false);
		if(issues.getIssues().length>0) {
			String message = issues.getIssues()[0].getMessage();
			IStatus status = createFailureStatus(ctx, message, eObj);
			return status;
		} else {
			return ctx.createSuccessStatus();
		}
	}
	
	/**
	 * @param ctx
	 * @param message
	 * @param target
	 * @return
	 */
	private IStatus createFailureStatus(IValidationContext ctx, String message, EObject target) {
		return new ConstraintStatus(this, target, message, ctx.getResultLocus());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.model.IModelConstraint#getDescriptor()
	 */
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}

	
}
