package com.odcgroup.t24.menu.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.edge.t24ui.CompositeScreen;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;

/**
 * DS-7509 problem non-regression test.
 * 
 * @author phanikumark & mvorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(MenuWithDependenciesInjectorProvider.class)
public class DS7509MenuEnquiryReferenceTest {

	@Inject ResourceSet resourceSet;
	EIO eio;
	
	@Test
	public void testMenu2EnquiryReference() throws Exception {
		eio = new EIO(resourceSet);
		ModelLoader ml = new ModelLoaderImpl(resourceSet);
		
		Enquiry dlenquiry = ml.getRootEObject(eio.getURI("ACCT.BAL.TODAY.enquiry", getClass()), Enquiry.class);
		assertNotNull(dlenquiry);
		assertFalse(dlenquiry.eIsProxy());
		
		URI uri = eio.getURI("TestMenu.menu", getClass());
		assertNotNull(uri);
		
		MenuRoot root = ml.getRootEObject(uri, MenuRoot.class);
		assertNotNull(root);
		assertFalse(root.eIsProxy());
		Enquiry enquiry = root.getRootItem().getEnquiry();
		assertNotNull(enquiry);
		assertFalse(enquiry.eIsProxy());
	}
	
	@Test
	public void testMenu2MenuReference() throws Exception {
		eio = new EIO(resourceSet);
		ModelLoader ml = new ModelLoaderImpl(resourceSet);
		
		MenuRoot dlmenu = ml.getRootEObject(eio.getURI("TestMenu.menu", getClass()), MenuRoot.class);
		assertNotNull(dlmenu);
		assertFalse(dlmenu.eIsProxy());
		
		URI uri = eio.getURI("TestMenu2.menu", getClass());
		assertNotNull(uri);
		
		MenuRoot root = ml.getRootEObject(uri, MenuRoot.class);
		assertNotNull(root);
		assertFalse(root.eIsProxy());
		MenuItem menu = root.getRootItem();
		assertNotNull(menu);
		MenuRoot incmenu = menu.getIncludedMenu();
		assertNotNull(incmenu);
		assertFalse(incmenu.eIsProxy());		
	}
	
	@Test
	public void testMenu2VersionReference() throws Exception {
		eio = new EIO(resourceSet);
		ModelLoader ml = new ModelLoaderImpl(resourceSet);
		
		Version dlver = ml.getRootEObject(eio.getURI("CUSTOMER,CREATE.version", getClass()), Version.class);
		assertNotNull(dlver);
		assertFalse(dlver.eIsProxy());
		
		URI uri = eio.getURI("TestMenu2.menu", getClass());
		assertNotNull(uri);
		
		MenuRoot root = ml.getRootEObject(uri, MenuRoot.class);
		assertNotNull(root);
		assertFalse(root.eIsProxy());
		MenuItem menu = root.getRootItem();
		assertNotNull(menu);
		List<MenuItem> submenus = menu.getSubmenus();
		assertTrue(submenus.size() == 2);
		MenuItem menuitem = submenus.get(0);
		Version version = menuitem.getVersion();
		assertNotNull(version);
		assertFalse(version.eIsProxy());		
	}
	
	@Test
	public void testMenu2COSReference() throws Exception {
		eio = new EIO(resourceSet);
		ModelLoader ml = new ModelLoaderImpl(resourceSet);
		
		CompositeScreen dlcos = ml.getRootEObject(eio.getURI("TestCOS.eson", getClass()), CompositeScreen.class);
		assertNotNull(dlcos);
		assertFalse(dlcos.eIsProxy());
		
		URI uri = eio.getURI("TestMenu2.menu", getClass());
		assertNotNull(uri);
		
		MenuRoot root = ml.getRootEObject(uri, MenuRoot.class);
		assertNotNull(root);
		assertFalse(root.eIsProxy());
		MenuItem menu = root.getRootItem();
		assertNotNull(menu);
		List<MenuItem> submenus = menu.getSubmenus();
		assertTrue(submenus.size() == 2);
		MenuItem menuitem = submenus.get(1);
		CompositeScreen cos = menuitem.getCompositeScreen();
		assertNotNull(cos);
		assertFalse(cos.eIsProxy());	
		assertEquals("TestCOS", cos.getName());
	}
}
