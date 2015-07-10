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

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This thread class implements a message based IPC.
 * A subclass may overide the exec() method as its entry point.
 *
 * The default implementation of run() calls exec() and catches all exceptions.
 * The run() method also handles the kill exception and that is why run is final.
 *
 * The default implementation of exec() calls receive in an infinite loop and
 * calls the process() method of any received message.
 *
 * @see #exec
 * @see IMessage
 */
public class ThreadWithQueue extends Thread implements MessageQueue
{
   private boolean killed;
   /* The signal queue. */
   private LinkedList messageQueue = new LinkedList();
   /* Sigselect array of class names. */
   private String[] selectClasses;
   /* List of signals that are attached. */
   private LinkedList attachList;
   /* The receiveStamp is used by the sync() method. */
   private long receiveStamp;

   public ThreadWithQueue()
   {
      super();
   }

   public ThreadWithQueue(String name)
   {
      super(name);
   }

   private static ThreadWithQueue currentProcess()
   {
      return (ThreadWithQueue) Thread.currentThread();
   }

   /**
    * Pop the message queue. Blocks until we have a matching message in our in
    * queue.
    *
    * @param classes  An "sig select array", only receive the specified messages.
    * @return the first matching message.
    * @see IMessage
    */
   public final synchronized IMessage receive(String[] classes)
      throws InterruptedException
   {
      selectClasses = classes;
      return _receive(false);
   }

   public final synchronized IMessage receive(String[] classes, boolean nonBlocking)
      throws InterruptedException
   {
      selectClasses = classes;
      return _receive(nonBlocking);
   }

   public final synchronized IMessage receive() throws InterruptedException
   {
      selectClasses = null;
      return _receive(false);
   }

   public final synchronized IMessage receive(boolean nonBlocking)
      throws InterruptedException
   {
      selectClasses = null;
      return _receive(nonBlocking);
   }

   private synchronized IMessage _receive(boolean nonBlocking)
      throws InterruptedException
   {
      IMessage m = null;

      if (currentThread() != this)
      {
         throw new Error("You may only do receive from your own thread.");
      }

      try
      {
         if (selectClasses == null)
         {
            if (nonBlocking)
            {
               if (messageQueue.size() > 0)
               {
                  m = (IMessage) messageQueue.removeFirst();
                  return m;
               }
               else
               {
                  return null;
               }
            }
            else
            {
               // Wait until we have at least one message.
               while (messageQueue.size() == 0)
               {
                  wait();
               }

               // Return the first message in the queue.
               m = (IMessage) messageQueue.removeFirst();
               return m;
            }
         }
         else
         {
            while (true)
            {
               ListIterator li = messageQueue.listIterator(0);
               while (li.hasNext())
               {
                  m = (IMessage) li.next();
                  if (inSigSelect(m))
                  {
                     li.remove();
                     selectClasses = null;
                     return m;
                  }
                  else
                  {
                     m = null;
                  }
               }
               if (nonBlocking)
               {
                  return null;
               }
               else
               {
                  wait();
               }
            }
         }
      }
      finally
      {
         if (m != null)
         {
            // This code supports the sync() method.
            receiveStamp++;
            m.receiveStamp = receiveStamp;
            notifyAll();
         }
      }
   }

   public final synchronized void remove(IMessage message)
   {
      messageQueue.remove(message);
   }

   public int getQueueLength()
   {
      if (this == currentProcess())
      {
         synchronized (this)
         {
            return messageQueue.size();
         }
      }
      else
      {
         throw new Error("The queue length is only availeble for the " +
               "context of this thread.");
      }
   }

   /**
    * Block the current thread until this thread has received the message (but
    * not processed the message!) or has been killed. The purpose of this method
    * is to implement flow control between a producer and a consumer.
    *
    * @param msg  the message to be recieved.
    */
   public synchronized void sync(IMessage msg) throws InterruptedException
   {
      if (this == currentThread())
      {
         throw new Error("A threadWithQueue may not call its own sync() method.");
      }
      while ((msg.receiveStamp > receiveStamp) && !killed)
      {
         wait();
      }
   }

