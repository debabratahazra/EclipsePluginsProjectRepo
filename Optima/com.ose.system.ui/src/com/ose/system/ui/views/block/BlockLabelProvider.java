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

package com.ose.system.ui.views.block;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.BlockInfo;
import com.ose.system.ui.util.StringUtils;

public class BlockLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   // TODO: Add supervisor, numSignalsAttached, and errorHandler when supported.
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof BlockInfo))
      {
         return null;
      }

      BlockInfo block = (BlockInfo) obj;
      switch (index)
      {
         case BlockView.COLUMN_NAME:
            return block.getName();
         case BlockView.COLUMN_BID:
            return StringUtils.toHexString(block.getId());
         case BlockView.COLUMN_SID:
            return StringUtils.toHexString(block.getSid());
         case BlockView.COLUMN_UID:
            return Integer.toString(block.getUid());
         case BlockView.COLUMN_MAX_SIGNAL_SIZE:
            return Integer.toString(block.getMaxSignalSize());
         case BlockView.COLUMN_SIGNAL_POOL_ID:
            return StringUtils.toHexString(block.getSignalPoolId());
         case BlockView.COLUMN_STACK_POOL_ID:
            return StringUtils.toHexString(block.getStackPoolId());
         case BlockView.COLUMN_EXECUTION_UNIT:
            return Short.toString(block.getExecutionUnit());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      return null;
   }
}
