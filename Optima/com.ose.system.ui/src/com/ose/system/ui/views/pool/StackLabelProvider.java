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
import com.ose.system.ui.util.ExtendedStackInfo;
import com.ose.system.ui.util.StringUtils;

public class StackLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof ExtendedStackInfo))
      {
         return null;
      }

      ExtendedStackInfo stack = (ExtendedStackInfo) obj;
      switch (index)
      {
         case PoolView.COLUMN_STACK_OWNER_ID:
            return StringUtils.toHexString(stack.getStackInfo().getPid());
         case PoolView.COLUMN_STACK_OWNER_NAME:
            return stack.getOwnerName();
         case PoolView.COLUMN_STACK_ADDRESS:
            return StringUtils.toHexString(stack.getStackInfo().getAddress());
         case PoolView.COLUMN_STACK_SIZE:
            return Integer.toString(stack.getStackInfo().getSize());
         case PoolView.COLUMN_STACK_BUFFER_SIZE:
            return Integer.toString(stack.getStackInfo().getBufferSize());
         case PoolView.COLUMN_STACK_USED:
            return Integer.toString(stack.getStackInfo().getUsed());
         case PoolView.COLUMN_STACK_RELATIVE_USED:
            return StringUtils.toPercentString(stack.getStackInfo().getUsed(),
                                               stack.getStackInfo().getSize());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      return null;
   }
}
