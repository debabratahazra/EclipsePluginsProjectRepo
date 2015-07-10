/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.ui.tests.logmanager;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.event.ui.EventPlugin;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestAttachTarget extends AbstractTargetBaseTest
{
   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();
      
      IUIContext ui = getUI();
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("Log Management").isActive());
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
   }
   
   public void testTargetAttachError() throws Exception
   {
      IUIContext ui = getUI();

      // Get the target node information
      TreeItem treeItem = UIUtils.getItemReference(ui, getTargetRegex());

      // Add Target-1 in Log Manager View
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Try to add Target-2
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      
      // Error occurred
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);
      assertTrue(text.contains("Error when attaching to target"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }

}
