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
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.GenericTestData;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.TestDataComparator;
import com.ose.ui.tests.utils.TreeData;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PullDownMenuItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

/*
 * This class covers test case "TC_SYS_BROWS_SORT"
 */
public class TestSystemBrowerSort extends AbstractTargetBaseTest
{
   String blockRegex = ".*alfa.*";
   String processRegex = ".*alfa_0.*"; 
   
   String [] regexpBlocks = { "\\Walfa 0x[0-9|A-Z]{1,}::", 
         "\\Wbeta 0x[0-9|A-Z]{1,}::", 
         "\\Wgamma 0x[0-9|A-Z]{1,}::" };
   
   String [] regexpProcesses = { "\\Walfa_0 0x[0-9|A-Z]{1,}::",
         "\\Walfa_1 0x[0-9|A-Z]{1,}::", "\\Walfa_2 0x[0-9|A-Z]{1,}::",
         "\\Walfa_3 0x[0-9|A-Z]{1,}::", "\\Walfa_4 0x[0-9|A-Z]{1,}::", "\\Walfa_5 0x[0-9|A-Z]{1,}::" };

   Pattern [] patternBlocks = new Pattern[10];
   Pattern [] patternProcesses = new Pattern[10];
   
   @Override
   public void setUp() throws Exception
   {
      super.setUp();
   }
   
