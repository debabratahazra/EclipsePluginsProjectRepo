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

import java.util.concurrent.Semaphore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import com.ose.chart.ui.Chart;
import com.ose.chart.ui.Refresher;

/**
 * UI canvas widget for displaying charts.
 */
public class ChartCanvas extends GLCanvas
{
   private static final int MINIMUM_WIDTH = 300;
   private static final int MINIMUM_HEIGHT = 200;
   
   private static final int DEFAULT_REFRESH_INTERVAL = 10;
   private int refreshInterval;
   private boolean refreshEnabled;
   
   private GraphicsContextGL context;
   private ChartUpdateHandler chartUpdateHandler;
   
   private Refresher refresher;
   private RunnableRefresh runnableRefresh;
   private Refreshable refreshHandler;
   private Semaphore refreshSemaphore;

   private MouseHandler mouseHandler = null;
   private KeyHandler keyHandler = null;

   private Chart chart = null;
   
   public ChartCanvas(Composite parent, int style)
   {
      super(parent, style | SWT.NO_BACKGROUND,
            createGLData());
      
      // Set up rendering
      setCurrent();
      context = new GraphicsContextGL();
      resize();

      // Set up refreshing
      refreshInterval = DEFAULT_REFRESH_INTERVAL;
      refreshEnabled = false;
      runnableRefresh = new RunnableRefresh();
      refreshHandler = new RefreshHandler();
      refresher = new Refresher(getDisplay(), refreshHandler);
      refreshSemaphore = new Semaphore(2, false);
      
      // Set up listeners
      chartUpdateHandler = new ChartUpdateHandler();
      addPaintListener(new PaintHandler());
      addControlListener(new ControlHandler());
   }
      
   public boolean hasChart()
   {
      return getChart() != null;
   }
   
   public Chart getChart()
   {
      return chart;
   }
   
   public void setChart(Chart chart)
   {
      if (hasChart())
      {
         // If already attached to a chart, detach by removing listener
         chart.removeChartUpdateListener(chartUpdateHandler);
         removeMouseHandler();
         removeKeyHandler();
      }
      
      this.chart = chart;
      
      // Listen for updates from new chart
      if (hasChart())
      {
         chart.addChartUpdateListener(chartUpdateHandler);
         setKeyHandler(InputHandlerFactory.createKeyHandler(this, chart));
         setMouseHandler(InputHandlerFactory.createMouseHandler(this, chart));
      }
      
      // Initialize the render state with the new chart
      initializeRenderState();
   }
      
   public void refresh()
   {
      if (!isDisposed())
      {
         drawFrame();
      }
   }
   
   protected void startRefreshing()
   {
      if (!refreshEnabled)
      {
         refreshEnabled = true;
         refresher.run();
      }
   }

   protected void stopRefreshing()
   {
      refreshEnabled = false;
   }

   protected String getGraphicsSubsystemInfo()
   {
      return "Vendor: " + context.getInfo("vendor") + "\n"
             + "Renderer: " + context.getInfo("renderer") + "\n"
             + "Version: " + context.getInfo("version") + "\n";
   }

   private static GLData createGLData()
   {
      GLData data = new GLData();
      data.doubleBuffer = true;
      data.depthSize = 16;
      return data;
   }
   
   private void setKeyHandler(KeyHandler handler)
   {
      if (keyHandler != null)
      {
         removeKeyHandler();
      }
      keyHandler = handler;
      addKeyListener(keyHandler);
   }

   private void removeKeyHandler()
   {
      if (keyHandler != null)
      {
         removeKeyListener(keyHandler);
         keyHandler = null;
      }
   }
   
   private void setMouseHandler(MouseHandler handler)
   {
      if (mouseHandler != null)
      {
         removeMouseHandler();
      }
      mouseHandler = handler;
      addMouseListener(mouseHandler);
      addMouseMoveListener(mouseHandler);
      addListener(SWT.MouseWheel, mouseHandler);
      addMouseTrackListener(mouseHandler);
   }
   
   private void removeMouseHandler()
   {
      if (mouseHandler != null)
      {
         removeMouseListener(mouseHandler);
         removeMouseMoveListener(mouseHandler);
         removeListener(SWT.MouseWheel, (Listener)mouseHandler);
         removeMouseTrackListener(mouseHandler);
         mouseHandler = null;
      }
   }
   
   private void constrainSize(Rectangle rect)
   {
      rect.width = Math.max(rect.width, MINIMUM_WIDTH);
      rect.height = Math.max(rect.height, MINIMUM_HEIGHT);      
   }
   
   private void resize()
   {
      Rectangle rect = getClientArea();
      constrainSize(rect);

      setCurrent();
      context.begin();
      context.resizeViewport(rect);
      context.end();
      
      refresh();
   }
      
   private void initializeRenderState()
   {
      setCurrent();
      context.begin();
      // Any initial render state changes here
      context.end();
      
      refresh();
   }
   
   private synchronized void drawFrame()
   {
      setCurrent();
      context.begin();
      
      // Reset transform
      context.loadIdentityTransform();

      if (hasChart())
      {
         // Draw the chart on the canvas
         Rectangle rect = context.getViewportSize();
         chart.draw(context, rect.width, rect.height);
         swapBuffers();
      }
      else
      {
         // Clear the canvas if no chart has been set yet
         context.clear(1.0f, 1.0f, 1.0f, 1.0f);
         swapBuffers();
      }

      context.end();
   }
   
   private void requestRefresh()
   {
      if (!isDisposed())
      {
         Display display = getDisplay();
         
         if (display != null)
         {
            // There should only be at most 2 scheduled refresh jobs
            // in the job queue at once. Any more than that would be
            // redundant and would likely cause "job flooding".
            if (refreshSemaphore.tryAcquire())
            {
               display.asyncExec(runnableRefresh);
            }
         }
      }
   }
   
   private class RefreshHandler implements Refreshable
   {
      public int getRefreshInterval()
      {
         return refreshInterval;
      }

      public boolean keepRefreshing()
      {
         return refreshEnabled;
      }

      public void refresh()
      {
         ChartCanvas.this.refresh();
      }
      
   }
   
   private class RunnableRefresh implements Runnable
   {
      public void run()
      {
         try
         {
            refresh();
         }
         finally
         {
            refreshSemaphore.release();
         }
      }
   }
      
   private class ChartUpdateHandler implements ChartUpdateListener
   {
      public void chartUpdated(Chart chart)
      {
         requestRefresh();
      }
   }
      
   private class PaintHandler implements PaintListener
   {
      public void paintControl(PaintEvent e)
      {
         refresh();
      }
   }

   private class ControlHandler implements ControlListener
   {
      public void controlMoved(ControlEvent e)
      {
         refresh();
      }

      public void controlResized(ControlEvent e)
      {
         resize();
      }
   }
}
