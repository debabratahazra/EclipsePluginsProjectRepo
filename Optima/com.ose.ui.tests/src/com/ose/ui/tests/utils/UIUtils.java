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

package com.ose.ui.tests.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.views.process.ProcessView;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.EditorLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class UIUtils
{
   public static final int SYSTEM_BROWSER_TOOLBAR_UPDATE = 0;

   public static final int SYSTEM_BROWSER_TOOLBAR_CLEAN = 1;

   public static final int SYSTEM_BROWSER_TOOLBAR_FIND_GATES = 3;

   public static final int SYSTEM_BROWSER_TOOLBAR_ADD_GATE = 4;

   public static final int SYSTEM_BROWSER_TOOLBAR_ADD_TARGET = 5;

   public static final int SYSTEM_BROWSER_TOOLBAR_CONNECT = 7;

   public static final int SYSTEM_BROWSER_TOOLBAR_DISCONNECT = 8;

   public static final int SYSTEM_BROWSER_TOOLBAR_LINK_WITH_EDITOR = 10;

   public static void clickSystemBrowserContextMenuItem(IUIContext ui,
         String text) throws Exception
   {
      ui.contextClick(new SWTWidgetLocator(Tree.class, new ViewLocator(
            "com.ose.system.ui.views.system.SystemView")), text);
   }

   public static void closeVisibleEditor(IUIContext ui, String editorTitleRegex)
         throws Exception
   {
      EditorLocator editorLocator = new EditorLocator(editorTitleRegex);

      // Wait for editor to become visible, close it, wait for it disappear.
      ui.wait(editorLocator.isVisible());
      ui.ensureThat(new CTabItemLocator(editorTitleRegex).isClosed());
      ui.wait(editorLocator.isVisible(false));
   }

   public static String getText(StyledText widget)
   {
      return (String) (new UIFunctor()
      {
         public Object function(Object input)
         {
            return ((StyledText) input).getText();
         }
      }).call(widget);
   }

   public static String getText(Text widget)
   {
      return (String) (new UIFunctor()
      {
         public Object function(Object input)
         {
            return ((Text) input).getText();
         }
      }).call(widget);
   }

   public static String getText(Tree tree, final int index)
   {
      return (String) (new UIFunctor()
      {
         public Object function(Object input)
         {
            return ((Tree) input).getItem(index).getText();
         }
      }).call(tree);
   }

   public static String getText(TreeItem item)
   {
      return (String) (new UIFunctor()
      {
         public Object function(Object input)
         {
            return ((TreeItem) input).getText();
         }
      }).call(item);
   }

   public static String getText(TableItem item, final int index)
   {
      return (String) (new UIFunctor()
      {
         public Object function(Object input)
         {
            return ((TableItem) input).getText(index);
         }
      }).call(item);     
   }

   public static boolean compareItemElements(TableItem item, String property,
         int index)
   {
      String itemColumnValue = getText(item, index);
      
      switch (index)
      {
         case ProcessView.COLUMN_SIGSELECT_COUNT:
            if (itemColumnValue.equalsIgnoreCase("All"))
            {
               itemColumnValue = "1";
            }
            break;            
      }

      if (itemColumnValue.equalsIgnoreCase(property))
      {
         return true;
      }
      return false;
   }

   public static String toHexString(String value)
   {
      if (value.startsWith("0x"))
      {
         return value;
      }
      return StringUtils.toHexString(Integer.parseInt(value));      
   }

   public static String toProcessTypeString(String value)
   {
      try
      {
         return StringUtils.toProcessTypeString(Integer.parseInt(value));
      }
      catch (NumberFormatException nfe)
      {
         return value;
      }
   }

   public static String toProcessStateString(String value)
   {
      try
      {
         return StringUtils.toProcessStateString(Integer.parseInt(value));
      }
      catch (NumberFormatException nfe)
      {
         return value;
      }
   }

   public static String toUIString(int value)
   {
      if (value == 0)
      {
         return "N/A";
      }
      return toString(value);      
   }

   public static String toString(int value)
   {
      return Integer.toString(value);      
   }

   public static void delay(IUIContext ui, int value)
   {
      ui.pause(value);
   }

   /**
    * Return a list of all processes having the given property
    * @param targetData
    * @param property
    * @return
    */
   public static List<String[]> getValues(List<String[]> entities,
         String property, int propertyIndex)
   {
      List<String[]> processes = new ArrayList<String[]>();

      for (String[] entity : entities)
      {
         String value = entity[propertyIndex];
         if (value != null && value.equalsIgnoreCase(property))
         {
            processes.add(entity);
            break;
         }
      }

      return Collections.unmodifiableList(processes);
   }

   public static String getTargetNodeLabel(String gateNodeLabel)
   {
      String targetNodeLabel = gateNodeLabel.substring(0,
            gateNodeLabel.length() - 1)
            + "\\/)";
      return gateNodeLabel + "/" + targetNodeLabel;
   }

   public static String getMainSegmentNodeLabel(String targetNodeLabel)
   {
      return targetNodeLabel + "/" + "main (0x0)";
   }

   public static Tree getTreeInView(IUIContext ui, String viewId)
         throws Exception
   {
      // Find a reference to the tree widget in the specified view
      SWTWidgetLocator parentLocator = new SWTWidgetLocator(Tree.class,
            new ViewLocator(viewId));

      IWidgetReference reference = (IWidgetReference) ui.find(parentLocator);

      // Return the widget
      return (Tree) reference.getWidget();
   }

   public static TreeItemPathLocator createItemPathLocator(IUIContext ui, 
         String path, String treeId) throws Exception
   {
      return createItemPathLocator(ui, new String[] {path}, treeId);
   }

   public static TreeItemPathLocator createItemPathLocator(IUIContext ui,
         String[] path, String treeId) throws Exception
   {
      Tree tree = UIUtils.getTreeInView(ui, treeId);
      return createItemPathLocator(path, tree);
   }

   public static TreeItemPathLocator createItemPathLocator(String path,
         Tree tree)
   {
      return createItemPathLocator(new String[] {path}, tree);
   }

   public static TreeItemPathLocator createItemPathLocator(String[] path,
         Tree tree)
   {
      TreeItemPathLocator previous = new TreeItemPathLocator(
            path[path.length - 1], tree);
      for (int i = path.length - 2; i >= 0; i--)
      {
         TreeItemPathLocator locator = new TreeItemPathLocator(path[i], tree,
               previous);
         previous = locator;
      }
      return previous;
   }

   public static LinkedList<TreeData> getTreeDataInView(final IUIContext ui,
         final String treeId) throws Exception
   {
      final LinkedList<TreeData> treeDataList = new LinkedList<TreeData>();

      Display.getDefault().syncExec(new Runnable()
      {
         public void run()
         {
            try
            {
               Tree tree = getTreeInView(ui, treeId);
               TreeViewer treeViewer = new TreeViewer(tree);
               treeViewer.expandAll();
               MiscUtils.waitForJobs(ui);

               TreeItem[] items = tree.getItems();
               traverseTree2(items, treeDataList, 0);
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      });
      return treeDataList;
   }

   public static TreeItem getItemReference(IUIContext ui, String targetRegex)
         throws Exception
   {
      // Get the target node information
      IWidgetReference reference = (IWidgetReference) ui
            .click(new TreeItemLocator(targetRegex, new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      return (TreeItem) reference.getWidget();
   }

   private static int traverseTree2(TreeItem[] items,
         LinkedList<TreeData> treeDataList, int index)
   {
      for (int i = 0; i < items.length; i++)
      {
         TreeItem item = items[i];
         String text = item.getText();
         treeDataList.add(new TreeData(getId(text), getName(text), item
               .getImage(), index));
         index++;
         if (items[i].getExpanded())
         {
            index = traverseTree2(items[i].getItems(), treeDataList, index);
         }
      }
      return index;
   }

   private static String getName(String input)
   {
      int start = 0;
      int end = input.lastIndexOf(' ');
      return input.substring(start, end);
   }

   private static String getId(String input)
   {
      int start = input.indexOf('(');
      int end = input.lastIndexOf(')');
      return input.substring(start + 1, end);
   }
}
