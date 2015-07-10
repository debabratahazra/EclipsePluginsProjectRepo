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

import com.ose.system.service.monitor.Monitor;

/**
 * This class contains information about an OSE process. A ProcessInfo object is
 * immutable and its content is a snapshot at the time of the call of the OSE
 * system model method it was returned from.
 *
 * @see com.ose.system.Target#getFilteredProcesses(IProgressMonitor, int, int, ProcessFilter)
 */
public class ProcessInfo
{
   /* Process types. */

   /** Prioritized process type. */
   public static final int TYPE_PRIORITIZED = Monitor.MONITOR_PROCESS_TYPE_PRIORITIZED;

   /** Background process type. */
   public static final int TYPE_BACKGROUND = Monitor.MONITOR_PROCESS_TYPE_BACKGROUND;

   /** Interrupt process type. */
   public static final int TYPE_INTERRUPT = Monitor.MONITOR_PROCESS_TYPE_INTERRUPT;

   /** Timer-interrupt process type. */
   public static final int TYPE_TIMER_INTERRUPT = Monitor.MONITOR_PROCESS_TYPE_TIMER_INTERRUPT;

   /** Phantom process type. */
   public static final int TYPE_PHANTOM = Monitor.MONITOR_PROCESS_TYPE_PHANTOM;

   /** Signal port process type. */
   public static final int TYPE_SIGNAL_PORT = Monitor.MONITOR_PROCESS_TYPE_SIGNAL_PORT;

   /** Idle process type. */
   public static final int TYPE_IDLE = Monitor.MONITOR_PROCESS_TYPE_IDLE;

   /** Killed process type. */
   public static final int TYPE_KILLED = Monitor.MONITOR_PROCESS_TYPE_KILLED;

   /** Unknown process type. */
   public static final int TYPE_UNKNOWN = Monitor.MONITOR_PROCESS_TYPE_UNKNOWN;

   /* Process states. */

   /** Process is waiting to receive a signal. */
   public static final int STATE_RECEIVE = Monitor.MONITOR_PROCESS_STATE_RECEIVE;

   /** Process is waiting for a delay to expire. */
   public static final int STATE_DELAY = Monitor.MONITOR_PROCESS_STATE_DELAY;

   /** Process is waiting at a semaphore. */
   public static final int STATE_SEMAPHORE = Monitor.MONITOR_PROCESS_STATE_SEMAPHORE;

   /** Process is waiting at a fast semaphore. */
   public static final int STATE_FAST_SEMAPHORE = Monitor.MONITOR_PROCESS_STATE_FAST_SEMAPHORE;

   /** Process is waiting for a remote system call to end. */
   public static final int STATE_REMOTE = Monitor.MONITOR_PROCESS_STATE_REMOTE;

   /** Process is stopped. */
   public static final int STATE_STOPPED = Monitor.MONITOR_PROCESS_STATE_STOPPED;

   /** Process is intercepted. */
   public static final int STATE_INTERCEPTED = Monitor.MONITOR_PROCESS_STATE_INTERCEPTED;

   /** Process is waiting at a mutex. */
   public static final int STATE_MUTEX = Monitor.MONITOR_PROCESS_STATE_MUTEX;

   /** Process is running. */
   public static final int STATE_RUNNING = Monitor.MONITOR_PROCESS_STATE_RUNNING;

   /** Process is ready to run. */
   public static final int STATE_READY = Monitor.MONITOR_PROCESS_STATE_READY;

   /** Process is killed. */
   public static final int STATE_KILLED = Monitor.MONITOR_PROCESS_STATE_KILLED;

   /* Target. */
   private final Target target;

   /* Process info. */
   private final String name;
   private final int id;
   private final int sid; // Only used in OSE 5.2 and later
   private final int bid;
   private final int uid;
   private final int type;
   private final int creator; // Not used
   private final int entrypoint;
   private final int stackSize;
   private final int supervisorStackSize; // Not used
   private final int timeSlice; // Not used
   private final int vector; // Not used
   private final short executionUnit;
   private final boolean supervisor; // Not used
   private final int state;
   private final int priority;
   private final int numSignalsInQueue;
   private final int numSignalsAttached; // Not used
   private final int numSignalsOwned;
   private final int semaphoreValue;
   private final int errorHandler; // Not used
   private final int properties; // Only used in OSE 5.2 and later
   private final int[] sigselect;
   private final String fileName;
   private final int lineNumber;

