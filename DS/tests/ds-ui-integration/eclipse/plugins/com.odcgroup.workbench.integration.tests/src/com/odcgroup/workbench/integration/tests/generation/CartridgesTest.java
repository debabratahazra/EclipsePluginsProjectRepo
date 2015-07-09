package com.odcgroup.workbench.integration.tests.generation;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.PropertiesPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.TreeObject;

@RunWith(SWTBotJunit4ClassRunner.class)
public class CartridgesTest extends AbstractSWTBotTest {
	
	@Test
	public void cartridgesAvailable() throws Exception {
		PropertiesPageObject properties = on.openProjectProperties(DS_PROJECT);
		SWTBotTreeItem codeGenNode = properties.selectTreeItem(new Path("Design Studio/Code Generation"));
		codeGenNode.setFocus();
		codeGenNode.doubleClick();
		
		TreeObject tree = new TreeObject(bot.activeShell().bot().treeWithLabel("Code Generation Cartridges:"));
		tree.selectTreeItem(new Path("All Categories/AAA (Domain)/UD Entities"));
		tree.selectTreeItem(new Path("All Categories/API Dynamic (Domain)/MDF Bootstrap"));
		tree.selectTreeItem(new Path("All Categories/API Dynamic (Domain)/Dataset"));
		tree.selectTreeItem(new Path("All Categories/API Dynamic (Domain)/Enum"));
		tree.selectTreeItem(new Path("All Categories/API (Domain)/Manifest"));
		tree.selectTreeItem(new Path("All Categories/API (Domain)/MML"));
		tree.selectTreeItem(new Path("All Categories/API (Domain)/Java model"));
		tree.selectTreeItem(new Path("All Categories/API (Domain)/Service model"));
		tree.selectTreeItem(new Path("All Categories/API (Rules)/VisualRules Dynamic API"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Java model Serializer"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Java beans Serializer"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Model Serializer"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Java beans"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/XML schemas"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Castor XML mappings"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Castor JDO mappings"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Domain)/Torque schemas"));
		tree.selectTreeItem(new Path("All Categories/Implementation (Rules)/VisualRules Code"));
		tree.selectTreeItem(new Path("All Categories/EJB (Domain)/Manifest"));
		tree.selectTreeItem(new Path("All Categories/EJB (Domain)/EJB delegates"));
		tree.selectTreeItem(new Path("All Categories/EJB (Domain)/EJB"));
		tree.selectTreeItem(new Path("All Categories/WUI Block/Service pageflow actions"));
		tree.selectTreeItem(new Path("All Categories/WUI Block/Module XSP"));
		tree.selectTreeItem(new Path("All Categories/WUI Block/Page XSP"));
		tree.selectTreeItem(new Path("All Categories/WUI Block/Translations"));
		tree.selectTreeItem(new Path("All Categories/WUI Block import/Triple'A Plus Workflow Config"));
		properties.pressCancelButton();
	}
	
	@Test
	public void translationNodeGenerationMenu() {
		assertNotNull(on.selectNode(new Path(DS_PROJECT + "/Translations")).contextMenu("Generate Code"));
	}
}
