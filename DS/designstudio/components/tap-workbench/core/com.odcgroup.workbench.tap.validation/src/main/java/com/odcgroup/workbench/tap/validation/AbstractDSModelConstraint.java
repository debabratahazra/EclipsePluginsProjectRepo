package com.odcgroup.workbench.tap.validation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class should be used by all DS validation constraints instead of the EMF
 * class {@link AbstractModelConstraint}.
 * NPEs will be caught by this implementation, which makes sure that no validation
 * constraints are automatically deactivated by EMF (see DS-4745).
 * 
 * @author Kai Kreuzer
 * @since 6.0.0
 *
 */
abstract public class AbstractDSModelConstraint extends AbstractModelConstraint {
	private static Logger logger = LoggerFactory.getLogger(AbstractDSModelConstraint.class);
	@Override
	public IStatus validate(IValidationContext ctx) {
		try {
			return doValidate(ctx);
		} catch(RuntimeException e) {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			String junitErr = writer.toString();
			logger.error("Junit error rootcause.... " + junitErr);
			return new Status(IStatus.ERROR, ValidationCore.PLUGIN_ID, "An exception occurred while evaluating validation constraint " + (ctx!=null ? ctx.getCurrentConstraintId() : "")+" Junit error rootcause.... " + junitErr, e);
		}
	}

	abstract protected IStatus doValidate(IValidationContext ctx);
}
