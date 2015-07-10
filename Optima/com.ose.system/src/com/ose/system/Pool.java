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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo64;

/**
 * This class represents an OSE pool and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A pool has no children nodes and a segment or target as parent node. Signals
 * and stacks of interest in a pool can be found using filters.
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.OseObject
 */
public class Pool extends OseObject implements SystemModelNode
{
   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Killed status. */
   private boolean killed;

   /* Target and segment. */
   private final Target target;
   private final Segment segment;

   /* Pool info. */
   private final int sid; // Only used in OSE 5.2 and later
   private long totalSize;
   private long freeSize;
   private final int stackAlignment;
   private final int stackAdmSize;
   private final int stackInternalAdmSize;
   private final int signalAlignment;
   private final int signalAdmSize;
   private final int signalInternalAdmSize;
   private final ArrayList stackSizes;
   private final ArrayList signalSizes;
   private final ArrayList poolFragments;

   /**
    * Create a new 32 bit pool object.
    *
    * @param target                 the target.
    * @param segment                the parent segment.
    * @param id                     the pool ID.
    * @param sid                    the parent segment ID.
    * @param totalSize              the total size.
    * @param freeSize               the free size.
    * @param stackAlignment         the stack alignment.
    * @param stackAdmSize           the stack admin size.
    * @param stackInternalAdmSize   the stack internal admin size.
    * @param signalAlignment        the signal alignment.
    * @param signalAdmSize          the signal admin size.
    * @param signalInternalAdmSize  the signal internal admin size.
    * @param stackSizes             the stack sizes.
    * @param signalSizes            the signal sizes.
    * @param poolFragments          the pool fragments.
    */
   Pool(Target target,
        Segment segment,
        int id,
        int sid,
        int totalSize,
        int freeSize,
        int stackAlignment,
        int stackAdmSize,
        int stackInternalAdmSize,
        int signalAlignment,
        int signalAdmSize,
        int signalInternalAdmSize,
        MonitorPoolBufferSizeInfo[] stackSizes,
        MonitorPoolBufferSizeInfo[] signalSizes,
        MonitorPoolFragmentInfo[] poolFragments)
   {
      super(id);
      if ((target == null) ||
          (target.hasExtendedSegmentSupport() && (segment == null)) ||
          (stackSizes == null) ||
          (signalSizes == null) ||
          (poolFragments == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.segment = segment;
      this.sid = sid;
      this.totalSize = totalSize;
      this.freeSize = freeSize;
      if (target.isMonitorObsolete())
      {
         /* Known values for OSE 5.1.1 target systems. */
         this.stackAlignment = 32;
         this.stackAdmSize = 0;
         this.stackInternalAdmSize = 1;
         this.signalAlignment = 32;
         this.signalAdmSize = 33;
         this.signalInternalAdmSize = 0;
      }
      else
      {
         this.stackAlignment = stackAlignment;
         this.stackAdmSize = stackAdmSize;
         this.stackInternalAdmSize = stackInternalAdmSize;
         this.signalAlignment = signalAlignment;
         this.signalAdmSize = signalAdmSize;
         this.signalInternalAdmSize = signalInternalAdmSize;
      }
      this.stackSizes = new ArrayList();
      for (int i = 0; i < stackSizes.length; i++)
      {
         PoolBufferSizeInfo stackSize = new PoolBufferSizeInfo(stackSizes[i]);
         this.stackSizes.add(stackSize);
      }
      this.signalSizes = new ArrayList();
      for (int i = 0; i < signalSizes.length; i++)
      {
         PoolBufferSizeInfo signalSize = new PoolBufferSizeInfo(signalSizes[i]);
         this.signalSizes.add(signalSize);
      }
      this.poolFragments = new ArrayList();
      for (int i = 0; i < poolFragments.length; i++)
      {
         PoolFragmentInfo fragment = new PoolFragmentInfo(poolFragments[i]);
         this.poolFragments.add(fragment);
      }
   }
   
   /**
    * Create a new 64 bit pool object.
    *
    * @param target                 the target.
    * @param segment                the parent segment.
    * @param id                     the pool ID.
    * @param sid                    the parent segment ID.
    * @param totalSize              the total size.
    * @param freeSize               the free size.
    * @param stackAlignment         the stack alignment.
    * @param stackAdmSize           the stack admin size.
    * @param stackInternalAdmSize   the stack internal admin size.
    * @param signalAlignment        the signal alignment.
    * @param signalAdmSize          the signal admin size.
    * @param signalInternalAdmSize  the signal internal admin size.
    * @param stackSizes             the stack sizes.
    * @param signalSizes            the signal sizes.
    * @param poolFragments          the pool fragments.
    */
   Pool(Target target,
        Segment segment,
        int id,
        int sid,
        long totalSize,
        long freeSize,
        int stackAlignment,
        int stackAdmSize,
        int stackInternalAdmSize,
        int signalAlignment,
        int signalAdmSize,
        int signalInternalAdmSize,
        MonitorPoolBufferSizeInfo[] stackSizes,
        MonitorPoolBufferSizeInfo[] signalSizes,
        MonitorPoolFragmentInfo64[] poolFragments)
   {
      super(id);
      if ((target == null) ||
          (target.hasExtendedSegmentSupport() && (segment == null)) ||
          (stackSizes == null) ||
          (signalSizes == null) ||
          (poolFragments == null))
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.segment = segment;
      this.sid = sid;
      this.totalSize = totalSize;
      this.freeSize = freeSize;
      if (target.isMonitorObsolete())
      {
         /* Known values for OSE 5.1.1 target systems. */
         this.stackAlignment = 32;
         this.stackAdmSize = 0;
         this.stackInternalAdmSize = 1;
         this.signalAlignment = 32;
         this.signalAdmSize = 33;
         this.signalInternalAdmSize = 0;
      }
      else
      {
         this.stackAlignment = stackAlignment;
         this.stackAdmSize = stackAdmSize;
         this.stackInternalAdmSize = stackInternalAdmSize;
         this.signalAlignment = signalAlignment;
         this.signalAdmSize = signalAdmSize;
         this.signalInternalAdmSize = signalInternalAdmSize;
      }
      this.stackSizes = new ArrayList();
      for (int i = 0; i < stackSizes.length; i++)
      {
         PoolBufferSizeInfo stackSize = new PoolBufferSizeInfo(stackSizes[i]);
         this.stackSizes.add(stackSize);
      }
      this.signalSizes = new ArrayList();
      for (int i = 0; i < signalSizes.length; i++)
      {
         PoolBufferSizeInfo signalSize = new PoolBufferSizeInfo(signalSizes[i]);
         this.signalSizes.add(signalSize);
      }
      this.poolFragments = new ArrayList();
      for (int i = 0; i < poolFragments.length; i++)
      {
         PoolFragmentInfo fragment = new PoolFragmentInfo(poolFragments[i]);
         this.poolFragments.add(fragment);
      }
   }

   /**
    * Change the mutable properties of this pool 32 bit.
    *
    * @param totalSize      the total size.
    * @param freeSize       the free size.
    * @param stackSizes     the stack sizes.
    * @param signalSizes    the signal sizes.
    * @param poolFragments  the pool fragments.
    * @return true if any of the mutable properties of this pool were changed,
    * false otherwise.
    */
   boolean change(int totalSize,
                  int freeSize,
                  MonitorPoolBufferSizeInfo[] stackSizes,
                  MonitorPoolBufferSizeInfo[] signalSizes,
                  MonitorPoolFragmentInfo[] poolFragments)
   {
      synchronized (lock)
      {
         // Always treat the pool as being
         // changed when it is updated.
         boolean changed = true;
         // FIXME: Determine if really changed.
         //boolean changed = ((this.totalSize != totalSize) ||
         //                   (this.freeSize != freeSize) || ...);
         this.totalSize = totalSize;
         this.freeSize = freeSize;
         this.stackSizes.clear();
         for (int i = 0; i < stackSizes.length; i++)
         {
            PoolBufferSizeInfo stackSize = new PoolBufferSizeInfo(stackSizes[i]);
            this.stackSizes.add(stackSize);
         }
         this.stackSizes.trimToSize();
         this.signalSizes.clear();
         for (int i = 0; i < signalSizes.length; i++)
         {
            PoolBufferSizeInfo signalSize = new PoolBufferSizeInfo(signalSizes[i]);
            this.signalSizes.add(signalSize);
         }
         this.signalSizes.trimToSize();
         this.poolFragments.clear();
         for (int i = 0; i < poolFragments.length; i++)
         {
            PoolFragmentInfo fragment = new PoolFragmentInfo(poolFragments[i]);
            this.poolFragments.add(fragment);
         }
         this.poolFragments.trimToSize();
         return changed;
      }
   }
   
   /**
    * Change the mutable properties of this pool 64 bit.
    *
    * @param totalSize      the total size.
    * @param freeSize       the free size.
    * @param stackSizes     the stack sizes.
    * @param signalSizes    the signal sizes.
    * @param poolFragments  the pool fragments.
    * @return true if any of the mutable properties of this pool were changed,
    * false otherwise.
    */
   boolean change(long totalSize,
                  long freeSize,
                  MonitorPoolBufferSizeInfo[] stackSizes,
                  MonitorPoolBufferSizeInfo[] signalSizes,
                  MonitorPoolFragmentInfo64[] poolFragments)
   {
      synchronized (lock)
      {
         // Always treat the pool as being
         // changed when it is updated.
         boolean changed = true;
         // FIXME: Determine if really changed.
         //boolean changed = ((this.totalSize != totalSize) ||
         //                   (this.freeSize != freeSize) || ...);
         this.totalSize = totalSize;
         this.freeSize = freeSize;
         this.stackSizes.clear();
         for (int i = 0; i < stackSizes.length; i++)
         {
            PoolBufferSizeInfo stackSize = new PoolBufferSizeInfo(stackSizes[i]);
            this.stackSizes.add(stackSize);
         }
         this.stackSizes.trimToSize();
         this.signalSizes.clear();
         for (int i = 0; i < signalSizes.length; i++)
         {
            PoolBufferSizeInfo signalSize = new PoolBufferSizeInfo(signalSizes[i]);
            this.signalSizes.add(signalSize);
         }
         this.signalSizes.trimToSize();
         this.poolFragments.clear();
         for (int i = 0; i < poolFragments.length; i++)
         {
            PoolFragmentInfo fragment = new PoolFragmentInfo(poolFragments[i]);
            this.poolFragments.add(fragment);
         }
         this.poolFragments.trimToSize();
         return changed;
      }
   }

   /**
    * Determine whether this pool is killed or not.
    *
    * @return true if this pool is killed, false otherwise.
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
    * Mark this pool as killed.
    *
    * @see com.ose.system.OseObject#setKilled()
    */
   void setKilled()
   {
      setKilled(true);
   }

   /**
    * Mark this pool as killed.
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
      target.removePoolId(getId());
      if (self)
      {
         SystemModel.getInstance().fireNodesChanged(getParent(),
               Collections.singletonList(this));
      }
   }

   /**
    * Determine whether all of the given immutable pool properties are identical
    * to the ones of this pool.
    *
    * @param id   ID of a pool.
    * @param sid  parent segment ID of a pool.
    * @return true if all of the given immutable pool properties matches this
    * pool, false otherwise.
    */
   boolean matches(int id, int sid)
   {
      synchronized (lock)
      {
         boolean result = false;
         // FIXME: The stackAlignment, stackAdmSize, stackInternalAdmSize,
         // signalAlignment, signalAdmSize, and signalInternalAdmSize fields
         // should also be tested if they are supported by the OSE monitor.
         result = ((getId() == id) && (this.sid == sid));
         return result;
      }
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
    * Return the parent segment ID of this pool.
    *
    * @return the parent segment ID of this pool.
    */
   public int getSid()
   {
      // Final field, no synchronization needed.
      return sid;
   }

   /**
    * Return the total size of this pool in bytes.
    *
    * @return the total size of this pool in bytes.
    */
   public long getTotalSize()
   {
      synchronized (lock)
      {
         return totalSize;
      }
   }

   /**
    * Return the free size of this pool in bytes.
    *
    * @return the free size of this pool in bytes.
    */
   public long getFreeSize()
   {
      synchronized (lock)
      {
         return freeSize;
      }
   }

   /**
    * Return the alignment size (in bytes) of stacks in this pool.
    * The configured stack buffer size plus the stack administration size must
    * be a multiple of this size.
    *
    * @return the alignment size (in bytes) of stacks in this pool.
    */
   public int getStackAlignment()
   {
      // Final field, no synchronization needed.
      return stackAlignment;
   }

   /**
    * Return the stack administration size, i.e. the difference (in bytes)
    * between the actual stack buffer size and the corresponding configured
    * stack buffer size in this pool.
    *
    * @return the difference (in bytes) between the actual stack buffer size and
    * the corresponding configured stack buffer size in this pool.
    */
   public int getStackAdmSize()
   {
      // Final field, no synchronization needed.
      return stackAdmSize;
   }

   /**
    * Return the internal stack administration size, i.e. the difference
    * (in bytes) between the runtime-requested stack size and the application-
    * accessible stack size in this pool.
    *
    * @return the difference (in bytes) between the runtime-requested stack size
    * and the application-accessible stack size in this pool.
    */
   public int getStackInternalAdmSize()
   {
      // Final field, no synchronization needed.
      return stackInternalAdmSize;
   }

   /**
    * Return the alignment size (in bytes) of signals in this pool.
    * The configured signal buffer size plus the signal administration size must
    * be a multiple of this size.
    *
    * @return the alignment size (in bytes) of signals in this pool.
    */
   public int getSignalAlignment()
   {
      // Final field, no synchronization needed.
      return signalAlignment;
   }

   /**
    * Return the signal administration size, i.e. the difference (in bytes)
    * between the actual signal buffer size and the corresponding configured
    * signal buffer size in this pool.
    *
    * @return the difference (in bytes) between the actual signal buffer size
    * and the corresponding configured signal buffer size in this pool.
    */
   public int getSignalAdmSize()
   {
      // Final field, no synchronization needed.
      return signalAdmSize;
   }

   /**
    * Return the internal signal administration size, i.e. the difference
    * (in bytes) between the runtime-requested signal size and the application-
    * accessible signal size in this pool.
    *
    * @return the difference (in bytes) between the runtime-requested signal
    * size and the application-accessible signal size in this pool.
    */
   public int getSignalInternalAdmSize()
   {
      // Final field, no synchronization needed.
      return signalInternalAdmSize;
   }

   /**
    * Return an array of the configured stack sizes of this pool. A new array is
    * created and returned for each call.
    *
    * @return an array of the configured stack sizes of this pool.
    */
   public PoolBufferSizeInfo[] getStackSizes()
   {
      synchronized (lock)
      {
         return (PoolBufferSizeInfo[]) stackSizes.toArray(new PoolBufferSizeInfo[0]);
      }
   }

   /**
    * Return an array of the configured signal sizes of this pool. A new array
    * is created and returned for each call.
    *
    * @return an array of the configured signal sizes of this pool.
    */
   public PoolBufferSizeInfo[] getSignalSizes()
   {
      synchronized (lock)
      {
         return (PoolBufferSizeInfo[]) signalSizes.toArray(new PoolBufferSizeInfo[0]);
      }
   }

   /**
    * Return an array of the pool fragments of this pool. A new array is created
    * and returned for each call.
    *
    * @return an array of the pool fragments of this pool.
    */
   public PoolFragmentInfo[] getPoolFragments()
   {
      synchronized (lock)
      {
         return (PoolFragmentInfo[]) poolFragments.toArray(new PoolFragmentInfo[0]);
      }
   }

   /**
    * Return an array of the signals in this pool that match the given signal
    * filter. The result is a snapshot of the pool at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param filter           the signal filter, or null if all signals in the
    * pool should be returned.
    * @return an array of the signals in this pool that match the given signal
    * filter.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public SignalInfo[] getFilteredPoolSignals(IProgressMonitor progressMonitor,
                                              SignalFilter filter)
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
            return target.getMonitorHandler().getFilteredPoolSignalsRequest(
                  progressMonitor, Monitor.MONITOR_SCOPE_ID, getId(),
                  (filter != null ? filter.getTags() : null));
         }
         else
         {
            return new SignalInfo[0];
         }
      }
   }

   /**
    * Return an array of the stacks in this pool that match the given stack
    * filter. The result is a snapshot of the pool at the time of the call.
    *
    * Note: The stack filter is only used in OSE 5.2 and later.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param filter           the stack filter, or null if all stacks in the
    * pool should be returned.
    * @return an array of the stacks in this pool that match the given stack
    * filter.
    * @throws IOException  if an I/O exception occurred; parent target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public StackInfo[] getFilteredPoolStacks(IProgressMonitor progressMonitor,
                                            StackFilter filter)
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
            List list = new ArrayList();
            StackInfo[] stacks;
            Map stackMap = new HashMap(100);

            if (target.hasBlockSupport())
            {
               BlockFilter blockFilter;
               BlockInfo[] blocks;

               blockFilter = new BlockFilter();
               blockFilter.filterStackPoolId(false, getId());
               blocks = target.getMonitorHandler().getFilteredBlockInfoRequest(
                     progressMonitor,
                     Target.SCOPE_SYSTEM,
                     0,
                     blockFilter.getTags());

               for (int i = 0; i < blocks.length; i++)
               {
                  stacks = target.getMonitorHandler().getFilteredStackUsageRequest(
                        progressMonitor,
                        Target.SCOPE_BLOCK_ID,
                        blocks[i].getId(),
                        (filter != null ? filter.getTags() : null));

                  for (int j = 0; j < stacks.length; j++)
                  {
                     StackInfo stack = stacks[j];
                     int stackAddress = stack.getAddress();
                     Integer stackKey = new Integer(stackAddress);
                     if (containsAddress(stackAddress) &&
                         !stackMap.containsKey(stackKey))
                     {
                        list.add(stack);
                        stackMap.put(stackKey, stack);
                     }
                  }
               }
            }
            else
            {
               // FIXME: It is not very efficient to retrieve all stacks in the
               // system and then on the host side exclude those that doesn't
               // belong to this pool. It would be more efficient if we could
               // retrieve only those stacks that belong to a specified pool.
               stacks = target.getMonitorHandler().getFilteredStackUsageRequest(
                     progressMonitor,
                     Target.SCOPE_SYSTEM,
                     0,
                     (filter != null ? filter.getTags() : null));

               for (int i = 0; i < stacks.length; i++)
               {
                  StackInfo stack = stacks[i];
                  int stackAddress = stack.getAddress();
                  Integer stackKey = new Integer(stackAddress);
                  if (containsAddress(stackAddress) &&
                      !stackMap.containsKey(stackKey))
                  {
                     list.add(stack);
                     stackMap.put(stackKey, stack);
                  }
               }
            }

            stacks = (StackInfo[]) list.toArray(new StackInfo[0]);

            if (target.isMonitorObsolete())
            {
               PoolBufferSizeInfo[] stackSizes = getStackSizes();
               for (int i = 0; i < stacks.length; i++)
               {
                  stacks[i].setBufferSize(stackSizes, stackAdmSize);
               }
            }

            return stacks;
         }
         else
         {
            return new StackInfo[0];
         }
      }
   }

   /**
    * Determine whether this pool contains the specified address.
    *
    * @param address  the address to check.
    * @return true if this pool contains the specified address, false otherwise.
    */
   private boolean containsAddress(int address)
   {
      for (Iterator i = poolFragments.iterator(); i.hasNext();)
      {
         PoolFragmentInfo poolFragment = (PoolFragmentInfo) i.next();
         long base = poolFragment.getAddress();
         long size = poolFragment.getSize();
         if ((address >= base) && (address < (base + size)))
         {
            return true;
         }
      }
      return false;
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
    * A pool object has no children and always returns an empty children array.
    *
    * @return an empty array.
    * @see com.ose.system.SystemModelNode#getChildren()
    */
   public SystemModelNode[] getChildren()
   {
      return new SystemModelNode[0];
   }

   /**
    * Determine whether this node is a leaf node or not. A pool object is a leaf
    * node and always returns true.
    *
    * @return true since a pool object is a leaf node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return true;
   }

   /**
    * This method has no effect on a pool since a pool has no lazily initialized
    * properties.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    */
   public void initLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      // Nothing to do.
   }

   /**
    * Update this pool.
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
            target.getMonitorHandler().getPoolInfoRequest(progressMonitor,
                  this, Monitor.MONITOR_SCOPE_ID, getId());
         }
      }
   }

   /**
    * This method has no effect on a pool since a pool has no lazily initialized
    * properties.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred.
    */
   void updateLazyProperties(IProgressMonitor progressMonitor)
      throws IOException
   {
      // Nothing to do.
   }

   /**
    * Clean this pool, i.e. remove it if it is marked as killed.
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
               segment.removePool(this);
               SystemModel.getInstance().fireNodesRemoved(segment,
                     Collections.singletonList(this));
            }
            else
            {
               target.removePool(this);
               SystemModel.getInstance().fireNodesRemoved(target,
                     Collections.singletonList(this));
            }
         }
      }
   }

   /**
    * Check the state of this pool object.
    *
    * @param recursive               not applicable.
    * @throws IllegalStateException  if this pool object is not in a consistent
    * state.
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
         if (target.hasExtendedSegmentSupport() && (segment == null))
         {
            throw new IllegalStateException("Segment is null.");
         }
         if (target.hasExtendedSegmentSupport() && (sid != segment.getId()))
         {
            throw new IllegalStateException("Inconsistent segment id.");
         }
         if (totalSize < freeSize)
         {
            throw new IllegalStateException("Inconsistent total/free size.");
         }
         if ((stackAlignment < 0) || (stackAlignment > 255))
         {
            throw new IllegalStateException(
                  "Invalid stack alignment: " + stackAlignment);
         }
         if ((stackAdmSize < 0) || (stackAdmSize > 255))
         {
            throw new IllegalStateException(
                  "Invalid stack admin size: " + stackAdmSize);
         }
         if ((stackInternalAdmSize < 0) || (stackInternalAdmSize > 255))
         {
            throw new IllegalStateException(
                  "Invalid stack internal admin size: " + stackInternalAdmSize);
         }
         if ((signalAlignment < 0) || (signalAlignment > 255))
         {
            throw new IllegalStateException(
                  "Invalid signal alignment: " + signalAlignment);
         }
         if ((signalAdmSize < 0) || (signalAdmSize > 255))
         {
            throw new IllegalStateException(
                  "Invalid signal admin size: " + signalAdmSize);
         }
         if ((signalInternalAdmSize < 0) || (signalInternalAdmSize > 255))
         {
            throw new IllegalStateException(
                  "Invalid signal internal admin size: " + signalInternalAdmSize);
         }
         if (stackSizes == null)
         {
            throw new IllegalStateException("Stack sizes is null.");
         }
         if (signalSizes == null)
         {
            throw new IllegalStateException("Signal sizes is null.");
         }
         if (poolFragments == null)
         {
            throw new IllegalStateException("Pool fragments is null.");
         }
      }
   }

   /**
    * Return a string representation of this pool object. The returned string
    * is of the form "pool (&lt;pool-id&gt;)", where pool-id is in hexadecimal
    * form.
    *
    * @return a string representation of this pool object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final field, no synchronization needed.
      return ("pool (0x" + Integer.toHexString(getId()).toUpperCase() + ")");
   }
}
