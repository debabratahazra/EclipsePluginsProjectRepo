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

package com.ose.ui.tests.systembrowser;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.ose.system.Target;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.views.block.BlockView;
import com.ose.system.ui.views.process.ProcessView;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.framework.RawFileTargetData;
import com.ose.ui.tests.utils.EntityFilter;
import com.ose.ui.tests.utils.EntityType;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.ComboItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TabItemLocator;
import com.windowtester.runtime.swt.locator.TableCellLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestSystemBrowser extends AbstractTargetBaseTest
{
   private static final String BLOCK_NAME = "sysmodtest";

   private static final String PROCESS_NAME = "main";

   private static final String TOTAL_POOL_SIZE = "65536";

   private static final String TOTAL_HEAP_SIZE = "393120";
	   
   String blockRegex = ".*gamma.*";
   String processRegex = ".*gamma_0.*";
   
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   public void tearDown() throws Exception
   {
      super.tearDown();
   }
   
   public void testMemoryView() throws Exception
   {
      IUIContext ui = getUI();

      ui.click(
            2,
            new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME, blockRegex,
                  processRegex), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));

      String memoryAddress = new LabeledTextLocator("Entrypoint:").getText(ui);
      UIUtils.closeVisibleEditor(ui, processRegex);

      // Find block node, right click it and select
      // the menu option "Show Memory"
      ui.contextClick(
            new TreeItemLocator(getBlockRegex(LOAD_MODULE_NAME, blockRegex),
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show Memory");

      ui.wait(new ShellShowingCondition("Show Memory"));

      ui.enterText(memoryAddress);
      ui.keyClick(WT.TAB);
      ui.enterText("128");

      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Show Memory"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      // Adjust the address to the address showed in the address row in the
      // memory viewer
      memoryAddress = memoryAddress.substring(2, memoryAddress.length() - 1);
      memoryAddress = memoryAddress + "0";
      for (int i = memoryAddress.length(); i < 8; i++)
      {
         memoryAddress = "0" + memoryAddress;
      }

      try
      {
         ui.find(new TableItemLocator(memoryAddress));
      }
      catch (WidgetSearchException e)
      {
         assertTrue(false);
      }
   }
   
   public void testBlockList() throws Exception
   {
      this.targetData = RawFileTargetData.INSTANCE;

      IUIContext ui = getUI();

      TreeItemLocator segmentLocator = new TreeItemLocator(
            getSegmentRegex(LOAD_MODULE_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID));

      ui.contextClick(segmentLocator, "Show in Block List");

      ui.wait(new ShellShowingCondition("Block Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Block Filter"));

      MiscUtils.waitForJobs(ui);

      try
      {
         List<String[]> blocksList = targetData.getEntities(EntityType.BLOCK,
               Target.SCOPE_SEGMENT_ID, getSegmentId(segmentLocator));

         Collections.sort(blocksList, new MiscUtils.EntityArraySorter(
               ProcessView.COLUMN_NAME));

         for (String[] properties : blocksList)
         {
            IWidgetReference locator = (IWidgetReference) ui
                  .find(new TableCellLocator(properties[BlockView.COLUMN_NAME],
                        BlockView.COLUMN_NAME + 1).in(new ViewLocator(
                        SystemBrowserPlugin.BLOCK_VIEW_ID)));

            TableItem item = (TableItem) locator.getWidget();

            assertTrue(UIUtils.compareItemElements(item,
                  properties[BlockView.COLUMN_NAME], BlockView.COLUMN_NAME));
            /*assertTrue(UIUtils.compareItemElements(item,
                  UIUtils.toHexString(properties[BlockView.COLUMN_BID]),
                  BlockView.COLUMN_BID));
            assertTrue(UIUtils.compareItemElements(item,
                  UIUtils.toHexString(properties[BlockView.COLUMN_SID]),
                  BlockView.COLUMN_SID));*/
            assertTrue(UIUtils.compareItemElements(item,
                  properties[BlockView.COLUMN_UID], BlockView.COLUMN_UID));
            assertTrue(UIUtils.compareItemElements(item,
                  properties[BlockView.COLUMN_MAX_SIGNAL_SIZE],
                  BlockView.COLUMN_MAX_SIGNAL_SIZE));
            assertTrue(UIUtils.compareItemElements(item, UIUtils
                  .toHexString(properties[BlockView.COLUMN_SIGNAL_POOL_ID]),
                  BlockView.COLUMN_SIGNAL_POOL_ID));
            assertTrue(UIUtils.compareItemElements(item, UIUtils
                  .toHexString(properties[BlockView.COLUMN_STACK_POOL_ID]),
                  BlockView.COLUMN_STACK_POOL_ID));
            assertTrue(UIUtils.compareItemElements(item,
                  properties[BlockView.COLUMN_EXECUTION_UNIT],
                  BlockView.COLUMN_EXECUTION_UNIT));
         }
      }
      catch (Exception e)
      {
         assertTrue(false);
      }
   }

   public void testBlockListFilter() throws Exception
   {
      this.targetData = RawFileTargetData.INSTANCE;

      IUIContext ui = getUI();

      TreeItemLocator segmentLocator = new TreeItemLocator(
            getSegmentRegex(LOAD_MODULE_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID));

      ui.contextClick(segmentLocator, "Show in Block List");

      ui.wait(new ShellShowingCondition("Block Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));

      ui.click(new LabeledTextLocator("Name:"));
      ui.enterText("psi");

      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Block Filter"));

      MiscUtils.waitForJobs(ui);

      try
      {
         ui.find(new TableItemLocator("psi"));
      }
      catch (WidgetSearchException e)
      {
         assertTrue(false);
      }
   }

   /*
    * This test case covers part of TC_SYS_BROWS_VIEW
    */
   public void testOpenEntityNodeEditor() throws Exception
   {
      IUIContext ui = getUI();

      /** Test Open Gate Editor */

      // Find gate node and double click it to open editor
      IWidgetReference reference = (IWidgetReference) ui.click(2,
            new TreeItemLocator(getGateRegex(), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      TreeItem item = (TreeItem) reference.getWidget();

      ui.assertThat(new LabeledTextLocator("Name:").hasText(getNodeName(item)));

      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));

      /** Test Open Target Editor */

      // Find Target node and double click to open editor
      reference = (IWidgetReference) ui.click(2, new TreeItemLocator(
            getTargetRegex(), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      item = (TreeItem) reference.getWidget();

      ui.assertThat(new LabeledTextLocator("Name:").hasText(getNodeName(item)));

      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));

      /** Test Open Pool Editor */

      // Find Pool node and double click to open editor
      reference = (IWidgetReference) ui.click(2, new TreeItemLocator(
            getPoolRegex(LOAD_MODULE_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      item = (TreeItem) reference.getWidget();

      ui.assertThat(new LabeledTextLocator("Total Size:")
            .hasText(TOTAL_POOL_SIZE));

      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));

      /** Test Open Heap Editor */

      // Find Heap node and double click to open editor
      reference = (IWidgetReference) ui.click(2, new TreeItemLocator(
            getHeapRegex(LOAD_MODULE_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      item = (TreeItem) reference.getWidget();

      // Settle for the actual heap size being of the same magnitude as the
      // expected heap size.
      String totalHeapSize = new LabeledTextLocator("Total Size:").getText(ui);
      assertTrue(totalHeapSize.length() == TOTAL_HEAP_SIZE.length());

      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));

      /** Test Open Block Editor */

      // Find Block node and double click to open editor
      reference = (IWidgetReference) ui.click(2, new TreeItemLocator(
            getBlockRegex(LOAD_MODULE_NAME, BLOCK_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      item = (TreeItem) reference.getWidget();

      ui.assertThat(new LabeledTextLocator("Name:").hasText(getNodeName(item)));

      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));

      /** Test Open Process Editor */

      // Find Process node and double click to open editor
      reference = (IWidgetReference) ui.click(2, new TreeItemLocator(
            getProcessRegex(LOAD_MODULE_NAME, BLOCK_NAME, PROCESS_NAME),
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      item = (TreeItem) reference.getWidget();

      ui.assertThat(new LabeledTextLocator("Name:").hasText(getNodeName(item)));

      UIUtils.closeVisibleEditor(ui, getEditorRegex(item));

   }
   
   public void testProcessList() throws Exception
   {
      this.targetData = RawFileTargetData.INSTANCE;
      
      IUIContext ui = getUI();

      TreeItemLocator segmentLocator = new TreeItemLocator(
            getSegmentRegex(LOAD_MODULE_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID));

      ui.contextClick(segmentLocator, "Show in Process List");

      // Click OK to close the dialog box
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Process Filter"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      List<String[]> processes = targetData.getFilteredEntities(
            EntityType.PROCESS, Target.SCOPE_SEGMENT_ID,
            getSegmentId(segmentLocator), null);

      Collections.sort(processes, new MiscUtils.EntityArraySorter(
            ProcessView.COLUMN_NAME));

      assertProcessDataResults(processes);
   }

   public void testProcessListFilter() throws Exception
   {
      this.targetData = RawFileTargetData.INSTANCE;

      TreeItemLocator segmentLocator = new TreeItemLocator(
            getSegmentRegex(LOAD_MODULE_NAME), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID));

      filterTest1(segmentLocator);

      filterTest2(segmentLocator);

      filterTest3(segmentLocator);
   }

   private void filterTest1(TreeItemLocator itemLocator) throws Exception
   {
      IUIContext ui = getUI();
      
      ui.contextClick(itemLocator, "Show in Process List");

      // Wait for Process Filter dialog box to show up
      ui.wait(new ShellShowingCondition("Process Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));

      // Change process type combobox to "Prioritized"
      ui.click(new ComboItemLocator("Prioritized", new SWTWidgetLocator(
            Combo.class, new SWTWidgetLocator(Composite.class))));

      // Switch to the "State" tab
      ui.click(new TabItemLocator("State"));

      // Change process state combobox to "Running"
      ui.click(new ComboItemLocator("Receive", new SWTWidgetLocator(
            Combo.class, new SWTWidgetLocator(Composite.class))));

      // Click OK to close the dialog box
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Process Filter"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      EntityFilter[] filters = {
            new EntityFilter(ProcessView.COLUMN_TYPE, "Prioritized", true),
            new EntityFilter(ProcessView.COLUMN_STATE, "Receive", true)};

      List<String[]> processes = targetData.getFilteredEntities(
            EntityType.PROCESS, Target.SCOPE_SEGMENT_ID,
            getSegmentId(itemLocator), filters);

      Collections.sort(processes, new MiscUtils.EntityArraySorter(
            ProcessView.COLUMN_NAME));

      IWidgetReference items[] = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));

      for (IWidgetReference locator : items)
      {
         assertEquals("Receive", UIUtils.getText(
               ((TableItem) locator.getWidget()), ProcessView.COLUMN_STATE));
      }
      
      assertProcessDataResults(processes);
   }

   private void filterTest2(TreeItemLocator itemLocator) throws Exception
   {
      IUIContext ui = getUI();

      ui.contextClick(itemLocator, "Show in Process List");

      // Wait for Process Filter dialog box to show up
      ui.wait(new ShellShowingCondition("Process Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));

      ui.click(new ComboItemLocator("Prioritized", new SWTWidgetLocator(
            Combo.class, new SWTWidgetLocator(Composite.class))));

      ui.click(new TabItemLocator("State"));
      ui.click(new LabeledTextLocator("Priority:"));
      ui.enterText("15");
      ui.keyClick(WT.TAB);
      ui.enterText("28");

      // Click OK to close the dialog box
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Process Filter"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      EntityFilter[] filters = {
            new EntityFilter(ProcessView.COLUMN_PRIORITY, "15", "28", true),
            new EntityFilter(ProcessView.COLUMN_TYPE, "Prioritized", true),};

      List<String[]> processes = targetData.getFilteredEntities(
            EntityType.PROCESS, Target.SCOPE_SEGMENT_ID,
            getSegmentId(itemLocator), filters);

      Collections.sort(processes, new MiscUtils.EntityArraySorter(
            ProcessView.COLUMN_NAME));

      IWidgetReference items[] = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));

      int priority = -1;
      
      for (IWidgetReference locator : items)
      {
         assertEquals("Prioritized", UIUtils.getText(
               ((TableItem) locator.getWidget()), ProcessView.COLUMN_TYPE));
         
         priority = Integer.parseInt(UIUtils.getText(
               ((TableItem) locator.getWidget()), ProcessView.COLUMN_PRIORITY));
         
         assertTrue(priority >=15 && priority < 28);
      }

      assertProcessDataResults(processes);
   }

   private void filterTest3(TreeItemLocator itemLocator) throws Exception
   {
      IUIContext ui = getUI();

      ui.contextClick(itemLocator, "Show in Process List");

      // Wait for Process Filter dialog box to show up
      ui.wait(new ShellShowingCondition("Process Filter"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));

      // Change process type combobox to "Background"
      ui.click(new ComboItemLocator("Background", new SWTWidgetLocator(
            Combo.class, new SWTWidgetLocator(Composite.class))));

      // Switch to the "State" tab
      ui.click(new TabItemLocator("State"));

      // Change process state combobox to "Ready"
      ui.click(new ComboItemLocator("Delay", new SWTWidgetLocator(
            Combo.class, new SWTWidgetLocator(Composite.class))));

      // Click OK to close the dialog box
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Process Filter"));

      // Wait for the process listing job to be completed
      MiscUtils.waitForJobs(ui);

      EntityFilter[] filters = {
            new EntityFilter(ProcessView.COLUMN_TYPE, "Background", true),
            new EntityFilter(ProcessView.COLUMN_STATE, "Delay", true)};

      List<String[]> processes = targetData.getFilteredEntities(
            EntityType.PROCESS, Target.SCOPE_SEGMENT_ID,
            getSegmentId(itemLocator), filters);

      Collections.sort(processes, new MiscUtils.EntityArraySorter(
            ProcessView.COLUMN_NAME));

      IWidgetReference items[] = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));

      for (IWidgetReference locator : items)
      {
         assertEquals("Background", UIUtils.getText(
               ((TableItem) locator.getWidget()), ProcessView.COLUMN_TYPE));

         assertEquals("Delay", UIUtils.getText(
               ((TableItem) locator.getWidget()), ProcessView.COLUMN_STATE));
      }

      assertProcessDataResults(processes);
   }

   private void assertProcessDataResults(List<String[]> processesList)
         throws Exception
   {
      TableItem item = null;
      IUIContext ui = getUI();

      for (String[] properties : processesList)
      {
         try
         {
            IWidgetReference locator = (IWidgetReference) ui
                  .find(new TableCellLocator(properties[ProcessView.COLUMN_NAME],
                        ProcessView.COLUMN_NAME + 1).in(new ViewLocator(
                        SystemBrowserPlugin.PROCESS_VIEW_ID)));
            
            item = (TableItem) locator.getWidget();

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_NAME], ProcessView.COLUMN_NAME));

            /*assertTrue(UIUtils.compareItemElements(item,
                  UIUtils.toHexString(properties[ProcessView.COLUMN_ID]),
                  ProcessView.COLUMN_ID));

            assertTrue(UIUtils.compareItemElements(item,
                  UIUtils.toHexString(properties[ProcessView.COLUMN_BID]),
                  ProcessView.COLUMN_BID));*/

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_UID], ProcessView.COLUMN_UID));

            assertTrue(UIUtils.compareItemElements(item, UIUtils
                  .toProcessTypeString(properties[ProcessView.COLUMN_TYPE]),
                  ProcessView.COLUMN_TYPE));

            assertTrue(UIUtils.compareItemElements(item, UIUtils
                  .toProcessStateString(properties[ProcessView.COLUMN_STATE]),
                  ProcessView.COLUMN_STATE));

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_PRIORITY],
                  ProcessView.COLUMN_PRIORITY));

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_SEMAPHORE_VALUE],
                  ProcessView.COLUMN_SEMAPHORE_VALUE));

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_FILE_NAME],
                  ProcessView.COLUMN_FILE_NAME));

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_STACK_SIZE],
                  ProcessView.COLUMN_STACK_SIZE));

            assertTrue(UIUtils.compareItemElements(item,
                  properties[ProcessView.COLUMN_EXECUTION_UNIT],
                  ProcessView.COLUMN_EXECUTION_UNIT));
         }
         catch (WidgetSearchException wse)
         {
            assertTrue(false);
         }
      }
   }
   
   private String getNodeName (TreeItem treeItem)
   {
      String REGEX = "\\(";
      Pattern p = Pattern.compile(REGEX);
      String gateName = p.split(UIUtils.getText(treeItem))[0].trim();
      return gateName ;
   }
   
   private String getEditorRegex(TreeItem treeItem)
   {
     String name = UIUtils.getText(treeItem);
     return name.trim();
   }
   
   private int getSegmentId(TreeItemLocator locator) throws Exception
   {
      IWidgetReference wRef = (IWidgetReference) getUI().click(locator);
      String segmentId = UIUtils.getText(((TreeItem) wRef.getWidget()));
      segmentId = segmentId.substring(segmentId.indexOf("(") + 3,
            segmentId.indexOf(")"));
      return Integer.parseInt(segmentId, 16);
   }
}
