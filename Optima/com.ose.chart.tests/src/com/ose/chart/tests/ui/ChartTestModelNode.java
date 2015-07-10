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

package com.ose.chart.tests.ui;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * This is the basic node in the tree viewer
 * 
 * The tree contains all available test cases for the charts and is structured
 * on three levels
 * first level is the root node, not displayed in the tree
 * on second level we select the chart type we want to see 
 * the third level contains the actual tests to be listed in the editor
 * 
 * the tree is source code generated, so we don't need to
 * implement more complex add or remove operations for the nodes.
 *
 */
public class ChartTestModelNode
{
   // the node can be the root node - 0, a chart node - 1, or a test node - 2 
   private int type;
   private String nodeName = "";
   /** the chart test can be failed, untested, passed */
   public int state = ChartTestConstants.STATE_TEST_UNTESTED; // default is untested 

   private ChartTestModelNode parent;
   // a list of TestChartModelNodes that are children to the current node
   private LinkedList childrens;
   
   // the information is a ChartTest object for leaf nodes, or a string otherwise
   private Object info;

   public ChartTestModelNode(int type)
   {
      this.type = type;
      this.info = null;
      this.parent = null;
      this.childrens = new LinkedList();
   }

   public ChartTestModelNode(int type, String nodeName)
   {
      this.type = type;
      this.nodeName = nodeName;
      this.info = null;
      this.parent = null;
      this.childrens = new LinkedList();
   }
   
   public ChartTestModelNode(int type, Object info)
   {
      this.type = type;
      this.info = info;
      this.parent = null;
      this.childrens = new LinkedList();
   }

   public ChartTestModelNode(int type, String nodeName, Object info)
   {
      this.type = type;
      this.info = info;
      this.nodeName = nodeName;
      this.parent = null;
      this.childrens = new LinkedList();
   }
   
   public ChartTestModelNode getParent()
   {
      return parent;
   }
   
   public void setParent(ChartTestModelNode parent)
   {
      this.parent = parent;
   }
   
   public void addChildren(ChartTestModelNode node)
   {
      node.parent = this;
      childrens.add(node);
   }
   
   public ChartTestModelNode[] getChildrens()
   {
      int nrChildrens = childrens.size();
      ChartTestModelNode[] childNodes = new ChartTestModelNode[nrChildrens];
      
      Iterator childrensIterator = childrens.iterator();
      
      int childIndex = 0;
      while (childrensIterator.hasNext())
      {
         childNodes[childIndex++] = (ChartTestModelNode)childrensIterator.next(); 
      }
      return childNodes;
   }
   
   /*
    * get the object containing the information relevant for the node
    */
   public Object getInfo()
   {
      return info;
   }
   
   public void setInfo(Object info)
   {
      this.info = info;
   }
   
   public boolean isRoot()
   {
      return type == ChartTestConstants.ROOT_NODE;
   }
   
   public boolean isChart()
   {
      return type == ChartTestConstants.CHART_NODE;
   }
   
   public boolean isTest()
   {
      return type == ChartTestConstants.TEST_NODE;
   }
   
   public String toString()
   {
      return nodeName;
   }

   public String getNodeName()
   {
      return nodeName;
   }

   public void setNodeName(String nodeName)
   {
      this.nodeName = nodeName;
   }
}
