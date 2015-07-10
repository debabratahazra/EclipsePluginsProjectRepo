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

package com.ose.system.ui.properties;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import com.ose.system.Process;
import com.ose.system.ui.util.StringUtils;

public class ProcessPropertySource implements IPropertySource
{
   // TODO: Add sid, creator, supervisor, supervisorStackSize, timeSlice,
   // vector, numSignalsAttached, and errorHandler when supported.

   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_NAME = "NAME";
   public static final String P_NAME_NAME = "Name:";

   public static final String P_ID_PID = "PID";
   public static final String P_NAME_PID = "Process ID:";

   public static final String P_ID_BID = "BID";
   public static final String P_NAME_BID = "Block ID:";

   public static final String P_ID_UID = "UID";
   public static final String P_NAME_UID = "User Number:";

   public static final String P_ID_TYPE = "TYPE";
   public static final String P_NAME_TYPE = "Type:";

   public static final String P_ID_STATE = "STATE";
   public static final String P_NAME_STATE = "State:";

   public static final String P_ID_PRIORITY = "PRIORITY";
   public static final String P_NAME_PRIORITY = "Priority:";

   public static final String P_ID_NUM_SIGNALS_IN_QUEUE =
      "NUM_SIGNALS_IN_QUEUE";
   public static final String P_NAME_NUM_SIGNALS_IN_QUEUE = "Signals in Queue:";

   public static final String P_ID_ENTRYPOINT = "ENTRYPOINT";
   public static final String P_NAME_ENTRYPOINT = "Entrypoint:";

   public static final String P_ID_SEMAPHORE_VALUE = "SEMAPHORE_VALUE";
   public static final String P_NAME_SEMAPHORE_VALUE = "Fast Semaphore:";

   public static final String P_ID_NUM_SIGNALS_OWNED = "NUM_SIGNALS_OWNED";
   public static final String P_NAME_NUM_SIGNALS_OWNED = "Owned Signals:";

   public static final String P_ID_SIGSELECT = "SIGSELECT";
   public static final String P_NAME_SIGSELECT = "Signal Select:";

   public static final String P_ID_FILE_NAME = "FILE_NAME";
   public static final String P_NAME_FILE_NAME = "Filename:";

   public static final String P_ID_LINE_NUMBER = "LINE_NUMBER";
   public static final String P_NAME_LINE_NUMBER = "Line Number:";

   public static final String P_ID_STACK_SIZE = "STACK_SIZE";
   public static final String P_NAME_STACK_SIZE = "Stack Size:";

   public static final String P_ID_EXECUTION_UNIT = "EXECUTION_UNIT";
   public static final String P_NAME_EXECUTION_UNIT = "Execution Unit:";

   private final IPropertyDescriptor[] propertyDescriptors;

   private Process process;

   public ProcessPropertySource(Process process)
   {
      List propDescriptorList;
      PropertyDescriptor propDescriptor;

      if (process == null)
      {
         throw new NullPointerException();
      }
      this.process = process;

      propDescriptorList = new ArrayList();

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_NAME, P_NAME_NAME);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_PID, P_NAME_PID);
      propDescriptorList.add(propDescriptor);

      if (process.getTarget().hasBlockSupport())
      {
         propDescriptor = new PropertyDescriptor(P_ID_BID, P_NAME_BID);
         propDescriptorList.add(propDescriptor);
      }

      propDescriptor = new PropertyDescriptor(P_ID_UID, P_NAME_UID);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_TYPE, P_NAME_TYPE);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_STATE, P_NAME_STATE);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_PRIORITY, P_NAME_PRIORITY);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_NUM_SIGNALS_IN_QUEUE,
            P_NAME_NUM_SIGNALS_IN_QUEUE);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_ENTRYPOINT,
            P_NAME_ENTRYPOINT);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_SEMAPHORE_VALUE,
            P_NAME_SEMAPHORE_VALUE);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_NUM_SIGNALS_OWNED,
            P_NAME_NUM_SIGNALS_OWNED);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_SIGSELECT, P_NAME_SIGSELECT);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_FILE_NAME, P_NAME_FILE_NAME);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_LINE_NUMBER,
            P_NAME_LINE_NUMBER);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_STACK_SIZE,
            P_NAME_STACK_SIZE);
      propDescriptorList.add(propDescriptor);

      propDescriptor = new PropertyDescriptor(P_ID_EXECUTION_UNIT,
            P_NAME_EXECUTION_UNIT);
      propDescriptorList.add(propDescriptor);

      propertyDescriptors = (IPropertyDescriptor[])
         propDescriptorList.toArray(new IPropertyDescriptor[0]);
   }

   public Object getEditableValue()
   {
      return process;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(process.isKilled());
      }
      else if (id.equals(P_ID_NAME))
      {
         return process.getName();
      }
      else if (id.equals(P_ID_PID))
      {
         return StringUtils.toHexString(process.getId());
      }
      else if (id.equals(P_ID_BID))
      {
         return StringUtils.toHexString(process.getBid());
      }
      else if (id.equals(P_ID_UID))
      {
         return Integer.toString(process.getUid());
      }
      else if (id.equals(P_ID_TYPE))
      {
         return StringUtils.toProcessTypeString(process.getType());
      }
      else if (id.equals(P_ID_STATE))
      {
         return StringUtils.toProcessStateString(process.getState());
      }
      else if (id.equals(P_ID_PRIORITY))
      {
         return Integer.toString(process.getPriority());
      }
      else if (id.equals(P_ID_NUM_SIGNALS_IN_QUEUE))
      {
         return Integer.toString(process.getNumSignalsInQueue());
      }
      else if (id.equals(P_ID_ENTRYPOINT))
      {
         return StringUtils.toHexString(process.getEntrypoint());
      }
      else if (id.equals(P_ID_SEMAPHORE_VALUE))
      {
         return Integer.toString(process.getSemaphoreValue());
      }
      else if (id.equals(P_ID_NUM_SIGNALS_OWNED))
      {
         return Integer.toString(process.getNumSignalsOwned());
      }
      else if (id.equals(P_ID_SIGSELECT))
      {
         StringBuffer sb = new StringBuffer();
         int[] sigselect = process.getSigselect();
         if (sigselect.length > 0)
         {
            String s = ((sigselect[0] == 0) ?
                        StringUtils.ALL : Integer.toString(sigselect[0]));
            sb.append(s + " ");
         }
         for (int i = 1; i < sigselect.length; i++)
         {
            sb.append(StringUtils.toHexString(sigselect[i]) + " ");
         }
         return sb.toString();
      }
      else if (id.equals(P_ID_FILE_NAME))
      {
         return ((process.getFileName().length() > 0) ?
                 process.getFileName() :
                 StringUtils.NOT_AVAILABLE);
      }
      else if (id.equals(P_ID_LINE_NUMBER))
      {
         return ((process.getLineNumber() > 0) ?
                 Integer.toString(process.getLineNumber()) :
                 StringUtils.NOT_AVAILABLE);
      }
      else if (id.equals(P_ID_STACK_SIZE))
      {
         return Integer.toString(process.getStackSize());
      }
      else if (id.equals(P_ID_EXECUTION_UNIT))
      {
         return Short.toString(process.getExecutionUnit());
      }
      else
      {
         return null;
      }
   }

   public void setPropertyValue(Object id, Object value)
   {
      // Not supported.
   }

   public void resetPropertyValue(Object id)
   {
      // Not supported.
   }

   public boolean isPropertySet(Object id)
   {
      return false;
   }
}
