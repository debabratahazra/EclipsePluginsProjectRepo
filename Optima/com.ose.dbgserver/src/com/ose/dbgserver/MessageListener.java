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

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Interface used by the target interface to process received messages.
 * A component may register itself as a MessageListener to the
 * TargetInterface and thereby bypass any otherhead in the target interface.
 *
 * @see DebugServer#addMessageListener
 */
public interface MessageListener
{
   /**
    * Called by the TargetInterface thread upon receive of a new message. No
    * lock is hold during the call. All synchronization must be done by the
    * receiver. The passed input stream is positioned after the signo. Note that
    * the correct message size (minus 4) must be read from the stream.
    *
    * @param signo  signo of the received message.
    * @param is  the input stream where the message must be read from.
    */
   public void messageReceived(int signo, DataInputStream is) throws IOException;
}
