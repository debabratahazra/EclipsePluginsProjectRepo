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
import java.util.Iterator;
import java.util.List;
import com.ose.system.Block;
import com.ose.system.Gate;
import com.ose.system.Segment;
import com.ose.system.SystemModel;
import com.ose.system.Target;
import com.ose.system.ui.util.StringUtils;
import com.ose.system.ui.views.block.BlockView;
import com.ose.system.ui.views.process.ProcessView;
import com.ose.ui.tests.utils.EntityFilter;
import com.ose.ui.tests.utils.EntityType;
import com.ose.ui.tests.utils.TestDataScanner;

public enum RawFileTargetData implements ITargetData
{
   INSTANCE;

   private final InetAddress testGateAddress;

   private final int testGatePort;
   
   private final String testLoadModulePath;
   
   private final String testTargetHuntPath;

   private Target testTarget;

   private Gate testGate;

   private RawFileTargetData()
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

   public void initialize()
   {
      testGate = SystemModel.getInstance().getGate(testGateAddress,
            testGatePort);

      try
      {
         setTestTarget();
      }
      catch (IOException e)
      {
         e.printStackTrace();
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

   public InetAddress getGateAddress()
   {
      return testGateAddress;
   }

   public int getGatePort()
   {
      return testGatePort;
   }

   public List<String[]> getBlocks(int scopeType, int scopeId)
   {
      List<String[]> blocks = new ArrayList<String[]>();

      try
      {
         blocks = TestDataScanner.getProperties("/testdata/blocks.data", null, -1);
         
         filtrate(blocks, scopeType, scopeId, BlockView.COLUMN_BID);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return blocks;
   }

   public List<String[]> getProcesses(int scopeType, int scopeId)
   {
      List<String[]> processes = new ArrayList<String[]>();

      try
      {
         processes = TestDataScanner.getProperties("/testdata/processes.data", null, -1);
         
         filtrate(processes, scopeType, scopeId, ProcessView.COLUMN_BID);
      
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return processes;
   }

   private void filtrate(List<String[]> entities, int scopeType, int scopeId, int bidColumnIndex)
   {
      if (testTarget == null)
      {
         initialize();
      }

      switch (scopeType)
      {
         case Target.SCOPE_SYSTEM:
            break;
         case Target.SCOPE_SEGMENT_ID:
            Segment[] segments = testTarget.getSegments();
            Block[] blocks = null;
            List<Integer> bids = new ArrayList<Integer>();
            for (Segment segment : segments)
            {
               if (segment.getId() == scopeId)
               {
                  blocks = segment.getBlocks();
                  for (Block block : blocks)
                  {
                     bids.add(block.getId());
                  }
                  break;
               }
            }
            Iterator<String[]> iter = entities.iterator();
            while (iter.hasNext())
            {
               String[] props = iter.next();
               if (!bids.contains(StringUtils.parseU32(props[bidColumnIndex])))
               {
                  iter.remove();
               }
            }
            break;
         case Target.SCOPE_BLOCK_ID:
            break;
      }
   }

   public String getTargetHuntPath()
   {
      return testTargetHuntPath;
   }

   public void destroy()
   {
      testGate = null;
      testTarget = null;
   }

   private List<String[]> getHeaps(int scopeType, int scopeId)
   {
      // TODO Return static heap information for the given target.
      return null;
   }

   private List<String[]> getPools(int scopeType, int scopeId)
   {
      List<String[]> pools = new ArrayList<String[]>();

      try
      {
         pools = TestDataScanner.getProperties("/testdata/pools.data", null, -1);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return pools;
   }

   public List<String[]> getEntities(EntityType type, int scopeType, int scopeId)
   {
      List<String[]> entities = new ArrayList<String[]>();
      switch (type)
      {
         case PROCESS:
            entities = getProcesses(scopeType, scopeId);
            break;
         case BLOCK:
            entities = getBlocks(scopeType, scopeId);
            break;
         case HEAP:
            entities = getHeaps(scopeType, scopeId);
            break;
         case POOL:
            entities = getPools(scopeType, scopeId);
            break;
      }
      return entities;
   }

   public List<String[]> getFilteredEntities(EntityType type, int scopeType, int scopeId, String filter,
         int columnIndex)
   {
      // TODO The concrete implementations should be enhanced to take care of
      // Scope(System, Segment, Block etc).
      List<String[]> entities = getEntities(type,scopeType, scopeId);
      entities = TestDataScanner.getProperties(entities, filter, columnIndex);
      return entities;
   }
   
   public List<String[]> getFilteredEntities(EntityType type, int scopeType, int scopeId, EntityFilter[] filter)
   {
      // TODO The concrete implementations should be enhanced to take care of
      // Scope(System, Segment, Block etc).
      List<String[]> entities = getEntities(type, scopeType, scopeId);
      entities = TestDataScanner.getProperties(entities, filter);
      return entities;
   }

   public String getTestLoadModulePath()
   {
      return testLoadModulePath;
   }
}
