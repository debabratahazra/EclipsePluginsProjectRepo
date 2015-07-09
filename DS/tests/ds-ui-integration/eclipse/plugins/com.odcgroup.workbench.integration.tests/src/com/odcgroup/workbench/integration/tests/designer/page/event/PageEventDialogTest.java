package com.odcgroup.workbench.integration.tests.designer.page.event;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.*;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;
import com.odcgroup.workbench.integration.tests.util.widgets.SWTBotListElement;

/**
 * 
 * @author atr
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class PageEventDialogTest { 

	private static SWTGefBot bot = new SWTGefBot();
	protected static final MenuPageObject menu = new MenuPageObject();
	protected static final OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
	private static SWTBotGefEditor editor;

	@BeforeClass
	public static void setUp() throws Exception {
		menu.createNewModelProject(DS_PROJECT);
		createModule();
		createPageflow();
		editor = bot.gefEditor(TEST_MODULE + ".module");
	}

	private static void createModule() throws Exception {
		IPath path = new Path(DS_PROJECT+"/Modules");
		SWTBotTreeItem pkgTreeItem = on.createPackage(path, STD_PACKAGE);
		assertNotNull("Package was not created correctly!", pkgTreeItem);
		pkgTreeItem.contextMenu("New Module...").click();
		SWTBotShell shell = bot.shell("New Module");
		shell.activate();
		bot.text("MyModule").setText(TEST_MODULE);
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
	}

	private static void createPageflow() throws Exception {
		IPath pageflowPath = new Path(DS_PROJECT+"/Pageflows");
		SWTBotTreeItem pkgTreeItem = on.createPackage(pageflowPath, STD_PACKAGE);
		pkgTreeItem.contextMenu("New Pageflow...").click();
		SWTBotShell shell = bot.shell("New Pageflow");
		shell.activate();
		bot.text("MyName").setText(TEST_PAGEFLOW);
		bot.text("").setText("testpfl Description");
		bot.button("Finish").click();
		bot.waitUntil(shellCloses(shell));
		menu.close();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		on.selectNode(new Path(DS_PROJECT)).collapse();
		if (editor != null) {
			editor.close();
		}
		AbstractSWTBotTest.deleteProjects();
	}

	private void checkParameterValue(Map<String, String> parameters, String parameter, String expectedValue) {
		assertTrue("The parameter [" + parameter + "] does not exist", parameter != null);
		String currentValue = parameters.get(parameter);
		assertEquals("The parameter [" + parameter + "] has the wrong value", expectedValue, currentValue);
	}

	@Test
	public void convertSimplifiedEventWithGroupRefToAdvancedEvent() {

		// add a button inside the box
		editor.activateTool("Button");
		editor.click(30, 30);

		// select the properties view
		bot.viewByTitle("Properties").setFocus();
		
		// select the event tab
		SWTBotListElement element = SWTBotListElement.get(bot, 4);
		assertTrue("Cannot select the Event. Tab in the properties view", "Event".equals(element.getText()));
		element.select();

		// create a new event, a popup modal dialog must be displayed
		bot.button("Create").click();

		// activate the event dialog
		SWTBotShell shell = bot.shell("Event");
		shell.activate();

		// Sets a transition id
		SWTBotRadio transitionRadio = shell.bot().radio(" Outgoing Transition's Id: ");
		transitionRadio.setFocus();
		transitionRadio.click();
		shell.bot().text(0).setText("my-transition");

		// Sets a group-ref
		SWTBotCheckBox widgetGroupRefChk = shell.bot().checkBox(" Widget Group Ref.: ");
		widgetGroupRefChk.setFocus();
		widgetGroupRefChk.click();
		shell.bot().text(1).setText("my-group-ref");

		// convert the simplified event to an advanced event
		shell.bot().radio("Advanced Event").click();

		// verify that Event "OnClick" is selected
		SWTBotCombo eventCbx = shell.bot().comboBoxWithLabel("Event:");
		assertEquals("Wrong event selected", "OnClick", eventCbx.getText());

		// verify that the Function "submit" is selected
		SWTBotList fctList = shell.bot().listWithLabel("Functions:");
		String[] values = fctList.selection();
		assertTrue("One function must be selected", values.length == 1);
		assertEquals("The wrong fonction is selected", "submit", fctList.selection()[0]);

		// verify that parameters "call-URI", "method", "target",
		// "widget-group-ref" have the correct values
		Map<String, String> parameters = new HashMap<String, String>();
		SWTBotTable table = shell.bot().tableInGroup(" Attributes ");
		for (int rx = 0; rx < table.rowCount(); rx++) {
			SWTBotTableItem tableItem = table.getTableItem(rx);
			String parameter = tableItem.getText(0);
			String value = tableItem.getText(1);
			parameters.put(parameter, value);
		}
		checkParameterValue(parameters, "call-URI", "<pageflow:continuation/>");
		checkParameterValue(parameters, "height", "");
		checkParameterValue(parameters, "left", "");
		checkParameterValue(parameters, "method", "post");
		checkParameterValue(parameters, "modal", "");
		checkParameterValue(parameters, "only-changed", "");
		checkParameterValue(parameters, "param", "flow-action=my-transition");
		checkParameterValue(parameters, "prevalidate", "");
		checkParameterValue(parameters, "target", "self");
		checkParameterValue(parameters, "top", "");
		checkParameterValue(parameters, "uri", "");
		checkParameterValue(parameters, "widget-group-ref", "my-group-ref");
		checkParameterValue(parameters, "width", "");

		// check tab Parameters has flow-action

		// close the dialog
		shell.bot().button("Cancel").click();

	}

	//@Test - see to enable it again:
	// DS-5245 [build] Unstable SWT Bot test: PageEventDialogTest
	public void convertSimplifiedEventWithPageFlowToAdvancedEvent() {

		// add a button inside the box
		editor.activateTool("Button");
		editor.click(30, 30);

		// select the properties view
		bot.viewByTitle("Properties").setFocus();
		
		// select the event tab
		SWTBotListElement element = SWTBotListElement.get(bot, 4);
		assertTrue("Cannot select the Event Tab in the properties view", "Event".equals(element.getText()));
		element.select();

		// create a new event, a popup modal dialog must be displayed
		bot.button("Create").click();

		// activate the event dialog
		SWTBotShell shell = bot.shell("Event");
		shell.activate();

		// Sets the page flow
		SWTBotRadio transitionRadio = shell.bot().radio(" New Pageflow: ");
		transitionRadio.setFocus();
		transitionRadio.click();
		{
			SWTBotShell confirmShell = bot.shell("Confirm");
			confirmShell.activate();
			confirmShell.bot().button("OK").click();
		}

		// select a pageflow
		shell.bot().button("Browse").click();
		SWTBotShell pageflowShell = bot.shell("Pageflow Selection");
		pageflowShell.activate();
		SWTBotText chooseText = pageflowShell.bot().textWithLabel("Choose an object:");
		if (chooseText.isEnabled()) {
			chooseText.setText("*");
			pageflowShell.bot().button("OK").click();
		} else {
			pageflowShell.bot().button("Cancel").click();
		}

		// convert the simplified event to an advanced event
		shell.bot().radio("Advanced Event").click();
		
		// verify that Event "OnClick" is selected
		SWTBotCombo eventCbx = shell.bot().comboBoxWithLabel("Event:");
		assertEquals("Wrong event selected", "OnClick", eventCbx.getText());

		// verify that the Function "submit" is selected
		SWTBotList fctList = shell.bot().listWithLabel("Functions:");
		String[] values = fctList.selection();
		assertTrue("One function must be selected", values.length == 1);
		assertEquals("The wrong fonction is selected", "submit", fctList.selection()[0]);

		// verify that parameters "call-URI", "method", "target",
		// "widget-group-ref" have the correct values
		Map<String, String> parameters = new HashMap<String, String>();
		SWTBotTable table = shell.bot().tableInGroup(" Attributes ");
		for (int rx = 0; rx < table.rowCount(); rx++) {
			SWTBotTableItem tableItem = table.getTableItem(rx);
			String parameter = tableItem.getText(0);
			String value = tableItem.getText(1);
			parameters.put(parameter, value);
		}
		checkParameterValue(parameters, "call-URI", "resource:///testpkg/Testpfl.pageflow");
		checkParameterValue(parameters, "height", "");
		checkParameterValue(parameters, "left", "");
		checkParameterValue(parameters, "method", "post");
		checkParameterValue(parameters, "modal", "");
		checkParameterValue(parameters, "only-changed", "");
		checkParameterValue(parameters, "param", "");
		checkParameterValue(parameters, "prevalidate", "");
		checkParameterValue(parameters, "target", "self");
		checkParameterValue(parameters, "top", "");
		checkParameterValue(parameters, "uri", "");
		checkParameterValue(parameters, "widget-group-ref", "");
		checkParameterValue(parameters, "width", "");

		// check tab Parameters has flow-action

		// close the dialog
		shell.bot().button("Cancel").click();

	}
}
