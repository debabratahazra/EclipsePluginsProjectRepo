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

package com.ose.system.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import junit.framework.TestCase;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import com.ose.system.AllocEvent;
import com.ose.system.AllocEventActionPoint;
import com.ose.system.BindEvent;
import com.ose.system.BindEventActionPoint;
import com.ose.system.Block;
import com.ose.system.BlockFilter;
import com.ose.system.BlockInfo;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReports;
import com.ose.system.CPUBlockReportsEnabledInfo;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUPriorityReports;
import com.ose.system.CPUPriorityReportsEnabledInfo;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReportPoint;
import com.ose.system.CPUProcessReports;
import com.ose.system.CPUProcessReportsEnabledInfo;
import com.ose.system.CPUReport;
import com.ose.system.CPUReports;
import com.ose.system.CPUReportsEnabledInfo;
import com.ose.system.CreateEvent;
import com.ose.system.CreateEventActionPoint;
import com.ose.system.DumpInfo;
import com.ose.system.ErrorEvent;
import com.ose.system.ErrorEventActionPoint;
import com.ose.system.EventActionPoint;
import com.ose.system.EventTraceHandle;
import com.ose.system.FreeEvent;
import com.ose.system.FreeEventActionPoint;
import com.ose.system.Gate;
import com.ose.system.KillEvent;
import com.ose.system.KillEventActionPoint;
import com.ose.system.LoadModuleInfo;
import com.ose.system.LossEvent;
import com.ose.system.Parameter;
import com.ose.system.Pool;
import com.ose.system.PoolBufferSizeInfo;
import com.ose.system.PoolFragmentInfo;
import com.ose.system.Heap;
import com.ose.system.HeapBufferSizeInfo;
import com.ose.system.HeapFragmentInfo;
import com.ose.system.HeapBufferInfo;
import com.ose.system.Process;
import com.ose.system.ProcessFilter;
import com.ose.system.ProcessInfo;
import com.ose.system.ProgramInfo;
import com.ose.system.ReceiveEvent;
import com.ose.system.ReceiveEventActionPoint;
import com.ose.system.ResetEvent;
import com.ose.system.Segment;
import com.ose.system.SendEvent;
import com.ose.system.SendEventActionPoint;
import com.ose.system.SignalFilter;
import com.ose.system.SignalInfo;
import com.ose.system.StackFilter;
import com.ose.system.StackInfo;
import com.ose.system.SwapEvent;
import com.ose.system.SwapEventActionPoint;
import com.ose.system.SystemModel;
import com.ose.system.SystemModelDumpFileWriter;
import com.ose.system.SystemModelEvent;
import com.ose.system.SystemModelListener;
import com.ose.system.SystemModelNode;
import com.ose.system.Target;
import com.ose.system.TargetEvent;
import com.ose.system.TargetEvents;
import com.ose.system.TargetListener;
import com.ose.system.TargetReport;
import com.ose.system.TimeoutEvent;
import com.ose.system.TimeoutEventActionPoint;
import com.ose.system.UserEvent;
import com.ose.system.UserEventActionPoint;
import com.ose.system.UserReport;
import com.ose.system.UserReports;
import com.ose.system.UserReportsEnabledInfo;

/**
 * This class consists of JUnit tests for the OSE System Model. In order to run
 * the tests you need the Refsys module called "sysmodtest" with a predetermined
 * set of blocks and processes, used by several of the tests.
 */
public class SystemModelTests extends TestCase
{
   private final InetAddress testGateAddress;

   private final int testGatePort;

   private final String testTargetHuntPath;

   private SystemModel sm;

   private SystemModelListener systemModelListener;

   private IProgressMonitor progressMonitor;

   private List changedEventsFired;

   private List addedEventsFired;

   private List removedEventsFired;

   private static Gate testGate;

   private static Target testTarget;

   /**
    * Constructor for the test class. A new instance is created for each test
    * method that is executed.
    *
    * The gate and target to be used is controlled by the following Java
    * properties:
    * com.ose.system.tests.gate.address: Gate address (mandatory)
    * com.ose.system.tests.gate.port: Gate port (default 21768)
    * com.ose.system.tests.target.huntpath: Target hunt path (default none)
    *
    * @param test  the name of the test that you want to run.
    */
   public SystemModelTests(String test)
   {
      super(test);

      String address = System.getProperty("com.ose.system.tests.gate.address");
      if (address == null)
      {
         throw new RuntimeException("Gate address not specified.");
      }
      try
      {
         testGateAddress = InetAddress.getByName(address);
      }
      catch (UnknownHostException e)
      {
         throw new RuntimeException("Invalid gate address.");
      }

      String port = System.getProperty("com.ose.system.tests.gate.port");
      if (port != null)
      {
         try
         {
            testGatePort = Integer.parseInt(port);
         }
         catch (NumberFormatException e)
         {
            throw new RuntimeException("Invalid port number.");
         }
      }
      else
      {
         testGatePort = 21768;
      }

      testTargetHuntPath =
         System.getProperty("com.ose.system.tests.target.huntpath", "");
   }

