package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Test class for IFModelUtil
 *
 * @author gasampong
 *
 */
public class IFModelUtilTest extends AbstractDSUnitTest{
	
	@Before
	public void setUp() throws Exception{
		createIFProject();	
	}
	
	private void createIFProject()throws Exception{
    	IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("TestIntegrationProject");    
        project.create(null);
        project.open(null);

        IProjectDescription descr = project.getDescription();
		descr.setNatureIds(new String[] { OfsCore.NATURE_ID, "com.temenos.tws.consumer.plugin.natures.TWSConsumerNature"});
		project.setDescription(descr, null);
        new OfsProjectInitializer().updateConfiguration(project, null);
        IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
        ofsProjects.put("TestIntegrationProject", ofsProject);        
        
		copyFromClasspathToModelsProject(ofsProject,getModelsNeededForTests());        
	}
	
	@After
	public void tearDown() throws Exception {
		// Delete of models project was failing as few tasks were not completed,
		// hence code written with in runnable
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				deleteModelsProjects();
			}
		};
		ResourcesPlugin.getWorkspace().run(runnable, null);
	}
		
	private String[] getModelsNeededForTests() {
		return new String[] { "/events/FXSwap.event","/events/MMInput.event",
				"/flows/FXSWAP.flow","/flows/MMPlacement.flow",
				"/schemas/FXSWAP/Common-EventCommon.xsd",
				"/schemas/FXSWAP/PWEXT-FXSWAP.xsd",
				"/schemas/FXSWAP/JBPMIF-BatchAaAccountClosureDetailsInputFlow",
				"/schemas/MMPlacement/Common-EventCommon.xsd",
				"/schemas/MMPlacement/PWEXT-MMPlacement.xsd"};
	}	
	
	@Test
	public void testIFSchemaMapValid(){
		HumanTaskNodeWrapper wrapper = new HumanTaskNodeWrapper();
		wrapper.setPropertyValue("Comment","FOREX, SC.SWAP A 1234");
		IFModelUtil util = new IFModelUtil(wrapper);
		Map<String,IFile> ifSchemas = util.getIFSchemaMap();
		assertEquals(1,ifSchemas.size());
		assertTrue(ifSchemas.containsKey("PWEXT-FXSWAP"));	
		Map<String,IFile> ifCommonSchemas = util.getIFCommonSchemaMap();
		assertEquals(1,ifCommonSchemas.size());
		assertTrue(ifCommonSchemas.containsKey("Common-EventCommon"));
	}
	
	@Test
	public void testIFSchemaMapValidWithFieldMappings(){
		HumanTaskNodeWrapper wrapper = new HumanTaskNodeWrapper();
		wrapper.setPropertyValue("Comment","FOREX,SC.SWAP A 1234 name=george mnemonic=ga");
		IFModelUtil util = new IFModelUtil(wrapper);
		Map<String,IFile> ifSchemas = util.getIFSchemaMap();
		assertEquals(1,ifSchemas.size());
		assertTrue(ifSchemas.containsKey("PWEXT-FXSWAP"));	
		Map<String,IFile> ifCommonSchemas = util.getIFCommonSchemaMap();
		assertEquals(1,ifCommonSchemas.size());
		assertTrue(ifCommonSchemas.containsKey("Common-EventCommon"));
	}	
	
	@Test
	public void testIFSchemaMapValidWithFieldMappingsAndEnqVars(){
		HumanTaskNodeWrapper wrapper = new HumanTaskNodeWrapper();
		wrapper.setPropertyValue("Comment","MM.MONEY.MARKET,PLACE I 1234 name=george mnemonic=ga "+
				"ENQ_VARS={CUSTOMER=george^GROUP.ID=CUSTOMER.SERVICE.AGENT}");
		IFModelUtil util = new IFModelUtil(wrapper);
		Map<String,IFile> ifSchemas = util.getIFSchemaMap();
		assertEquals(1,ifSchemas.size());
		assertTrue(ifSchemas.containsKey("PWEXT-MMPlacement"));	
		Map<String,IFile> ifCommonSchemas = util.getIFCommonSchemaMap();
		assertEquals(1,ifCommonSchemas.size());
		assertTrue(ifCommonSchemas.containsKey("Common-EventCommon"));
	}		
	
	@Test
	public void testIFSchemaMapInvalidMode(){
		HumanTaskNodeWrapper wrapper = new HumanTaskNodeWrapper();
		wrapper.setPropertyValue("Comment","FOREX,SC.SWAP I 1234");
		IFModelUtil util = new IFModelUtil(wrapper);
		Map<String,IFile> ifSchemas = util.getIFSchemaMap();
		assertEquals(0,ifSchemas.size());
		Map<String,IFile> ifCommonSchemas = util.getIFCommonSchemaMap();
		assertEquals(1,ifCommonSchemas.size());
		assertTrue(ifCommonSchemas.containsKey("Common-EventCommon"));
	}
	
	@Test
	public void testIFSchemaMapInvalidVersionAndMode(){
		HumanTaskNodeWrapper wrapper = new HumanTaskNodeWrapper();
		wrapper.setPropertyValue("Comment","FOREX,SC.SWAP1 I 1234");
		IFModelUtil util = new IFModelUtil(wrapper);
		Map<String,IFile> ifSchemas = util.getIFSchemaMap();
		assertEquals(0,ifSchemas.size());
		Map<String,IFile> ifCommonSchemas = util.getIFCommonSchemaMap();
		assertEquals(1,ifCommonSchemas.size());
		assertTrue(ifCommonSchemas.containsKey("Common-EventCommon"));
	}
}
