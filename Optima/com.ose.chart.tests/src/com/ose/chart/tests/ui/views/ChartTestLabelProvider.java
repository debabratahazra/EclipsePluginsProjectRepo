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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.chart.tests.ui.ChartTestConstants;
import com.ose.chart.tests.ui.ChartTest;
import com.ose.chart.tests.ui.ChartTestImages;
import com.ose.chart.tests.ui.ChartTestModelNode;

public class ChartTestLabelProvider extends LabelProvider
{
   public String getText(Object obj)
   {
      return obj.toString();
   }

   public Image getImage (Object obj)
   {
      if (! (obj instanceof ChartTestModelNode))
         return null;
      
      ChartTestModelNode node = (ChartTestModelNode)obj;
      
      String imageKey = null;
      
      if (node.isTest())
      {
         ChartTest test = (ChartTest)node.getInfo();
         switch (test.getState())
         {
         case ChartTestConstants.STATE_TEST_UNTESTED:
            imageKey = ChartTestImages.IMG_OBJ_CHART_TEST;
            break;
         case ChartTestConstants.STATE_TEST_FAILED:
            imageKey = ChartTestImages.IMG_OBJ_CHART_TEST_FAILED;
            break;
         case ChartTestConstants.STATE_TEST_VALIDATED:
            imageKey = ChartTestImages.IMG_OBJ_CHART_TEST_VALIDATED;
            break;
         default:
            return null;      
         }
      }
      else if (node.isChart())
      {
         int chartState;
         // check the status of the children nodes
         ChartTestModelNode children[] = node.getChildrens();
         
         // if there is no children, the chart is untested
         if (children.length == 0)
            chartState = ChartTestConstants.STATE_TEST_UNTESTED;
         else
         {
            // assume it is validated
            chartState = ChartTestConstants.STATE_TEST_VALIDATED;
            for (int i = 0; i < children.length; i++)
            {
               ChartTest test = (ChartTest)children[i].getInfo();
               // if any test is failed, the chart is failed, no need to check further
               if (test.getState() == ChartTestConstants.STATE_TEST_FAILED)
               {
                  chartState = ChartTestConstants.STATE_TEST_FAILED;
                  break;
               }
               // if the chart isn't failed but some tests are untested, the chart is untested
               else if (test.getState() == ChartTestConstants.STATE_TEST_UNTESTED)
               {
                  chartState = ChartTestConstants.STATE_TEST_UNTESTED;
               }
            }
         }
         
         switch (chartState)
         {
         case ChartTestConstants.STATE_TEST_UNTESTED:
            imageKey = ChartTestImages.IMG_OBJ_CHART_TYPE;
            break;
         case ChartTestConstants.STATE_TEST_FAILED:
            imageKey = ChartTestImages.IMG_OBJ_CHART_TYPE_FAILED;
            break;
         case ChartTestConstants.STATE_TEST_VALIDATED:
            imageKey = ChartTestImages.IMG_OBJ_CHART_TYPE_VALIDATED;
            break;
         default:
            return null;      
         }
      }
  
      return ChartTestImages.get(imageKey);
   }
}
