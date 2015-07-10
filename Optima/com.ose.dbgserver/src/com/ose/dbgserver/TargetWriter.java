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

package com.ose.dbgserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import com.ose.dbgserver.protocol.DBGIOPCSessionInit;
import com.ose.dbgserver.protocol.DBGIOPCSessionTerminate;
import com.ose.dbgserver.protocol.DBGIOPCSignal;
import com.ose.dbgserver.protocol.Message;
import com.ose.dbgserver.util.ThreadWithQueue;

class TargetWriter extends ThreadWithQueue
{
   private final DataOutputStream outputStream;
   private final TargetInterface ti;
   private boolean runExec = true;
   private LinkedList queue = new LinkedList();
   private boolean block = false;
   private LinkedList blockedMessages = new LinkedList();

   TargetWriter(DataOutputStream _os, TargetInterface _ti)
   {
      super("TargetWriter");
      if (_os == null)
      {
         throw new NullPointerException(
               "The target's out stream may not be null");
      }
      if (_ti == null)
      {
         throw new NullPointerException("The target interface may not be null");
      }

      outputStream = _os;
      ti = _ti;

      start();
   }

   public synchronized void write(Message m)
   {
      /*
       * Special handling for IOPC transactions. All writes to the dbgserver not
       * related to the IOPC transaction will be queued until the IOPC
       * transaction is finished. Note that only one IOPC transaction can be in
       * progress.
       */

      if (m instanceof DBGIOPCSessionInit)
      {
         if (block)
         {
            throw new Error("Only one IOPC transaction can be in progress");
         }
         block = true;
         doWrite(m);
      }
      else if (m instanceof DBGIOPCSessionTerminate)
      {
         if (!block)
         {
            throw new Error("No IOPC transaction in progress.");
         }
         doWrite(m);
      }
      else if (m instanceof DBGIOPCSignal)
      {
         if (!block)
         {
            throw new Error("No IOPC transaction in progress.");
         }
         doWrite(m);
      }
      else
      {
         if (block)
         {
            blockedMessages.addLast(m);
         }
         else
         {
            doWrite(m);
         }
      }
   }

   private synchronized void doWrite(Message m)
   {
      queue.addLast(m);
      notify();
   }

   /** Signal that the IOPC transaction is finished. */
   public synchronized void IOPCdone()
   {
      if (!block)
      {
         throw new Error("No IOPC transaction in progress.");
      }
      block = false;
      queue.addAll(blockedMessages);
      blockedMessages.clear();
      notify();
   }

   public void exec()
   {
      while (runExec)
      {
         boolean doflush;
         Message m;
         setPriority(MAX_PRIORITY);

         synchronized (this)
         {
            try
            {
               while (queue.size() == 0)
               {
                  wait();
               }
               m = (Message) queue.remove(0);
               doflush = (queue.size() == 0);
               // Just flush if there is no other messages going out.
            }
            catch (InterruptedException e)
            {
               m = null;
               doflush = false;
            }
         }

         if (m != null)
         {
            try
            {
               m.sendMessage(outputStream);
               if (doflush)
               {
                  outputStream.flush();
               }
            }
            catch (IOException e)
            {
               ti.disconnect(e);
               return;
            }
         }
      }
   }

   /** Causes this thread to gracefully return from exec(). */
   public void stopThread()
   {
      runExec = false;
      interrupt();
   }
}
