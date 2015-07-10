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

package com.ose.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorTag;

/**
 * This class is a filter used when filtering OSE processes.
 *
 * @see com.ose.system.Target#getFilteredProcesses(IProgressMonitor, int, int, ProcessFilter)
 */
public class ProcessFilter
{
   private final List tags;

   /**
    * Create a new empty process filter object.
    */
   public ProcessFilter()
   {
      tags = new ArrayList();
   }

   /**
    * Add a filter criteria for the type of a process, i.e. one of the
    * ProcessInfo.TYPE_* constants.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param type     the process type value.
    * @see ProcessInfo#TYPE_PRIORITIZED
    * @see ProcessInfo#TYPE_BACKGROUND
    * @see ProcessInfo#TYPE_INTERRUPT
    * @see ProcessInfo#TYPE_TIMER_INTERRUPT
    * @see ProcessInfo#TYPE_PHANTOM
    * @see ProcessInfo#TYPE_SIGNAL_PORT
    * @see ProcessInfo#TYPE_IDLE
    * @see ProcessInfo#TYPE_KILLED
    * @see ProcessInfo#TYPE_UNKNOWN
    */
   public void filterType(boolean exclude, int type)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_TYPE,
            new Integer(type)));
   }

   /**
    * Add a filter criteria for the state of a process, i.e. one of the
    * ProcessInfo.STATE_* constants.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param state    the process state value.
    * @see ProcessInfo#STATE_RECEIVE
    * @see ProcessInfo#STATE_DELAY
    * @see ProcessInfo#STATE_SEMAPHORE
    * @see ProcessInfo#STATE_FAST_SEMAPHORE
    * @see ProcessInfo#STATE_REMOTE
    * @see ProcessInfo#STATE_STOPPED
    * @see ProcessInfo#STATE_INTERCEPTED
    * @see ProcessInfo#STATE_MUTEX
    * @see ProcessInfo#STATE_RUNNING
    * @see ProcessInfo#STATE_READY
    * @see ProcessInfo#STATE_KILLED
    */
   public void filterState(boolean exclude, int state)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_STATE,
            new Integer(state)));
   }

   /**
    * Add a filter criteria for the priority of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min process priority value, inclusive.
    * @param to       the max process priority value, exclusive.
    */
   public void filterPriority(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_PRIORITY,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the user number of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min user number value, inclusive.
    * @param to       the max user number value, exclusive.
    */
   public void filterUid(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_UID,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the process ID of the creator of a process.
    *
    * @param exclude    whether or not to invert the filter criteria.
    * @param creatorId  the creator process ID value.
    */
   public void filterCreatorId(boolean exclude, int creatorId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_CREATOR_ID,
            new Integer(creatorId)));
   }

   /**
    * Add a filter criteria for the supervisor mode property of a process.
    *
    * @param exclude     whether or not to invert the filter criteria.
    * @param supervisor  the supervisor mode value.
    */
   public void filterSupervisor(boolean exclude, int supervisor)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SUPERVISOR,
            new Integer(supervisor)));
   }

   /**
    * Add a filter criteria for the number of signals in the queue of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min number of signals in queue value, inclusive.
    * @param to       the max number of signals in queue value, exclusive.
    */
   public void filterSignalsInQueue(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SIGNALS_IN_QUEUE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the number of signals attached to a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min number of signals attached value, inclusive.
    * @param to       the max number of signals attached value, exclusive.
    */
   public void filterSignalsAttached(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SIGNALS_ATTACHED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the entrypoint address of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min entrypoint address value, inclusive.
    * @param to       the max entrypoint address value, exclusive.
    */
   public void filterEntrypoint(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_ENTRYPOINT,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the fast semaphore value of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min fast semaphore value, inclusive.
    * @param to       the max fast semaphore value, exclusive.
    */
   public void filterSemaphoreValue(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SEMAPHORE_VALUE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the stack size of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min stack size value, inclusive.
    * @param to       the max stack size value, exclusive.
    */
   public void filterStackSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_STACK_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the supervisor stack size of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min supervisor stack size value, inclusive.
    * @param to       the max supervisor stack size value, exclusive.
    */
   public void filterSupervisorStackSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SUPERVISOR_STACK_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the line number in the source file where a
    * process is executing.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min line number value, inclusive.
    * @param to       the max line number value, exclusive.
    */
   public void filterLineNumber(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_LINE_NUMBER,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the number of signals owned by a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min number of signals owned value, inclusive.
    * @param to       the max number of signals owned value, exclusive.
    */
   public void filterSignalsOwned(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SIGNALS_OWNED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the timeslice of a background or timer-interrupt
    * process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min timeslice value, inclusive.
    * @param to       the max timeslice value, exclusive.
    */
   public void filterTimeSlice(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_TIME_SLICE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the vector of an interrupt process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min vector value, inclusive.
    * @param to       the max vector value, exclusive.
    */
   public void filterVector(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_VECTOR,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the error handler address of a process.
    *
    * @param exclude       whether or not to invert the filter criteria.
    * @param errorHandler  the error handler address value.
    */
   public void filterErrorHandler(boolean exclude, int errorHandler)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_ERROR_HANDLER,
            new Integer(errorHandler)));
   }

   /**
    * Add a filter criteria for the system property of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    */
   public void filterSystem(boolean exclude)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SYSTEM));
   }

   /**
    * Add a filter criteria for the signal select count of a process.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min signal select count value, inclusive.
    * @param to       the max signal select count value, exclusive.
    */
   public void filterSigselectCount(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_SIGSELECT_COUNT,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the name of a process. Accepts wildcards, where
    * a '*' matches any string of zero or more characters, and a '?' matches any
    * single character.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param name     the process name value.
    */
   public void filterName(boolean exclude, String name)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NAME, name));
   }

   /**
    * Add a filter criteria for the name of the source file where a process is
    * executing. Accepts wildcards, where a '*' matches any string of zero or
    * more characters, and a '?' matches any single character.
    *
    * @param exclude   whether or not to invert the filter criteria.
    * @param fileName  the file name value.
    */
   public void filterFileName(boolean exclude, String fileName)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_FILE_NAME,
            fileName));
   }

   /**
    * Add a filter criteria for the execution unit a process is bound to.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min execution unit value, inclusive.
    * @param to       the max execution unit value, exclusive.
    */
   public void filterExecutionUnit(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_PROCESS_FILTER_TAG_EU_ID,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Return an array of the OSE monitor protocol tags constituting this process
    * filter.
    *
    * @return an array of the OSE monitor protocol tags constituting this
    * process filter.
    */
   MonitorTag[] getTags()
   {
      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this process filter object.
    *
    * @return a string representation of this process filter object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer("Process Filter:\n");
      for (Iterator i = tags.iterator(); i.hasNext();)
      {
         MonitorTag tag = (MonitorTag) i.next();
         sb.append("Type: " + tag.getType() + ", args: " + tag.getArgs() + "\n");
      }
      return sb.toString();
   }
}