   /**
    * Create a new process info object.
    *
    * @param target               the target.
    * @param name                 the process name.
    * @param id                   the process ID.
    * @param sid                  the parent segment ID.
    * @param bid                  the parent block ID.
    * @param uid                  the user number.
    * @param type                 the process type.
    * @param creator              the creator process ID.
    * @param entrypoint           the entrypoint address.
    * @param stackSize            the stack size.
    * @param supervisorStacksize  the supervisor stack size.
    * @param timeSlice            the timeslice.
    * @param vector               the vector.
    * @param executionUnit        the execution unit.
    * @param supervisor           supervisor mode or not.
    * @param state                the process state.
    * @param priority             the process priority.
    * @param numSignalsInQueue    the number of signals in the queue.
    * @param numSignalsAttached   the number of attached signals.
    * @param numSignalsOwned      the number of owned signals.
    * @param semaphoreValue       the fast semaphore value.
    * @param errorHandler         the error handler address.
    * @param properties           the properties bit field.
    * @param sigselect            the signal select array.
    * @param fileName             the filename.
    * @param lineNumber           the line number.
    */
   ProcessInfo(Target target,
               String name,
               int id,
               int sid,
               int bid,
               int uid,
               int type,
               int creator,
               int entrypoint,
               int stackSize,
               int supervisorStacksize,
               int timeSlice,
               int vector,
               short executionUnit,
               boolean supervisor,
               int state,
               int priority,
               int numSignalsInQueue,
               int numSignalsAttached,
               int numSignalsOwned,
               int semaphoreValue,
               int errorHandler,
               int properties,
               int[] sigselect,
               String fileName,
               int lineNumber)
   {
      if ((target == null) || (name == null) ||
          (sigselect == null) || (fileName == null))
      {
         throw new NullPointerException();
      }
      if ((type != TYPE_PRIORITIZED) &&
          (type != TYPE_BACKGROUND) &&
          (type != TYPE_INTERRUPT) &&
          (type != TYPE_TIMER_INTERRUPT) &&
          (type != TYPE_PHANTOM) &&
          (type != TYPE_SIGNAL_PORT) &&
          (type != TYPE_IDLE) &&
          (type != TYPE_KILLED) &&
          (type != TYPE_UNKNOWN))
      {
         type = TYPE_UNKNOWN;
      }
      if (((state & STATE_RECEIVE) == 0) &&
          ((state & STATE_DELAY) == 0) &&
          ((state & STATE_SEMAPHORE) == 0) &&
          ((state & STATE_FAST_SEMAPHORE) == 0) &&
          ((state & STATE_REMOTE) == 0) &&
          ((state & STATE_STOPPED) == 0) &&
          ((state & STATE_INTERCEPTED) == 0) &&
          ((state & STATE_MUTEX) == 0) &&
          ((state & STATE_RUNNING) == 0) &&
          ((state & STATE_READY) == 0) &&
          ((state & STATE_KILLED) == 0))
      {
         throw new IllegalStateException("Invalid state: " + state);
      }
      if ((priority < 0) || (priority > 31))
      {
         throw new IllegalStateException("Invalid priority: " + priority);
      }
      this.target = target;
      this.name = name;
      this.id = id;
      this.sid = sid;
      this.bid = bid;
      this.uid = uid;
      this.type = type;
      this.creator = creator;
      this.entrypoint = entrypoint;
      this.stackSize = stackSize;
      this.supervisorStackSize = supervisorStacksize;
      this.timeSlice = timeSlice;
      this.vector = vector;
      this.executionUnit = executionUnit;
      this.supervisor = supervisor;
      this.state = state;
      this.priority = priority;
      this.numSignalsInQueue = numSignalsInQueue;
      this.numSignalsAttached = numSignalsAttached;
      this.numSignalsOwned = numSignalsOwned;
      this.semaphoreValue = semaphoreValue;
      this.errorHandler = errorHandler;
      this.properties = properties;
      this.sigselect = sigselect;
      this.fileName = fileName;
      this.lineNumber = lineNumber;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Return the name of this process.
    *
    * @return the name of this process.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Return the ID of this process.
    *
    * @return the ID of this process.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Return the parent segment ID of this process.
    *
    * @return the parent segment ID of this process.
    */
   public int getSid()
   {
      return sid;
   }

   /**
    * Return the parent block ID of this process.
    *
    * @return the parent block ID of this process.
    */
   public int getBid()
   {
      return bid;
   }

   /**
    * Return the user number of this process.
    *
    * @return the user number of this process.
    */
   public int getUid()
   {
      return uid;
   }

   /**
    * Return the type of this process, i.e. one of the ProcessInfo.TYPE_*
    * constants.
    *
    * @return the type of this process.
    * @see #TYPE_PRIORITIZED
    * @see #TYPE_BACKGROUND
    * @see #TYPE_INTERRUPT
    * @see #TYPE_TIMER_INTERRUPT
    * @see #TYPE_PHANTOM
    * @see #TYPE_SIGNAL_PORT
    * @see #TYPE_IDLE
    * @see #TYPE_KILLED
    * @see #TYPE_UNKNOWN
    */
   public int getType()
   {
      return type;
   }

   /**
    * Return the process ID of the creator of this process.
    *
    * @return the process ID of the creator of this process.
    */
   public int getCreator()
   {
      return creator;
   }

   /**
    * Return the entrypoint address of this process.
    *
    * @return the entrypoint address of this process.
    */
   public int getEntrypoint()
   {
      return entrypoint;
   }

   /**
    * Return the stack size of this process.
    *
    * @return the stack size of this process.
    */
   public int getStackSize()
   {
      return stackSize;
   }

   /**
    * Return the supervisor stack size of this process.
    *
    * @return the supervisor stack size of this process.
    */
   public int getSupervisorStackSize()
   {
      return supervisorStackSize;
   }

   /**
    * Return the timeslice of this background or timer-interrupt process or 0
    * if this process is of another type.
    *
    * @return the timeslice of this background or timer-interrupt process or 0
    * if this process is of another type.
    */
   public int getTimeSlice()
   {
      return timeSlice;
   }

   /**
    * Return the vector of this interrupt process or 0 if this process is of
    * another type.
    *
    * @return the vector of this interrupt process or 0 if this process is of
    * another type.
    */
   public int getVector()
   {
      return vector;
   }

   /**
    * Return the execution unit this process is bound to.
    *
    * @return the execution unit this process is bound to.
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }

   /**
    * Determine whether this process runs in supervisor mode or not.
    *
    * @return true if this process runs in supervisor mode, false othetwise.
    */
   public boolean isSupervisor()
   {
      return supervisor;
   }

   /**
    * Return the state of this process, i.e. one of the ProcessInfo.STATE_*
    * constants.
    *
    * @return the state of this process.
    * @see #STATE_RECEIVE
    * @see #STATE_DELAY
    * @see #STATE_SEMAPHORE
    * @see #STATE_FAST_SEMAPHORE
    * @see #STATE_REMOTE
    * @see #STATE_STOPPED
    * @see #STATE_INTERCEPTED
    * @see #STATE_MUTEX
    * @see #STATE_RUNNING
    * @see #STATE_READY
    * @see #STATE_KILLED
    */
   public int getState()
   {
      return state;
   }

   /**
    * Return the priority of this process.
    *
    * @return the priority of this process.
    */
   public int getPriority()
   {
      return priority;
   }

   /**
    * Return the number of signals in the queue of this process.
    *
    * @return the number of signals in the queue of this process.
    */
   public int getNumSignalsInQueue()
   {
      return numSignalsInQueue;
   }

   /**
    * Return the number of signals attached to this process.
    *
    * @return the number of signals attached to this process.
    */
   public int getNumSignalsAttached()
   {
      return numSignalsAttached;
   }

   /**
    * Return the number of signals owned by this process.
    *
    * @return the number of signals owned by this process.
    */
   public int getNumSignalsOwned()
   {
      return numSignalsOwned;
   }

   /**
    * Return the fast semaphore value of this process.
    *
    * @return the fast semaphore value of this process.
    */
   public int getSemaphoreValue()
   {
      return semaphoreValue;
   }

   /**
    * Return the error handler address of this process.
    *
    * @return the error handler address of this process.
    */
   public int getErrorHandler()
   {
      return errorHandler;
   }

   /**
    * Return the properties bit field of this process.
    * Bit 0 is set to 1 for system processes in OSE 5.2 and later.
    * Other bits are undefined.
    *
    * @return the properties bit field of this process.
    */
   public int getProperties()
   {
      return properties;
   }

   /**
    * Return the signal select array of this process (never null). A new array
    * is created and returned for each call.
    *
    * @return the signal select array of this process (never null).
    */
   public int[] getSigselect()
   {
      // Defensive copy.
      return (int[]) sigselect.clone();
   }

   /**
    * Return the name of the source file where this process is executing or an
    * empty string if not known.
    *
    * @return the name of the source file where this process is executing or an
    * empty string if not known.
    */
   public String getFileName()
   {
      return fileName;
   }

   /**
    * Return the line number in the source file where this process is
    * executing or 0 if not known.
    *
    * @return the line number in the source file where this process is
    * executing or 0 if not known.
    */
   public int getLineNumber()
   {
      return lineNumber;
   }

   /**
    * Return a string representation of this process info object. The returned
    * string is of the form "&lt;process-name&gt; (&lt;process-id&gt;)", where
    * process-id is in hexadecimal form.
    *
    * @return a string representation of this process info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (name + " (0x" + Integer.toHexString(id).toUpperCase() + ")");
   }
}
