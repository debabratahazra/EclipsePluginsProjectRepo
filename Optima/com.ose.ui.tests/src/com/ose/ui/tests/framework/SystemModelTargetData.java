/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.ui.tests.framework;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.NullProgressMonitor;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Pool;
import com.ose.system.PoolBufferSizeInfo;
import com.ose.system.PoolFragmentInfo;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.views.block.BlockView;
import com.ose.system.ui.views.process.ProcessView;
import com.ose.ui.tests.utils.EntityFilter;
import com.ose.ui.tests.utils.EntityType;
import com.ose.ui.tests.utils.TestDataScanner;
import com.ose.ui.tests.utils.UIUtils;

public enum SystemModelTargetData implements ITargetData
{
   INSTANCE;

   private final InetAddress testGateAddress;

   private final int testGatePort;

   private final String testTargetHuntPath;
   
   private final String testLoadModulePath;
   
   private SystemModel sm;

   private Gate testGate;

   private Target testTarget;

   private SystemModelTargetData()
   {
      String address = System.getProperty("com.ose.ui.tests.gate.address");
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

      String port = System.getProperty("com.ose.ui.tests.gate.port");
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

      testTargetHuntPath = System.getProperty(
            "com.ose.ui.tests.target.huntpath", "");
      
      testLoadModulePath = System
            .getProperty("com.ose.ui.tests.target.loadmodulepath");
   }

   public void initialize() throws IOException
   {
      sm = SystemModel.getInstance();

      // Always perform this initial setup; basically this is equal to
      // performing the three first tests, without this code it is not
      // possible to run subsequent tests independently.
      setTestGate();
      try
      {
         setTestTarget();
         refresh();
      }
      catch (IOException e)
      {
         e.printStackTrace();
         throw e;
      }
   }

   /**
    * Sets the test gate.
    */
   private void setTestGate()
   {
      if (testGate == null)
      {
         testGate = sm.getGate(testGateAddress, testGatePort);
      }
   }

   /**
    * Sets the test target.
    * 
    * @return true if the target is not on the same hardware and was added using
    *         its hunt path, false if the target is on the same hardware and
    *         does not need to be added manually.
    * @throws IOException
    *            if an I/O exception occurred.
    */
   private boolean setTestTarget() throws IOException
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

   public List<String[]> getBlocks(int scopeType, int scopeId)
   {
      List<String[]> blocks = new ArrayList<String[]>();
      Block[] blocksinfo = new Block[0];

      switch (scopeType)
      {
         case Target.SCOPE_SYSTEM:
            blocksinfo = testTarget.getBlocks();
            break;
         case Target.SCOPE_SEGMENT_ID:
            Segment[] segments = testTarget.getSegments();
            for (Segment segment : segments)
            {
               if (segment.getId() == scopeId)
               {
                  blocksinfo = segment.getBlocks();
                  break;
               }
            }
            break;
         case Target.SCOPE_BLOCK_ID:
            //TODO Currently support only segments
            break;
      }      

      for (Block block : blocksinfo)
      {
         String[] blockProperties = new String[8];
         blockProperties[BlockView.COLUMN_NAME] = block.getName();
         blockProperties[BlockView.COLUMN_BID] = UIUtils
               .toString(block.getId());
         blockProperties[BlockView.COLUMN_SID] = UIUtils.toString(block
               .getSid());
         blockProperties[BlockView.COLUMN_UID] = UIUtils.toString(block
               .getUid());
         blockProperties[BlockView.COLUMN_MAX_SIGNAL_SIZE] = UIUtils
               .toString(block.getMaxSignalSize());
         blockProperties[BlockView.COLUMN_SIGNAL_POOL_ID] = UIUtils
               .toString(block.getSignalPoolId());
         blockProperties[BlockView.COLUMN_STACK_POOL_ID] = UIUtils
               .toString(block.getStackPoolId());
         blockProperties[BlockView.COLUMN_EXECUTION_UNIT] = UIUtils
               .toString(block.getExecutionUnit());

         blocks.add(blockProperties);
      }

      return blocks;
   }

