/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

public abstract class ChartContentTransformer extends ChartContentProvider
{
   private ChartContentProvider sourceContentProvider;

   private final ContentChangeListener sourceContentChangeHandler =
      new ContentChangeHandler();

   public final ChartContentProvider getSourceContentProvider()
   {
      return sourceContentProvider;
   }

   public final void setSourceContentProvider(ChartContentProvider contentProvider)
   {
      if (sourceContentProvider != null)
      {
         sourceContentProvider.removeContentChangeListener(
               sourceContentChangeHandler);
      }
      sourceContentProvider = contentProvider;
      if (sourceContentProvider != null)
      {
         sourceContentProvider.addContentChangeListener(
               sourceContentChangeHandler);
      }
      sourceContentChanged(sourceContentProvider);
      notifyContentChanged();
   }
   
   public abstract int getSourceItem(int item);
   
   public abstract int getSourceSeries(int series);
   
   public abstract int getSourceLayer(int layer);

   protected abstract void sourceContentChanged(ChartContentProvider contentProvider);

   private class ContentChangeHandler implements ContentChangeListener
   {
      public void contentChanged(ChartContentProvider contentProvider)
      {
         sourceContentChanged(contentProvider);
         notifyContentChanged();
      }
   }
}
