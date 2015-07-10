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

public abstract class IMessage implements Runnable
{
   long receiveStamp = Long.MAX_VALUE; /* @see ThreadWithQueue#sync */

   private MessageQueue sender;
   private boolean hasBeenSent;

   public void run()
   {
      process();
   }

   public void process()
   {
      throw new Error("No implementation of process for this message.");
   }

   public final void send(MessageQueue receiver)
   {
      hasBeenSent = true;
      receiver.post(this);
   }

   public final void send(MessageQueue receiver, MessageQueue sender)
   {
      this.sender = sender;
      send(receiver);
   }

   public final MessageQueue getSender()
   {
      if (!hasBeenSent)
      {
         throw new Error("The message has to be sent before getSender is called.");
      }
      return sender;
   }

   public final void setSender(MessageQueue sender)
   {
      hasBeenSent = true;
      this.sender = sender;
   }

   public final void returnToSender()
   {
      if (sender != null)
      {
         send(sender);
      }
      else
      {
         throw new Error("No sender is set.");
      }
   }
}
