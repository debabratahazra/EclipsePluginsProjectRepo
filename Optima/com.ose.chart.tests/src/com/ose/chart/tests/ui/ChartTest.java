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

import com.ose.chart.ui.Chart;

/**
 * data objects that encapsulate information used in the editor
 *
 */
public class ChartTest
{
   private Chart chart;                // the chart we want to display
   private String testDescription;     // a description of the test type
   private int chartTestID;            // id that lets us identify the method to initialize the content
   
   /**
    * the chart test can be failed, untested, passed
    */
   public int state = ChartTestConstants.STATE_TEST_UNTESTED; // default is untested 
   
   public ChartTest()
   {
      chart = null;
      testDescription ="No chart provided";
   }

   public ChartTest(Chart chart, String testDescription)
   {
      this.chart = chart;
      this.testDescription = testDescription;
      this.chartTestID = 0;
   }
   
   public ChartTest(Chart chart, String testDescription, int chartTestID)
   {
      this.chart = chart;
      this.testDescription = testDescription;
      this.chartTestID = chartTestID;
   }
   
   public Chart getChart()
   {
      return chart;
   }

   public void setChart(Chart chart)
   {
      this.chart = chart;
   }

   public String getTestDescription()
   {
      return testDescription;
   }

   public void setTestDescription(String test_description)
   {
      this.testDescription = test_description;
   }

   public int getState()
   {
      return state;
   }

   public void setState(int state)
   {
      this.state = state;
   }

   public int getChartTestID()
   {
      return chartTestID;
   }

   public void setChartTestID(int chartTestID)
   {
      this.chartTestID = chartTestID;
   }
}
