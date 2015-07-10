/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.editor.nodes;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class CounterTypeNode extends TreeNode
{
   private final int type;
   private String name;
   private short executionUnit;
   private long triggerValue;
   private int maxReports;
   private int reportCount;
   private int reportLossCount;

   public CounterTypeNode(int type)
   {
      super();
      this.type = type;
   }

   public int getType()
   {
      return type;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public short getExecutionUnit()
   {
      return executionUnit;
   }

   public void setExecutionUnit(short executionUnit)
   {
      this.executionUnit = executionUnit;
   }

   public long getTriggerValue()
   {
      return triggerValue;
   }

   public void setTriggerValue(long triggerValue)
   {
      this.triggerValue = triggerValue;
   }

   public int getMaxReports()
   {
      return maxReports;
   }

   public void setMaxReports(int maxReports)
   {
      this.maxReports = maxReports;
   }

   public int getReportCount()
   {
      return reportCount;
   }

   public void addReportCount(int count)
   {
      reportCount += count;
   }

   public int getReportLossCount()
   {
      return reportLossCount;
   }

   public void addReportLossCount(int count)
   {
      reportLossCount += count;
   }

   public Image getImage()
   {
      return null;
   }

   public Color getColor()
   {
      return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
   }

   public String toString()
   {
      return name;
   }
}
