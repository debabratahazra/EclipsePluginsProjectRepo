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

package com.ose.system.ui.views.pool;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.ui.util.ExtendedSignalInfo;
import com.ose.system.ui.util.StringUtils;

public class SignalLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof ExtendedSignalInfo))
      {
         return null;
      }

      ExtendedSignalInfo sig = (ExtendedSignalInfo) obj;
      switch (index)
      {
         case PoolView.COLUMN_SIGNAL_BROKEN_BUFFER:
            return "";
         case PoolView.COLUMN_SIGNAL_NUMBER:
            return StringUtils.toU32String(sig.getSignalInfo().getSigNo());
         case PoolView.COLUMN_SIGNAL_OWNER_ID:
            return StringUtils.toHexString(sig.getSignalInfo().getOwner());
         case PoolView.COLUMN_SIGNAL_OWNER_NAME:
            return sig.getOwnerName();
         case PoolView.COLUMN_SIGNAL_SENDER_ID:
            return StringUtils.toHexString(sig.getSignalInfo().getSender());
         case PoolView.COLUMN_SIGNAL_SENDER_NAME:
            return sig.getSenderName();
         case PoolView.COLUMN_SIGNAL_ADDRESSEE_ID:
            return StringUtils.toHexString(sig.getSignalInfo().getAddressee());
         case PoolView.COLUMN_SIGNAL_ADDRESSEE_NAME:
            return sig.getAddresseeName();
         case PoolView.COLUMN_SIGNAL_SIZE:
            return Integer.toString(sig.getSignalInfo().getSize());
         case PoolView.COLUMN_SIGNAL_BUFFER_SIZE:
            return Integer.toString(sig.getSignalInfo().getBufferSize());
         case PoolView.COLUMN_SIGNAL_ADDRESS:
            return StringUtils.toHexString(sig.getSignalInfo().getAddress());
         case PoolView.COLUMN_SIGNAL_STATUS:
            return StringUtils.toSignalStatusString(sig.getSignalInfo().getStatus());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if (!(obj instanceof ExtendedSignalInfo))
      {
         return null;
      }

      ExtendedSignalInfo sig = (ExtendedSignalInfo) obj;
      if ((index == PoolView.COLUMN_SIGNAL_BROKEN_BUFFER) &&
          sig.isEndmarkBroken())
      {
         return PlatformUI.getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_OBJS_WARN_TSK);
      }
      else
      {
         return null;
      }
   }
}
