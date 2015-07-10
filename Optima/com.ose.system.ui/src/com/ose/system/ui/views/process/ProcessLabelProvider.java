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

package com.ose.system.ui.views.process;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.ProcessInfo;
import com.ose.system.ui.util.StringUtils;

public class ProcessLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   // TODO: Add sid, creator, supervisor, supervisorStackSize, timeSlice,
   // vector, numSignalsAttached, and errorHandler when supported.
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof ProcessInfo))
      {
         return null;
      }

      ProcessInfo process = (ProcessInfo) obj;
      switch (index)
      {
         case ProcessView.COLUMN_NAME:
            return process.getName();
         case ProcessView.COLUMN_ID:
            return StringUtils.toHexString(process.getId());
         case ProcessView.COLUMN_BID:
            return StringUtils.toHexString(process.getBid());
         case ProcessView.COLUMN_UID:
            return Integer.toString(process.getUid());
         case ProcessView.COLUMN_TYPE:
            return StringUtils.toProcessTypeString(process.getType());
         case ProcessView.COLUMN_STATE:
            return StringUtils.toProcessStateString(process.getState());
         case ProcessView.COLUMN_PRIORITY:
            return Integer.toString(process.getPriority());
         case ProcessView.COLUMN_NUM_SIGNALS_IN_QUEUE:
            return Integer.toString(process.getNumSignalsInQueue());
         case ProcessView.COLUMN_SIGSELECT_COUNT:
            int[] sigsel = process.getSigselect();
            return ((sigsel.length > 0) ?
                    ((sigsel[0] == 0) ? StringUtils.ALL : Integer.toString(sigsel[0])) :
                    StringUtils.N_A);
         case ProcessView.COLUMN_ENTRYPOINT:
            return StringUtils.toHexString(process.getEntrypoint());
         case ProcessView.COLUMN_SEMAPHORE_VALUE:
            return Integer.toString(process.getSemaphoreValue());
         case ProcessView.COLUMN_NUM_SIGNALS_OWNED:
            return Integer.toString(process.getNumSignalsOwned());
         case ProcessView.COLUMN_FILE_NAME:
            return ((process.getFileName().length() > 0) ?
                    process.getFileName() :
                    StringUtils.N_A);
         case ProcessView.COLUMN_LINE_NUMBER:
            return ((process.getLineNumber() > 0) ?
                    Integer.toString(process.getLineNumber()) :
                    StringUtils.N_A);
         case ProcessView.COLUMN_STACK_SIZE:
            return ((process.getStackSize() > 0) ?
                    Integer.toString(process.getStackSize()) :
                    StringUtils.N_A);
         case ProcessView.COLUMN_EXECUTION_UNIT:
            return Short.toString(process.getExecutionUnit());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      return null;
   }
}
