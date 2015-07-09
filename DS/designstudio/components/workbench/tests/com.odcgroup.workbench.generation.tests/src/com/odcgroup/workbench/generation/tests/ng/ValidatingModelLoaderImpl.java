package com.odcgroup.workbench.generation.tests.ng;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;

/**
 * ModelLoaderImpl extension which validates for errors.
 * 
 * In production this doesn't make sense (as it's costly, and because the
 * Builder would already have added error markers), but when running standalone
 * tests in development, this is VERY useful.
 * 
 * @author Michael Vorburger
 */
public class ValidatingModelLoaderImpl extends ModelLoaderImpl {

	protected @Inject BetterValidationTestHelper validationTestHelper;  
	
	@Inject
	public ValidatingModelLoaderImpl(ResourceSet rs) {
		super(rs);
	}

	@Override
	public Resource getResource(URI uri) throws Exception {
		Resource r = super.getResource(uri);
		if (r.getContents() == null || r.getContents().isEmpty())
			return r;
		EObject eo = getRootEObject(r);
		validationTestHelper.assertNoErrors(eo);
		return r;
	}
	
	private static class BetterValidationTestHelper extends ValidationTestHelper {
		@Override
		protected StringBuilder getIssuesAsString(EObject model, Iterable<Issue> issues, StringBuilder result) {
			for(Issue issue: issues) {
				result.append(issue.getSeverity())
				.append(" (")
				.append(issue.getCode()) 
				.append(") '")
				.append(issue.getMessage());
				final URI uriToProblem = issue.getUriToProblem();
				if (uriToProblem != null) {
					EObject eObject = model.eResource().getResourceSet().getEObject(uriToProblem, true);
					result.append("' on ")
					.append(eObject.eClass().getName());
				}
				result.append("\n");
			}
			return result;
		}
	}
		 	
}
