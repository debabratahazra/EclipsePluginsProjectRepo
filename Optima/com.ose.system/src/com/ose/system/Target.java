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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import com.ose.gateway.Gateway;
import com.ose.gateway.Signal;
import com.ose.system.service.monitor.Monitor;
import com.ose.system.service.monitor.MonitorCPUBlockReport;
import com.ose.system.service.monitor.MonitorCPUPriReport;
import com.ose.system.service.monitor.MonitorCPUProcessReport;
import com.ose.system.service.monitor.MonitorCPUReport;
import com.ose.system.service.monitor.MonitorCounterReport;
import com.ose.system.service.monitor.MonitorHeapBufferInfo;
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo;
import com.ose.system.service.monitor.MonitorHeapBufferSizeInfo64;
import com.ose.system.service.monitor.MonitorHeapFragmentInfo;
import com.ose.system.service.monitor.MonitorHeapFragmentInfo64;
import com.ose.system.service.monitor.MonitorListener;
import com.ose.system.service.monitor.MonitorPoolBufferSizeInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo;
import com.ose.system.service.monitor.MonitorPoolFragmentInfo64;
import com.ose.system.service.monitor.MonitorSignalInfo;
import com.ose.system.service.monitor.MonitorStackInfo;
import com.ose.system.service.monitor.MonitorStatus;
import com.ose.system.service.monitor.MonitorTag;
import com.ose.system.service.monitor.MonitorUserReport;
import com.ose.system.service.monitor.signal.MonitorGetAllocTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetBindTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetCreateTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetErrorTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetFreeBufTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetKillTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetLossTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetProcessTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetReceiveTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetResetTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSendTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetSwapTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetTimeoutTraceReply;
import com.ose.system.service.monitor.signal.MonitorGetUserTraceReply;
import com.ose.system.service.pm.ProgramManager;
import com.ose.system.service.pm.ProgramManagerListener;
import com.ose.system.service.pm.ProgramManagerStatus;

/*
 * Multi-Threading Implementation Notes:
 *
 * There are two types of threads in the perspective of the system model:
 * two internal service receiver threads (one monitor receiver thread and one
 * program manager receiver thread for each target) and any number of client
 * threads that use the system model's public API.
 *
 * Adding segments/pools/heaps/blocks/processes to a target:
 * The monitor receiver thread is the only thread that can add segments, pools,
 * heaps, blocks, and processes to a target using the MonitorListener callback
 * methods.
 *
 * Removing segments/pools/heaps/blocks/processes from a target:
 * Any system model client thread, but not the receiver threads, can remove
 * killed segments, pools, heaps, blocks, and processes from a target using the
 * SystemModelNode.clean() method and by connecting a disconnected target using
 * the Target.connect() method.
 *
 * Changing segments/pools/heaps/blocks/processes in a target:
 * The monitor receiver thread is the only thread that can change the properties
 * of segments, pools, heaps, blocks, and processes in a target using the
 * MonitorListener callback methods.
 *
 * Marking segments/pools/heaps/blocks/processes as killed in a target:
 * Segments, pools, heaps, blocks, and processes in a target can be marked as
 * killed by the monitor receiver thread using the MonitorListener callback
 * methods and by any system model client thread using the Target.disconnect()
 * method directly or indirectly via Gate.update() or SystemModel.update().
 *
 * Synchronization:
 * All SystemModelNode implementations have an internal lock for synchronized
 * reading and writing of instance fields. All SystemModelNode implementations
 * store their SystemModelNode children in synchronized lists. All target
 * communicating methods in Target/Segment/Pool/Heap/Block/Process synchronize
 * on the target instance to ensure atomicity of the individual target
 * operations. All target operations are done via (synchronized methods in) a
 * monitor handler or program manager handler. The monitor handler and program
 * manager handler has an internal lock for synchronizing request/reply/endmark
 * target transactions.
 */

// FIXME: Remove the disclaimer about non-public performance counter API in the
// class description below when it is being made public.

/**
 * This class represents an OSE target and is a node in the hierarchical OSE
 * system model tree structure.
 * <p>
 * A target has segments or pools, heaps, and blocks or processes as children
 * nodes and a gate as parent node. A target can be connected to and disconnected
 * from. Blocks and processes of interest in a target can be found using filters.
 * Load modules can be installed and uninstalled on a target, and programs can
 * be created and killed. Post-mortem dumps can be retrieved. Various types of
 * profiling can be performed. Event action points can be used for defining what
 * kind of actions should be taken when certain types of target events occur.
 * Target events can be asynchronously reported and manually retrieved from the
 * event trace.
 * <p>
 * <b>Note: The API for performance counters is NOT yet public and should not be
 * used!</b>
 *
 * @see com.ose.system.SystemModelNode
 * @see com.ose.system.Gate#addTarget(String)
 */
public class Target implements SystemModelNode
{
   /* OS types. */

   /** OSE 5.x OS type. */
   public static final int OS_OSE_5 = Monitor.MONITOR_OS_OSE_5;

   /** OSE 4.x OS type. */
   public static final int OS_OSE_4 = Monitor.MONITOR_OS_OSE_4;

   /** OSE Epsilon OS type. */
   public static final int OS_OSE_EPSILON = Monitor.MONITOR_OS_OSE_EPSILON;

   /** OSE CK OS type. */
   public static final int OS_OSE_CK = Monitor.MONITOR_OS_OSE_CK;

   /** Unknown OS type. */
   public static final int OS_UNKNOWN = Monitor.MONITOR_OS_UNKNOWN;

   /* CPU types. */

   /** PowerPC CPU type. */
   public static final int CPU_PPC = Monitor.MONITOR_CPU_PPC;

   /** Motorola 68K CPU type. */
   public static final int CPU_M68K = Monitor.MONITOR_CPU_M68K;

   /** ARM CPU type. */
   public static final int CPU_ARM = Monitor.MONITOR_CPU_ARM;

   /** MIPS CPU type. */
   public static final int CPU_MIPS = Monitor.MONITOR_CPU_MIPS;

   /** Intel x86 CPU type. */
   public static final int CPU_X86 = Monitor.MONITOR_CPU_X86;

   /** SPARC CPU type. */
   public static final int CPU_SPARC = Monitor.MONITOR_CPU_SPARC;

   /** TMS320c64x CPU type. */
   public static final int CPU_C64X = Monitor.MONITOR_CPU_C64X;

   /** TMS320c64x+ CPU type. */
   public static final int CPU_C64XP = Monitor.MONITOR_CPU_C64XP;

   /** SP26xx CPU type. */
   public static final int CPU_SP26XX = Monitor.MONITOR_CPU_SP26XX;

   /** SP27xx CPU type. */
   public static final int CPU_SP27XX = Monitor.MONITOR_CPU_SP27XX;

   /** Ceva-TL3 CPU type. */
   public static final int CPU_TL3 = Monitor.MONITOR_CPU_TL3;

   /** Ceva-X CPU type. */
   public static final int CPU_CEVAX = Monitor.MONITOR_CPU_CEVAX;

   /** MSC8144 CPU type. */
   public static final int CPU_M8144 = Monitor.MONITOR_CPU_M8144;

   /** MSC8156 CPU type. */
   public static final int CPU_M8156 = Monitor.MONITOR_CPU_M8156;

   /** TMS320c66x CPU type. */
   public static final int CPU_C66X = Monitor.MONITOR_CPU_C66X;

   /** TMS320c674x CPU type. */
   public static final int CPU_C674X = Monitor.MONITOR_CPU_C674X;

   /** Unknown CPU type. */
   public static final int CPU_UNKNOWN = Monitor.MONITOR_CPU_UNKNOWN;

   /* Scope types. */

   /** Scope type for all processes/blocks/heaps/pools/segments in the target system. */
   public static final int SCOPE_SYSTEM = Monitor.MONITOR_SCOPE_SYSTEM;

   /** Scope type for all processes/blocks/heaps/pools in the specified segment. */
   public static final int SCOPE_SEGMENT_ID = Monitor.MONITOR_SCOPE_SEGMENT_ID;

   /** Scope type for all processes in the specified block. */
   public static final int SCOPE_BLOCK_ID = Monitor.MONITOR_SCOPE_BLOCK_ID;

   /** Scope type for the specified process/block/heap/pool/segment. */
   public static final int SCOPE_ID = Monitor.MONITOR_SCOPE_ID;

   /** Value representing all execution units when profiling. */
   public static final short ALL_EXECUTION_UNITS =
      Monitor.MONITOR_ALL_EXECUTION_UNITS;

   /* Service ids. */

   /** Monitor service ID. */
   public static final String SERVICE_MONITOR_ID =
      "com.ose.system.service.monitor.Monitor";

   /** Program Manager service ID. */
   public static final String SERVICE_PROGRAM_MANAGER_ID =
      "com.ose.system.service.pm.ProgramManager";

   /* Sender gateway client name. */
   private static final String SENDER_PROXY_CLIENT_NAME = "ose_tools_sender_proxy";

   /* Internal field read/write lock. */
   private final Object lock = new Object();

   /* Connection status. */
   private volatile boolean connected;
   private final Object connectionLock = new Object();

   /* Attachment status. */
   private volatile boolean attached;

   /* Misc state. */
   private final Gate gate;
   private final String huntPath;
   private final Gateway senderGateway;
   private final Monitor monitor;
   private final MonitorHandler monitorHandler;
   private final ProgramManager pm;
   private final ProgramManagerHandler pmHandler;

   /* Target info. */
   private final String name;
   private final String extendedName;
   private final boolean bigEndian;
   private final int osType;
   private final int cpuType;
   private volatile short numExecutionUnits;
   private volatile int tickLength;
   private volatile int ntickFrequency;

   /* System parameters. */
   private final ArrayList sysParams;
   private final ArrayList tmpSysParams;

   /* Id-to-OseObject (segment/block/process) mapping
    * (synchronized map), only non-killed objects.
    */
   private final Map osedb;

   /* Id-to-Pool mapping (synchronized map), only non-killed pools. */
   private final Map pooldb;

   /* Id-to-Heap mapping (synchronized map), only non-killed heaps. */
   private final Map heapdb;

   /* Segments (synchronized list).
    * Only used if the target supports segments.
    */
   private final List segments;

   /* Pools (synchronized list).
    * Only used if the target does not support segments.
    */
   private final List pools;

   /* Heaps (synchronized list).
    * Only used if the target does not support segments.
    */
   private final List heaps;

   /* Blocks (synchronized list).
    * Only used if the target supports blocks but not segments.
    */
   private final List blocks;

   /* Processes (synchronized list).
    * Only used if the target does not support blocks.
    */
   private final List processes;

   /* Performance counter report listener list. */
   private final EventListenerList reportListenerList;

   /* Target event listener list. */
   private final EventListenerList eventListenerList;

   /* Process cache for event notification. */
   private volatile ProcessInfoCache notifyProcessCache;

   /* Process cache for event trace. */
   private volatile ProcessInfoCache traceProcessCache;

   /**
    * Create a new target object that is accessible from the specified gate
    * using the specified link handler hunt path prefix. Use an empty hunt path
    * string if the target runs on the same CPU core as the gate.
    * <p>
    * This constructor will also create the common sender gateway used by this
    * target's service proxies, create this target's monitor service proxy and,
    * if available, create this target's program manager service proxy.
    *
    * @param gate          the parent gate.
    * @param huntPath      the target link handler hunt path prefix, i.e.
    * link-name-1/link-name-2/.../link-name-n or an empty string.
    * @throws IOException  if an I/O exception occurred, e.g. the target has no
    * OSE monitor or it is not responding due to a communication error.
    */
   Target(Gate gate, String huntPath) throws IOException
   {
      if (gate == null)
      {
         throw new NullPointerException();
      }
      if (huntPath == null)
      {
         throw new NullPointerException();
      }

      this.gate = gate;
      this.huntPath = huntPath.trim();
      senderGateway = new Gateway(gate.getName(), gate.getAddress(), gate.getPort());

      try
      {
         ProgramManagerHandler tmpPmHandler = null;
         ProgramManager tmpPm = null;
         String systemName;

         // Create the common sender gateway.
         senderGateway.connect(SENDER_PROXY_CLIENT_NAME);

         // Create the monitor.
         // The monitor will make the sender gateway switch to target byte order.
         monitorHandler = new MonitorHandler();
         monitor = new Monitor(monitorHandler, senderGateway, huntPath);

         // If available, create the program manager.
         if ((monitor.getOsType() == OS_OSE_5) || (monitor.getOsType() == OS_OSE_4))
         {
            try
            {
               tmpPmHandler = new ProgramManagerHandler();
               tmpPm = new ProgramManager(tmpPmHandler, senderGateway, huntPath);
            }
            catch (IOException e)
            {
               tmpPmHandler = null;
               tmpPm = null;
            }
         }
         pmHandler = tmpPmHandler;
         pm = tmpPm;

         // Construct the target name.
         systemName = monitor.getSystemName();
         if (systemName != null)
         {
            systemName = systemName.trim();
         }
         if ((systemName == null) || (systemName.length() == 0))
         {
            systemName = "nameless";
         }

         // Initialize the target object.
         name = systemName;
         extendedName = systemName + " (" + gate.getAddress().getHostAddress() +
            ":" + gate.getPort() + "/" + this.huntPath + ")";
         bigEndian = monitor.isBigEndian();
         osType = monitor.getOsType();
         cpuType = monitor.getCpuClass();
         sysParams = new ArrayList();
         tmpSysParams = new ArrayList();
         osedb = Collections.synchronizedMap(new HashMap(64));
         pooldb = Collections.synchronizedMap(new HashMap());
         heapdb = Collections.synchronizedMap(new HashMap());
         segments = Collections.synchronizedList(new ArrayList());
         pools = Collections.synchronizedList(new ArrayList());
         heaps = Collections.synchronizedList(new ArrayList());
         blocks = Collections.synchronizedList(new ArrayList());
         processes = Collections.synchronizedList(new ArrayList());
         reportListenerList = new EventListenerList();
         eventListenerList = new EventListenerList();
      }
      finally
      {
         try
         {
            if (senderGateway.isConnected())
            {
               senderGateway.disconnect();
            }
         }
         catch (IOException ignore) {}
      }
   }

   /**
    * Add a system parameter to this target.
    *
    * @param name   the name of the system parameter.
    * @param value  the value of the system parameter.
    * @see #commitSysParams()
    */
   void addSysParam(String name, String value)
   {
      synchronized (lock)
      {
         tmpSysParams.add(new Parameter(name, value));
      }
   }

   /**
    * Commit all system parameters added to this target with the addSysParam()
    * method.
    *
    * @return true if any system parameter was changed, added, or removed;
    * false otherwise.
    * @see #addSysParam(String, String)
    */
   boolean commitSysParams()
   {
      synchronized (lock)
      {
         boolean changed = !sysParams.equals(tmpSysParams);
         if (changed)
         {
            sysParams.clear();
            sysParams.addAll(tmpSysParams);
            sysParams.trimToSize();
         }
         tmpSysParams.clear();
         tmpSysParams.trimToSize();
         return changed;
      }
   }

   /**
    * Return the OSE object (segment/block/process) corresponding to the
    * specified ID.
    *
    * @param id  the OSE object ID.
    * @return the OSE object corresponding to the specified ID, or null if there
    * is no mapping for the specified ID.
    */
   Object getId(int id)
   {
      return osedb.get(new Integer(id));
   }

   /**
    * Add a mapping for the specified ID and its corresponding OSE object
    * (segment/block/process).
    *
    * @param id      the ID of the OSE object.
    * @param object  the OSE object.
    */
   private void putId(int id, Object object)
   {
      osedb.put(new Integer(id), object);
   }

   /**
    * Remove the mapping for the specified OSE object ID.
    *
    * @param id  the ID of the OSE object.
    */
   void removeId(int id)
   {
      osedb.remove(new Integer(id));
   }

   /**
    * Return the OSE pool corresponding to the specified ID.
    *
    * @param id  the OSE pool ID.
    * @return the OSE pool corresponding to the specified ID, or null if there
    * is no mapping for the specified ID.
    */
   private Pool getPoolId(int id)
   {
      return (Pool) pooldb.get(new Integer(id));
   }

   /**
    * Add a mapping for the specified ID and its corresponding OSE pool.
    *
    * @param id    the ID of the OSE pool.
    * @param pool  the OSE pool.
    */
   private void putPoolId(int id, Pool pool)
   {
      pooldb.put(new Integer(id), pool);
   }

   /**
    * Remove the mapping for the specified OSE pool ID.
    *
    * @param id  the ID of the OSE pool.
    */
   void removePoolId(int id)
   {
      pooldb.remove(new Integer(id));
   }

   /**
    * Return the OSE heap corresponding to the specified ID.
    *
    * @param id  the OSE heap ID.
    * @return the OSE heap corresponding to the specified ID, or null if there
    * is no mapping for the specified ID.
    */
   private Heap getHeapId(int id)
   {
      return (Heap) heapdb.get(new Integer(id));
   }

   /**
    * Add a mapping for the specified ID and its corresponding OSE heap.
    *
    * @param id    the ID of the OSE heap.
    * @param heap  the OSE heap.
    */
   private void putHeapId(int id, Heap heap)
   {
      heapdb.put(new Integer(id), heap);
   }

   /**
    * Remove the mapping for the specified OSE heap ID.
    *
    * @param id  the ID of the OSE heap.
    */
   void removeHeapId(int id)
   {
      heapdb.remove(new Integer(id));
   }

   /**
    * Add a segment to this target.
    *
    * @param segment  the segment to add.
    */
   private void addSegment(Segment segment)
   {
      segments.add(segment);
   }

   /**
    * Remove a segment from this target.
    *
    * @param segment  the segment to remove.
    */
   void removeSegment(Segment segment)
   {
      segments.remove(segment);
   }

   /**
    * Add a pool to this target.
    *
    * @param pool  the pool to add.
    */
   private void addPool(Pool pool)
   {
      pools.add(pool);
   }

   /**
    * Remove a pool from this target.
    *
    * @param pool  the pool to remove.
    */
   void removePool(Pool pool)
   {
      pools.remove(pool);
   }

   /**
    * Add a heap to this target.
    *
    * @param heap  the heap to add.
    */
   private void addHeap(Heap heap)
   {
      heaps.add(heap);
   }

   /**
    * Remove a heap from this target.
    *
    * @param heap  the heap to remove.
    */
   void removeHeap(Heap heap)
   {
      heaps.remove(heap);
   }

   /**
    * Add a block to this target.
    *
    * @param block  the block to add.
    */
   private void addBlock(Block block)
   {
      blocks.add(block);
   }

   /**
    * Remove a block from this target.
    *
    * @param block  the block to remove.
    */
   void removeBlock(Block block)
   {
      blocks.remove(block);
   }

   /**
    * Add a process to this target.
    *
    * @param process  the process to add.
    */
   private void addProcess(Process process)
   {
      processes.add(process);
   }

   /**
    * Remove a process from this target.
    *
    * @param process  the process to remove.
    */
   void removeProcess(Process process)
   {
      processes.remove(process);
   }

   /**
    * Determine whether the specified link handler hunt path prefix matches the
    * link handler hunt path prefix of this target or not.
    *
    * @param huntPath  a link handler hunt path prefix.
    * @return true if the specified link handler hunt path prefix matches the
    * link handler hunt path prefix of this target, false otherwise.
    */
   boolean matches(String huntPath)
   {
      // Final field, no synchronization needed.
      return this.huntPath.equals(huntPath);
   }

   /**
    * Connect to this target if it is disconnected and, if successful, clear its
    * state in the system model. As a convenience, this method will first try to
    * connect to this target's gate if it is disconnected and then, if
    * successful, try to connect to this target.
    * <p>
    * Implementation note: Connecting to a target means connecting to the common
    * sender gateway used by the target's service proxies and connecting to the
    * target's monitor service proxy.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException     if an I/O exception occurred, e.g. the target has
    * no operative OSE monitor or it is not responding due to a communication
    * error.
    */
   public void connect(IProgressMonitor progressMonitor) throws IOException
   {
      if (gate.isKilled())
      {
         gate.connect(progressMonitor);
         if (gate.isKilled())
         {
            return;
         }
      }

      synchronized (connectionLock)
      {
         if (!connected)
         {
            senderGateway.connect(SENDER_PROXY_CLIENT_NAME);
            senderGateway.setBigEndian(bigEndian);
            try
            {
               monitor.connect();
               numExecutionUnits = ((monitor.getNumExecutionUnits() == 0) ?
                     1 : monitor.getNumExecutionUnits());
               tickLength = monitor.getTickLength();
               ntickFrequency = monitor.getNTickFrequency();
               cleanChildren();
               // Clear manually just in case.
               osedb.clear();
               pooldb.clear();
               heapdb.clear();
               segments.clear();
               pools.clear();
               heaps.clear();
               blocks.clear();
               processes.clear();
               connected = true;
               SystemModel.getInstance().fireNodesChanged(gate,
                     Collections.singletonList(this));
            }
            catch (IOException e)
            {
               try
               {
                  senderGateway.disconnect();
               }
               catch (IOException ignore) {}
               throw e;
            }
         }
      }
   }

