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

public interface MessageQueue
{
   public IMessage receive(String[] classes) throws InterruptedException;

   public IMessage receive(String[] classes, boolean nonBlocking)
      throws InterruptedException;

   public IMessage receive() throws InterruptedException;

   public IMessage receive(boolean nonBlocking) throws InterruptedException;

   /** Remove the message from the queue. */
   public void remove(IMessage message);

   /** The post method should only be called from the send method in IMessage. */
   public void post(IMessage message);
}
