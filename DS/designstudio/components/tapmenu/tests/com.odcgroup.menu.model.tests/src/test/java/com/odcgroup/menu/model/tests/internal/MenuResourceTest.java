package com.odcgroup.menu.model.tests.internal;

import static com.odcgroup.workbench.core.tests.util.TestTankResourcesTestUtil.loadTestTankResourceAsString;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.commons.lang.StringUtils;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.ui.AbstractWorkbenchTest;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.odcgroup.menu.model.MenuFactory;
import com.odcgroup.menu.model.MenuInjectorProvider;
import com.odcgroup.menu.model.MenuItem;
import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.menu.model.Translation;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.dsl.resources.AbstractDSLResource;

@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(MenuInjectorProvider.class)
public class MenuResourceTest extends AbstractWorkbenchTest {
	
	private static String MENU_MODEL = "/menu/Default/gen/MenuGen.menu";
	
	@Inject 
	private Provider<Serializer> serializerProvider;
	
	private MenuRoot createSampleMenu() {
		MenuRoot model = MenuFactory.eINSTANCE.createMenuRoot();
		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("menu"));
		MenuItem rootItem = MenuFactory.eINSTANCE.createMenuItem();
		rootItem.setName("menu.aaa.activity");
		rootItem.setShortcut("CTRL+ALT+F3");
		
		MenuItem homeItem = MenuFactory.eINSTANCE.createMenuItem();
		homeItem.setName("menu.aaa.Home");
		homeItem.setShortcut("CTRL+F2");
		rootItem.getSubmenus().add(homeItem);
		
		MenuItem subitem01 = MenuFactory.eINSTANCE.createMenuItem();
		subitem01.setName("menu.aaa.activity.Home.Context");
		subitem01.setPageflow("/activity/aaa/pageflow/start/domain-pageflow-DomainContext/DomainContext");
		subitem01.setSecurityRole("PCK_DS_CONTEXT_MENU");
		subitem01.setShortcut("CTRL+1");
		
		homeItem.getSubmenus().add(subitem01);
		
		Translation translation = MenuFactory.eINSTANCE.createTranslation();
		translation.setLanguage("en");
		translation.setMessage("english");
		subitem01.getLabels().add(translation);
		
		model.setRootItem(rootItem);
		return model;
	}
	
	private String removeEOL(String str) {
		str = StringUtils.remove(str, '\n');
		str = StringUtils.remove(str, '\r');
		return str;
	}
	
	private String resourceFileAsString(String path) throws IOException {
		return removeEOL(Resources.toString(this.getClass().getResource(path), Charsets.UTF_8));
	}
	
	@Test
	public void testCommentHeader() {
		try {
			String content = loadTestTankResourceAsString(this.getClass(), MENU_MODEL);
			Assert.assertTrue(content.startsWith(AbstractDSLResource.DSL_FILE_HEADER_COMMENT));
		} catch (Throwable ex) {
			throw Exceptions.sneakyThrow(ex);
		}
	}
	
	@Test
	public void testResourceContents() {
		try {
			String expected = resourceFileAsString("/com/odcgroup/menu/model/tests/ExpectedMenu.menu");
			Serializer serializer = serializerProvider.get();
			String content = removeEOL(serializer.serialize(createSampleMenu()));
			Assert.assertTrue(StringUtils.equals(expected, content));
		} catch (Throwable ex) {
			throw Exceptions.sneakyThrow(ex);
		}
	}


}
