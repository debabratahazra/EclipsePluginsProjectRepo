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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import com.ose.chart.tests.ui.TestGenerator;
import com.ose.chart.tests.ui.ChartTestModelNode;

/**
 * content provider for the tree viewer, this actually generates the Test Suite
 *
 */
public class ChartTestContentProvider implements ITreeContentProvider
{
   private static final ChartTestModelNode[] EMPTY_ARRAY = new ChartTestModelNode[0];
   private ChartTestModelNode parent; 

   // this creates the root node of the tree
   public ChartTestContentProvider()
   {
      parent = TestGenerator.generateTestSuite();
   }
   
   public ChartTestModelNode getInitialInput()
   {
      return parent;
   }
   
   public Object[] getChildren(Object parentElement)
   {
      if (parentElement instanceof ChartTestModelNode)
      {
         ChartTestModelNode node = (ChartTestModelNode)parentElement;
         return node.getChildrens();
      }
      else
      {
         return EMPTY_ARRAY;
      }
   }

   public Object getParent(Object element)
   {
      if (element instanceof ChartTestModelNode)
      {
         ChartTestModelNode node = (ChartTestModelNode)element;
         return node.getParent();
      }
      else
      {
         return null;
      }
   }

   public boolean hasChildren(Object element)
   {
      return this.getChildren(element).length > 0;
   }

   public Object[] getElements(Object inputElement)
   {
      return getChildren(inputElement);
   }

   public void dispose()
   {
      // Nothing to be done.
   }

   public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
   {
      //viewer.setInput(newInput);
   }
}
