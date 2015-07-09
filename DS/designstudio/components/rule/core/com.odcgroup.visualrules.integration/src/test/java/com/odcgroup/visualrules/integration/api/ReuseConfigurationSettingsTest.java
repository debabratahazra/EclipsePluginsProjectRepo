/*
 * Copyright (C) 2006 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

package com.odcgroup.visualrules.integration.api;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.workbench.core.OfsCore;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.model.core.resource.ResourceFacade;
import de.visualrules.ui.integration.VisualRulesIntegration;

public class ReuseConfigurationSettingsTest extends TestCase
{
   private IProject iProject;
   private IDataModelIntegration dataModelIntegration;

   protected void setUp() throws Exception
   {
      super.setUp();
      dataModelIntegration = VisualRulesIntegration.getDataModelIntegration();

      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      iProject = workspace.getRoot().getProject(getName());
      final IProjectDescription description = workspace.newProjectDescription(iProject.getName());
      iProject.create(description, null);
      iProject.open(null);
      // add OFS nature
      description.setNatureIds(new String[] {OfsCore.NATURE_ID});
      iProject.setDescription(description, null);
      
		IFolder ruleFolder = iProject.getFolder("rules");
		ruleFolder.create(true, true, null);
		String ruleName = "rules_123";
		IFile file = ruleFolder.getFile(ruleName + ".vrmodel");
		if(!file.exists()) {
			InputStream is = getRuleFileInputStream(ruleName);
			file.create(is, false, null);
		}
      
//      modelfile = iProject.getFile("model.vrmodel");
//      model = BaseFactory.eINSTANCE.createRuleModel();
//      model.setName("model");
//      RulePackage pack = BaseFactory.eINSTANCE.createRulePackage();
//      pack.setName("pack");
//
//      ResourceFacade.getResourceManager().store(model, modelfile, new ResourceSetImpl());

//      model = (RuleModel) ResourceFacade.getResourceManager().openFile(modelfile, null);

   }

   @Override
   protected void tearDown() throws Exception
   {
      ResourceFacade.getResourceManager().unloadAll();
      iProject.delete(true, null);
      super.tearDown();
   }

   @SuppressWarnings("unchecked")
	public void testEditReuseSettings() throws Exception {
      de.visualrules.integration.model.RulePackage pkg1 = IntegrationFactory.eINSTANCE.createRulePackage();
      pkg1.setName("package1");
      de.visualrules.integration.model.RulePackage pkg2 = IntegrationFactory.eINSTANCE.createRulePackage();
      pkg2.setName("package2");
      pkg2.getImports().add("vrpath:/rules_123/pkg1");
      
      // create the packages and the class
      dataModelIntegration.merge(pkg1, "vrpath:/rules_123", null);

      // create the packages and the class
      dataModelIntegration.merge(pkg2, "vrpath:/rules_123", null);
      
      // save and generate
      dataModelIntegration.save("vrpath:/rules_123");
      
      pkg2 = dataModelIntegration.getPackage("vrpath:/rules_123/pkg2");
      
      assert(pkg2.getImports().contains("vrpath:/rules_123/pkg1"));
      
   }

	private static InputStream getRuleFileInputStream(String ruleName) throws CoreException { 
		InputStream inputStream =
			Thread.currentThread().getContextClassLoader().getResourceAsStream("com/odcgroup/visualrules/integration/rules.vrmodel");
		try {
			String fileContent = IOUtils.toString(inputStream);
			String newContent = fileContent.replace("${rulename}", ruleName);
			return IOUtils.toInputStream(newContent); 
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID, "Could not read rule model template file", e));
		}
	}

}
