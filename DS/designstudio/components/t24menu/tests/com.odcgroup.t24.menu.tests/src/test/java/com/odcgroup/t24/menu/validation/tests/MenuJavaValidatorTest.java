package com.odcgroup.t24.menu.validation.tests;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.validation.IResourceValidator;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.menu.MenuInjectorProvider;
import com.odcgroup.t24.menu.validation.MenuJavaValidator;

/**
 * Tests for MenuJavaValidator.
 * 
 * @author vramya
 * 
 */
@RunWith(XtextRunner.class)
@InjectWith(MenuInjectorProvider.class)
public class MenuJavaValidatorTest {
	@Inject
	ResourceSet rs;
	@Inject
	ParseHelper<EObject> parseHelper;
	@Inject
	ValidatorTester<MenuJavaValidator> tester;
	@Inject
	IResourceValidator resourceValidator;

	/**
	 * Test validation of physical file name of resource matches the "name" in
	 * the model (of the "root" content EObject).
	 */
	@Test
	public void testCheckFileNameAndRootModelNameAreSame() {
		InputStream is = getClass().getResourceAsStream("/com/odcgroup/t24/menu/validation/tests/MyMenu.menu");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is, URI.createURI("com/odcgroup/t24/menu/validation/tests/MyMenu.menu"),
				null, rs);
		tester.validate(testModel).assertErrorContains("Menu file name and root model name are not same.");
	}

}
