package com.odcgroup.service.model.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.Argument;
import com.odcgroup.service.model.component.ComponentPackage;
import com.odcgroup.service.model.component.InOutType;
import com.odcgroup.service.model.component.Method;
import com.odcgroup.service.model.component.Multiplicity;

/**
 * Validator.
 *
 * @author rameshsampenga
 *
 */
public class ComponentJavaValidator extends AbstractComponentJavaValidator {
	
	public static final String ERR_OUTGOING_PRIMITIVE = "outgoingprimitive";

	@Check
	public void checkAnyOutDirectionOfPrimitiveTypes(Argument argument) {

		EObject eContainer = argument.eContainer();
		if (eContainer instanceof Method) {

			// Evaluating only for 'external method' s
			if (((Method) eContainer).getAccessSpecifier() == AccessSpecifier.EXTERNAL) {

				MdfEntity type = argument.getType();
				InOutType inout = argument.getInout();
				Multiplicity multiplicity = argument.getMultiplicity();

				if (type.isPrimitiveType() && (inout == InOutType.OUT || inout == InOutType.INOUT)
						&& (multiplicity == Multiplicity.OPTIONAL || multiplicity == Multiplicity.MANDATORY)) {

					error("Out direction of primitive type '" + type.getName() + "' with multiplicity '"
							+ multiplicity.getLiteral() + "' is not allowed in external methods", argument,
							ComponentPackage.Literals.ARGUMENT__NAME, ERR_OUTGOING_PRIMITIVE);
				}
			}
		}
	}

}