   public List<String[]> getProcesses(int scopeType, int scopeId)
   {
      List<String[]> processes = new ArrayList<String[]>();
      List<Process> processesInfo = new ArrayList<Process>();
      Process[] procs = null;

      switch (scopeType)
      {
         case Target.SCOPE_SYSTEM:
            procs = testTarget.getProcesses();
            break;
         case Target.SCOPE_SEGMENT_ID:
            Segment[] segments = testTarget.getSegments();
            for (Segment segment : segments)
            {
               if (segment.getId() == scopeId)
               {
                  Block[] blocks = segment.getBlocks();
                  for (Block block : blocks)
                  {
                     for (Process prrr : block.getProcesses())
                     {
                        processesInfo.add(prrr);
                     }
                  }
                  procs = processesInfo.toArray(new Process[processesInfo.size()]);
                  break;
               }
            }
            break;
         case Target.SCOPE_BLOCK_ID:
            //TODO Currently support only segments
            break;
      }

      for (Process process : procs)
      {
         String[] processProperties = new String[16];
         processProperties[ProcessView.COLUMN_NAME] = process.getName();
         processProperties[ProcessView.COLUMN_ID] = UIUtils.toHexString(UIUtils
               .toString(process.getId()));
         processProperties[ProcessView.COLUMN_BID] = UIUtils.toHexString(
               UIUtils.toString(process.getBid()));
         processProperties[ProcessView.COLUMN_UID] = UIUtils.toString(process
               .getUid());
         processProperties[ProcessView.COLUMN_TYPE] = UIUtils.toProcessTypeString(
               UIUtils.toString(process.getType()));
         processProperties[ProcessView.COLUMN_STATE] = UIUtils.toProcessStateString(
               UIUtils.toString(process.getState()));
         processProperties[ProcessView.COLUMN_PRIORITY] = UIUtils
               .toString(process.getPriority());
         processProperties[ProcessView.COLUMN_NUM_SIGNALS_IN_QUEUE] = UIUtils
               .toString(process.getNumSignalsInQueue());
         processProperties[ProcessView.COLUMN_SIGSELECT_COUNT] = UIUtils
               .toUIString(process.getSigselect().length);
         processProperties[ProcessView.COLUMN_ENTRYPOINT] = UIUtils.toHexString(
               UIUtils.toString(process.getEntrypoint()));
         processProperties[ProcessView.COLUMN_SEMAPHORE_VALUE] = UIUtils
               .toString(process.getSemaphoreValue());
         processProperties[ProcessView.COLUMN_NUM_SIGNALS_OWNED] = UIUtils
               .toString(process.getNumSignalsOwned());
         processProperties[ProcessView.COLUMN_FILE_NAME] = (process
               .getFileName() == "") ? "N/A" : process.getFileName();
         processProperties[ProcessView.COLUMN_LINE_NUMBER] = UIUtils
               .toUIString(process.getLineNumber());
         processProperties[ProcessView.COLUMN_STACK_SIZE] = UIUtils
               .toUIString(process.getStackSize());
         processProperties[ProcessView.COLUMN_EXECUTION_UNIT] = UIUtils
               .toString(process.getExecutionUnit());

         processes.add(processProperties);
      }

      return processes;
   }

   public void destroy() throws IOException
   {
      testGate = null;
      testTarget = null;
   }

   public InetAddress getGateAddress()
   {
      return testGateAddress;
   }

   public int getGatePort()
   {
      return testGatePort;
   }

   public String getTestLoadModulePath()
   {
      return testLoadModulePath;
   }
   
   public String getTargetHuntPath()
   {
      return testTargetHuntPath;
   }

