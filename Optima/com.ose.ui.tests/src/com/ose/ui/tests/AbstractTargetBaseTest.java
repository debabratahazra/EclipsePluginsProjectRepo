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

package com.ose.ui.tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.views.loadmodule.LoadModuleView;
import com.ose.ui.tests.framework.ITargetData;
import com.ose.ui.tests.framework.SystemModelTargetData;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.MultipleWidgetsFoundException;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.WidgetNotFoundException;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.locator.ILocator;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PullDownMenuItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class AbstractTargetBaseTest extends AbstractBaseTest
{
   public ITargetData targetData;

   String gateRegex;

   String targetRegex;

   public AbstractTargetBaseTest()
   {
      this.targetData = null;
      this.gateRegex = "";
      this.targetRegex = "";
   }

   @Override
   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();
   }
   
   @Override
   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();
   }

   @Override
   public void setUp() throws Exception
   {
      super.setUp();

      if (targetData == null)
      {
         targetData = SystemModelTargetData.INSTANCE;
      }

      this.gateRegex = createGateRegex(targetData.getGateAddress()
            .getHostAddress());
      this.targetRegex = createTargetRegex(targetData.getGateAddress()
            .getHostAddress());

      addGate(targetData.getGateAddress().getHostAddress());

      targetData.initialize();
      
      //installLoadModule(targetData.getTestLoadModulePath(), LOAD_MODULE_NAME);
      
   }

   @Override
   public void tearDown() throws Exception
   {
      //uninstallLoadModule(LOAD_MODULE_NAME);
      
      removeGate(targetData.getGateAddress().getHostAddress());

      targetData.destroy();

      super.tearDown();
   }

   void addGate(String address) throws Exception
   {
      IUIContext ui = getUI();

      connectToGate(address, ui);
      
      // If we did not exit the last test correctly target might already be added.
      ShellShowingCondition condition = new ShellShowingCondition("Problem Occurred");
      if(condition.test())
      {
         // Disconnect
         removeGate(address);
         // Reconnect
         connectToGate(address, ui);
      }
      
      ui.click(new TreeItemLocator(getGateRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));

      IWidgetReference reference = (IWidgetReference) ui
            .click(new TreeItemLocator(getTargetRegex(), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      
      TreeItem item = (TreeItem) reference.getWidget();

      MiscUtils.waitForJobs(ui);

      ILocator locator = new TreeItemLocator(getTargetRegex() + "/.*",
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID));

      boolean retry = true;
      int attempts = 0;
      String nodeLabel = "";

      while (retry && attempts < 5)
      {
         try
         {
            ++attempts;
            reference = (IWidgetReference) ui.click(locator);
            MiscUtils.waitForJobs(ui);
            item = (TreeItem) reference.getWidget();
            nodeLabel = UIUtils.getText(item);

            Pattern pattern = Pattern.compile("pool");
            Matcher matcher = pattern.matcher(nodeLabel);
            if (matcher.find())
            {
               retry = false;
            }
            else
            {
               locator = new TreeItemLocator(getTargetRegex() + "/" + nodeLabel
                     + "/.*", new ViewLocator(
                     SystemBrowserPlugin.SYSTEM_VIEW_ID));
            }
         }
         catch (WidgetNotFoundException wnfe)
         {
            fail("Node not present");
         }
         catch (MultipleWidgetsFoundException mwfe)
         {
            // Ignore and exit
         }
      }

      MiscUtils.waitForJobs(ui);
   }

   private void connectToGate(String address, IUIContext ui) throws Exception,
         WidgetSearchException
   {
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Add Gate...");

      ui.wait(new ShellShowingCondition("Add Gate"));

      // Enter address of target in the Add Gate dialog
      ui.click(new LabeledTextLocator("Address:"));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(address);
      ui.click(new ButtonLocator("OK"));

      // Wait for Add Gate dialog to close
      ui.wait(new ShellDisposedCondition("Add Gate"));
      MiscUtils.waitForJobs(ui);
   }

   void removeGate(String address) throws Exception
   {
      IUIContext ui = getUI();

      // Click on gateway node in system browser tree
      ui.click(new TreeItemLocator(getGateRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.keyClick(WT.KEYPAD_SUBTRACT);
      ui.click(new TreeItemLocator(getGateRegex(), new ViewLocator(
              SystemBrowserPlugin.SYSTEM_VIEW_ID)));

      UIUtils.clickSystemBrowserContextMenuItem(ui, "Disconnect");
      MiscUtils.waitForJobs(ui);

      UIUtils.clickSystemBrowserContextMenuItem(ui, "Clean");
      MiscUtils.waitForJobs(ui);
   }
   
   void installLoadModule(String filepath, String handlerName) throws Exception
   {
      IUIContext ui = getUI();

      if (isLoadModuleRunning (ui, handlerName))
      {
         return;
      }

      if (isLoadModuleInstalled(ui, handlerName))
      {
         return;
      }

      /** Install and run the Load module on target */

      //Click "Install Load Module" toolbar button
      assertNotNull("Load module file path isn't specified.", filepath);
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 2,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellShowingCondition("Install Load Module"));
      ui.click(2, new LabeledTextLocator("Load Module:"));
      ui.enterText(filepath);
      ui.click(2, new LabeledTextLocator("Installation Handle:"));
      ui.enterText(handlerName);
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Install Load Module"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      runLoadModule(ui, handlerName);
   }

   /**
    * Assert that, Load module is already installed or not
    * @param ui
    * @param handlerName
    * @return
    */
   private boolean isLoadModuleInstalled(IUIContext ui, String handlerName) throws Exception
   {
      // Show Load Modules in "Load Modules" View
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Load Modules");
      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      IWidgetReference[] items = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*", new ViewLocator(
                  SystemBrowserPlugin.LOAD_MODULE_VIEW_ID)));

      for (IWidgetReference iWidgetReference : items)
      {
         String installHandlerName = UIUtils.getText(
               ((TableItem) iWidgetReference.getWidget()),
               LoadModuleView.COLUMN_INSTALL_HANDLE).trim();
         if (handlerName.equals(installHandlerName))
         {
            runLoadModule(ui, handlerName);
            return true;
         }
      }
      return false;
   }

   /**
    * Assert that, Load Module is running on target or not
    * @param ui
    * @param handlerName
    * @return
    */
   private boolean isLoadModuleRunning(IUIContext ui, String handlerName)
   {
      IWidgetReference items[] = (IWidgetReference[]) ui
            .findAll(new TreeItemLocator(".*", new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      for (IWidgetReference iWidgetReference : items)
      {
         String installHandlerName = UIUtils.getText(
               ((TreeItem) iWidgetReference.getWidget())).trim();
         String handlerRegex = handlerName + ".* \\(.+\\)";
         Pattern p = Pattern.compile(handlerRegex);
         Matcher matcher = p.matcher(installHandlerName);
         if (matcher.find())
         {
            return true;
         }
      }
      return false;
   }

   /**
    * Run the Load module on target
    * @param ui
    * @param handlerName
    * @throws Exception
    */
   private void runLoadModule(IUIContext ui, String handlerName)
         throws Exception
   {
      // Click "Create Program" button of the selected load module
      ui.click(new TableItemLocator(handlerName, new ViewLocator(
            SystemBrowserPlugin.LOAD_MODULE_VIEW_ID)));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 4,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellShowingCondition("Create Program"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Create Program"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);
   }
   
   /**
    * Uninstall Load module from target
    * @param handlerName
    * @throws Exception
    */
   void uninstallLoadModule(String handlerName) throws Exception
   {
      IUIContext ui = getUI();

      if (!isLoadModuleRunning(ui, handlerName))
      {
         return;
      }

      ui.contextClick(new TreeItemLocator(getTargetRegex(),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Load Modules");
      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.contextClick(new TreeItemLocator(getSegmentRegex(LOAD_MODULE_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Kill");
      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.click(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.click(new PullDownMenuItemLocator("Clean", new SWTWidgetLocator(
            ToolItem.class, "", 1, new SWTWidgetLocator(ToolBar.class, 2,
                  new SWTWidgetLocator(Composite.class)))));
      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      ui.assertThat(new TableItemLocator(LOAD_MODULE_NAME, new ViewLocator(
            SystemBrowserPlugin.LOAD_MODULE_VIEW_ID)).isVisible());
      ui.contextClick(new TableItemLocator(LOAD_MODULE_NAME, new ViewLocator(
            SystemBrowserPlugin.LOAD_MODULE_VIEW_ID)), "Uninstall Load Module");
      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      if (isLoadModuleInstalled(ui, handlerName))
      {
         assertTrue(false);
      }
   }

   String createGateRegex(String address)
   {
      return "\\p{Graph}+ \\(" + address + ":\\d+\\)";
   }

   String createTargetRegex(String address)
   {
      return createGateRegex(address) + "/" + "\\p{Graph}+ \\(" + address
            + ":\\d+\\/\\)";
   }
   
   protected String getGateRegex()
   {
      return gateRegex;
   }
   
   protected String getTargetRegex()
   {
      return targetRegex;
   }
   
   protected String getSegmentRegex(String segmentName)
   {
      return getTargetRegex() + "/" + segmentName + ".* \\(.+\\)";
   }

   protected String getPoolRegex(String segmentName)
   {
      return getTargetRegex() + "/" + segmentName + ".* \\(.+\\)" + "/"
            + "pool \\(.+\\)";
   }

   protected String getHeapRegex(String segmentName)
   {
      return getTargetRegex() + "/" + segmentName + ".* \\(.+\\)" + "/"
            + "heap \\(.+\\)";
   }

   protected String getBlockRegex(String segmentName, String blockName)
   {
      return getTargetRegex() + "/" + segmentName + ".* \\(.+\\)" + "/"
            + blockName + " \\(.+\\)";
   }

   protected String getProcessRegex(String segmentName, String blockName,
         String processName)
   {
      return getTargetRegex() + "/" + segmentName + ".* \\(.+\\)" + "/"
            + blockName + " \\(.+\\)" + "/" + processName + " \\(.+\\)";
   }
}
