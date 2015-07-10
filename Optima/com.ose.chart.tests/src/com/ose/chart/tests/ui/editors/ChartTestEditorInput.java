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

package com.ose.chart.tests.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import com.ose.chart.ui.Chart;

/**
 * editor input class for our ChartTestEditor
 *
 */
public class ChartTestEditorInput implements IEditorInput
{
   private Chart chart;
   
   private String testDescription;
   
   public ChartTestEditorInput()
   {
      chart = null;
      testDescription = "No chart was provided";
   }
   
   public ChartTestEditorInput(Chart c, String description)
   {
      chart = c;
      testDescription = description;
   }
   
   /**
    * if this method is not implemented, the editor will open the same content
    * multiple times
    */
   public boolean equals(Object obj)
   {
      if (!(obj instanceof ChartTestEditorInput))
         return false;
      ChartTestEditorInput editor = (ChartTestEditorInput)obj;
      // if the chart object are different it's another input
      if (!this.chart.equals(editor.chart))
         return false;
      // if test descriptions are different, it's another input
      if (!this.testDescription.equals(editor.testDescription))
         return false;
      return true;
   }
   
   public boolean exists()
   {
      return false;
   }

   public ImageDescriptor getImageDescriptor()
   {
      return null;
   }

   public String getName()
   {
      return chart.getTitle();
   }

   public IPersistableElement getPersistable()
   {
      return null;
   }

   public String getToolTipText()
   {
      // hover over filename ToolTip
      return chart.getTitle();
   }

   public Object getAdapter(Class adapter)
   {
      return null;
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

   public void setTestDescription(String testDescription)
   {
      this.testDescription = testDescription;
   }
}
