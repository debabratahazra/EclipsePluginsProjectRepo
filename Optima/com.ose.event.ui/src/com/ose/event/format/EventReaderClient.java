/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.event.format;

import com.ose.system.TargetEvent;

/**
 * This interface should be implemented by clients that want to read event
 * XML/dump files.
 */
public interface EventReaderClient
{
   /**
    * This method is called once initially when the target attributes are read.
    *
    * @param target  the name of the target.
    * @param bigEndian  true if the target has big endian byte order, false if
    * it has little endian byte order.
    * @param osType  the OS type of the target, i.e. one of the Target.OS_*
    * constants.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param tickLength  the tick length of the target in microseconds.
    * @param microTickFrequency  the ntick timer frequency of the target in Hz.
    * @param scope  the event scope string.
    * @param eventActions  the name of the event action settings file.
    */
   public void commonAttributesRead(String target,
                                    boolean bigEndian,
                                    int osType,
                                    int numExecutionUnits,
                                    int tickLength,
                                    int microTickFrequency,
                                    String scope,
                                    String eventActions);

   /**
    * This method is called each time an event is read.
    *
    * @param event  the event that was read.
    */
   public void eventRead(TargetEvent event);
}