   private void refresh()
   {
      try
      {
         testTarget.update(new NullProgressMonitor(), true);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private List<String[]> getHeaps(int scopeType, int scopeId)
   {
      // TODO Use System Model APIs to retrieve heap information
      return null;
   }

   private List<String[]> getPools(int scopeType, int scopeId)
   {
      List<String[]> pools = new ArrayList<String[]>();

      Pool[] osePools = testTarget.getPools();
      for (Pool pool : osePools)
      {
         String[] poolProperties = new String[8];
         poolProperties[0] = StringUtils.toKilledString(pool.isKilled());
         poolProperties[1] = UIUtils.toHexString(UIUtils
               .toString(pool.getId()));

         long totalSize = pool.getTotalSize();
         long freeSize = pool.getFreeSize();
         long usedSize = totalSize - freeSize;

         poolProperties[2] = Long.toString(totalSize);
         poolProperties[3] = Long.toString(freeSize) + " (" +
               StringUtils.toPercentString(freeSize, totalSize) + ")";
         poolProperties[4] = Long.toString(usedSize) + " (" +
               StringUtils.toPercentString(usedSize, totalSize) + ")";

         StringBuffer strBuffer = new StringBuffer();
         PoolFragmentInfo[] poolFragments = pool.getPoolFragments();
         for (int i = 0; i < poolFragments.length; i++)
         {
            PoolFragmentInfo fragment = poolFragments[i];
            long address = fragment.getAddress();
            long size = fragment.getSize();
            long usedStacks = fragment.getUsedStacks();
            long usedSignals = fragment.getUsedSignals();
            long usedTotal = usedStacks + usedSignals;
            long free = size - usedTotal;

            strBuffer.append(StringUtils.toHexString(address) + "-");
            strBuffer.append(StringUtils.toHexString(address + size) + "-");
            strBuffer.append(Long.toString(size) + "-");
            strBuffer.append(Long.toString(free) + " (" +
                  StringUtils.toPercentString(free, size) + ")" + "-");
            strBuffer.append(Long.toString(usedTotal) + " (" +
                  StringUtils.toPercentString(usedTotal, size) + ")" + "-");
            strBuffer.append(Long.toString(usedStacks) + " (" +
                  StringUtils.toPercentString(usedStacks, size) + ")" + "-");
            strBuffer.append(Long.toString(usedSignals) + " (" +
                  StringUtils.toPercentString(usedSignals, size) + ")");

            if (i > 0 && i != poolFragments.length)
            {
               strBuffer.append(":");
            }
         }
         poolProperties[5] = strBuffer.toString();

         strBuffer = new StringBuffer();
         PoolBufferSizeInfo[] stackSizes = pool.getStackSizes();
         for (int i = 0; i < stackSizes.length; i++)
         {
            strBuffer.append(Integer.toString(stackSizes[i].getAllocated()) + "-");
         }
         poolProperties[6] = strBuffer.toString();

         strBuffer = new StringBuffer();
         PoolBufferSizeInfo[] signalSizes = pool.getSignalSizes();
         for (int i = 0; i < signalSizes.length; i++)
         {
            strBuffer.append(Integer.toString(signalSizes[i].getAllocated()) + "-");
         }
         poolProperties[7] = strBuffer.toString();

         pools.add(poolProperties);
      }

      return pools;
   }

   public List<String[]> getEntities(EntityType type, int scopetype, int scope_id)
   {
      List<String[]> entities = new ArrayList<String[]>();
      switch (type)
      {
         case PROCESS:
            entities = getProcesses(scopetype, scope_id);
            break;
         case BLOCK:
            entities = getBlocks(scopetype, scope_id);
            break;
         case HEAP:
            entities = getHeaps(scopetype, scope_id);
            break;
         case POOL:
            entities = getPools(scopetype, scope_id);
            break;
      }
      return entities;
   }

   public List<String[]> getFilteredEntities(EntityType type, int scopetype, int scope_id, String filter,
         int columnIndex)
   {
      // TODO The concrete implementations should be enhanced to take care of
      // Scope(System, Segment, Block etc).
      List<String[]> entities = getEntities(type, scopetype, scope_id);
      entities = TestDataScanner.getProperties(entities, filter, columnIndex);
      return entities;
   }
   
   public List<String[]> getFilteredEntities(EntityType type, int scopetype, int scope_id, EntityFilter[] filter)
   {
      // TODO The concrete implementations should be enhanced to take care of
      // Scope(System, Segment, Block etc).
      List<String[]> entities = getEntities(type, scopetype, scope_id);
      entities = TestDataScanner.getProperties(entities, filter);
      return entities;
   }   
}
