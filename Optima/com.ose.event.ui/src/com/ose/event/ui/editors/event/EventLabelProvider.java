/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.event.ui.editors.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.AllocEvent;
import com.ose.system.BindEvent;
import com.ose.system.CreateEvent;
import com.ose.system.ErrorEvent;
import com.ose.system.FreeEvent;
import com.ose.system.KillEvent;
import com.ose.system.LossEvent;
import com.ose.system.ReceiveEvent;
import com.ose.system.ResetEvent;
import com.ose.system.SendEvent;
import com.ose.system.SwapEvent;
import com.ose.system.TargetEvent;
import com.ose.system.TimeoutEvent;
import com.ose.system.UserEvent;
import com.ose.system.ui.util.StringUtils;

class EventLabelProvider extends LabelProvider implements ITableLabelProvider
{
   private static final DateFormat DATE_FORMAT;

   private final boolean trace;

   static
   {
      DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
   }

   EventLabelProvider(boolean trace)
   {
      this.trace = trace;
   }

   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof TargetEvent))
      {
         return null;
      }

      TargetEvent event = (TargetEvent) obj;
      switch (index)
      {
         case EventEditor.COLUMN_ENTRY:
            return (trace ? toLongString(event.getReference()) : null);
         case EventEditor.COLUMN_TICK:
            return toLongString(event.getTick()) + ":" +
               toLongString(event.getNTick());
         case EventEditor.COLUMN_TIMESTAMP:
            return DATE_FORMAT.format(new Date(event.getMilliSeconds()));
         case EventEditor.COLUMN_FILE_LINE:
            return (((event.getFileName().length() > 0) && (event.getLineNumber() > 0)) ?
               event.getFileName() + ":" + toLongString(event.getLineNumber()) : "");
         default:
            if (event instanceof SendEvent)
            {
               return getSendColumnText((SendEvent) event, index);
            }
            else if (event instanceof ReceiveEvent)
            {
               return getReceiveColumnText((ReceiveEvent) event, index);
            }
            else if (event instanceof SwapEvent)
            {
               return getSwapColumnText((SwapEvent) event, index);
            }
            else if (event instanceof CreateEvent)
            {
               return getCreateColumnText((CreateEvent) event, index);
            }
            else if (event instanceof KillEvent)
            {
               return getKillColumnText((KillEvent) event, index);
            }
            else if (event instanceof ErrorEvent)
            {
               return getErrorColumnText((ErrorEvent) event, index);
            }
            else if (event instanceof ResetEvent)
            {
               return getResetColumnText((ResetEvent) event, index);
            }
            else if (event instanceof UserEvent)
            {
               return getUserColumnText((UserEvent) event, index);
            }
            else if (event instanceof BindEvent)
            {
               return getBindColumnText((BindEvent) event, index);
            }
            else if (event instanceof AllocEvent)
            {
               return getAllocColumnText((AllocEvent) event, index);
            }
            else if (event instanceof FreeEvent)
            {
               return getFreeColumnText((FreeEvent) event, index);
            }
            else if (event instanceof TimeoutEvent)
            {
               return getTimeoutColumnText((TimeoutEvent) event, index);
            }
            else if (event instanceof LossEvent)
            {
               return getLossColumnText((LossEvent) event, index);
            }
            else
            {
               return null;
            }
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      return null;
   }

   private String getSendColumnText(SendEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Send";
         case EventEditor.COLUMN_FROM:
            return event.getSenderProcess().toString();
         case EventEditor.COLUMN_TO:
            return event.getAddresseeProcess().toString();
         case EventEditor.COLUMN_NUMBER:
            return toLongString(event.getSignalNumber());
         case EventEditor.COLUMN_SIZE:
            return toLongString(event.getSignalSize());
         case EventEditor.COLUMN_ADDRESS:
            return StringUtils.toHexString(event.getSignalAddress());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getReceiveColumnText(ReceiveEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Receive";
         case EventEditor.COLUMN_FROM:
            return event.getSenderProcess().toString();
         case EventEditor.COLUMN_TO:
            return event.getAddresseeProcess().toString();
         case EventEditor.COLUMN_NUMBER:
            return toLongString(event.getSignalNumber());
         case EventEditor.COLUMN_SIZE:
            return toLongString(event.getSignalSize());
         case EventEditor.COLUMN_ADDRESS:
            return StringUtils.toHexString(event.getSignalAddress());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getSwapColumnText(SwapEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Swap";
         case EventEditor.COLUMN_FROM:
            return event.getSwappedOutProcess().toString();
         case EventEditor.COLUMN_TO:
            return event.getSwappedInProcess().toString();
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getCreateColumnText(CreateEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Create";
         case EventEditor.COLUMN_FROM:
            TargetEvent.ProcessInfo creator = event.getCreatorProcess();
            return ((creator != null) ? creator.toString() : null);
         case EventEditor.COLUMN_TO:
            return event.getCreatedProcess().toString();
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getKillColumnText(KillEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Kill";
         case EventEditor.COLUMN_FROM:
            TargetEvent.ProcessInfo killer = event.getKillerProcess();
            return ((killer != null) ? killer.toString() : null);
         case EventEditor.COLUMN_TO:
            return event.getKilledProcess().toString();
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getErrorColumnText(ErrorEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Error";
         case EventEditor.COLUMN_FROM:
            return event.getProcess().toString();
         case EventEditor.COLUMN_ERROR_CALLER:
            return (event.isExecutive() ? "Kernel" : "User");
         case EventEditor.COLUMN_ERROR_CODE:
            return StringUtils.toHexString(event.getError());
         case EventEditor.COLUMN_EXTRA_PARAMETER:
            return StringUtils.toHexString(event.getExtra());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getResetColumnText(ResetEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return (event.isWarmReset() ? "Warm Reset" : "Cold Reset");
         default:
            return null;
      }
   }

   private String getUserColumnText(UserEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "User";
         case EventEditor.COLUMN_FROM:
            return event.getProcess().toString();
         case EventEditor.COLUMN_NUMBER:
            return toLongString(event.getEventNumber());
         case EventEditor.COLUMN_SIZE:
            return toLongString(event.getEventDataSize());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getBindColumnText(BindEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Bind";
         case EventEditor.COLUMN_FROM:
            return event.getProcess().toString();
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getFromExecutionUnit());
         case EventEditor.COLUMN_TO_EXEC_UNIT:
            return toIntString(event.getToExecutionUnit());
         default:
            return null;
      }
   }

   private String getAllocColumnText(AllocEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Alloc";
         case EventEditor.COLUMN_FROM:
            return event.getProcess().toString();
         case EventEditor.COLUMN_NUMBER:
            return toLongString(event.getSignalNumber());
         case EventEditor.COLUMN_SIZE:
            return toLongString(event.getSignalSize());
         case EventEditor.COLUMN_ADDRESS:
            return StringUtils.toHexString(event.getSignalAddress());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getFreeColumnText(FreeEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Free";
         case EventEditor.COLUMN_FROM:
            return event.getProcess().toString();
         case EventEditor.COLUMN_NUMBER:
            return toLongString(event.getSignalNumber());
         case EventEditor.COLUMN_SIZE:
            return toLongString(event.getSignalSize());
         case EventEditor.COLUMN_ADDRESS:
            return StringUtils.toHexString(event.getSignalAddress());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getTimeoutColumnText(TimeoutEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            switch (event.getSystemCall())
            {
               case TimeoutEvent.SYSTEM_CALL_RECEIVE:
                  return "Receive Timeout";
               default:
                  return "Unknown Timeout";
            }
         case EventEditor.COLUMN_FROM:
            return event.getProcess().toString();
         case EventEditor.COLUMN_NUMBER:
            return toLongString(event.getTimeout());
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return toIntString(event.getExecutionUnit());
         default:
            return null;
      }
   }

   private String getLossColumnText(LossEvent event, int index)
   {
      switch (index)
      {
         case EventEditor.COLUMN_EVENT:
            return "Loss";
         case EventEditor.COLUMN_SIZE:
            return toLongString(event.getLostSize());
         default:
            return null;
      }
   }

   private static String toIntString(short s)
   {
      return Integer.toString(s & 0xFFFF);
   }

   private static String toLongString(int i)
   {
      return Long.toString(i & 0xFFFFFFFFL);
   }
}
