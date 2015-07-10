/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.gateway.server;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.ose.gateway.Signal;

/**
 * This class represents a signal queue.
 */
public final class SignalQueue
{
   /** Signal selection mask representing an any signal type selection. */
   public static final int[] ANY_SIGNAL = {0};

   private final List list = new ArrayList();

   /**
    * Post the given signal to this signal queue.
    *
    * @param signal  the signal to post.
    */
   public void post(Signal signal)
   {
      if (signal == null)
      {
         throw new NullPointerException();
      }

      synchronized (list)
      {
         list.add(signal);
         list.notifyAll();
      }
   }

   /**
    * Receive the first signal from this signal queue.
    * <p>
    * The call returns immediately if a signal can be found in the signal queue.
    * Otherwise it waits until a signal arrives in the signal queue. There is no
    * timeout; the call will wait indefinitely if no signal arrives.
    *
    * @return the first signal from this signal queue.
    * @throws InterruptedIOException  if the calling thread was interrupted
    * while waiting.
    */
   public Signal receive() throws InterruptedIOException
   {
      return receive(ANY_SIGNAL);
   }

   /**
    * Receive the first wanted signal from this signal queue.
    * <p>
    * The call returns immediately if a wanted signal can be found in the signal
    * queue. Otherwise it waits until a wanted signal arrives in the signal
    * queue. Unwanted signals are always left in the signal queue. There is no
    * timeout; the call will wait indefinitely if no wanted signal arrives.
    *
    * @param sigSelect  a signal selection mask defining which signals are
    * wanted.
    * @return the first matching signal from this signal queue.
    * @throws InterruptedIOException  if the calling thread was interrupted
    * while waiting.
    */
   public Signal receive(int[] sigSelect) throws InterruptedIOException
   {
      Signal signal = null;

      if (sigSelect == null)
      {
         throw new NullPointerException();
      }
      else if (sigSelect.length == 0)
      {
         throw new IllegalArgumentException();
      }
      else if ((sigSelect[0] != 0) &&
               (Math.abs(sigSelect[0]) != (sigSelect.length - 1)))
      {
         throw new IllegalArgumentException();
      }

      synchronized (list)
      {
         signal = findSignal(sigSelect);

         while (signal == null)
         {
            try
            {
               list.wait();
            }
            catch (InterruptedException e)
            {
               throw new InterruptedIOException();
            }

            signal = findSignal(sigSelect);
         }
      }

      return signal;
   }

   /**
    * Receive the first signal from this signal queue.
    * <p>
    * The call returns immediately if a signal can be found in the signal queue.
    * Otherwise it waits until a signal arrives in the signal queue or the
    * timeout expires.
    *
    * @param timeout  the number of milliseconds to wait for a signal.
    * The receive will be aborted if no signal arrives in the signal queue
    * before the timeout expires. A timeout value of 0 can be used to check if
    * a signal is available now.
    * @return the first signal from this signal queue, or null if no signal was
    * received before timeout.
    * @throws InterruptedIOException  if the calling thread was interrupted
    * while waiting.
    */
   public Signal receive(long timeout) throws InterruptedIOException
   {
      return receive(ANY_SIGNAL, timeout);
   }

   /**
    * Receive the first wanted signal from this signal queue.
    * <p>
    * The call returns immediately if a wanted signal can be found in the signal
    * queue. Otherwise it waits until a wanted signal arrives in the signal
    * queue or the timeout expires. Unwanted signals are always left in the
    * signal queue.
    *
    * @param sigSelect  a signal selection mask defining which signals are
    * wanted.
    * @param timeout  the number of milliseconds to wait for a wanted signal.
    * The receive will be aborted if no wanted signal arrives in the signal
    * queue before the timeout expires. A timeout value of 0 can be used to
    * check if a wanted signal is available now.
    * @return the first matching signal from this signal queue, or null if no
    * matching signal was received before timeout.
    * @throws InterruptedIOException  if the calling thread was interrupted
    * while waiting.
    */
   public Signal receive(int[] sigSelect, long timeout)
      throws InterruptedIOException
   {
      Signal signal = null;

      if (sigSelect == null)
      {
         throw new NullPointerException();
      }
      else if (sigSelect.length == 0)
      {
         throw new IllegalArgumentException();
      }
      else if ((sigSelect[0] != 0) &&
               (Math.abs(sigSelect[0]) != (sigSelect.length - 1)))
      {
         throw new IllegalArgumentException();
      }

      if (timeout < 0)
      {
         throw new IllegalArgumentException();
      }
      else if (timeout == 0)
      {
         timeout = 1;
      }

      synchronized (list)
      {
         signal = findSignal(sigSelect);

         while (signal == null)
         {
            long elapsed;

            try
            {
               long start = System.currentTimeMillis();
               list.wait(timeout);
               elapsed = System.currentTimeMillis() - start;
            }
            catch (InterruptedException e)
            {
               throw new InterruptedIOException();
            }

            signal = findSignal(sigSelect);
            if (elapsed < timeout)
            {
               timeout -= elapsed;
            }
            else
            {
               break;
            }
         }
      }

      return signal;
   }

   /**
    * Clear this signal queue, i.e. remove all its signals.
    */
   public void clear()
   {
      synchronized (list)
      {
         list.clear();
      }
   }

   /**
    * Return the first matching signal from this signal queue or null if no
    * matching signal was found.
    * <p>
    * @param sigSelect  a signal selection mask defining which signals are
    * wanted.
    * @return the first matching signal from this signal queue or null if no
    * matching signal was found.
    */
   private Signal findSignal(int[] sigSelect)
   {
      for (Iterator i = list.iterator(); i.hasNext();)
      {
         Signal signal = (Signal) i.next();
         int sigNo = signal.getSigNo();

         if (sigSelect[0] < 0)
         {
            boolean excluded = false;
            for (int j = 1; j < sigSelect.length; j++)
            {
               if (sigNo == sigSelect[j])
               {
                  excluded = true;
                  break;
               }
            }
            if (!excluded)
            {
               i.remove();
               return signal;
            }
         }
         else if (sigSelect[0] > 0)
         {
            for (int j = 1; j < sigSelect.length; j++)
            {
               if (sigNo == sigSelect[j])
               {
                  i.remove();
                  return signal;
               }
            }
         }
         else
         {
            i.remove();
            return signal;
         }
      }

      return null;
   }
}
