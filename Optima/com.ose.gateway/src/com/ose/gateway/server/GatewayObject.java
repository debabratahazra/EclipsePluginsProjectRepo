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

import com.ose.gateway.Signal;

/**
 * This interface represents a host gateway object. A host gateway object is
 * either a host gateway service or a host gateway client.
 *
 * @see com.ose.gateway.server.AbstractGatewayService
 * @see com.ose.gateway.server.GatewayClient
 */
public interface GatewayObject
{
   /**
    * Return the ID of this gateway object.
    *
    * @return the ID of this gateway object.
    */
   public int getId();

   /**
    * Return the name of this gateway object.
    *
    * @return the name of this gateway object.
    */
   public String getName();

   /**
    * Called when a signal has been sent to this gateway object.
    *
    * @param signal  the signal that has been received by this gateway object.
    */
   public void signalReceived(Signal signal);
}
