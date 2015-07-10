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

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.locator.IItemLocator;
import com.windowtester.runtime.locator.WidgetReference;
import com.windowtester.runtime.swt.condition.SWTIdleCondition;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import abbot.tester.swt.TreeItemTester;
import junit.framework.TestCase;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TreeItemPathLocator
{
   private int _index;

   private String _labelPattern;

   private TreeItemPathLocator _child;

   private TreeItemPathLocator _parent;

   private Tree _parentTree;

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern)
   {
      this(labelPattern, -1, null, null);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(int index)
   {
      this(null, index, null, null);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern, int index)
   {
      this(labelPattern, index, null, null);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern, TreeItemPathLocator child)
   {
      this(labelPattern, -1, null, child);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern, Tree parentTree)
   {
      this(labelPattern, -1, parentTree, null);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(int index, TreeItemPathLocator child)
   {
      this(null, index, null, child);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(int index, Tree parentTree)
   {
      this(null, index, parentTree, null);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern, int index,
         TreeItemPathLocator child)
   {
      this(labelPattern, index, null, child);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern, int index, Tree parentTree)
   {
      this(labelPattern, index, parentTree, null);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(String labelPattern, Tree parentTree,
         TreeItemPathLocator child)
   {
      this(labelPattern, -1, parentTree, child);
   }

   /**
    * @see TreeItemPathLocator#TreeItemPathLocator(String, int, Tree,
    *      TreeItemPathLocator)
    */
   public TreeItemPathLocator(int index, Tree parentTree,
         TreeItemPathLocator child)
   {
      this(null, index, parentTree, child);
   }

   /**
    * Describes a node in a tree path to a TreeItem.
    * 
    * @param labelPattern
    *           - The text label as seen on the TreeItem or a regular expression
    *           that identifies the TreeItem by label. This parameter is
    *           optional. If <code>null</code> then find the first TreeItem
    *           matching the union of the other criteria
    * @param index
    *           - The index of the TreeItem in its parent TreeItem (or Tree if
    *           this is a root node). This parameter is optional. If -1 then
    *           find the first TreeItem matching the union of the other criteria
    * @param parentTree
    *           - The Tree widget containing this TreeItem. This parameter is
    *           optional. If <code>null</code> then find the first TreeItem
    *           matching the union of the other criteria (the first TreeItem as
    *           searched in all visible Tree widgets that match the other
    *           criteria will be found)
    * @param child
    *           - The description of the child node to click, or <code>
     *                       null</code>
    *           if this is the leaf node in the tree path that is to be clicked.
    */
   public TreeItemPathLocator(String labelPattern, int index, Tree parentTree,
         TreeItemPathLocator child)
   {
      _index = index;
      _labelPattern = labelPattern;
      _child = child;
      _parentTree = parentTree;

      if (_child != null)
      {
         _child.setParent(this);
      }
   }

   /**
    * Walk the tree path described by this node: Click this node to select it
    * then, if there is a child tree path node, expand this node and recurse
    * into the child TreeItemPathLocator
    * 
    * @param ui
    *           - Driver for UI-generated input
    * @throws WidgetSearchException
    *            - If the TreeItem described by this tree item path cannot be
    *            found for any reason
    */
   public void findAndClick(IUIContext ui) throws WidgetSearchException
   {
      findAndClick(ui, null, 1);
   }

   public void findAndDoubleClick(IUIContext ui) throws WidgetSearchException
   {
      findAndClick(ui, null, 2);
   }

   public void findAndClick(IUIContext ui, int clickCount)
         throws WidgetSearchException
   {
      findAndClick(ui, null, clickCount);
   }

   private TreeItem findAndClick(IUIContext ui, TreeItem parent)
         throws WidgetSearchException
   {
      return findAndClick(ui, parent, 1);
   }
   
   /**
    * Internal method used for recursion.
    * 
    * @return TreeItem - The leaf node that was found
    */
   private TreeItem findAndClick(IUIContext ui, TreeItem parent, int clickCount)
         throws WidgetSearchException
   {
      TreeItemByIndexLocator treeItemLoc = new TreeItemByIndexLocator(_index,
            _labelPattern, parent, _parentTree);
      ui.click(_child == null ? clickCount : 1, treeItemLoc);

      TreeItem thisItem = treeItemLoc.getFoundItem();
      String message = MessageFormat.format(
            "DID NOT FIND A TREE NODE FOR ITEM <{0}>", //$NON-NLS-1$
            new Object[] { toString() });
      TestCase.assertNotNull(message, thisItem);

      TreeItem returnItem = thisItem;

      if (_child != null)
      {
         TreeItemTester itemTester = new TreeItemTester();
         itemTester.actionExpandItem(thisItem);

         // Let the sub-nodes render
         new SWTIdleCondition().waitForIdle();

         // Just for good measure
         ui.pause(1000);

         returnItem = _child.findAndClick(ui, thisItem, clickCount);
      }

      return returnItem;
   }

   /**
    * Walk the tree path described by this node: Click this node to select it
    * then, if there is a child tree path node, expand this node and recurse
    * into the child TreeItemPathLocator. When the leaf node is found, context
    * click the node and execute the menu action with the given menu item label
    * 
    * @param ui
    *           - Driver for UI-generated input
    * @param menuItem
    *           - The path to the menu item to select (can be a regular
    *           expression as described in the StringComparator utility)
    * @throws WidgetSearchException
    *            - If the TreeItem described by this tree item path cannot be
    *            found for any reason
    */
   public void findAndContextClick(IUIContext ui, String menuPath)
         throws WidgetSearchException
   {
      TreeItem leafNode = findAndClick(ui, null);
      ui.contextClick(new WidgetReference(leafNode), menuPath);
   }

   /**
    * @param parent
    *           - Used to debugging purposes only
    */
   private void setParent(TreeItemPathLocator parent)
   {
      _parent = parent;
   }

   /**
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      StringBuilder builder = new StringBuilder();

      if (_parent != null)
      {
         builder.append(_parent.toString());
      }

      builder.append("/"); //$NON-NLS-1$
      builder.append(_labelPattern);

      return builder.toString();
   }

   /**
    * Manages the location and selection of TreeItems in a Tree.
    */
   private class TreeItemByIndexLocator extends SWTWidgetLocator implements
         IItemLocator
   {
      private static final long serialVersionUID = 1L;

      private int _count;

      private TreeItem _parentItem;

      private Tree _parentTree;

      private TreeItemTester _itemTester;

      private String _rawLabel;

      private Pattern _pattern;

      private TreeItem _foundItem = null;

      /**
       * Save the data members.
       */
      public TreeItemByIndexLocator(int index, String labelPattern,
            TreeItem parentItem, Tree parentTree)
      {
         super(TreeItem.class);
         _count = index;
         _itemTester = new TreeItemTester();
         _parentItem = parentItem;
         _parentTree = parentTree;

         _rawLabel = labelPattern;

         try
         {
            _pattern = Pattern.compile(labelPattern);
         }
         catch (PatternSyntaxException e)
         {
         }
      }

      /**
       * @return TreeItem - The TreeItem that was found and clicked with this
       *         widget locator
       */
      public TreeItem getFoundItem()
      {
         return _foundItem;
      }

      /**
       * @return String - The path to the item, or
       * 
       *         <pre>
       * null
       * </pre>
       * 
       *         <p>
       *         if it is not found.
       *         </p>
       */
      public String getPath()
      {
         StringBuffer sb = new StringBuffer(0);

         if (_foundItem != null)
         {
            sb.append(_foundItem.getParentItem().toString());
            sb.append(File.separatorChar);
            sb.append(_foundItem.toString());
            IPath path = new Path(sb.toString());

            return path.toPortableString();
         }
         else
         {
            return null;
         }
      }

      /**
       * @see com.windowtester.runtime.swt.locator.SWTWidgetLocator#matches(java.lang.Object)
       */
      @Override
      public boolean matches(Object obj)
      {
         boolean matches = false;

         if ((_foundItem == null) && (obj != null)
               && (TreeItem.class.isAssignableFrom(obj.getClass())))
         {
            TreeItem testedItem = (TreeItem) obj;

            if (_parentItem != null)
            {
               TreeItem testedParent = _itemTester.getParentItem(testedItem);

               if (_parentItem != testedParent)
               {
                  return false;
               }
            }

            if (_parentTree != null)
            {
               Tree testedTree = _itemTester.getParent(testedItem);

               if (_parentTree != testedTree)
               {
                  return false;
               }
            }

            if (_rawLabel != null)
            {
               String testedLabel = _itemTester.getText(testedItem);

               if (!matchesLabel(testedLabel))
               {
                  return false;
               }
            }

            matches = _count <= 0;
            _count--;
            //System.err.println("---->> " + obj.getClass().getName() + "(" + (_count + 1) + ")::" + (matches)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

            if (matches)
            {
               _foundItem = testedItem;
            }
         }

         return matches;
      }

      /**
       * @return boolean - True if the given value is exactly equal to the
       *         expected label or the given value matches a regex expression
       *         compiled from the expected label pattern; False otherwise
       */
      private boolean matchesLabel(String value)
      {
         boolean matches = _rawLabel.equals(value);
         ;

         if (!matches && (_pattern != null))
         {
            Matcher m = _pattern.matcher(value);
            matches = m.matches();
         }

         return matches;
      }
   }
}
