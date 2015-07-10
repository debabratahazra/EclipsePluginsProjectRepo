/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.chart.tests.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import com.ose.chart.tests.ui.ChartTestConstants;
import com.ose.chart.tests.ui.TestGenerator;
import com.ose.chart.tests.ui.ChartTest;
import com.ose.chart.tests.ui.ChartTestImages;
import com.ose.chart.tests.ui.ChartTestModelNode;
import com.ose.chart.tests.ui.ChartTestPlugin;
import com.ose.chart.tests.ui.TreeUtils;
import com.ose.chart.tests.ui.editors.ChartTestEditorInput;

/**
 * view the tests under a tree structure.
 * 
 * from this view, you can open a validation state file
 * create one, and save the validation state.
 * 
 * right click on a Test to validate or invalidate it
 * 
 * the charts are set to failed if at least one of the tests are failed,
 * valid if all the tests are validated, otherwise they are not fully tested.
 * 
 * the save option in the toolbar is only activated if a path to a file is provided,
 * either by opening a file or by saving into a new one
 *
 */
public class ChartTestView extends ViewPart
{
   private TreeViewer viewer;
   
   // actions for the popup menu
   private Action validateTestAction;
   private Action failTestAction;
   private Action clearTestStateAction;
   
   // actions for the toolbar
   private Action openTestStateAction;
   private Action saveTestStateAction;
   private Action saveTestStateAsAction;
   
   private ChartTestEditorInput editorInput;
   
   public String storageFile = "";
   
   public ChartTestView()
   {
   }
   
   public void createPartControl(Composite parent)
   {
      ChartTestContentProvider contentProvider;
      
      contentProvider = new ChartTestContentProvider();
      viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
      viewer.setUseHashlookup(true);
      viewer.setContentProvider(contentProvider);
      viewer.setLabelProvider(new ChartTestLabelProvider());
      viewer.setInput(contentProvider.getInitialInput());       
      
      viewer.addOpenListener(new DoubleClickTreeListener());
      
      createActions();
      createContextMenu();
      contributeToActionBars();
   }
   
   private void createActions()
   {
      validateTestAction = new ValidateTestAction();
      failTestAction = new FailTestAction();
      clearTestStateAction = new ClearTestStateAction();
      openTestStateAction = new OpenTestStateAction();
      saveTestStateAction = new SaveTestStateAction();
      saveTestStateAsAction = new SaveTestStateAsAction();
   }
   
   private void createContextMenu()
   {
      MenuManager menuMgr;
      Menu contextMenu;
      
      menuMgr = new MenuManager();
      menuMgr.setRemoveAllWhenShown(true);
      menuMgr.addMenuListener(new ContextMenuHandler());
      contextMenu = menuMgr.createContextMenu(viewer.getControl());
      viewer.getControl().setMenu(contextMenu);
      getSite().registerContextMenu(menuMgr, viewer);
   }
   
   private void contributeToActionBars()
   {
      IActionBars bars = getViewSite().getActionBars();
      fillLocalToolBar(bars.getToolBarManager());
   }
   
   private void fillLocalToolBar(IToolBarManager manager)
   {
      manager.add(openTestStateAction);
      manager.add(saveTestStateAction);
      saveTestStateAction.setEnabled(false);
      if (storageFile != null && storageFile.compareTo("") != 0)
         saveTestStateAction.setEnabled(true);
      manager.add(saveTestStateAsAction);
   }

   /**
    * returns the selected node
    */
   public ChartTestModelNode getSelectedNode()
   {
      ChartTestModelNode selected = null;
      ISelection selection = viewer.getSelection();
      if (!(selection instanceof TreeSelection))
         return null;
      
      TreeSelection treeSelection = (TreeSelection)selection;
      Object data = treeSelection.getFirstElement();
      
      if (!(data instanceof ChartTestModelNode))
         return null;
      
      selected = (ChartTestModelNode)data;
      
      return selected;
   }
   
   /**
    * returns the root node
    */
   public ChartTestModelNode getRoot()
   {
      ChartTestContentProvider contentProvider = (ChartTestContentProvider)viewer.getContentProvider();      
      return contentProvider.getInitialInput();
   }
   
   public void setFocus()
   {
      // TODO Auto-generated method stub
   }

   /**
    * add the context menu only for TestChart nodes
    *
    */
   class ContextMenuHandler implements IMenuListener
   {
      public void menuAboutToShow(IMenuManager manager)
      {
         ChartTestModelNode node = getSelectedNode();
         if (node.isTest())
         {
            manager.add(validateTestAction);
            manager.add(failTestAction);
            manager.add(clearTestStateAction);
         }
      }
   }