   /**
    * Disconnect from this target if it is connected and recursivly mark all its
    * children nodes (segments/pools/heaps/blocks/processes) as killed in the
    * system model.
    * <p>
    * Implementation note: Disconnecting from a target means disconnecting from
    * the target's program manager service proxy (if available), disconnecting
    * from the target's monitor service proxy, and disconnecting from the common
    * sender gateway used by the target's service proxies.
    */
   public void disconnect()
   {
      synchronized (connectionLock)
      {
         if (connected)
         {
            connected = false;
            attached = false;
            if (pm != null)
            {
               pm.disconnect();
            }
            monitor.disconnect();
            try
            {
               senderGateway.disconnect();
            }
            catch (IOException e)
            {
               System.out.println("Error disconnecting sender gateway.");
            }
            notifyProcessCache = null;
            traceProcessCache = null;

            if (hasExtendedSegmentSupport())
            {
               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  segment.setKilled(false);
               }
               SystemModel.getInstance().fireNodesChanged(this, segments);
            }
            else
            {
               for (Iterator i = pools.iterator(); i.hasNext();)
               {
                  Pool pool = (Pool) i.next();
                  pool.setKilled(false);
               }
               SystemModel.getInstance().fireNodesChanged(this, pools);
               for (Iterator i = heaps.iterator(); i.hasNext();)
               {
                  Heap heap = (Heap) i.next();
                  heap.setKilled(false);
               }
               SystemModel.getInstance().fireNodesChanged(this, heaps);
               if (hasBlockSupport())
               {
                  for (Iterator i = blocks.iterator(); i.hasNext();)
                  {
                     Block block = (Block) i.next();
                     block.setKilled(false);
                  }
                  SystemModel.getInstance().fireNodesChanged(this, blocks);
               }
               else
               {
                  for (Iterator i = processes.iterator(); i.hasNext();)
                  {
                     Process process = (Process) i.next();
                     process.setKilled(false);
                  }
                  SystemModel.getInstance().fireNodesChanged(this, processes);
               }
            }
            SystemModel.getInstance().fireNodesChanged(gate,
                  Collections.singletonList(this));
         }
      }
   }

   /**
    * Determine whether this target is connected (i.e. not killed) or not.
    *
    * @return true if this target is connected, false otherwise.
    * @see #isKilled()
    */
   public boolean isConnected()
   {
      return connected;
   }

   /**
    * Determine whether this target is killed (i.e. not connected) or not.
    *
    * @return true if this target is killed, false otherwise.
    * @see com.ose.system.SystemModelNode#isKilled()
    * @see #isConnected()
    */
   public boolean isKilled()
   {
      return !connected;
   }

   /**
    * Return the parent gate object.
    *
    * @return the parent gate object.
    */
   public Gate getGate()
   {
      // Final field, no synchronization needed.
      return gate;
   }

   /**
    * Return the link handler hunt path prefix of this target. An empty hunt
    * path string means that the target runs on the same CPU core as its parent
    * gate.
    *
    * @return the link handler hunt path prefix of this target.
    */
   public String getHuntPath()
   {
      // Final field, no synchronization needed.
      return huntPath;
   }

   /**
    * Determine whether this target's monitor is obsolete or not.
    *
    * @return true if this target's monitor is obsolete, false otherwise.
    */
   public boolean isMonitorObsolete()
   {
      return monitor.isObsolete();
   }

   /**
    * Return the monitor handler of this target.
    *
    * @return the monitor handler of this target.
    */
   MonitorHandler getMonitorHandler()
   {
      return monitorHandler;
   }

   /**
    * Return the name of this target.
    *
    * @return the name of this target.
    */
   public String getName()
   {
      // Final field, no synchronization needed.
      return name;
   }

   /**
    * Determine whether this target has big endian byte order or not.
    *
    * @return true if this target has big endian byte order, false if it has
    * little endian byte order.
    */
   public boolean isBigEndian()
   {
      // Final field, no synchronization needed.
      return bigEndian;
   }

   /**
    * Determine whether this target's monitor is a run-mode monitor.
    *
    * @return true if this target's monitor is a run-mode monitor, false
    * otherwise.
    */
   public boolean isRunModeMonitor()
   {
      return monitor.isRunMode();
   }

   /**
    * Determine whether this target's monitor is a freeze-mode monitor.
    *
    * @return true if this target's monitor is a freeze-mode monitor, false
    * otherwise.
    */
   public boolean isFreezeModeMonitor()
   {
      return monitor.isFreezeMode();
   }

   /**
    * Determine whether this target's monitor is a post-mortem monitor.
    *
    * @return true if this target's monitor is a post-mortem monitor, false
    * otherwise.
    */
   public boolean isPostMortemMonitor()
   {
      return monitor.isPostMortem();
   }

   /**
    * Determine whether this target's monitor supports retrieving system
    * parameters.
    *
    * @return true if this target's monitor supports retrieving system
    * parameters, false otherwise.
    */
   public boolean hasGetSysParamSupport()
   {
      return monitor.hasGetSysParamRequest();
   }

   /**
    * Determine whether this target's monitor supports setting system
    * parameters.
    *
    * @return true if this target's monitor supports setting system
    * parameters, false otherwise.
    */
   public boolean hasSetSysParamSupport()
   {
      return monitor.hasSetSysParamRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving segments.
    *
    * @return true if this target's monitor supports retrieving segments, false
    * otherwise.
    */
   public boolean hasSegmentSupport()
   {
      return monitor.hasGetSegmentInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving segments with
    * extended information.
    *
    * @return true if this target's monitor supports retrieving segments with
    * extended information, false otherwise.
    */
   public boolean hasExtendedSegmentSupport()
   {
      return (monitor.hasGetSegmentInfoRequest() &&
              monitor.hasSegmentInfoLongFeature());
   }

   /**
    * Determine whether this target's monitor supports retrieving pools.
    *
    * @return true if this target's monitor supports retrieving pools, false
    * otherwise.
    */
   public boolean hasPoolSupport()
   {
      return monitor.hasGetPoolInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving pool signals.
    *
    * @return true if this target's monitor supports retrieving pool signals,
    * false otherwise.
    */
   public boolean hasPoolSignalSupport()
   {
      return monitor.hasGetPoolSignalsRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving pool stacks.
    *
    * @return true if this target's monitor supports retrieving pool stacks,
    * false otherwise.
    */
   public boolean hasPoolStackSupport()
   {
      return monitor.hasGetStackUsageRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving heaps.
    *
    * @return true if this target's monitor supports retrieving heaps, false
    * otherwise.
    */
   public boolean hasHeapSupport()
   {
      return monitor.hasGetHeapInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving heap
    * fragments.
    *
    * @return true if this target's monitor supports retrieving heap fragments,
    * false otherwise.
    */
   public boolean hasHeapFragmentSupport()
   {
      return monitor.hasGetHeapFragmentInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving heap buffers.
    *
    * @return true if this target's monitor supports retrieving heap buffers,
    * false otherwise.
    */
   public boolean hasHeapBufferSupport()
   {
      return monitor.hasGetHeapBuffersRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving blocks.
    *
    * @return true if this target's monitor supports retrieving blocks, false
    * otherwise.
    */
   public boolean hasBlockSupport()
   {
      return monitor.hasGetBlockInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving processes.
    *
    * @return true if this target's monitor supports retrieving processes, false
    * otherwise.
    */
   public boolean hasProcessSupport()
   {
      return monitor.hasGetProcessInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving memory.
    *
    * @return true if this target's monitor supports retrieving memory, false
    * otherwise.
    */
   public boolean hasMemorySupport()
   {
      return monitor.hasGetMemoryRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving dumps.
    *
    * @return true if this target's monitor supports retrieving dumps, false
    * otherwise.
    */
   public boolean hasDumpSupport()
   {
      return (monitor.hasGetRamdumpInfoRequest() &&
              monitor.hasGetRamdumpRequest());
   }

   /**
    * Determine whether this target's monitor supports retrieving environment
    * variables from blocks or processes.
    *
    * @return true if this target's monitor supports retrieving environment
    * variables from blocks or processes, false otherwise.
    */
   public boolean hasGetEnvVarSupport()
   {
      return monitor.hasGetEnvVarsRequest();
   }

   /**
    * Determine whether this target's monitor supports setting environment
    * variables on blocks or processes.
    *
    * @return true if this target's monitor supports setting environment
    * variables on blocks or processes, false otherwise.
    */
   public boolean hasSetEnvVarSupport()
   {
      return monitor.hasSetEnvVarRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving process signal
    * queues.
    *
    * @return true if this target's monitor supports retrieving process signal
    * queues, false otherwise.
    */
   public boolean hasSignalQueueSupport()
   {
      return monitor.hasGetSignalQueueRequest();
   }

   /**
    * Determine whether this target's monitor supports killing segments, blocks,
    * or processes.
    *
    * @return true if this target's monitor supports killing segments, blocks,
    * or processes, false otherwise.
    */
   public boolean hasKillScopeSupport()
   {
      return monitor.hasKillScopeRequest();
   }

   /**
    * Determine whether this target's monitor supports starting segments,
    * blocks, or processes.
    *
    * @return true if this target's monitor supports starting segments, blocks,
    * or processes, false otherwise.
    */
   public boolean hasStartScopeSupport()
   {
      return monitor.hasStartScopeRequest();
   }

   /**
    * Determine whether this target's monitor supports stopping segments,
    * blocks, or processes.
    *
    * @return true if this target's monitor supports stopping segments, blocks,
    * or processes, false otherwise.
    */
   public boolean hasStopScopeSupport()
   {
      return monitor.hasStopScopeRequest();
   }

   /**
    * Determine whether this target's monitor supports signaling the fast
    * semaphore of processes.
    *
    * @return true if this target's monitor supports signaling the fast
    * semaphore of processes, false otherwise.
    */
   public boolean hasSignalSemaphoreSupport()
   {
      return monitor.hasSignalSemaphoreRequest();
   }

   /**
    * Determine whether this target's monitor supports setting the priority of
    * processes.
    *
    * @return true if this target's monitor supports setting the priority of
    * processes, false otherwise.
    */
   public boolean hasSetPrioritySupport()
   {
      return monitor.hasSetPriorityRequest();
   }

   /**
    * Determine whether this target's monitor supports setting the execution
    * unit of blocks/processes.
    *
    * @return true if this target's monitor supports setting the execution unit
    * of blocks/processes, false otherwise.
    */
   public boolean hasSetExecutionUnitSupport()
   {
      return monitor.hasSetExecutionUnitRequest();
   }

   /**
    * Determine whether this target has a program manager.
    *
    * @return true if this target has a program manager, false otherwise.
    */
   public boolean hasProgramManagerSupport()
   {
      return (pm != null);
   }

   /**
    * Determine whether this target has a program manager that supports
    * retrieving load modules.
    *
    * @return true if this target has a program manager that supports
    * retrieving load modules, false otherwise.
    */
   public boolean hasLoadModuleSupport()
   {
      return ((pm != null) &&
              pm.hasLoadModuleInstallHandlesRequest() &&
              pm.hasLoadModuleInfoRequest());
   }

   /**
    * Determine whether this target has a program manager that supports
    * installing load modules.
    *
    * @return true if this target has a program manager that supports installing
    * load modules, false otherwise.
    */
   public boolean hasInstallLoadModuleSupport()
   {
      return ((pm != null) && pm.hasInstallLoadModuleRequest());
   }

   /**
    * Determine whether this target has a program manager that supports
    * uninstalling load modules.
    *
    * @return true if this target has a program manager that supports
    * uninstalling load modules, false otherwise.
    */
   public boolean hasUninstallLoadModuleSupport()
   {
      return ((pm != null) && pm.hasUninstallLoadModuleRequest());
   }

   /**
    * Determine whether this target has a program manager that supports
    * retrieving programs.
    *
    * @return true if this target has a program manager that supports
    * retrieving programs, false otherwise.
    */
   public boolean hasProgramSupport()
   {
      return ((pm != null) && pm.hasProgramInfoRequest());
   }

   /**
    * Determine whether this target has a program manager that supports creating
    * and starting programs.
    *
    * @return true if this target has a program manager that supports creating
    * and starting programs, false otherwise.
    */
   public boolean hasCreateProgramSupport()
   {
      return ((pm != null) &&
              pm.hasCreateProgramRequest() &&
              pm.hasStartProgramRequest());
   }

   /**
    * Determine whether this target has a program manager that supports killing
    * programs.
    *
    * @return true if this target has a program manager that supports killing
    * programs, false otherwise.
    */
   public boolean hasKillProgramSupport()
   {
      return ((pm != null) && pm.hasKillProgramRequest());
   }

   /**
    * Determine whether this target has a program manager that supports
    * translating a process, block, or segment ID to a program ID.
    *
    * @return true if this target has a program manager that supports
    * translating a process, block, or segment ID to a program ID, false
    * otherwise.
    */
   public boolean hasTranslateProgramIdSupport()
   {
      return ((pm != null) && pm.hasGetProgramPidRequest());
   }

   /**
    * Determine whether this target's monitor supports CPU profiling over all
    * execution units.
    *
    * @return true if this target's monitor supports CPU profiling over all
    * execution units, false otherwise.
    * @see #ALL_EXECUTION_UNITS
    */
   public boolean hasAllExecutionUnitsSupport()
   {
      return monitor.hasProfAllExecutionUnitsFeature();
   }

   /**
    * Determine whether this target's monitor supports per core (execution unit)
    * profiled processes/blocks measurement profiling.
    *
    * @return true if this target's monitor supports per core (execution unit)
    * profiled processes/blocks measurement profiling, false otherwise.
    */
   public boolean hasPerCoreProfiledProcessesSupport()
   {
      return monitor.hasPerCoreProfiledProcessesFeature();
   }

   /**
    * Determine whether this target's monitor supports signal filtering
    * based on number in signal data.
    *
    * @return true if this target's monitor supports signal filtering
    * based on number in signal data.
    */
   public boolean hasSignalDataFilterFeature()
   {
      return monitor.hasSignalDataFilterFeature();
   }

   /**
    * Determine whether this target's monitor supports retrieving CPU load
    * report enablement information and CPU load reports.
    *
    * @return true if this target's monitor supports retrieving CPU load report
    * enablement information and CPU load reports, false otherwise.
    */
   public boolean hasGetCPUReportsSupport()
   {
      return (monitor.hasGetCPUReportsEnabledRequest() &&
              monitor.hasGetCPUReportsRequest());
   }

   /**
    * Determine whether this target's monitor supports setting CPU load report
    * enablement information.
    *
    * @return true if this target's monitor supports setting CPU load report
    * enablement information, false otherwise.
    */
   public boolean hasSetCPUReportsEnabledSupport()
   {
      return monitor.hasSetCPUReportsEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving CPU priority
    * level load report enablement information and CPU priority level load
    * reports.
    *
    * @return true if this target's monitor supports retrieving CPU priority
    * level load report enablement information and CPU priority level load
    * reports, false otherwise.
    */
   public boolean hasGetCPUPriorityReportsSupport()
   {
      return (monitor.hasGetCPUPriReportsEnabledRequest() &&
              monitor.hasGetCPUPriReportsRequest());
   }

   /**
    * Determine whether this target's monitor supports setting CPU priority
    * level load report enablement information.
    *
    * @return true if this target's monitor supports setting CPU priority level
    * load report enablement information, false otherwise.
    */
   public boolean hasSetCPUPriorityReportsEnabledSupport()
   {
      return monitor.hasSetCPUPriReportsEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving CPU process
    * level load report enablement information and CPU process level load
    * reports.
    *
    * @return true if this target's monitor supports retrieving CPU process
    * level load report enablement information and CPU process level load
    * reports, false otherwise.
    */
   public boolean hasGetCPUProcessReportsSupport()
   {
      return (monitor.hasGetCPUProcessReportsEnabledRequest() &&
              monitor.hasGetCPUProcessReportsRequest());
   }

   /**
    * Determine whether this target's monitor supports setting CPU process level
    * load report enablement information.
    *
    * @return true if this target's monitor supports setting CPU process level
    * load report enablement information, false otherwise.
    */
   public boolean hasSetCPUProcessReportsEnabledSupport()
   {
      return monitor.hasSetCPUProcessReportsEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports setting, clearing, and
    * retrieving CPU process report points.
    *
    * @return true if this target's monitor supports setting, clearing, and
    * retrieving CPU process report points, false otherwise.
    */
   public boolean hasCPUProcessReportPointSupport()
   {
      return (monitor.hasSetCPUProcessReportpointRequest() &&
              monitor.hasClearCPUProcessReportpointRequest() &&
              monitor.hasGetCPUProcessReportpointInfoRequest());
   }

   /**
    * Determine whether this target's monitor supports retrieving CPU block
    * level load report enablement information and CPU block level load
    * reports.
    *
    * @return true if this target's monitor supports retrieving CPU block
    * level load report enablement information and CPU block level load
    * reports, false otherwise.
    */
   public boolean hasGetCPUBlockReportsSupport()
   {
      return (monitor.hasGetCPUBlockReportsEnabledRequest() &&
              monitor.hasGetCPUBlockReportsRequest());
   }

   /**
    * Determine whether this target's monitor supports setting CPU block level
    * load report enablement information.
    *
    * @return true if this target's monitor supports setting CPU block level
    * load report enablement information, false otherwise.
    */
   public boolean hasSetCPUBlockReportsEnabledSupport()
   {
      return monitor.hasSetCPUBlockReportsEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving user report
    * enablement information and user reports.
    *
    * @return true if this target's monitor supports retrieving user report
    * enablement information and user reports, false otherwise.
    */
   public boolean hasGetUserReportsSupport()
   {
      return (monitor.hasGetUserReportsEnabledRequest() &&
              monitor.hasGetUserReportsRequest());
   }

   /**
    * Determine whether this target's monitor supports setting user report
    * enablement information.
    *
    * @return true if this target's monitor supports setting user report
    * enablement information, false otherwise.
    */
   public boolean hasSetUserReportsEnabledSupport()
   {
      return monitor.hasSetUserReportsEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving the types of
    * performance counters supported by this target.
    *
    * @return true if this target's monitor supports retrieving the types of
    * performance counters supported by this target, false otherwise.
    */
   public boolean hasPerformanceCounterSupport()
   {
      return monitor.hasGetSupportedCounterTypesRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving performance
    * counter enablement information.
    *
    * @return true if this target's monitor supports retrieving performance
    * counter enablement information, false otherwise.
    */
   public boolean hasGetPerformanceCounterEnabledSupport()
   {
      return monitor.hasGetCounterTypeEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports setting performance
    * counter enablement information.
    *
    * @return true if this target's monitor supports setting performance counter
    * enablement information, false otherwise.
    */
   public boolean hasSetPerformanceCounterEnabledSupport()
   {
      return monitor.hasSetCounterTypeEnabledRequest();
   }

   /**
    * Determine whether this target's monitor supports querying and setting
    * performance counter report notification enablement.
    *
    * @return true if this target's monitor supports querying and setting
    * performance counter report notification enablement, false otherwise.
    */
   public boolean hasPerformanceCounterNotifyEnabledSupport()
   {
      return (monitor.hasGetCounterReportsNotifyEnabledRequest() &&
              monitor.hasSetCounterReportsNotifyEnabledRequest());
   }

   /**
    * Determine whether this target's monitor supports attaching to and
    * detaching from scopes.
    *
    * @return true if this target's monitor supports attaching to and detaching
    * from scopes, false otherwise.
    */
   public boolean hasAttachDetachSupport()
   {
      return (monitor.hasAttachRequest() && monitor.hasDetachRequest());
   }

   /**
    * Determine whether this target's monitor supports intercepting and resuming
    * scopes.
    *
    * @return true if this target's monitor supports intercepting and resuming
    * scopes, false otherwise.
    */
   public boolean hasInterceptResumeSupport()
   {
      return (monitor.hasInterceptScopeRequest() &&
              monitor.hasResumeScopeRequest());
   }

   /**
    * Determine whether this target's monitor supports redefining main and
    * intercept scopes previously set when attaching.
    *
    * @return true if this target's monitor supports redefining main and
    * intercept scopes previously set when attaching, false otherwise.
    */
   public boolean hasSetScopeSupport()
   {
      return (monitor.hasSetScopeRequest() &&
              monitor.hasSetInterceptScopeRequest());
   }

   /**
    * Determine whether this target's monitor supports setting send event action
    * points.
    *
    * @return true if this target's monitor supports setting send event action
    * points, false otherwise.
    */
   public boolean hasSetSendEventActionPointSupport()
   {
      return monitor.hasSetSendActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting receive event
    * action points.
    *
    * @return true if this target's monitor supports setting receive event
    * action points, false otherwise.
    */
   public boolean hasSetReceiveEventActionPointSupport()
   {
      return monitor.hasSetReceiveActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting swap event action
    * points.
    *
    * @return true if this target's monitor supports setting swap event action
    * points, false otherwise.
    */
   public boolean hasSetSwapEventActionPointSupport()
   {
      return monitor.hasSetSwapActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting create event
    * action points.
    *
    * @return true if this target's monitor supports setting create event action
    * points, false otherwise.
    */
   public boolean hasSetCreateEventActionPointSupport()
   {
      return monitor.hasSetCreateActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting kill event action
    * points.
    *
    * @return true if this target's monitor supports setting kill event action
    * points, false otherwise.
    */
   public boolean hasSetKillEventActionPointSupport()
   {
      return monitor.hasSetKillActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting error event
    * action points.
    *
    * @return true if this target's monitor supports setting error event action
    * points, false otherwise.
    */
   public boolean hasSetErrorEventActionPointSupport()
   {
      return monitor.hasSetErrorActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting user event action
    * points.
    *
    * @return true if this target's monitor supports setting user event action
    * points, false otherwise.
    */
   public boolean hasSetUserEventActionPointSupport()
   {
      return monitor.hasSetUserActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting bind event action
    * points.
    *
    * @return true if this target's monitor supports setting bind event action
    * points, false otherwise.
    */
   public boolean hasSetBindEventActionPointSupport()
   {
      return monitor.hasSetBindActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting alloc event
    * action points.
    *
    * @return true if this target's monitor supports setting alloc event action
    * points, false otherwise.
    */
   public boolean hasSetAllocEventActionPointSupport()
   {
      return monitor.hasSetAllocActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting free event action
    * points.
    *
    * @return true if this target's monitor supports setting free event action
    * points, false otherwise.
    */
   public boolean hasSetFreeEventActionPointSupport()
   {
      return monitor.hasSetFreeBufActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports setting timeout event
    * action points.
    *
    * @return true if this target's monitor supports setting timeout event
    * action points, false otherwise.
    */
   public boolean hasSetTimeoutEventActionPointSupport()
   {
      return monitor.hasSetTimeoutActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports clearing event action
    * points.
    *
    * @return true if this target's monitor supports clearing event action
    * points, false otherwise.
    */
   public boolean hasClearEventActionPointSupport()
   {
      return monitor.hasClearEventActionpointRequest();
   }

   /**
    * Determine whether this target's monitor supports clearing all event action
    * points in one clearEventActionPoint() request.
    *
    * @return true if this target's monitor supports clearing all event action
    * points in one clearEventActionPoint() request, false otherwise.
    * @see #clearEventActionPoint(IProgressMonitor, EventActionPoint)
    */
   public boolean hasClearAllEventActionPointsSupport()
   {
      return monitor.hasClearAllEventActionpointsFeature();
   }

   /**
    * Determine whether this target's monitor supports the deprecated way of
    * retrieving event action points.
    *
    * @return true if this target's monitor supports the deprecated way of
    * retrieving event action points, false otherwise.
    */
   public boolean hasDeprecatedGetEventActionPointsSupport()
   {
      return monitor.hasGetEventActionpointInfoRequest();
   }

   /**
    * Determine whether this target's monitor supports the new way of retrieving
    * event action points.
    *
    * @return true if this target's monitor supports the new way of retrieving
    * event action points, false otherwise.
    */
   public boolean hasGetEventActionPointsSupport()
   {
      return monitor.hasGetEventActionpointsRequest();
   }

   /**
    * Determine whether this target's monitor supports getting and setting
    * active event state.
    *
    * @return true if this target's monitor supports getting and setting active
    * event state, false otherwise.
    */
   public boolean hasEventStateSupport()
   {
      return (monitor.hasGetEventStateRequest() &&
              monitor.hasSetEventStateRequest());
   }

   /**
    * Determine whether this target's monitor supports querying and setting
    * event notification enablement.
    *
    * @return true if this target's monitor supports querying and setting event
    * notification enablement, false otherwise.
    */
   public boolean hasEventNotifyEnabledSupport()
   {
      return (monitor.hasGetNotifyEnabledRequest() &&
              monitor.hasSetNotifyEnabledRequest());
   }

   /**
    * Determine whether this target's monitor supports querying and setting
    * event trace enablement.
    *
    * @return true if this target's monitor supports querying and setting event
    * trace enablement, false otherwise.
    */
   public boolean hasEventTraceEnabledSupport()
   {
      return (monitor.hasGetTraceEnabledRequest() &&
              monitor.hasSetTraceEnabledRequest());
   }

   /**
    * Determine whether this target's monitor supports clearing the event trace.
    *
    * @return true if this target's monitor supports clearing the event trace,
    * false otherwise.
    */
   public boolean hasClearEventTraceSupport()
   {
      return monitor.hasClearTraceRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving the event
    * trace while event tracing is disabled.
    *
    * @return true if this target's monitor supports retrieving the event trace
    * while event tracing is disabled, false otherwise.
    */
   public boolean hasGetEventTraceSupport()
   {
      return monitor.hasGetTraceRequest();
   }

   /**
    * Determine whether this target's monitor supports retrieving the event
    * trace while event tracing is enabled.
    *
    * @return true if this target's monitor supports retrieving the event trace
    * while event tracing is enabled, false otherwise.
    */
   public boolean hasGetEventTraceMultipleSupport()
   {
      return monitor.hasGetTraceMultipleRequest();
   }

   /**
    * Return the OS type of this target, i.e. one of the Target.OS_* constants.
    *
    * @return the OS type of this target.
    * @see #OS_OSE_5
    * @see #OS_OSE_4
    * @see #OS_OSE_EPSILON
    * @see #OS_OSE_CK
    * @see #OS_UNKNOWN
    */
   public int getOsType()
   {
      // Final field, no synchronization needed.
      return osType;
   }

   /**
    * Return the CPU type of this target, i.e. one of the Target.CPU_*
    * constants.
    *
    * @return the CPU type of this target.
    * @see #CPU_PPC
    * @see #CPU_M68K
    * @see #CPU_ARM
    * @see #CPU_MIPS
    * @see #CPU_X86
    * @see #CPU_SPARC
    * @see #CPU_C64X
    * @see #CPU_C64XP
    * @see #CPU_SP26XX
    * @see #CPU_SP27XX
    * @see #CPU_TL3
    * @see #CPU_CEVAX
    * @see #CPU_M8144
    * @see #CPU_M8156
    * @see #CPU_C66X
    * @see #CPU_C674X
    * @see #CPU_UNKNOWN
    */
   public int getCpuType()
   {
      // Final field, no synchronization needed.
      return cpuType;
   }

   /**
    * Return the number of execution units for this target, only available if
    * this target is connected.
    *
    * @return the number of execution units for this target.
    */
   public short getNumExecutionUnits()
   {
      // Volatile field, no synchronization needed.
      return numExecutionUnits;
   }

   /**
    * Return the tick length of this target in microseconds, only available if
    * this target is connected.
    *
    * @return the tick length of this target in microseconds.
    */
   public int getTickLength()
   {
      // Volatile field, no synchronization needed.
      return tickLength;
   }

   /**
    * Return the ntick timer frequency of this target in Hz, only available if
    * this target is connected.
    * <p>
    * Note: Only available if this target's monitor is not obsolete
    * (e.g. OSE 5.2 and later), i.e. if isMonitorObsolete() returns false.
    *
    * @return the ntick timer frequency of this target in Hz.
    */
   public int getNTickFrequency()
   {
      // Volatile field, no synchronization needed.
      return ntickFrequency;
   }

   /**
    * Return an array of the system parameters for this target (never null).
    * A new array is created and returned for each call.
    *
    * @return an array of the system parameters for this target (never null).
    */
   public Parameter[] getSysParams()
   {
      synchronized (lock)
      {
         return (Parameter[]) sysParams.toArray(new Parameter[0]);
      }
   }

   /**
    * Return an array of the segments in this target (never null). A new array
    * is created and returned for each call. If this target does not support
    * segments, an empty array is returned.
    *
    * @return an array of the segments in this target (never null).
    */
   public Segment[] getSegments()
   {
      return (Segment[]) segments.toArray(new Segment[0]);
   }

   /**
    * Return an array of the pools in this target (never null). A new array is
    * created and returned for each call.
    *
    * @return an array of the pools in this target (never null).
    */
   public Pool[] getPools()
   {
      if (hasExtendedSegmentSupport())
      {
         List poolsList = new ArrayList();
         Segment[] segmentsArray = getSegments();

         for (int i = 0; i < segmentsArray.length; i++)
         {
            Pool[] poolsArray = segmentsArray[i].getPools();
            for (int j = 0; j < poolsArray.length; j++)
            {
               poolsList.add(poolsArray[j]);
            }
         }
         return (Pool[]) poolsList.toArray(new Pool[0]);
      }
      else
      {
         return (Pool[]) pools.toArray(new Pool[0]);
      }
   }

   /**
    * Return an array of the heaps in this target (never null). A new array is
    * created and returned for each call.
    *
    * @return an array of the heaps in this target (never null).
    */
   public Heap[] getHeaps()
   {
      if (hasExtendedSegmentSupport())
      {
         List heapsList = new ArrayList();
         Segment[] segmentsArray = getSegments();

         for (int i = 0; i < segmentsArray.length; i++)
         {
            Heap[] heapsArray = segmentsArray[i].getHeaps();
            for (int j = 0; j < heapsArray.length; j++)
            {
               heapsList.add(heapsArray[j]);
            }
         }
         return (Heap[]) heapsList.toArray(new Heap[0]);
      }
      else
      {
         return (Heap[]) heaps.toArray(new Heap[0]);
      }
   }

   /**
    * Return an array of the blocks in this target (never null). A new array is
    * created and returned for each call. If this target does not support
    * blocks, an empty array is returned.
    *
    * @return an array of the blocks in this target (never null).
    */
   public Block[] getBlocks()
   {
      if (hasExtendedSegmentSupport())
      {
         List blocksList = new ArrayList();
         Segment[] segmentsArray = getSegments();

         for (int i = 0; i < segmentsArray.length; i++)
         {
            Block[] blocksArray = segmentsArray[i].getBlocks();
            for (int j = 0; j < blocksArray.length; j++)
            {
               blocksList.add(blocksArray[j]);
            }
         }
         return (Block[]) blocksList.toArray(new Block[0]);
      }
      else
      {
         return (Block[]) blocks.toArray(new Block[0]);
      }
   }

   /**
    * Return an array of the processes in this target (never null). A new array
    * is created and returned for each call.
    *
    * @return an array of the processes in this target (never null).
    */
   public Process[] getProcesses()
   {
      if (hasExtendedSegmentSupport())
      {
         List processesList = new ArrayList();
         Segment[] segmentsArray = getSegments();

         for (int i = 0; i < segmentsArray.length; i++)
         {
            Block[] blocksArray = segmentsArray[i].getBlocks();
            for (int j = 0; j < blocksArray.length; j++)
            {
               Process[] processesArray = blocksArray[j].getProcesses();
               for (int k = 0; k < processesArray.length; k++)
               {
                  processesList.add(processesArray[k]);
               }
            }
         }
         return (Process[]) processesList.toArray(new Process[0]);
      }
      else if (hasBlockSupport())
      {
         List processesList = new ArrayList();
         Block[] blocksArray = getBlocks();

         for (int i = 0; i < blocksArray.length; i++)
         {
            Process[] processesArray = blocksArray[i].getProcesses();
            for (int j = 0; j < processesArray.length; j++)
            {
               processesList.add(processesArray[j]);
            }
         }
         return (Process[]) processesList.toArray(new Process[0]);
      }
      else
      {
         return (Process[]) processes.toArray(new Process[0]);
      }
   }

   /**
    * Return the parent gate object.
    *
    * @return the parent gate object.
    * @see com.ose.system.SystemModelNode#getParent()
    */
   public SystemModelNode getParent()
   {
      return getGate();
   }

   /**
    * Return an array of the children segments or pools, heaps, and blocks or
    * processes in this target. A new array is created and returned for each
    * call.
    *
    * @return an array of the children segments or pools, heaps, and blocks or
    * processes in this target.
    * @see com.ose.system.SystemModelNode#getChildren()
    * @see Segment
    * @see Pool
    * @see Heap
    * @see Block
    * @see Process
    */
   public SystemModelNode[] getChildren()
   {
      if (hasExtendedSegmentSupport())
      {
         return getSegments();
      }
      else
      {
         SystemModelNode[] poolsArray = getPools();
         SystemModelNode[] heapsArray = getHeaps();
         SystemModelNode[] blocksOrProcessesArray =
            (hasBlockSupport() ? getBlocks() : (SystemModelNode[]) getProcesses());
         int poolsLength = poolsArray.length;
         int heapsLength = heapsArray.length;
         int blocksOrProcessesLength = blocksOrProcessesArray.length;
         SystemModelNode[] children =
            new SystemModelNode[poolsLength + heapsLength + blocksOrProcessesLength];
         System.arraycopy(poolsArray, 0, children, 0, poolsLength);
         System.arraycopy(heapsArray, 0, children, poolsLength, heapsLength);
         System.arraycopy(blocksOrProcessesArray, 0,
            children, poolsLength + heapsLength, blocksOrProcessesLength);
         return children;
      }
   }

   /**
    * Determine whether this node is a leaf node or not. A target object is not
    * a leaf node and always returns false.
    *
    * @return false since a target object is not a leaf node.
    * @see com.ose.system.SystemModelNode#isLeaf()
    */
   public boolean isLeaf()
   {
      return false;
   }

   /**
    * Update this target and its segments or pools, heaps, and blocks or
    * processes if it is connected. If recursive is true, also update the
    * children nodes of the segments nodes or pool nodes, heap nodes, and block
    * or process nodes and so on; i.e. recursivly update the branch in the tree
    * that is rooted in this target node.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        whether or not to recursivly update the whole
    * branch in the tree that is rooted in this target node.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
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

      synchronized (this)
      {
         if (!isKilled())
         {
            if (hasExtendedSegmentSupport())
            {
               updateWithSegmentSupport(progressMonitor, recursive);
               return;
            }

            if (hasGetSysParamSupport())
            {
               monitorHandler.getSysParamRequest(progressMonitor);
            }

            if (hasPoolSupport())
            {
               monitorHandler.getPoolInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_SYSTEM, 0);
            }

            if (hasHeapSupport())
            {
               monitorHandler.getHeapInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_SYSTEM, 0);

               for (Iterator i = heaps.iterator(); i.hasNext();)
               {
                  Heap heap = (Heap) i.next();
                  if (!heap.isKilled())
                  {
                     heap.updateLazyProperties(progressMonitor);
                  }
               }
            }

            if (hasBlockSupport())
            {
               monitorHandler.getBlockInfoRequest(progressMonitor, this,
                     Monitor.MONITOR_SCOPE_SYSTEM, 0);

               for (Iterator i = blocks.iterator(); i.hasNext();)
               {
                  Block block = (Block) i.next();
                  if (!block.isKilled())
                  {
                     block.updateLazyProperties(progressMonitor);
                  }
               }

               if (recursive)
               {
                  if (hasProcessSupport())
                  {
                     monitorHandler.getProcessInfoRequest(progressMonitor, this,
                           1, Monitor.MONITOR_SCOPE_SYSTEM, 0);
                  }

                  for (Iterator i = blocks.iterator(); i.hasNext();)
                  {
                     Block block = (Block) i.next();
                     if (!block.isKilled())
                     {
                        for (Iterator j = block.getProcessList().iterator(); j.hasNext();)
                        {
                           Process process = (Process) j.next();
                           if (!process.isKilled())
                           {
                              process.updateLazyProperties(progressMonitor);
                           }
                        }
                     }
                  }
               }
            }
            else if (hasProcessSupport())
            {
               monitorHandler.getProcessInfoRequest(progressMonitor, this,
                     1, Monitor.MONITOR_SCOPE_SYSTEM, 0);

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
   }

   /**
    * Update this target and its segments if it is connected. If recursive is
    * true, also update the children nodes of the segment nodes and so on; i.e.
    * recursivly update the branch in the tree that is rooted in this target
    * node.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        whether or not to recursivly update the whole
    * branch in the tree that is rooted in this target node.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see com.ose.system.SystemModelNode#update(org.eclipse.core.runtime.IProgressMonitor,
    * boolean)
    */
   private void updateWithSegmentSupport(IProgressMonitor progressMonitor,
                                         boolean recursive)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            if (hasGetSysParamSupport())
            {
               monitorHandler.getSysParamRequest(progressMonitor);
            }

            if (hasExtendedSegmentSupport())
            {
               monitorHandler.getSegmentInfoRequest(progressMonitor, this,
                     1, Monitor.MONITOR_SCOPE_SYSTEM, 0);
            }

            if (recursive)
            {
               if (hasPoolSupport())
               {
                  monitorHandler.getPoolInfoRequest(progressMonitor, this,
                        Monitor.MONITOR_SCOPE_SYSTEM, 0);
               }

               if (hasHeapSupport())
               {
                  monitorHandler.getHeapInfoRequest(progressMonitor, this,
                        Monitor.MONITOR_SCOPE_SYSTEM, 0);
               }

               if (hasBlockSupport())
               {
                  monitorHandler.getBlockInfoRequest(progressMonitor, this,
                        Monitor.MONITOR_SCOPE_SYSTEM, 0);
               }

               if (hasProcessSupport())
               {
                  monitorHandler.getProcessInfoRequest(progressMonitor, this,
                        1, Monitor.MONITOR_SCOPE_SYSTEM, 0);
               }

               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  if (!segment.isKilled())
                  {
                     for (Iterator j = segment.getHeapList().iterator(); j.hasNext();)
                     {
                        Heap heap = (Heap) j.next();
                        if (!heap.isKilled())
                        {
                           heap.updateLazyProperties(progressMonitor);
                        }
                     }

                     for (Iterator j = segment.getBlockList().iterator(); j.hasNext();)
                     {
                        Block block = (Block) j.next();
                        if (!block.isKilled())
                        {
                           block.updateLazyProperties(progressMonitor);
                        }
                     }

                     for (Iterator j = segment.getBlockList().iterator(); j.hasNext();)
                     {
                        Block block = (Block) j.next();
                        if (!block.isKilled())
                        {
                           for (Iterator k = block.getProcessList().iterator(); k.hasNext();)
                           {
                              Process process = (Process) k.next();
                              if (!process.isKilled())
                              {
                                 process.updateLazyProperties(progressMonitor);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   /**
    * Clean this target, i.e. recursivly remove all nodes marked as killed in
    * the branch in the tree that is rooted in this target node.
    *
    * @see com.ose.system.SystemModelNode#clean()
    */
   public void clean()
   {
      synchronized (this)
      {
         if (isKilled())
         {
            gate.removeTarget(this);
            SystemModel.getInstance().fireNodesRemoved(gate, Collections.singletonList(this));
         }

         cleanChildren();
      }
   }

   /**
    * Clean the segments, pools, heaps, blocks, and processes of this target
    * recursivly.
    */
   void cleanChildren()
   {
      synchronized (this)
      {
         if (hasExtendedSegmentSupport())
         {
            List removedChildren = new ArrayList();
            List originalSegments = new ArrayList(segments);

            for (Iterator i = segments.iterator(); i.hasNext();)
            {
               Segment segment = (Segment) i.next();
               if (segment.isKilled())
               {
                  i.remove();
                  removedChildren.add(segment);
               }
            }

            if (!removedChildren.isEmpty())
            {
               SystemModel.getInstance().fireNodesRemoved(this, removedChildren);
            }

            for (Iterator i = originalSegments.iterator(); i.hasNext();)
            {
               Segment segment = (Segment) i.next();
               segment.cleanChildren();
            }
         }
         else
         {
            List removedChildren = new ArrayList();
            List originalBlocks = new ArrayList(blocks);

            for (Iterator i = pools.iterator(); i.hasNext();)
            {
               Pool pool = (Pool) i.next();
               if (pool.isKilled())
               {
                  i.remove();
                  removedChildren.add(pool);
               }
            }

            for (Iterator i = heaps.iterator(); i.hasNext();)
            {
               Heap heap = (Heap) i.next();
               if (heap.isKilled())
               {
                  i.remove();
                  removedChildren.add(heap);
               }
            }

            if (hasBlockSupport())
            {
               for (Iterator i = blocks.iterator(); i.hasNext();)
               {
                  Block block = (Block) i.next();
                  if (block.isKilled())
                  {
                     i.remove();
                     removedChildren.add(block);
                  }
               }
            }
            else if (hasProcessSupport())
            {
               for (Iterator i = processes.iterator(); i.hasNext();)
               {
                  Process process = (Process) i.next();
                  if (process.isKilled())
                  {
                     i.remove();
                     removedChildren.add(process);
                  }
               }
            }

            if (!removedChildren.isEmpty())
            {
               SystemModel.getInstance().fireNodesRemoved(this, removedChildren);
            }

            if (hasBlockSupport())
            {
               for (Iterator i = originalBlocks.iterator(); i.hasNext();)
               {
                  Block block = (Block) i.next();
                  block.cleanChildren();
               }
            }
         }
      }
   }

   /**
    * Return an object which is an instance of the given adapter class
    * associated with this object. Return null if no such object can be found.
    *
    * @param adapter  the adapter class to look up.
    * @return an object castable to the given adapter class, or null if this
    * object does not have an adapter for the given adapter class.
    * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
    */
   public Object getAdapter(Class adapter)
   {
      return Platform.getAdapterManager().getAdapter(this, adapter);
   }

   /**
    * Check the state of this target object. If recursive is true, recursivly
    * check the state of all nodes in the branch in the tree that is rooted in
    * this target node.
    *
    * @param recursive  whether or not to recursivly check the state of all
    * nodes in the branch in the tree that is rooted in this target node.
    * @throws IllegalStateException  if this target object is not in a
    * consistent state or, if recursive is true, if any node in the branch in
    * the tree that is rooted in this target node is not in a consistent state.
    */
   public void checkState(boolean recursive) throws IllegalStateException
   {
      // Final fields, no synchronization needed.
      if (gate == null)
      {
         throw new IllegalStateException("Gate is null.");
      }
      if (huntPath == null)
      {
         throw new IllegalStateException("Hunt path is null.");
      }
      if (senderGateway == null)
      {
         throw new IllegalStateException("Sender gateway is null.");
      }
      if (monitor == null)
      {
         throw new IllegalStateException("Monitor is null.");
      }
      if (monitorHandler == null)
      {
         throw new IllegalStateException("Monitor handler is null.");
      }
      // pm and pmHandler can be null.
      // connected cannot be tested.
      // attached cannot be tested.
      if (name == null)
      {
         throw new IllegalStateException("Name is null.");
      }
      // bigEndian cannot be tested.
      if ((osType != OS_OSE_5) &&
          (osType != OS_OSE_4) &&
          (osType != OS_OSE_EPSILON) &&
          (osType != OS_OSE_CK) &&
          (osType != OS_UNKNOWN))
      {
         throw new IllegalStateException("Invalid OS type: " + osType);
      }
      if ((cpuType != CPU_PPC) &&
          (cpuType != CPU_M68K) &&
          (cpuType != CPU_ARM) &&
          (cpuType != CPU_MIPS) &&
          (cpuType != CPU_X86) &&
          (cpuType != CPU_SPARC) &&
          (cpuType != CPU_C64X) &&
          (cpuType != CPU_C64XP) &&
          (cpuType != CPU_SP26XX) &&
          (cpuType != CPU_SP27XX) &&
          (cpuType != CPU_TL3) &&
          (cpuType != CPU_CEVAX) &&
          (cpuType != CPU_M8144) &&
          (cpuType != CPU_M8156) &&
          (cpuType != CPU_C66X) &&
          (cpuType != CPU_C674X) &&
          (cpuType != CPU_UNKNOWN))
      {
         throw new IllegalStateException("Invalid CPU type: " + cpuType);
      }
      // numExecutionUnits, tickLength, and ntickFrequency can be anything.
      if (sysParams == null)
      {
         throw new IllegalStateException("System parameters is null.");
      }
      if (osedb == null)
      {
         throw new IllegalStateException("Id-to-object mapping is null.");
      }
      if (pooldb == null)
      {
         throw new IllegalStateException("Id-to-pool mapping is null.");
      }
      if (heapdb == null)
      {
         throw new IllegalStateException("Id-to-heap mapping is null.");
      }
      if (segments == null)
      {
         throw new IllegalStateException("Segments is null.");
      }
      if (pools == null)
      {
         throw new IllegalStateException("Pools is null.");
      }
      if (heaps == null)
      {
         throw new IllegalStateException("Heaps is null.");
      }
      if (blocks == null)
      {
         throw new IllegalStateException("Blocks is null.");
      }
      if (processes == null)
      {
         throw new IllegalStateException("Processes is null.");
      }
      if (reportListenerList == null)
      {
         throw new IllegalStateException(
            "Performance counter report listener list is null.");
      }
      if (eventListenerList == null)
      {
         throw new IllegalStateException("Target event listener list is null.");
      }
      // notifyProcessCache cannot be tested.
      // traceProcessCache cannot be tested.

      if (recursive)
      {
         if (hasExtendedSegmentSupport())
         {
            synchronized (segments)
            {
               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  segment.checkState(recursive);
               }
            }
         }
         else
         {
            synchronized (pools)
            {
               for (Iterator i = pools.iterator(); i.hasNext();)
               {
                  Pool pool = (Pool) i.next();
                  pool.checkState(recursive);
               }
            }
            synchronized (heaps)
            {
               for (Iterator i = heaps.iterator(); i.hasNext();)
               {
                  Heap heap = (Heap) i.next();
                  heap.checkState(recursive);
               }
            }
            if (hasBlockSupport())
            {
               synchronized (blocks)
               {
                  for (Iterator i = blocks.iterator(); i.hasNext();)
                  {
                     Block block = (Block) i.next();
                     block.checkState(recursive);
                  }
               }
            }
            else if (hasProcessSupport())
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
      }
   }

   /**
    * Set a system parameter for this target.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param reconfigure      if true and if the system parameter is read by
    * the OSE monitor itself, then reread and reconfigure that parameter in
    * the OSE monitor, after setting it.
    * @param name             the name of the system parameter.
    * @param value            the value of the system parameter or null to
    * remove the system parameter.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; parent target is automatically disconnected.
    */
   public void setSysParam(IProgressMonitor progressMonitor,
                           boolean reconfigure,
                           String name,
                           String value)
      throws IOException
   {
      if ((progressMonitor == null) || (name == null))
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setSysParamRequest(progressMonitor,
                                              reconfigure,
                                              name,
                                              value);
            update(progressMonitor, false);
         }
      }
   }

   /**
    * Return an array of the blocks in the specified scope in this target that
    * match the given block filter if this target is connected. The result is a
    * snapshot of the target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param scopeType        the scope type, i.e. one of the Target.SCOPE_*
    * constants.
    * @param scopeId          the scope ID, or 0 if scopeType is
    * Target.SCOPE_SYSTEM.
    * @param filter           the block filter, or null if all blocks in the
    * specified scope should be returned.
    * @return an array of the blocks in the specified scope in this target that
    * match the given block filter, or an empty array if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_ID
    */
   public BlockInfo[] getFilteredBlocks(IProgressMonitor progressMonitor,
                                        int scopeType,
                                        int scopeId,
                                        BlockFilter filter)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getFilteredBlockInfoRequest(progressMonitor,
                  scopeType, scopeId, ((filter != null) ? filter.getTags() : null));
         }
         else
         {
            return new BlockInfo[0];
         }
      }
   }

   /**
    * Return an array of the processes in the specified scope in this target
    * that match the given process filter if this target is connected. The
    * result is a snapshot of the target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param scopeType        the scope type, i.e. one of the Target.SCOPE_*
    * constants.
    * @param scopeId          the scope ID, or 0 if scopeType is
    * Target.SCOPE_SYSTEM.
    * @param filter           the process filter, or null if all processes in
    * the specified scope should be returned.
    * @return an array of the processes in the specified scope in this target
    * that match the given process filter, or an empty array if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    */
   public ProcessInfo[] getFilteredProcesses(IProgressMonitor progressMonitor,
                                             int scopeType,
                                             int scopeId,
                                             ProcessFilter filter)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getFilteredProcessInfoRequest(progressMonitor,
                  scopeType, scopeId, ((filter != null) ? filter.getTags() : null));
         }
         else
         {
            return new ProcessInfo[0];
         }
      }
   }

   /**
    * Return a byte array of the memory at the specified address of the
    * specified size in the specified process, block or segment in this target
    * if this target is connected. If all the requested memory can not be read,
    * a smaller amount of memory from the specified address may be returned.
    * The result is a snapshot of the target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param pid      process/block/segment ID; the address is in this context.
    * @param address  memory address to start reading from.
    * @param size     number of bytes to attempt to read.
    * @return a byte array of the retrieved memory from this target, or an empty
    * array if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public byte[] getMemory(IProgressMonitor progressMonitor,
                           int pid,
                           long address,
                           long size)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getMemoryRequest(progressMonitor, pid, address, size);
         }
         else
         {
            return new byte[0];
         }
      }
   }

   /**
    * Return an array of a specified set of the dumps on this target if this
    * target is connected. The result is a snapshot of the target at the time
    * of the call.
    * <p>
    * Available dumps are numbered from 0 to N - 1, where 0 is the most recent
    * dump and N is the total number of dumps. Dumps are returned in reverse
    * chronological order, i.e. most recent dump first.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param from  the index of the first dump, inclusive. If this index is
    * greater than N, no dumps will be returned.
    * @param to  the index of the last dump, exclusive. If this index is
    * greater than N, dumps up to the end of the dump list will be returned.
    * @return an array of the dumps in the specified interval on this target,
    * or an empty array if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public DumpInfo[] getDumps(IProgressMonitor progressMonitor, int from, int to)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getRamdumpInfoRequest(progressMonitor,
                                                        from,
                                                        to);
         }
         else
         {
            return new DumpInfo[0];
         }
      }
   }

   /**
    * Collect the specified dump from this target if this target is connected.
    * <p>
    * The collected dump will be incrementally written to the specified output
    * stream. It is the responsibility of the caller to close the output stream
    * afterwards.
    * <p>
    * If the specified progress monitor is setup with the total size of the
    * dump, its progress will be incrementally updated as the dump is collected.
    * It is the responsibility of the caller to complete the progress monitor
    * afterwards.
    *
    * @param progressMonitor  the progress monitor used for cancellation and
    * updating.
    * @param dumpId  the id of the dump to collect.
    * @param out  the output stream where to write the collected dump.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void collectDump(IProgressMonitor progressMonitor,
                           int dumpId,
                           OutputStream out)
      throws IOException
   {
      if ((progressMonitor == null) || (out == null))
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.getRamdumpRequest(progressMonitor, dumpId, out);
         }
      }
   }

   /**
    * Install a load module on this target if it is connected and has a program
    * manager.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param fileName         the file name of the load module.
    * @param installHandle    the installation handle of the load module.
    * @param persistent       whether or not the load module should be
    * persistent.
    * @param absolute         whether or not the load module is absolute linked.
    * @param parameters       the load module installation parameters, or null
    * if none.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public void installLoadModule(IProgressMonitor progressMonitor,
                                 String fileName,
                                 String installHandle,
                                 boolean persistent,
                                 boolean absolute,
                                 Map parameters)
      throws IOException
   {
      if ((progressMonitor == null) ||
          (fileName == null) ||
          (installHandle == null))
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            int options;
            String[] conf = null;

            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               return;
            }

            // Add any load module installation options.
            options = 0;
            if (persistent)
            {
               options |= ProgramManager.PM_LOAD_MODULE_PERSISTENT;
            }
            if (absolute)
            {
               options |= ProgramManager.PM_LOAD_MODULE_ABSOLUTE;
            }

            // Add any load module parameters.
            if (parameters != null)
            {
               int j = 0;
               conf = new String[parameters.entrySet().size()];
               for (Iterator i = parameters.entrySet().iterator(); i.hasNext(); j++)
               {
                  Entry entry = (Entry) i.next();
                  conf[j] = (String) entry.getKey() + "=" + (String) entry.getValue();
               }
            }

            // Install load module.
            pmHandler.installLoadModuleRequest(
                  progressMonitor,
                  options,
                  ProgramManager.PM_ELF_FILE_FORMAT,
                  fileName,
                  installHandle,
                  conf);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }
      }
   }

   /**
    * Uninstall a load module from this target if it is connected and has a
    * program manager.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param installHandle    the installation handle of the load module.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public void uninstallLoadModule(IProgressMonitor progressMonitor,
                                   String installHandle)
      throws IOException
   {
      if ((progressMonitor == null) || (installHandle == null))
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               return;
            }

            // Uninstall load module.
            pmHandler.uninstallLoadModuleRequest(progressMonitor, installHandle);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }
      }
   }

   /**
    * Return the load module with the specified installation handle if this
    * target is connected and has a program manager.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param installHandle    the installation handle of the load module.
    * @return the load module with the specified installation handle, or null
    * if this target is disconnected or does not have a program manager.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public LoadModuleInfo getLoadModuleInfo(IProgressMonitor progressMonitor,
                                           String installHandle)
      throws IOException
   {
      LoadModuleInfo loadModule = null;

      if ((progressMonitor == null) || (installHandle == null))
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            // Get info about load module.
            loadModule =
               pmHandler.loadModuleInfoRequest(progressMonitor, installHandle);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }
      }

      return loadModule;
   }

   /**
    * Return an array of all load modules on this target if it is connected and
    * has a program manager.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return an array of all load modules on this target, or an empty array if
    * this target is disconnected or does not have a program manager.
    * @throws IOException  if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public LoadModuleInfo[] getLoadModuleInfo(IProgressMonitor progressMonitor)
      throws IOException
   {
      List loadModules = new ArrayList();

      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            String[] installHandles;

            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            // Get all load module install handles.
            installHandles =
               pmHandler.loadModuleInstallHandlesRequest(progressMonitor);

            // Get info about all load modules.
            for (int i = 0; i < installHandles.length; i++)
            {
               try
               {
                  LoadModuleInfo loadModule =
                     pmHandler.loadModuleInfoRequest(progressMonitor, installHandles[i]);
                  loadModules.add(loadModule);
               }
               catch (ServiceException ignore)
               {
                  // Ignore load modules that has been uninstalled
                  // after we retrieved their install handles.
               }
            }
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }
      }

      return (LoadModuleInfo[]) loadModules.toArray(new LoadModuleInfo[0]);
   }

   /**
    * Create and start a program from the load module with the specified
    * installation handle on this target if it is connected and has a program
    * manager. If successful, this target is non-recursivly updated so that its
    * list of segments or pools, heaps, and blocks is up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param installHandle    the installation handle of the load module.
    * @param parameters       the program parameters, or null if none.
    * @return the ID of the created program, or 0 if this target is disconnected
    * or does not have a program manager.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public int createProgram(IProgressMonitor progressMonitor,
                            String installHandle,
                            Map parameters)
      throws IOException
   {
      int progpid = 0;

      if ((progressMonitor == null) || (installHandle == null))
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            String[] conf = null;

            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            // Add any load module parameters.
            if (parameters != null)
            {
               int j = 0;
               conf = new String[parameters.entrySet().size()];
               for (Iterator i = parameters.entrySet().iterator(); i.hasNext(); j++)
               {
                  Entry entry = (Entry) i.next();
                  conf[j] = (String) entry.getKey() + "=" + (String) entry.getValue();
               }
            }

            // Create program.
            progpid = pmHandler.createProgramRequest(
                  progressMonitor,
                  ProgramManager.PM_NEW_DOMAIN,
                  installHandle,
                  conf);

            // Start program.
            pmHandler.startProgramRequest(progressMonitor, progpid);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }

         // Check for cancellation.
         if (progressMonitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Update target non-recursivly.
         update(progressMonitor, false);
      }

      return progpid;
   }

   /**
    * Kill the program with the specified ID on this target if it is connected
    * and has a program manager. If successful, this target is non-recursivly
    * updated so that its list of segments or pools, heaps, and blocks is
    * up-to-date.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param progpid          the ID of the program to kill.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public void killProgram(IProgressMonitor progressMonitor, int progpid)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            // Kill program.
            pmHandler.killProgramRequest(progressMonitor, progpid);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }

         // Check for cancellation.
         if (progressMonitor.isCanceled())
         {
            throw new OperationCanceledException();
         }

         // Update target non-recursivly.
         update(progressMonitor, false);
      }
   }

   /**
    * Return the program with the specified ID if this target is connected and
    * has a program manager.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param progpid          the ID of the program.
    * @return the program with the specified ID, or null if this target is
    * disconnected or does not have a program manager.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public ProgramInfo getProgramInfo(IProgressMonitor progressMonitor, int progpid)
      throws IOException
   {
      ProgramInfo programInfo = null;

      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            // Get program info.
            programInfo = pmHandler.programInfoRequest(progressMonitor, progpid);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }
      }

      return programInfo;
   }

   /**
    * Translate a process, block, or segment ID to a program ID if this target
    * is connected and has a program manager.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param pid              a process, block, or segment ID.
    * @return the corresponding program ID, or 0 if this target is disconnected
    * or does not have a program manager.
    * @throws IOException     if an I/O exception occurred.
    * @throws ServiceException  if the program manager service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled.
    */
   public int getProgramId(IProgressMonitor progressMonitor, int pid)
      throws IOException
   {
      int progpid = 0;

      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      if (!isKilled() && (pm != null))
      {
         try
         {
            // Connect to program manager.
            pm.connect();

            // Check for cancellation.
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            // Look up program ID.
            progpid = pmHandler.getProgramPidRequest(progressMonitor, pid);
         }
         finally
         {
            // Disconnect from program manager.
            pm.disconnect();
         }
      }

      return progpid;
   }

   /**
    * Return CPU load report enablement information for this target if it is
    * connected. The result is a snapshot of the target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return CPU load report enablement information for this target, or null if
    * this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public CPUReportsEnabledInfo getCPUReportsEnabled(
         IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUReportsEnabledRequest(progressMonitor);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Set CPU load report enablement information for this target if it is
    * connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled          the CPU load reporting enablement status.
    * @param interval         the CPU load reporting integration interval in
    * ticks.
    * @param priority         the CPU load reporting priority limit. CPU time
    * spent on this or lower priority (higher value) is disregarded.
    * @param maxReports       the suggested maximum number of CPU load reports
    * kept in the target. If 0 the default value of the target will be used.
    * The value will be ignored if it is too large for the target.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setCPUReportsEnabled(IProgressMonitor progressMonitor,
                                    boolean enabled,
                                    int interval,
                                    int priority,
                                    int maxReports)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setCPUReportsEnabledRequest(progressMonitor,
                                                       enabled,
                                                       interval,
                                                       priority,
                                                       maxReports);
         }
      }
   }

   /**
    * Return CPU load reports for this target if it is connected.
    * Requests are made for a number of CPU load reports that have a later time
    * stamp (tick and ntick), but not equal, than that specified. Set tick and
    * ntick to 0 to get all. The result is a snapshot of the target at the time
    * of the call.
    * <p>
    * Typically, a client calls this method periodically with the time stamp
    * (tick and ntick) of the last CPU load report from the previous call as
    * argument. To avoid losing reports, the time between each call should not
    * be greater than the CPU load reporting integration interval times the size
    * of the target's CPU load report buffer in number of CPU load reports.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit    the execution unit for which to get CPU load
    * reports or Target.ALL_EXECUTION_UNITS.
    * @param tick             the initial time stamp in number of ticks.
    * @param ntick            the initial time stamp in number of timer steps
    * since the last tick.
    * @return CPU load reports for this target, or null if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #ALL_EXECUTION_UNITS
    */
   public CPUReports getCPUReports(IProgressMonitor progressMonitor,
                                   short executionUnit,
                                   int tick,
                                   int ntick)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUReportsRequest(progressMonitor,
                                                       executionUnit,
                                                       tick,
                                                       ntick);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Return CPU priority level load report enablement information for this
    * target if it is connected. The result is a snapshot of the target at the
    * time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return CPU priority level load report enablement information for this
    * target, or null if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public CPUPriorityReportsEnabledInfo getCPUPriorityReportsEnabled(
         IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUPriReportsEnabledRequest(progressMonitor);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Set CPU priority level load report enablement information for this target
    * if it is connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled          the CPU priority level load reporting enablement
    * status.
    * @param interval         the CPU priority level load reporting integration
    * interval in ticks.
    * @param maxReports       the suggested maximum number of CPU priority level
    * load reports kept in the target. If 0 the default value of the target will
    * be used. The value will be ignored if it is too large for the target.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setCPUPriorityReportsEnabled(IProgressMonitor progressMonitor,
                                            boolean enabled,
                                            int interval,
                                            int maxReports)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setCPUPriReportsEnabledRequest(progressMonitor,
                                                          enabled,
                                                          interval,
                                                          maxReports);
         }
      }
   }

   /**
    * Return CPU priority level load reports for this target if it is connected.
    * Requests are made for a number of CPU priority level load reports that
    * have a later time stamp (tick and ntick), but not equal, than that
    * specified. Set tick and ntick to 0 to get all. The result is a snapshot of
    * the target at the time of the call.
    * <p>
    * Typically, a client calls this method periodically with the time stamp
    * (tick and ntick) of the last CPU priority level load report from the
    * previous call as argument. To avoid losing reports, the time between each
    * call should not be greater than the CPU priority level load reporting
    * integration interval times the size of the target's CPU priority level
    * load report buffer in number of CPU priority level load reports.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit    the execution unit for which to get CPU priority
    * level load reports or Target.ALL_EXECUTION_UNITS.
    * @param tick             the initial time stamp in number of ticks.
    * @param ntick            the initial time stamp in number of timer steps
    * since the last tick.
    * @return CPU priority level load reports for this target, or null if this
    * target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #ALL_EXECUTION_UNITS
    */
   public CPUPriorityReports getCPUPriorityReports(
         IProgressMonitor progressMonitor,
         short executionUnit,
         int tick,
         int ntick)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUPriReportsRequest(progressMonitor,
                                                          executionUnit,
                                                          tick,
                                                          ntick);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Return CPU process level load report enablement information for this
    * target if it is connected. The result is a snapshot of the target at the
    * time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return CPU process level load report enablement information for this
    * target, or null if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public CPUProcessReportsEnabledInfo getCPUProcessReportsEnabled(
         IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUProcessReportsEnabledRequest(progressMonitor);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Set CPU process level load report enablement information for this target
    * if it is connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled          the CPU process level load reporting enablement
    * status.
    * @param interval         the CPU process level load reporting integration
    * interval in ticks.
    * @param maxReports       the suggested maximum number of CPU process level
    * load reports kept in the target. If 0 the default value of the target will
    * be used. The value will be ignored if it is too large for the target.
    * @param maxProcsReport   the suggested maximum number of processes per
    * CPU process level load report.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setCPUProcessReportsEnabled(IProgressMonitor progressMonitor,
                                           boolean enabled,
                                           int interval,
                                           int maxReports,
                                           int maxProcsReport)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setCPUProcessReportsEnabledRequest(progressMonitor,
                                                              enabled,
                                                              interval,
                                                              maxReports,
                                                              maxProcsReport);
         }
      }
   }

   /**
    * Set a CPU process report point for this target if it is connected.
    * If the operation is successful, the id of the given CPU process report
    * point object is updated.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param reportPoint      the CPU process report point to be set.
    * @return the id of the CPU process report point set, or 0 if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public int setCPUProcessReportPoint(IProgressMonitor progressMonitor,
                                       CPUProcessReportPoint reportPoint)
      throws IOException
   {
      if ((progressMonitor == null) || (reportPoint == null))
      {
         throw new NullPointerException();
      }

      if (reportPoint.getId() != 0)
      {
         throw new IllegalArgumentException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            int id;

            id = monitorHandler.setCPUProcessReportpointRequest(
                  progressMonitor,
                  reportPoint.getScopeType(),
                  reportPoint.getScopeId(),
                  reportPoint.getTags());
            reportPoint.setId(id);
            return id;
         }
         else
         {
            return 0;
         }
      }
   }

   /**
    * Clear a CPU process report point for this target if it is connected.
    * If the operation is successful, the id of the given CPU process report
    * point object is reset to 0.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param reportPoint      the CPU process report point to be cleared or null
    * to clear all.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void clearCPUProcessReportPoint(IProgressMonitor progressMonitor,
                                          CPUProcessReportPoint reportPoint)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            int id = ((reportPoint != null) ? reportPoint.getId() : ~0);
            monitorHandler.clearCPUProcessReportpointRequest(progressMonitor, id);
            if (reportPoint != null)
            {
               reportPoint.setId(0);
            }
         }
      }
   }

   /**
    * Return an array of the CPU process report points for this target if it is
    * connected. The result is a snapshot of the target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return an array of the CPU process report points for this target, or an
    * empty array if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public CPUProcessReportPoint[] getCPUProcessReportPoints(
         IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUProcessReportpointInfoRequest(progressMonitor);
         }
         else
         {
            return new CPUProcessReportPoint[0];
         }
      }
   }

   /**
    * Return CPU process level load reports for this target if it is connected.
    * Requests are made for a number of CPU process level load reports that
    * have a later time stamp (tick and ntick), but not equal, than that
    * specified. Set tick and ntick to 0 to get all. The result is a snapshot of
    * the target at the time of the call.
    * <p>
    * Typically, a client calls this method periodically with the time stamp
    * (tick and ntick) of the last CPU process level load report from the
    * previous call as argument. To avoid losing reports, the time between each
    * call should not be greater than the CPU process level load reporting
    * integration interval times the size of the target's CPU process level load
    * report buffer in number of CPU process level load reports.
    * <p>
    * Note that some OSE target monitors return all processes that have executed
    * within the integration interval, while other OSE target monitors return a
    * fixed-size subset containing the most time-consuming processes that have
    * executed within the integration interval and those processes explicitly
    * set with setCPUProcessReportPoint().
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit    the execution unit for which to get CPU process
    * level load reports or Target.ALL_EXECUTION_UNITS.
    * @param tick             the initial time stamp in number of ticks.
    * @param ntick            the initial time stamp in number of timer steps
    * since the last tick.
    * @return CPU process level load reports for this target, or null if this
    * target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #ALL_EXECUTION_UNITS
    */
   public CPUProcessReports getCPUProcessReports(IProgressMonitor progressMonitor,
                                                 short executionUnit,
                                                 int tick,
                                                 int ntick)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUProcessReportsRequest(progressMonitor,
                                                              executionUnit,
                                                              tick,
                                                              ntick);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Return CPU block level load report enablement information for this
    * target if it is connected. The result is a snapshot of the target at the
    * time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return CPU block level load report enablement information for this
    * target, or null if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public CPUBlockReportsEnabledInfo getCPUBlockReportsEnabled(
         IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUBlockReportsEnabledRequest(progressMonitor);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Set CPU block level load report enablement information for this target
    * if it is connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled          the CPU block level load reporting enablement
    * status.
    * @param interval         the CPU block level load reporting integration
    * interval in ticks.
    * @param maxReports       the suggested maximum number of CPU block level
    * load reports kept in the target. If 0 the default value of the target will
    * be used. The value will be ignored if it is too large for the target.
    * @param maxBlocksReport  the suggested maximum number of blocks per
    * CPU block level load report.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setCPUBlockReportsEnabled(IProgressMonitor progressMonitor,
                                         boolean enabled,
                                         int interval,
                                         int maxReports,
                                         int maxBlocksReport)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setCPUBlockReportsEnabledRequest(progressMonitor,
                                                            enabled,
                                                            interval,
                                                            maxReports,
                                                            maxBlocksReport);
         }
      }
   }

   /**
    * Return CPU block level load reports for this target if it is connected.
    * Requests are made for a number of CPU block level load reports that
    * have a later time stamp (tick and ntick), but not equal, than that
    * specified. Set tick and ntick to 0 to get all. The result is a snapshot of
    * the target at the time of the call.
    * <p>
    * Typically, a client calls this method periodically with the time stamp
    * (tick and ntick) of the last CPU block level load report from the
    * previous call as argument. To avoid losing reports, the time between each
    * call should not be greater than the CPU block level load reporting
    * integration interval times the size of the target's CPU block level load
    * report buffer in number of CPU block level load reports.
    * <p>
    * Note that some OSE target monitors return all blocks that have executed
    * within the integration interval, while other OSE target monitors return a
    * fixed-size subset containing the most time-consuming blocks that have
    * executed within the integration interval.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit    the execution unit for which to get CPU block
    * level load reports or Target.ALL_EXECUTION_UNITS.
    * @param tick             the initial time stamp in number of ticks.
    * @param ntick            the initial time stamp in number of timer steps
    * since the last tick.
    * @return CPU block level load reports for this target, or null if this
    * target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #ALL_EXECUTION_UNITS
    */
   public CPUBlockReports getCPUBlockReports(IProgressMonitor progressMonitor,
                                             short executionUnit,
                                             int tick,
                                             int ntick)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCPUBlockReportsRequest(progressMonitor,
                                                            executionUnit,
                                                            tick,
                                                            ntick);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Return user report enablement information for the specified user report
    * number for this target if it is connected. The result is a snapshot of the
    * target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param reportNumber     the user report number.
    * @return User report enablement information for the specified user report
    * number for this target, or null if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public UserReportsEnabledInfo getUserReportsEnabled(
         IProgressMonitor progressMonitor,
         int reportNumber)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getUserReportsEnabledRequest(progressMonitor,
                                                               reportNumber);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Set user report enablement information for the specified user report
    * number for this target if it is connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param reportNumber     the user report number.
    * @param enabled          the user reporting enablement status.
    * @param interval         the user reporting integration interval in ticks.
    * @param maxReports       the suggested maximum number of user reports kept
    * in the target. If 0 the default value of the target will be used. The
    * value will be ignored if it is too large for the target.
    * @param maxValuesReport  the suggested maximum number of values per user
    * report.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setUserReportsEnabled(IProgressMonitor progressMonitor,
                                     int reportNumber,
                                     boolean enabled,
                                     int interval,
                                     int maxReports,
                                     int maxValuesReport)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setUserReportsEnabledRequest(progressMonitor,
                                                        reportNumber,
                                                        enabled,
                                                        interval,
                                                        maxReports,
                                                        maxValuesReport);
         }
      }
   }
   
   /**
    * Return user reports for the specified user report number for this target
    * if it is connected. Requests are made for a number of user reports that
    * have a later time stamp (tick and ntick), but not equal, than that
    * specified. Set tick and ntick to 0 to get all. The result is a snapshot of
    * the target at the time of the call.
    * <p>
    * Typically, a client calls this method periodically with the time stamp
    * (tick and ntick) of the last user report from the previous call as
    * argument. To avoid losing reports, the time between each call should not
    * be greater than the user reporting integration interval times the size of
    * the target's user report buffer in number of user reports.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param reportNumber     the user report number.
    * @param tick             the initial time stamp in number of ticks.
    * @param ntick            the initial time stamp in number of timer steps
    * since the last tick.
    * @return User reports for the specified user report number for this target,
    * or null if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public UserReports getUserReports(IProgressMonitor progressMonitor,
                                     int reportNumber,
                                     int tick,
                                     int ntick)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getUserReportsRequest(progressMonitor,
                                                        reportNumber,
                                                        tick,
                                                        ntick);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Return an array of the types of performance counters supported by this
    * target if this target is connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return an array of the types of performance counters supported by this
    * target, or an empty array if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public PerformanceCounterInfo[] getPerformanceCounters(
         IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getSupportedCounterTypesRequest(progressMonitor);
         }
         else
         {
            return new PerformanceCounterInfo[0];
         }
      }
   }

   /**
    * Return enablement information for the specified type of performance
    * counter on the specified execution unit for this target if it is
    * connected. The result is a snapshot of the target at the time of the call.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit  the execution unit where to query the enablement
    * information for the specified type of performance counter.
    * @param type  the type of performance counter to get enablement information
    * for.
    * @return enablement information for the specified type of performance
    * counter on the specified execution unit for this target, or null if this
    * target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public PerformanceCounterEnabledInfo getPerformanceCounterEnabled(
         IProgressMonitor progressMonitor,
         short executionUnit,
         int type)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCounterTypeEnabledRequest(progressMonitor,
                                                               executionUnit,
                                                               type);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Set enablement information for the specified type of performance counter
    * on the specified execution unit for this target if it is connected.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled  true if the specified type of performance counter should
    * be enabled on the specified execution unit, false otherwise.
    * @param executionUnit  the execution unit where to set the enablement
    * information for the specified type of performance counter.
    * @param type  the type of performance counter to set enablement information
    * for.
    * @param value  the trigger value of the enabled performance counter.
    * @param maxReports  the suggested maximum number of performance counter
    * reports kept in the target for the specified performance counter type and
    * execution unit. If 0 the default value of the target will be used. The
    * value will be ignored if it is too large for the target.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setPerformanceCounterEnabled(IProgressMonitor progressMonitor,
                                            boolean enabled,
                                            short executionUnit,
                                            int type,
                                            long value,
                                            int maxReports)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setCounterTypeEnabledRequest(progressMonitor,
                                                        enabled,
                                                        executionUnit,
                                                        type,
                                                        value,
                                                        maxReports);
         }
      }
   }

   /**
    * Add a performance counter report listener. Has no effect if an identical
    * listener has already been added.
    * <p>
    * Performance counter reports will only be received if performance counter
    * report notification on this target is enabled.
    *
    * @param listener  the performance counter report listener to add.
    * @see #setPerformanceCounterNotifyEnabled(IProgressMonitor, boolean, short, int)
    */
   public void addPerformanceCounterListener(PerformanceCounterListener listener)
   {
      reportListenerList.add(listener);
   }

   /**
    * Remove a previously added performance counter report listener. Has no
    * effect if an identical listener has not previously been added.
    *
    * @param listener  the performance counter report listener to remove.
    */
   public void removePerformanceCounterListener(PerformanceCounterListener listener)
   {
      reportListenerList.remove(listener);
   }

   /**
    * Determine whether performance counter report notification is enabled or
    * not for the specified type of performance counter on the specified
    * execution unit on this target.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit  the execution unit where to query the performance
    * counter report notification enablement status for the specified type of
    * performance counter.
    * @param type  the type of performance counter to get performance counter
    * report notification enablement status for.
    * @return true if performance counter report notification is enabled for the
    * specified type of performance counter on the specified execution unit on
    * this target, false otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public boolean isPerformanceCounterNotifyEnabled(
         IProgressMonitor progressMonitor,
         short executionUnit,
         int type)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getCounterReportsNotifyEnabledRequest(
                  progressMonitor, executionUnit, type);
         }
         else
         {
            return false;
         }
      }
   }

   /**
    * Enable/disable performance counter report notification for the specified
    * type of performance counter on the specified execution unit on this
    * target.
    * <p>
    * Add a performance counter report listener with
    * addPerformanceCounterListener() to receive performance counter reports if
    * performance counter report notification on this target is enabled.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param executionUnit  the execution unit where to set the performance
    * counter report notification enablement status for the specified type of
    * performance counter.
    * @param type  the type of performance counter to set performance counter
    * report notification enablement status for.
    * @param enabled  true if performance counter report notification shall be
    * enabled for the specified type of performance counter on the specified
    * execution unit on this target, false otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setPerformanceCounterNotifyEnabled(IProgressMonitor progressMonitor,
                                                  boolean enabled,
                                                  short executionUnit,
                                                  int type)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setCounterReportsNotifyEnabledRequest(progressMonitor,
                                                                 enabled,
                                                                 executionUnit,
                                                                 type);
         }
      }
   }

   /**
    * Attach to a scope on this target. A client is required to attach to a
    * scope before it can intercept and resume processes in that scope, use
    * event action points, receive notification about events, or read the event
    * trace.
    * <p>
    * There are two scopes associated with an attach. The main scope limits the
    * set of processes the client is interested in, i.e. what it wants to see
    * events for, etc. The other scope is the intercept scope, which limits the
    * set of processes that the client can intercept, either directly or through
    * event action points. The intercept scope must always be equal to or a
    * subset of the main scope.
    * <p>
    * If the target supports automatic scope reduction, scopes specified in
    * event action points and to the intercept() and resume() methods, will, if
    * needed, be automatically reduced to the attached main or intercept scope
    * (whichever is applicable) or a subset there of.
    * <p>
    * If the target does not support automatic scope reduction, a client must
    * itself make sure that scopes it specifies in event action points and to
    * the intercept() and resume() methods, are equal to or a subset of the
    * attached main or intercept scope (whichever is applicable).
    *
    * @param progressMonitor     the progress monitor used for cancellation.
    * @param mainScopeType       the main scope type, i.e. one of the
    * Target.SCOPE_* constants.
    * @param mainScopeId         the main scope ID, or 0 if mainScopeType is
    * Target.SCOPE_SYSTEM.
    * @param interceptScopeType  the intercept scope type, i.e. one of the
    * Target.SCOPE_* constants.
    * @param interceptScopeId    the intercept scope ID, or 0 if
    * interceptScopeType is Target.SCOPE_SYSTEM.
    * @param keepEventActionPoints  whether or not to keep event action points
    * for trace after detaching, disconnecting, or a target warm start.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    */
   public void attach(IProgressMonitor progressMonitor,
                      int mainScopeType,
                      int mainScopeId,
                      int interceptScopeType,
                      int interceptScopeId,
                      boolean keepEventActionPoints)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            List tags;
            MonitorTag[] tagsArray;

            if (attached)
            {
               throw new IllegalStateException(
                  "An attempt was made to attach to a target that is already attached.");
            }

            tags = new ArrayList();
            tags.add(new MonitorTag(Monitor.MONITOR_ATTACH_TAG_EVENT_LOCK));
            tags.add(new MonitorTag(Monitor.MONITOR_ATTACH_TAG_EVENT_SIGSELECT));
            if (keepEventActionPoints)
            {
               tags.add(new MonitorTag(Monitor.MONITOR_ATTACH_TAG_KEEP_EVENT_TRACEPOINTS));
            }
            tagsArray = (MonitorTag[]) tags.toArray(new MonitorTag[0]);
            monitorHandler.attachRequest(progressMonitor,
                                         mainScopeType,
                                         mainScopeId,
                                         interceptScopeType,
                                         interceptScopeId,
                                         tagsArray);
            attached = true;
         }
      }
   }

   /**
    * Detach from a previously attached scope on this target.
    * <p>
    * All event action points set will be cleared after detaching. However, if
    * event action points for trace were made persistent when attaching, such
    * event action points will not be cleared.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param resume  true if the attached scope should be resumed after
    * detaching, false otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void detach(IProgressMonitor progressMonitor, boolean resume)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            if (!attached)
            {
               throw new IllegalStateException("Not attached.");
            }
            monitorHandler.detachRequest(progressMonitor, resume);
            attached = false;
         }
      }
   }

   /**
    * Determine whether this target is attached to a scope or not.
    *
    * @return true if this target is attached to a scope, false otherwise.
    */
   public boolean isAttached()
   {
      return attached;
   }

   /**
    * Intercept a scope on this target.
    * <p>
    * Currently, only the intercept scope set when attaching or, if automatic
    * scope reduction is supported, a scope that is a superset of that scope is
    * supported.
    * <p>
    * Only processes in the intersection of the specified scope and the
    * intercept scope set when attaching are intercepted.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param scopeType        the scope type, i.e. one of the Target.SCOPE_*
    * constants.
    * @param scopeId          the scope ID, or 0 if scopeType is
    * Target.SCOPE_SYSTEM.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    */
   public void intercept(IProgressMonitor progressMonitor,
                         int scopeType,
                         int scopeId)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.interceptScopeRequest(progressMonitor,
                                                 scopeType,
                                                 scopeId,
                                                 null);
         }
      }
   }

   /**
    * Resume a scope on this target.
    * <p>
    * Currently, only the intercept scope set when attaching or, if automatic
    * scope reduction is supported, a scope that is a superset of that scope is
    * supported.
    * <p>
    * Only processes in the intersection of the specified scope and the
    * intercept scope set when attaching are resumed.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param scopeType        the scope type, i.e. one of the Target.SCOPE_*
    * constants.
    * @param scopeId          the scope ID, or 0 if scopeType is
    * Target.SCOPE_SYSTEM.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    */
   public void resume(IProgressMonitor progressMonitor,
                      int scopeType,
                      int scopeId)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.resumeScopeRequest(progressMonitor,
                                              scopeType,
                                              scopeId,
                                              null);
         }
      }
   }

   /**
    * Redefine the main scope previously set when attaching.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param scopeType        the main scope type, i.e. one of the
    * Target.SCOPE_* constants.
    * @param scopeId          the main scope ID, or 0 if scopeType is
    * Target.SCOPE_SYSTEM.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    */
   public void setMainScope(IProgressMonitor progressMonitor,
                            int scopeType,
                            int scopeId)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setScopeRequest(progressMonitor,
                                           scopeType,
                                           scopeId,
                                           null);
         }
      }
   }

   /**
    * Redefine the intercept scope previously set when attaching. The intercept
    * scope must be equal to or a subset of the main scope.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param scopeType        the intercept scope type, i.e. one of the
    * Target.SCOPE_* constants.
    * @param scopeId          the intercept scope ID, or 0 if scopeType is
    * Target.SCOPE_SYSTEM.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #SCOPE_SYSTEM
    * @see #SCOPE_SEGMENT_ID
    * @see #SCOPE_BLOCK_ID
    * @see #SCOPE_ID
    */
   public void setInterceptScope(IProgressMonitor progressMonitor,
                                 int scopeType,
                                 int scopeId)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setInterceptScopeRequest(progressMonitor,
                                                    scopeType,
                                                    scopeId,
                                                    null);
         }
      }
   }

   /**
    * Set an event action point for this target. If the operation is successful,
    * the id of the given event action point object is updated.
    * <p>
    * An event action point defines what kind of action should be taken when a
    * certain type of event occurs.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor   the progress monitor used for cancellation.
    * @param eventActionPoint  the event action point to be set.
    * @return the id of the event action point set, or 0 if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public int setEventActionPoint(IProgressMonitor progressMonitor,
                                  EventActionPoint eventActionPoint)
      throws IOException
   {
      if ((progressMonitor == null) || (eventActionPoint == null))
      {
         throw new NullPointerException();
      }

      if (eventActionPoint.getId() != 0)
      {
         throw new IllegalArgumentException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            int id = 0;

            if (eventActionPoint instanceof SendEventActionPoint)
            {
               id = monitorHandler.setSendActionpointRequest(
                     progressMonitor, (SendEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof ReceiveEventActionPoint)
            {
               id = monitorHandler.setReceiveActionpointRequest(
                     progressMonitor, (ReceiveEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof SwapEventActionPoint)
            {
               id = monitorHandler.setSwapActionpointRequest(
                     progressMonitor, (SwapEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof CreateEventActionPoint)
            {
               id = monitorHandler.setCreateActionpointRequest(
                     progressMonitor, (CreateEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof KillEventActionPoint)
            {
               id = monitorHandler.setKillActionpointRequest(
                     progressMonitor, (KillEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof ErrorEventActionPoint)
            {
               id = monitorHandler.setErrorActionpointRequest(
                     progressMonitor, (ErrorEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof UserEventActionPoint)
            {
               id = monitorHandler.setUserActionpointRequest(
                     progressMonitor, (UserEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof BindEventActionPoint)
            {
               id = monitorHandler.setBindActionpointRequest(
                     progressMonitor, (BindEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof AllocEventActionPoint)
            {
               id = monitorHandler.setAllocActionpointRequest(
                     progressMonitor, (AllocEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof FreeEventActionPoint)
            {
               id = monitorHandler.setFreeBufActionpointRequest(
                     progressMonitor, (FreeEventActionPoint) eventActionPoint);
            }
            else if (eventActionPoint instanceof TimeoutEventActionPoint)
            {
               id = monitorHandler.setTimeoutActionpointRequest(
                     progressMonitor, (TimeoutEventActionPoint) eventActionPoint);
            }
            else
            {
               throw new IllegalArgumentException();
            }
            eventActionPoint.setId(id);
            return id;
         }
         else
         {
            return 0;
         }
      }
   }

   /**
    * Clear an event action point for this target. If the operation is
    * successful, the id of the given event action point object is reset to 0.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor   the progress monitor used for cancellation.
    * @param eventActionPoint  the event action point to be cleared or, if
    * clearing all event action points is supported, null to clear all.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    * @see #hasClearAllEventActionPointsSupport()
    */
   public void clearEventActionPoint(IProgressMonitor progressMonitor,
                                     EventActionPoint eventActionPoint)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            int id = ((eventActionPoint != null) ? eventActionPoint.getId() : ~0);
            monitorHandler.clearEventActionpointRequest(progressMonitor, id);
            if (eventActionPoint != null)
            {
               eventActionPoint.setId(0);
            }
         }
      }
   }

   /**
    * Return an array of the event action points for this target.
    * The result is a snapshot of the target at the time of the call.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return an array of the event action points for this target, or an empty
    * array if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public EventActionPoint[] getEventActionPoints(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            if (hasGetEventActionPointsSupport())
            {
               return monitorHandler.getEventActionpointsRequest(progressMonitor);
            }
            else if (hasDeprecatedGetEventActionPointsSupport())
            {
               return monitorHandler.getEventActionpointInfoRequest(progressMonitor);
            }
            else
            {
               return new EventActionPoint[0];
            }
         }
         else
         {
            return new EventActionPoint[0];
         }
      }
   }

   /**
    * Return the active event state for this target.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return the active event state for this target, or 0 if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public int getEventState(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getEventStateRequest(progressMonitor);
         }
         else
         {
            return 0;
         }
      }
   }

   /**
    * Set the active event state for this target.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param state            the new event state to be set for this target.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setEventState(IProgressMonitor progressMonitor, int state)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setEventStateRequest(progressMonitor, state);
         }
      }
   }

   /**
    * Add a target event listener. Has no effect if an identical listener has
    * already been added.
    * <p>
    * Target events will only be received if event notification on this target
    * is enabled.
    *
    * @param listener  the target event listener to add.
    * @see #setEventNotifyEnabled(IProgressMonitor, boolean)
    */
   public void addTargetListener(TargetListener listener)
   {
      eventListenerList.add(listener);
   }

   /**
    * Remove a previously added target event listener. Has no effect if an
    * identical listener has not previously been added.
    *
    * @param listener  the target event listener to remove.
    */
   public void removeTargetListener(TargetListener listener)
   {
      eventListenerList.remove(listener);
   }

   /**
    * Determine whether event notification on this target is enabled or not.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return true if event notification on this target is enabled, false
    * otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public boolean isEventNotifyEnabled(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getNotifyEnabledRequest(progressMonitor);
         }
         else
         {
            return false;
         }
      }
   }

   /**
    * Enable/disable event notification on this target.
    * <p>
    * Add a target event listener with addTargetListener() to receive target
    * events if event notification on this target is enabled.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled  true if event notification on this target shall be
    * enabled, false otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setEventNotifyEnabled(IProgressMonitor progressMonitor,
                                     boolean enabled)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setNotifyEnabledRequest(progressMonitor, enabled);
         }
      }
   }

   /**
    * Determine whether event tracing on this target is enabled or not.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @return true if event tracing on this target is enabled, false otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public boolean isEventTraceEnabled(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getTraceEnabledRequest(progressMonitor);
         }
         else
         {
            return false;
         }
      }
   }

   /**
    * Enable/disable event tracing on this target.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param enabled  true if event tracing on this target shall be enabled,
    * false otherwise.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void setEventTraceEnabled(IProgressMonitor progressMonitor,
                                    boolean enabled)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.setTraceEnabledRequest(progressMonitor, enabled);
         }
      }
   }

   /**
    * Clear the event trace on this target.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public void clearEventTrace(IProgressMonitor progressMonitor)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            monitorHandler.clearTraceRequest(progressMonitor);
         }
      }
   }

   /**
    * Return an array of a specified set of events from this target's event
    * trace. The result is a snapshot of the target at the time of the call.
    * <p>
    * Available events in the event trace are numbered from 0 to N - 1, where 0
    * is the oldest event and N is the total number of events in the trace.
    * <p>
    * When reading the event trace with this method, it is automatically
    * disabled to protect the parts being read from being overwritten. The event
    * trace needs to be explicitly enabled again if needed.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param from  the index of the first event to read, inclusive. If this
    * index is greater than N, an unspecified (but reasonable) number of events
    * from the end of the trace will be returned.
    * @param to  the index of the last event to read, exclusive. If this index
    * is greater than N, events up to the end of the event trace will be
    * returned.
    * @return an array of the events in the specified interval from this
    * target's event trace, or an empty array if this target is disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public TargetEvent[] getEventTrace(IProgressMonitor progressMonitor,
                                      int from,
                                      int to)
      throws IOException
   {
      if (progressMonitor == null)
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getTraceRequest(progressMonitor, from, to, null);
         }
         else
         {
            return new TargetEvent[0];
         }
      }
   }

   /**
    * Return events from this target's event trace. The result is a snapshot of
    * the target at the time of the call.
    * <p>
    * This method does not disable the event trace while reading it, is more
    * effecient for high-latency target communication channels than
    * getEventTrace(), and is suitable for continuosly reading the event trace.
    * <p>
    * If the specified timeout is set to 0 then the event trace is read
    * immediately. If the timeout is set to -1 then the event trace is read when
    * a suitable amount of events has been written to it. In all other cases,
    * the event trace is read when a suitable amount of events has been written
    * to it or the timeout expires.
    * <p>
    * For the first call of this method in a sequence of related calls, pass a
    * newly created event trace handle object to read the oldest events in the
    * event trace. Subsequent calls shall use the same event trace handle object
    * in order to get only new events that has been written to the event trace.
    * <p>
    * If events are lost due to a high event rate when event tracing is enabled
    * then a LossEvent is inserted in the returned event stream.
    * <p>
    * Requires that a scope on this target has been attached to.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param timeout          the timeout in milliseconds.
    * @param handle           the event trace handle.
    * @return events from this target's event trace, or null if this target is
    * disconnected.
    * @throws IOException  if an I/O exception occurred; target is
    * automatically disconnected.
    * @throws ServiceException  if the monitor service reported an error.
    * @throws OperationCanceledException  if the operation was interrupted or
    * cancelled; target is automatically disconnected.
    */
   public TargetEvents getEventTraceMultiple(IProgressMonitor progressMonitor,
                                             int timeout,
                                             EventTraceHandle handle)
      throws IOException
   {
      if ((progressMonitor == null) || (handle == null))
      {
         throw new NullPointerException();
      }

      synchronized (this)
      {
         if (!isKilled())
         {
            return monitorHandler.getTraceMultipleRequest(progressMonitor,
                                                          timeout,
                                                          handle);
         }
         else
         {
            return null;
         }
      }
   }

   /**
    * Return a string representation of this target object. The returned string
    * is of the form "&lt;target-name&gt; 
    * (&lt;gate-IP-address&gt;:&lt;gate-port&gt;/[&lt;hunt-path-prefix&gt;])".
    *
    * @return a string representation of this target object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      // Final field, no synchronization needed.
      return extendedName;
   }

   /**
    * This class handles all transactions with the OSE monitor on this target
    * (except monitor creation/connection/disconnection), i.e. all requests sent
    * to and all replies received from the OSE monitor (except as noted) are
    * handled by this class. There is one instance of this class for a target
    * instance.
    * <p>
    * Only one on-going transaction with the OSE monitor is permitted at a time.
    * The request methods are called by client threads; they start a new
    * transaction by performing the actual request and then wait (with periodic
    * cancellation polling) for the corresponding last reply. The reply call-
    * back methods are called by the monitor receiver thread; they update the
    * OSE system model if applicable, and notifies the client thread that the
    * last reply has been received and that the transaction has thus ended.
    *
    * @see MonitorListener
    */
   class MonitorHandler implements MonitorListener
   {
      private static final long WAIT_TIMEOUT = 1000L;

      private final Object lock;
      private boolean done;
      private long timeStamp;
      private int status;
      private Object parent;
      private boolean gotReplies;
      private final ArrayList changedObjects;
      private final ArrayList addedObjects;

      /**
       * Create the monitor handler object for this target.
       */
      MonitorHandler()
      {
         lock = new Object();
         done = false;
         timeStamp = 0;
         status = MonitorStatus.MONITOR_STATUS_OK;
         parent = null;
         gotReplies = false;
         changedObjects = new ArrayList();
         addedObjects = new ArrayList();
      }

      /**
       * Return a list of the nodes in the given list that are direct children
       * to the given parent node and remove those nodes from the given list.
       *
       * @param parent   the parent node.
       * @param objects  a list of nodes.
       * @return a list of the nodes in the given list that are direct children
       * to the given parent node (never null).
       */
      private List getChildren(SystemModelNode parent, List objects)
      {
         List children = new ArrayList();
         for (Iterator i = objects.iterator(); i.hasNext();)
         {
            SystemModelNode node = (SystemModelNode) i.next();
            if (node.getParent() == parent)
            {
               children.add(node);
               i.remove();
            }
         }
         return children;
      }

      /*************************************************************************
       * Transaction methods.
       ************************************************************************/

      /**
       * Initialize a transaction with the OSE monitor.
       * Called by a client thread.
       *
       * @param parent  the parent object of the transaction or null if not
       * applicable.
       */
      private void init(Object parent)
      {
         done = false;
         timeStamp = System.currentTimeMillis();
         status = MonitorStatus.MONITOR_STATUS_OK;
         this.parent = parent;
         gotReplies = false;
         changedObjects.clear();
         changedObjects.trimToSize();
         addedObjects.clear();
         addedObjects.trimToSize();
      }

      /**
       * Wait for the end of a transaction with the OSE monitor.
       * Called by a client thread.
       *
       * @param progressMonitor    the progress monitor used for cancellation.
       * @throws ServiceException  if the OSE monitor reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled (target is automatically disconnected), or the OSE monitor
       * has been disconnected.
       */
      private void waitForEndmark(IProgressMonitor progressMonitor)
         throws ServiceException
      {
         while (!done)
         {
            try
            {
               lock.wait(WAIT_TIMEOUT);
            }
            catch (InterruptedException e)
            {
               progressMonitor.setCanceled(true);
            }
            if (progressMonitor.isCanceled())
            {
               // Disconnect from the target if the request has been cancelled.
               // This is the only safe way to handle cancellation since there
               // is no general framework for cancelling ongoing signal
               // transactions in OSE.
               disconnect();
               throw new OperationCanceledException();
            }
            if (!monitor.isConnected())
            {
               // The monitor has been disconnected, cancel the operation.
               throw new OperationCanceledException();
            }
         }

         if (status != MonitorStatus.MONITOR_STATUS_OK)
         {
            throw new ServiceException(
                  SERVICE_MONITOR_ID, status, MonitorStatus.getMessage(status));
         }
      }

      /**
       * Notify the end of a transaction with the OSE monitor.
       * Called by the monitor receiver thread.
       *
       * @param status  the status of the transaction.
       */
      private void notifyEndmark(int status)
      {
         synchronized (lock)
         {
            this.status = status;
            done = true;
            lock.notifyAll();
         }
      }

      /*************************************************************************
       * Request methods.
       ************************************************************************/

      /**
       * Request for process information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a target,
       * segment, block, or process.
       * @param level            the reply level; 0 for regular reply or 1 for
       * long reply.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getProcessInfoRequest(IProgressMonitor progressMonitor,
                                              Object parent,
                                              int level,
                                              int scopeType,
                                              int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getProcessInfoRequest(level, scopeType, scopeId, null);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving process information: " +
                                     e.getMessage());
            }
         }
      }

      /**
       * Request for filtered process information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the process filter, or null if all processes in
       * the specified scope should be returned.
       * @return an array of the processes in the specified scope that match the
       * given process filter (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized ProcessInfo[] getFilteredProcessInfoRequest(
            IProgressMonitor progressMonitor,
            int scopeType,
            int scopeId,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getProcessInfoRequest(1, scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving process information: " +
                                     e.getMessage());
            }
            return (ProcessInfo[]) addedObjects.toArray(new ProcessInfo[0]);
         }
      }

      /**
       * Request for block information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a target,
       * segment, or block.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getBlockInfoRequest(IProgressMonitor progressMonitor,
                                            Object parent,
                                            int scopeType,
                                            int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getBlockInfoRequest(scopeType, scopeId, null);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving block information: " +
                                     e.getMessage());
            }
         }
      }

      /**
       * Request for filtered block information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the block filter, or null if all blocks in the
       * specified scope should be returned.
       * @return an array of the blocks in the specified scope that match the
       * given block filter (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized BlockInfo[] getFilteredBlockInfoRequest(
            IProgressMonitor progressMonitor,
            int scopeType,
            int scopeId,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getBlockInfoRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving block information: " +
                                     e.getMessage());
            }
            return (BlockInfo[]) addedObjects.toArray(new BlockInfo[0]);
         }
      }

      /**
       * Request for segment information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a target
       * or segment.
       * @param level            the reply level; 0 for regular reply or 1 for
       * long reply.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getSegmentInfoRequest(IProgressMonitor progressMonitor,
                                              Object parent,
                                              int level,
                                              int scopeType,
                                              int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getSegmentInfoRequest(level, scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving segment information: " +
                                     e.getMessage());
            }
         }
      }

      /**
       * Request for filtered process stack usage.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the stack filter, or null if all stacks in the
       * specified scope should be returned.
       * @return an array of the stacks in the specified scope that match the
       * given stack filter (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized StackInfo[] getFilteredStackUsageRequest(
            IProgressMonitor progressMonitor,
            int scopeType,
            int scopeId,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getStackUsageRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving process stack usage information: " +
                     e.getMessage());
            }
            return (StackInfo[]) addedObjects.toArray(new StackInfo[0]);
         }
      }

      /**
       * Request for block/process environment variables.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a block
       * or process.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getEnvVarsRequest(IProgressMonitor progressMonitor,
                                          Object parent,
                                          int scopeType,
                                          int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getEnvVarsRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving block/process environment variables: " +
                     e.getMessage());
            }
         }
      }

      /**
       * Request for process signal queue information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a process.
       * @param level            the reply level; 0 for regular reply or 1 for
       * long reply.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getSignalQueueRequest(IProgressMonitor progressMonitor,
                                              Object parent,
                                              int level,
                                              int scopeType,
                                              int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getSignalQueueRequest(level, scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving process signal queue information: " +
                     e.getMessage());
            }
         }
      }

      /**
       * Request for pool information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a target,
       * segment, or pool.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getPoolInfoRequest(IProgressMonitor progressMonitor,
                                           Object parent,
                                           int scopeType,
                                           int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getPoolInfoRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving pool information: " +
                                     e.getMessage());
            }
         }
      }

      /**
       * Request for filtered pool signal information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the signal filter, or null if all signals in
       * the specified pool should be returned.
       * @return an array of the signals in the specified pool that match the
       * given signal filter (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized SignalInfo[] getFilteredPoolSignalsRequest(
            IProgressMonitor progressMonitor,
            int scopeType,
            int scopeId,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getPoolSignalsRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving pool signal information: " +
                     e.getMessage());
            }
            return (SignalInfo[]) addedObjects.toArray(new SignalInfo[0]);
         }
      }

      /**
       * Request for heap information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent object of the transaction; a target,
       * segment, or heap.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getHeapInfoRequest(IProgressMonitor progressMonitor,
                                           Object parent,
                                           int scopeType,
                                           int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getHeapInfoRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving heap information: " +
                                     e.getMessage());
            }
         }
      }

      /**
       * Request for heap fragment information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param parent           the parent heap of the transaction.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getHeapFragmentInfoRequest(
            IProgressMonitor progressMonitor,
            Object parent,
            int scopeType,
            int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(parent);
            try
            {
               monitor.getHeapFragmentInfoRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving heap fragment information: " +
                     e.getMessage());
            }
         }
      }

      /**
       * Request for filtered heap buffer information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the heap buffer filter, or null if all heap
       * buffers in the specified heap should be returned.
       * @return an array of the heap buffers in the specified heap that match
       * the given heap buffer filter (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized HeapBufferInfo[] getFilteredHeapBuffersRequest(
            IProgressMonitor progressMonitor,
            int scopeType,
            int scopeId,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getHeapBuffersRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving heap buffer information: " +
                     e.getMessage());
            }
            return (HeapBufferInfo[]) addedObjects.toArray(new HeapBufferInfo[0]);
         }
      }

      /**
       * Request for system parameters.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getSysParamRequest(IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getSysParamRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving system parameters: " +
                                     e.getMessage());
            }
         }
      }

      /**
       * Request for setting a system parameter.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param reconfigure  if true and if the system parameter is read by the
       * OSE monitor itself, then reread and reconfigure that parameter in the
       * OSE monitor, after setting it.
       * @param name   the name of the system parameter.
       * @param value  the value of the system parameter or null to remove the
       * system parameter.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void setSysParamRequest(IProgressMonitor progressMonitor,
                                           boolean reconfigure,
                                           String name,
                                           String value)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setSysParamRequest(reconfigure, name, value);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for reading memory.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param pid      process/block/segment ID; the address is in this context.
       * @param address  memory address to start reading from.
       * @param size     number of bytes to attempt to read.
       * @return a byte array of the retrieved memory (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized byte[] getMemoryRequest(IProgressMonitor progressMonitor,
                                           int pid,
                                           long address,
                                           long size)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            int length = 0;
            byte[] memory;

            init(null);
            try
            {
               monitor.getMemoryRequest(pid, address, size);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               if (!gotReplies)
               {
                  throw e;
               }
            }

            for (Iterator i = addedObjects.iterator(); i.hasNext();)
            {
               byte[] chunk = (byte[]) i.next();
               length += chunk.length;
            }
            memory = new byte[length];
            length = 0;
            for (Iterator i = addedObjects.iterator(); i.hasNext();)
            {
               byte[] chunk = (byte[]) i.next();
               System.arraycopy(chunk, 0, memory, length, chunk.length);
               length += chunk.length;
            }
            return memory;
         }
      }

      /**
       * Request for killing a segment/block/process.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void killScopeRequest(IProgressMonitor progressMonitor,
                                         int scopeType,
                                         int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.killScopeRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for starting a segment/block/process.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void startScopeRequest(IProgressMonitor progressMonitor,
                                          int scopeType,
                                          int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.startScopeRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for stopping a segment/block/process
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void stopScopeRequest(IProgressMonitor progressMonitor,
                                         int scopeType,
                                         int scopeId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.stopScopeRequest(scopeType, scopeId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for setting a string-valued environment variable on a
       * block/process.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param pid    ID of a block/process.
       * @param name   the name of the environment variable.
       * @param value  the string-value of the environment variable or null to
       * remove the environment variable.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void setEnvVarRequest(IProgressMonitor progressMonitor,
                                         int pid,
                                         String name,
                                         String value)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setEnvVarRequest(pid, name, value);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for setting a pointer-valued environment variable on a
       * block/process.
       *
       * @param pid    ID of a block/process.
       * @param name   the name of the environment variable.
       * @param value  the pointer-value of the environment variable.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void setEnvVarRequest(int pid, String name, int value)
         throws ServiceException, IOException
      {
         // TODO: Not yet supported.
      }

      /**
       * Request for signalling the fast semaphore of a process.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param pid              the process ID.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void signalSemaphoreRequest(IProgressMonitor progressMonitor,
                                               int pid)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.signalSemaphoreRequest(pid);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for setting the priority of a process.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param pid              the process ID.
       * @param priority         the priority.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void setPriorityRequest(IProgressMonitor progressMonitor,
                                           int pid,
                                           int priority)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setPriorityRequest(pid, priority);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for setting the execution unit of a block/process.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param pid              the block/process ID.
       * @param euId             the execution unit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void setExecutionUnitRequest(IProgressMonitor progressMonitor,
                                                int pid,
                                                short euId)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setExecutionUnitRequest(pid, euId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for dump information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param from             the index of the first dump, inclusive.
       * @param to               the index of the last dump, exclusive.
       * @return an array of the dumps in the specified interval (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized DumpInfo[] getRamdumpInfoRequest(
            IProgressMonitor progressMonitor,
            int from,
            int to)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getRamdumpInfoRequest(from, to);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving dump information: " +
                                     e.getMessage());
            }
            return (DumpInfo[]) addedObjects.toArray(new DumpInfo[0]);
         }
      }

      /**
       * Request for dump retrieval.
       *
       * @param progressMonitor  the progress monitor used for cancellation and
       * updating.
       * @param dumpId           the id of the dump to retrieve.
       * @param out              the output stream where to write the retrieved
       * dump.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled; target is automatically disconnected.
       */
      synchronized void getRamdumpRequest(IProgressMonitor progressMonitor,
                                          int dumpId,
                                          OutputStream out)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               addedObjects.add(out);
               addedObjects.add(progressMonitor);
               monitor.getRamdumpRequest(dumpId);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for CPU load report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return CPU load report enablement information for this target
       * (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUReportsEnabledInfo getCPUReportsEnabledRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUReportsEnabledRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUReportsEnabledInfo) addedObjects.get(0);
         }
      }

      /**
       * Request for setting CPU load report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled          the enablement status.
       * @param interval         the integration interval in ticks.
       * @param priority         the priority limit.
       * @param maxReports       the max reports limit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setCPUReportsEnabledRequest(
            IProgressMonitor progressMonitor,
            boolean enabled,
            int interval,
            int priority,
            int maxReports)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCPUReportsEnabledRequest(enabled,
                                                   interval,
                                                   priority,
                                                   maxReports);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for CPU load reports.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param euId             the execution unit for which to get CPU load
       * reports.
       * @param tick             the initial time stamp in number of ticks.
       * @param ntick            the initial time stamp in number of timer steps
       * since the last tick.
       * @return CPU load reports for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUReports getCPUReportsRequest(
            IProgressMonitor progressMonitor,
            short euId,
            int tick,
            int ntick)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUReportsRequest(euId, tick, ntick);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUReports) addedObjects.get(0);
         }
      }

      /**
       * Request for CPU priority level load report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return CPU priority level load report enablement information for this
       * target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUPriorityReportsEnabledInfo getCPUPriReportsEnabledRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUPriReportsEnabledRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUPriorityReportsEnabledInfo) addedObjects.get(0);
         }
      }

      /**
       * Request for setting CPU priority level load report enablement
       * information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled          the enablement status.
       * @param interval         the integration interval in ticks.
       * @param maxReports       the max reports limit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setCPUPriReportsEnabledRequest(
            IProgressMonitor progressMonitor,
            boolean enabled,
            int interval,
            int maxReports)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCPUPriReportsEnabledRequest(enabled,
                                                      interval,
                                                      maxReports);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for CPU priority level load reports.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param euId             the execution unit for which to get CPU
       * priority level load reports.
       * @param tick             the initial time stamp in number of ticks.
       * @param ntick            the initial time stamp in number of timer steps
       * since the last tick.
       * @return CPU priority level load reports for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUPriorityReports getCPUPriReportsRequest(
            IProgressMonitor progressMonitor,
            short euId,
            int tick,
            int ntick)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUPriReportsRequest(euId, tick, ntick);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUPriorityReports) addedObjects.get(0);
         }
      }

      /**
       * Request for CPU process level load report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return CPU process level load report enablement information for this
       * target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUProcessReportsEnabledInfo getCPUProcessReportsEnabledRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUProcessReportsEnabledRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUProcessReportsEnabledInfo) addedObjects.get(0);
         }
      }

      /**
       * Request for setting CPU process level load report enablement
       * information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled          the enablement status.
       * @param interval         the integration interval in ticks.
       * @param maxReports       the max reports limit.
       * @param maxProcsReport   the max processes per report limit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setCPUProcessReportsEnabledRequest(
            IProgressMonitor progressMonitor,
            boolean enabled,
            int interval,
            int maxReports,
            int maxProcsReport)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCPUProcessReportsEnabledRequest(enabled,
                                                          interval,
                                                          maxReports,
                                                          maxProcsReport,
                                                          false,
                                                          (byte) 0,
                                                          hasPerCoreProfiledProcessesSupport());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for setting a CPU process report point.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the monitor tags, can be null.
       * @return the id of the CPU process report point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setCPUProcessReportpointRequest(
            IProgressMonitor progressMonitor,
            int scopeType,
            int scopeId,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCPUProcessReportpointRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for clearing a CPU process report point.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param id               the id of the CPU process report point to be
       * cleared or ~0 to clear all.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void clearCPUProcessReportpointRequest(
            IProgressMonitor progressMonitor,
            int id)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.clearCPUProcessReportpointRequest(id);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for CPU process report points.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return an array of the CPU process report points for this target
       * (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUProcessReportPoint[] getCPUProcessReportpointInfoRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUProcessReportpointInfoRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log(
                     "Error retrieving CPU process report points: " +
                     e.getMessage());
            }
            return (CPUProcessReportPoint[])
               addedObjects.toArray(new CPUProcessReportPoint[0]);
         }
      }

      /**
       * Request for CPU process level load reports.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param euId             the execution unit for which to get CPU
       * process level load reports.
       * @param tick             the initial time stamp in number of ticks.
       * @param ntick            the initial time stamp in number of timer steps
       * since the last tick.
       * @return CPU process level load reports for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUProcessReports getCPUProcessReportsRequest(
            IProgressMonitor progressMonitor,
            short euId,
            int tick,
            int ntick)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUProcessReportsRequest(euId, tick, ntick);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUProcessReports) addedObjects.get(0);
         }
      }

      /**
       * Request for CPU block level load report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return CPU block level load report enablement information for this
       * target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUBlockReportsEnabledInfo getCPUBlockReportsEnabledRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUBlockReportsEnabledRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUBlockReportsEnabledInfo) addedObjects.get(0);
         }
      }

      /**
       * Request for setting CPU block level load report enablement
       * information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled          the enablement status.
       * @param interval         the integration interval in ticks.
       * @param maxReports       the max reports limit.
       * @param maxBlocksReport  the max blocks per report limit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setCPUBlockReportsEnabledRequest(
            IProgressMonitor progressMonitor,
            boolean enabled,
            int interval,
            int maxReports,
            int maxBlocksReport)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCPUBlockReportsEnabledRequest(enabled,
                                                        interval,
                                                        maxReports,
                                                        maxBlocksReport,
                                                        false,
                                                        (byte) 0,
                                                        hasPerCoreProfiledProcessesSupport());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for CPU block level load reports.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param euId             the execution unit for which to get CPU block
       * level load reports.
       * @param tick             the initial time stamp in number of ticks.
       * @param ntick            the initial time stamp in number of timer steps
       * since the last tick.
       * @return CPU block level load reports for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized CPUBlockReports getCPUBlockReportsRequest(
            IProgressMonitor progressMonitor,
            short euId,
            int tick,
            int ntick)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCPUBlockReportsRequest(euId, tick, ntick);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (CPUBlockReports) addedObjects.get(0);
         }
      }

      /**
       * Request for user report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param reportNo         the user report number.
       * @return User report enablement information for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized UserReportsEnabledInfo getUserReportsEnabledRequest(
            IProgressMonitor progressMonitor,
            int reportNo)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getUserReportsEnabledRequest(reportNo);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (UserReportsEnabledInfo) addedObjects.get(0);
         }
      }

      /**
       * Request for setting user report enablement information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param reportNo         the user report number.
       * @param enabled          the enablement status.
       * @param interval         the integration interval in ticks.
       * @param maxReports       the max reports limit.
       * @param maxValuesReport  the max values per report limit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setUserReportsEnabledRequest(
            IProgressMonitor progressMonitor,
            int reportNo,
            boolean enabled,
            int interval,
            int maxReports,
            int maxValuesReport)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setUserReportsEnabledRequest(reportNo,
                                                    enabled,
                                                    interval,
                                                    maxReports,
                                                    maxValuesReport);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for user reports.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param reportNo         the user report number.
       * @param tick             the initial time stamp in number of ticks.
       * @param ntick            the initial time stamp in number of timer steps
       * since the last tick.
       * @return User reports for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized UserReports getUserReportsRequest(
            IProgressMonitor progressMonitor,
            int reportNo,
            int tick,
            int ntick)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getUserReportsRequest(reportNo, tick, ntick);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (UserReports) addedObjects.get(0);
         }
      }

      /**
       * Request for retrieving the supported performance counter types.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return an array of the performance counter types supported by this
       * target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized PerformanceCounterInfo[] getSupportedCounterTypesRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getSupportedCounterTypesRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving supported counter types: " +
                                     e.getMessage());
            }
            return (PerformanceCounterInfo[])
               addedObjects.toArray(new PerformanceCounterInfo[0]);
         }
      }

      /**
       * Request for enablement information for the specified type of
       * performance counter on the specified execution unit.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param euId  the execution unit.
       * @param counterType  the performance counter type.
       * @return enablement information for the specified type of performance
       * counter on the specified execution unit for this target (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized PerformanceCounterEnabledInfo getCounterTypeEnabledRequest(
            IProgressMonitor progressMonitor,
            short euId,
            int counterType)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCounterTypeEnabledRequest(euId, counterType);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return (PerformanceCounterEnabledInfo) addedObjects.get(0);
         }
      }

      /**
       * Request for setting enablement information for the specified type of
       * performance counter on the specified execution unit.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled  performance counter type enablement status.
       * @param euId  the execution unit.
       * @param counterType  the performance counter type.
       * @param counterValue  the trigger value of the enabled performance
       * counter.
       * @param maxReports  the max reports limit.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setCounterTypeEnabledRequest(
            IProgressMonitor progressMonitor,
            boolean enabled,
            short euId,
            int counterType,
            long counterValue,
            int maxReports)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCounterTypeEnabledRequest(enabled,
                                                    euId,
                                                    counterType,
                                                    counterValue,
                                                    maxReports);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for performance counter report notification enablement status
       * for the specified type of performance counter on the specified
       * execution unit.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param euId  the execution unit.
       * @param counterType  the performance counter type.
       * @return performance counter report notification enablement status for
       * the specified type of performance counter on the specified execution
       * unit for this target.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized boolean getCounterReportsNotifyEnabledRequest(
            IProgressMonitor progressMonitor,
            short euId,
            int counterType)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getCounterReportsNotifyEnabledRequest(euId, counterType);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Boolean) addedObjects.get(0)).booleanValue();
         }
      }

      /**
       * Request for enabling/disabling performance counter report notification
       * for the specified type of performance counter on the specified
       * execution unit.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled  performance counter report notification enablement
       * status.
       * @param euId  the execution unit.
       * @param counterType  the performance counter type.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setCounterReportsNotifyEnabledRequest(
            IProgressMonitor progressMonitor,
            boolean enabled,
            short euId,
            int counterType)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCounterReportsNotifyEnabledRequest(
                     enabled, euId, counterType);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for attaching to a scope.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the main scope type.
       * @param scopeId          the main scope ID.
       * @param interceptType    the intercept scope type.
       * @param interceptId      the intercept scope ID.
       * @param tags             the monitor tags, can be null.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void attachRequest(IProgressMonitor progressMonitor,
                                      int scopeType,
                                      int scopeId,
                                      int interceptType,
                                      int interceptId,
                                      MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.attachRequest(scopeType,
                                     scopeId,
                                     interceptType,
                                     interceptId,
                                     tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for detaching from a scope.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param resume           true if the attached scope should be resumed,
       * false otherwise.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void detachRequest(IProgressMonitor progressMonitor,
                                      boolean resume)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.detachRequest(resume);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for intercepting a scope.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the monitor tags, can be null.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void interceptScopeRequest(IProgressMonitor progressMonitor,
                                              int scopeType,
                                              int scopeId,
                                              MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.interceptScopeRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for resuming a scope.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the monitor tags, can be null.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void resumeScopeRequest(IProgressMonitor progressMonitor,
                                           int scopeType,
                                           int scopeId,
                                           MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.resumeScopeRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for redefining the attached main scope.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the monitor tags, can be null.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setScopeRequest(IProgressMonitor progressMonitor,
                                        int scopeType,
                                        int scopeId,
                                        MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setScopeRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for redefining the attached intercept scope.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param scopeType        the scope type.
       * @param scopeId          the scope ID.
       * @param tags             the monitor tags, can be null.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setInterceptScopeRequest(IProgressMonitor progressMonitor,
                                                 int scopeType,
                                                 int scopeId,
                                                 MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setInterceptScopeRequest(scopeType, scopeId, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for setting a send event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the send event action point to be set.
       * @return the id of the send event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setSendActionpointRequest(
            IProgressMonitor progressMonitor,
            SendEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setSendActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags(monitor.isBigEndian()));
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a receive event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the receive event action point to be set.
       * @return the id of the receive event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setReceiveActionpointRequest(
            IProgressMonitor progressMonitor,
            ReceiveEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setReceiveActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags(monitor.isBigEndian()));
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a swap event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the swap event action point to be set.
       * @return the id of the swap event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setSwapActionpointRequest(
            IProgressMonitor progressMonitor,
            SwapEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setSwapActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a create event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the create event action point to be set.
       * @return the id of the create event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setCreateActionpointRequest(
            IProgressMonitor progressMonitor,
            CreateEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setCreateActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a kill event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the kill event action point to be set.
       * @return the id of the kill event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setKillActionpointRequest(
            IProgressMonitor progressMonitor,
            KillEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setKillActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting an error event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the error event action point to be set.
       * @return the id of the error event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setErrorActionpointRequest(
            IProgressMonitor progressMonitor,
            ErrorEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setErrorActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a user event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the user event action point to be set.
       * @return the id of the user event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setUserActionpointRequest(
            IProgressMonitor progressMonitor,
            UserEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setUserActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags(monitor.isBigEndian()));
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a bind event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the bind event action point to be set.
       * @return the id of the bind event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setBindActionpointRequest(
            IProgressMonitor progressMonitor,
            BindEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setBindActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting an alloc event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the alloc event action point to be set.
       * @return the id of the alloc event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setAllocActionpointRequest(
            IProgressMonitor progressMonitor,
            AllocEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setAllocActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a free event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the free event action point to be set.
       * @return the id of the free event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setFreeBufActionpointRequest(
            IProgressMonitor progressMonitor,
            FreeEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setFreeBufActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags(monitor.isBigEndian()));
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting a timeout event action point.
       *
       * @param progressMonitor   the progress monitor used for cancellation.
       * @param eventActionPoint  the timeout event action point to be set.
       * @return the id of the timeout event action point set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int setTimeoutActionpointRequest(
            IProgressMonitor progressMonitor,
            TimeoutEventActionPoint eventActionPoint)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setTimeoutActionpointRequest(
                     eventActionPoint.getState(),
                     eventActionPoint.getFromScopeType(),
                     eventActionPoint.getFromScopeId(),
                     eventActionPoint.getToScopeType(),
                     eventActionPoint.getToScopeId(),
                     eventActionPoint.isNot(),
                     eventActionPoint.getAction(),
                     eventActionPoint.getInterceptScopeType(),
                     eventActionPoint.getInterceptScopeId(),
                     eventActionPoint.getParameter(),
                     eventActionPoint.getClient(),
                     eventActionPoint.getId(),
                     eventActionPoint.getCount(),
                     eventActionPoint.getTags());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for clearing an event action point.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param id               the id of the event action point to be cleared
       * or ~0 to clear all.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void clearEventActionpointRequest(
            IProgressMonitor progressMonitor,
            int id)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.clearEventActionpointRequest(id);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for event action points.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return an array of the event action points for this target (never
       * null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized EventActionPoint[] getEventActionpointInfoRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getEventActionpointInfoRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving event action points: " +
                                     e.getMessage());
            }
            return (EventActionPoint[])
               addedObjects.toArray(new EventActionPoint[0]);
         }
      }

      /**
       * Request for event action points.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return an array of the event action points for this target (never
       * null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized EventActionPoint[] getEventActionpointsRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getEventActionpointsRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving event action points: " +
                                     e.getMessage());
            }
            return (EventActionPoint[])
               addedObjects.toArray(new EventActionPoint[0]);
         }
      }

      /**
       * Request for the active event state.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return the active event state.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized int getEventStateRequest(IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getEventStateRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Integer) addedObjects.get(0)).intValue();
         }
      }

      /**
       * Request for setting the active event state.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param state            the new event state to be set.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setEventStateRequest(IProgressMonitor progressMonitor,
                                             int state)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setEventStateRequest(state);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for event notification enablement status.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return event notification enablement status.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized boolean getNotifyEnabledRequest(IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getNotifyEnabledRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Boolean) addedObjects.get(0)).booleanValue();
         }
      }

      /**
       * Request for enabling/disabling event notification.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled  event notification enablement status.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setNotifyEnabledRequest(IProgressMonitor progressMonitor,
                                                boolean enabled)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setNotifyEnabledRequest(enabled);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for event trace enablement status.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return event trace enablement status.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized boolean getTraceEnabledRequest(IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getTraceEnabledRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            return ((Boolean) addedObjects.get(0)).booleanValue();
         }
      }

      /**
       * Request for enabling/disabling the event trace.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param enabled  event trace enablement status.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void setTraceEnabledRequest(IProgressMonitor progressMonitor,
                                               boolean enabled)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.setTraceEnabledRequest(enabled);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for clearing the event trace.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized void clearTraceRequest(IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.clearTraceRequest();
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
         }
      }

      /**
       * Request for the event trace.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param from             the index of the first event, inclusive.
       * @param to               the index of the last event, exclusive.
       * @param tags             the monitor tags, can be null.
       * @return an array of the events in the specified interval (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized TargetEvent[] getTraceRequest(
            IProgressMonitor progressMonitor,
            int from,
            int to,
            MonitorTag[] tags)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getTraceRequest(from, to, tags);
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            try
            {
               waitForEndmark(progressMonitor);
            }
            catch (ServiceException e)
            {
               /* Treat partial success as full success. */
               // FIXME: Somehow inform the caller of the partial success.
               if (!gotReplies)
               {
                  throw e;
               }
               SystemModelPlugin.log("Error retrieving event trace: " +
                                     e.getMessage());
            }
            return (TargetEvent[]) addedObjects.toArray(new TargetEvent[0]);
         }
      }

      /**
       * Request for the event trace.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param timeout          the timeout.
       * @param handle           the handle.
       * @return the retrieved events (never null).
       * @throws IOException  if an I/O exception occurred; target is
       * automatically disconnected.
       * @throws ServiceException  if the monitor service reported an error.
       * @throws OperationCanceledException  if the operation was interrupted or
       * cancelled; target is automatically disconnected.
       */
      synchronized TargetEvents getTraceMultipleRequest(
            IProgressMonitor progressMonitor,
            int timeout,
            EventTraceHandle handle)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init(null);
            try
            {
               monitor.getTraceMultipleRequest(timeout, handle.getHandle());
            }
            catch (IOException e)
            {
               disconnect();
               throw e;
            }
            waitForEndmark(progressMonitor);
            handle.setHandle(((Integer) addedObjects.get(0)).intValue());
            return (TargetEvents) addedObjects.get(1);
         }
      }

      /*************************************************************************
       * Reply methods.
       ************************************************************************/

      /**
       * @see com.ose.system.service.monitor.MonitorListener#exceptionThrown(java.lang.Throwable)
       */
      public void exceptionThrown(Throwable e)
      {
         SystemModelPlugin.log(e);
         e.printStackTrace();
         disconnect();
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#monitorKilled()
       */
      public void monitorKilled()
      {
         SystemModelPlugin.log("Monitor killed.");
         System.err.println("Monitor killed.");
         disconnect();
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getProcessInfoReply(int,
       * int, int, int, int, int, int, int, java.lang.String)
       */
      public void getProcessInfoReply(int pid,
                                      int bid,
                                      int sid,
                                      int type,
                                      int state,
                                      int priority,
                                      int entrypoint,
                                      int properties,
                                      String name)
      {
         getProcessInfoLongReply(pid,
                                 bid,
                                 sid,
                                 type,
                                 state,
                                 priority,
                                 0, /* uid */
                                 0, /* creator */
                                 false, /* supervisor */
                                 0, /* signalsInQueue */
                                 0, /* signalsAttached */
                                 entrypoint,
                                 0, /* semaphoreValue */
                                 0, /* stackSize */
                                 0, /* supervisorStackSize */
                                 0, /* lineNumber */
                                 0, /* signalsOwned */
                                 0, /* timeSlice */
                                 0, /* vector */
                                 0, /* errorHandler */
                                 properties,
                                 (short) 0, /* euId */
                                 new int[0], /* sigselect */
                                 name,
                                 "" /* fileName */);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getProcessInfoLongReply(int,
       * int, int, int, int, int, int, int, boolean, int, int, int, int, int,
       * int, int, int, int, int, int, int, short, int[], java.lang.String, java.lang.String)
       */
      public void getProcessInfoLongReply(int pid,
                                          int bid,
                                          int sid,
                                          int type,
                                          int state,
                                          int priority,
                                          int uid,
                                          int creator,
                                          boolean supervisor,
                                          int signalsInQueue,
                                          int signalsAttached,
                                          int entrypoint,
                                          int semaphoreValue,
                                          int stackSize,
                                          int supervisorStackSize,
                                          int lineNumber,
                                          int signalsOwned,
                                          int timeSlice,
                                          int vector,
                                          int errorHandler,
                                          int properties,
                                          short euId,
                                          int[] sigselect,
                                          String processName,
                                          String fileName)
      {
         if (parent == null)
         {
            // If parent is null, it means we are handling a process filtering
            // request, and the only thing that needs to be done is to store
            // the received process info.
            ProcessInfo process = new ProcessInfo(Target.this,
                                                  processName,
                                                  pid,
                                                  sid,
                                                  bid,
                                                  uid,
                                                  type,
                                                  creator,
                                                  entrypoint,
                                                  stackSize,
                                                  supervisorStackSize,
                                                  timeSlice,
                                                  vector,
                                                  euId,
                                                  supervisor,
                                                  state,
                                                  priority,
                                                  signalsInQueue,
                                                  signalsAttached,
                                                  signalsOwned,
                                                  semaphoreValue,
                                                  errorHandler,
                                                  properties,
                                                  sigselect,
                                                  fileName,
                                                  lineNumber);
            addedObjects.add(process);
         }
         else
         {
            OseObject obj;
            Block block;

            if (hasBlockSupport())
            {
               obj = (OseObject) getId(bid);
               block = (Block) obj;
               obj = (OseObject) getId(pid);
            }
            else
            {
               obj = (OseObject) getId(pid);
               block = null;
            }

            if (((obj != null) && !(obj instanceof Process)) ||
                ((obj instanceof Process) &&
                      !((Process) obj).matches(pid,
                                               processName,
                                               sid,
                                               bid,
                                               uid,
                                               type,
                                               creator,
                                               entrypoint,
                                               stackSize,
                                               supervisorStackSize,
                                               timeSlice,
                                               vector)))
            {
               // Handle dormant OSE object whose id has been recycled.
               obj.setKilled();
               obj = null;
            }

            Process process = (Process) obj;
            if (process == null)
            {
               process = new Process(Target.this,
                                     block,
                                     processName,
                                     pid,
                                     sid,
                                     bid,
                                     uid,
                                     type,
                                     creator,
                                     entrypoint,
                                     stackSize,
                                     supervisorStackSize,
                                     timeSlice,
                                     vector,
                                     euId,
                                     supervisor,
                                     state,
                                     priority,
                                     signalsInQueue,
                                     signalsAttached,
                                     signalsOwned,
                                     semaphoreValue,
                                     errorHandler,
                                     properties,
                                     sigselect,
                                     fileName,
                                     lineNumber);
               putId(pid, process);
               if (hasBlockSupport())
               {
                  block.addProcess(process);
               }
               else
               {
                  addProcess(process);
               }
               addedObjects.add(process);
            }
            else
            {
               boolean changed = process.change(euId,
                                                supervisor,
                                                state,
                                                priority,
                                                signalsInQueue,
                                                signalsAttached,
                                                signalsOwned,
                                                semaphoreValue,
                                                errorHandler,
                                                sigselect,
                                                fileName,
                                                lineNumber);
               if (changed)
               {
                  changedObjects.add(process);
               }
            }
            process.setTimeStamp(timeStamp);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getProcessInfoEndmark(int)
       */
      public void getProcessInfoEndmark(int status)
      {
         // If parent is null, it means we are handling a process filtering
         // request, and the only thing that needs to be done when we get the
         // endmark is to notify that the transaction is completed.
         if (parent instanceof Process)
         {
            Process process = (Process) parent;
            if (!process.isKilled() && (process.getTimeStamp() != timeStamp))
            {
               process.setKilled();
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(process.getParent(),
                     changedObjects);
            }
         }
         else if (parent instanceof Block)
         {
            Block block = (Block) parent;
            for (Iterator i = block.getProcessList().iterator(); i.hasNext();)
            {
               Process process = (Process) i.next();
               if (!process.isKilled() && (process.getTimeStamp() != timeStamp))
               {
                  process.setKilled();
               }
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(block, changedObjects);
            }
            if (!addedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesAdded(block, addedObjects);
            }
         }
         else if (parent instanceof Segment)
         {
            List changedProcesses = null;
            List addedProcesses = null;
            Segment segment = (Segment) parent;
            for (Iterator i = segment.getBlockList().iterator(); i.hasNext();)
            {
               Block block = (Block) i.next();
               for (Iterator j = block.getProcessList().iterator(); j.hasNext();)
               {
                  Process process = (Process) j.next();
                  if (!process.isKilled() && (process.getTimeStamp() != timeStamp))
                  {
                     process.setKilled();
                  }
               }
               changedProcesses = getChildren(block, changedObjects);
               if (!changedProcesses.isEmpty())
               {
                  SystemModel.getInstance().fireNodesChanged(block, changedProcesses);
               }
               addedProcesses = getChildren(block, addedObjects);
               if (!addedProcesses.isEmpty())
               {
                  SystemModel.getInstance().fireNodesAdded(block, addedProcesses);
               }
            }
         }
         else if (parent instanceof Target)
         {
            if (hasExtendedSegmentSupport())
            {
               List changedProcesses = null;
               List addedProcesses = null;
               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  for (Iterator j = segment.getBlockList().iterator(); j.hasNext();)
                  {
                     Block block = (Block) j.next();
                     for (Iterator k = block.getProcessList().iterator(); k.hasNext();)
                     {
                        Process process = (Process) k.next();
                        if (!process.isKilled() && (process.getTimeStamp() != timeStamp))
                        {
                           process.setKilled();
                        }
                     }
                     changedProcesses = getChildren(block, changedObjects);
                     if (!changedProcesses.isEmpty())
                     {
                        SystemModel.getInstance().fireNodesChanged(block, changedProcesses);
                     }
                     addedProcesses = getChildren(block, addedObjects);
                     if (!addedProcesses.isEmpty())
                     {
                        SystemModel.getInstance().fireNodesAdded(block, addedProcesses);
                     }
                  }
               }
            }
            else if (hasBlockSupport())
            {
               List changedProcesses = null;
               List addedProcesses = null;
               for (Iterator i = blocks.iterator(); i.hasNext();)
               {
                  Block block = (Block) i.next();
                  for (Iterator j = block.getProcessList().iterator(); j.hasNext();)
                  {
                     Process process = (Process) j.next();
                     if (!process.isKilled() && (process.getTimeStamp() != timeStamp))
                     {
                        process.setKilled();
                     }
                  }
                  changedProcesses = getChildren(block, changedObjects);
                  if (!changedProcesses.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesChanged(block, changedProcesses);
                  }
                  addedProcesses = getChildren(block, addedObjects);
                  if (!addedProcesses.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesAdded(block, addedProcesses);
                  }
               }
            }
            else
            {
               for (Iterator i = processes.iterator(); i.hasNext();)
               {
                  Process process = (Process) i.next();
                  if (!process.isKilled() && (process.getTimeStamp() != timeStamp))
                  {
                     process.setKilled();
                  }
               }
               if (!changedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesChanged(Target.this, changedObjects);
               }
               if (!addedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesAdded(Target.this, addedObjects);
               }
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getBlockInfoReply(int,
       * int, int, boolean, int, int, int, int, int, short, java.lang.String)
       */
      public void getBlockInfoReply(int bid,
                                    int sid,
                                    int uid,
                                    boolean supervisor,
                                    int signalsAttached,
                                    int errorHandler,
                                    int maxSigSize,
                                    int sigPoolId,
                                    int stackPoolId,
                                    short euId,
                                    String name)
      {
         if (parent == null)
         {
            // If parent is null, it means we are handling a block filtering
            // request, and the only thing that needs to be done is to store
            // the received block info.
            BlockInfo block = new BlockInfo(Target.this,
                                            name,
                                            bid,
                                            sid,
                                            uid,
                                            supervisor,
                                            maxSigSize,
                                            sigPoolId,
                                            stackPoolId,
                                            euId,
                                            signalsAttached,
                                            errorHandler);
            addedObjects.add(block);
         }
         else
         {
            OseObject obj;
            Segment segment;

            if (hasExtendedSegmentSupport())
            {
               obj = (OseObject) getId(sid);
               segment = (Segment) obj;
               obj = (OseObject) getId(bid);
            }
            else
            {
               obj = (OseObject) getId(bid);
               segment = null;
            }

            if (((obj != null) && !(obj instanceof Block)) ||
                ((obj instanceof Block) && !((Block) obj).matches(bid,
                                                                  name,
                                                                  sid,
                                                                  uid,
                                                                  supervisor,
                                                                  maxSigSize,
                                                                  sigPoolId,
                                                                  stackPoolId)))
            {
               // Handle dormant OSE object whose id has been recycled.
               obj.setKilled();
               obj = null;
            }

            Block block = (Block) obj;
            if (block == null)
            {
               block = new Block(Target.this,
                                 segment,
                                 name,
                                 bid,
                                 sid,
                                 uid,
                                 supervisor,
                                 maxSigSize,
                                 sigPoolId,
                                 stackPoolId,
                                 euId,
                                 signalsAttached,
                                 errorHandler);
               putId(bid, block);
               if (hasExtendedSegmentSupport())
               {
                  segment.addBlock(block);
               }
               else
               {
                  addBlock(block);
               }
               addedObjects.add(block);
            }
            else
            {
               boolean changed = block.change(euId, signalsAttached, errorHandler);
               if (changed)
               {
                  changedObjects.add(block);
               }
            }
            block.setTimeStamp(timeStamp);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getBlockInfoEndmark(int)
       */
      public void getBlockInfoEndmark(int status)
      {
         // If parent is null, it means we are handling a block filtering
         // request, and the only thing that needs to be done when we get the
         // endmark is to notify that the transaction is completed.
         if (parent instanceof Block)
         {
            Block block = (Block) parent;
            if (!block.isKilled() && (block.getTimeStamp() != timeStamp))
            {
               block.setKilled();
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(block.getParent(),
                     changedObjects);
            }
         }
         else if (parent instanceof Segment)
         {
            Segment segment = (Segment) parent;
            for (Iterator i = segment.getBlockList().iterator(); i.hasNext();)
            {
               Block block = (Block) i.next();
               if (!block.isKilled() && (block.getTimeStamp() != timeStamp))
               {
                  block.setKilled();
               }
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(segment, changedObjects);
            }
            if (!addedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesAdded(segment, addedObjects);
            }
         }
         else if (parent instanceof Target)
         {
            if (hasExtendedSegmentSupport())
            {
               List changedBlocks = null;
               List addedBlocks = null;
               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  for (Iterator j = segment.getBlockList().iterator(); j.hasNext();)
                  {
                     Block block = (Block) j.next();
                     if (!block.isKilled() && (block.getTimeStamp() != timeStamp))
                     {
                        block.setKilled();
                     }
                  }
                  changedBlocks = getChildren(segment, changedObjects);
                  if (!changedBlocks.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesChanged(segment, changedBlocks);
                  }
                  addedBlocks = getChildren(segment, addedObjects);
                  if (!addedBlocks.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesAdded(segment, addedBlocks);
                  }
               }
            }
            else
            {
               for (Iterator i = blocks.iterator(); i.hasNext();)
               {
                  Block block = (Block) i.next();
                  if (!block.isKilled() && (block.getTimeStamp() != timeStamp))
                  {
                     block.setKilled();
                  }
               }
               if (!changedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesChanged(Target.this, changedObjects);
               }
               if (!addedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesAdded(Target.this, addedObjects);
               }
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSegmentInfoReply(int, int)
       */
      public void getSegmentInfoReply(int sid, int number)
      {
         getSegmentInfoLongReply(sid,
                                 number,
                                 0, /* signalsAttached */
                                 false, /* masMapped */
                                 "segment" /* name */);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSegmentInfoLongReply(int,
       * int, int, boolean, java.lang.String)
       */
      public void getSegmentInfoLongReply(int sid,
                                          int number,
                                          int signalsAttached,
                                          boolean masMapped,
                                          String name)
      {
         if (parent == null)
         {
            // If parent is null, it means we are handling a segment filtering
            // request, and the only thing that needs to be done is to store
            // the received segment info.
            /*SegmentInfo segment = new SegmentInfo(Target.this,
                                                  name,
                                                  sid,
                                                  number,
                                                  signalsAttached,
                                                  masMapped);
            addedObjects.add(segment);*/
         }
         else
         {
            OseObject obj = (OseObject) getId(sid);
            if (((obj != null) && !(obj instanceof Segment)) ||
                ((obj instanceof Segment) && !((Segment) obj).matches(sid,
                                                                      name,
                                                                      number)))
            {
               // Handle dormant OSE object whose id has been recycled.
               obj.setKilled();
               obj = null;
            }

            Segment segment = (Segment) obj;
            if (segment == null)
            {
               segment = new Segment(Target.this,
                                     name,
                                     sid,
                                     number,
                                     signalsAttached,
                                     masMapped);
               putId(sid, segment);
               addSegment(segment);
               addedObjects.add(segment);
            }
            else
            {
               boolean changed = segment.change(signalsAttached, masMapped);
               if (changed)
               {
                  changedObjects.add(segment);
               }
            }
            segment.setTimeStamp(timeStamp);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSegmentInfoEndmark(int)
       */
      public void getSegmentInfoEndmark(int status)
      {
         // If parent is null, it means we are handling a segment filtering
         // request, and the only thing that needs to be done when we get the
         // endmark is to notify that the transaction is completed.
         if (parent instanceof Segment)
         {
            Segment segment = (Segment) parent;
            if (!segment.isKilled() && (segment.getTimeStamp() != timeStamp))
            {
               segment.setKilled();
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(segment.getParent(),
                     changedObjects);
            }
         }
         else if (parent instanceof Target)
         {
            for (Iterator i = segments.iterator(); i.hasNext();)
            {
               Segment segment = (Segment) i.next();
               if (!segment.isKilled() && (segment.getTimeStamp() != timeStamp))
               {
                  segment.setKilled();
               }
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(Target.this, changedObjects);
            }
            if (!addedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesAdded(Target.this, addedObjects);
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getStackUsageReply(int,
       * com.ose.system.service.monitor.MonitorStackInfo,
       * com.ose.system.service.monitor.MonitorStackInfo)
       */
      public void getStackUsageReply(int pid,
                                     MonitorStackInfo main,
                                     MonitorStackInfo supervisor)
      {
         addedObjects.add(new StackInfo(pid, main));
         // TODO: Supervisor-mode stacks for user-mode processes are ignored.
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getStackUsageEndmark(int)
       */
      public void getStackUsageEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getEnvVarsReply(int,
       * java.lang.String, java.lang.String)
       */
      public void getEnvVarsReply(int pid, String name, String value)
      {
         Object obj = getId(pid);
         if (obj instanceof Block)
         {
            Block block = (Block) obj;
            block.addEnvVar(name, value);
         }
         else if (obj instanceof Process)
         {
            Process process = (Process) obj;
            process.addEnvVar(name, value);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getEnvVarsEndmark(int)
       */
      public void getEnvVarsEndmark(int status)
      {
         if (parent instanceof Block)
         {
            Block block = (Block) parent;
            boolean changed = block.commitEnvVars();
            if (changed)
            {
               SystemModel.getInstance().fireNodesChanged(block.getParent(),
                                                          Collections.singletonList(block));
            }
         }
         else if (parent instanceof Process)
         {
            Process process = (Process) parent;
            boolean changed = process.commitEnvVars();
            if (changed)
            {
               SystemModel.getInstance().fireNodesChanged(process.getParent(),
                                                          Collections.singletonList(process));
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSignalQueueReply(int,
       * com.ose.system.service.monitor.MonitorSignalInfo)
       */
      public void getSignalQueueReply(int pid, MonitorSignalInfo signal)
      {
         Object obj = getId(pid);
         if (obj instanceof Process)
         {
            Process process = (Process) obj;
            process.addSignalQueueItem(pid, signal, null);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSignalQueueLongReply(int,
       * com.ose.system.service.monitor.MonitorSignalInfo, byte[])
       */
      public void getSignalQueueLongReply(int pid,
                                          MonitorSignalInfo signal,
                                          byte[] data)
      {
         Object obj = getId(pid);
         if (obj instanceof Process)
         {
            Process process = (Process) obj;
            process.addSignalQueueItem(pid, signal, data);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSignalQueueEndmark(int)
       */
      public void getSignalQueueEndmark(int status)
      {
         if (parent instanceof Process)
         {
            Process process = (Process) parent;
            boolean changed = process.commitSignalQueue();
            if (changed)
            {
               SystemModel.getInstance().fireNodesChanged(process.getParent(),
                                                          Collections.singletonList(process));
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getPoolInfoReply(
       * int, int, int, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorPoolFragmentInfo[],
       * com.ose.system.service.monitor.MonitorPoolBufferSizeInfo[],
       * com.ose.system.service.monitor.MonitorPoolBufferSizeInfo[])
       */
      public void getPoolInfoReply(int pid,
                                   int sid,
                                   int total,
                                   int free,
                                   int stackAlignment,
                                   int stackAdmSize,
                                   int stackInternalAdmSize,
                                   int signalAlignment,
                                   int signalAdmSize,
                                   int signalInternalAdmSize,
                                   MonitorPoolFragmentInfo[] fragments,
                                   MonitorPoolBufferSizeInfo[] stackSizes,
                                   MonitorPoolBufferSizeInfo[] signalSizes)
      {
         Pool pool = getPoolId(pid);
         Segment segment = hasExtendedSegmentSupport() ? (Segment) getId(sid) : null;

         if ((pool != null) && !pool.matches(pid, sid))
         {
            // Handle dormant OSE pool whose id has been recycled.
            pool.setKilled();
            pool = null;
         }

         if (pool == null)
         {
            pool = new Pool(Target.this,
                            segment,
                            pid,
                            sid,
                            total,
                            free,
                            stackAlignment,
                            stackAdmSize,
                            stackInternalAdmSize,
                            signalAlignment,
                            signalAdmSize,
                            signalInternalAdmSize,
                            stackSizes,
                            signalSizes,
                            fragments);
            putPoolId(pid, pool);
            if (hasExtendedSegmentSupport())
            {
               segment.addPool(pool);
            }
            else
            {
               addPool(pool);
            }
            addedObjects.add(pool);
         }
         else
         {
            boolean changed = pool.change(total,
                                          free,
                                          stackSizes,
                                          signalSizes,
                                          fragments);
            if (changed)
            {
               changedObjects.add(pool);
            }
         }
         pool.setTimeStamp(timeStamp);
         gotReplies = true;
      }
      
      /**
       * @see com.ose.system.service.monitor.MonitorListener#getPoolInfo64Reply(
       * int, int, long, long, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorPoolFragmentInfo64[],
       * com.ose.system.service.monitor.MonitorPoolBufferSizeInfo[],
       * com.ose.system.service.monitor.MonitorPoolBufferSizeInfo[])
       */
      public void getPoolInfo64Reply(int pid,
                                     int sid,
                                     long total,
                                     long free,
                                     int stackAlignment,
                                     int stackAdmSize,
                                     int stackInternalAdmSize,
                                     int signalAlignment,
                                     int signalAdmSize,
                                     int signalInternalAdmSize,
                                     MonitorPoolFragmentInfo64[] fragments,
                                     MonitorPoolBufferSizeInfo[] stackSizes,
                                     MonitorPoolBufferSizeInfo[] signalSizes)
      {
         Pool pool = getPoolId(pid);
         Segment segment = hasExtendedSegmentSupport() ? (Segment) getId(sid) : null;

         if ((pool != null) && !pool.matches(pid, sid))
         {
            // Handle dormant OSE pool whose id has been recycled.
            pool.setKilled();
            pool = null;
         }

         if (pool == null)
         {
            pool = new Pool(Target.this,
                            segment,
                            pid,
                            sid,
                            total,
                            free,
                            stackAlignment,
                            stackAdmSize,
                            stackInternalAdmSize,
                            signalAlignment,
                            signalAdmSize,
                            signalInternalAdmSize,
                            stackSizes,
                            signalSizes,
                            fragments);
            putPoolId(pid, pool);
            if (hasExtendedSegmentSupport())
            {
               segment.addPool(pool);
            }
            else
            {
               addPool(pool);
            }
            addedObjects.add(pool);
         }
         else
         {
            boolean changed = pool.change(total,
                                          free,
                                          stackSizes,
                                          signalSizes,
                                          fragments);
            if (changed)
            {
               changedObjects.add(pool);
            }
         }
         pool.setTimeStamp(timeStamp);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getPoolInfoEndmark(int)
       */
      public void getPoolInfoEndmark(int status)
      {
         if (parent instanceof Pool)
         {
            Pool pool = (Pool) parent;
            if (!pool.isKilled() && (pool.getTimeStamp() != timeStamp))
            {
               pool.setKilled();
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(pool.getParent(),
                     changedObjects);
            }
         }
         else if (parent instanceof Segment)
         {
            Segment segment = (Segment) parent;
            for (Iterator i = segment.getPoolList().iterator(); i.hasNext();)
            {
               Pool pool = (Pool) i.next();
               if (!pool.isKilled() && (pool.getTimeStamp() != timeStamp))
               {
                  pool.setKilled();
               }
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(segment, changedObjects);
            }
            if (!addedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesAdded(segment, addedObjects);
            }
         }
         else if (parent instanceof Target)
         {
            if (hasExtendedSegmentSupport())
            {
               List changedPools = null;
               List addedPools = null;
               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  for (Iterator j = segment.getPoolList().iterator(); j.hasNext();)
                  {
                     Pool pool = (Pool) j.next();
                     if (!pool.isKilled() && (pool.getTimeStamp() != timeStamp))
                     {
                        pool.setKilled();
                     }
                  }
                  changedPools = getChildren(segment, changedObjects);
                  if (!changedPools.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesChanged(segment, changedPools);
                  }
                  addedPools = getChildren(segment, addedObjects);
                  if (!addedPools.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesAdded(segment, addedPools);
                  }
               }
            }
            else
            {
               for (Iterator i = pools.iterator(); i.hasNext();)
               {
                  Pool pool = (Pool) i.next();
                  if (!pool.isKilled() && (pool.getTimeStamp() != timeStamp))
                  {
                     pool.setKilled();
                  }
               }
               if (!changedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesChanged(Target.this, changedObjects);
               }
               if (!addedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesAdded(Target.this, addedObjects);
               }
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getPoolSignalsReply(int,
       * com.ose.system.service.monitor.MonitorSignalInfo[])
       */
      public void getPoolSignalsReply(int pid, MonitorSignalInfo[] signals)
      {
         if (getPoolId(pid) != null)
         {
            for (int i = 0; i < signals.length; i++)
            {
               SignalInfo signalInfo = new SignalInfo(signals[i], null);
               addedObjects.add(signalInfo);
            }
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getPoolSignalsEndmark(int)
       */
      public void getPoolSignalsEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapInfoReply(
       * int, int, int, int, int, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorHeapBufferSizeInfo[])
       */
      public void getHeapInfoReply(int id,
                                   int owner,
                                   int sid,
                                   int size,
                                   int usedSize,
                                   int peakUsedSize,
                                   int reqSize,
                                   int largestFree,
                                   int largeThreshold,
                                   int priv,
                                   int shared,
                                   int mallocFailed,
                                   MonitorHeapBufferSizeInfo[] bufferSizes)
      {
         Heap heap = getHeapId(id);
         Segment segment = hasExtendedSegmentSupport() ? (Segment) getId(sid) : null;

         if ((heap != null) && !heap.matches(id, owner, sid))
         {
            // Handle dormant OSE heap whose id has been recycled.
            heap.setKilled();
            heap = null;
         }

         if (heap == null)
         {
            heap = new Heap(Target.this,
                            segment,
                            id,
                            owner,
                            sid,
                            size,
                            usedSize,
                            peakUsedSize,
                            reqSize,
                            largestFree,
                            largeThreshold,
                            priv,
                            shared,
                            mallocFailed,
                            bufferSizes);
            putHeapId(id, heap);
            if (hasExtendedSegmentSupport())
            {
               segment.addHeap(heap);
            }
            else
            {
               addHeap(heap);
            }
            addedObjects.add(heap);
         }
         else
         {
            boolean changed = heap.change(size,
                                          usedSize,
                                          peakUsedSize,
                                          reqSize,
                                          largestFree,
                                          largeThreshold,
                                          priv,
                                          shared,
                                          mallocFailed,
                                          bufferSizes);
            if (changed)
            {
               changedObjects.add(heap);
            }
         }
         heap.setTimeStamp(timeStamp);
         gotReplies = true;
      }
      
      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapInfo64Reply(
       * int, int, int, long, long, long, long, long, long, int, int, int,
       * com.ose.system.service.monitor.MonitorHeapBufferSizeInfo64[])
       */
      public void getHeapInfo64Reply(int id,
                                     int owner,
                                     int sid,
                                     long size,
                                     long usedSize,
                                     long peakUsedSize,
                                     long reqSize,
                                     long largestFree,
                                     long largeThreshold,
                                     int priv,
                                     int shared,
                                     int mallocFailed,
                                     MonitorHeapBufferSizeInfo64[] bufferSizes)
      {
         Heap heap = getHeapId(id);
         Segment segment = hasExtendedSegmentSupport() ? (Segment) getId(sid) : null;

         if ((heap != null) && !heap.matches(id, owner, sid))
         {
            // Handle dormant OSE heap whose id has been recycled.
            heap.setKilled();
            heap = null;
         }

         if (heap == null)
         {
            heap = new Heap(Target.this,
                            segment,
                            id,
                            owner,
                            sid,
                            size,
                            usedSize,
                            peakUsedSize,
                            reqSize,
                            largestFree,
                            largeThreshold,
                            priv,
                            shared,
                            mallocFailed,
                            bufferSizes);
            putHeapId(id, heap);
            if (hasExtendedSegmentSupport())
            {
               segment.addHeap(heap);
            }
            else
            {
               addHeap(heap);
            }
            addedObjects.add(heap);
         }
         else
         {
            boolean changed = heap.change(size,
                                          usedSize,
                                          peakUsedSize,
                                          reqSize,
                                          largestFree,
                                          largeThreshold,
                                          priv,
                                          shared,
                                          mallocFailed,
                                          bufferSizes);
            if (changed)
            {
               changedObjects.add(heap);
            }
         }
         heap.setTimeStamp(timeStamp);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapInfoEndmark(int)
       */
      public void getHeapInfoEndmark(int status)
      {
         if (parent instanceof Heap)
         {
            Heap heap = (Heap) parent;
            if (!heap.isKilled() && (heap.getTimeStamp() != timeStamp))
            {
               heap.setKilled();
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(heap.getParent(),
                     changedObjects);
            }
         }
         else if (parent instanceof Segment)
         {
            Segment segment = (Segment) parent;
            for (Iterator i = segment.getHeapList().iterator(); i.hasNext();)
            {
               Heap heap = (Heap) i.next();
               if (!heap.isKilled() && (heap.getTimeStamp() != timeStamp))
               {
                  heap.setKilled();
               }
            }
            if (!changedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesChanged(segment, changedObjects);
            }
            if (!addedObjects.isEmpty())
            {
               SystemModel.getInstance().fireNodesAdded(segment, addedObjects);
            }
         }
         else if (parent instanceof Target)
         {
            if (hasExtendedSegmentSupport())
            {
               List changedHeaps = null;
               List addedHeaps = null;
               for (Iterator i = segments.iterator(); i.hasNext();)
               {
                  Segment segment = (Segment) i.next();
                  for (Iterator j = segment.getHeapList().iterator(); j.hasNext();)
                  {
                     Heap heap = (Heap) j.next();
                     if (!heap.isKilled() && (heap.getTimeStamp() != timeStamp))
                     {
                        heap.setKilled();
                     }
                  }
                  changedHeaps = getChildren(segment, changedObjects);
                  if (!changedHeaps.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesChanged(segment, changedHeaps);
                  }
                  addedHeaps = getChildren(segment, addedObjects);
                  if (!addedHeaps.isEmpty())
                  {
                     SystemModel.getInstance().fireNodesAdded(segment, addedHeaps);
                  }
               }
            }
            else
            {
               for (Iterator i = heaps.iterator(); i.hasNext();)
               {
                  Heap heap = (Heap) i.next();
                  if (!heap.isKilled() && (heap.getTimeStamp() != timeStamp))
                  {
                     heap.setKilled();
                  }
               }
               if (!changedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesChanged(Target.this, changedObjects);
               }
               if (!addedObjects.isEmpty())
               {
                  SystemModel.getInstance().fireNodesAdded(Target.this, addedObjects);
               }
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapFragmentInfoReply(int,
       * com.ose.system.service.monitor.MonitorHeapFragmentInfo[])
       */
      public void getHeapFragmentInfoReply(int id,
                                           MonitorHeapFragmentInfo[] fragments)
      {
         Heap heap = getHeapId(id);
         if (heap != null)
         {
            heap.addHeapFragments(fragments);
         }
         gotReplies = true;
      }
      
      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapFragmentInfo64Reply(int,
       * com.ose.system.service.monitor.MonitorHeapFragmentInfo64[])
       */
      public void getHeapFragmentInfo64Reply(int id,
                                           MonitorHeapFragmentInfo64[] fragments)
      {
         Heap heap = getHeapId(id);
         if (heap != null)
         {
            heap.addHeapFragments(fragments);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapFragmentInfoEndmark(int)
       */
      public void getHeapFragmentInfoEndmark(int status)
      {
         if (parent instanceof Heap)
         {
            Heap heap = (Heap) parent;
            boolean changed = heap.commitHeapFragments();
            if (changed)
            {
               SystemModel.getInstance().fireNodesChanged(
                  heap.getParent(), Collections.singletonList(heap));
            }
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapBuffersReply(int,
       * com.ose.system.service.monitor.MonitorHeapBufferInfo[])
       */
      public void getHeapBuffersReply(int id, MonitorHeapBufferInfo[] buffers)
      {
         if (getHeapId(id) != null)
         {
            for (int i = 0; i < buffers.length; i++)
            {
               HeapBufferInfo buffer = new HeapBufferInfo(buffers[i]);
               addedObjects.add(buffer);
            }
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getHeapBuffersEndmark(int)
       */
      public void getHeapBuffersEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSysParamReply(java.lang.String,
       * java.lang.String)
       */
      public void getSysParamReply(String name, String value)
      {
         Target.this.addSysParam(name, value);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSysParamEndmark(int)
       */
      public void getSysParamEndmark(int status)
      {
         boolean changed = Target.this.commitSysParams();
         if (changed)
         {
            SystemModel.getInstance().fireNodesChanged(gate,
                  Collections.singletonList(Target.this));
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setSysParamReply(int)
       */
      public void setSysParamReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getMemoryReply(int, int, byte[])
       */
      public void getMemoryReply(int pid, long address, byte[] data)
      {
         addedObjects.add(data);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getMemoryEndmark(int)
       */
      public void getMemoryEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#killScopeReply(int)
       */
      public void killScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#startScopeReply(int)
       */
      public void startScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#stopScopeReply(int)
       */
      public void stopScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setEnvVarReply(int)
       */
      public void setEnvVarReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#signalSemaphoreReply(int)
       */
      public void signalSemaphoreReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setPriorityReply(int)
       */
      public void setPriorityReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setExecutionUnitReply(int)
       */
      public void setExecutionUnitReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getRamdumpInfoReply(int, int, int, int)
       */
      public void getRamdumpInfoReply(int dumpId, int size, int sec, int microsec)
      {
         DumpInfo dump = new DumpInfo(Target.this, dumpId, size, sec, microsec);
         addedObjects.add(dump);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getRamdumpInfoEndmark(int)
       */
      public void getRamdumpInfoEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getRamdumpReply(int, int, byte[])
       */
      public void getRamdumpReply(int dumpId, int offset, byte[] data)
      {
         try
         {
            OutputStream out = (OutputStream) addedObjects.get(0);
            IProgressMonitor progress = (IProgressMonitor) addedObjects.get(1);

            out.write(data);
            progress.worked(data.length);
         }
         catch (Exception e)
         {
            SystemModelPlugin.log("Error writing dump " + dumpId +
                                  " at offset " + offset);
            SystemModelPlugin.log(e);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getRamdumpEndmark(int)
       */
      public void getRamdumpEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUReportsEnabledReply(boolean,
       * int, int, int, int, int, int)
       */
      public void getCPUReportsEnabledReply(boolean enabled,
                                            int interval,
                                            int priority,
                                            int maxReports,
                                            int sec,
                                            int sectick,
                                            int secntick)
      {
         CPUReportsEnabledInfo info = new CPUReportsEnabledInfo(Target.this,
                                                                enabled,
                                                                interval,
                                                                priority,
                                                                maxReports,
                                                                sec,
                                                                sectick,
                                                                secntick);
         addedObjects.add(info);
         notifyEndmark(MonitorStatus.MONITOR_STATUS_OK);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCPUReportsEnabledReply(int)
       */
      public void setCPUReportsEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUReportsReply(int,
       * boolean, short, com.ose.system.service.monitor.MonitorCPUReport[])
       */
      public void getCPUReportsReply(int status,
                                     boolean enabled,
                                     short euId,
                                     MonitorCPUReport[] reports)
      {
         CPUReports cpuReports = new CPUReports(Target.this, enabled, euId, reports);
         addedObjects.add(cpuReports);
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUPriReportsEnabledReply(boolean,
       * int, int, int, int, int)
       */
      public void getCPUPriReportsEnabledReply(boolean enabled,
                                               int interval,
                                               int maxReports,
                                               int sec,
                                               int sectick,
                                               int secntick)
      {
         CPUPriorityReportsEnabledInfo info = new CPUPriorityReportsEnabledInfo(
            Target.this, enabled, interval, maxReports, sec, sectick, secntick);
         addedObjects.add(info);
         notifyEndmark(MonitorStatus.MONITOR_STATUS_OK);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCPUPriReportsEnabledReply(int)
       */
      public void setCPUPriReportsEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUPriReportsReply(int,
       * boolean, short, com.ose.system.service.monitor.MonitorCPUPriReport[])
       */
      public void getCPUPriReportsReply(int status,
                                        boolean enabled,
                                        short euId,
                                        MonitorCPUPriReport[] reports)
      {
         CPUPriorityReports cpuReports =
            new CPUPriorityReports(Target.this, enabled, euId, reports);
         addedObjects.add(cpuReports);
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUProcessReportsEnabledReply(boolean,
       * int, int, int, int, int, int)
       */
      public void getCPUProcessReportsEnabledReply(boolean enabled,
                                                   int interval,
                                                   int maxReports,
                                                   int maxProcsReport,
                                                   int sec,
                                                   int sectick,
                                                   int secntick)
      {
         CPUProcessReportsEnabledInfo info = new CPUProcessReportsEnabledInfo(
               Target.this,
               enabled,
               interval,
               maxReports,
               maxProcsReport,
               sec,
               sectick,
               secntick);
         addedObjects.add(info);
         notifyEndmark(MonitorStatus.MONITOR_STATUS_OK);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCPUProcessReportsEnabledReply(int)
       */
      public void setCPUProcessReportsEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCPUProcessReportpointReply(int,
       * int)
       */
      public void setCPUProcessReportpointReply(int status, int id)
      {
         addedObjects.add(new Integer(id));
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#clearCPUProcessReportpointReply(int)
       */
      public void clearCPUProcessReportpointReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUProcessReportpointInfoReply(int,
       * int, int, com.ose.system.service.monitor.MonitorTag[])
       */
      public void getCPUProcessReportpointInfoReply(int scopeType,
                                                    int scopeId,
                                                    int id,
                                                    MonitorTag[] tags)
      {
         CPUProcessReportPoint reportPoint =
            new CPUProcessReportPoint(id, scopeType, scopeId, tags);
         addedObjects.add(reportPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUProcessReportpointInfoEndmark(int)
       */
      public void getCPUProcessReportpointInfoEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUProcessReportsReply(int,
       * boolean, short, com.ose.system.service.monitor.MonitorCPUProcessReport[])
       */
      public void getCPUProcessReportsReply(int status,
                                            boolean enabled,
                                            short euId,
                                            MonitorCPUProcessReport[] reports)
      {
         CPUProcessReports cpuReports =
            new CPUProcessReports(Target.this, enabled, euId, reports);
         addedObjects.add(cpuReports);
         notifyEndmark(status);
      }  

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUBlockReportsEnabledReply(boolean,
       * int, int, int, int, int, int)
       */
      public void getCPUBlockReportsEnabledReply(boolean enabled,
                                                 int interval,
                                                 int maxReports,
                                                 int maxBlocksReport,
                                                 int sec,
                                                 int sectick,
                                                 int secntick)
      {
         CPUBlockReportsEnabledInfo info = new CPUBlockReportsEnabledInfo(
               Target.this,
               enabled,
               interval,
               maxReports,
               maxBlocksReport,
               sec,
               sectick,
               secntick);
         addedObjects.add(info);
         notifyEndmark(MonitorStatus.MONITOR_STATUS_OK);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCPUBlockReportsEnabledReply(int)
       */
      public void setCPUBlockReportsEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCPUBlockReportsReply(int,
       * boolean, short, com.ose.system.service.monitor.MonitorCPUBlockReport[])
       */
      public void getCPUBlockReportsReply(int status,
                                          boolean enabled,
                                          short euId,
                                          MonitorCPUBlockReport[] reports)
      {
         CPUBlockReports cpuReports =
            new CPUBlockReports(Target.this, enabled, euId, reports);
         addedObjects.add(cpuReports);
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getUserReportsEnabledReply(int,
       * boolean, int, int, int, int, int, int)
       */
      public void getUserReportsEnabledReply(int reportNo,
                                             boolean enabled,
                                             int interval,
                                             int maxReports,
                                             int maxValuesReport,
                                             int sec,
                                             int sectick,
                                             int secntick)
      {
         UserReportsEnabledInfo info = new UserReportsEnabledInfo(
               Target.this,
               reportNo,
               enabled,
               interval,
               maxReports,
               maxValuesReport,
               sec,
               sectick,
               secntick);
         addedObjects.add(info);
         notifyEndmark(MonitorStatus.MONITOR_STATUS_OK);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setUserReportsEnabledReply(int)
       */
      public void setUserReportsEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getUserReportsReply(int,
       * int, boolean, boolean, boolean, boolean,
       * com.ose.system.service.monitor.MonitorUserReport[])
       */
      public void getUserReportsReply(int status,
                                      int reportNo,
                                      boolean enabled,
                                      boolean continuous,
                                      boolean maxmin,
                                      boolean multiple,
                                      MonitorUserReport[] reports)
      {
         UserReports userReports = new UserReports(Target.this,
                                                   reportNo,
                                                   enabled,
                                                   continuous,
                                                   maxmin,
                                                   multiple,
                                                   reports);
         addedObjects.add(userReports);
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSupportedCounterTypesReply(int,
       * java.lang.String)
       */
      public void getSupportedCounterTypesReply(int counterType, String name)
      {
         PerformanceCounterInfo performanceCounter =
            new PerformanceCounterInfo(counterType, name);
         addedObjects.add(performanceCounter);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSupportedCounterTypesEndmark(int)
       */
      public void getSupportedCounterTypesEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCounterTypeEnabledReply(int,
       * boolean, short, int, long, int)
       */
      public void getCounterTypeEnabledReply(int status,
                                             boolean enabled,
                                             short euId,
                                             int counterType,
                                             long counterValue,
                                             int maxReports)
      {
         PerformanceCounterEnabledInfo info =
            new PerformanceCounterEnabledInfo(Target.this,
                                              enabled,
                                              euId,
                                              counterType,
                                              counterValue,
                                              maxReports);
         addedObjects.add(info);
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCounterTypeEnabledReply(int)
       */
      public void setCounterTypeEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCounterReportsNotifyEnabledReply(int,
       * boolean, short, int)
       */
      public void getCounterReportsNotifyEnabledReply(int status,
                                                      boolean enabled,
                                                      short euId,
                                                      int counterType)
      {
         addedObjects.add(new Boolean(enabled));
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setCounterReportsNotifyEnabledReply(int)
       */
      public void setCounterReportsNotifyEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#counterReportsNotify(short,
       * int, com.ose.system.service.monitor.MonitorCounterReport[])
       */
      public void counterReportsNotify(short euId,
                                       int counterType,
                                       MonitorCounterReport[] reports)
      {
         PerformanceCounterReports performanceCounterReports =
            new PerformanceCounterReports(Target.this,
                                          euId,
                                          counterType,
                                          reports);
         Object[] listeners = reportListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((PerformanceCounterListener) listeners[i]).reportsRetrieved(
                  performanceCounterReports);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#counterReportsLossNotify(short,
       * int, int)
       */
      public void counterReportsLossNotify(short euId,
                                           int counterType,
                                           int reportsLost)
      {
         Object[] listeners = reportListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((PerformanceCounterListener) listeners[i]).reportsLost(
                  euId, counterType, reportsLost);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#attachReply(int, int)
       */
      public void attachReply(int status, int processCacheCount)
      {
         if (status == MonitorStatus.MONITOR_STATUS_OK)
         {
            notifyProcessCache = new ProcessInfoCache(processCacheCount);
            traceProcessCache = new ProcessInfoCache(processCacheCount);
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#detachReply(int)
       */
      public void detachReply(int status)
      {
         if (status == MonitorStatus.MONITOR_STATUS_OK)
         {
            notifyProcessCache = null;
            traceProcessCache = null;
         }
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#interceptScopeReply(int)
       */
      public void interceptScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#resumeScopeReply(int)
       */
      public void resumeScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setScopeReply(int)
       */
      public void setScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setInterceptScopeReply(int)
       */
      public void setInterceptScopeReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setEventActionpointReply(int,
       * int)
       */
      public void setEventActionpointReply(int status, int id)
      {
         addedObjects.add(new Integer(id));
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#clearEventActionpointReply(int)
       */
      public void clearEventActionpointReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSendActionpointInfoReply(int,
       * int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getSendActionpointInfoReply(int state,
                                              int fromType,
                                              int fromId,
                                              int toType,
                                              int toId,
                                              boolean not,
                                              int action,
                                              int interceptType,
                                              int interceptId,
                                              int parameter,
                                              int client,
                                              int id,
                                              int count,
                                              MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint =
            new SendEventActionPoint(state,
                                     fromType,
                                     fromId,
                                     toType,
                                     toId,
                                     not,
                                     action,
                                     interceptType,
                                     interceptId,
                                     parameter,
                                     client,
                                     id,
                                     count,
                                     tags,
                                     bigEndian);
         addedObjects.add(eventActionPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getReceiveActionpointInfoReply(int,
       * int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getReceiveActionpointInfoReply(int state,
                                                 int fromType,
                                                 int fromId,
                                                 int toType,
                                                 int toId,
                                                 boolean not,
                                                 int action,
                                                 int interceptType,
                                                 int interceptId,
                                                 int parameter,
                                                 int client,
                                                 int id,
                                                 int count,
                                                 MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint =
            new ReceiveEventActionPoint(state,
                                        fromType,
                                        fromId,
                                        toType,
                                        toId,
                                        not,
                                        action,
                                        interceptType,
                                        interceptId,
                                        parameter,
                                        client,
                                        id,
                                        count,
                                        tags,
                                        bigEndian);
         addedObjects.add(eventActionPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSwapActionpointInfoReply(int,
       * int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getSwapActionpointInfoReply(int state,
                                              int fromType,
                                              int fromId,
                                              int toType,
                                              int toId,
                                              boolean not,
                                              int action,
                                              int interceptType,
                                              int interceptId,
                                              int parameter,
                                              int client,
                                              int id,
                                              int count,
                                              MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint =
            new SwapEventActionPoint(state,
                                     fromType,
                                     fromId,
                                     toType,
                                     toId,
                                     not,
                                     action,
                                     interceptType,
                                     interceptId,
                                     parameter,
                                     client,
                                     id,
                                     count,
                                     tags,
                                     bigEndian);
         addedObjects.add(eventActionPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCreateActionpointInfoReply(int,
       * int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getCreateActionpointInfoReply(int state,
                                                int fromType,
                                                int fromId,
                                                int toType,
                                                int toId,
                                                boolean not,
                                                int action,
                                                int interceptType,
                                                int interceptId,
                                                int parameter,
                                                int client,
                                                int id,
                                                int count,
                                                MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint =
            new CreateEventActionPoint(state,
                                       fromType,
                                       fromId,
                                       toType,
                                       toId,
                                       not,
                                       action,
                                       interceptType,
                                       interceptId,
                                       parameter,
                                       client,
                                       id,
                                       count,
                                       tags,
                                       bigEndian);
         addedObjects.add(eventActionPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getKillActionpointInfoReply(int,
       * int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getKillActionpointInfoReply(int state,
                                              int fromType,
                                              int fromId,
                                              int toType,
                                              int toId,
                                              boolean not,
                                              int action,
                                              int interceptType,
                                              int interceptId,
                                              int parameter,
                                              int client,
                                              int id,
                                              int count,
                                              MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint =
            new KillEventActionPoint(state,
                                     fromType,
                                     fromId,
                                     toType,
                                     toId,
                                     not,
                                     action,
                                     interceptType,
                                     interceptId,
                                     parameter,
                                     client,
                                     id,
                                     count,
                                     tags,
                                     bigEndian);
         addedObjects.add(eventActionPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getErrorActionpointInfoReply(int,
       * int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getErrorActionpointInfoReply(int state,
                                               int fromType,
                                               int fromId,
                                               int toType,
                                               int toId,
                                               boolean not,
                                               int action,
                                               int interceptType,
                                               int interceptId,
                                               int parameter,
                                               int client,
                                               int id,
                                               int count,
                                               MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint =
            new ErrorEventActionPoint(state,
                                      fromType,
                                      fromId,
                                      toType,
                                      toId,
                                      not,
                                      action,
                                      interceptType,
                                      interceptId,
                                      parameter,
                                      client,
                                      id,
                                      count,
                                      tags,
                                      bigEndian);
         addedObjects.add(eventActionPoint);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getEventActionpointInfoEndmark(int)
       */
      public void getEventActionpointInfoEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getEventActionpointsReply(int,
       * int, int, int, int, int, boolean, int, int, int, int, int, int, int,
       * com.ose.system.service.monitor.MonitorTag[])
       */
      public void getEventActionpointsReply(int type,
                                            int state,
                                            int fromType,
                                            int fromId,
                                            int toType,
                                            int toId,
                                            boolean not,
                                            int action,
                                            int interceptType,
                                            int interceptId,
                                            int parameter,
                                            int client,
                                            int id,
                                            int count,
                                            MonitorTag[] tags)
      {
         EventActionPoint eventActionPoint = null;

         switch (type)
         {
            case Monitor.MONITOR_EVENT_ACTIONPOINT_CREATE:
               eventActionPoint = new CreateEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_KILL:
               eventActionPoint = new KillEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_SWAP:
               eventActionPoint = new SwapEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_SEND:
               eventActionPoint = new SendEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_RECEIVE:
               eventActionPoint = new ReceiveEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_ERROR:
               eventActionPoint = new ErrorEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_USER:
               eventActionPoint = new UserEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_BIND:
               eventActionPoint = new BindEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_ALLOC:
               eventActionPoint = new AllocEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_FREE_BUF:
               eventActionPoint = new FreeEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            case Monitor.MONITOR_EVENT_ACTIONPOINT_TIMEOUT:
               eventActionPoint = new TimeoutEventActionPoint(
                     state,
                     fromType,
                     fromId,
                     toType,
                     toId,
                     not,
                     action,
                     interceptType,
                     interceptId,
                     parameter,
                     client,
                     id,
                     count,
                     tags,
                     bigEndian);
               break;
            default:
               SystemModelPlugin.log(
                     "Unknown type of event action point retrieved: " + type);
               break;
         }

         if (eventActionPoint != null)
         {
            addedObjects.add(eventActionPoint);
         }
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getEventActionpointsEndmark(int)
       */
      public void getEventActionpointsEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getEventStateReply(int,
       * int)
       */
      public void getEventStateReply(int status, int state)
      {
         addedObjects.add(new Integer(state));
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setEventStateReply(int)
       */
      public void setEventStateReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getNotifyEnabledReply(int,
       * boolean)
       */
      public void getNotifyEnabledReply(int status, boolean enabled)
      {
         addedObjects.add(new Boolean(enabled));
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setNotifyEnabledReply(int)
       */
      public void setNotifyEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#processNotify(int,
       * int, int, int, int, int, java.lang.String)
       */
      public void processNotify(int pid,
                                int bid,
                                int sid,
                                int type,
                                int entrypoint,
                                int properties,
                                String name)
      {
         TargetEvent.ProcessInfo process;

         process = new TargetEvent.ProcessInfo(Target.this,
                                               pid,
                                               bid,
                                               sid,
                                               type,
                                               entrypoint,
                                               properties,
                                               name);
         notifyProcessCache.put(pid, process);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#sendNotify(int,
       * int, int, int, int, int, int, int, int, int, int, int, short,
       * java.lang.String, byte[])
       */
      public void sendNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int signalNumber,
                             int signalSender,
                             int signalAddressee,
                             int signalSize,
                             int signalAddress,
                             int signalId,
                             int lineNumber,
                             short euId,
                             String fileName,
                             byte[] signalData)
      {
         SendEvent event;
         Object[] listeners;

         event = new SendEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               signalNumber,
                               notifyProcessCache.get(signalSender),
                               notifyProcessCache.get(signalAddressee),
                               signalSize,
                               signalAddress,
                               signalId,
                               lineNumber,
                               euId,
                               fileName,
                               signalData);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).signalSent(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#receiveNotify(int,
       * int, int, int, int, int, int, int, int, int, int, int, short,
       * java.lang.String, byte[])
       */
      public void receiveNotify(int reference,
                                int tick,
                                int ntick,
                                int sec,
                                int sectick,
                                int signalNumber,
                                int signalSender,
                                int signalAddressee,
                                int signalSize,
                                int signalAddress,
                                int signalId,
                                int lineNumber,
                                short euId,
                                String fileName,
                                byte[] signalData)
      {
         ReceiveEvent event;
         Object[] listeners;

         event = new ReceiveEvent(Target.this,
                                  Target.this.getTickLength(),
                                  reference,
                                  tick,
                                  ntick,
                                  sec,
                                  sectick,
                                  signalNumber,
                                  notifyProcessCache.get(signalSender),
                                  notifyProcessCache.get(signalAddressee),
                                  signalSize,
                                  signalAddress,
                                  signalId,
                                  lineNumber,
                                  euId,
                                  fileName,
                                  signalData);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).signalReceived(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#swapNotify(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void swapNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int from,
                             int to,
                             int lineNumber,
                             short euId,
                             String fileName)
      {
         SwapEvent event;
         Object[] listeners;

         event = new SwapEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               notifyProcessCache.get(to),
                               lineNumber,
                               euId,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).processSwapped(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#createNotify(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void createNotify(int reference,
                               int tick,
                               int ntick,
                               int sec,
                               int sectick,
                               int from,
                               int to,
                               int lineNumber,
                               short euId,
                               String fileName)
      {
         CreateEvent event;
         Object[] listeners;

         event = new CreateEvent(Target.this,
                                 Target.this.getTickLength(),
                                 reference,
                                 tick,
                                 ntick,
                                 sec,
                                 sectick,
                                 notifyProcessCache.get(from),
                                 notifyProcessCache.get(to),
                                 lineNumber,
                                 euId,
                                 fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).processCreated(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#killNotify(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void killNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int from,
                             int to,
                             int lineNumber,
                             short euId,
                             String fileName)
      {
         KillEvent event;
         Object[] listeners;

         event = new KillEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               notifyProcessCache.get(to),
                               lineNumber,
                               euId,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).processKilled(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#errorNotify(int,
       * int, int, int, int, int, boolean, int, int, int, short, java.lang.String)
       */
      public void errorNotify(int reference,
                              int tick,
                              int ntick,
                              int sec,
                              int sectick,
                              int from,
                              boolean exec,
                              int error,
                              int extra,
                              int lineNumber,
                              short euId,
                              String fileName)
      {
         ErrorEvent event;
         Object[] listeners;

         event = new ErrorEvent(Target.this,
                                Target.this.getTickLength(),
                                reference,
                                tick,
                                ntick,
                                sec,
                                sectick,
                                notifyProcessCache.get(from),
                                exec,
                                error,
                                extra,
                                lineNumber,
                                euId,
                                fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).errorCalled(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#userNotify(int,
       * int, int, int, int, int, int, int, int, short, java.lang.String, byte[])
       */
      public void userNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int from,
                             int eventNumber,
                             int eventSize,
                             int lineNumber,
                             short euId,
                             String fileName,
                             byte[] eventData)
      {
         UserEvent event;
         Object[] listeners;

         event = new UserEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               eventNumber,
                               eventSize,
                               lineNumber,
                               euId,
                               fileName,
                               eventData);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).userReported(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#bindNotify(int,
       * int, int, int, int, int, short, short, int, java.lang.String)
       */
      public void bindNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int from,
                             short fromEuId,
                             short toEuId,
                             int lineNumber,
                             String fileName)
      {
         BindEvent event;
         Object[] listeners;

         event = new BindEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               fromEuId,
                               toEuId,
                               lineNumber,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).processBound(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#allocNotify(int,
       * int, int, int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void allocNotify(int reference,
                              int tick,
                              int ntick,
                              int sec,
                              int sectick,
                              int from,
                              int signalNumber,
                              int signalSize,
                              int signalAddress,
                              int signalId,
                              int lineNumber,
                              short euId,
                              String fileName)
      {
         AllocEvent event;
         Object[] listeners;

         event = new AllocEvent(Target.this,
                                Target.this.getTickLength(),
                                reference,
                                tick,
                                ntick,
                                sec,
                                sectick,
                                notifyProcessCache.get(from),
                                signalNumber,
                                signalSize,
                                signalAddress,
                                signalId,
                                lineNumber,
                                euId,
                                fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).signalAllocated(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#freeBufNotify(int,
       * int, int, int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void freeBufNotify(int reference,
                                int tick,
                                int ntick,
                                int sec,
                                int sectick,
                                int from,
                                int signalNumber,
                                int signalSize,
                                int signalAddress,
                                int signalId,
                                int lineNumber,
                                short euId,
                                String fileName)
      {
         FreeEvent event;
         Object[] listeners;

         event = new FreeEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               signalNumber,
                               signalSize,
                               signalAddress,
                               signalId,
                               lineNumber,
                               euId,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).signalFreed(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#timeoutNotify(int,
       * int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void timeoutNotify(int reference,
                                int tick,
                                int ntick,
                                int sec,
                                int sectick,
                                int timeout,
                                int tmoSource,
                                int from,
                                int lineNumber,
                                short euId,
                                String fileName)
      {
         TimeoutEvent event;
         Object[] listeners;

         event = new TimeoutEvent(Target.this,
                                  Target.this.getTickLength(),
                                  reference,
                                  tick,
                                  ntick,
                                  sec,
                                  sectick,
                                  timeout,
                                  tmoSource,
                                  notifyProcessCache.get(from),
                                  lineNumber,
                                  euId,
                                  fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).systemCallTimedout(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#lossNotify(int,
       * int, int, int, int, int)
       */
      public void lossNotify(int reference,
                             int tick,
                             int ntick,
                             int sec,
                             int sectick,
                             int lostSize)
      {
         LossEvent event;
         Object[] listeners;

         event = new LossEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               lostSize);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).eventsLost(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#processInterceptNotify(int,
       * int, int, int, int, int, java.lang.String)
       */
      public void processInterceptNotify(int pid,
                                         int bid,
                                         int sid,
                                         int type,
                                         int entrypoint,
                                         int properties,
                                         String name)
      {
         TargetEvent.ProcessInfo process;

         process = new TargetEvent.ProcessInfo(Target.this,
                                               pid,
                                               bid,
                                               sid,
                                               type,
                                               entrypoint,
                                               properties,
                                               name);
         notifyProcessCache.put(pid, process);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#sendInterceptNotify(int,
       * int, int, int, int, int, int, int, int, int, int, int, short,
       * java.lang.String, byte[])
       */
      public void sendInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int signalNumber,
                                      int signalSender,
                                      int signalAddressee,
                                      int signalSize,
                                      int signalAddress,
                                      int signalId,
                                      int lineNumber,
                                      short euId,
                                      String fileName,
                                      byte[] signalData)
      {
         SendEvent event;
         Object[] listeners;

         event = new SendEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               signalNumber,
                               notifyProcessCache.get(signalSender),
                               notifyProcessCache.get(signalAddressee),
                               signalSize,
                               signalAddress,
                               signalId,
                               lineNumber,
                               euId,
                               fileName,
                               signalData);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#receiveInterceptNotify(int,
       * int, int, int, int, int, int, int, int, int, int, int, short,
       * java.lang.String, byte[])
       */
      public void receiveInterceptNotify(int reference,
                                         int tick,
                                         int ntick,
                                         int sec,
                                         int sectick,
                                         int signalNumber,
                                         int signalSender,
                                         int signalAddressee,
                                         int signalSize,
                                         int signalAddress,
                                         int signalId,
                                         int lineNumber,
                                         short euId,
                                         String fileName,
                                         byte[] signalData)
      {
         ReceiveEvent event;
         Object[] listeners;

         event = new ReceiveEvent(Target.this,
                                  Target.this.getTickLength(),
                                  reference,
                                  tick,
                                  ntick,
                                  sec,
                                  sectick,
                                  signalNumber,
                                  notifyProcessCache.get(signalSender),
                                  notifyProcessCache.get(signalAddressee),
                                  signalSize,
                                  signalAddress,
                                  signalId,
                                  lineNumber,
                                  euId,
                                  fileName,
                                  signalData);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#swapInterceptNotify(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void swapInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int from,
                                      int to,
                                      int lineNumber,
                                      short euId,
                                      String fileName)
      {
         SwapEvent event;
         Object[] listeners;

         event = new SwapEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               notifyProcessCache.get(to),
                               lineNumber,
                               euId,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#createInterceptNotify(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void createInterceptNotify(int reference,
                                        int tick,
                                        int ntick,
                                        int sec,
                                        int sectick,
                                        int from,
                                        int to,
                                        int lineNumber,
                                        short euId,
                                        String fileName)
      {
         CreateEvent event;
         Object[] listeners;

         event = new CreateEvent(Target.this,
                                 Target.this.getTickLength(),
                                 reference,
                                 tick,
                                 ntick,
                                 sec,
                                 sectick,
                                 notifyProcessCache.get(from),
                                 notifyProcessCache.get(to),
                                 lineNumber,
                                 euId,
                                 fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#killInterceptNotify(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void killInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int from,
                                      int to,
                                      int lineNumber,
                                      short euId,
                                      String fileName)
      {
         KillEvent event;
         Object[] listeners;

         event = new KillEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               notifyProcessCache.get(to),
                               lineNumber,
                               euId,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#errorInterceptNotify(int,
       * int, int, int, int, int, boolean, int, int, int, short, java.lang.String)
       */
      public void errorInterceptNotify(int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       int from,
                                       boolean exec,
                                       int error,
                                       int extra,
                                       int lineNumber,
                                       short euId,
                                       String fileName)
      {
         ErrorEvent event;
         Object[] listeners;

         event = new ErrorEvent(Target.this,
                                Target.this.getTickLength(),
                                reference,
                                tick,
                                ntick,
                                sec,
                                sectick,
                                notifyProcessCache.get(from),
                                exec,
                                error,
                                extra,
                                lineNumber,
                                euId,
                                fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#userInterceptNotify(int,
       * int, int, int, int, int, int, int, int, short, java.lang.String, byte[])
       */
      public void userInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int from,
                                      int eventNumber,
                                      int eventSize,
                                      int lineNumber,
                                      short euId,
                                      String fileName,
                                      byte[] eventData)
      {
         UserEvent event;
         Object[] listeners;

         event = new UserEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               eventNumber,
                               eventSize,
                               lineNumber,
                               euId,
                               fileName,
                               eventData);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#bindInterceptNotify(int,
       * int, int, int, int, int, short, short, int, java.lang.String)
       */
      public void bindInterceptNotify(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int from,
                                      short fromEuId,
                                      short toEuId,
                                      int lineNumber,
                                      String fileName)
      {
         BindEvent event;
         Object[] listeners;

         event = new BindEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               fromEuId,
                               toEuId,
                               lineNumber,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#allocInterceptNotify(int,
       * int, int, int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void allocInterceptNotify(int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       int from,
                                       int signalNumber,
                                       int signalSize,
                                       int signalAddress,
                                       int signalId,
                                       int lineNumber,
                                       short euId,
                                       String fileName)
      {
         AllocEvent event;
         Object[] listeners;

         event = new AllocEvent(Target.this,
                                Target.this.getTickLength(),
                                reference,
                                tick,
                                ntick,
                                sec,
                                sectick,
                                notifyProcessCache.get(from),
                                signalNumber,
                                signalSize,
                                signalAddress,
                                signalId,
                                lineNumber,
                                euId,
                                fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#freeBufInterceptNotify(int,
       * int, int, int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void freeBufInterceptNotify(int reference,
                                         int tick,
                                         int ntick,
                                         int sec,
                                         int sectick,
                                         int from,
                                         int signalNumber,
                                         int signalSize,
                                         int signalAddress,
                                         int signalId,
                                         int lineNumber,
                                         short euId,
                                         String fileName)
      {
         FreeEvent event;
         Object[] listeners;

         event = new FreeEvent(Target.this,
                               Target.this.getTickLength(),
                               reference,
                               tick,
                               ntick,
                               sec,
                               sectick,
                               notifyProcessCache.get(from),
                               signalNumber,
                               signalSize,
                               signalAddress,
                               signalId,
                               lineNumber,
                               euId,
                               fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#timeoutInterceptNotify(int,
       * int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void timeoutInterceptNotify(int reference,
                                         int tick,
                                         int ntick,
                                         int sec,
                                         int sectick,
                                         int timeout,
                                         int tmoSource,
                                         int from,
                                         int lineNumber,
                                         short euId,
                                         String fileName)
      {
         TimeoutEvent event;
         Object[] listeners;

         event = new TimeoutEvent(Target.this,
                                  Target.this.getTickLength(),
                                  reference,
                                  tick,
                                  ntick,
                                  sec,
                                  sectick,
                                  timeout,
                                  tmoSource,
                                  notifyProcessCache.get(from),
                                  lineNumber,
                                  euId,
                                  fileName);
         listeners = eventListenerList.getListeners();
         for (int i = 0; i < listeners.length; i++)
         {
            ((TargetListener) listeners[i]).scopeIntercepted(event);
         }
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getTraceEnabledReply(int,
       * boolean)
       */
      public void getTraceEnabledReply(int status, boolean enabled)
      {
         addedObjects.add(new Boolean(enabled));
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#setTraceEnabledReply(int)
       */
      public void setTraceEnabledReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#clearTraceReply(int)
       */
      public void clearTraceReply(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getProcessTraceReply(int,
       * int, int, int, int, int, java.lang.String)
       */
      public void getProcessTraceReply(int pid,
                                       int bid,
                                       int sid,
                                       int type,
                                       int entrypoint,
                                       int properties,
                                       String name)
      {
         TargetEvent.ProcessInfo process;

         process = new TargetEvent.ProcessInfo(Target.this,
                                               pid,
                                               bid,
                                               sid,
                                               type,
                                               entrypoint,
                                               properties,
                                               name);
         traceProcessCache.put(pid, process);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSendTraceReply(int,
       * int, int, int, int, int, int, int, int, int, int, int, short,
       * java.lang.String, byte[])
       */
      public void getSendTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int signalNumber,
                                    int signalSender,
                                    int signalAddressee,
                                    int signalSize,
                                    int signalAddress,
                                    int signalId,
                                    int lineNumber,
                                    short euId,
                                    String fileName,
                                    byte[] signalData)
      {
         TargetEvent event = new SendEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               signalNumber,
               traceProcessCache.get(signalSender),
               traceProcessCache.get(signalAddressee),
               signalSize,
               signalAddress,
               signalId,
               lineNumber,
               euId,
               fileName,
               signalData);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getReceiveTraceReply(int,
       * int, int, int, int, int, int, int, int, int, int, int, short,
       * java.lang.String, byte[])
       */
      public void getReceiveTraceReply(int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       int signalNumber,
                                       int signalSender,
                                       int signalAddressee,
                                       int signalSize,
                                       int signalAddress,
                                       int signalId,
                                       int lineNumber,
                                       short euId,
                                       String fileName,
                                       byte[] signalData)
      {
         TargetEvent event = new ReceiveEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               signalNumber,
               traceProcessCache.get(signalSender),
               traceProcessCache.get(signalAddressee),
               signalSize,
               signalAddress,
               signalId,
               lineNumber,
               euId,
               fileName,
               signalData);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getSwapTraceReply(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void getSwapTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    int to,
                                    int lineNumber,
                                    short euId,
                                    String fileName)
      {
         TargetEvent event = new SwapEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               traceProcessCache.get(from),
               traceProcessCache.get(to),
               lineNumber,
               euId,
               fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getCreateTraceReply(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void getCreateTraceReply(int reference,
                                      int tick,
                                      int ntick,
                                      int sec,
                                      int sectick,
                                      int from,
                                      int to,
                                      int lineNumber,
                                      short euId,
                                      String fileName)
      {
         TargetEvent event = new CreateEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               traceProcessCache.get(from),
               traceProcessCache.get(to),
               lineNumber,
               euId,
               fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getKillTraceReply(int,
       * int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void getKillTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    int to,
                                    int lineNumber,
                                    short euId,
                                    String fileName)
      {
         TargetEvent event = new KillEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               traceProcessCache.get(from),
               traceProcessCache.get(to),
               lineNumber,
               euId,
               fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getErrorTraceReply(int,
       * int, int, int, int, int, boolean, int, int, int, short, java.lang.String)
       */
      public void getErrorTraceReply(int reference,
                                     int tick,
                                     int ntick,
                                     int sec,
                                     int sectick,
                                     int from,
                                     boolean exec,
                                     int error,
                                     int extra,
                                     int lineNumber,
                                     short euId,
                                     String fileName)
      {
         TargetEvent event = new ErrorEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               traceProcessCache.get(from),
               exec,
               error,
               extra,
               lineNumber,
               euId,
               fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getResetTraceReply(int,
       * int, int, int, int, boolean, int, java.lang.String)
       */
      public void getResetTraceReply(int reference,
                                     int tick,
                                     int ntick,
                                     int sec,
                                     int sectick,
                                     boolean warm,
                                     int lineNumber,
                                     String fileName)
      {
         TargetEvent event = new ResetEvent(Target.this,
                                            Target.this.getTickLength(),
                                            reference,
                                            tick,
                                            ntick,
                                            sec,
                                            sectick,
                                            warm,
                                            lineNumber,
                                            fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getUserTraceReply(int,
       * int, int, int, int, int, int, int, int, short, java.lang.String, byte[])
       */
      public void getUserTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    int eventNumber,
                                    int eventSize,
                                    int lineNumber,
                                    short euId,
                                    String fileName,
                                    byte[] eventData)
      {
         TargetEvent event = new UserEvent(
               Target.this,
               Target.this.getTickLength(),
               reference,
               tick,
               ntick,
               sec,
               sectick,
               traceProcessCache.get(from),
               eventNumber,
               eventSize,
               lineNumber,
               euId,
               fileName,
               eventData);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getBindTraceReply(int,
       * int, int, int, int, int, short, short, int, java.lang.String)
       */
      public void getBindTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int from,
                                    short fromEuId,
                                    short toEuId,
                                    int lineNumber,
                                    String fileName)
      {
         TargetEvent event = new BindEvent(Target.this,
                                           Target.this.getTickLength(),
                                           reference,
                                           tick,
                                           ntick,
                                           sec,
                                           sectick,
                                           traceProcessCache.get(from),
                                           fromEuId,
                                           toEuId,
                                           lineNumber,
                                           fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getAllocTraceReply(int,
       * int, int, int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void getAllocTraceReply(int reference,
                                     int tick,
                                     int ntick,
                                     int sec,
                                     int sectick,
                                     int from,
                                     int signalNumber,
                                     int signalSize,
                                     int signalAddress,
                                     int signalId,
                                     int lineNumber,
                                     short euId,
                                     String fileName)
      {
         TargetEvent event = new AllocEvent(Target.this,
                                            Target.this.getTickLength(),
                                            reference,
                                            tick,
                                            ntick,
                                            sec,
                                            sectick,
                                            traceProcessCache.get(from),
                                            signalNumber,
                                            signalSize,
                                            signalAddress,
                                            signalId,
                                            lineNumber,
                                            euId,
                                            fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getFreeBufTraceReply(int,
       * int, int, int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void getFreeBufTraceReply(int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       int from,
                                       int signalNumber,
                                       int signalSize,
                                       int signalAddress,
                                       int signalId,
                                       int lineNumber,
                                       short euId,
                                       String fileName)
      {
         TargetEvent event = new FreeEvent(Target.this,
                                           Target.this.getTickLength(),
                                           reference,
                                           tick,
                                           ntick,
                                           sec,
                                           sectick,
                                           traceProcessCache.get(from),
                                           signalNumber,
                                           signalSize,
                                           signalAddress,
                                           signalId,
                                           lineNumber,
                                           euId,
                                           fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getTimeoutTraceReply(int,
       * int, int, int, int, int, int, int, int, short, java.lang.String)
       */
      public void getTimeoutTraceReply(int reference,
                                       int tick,
                                       int ntick,
                                       int sec,
                                       int sectick,
                                       int timeout,
                                       int tmoSource,
                                       int from,
                                       int lineNumber,
                                       short euId,
                                       String fileName)
      {
         TargetEvent event = new TimeoutEvent(Target.this,
                                              Target.this.getTickLength(),
                                              reference,
                                              tick,
                                              ntick,
                                              sec,
                                              sectick,
                                              timeout,
                                              tmoSource,
                                              traceProcessCache.get(from),
                                              lineNumber,
                                              euId,
                                              fileName);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getLossTraceReply(int,
       * int, int, int, int, int)
       */
      public void getLossTraceReply(int reference,
                                    int tick,
                                    int ntick,
                                    int sec,
                                    int sectick,
                                    int lostSize)
      {
         TargetEvent event = new LossEvent(Target.this,
                                           Target.this.getTickLength(),
                                           reference,
                                           tick,
                                           ntick,
                                           sec,
                                           sectick,
                                           lostSize);
         addedObjects.add(event);
         gotReplies = true;
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getTraceEndmark(int)
       */
      public void getTraceEndmark(int status)
      {
         notifyEndmark(status);
      }

      /**
       * @see com.ose.system.service.monitor.MonitorListener#getTraceMultipleReply(int,
       * int, boolean, com.ose.gateway.Signal[])
       */
      public void getTraceMultipleReply(int status,
                                        int handle,
                                        boolean enabled,
                                        Signal[] events)
      {
         List eventList = new ArrayList();

         for (int i = 0; i < events.length; i++)
         {
            Signal sig = events[i];
            TargetEvent event = null;

            switch (sig.getSigNo())
            {
               case MonitorGetProcessTraceReply.SIG_NO:
                  MonitorGetProcessTraceReply processReply =
                     (MonitorGetProcessTraceReply) sig;
                  TargetEvent.ProcessInfo process =
                     new TargetEvent.ProcessInfo(
                        Target.this,
                        processReply.pid,
                        processReply.bid,
                        processReply.sid,
                        processReply.type,
                        processReply.entrypoint,
                        processReply.properties,
                        processReply.name);
                  traceProcessCache.put(processReply.pid, process);
                  break;
               case MonitorGetSendTraceReply.SIG_NO:
                  MonitorGetSendTraceReply sendReply =
                     (MonitorGetSendTraceReply) sig;
                  event = new SendEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        sendReply.reference,
                        sendReply.tick,
                        sendReply.ntick,
                        sendReply.sec,
                        sendReply.sectick,
                        sendReply.signalNumber,
                        traceProcessCache.get(sendReply.signalSender),
                        traceProcessCache.get(sendReply.signalAddressee),
                        sendReply.signalSize,
                        sendReply.signalAddress,
                        sendReply.signalId,
                        sendReply.lineNumber,
                        sendReply.euId,
                        sendReply.fileName,
                        sendReply.signalData);
                  break;
               case MonitorGetReceiveTraceReply.SIG_NO:
                  MonitorGetReceiveTraceReply receiveReply =
                     (MonitorGetReceiveTraceReply) sig;
                  event = new ReceiveEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        receiveReply.reference,
                        receiveReply.tick,
                        receiveReply.ntick,
                        receiveReply.sec,
                        receiveReply.sectick,
                        receiveReply.signalNumber,
                        traceProcessCache.get(receiveReply.signalSender),
                        traceProcessCache.get(receiveReply.signalAddressee),
                        receiveReply.signalSize,
                        receiveReply.signalAddress,
                        receiveReply.signalId,
                        receiveReply.lineNumber,
                        receiveReply.euId,
                        receiveReply.fileName,
                        receiveReply.signalData);
                  break;
               case MonitorGetSwapTraceReply.SIG_NO:
                  MonitorGetSwapTraceReply swapReply =
                     (MonitorGetSwapTraceReply) sig;
                  event = new SwapEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        swapReply.reference,
                        swapReply.tick,
                        swapReply.ntick,
                        swapReply.sec,
                        swapReply.sectick,
                        traceProcessCache.get(swapReply.from),
                        traceProcessCache.get(swapReply.to),
                        swapReply.lineNumber,
                        swapReply.euId,
                        swapReply.fileName);
                  break;
               case MonitorGetCreateTraceReply.SIG_NO:
                  MonitorGetCreateTraceReply createReply =
                     (MonitorGetCreateTraceReply) sig;
                  event = new CreateEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        createReply.reference,
                        createReply.tick,
                        createReply.ntick,
                        createReply.sec,
                        createReply.sectick,
                        traceProcessCache.get(createReply.from),
                        traceProcessCache.get(createReply.to),
                        createReply.lineNumber,
                        createReply.euId,
                        createReply.fileName);
                  break;
               case MonitorGetKillTraceReply.SIG_NO:
                  MonitorGetKillTraceReply killReply =
                     (MonitorGetKillTraceReply) sig;
                  event = new KillEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        killReply.reference,
                        killReply.tick,
                        killReply.ntick,
                        killReply.sec,
                        killReply.sectick,
                        traceProcessCache.get(killReply.from),
                        traceProcessCache.get(killReply.to),
                        killReply.lineNumber,
                        killReply.euId,
                        killReply.fileName);
                  break;
               case MonitorGetErrorTraceReply.SIG_NO:
                  MonitorGetErrorTraceReply errorReply =
                     (MonitorGetErrorTraceReply) sig;
                  event = new ErrorEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        errorReply.reference,
                        errorReply.tick,
                        errorReply.ntick,
                        errorReply.sec,
                        errorReply.sectick,
                        traceProcessCache.get(errorReply.from),
                        errorReply.exec,
                        errorReply.error,
                        errorReply.extra,
                        errorReply.lineNumber,
                        errorReply.euId,
                        errorReply.fileName);
                  break;
               case MonitorGetResetTraceReply.SIG_NO:
                  MonitorGetResetTraceReply resetReply =
                     (MonitorGetResetTraceReply) sig;
                  event = new ResetEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        resetReply.reference,
                        resetReply.tick,
                        resetReply.ntick,
                        resetReply.sec,
                        resetReply.sectick,
                        resetReply.warm,
                        resetReply.lineNumber,
                        resetReply.fileName);
                  break;
               case MonitorGetUserTraceReply.SIG_NO:
                  MonitorGetUserTraceReply userReply =
                     (MonitorGetUserTraceReply) sig;
                  event = new UserEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        userReply.reference,
                        userReply.tick,
                        userReply.ntick,
                        userReply.sec,
                        userReply.sectick,
                        traceProcessCache.get(userReply.from),
                        userReply.eventNumber,
                        userReply.eventSize,
                        userReply.lineNumber,
                        userReply.euId,
                        userReply.fileName,
                        userReply.eventData);
                  break;
               case MonitorGetBindTraceReply.SIG_NO:
                  MonitorGetBindTraceReply bindReply =
                     (MonitorGetBindTraceReply) sig;
                  event = new BindEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        bindReply.reference,
                        bindReply.tick,
                        bindReply.ntick,
                        bindReply.sec,
                        bindReply.sectick,
                        traceProcessCache.get(bindReply.from),
                        bindReply.fromEuId,
                        bindReply.toEuId,
                        bindReply.lineNumber,
                        bindReply.fileName);
                  break;
               case MonitorGetAllocTraceReply.SIG_NO:
                  MonitorGetAllocTraceReply allocReply =
                     (MonitorGetAllocTraceReply) sig;
                  event = new AllocEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        allocReply.reference,
                        allocReply.tick,
                        allocReply.ntick,
                        allocReply.sec,
                        allocReply.sectick,
                        traceProcessCache.get(allocReply.from),
                        allocReply.signalNumber,
                        allocReply.signalSize,
                        allocReply.signalAddress,
                        allocReply.signalId,
                        allocReply.lineNumber,
                        allocReply.euId,
                        allocReply.fileName);
                  break;
               case MonitorGetFreeBufTraceReply.SIG_NO:
                  MonitorGetFreeBufTraceReply freeBufReply =
                     (MonitorGetFreeBufTraceReply) sig;
                  event = new FreeEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        freeBufReply.reference,
                        freeBufReply.tick,
                        freeBufReply.ntick,
                        freeBufReply.sec,
                        freeBufReply.sectick,
                        traceProcessCache.get(freeBufReply.from),
                        freeBufReply.signalNumber,
                        freeBufReply.signalSize,
                        freeBufReply.signalAddress,
                        freeBufReply.signalId,
                        freeBufReply.lineNumber,
                        freeBufReply.euId,
                        freeBufReply.fileName);
                  break;
               case MonitorGetTimeoutTraceReply.SIG_NO:
                  MonitorGetTimeoutTraceReply timeoutReply =
                     (MonitorGetTimeoutTraceReply) sig;
                  event = new TimeoutEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        timeoutReply.reference,
                        timeoutReply.tick,
                        timeoutReply.ntick,
                        timeoutReply.sec,
                        timeoutReply.sectick,
                        timeoutReply.timeout,
                        timeoutReply.tmoSource,
                        traceProcessCache.get(timeoutReply.from),
                        timeoutReply.lineNumber,
                        timeoutReply.euId,
                        timeoutReply.fileName);
                  break;
               case MonitorGetLossTraceReply.SIG_NO:
                  MonitorGetLossTraceReply lossReply =
                     (MonitorGetLossTraceReply) sig;
                  event = new LossEvent(
                        Target.this,
                        Target.this.getTickLength(),
                        lossReply.reference,
                        lossReply.tick,
                        lossReply.ntick,
                        lossReply.sec,
                        lossReply.sectick,
                        lossReply.lostSize);
                  break;
               default:
                  SystemModelPlugin.log(
                     "Unknown event retrieved from event trace: " + sig.getSigNo());
                  break;
            }

            if (event != null)
            {
               eventList.add(event);
            }
         }

         addedObjects.add(new Integer(handle));
         addedObjects.add(new TargetEvents(Target.this, enabled,
            (TargetEvent[]) eventList.toArray(new TargetEvent[0])));
         notifyEndmark(status);
      }
   }

   /**
    * This class handles all transactions with the OSE program manager on this
    * target (except program manager creation/connection/disconnection), i.e.
    * all requests sent to and all replies received from the OSE program manager
    * (except as noted) are handled by this class. There is one instance of this
    * class for a target instance.
    * <p>
    * Only one on-going transaction with the OSE program manager is permitted at
    * a time. The request methods are called by client threads; they start a new
    * transaction by performing the actual request and then wait (with periodic
    * cancellation polling) for the corresponding last reply. The reply call-
    * back methods are called by the program manager receiver thread; they
    * produce the result if applicable, and notifies the client thread that the
    * last reply has been received and that the transaction has thus ended.
    *
    * @see ProgramManagerListener
    */
   class ProgramManagerHandler implements ProgramManagerListener
   {
      private static final long WAIT_TIMEOUT = 1000L;

      private final Object lock;
      private boolean done;
      private int status;
      private Object result;

      /**
       * Create the program manager handler object for this target.
       */
      ProgramManagerHandler()
      {
         lock = new Object();
         done = false;
         status = ProgramManagerStatus.PM_SUCCESS;
         result = null;
      }

      /*************************************************************************
       * Transaction methods.
       ************************************************************************/

      /**
       * Initialize a transaction with the OSE program manager.
       * Called by a client thread.
       */
      private void init()
      {
         done = false;
         status = ProgramManagerStatus.PM_SUCCESS;
         result = null;
      }

      /**
       * Wait for the end of a transaction with the OSE program manager.
       * Called by a client thread.
       *
       * @param progressMonitor    the progress monitor used for cancellation.
       * @throws ServiceException  if the OSE program manager reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled, or the OSE program manager has been disconnected.
       */
      private void waitForReply(IProgressMonitor progressMonitor)
         throws ServiceException
      {
         while (!done)
         {
            try
            {
               lock.wait(WAIT_TIMEOUT);
            }
            catch (InterruptedException e)
            {
               progressMonitor.setCanceled(true);
            }
            if (progressMonitor.isCanceled())
            {
               throw new OperationCanceledException();
            }
            if (!pm.isConnected())
            {
               // The program manager has been disconnected,
               // cancel the operation.
               throw new OperationCanceledException();
            }
         }

         if (status != ProgramManagerStatus.PM_SUCCESS)
         {
            throw new ServiceException(SERVICE_PROGRAM_MANAGER_ID,
                  status, ProgramManagerStatus.getMessage(status));
         }
      }

      /**
       * Notify the end of a transaction with the OSE program manager.
       * Called by the program manager receiver thread.
       *
       * @param status  the status of the transaction.
       */
      private void notifyReply(int status)
      {
         synchronized (lock)
         {
            this.status = status;
            done = true;
            lock.notifyAll();
         }
      }

      /*************************************************************************
       * Request methods.
       ************************************************************************/

      /**
       * Request for installing a load module.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param options          the load module installation options.
       * @param file_format      the file format of the load module.
       * @param file_name        the file name of the load module.
       * @param install_handle   the installation handle of the load module.
       * @param conf             the load module installation parameters, or
       * null if none.
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized void installLoadModuleRequest(IProgressMonitor progressMonitor,
                                                 int options,
                                                 String file_format,
                                                 String file_name,
                                                 String install_handle,
                                                 String[] conf)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.installLoadModuleRequest(options,
                                        file_format,
                                        file_name,
                                        install_handle,
                                        conf);
            waitForReply(progressMonitor);
         }
      }

      /**
       * Request for uninstalling a load module.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param install_handle   the installation handle of the load module.
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized void uninstallLoadModuleRequest(
            IProgressMonitor progressMonitor, String install_handle)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.uninstallLoadModuleRequest(install_handle);
            waitForReply(progressMonitor);
         }
      }

      /**
       * Request for all load module installation handles.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @return an array of all load module installation handles (never null).
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized String[] loadModuleInstallHandlesRequest(
            IProgressMonitor progressMonitor)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.loadModuleInstallHandlesRequest();
            waitForReply(progressMonitor);
            return (String[]) result;
         }
      }

      /**
       * Request for load module information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param install_handle   the installation handle of the load module.
       * @return the load module with the specified installation handle (never
       * null).
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized LoadModuleInfo loadModuleInfoRequest(
            IProgressMonitor progressMonitor, String install_handle)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.loadModuleInfoRequest(install_handle);
            waitForReply(progressMonitor);
            return (LoadModuleInfo) result;
         }
      }

      /**
       * Request for creating a program.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param domain           the domain.
       * @param install_handle   the installation handle of the load module.
       * @param conf             the program configuration, or null if none.
       * @return the ID of the created program.
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized int createProgramRequest(IProgressMonitor progressMonitor,
                                            int domain,
                                            String install_handle,
                                            String[] conf)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.createProgramRequest(domain, install_handle, conf);
            waitForReply(progressMonitor);
            return ((Integer) result).intValue();
         }
      }

      /**
       * Request for starting a program.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param progpid          the ID of the program.
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized void startProgramRequest(IProgressMonitor progressMonitor,
                                            int progpid)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.startProgramRequest(progpid);
            waitForReply(progressMonitor);
         }
      }

      /**
       * Request for killing a program.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param progpid          the ID of the program.
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized void killProgramRequest(IProgressMonitor progressMonitor,
                                           int progpid)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.killProgramRequest(progpid);
            waitForReply(progressMonitor);
         }
      }

      /**
       * Request for program information.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param progpid          the ID of the program.
       * @return the program with the specified ID (never null).
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized ProgramInfo programInfoRequest(IProgressMonitor progressMonitor,
                                                  int progpid)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.programInfoRequest(progpid);
            waitForReply(progressMonitor);
            return (ProgramInfo) result;
         }
      }

      /**
       * Request for translating a process, block, or segment ID to a program ID.
       *
       * @param progressMonitor  the progress monitor used for cancellation.
       * @param pid              a process, block, or segment ID.
       * @return the corresponding program ID.
       * @throws IOException  if an I/O exception occurred.
       * @throws ServiceException  if the program manager service reported an error.
       * @throws OperationCanceledException  if the transaction was interrupted
       * or cancelled.
       */
      synchronized int getProgramPidRequest(IProgressMonitor progressMonitor,
                                            int pid)
         throws ServiceException, IOException
      {
         synchronized (lock)
         {
            init();
            pm.getProgramPidRequest(pid);
            waitForReply(progressMonitor);
            return ((Integer) result).intValue();
         }
      }

      /*************************************************************************
       * Reply methods.
       ************************************************************************/

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#exceptionThrown(java.lang.Throwable)
       */
      public void exceptionThrown(Throwable e)
      {
         SystemModelPlugin.log(e);
         e.printStackTrace();
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#programManagerKilled()
       */
      public void programManagerKilled()
      {
         SystemModelPlugin.log("Program manager killed.");
         System.err.println("Program manager killed.");
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#installLoadModuleReply(int,
       * java.lang.String)
       */
      public void installLoadModuleReply(int status, String install_handle)
      {
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#uninstallLoadModuleReply(int,
       * java.lang.String)
       */
      public void uninstallLoadModuleReply(int status, String install_handle)
      {
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#loadModuleInstallHandlesReply(int,
       * java.lang.String[])
       */
      public void loadModuleInstallHandlesReply(int status,
                                                String[] install_handles)
      {
         result = install_handles;
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#loadModuleInfoReply(int,
       * java.lang.String, int, int, int, int, int, int, int, int,
       * java.lang.String, java.lang.String)
       */
      public void loadModuleInfoReply(int status,
                                      String install_handle,
                                      long entrypoint,
                                      int options,
                                      long text_base,
                                      long text_size,
                                      long data_base,
                                      long data_size,
                                      int no_of_sections,
                                      int no_of_instances,
                                      String file_format,
                                      String file_name)
      {
         result = new LoadModuleInfo(Target.this,
                                     install_handle,
                                     entrypoint,
                                     options,
                                     text_base,
                                     text_size,
                                     data_base,
                                     data_size,
                                     no_of_sections,
                                     no_of_instances,
                                     file_format,
                                     file_name);
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#createProgramReply(int,
       * int, java.lang.String, int, int)
       */
      public void createProgramReply(int status,
                                     int domain,
                                     String install_handle,
                                     int progpid,
                                     int main_bid)
      {
         result = new Integer(progpid);
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#startProgramReply(int, int)
       */
      public void startProgramReply(int status, int progpid)
      {
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#killProgramReply(int, int)
       */
      public void killProgramReply(int status, int progpid)
      {
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#programInfoReply(int, java.lang.String,
       * int, int, int, int, int, int, int, int, boolean, int, int, int, int, int, int)
       */
      public void programInfoReply(int status,
                                   String install_handle,
                                   int progpid,
                                   int domain,
                                   int main_block,
                                   int main_process,
                                   long pool_base,
                                   long pool_size,
                                   int uid,
                                   int state,
                                   boolean extended_info,
                                   int segpid,
                                   int stk_poolid,
                                   int sig_poolid,
                                   long sig_pool_base,
                                   long sig_pool_size,
                                   int heap)
      {
         result = new ProgramInfo(Target.this,
                                  install_handle,
                                  progpid,
                                  domain,
                                  main_block,
                                  main_process,
                                  pool_base,
                                  pool_size,
                                  uid,
                                  state,
                                  extended_info,
                                  segpid,
                                  stk_poolid,
                                  sig_poolid,
                                  sig_pool_base,
                                  sig_pool_size,
                                  heap);
         notifyReply(status);
      }

      /**
       * @see com.ose.system.service.pm.ProgramManagerListener#getProgramPidReply(int, int, int)
       */
      public void getProgramPidReply(int status, int pid, int progpid)
      {
         result = new Integer(progpid);
         notifyReply(status);
      }
   }

   /**
    * This class provides a process info cache of a specified maximum size
    * where the oldest entry is removed if the size limit is threatened to
    * be exceeded.
    *
    * @see java.util.LinkedHashMap
    */
   class ProcessInfoCache extends LinkedHashMap
   {
      private static final long serialVersionUID = 1L;

      private final int processCacheCount;

      /**
       * Create a new process info cache object.
       *
       * @param processCacheCount  the maximum size of this process info cache.
       */
      ProcessInfoCache(int processCacheCount)
      {
         super(processCacheCount);
         if (processCacheCount < 1)
         {
            throw new IllegalArgumentException();
         }
         this.processCacheCount = processCacheCount;
      }

      /**
       * Return the process info object with the specified process ID or null if
       * there is no mapping for the specified process ID.
       *
       * @param id  the process ID.
       * @return the corresponding process info object or null if there is no
       * mapping for the specified process ID.
       */
      public TargetEvent.ProcessInfo get(int id)
      {
         TargetEvent.ProcessInfo process;

         process = (TargetEvent.ProcessInfo) get(new Integer(id));
         // TODO: If there is no process info object for a non-zero process ID,
         // should we invent one with default values for all properties except
         // the process ID? Should we also store it in the process info cache?
         /*
         if ((id != 0) && (process == null))
         {
            process = new TargetEvent.ProcessInfo(
                  Target.this,
                  id,
                  0,
                  0,
                  TargetEvent.ProcessInfo.TYPE_UNKNOWN,
                  0,
                  0,
                  "<unknown>");
         }
         */
         return process;
      }

      /**
       * Associate the specified process ID with the specified process info
       * object. If there is a previous mapping for the specified process ID,
       * the old process info object is removed.
       *
       * @param id  the process ID.
       * @param process  the corresponding process info object.
       */
      public void put(int id, TargetEvent.ProcessInfo process)
      {
         Integer key;

         if (process == null)
         {
            throw new IllegalArgumentException();
         }
         key = new Integer(id);
         if (containsKey(key))
         {
            // We have a process who has died and whos id has been recycled,
            // remove its mapping to update its insertion order.
            remove(key);
         }
         put(key, process);
      }

      /**
       * This method limits the maximum size of the process info cache to
       * processCacheCount entries and removes the oldest entry when a new entry
       * is added that threatens the size limit of the process info cache to be
       * exceeded.
       *
       * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
       */
      protected boolean removeEldestEntry(Map.Entry eldest)
      {
         return (size() > processCacheCount);
      }
   }
}
