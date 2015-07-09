package com.odcgroup.workbench.integration.tests.util.pageobjects;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.MODULES_DEFAULT_PACKAGE;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

/**
 * A PageObject that represents the Odyssey Navigator
 *
 * @author Kai Kreuzer
 *
 */
public class OdysseyNavigatorPageObject extends AbstractNavigatorPageObject {

	
	public OdysseyNavigatorPageObject() {
		super("DS Navigator");
	}

	public SWTBotTreeItem createPackage(IPath path, String packageName) {
		createPackage_openWizard(path);
		bot.textWithLabel("Model Package Name :").setText(packageName);
		bot.button("Finish").click();
		return selectNode(path).expand().getNode(packageName);
	}

	public void createPackage_openWizard(IPath path) {
		selectNode(path).contextMenu("New Package...").click();
		SWTBotShell newPackageShell = bot.shell("New model package");
		newPackageShell.activate();
	}
	
	public ModuleEditorPageObject createModule(String projectName, String moduleName) {
		createModule_openWizard(projectName);
		bot.text("MyModule").setText(moduleName);
		bot.button("Finish").click();
		return new ModuleEditorPageObject(moduleName);
	}

	/**
	 * @param projectName
	 */
	public void createModule_openWizard(String projectName) {
		String defaultModulePackage = projectName+"/"+MODULES_DEFAULT_PACKAGE;
		IPath defaultModulePackagePath = new Path(defaultModulePackage);
		SWTBotTreeItem rootTreeItem = selectNode(defaultModulePackagePath);
		rootTreeItem.contextMenu("New Module...").click();
		SWTBotShell shell = bot.shell("New Module");
		shell.activate();
	}
	
	public void createFragment_openWizard(String projectName, String moduleName) {
		SWTBotTreeItem pkgTreeItem = createPackageIfNecessary(projectName, "Fragments", STD_PACKAGE);
		pkgTreeItem.contextMenu("New Fragment...").click();
		SWTBotShell shell = bot.shell("New Fragment");
		shell.activate();
	}

	public void createPage_openWizard(String projectName) {
		SWTBotTreeItem pkgTreeItem = createPackageIfNecessary(projectName, "Pages", STD_PACKAGE);
		pkgTreeItem.contextMenu("New Page...").click();
		SWTBotShell shell = bot.shell("New Page");
		shell.activate();
	}

	public void createDomain_openWizard(String projectName) {
		SWTBotTreeItem pkgTreeItem = createPackageIfNecessary(projectName, "Domains", STD_PACKAGE);
		pkgTreeItem.contextMenu("New Domain...").click();
		SWTBotShell shell = bot.shell("New Domain");
		shell.activate();
	}
	
	public void createPageflow_openWizard(String projectName) {
		SWTBotTreeItem pkgTreeItem = createPackageIfNecessary(projectName, "Pageflows", STD_PACKAGE);
		pkgTreeItem.contextMenu("New Pageflow...").click();
		SWTBotShell shell = bot.shell("New Pageflow");
		shell.activate();
	}

	public void createWorkflow_openWizard(String projectName) {
		SWTBotTreeItem pkgTreeItem = createPackageIfNecessary(projectName, "Workflows", STD_PACKAGE);
		pkgTreeItem.contextMenu("New Workflow...").click();
		SWTBotShell shell = bot.shell("New Workflow");
		shell.activate();
	}
	
	/**
	 * Note: the pms-models project must have been created first
	 */
	public void importTAMetaDictionary_openWizard() {
		IPath packagePath = new Path("pms-models/Domains/aaa/entities");
		SWTBotTreeItem pmsModelsEntities = selectNode(packagePath);
		pmsModelsEntities.contextMenu("Import Triple'A Plus Core Meta Dictionary...").click();
		SWTBotShell shell = bot.shell("Import Triple'A Plus Core Meta Dictionary");
		shell.activate();
	}

	/**
	 * Note: the pms-models project must have been created first
	 */
	public void importTAFormats_openWizard() {
		IPath packagePath = new Path("pms-models/Domains/aaa/formats");
		SWTBotTreeItem pmsModelsEntities = selectNode(packagePath);
		pmsModelsEntities.contextMenu("Import Triple'A Plus Core Formats...").click();
		SWTBotShell shell = bot.shell("Import Triple'A Plus Core Formats");
		shell.activate();
	}

	/**
	 * @param projectName
	 * @return
	 */
	private SWTBotTreeItem createPackageIfNecessary(String projectName, String modelType, String packageName) {
		IPath modelPath = new Path(projectName+"/" + modelType + "/");
		IPath packagePath = new Path(projectName+"/" + modelType + "/"+packageName);
		try {
			selectNode(packagePath);
		} catch (WidgetNotFoundException e) {
			createPackage(modelPath, packageName);
		}
		return selectNode(packagePath);
	}
	
	public void runCodeGeneration(String path) {
		selectNode(new Path(path)).contextMenu("Generate Code").click();
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 3000L;
			SWTBotShell shell = bot.shell("Running Code Generation");
			bot.waitUntil(shellCloses(shell), 300000);
		}
	    catch (WidgetNotFoundException e) {
			// ignore - code gen is sometimes too quick for the Running Code Generation popup to appear
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
	}
	
	public void runCodeGeneration(String projectName, String packageName, String fileName) {
		runCodeGeneration(projectName+"/"+packageName+"/"+fileName);
	}

	public void runDocGeneration(String path) {
		selectNode(new Path(path)).contextMenu("Generate Documentation").click();
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 3000L;
			SWTBotShell shell = bot.shell("Running Documentation Generation");
			bot.waitUntil(shellCloses(shell), 300000);
		}
	    catch (WidgetNotFoundException e) {
			// ignore - code gen is sometimes too quick for the Running Code Generation popup to appear
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
	}
	
	public void runDocGeneration(String projectName, String packageName, String fileName) {
		runDocGeneration(projectName+"/"+packageName+"/"+fileName);
	}

	public void customizeModel(IPath pathToModel) {
		selectNode(pathToModel).contextMenu("Customize It!").click();
	}

	public void openFile(String path) {
		selectNode(new Path(path)).doubleClick();
	}
	
	public void createCustoPackage(String path) {
		// Select context menu
		selectNode(new Path(path)).contextMenu("Create Custo Package").click();
		
		// Confirm launch
		bot.shell("Custo Packager Launch confirmation");
		bot.button("OK").click();
		
		// Wait for the launch to finish
		long oldTimeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 10000L;
			SWTBotShell shell = bot.shell("Generating Custo Package");
			bot.waitUntil(shellCloses(shell), 20/*minutes*/*60*1000);
		} finally {
			SWTBotPreferences.TIMEOUT = oldTimeout;
		}
	}

	public void cancelWizard() {
		bot.button("Cancel").click();
	}

}
