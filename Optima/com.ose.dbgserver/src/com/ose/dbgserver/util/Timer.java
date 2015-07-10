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

package com.ose.dbgserver.util;

public class Timer extends Thread
{
   private long delayTime;
   private TimerInterface timerInterfaceObj;
   private boolean isCanceled;
   private boolean isReseted;

   public Timer(TimerInterface timerInterface, long millis)
   {
      delayTime = millis;
      timerInterfaceObj = timerInterface;
      isCanceled = false;
      start();
   }

   public void cancel()
   {
      isCanceled = true;
      interrupt();
   }

   public final void reset()
   {
      isReseted = true;
      interrupt();
   }

   public void run()
   {
      isReseted = true;
      while (isReseted)
      {
         try
         {
            sleep(delayTime);
            isReseted = false;
         } catch (InterruptedException ignore) {}
      }
      if (!isCanceled)
      {
         timerInterfaceObj.timerExpired(this);
      }
   }
}
