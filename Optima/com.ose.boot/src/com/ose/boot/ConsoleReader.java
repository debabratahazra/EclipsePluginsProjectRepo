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

package com.ose.boot;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.IOException;

/**
 * This class implements a thread based input buffer with timeouts for reading.
 */
class ConsoleReader extends Thread
{
   private InputStream in;
   private int[] queue;
   private int head = -1;
   private int tail = 0;
   private int size = 0;
   private volatile boolean closed = false;

   /**
    * Create a circular input buffer of specified size and connect it to the
    * specified input stream.
    *
    * @param in       The input stream to connect this input buffer to.
    * @param bufsize  The size of the input buffer in bytes.
    */
   ConsoleReader(InputStream in, int bufsize)
   {
      super("Console Reader");
      setDaemon(true);
      setPriority(Thread.NORM_PRIORITY - 1);

      if (bufsize < 1)
      {
         throw new IllegalArgumentException("Invalid console input buffer size.");
      }

      this.in = in;
      queue = new int[bufsize];
      start();
   }

   /**
    * Read a byte from the input buffer. If no data is available block for
    * maximal tmo milliseconds, however, if tmo is -1 then block indefinitely
    * or if tmo is zero timeout directly.
    *
    * @param   tmo  The timeout in milliseconds to block.
    * @return  The byte read or -1 if end of stream is reached.
    * @exception TimeoutException        If the operation timed out.
    * @exception InterruptedIOException  If the operation was interrupted by
    *                                    another thread.
    * @exception IOException             If some other I/O error occurs.
    */
   synchronized int read(int tmo) throws IOException
   {
      try
      {
         int c;

         if (head > queue.length - 1)
         {
            throw new IllegalStateException(
               "Console I/O queue handling inconsistent: head = " + head);
         }

         if ((tail < 0) || (tail > queue.length - 1))
         {
            throw new IllegalStateException(
               "Console I/O queue handling inconsistent: tail = " + tail);
         }

         if ((size < 0) || (size > queue.length))
         {
            throw new IllegalStateException(
               "Console I/O queue handling inconsistent: size = " + size);
         }

         if (closed)
         {
            return -1;
         }

         if (size == 0)
         {
            if (tmo > 0)
            {
               wait(tmo);
            }
            else if (tmo < 0)
            {
               wait();
            }
            if (size == 0)
            {
               throw new TimeoutException("Console operation timed out.");
            }
         }

         c = queue[tail];
         if (tail < queue.length - 1)
         {
            tail++;
         }
         else
         {
            tail = 0;
         }
         if (size == queue.length)
         {
            notify();
         }
         size--;
         return c;
      }
      catch (InterruptedException e)
      {
         throw new InterruptedIOException("Console operation interrupted.");
      }
   }

   void close()
   {
      closed = true;
      interrupt();
   }

   public void run()
   {
      try
      {
         int c = 0;

         while (c >= 0)
         {
            try
            {
               if (closed)
               {
                  return;
               }
               c = in.read();
            }
            catch (IOException e)
            {
               c = -1; // terminate
               System.err.println("I/O exception occurred in console reader:");
               e.printStackTrace();
            }

            synchronized (this)
            {
               while (size >= queue.length)
               {
                  try
                  {
                     wait();
                  }
                  catch (InterruptedException e)
                  {
                     if (closed)
                     {
                        return;
                     }
                     c = -1; // terminate
                  }
               }

               size++;
               if (head < queue.length - 1)
               {
                  head++;
               }
               else
               {
                  head = 0;
               }
               queue[head] = c;
               notify();
            }
         }
      }
      finally
      {
         try
         {
            in.close();
         }
         catch (IOException e) {}
      }
   }
}