   /**
    * Sets up the preconditions for the tests, and will be carried out for each
    * test.
    */
   public void setUp()
   {
      sm = SystemModel.getInstance();
      systemModelListener = new SystemModelHandler();
      progressMonitor = new NullProgressMonitor();
      changedEventsFired = new ArrayList();
      addedEventsFired = new ArrayList();
      removedEventsFired = new ArrayList();
      sm.addSystemModelListener(systemModelListener);

      // Always perform this initial setup; basically this is equal to
      // performing the three first tests, without this code it is not
      // possible to run subsequent tests independently.
      setTestGate();
      try
      {
         setTestTarget();
         connectToTestTarget();
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
   }

   /**
    * Cleans up after the tests, and will be carried out for each test.
    */
   public void tearDown()
   {
      sm.removeSystemModelListener(systemModelListener);
   }

   /**
    * Sets the test gate.
    */
   public void setTestGate()
   {
      if (testGate == null)
      {
         testGate = sm.addGate(testGateAddress, testGatePort);
      }
   }

   /**
    * Sets the test target.
    *
    * @return true if the target is not on the same hardware and was added using
    * its hunt path, false if the target is on the same hardware and does not
    * need to be added manually.
    * @throws IOException  if an I/O exception occurred.
    */
   public boolean setTestTarget() throws IOException
   {
      if (testTarget == null)
      {
         Target[] targets = testGate.getTargets();
         if (targets.length == 1)
         {
            // The gate and target runs on the same hardware,
            // no need to add the target manually.
            testTarget = targets[0];
            return false;
         }
         else
         {
            // The gate and target does not run on the same hardware,
            // we must add the target manually using its hunt path.
            testTarget = testGate.addTarget(testTargetHuntPath);
            return true;
         }
      }
      else
      {
         Target[] targets = testGate.getTargets();
         return (targets.length != 1);
      }
   }

   /**
    * Connects to the test target and attaches to its event system.
    * @throws IOException  if an I/O exception occurred.
    */
   public void connectToTestTarget() throws IOException
   {
      if (!testTarget.isConnected())
      {
         testTarget.connect(progressMonitor);
         testTarget.update(progressMonitor, true);

         if (testTarget.hasAttachDetachSupport() &&
             testTarget.hasClearEventActionPointSupport() &&
             testTarget.hasEventNotifyEnabledSupport() &&
             testTarget.hasEventTraceEnabledSupport())
         {
            boolean blockFound = false;
            Block[] blocks;

            blocks = testTarget.getBlocks();
            for (int i = 0; i < blocks.length; i++)
            {
               Block block = blocks[i];
               if (block.getName().equals("psi"))
               {
                  blockFound = true;
                  testTarget.attach(progressMonitor,
                                    Target.SCOPE_BLOCK_ID,
                                    block.getId(),
                                    Target.SCOPE_BLOCK_ID,
                                    block.getId(),
                                    false);
                  break;
               }
            }

            if (!blockFound)
            {
               testTarget.attach(progressMonitor,
                                 Target.SCOPE_SYSTEM,
                                 0,
                                 Target.SCOPE_SYSTEM,
                                 0,
                                 false);
            }
         }
      }
   }

   // ---------START TESTS---------------------------------------------

   /**
    * Adds a gate to the System Model and verifies that the System Model gets
    * the correct "added" events.
    */
   public void testAddGate()
   {
      boolean gateAdded = false;
      boolean gateAddedEvent = false;

      setTestGate();

      if (testGate != null)
      {
         Gate[] gates = sm.getGates();
         for (int i = 0; i < gates.length; i++)
         {
            if (gates[i] == testGate)
            {
               gateAdded = true;
               break;
            }
         }
      }

      for (Iterator i = addedEventsFired.iterator(); i.hasNext();)
      {
         SystemModelEvent event = (SystemModelEvent) i.next();
         for (Iterator j = event.getChildren().iterator(); j.hasNext();)
         {
            SystemModelNode node = (SystemModelNode) j.next();
            if (node == testGate)
            {
               // This only makes sure that we get the event we
               // want, not that we get any leftover events that we don't need.
               gateAddedEvent = true;
               break;
            }
         }
      }
      sm.checkState(true);
      assertTrue(gateAdded && gateAddedEvent);
   }

   /**
    * Adds a target to the System Model and verifies that the System Model gets
    * the correct "added" events.
    */
   public void testAddTarget()
   {
      boolean targetAdded = false;
      boolean targetAddedEvent = false;
      Target[] targets;

      try
      {
         boolean remoteTarget = setTestTarget();
         if (!remoteTarget)
         {
            // The gate and target runs on the same hardware,
            // no need to add the target manually and check events.
            return;
         }
         else
         {
            // For a remote target, test adding it again, which should return null.
            Target nullTarget = testGate.addTarget(testTargetHuntPath);
            assertTrue(nullTarget == null);
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }

      targets = testGate.getTargets();
      for (int i = 0; i < targets.length; i++)
      {
         Target target = targets[i];
         if (target == testTarget)
         {
            targetAdded = true;
            break;
         }
      }

      for (Iterator i = addedEventsFired.iterator(); i.hasNext();)
      {
         SystemModelEvent event = (SystemModelEvent) i.next();
         for (Iterator j = event.getChildren().iterator(); j.hasNext();)
         {
            SystemModelNode node = (SystemModelNode) j.next();
            if (node == testTarget)
            {
               targetAddedEvent = true;
               break;
            }
         }
      }
      sm.checkState(true);
      assertTrue(targetAdded && targetAddedEvent);
   }

   /**
    * Connects the System Model to a target that is to be used as test target.
    * Must be performed as the first test in order for the other tests to work.
    */
   public void testConnectToTarget()
   {
      try
      {
         connectToTestTarget();
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on the System Model.
    */
   public void testUpdateSystemModel()
   {
      try
      {
         sm.update(progressMonitor, true);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on all the gates in the System Model.
    */
   public void testUpdateGate()
   {
      Gate[] gates = sm.getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate gate = gates[i];
         try
         {
            gate.update(progressMonitor, true);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on the test target in the System Model.
    */
   public void testUpdateTarget()
   {
      try
      {
         testTarget.update(progressMonitor, true);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on all the segments in the System Model.
    */
   public void testUpdateSegment()
   {
      Segment[] segments = testTarget.getSegments();
      for (int i = 0; i < segments.length; i++)
      {
         Segment segment = segments[i];
         try
         {
            segment.update(progressMonitor, true);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on all the pools in the System Model.
    */
   public void testUpdatePool()
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         try
         {
            pool.update(progressMonitor, true);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on all the heaps in the System Model.
    */
   public void testUpdateHeap()
   {
      Heap[] heaps = testTarget.getHeaps();
      for (int i = 0; i < heaps.length; i++)
      {
         Heap heap = heaps[i];
         try
         {
            heap.update(progressMonitor, true);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on all the blocks in the System Model.
    */
   public void testUpdateBlock()
   {
      Block[] blocks = testTarget.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         try
         {
            block.update(progressMonitor, true);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a recursive update on all the processes in the System Model.
    */
   public void testUpdateProcess()
   {
      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i
            .hasNext();)
      {
         Process process = (Process) i.next();
         try
         {
            process.update(progressMonitor, true);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on the System Model.
    */
   public void testUpdateSystemModelNonRecursive()
   {
      try
      {
         sm.update(progressMonitor, false);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on all the gates in the System Model.
    */
   public void testUpdateGateNonRecursive()
   {
      Gate[] gates = sm.getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate gate = gates[i];
         try
         {
            gate.update(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on the test target in the System Model.
    */
   public void testUpdateTargetNonRecursive()
   {
      try
      {
         testTarget.update(progressMonitor, false);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on all the segments in the System Model.
    */
   public void testUpdateSegmentNonRecursive()
   {
      Segment[] segments = testTarget.getSegments();
      for (int i = 0; i < segments.length; i++)
      {
         Segment segment = segments[i];
         try
         {
            segment.update(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on all the pools in the System Model.
    */
   public void testUpdatePoolNonRecursive()
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         try
         {
            pool.update(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on all the heaps in the System Model.
    */
   public void testUpdateHeapNonRecursive()
   {
      Heap[] heaps = testTarget.getHeaps();
      for (int i = 0; i < heaps.length; i++)
      {
         Heap heap = heaps[i];
         try
         {
            heap.update(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on all the blocks in the System Model.
    */
   public void testUpdateBlockNonRecursive()
   {
      Block[] blocks = testTarget.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         try
         {
            block.update(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Performs a non-recursive update on all the processes in the System Model.
    */
   public void testUpdateProcessNonRecursive()
   {
      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         try
         {
            process.update(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Launches separate threads that perform the same operations on the system
    * model objects, while continuously checking the state of the system model.
    * Any exceptions thrown will cause the test to fail.
    */
   public void testThreadSafety()
   {
      TestThread t1 = new TestThread();
      TestThread t2 = new TestThread();

      // Start t1 and t2 and wait for them to terminate.
      t1.start();
      t2.start();
      while (t1.isAlive() || t2.isAlive())
      {
         // If interrupted while waiting, simply try
         // again until both threads have terminated.
         try { t1.join(); } catch (InterruptedException e) {}
         try { t2.join(); } catch (InterruptedException e) {}
      }

      assertTrue(!t1.getCausedError());
      assertTrue(!t2.getCausedError());
      sm.checkState(true);
   }

   /**
    * Goes through all the gates in the System Model.
    */
   public void testListGates()
   {
      Gate[] gates = sm.getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate gate = gates[i];
         gate.toString();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the targets in the System Model.
    */
   public void testListTargets()
   {
      Gate[] gates = sm.getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate gate = gates[i];
         Target[] targets = gate.getTargets();
         for (int j = 0; j < targets.length; j++)
         {
            Target target = targets[j];
            target.toString();
         }
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the segments in the System Model.
    */
   public void testListSegments()
   {
      Segment[] segments = testTarget.getSegments();
      for (int i = 0; i < segments.length; i++)
      {
         Segment segment = segments[i];
         segment.toString();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the pools in the System Model.
    */
   public void testListPools()
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         pool.toString();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the heaps in the System Model.
    */
   public void testListHeaps()
   {
      Heap[] heaps = testTarget.getHeaps();
      for (int i = 0; i < heaps.length; i++)
      {
         Heap heap = heaps[i];
         heap.toString();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the blocks in the System Model.
    */
   public void testListBlocks()
   {
      Block[] blocks = testTarget.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         block.toString();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the processes in the System Model.
    */
   public void testListProcesses()
   {
      List processes = allProcessesOnTarget(testTarget);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         process.toString();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the load modules on the target. Tries to find the core
    * module by name and by searching for it in the list of all load modules.
    * Accesses all properties of found load modules
    */
   public void testListLoadModules()
   {
      if (testTarget.hasLoadModuleSupport())
      {
         boolean coreModuleFoundByList = false;
         boolean coreModuleFoundByName = false;

         try
         {
            LoadModuleInfo[] loadModules;
            LoadModuleInfo loadModule;

            loadModules = testTarget.getLoadModuleInfo(progressMonitor);
            for (int i = 0; i < loadModules.length; i++)
            {
               // The core module should always be there.
               loadModule = loadModules[i];

               // Access all properties.
               loadModule.getDataBaseLong();
               loadModule.getDataSizeLong();
               loadModule.getEntrypointLong();
               loadModule.getFileFormat();
               loadModule.getFileName();
               loadModule.getInstallHandle();
               loadModule.getNumInstances();
               loadModule.getNumSections();
               loadModule.getOptions();
               loadModule.getTarget();
               loadModule.getTextBaseLong();
               loadModule.getTextSizeLong();
               loadModule.toString();

               if (loadModule.getInstallHandle().equals("core_module"))
               {
                  coreModuleFoundByList = true;
                  break;
               }
            }

            loadModule = testTarget.getLoadModuleInfo(progressMonitor, "core_module");
            if (loadModule != null)
            {
               coreModuleFoundByName = loadModule.getInstallHandle().equals("core_module");
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }

         assertTrue(coreModuleFoundByList && coreModuleFoundByName);
         sm.checkState(true);
      }
   }

   /**
    * Performs a clean() on the System Model.
    */
   public void testCleanSystemModel()
   {
      sm.clean();
      sm.checkState(true);
   }

   /**
    * Performs a clean() on all the gates in the System Model.
    */
   public void testCleanGate()
   {
      Gate[] gates = sm.getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate gate = gates[i];
         gate.clean();
      }
      sm.checkState(true);
   }

   /**
    * Performs a clean() on the test target in the System Model.
    */
   public void testCleanTarget()
   {
      testTarget.clean();
      sm.checkState(true);
   }

   /**
    * Performs a clean() on all the segments in the System Model.
    */
   public void testCleanSegment()
   {
      Segment[] segments = testTarget.getSegments();
      for (int i = 0; i < segments.length; i++)
      {
         Segment segment = segments[i];
         segment.clean();
      }
      sm.checkState(true);
   }

   /**
    * Performs a clean() on all the pools in the System Model.
    */
   public void testCleanPool()
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         pool.clean();
      }
      sm.checkState(true);
   }

   /**
    * Performs a clean() on all the heaps in the System Model.
    */
   public void testCleanHeap()
   {
      Heap[] heaps = testTarget.getHeaps();
      for (int i = 0; i < heaps.length; i++)
      {
         Heap heap = heaps[i];
         heap.clean();
      }
      sm.checkState(true);
   }

   /**
    * Performs a clean() on all the blocks in the System Model.
    */
   public void testCleanBlock() throws IOException
   {
      Block[] blocks = testTarget.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         block.clean();
      }
      sm.checkState(true);
   }

   /**
    * Performs a clean() on all the processes in the System Model.
    */
   public void testCleanProcess()
   {
      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         process.clean();
      }
      sm.checkState(true);
   }

   /**
    * Goes through some of the System Model properties' getXxx() methods.
    */
   public void testAccessingSystemModelProperties()
   {
      sm.getBroadcastPort();
      sm.getBroadcastTimeout();
      sm.getChildren();
      sm.getGates();
      sm.getParent();
      sm.getPingTimeout();
      sm.toString();
      sm.checkState(true);
   }

   /**
    * Goes through all the gate properties' getXxx() methods.
    */
   public void testAccessingGateProperties()
   {
      Gate gate = testTarget.getGate();
      Gate.ping(testTarget.getGate().getAddress(), testTarget.getGate().getPort(), 2000);
      gate.getAddress();
      gate.getParent();
      gate.getChildren();
      gate.getName();
      gate.getPort();
      gate.getTargets();
      assertTrue(testGate.getTarget(testTarget.getHuntPath()) == testTarget);
      gate.isLeaf();
      gate.isKilled();
      gate.isConnected();
      gate.toString();
      sm.checkState(true);
   }

   /**
    * Goes through all the target properties' getXxx() methods.
    */
   public void testAccessingTargetProperties()
   {
      testTarget.getBlocks();
      testTarget.getChildren();
      testTarget.getCpuType();
      testTarget.getGate();
      testTarget.getHeaps();
      testTarget.getHuntPath();
      testTarget.getName();
      testTarget.getNTickFrequency();
      testTarget.getNumExecutionUnits();
      testTarget.getOsType();
      testTarget.getParent();
      testTarget.getPools();
      testTarget.getProcesses();
      testTarget.getSegments();
      testTarget.getTickLength();
      testTarget.isAttached();
      testTarget.isBigEndian();
      testTarget.isConnected();
      try
      {
         testTarget.isEventNotifyEnabled(progressMonitor);
         testTarget.isEventTraceEnabled(progressMonitor);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      testTarget.isFreezeModeMonitor();
      testTarget.isKilled();
      testTarget.isLeaf();
      testTarget.isMonitorObsolete();
      testTarget.isPostMortemMonitor();
      testTarget.isRunModeMonitor();
      testTarget.toString();
      Parameter[] systemParameter = testTarget.getSysParams();
      for (int j = 0; j < systemParameter.length; j++)
      {
         Parameter sp = systemParameter[j];
         sp.getName();
         sp.getValue();
         sp.hashCode();
         sp.toString();
      }

      testTarget.hasAttachDetachSupport();
      testTarget.hasBlockSupport();
      testTarget.hasClearEventActionPointSupport();
      testTarget.hasClearEventTraceSupport();
      testTarget.hasCPUProcessReportPointSupport();
      testTarget.hasCreateProgramSupport();
      testTarget.hasDeprecatedGetEventActionPointsSupport();
      testTarget.hasDumpSupport();
      testTarget.hasEventNotifyEnabledSupport();
      testTarget.hasEventStateSupport();
      testTarget.hasEventTraceEnabledSupport();
      testTarget.hasExtendedSegmentSupport();
      testTarget.hasGetCPUPriorityReportsSupport();
      testTarget.hasGetCPUProcessReportsSupport();
      testTarget.hasGetCPUReportsSupport();
      testTarget.hasGetEnvVarSupport();
      testTarget.hasGetEventActionPointsSupport();
      testTarget.hasGetEventTraceMultipleSupport();
      testTarget.hasGetEventTraceSupport();
      testTarget.hasGetSysParamSupport();
      testTarget.hasGetUserReportsSupport();
      testTarget.hasInstallLoadModuleSupport();
      testTarget.hasInterceptResumeSupport();
      testTarget.hasKillProgramSupport();
      testTarget.hasKillScopeSupport();
      testTarget.hasLoadModuleSupport();
      testTarget.hasMemorySupport();
      testTarget.hasPoolSignalSupport();
      testTarget.hasPoolStackSupport();
      testTarget.hasPoolSupport();
      testTarget.hasProcessSupport();
      testTarget.hasProgramManagerSupport();
      testTarget.hasProgramSupport();
      testTarget.hasSegmentSupport();
      testTarget.hasSetAllocEventActionPointSupport();
      testTarget.hasSetBindEventActionPointSupport();
      testTarget.hasSetCPUPriorityReportsEnabledSupport();
      testTarget.hasSetCPUProcessReportsEnabledSupport();
      testTarget.hasSetCPUReportsEnabledSupport();
      testTarget.hasSetCreateEventActionPointSupport();
      testTarget.hasSetEnvVarSupport();
      testTarget.hasSetErrorEventActionPointSupport();
      testTarget.hasSetFreeEventActionPointSupport();
      testTarget.hasSetKillEventActionPointSupport();
      testTarget.hasSetReceiveEventActionPointSupport();
      testTarget.hasSetScopeSupport();
      testTarget.hasSetSendEventActionPointSupport();
      testTarget.hasSetSwapEventActionPointSupport();
      testTarget.hasSetSysParamSupport();
      testTarget.hasSetTimeoutEventActionPointSupport();
      testTarget.hasSetUserEventActionPointSupport();
      testTarget.hasSetUserReportsEnabledSupport();
      testTarget.hasSignalQueueSupport();
      testTarget.hasStartScopeSupport();
      testTarget.hasStopScopeSupport();
      testTarget.hasTranslateProgramIdSupport();
      testTarget.hasUninstallLoadModuleSupport();

      sm.checkState(true);
   }

   /**
    * If supported, test setting a system parameter.
    */
   public void testSettingTargetSysParam()
   {
      if (testTarget.hasGetSysParamSupport() &&
          testTarget.hasSetSysParamSupport())
      {
         boolean setSysParamOK = false;
         Parameter[] sysParams;

         try
         {
            testTarget.setSysParam(progressMonitor,
                                   false,
                                   "unittest_sysparam",
                                   "unittest_sysparam_value");
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }

         sysParams = testTarget.getSysParams();
         for (int i = 0; i < sysParams.length; i++)
         {
            Parameter sp = sysParams[i];
            if (sp.getName().equals("unittest_sysparam") &&
                sp.getValue().equals("unittest_sysparam_value"))
            {
               setSysParamOK = true;
            }
         }

         assertTrue(setSysParamOK);
         sm.checkState(true);
      }
   }

   /**
    * Goes through all the segment properties' getXxx() methods, and their
    * respective variables.
    */
   public void testAccessingSegmentProperties()
   {
      Segment[] segments = testTarget.getSegments();
      for (int i = 0; i < segments.length; i++)
      {
         Segment segment = segments[i];
         segment.getBlocks();
         segment.getChildren();
         segment.getHeaps();
         segment.getId();
         segment.getName();
         segment.getNumSignalsAttached();
         segment.getParent();
         segment.getPools();
         segment.getSegmentNumber();
         segment.getTarget();
         segment.toString();
         segment.isKilled();
         segment.isLeaf();
         segment.isMasMapped();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the pool properties' getXxx() methods.
    */
   public void testAccessingPoolProperties()
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         pool.getChildren();
         pool.getFreeSize();
         pool.getId();
         pool.getParent();

         PoolFragmentInfo[] poolFragmentInfo = pool.getPoolFragments();
         for (int j = 0; j < poolFragmentInfo.length; j++)
         {
            PoolFragmentInfo pfi = poolFragmentInfo[j];
            pfi.getAddress();
            pfi.getClass();
            pfi.getSize();
            pfi.getUsedStacks();
            pfi.getUsedSignals();
            pfi.hashCode();
            pfi.toString();
            pfi.equals(pfi);
         }

         pool.getSid();

         PoolBufferSizeInfo[] poolBufferSizeInfoSignal = pool.getSignalSizes();
         for (int j = 0; j < poolBufferSizeInfoSignal.length; j++)
         {
            PoolBufferSizeInfo pbsi = poolBufferSizeInfoSignal[j];
            pbsi.getAllocated();
            pbsi.getClass();
            pbsi.getFree();
            pbsi.getSize();
            pbsi.hashCode();
            pbsi.toString();
            pbsi.equals(pbsi);
         }

         PoolBufferSizeInfo[] poolBufferSizeInfoStack = pool.getStackSizes();
         for (int j = 0; j < poolBufferSizeInfoStack.length; j++)
         {
            PoolBufferSizeInfo pbsi = poolBufferSizeInfoStack[j];
            pbsi.getAllocated();
            pbsi.getClass();
            pbsi.getFree();
            pbsi.getSize();
            pbsi.hashCode();
            pbsi.toString();
         }

         pool.getSegment();
         pool.getTarget();
         pool.toString();
         pool.isLeaf();
         pool.isKilled();
         pool.getTotalSize();
         try
         {
            SignalInfo[] poolSignals = pool.getFilteredPoolSignals(
                  progressMonitor, null);
            for (int j = 0; j < poolSignals.length; j++)
            {
               SignalInfo signal = poolSignals[j];
               signal.getAddressee();
               signal.getOwner();
               signal.getAddress();
               signal.getSender();
               signal.getSigNo();
               signal.getSize();
               signal.getBufferSize();
               signal.getStatus();
               signal.getData();
               signal.hashCode();
               signal.toString();
               signal.equals(signal);
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the heap properties' getXxx() methods.
    */
   public void testAccessingHeapProperties()
   {
      Heap[] heaps = testTarget.getHeaps();
      for (int i = 0; i < heaps.length; i++)
      {
         Heap heap = heaps[i];
         heap.getChildren();
         heap.getFreeSize();
         heap.getId();
         heap.getParent();

         HeapFragmentInfo[] heapFragmentInfo = heap.getHeapFragments();
         for (int j = 0; j < heapFragmentInfo.length; j++)
         {
            HeapFragmentInfo hfi = heapFragmentInfo[j];
            hfi.getAddress();
            hfi.getClass();
            hfi.getSize();
            hfi.getUsedSize();
            hfi.hashCode();
            hfi.toString();
            hfi.equals(hfi);
         }

         heap.getSid();

         HeapBufferSizeInfo[] heapBufferSizeInfo = heap.getHeapBufferSizes();
         for (int j = 0; j < heapBufferSizeInfo.length; j++)
         {
            HeapBufferSizeInfo hbsi = heapBufferSizeInfo[j];
            hbsi.getAllocated();
            hbsi.getClass();
            hbsi.getFree();
            hbsi.getSize();
            hbsi.hashCode();
            hbsi.toString();
            hbsi.equals(hbsi);
         }

         heap.getSegment();
         heap.getTarget();
         heap.toString();
         heap.isLeaf();
         heap.isKilled();
         heap.getSize();
         try
         {
            HeapBufferInfo[] heapBuffers = heap.getFilteredHeapBuffers(
                  progressMonitor, null);
            for (int j = 0; j < heapBuffers.length; j++)
            {
               HeapBufferInfo heapBuffer = heapBuffers[j];
               heapBuffer.getOwner();
               heapBuffer.isShared();
               heapBuffer.getAddress();
               heapBuffer.getSize();
               heapBuffer.getRequestedSize();
               heapBuffer.getFileName();
               heapBuffer.getLineNumber();
               heapBuffer.getStatus();
               heapBuffer.hashCode();
               heapBuffer.toString();
               heapBuffer.equals(heapBuffer);
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the block properties' getXxx() methods, and their
    * respective variables.
    */
   public void testAccessingBlockProperties()
   {
      Block[] blocks = testTarget.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         block.getChildren();
         block.getErrorHandler();
         block.getExecutionUnit();
         block.getId();
         block.getMaxSignalSize();
         block.getName();
         block.getNumSignalsAttached();
         block.getParent();
         block.getProcesses();
         block.getSegment();
         block.getSid();
         block.getSignalPoolId();
         block.getStackPoolId();
         block.getTarget();
         block.getUid();
         block.toString();
         Parameter[] envVars = block.getEnvVars();
         for (int j = 0; j < envVars.length; j++)
         {
            Parameter envVar = envVars[j];
            envVar.getName();
            envVar.getValue();
         }
         block.isSupervisor();
         block.isKilled();
         block.isLeaf();
      }
      sm.checkState(true);
   }

   /**
    * Goes through all the process properties' getXxx() methods, and their
    * respective variables.
    */
   public void testAccessingProcessProperties()
   {
      List processes = allProcessesOnTarget(testTarget);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         process.getBid();
         process.getBlock();
         process.getChildren();
         process.getCreator();
         process.getEntrypoint();
         process.getEnvVars();
         process.getErrorHandler();
         process.getFileName();
         process.getSemaphoreValue();
         process.getId();
         process.getLineNumber();
         process.getName();
         process.getNumSignalsAttached();
         process.getNumSignalsOwned();
         process.getNumSignalsInQueue();
         process.getParent();
         process.getPriority();

         SignalInfo[] signalInfo = process.getSignalQueue();
         for (int j = 0; j < signalInfo.length; j++)
         {
            SignalInfo si = signalInfo[j];
            si.getAddress();
            si.getAddressee();
            si.getBufferSize();
            si.getClass();
            si.getData();
            si.getOwner();
            si.getSender();
            si.getSigNo();
            si.getSize();
            si.getStatus();
            si.hashCode();
            si.toString();
         }

         process.getSigselect();
         process.getStackSize();
         process.getSupervisorStackSize();
         process.getState();
         process.getTimeSlice();
         process.getType();
         process.getUid();
         process.getVector();
         process.isSupervisor();
         process.isLeaf();
         process.isKilled();
         process.toString();
         SignalInfo[] signalQueue = process.getSignalQueue();
         for (int j = 0; j < signalQueue.length; j++)
         {
            SignalInfo signal = signalQueue[j];
            signal.getAddressee();
            signal.getData();
            signal.getSender();
            signal.getSigNo();
            signal.getSize();
         }
         process.getExecutionUnit();
         process.getProperties();
      }
      sm.checkState(true);
   }

   /**
    * Check that the process types are only those allowed for OSEck
    */
   public void testOSEckProcessTypes()
   {
      boolean success = true;
      List processes = allProcessesOnTarget(testTarget);

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process p = (Process) i.next();
         if (p.getType() != Process.TYPE_PRIORITIZED &&
             p.getType() != Process.TYPE_IDLE &&
             p.getType() != Process.TYPE_INTERRUPT &&
             p.getType() != Process.TYPE_PHANTOM)
         {
            success = false;
         }
      }
      assertTrue(success);
   }

   /**
    * Check that the process states are only those allowed in OSeck:
    */
   public void testOSEckProcessStates()
   {
      boolean success = true;
      List processes = allProcessesOnTarget(testTarget);

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process p = (Process) i.next();
         if (p.getState() != Process.STATE_STOPPED &&
             p.getState() != Process.STATE_READY &&
             p.getState() != Process.STATE_RUNNING &&
             p.getState() != Process.STATE_DELAY &&
             p.getState() != Process.STATE_FAST_SEMAPHORE &&
             p.getState() != Process.STATE_RECEIVE &&
             p.getState() != Process.STATE_SEMAPHORE)
         {
            success = false;
         }
      }
      assertTrue(success);
   }

   /**
    * Check that at least one pool that contains both signals and stacks exists
    */
   public void testOSEckPoolContent()
   {
      boolean success = false;

      try
      {
         Pool[] pools = testTarget.getPools();
         for (int i = 0; i < pools.length; i++)
         {
            Pool pool = pools[i];
            SignalInfo[] signals = pool.getFilteredPoolSignals(progressMonitor, new SignalFilter());
            StackInfo[] stacks = pool.getFilteredPoolStacks(progressMonitor, new StackFilter());
            if (signals.length > 0 && stacks.length > 0)
            {
               success = true;
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      assertTrue(success);
   }

   /**
    * Check the maximum number of event action points that can be set on the target is 8
    */
   public void testOSEckMaxEventActionPoints()
   {
      int maxEventActionPoints = 8;
      int eventsSet = 0;

      try
      {
         // set notify events
         if (testTarget.hasEventNotifyEnabledSupport())
         {
            TargetEventVerifier targetEventNotificationVerifier =
               new TargetEventVerifier(EventActionPoint.ACTION_NOTIFY);
            List notificationPoints = targetEventNotificationVerifier.getEventActionPoints();
            for (Iterator i = notificationPoints.iterator(); i.hasNext();)
            {
               EventActionPoint eventActionPoint = (EventActionPoint) i.next();
               testTarget.setEventActionPoint(progressMonitor, eventActionPoint);
               eventsSet++;
            }
         }


         // set trace events
         if (testTarget.hasGetEventTraceMultipleSupport())
         {
            TargetEventVerifier targetEventTraceVerifier =
               new TargetEventVerifier(EventActionPoint.ACTION_TRACE);
            List tracePoints = targetEventTraceVerifier.getEventActionPoints();
            for (Iterator i = tracePoints.iterator(); i.hasNext();)
            {
               EventActionPoint eventActionPoint = (EventActionPoint) i.next();
               testTarget.setEventActionPoint(progressMonitor, eventActionPoint);
               eventsSet++;
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      catch (com.ose.system.ServiceException se)
      {
         assertTrue(eventsSet == maxEventActionPoints);
      }
      finally
      {
         // need to clear the event action points regardless
         try
         {
            if (testTarget.hasClearAllEventActionPointsSupport())
            {
               testTarget.clearEventActionPoint(progressMonitor, null);
            }
            else if (testTarget.hasClearEventActionPointSupport())
            {
               EventActionPoint[] points =
                  testTarget.getEventActionPoints(progressMonitor);
               for (int i = 0; i < points.length; i++)
               {
                  testTarget.clearEventActionPoint(progressMonitor, points[i]);
               }
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      assertTrue(eventsSet <= maxEventActionPoints);
   }

   /**
    * OSEck list signals with signal number 0xFFFFFFFF
    * Currently the only way to list that signal is to exclude 0 to 0xFFFFFFFF
    * that is because the upper margin of the interval is exclusive.
    * error is both in OSE and OSEck
    */
   public void testFilterSpecifiedSignal()
   {
      int signalNumber = -1;
      boolean success = false;

      signalNumber = -1;

      String signalNumberProperty = System.getProperty("com.ose.system.tests.filteredSignal");
      if (signalNumberProperty != null)
      {
         try
         {
            signalNumber = Integer.parseInt(signalNumberProperty);
         }
         catch (NumberFormatException e)
         {
            throw new RuntimeException("Invalid signal number.");
         }
      }
      if (signalNumber != -1)
      {
         SignalFilter sf = new SignalFilter();
         sf.filterNumber(false, signalNumber, signalNumber +1);

         try{
            Pool[] pools = testTarget.getPools();
            for (int i = 0; i < pools.length; i++)
            {
               Pool pool = pools[i];

               SignalInfo[] signals = signalFilterTester(sf, pool.getId());
               if (signals.length > 0)
               {
                  success = true;
               }
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
      else
      {
         success = true;
      }
      assertTrue(success);
   }

   /**
    * Stops process "alfa_0" on the test target.
    */
   public void testStopProcess()
   {
      if (testTarget.hasStopScopeSupport())
      {
         boolean success = stopNodeInSystemModel(allProcessesOnTarget(testTarget),
               "alfa_0");
         sm.checkState(true);
         assertTrue(success);
      }
   }

   /**
    * Starts process "alfa_0" on the test target.
    */
   public void testStartProcess()
   {
      if (testTarget.hasStartScopeSupport())
      {
         boolean success = startNodeInSystemModel(
               allProcessesOnTarget(testTarget), "alfa_0");
         sm.checkState(true);
         assertTrue(success);
      }
   }

   /**
    * Stops block "alfa" on the test target.
    */
   public void testStopBlock()
   {
      if (testTarget.hasStopScopeSupport() && testTarget.hasBlockSupport())
      {
         boolean success = stopNodeInSystemModel(Arrays.asList(testTarget
               .getBlocks()), "alfa");
         sm.checkState(true);
         assertTrue(success);
      }
   }

   /**
    * Starts block "alfa" on the test target.
    */
   public void testStartBlock()
   {
      if (testTarget.hasStartScopeSupport() && testTarget.hasBlockSupport())
      {
         boolean success = startNodeInSystemModel(Arrays.asList(testTarget
               .getBlocks()), "alfa");
         sm.checkState(true);
         assertTrue(success);
      }
   }

   /**
    * If segments are supported, stops the segment of the system model tests on
    * the test target if that segment is not the system segment.
    */
   public void testStopSegment()
   {
      if (testTarget.hasStopScopeSupport() && testTarget.hasExtendedSegmentSupport())
      {
         Segment segment = findTestSegment();
         if ((segment != null) && (segment.getId() != 0))
         {
            boolean success = stopNodeInSystemModel(Arrays.asList(testTarget
                  .getSegments()), segment.getName());
            sm.checkState(true);
            assertTrue(success);
         }
      }
   }

   /**
    * If segments are supported, starts the segment of the system model tests on
    * the test target if that segment is not the system segment.
    */
   public void testStartSegment()
   {
      if (testTarget.hasStartScopeSupport() && testTarget.hasExtendedSegmentSupport())
      {
         Segment segment = findTestSegment();
         if ((segment != null) && (segment.getId() != 0))
         {
            boolean success = startNodeInSystemModel(Arrays.asList(testTarget
                  .getSegments()), segment.getName());
            sm.checkState(true);
            assertTrue(success);
         }
      }
   }

   /**
    * Tests all parameters in all scopes in the block filter. Depends on the
    * block "alfa"
    */
   public void testBlockFilter()
   {
      if (testTarget.hasBlockSupport())
      {
         int alfaBID = 0;
         int alfaSID = 0;
         boolean alfaExists = false;
         boolean success1 = false;
         boolean success2 = false;
         boolean success3 = false;

         Block[] blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block b = blocks[i];
            if (b.getName().equals("alfa"))
            {
               alfaExists = true;
               alfaBID = b.getId();
               alfaSID = b.getSid();
            }
         }

         if (blockFilterTester(Target.SCOPE_SYSTEM, 0))
         {
            success1 = true;
         }
         else
         {
            System.out.println("testBlockFilter, system scope: failed");
         }

         if (alfaExists)
         {
            if (blockFilterTester(Target.SCOPE_SEGMENT_ID, alfaSID))
            {
               success2 = true;
            }
            else
            {
               System.out.println("testBlockFilter, segment scope: failed");
            }
            if (blockFilterTester(Target.SCOPE_BLOCK_ID, alfaBID))
            {
               success3 = true;
            }
            else
            {
               System.out.println("testBlockFilter, block scope: failed");
            }
         }
         else
         {
            System.out.println("Block alfa does not exist");
         }
         sm.checkState(true);
         assertTrue(success1 && success2 && success3);
      }
   }

   /**
    * Tests all parameters in all scopes in the process filter. Depends on the
    * process "alfa_0"
    */
   public void testProcessFilter()
   {
      int alfa_0PID = 0;
      int alfa_0BID = 0;
      boolean alfa_0Exists = false;
      boolean success1 = false;
      boolean success2 = false;
      boolean success3 = false;

      List processes = allProcessesOnTarget(testTarget);

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process p = (Process) i.next();
         if (p.getName().equals("alfa_0"))
         {
            alfa_0Exists = true;
            alfa_0PID = p.getId();
            alfa_0BID = p.getBid();
         }
      }

      if (processFilterTester(Target.SCOPE_SYSTEM, 0))
      {
         success1 = true;
      }
      else
      {
         System.out.println("testProcessFilter, system scope: failed");
      }

      if (alfa_0Exists)
      {
         if (testTarget.hasBlockSupport())
         {
            if (processFilterTester(Target.SCOPE_BLOCK_ID, alfa_0BID))
            {
               success2 = true;
            }
            else
            {
               System.out.println("testProcessFilter, block scope: failed");
            }
         }
         else
         {
            success2 = true;
         }

         if (processFilterTester(Target.SCOPE_ID, alfa_0PID))
         {
            success3 = true;
         }
         else
         {
            System.out.println("testProcessFilter, process scope: failed");
         }
      }
      else
      {
         System.out.println("Process alfa_0 does not exist");
      }
      sm.checkState(true);
      assertTrue(success1 && success2 && success3);
   }

   public void testProcessFilterCriteria()
   {
      // We will use the name filter to restrict the search space, and then test
      // various known criteria to find just the process we are interested in.

      try
      {
         ProcessFilter pf;

         pf = new ProcessFilter();
         pf.filterName(false, "omega_?");
         pf.filterState(false, ProcessInfo.STATE_STOPPED);
         assertTrue(findSingleProcessNamed("omega_1", pf));

         // Exclude all possible states for omega_* processes
         // except the one of omega_4 (fsem).
         pf = new ProcessFilter();
         pf.filterName(false, "omega_?");
         pf.filterState(true, ProcessInfo.STATE_STOPPED);
         pf.filterState(true, ProcessInfo.STATE_DELAY);
         pf.filterState(true, ProcessInfo.STATE_READY);
         pf.filterState(true, ProcessInfo.STATE_RUNNING);
         pf.filterState(true, ProcessInfo.STATE_RECEIVE);
         assertTrue(findSingleProcessNamed("omega_4", pf));

         // Must have exactly 4 signals in its queue.
         pf = new ProcessFilter();
         pf.filterName(false, "omega_?");
         pf.filterSignalsInQueue(false, 4, 5);
         assertTrue(findSingleProcessNamed("omega_3", pf));
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
   }

   /**
    * Tests filtering signals by sender name (both including and excluding
    * signals from that sender). Depends on the process "omega_2", which is
    * expected to send exactly four signals to omega_3.
    */
   public void testSignalFilter()
   {
      try
      {
         boolean success = true;
         Process omega_2_;
         Process ose_sysd_;
         int signalPoolId = 0;
         SignalFilter sf;
         SignalInfo[] signals;
         int numFromOmega_2 = 0;

         omega_2_ = findProcessOnTarget("omega_2", testTarget);
         ose_sysd_ = findProcessOnTarget("ose_sysd", testTarget);
         if (testTarget.hasBlockSupport())
         {
            signalPoolId = omega_2_.getBlock().getSignalPoolId();
         }
         else
         {
            Pool[] targetPools = testTarget.getPools();
            if (targetPools.length > 0)
            {
               // We use the first pool available.
               signalPoolId = targetPools[0].getId();
            }
            else
            {
               System.out.println("There are no pools on the target");
               fail();
            }
         }

         // Count total number of signals from omega_2.
         sf = new SignalFilter();
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getSender() == omega_2_.getId())
            {
               numFromOmega_2++;
            }
         }

         // Find signals sent from "omega_2" and compare with previous count.
         sf = new SignalFilter();
         sf.filterSenderName(false, "omega_2");
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         if (signals.length != 4)
         {
            success = false;
         }
         // 2008-12-11: This fails on OSE because of OSECR-5391.
         if (numFromOmega_2 != 4)
         {
            success = false;
         }

         // Find signals NOT sent from "omega_2", none of them should have
         // omega_2 as sender. Note: Other processes may be sending signals
         // while this test is performed.
         sf = new SignalFilter();
         sf.filterSenderName(true, "omega_2");
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getSender() == omega_2_.getId())
            {
               success = false;
            }
         }

         sf = new SignalFilter();
         sf.filterSize(true, 2048, Integer.MAX_VALUE);
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getSize() >= 2048)
            {
               success = false;
               System.out.println("SignalFilter-test getSize() true");
            }
         }

         sf = new SignalFilter();
         sf.filterStatus(false, SignalInfo.STATUS_FREE);
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getStatus() != SignalInfo.STATUS_FREE)
            {
               success = false;
               System.out.println("SignalFilter-test getStatus() false");
            }
         }

         sf = new SignalFilter();
         sf.filterBufferSize(false, 0, 128);
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getBufferSize() >= 128)
            {
               success = false;
               System.out.println("SignalFilter-test getBufferSize() false");
            }
         }

         sf = new SignalFilter();
         sf.filterNumber(true, 1001, Integer.MAX_VALUE);
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getSigNo() > 1000)
            {
               success = false;
               System.out.println("SignalFilter-test getSigNo() true");
            }
         }

         sf = new SignalFilter();
         sf.filterOwnerName(false, "ose_sysd");
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getOwner() != ose_sysd_.getId())
            {
               success = false;
               System.out.println("SignalFilter-test getOwner() false (1)");
            }
         }

         sf = new SignalFilter();
         sf.filterOwnerId(false, ose_sysd_.getId());
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getOwner() != ose_sysd_.getId())
            {
               success = false;
               System.out.println("SignalFilter-test getOwner() false (2)");
            }
         }

         sf = new SignalFilter();
         sf.filterAddresseeName(false, "ose_sysd");
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getAddressee() != ose_sysd_.getId())
            {
               success = false;
               System.out.println("SignalFilter-test getAddressee() false (1)");
            }
         }

         sf = new SignalFilter();
         sf.filterAddresseeId(false, ose_sysd_.getId());
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getAddressee() != ose_sysd_.getId())
            {
               success = false;
               System.out.println("SignalFilter-test getAddressee() false (2)");
            }
         }

         sf = new SignalFilter();
         sf.filterSenderId(false, ose_sysd_.getId());
         sf.toString();
         signals = signalFilterTester(sf, signalPoolId);
         for (int i = 0; i < signals.length; i++)
         {
            SignalInfo signal = signals[i];
            if (signal.getSender() != ose_sysd_.getId())
            {
               success = false;
               System.out.println("SignalFilter-test getSender() false");
            }
         }

         if (!success)
         {
            fail();
         }

         sm.checkState(true);
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
   }

   /**
    * Tests filtering stacks by owner name. Depends on the process "omega_2".
    * Also tests basic stack properties against known facts for the stack of
    * omega_2.
    */
   public void testStackFilter()
   {
      try
      {
         boolean success = true;
         Process omega_2_;
         int stackPoolId = 0;
         StackFilter sf;
         StackInfo[] stacks;
         StackInfo stack;

         omega_2_ = findProcessOnTarget("omega_2", testTarget);
         if (testTarget.hasBlockSupport())
         {
            stackPoolId = omega_2_.getBlock().getStackPoolId();
         }
         else
         {
            Pool[] targetPools = testTarget.getPools();
            if (targetPools.length > 0)
            {
               // We use the first pool available.
               stackPoolId = targetPools[0].getId();
            }
            else
            {
               System.out.println("There are no pools on the target");
               fail();
            }
         }

         sf = new StackFilter();
         sf.filterOwnerName(false, "omega_2");
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         assertTrue(stacks.length >= 1);
         stack = stacks[0];
         assertTrue(stack.getPid() == omega_2_.getId());
         // The process was created with 1000 as stack size, but there is overhead.
         assertTrue(stack.getSize() > 1000);
         // Due to internal fragmentation, the actual buffer size may be larger.
         assertTrue(stack.getBufferSize() >= stack.getSize());
         assertTrue(stack.equals(stack));
         stack.hashCode();
         stack.toString();
         if (stack.getUsed() > 0)
         {
            // If the target is a soft kernel, the stack usage meter does not
            // work. If it does work, at least 128 bytes should be used, since
            // a char[128] is put on the stack.
            assertTrue(stack.getUsed() > 128);
            assertTrue(stack.getUsed() <= stack.getSize());
         }

         // Check that the inverse filter for omega_2 does not find the stack of omega_2.
         sf = new StackFilter();
         sf.filterOwnerName(true, "omega_2");
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if (stacks[i].getPid() == omega_2_.getId())
            {
               fail();
            }
         }

         // Now some tests that do not assume anything about the target,
         // they just test that the methods are logically ok.

         sf = new StackFilter();
         sf.filterBufferSize(true, 1025, Integer.MAX_VALUE);
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if (stacks[i].getBufferSize() >= 1025)
            {
               success = false;
               System.out.println("StackFilter-test getBufferSize() true");
            }
         }

         sf = new StackFilter();
         sf.filterSize(false, 0, 129);
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if (stacks[i].getSize() >= 129)
            {
               success = false;
               System.out.println("StackFilter-test getSize() true");
            }
         }

         sf = new StackFilter();
         sf.filterUsed(false, 0, 129);
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if (stacks[i].getUsed() >= 129)
            {
               success = false;
               System.out.println("StackFilter-test getUsed() false");
            }
         }

         sf = new StackFilter();
         sf.filterUnused(true, 129, Integer.MAX_VALUE);
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if ((stacks[i].getSize() - stacks[i].getUsed()) >= 129)
            {
               success = false;
               System.out.println("StackFilter-test filterUnused() false");
            }
         }

         sf = new StackFilter();
         sf.filterUnused(true, 129, Integer.MAX_VALUE);
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if ((stacks[i].getSize() - stacks[i].getUsed()) >= 129)
            {
               success = false;
               System.out.println("StackFilter-test filterUnused() false");
            }
         }

         sf = new StackFilter();
         sf.filterOwnerId(false, omega_2_.getId());
         sf.toString();
         stacks = stackFilterTester(sf, stackPoolId);
         for (int i = 0; i < stacks.length; i++)
         {
            if (stacks[i].getPid() != omega_2_.getId())
            {
               success = false;
               System.out.println("StackFilter-test getPid() false");
            }
         }

         if (!success)
         {
            fail();
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
   }

   /**
    * Tests retrieving program info. The target must have a program manager.
    * Depends on the process "omega_2".
    */
   public void testProgramInfo()
   {
      if (testTarget.hasProgramSupport())
      {
         int omega2Id = 0;

         // First find the id of the omega_2 process.
         try
         {
            ProcessFilter pf;
            ProcessInfo[] pia;

            pf = new ProcessFilter();
            pf.filterName(false, "omega_2");
            pia = testTarget.getFilteredProcesses(progressMonitor,
                                                  Target.SCOPE_SYSTEM,
                                                  0,
                                                  pf);
            if ((pia.length == 1) && pia[0].getName().equals("omega_2"))
            {
               omega2Id = pia[0].getId();
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }

         try
         {
            int programId;
            ProgramInfo programInfo;

            programId = testTarget.getProgramId(progressMonitor, omega2Id);
            programInfo = testTarget.getProgramInfo(progressMonitor, programId);
            assertTrue(programInfo.getId() == programId);
            assertTrue(programInfo.getState() == ProgramInfo.PROGRAM_STARTED);
            assertTrue(programInfo.getTarget() == testTarget);
            // Access remaining properties.
            programInfo.getDomain();
            programInfo.getHeap();
            programInfo.getInstallHandle();
            programInfo.getMainBid();
            programInfo.getMainPid();
            programInfo.getSid();
            programInfo.getSignalPoolBaseLong();
            programInfo.getSignalPoolId();
            programInfo.getSignalPoolSizeLong();
            programInfo.getStackPoolBaseLong();
            programInfo.getStackPoolId();
            programInfo.getStackPoolSizeLong();
            programInfo.getUid();
            programInfo.hasExtendedInfo();
            programInfo.toString();
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
   }

   // In the "sysmodtest" test module:
   // omega_0 waits in a delay
   // omega_1 is stopped
   // omega_2 waits in a receive
   // omega_3 has four signals in its signal queue
   // omega_4 waits on a fast semaphore
   // omega_5 has three environment variables

   /**
    * Verifies that process "omega_2" on the test target is in receive state.
    */
   public void testVerifyReceiveStateOnProcess()
   {
      boolean receiveYes = false;
      List processes = allProcessesOnTarget(testTarget);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals("omega_2"))
            if ((process.getState() & Process.STATE_RECEIVE) > 0)
            {
               receiveYes = true;
            }
      }
      sm.checkState(true);
      assertTrue(receiveYes);
   }

   /**
    * Verifies that process "omega_3" on the test target has a signal queue with
    * 4 signals waiting.
    */
   public void testVerifySignalQueueOnProcess()
   {
      final int numSignalsInQueue = 4;
      boolean rightNumSignalsInQueue = false;
      List processes = allProcessesOnTarget(testTarget);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals("omega_3"))
         {
            if (process.getNumSignalsInQueue() == numSignalsInQueue)
            {
               rightNumSignalsInQueue = true;
            }
         }
      }
      sm.checkState(true);
      assertTrue(rightNumSignalsInQueue);
   }

   /**
    * Verifies that process "omega_4" on the test target flags a semaphore.
    */
   public void testVerifySemaphoreOnProcess()
   {
      boolean semaphoreYes = false;
      List processes = allProcessesOnTarget(testTarget);
      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals("omega_4"))
         {
            if ((process.getState() & Process.STATE_FAST_SEMAPHORE) > 0)
            {
               semaphoreYes = true;
            }
         }
      }
      sm.checkState(true);
      assertTrue(semaphoreYes);
   }

   /**
    * Verifies that process "omega_5" on the test target has the environment
    * variables "athens", "kos", and "crete" with the respective values "sun",
    * "rain", and "hot". Also tests setting a new environment variable.
    */
   public void testVerifyEnvVarsOnProcess()
   {
      if (testTarget.hasGetEnvVarSupport() && testTarget.hasSetEnvVarSupport())
      {
         boolean envVar0 = false;
         boolean envVar1 = false;
         boolean envVar2 = false;
         boolean newEnvVar = false;

         boolean testSucceded = false;
         List processes = allProcessesOnTarget(testTarget);
         for (Iterator i = processes.iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.getName().equals("omega_5"))
            {
               Parameter[] envVars;

               try
               {
                  process.initLazyProperties(progressMonitor);
               }
               catch (IOException e)
               {
                  e.printStackTrace();
                  fail();
               }

               envVars = process.getEnvVars();
               for (int j = 0; j < envVars.length; j++)
               {
                  Parameter envVar = envVars[j];
                  if (envVar.getName().equals("athens")
                        && envVar.getValue().equals("sun"))
                  {
                     envVar0 = true;
                  }
                  if (envVar.getName().equals("kos")
                        && envVar.getValue().equals("rain"))
                  {
                     envVar1 = true;
                  }
                  if (envVar.getName().equals("crete")
                        && envVar.getValue().equals("hot"))
                  {
                     envVar2 = true;
                  }
               }

               // TODO: Setting pointer-valued environment variables is not yet
               // supported by the system model. Test to set and get a pointer-
               // valued environment variable when that support is added.

               try
               {
                  process.setEnvVar(progressMonitor, "sparta", "warriors");
               }
               catch (IOException e)
               {
                  e.printStackTrace();
                  fail();
               }

               envVars = process.getEnvVars();
               for (int j = 0; j < envVars.length; j++)
               {
                  Parameter envVar = envVars[j];
                  if (envVar.getName().equals("sparta") &&
                      envVar.getValue().equals("warriors"))
                  {
                     newEnvVar = true;
                  }
               }
            }
         }

         sm.checkState(true);
         testSucceded = envVar0 && envVar1 && envVar2;
         assertTrue(testSucceded);
         testSucceded = newEnvVar;
         assertTrue(testSucceded);
      }
   }

   /**
    * Verifies that process "omega_bg" on the test target is a backgound
    * process.
    */
   public void testVerifyBackgroundTypeOnProcess()
   {
      boolean background = false;
      List processes = allProcessesOnTarget(testTarget);

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals("omega_bg"))
         {
            if ((process.getType() & Process.TYPE_BACKGROUND) > 0)
            {
               background = true;
            }
         }
      }
      sm.checkState(true);
      assertTrue(background);
   }

   /**
    * Verifies that process "omega_pht" on the test target is a phantom process.
    */
   public void testVerifyPhantomTypeOnProcess()
   {
      boolean phantom = false;
      List processes = allProcessesOnTarget(testTarget);

      for (Iterator i = processes.iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals("omega_pht"))
         {
            if ((process.getType() & Process.TYPE_PHANTOM) > 0)
            {
               phantom = true;
            }
         }
      }
      sm.checkState(true);
      assertTrue(phantom);
   }

   /**
    * Sets and gets block environment variables on the block "psi" on the test
    * target.
    */
   public void testVerifyEnvVarsOnBlock()
   {
      if (testTarget.hasBlockSupport() &&
          testTarget.hasGetEnvVarSupport() &&
          testTarget.hasSetEnvVarSupport())
      {
         boolean envVar0 = false;
         boolean envVar1 = false;
         boolean testSucceded = false;
         Block[] blocks;

         blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block block = blocks[i];
            if (block.getName().equals("psi"))
            {
               Parameter[] envVars;

               // TODO: Setting pointer-valued environment variables is not yet
               // supported by the system model. Test to set and get a pointer-
               // valued environment variable when that support is added.

               try
               {
                  block.initLazyProperties(progressMonitor);
                  block.setEnvVar(progressMonitor, "Hannibal", "Carthage");
                  block.setEnvVar(progressMonitor, "Scipio", "Rome");
               }
               catch (IOException e)
               {
                  e.printStackTrace();
                  fail();
               }

               envVars = block.getEnvVars();
               for (int j = 0; j < envVars.length; j++)
               {
                  Parameter envVar = envVars[j];

                  if (envVar.getName().equals("Hannibal") &&
                      envVar.getValue().equals("Carthage"))
                  {
                     envVar0 = true;
                  }
                  if (envVar.getName().equals("Scipio") &&
                      envVar.getValue().equals("Rome"))
                  {
                     envVar1 = true;
                  }
               }
            }
         }

         sm.checkState(true);
         testSucceded = envVar0 && envVar1;
         assertTrue(testSucceded);
      }
   }

   /**
    * Pings the gates in the System Model.
    */
   public void testPingGates()
   {
      boolean pingSucceded = true;

      Gate[] gates = sm.getGates();
      for (int i = 0; i < gates.length; i++)
      {
         Gate gate = gates[i];
         if (!(gate.ping(2000)))
         {
            pingSucceded = false;
         }
      }
      sm.checkState(true);
      assertTrue(pingSucceded);
   }

   /**
    * Checks that a certain set of events have been received as notifications.
    * Depends on the test setup having added a TargetEventVerifier
    * (TargetListener) and enabled the event notification.
    */
   public void testTargetEventNotifications()
   {
      if (testTarget.hasEventNotifyEnabledSupport())
      {
         TargetEventVerifier targetEventNotificationVerifier =
            new TargetEventVerifier(EventActionPoint.ACTION_NOTIFY);
         testTarget.addTargetListener(targetEventNotificationVerifier);

         try
         {
            setEventActions(targetEventNotificationVerifier.getEventActionPoints());
            testTarget.setEventNotifyEnabled(progressMonitor, true);
         }
         catch (IOException e)
         {
            fail();
         }
         // Wait in order for some events to reach us. The longest running period
         // in the test is for psi_3. It has a cycle of at least 10s, and was
         // launched before the test so sleep 10s + 5s = 15s counting from the
         // moment we started listening to events, to be sure.
         try
         {
            Thread.sleep(15000);
         } catch (InterruptedException ignore) {}

         try
         {
            // Disable event notification so that no more notifications arrive.
            testTarget.setEventNotifyEnabled(progressMonitor, false);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }

         if (targetEventNotificationVerifier.getLossEvent().length > 0)
         {
            System.out.println("The testTargetEventTrace may fail because there are lost events");
         }

         targetEventNotificationVerifier.accessAllEventProperties();
         if (testTarget.hasSetSendEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkSendEvents());
         }
         if (testTarget.hasSetReceiveEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkReceiveEvents());
         }
         if (testTarget.hasSetFreeEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkFreeEvents());
         }
         if (testTarget.hasSetAllocEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkAllocEvents());
         }
         if (testTarget.hasSetCreateEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkCreateEvents());
         }
         if (testTarget.hasSetKillEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkKillEvents());
         }
         if (testTarget.hasSetErrorEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkErrorEvents());
         }
         if (testTarget.hasSetBindEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkBindEvents());
         }
         if (testTarget.hasSetUserEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkUserEvents());
         }
         if (testTarget.hasSetTimeoutEventActionPointSupport())
         {
            assertTrue(targetEventNotificationVerifier.checkTimeoutEvents());
         }
         // Not yet implemented:
         //assertTrue(targetEventNotificationVerifier.checkResetEvents());

         testTarget.removeTargetListener(targetEventNotificationVerifier);
         sm.checkState(true);
      }
   }

   /**
    * Check that events are returned and that the event trace is disabled by
    * calling getEventTrace(). Re-enables the event trace afterwards.
    * Depends on the test setup having enabled the event trace.
    */
   public void testTargetEventTrace()
   {
      if (testTarget.hasGetEventTraceSupport())
      {
         try
         {
            TargetEventVerifier targetEventTraceVerifier =
               new TargetEventVerifier(EventActionPoint.ACTION_TRACE);
            setEventActions(targetEventTraceVerifier.getEventActionPoints());
            testTarget.setEventTraceEnabled(progressMonitor, true);
            // Wait in order for some events to reach us. The longest running
            // period in the test is for psi_3. It has a cycle of at least 10s
            // so sleep 10s + 5s, to be sure.
            try
            {
               Thread.sleep(15000);
            } catch (InterruptedException ignore) {}

            // Get all events in the event trace. This method call
            // has the side effect of disabling the event trace.
            TargetEvent[] targetEvents =
               testTarget.getEventTrace(progressMonitor, 0, Integer.MAX_VALUE);

            // Since old events are overwritten, it is difficult to know exactly
            // which events will be returned but at least some events should be
            // there.
            assertTrue(targetEvents.length > 0);

            // The event trace should have been disabled by getEventTrace().
            assertTrue(!testTarget.isEventTraceEnabled(progressMonitor));

            testTarget.clearEventTrace(progressMonitor);
            targetEvents = testTarget.getEventTrace(progressMonitor, 0, Integer.MAX_VALUE);
            assertTrue(0 == targetEvents.length);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
   }

   /**
    * Checks that a certain set of events have been received.
    * Depends on the test setup having enabled the event trace.
    */
   public void testTargetEventTraceMultiple()
   {
      if (testTarget.hasGetEventTraceMultipleSupport())
      {
         TargetEventVerifier targetEventTraceVerifier =
            new TargetEventVerifier(EventActionPoint.ACTION_TRACE);
         EventTraceCollectThread eventTraceCollectThread =
            new EventTraceCollectThread(targetEventTraceVerifier);
         try
         {
            setEventActions(targetEventTraceVerifier.getEventActionPoints());
            testTarget.setEventTraceEnabled(progressMonitor, true);
         }
         catch (IOException e)
         {
            fail();
         }
         eventTraceCollectThread.start();
         // Wait in order for some events to reach us. The longest running period
         // in the test is for psi_3. It has a cycle of at least 10s so sleep
         // 10s + 5s, to be sure. Note: To make the test quick, the event trace
         // should be enabled for as little time as possible because it takes a
         // long time to read all events if there are many events.
         try
         {
            Thread.sleep(15000);
         } catch (InterruptedException ignore) {}

         try
         {
            // The thread is collecting events for us - now it can be stopped.
            eventTraceCollectThread.interrupt();
            eventTraceCollectThread.join();
         }
         catch (InterruptedException e)
         {
            fail();
         }

         try
         {
            // No more events are needed.
            testTarget.setEventTraceEnabled(progressMonitor, false);

            // No sync is needed now since the thread is dead.
            if (targetEventTraceVerifier.getLossEvent().length > 0)
            {
               System.out.println("The testTargetEventTraceMultiple may fail because there are lost events");
            }

            targetEventTraceVerifier.accessAllEventProperties();
            if (testTarget.hasSetSendEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkSendEvents());
            }
            if (testTarget.hasSetReceiveEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkReceiveEvents());
            }
            if (testTarget.hasSetFreeEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkFreeEvents());
            }
            if (testTarget.hasSetAllocEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkAllocEvents());
            }
            if (testTarget.hasSetCreateEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkCreateEvents());
            }
            if (testTarget.hasSetKillEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkKillEvents());
            }
            if (testTarget.hasSetErrorEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkErrorEvents());
            }
            if (testTarget.hasSetBindEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkBindEvents());
            }
            if (testTarget.hasSetUserEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkUserEvents());
            }
            if (testTarget.hasSetTimeoutEventActionPointSupport())
            {
               assertTrue(targetEventTraceVerifier.checkTimeoutEvents());
            }
            // Not yet implemented:
            //assertTrue(targetEventTraceVerifier.checkResetEvents());
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
   }

   /**
    * Configures the target to generate CPUReports (cpu usage reports) during a
    * short period and verifies that reports are generated and that properties
    * of involved objects are correct.
    */
   public void testCPUReports()
   {
      if (testTarget.hasSetCPUReportsEnabledSupport())
      {
         int tick = testTarget.getTickLength();
         int nticksPerSec = (int) Math.round(1000000.0 / tick);
         // The reporting interval will be ten reports per second.
         final int nReportsPerSecond = 20;
         final int nInterval = nticksPerSec / nReportsPerSecond;
         final short executionUnit = 0;

         try
         {
            CPUReportsEnabledInfo info;
            boolean interrupted = false;
            int nSleepSeconds;
            CPUReports reports;
            TargetReport[] targetReports;

            testTarget.setCPUReportsEnabled(progressMonitor,
                                            true,
                                            nInterval,
                                            31,
                                            0);
            info = testTarget.getCPUReportsEnabled(progressMonitor);
            assertTrue(info.getPriority() == 31);
            assertTrue(info.getInterval() == nInterval);
            // Should get back the default value of the target,
            // which should be positive since support is enabled.
            assertTrue(info.getMaxReports() > 0);
            assertTrue(info.getTarget() == testTarget);
            assertTrue(info.isEnabled());

            // We should not sleep longer than info.getMaxReports() /
            // nReportsPerSecond and 3 seconds will be enough time in any case.
            nSleepSeconds = Math.min(3, info.getMaxReports() / nReportsPerSecond);
            try
            {
               Thread.sleep(nSleepSeconds * 1000 + 10);
            }
            catch (InterruptedException e)
            {
               interrupted = true;
            }

            reports = testTarget.getCPUReports(progressMonitor, executionUnit, 0, 0);
            assertTrue(reports.getTarget() == testTarget);
            assertTrue(reports.isEnabled());
            assertTrue(reports.getExecutionUnit() == executionUnit);
            targetReports = reports.getReports();
            for (int i = 0; i < targetReports.length; i++)
            {
               CPUReport report = (CPUReport) targetReports[i];
               report.getSum();
               report.getInterval();
               report.getNTick();
               report.getTick();
            }

            if (!interrupted && (nSleepSeconds >= 1))
            {
               // Check that a reasonable number of reports have been received
               // (+- 30%).
               assertTrue(targetReports.length >= (nSleepSeconds * nReportsPerSecond * 0.7));
               assertTrue(targetReports.length <= (nSleepSeconds * nReportsPerSecond * 1.3));
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
         finally
         {
            try
            {
               testTarget.setCPUReportsEnabled(progressMonitor,
                                               false,
                                               nInterval,
                                               31,
                                               0);
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
    * Configures the target to generate CPUPriorityReport (cpu usage reports for
    * background, interrupt, and prioritized processes) during a short period
    * and verifies that reports are generated and that properties of involved
    * objects are correct.
    */
   public void testCPUPriorityReports()
   {
      if (testTarget.hasSetCPUPriorityReportsEnabledSupport())
      {
         int tick = testTarget.getTickLength();
         int nticksPerSec = (int) Math.round(1000000.0 / tick);
         // The reporting interval will be ten reports per second.
         final int nReportsPerSecond = 20;
         final int nInverval = nticksPerSec / nReportsPerSecond;
         final short executionUnit = 0;

         try
         {
            CPUPriorityReportsEnabledInfo info;
            boolean interrupted = false;
            int nSleepSeconds;
            CPUPriorityReports reports;
            TargetReport[] targetReports;

            testTarget.setCPUPriorityReportsEnabled(progressMonitor,
                                                    true,
                                                    nInverval,
                                                    0);
            info = testTarget.getCPUPriorityReportsEnabled(progressMonitor);
            assertTrue(info.getInterval() == nInverval);
            // Should get back the default value of the target, which should be
            // positive since support is enabled.
            assertTrue(info.getMaxReports() > 0);
            assertTrue(info.getTarget() == testTarget);
            assertTrue(info.isEnabled());

            // We should not sleep longer than info.getMaxReports() /
            // nReportsPerSecond and 3 seconds will be enough time in any case.
            nSleepSeconds = Math.min(3, info.getMaxReports() / nReportsPerSecond);
            try
            {
               Thread.sleep(nSleepSeconds * 1000 + 10);
            }
            catch (InterruptedException e)
            {
               interrupted = true;
            }

            reports = testTarget.getCPUPriorityReports(progressMonitor, executionUnit, 0, 0);
            assertTrue(reports.getTarget() == testTarget);
            assertTrue(reports.isEnabled());
            assertTrue(reports.getExecutionUnit() == executionUnit);
            targetReports = reports.getReports();
            for (int i = 0; i < targetReports.length; i++)
            {
               CPUPriorityReport report = (CPUPriorityReport) targetReports[i];
               report.getSumBackground();
               report.getSumInterrupt();
               report.getSumPrioritized();
               report.getInterval();
               report.getNTick();
               report.getTick();
            }

            if (!interrupted && (nSleepSeconds >= 1))
            {
               // Check that a reasonable number of reports have been received
               // (+- 30%)
               assertTrue(targetReports.length >= (nSleepSeconds * nReportsPerSecond * 0.7));
               assertTrue(targetReports.length <= (nSleepSeconds * nReportsPerSecond * 1.3));
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
         finally
         {
            try
            {
               testTarget.setCPUPriorityReportsEnabled(progressMonitor,
                                                       false,
                                                       nInverval,
                                                       0);
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
    * Tests adding, accessing, and removing CPUProcessReportPoints on the target.
    */
   public void testCPUProcessReportPoint()
   {
      if (testTarget.hasCPUProcessReportPointSupport())
      {
         try
         {
            CPUProcessReportPoint[] cpuProcessReportPoints;
            CPUProcessReportPoint cpuProcessReportPoint;

            // If CPU process reports has never been enabled, setting CPU
            // process report points will fail. Therefore, we enable/disable
            // CPU process reports before setting the CPU process report points.
            testTarget.setCPUProcessReportsEnabled(progressMonitor, true, 100, 0, 0);
            testTarget.setCPUProcessReportsEnabled(progressMonitor, false, 100, 0, 0);

            // Clear all process points by iteration.
            cpuProcessReportPoints =
               testTarget.getCPUProcessReportPoints(progressMonitor);
            for (int i = 0; i < cpuProcessReportPoints.length; i++)
            {
               testTarget.clearCPUProcessReportPoint(progressMonitor,
                                                     cpuProcessReportPoints[i]);
            }
            cpuProcessReportPoints =
               testTarget.getCPUProcessReportPoints(progressMonitor);
            assertTrue(cpuProcessReportPoints.length == 0);

            // Set a new process report point.
            cpuProcessReportPoint = new CPUProcessReportPoint("psi*");
            assertTrue(cpuProcessReportPoint.getNamePattern().equals("psi*"));
            cpuProcessReportPoint.toString();
            testTarget.setCPUProcessReportPoint(progressMonitor,
                                                cpuProcessReportPoint);
            cpuProcessReportPoints =
               testTarget.getCPUProcessReportPoints(progressMonitor);
            assertTrue(cpuProcessReportPoints.length == 1);
            assertTrue(cpuProcessReportPoints[0].getNamePattern().equals("psi*"));

            // Clear all by passing null as 2nd parameter.
            testTarget.clearCPUProcessReportPoint(progressMonitor, null);
            cpuProcessReportPoints =
               testTarget.getCPUProcessReportPoints(progressMonitor);
            assertTrue(cpuProcessReportPoints.length == 0);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
   }

   /**
    * Configures the target to generate CPUProcessReport (cpu usage reports for
    * individual processes) during a short period and verifies that reports are
    * generated and that properties of involved objects are correct.
    */
   public void testCPUProcessReports()
   {
      if (testTarget.hasSetCPUProcessReportsEnabledSupport())
      {
         int tick = testTarget.getTickLength();
         int nticksPerSec = (int) Math.round(1000000.0 / tick);
         // The reporting interval will be ten reports per second.
         final int nReportsPerSecond = 20;
         final int nInverval = nticksPerSec / nReportsPerSecond;
         final int maxProcsReport = 8;
         final short executionUnit = 0;

         try
         {
            CPUProcessReportsEnabledInfo info;
            boolean interrupted = false;
            int nSleepSeconds;
            CPUProcessReports reports;
            TargetReport[] targetReports;

            testTarget.setCPUProcessReportsEnabled(progressMonitor,
                                                   true,
                                                   nInverval,
                                                   0,
                                                   maxProcsReport);
            info = testTarget.getCPUProcessReportsEnabled(progressMonitor);
            assertTrue(info.getInterval() == nInverval);
            // Should get back the default value of the target, which should be
            // positive since support is enabled.
            assertTrue(info.getMaxReports() > 0);
            assertTrue(info.getTarget() == testTarget);
            assertTrue(info.isEnabled());
            assertTrue(info.getMaxProcessesPerReport() == maxProcsReport);

            // We should not sleep longer than info.getMaxReports() /
            // nReportsPerSecond and 3 seconds will be enough time in any case.
            nSleepSeconds = Math.min(3, info.getMaxReports() / nReportsPerSecond);
            try
            {
               Thread.sleep(nSleepSeconds * 1000 + 10);
            }
            catch (InterruptedException e)
            {
               interrupted = true;
            }

            reports = testTarget.getCPUProcessReports(progressMonitor, executionUnit, 0, 0);
            assertTrue(reports.getTarget() == testTarget);
            assertTrue(reports.isEnabled());
            assertTrue(reports.getExecutionUnit() == executionUnit);
            targetReports = reports.getReports();
            for (int i = 0; i < targetReports.length; i++)
            {
               CPUProcessReport report;
               CPUProcessReport.CPUProcessLoad[] cpuProcessLoads;

               report = (CPUProcessReport) targetReports[i];
               report.getSumOther();
               cpuProcessLoads = report.getSumProcesses();
               for (int j = 0; j < cpuProcessLoads.length; j++)
               {
                  CPUProcessReport.CPUProcessLoad cpuProcessLoad = cpuProcessLoads[j];
                  cpuProcessLoad.getId();
                  cpuProcessLoad.getSum();
               }
               report.getInterval();
               report.getNTick();
               report.getTick();
            }

            if (!interrupted && (nSleepSeconds >= 1))
            {
               // Check that a reasonable number of reports have been received
               // (+- 30%)
               assertTrue(targetReports.length >= (nSleepSeconds * nReportsPerSecond * 0.7));
               assertTrue(targetReports.length <= (nSleepSeconds * nReportsPerSecond * 1.3));
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
         finally
         {
            try
            {
               testTarget.setCPUProcessReportsEnabled(progressMonitor,
                                                      false,
                                                      nInverval,
                                                      0,
                                                      0);
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
    * Configures the target to generate CPUBlockReport (cpu usage reports for
    * individual blocks) during a short period and verifies that reports are
    * generated and that properties of involved objects are correct.
    */
   public void testCPUBlockReports()
   {
      if (testTarget.hasSetCPUBlockReportsEnabledSupport())
      {
         int tick = testTarget.getTickLength();
         int nticksPerSec = (int) Math.round(1000000.0 / tick);
         // The reporting interval will be ten reports per second.
         final int nReportsPerSecond = 20;
         final int nInverval = nticksPerSec / nReportsPerSecond;
         final int maxBlocksReport = 8;
         final short executionUnit = 0;

         try
         {
            CPUBlockReportsEnabledInfo info;
            boolean interrupted = false;
            int nSleepSeconds;
            CPUBlockReports reports;
            TargetReport[] targetReports;

            testTarget.setCPUBlockReportsEnabled(progressMonitor,
                                                 true,
                                                 nInverval,
                                                 0,
                                                 maxBlocksReport);
            info = testTarget.getCPUBlockReportsEnabled(progressMonitor);
            assertTrue(info.getInterval() == nInverval);
            // Should get back the default value of the target, which should be
            // positive since support is enabled.
            assertTrue(info.getMaxReports() > 0);
            assertTrue(info.getTarget() == testTarget);
            assertTrue(info.isEnabled());
            assertTrue(info.getMaxBlocksPerReport() == maxBlocksReport);

            // We should not sleep longer than info.getMaxReports() /
            // nReportsPerSecond and 3 seconds will be enough time in any case.
            nSleepSeconds = Math.min(3, info.getMaxReports() / nReportsPerSecond);
            try
            {
               Thread.sleep(nSleepSeconds * 1000 + 10);
            }
            catch (InterruptedException e)
            {
               interrupted = true;
            }

            reports = testTarget.getCPUBlockReports(progressMonitor, executionUnit, 0, 0);
            assertTrue(reports.getTarget() == testTarget);
            assertTrue(reports.isEnabled());
            assertTrue(reports.getExecutionUnit() == executionUnit);
            targetReports = reports.getReports();
            for (int i = 0; i < targetReports.length; i++)
            {
               CPUBlockReport report;
               CPUBlockReport.CPUBlockLoad[] cpuBlockLoads;

               report = (CPUBlockReport) targetReports[i];
               report.getSumOther();
               cpuBlockLoads = report.getSumBlocks();
               for (int j = 0; j < cpuBlockLoads.length; j++)
               {
                  CPUBlockReport.CPUBlockLoad cpuBlockLoad = cpuBlockLoads[j];
                  cpuBlockLoad.getId();
                  cpuBlockLoad.getSum();
               }
               report.getInterval();
               report.getNTick();
               report.getTick();
            }

            if (!interrupted && (nSleepSeconds >= 1))
            {
               // Check that a reasonable number of reports have been received
               // (+- 30%)
               assertTrue(targetReports.length >= (nSleepSeconds * nReportsPerSecond * 0.7));
               assertTrue(targetReports.length <= (nSleepSeconds * nReportsPerSecond * 1.3));
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
         finally
         {
            try
            {
               testTarget.setCPUBlockReportsEnabled(progressMonitor,
                                                    false,
                                                    nInverval,
                                                    0,
                                                    0);
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
    * Configures the target to generate UserReports during a short period and
    * verifies that reports are generated and that properties of involved
    * objects are correct.
    */
   public void testUserReports()
   {
      if (testTarget.hasSetUserReportsEnabledSupport())
      {
         int tick = testTarget.getTickLength();
         int nticksPerSec = (int) Math.round(1000000.0 / tick);
         // The reporting interval will be ten reports per second.
         final int nReportsPerSecond = 20;
         final int nInterval = nticksPerSec / nReportsPerSecond;
         // This corresponds to heap profiling and is the only defined user
         // report for now.
         final int nReportNumber = 51;
         final int maxValuesReport = 8;

         try
         {
            UserReportsEnabledInfo info;
            boolean interrupted = false;
            int nSleepSeconds;
            UserReports reports;
            TargetReport[] targetReports;

            testTarget.setUserReportsEnabled(progressMonitor,
                                             nReportNumber,
                                             true,
                                             nInterval,
                                             0,
                                             maxValuesReport);
            info = testTarget.getUserReportsEnabled(progressMonitor, nReportNumber);
            assertTrue(info.getReportNumber() == nReportNumber);
            assertTrue(info.getInterval() == nInterval);
            // Should get back the default value of the target, which should be
            // positive since support is enabled.
            assertTrue(info.getMaxReports() > 0);
            assertTrue(info.getMaxValuesPerReport() == maxValuesReport);
            assertTrue(info.getTarget() == testTarget);
            assertTrue(info.isEnabled());

            // We should not sleep longer than info.getMaxReports() /
            // nReportsPerSecond and 3 seconds will be enough time in any case.
            nSleepSeconds = Math.min(3, info.getMaxReports() / nReportsPerSecond);
            try
            {
               Thread.sleep(nSleepSeconds * 1000 + 10);
            }
            catch (InterruptedException e)
            {
               interrupted = true;
            }

            reports = testTarget.getUserReports(progressMonitor, nReportNumber, 0, 0);
            assertTrue(reports.getReportNumber() == nReportNumber);
            assertTrue(reports.getTarget() == testTarget);
            assertTrue(reports.isEnabled());
            reports.isContinuous();
            reports.isMaxMin();
            reports.isMultiple();
            targetReports = reports.getReports();
            for (int i = 0; i < targetReports.length; i++)
            {
               UserReport report;
               UserReport.UserReportValue[] reportValues;

               report = (UserReport) targetReports[i];
               report.getMaxOther();
               report.getMinOther();
               report.getSumOther();
               report.getInterval();
               report.getNTick();
               report.getTick();
               reportValues = report.getValues();
               for (int j = 0; j < reportValues.length; j++)
               {
                  UserReport.UserReportValue reportValue = reportValues[j];
                  reportValue.getId();
                  reportValue.getSum();
               }
            }

            if (!interrupted && (nSleepSeconds >= 1))
            {
               // Check that a reasonable number of reports have been received
               // (+- 50%)
               assertTrue(targetReports.length >= (nSleepSeconds * nReportsPerSecond * 0.5));
               assertTrue(targetReports.length <= (nSleepSeconds * nReportsPerSecond * 1.5));
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
         finally
         {
            try
            {
               testTarget.setUserReportsEnabled(progressMonitor,
                                                nReportNumber,
                                                false,
                                                nInterval,
                                                0,
                                                0);
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   /**
    * Writes a dump file to a temporary file (auto-deleted). Also tests off-line
    * creation of CPUReport objects before writing them into the dump file.
    */
   public void testSystemModelDumpFileWriter()
   {
      Process psi_4_;
      ProcessInfo psi_4_info = null;
      File tmpDumpFile = null;
      SystemModelDumpFileWriter writer = null;

      psi_4_ = findProcessOnTarget("psi_4", testTarget);
      try
      {
         ProcessFilter pf;
         ProcessInfo[] pia;

         pf = new ProcessFilter();
         pf.filterName(false, "psi_4");
         pia = testTarget.getFilteredProcesses(
               progressMonitor, Target.SCOPE_SYSTEM, 0, pf);
         if (pia.length == 1 && pia[0].getName().equals("psi_4"))
         {
            psi_4_info = pia[0];
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }

      // Create a temp file with name dump*.tmp in the default temp dir.
      try
      {
         short executionUnit = 0;
         int interval = 500;
         int maxReports = 20;
         int maxProcsReport = 8;
         int tick = 1000;
         int ntick = 340;
         int sumInterrupt = 670;
         int sumBackground = 1200;
         // Has to be 32, one for each prio level (this is NOT documentet but
         // otherwise an exception is thrown).
         int[] sumPrioritized = new int[32];
         int sumOther = 430;
         int sum = 125;
         CPUPriorityReport[] offlineCPUPriorityReports;
         CPUProcessReport.CPUProcessLoad[] sumProcesses;
         CPUProcessReport[] offlineCPUProcessReports;
         CPUReport[] offlineCPUReports;

         tmpDumpFile = File.createTempFile("dump", null);
         writer = new SystemModelDumpFileWriter(
            tmpDumpFile, testTarget.isBigEndian(), 17);
         writer.writeCPUPriorityReportInfo(
            testTarget.getName(), executionUnit, interval, maxReports);
         writer.writeCPUPriorityReportsEnabled(
            true, interval, maxReports, 0, 0, 0, false);
         writer.writeCPUProcessReportInfo(testTarget.getName(),
            executionUnit, interval, maxReports, maxProcsReport, "psi");
         writer.writeCPUProcessReportsEnabled(
            true, 500, maxReports, maxProcsReport, 0, 0, 0, false);
         writer.writeCPUReportInfo(
            testTarget.getName(), executionUnit, interval, maxReports, 31);
         writer.writeCPUReportsEnabled(
            true, interval, 25, maxReports, 0, 0, 0, false);

         offlineCPUPriorityReports = new CPUPriorityReport[1];
         offlineCPUPriorityReports[0] = CPUPriorityReport.getInstance(
            tick, ntick, interval, sumInterrupt, sumBackground, sumPrioritized);
         assertTrue(offlineCPUPriorityReports[0].getSumBackground() == sumBackground);
         assertTrue(offlineCPUPriorityReports[0].getSumInterrupt() == sumInterrupt);
         assertTrue(offlineCPUPriorityReports[0].getSumPrioritized().length == sumPrioritized.length);
         writer.writeCPUPriorityReports(offlineCPUPriorityReports, executionUnit, false);
         writer.writeCPUPriorityReports(offlineCPUPriorityReports, executionUnit, true);

         sumProcesses = new CPUProcessReport.CPUProcessLoad[1];
         sumProcesses[0] = CPUProcessReport.CPUProcessLoad.getInstance(1000, 8800);
         offlineCPUProcessReports = new CPUProcessReport[1];
         offlineCPUProcessReports[0] = CPUProcessReport.getInstance(
            tick, ntick, interval, sumOther, sumProcesses);
         assertTrue(offlineCPUProcessReports[0].getSumOther() == sumOther);
         assertTrue(offlineCPUProcessReports[0].getSumProcesses().length == sumProcesses.length);
         writer.writeCPUProcessReports(offlineCPUProcessReports, executionUnit, false);
         writer.writeCPUProcessReports(offlineCPUProcessReports, executionUnit, true);

         offlineCPUReports = new CPUReport[1];
         offlineCPUReports[0] = CPUReport.getInstance(
            tick, ntick, interval, sum);
         assertTrue(offlineCPUReports[0].getSum() == sum);
         writer.writeCPUReports(offlineCPUReports, executionUnit, false);
         writer.writeCPUReports(offlineCPUReports, executionUnit, true);

         // TODO: Verify that the call is done correctly (sequence and parameters).
         writer.startWritingEvents(
            "nameless", "System", "myActions", false, false);
         writer.writeEvent(SendEvent.getInstance(
               new Object(), 0, 0, 0, 0, 0, 0, 0,
               TargetEvent.ProcessInfo.getInstance(
                     new Object(), 0, 0, 0, 0, 0, 0, "ping"),
               TargetEvent.ProcessInfo.getInstance(
                     new Object(), 0, 0, 0, 0, 0, 0, "pong"),
               0, 0, 0, 0, (short) 0, null, null, null));
         // TODO: Verify that the call is done correctly (sequence and parameters).
         writer.endWritingEvents(0);

         // TODO: Verify that the call is done correctly (sequence and parameters).
         writer.startWritingProcesses(Target.SCOPE_SYSTEM, 0, false);
         writer.writeProcess(psi_4_.getId(), "psi_4");
         if (psi_4_info != null)
         {
            writer.writeProcess(psi_4_info);
         }
         // TODO: Verify that the call is done correctly (sequence and parameters).
         writer.endWritingProcesses(0);

         writer.writeSystemInfo(testTarget.getOsType(),
                                testTarget.getCpuType(),
                                testTarget.getName(),
                                testTarget.getNumExecutionUnits(),
                                testTarget.getTickLength(),
                                testTarget.getNTickFrequency(),
                                false);

         /*
          * These methods are not documented, include when its known how to
          * use them:
          * writer.writeUserReportInfo()
          * writer.writeUserReports()
          * writer.writeUserReportsEnabled()
          */
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }
      finally
      {
         if (writer != null)
         {
            writer.close();
         }
         if (tmpDumpFile != null)
         {
            tmpDumpFile.delete();
         }
      }
   }

   /**
    * Goes trough the dumps on the target (if any) and accesses all properties
    * of the DumpInfo objects. Also collects those dumps into memory buffers and
    * verifies that the sizes of the collected buffer are correct.
    */
   public void testTargetDumps()
   {
      if (testTarget.hasDumpSupport())
      {
         try
         {
            DumpInfo[] dumps =
               testTarget.getDumps(progressMonitor, 0, Integer.MAX_VALUE);
            for (int i = 0; i < dumps.length; i++)
            {
               DumpInfo dump;
               ByteArrayOutputStream out;

               dump = dumps[i];
               dump.getId();
               dump.getMicroSeconds();
               dump.getSeconds();
               dump.getSize();
               dump.getTarget();
               dump.toString();
               assertTrue(dump.getTarget() == testTarget);

               out = new ByteArrayOutputStream();
               testTarget.collectDump(progressMonitor, dump.getId(), out);
               assertTrue(dump.getSize() == out.size());
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
      }
   }

   /**
    * Tests retrieving raw memory from pool fragments.
    */
   public void testGetMemory()
   {
      if (testTarget.hasMemorySupport() && testTarget.hasPoolSupport())
      {
         // Pick a pool, a pool fragment, and a signal that we will later retrieve
         // memory from. We will use the pool's segment id as base address.
         Pool[] pools;
         Pool selectedPool = null;
         PoolFragmentInfo selectedPoolFragment = null;
         SignalInfo selectedSignal = null;

         pools = testTarget.getPools();
         for (int i = 0; i < pools.length && (selectedPoolFragment == null); i++)
         {
            Pool pool = pools[i];
            PoolFragmentInfo[] poolFragmentInfo = pool.getPoolFragments();

            for (int j = 0; j < poolFragmentInfo.length && (selectedPoolFragment == null); j++)
            {
               PoolFragmentInfo pfi = poolFragmentInfo[j];
               if (pfi.getSize() > 0)
               {
                  selectedPool = pool;
                  selectedPoolFragment = pfi;
                  break;
               }
            }

            if (selectedPool != null)
            {
               try
               {
                  SignalInfo[] poolSignals = selectedPool.getFilteredPoolSignals(
                        progressMonitor, null);
                  for (int j = 0; j < poolSignals.length; j++)
                  {
                     SignalInfo signal = poolSignals[j];
                     selectedSignal = signal;
                     break;
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
                  fail();
               }
            }
         }

         if ((selectedPool != null) &&
             (selectedPoolFragment != null) &&
             (selectedSignal != null))
         {
            try
            {
               testTarget.getMemory(progressMonitor,
                                    selectedPool.getSid(),
                                    selectedPoolFragment.getAddress(),
                                    selectedPoolFragment.getSize());
               testTarget.getMemory(progressMonitor,
                                    selectedPool.getSid(),
                                    selectedSignal.getAddress(),
                                    selectedSignal.getSize());
            }
            catch (IOException e)
            {
               e.printStackTrace();
               fail();
            }
         }

         sm.checkState(true);
      }
   }

   /**
    * Tests getting, changing, and restoring the event state.
    */
   public void testChangeEventState()
   {
      if (testTarget.hasEventStateSupport())
      {
         try
         {
            int state;
            int newState;
            int restoredState;

            state = testTarget.getEventState(progressMonitor);
            testTarget.setEventState(progressMonitor, state + 1);
            newState = testTarget.getEventState(progressMonitor);
            assertTrue(newState == (state + 1));
            testTarget.setEventState(progressMonitor, state);
            restoredState = testTarget.getEventState(progressMonitor);
            assertTrue(restoredState == state);
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }

         sm.checkState(true);
      }
   }

   /**
    * Tests clearing and getting event action points.
    */
   public void testClearEventActionPoints()
   {
      if (testTarget.hasClearEventActionPointSupport())
      {
         try
         {
            TargetEventVerifier targetEventNotificationVerifier =
               new TargetEventVerifier(EventActionPoint.ACTION_NOTIFY);
            setEventActions(targetEventNotificationVerifier.getEventActionPoints());

            // Otherwise we wont get the event action points unless they are persistent.
            assertTrue(testTarget.isAttached());

            if (testTarget.hasClearAllEventActionPointsSupport())
            {
               testTarget.clearEventActionPoint(progressMonitor, null);
            }
            else
            {
               EventActionPoint[] points =
                  testTarget.getEventActionPoints(progressMonitor);
               for (int i = 0; i < points.length; i++)
               {
                  testTarget.clearEventActionPoint(progressMonitor, points[i]);
               }
            }

            if (testTarget.hasGetEventActionPointsSupport())
            {
               EventActionPoint[] points =
                  testTarget.getEventActionPoints(progressMonitor);
               assertTrue(points.length == 0);
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }
         sm.checkState(true);
      }
   }

   /**
    * Performs a clean() on the System Model after having killed and removed
    * process "alfa_0" from the test target. Checks if the System Model gets the
    * expected "changed" and "removed" events.
    */
   public void testCleanSystemModelWithKilledProcess()
   {
      boolean changedResult = false;
      boolean removedResult = false;
      String processName = "alfa_0";

      List gateChangedCheckList = new ArrayList();
      List targetChangedCheckList = new ArrayList();
      List segmentChangedCheckList = new ArrayList();
      List poolChangedCheckList = new ArrayList();
      List heapChangedCheckList = new ArrayList();
      List blockChangedCheckList = new ArrayList();
      List processChangedCheckList = new ArrayList();

      List gateRemovedCheckList = new ArrayList();
      List targetRemovedCheckList = new ArrayList();
      List segmentRemovedCheckList = new ArrayList();
      List poolRemovedCheckList = new ArrayList();
      List heapRemovedCheckList = new ArrayList();
      List blockRemovedCheckList = new ArrayList();
      List processRemovedCheckList = new ArrayList();

      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals(processName))
         {
            processChangedCheckList.add(process);
            processRemovedCheckList.add(process);
         }
      }

      killNodeInSystemModel(allProcessesOnTarget(testTarget), processName);

      changedResult = checkEventsFired(gateChangedCheckList,
            targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
            heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
            changedEventsFired);
      sm.clean();
      removedResult = checkEventsFired(gateRemovedCheckList,
            targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
            heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
            removedEventsFired);
      sm.checkState(true);
      assertTrue(changedResult && removedResult);
   }

   /**
    * Performs a clean() on the gate after having killed and removed process
    * "alfa_1" from the test target. Checks if the System Model gets the
    * expected "changed" and "removed" events.
    */
   public void testCleanGateWithKilledProcess()
   {
      boolean changedResult = false;
      boolean removedResult = false;
      String processName = "alfa_1";

      List gateChangedCheckList = new ArrayList();
      List targetChangedCheckList = new ArrayList();
      List segmentChangedCheckList = new ArrayList();
      List poolChangedCheckList = new ArrayList();
      List heapChangedCheckList = new ArrayList();
      List blockChangedCheckList = new ArrayList();
      List processChangedCheckList = new ArrayList();

      List gateRemovedCheckList = new ArrayList();
      List targetRemovedCheckList = new ArrayList();
      List segmentRemovedCheckList = new ArrayList();
      List poolRemovedCheckList = new ArrayList();
      List heapRemovedCheckList = new ArrayList();
      List blockRemovedCheckList = new ArrayList();
      List processRemovedCheckList = new ArrayList();

      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals(processName))
         {
            processChangedCheckList.add(process);
            processRemovedCheckList.add(process);
         }
      }

      killNodeInSystemModel(allProcessesOnTarget(testTarget), processName);

      changedResult = checkEventsFired(gateChangedCheckList,
            targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
            heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
            changedEventsFired);

      List safetyGateList = Arrays.asList(sm.getGates());
      for (Iterator i = safetyGateList.iterator(); i.hasNext();)
      {
         Gate gate = (Gate) i.next();
         gate.clean();
      }
      removedResult = checkEventsFired(gateRemovedCheckList,
            targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
            heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
            removedEventsFired);
      sm.checkState(true);
      assertTrue(changedResult && removedResult);
   }

   /**
    * Performs a clean() on the test target after having killed and removed
    * process "alfa_2" from the test target. Checks if the System Model gets the
    * expected "changed" and "removed" events.
    */
   public void testCleanTargetWithKilledProcess()
   {
      boolean changedResult = false;
      boolean removedResult = false;
      String processName = "alfa_2";

      List gateChangedCheckList = new ArrayList();
      List targetChangedCheckList = new ArrayList();
      List segmentChangedCheckList = new ArrayList();
      List poolChangedCheckList = new ArrayList();
      List heapChangedCheckList = new ArrayList();
      List blockChangedCheckList = new ArrayList();
      List processChangedCheckList = new ArrayList();

      List gateRemovedCheckList = new ArrayList();
      List targetRemovedCheckList = new ArrayList();
      List segmentRemovedCheckList = new ArrayList();
      List poolRemovedCheckList = new ArrayList();
      List heapRemovedCheckList = new ArrayList();
      List blockRemovedCheckList = new ArrayList();
      List processRemovedCheckList = new ArrayList();

      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals(processName))
         {
            processChangedCheckList.add(process);
            processRemovedCheckList.add(process);
         }
      }

      killNodeInSystemModel(allProcessesOnTarget(testTarget), processName);

      changedResult = checkEventsFired(gateChangedCheckList,
            targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
            heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
            changedEventsFired);
      testTarget.clean();
      removedResult = checkEventsFired(gateRemovedCheckList,
            targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
            heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
            removedEventsFired);
      sm.checkState(true);
      assertTrue(changedResult && removedResult);
   }

   /**
    * Performs a clean() on the system model test segment on the test target,
    * after having killed and removed process "alfa_3" from the same target.
    * Checks if the System Model gets the expected "changed" and "removed"
    * events.
    */
   // FIXME: We need to add one more process in the alfa block on the target in
   // order to run this test, otherwise the kill process tests will kill all
   // processes in the alfa block, which will also kill the alfa block as a
   // result. This means that the alfa block will not be able to be killed in
   // the first kill block test.
   public void testCleanSegmentWithKilledProcess()
   {
      if (testTarget.hasExtendedSegmentSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String processName = "alfa_3";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.getName().equals(processName))
            {
               processChangedCheckList.add(process);
               processRemovedCheckList.add(process);
            }
         }

         killNodeInSystemModel(allProcessesOnTarget(testTarget), processName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);
         findTestSegment().clean();
         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Performs a clean() on block "alfa" on the test target, after having killed
    * and removed process "alfa_3" from the same target. Checks if the System
    * Model gets the expected "changed" and "removed" events.
    */
   public void testCleanBlockWithKilledProcess()
   {
      if (testTarget.hasBlockSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String processName = "alfa_4";
         String blockName = "alfa";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.getName().equals(processName))
            {
               processChangedCheckList.add(process);
               processRemovedCheckList.add(process);
            }
         }

         killNodeInSystemModel(allProcessesOnTarget(testTarget), processName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);

         List safetyBlockList = Arrays.asList(testTarget.getBlocks());
         for (Iterator i = safetyBlockList.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            if (block.getName().equals(blockName))
            {
               block.clean();
            }
         }
         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Performs a clean() on the process "alfa_4" on the test target after having
    * killed and removed process "alfa_4" from the same target. Checks if the
    * System Model gets the expected "changed" and "removed" events.
    */
   public void testCleanProcessWithKilledProcess()
   {
      boolean changedResult = false;
      boolean removedResult = false;
      String processName = "alfa_5";

      List gateChangedCheckList = new ArrayList();
      List targetChangedCheckList = new ArrayList();
      List segmentChangedCheckList = new ArrayList();
      List poolChangedCheckList = new ArrayList();
      List heapChangedCheckList = new ArrayList();
      List blockChangedCheckList = new ArrayList();
      List processChangedCheckList = new ArrayList();

      List gateRemovedCheckList = new ArrayList();
      List targetRemovedCheckList = new ArrayList();
      List segmentRemovedCheckList = new ArrayList();
      List poolRemovedCheckList = new ArrayList();
      List heapRemovedCheckList = new ArrayList();
      List blockRemovedCheckList = new ArrayList();
      List processRemovedCheckList = new ArrayList();

      for (Iterator i = allProcessesOnTarget(testTarget).iterator(); i.hasNext();)
      {
         Process process = (Process) i.next();
         if (process.getName().equals(processName))
         {
            processChangedCheckList.add(process);
            processRemovedCheckList.add(process);
         }
      }

      killNodeInSystemModel(allProcessesOnTarget(testTarget), processName);

      changedResult = checkEventsFired(gateChangedCheckList,
            targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
            heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
            changedEventsFired);

      List safetyProcessList = new ArrayList(allProcessesOnTarget(testTarget));
      for (Iterator i = safetyProcessList.iterator(); i.hasNext();)
      {
         Process p = (Process) i.next();
         if (p.getName().equals(processName))
         {
            p.clean();
         }
      }
      removedResult = checkEventsFired(gateRemovedCheckList,
            targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
            heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
            removedEventsFired);
      sm.checkState(true);
      assertTrue(changedResult && removedResult);
   }

   /**
    * Performs a clean() on the System Model after having killed and removed
    * block "alfa" from the test target. Checks if the System Model gets the
    * expected "changed" and "removed" events.
    */
   public void testCleanSystemModelWithKilledBlock()
   {
      if (testTarget.hasBlockSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String blockName = "alfa";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         Block[] blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block block = blocks[i];
            if (block.getName().equals(blockName))
            {
               blockChangedCheckList.add(block);
               blockRemovedCheckList.add(block);
               processChangedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
               processRemovedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
            }
         }

         killNodeInSystemModel(Arrays.asList(testTarget.getBlocks()), blockName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);
         sm.clean();
         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Performs a clean() on the gate after having killed and removed block
    * "beta" from the test target. Checks if the System Model gets the expected
    * "changed" and "removed" events.
    */
   public void testCleanGateWithKilledBlock()
   {
      if (testTarget.hasBlockSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String blockName = "beta";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         Block[] blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block block = blocks[i];
            if (block.getName().equals(blockName))
            {
               blockChangedCheckList.add(block);
               blockRemovedCheckList.add(block);
               processChangedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
               processRemovedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
            }
         }

         killNodeInSystemModel(Arrays.asList(testTarget.getBlocks()), blockName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);

         List safetyGateList = Arrays.asList(sm.getGates());
         for (Iterator i = safetyGateList.iterator(); i.hasNext();)
         {
            Gate gate = (Gate) i.next();
            gate.clean();
         }

         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Performs a clean() on the test target after having killed and removed
    * block "gamma" from the same target. Checks if the System Model gets the
    * expected "changed" and "removed" events.
    */
   public void testCleanTargetWithKilledBlock()
   {
      if (testTarget.hasBlockSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String blockName = "gamma";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         Block[] blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block block = blocks[i];
            if (block.getName().equals(blockName))
            {
               blockChangedCheckList.add(block);
               blockRemovedCheckList.add(block);
               processChangedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
               processRemovedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
            }
         }

         killNodeInSystemModel(Arrays.asList(testTarget.getBlocks()), blockName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);
         testTarget.clean();
         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Performs a clean() on the system model test segment on the test target
    * after having killed and removed block "gamma" from the same target.
    * Checks if the System Model gets the expected "changed" and "removed"
    * events.
    */
   // FIXME: We need to add one more block on the target in order to run this test.
   public void testCleanSegmentWithKilledBlock()
   {
      if (testTarget.hasExtendedSegmentSupport() && testTarget.hasBlockSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String blockName = "gamma";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         Block[] blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block block = blocks[i];
            if (block.getName().equals(blockName))
            {
               blockChangedCheckList.add(block);
               blockRemovedCheckList.add(block);
               processChangedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
               processRemovedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
            }
         }

         killNodeInSystemModel(Arrays.asList(testTarget.getBlocks()), blockName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);
         findTestSegment().clean();
         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Performs a clean() on block "delta" on the test target after having killed
    * and removed block "delta" from the same target. Checks if the System Model
    * gets the expected "changed" and "removed" events.
    */
   public void testCleanBlockWithKilledBlock()
   {
      if (testTarget.hasBlockSupport())
      {
         boolean changedResult = false;
         boolean removedResult = false;
         String blockName = "delta";

         List gateChangedCheckList = new ArrayList();
         List targetChangedCheckList = new ArrayList();
         List segmentChangedCheckList = new ArrayList();
         List poolChangedCheckList = new ArrayList();
         List heapChangedCheckList = new ArrayList();
         List blockChangedCheckList = new ArrayList();
         List processChangedCheckList = new ArrayList();

         List gateRemovedCheckList = new ArrayList();
         List targetRemovedCheckList = new ArrayList();
         List segmentRemovedCheckList = new ArrayList();
         List poolRemovedCheckList = new ArrayList();
         List heapRemovedCheckList = new ArrayList();
         List blockRemovedCheckList = new ArrayList();
         List processRemovedCheckList = new ArrayList();

         Block[] blocks = testTarget.getBlocks();
         for (int i = 0; i < blocks.length; i++)
         {
            Block block = blocks[i];
            if (block.getName().equals(blockName))
            {
               blockChangedCheckList.add(block);
               blockRemovedCheckList.add(block);
               processChangedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
               processRemovedCheckList = new ArrayList(Arrays.asList(block
                     .getChildren()));
            }
         }

         killNodeInSystemModel(Arrays.asList(testTarget.getBlocks()), blockName);

         changedResult = checkEventsFired(gateChangedCheckList,
               targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
               heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
               changedEventsFired);

         List safetyBlockList = Arrays.asList(testTarget.getBlocks());
         for (Iterator u = safetyBlockList.iterator(); u.hasNext();)
         {
            Block block = (Block) u.next();
            if (block.getName().equals(blockName))
            {
               block.clean();
            }
         }
         removedResult = checkEventsFired(gateRemovedCheckList,
               targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
               heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
               removedEventsFired);
         sm.checkState(true);
         assertTrue(changedResult && removedResult);
      }
   }

   /**
    * Detaches from the test target.
    * Before that, tests changing the attach scopes.
    */
   public void testChangeAttachProperties()
   {
      if (testTarget.hasSetScopeSupport())
      {
         try
         {
            //int numPointsAfterDetach;

            assertTrue(testTarget.isAttached());
            // Detach with resuming previous attach scope.
            testTarget.detach(progressMonitor, true);
            assertTrue(!testTarget.isAttached());

            //points = testTarget.getEventActionPoints(progressMonitor);
            // We attached without persistent points so they should be cleared now.
            //numPointsAfterDetach = points.length;
            // This test currently fails, and problem will be fixed for OSE 5.5.
            //assertTrue(points.length == 0);

            testTarget.attach(progressMonitor,
                              Target.SCOPE_SYSTEM,
                              0,
                              Target.SCOPE_SYSTEM,
                              0,
                              false);
            assertTrue(testTarget.isAttached());

            testTarget.setMainScope(progressMonitor, Target.SCOPE_SYSTEM, 0);
            testTarget.setInterceptScope(progressMonitor, Target.SCOPE_SYSTEM, 0);
            testTarget.intercept(progressMonitor, Target.SCOPE_SYSTEM, 0);
            testTarget.resume(progressMonitor, Target.SCOPE_SYSTEM, 0);
            // Detach without resuming previous attach scope.
            testTarget.detach(progressMonitor, false);
            assertTrue(!testTarget.isAttached());

            if (testTarget.hasGetEventActionPointsSupport())
            {
               // We attached without persistent points so they should be cleared now.
               EventActionPoint[] points =
                  testTarget.getEventActionPoints(progressMonitor);
               assertTrue(points.length == 0);
            }
         }
         catch (IOException e)
         {
            e.printStackTrace();
            fail();
         }

         sm.checkState(true);
      }
   }

   /**
    * Disconnects from the test target. Verifies that the correct "changed" and
    * "removed" events were received.
    */
   public void testDisconnectFromTarget()
   {
      boolean changedResult = false;
      boolean removedResult = false;

      List gateChangedCheckList = new ArrayList();
      List targetChangedCheckList = new ArrayList();
      List segmentChangedCheckList = new ArrayList(Arrays.asList(testTarget
            .getSegments()));
      List poolChangedCheckList = new ArrayList(Arrays.asList(testTarget
            .getPools()));
      List heapChangedCheckList = new ArrayList(Arrays.asList(testTarget
            .getHeaps()));
      List blockChangedCheckList = new ArrayList(Arrays.asList(testTarget
            .getBlocks()));
      List processChangedCheckList = new ArrayList(
            allProcessesOnTarget(testTarget));

      List gateRemovedCheckList = new ArrayList();
      List targetRemovedCheckList = new ArrayList();
      List segmentRemovedCheckList = new ArrayList(Arrays.asList(testTarget
            .getSegments()));
      List poolRemovedCheckList = new ArrayList(Arrays.asList(testTarget
            .getPools()));
      List heapRemovedCheckList = new ArrayList(Arrays.asList(testTarget
            .getHeaps()));
      List blockRemovedCheckList = new ArrayList(Arrays.asList(testTarget
            .getBlocks()));
      List processRemovedCheckList = new ArrayList(
            allProcessesOnTarget(testTarget));

      targetChangedCheckList.add(testTarget);
      targetRemovedCheckList.add(testTarget);

      testTarget.disconnect();

      changedResult = checkEventsFired(gateChangedCheckList,
            targetChangedCheckList, segmentChangedCheckList, poolChangedCheckList,
            heapChangedCheckList, blockChangedCheckList, processChangedCheckList,
            changedEventsFired);
      testTarget.clean();
      removedResult = checkEventsFired(gateRemovedCheckList,
            targetRemovedCheckList, segmentRemovedCheckList, poolRemovedCheckList,
            heapRemovedCheckList, blockRemovedCheckList, processRemovedCheckList,
            removedEventsFired);
      sm.checkState(true);
      assertTrue(changedResult && removedResult);
   }

   // ---------END TESTS---------------------------------------------

   /**
    * Stops a node in the System Model.
    *
    * @param list
    *           a list of nodes that contains the node you want to stop.
    * @param toBeKilled
    *           the name of the node that you want to stop.
    */
   private boolean stopNodeInSystemModel(List list, String toBeStopped)
   {
      boolean stoppedWorked = true;
      boolean found = false;

      if (list.get(0) instanceof Segment)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Segment segment = (Segment) i.next();
            if (segment.getName().equals(toBeStopped))
            {
               found = true;
               try
               {
                  segment.stop(progressMonitor);
                  Block[] blocks = segment.getBlocks();
                  for (int j = 0; j < blocks.length; j++)
                  {
                     Process[] processes = blocks[j].getProcesses();
                     for (int k = 0; k < processes.length; k++)
                     {
                        Process process = processes[k];
                        process.update(progressMonitor, true);
                        if ((process.getState() & Process.STATE_STOPPED) == 0)
                        {
                           stoppedWorked = false;
                        }
                     }
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         if (found == false)
         {
            System.out.println("### Segment " + toBeStopped + " does not exist");
         }
      }
      else if (list.get(0) instanceof Block)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            if (block.getName().equals(toBeStopped))
            {
               found = true;
               try
               {
                  block.stop(progressMonitor);
                  Process[] processes = block.getProcesses();
                  for (int j = 0; j < processes.length; j++)
                  {
                     Process process = processes[j];
                     process.update(progressMonitor, true);
                     if ((process.getState() & Process.STATE_STOPPED) == 0)
                     {
                        stoppedWorked = false;
                     }
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         if (found == false)
         {
            System.out.println("### Block " + toBeStopped + " does not exist");
         }
      }
      else if (list.get(0) instanceof Process)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.getName().equals(toBeStopped))
            {
               found = true;
               try
               {
                  process.stop(progressMonitor);
                  process.update(progressMonitor, true);
                  if ((process.getState() & Process.STATE_STOPPED) == 0)
                  {
                     stoppedWorked = false;
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         if (found == false)
         {
            System.out
                  .println("### Process " + toBeStopped + " does not exist");
         }
      }
      else
      {
         System.out.println("No stop support for node " + toBeStopped);
         stoppedWorked = false;
      }

      return stoppedWorked;
   }

   /**
    * Starts a node in the System Model.
    *
    * @param list
    *           a list of nodes that contains the node you want to start.
    * @param toBeKilled
    *           the name of the node that you want to start.
    */
   private boolean startNodeInSystemModel(List list, String toBeStarted)
   {
      boolean startWorked = true;
      boolean found = false;

      if (list.get(0) instanceof Segment)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Segment segment = (Segment) i.next();
            if (segment.getName().equals(toBeStarted))
            {
               found = true;
               try
               {
                  segment.start(progressMonitor);
                  Block[] blocks = segment.getBlocks();
                  for (int j = 0; j < blocks.length; j++)
                  {
                     Process[] processes = blocks[j].getProcesses();
                     for (int k = 0; k < processes.length; k++)
                     {
                        Process p = processes[k];
                        p.update(progressMonitor, true);
                        if ((p.getState() & Process.STATE_STOPPED) != 0
                              || (p.getState() & Process.STATE_INTERCEPTED) != 0)
                        {
                           startWorked = false;
                        }
                     }
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         if (found == false)
         {
            System.out.println("### Segment " + toBeStarted + " does not exist");
         }
      }
      else if (list.get(0) instanceof Block)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            if (block.getName().equals(toBeStarted))
            {
               found = true;
               try
               {
                  block.start(progressMonitor);
                  Process[] processes = block.getProcesses();
                  for (int j = 0; j < processes.length; j++)
                  {
                     Process p = processes[j];
                     p.update(progressMonitor, true);
                     if ((p.getState() & Process.STATE_STOPPED) != 0
                           || (p.getState() & Process.STATE_INTERCEPTED) != 0)
                     {
                        startWorked = false;
                     }
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         if (found == false)
         {
            System.out.println("### Block " + toBeStarted + " does not exist");
         }
      }
      else if (list.get(0) instanceof Process)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.getName().equals(toBeStarted))
            {
               found = true;
               try
               {
                  process.start(progressMonitor);
                  process.update(progressMonitor, true);
                  if ((process.getState() & Process.STATE_STOPPED) != 0
                        || (process.getState() & Process.STATE_INTERCEPTED) != 0)
                  {
                     startWorked = false;
                  }
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
         }
         if (found == false)
         {
            System.out
                  .println("### Process " + toBeStarted + " does not exist");
         }
      }
      else
      {
         System.out.println("No start support for node " + toBeStarted);
         startWorked = false;
      }

      return startWorked;
   }

   /**
    * Kills a node in the System Model
    *
    * @param list
    *           a list of nodes that contains the node you want to kill.
    * @param toBeKilled
    *           the name of the node that you want to kill.
    */
   private SystemModelNode killNodeInSystemModel(List list, String toBeKilled)
   {
      SystemModelNode returnValue = null;
      boolean found = false;

      if (list.get(0) instanceof Block)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Block block = (Block) i.next();
            if (block.getName().equals(toBeKilled))
            {
               found = true;
               try
               {
                  block.kill(progressMonitor);
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
            returnValue = block;
         }
         if (found == false)
         {
            System.out.println("### Block " + toBeKilled + " does not exist");
            returnValue = null;
         }
      }
      else if (list.get(0) instanceof Process)
      {
         for (Iterator i = list.iterator(); i.hasNext();)
         {
            Process process = (Process) i.next();
            if (process.getName().equals(toBeKilled))
            {
               found = true;
               try
               {
                  process.kill(progressMonitor);
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
            }
            returnValue = process;
         }
         if (found == false)
         {
            System.out.println("### Process " + toBeKilled + " does not exist");
            returnValue = null;
         }
      }
      else
      {
         System.out.println("No kill support for node " + toBeKilled);
         returnValue = null;
      }

      // Kludge: Wait for the resulting system model events to arrive.
      try
      {
         Thread.sleep(200);
      } catch (InterruptedException e) {}

      return returnValue; // the return value is not used anymore...
   }

   /**
    * Finds all processes on the target and returns them in a list.
    *
    * @param target
    *           the target for which you want to list the processes.
    */
   private List allProcessesOnTarget(Target target)
   {
      List processesOnTarget = new ArrayList();
      Process[] processes = target.getProcesses();
      for (int i = 0; i < processes.length; i++)
      {
         processesOnTarget.add(processes[i]);
      }
      return processesOnTarget;
   }

   private Process findProcessOnTarget(String processName, Target target)
   {
      Process[] processes = target.getProcesses();
      for (int i = 0; i < processes.length; i++)
      {
         Process process = processes[i];
         if (process.getName().equals(processName))
         {
            return process;
         }
      }
      throw new RuntimeException("Process not found");
   }

   /**
    * If segments are supported, return the segment of the system model tests.
    */
   private Segment findTestSegment()
   {
      Segment segment = null;

      Block[] blocks = testTarget.getBlocks();
      for (int i = 0; i < blocks.length; i++)
      {
         Block block = blocks[i];
         if (block.getName().equals("psi"))
         {
            segment = block.getSegment();
            break;
         }
      }

      return segment;
   }

   /**
    * Verifies that the events that you got when altering something in the
    * System Model are the ones you are supposed to get.
    *
    * @param gateCheckList
    *           a list of gates that you have changed, empty list if you have
    *           not changed any.
    * @param targetCheckList
    *           a list of targets that you have changed, empty list if you have
    *           not changed any.
    * @param segmentCheckList
    *           a list of segments that you have changed, empty list if you have
    *           not changed any.
    * @param poolCheckList
    *           a list of pools that you have changed, empty list if you have
    *           not changed any.
    * @param blockCheckList
    *           a list of blocks that you have changed, empty lis if you have
    *           not changed any.
    * @param processCheckList
    *           a list of processes that you have changed, empty list if you
    *           have not changed any.
    * @param eventsFired
    *           a list of the events that are fired after having changed
    *           something in the System Model.
    */
   private boolean checkEventsFired(List gateCheckList, List targetCheckList,
         List segmentCheckList, List poolCheckList, List heapCheckList,
         List blockCheckList, List processCheckList, List eventsFiredList)
   {
      boolean eventsCheckedOut = false;
      boolean eventsListHasItems = true;
      boolean eventsListIsEmpty = false;

      List gateEventsList = new ArrayList();
      List targetEventsList = new ArrayList();
      List segmentEventsList = new ArrayList();
      List poolEventsList = new ArrayList();
      List heapEventsList = new ArrayList();
      List blockEventsList = new ArrayList();
      List processEventsList = new ArrayList();

      if (eventsFiredList.isEmpty())
      {
         eventsListHasItems = false;
      }
      else
      {
         for (Iterator i = eventsFiredList.iterator(); i.hasNext();)
         {
            SystemModelEvent e = (SystemModelEvent) i.next();
            List children = e.getChildren();
            for (Iterator j = children.iterator(); j.hasNext();)
            {
               SystemModelNode n = (SystemModelNode) j.next();
               if (n instanceof Segment)
               {
                  if (n.isKilled())
                  {
                     for (Iterator k = segmentCheckList.iterator(); k.hasNext();)
                     {
                        Segment s = (Segment) k.next();
                        if (s.getId() == ((Segment) n).getId())
                        {
                           k.remove();
                           if (!(segmentEventsList.contains(e)))
                           {
                              segmentEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else if (n instanceof Pool)
               {
                  if (n.isKilled())
                  {
                     for (Iterator k = poolCheckList.iterator(); k.hasNext();)
                     {
                        Pool p = (Pool) k.next();
                        if (p.getId() == ((Pool) n).getId())
                        {
                           k.remove();
                           if (!(poolEventsList.contains(e)))
                           {
                              poolEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else if (n instanceof Heap)
               {
                  if (n.isKilled())
                  {
                     for (Iterator k = heapCheckList.iterator(); k.hasNext();)
                     {
                        Heap p = (Heap) k.next();
                        if (p.getId() == ((Heap) n).getId())
                        {
                           k.remove();
                           if (!(heapEventsList.contains(e)))
                           {
                              heapEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else if (n instanceof Block)
               {
                  if (n.isKilled())
                  {
                     for (Iterator l = blockCheckList.iterator(); l.hasNext();)
                     {
                        Block b = (Block) l.next();
                        if (b.getId() == ((Block) n).getId())
                        {
                           l.remove();
                           if (!(blockEventsList.contains(e)))
                           {
                              blockEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else if (n instanceof Process)
               {
                  if (n.isKilled())
                  {
                     for (Iterator m = processCheckList.iterator(); m.hasNext();)
                     {
                        Process p = (Process) m.next();
                        if (p.getId() == ((Process) n).getId())
                        {
                           m.remove();
                           if (!(processEventsList.contains(e)))
                           {
                              processEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else if (n instanceof Target)
               {
                  if (n.isKilled())
                  {
                     for (Iterator q = targetCheckList.iterator(); q.hasNext();)
                     {
                        Target t = (Target) q.next();
                        if (t.getName().equals(((Target) n).getName()))
                        {
                           q.remove();
                           if (!(targetEventsList.contains(e)))
                           {
                              targetEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else if (n instanceof Gate)
               {
                  if (n.isKilled())
                  {
                     for (Iterator r = gateCheckList.iterator(); r.hasNext();)
                     {
                        Gate g = (Gate) r.next();
                        if (g.getName().equals(((Gate) n).getName()))
                        {
                           r.remove();
                           if (!(gateEventsList.contains(e)))
                           {
                              gateEventsList.add(e);
                           }
                        }
                     }
                  }
               }
               else
               {
                  System.out.println("Unknown Remove Event");
                  fail();
               }
            }
         }

         if ((gateCheckList.size() == 0) && (targetCheckList.size() == 0)
               && (segmentCheckList.size() == 0) && (poolCheckList.size() == 0)
               && (blockCheckList.size() == 0) && (processCheckList.size() == 0))
         {
            // If we got the events we expected we're fine. Any additional
            // events are not necessarily an error and are simply ignored
            // for now.
            eventsListIsEmpty = true;
            if (!eventsFiredList.isEmpty())
            {
               eventsFiredList.clear();
            }
         }
      }
      eventsCheckedOut = eventsListHasItems && eventsListIsEmpty;

      return eventsCheckedOut;
   }

   private boolean blockFilterTester(int scope, int ID)
   {
      BlockFilter bf = new BlockFilter();
      BlockInfo[] bia;
      boolean success = true;

      bf.filterName(false, "alfa");
      try
      {
         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (!(bia[i].getName().equals("alfa")))
            {
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getName()1 false");
            }
            if (i > 0)
            {
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getName()2 false");
            }
         }

         // --------- BlockFilter-test testing untested methods
         if (bia.length != 0)
         {
            bia[0].getClass();
            bia[0].getErrorHandler();
            bia[0].getExecutionUnit();
            bia[0].getId();
            bia[0].getSid();
            bia[0].isSupervisor();
            bia[0].getSignalPoolId();
            bia[0].getStackPoolId();
            bia[0].getNumSignalsAttached();
            bia[0].getTarget();
            bia[0].toString();
         }
         bf.toString();
         // --------- End of BlockFilter-test

         bf = new BlockFilter();
         bf.filterName(true, "alfa");

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getName().equals("alfa"))
            {
               success = false;
               System.out.println(bia[i].getName() + " bf-test getName() true");
            }
         }

         bf = new BlockFilter();
         bf.filterUid(false, 0, 0);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (!(bia[i].getUid() == 0))
            {
               success = false;
               System.out.println(bia[i].getName() + " bf-test getUid() false");
            }
         }

         bf = new BlockFilter();
         bf.filterUid(true, 0, 0);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if ((bia[i].getUid() < 0) || (bia[i].getUid() > 0))
            {
               success = false;
               System.out.println(bia[i].getName() + " bf-test getUid() true");
            }
         }

         bf = new BlockFilter();
         bf.filterMaxSigSize(false, 65566, 65568);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getMaxSignalSize() < 65566 ||
                bia[i].getMaxSignalSize() > 65568)
            {
               // If a block size other than [65566,65568] is found, it's an error.
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getMaxSignalSize() false");
            }
         }

         bf = new BlockFilter();
         bf.filterMaxSigSize(true, 65566, 65568);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getMaxSignalSize() > 65566 &&
                bia[i].getMaxSignalSize() < 65568)
            {
               // If a block size in [65566,65568] is found, it's an error.
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getMaxSignalSize() true");
            }
         }

         bf = new BlockFilter();
         bf.filterSigPoolId(false, 0);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getSignalPoolId() != 0)
            {
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getSignalPoolId() false");
            }
         }

         bf = new BlockFilter();
         bf.filterSigPoolId(true, 0);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getSignalPoolId() == 0)
            {
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getSignalPoolId() true");
            }
         }

         bf = new BlockFilter();
         bf.filterStackPoolId(false, 65536);
         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getSignalPoolId() != 65536)
            {
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getSignalPoolId() false");
            }
         }

         bf = new BlockFilter();
         bf.filterStackPoolId(true, 65536);

         bia = testTarget.getFilteredBlocks(progressMonitor, scope, ID, bf);
         for (int i = 0; i < bia.length; i++)
         {
            if (bia[i].getSignalPoolId() == 65536)
            {
               success = false;
               System.out.println(bia[i].getName()
                     + " bf-test getSignalPoolId() true");
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      return success;
   }

   private boolean findSingleProcessNamed(String name, ProcessFilter pf)
      throws IOException
   {
      ProcessInfo[] pia = testTarget.getFilteredProcesses(progressMonitor,
                                                          Target.SCOPE_SYSTEM,
                                                          0,
                                                          pf);
      return ((pia.length == 1) && pia[0].getName().equals(name));
   }

   private boolean processFilterTester(int scope, int ID)
   {
      ProcessFilter pf = new ProcessFilter();
      ProcessInfo[] pia;
      boolean success = true;

      pf.filterName(false, "alfa_0");
      try
      {
         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getName().equals("alfa_0")))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getName()1 false");
            }
            if (i > 0)
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getName()2 false");
            }
         }

         // --------- ProcessFilter-test testing untested methods
         if (pia.length != 0)
         {
            pia[0].getClass();
            pia[0].getErrorHandler();
            pia[0].getId();
            pia[0].getSid();
            pia[0].getBid();
            pia[0].getCreator();
            pia[0].isSupervisor();
            pia[0].getSupervisorStackSize();
            pia[0].getNumSignalsAttached();
            pia[0].getTarget();
            pia[0].getTimeSlice();
            pia[0].getVector();
            pia[0].toString();
            pia[0].getExecutionUnit();
         }
         pf.toString();
         // --------- End of ProcessFilter-test

         pf = new ProcessFilter();
         pf.filterName(true, "alfa_0");

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getName().equals("alfa_0"))
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getName() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterType(false, Process.TYPE_PRIORITIZED);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getType() == Process.TYPE_PRIORITIZED))
            {
               success = false;
               System.out
                     .println(pia[i].getName() + " pf-test getType() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterType(true, Process.TYPE_PRIORITIZED);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getType() == Process.TYPE_PRIORITIZED)
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getType() true");
            }
         }

         int entryPointMin = 0;
         int entryPointMax = 0;
         switch (testTarget.getOsType())
         {
         case Target.OS_OSE_5:
            entryPointMin = 0x536E49;
            entryPointMax = 0x536E51;
            break;
         case Target.OS_OSE_CK:
            entryPointMin = 0x41EE20;
            entryPointMax = 0x421C70;
            break;
         default:
            break;
         }

         pf = new ProcessFilter();
         pf.filterEntrypoint(false, entryPointMin, entryPointMax);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getEntrypoint() < entryPointMin
                  || pia[i].getEntrypoint() > entryPointMax)
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getEntrypoint() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterEntrypoint(true, entryPointMin, entryPointMax);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getEntrypoint() <= entryPointMin || pia[i].getEntrypoint() >= entryPointMax))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getEntrypoint() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterUid(false, 0, 0);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getUid() == 0))
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getUid() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterUid(true, 0, 0);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if ((pia[i].getUid() < 0) || (pia[i].getUid() > 0))
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getUid() true");
            }
         }

         int minStackSize = 0;
         int maxStackSize = 0;
         switch (testTarget.getOsType())
         {
         case Target.OS_OSE_5:
            minStackSize = 1519;
            maxStackSize = 1521;
            break;
         case Target.OS_OSE_CK:
            minStackSize = 1023;
            maxStackSize = 1025;
            break;
         default:
            break;
         }

         pf = new ProcessFilter();
         pf.filterStackSize(false, minStackSize, maxStackSize);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getStackSize() > minStackSize) && (pia[i].getStackSize() < maxStackSize)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getStackSize() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterStackSize(true, minStackSize, maxStackSize);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getStackSize() <= minStackSize || pia[i].getStackSize() >= maxStackSize))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getStackSize() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterState(false, Process.STATE_READY);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getState() != Process.STATE_READY)
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getState() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterState(true, Process.STATE_READY);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getState() == Process.STATE_READY)
            {
               success = false;
               System.out
                     .println(pia[i].getName() + " pf-test getState() true");
            }
         }

         String fileName = "";
         int fileLineNumber = 0;
         switch (testTarget.getOsType())
         {
         case Target.OS_OSE_5:
            fileName = "logd.c";
            fileLineNumber = 50;
            break;
         case Target.OS_OSE_CK:
            fileName = "/vobs/odo/dbg/testsystem/optima_demo/src/sysmodtest_start.c";
            fileLineNumber = 80;
            break;
         default:
            break;
         }

         pf = new ProcessFilter();
         pf.filterFileName(false, fileName);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getFileName().equals(fileName)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getFileName() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterFileName(true, fileName);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getFileName().equals(fileName))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getFileName() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterLineNumber(false, 0, fileLineNumber);
         pf.filterFileName(false, fileName);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getLineNumber() > 0 && pia[i].getLineNumber() < fileLineNumber) && (pia[i]
                  .getFileName().equals(fileName))))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getLineNumber() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterLineNumber(true, 0, fileLineNumber);
         pf.filterFileName(false, fileName);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getLineNumber() <= 0 || pia[i].getLineNumber() >= fileLineNumber) && (pia[i]
                  .getFileName().equals(fileName))))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getLineNumber() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterPriority(false, 0, 15);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!(pia[i].getPriority() >= 0 && pia[i].getPriority() < 16))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getPriority() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterPriority(true, 0, 15);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getPriority() < 0) || (pia[i].getPriority() > 14)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getPriority() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterSemaphoreValue(false, 0, 1);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getSemaphoreValue() >= 0) && (pia[i]
                  .getSemaphoreValue() <= 1)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getSemaphoreValue() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterSemaphoreValue(true, 0, 1);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getSemaphoreValue() < 0) || (pia[i]
                  .getSemaphoreValue() > 1)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getSemaphoreValue() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterSignalsInQueue(false, 0, 3);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getNumSignalsInQueue() >= 0) && (pia[i]
                  .getNumSignalsInQueue() <= 3)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getNumSignalsInQueue() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterSignalsInQueue(true, 0, 3);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getNumSignalsInQueue() < 0) || (pia[i]
                  .getNumSignalsInQueue() > 3)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getNumSignalsInQueue() true");
            }
         }

         pf = new ProcessFilter();
         pf.filterSignalsOwned(false, 0, 3);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getNumSignalsOwned() >= 0) && (pia[i]
                  .getNumSignalsOwned() <= 3)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getNumSignalsOwned() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterSignalsOwned(true, 0, 3);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (!((pia[i].getNumSignalsOwned() < 0) || (pia[i]
                  .getNumSignalsOwned() > 2)))
            {
               success = false;
               System.out.println(pia[i].getName()
                     + " pf-test getNumSignalsOwned() true");
            }
         }

         // only testing the explicitly wanted signals
         pf = new ProcessFilter();
         pf.filterSigselectCount(false, 1, 3);

         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getSigselect().length != 0)
            {
               if (!((pia[i].getSigselect()[0] >= 1) && (pia[i].getSigselect()[0] <= 3)))
               {
                  success = false;
                  System.out.println(pia[i].getName()
                        + " pf-test getSigselect() false");
               }
            }
         }

         Process ose_sysd_ = findProcessOnTarget("ose_sysd", testTarget);
         if (ose_sysd_.getCreator() != 0)
         {
            // The creator id of processes may not be available and in that
            // case the test is not meaningful.
            pf = new ProcessFilter();
            pf.filterCreatorId(false, ose_sysd_.getId());
            pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
            for (int i = 0; i < pia.length; i++)
            {
               if (pia[i].getCreator() != ose_sysd_.getId())
               {
                  success = false;
                  System.out.println(pia[i].getName() + " pf-test getCreator() false");
               }
            }
         }

         Process psi_4_ = findProcessOnTarget("psi_4", testTarget);
         pf = new ProcessFilter();
         pf.filterErrorHandler(false, psi_4_.getErrorHandler());
         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getErrorHandler() != psi_4_.getErrorHandler())
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getErrorHandler() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterExecutionUnit(false, 0, 1); // range [0..0]
         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getExecutionUnit() != 0)
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getExecutionUnit() false");
            }
         }

         pf = new ProcessFilter();
         pf.filterSignalsAttached(false, 0, 1); // range [0..0]
         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if (pia[i].getNumSignalsAttached() != 0)
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getNumSignalsAttached() false");
            }
         }

         // TODO: pf.filterSupervisor not tested because no corresponding
         // property was found! Can Process.isSupervisor() be used
         // (boolen property but filter takes an int range)?

         pf = new ProcessFilter();
         pf.filterSupervisorStackSize(false, 0, 2048);
         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         for (int i = 0; i < pia.length; i++)
         {
            if ((pia[i].getSupervisorStackSize() < 0) ||
                (pia[i].getSupervisorStackSize() >= 2048))
            {
               success = false;
               System.out.println(pia[i].getName() + " pf-test getSupervisorStackSize() false");
            }
         }

         // TODO: pf.filterSystem not tested because no corresponding property was found!
         pf = new ProcessFilter();
         pf.filterSystem(false);
         pia = testTarget.getFilteredProcesses(progressMonitor, scope, ID, pf);
         // To find if it is a system process we have to find
         // Process.getProperties (not available in ProcessInfo).
         for (Iterator iter = allProcessesOnTarget(testTarget).iterator(); iter.hasNext();)
         {
            Process process = (Process) iter.next();
            for (int i = 0; i < pia.length; i++)
            {
               if (process.getId() == pia[i].getId())
               {
                  // This is one of the processes found by the filter,
                  // it should be a system process.
                  if ((process.getProperties() & 0x1) != 0x1)
                  {
                     success = false;
                     System.out.println(pia[i].getName() + " pf-test getProperties() false");
                  }
                  break;
               }
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
         fail();
      }

      return success;
   }

   private SignalInfo[] signalFilterTester(SignalFilter sf, int poolID)
      throws IOException
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         if (pool.getId() == poolID)
         {
            SignalInfo[] signals = pool.getFilteredPoolSignals(progressMonitor, sf);
            return signals;
         }
      }
      throw new RuntimeException("Requested pool not found");
   }

   private StackInfo[] stackFilterTester(StackFilter sf, int poolID)
      throws IOException
   {
      Pool[] pools = testTarget.getPools();
      for (int i = 0; i < pools.length; i++)
      {
         Pool pool = pools[i];
         if (pool.getId() == poolID)
         {
            StackInfo[] stacks = pool.getFilteredPoolStacks(progressMonitor, sf);
            return stacks;
         }
      }
      throw new RuntimeException("Requested pool not found");
   }

   private void setEventActions(List eventActionPoints) throws IOException
   {
      // Clear any old event action points.
      if (testTarget.hasClearAllEventActionPointsSupport())
      {
         testTarget.clearEventActionPoint(progressMonitor, null);
      }
      else
      {
         EventActionPoint[] oldEventActionPoints =
            testTarget.getEventActionPoints(progressMonitor);
         for (int i = 0; i < oldEventActionPoints.length; i++)
         {
            testTarget.clearEventActionPoint(progressMonitor,
                                             oldEventActionPoints[i]);
         }
      }

      for (Iterator i = eventActionPoints.iterator(); i.hasNext();)
      {
         EventActionPoint eventActionPoint = (EventActionPoint) i.next();
         testTarget.setEventActionPoint(progressMonitor, eventActionPoint);
      }
   }

   /**
    * Listens to the events that the System Model sends out and keeps count of
    * them. Accesses properties of incoming events.
    */
   private class SystemModelHandler implements SystemModelListener
   {
      public void nodesChanged(SystemModelEvent event)
      {
         changedEventsFired.add(event);
         event.getParent();
         event.toString();
         event.getChildren();
      }

      public void nodesAdded(SystemModelEvent event)
      {
         addedEventsFired.add(event);
         event.getParent();
         event.toString();
         event.getChildren();
      }

      public void nodesRemoved(SystemModelEvent event)
      {
         removedEventsFired.add(event);
         event.getParent();
         event.toString();
         event.getChildren();
      }
   }

   private class TestThread extends Thread
   {
      private final Random random = new Random(12345);
      private boolean causedError;

      public void run()
      {
         try
         {
            for (int i = 0; i < 4; i++)
            {
               try
               {
                  Thread.sleep(500 + random.nextInt(500));
               } catch (InterruptedException e) {}
               sm.clean();
               sm.update(new NullProgressMonitor(), true);
               testTarget.getBlocks();
               testTarget.getGate();
               testTarget.getHeaps();
               testTarget.getPools();
               testTarget.getProcesses();
               testTarget.getSegments();
               testTarget.getSysParams();
               sm.getGates();
               testGate.getChildren();
               testGate.getTargets();
               sm.checkState(true);
            }
         }
         catch (Throwable t)
         {
            causedError = true;
         }
      }

      public boolean getCausedError()
      {
         return causedError;
      }
   }

   /**
    * This class is a utility class for testing the event system together with
    * the sysmodtest refsys module. It checks events for processes in the "psi"
    * block.
    *
    * Usage:
    * Pass it to a target in setEventListener(). Then call getEvenActionsPoints()
    * and pass them to the target. Event notifications will then be received and
    * stored in lists in this object. Finally, check that the expected events
    * occurred by calling the checkXxxEvents() methods.
    *
    * Example:
    *
    * // First setup a Target (testTarget) and connect to it...
    *
    * testTarget.attach(progressMonitor, Target.SCOPE_SYSTEM, 0, Target.SCOPE_SYSTEM, 0, true);
    * TargetEventVerifier targetEventVerifier = new TargetEventVerifier();
    * testTarget.addTargetListener(targetEventVerifier);
    *
    * List eventActionPoints = targetEventVerifier.getEventActionPoints();
    * for (Iterator i = eventActionPoints.iterator(); i.hasNext();)
    * {
    *    EventActionPoint eventActionPoint = (EventActionPoint)i.next();
    *    testTarget.setEventActionPoint(progressMonitor, eventActionPoint);
    * }
    * testTarget.setEventNotifyEnabled(progressMonitor,  true);
    *
    * // Allow at least 15 seconds to elapse...
    *
    * boolean testOK = targetEventVerifier.checkSendEvents();
    */
   private class TargetEventVerifier implements TargetListener
   {
      private List signalSent = new ArrayList();

      private List signalReceived = new ArrayList();

      private List processSwapped = new ArrayList();

      private List processCreated = new ArrayList();

      private List processKilled = new ArrayList();

      private List errorCalled = new ArrayList();

      private List targetReset = new ArrayList();

      private List userReported = new ArrayList();

      private List processBound = new ArrayList();

      private List signalAllocated = new ArrayList();

      private List signalFreed = new ArrayList();

      private List timedout = new ArrayList();

      private List eventLost = new ArrayList();

      private final int action;

      TargetEventVerifier(int action)
      {
         switch (action)
         {
            case EventActionPoint.ACTION_NOTIFY:
            case EventActionPoint.ACTION_TRACE:
               this.action = action;
               break;
            default:
               throw new IllegalArgumentException(
                  "TargetEventVerifier: unsupported action passed to constructor");
         }
      }

      public SendEvent[] getSendEvent()
      {
         return (SendEvent[]) signalSent.toArray(new SendEvent[0]);
      }

      public ReceiveEvent[] getReceiveEvent()
      {
         return (ReceiveEvent[]) signalReceived.toArray(new ReceiveEvent[0]);
      }

      public SwapEvent[] getSwapEvent()
      {
         return (SwapEvent[]) processSwapped.toArray(new SwapEvent[0]);
      }

      public CreateEvent[] getCreateEvent()
      {
         return (CreateEvent[]) processCreated.toArray(new CreateEvent[0]);
      }

      public KillEvent[] getKillEvent()
      {
         return (KillEvent[]) processKilled.toArray(new KillEvent[0]);
      }

      public ErrorEvent[] getErrorEvent()
      {
         return (ErrorEvent[]) errorCalled.toArray(new ErrorEvent[0]);
      }

      public ResetEvent[] getResetEvent()
      {
         return (ResetEvent[]) targetReset.toArray(new ResetEvent[0]);
      }

      public UserEvent[] getUserEvent()
      {
         return (UserEvent[]) userReported.toArray(new UserEvent[0]);
      }

      public BindEvent[] getBindEvent()
      {
         return (BindEvent[]) processBound.toArray(new BindEvent[0]);
      }

      public AllocEvent[] getAllocEvent()
      {
         return (AllocEvent[]) signalAllocated.toArray(new AllocEvent[0]);
      }

      public FreeEvent[] getFreeEvent()
      {
         return (FreeEvent[]) signalFreed.toArray(new FreeEvent[0]);
      }

      public TimeoutEvent[] getTimeoutEvent()
      {
         return (TimeoutEvent[]) timedout.toArray(new TimeoutEvent[0]);
      }

      public LossEvent[] getLossEvent()
      {
         return (LossEvent[]) eventLost.toArray(new LossEvent[0]);
      }

      // Intented for adding the events from the target's event trace.
      public void addEvents(TargetEvent[] events)
      {
         for (int i = 0; i < events.length; i++)
         {
            // Check runtime class and add it to the correct array.
            TargetEvent event = events[i];
            if (event instanceof AllocEvent)
            {
               signalAllocated.add(event);
            }
            else if (event instanceof BindEvent)
            {
               processBound.add(event);
            }
            else if (event instanceof CreateEvent)
            {
               processCreated.add(event);
            }
            else if (event instanceof ErrorEvent)
            {
               errorCalled.add(event);
            }
            else if (event instanceof FreeEvent)
            {
               signalFreed.add(event);
            }
            else if (event instanceof KillEvent)
            {
               processKilled.add(event);
            }
            else if (event instanceof LossEvent)
            {
               eventLost.add(event);
            }
            else if (event instanceof ReceiveEvent)
            {
               signalReceived.add(event);
            }
            else if (event instanceof ResetEvent)
            {
               targetReset.add(event);
            }
            else if (event instanceof SendEvent)
            {
               signalSent.add(event);
            }
            else if (event instanceof SwapEvent)
            {
               processSwapped.add(event);
            }
            else if (event instanceof TimeoutEvent)
            {
               timedout.add(event);
            }
            else if (event instanceof UserEvent)
            {
               userReported.add(event);
            }
         }
      }

      // Access all properties.
      public void accessAllEventProperties()
      {
         List[] allLists =
         {
            signalAllocated, processBound, processCreated, errorCalled,
            signalFreed, processKilled, eventLost, signalReceived,
            targetReset, signalSent, processSwapped, timedout, userReported
         };

         for (int i = 0; i < allLists.length; i++)
         {
            List list = allLists[i];
            for (Iterator iter = list.iterator(); iter.hasNext();)
            {
               TargetEvent event = (TargetEvent) iter.next();
               event.getFileName();
               event.getLineNumber();
               event.getNTick();
               event.getReference();
               event.getSeconds();
               event.getSecondsTick();
               event.getTick();
               event.toString();
            }
         }
      }

      public boolean checkSendEvents()
      {
         SendEvent[] sendEvents = getSendEvent();

         // Process psi_1 allocates and sends signals (sigNo 1000) to psi_2.
         for (int i = 0; i < sendEvents.length; i++)
         {
            if ((sendEvents[i].getSignalNumber() == 1000) &&
                sendEvents[i].getSenderProcess().getName().endsWith("psi_1") &&
                sendEvents[i].getAddresseeProcess().getName().endsWith("psi_2"))
            {
               return true;
            }
         }
         return false;
      }

      public boolean checkReceiveEvents()
      {
         ReceiveEvent[] receiveEvents = getReceiveEvent();

         // Process psi_1 allocates and sends signals (sigNo 1000) to psi_2.
         for (int i = 0; i < receiveEvents.length; i++)
         {
            if ((receiveEvents[i].getSignalNumber() == 1000) &&
                receiveEvents[i].getSenderProcess().getName().endsWith("psi_1") &&
                receiveEvents[i].getAddresseeProcess().getName().endsWith("psi_2"))
            {
               return true;
            }
         }
         return false;
      }

      public boolean checkAllocEvents()
      {
         AllocEvent[] allocEvents = getAllocEvent();

         for (int i = 0; i < allocEvents.length; i++)
         {
            if ((allocEvents[i].getSignalNumber() == 1000) &&
                allocEvents[i].getProcess().getName().endsWith("psi_1"))
            {
               return true;
            }
         }
         return false;
      }

      public boolean checkFreeEvents()
      {
         FreeEvent[] freeEvents = getFreeEvent();

         // Process psi_1 allocates and sends signals (sigNo 1000) to psi_2,
         // psi_2 then frees these signals.
         for (int i = 0; i < freeEvents.length; i++)
         {
            if ((freeEvents[i].getSignalNumber() == 1000) &&
                freeEvents[i].getProcess().getName().endsWith("psi_2"))
            {
               return true;
            }
         }
         return false;
      }

      public boolean checkCreateEvents()
      {
         CreateEvent[] createEvents = getCreateEvent();

         // Process psi_3 creates a new process psi_new and then kills it.
         for (int i = 0; i < createEvents.length; i++)
         {
            if (createEvents[i].getCreatedProcess().getName().indexOf("psi_new") >= 0)
            {
               return true;
            }
         }
         System.out.println("checkCreateEvents failed. " +
               createEvents.length + " create events received.");
         return false;
      }

      public boolean checkKillEvents()
      {
         KillEvent[] killEvents = getKillEvent();

         // Process psi_3 creates a new process psi_new and then kills it.
         for (int i = 0; i < killEvents.length; i++)
         {
            if (killEvents[i].getKilledProcess().getName().indexOf("psi_new") >= 0)
            {
               return true;
            }
         }
         System.out.println("checkKillEvents failed. " +
               killEvents.length + " kill events received.");
         return false;
      }

      public boolean checkErrorEvents()
      {
         // No notifications arrive yet, don't know why...
         ErrorEvent[] errorEvents = getErrorEvent();

         // Process psi_4 frees a buffer that is not allocated.
         // #define OSE_FREE_BUF (0x110000)
         // #define OSE_EUSED_NIL_POINTER (0x31)
         // In OSEck error events are reported from system.
         if (testTarget.getOsType() == Target.OS_OSE_CK)
         {
            for (int i = 0; i < errorEvents.length; i++)
            {
               if ((errorEvents[i].getError() & 0x4001) == 0x4001)
               {
                  return true;
               }
            }
         }
         else
         {
            for (int i = 0; i < errorEvents.length; i++)
            {
               if (errorEvents[i].getProcess().getName().endsWith("psi_4") &&
                   (errorEvents[i].getError() & 0x110031) == 0x110031)
               {
                  return true;
               }
            }
         }
         System.out.println("checkErrorEvents failed. " +
               errorEvents.length + " error events received.");
         return false;
      }

      public boolean checkBindEvents()
      {
         // FIXME
         return true;
      }

      public boolean checkUserEvents()
      {
         // FIXME
         return true;
      }

      public boolean checkResetEvents()
      {
         // FIXME
         return true;
      }

      public boolean checkTimeoutEvents()
      {
         // FIXME
         return true;
      }

      /**
       * Creates a new list of EvenActionPoints configured to generate
       * notifications about things that happen in processes with names that
       * match "psi*". The target that has this object as event listener should
       * invoke setEventActionPoint() for each object in the list.
       */
      public List getEventActionPoints()
      {
         List eventActionsPoints = new ArrayList();

         // There are a lot of parameters to set for EventActionPoint objects,
         // but basically we will just tell them to generate a notification
         // (with EventActionPoint.ACTION_NOTIFY) or a trace event (with
         // EventActionPoint.ACTION_TRACE) for something that happens in
         // processes named "psi". The rest of the parameters are modifiers,
         // range filters etc, which we leave with typical parameters. Some
         // object take a "from" and a "to" process, others just have a "from"
         // process.

         if (testTarget.hasSetSendEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new SendEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*",
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, true, 0, false, 0x00000000,
                  0xFFFFFFFF, 0, 1000, false, 0, 0, false, 0, 0xFFFFFFFF )));
         }

         if (testTarget.hasSetReceiveEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new ReceiveEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*",
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, true, 0, false, 0x00000000,
                  0xFFFFFFFF, 0, 1000, false, 0, 0, false, 0, 0xFFFFFFFF )));
         }

         if (testTarget.hasSetAllocEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new AllocEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, 0, 0, 1000)));
         }

         if (testTarget.hasSetBindEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new BindEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, 0, 0, 1000)));
         }

         if (testTarget.hasSetCreateEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new CreateEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*",
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, 0, 0, 1000)));
         }

         if (testTarget.hasSetErrorEventActionPointSupport())
         {
            switch (testTarget.getOsType())
            {
            // For OSEck we track user error events in system scope.
            case Target.OS_OSE_CK:
               eventActionsPoints.add(doTest(new ErrorEventActionPoint(0,
                     EventActionPoint.SCOPE_SYSTEM, 0, "", 0, 0, "", false,
                     action, true, 0, false, false, 0, 0xFFFFFFFF,
                     false, 0, 0xFFFFFFFF, 0, 1000)));
               break;
            default:
               eventActionsPoints.add(doTest(new ErrorEventActionPoint(0,
                     EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                     action, true, 0, true, false, 0, 0xFFFFFFFF,
                     false, 0, 0xFFFFFFFF, 0, 1000)));
            }
         }

         if (testTarget.hasSetFreeEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new FreeEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, 0, 0, 1000, false, 0, 0, false, 0, 0xFFFFFFFF )));
         }

         if (testTarget.hasSetKillEventActionPointSupport())
         {
            switch (testTarget.getOsType())
            {
            // For OSEck kill events are in system scope.
            case Target.OS_OSE_CK:
               eventActionsPoints.add(doTest(new KillEventActionPoint(0,
                     EventActionPoint.SCOPE_SYSTEM, 0, "",
                     EventActionPoint.SCOPE_SYSTEM, 0, "", 0, 0, "", false,
                     action, true, 0, 0, 1000)));
               break;
            default:
               eventActionsPoints.add(doTest(new KillEventActionPoint(0,
                     EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*",
                     EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                     action, true, 0, 0, 1000)));
            }
         }

         // This is added only for action trace.
         if (testTarget.hasSetSwapEventActionPointSupport() &&
             (action == EventActionPoint.ACTION_TRACE))
         {
            eventActionsPoints.add(doTest(new SwapEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*",
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, 0, 0, 1000)));
         }

         if (testTarget.hasSetTimeoutEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new TimeoutEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, 0, 0, 1000)));
         }

         if (testTarget.hasSetUserEventActionPointSupport())
         {
            eventActionsPoints.add(doTest(new UserEventActionPoint(0,
                  EventActionPoint.SCOPE_NAME_PATTERN, 0, "psi*", 0, 0, "", false,
                  action, true, true, 0, false, 0, 0xFFFFFFFF,
                  0, 1000, false, 0, 0, false, 0, 0xFFFFFFFF )));
         }

         return eventActionsPoints;
      }

      protected SendEventActionPoint doTest(SendEventActionPoint point)
      {
         point.getNumberMax();
         point.getNumberMin();
         point.isNumberRangeInverted();
         point.isSignalDataIncluded();
         // void setNumberMax(int numberMax);
         // void setNumberMin(int numberMin);
         // void setNumberRangeInverted(boolean numberRangeInverted);
         // void setSignalDataIncluded(boolean signalDataIncluded);
         point.toString();
         return point;
      }

      protected ReceiveEventActionPoint doTest(ReceiveEventActionPoint point)
      {
         point.getNumberMax();
         point.getNumberMin();
         point.isNumberRangeInverted();
         point.isSignalDataIncluded();
         // void setNumberMax(int numberMax);
         // void setNumberMin(int numberMin);
         // void setNumberRangeInverted(boolean numberRangeInverted);
         // void setSignalDataIncluded(boolean signalDataIncluded);
         point.toString();
         return point;
      }

      protected AllocEventActionPoint doTest(AllocEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected BindEventActionPoint doTest(BindEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected CreateEventActionPoint doTest(CreateEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected ErrorEventActionPoint doTest(ErrorEventActionPoint point)
      {
         point.getErrorMax();
         point.getErrorMin();
         point.getExtraMax();
         point.getExtraMin();
         point.isErrorRangeInverted();
         point.isExecutive();
         point.isExtraRangeInverted();
         // void setErrorMax(int errorMax);
         // void setErrorMin(int errorMin);
         // void setErrorRangeInverted(boolean errorRangeInverted);
         // void setExecutive(boolean executive);
         // void setExtraMax(int extraMax);
         // void setExtraMin(int extraMin);
         // void setExtraRangeInverted(boolean extraRangeInverted);
         point.toString();
         return point;
      }

      protected FreeEventActionPoint doTest(FreeEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected KillEventActionPoint doTest(KillEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected SwapEventActionPoint doTest(SwapEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected TimeoutEventActionPoint doTest(TimeoutEventActionPoint point)
      {
         // No methods to test at this point.
         return point;
      }

      protected UserEventActionPoint doTest(UserEventActionPoint point)
      {
         point.getNumberMax();
         point.getNumberMin();
         point.isEventDataIncluded();
         point.isNumberRangeInverted();
         // void setEventDataIncluded(boolean eventDataIncluded);
         // void setNumberMax(int numberMax);
         // void setNumberMin(int numberMin);
         // void setNumberRangeInverted(boolean numberRangeInverted);
         point.toString();
         return point;
      }

      public void signalSent(SendEvent event)
      {
         signalSent.add(event);
      }

      public void signalReceived(ReceiveEvent event)
      {
         signalReceived.add(event);
      }

      public void processSwapped(SwapEvent event)
      {
         processSwapped.add(event);
      }

      public void processCreated(CreateEvent event)
      {
         processCreated.add(event);
      }

      public void processKilled(KillEvent event)
      {
         processKilled.add(event);
      }

      public void errorCalled(ErrorEvent event)
      {
         errorCalled.add(event);
      }

      public void targetReset(ResetEvent event)
      {
         targetReset.add(event);
      }

      public void userReported(UserEvent event)
      {
         userReported.add(event);
      }

      public void processBound(BindEvent event)
      {
         processBound.add(event);
      }

      public void signalAllocated(AllocEvent event)
      {
         signalAllocated.add(event);
      }

      public void signalFreed(FreeEvent event)
      {
         signalFreed.add(event);
      }

      public void systemCallTimedout(TimeoutEvent event)
      {
         timedout.add(event);
      }

      public void eventsLost(LossEvent event)
      {
         eventLost.add(event);
      }

      public void scopeIntercepted(SendEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(ReceiveEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(SwapEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(CreateEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(KillEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(ErrorEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(UserEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(BindEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(AllocEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(FreeEvent event)
      {
         System.out.println(event.toString());
      }

      public void scopeIntercepted(TimeoutEvent event)
      {
         System.out.println(event.toString());
      }
   }

   private class EventTraceCollectThread extends Thread
   {
      private final TargetEventVerifier eventTraceVerifier;

      EventTraceCollectThread(TargetEventVerifier targetEventVerifier)
      {
         if (targetEventVerifier == null)
         {
            throw new IllegalArgumentException();
         }
         eventTraceVerifier = targetEventVerifier;
      }

      public void run()
      {
         IProgressMonitor nullProgressMonitor = new NullProgressMonitor();
         EventTraceHandle handle = new EventTraceHandle();

         while (!interrupted())
         {
            try
            {
               Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
               return;
            }

            try
            {
               // The Target class should be thread-safe so just use it...
               TargetEvents eventInfo = testTarget.getEventTraceMultiple(
                     nullProgressMonitor, 500, handle);
               if (eventInfo != null)
               {
                  // Access properties.
                  eventInfo.getTarget();
                  eventInfo.isEnabled();
                  synchronized (eventTraceVerifier)
                  {
                     eventTraceVerifier.addEvents(eventInfo.getEvents());
                  }
               }
            }
            catch (OperationCanceledException e)
            {
               return;
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
         }
      }
   }
}
