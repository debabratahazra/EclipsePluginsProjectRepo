/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.chart.model;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Provides content to charts. In order to allow dynamic content and not
 * dictate (more than necessary) how the implementation organizes
 * its data structures hierarchically, the interface functions make a few
 * semantic assumptions, that are not programmatically enforced. 
 * 
 * <ul>
 * <li>An <i>item</i> represents one discrete double precision value.</li>
 * <li>A <i>series</i> is an indexed collection of <i>items</i>.</li>
 * <li>A <i>layer</i> is used for conceptually stacking/clustering items.</li> 
 * </ul>
 * 
 * Example:
 * Every month over a period of 10 years (1998-2008), the temperature for a
 * given location was recorded on a daily basis. The highest, lowest and
 * average temperature for each month was noted.     
 *  
 * This kind of data would be organized as ten series (indexed 0 to 9)
 * where each series represents a year. Each series would have twelve item
 * slots, (indexed 0 to 11), one for each month. The low, high and average
 * temperature would be items, each in a different layer.
 * 
 * <pre>
 * lowTempApr2001 = provider.value(0, 3, 3); // low, 2001, April 
 * highTempAug2002 = provider.value(1, 4, 7); // high, 2002, August
 * avgTempApr2003 = provider.value(2, 5, 4); // avg, 2003, May
 * </pre> 
 */
public abstract class ChartContentProvider
{
   private LinkedList listeners = new LinkedList();

   public abstract double getValue(int layer, int series, int item);
   public abstract String getValueLabel(int layer, int series, int item);
   public abstract String getItemLabel(int layer, int series, int item);   
   public abstract String getSeriesLabel(int layer, int series);
   public abstract String getLayerLabel(int layer);
   public abstract Range getItemRange();
   public abstract Range getSeriesRange();
   public abstract Range getLayerRange();
   
   public void addContentChangeListener(ContentChangeListener listener)
   {
      listeners.add(listener);
   }

   public void removeContentChangeListener(ContentChangeListener listener)
   {
      listeners.remove(listener);
   }

   protected void notifyContentChanged()
   {
      ListIterator iterator = listeners.listIterator();

      while (iterator.hasNext())
      {
         ((ContentChangeListener)iterator.next()).contentChanged(this);
      }
   }
}

