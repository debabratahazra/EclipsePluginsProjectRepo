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

package com.ose.chart.ui;

import org.eclipse.core.runtime.ListenerList;
import java.util.ArrayList;
import java.util.List;
import com.ose.chart.model.ChartContentProvider;
import com.ose.chart.model.ChartContentTransformer;
import com.ose.chart.model.ContentChangeListener;
import com.ose.chart.model.MutableRange;
import com.ose.chart.model.Range;

/**
 * Abstract base class for all charts.
 */
public abstract class Chart implements ContentChangeListener
{
   // --- Start of configurable properties ------------------------------------

   private ChartContentProvider contentProvider;

   private List contentTransformers = new ArrayList();

   private ChartContentProvider content;
   
   private ChartColorProvider colors;
   
   private String title;

   private ColorRGBA backgroundColor = new ColorRGBA(1.0f, 1.0f, 1.0f);

   private ColorRGBA gridColor = new ColorRGBA(0.9f, 0.9f, 0.9f);

   private ColorRGBA textColor = new ColorRGBA(0.0f, 0.0f, 0.0f);
   
   private ColorRGBA deselectedTextColor = new ColorRGBA(0.8f, 0.8f, 0.8f);

   private ColorRGBA axisColor = new ColorRGBA(0.0f, 0.0f, 0.0f);

   private String valueAxisLabel = "Value";

   private String itemAxisLabel = "Item";
   
   private MutableRange itemSelection = null;
   
   private MutableRange seriesSelection = null;
   
   private MutableRange layerSelection = null;

   // --- End of configurable properties --------------------------------------

   private ListenerList updateListeners;
   
   private ListenerList selectionListeners;
   
   private ListenerList windowListeners;

   private Camera camera;
   
   private NotificationState notificationState = new NotificationState();
      
