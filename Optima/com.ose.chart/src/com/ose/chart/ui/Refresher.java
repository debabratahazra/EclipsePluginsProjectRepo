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

import org.eclipse.swt.widgets.Display;

/**
 * The Refresher object calls the {@link Refreshable#refresh()} method
 * of a refreshable object at regular intervals by setting up timers
 * on the Eclipse UI thread.
 */
class Refresher implements Runnable
{
   private Display display;
   private Refreshable refreshable;
   
   public Refresher(Display display, Refreshable refreshable)
   {
      this.display = display;
      this.refreshable = refreshable;
   }

   public void run()
   {
      refreshable.refresh();
      if (refreshable.keepRefreshing())
      {
         display.timerExec(refreshable.getRefreshInterval(), this);
      }
   }
}
