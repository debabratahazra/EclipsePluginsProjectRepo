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

package com.ose.prof.ui.editors.profiler;

public interface SynchronizedScrollPart
{
   public void addSynchronizedScrollPartListener(
                                    SynchronizedScrollPartListener listener);

   public void removeSynchronizedScrollPartListener(
                                    SynchronizedScrollPartListener listener);

   // Returns 0 if there are no entries.
   public long getFirstLocalTime();

   // Returns 0 if there are no entries.
   public long getLastLocalTime();

   public void setScrollWindowRange(long time1, long time2);

   public void scrollTo(long time);
}
