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

package com.ose.chart.ui;

import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.Range;

public abstract class PolarChart extends Chart
{
   public PolarChart(String title,
                     ChartContentProvider content,
                     ChartColorProvider colors,
                     Camera camera)
   {
      super(title, content, colors, camera);
      
      disableNotifications();
      
      // Future property changes should go here
      
      enableNotifications();
   }
   
   @Override
   public Range getItemWindow()
   {
      ChartContentProvider provider = getContentProvider();
      if (provider == null)
      {
         return null;
      }
      else
      {
         return getContentProvider().getItemRange();
      }
   }
   
   @Override
   public void contentChanged(ChartContentProvider provider)
   {
      super.contentChanged(provider);
   }   
}