   /**
    * on double click open a TestChart into the editor
    *
    */
   class DoubleClickTreeListener implements IOpenListener
   {
      public void open (OpenEvent oe)
      {  
         ChartTestModelNode modelNode = getSelectedNode();
         
         if (modelNode == null)
            return;
         
         // TODO : need so start the editor here
         if (modelNode.isTest())
         {
            
            Object info = modelNode.getInfo();
            if (! (info instanceof ChartTest))
               return;
            
            ChartTest test = (ChartTest)info;  
            
            // FIXME : make the functions return boolean and check if the test was actually started
            
            TestGenerator.startTest(modelNode, test.getChartTestID());

            editorInput = new ChartTestEditorInput(test.getChart(), test.getTestDescription());
               
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

            if (page != null)
            {
               try
               {
                  page.openEditor(editorInput, ChartTestPlugin.CHART_TEST_EDITOR_ID);
               }
               catch (PartInitException e)
               {
                  System.out.println("We caught an exception" + e);
               }
            }  
         }
      }
   }
      
   /**
    * select this option from the context menu to validate a test
    *
    */
   class ValidateTestAction extends Action
   {
      public ValidateTestAction()
      {
         super("Validate Test");
         setToolTipText("Validate Test");
         setImageDescriptor(ChartTestImages.DESC_OBJ_VALIDATE);
      }

      public void run()
      {
         ChartTestModelNode selected = getSelectedNode();
         if (selected == null)
            return;
         
         Object info = selected.getInfo();
         if (info instanceof ChartTest)
         {
            ChartTest test = (ChartTest)info;
            test.setState(ChartTestConstants.STATE_TEST_VALIDATED);
            viewer.refresh(true);
         }  
      }
   }
   
   /**
    * select this option from the context menu to fail a test
    *
    */
   class FailTestAction extends Action
   {
      public FailTestAction()
      {
         super("Fail Test");
         setToolTipText("Fail Test");
         setImageDescriptor(ChartTestImages.DESC_OBJ_FAIL);         
      }

      public void run()
      {
         ChartTestModelNode selected = getSelectedNode();
         if (selected == null)
            return;
         
         Object info = selected.getInfo();
         if (info instanceof ChartTest)
         {
            ChartTest test = (ChartTest)info;
            test.setState(ChartTestConstants.STATE_TEST_FAILED);
            viewer.refresh(true);
         }
      }
   }
   
   class ClearTestStateAction extends Action
   {
      public ClearTestStateAction()
      {
         super("Clear Test State");
         setToolTipText("Clear Test State");
         setImageDescriptor(ChartTestImages.DESC_OBJ_OPEN_TESTS_STATE);
      }

      public void run()
      {
         ChartTestModelNode selected = getSelectedNode();
         if (selected == null)
            return;
         
         Object info = selected.getInfo();
         if (info instanceof ChartTest)
         {
            ChartTest test = (ChartTest)info;
            test.setState(ChartTestConstants.STATE_TEST_UNTESTED);
            viewer.refresh(true);
         }
      }
   }
   
   /**
    * opens a file that has Test states stored
    *
    */
   class OpenTestStateAction extends Action
   {
      public OpenTestStateAction()
      {
         super("");
         setToolTipText("Open Tests State");
         setImageDescriptor(ChartTestImages.DESC_OBJ_OPEN_TESTS_STATE);         
      }
      
      public void run()
      {
         FileDialog fileDialog = new FileDialog(viewer.getControl().getShell(), SWT.OPEN);
         fileDialog.setText("Open Tests State");
         storageFile = fileDialog.open();
         
         if (storageFile != null)
            saveTestStateAction.setEnabled(true);
         
         try
         {
            TreeUtils.readTree(getRoot(), storageFile);
            viewer.refresh(true);
         }
         catch(Exception e)
         {
         }
      }
   }
   
   /**
    * save the test states to the file located under storageFile
    *
    */
   class SaveTestStateAction extends Action
   {
      public SaveTestStateAction()
      {
         super("");
         setToolTipText("Save Tests State");
         setImageDescriptor(ChartTestImages.DESC_OBJ_SAVE_TESTS_STATE);                  
      }
      
      public void run()
      {
         try
         {
            TreeUtils.saveTree(getRoot(), storageFile);                              
         }
         catch (Exception e)
         {
         }
      }
   }
   
   /**
    * open a save dialog to choose the file location where the test states will be stored
    *
    */
   class SaveTestStateAsAction extends Action
   {
      public SaveTestStateAsAction()
      {
         super("");
         setToolTipText("Save Tests State As");
         setImageDescriptor(ChartTestImages.DESC_OBJ_SAVE_TESTS_STATE_AS);                  
      }
      
      public void run()
      {
         FileDialog fileDialog = new FileDialog(viewer.getControl().getShell(), SWT.SAVE);
         fileDialog.setText("Save Tests State As");
         
         storageFile = fileDialog.open();

         if (storageFile != null)
            saveTestStateAction.setEnabled(true);
         
         try
         {
            TreeUtils.saveTree(getRoot(), storageFile);                           
         }
         catch (Exception e)
         {
         }
      }
   }
}
