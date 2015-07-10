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

/**
 * Constant values across the package.
 *
 */
public class ChartTestConstants
{
   // chart types
//   public static final int BAR_CHART_2D = 0;
//   public static final int STACKED_BAR_CHART_2D = 1;
//   public static final int BAR_CHART_3D = 2;
//   public static final int CLUSTERED_BAR_CHART_3D = 3;
//   public static final int STACKED_BAR_CHART_3D = 4;
   
   // node types
   public static final int ROOT_NODE = 0;
   public static final int CHART_NODE = 1;
   public static final int TEST_NODE = 2;
   
   // chart test states
   public static final int STATE_TEST_UNTESTED = 0;
   public static final int STATE_TEST_FAILED = 1;
   public static final int STATE_TEST_VALIDATED = 2;

   // tokens for the status file
   public static final String START_TOKEN = "root";
   public static final String CHART_TOKEN = "chart";
   public static final String TEST_TOKEN = "test";
   public static final String END_TOKEN = "end";
}
