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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.Target.MonitorHandler;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorStatus;

/**
 * This class represents an OSE block and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A block has processes as children nodes and a segment or target as parent
 * node. A block can be started, stopped, and killed, and environment variables
 * can be set on it.
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.OseObject
 */
public class Block extends OseObject implements SystemModelNode
{
   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Killed status. */
   private boolean killed;

   /* Target and segment. */
   private final Target target;
   private final Segment segment;

   /* Block info. */
   private final String name;
   private final int sid;
   private final int uid;
   private final boolean supervisor; // Not used
   private final int maxSignalSize;
   private final int signalPoolId;
   private final int stackPoolId;
   private short executionUnit;
   private int numSignalsAttached; // Not used
   private int errorHandler; // Not used

   /* Environment variables. */
   private final ArrayList envVars;
   private final ArrayList tmpEnvVars;
   private boolean envVarsInitialized;

   /* Processes (synchronized list). */
   private final List processes;

   /**
    * Create a new block object.
    *
    * @param target              the target.
    * @param segment             the parent segment.
    * @param name                the block name.
    * @param id                  the block ID.
    * @param sid                 the parent segment ID.
    * @param uid                 the user number.
    * @param supervisor          supervisor mode or not.
    * @param maxSignalSize       the max signal size.
    * @param signalPoolId        the signal pool ID.
    * @param stackPoolId         the stack pool ID.
    * @param executionUnit       the execution unit.
    * @param numSignalsAttached  the number of attached signals.
    * @param errorHandler        the error handler address.
    */
   Block(Target target,
         Segment segment,
         String name,
         int id,
         int sid,
         int uid,
         boolean supervisor,
         int maxSignalSize,
         int signalPoolId,
         int stackPoolId,
         short executionUnit,
         int numSignalsAttached,
         int errorHandler)
   {
      super(id);
      if ((target == null) ||
          (target.hasExtendedSegmentSupport() && (segment == null)) ||
          (name == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.segment = segment;
      this.name = name;
      this.sid = sid;
      this.uid = uid;
      this.supervisor = supervisor;
      this.maxSignalSize = maxSignalSize;
      this.signalPoolId = signalPoolId;
      this.stackPoolId = stackPoolId;
      this.executionUnit = executionUnit;
      this.numSignalsAttached = numSignalsAttached;
      this.errorHandler = errorHandler;
      envVars = new ArrayList();
      tmpEnvVars = new ArrayList();
      processes = Collections.synchronizedList(new ArrayList());
   }

   /**
    * Change the mutable properties of this block.
    *
    * @param executionUnit       the execution unit.
    * @param numSignalsAttached  the number of attached signals.
    * @param errorHandler        the error handler address.
    * @return true if any of the mutable properties of this block were changed,
    * false otherwise.
    */
   boolean change(short executionUnit, int numSignalsAttached, int errorHandler)
   {
      synchronized (lock)
      {
         boolean changed = ((this.executionUnit != executionUnit) ||
                            (this.numSignalsAttached != numSignalsAttached) ||
                            (this.errorHandler != errorHandler));
         this.executionUnit = executionUnit;
         this.numSignalsAttached = numSignalsAttached;
         this.errorHandler = errorHandler;
         return changed;
      }
   }

   /**
    * Add an environment variable to this block.
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
    * Commit all environment variables added to this block with the addEnvVar()
    * method.
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
    * Add a process to this block.
    *
    * @param process  the process to add.
    */
   void addProcess(Process process)
   {
      processes.add(process);
   }

   /**
    * Remove a process from this block.
    *
    * @param process  the process to remove.
    */
   void removeProcess(Process process)
   {
      processes.remove(process);
   }

   /**
    * Determine whether this block is killed or not.
    *
    * @return true if this block is killed, false otherwise.
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
    * Mark this block and all its processes as killed.
    *
    * @see com.ose.system.OseObject#setKilled()
    */
   void setKilled()
   {
      setKilled(true);
   }

   /**
    * Mark this block and all its processes as killed.
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
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         process.setKilled(false);
      }
      SystemModel.getInstance().fireNodesChanged(this, processes);
      if (self)
      {
         SystemModel.getInstance().fireNodesChanged(getParent(),
               Collections.singletonList(this));
      }
   }

   /**
    * Determine whether all of the given immutable block properties are
    * identical to the ones of this block.
    *
    * @param id                  ID of a block.
    * @param name                name of a block.
    * @param sid                 parent segment ID of a block.
    * @param uid                 user number of a block.
    * @param supervisor          supervisor mode flag of a block.
    * @param maxSignalSize       max signal size of a block.
    * @param signalPoolId        signal pool ID of a block.
    * @param stackPoolId         stack pool ID of a block.
    * @return true if all of the given immutable block properties matches this
    * block, false otherwise.
    */
   boolean matches(int id,
                   String name,
                   int sid,
                   int uid,
                   boolean supervisor,
                   int maxSignalSize,
                   int signalPoolId,
                   int stackPoolId)
   {
      // Final fields, no synchronization needed.
      boolean result = false;
      result = ((getId() == id) &&
                (this.name.equals(name)) &&
                (this.sid == sid) &&
                (this.uid == uid) &&
                (this.supervisor == supervisor) &&
                (this.maxSignalSize == maxSignalSize) &&
                (this.signalPoolId == signalPoolId) &&
                (this.stackPoolId == stackPoolId));
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
    * Return the parent segment object or null if this target does not support
    * segments.
    *
    * @return the parent segment object or null if this target does not support
    * segments.
    */
   public Segment getSegment()
   {
      // Final field, no synchronization needed.
      return segment;
   }

   /**
    * Return the name of this block.
    *
    * @return the name of this block.
    */
   public String getName()
   {
      // Final field, no synchronization needed.
      return name;
   }

   /**
    * Return the parent segment ID of this block.
    *
    * @return the parent segment ID of this block.
    */
   public int getSid()
   {
      // Final field, no synchronization needed.
      return sid;
   }

   /**
    * Return the user number of this block.
    *
    * @return the user number of this block.
    */
   public int getUid()
   {
      // Final field, no synchronization needed.
      return uid;
   }

   /**
    * Determine whether this block is a supervisor mode block or not.
    *
    * @return true if this block is a supervisor mode block, false othetwise.
    */
   public boolean isSupervisor()
   {
      // Final field, no synchronization needed.
      return supervisor;
   }

   /**
    * Return the maximum signal size, in bytes, that can be allocated by a
    * process in this block.
    *
    * @return the maximum signal size, in bytes, that can be allocated by a
    * process in this block.
    */
   public int getMaxSignalSize()
   {
      // Final field, no synchronization needed.
      return maxSignalSize;
   }

   /**
    * Return the ID of the pool used as signal pool by this block.
    *
    * @return the ID of the pool used as signal pool by this block.
    */
   public int getSignalPoolId()
   {
      // Final field, no synchronization needed.
      return signalPoolId;
   }

   /**
    * Return the ID of the pool used as stack pool by this block.
    *
    * @return the ID of the pool used as stack pool by this block.
    */
   public int getStackPoolId()
   {
      // Final field, no synchronization needed.
      return stackPoolId;
   }

   /**
    * Return the execution unit this block is bound to.
    *
    * @return the execution unit this block is bound to.
    */
   public short getExecutionUnit()
   {
      synchronized (lock)
      {
         return executionUnit;
      }
   }

   /**
    * Return the number of signals attached to this block.
    *
    * @return the number of signals attached to this block.
    */
   public int getNumSignalsAttached()
   {
      synchronized (lock)
      {
         return numSignalsAttached;
      }
   }

   /**
    * Return the error handler address of this block.
    *
    * @return the error handler address of this block.
    */
   public int getErrorHandler()
   {
      synchronized (lock)
      {
         return errorHandler;
      }
   }

   /**
    * Return an array of the environment variables set on this block (never
    * null). A new array is created and returned for each call.
    *
    * Note that the environment variables of a block are lazily initialized and
    * will only be available if they have been initialized.
    *
    * @return an array of the environment variables set on this block (never
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
    * Return an array of the processes in this block (never null). A new array
    * is created and returned for each call.
    *
    * @return an array of the processes in this block (never null).
    */
   public Process[] getProcesses()
   {
      return (Process[]) processes.toArray(new Process[0]);
   }

   /**
    * Return the internal list of the processes in this block (never null).
    *
    * @return the internal list of the processes in this block (never null).
    * @see Process
    */
   List getProcessList()
   {
      return processes;
   }

   /**
    * Return the parent object. If this target supports segments, the parent
    * object is a segment, otherwise it is a target object.
    *
    * @return the parent object.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return (target.hasExtendedSegmentSupport() ?
            getSegment() : (SystemModelNode) getTarget());
   }

   /**
    * Return an array of the children processes in this block. A new array is
    * created and returned for each call.
    *
    * @return an array of the children processes in this block.
    * @see com.ose.system.SystemModelNode#getChildren()
    * @see Process
    */
   public SystemModelNode[] getChildren()
   {
      return getProcesses();
   }

   /**
    * Determine whether this node is a leaf node or not. A block object is not a
    * leaf node and always returns false.
    *
    * @return false since a block object is not a leaf node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return false;
   }

   /**
    * If the lazily initialized environment variables of this block have not
    * been initialized, initialize them and update the other properties of this
    * block for consistency.
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
         if (!isKilled() && target.hasGetEnvVarSupport() && !envVarsInitialized)
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();

            monitorHandler.getBlockInfoRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
            monitorHandler.getEnvVarsRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
            envVarsInitialized = true;
         }
      }
   }

   /**
    * Update this block and its processes. The lazily initialized environment
    * variables of this block will only be updated if they have been initialized.
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

            monitorHandler.getBlockInfoRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
            if (target.hasGetEnvVarSupport() && envVarsInitialized)
            {
               monitorHandler.getEnvVarsRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_ID, getId());
            }

            monitorHandler.getProcessInfoRequest(progressMonitor, this,
                  1, Monitor.MONITOR_SCOPE_BLOCK_ID, getId());

            for (Iterator i = processes.iterator(); i.hasNext();)
            {
               Process process = (Process) i.next();
               if (!process.isKilled())
               {
                  process.updateLazyProperties(progressMonitor);
               }
            }
         }
      }
   }

   /**
    * If the lazily initialized environment variables of this block have been
    * initialized, update them.
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
         if (!isKilled() && target.hasGetEnvVarSupport() && envVarsInitialized)
         {
            MonitorHandler monitorHandler = target.getMonitorHandler();
            monitorHandler.getEnvVarsRequest(progressMonitor, this,
                  Monitor.MONITOR_SCOPE_ID, getId());
         }
      }
   }

   /**
    * Clean this block, i.e. remove all of its processes marked as killed and
    * remove the block itself if marked as killed.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      synchronized (target)
      {
         if (isKilled())
         {
            if (target.hasExtendedSegmentSupport())
            {
               segment.removeBlock(this);
               SystemModel.getInstance().fireNodesRemoved(segment,
                     Collections.singletonList(this));
            }
            else
            {
               target.removeBlock(this);
               SystemModel.getInstance().fireNodesRemoved(target,
                     Collections.singletonList(this));
            }
         }

         cleanChildren();
      }
   }

   /**
    * Clean this block's processes, i.e. remove all of its processes marked as
    * killed.
    */
   void cleanChildren()
   {
      synchronized (target)
      {
         List removedProcesses = new ArrayList();
         for (Iterator i = processes.iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.isKilled())
            {
               i.remove();
               removedProcesses.add(process);
            }
         }
         if (!removedProcesses.isEmpty())
         {
            SystemModel.getInstance().fireNodesRemoved(this, removedProcesses);
         }
      }
   }

   /**
    * Start this block if possible. If successful, this block is recursivly
    * updated so that itself and its processes are up-to-date.
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
                     Monitor.MONITOR_SCOPE_BLOCK_ID, getId());
               update(progressMonitor, true);
            }
            else
            {
               // Compensate for a missing condition check in the monitor.
               throw new ServiceException(Target.SERVICE_MONITOR_ID,
                  MonitorStatus.MONITOR_STATUS_CANCELLED,
                  "An attempt was made to start a block that is not stopped.");
            }
         }
      }
   }

   /**
    * Determine whether this block can be started or not.
    *
    * @return true if this block can be started, false otherwise.
    */
   boolean isStartable()
   {
      synchronized (processes)
      {
         for (Iterator i = processes.iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (!process.isKilled() && !process.isStartable())
            {
               return false;
            }
         }
      }

      return true;
   }

   /**
    * Stop this block if possible. If successful, this block is recursivly
    * updated so that itself and its processes are up-to-date.
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
                  Monitor.MONITOR_SCOPE_BLOCK_ID, getId());
            update(progressMonitor, true);
         }
      }
   }

   /**
    * Kill this block if possible. If successful, this block is recursivly
    * updated so that itself and its processes are up-to-date.
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
                  Monitor.MONITOR_SCOPE_BLOCK_ID, getId());
            update(progressMonitor, true);
            getParent().update(progressMonitor, false);
         }
      }
   }

   /**
    * Set a string-valued environment variable on this block.
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
    * Set a pointer-valued environment variable on this block.
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
    * Set the execution unit of this block if possible. If successful, this
    * block is recursivly updated so that itself and its processes are
    * up-to-date.
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
    * Check the state of this block object and, if recursive is true, the state
    * of its process objects.
    *
    * @param recursive  whether or not to check the state of this block's
    * processes.
    * @throws IllegalStateException  if this block object is not in a consistent
    * state or, if recursive is true, if any of its process objects is not in a
    * consistent state.
    * @see com.ose.system.SystemModelNode#checkState(boolean)
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      // Final fields, no synchronization needed.

      if (target == null)
      {
         throw new IllegalStateException("Target is null.");
      }
      if (target.hasExtendedSegmentSupport() && (segment == null))
      {
         throw new IllegalStateException("Segment is null.");
      }
      if (name == null)
      {
         throw new IllegalStateException("Name is null.");
      }
      if (target.hasExtendedSegmentSupport() && (sid != segment.getId()))
      {
         throw new IllegalStateException("Inconsistent segment id.");
      }
      // uid can be anything.
      // supervisor can be anything.
      // maxSignalSize can be anything.
      // signalPoolId can be anything.
      // stackPoolId can be anything.
      // executionUnit can be anything.
      // numSignalsAttached can be anything.
      // errorHandler can be anything.
      if (envVars == null)
      {
         throw new IllegalStateException("Environment variables is null.");
      }
      if (processes == null)
      {
         throw new IllegalStateException("Processes is null.");
      }

      if (recursive)
      {
         synchronized (processes)
         {
            for (Iterator i = processes.iterator(); i.hasNext();)
            {
               Process process = (Process) i.next();
               process.checkState(recursive);
            }
         }
      }
   }

   /**
    * Return a string representation of this block object. The returned string
    * is of the form "&lt;block-name&gt; (&lt;block-id&gt;)", where block-id is
    * in hexadecimal form.
    *
    * @return a string representation of this block object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final fields, no synchronization needed.
      return (name + " (0x" + Integer.toHexString(getId()).toUpperCase() + ")");
   }
}
