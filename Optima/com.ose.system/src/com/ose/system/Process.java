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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.Target.MonitorHandler;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorSignalInfo;
import com.ose.system.service.monitor.MonitorStatus;

/**
 * This class represents an OSE process and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A process has no children nodes and a block or target as parent node.
 * A process can be started, stopped, and killed, and environment variables
 * can be set on it.
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.OseObject
 */
public class Process extends OseObject implements SystemModelNode
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

   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Killed status. */
   private boolean killed;

   /* Target and block. */
   private final Target target;
   private final Block block;

   /* Process info. */
   private final String name;
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
   private short executionUnit;
   private boolean supervisor; // Not used
   private int state;
   private int priority;
   private int numSignalsInQueue;
   private int numSignalsAttached; // Not used
   private int numSignalsOwned;
   private int semaphoreValue;
   private int errorHandler; // Not used
   private final int properties; // Only used in OSE 5.2 and later
   private int[] sigselect;
   private String fileName;
   private int lineNumber;

   /* Environment variables. */
   private final ArrayList envVars;
   private final ArrayList tmpEnvVars;
   private boolean envVarsInitialized;

   /* Signal queue. */
   private final ArrayList signalQueue;
   private final ArrayList tmpSignalQueue;
   private boolean signalQueueInitialized;

   /**
    * Create a new process object.
    *
    * @param target               the target.
    * @param block                the parent block.
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
   Process(Target target,
           Block block,
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
      super(id);
      if ((target == null) ||
          (target.hasBlockSupport() && (block == null)) ||
          (name == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.block = block;
      this.name = name;
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
      envVars = new ArrayList();
      tmpEnvVars = new ArrayList();
      signalQueue = new ArrayList();
      tmpSignalQueue = new ArrayList();
   }

   /**
    * Change the mutable properties of this process.
    *
    * @param executionUnit       the execution unit.
    * @param supervisor          supervisor mode or not.
    * @param state               the process state.
    * @param priority            the process priority.
    * @param numSignalsInQueue   the number of signals in the queue.
    * @param numSignalsAttached  the number of attached signals.
    * @param numSignalsOwned     the number of owned signals.
    * @param semaphoreValue      the fast semaphore value.
    * @param errorHandler        the error handler address.
    * @param sigselect           the signal select array.
    * @param fileName            the filename.
    * @param lineNumber          the line number.
    * @return true if any of the mutable properties of this process were
    * changed, false otherwise.
    */
   boolean change(short executionUnit,
                  boolean supervisor,
                  int state,
                  int priority,
                  int numSignalsInQueue,
                  int numSignalsAttached,
                  int numSignalsOwned,
                  int semaphoreValue,
                  int errorHandler,
                  int[] sigselect,
                  String fileName,
                  int lineNumber)
   {
      synchronized (lock)
      {
         boolean changed = ((this.executionUnit != executionUnit) ||
                            (this.supervisor != supervisor) ||
                            (this.state != state) ||
                            (this.priority != priority) ||
                            (this.numSignalsInQueue != numSignalsInQueue) ||
                            (this.numSignalsAttached != numSignalsAttached) ||
                            (this.numSignalsOwned != numSignalsOwned) ||
                            (this.semaphoreValue != semaphoreValue) ||
                            (this.errorHandler != errorHandler) ||
                            !Arrays.equals(this.sigselect, sigselect) ||
                            !this.fileName.equals(fileName) ||
                            (this.lineNumber != lineNumber));
         this.executionUnit = executionUnit;
         this.supervisor = supervisor;
         this.state = state;
         this.priority = priority;
         this.numSignalsInQueue = numSignalsInQueue;
         this.numSignalsAttached = numSignalsAttached;
         this.numSignalsOwned = numSignalsOwned;
         this.semaphoreValue = semaphoreValue;
         this.errorHandler = errorHandler;
         this.sigselect = sigselect;
         this.fileName = fileName;
         this.lineNumber = lineNumber;
         return changed;
      }
   }

   /**
    * Add an environment variable to this process.
    *
    * @param name   the name of the environment variable.
    * @param value  the value of the environment variable.
    * @see #commitEnvVars()
    */
   void addEnvVar(String name, String value)
   {
      synchronized (lock)
      {
         tmpEnvVars.add(new Parameter(name, value));
      }
   }

   /**
    * Commit all environment variables added to this process with the
    * addEnvVar() method.
    *
    * @return true if any environment variable was changed, added, or removed;
    * false otherwise.
    * @see #addEnvVar(String, String)
    */
   boolean commitEnvVars()
   {
      synchronized (lock)
      {
         boolean changed = !envVars.equals(tmpEnvVars);
         if (changed)
         {
            envVars.clear();
            envVars.addAll(tmpEnvVars);
            envVars.trimToSize();
         }
         tmpEnvVars.clear();
         tmpEnvVars.trimToSize();
         return changed;
      }
   }

   /**
    * Add a signal to the signal queue of this process.
    *
    * @param pid     ID of the process holding the signal queue.
    * @param signal  the signal.
    * @param data    the signal data or null.
    * @see #commitSignalQueue()
    * @see MonitorSignalInfo
    */
   void addSignalQueueItem(int pid, MonitorSignalInfo signal, byte[] data)
   {
      synchronized (lock)
      {
         tmpSignalQueue.add(new SignalInfo(signal, data));
      }
   }

   /**
    * Commit all signals added to the signal queue of this process with the
    * addSignalQueueItem() method.
    *
    * @return true if the signal queue was changed, false otherwise.
    * @see #addSignalQueueItem(int, MonitorSignalInfo, byte[])
    */
   boolean commitSignalQueue()
   {
      synchronized (lock)
      {
         boolean changed = !signalQueue.equals(tmpSignalQueue);
         if (changed)
         {
            signalQueue.clear();
            signalQueue.addAll(tmpSignalQueue);
            signalQueue.trimToSize();
         }
         tmpSignalQueue.clear();
         tmpSignalQueue.trimToSize();
         if (numSignalsInQueue != signalQueue.size())
         {
            numSignalsInQueue = signalQueue.size();
            changed = true;
         }
         return changed;
      }
   }

   /**
    * Determine whether this process is killed or not.
    *
    * @return true if this process is killed, false otherwise.
    * @see com.ose.system.SystemModelNode#isKilled()
    */
   public boolean isKilled()
   {
      synchronized (lock)
      {
         return killed;
      }
   }

   /**
    * Mark this process as killed.
    *
    * @see com.ose.system.OseObject#setKilled()
    */
   void setKilled()
   {
      setKilled(true);
   }

   /**
    * Mark this process as killed.
    *
    * @param self  true if a change event should be fired for this node.
    */
   void setKilled(boolean self)
   {
      synchronized (lock)
      {
         if (killed)
         {
            return;
         }
         killed = true;
      }
      target.removeId(getId());
      if (self)
      {
         SystemModel.getInstance().fireNodesChanged(getParent(),
               Collections.singletonList(this));
      }
   }

   /**
    * Determine whether all of the given immutable process properties are
    * identical to the ones of this process.
    *
    * @param id         ID of a process.
    * @param name       name of a process.
    * @param sid        parent segment ID of a process.
    * @param bid        parent block ID of a process.
    * @param uid        user number of a process.
    * @param type       type of a process.
    * @param creator    creator process ID of a process.
    * @param entrypoint entrypoint address of a process.
    * @param stackSize  stack size of a process.
    * @param supervisorStackSize  supervisor stack size of a process.
    * @param timeSlice  timeslice of a process.
    * @param vector     vector of a process.
    * @return true if all of the given immutable process properties matches this
    * process, false otherwise.
    */
   boolean matches(int id,
                   String name,
                   int sid,
                   int bid,
                   int uid,
                   int type,
                   int creator,
                   int entrypoint,
                   int stackSize,
                   int supervisorStackSize,
                   int timeSlice,
                   int vector)
   {
      // Final fields, no synchronization needed.
      boolean result = false;
      // FIXME: The properties field should also be tested if it is supported.
      result = ((getId() == id) &&
                (this.name.equals(name)) &&
                (this.sid == sid) &&
                (this.bid == bid) &&
                (this.uid == uid) &&
                (this.type == type) &&
                (this.creator == creator) &&
                (this.entrypoint == entrypoint) &&
                (this.stackSize == stackSize) &&
                (this.supervisorStackSize == supervisorStackSize) &&
                (this.timeSlice == timeSlice) &&
                (this.vector == vector));
      return result;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      // Final field, no synchronization needed.
      return target;
   }

   /**
    * Return the parent block object or null if this target does not support
    * blocks.
    *
    * @return the parent block object or null if this target does not support
    * blocks.
    */
   public Block getBlock()
   {
      // Final field, no synchronization needed.
      return block;
   }

   /**
    * Return the name of this process.
    *
    * @return the name of this process.
    */
   public String getName()
   {
      // Final field, no synchronization needed.
      return name;
   }

   /**
    * Return the parent segment ID of this process.
    *
    * @return the parent segment ID of this process.
    */
   public int getSid()
   {
      // Final field, no synchronization needed.
      return sid;
   }

   /**
    * Return the parent block ID of this process or 0 if this target does not
    * support blocks.
    *
    * @return the parent block ID of this process or 0 if this target does not
    * support blocks.
    */
   public int getBid()
   {
      // Final field, no synchronization needed.
      return bid;
   }

   /**
    * Return the user number of this process.
    *
    * @return the user number of this process.
    */
   public int getUid()
   {
      // Final field, no synchronization needed.
      return uid;
   }

   /**
    * Return the type of this process, i.e. one of the Process.TYPE_* constants.
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
      // Final field, no synchronization needed.
      return type;
   }

   /**
    * Return the process ID of the creator of this process.
    *
    * @return the process ID of the creator of this process.
    */
   public int getCreator()
   {
      // Final field, no synchronization needed.
      return creator;
   }

   /**
    * Return the entrypoint address of this process.
    *
    * @return the entrypoint address of this process.
    */
   public int getEntrypoint()
   {
      // Final field, no synchronization needed.
      return entrypoint;
   }

   /**
    * Return the stack size of this process.
    *
    * @return the stack size of this process.
    */
   public int getStackSize()
   {
      // Final field, no synchronization needed.
      return stackSize;
   }

   /**
    * Return the supervisor stack size of this process.
    *
    * @return the supervisor stack size of this process.
    */
   public int getSupervisorStackSize()
   {
      // Final field, no synchronization needed.
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
      // Final field, no synchronization needed.
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
      // Final field, no synchronization needed.
      return vector;
   }

   /**
    * Return the execution unit this process is bound to.
    *
    * @return the execution unit this process is bound to.
    */
   public short getExecutionUnit()
   {
      synchronized (lock)
      {
         return executionUnit;
      }
   }

   /**
    * Determine whether this process runs in supervisor mode or not.
    *
    * @return true if this process runs in supervisor mode, false othetwise.
    */
   public boolean isSupervisor()
   {
      synchronized (lock)
      {
         return supervisor;
      }
   }

   /**
    * Return the state of this process, i.e. one of the Process.STATE_*
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
      synchronized (lock)
      {
         return state;
      }
   }

   /**
    * Return the priority of this process.
    *
    * @return the priority of this process.
    */
   public int getPriority()
   {
      synchronized (lock)
      {
         return priority;
      }
   }

   /**
    * Return the number of signals in the queue of this process.
    *
    * @return the number of signals in the queue of this process.
    */
   public int getNumSignalsInQueue()
   {
      synchronized (lock)
      {
         return numSignalsInQueue;
      }
   }

   /**
    * Return the number of signals attached to this process.
    *
    * @return the number of signals attached to this process.
    */
   public int getNumSignalsAttached()
   {
      synchronized (lock)
      {
         return numSignalsAttached;
      }
   }

   /**
    * Return the number of signals owned by this process.
    *
    * @return the number of signals owned by this process.
    */
   public int getNumSignalsOwned()
   {
      synchronized (lock)
      {
         return numSignalsOwned;
      }
   }

   /**
    * Return the fast semaphore value of this process.
    *
    * @return the fast semaphore value of this process.
    */
   public int getSemaphoreValue()
   {
      synchronized (lock)
      {
         return semaphoreValue;
      }
   }

   /**
    * Return the error handler address of this process.
    *
    * @return the error handler address of this process.
    */
   public int getErrorHandler()
   {
      synchronized (lock)
      {
         return errorHandler;
      }
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
      // Final field, no synchronization needed.
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
      synchronized (lock)
      {
         // Defensive copy.
         return (int[]) sigselect.clone();
      }
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
      synchronized (lock)
      {
         return fileName;
      }
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
      synchronized (lock)
      {
         return lineNumber;
      }
   }

   /**
    * Return an array of the environment variables set on this process (never
    * null). A new array is created and returned for each call.
    *
    * Note that the environment variables of a process are lazily initialized
    * and will only be available if they have been initialized.
    *
    * @return an array of the environment variables set on this process (never
    * null).
    */
   public Parameter[] getEnvVars()
   {
      synchronized (lock)
      {
         return (Parameter[]) envVars.toArray(new Parameter[0]);
      }
   }

   /**
    * Return the signal queue of this process (never null). A new array is
    * created and returned for each call.
    *
    * Note that the signal queue of a process is lazily initialized and will
    * only be available if it has been initialized.
    *
    * @return the signal queue of this process (never null).
    */
   public SignalInfo[] getSignalQueue()
   {
      synchronized (lock)
      {
         return (SignalInfo[]) signalQueue.toArray(new SignalInfo[0]);
      }
   }

   /**
    * Return the parent object. If this target supports blocks, the parent
    * object is a block, otherwise it is a target object.
    *
    * @return the parent object.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return (target.hasBlockSupport() ? getBlock() : (SystemModelNode) getTarget());
   }

   /**
    * A process object has no children and always returns an empty children
    * array.
    *
    * @return an empty array.
    * @see com.ose.system.SystemModelNode#getChildren()
    */
   public SystemModelNode[] getChildren()
   {
      return new SystemModelNode[0];
   }

   /**
    * Determine whether this node is a leaf node or not. A process object is a
    * leaf node and always returns true.
    *
    * @return true since a process object is a leaf node
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return true;
   }

   /**
    * If the lazily initialized environment variables and signal queue of this
    * process have not been initialized, initialize them and update the other
    * properties of this process for consistency.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    * @see com.ose.system.OseObject#initLazyProperties(org.eclipse.core.runtime.IProgressMonitor)
    */
   public void initLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();
            if ((target.hasGetEnvVarSupport() && !envVarsInitialized) ||
                (target.hasSignalQueueSupport() && !signalQueueInitialized))
            {
               monitorHandler.getProcessInfoRequest(progressMonitor, this,
                     1, Monitor.MONITOR_SCOPE_ID, getId());
            }
            if (target.hasGetEnvVarSupport() && !envVarsInitialized)
            {
               monitorHandler.getEnvVarsRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_ID, getId());
               envVarsInitialized = true;
            }
            if (target.hasSignalQueueSupport() && !signalQueueInitialized)
            {
               monitorHandler.getSignalQueueRequest(progressMonitor, this,
                     0, Monitor.MONITOR_SCOPE_ID, getId());
               signalQueueInitialized = true;
            }
         }
      }
   }

   /**
    * Update this process. The lazily initialized environment variables and
    * signal queue of this process will only be updated if they have been
    * initialized.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        not applicable.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    * @see com.ose.system.SystemModelNode#update(org.eclipse.core.runtime.IProgressMonitor,
    * boolean)
    */
   public void update(IProgressMonitor progressMonitor, boolean recursive)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();
            monitorHandler.getProcessInfoRequest(progressMonitor, this,
                  1, Monitor.MONITOR_SCOPE_ID, getId());
            if (target.hasGetEnvVarSupport() && envVarsInitialized)
            {
               monitorHandler.getEnvVarsRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_ID, getId());
            }
            if (target.hasSignalQueueSupport() && signalQueueInitialized)
            {
               monitorHandler.getSignalQueueRequest(progressMonitor, this,
                     0, Monitor.MONITOR_SCOPE_ID, getId());
            }
         }
      }
   }

   /**
    * If the lazily initialized environment variables and signal queue of this
    * process have been initialized, update them.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    * @see com.ose.system.OseObject#updateLazyProperties(org.eclipse.core.runtime.IProgressMonitor)
    */
   void updateLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();
            if (target.hasGetEnvVarSupport() && envVarsInitialized)
            {
               monitorHandler.getEnvVarsRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_ID, getId());
            }
            if (target.hasSignalQueueSupport() && signalQueueInitialized)
            {
               monitorHandler.getSignalQueueRequest(progressMonitor, this,
                     0, Monitor.MONITOR_SCOPE_ID, getId());
            }
         }
      }
   }

   /**
    * Clean this process, i.e. remove it if it is marked as killed.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      synchronized (target)
      {
         if (isKilled())
         {
            if (target.hasBlockSupport())
            {
               block.removeProcess(this);
               SystemModel.getInstance().fireNodesRemoved(
                  block, Collections.singletonList(this));
            }
            else
            {
               target.removeProcess(this);
               SystemModel.getInstance().fireNodesRemoved(
                  target, Collections.singletonList(this));
            }
         }
      }
   }

   /**
    * Start this process if possible. If successful, this process is updated so
    * that its state is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void start(IProgressMonitor progressMonitor) throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         update(progressMonitor, true);
         if (!isKilled())
         {
            if (isStartable())
            {
               target.getMonitorHandler().startScopeRequest(progressMonitor,
                     Monitor.MONITOR_SCOPE_ID, getId());
               update(progressMonitor, true);
            }
            else
            {
               // Compensate for a missing condition check in the monitor.
               throw new ServiceException(Target.SERVICE_MONITOR_ID,
                  MonitorStatus.MONITOR_STATUS_CANCELLED,
                  "An attempt was made to start a process that is not stopped.");
            }
         }
      }
   }

   /**
    * Determine whether this process can be started or not.
    *
    * @return true if this process can be started, false otherwise.
    */
   boolean isStartable()
   {
      boolean startableType;
      boolean stopped;

      startableType = (type == TYPE_PRIORITIZED) ||
                      (type == TYPE_BACKGROUND) ||
                      (type == TYPE_TIMER_INTERRUPT);
      stopped = ((getState() & STATE_STOPPED) != 0);
      return !startableType || (startableType && stopped);
   }

   /**
    * Stop this process if possible. If successful, this process is updated so
    * that its state is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void stop(IProgressMonitor progressMonitor) throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().stopScopeRequest(progressMonitor,
                  Monitor.MONITOR_SCOPE_ID, getId());
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Kill this process if possible. If successful, this process is updated so
    * that its state is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void kill(IProgressMonitor progressMonitor) throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().killScopeRequest(progressMonitor,
                  Monitor.MONITOR_SCOPE_ID, getId());
            update(progressMonitor, true);
            if (target.hasBlockSupport())
            {
               block.update(progressMonitor, true);
               if (block.isKilled())
               {
                  target.update(progressMonitor, false);
               }
            }
         }
      }
   }

   /**
    * Set a string-valued environment variable on this process.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param name             the name of the environment variable.
    * @param value            the string-value of the environment variable or
    * null to remove the environment variable.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void setEnvVar(IProgressMonitor progressMonitor, String name, String value)
      throws IOException
   {
      if ((progressMonitor == null) || (name == null))
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().setEnvVarRequest(progressMonitor,
                                                        getId(),
                                                        name,
                                                        value);
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Set a pointer-valued environment variable on this process.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param name             the name of the environment variable.
    * @param value            the pointer-value of the environment variable.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void setEnvVar(IProgressMonitor progressMonitor, String name, int value)
      throws IOException
   {
      // TODO: Not yet supported.
   }

   /**
    * Signal the fast semaphore of this process if possible. If successful, this
    * process is updated so that its state is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void signalSemaphore(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().signalSemaphoreRequest(progressMonitor,
                                                              getId());
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Set the priority of this process if possible. If successful, this process
    * is updated so that its state is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param priority         the priority to set.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void setPriority(IProgressMonitor progressMonitor, int priority)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().setPriorityRequest(progressMonitor,
                                                          getId(),
                                                          priority);
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Set the execution unit of this process if possible. If successful, this
    * process is updated so that its state is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit    the execution unit to set.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void setExecutionUnit(IProgressMonitor progressMonitor, short executionUnit)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (target)
      {
         if (!isKilled())
         {
            target.getMonitorHandler().setExecutionUnitRequest(progressMonitor,
                                                               getId(),
                                                               executionUnit);
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Check the state of this process object.
    *
    * @param recursive               not applicable.
    * @throws IllegalStateException  if this process object is not in a
    * consistent state.
    * @see com.ose.system.SystemModelNode#checkState(boolean)
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      synchronized (lock)
      {
         if (target == null)
         {
            throw new IllegalStateException("Target is null.");
         }
         if (target.hasBlockSupport() && (block == null))
         {
            throw new IllegalStateException("Block is null.");
         }
         if (name == null)
         {
            throw new IllegalStateException("Name is null.");
         }
         /* TODO: Uncomment when supported.
         if (sid != block.getSid())
         {
            throw new IllegalStateException("Inconsistent segment id.");
         }
         */
         if (target.hasBlockSupport() && (bid != block.getId()))
         {
            throw new IllegalStateException("Inconsistent block id.");
         }
         // uid can be anything.
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
            throw new IllegalStateException("Invalid type: " + type);
         }
         // creator can be anything.
         // entrypoint can be anything.
         // stackSize can be anything.
         // supervisorStackSize can be anything.
         // timeSlice can be anything.
         // vector can be anything.
         // executionUnit can be anything.
         // supervisor can be anything.
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
         // numSignalsAttached can be anything.
         // numSignalsOwned can be anything.
         // semaphoreValue can be anything.
         // errorHandler can be anything.
         // properties can be anything.
         if (sigselect == null)
         {
            throw new IllegalStateException("Signal select is null.");
         }
         if (fileName == null)
         {
            throw new IllegalStateException("Filename is null.");
         }
         if ((fileName.equals("") && (lineNumber != 0)) ||
             (!fileName.equals("") && (lineNumber == 0)))
         {
            throw new IllegalStateException("Inconsistent filename / line number.");
         }
         if (envVars == null)
         {
            throw new IllegalStateException("Environment variables is null.");
         }
         if (signalQueue == null)
         {
            throw new IllegalStateException("Signal queue is null.");
         }
         if (signalQueueInitialized && (numSignalsInQueue != signalQueue.size()))
         {
            throw new IllegalStateException("Inconsistent signal queue size.");
         }
      }
   }

   /**
    * Return a string representation of this process object. The returned
    * string is of the form "&lt;process-name&gt; (&lt;process-id&gt;)", where
    * process-id is in hexadecimal form.
    *
    * @return a string representation of this process object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final fields, no synchronization needed.
      return (name + " (0x" + Integer.toHexString(getId()).toUpperCase() + ")");
   }
}
