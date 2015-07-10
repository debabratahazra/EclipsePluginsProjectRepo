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

package com.ose.system.ui.util;

import com.ose.system.SignalInfo;

public class ExtendedSignalInfo
{
   private final SignalInfo signalInfo;
   private final String ownerName;
   private final String senderName;
   private final String addresseeName;

   public ExtendedSignalInfo(SignalInfo signalInfo,
                      String ownerName,
                      String senderName,
                      String addresseeName)
   {
      this.signalInfo = signalInfo;
      this.ownerName = ownerName;
      this.senderName = senderName;
      this.addresseeName = addresseeName;
   }

   public SignalInfo getSignalInfo()
   {
      return signalInfo;
   }

   public String getOwnerName()
   {
      return ownerName;
   }

   public String getSenderName()
   {
      return senderName;
   }

   public String getAddresseeName()
   {
      return addresseeName;
   }

   public boolean isEndmarkBroken()
   {
      return (signalInfo.getStatus() == SignalInfo.STATUS_ENDMARK);
   }
}
