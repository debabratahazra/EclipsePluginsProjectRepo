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

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

/**
 * contains methods used for operations specific to a ChartTestModelNode object
 * The main purpose is to handle the states and implement methods for the validation 
 * mechanism
 *
 */
public class TreeUtils
{   
   /**
    * prints the tree to the console
    * @param node
    */
   public static void printTree(ChartTestModelNode node)
   {
      if (node.isRoot())
      {
         System.out.println("root");
         ChartTestModelNode[] charts = node.getChildrens();
         for (int i = 0; i < charts.length; i++)
         {
            printTree(charts[i]);
         }
      }
      else if (node.isChart())
      {
         System.out.println("Chart -> " + node);
         ChartTestModelNode[] tests = node.getChildrens();
         for (int i = 0; i < tests.length; i++)
         {
            printTree(tests[i]);
         }
      }
      else if (node.isTest())
      {   
         System.out.println("Test -> " + node);
      }
   }

   /**
    * save the information from the tree specified by node to the file specified by filename
    * File format is
    * START_TOKEN
    * CHART_TOKEN   chartName
    * TEST_TOKEN    testName    state
    * TEST_TOKEN    testName    state
    * CHART_TOKEN   chartName
    * TEST_TOKEN    testName    state
    * ....
    * END_TOKEN
    * @param node
    * @param filename
    * @throws IOException
    */
   public static void saveTree(ChartTestModelNode node, String filename) throws IOException
   {
      FileWriter writer = new FileWriter(filename, false);
      if (node.isRoot()){
         writer.write(ChartTestConstants.START_TOKEN + "\n");
         ChartTestModelNode[] charts = node.getChildrens();
         
         for (int i = 0; i < charts.length; i++)
         {
            if (charts[i].isChart())
            {
               writer.write(ChartTestConstants.CHART_TOKEN + "\t" + charts[i] + "\n");
               ChartTestModelNode[] tests = charts[i].getChildrens();
               
               for (int j = 0; j < tests.length; j++)
               {
                  if (tests[j].isTest())
                  {
                     Object info = tests[j].getInfo();
                     if (info instanceof ChartTest)
                     {
                        ChartTest chartTest = (ChartTest)info; 
                        writer.write(ChartTestConstants.TEST_TOKEN + "\t" + tests[j] + "\t" + chartTest.getState() + "\n");
                     }
                  }
               }
            }
         }  
         writer.write(ChartTestConstants.END_TOKEN + "\n");
      }
      writer.close();
   }

   /**
    * we try to load the stated for the ChartTests in the tree from the file
    * specified by filename. The file is parsed based on the assumption that it
    * has the format specified by saveTree() method.
    * @param root
    * @param filename
    * @throws FileNotFoundException
    * @throws IOException
    */
   public static void readTree(ChartTestModelNode root, String filename) throws FileNotFoundException, IOException
   {
      RandomAccessFile reader = new RandomAccessFile(filename, "r");
      
      String line;
      String chartName = null;
      String chartTestName = null;

      while ((line = reader.readLine()) != null)
      {
         StringTokenizer strTok = new StringTokenizer(line, "\t");
                  
         if (strTok.hasMoreTokens())
         {
            String token;
            token = strTok.nextToken();
            if (token.compareTo(ChartTestConstants.START_TOKEN) == 0)
            {
            }
            else if (token.compareTo(ChartTestConstants.CHART_TOKEN) == 0)
            {
               chartName = null;
               if (strTok.hasMoreTokens()){
                  token = strTok.nextToken();
                  chartName = token;
               }
            }
            else if (token.compareTo(ChartTestConstants.TEST_TOKEN) == 0)
            {
               chartTestName = null;
               if (chartName != null && strTok.hasMoreTokens())
               {
                  token = strTok.nextToken();
                  chartTestName = token;
                  
                  if (strTok.hasMoreTokens())
                  {
                     int state;
                     try
                     {
                        state = Integer.parseInt(strTok.nextToken());
                     }
                     catch (NumberFormatException e)
                     {
                        state = ChartTestConstants.STATE_TEST_UNTESTED;
                     }
                     setState(root, chartName, chartTestName, state);
                  }
               }
            }
            else if (token.compareTo(ChartTestConstants.END_TOKEN) == 0)
            {
               reader.close();
               return;
            }
         }
      }
      reader.close();
      return;
   }

   /**
    * method to set the state of a ChartTest, identified by the Tree, 
    * chartName and chartTestName
    * 
    * maybe rewrite this function non-recursive
    * 
    * @param root
    * @param chartName
    * @param chartTestName
    * @param state
    */
   public static void setState(ChartTestModelNode node, String chartName, String chartTestName, int state)
   {
      if (node.isRoot())
      {
         ChartTestModelNode charts[] = node.getChildrens();
         for (int i = 0; i < charts.length; i++)
         {
            setState(charts[i], chartName, chartTestName, state);
         }
      }
      else if (node.isChart())
      {
         if (node.toString().compareTo(chartName) != 0)
            return;
         ChartTestModelNode tests[] = node.getChildrens();
         for (int i = 0; i < tests.length; i++)
         {
            setState(tests[i], chartName, chartTestName, state);
         }
      }
      else if (node.isTest())
      {
         if (node.toString().compareTo(chartTestName) != 0)
            return;
         ChartTestModelNode parent = node.getParent();
         if (parent.toString().compareTo(chartName) != 0)
            return;
         Object info = node.getInfo();
         if (!(info instanceof ChartTest))
            return;
         ChartTest test = (ChartTest)info;
         test.setState(state);
      }
      return;
   }

   /**
    * used for initial testing
    */
   public ChartTestModelNode generateTree()
   {
      ChartTestModelNode root = new ChartTestModelNode(0, "root");
      
      ChartTestModelNode chart1 = new ChartTestModelNode(1, "chart1");
      ChartTestModelNode test11 = new ChartTestModelNode(2, "chart1test1");
      ChartTestModelNode test12 = new ChartTestModelNode(2, "chart1test2");
      ChartTestModelNode test13 = new ChartTestModelNode(2, "chart1test3");
      ChartTestModelNode test14 = new ChartTestModelNode(2, "chart1test4");

      chart1.addChildren(test11);
      chart1.addChildren(test12);
      chart1.addChildren(test13);
      chart1.addChildren(test14);
      
      ChartTestModelNode chart2 = new ChartTestModelNode(1, "chart2");
      ChartTestModelNode test21 = new ChartTestModelNode(2, "chart2test1");
      ChartTestModelNode test22 = new ChartTestModelNode(2, "chart2test2");

      chart2.addChildren(test21);
      chart2.addChildren(test22);

      ChartTestModelNode chart3 = new ChartTestModelNode(1, "chart3");   
      ChartTestModelNode test31 = new ChartTestModelNode(2, "chart3test1");
      ChartTestModelNode test32 = new ChartTestModelNode(2, "chart3test2");
      ChartTestModelNode test33 = new ChartTestModelNode(2, "chart3test3");
      
      chart3.addChildren(test31);
      chart3.addChildren(test32);
      chart3.addChildren(test33);
      
      root.addChildren(chart1);
      root.addChildren(chart2);
      root.addChildren(chart3);
      
      return root;
   }
}