   private boolean inSigSelect(IMessage msg)
   {
      if (selectClasses == null)
      {
         return true;
      }
      else
      {
         String name = msg.getClass().getName();
         for (int i = 0; i < selectClasses.length; i++)
         {
            if (name.equals(selectClasses[i]))
            {
               return true;
            }
         }
      }
      return false;
   }

   /** The post method should only be called from the send method in IMessage. */
   public final synchronized void post(IMessage message)
   {
      if (!killed)
      {
         message.receiveStamp = Long.MAX_VALUE;
         messageQueue.addLast(message);
         if (inSigSelect(message))
         {
            notifyAll();
         }
      }
   }

   /**
    * Run handles uncaught exceptions and the kill exception.
    * Use exec() as entrypoint instead of run().
    *
    * @see #exec
    */
   public final void run()
   {
      try
      {
         exec();
      }
      catch (Throwable e)
      {
         if (e instanceof ThreadDeath)
         {
            // Ignore
         }
         else if (!(e instanceof KillException))
         {
            setPriority(MIN_PRIORITY);
            try
            {
               while (true)
               {
                  sleep(Integer.MAX_VALUE);
               }
            }
            catch (InterruptedException ie)
            {
               while (true);
            }
         }
      }
      finally
      {
         killed = true;
         triggAttach();
      }
   }

   /**
    * Entrypoint to this thread. Receive any message and call its process()
    * method in a infinite loop.
    */
   public void exec()
   {
      try
      {
         for (;;)
         {
            IMessage message = receive();
            message.process();
         }
      }
      catch (InterruptedException e)
      {
         // Someone has interrupted us so we will silently exit (is this correct?).
      }
   }

   public final synchronized void attach(IMessage attachSignal)
   {
      attachSignal.setSender((ThreadWithQueue) currentThread());
      if (!killed)
      {
         if (attachList == null)
         {
            attachList = new LinkedList();
         }
         attachList.addLast(attachSignal);
      }
      else
      {
         // The thread is already killed.
         attachSignal.returnToSender();
      }
   }

   public final synchronized void attach(IMessage attachSignal, MessageQueue attacher)
   {
      attachSignal.setSender(attacher);
      if (!killed)
      {
         if (attachList == null)
         {
            attachList = new LinkedList();
         }
         attachList.addLast(attachSignal);
      }
      else
      {
         // The thread is already killed.
         attachSignal.returnToSender();
      }
   }

   public final synchronized void detach(IMessage attachSignal)
   {
      boolean wasKilled;
      synchronized (this)
      {
         wasKilled = killed;
         if (!wasKilled)
         {
            if (attachList == null)
            {
               throw new Error("This message is not attached to this thread.");
            }
            try
            {
               attachList.remove(attachSignal);
            }
            catch (NoSuchElementException e)
            {
               throw new Error("This message is not attached to this thread.");
            }
         }
      }
      if (wasKilled)
      {
         try
         {
            attachSignal.getSender();
            messageQueue.remove(attachSignal);
         } catch (NoSuchElementException ignore) {}
      }
   }

   /**
    * Kill the current thread. This method should of course only be called from
    * a ThreadWithQueue.
    */
   public static void kill()
   {
      throw new KillException();
   }

   private synchronized void triggAttach()
   {
      if (attachList != null)
      {
         ListIterator li = attachList.listIterator(0);
         while (li.hasNext())
         {
            IMessage attachSignal = (IMessage) li.next();
            attachSignal.returnToSender();
         }
         attachList = null;
      }
      // Make sure that anyone blocked on us have a chance to notice our death.
      notifyAll();
   }

   /* This exception is thrown from the kill method to signal that the thread
    * should kill it self.
    */
   private static class KillException extends Error
   {
      private static final long serialVersionUID = 1L;

      KillException()
      {
         super("The thread was killed.");
      }
   }
}
