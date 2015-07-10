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

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
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

class EventSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private final boolean trace;
   private int column;
   private int direction;

   EventSorter(boolean trace)
   {
      super();
      this.trace = trace;
      reset();
   }

   public void reset()
   {
      column = EventEditor.COLUMN_ENTRY;
      direction = ASCENDING;
   }

   public void sortByColumn(int column)
   {
      if (this.column == column)
      {
         // Same column, toggle sort direction.
         direction *= DESCENDING;
      }
      else
      {
         // New column, reset sort direction.
         this.column = column;
         direction = ASCENDING;
      }
   }

   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof TargetEvent) || !(e2 instanceof TargetEvent))
      {
         return 0;
      }

      TargetEvent event1 = (TargetEvent) e1;
      TargetEvent event2 = (TargetEvent) e2;

      switch (column)
      {
         case EventEditor.COLUMN_ENTRY:
            return (trace ? compareUnsignedInts(event1.getReference(),
                  event2.getReference()) : 0);
         case EventEditor.COLUMN_TICK:
            return compareUnsignedIntsPair(event1.getTick(), event1.getNTick(),
                                           event2.getTick(), event2.getNTick());
         case EventEditor.COLUMN_TIMESTAMP:
            return compareUnsignedIntsPair(
                  event1.getSeconds(), event1.getSecondsTick(),
                  event2.getSeconds(), event2.getSecondsTick());
         case EventEditor.COLUMN_EVENT:
            return compareStrings(getEvent(event1), getEvent(event2));
         case EventEditor.COLUMN_FROM:
            return compareStrings(getFrom(event1), getFrom(event2));
         case EventEditor.COLUMN_TO:
            return compareStrings(getTo(event1), getTo(event2));
         case EventEditor.COLUMN_FILE_LINE:
            return compareStringUnsignedIntPair(
                  event1.getFileName(), event1.getLineNumber(),
                  event2.getFileName(), event2.getLineNumber());
         case EventEditor.COLUMN_NUMBER:
            return compareUnsignedInts(getNumber(event1), getNumber(event2));
         case EventEditor.COLUMN_SIZE:
            return compareUnsignedInts(getSize(event1), getSize(event2));
         case EventEditor.COLUMN_ADDRESS:
            return compareUnsignedInts(getAddress(event1), getAddress(event2));
         case EventEditor.COLUMN_ERROR_CALLER:
            return compareStrings(getErrorCaller(event1),
                                  getErrorCaller(event2));
         case EventEditor.COLUMN_ERROR_CODE:
            return compareUnsignedInts(getErrorCode(event1),
                                       getErrorCode(event2));
         case EventEditor.COLUMN_EXTRA_PARAMETER:
            return compareUnsignedInts(getExtraParameter(event1),
                                       getExtraParameter(event2));
         case EventEditor.COLUMN_FROM_EXEC_UNIT:
            return compareUnsignedShorts(getFromExecUnit(event1),
                                         getFromExecUnit(event2));
         case EventEditor.COLUMN_TO_EXEC_UNIT:
            return compareUnsignedShorts(getToExecUnit(event1),
                                         getToExecUnit(event2));
         default:
            return 0;
      }
   }

   private String getEvent(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return "Send";
      }
      else if (event instanceof ReceiveEvent)
      {
         return "Receive";
      }
      else if (event instanceof SwapEvent)
      {
         return "Swap";
      }
      else if (event instanceof CreateEvent)
      {
         return "Create";
      }
      else if (event instanceof KillEvent)
      {
         return "Kill";
      }
      else if (event instanceof ErrorEvent)
      {
         return "Error";
      }
      else if (event instanceof ResetEvent)
      {
         return (((ResetEvent) event).isWarmReset() ? "Warm Reset" : "Cold Reset");
      }
      else if (event instanceof UserEvent)
      {
         return "User";
      }
      else if (event instanceof BindEvent)
      {
         return "Bind";
      }
      else if (event instanceof AllocEvent)
      {
         return "Alloc";
      }
      else if (event instanceof FreeEvent)
      {
         return "Free";
      }
      else if (event instanceof TimeoutEvent)
      {
         switch (((TimeoutEvent) event).getSystemCall())
         {
            case TimeoutEvent.SYSTEM_CALL_RECEIVE:
               return "Receive Timeout";
            default:
               return "Unknown Timeout";
         }
      }
      else if (event instanceof LossEvent)
      {
         return "Loss";
      }
      else
      {
         return "";
      }
   }

   private String getFrom(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return ((SendEvent) event).getSenderProcess().toString();
      }
      else if (event instanceof ReceiveEvent)
      {
         return ((ReceiveEvent) event).getSenderProcess().toString();
      }
      else if (event instanceof SwapEvent)
      {
         return ((SwapEvent) event).getSwappedOutProcess().toString();
      }
      else if (event instanceof CreateEvent)
      {
         TargetEvent.ProcessInfo creator = ((CreateEvent) event).getCreatorProcess();
         return ((creator != null) ? creator.toString() : "");
      }
      else if (event instanceof KillEvent)
      {
         TargetEvent.ProcessInfo killer = ((KillEvent) event).getKillerProcess();
         return ((killer != null) ? killer.toString() : "");
      }
      else if (event instanceof ErrorEvent)
      {
         return ((ErrorEvent) event).getProcess().toString();
      }
      else if (event instanceof UserEvent)
      {
         return ((UserEvent) event).getProcess().toString();
      }
      else if (event instanceof BindEvent)
      {
         return ((BindEvent) event).getProcess().toString();
      }
      else if (event instanceof AllocEvent)
      {
         return ((AllocEvent) event).getProcess().toString();
      }
      else if (event instanceof FreeEvent)
      {
         return ((FreeEvent) event).getProcess().toString();
      }
      else if (event instanceof TimeoutEvent)
      {
         return ((TimeoutEvent) event).getProcess().toString();
      }
      else
      {
         return "";
      }
   }

   private String getTo(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return ((SendEvent) event).getAddresseeProcess().toString();
      }
      else if (event instanceof ReceiveEvent)
      {
         return ((ReceiveEvent) event).getAddresseeProcess().toString();
      }
      else if (event instanceof SwapEvent)
      {
         return ((SwapEvent) event).getSwappedInProcess().toString();
      }
      else if (event instanceof CreateEvent)
      {
         return ((CreateEvent) event).getCreatedProcess().toString();
      }
      else if (event instanceof KillEvent)
      {
         return ((KillEvent) event).getKilledProcess().toString();
      }
      else
      {
         return "";
      }
   }

   private int getNumber(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return ((SendEvent) event).getSignalNumber();
      }
      else if (event instanceof ReceiveEvent)
      {
         return ((ReceiveEvent) event).getSignalNumber();
      }
      else if (event instanceof UserEvent)
      {
         return ((UserEvent) event).getEventNumber();
      }
      else if (event instanceof AllocEvent)
      {
         return ((AllocEvent) event).getSignalNumber();
      }
      else if (event instanceof FreeEvent)
      {
         return ((FreeEvent) event).getSignalNumber();
      }
      else if (event instanceof TimeoutEvent)
      {
         return ((TimeoutEvent) event).getTimeout();
      }
      else
      {
         return 0;
      }
   }

   private int getSize(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return ((SendEvent) event).getSignalSize();
      }
      else if (event instanceof ReceiveEvent)
      {
         return ((ReceiveEvent) event).getSignalSize();
      }
      else if (event instanceof UserEvent)
      {
         return ((UserEvent) event).getEventDataSize();
      }
      else if (event instanceof AllocEvent)
      {
         return ((AllocEvent) event).getSignalSize();
      }
      else if (event instanceof FreeEvent)
      {
         return ((FreeEvent) event).getSignalSize();
      }
      else if (event instanceof LossEvent)
      {
         return ((LossEvent) event).getLostSize();
      }
      else
      {
         return 0;
      }
   }

   private int getAddress(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return ((SendEvent) event).getSignalAddress();
      }
      else if (event instanceof ReceiveEvent)
      {
         return ((ReceiveEvent) event).getSignalAddress();
      }
      else if (event instanceof AllocEvent)
      {
         return ((AllocEvent) event).getSignalAddress();
      }
      else if (event instanceof FreeEvent)
      {
         return ((FreeEvent) event).getSignalAddress();
      }
      else
      {
         return 0;
      }
   }

   private String getErrorCaller(TargetEvent event)
   {
      if (event instanceof ErrorEvent)
      {
         return (((ErrorEvent) event).isExecutive() ? "Kernel" : "User");
      }
      else
      {
         return "";
      }
   }

   private int getErrorCode(TargetEvent event)
   {
      if (event instanceof ErrorEvent)
      {
         return ((ErrorEvent) event).getError();
      }
      else
      {
         return 0;
      }
   }

   private int getExtraParameter(TargetEvent event)
   {
      if (event instanceof ErrorEvent)
      {
         return ((ErrorEvent) event).getExtra();
      }
      else
      {
         return 0;
      }
   }

   private short getFromExecUnit(TargetEvent event)
   {
      if (event instanceof SendEvent)
      {
         return ((SendEvent) event).getExecutionUnit();
      }
      else if (event instanceof ReceiveEvent)
      {
         return ((ReceiveEvent) event).getExecutionUnit();
      }
      else if (event instanceof SwapEvent)
      {
         return ((SwapEvent) event).getExecutionUnit();
      }
      else if (event instanceof CreateEvent)
      {
         return ((CreateEvent) event).getExecutionUnit();
      }
      else if (event instanceof KillEvent)
      {
         return ((KillEvent) event).getExecutionUnit();
      }
      else if (event instanceof ErrorEvent)
      {
         return ((ErrorEvent) event).getExecutionUnit();
      }
      else if (event instanceof UserEvent)
      {
         return ((UserEvent) event).getExecutionUnit();
      }
      else if (event instanceof BindEvent)
      {
         return ((BindEvent) event).getFromExecutionUnit();
      }
      else if (event instanceof AllocEvent)
      {
         return ((AllocEvent) event).getExecutionUnit();
      }
      else if (event instanceof FreeEvent)
      {
         return ((FreeEvent) event).getExecutionUnit();
      }
      else if (event instanceof TimeoutEvent)
      {
         return ((TimeoutEvent) event).getExecutionUnit();
      }
      else
      {
         return 0;
      }
   }

   private short getToExecUnit(TargetEvent event)
   {
      if (event instanceof BindEvent)
      {
         return ((BindEvent) event).getToExecutionUnit();
      }
      else
      {
         return 0;
      }
   }

   private int compareStrings(String s1, String s2)
   {
      int result = s1.compareTo(s2);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareUnsignedShorts(short s1, short s2)
   {
      int result;
      int i1 = 0xFFFF & s1;
      int i2 = 0xFFFF & s2;

      result = (i1 < i2) ? -1 : ((i1 > i2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareUnsignedInts(int i1, int i2)
   {
      int result;
      long l1 = 0xFFFFFFFFL & i1;
      long l2 = 0xFFFFFFFFL & i2;

      result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareUnsignedIntsPair(int i11, int i12, int i21, int i22)
   {
      int result;
      long l11 = 0xFFFFFFFFL & i11;
      long l12 = 0xFFFFFFFFL & i12;
      long l21 = 0xFFFFFFFFL & i21;
      long l22 = 0xFFFFFFFFL & i22;

      if (l11 < l21)
      {
         result = -1;
      }
      else if (l11 > l21)
      {
         result = 1;
      }
      else
      {
         result = (l12 < l22) ? -1 : ((l12 > l22) ? 1 : 0);
      }

      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }

   private int compareStringUnsignedIntPair(String s1, int i1, String s2, int i2)
   {
      int result;

      result = compareStrings(s1, s2);
      if (result == 0)
      {
         result = compareUnsignedInts(i1, i2);
      }
      return result;
   }
}