   public Chart(String title,
                ChartContentProvider content,
                ChartColorProvider colors,
                Camera camera)
   {
      disableNotifications();

      this.title = title; 

      updateListeners = new ListenerList();
      selectionListeners = new ListenerList();
      windowListeners = new ListenerList();
      
      setCamera(camera);
      
      // Configurables
      setContentProvider(content);
      setColorProvider(colors);
      
      enableNotifications();
   }
      
   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
      notifyChartUpdated();
   }
   
   public String getValueAxisLabel()
   {
      return valueAxisLabel;
   }
   
   public void setValueAxisLabel(String label)
   {
      valueAxisLabel = label;
      notifyChartUpdated();

   }
   
   public String getItemAxisLabel()
   {
      return itemAxisLabel;
   }
   
   public void setItemAxisLabel(String label)
   {
      itemAxisLabel = label;
      notifyChartUpdated();
   }
      
   public ColorRGBA getBackgroundColor()
   {
      return backgroundColor;
   }

   public void setBackgroundColor(ColorRGBA color)
   {
      backgroundColor = new ColorRGBA(color);
      notifyChartUpdated();
   }

   public ColorRGBA getGridColor()
   {
      return gridColor;
   }

   public void setGridColor(ColorRGBA color)
   {
      gridColor = new ColorRGBA(color);
      notifyChartUpdated();
   }

   public ColorRGBA getTextColor()
   {
      return textColor;
   }

   public void setTextColor(ColorRGBA color)
   {
      textColor = new ColorRGBA(color);
      notifyChartUpdated();
   }

   public ColorRGBA getDeselectedTextColor()
   {
      return deselectedTextColor;
   }

   public void setDeselectedTextColor(ColorRGBA color)
   {
      deselectedTextColor = new ColorRGBA(color);
      notifyChartUpdated();
   }
   
   public ColorRGBA getAxisColor()
   {
      return axisColor;
   }

   public void setAxisColor(ColorRGBA color)
   {
      axisColor = new ColorRGBA(color);
      notifyChartUpdated();
   }
   
   public void setContentProvider(ChartContentProvider chartContentProvider)
   {
      if (chartContentProvider == null)
      {
         throw new NullPointerException("Content provider must be supplied.");
      }
      if (contentTransformers.isEmpty())
      {
         if (contentProvider != null)
         {
            contentProvider.removeContentChangeListener(this);
         }
         contentProvider = chartContentProvider;
         contentProvider.addContentChangeListener(this);
         content = contentProvider;
      }
      else
      {
         ChartContentTransformer firstContentTransformer =
            (ChartContentTransformer) contentTransformers.get(0);
         contentProvider = chartContentProvider;
         firstContentTransformer.setSourceContentProvider(contentProvider);
      }
      clearItemSelection();
      clearSeriesSelection();
      clearLayerSelection();
      notifyChartUpdated();
   }

   // FIXME: Change name?
   public ChartContentProvider getSourceContentProvider()
   {
      return contentProvider;
   }

   public ChartContentProvider getContentProvider()
   {
      return content;
   }

   public void addContentTransformer(ChartContentTransformer contentTransformer)
   {
      if (contentTransformer == null)
      {
         throw new NullPointerException("Content transformer must be supplied.");
      }
      if (content == null)
      {
         throw new IllegalStateException("Content provider has not been set.");
      }
      content.removeContentChangeListener(this);
      contentTransformer.setSourceContentProvider(content);
      contentTransformers.add(contentTransformer);
      content = contentTransformer;
      content.addContentChangeListener(this);
      
      clearSelections();
      notifyChartUpdated();
   }

   public void removeContentTransformer(ChartContentTransformer contentTransformer)
   {
      int i;

      if (contentTransformer == null)
      {
         throw new NullPointerException("Content transformer must be supplied.");
      }

      i = contentTransformers.indexOf(contentTransformer);
      if (i >= 0)
      {
         boolean removeLast;
         ChartContentProvider prev;
         ChartContentTransformer next;

         removeLast = (i == (contentTransformers.size() - 1));
         prev = (i == 0) ? contentProvider :
            (ChartContentProvider) contentTransformers.get(i - 1);
         next = removeLast ? null :
            (ChartContentTransformer) contentTransformers.get(i + 1);
         if (removeLast)
         {
            contentTransformer.removeContentChangeListener(this);
            prev.addContentChangeListener(this);
            content = prev;
         }
         if (next != null)
         {
            next.setSourceContentProvider(prev);
         }
         contentTransformer.setSourceContentProvider(null);
         contentTransformers.remove(i);
         clearItemSelection();
         clearSeriesSelection();
         clearLayerSelection();
         notifyChartUpdated();
      }
   }

   public ChartContentTransformer[] getContentTransformers()
   {
      ChartContentTransformer[] contentTransformerArray =
         new ChartContentTransformer[contentTransformers.size()];
      contentTransformers.toArray(contentTransformerArray);
      return contentTransformerArray;
   }

   public void setColorProvider(ChartColorProvider colorProvider)
   {
      if (colorProvider == null)
      {
         throw new NullPointerException("Color provider must be supplied.");
      }
      colors = colorProvider;
      notifyChartUpdated();
   }

   public ChartColorProvider getColorProvider()
   {
      return colors;
   }
   
   public Range getItemWindow()
   {
      return new MutableRange(0, 1);
   }
   
   public void setItemWindow(int offset, int count)
   {
      // The setItemWindow method restricts the actual window as it pleases.
      // By default it doesn't allow the window to be changed at all.
   }

   public void setItemWindow(Range range)
   {
      setItemWindow(range.getOffset(), range.getCount());
   }
   
   public Range getSeriesWindow()
   {
      return new MutableRange(0, 1);
   }
   
   public void setSeriesWindow(int offset, int count)
   {
      // The setSeriesWindow method restricts the actual window as it pleases.
      // By default it doesn't allow the window to be changed at all.
      return;
   }

   public void setSeriesWindow(Range range)
   {
      setSeriesWindow(range.getOffset(), range.getCount());
   }

   public Range getLayerWindow()
   {
      return new MutableRange(0, 1);
   }
   
   public void setLayerWindow(int offset, int count)
   {
      // The setLayerWindow method restricts the actual window as it pleases.
      // By default it doesn't allow the window to be changed.
      return;      
   }

   public void setLayerWindow(Range range)
   {
      setLayerWindow(range.getOffset(), range.getCount());
   }
   
   public Range getItemSelection()
   {
      if (content != null)
      {
         if (itemSelection == null)
         {
            return content.getItemRange();
         }
         else
         {
            return itemSelection;
         }
      }
      else
      {
         throw new NullPointerException("No content provider set.");
      }
   }
   
   public void setItemSelection(Range range)
   {
      if (range == null)
      {
         throw new NullPointerException("Item selection range can not be null.");
      }
      
      setItemSelection(range.getOffset(), range.getCount());
   }
      
   public void setItemSelection(int offset, int count)
   {
      if (content != null)
      {
         if (itemSelection == null)
         {
            itemSelection = new MutableRange(offset, (count < 1) ? 1 : count);
            adjustSelection();
            notifyItemSelectionChanged();
            notifyChartUpdated();
         }
         else
         {
            int oldOffset = itemSelection.getOffset();
            int oldCount = itemSelection.getCount();
            itemSelection.set(offset, (count < 1) ? 1 : count);
            adjustSelection();
            if (!itemSelection.equals(oldOffset, oldCount))
            {
               notifyItemSelectionChanged();
               notifyChartUpdated();
            }
         }
      }
   }

   public void clearItemSelection()
   {
      if (itemSelection != null)
      {
         itemSelection = null;
         notifyItemSelectionChanged();
         notifyChartUpdated();
      }
   }
      
   public Range getSeriesSelection()
   {
      if (content != null)
      {
         if (seriesSelection == null)
         { 
            return content.getSeriesRange();
         }
         else
         {
            return seriesSelection;
         }
      }
      else
      {
         throw new NullPointerException("No content provider set.");
      }
   }
   
   public void setSeriesSelection(Range range)
   {
      if (range == null)
      {
         throw new NullPointerException("Series selection range can not be null.");
      }
      
      setSeriesSelection(range.getOffset(), range.getCount());
   }
      
   public void setSeriesSelection(int offset, int count)
   {
      if (content != null)
      {
         if (seriesSelection == null)
         {
            seriesSelection = new MutableRange(offset, (count < 1) ? 1 : count);
            adjustSelection();
            notifySeriesSelectionChanged();
            notifyChartUpdated();
         }
         else
         {
            int oldOffset = seriesSelection.getOffset();
            int oldCount = seriesSelection.getCount();
            seriesSelection.set(offset, (count < 1) ? 1 : count);
            adjustSelection();
            if (!seriesSelection.equals(oldOffset, oldCount))
            {
               notifySeriesSelectionChanged();
               notifyChartUpdated();
            }
         }
      }
   }

   public void clearSeriesSelection()
   {
      if (seriesSelection != null)
      {
         seriesSelection = null;
         notifySeriesSelectionChanged();
         notifyChartUpdated();
      }
   }
   
   public Range getLayerSelection()
   {
      if (content != null)
      {
         if (layerSelection == null)
         {
            return content.getLayerRange();
         }
         else
         {
            return layerSelection;
         }
      }
      else
      {
         throw new NullPointerException("No content provider set.");
      }
   }
   
   public void setLayerSelection(Range range)
   {
      if (range == null)
      {
         throw new NullPointerException("Series selection range can not be null.");
      }
      
      setLayerSelection(range.getOffset(), range.getCount());
   }
      
   public void setLayerSelection(int offset, int count)
   {
      if (content != null)
      {
         if (layerSelection == null)
         {
            layerSelection = new MutableRange(offset, (count < 1) ? 1 : count);
            adjustSelection();
            notifyLayerSelectionChanged();
            notifyChartUpdated();
         }
         else
         {
            int oldOffset = layerSelection.getOffset();
            int oldCount = layerSelection.getCount();
            layerSelection.set(offset, (count < 1) ? 1 : count);
            adjustSelection();
            if (!layerSelection.equals(oldOffset, oldCount))
            {
               notifyLayerSelectionChanged();
               notifyChartUpdated();
            }
         }
      }
   }
   
   public void clearLayerSelection()
   {
      if (layerSelection != null)
      {
         layerSelection = null;
         notifyLayerSelectionChanged();
         notifyChartUpdated();
      }
   }
   
   public void clearSelections()
   {
      boolean updatePending = false;
      if (itemSelection != null)
      {
         itemSelection = null;
         notifyItemSelectionChanged();
         updatePending = true;
      }
      if (seriesSelection != null)
      {
         seriesSelection = null;
         notifySeriesSelectionChanged();
         updatePending = true;
      }
      if (layerSelection != null)
      {
         layerSelection = null;
         notifyLayerSelectionChanged();
         updatePending = true;
      }
      if (updatePending)
      {
         notifyChartUpdated();
      }
   }
   
   public void beginChanges()
   {
      if (notificationState.areChangesInProgress())
      {
         // FIXME: Throw more specific exception
         throw new RuntimeException("Redundant beginChanges() call");
      }
      notificationState.clearPending();
      
      disableNotifications();
      
      notificationState.setChangesInProgress(true);
   }
   
   public void endChanges(boolean suppressPendingNotifications)
   {
      enableNotifications();
      if (!suppressPendingNotifications)
      {
         if (notificationState.isPending(NotificationState.LAYER_SELECTION))
         {
            notifyLayerSelectionChanged();
         }
         if (notificationState.isPending(NotificationState.SERIES_SELECTION))
         {
            notifySeriesSelectionChanged();
         }
         if (notificationState.isPending(NotificationState.ITEM_SELECTION))
         {
            notifyItemSelectionChanged();
         }
         if (notificationState.isPending(NotificationState.UPDATE))
         {
            notifyChartUpdated();
         }
      }
      notificationState.setChangesInProgress(false);
   }
      
   public void contentChanged(ChartContentProvider provider)
   {
      adjustSelection();
      notifyChartUpdated();
      notifyLayerSelectionChanged();
      notifySeriesSelectionChanged();
      notifyItemSelectionChanged();
      
   }
   
   public void addChartUpdateListener(ChartUpdateListener listener)
   {
      updateListeners.add(listener);
   }

   public void removeChartUpdateListener(ChartUpdateListener listener)
   {
      updateListeners.remove(listener);
   }

   public void addChartSelectionListener(ChartSelectionListener listener)
   {
      selectionListeners.add(listener);
   }

   public void removeChartSelectionListener(ChartSelectionListener listener)
   {
      selectionListeners.remove(listener);
   }

   public void addChartWindowListener(ChartWindowListener listener)
   {
      windowListeners.add(listener);
   }

   public void removeChartWindowListener(ChartWindowListener listener)
   {
      windowListeners.remove(listener);
   }
   
   public String getToolTip(int x, int y)
   {
      // Does nothing unless overridden
      return null;
   }

   public synchronized void draw(GraphicsContext g, int width, int height)
   {
      // Let's not issue update notifications while in the middle of updating
      disableUpdateNotifications();
      
      clearBackground(g, width, height);
      
      camera.applyProjectionTransform(g, width, height);
      camera.applyWorldViewTransform(g, width, height);
      
      drawChart(g, width, height);
      
      enableUpdateNotifications();
   }
   
   protected int getSourceSeries(int series)
   {
      if (contentTransformers.isEmpty())
      {
         return series;
      }
      else
      {
         int s = series;
         for (int i = contentTransformers.size() - 1; i >= 0; i--)
         {
            ChartContentTransformer transformer = 
               (ChartContentTransformer) contentTransformers.get(i);
            s = transformer.getSourceSeries(s);
         }
         
         return s;
      }
   }
   
   protected void enableNotifications()
   {
      notificationState.enableCategory(NotificationState.UPDATE_CATEGORY
                                       | NotificationState.SELECTION_CATEGORY
                                       | NotificationState.WINDOW_CATEGORY);
   }

   protected void disableNotifications()
   {
      notificationState.disableCategory(NotificationState.UPDATE_CATEGORY
                                        | NotificationState.SELECTION_CATEGORY
                                        | NotificationState.WINDOW_CATEGORY);
   }

   protected void enableUpdateNotifications()
   {
      notificationState.enableCategory(NotificationState.UPDATE_CATEGORY);
   }
   
   protected void disableUpdateNotifications()
   {
      notificationState.disableCategory(NotificationState.UPDATE_CATEGORY);
   }
   
   protected boolean areUpdateNotificationsEnabled()
   {
      return notificationState.isCategoryEnabled(NotificationState.UPDATE_CATEGORY);
   }
   
   protected void notifyChartUpdated()
   {
      if (areUpdateNotificationsEnabled())
      {
         Object[] listeners = updateListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartUpdateListener)listeners[i]).chartUpdated(this);
         }
      }
      else
      {
         notificationState.setPending(NotificationState.UPDATE);
      }
   }
   
   protected void adjustSelection()
   {
      if (content != null)
      {
         if (itemSelection != null)
         {
            itemSelection.constrain(content.getItemRange());
         }
         if (seriesSelection != null)
         {
            seriesSelection.constrain(content.getSeriesRange());
         }
         if (layerSelection != null)
         {
            layerSelection.constrain(content.getLayerRange());
         }
      }
   }
   
   protected void enableSelectionNotifications()
   {
      notificationState.enableCategory(NotificationState.SELECTION_CATEGORY);
   }

   protected void disableSelectionNotifications()
   {
      notificationState.disableCategory(NotificationState.SELECTION_CATEGORY);
   }
   
   protected boolean areSelectionNotificationsEnabled()
   {
      return notificationState.isCategoryEnabled(NotificationState.SELECTION_CATEGORY);
   }
   
   protected void notifyItemSelectionChanged()
   {
      if (areSelectionNotificationsEnabled())
      {
         Object[] listeners = selectionListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartSelectionListener)listeners[i]).itemSelectionChanged(this);
         }
      }
      else
      {
         notificationState.setPending(NotificationState.ITEM_SELECTION);
      }
   }

   protected void notifySeriesSelectionChanged()
   {
      if (areSelectionNotificationsEnabled())
      {
         Object[] listeners = selectionListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartSelectionListener)listeners[i]).seriesSelectionChanged(this);
         }
      }
      else
      {
         notificationState.setPending(NotificationState.SERIES_SELECTION);
      }
   }

   protected void notifyLayerSelectionChanged()
   {
      if (areSelectionNotificationsEnabled())
      {
         Object[] listeners = selectionListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartSelectionListener)listeners[i]).layerSelectionChanged(this);
         }
      }
      else
      {
         notificationState.isPending(NotificationState.LAYER_SELECTION);
      }
   }

   public void enableWindowNotifications()
   {
      notificationState.enableCategory(NotificationState.WINDOW_CATEGORY);
   }

   public void disableWindowNotifications()
   {
      notificationState.disableCategory(NotificationState.WINDOW_CATEGORY);
   }

   public boolean areWindowNotificationsEnabled()
   {
      return notificationState.isCategoryEnabled(NotificationState.WINDOW_CATEGORY);
   }

   protected void notifyItemWindowChanged()
   {
      if (areWindowNotificationsEnabled())
      {
         Object[] listeners = windowListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartWindowListener)listeners[i]).itemWindowChanged(this);
         }
      }
      else
      {
         notificationState.setPending(NotificationState.ITEM_WINDOW);
      }
   }

   protected void notifySeriesWindowChanged()
   {
      if (areWindowNotificationsEnabled())
      {
         Object[] listeners = windowListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartWindowListener)listeners[i]).seriesWindowChanged(this);
         }
      }
      else
      {
         notificationState.setPending(NotificationState.SERIES_WINDOW);
      }
   }

   protected void notifyLayerWindowChanged()
   {
      if (areWindowNotificationsEnabled())
      {
         Object[] listeners = windowListeners.getListeners();
         
         for (int i = 0; i < listeners.length; i++)
         {
            ((ChartWindowListener)listeners[i]).layerWindowChanged(this);
         }
      }
      else
      {
         notificationState.isPending(NotificationState.LAYER_WINDOW);
      }
   }

   protected abstract void drawChart(GraphicsContext g, int width, int height);
   
   private void clearBackground(GraphicsContext g, int width, int height)
   {
      g.clear(getBackgroundColor());
   }
   
   private void setCamera(Camera camera)
   {
      this.camera = camera;
      notifyChartUpdated();
   }
   
   protected Camera getCamera()
   {
      return camera; 
   }
      
   private class NotificationState
   {
      public static final int UPDATE_CATEGORY = 1;
      public static final int SELECTION_CATEGORY = 2;
      public static final int WINDOW_CATEGORY = 4;

      public static final int UPDATE = 1;
      public static final int ITEM_SELECTION = 2;
      public static final int SERIES_SELECTION = 4;
      public static final int LAYER_SELECTION = 8;
      public static final int ITEM_WINDOW = 16;
      public static final int SERIES_WINDOW = 32;
      public static final int LAYER_WINDOW = 64;

      private int enabledNotifications = 0;
      private int pendingNotifications = 0;
      private boolean changesInProgress = false;
      
      public boolean isCategoryEnabled(int notificationCategory)
      {
         return (enabledNotifications & notificationCategory) != 0;
      }

      public void enableCategory(int notificationCategoryMask)
      {
         enabledNotifications |= notificationCategoryMask;
      }

      public void disableCategory(int notificationCategoryMask)
      {
         enabledNotifications &= ~notificationCategoryMask;
      }

      public boolean isPending(int notificationType)
      {
         return (pendingNotifications & notificationType) != 0;
      }

      public void setPending(int notificationType)
      {
         pendingNotifications |= notificationType;
      }

      public void clearPending()
      {
         pendingNotifications = 0;
      }

      public boolean areChangesInProgress()
      {
         return changesInProgress;
      }

      public void setChangesInProgress(boolean inProgress)
      {
         this.changesInProgress = inProgress;
      }
   }
}