   @Override
   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testEntitySortByName() throws Exception
   {
      IUIContext ui = getUI();
      
      LinkedList<GenericTestData> blockList = new LinkedList<GenericTestData>();
      LinkedList<GenericTestData> processList = new LinkedList<GenericTestData>();
           
      /***** GETTING TO A KNOWN STATE *******/
            
      ui.click(new PullDownMenuItemLocator("Sort By/Name", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      // Expand all Nodes in System Browser
      expandAllNodes(ui);
      
      LinkedList<TreeData> treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      String treeDataString = MiscUtils.listDataToString(treeData);
         
      for(int i = 0; i < regexpBlocks.length; i++)
      {
          patternBlocks[i] = Pattern.compile(regexpBlocks[i]);
          Matcher matcher= patternBlocks[i].matcher(treeDataString);
          if(!matcher.find())
          {
              assertTrue(false);
          }
          blockList.add(new GenericTestData(matcher.start(), 
                         getId(matcher.group()), getName(matcher.group())));
          matcher.reset();
      }
      // Sort on the positions they are found in the treeDataString 
      Collections.sort(blockList, new TestDataComparator());
   
      if(verifyAscendingName(blockList))
      {
         ui.click(new PullDownMenuItemLocator("Sort By/Ascending", new ViewLocator(
               SystemBrowserPlugin.SYSTEM_VIEW_ID)));
         MiscUtils.waitForJobs(ui);
      }
      
      blockList.clear();
      
      /************* END ***********************/
      
      ui.click(new PullDownMenuItemLocator("Sort By/None", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
          
      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      
      // Get blocks for validate "None" 
      for(int i = 0; i < regexpBlocks.length; i++)
      {
    	  Matcher matcher= patternBlocks[i].matcher(treeDataString);
    	  if(!matcher.find())
    	  {
    		  assertTrue(false);
    	  }
    	  blockList.add(new GenericTestData(matcher.start(), 
                         getId(matcher.group()), getName(matcher.group())));
    	  matcher.reset();
      }
      // Sort on the positions they are found in the treeDataString 
      Collections.sort(blockList, new TestDataComparator());
      
      // Get process for validate "None" 
      for(int i = 0; i < regexpProcesses.length; i++)
      {
    	  patternProcesses[i] = Pattern.compile(regexpProcesses[i]);
    	  Matcher matcher= patternProcesses[i].matcher(treeDataString);
    	  if(!matcher.find())
    	  {
    		  assertTrue(false);
    	  }
    	  processList.add(new GenericTestData(matcher.start(), 
                getId(matcher.group()), getName(matcher.group())));
          matcher.reset();
      }
      Collections.sort(processList, new TestDataComparator());
      
      ui.click(new PullDownMenuItemLocator("Sort By/Name", new ViewLocator(
          SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
      
      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      System.out.println(treeDataString);
      validateName(treeDataString, null, null, 2);
      
      ui.click(new PullDownMenuItemLocator("Sort By/Ascending", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
      
      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      validateName(treeDataString, null, null, 3);
      
      ui.click(new PullDownMenuItemLocator("Sort By/None", new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
      
      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      validateName(treeDataString, blockList, processList, 1);
     
   }
   
   public void testEntitySortById() throws Exception
   {
      IUIContext ui = getUI();

      LinkedList<GenericTestData> blockList = new LinkedList<GenericTestData>();
      LinkedList<GenericTestData> processList = new LinkedList<GenericTestData>();  
      
      /********GETTING TO A KNOWN STATE ************/
      
      ui.click(new PullDownMenuItemLocator("Sort By/ID", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
      
      LinkedList<TreeData> treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      String treeDataString = MiscUtils.listDataToString(treeData);
      
      for(int i = 0; i < regexpBlocks.length; i++)
      {
          patternBlocks[i] = Pattern.compile(regexpBlocks[i]);
          Matcher matcher= patternBlocks[i].matcher(treeDataString);
          if(!matcher.find())
          {
              assertTrue(false);
          }
          blockList.add(new GenericTestData(matcher.start(), 
                         getId(matcher.group()), getName(matcher.group())));
          matcher.reset();
      }
      Collections.sort(blockList, new TestDataComparator());
      
      if(verifyAscendingId(blockList))
      {
         ui.click(new PullDownMenuItemLocator("Sort By/Ascending", new ViewLocator(
               SystemBrowserPlugin.SYSTEM_VIEW_ID)));
         MiscUtils.waitForJobs(ui);
      }
      
      blockList.clear();
      
      /************** END ******************/
      
      ui.click(new PullDownMenuItemLocator("Sort By/None", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
           
      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      
      // Get blocks for validate "None" 
      for(int i = 0; i < regexpBlocks.length; i++)
      {
          Matcher matcher = patternBlocks[i].matcher(treeDataString);
          if(!matcher.find())
          {
              assertTrue(false);
          }
          blockList.add(new GenericTestData(matcher.start(), 
                         getId(matcher.group()), getName(matcher.group())));
          matcher.reset();
      }
      
      Collections.sort(blockList, new TestDataComparator()); 
      
      // Get processes for validate "None" 
      for(int i = 0; i < regexpProcesses.length; i++)
      {
          patternProcesses[i] = Pattern.compile(regexpProcesses[i]);
          Matcher matcher = patternProcesses[i].matcher(treeDataString);
          if(!matcher.find())
          {
              assertTrue(false);
          }
          processList.add(new GenericTestData(matcher.start(), 
                getId(matcher.group()), getName(matcher.group())));        
          matcher.reset();
      }
      
      Collections.sort(processList, new TestDataComparator());   
      
      ui.click(new PullDownMenuItemLocator("Sort By/ID", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);

      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      validateId(treeDataString, null, null, 2);
      
      ui.click(new PullDownMenuItemLocator("Sort By/Ascending", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);

      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      validateId(treeDataString, null, null, 3);

      ui.click(new PullDownMenuItemLocator("Sort By/None", new ViewLocator(
                    SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      MiscUtils.waitForJobs(ui);
      
      expandAllNodes(ui);
      
      treeData = UIUtils.getTreeDataInView(ui, SystemBrowserPlugin.SYSTEM_VIEW_ID);
      treeDataString = MiscUtils.listDataToString(treeData);
      validateId(treeDataString, blockList, processList, 1);
   }
   
   private void validateId(String input, 
         LinkedList<GenericTestData> bl, 
         LinkedList<GenericTestData> pl, 
         int flag)
   {
      LinkedList<GenericTestData> blockList = new LinkedList<GenericTestData>();
      LinkedList<GenericTestData> processList = new LinkedList<GenericTestData>();  
      
      //  Validate blocks 
      for(int i = 0; i < regexpBlocks.length; i++)
      {
         Matcher matcher = patternBlocks[i].matcher(input);
         if(!matcher.find())
         {
            assertTrue(false); 
         }
         blockList.add(new GenericTestData(matcher.start(), 
               getId(matcher.group()), getName(matcher.group())));
         matcher.reset();
      }
      
      Collections.sort(blockList, new TestDataComparator());
      
      switch(flag)
      {
         case 1:
            assertTrue(verifyNonesortId(blockList, bl));  
            break;
         case 2:
            assertTrue(verifyDescendingId(blockList));
            break;
         case 3:
            assertTrue(verifyAscendingId(blockList));
            break;
      }
      
      // Validate processes 
      for(int i = 0; i < regexpProcesses.length; i++)
      {
         Matcher matcher = patternProcesses[i].matcher(input);
         if(!matcher.find())
         {
            assertTrue(false);
         }
         processList.add(new GenericTestData(matcher.start(), 
               getId(matcher.group()), getName(matcher.group())));
         matcher.reset();
      }
      Collections.sort(processList, new TestDataComparator());   
      
      switch(flag)
      {
         case 1:
            assertTrue(verifyNonesortId(processList, pl));  
            break;
         case 2:
            assertTrue(verifyDescendingId(processList));
            break;
         case 3:
            assertTrue(verifyAscendingId(processList));
            break;
      }
   }
   
   private String getName(String input)
   {
      int start = 1;
      int end = input.lastIndexOf(' ');
      return input.substring(start, end);
   }
   
   private int getId(String input)
   {
      int start = input.indexOf( " 0x"); 
      int end = input.lastIndexOf("::");
      String str = input.substring(start+3, end);
      return Integer.parseInt(str, 16);
      
   }
   
   static public boolean verifyAscendingName(LinkedList<GenericTestData> list)
   {
      int size = list.size();
      
      for(int i = 0; i < size; i++)
      {
         GenericTestData td1 = list.get(i);
         ListIterator<GenericTestData> itr = list.listIterator(i);
         while(itr.hasNext())
         {
            GenericTestData td2 = (GenericTestData)itr.next();
            if(td1.getString1().compareToIgnoreCase(td2.getString1()) > 0)
            {
               return false;
            }
         }
      }   
      return true;
   }
   
   static public boolean verifyAscendingId(LinkedList<GenericTestData> list)
   {
      int size = list.size();
      
      for(int i = 0; i < size; i++)
      {
         GenericTestData td1 = list.get(i);
         ListIterator<GenericTestData> itr = list.listIterator(i);
         while(itr.hasNext())
         {
            GenericTestData td2 = (GenericTestData)itr.next();
            if(td1.getValue2() > td2.getValue2())
            {
               return false;
            }
         }
      }   
      return true;
   }
   
   static public boolean verifyDescendingName(LinkedList<GenericTestData> list)
   {
      int size = list.size();
      
      for(int i = 0; i < size; i++)
      {
         GenericTestData td1 = list.get(i);
         ListIterator<GenericTestData> itr = list.listIterator(i);
         while(itr.hasNext())
         {
            GenericTestData td2 = (GenericTestData)itr.next();
            if(td1.getString1().compareToIgnoreCase(td2.getString1()) < 0 )
            {
               return false;
            }
         }
      }   
      return true;
   }
   
   static public boolean verifyDescendingId(LinkedList<GenericTestData> list)
   {
      int size = list.size();
      
      for(int i = 0; i < size; i++)
      {
         GenericTestData td1 = list.get(i);
         ListIterator<GenericTestData> itr = list.listIterator(i);
         while(itr.hasNext())
         {
            GenericTestData td2 = (GenericTestData)itr.next();
            if(td1.getValue2() < td2.getValue2())
            {
               return false;
            }
         }
      }   
      return true;
   }
   
   static public boolean verifyNonesortName(LinkedList<GenericTestData> list1, LinkedList<GenericTestData> list2)
   {
      ListIterator<GenericTestData> itr = list2.listIterator();
      int i = 0;
      while(itr.hasNext())
      {
         GenericTestData td2 = (GenericTestData)itr.next();
         GenericTestData td1 = list1.get(i);
         i++;
         if(td2.getString1().compareToIgnoreCase(td1.getString1()) != 0)
         {
            return false;
         }
      }
      return true;
   }
   
   static public boolean verifyNonesortId(LinkedList<GenericTestData> list1, LinkedList<GenericTestData> list2)
   {
      ListIterator<GenericTestData> itr = list2.listIterator();
      int i = 0;
      while(itr.hasNext())
      {
         GenericTestData td2 = (GenericTestData)itr.next();
         GenericTestData td1 = list1.get(i);
         i++;
         if((td2.getValue2() != td1.getValue2()))
         {
            return false;
         }
      }
      return true;
   }
   
   
   private void expandAllNodes(IUIContext ui) throws Exception
   {
      String[] blockName = {"alfa", "beta", "delta", "gamma", "omega", "psi", "sysmodtest"};
      String[] processName = {"alfa_0", "beta_0", "delta_0", "gamma_0", "omega_0", "psi_0", "main"};
      for (int i = 0; i < blockName.length; i++)
      {
         ui.click(new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME, blockName[i], processName[i]), new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));   
      }
   }

   private void validateName(String input, 
         LinkedList<GenericTestData> bl, 
         LinkedList<GenericTestData> pl, 
         int flag)
   {
      LinkedList<GenericTestData> blockList = new LinkedList<GenericTestData>();
      LinkedList<GenericTestData> processList = new LinkedList<GenericTestData>();  
      
      // Validate blocks 
      for(int i = 0; i < regexpBlocks.length; i++)
      {
         Matcher matcher = patternBlocks[i].matcher(input);
         if(!matcher.find())
         {
            assertTrue(false); 
         }
         blockList.add(new GenericTestData(matcher.start(), 
               getId(matcher.group()), getName(matcher.group())));
         matcher.reset();
      }
      
      Collections.sort(blockList, new TestDataComparator());
      
      switch(flag)
      {
         case 1:
            assertTrue(verifyNonesortName(blockList, bl));  
            break;
         case 2:
            assertTrue(verifyDescendingName(blockList));
            break;
         case 3:
            assertTrue(verifyAscendingName(blockList));
            break;
      }
      
      // Validate processes  
      for(int i = 0; i < regexpProcesses.length; i++)
      {
         Matcher matcher = patternProcesses[i].matcher(input);
         if(!matcher.find())
         {
            assertTrue(false);
         }
         processList.add(new GenericTestData(matcher.start(), 
               getId(matcher.group()), getName(matcher.group())));
         matcher.reset();
      }
      Collections.sort(processList, new TestDataComparator());    
      
      switch(flag)
      {
         case 1:
            assertTrue(verifyNonesortName(processList, pl));  
            break;
         case 2:
            assertTrue(verifyDescendingName(processList));
            break;
         case 3:
            assertTrue(verifyAscendingName(processList));
            break;
      }
   }
}
