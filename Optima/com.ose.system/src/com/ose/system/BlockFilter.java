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
 * This class is a filter used when filtering OSE blocks.
 *
 * @see com.ose.system.Target#getFilteredBlocks(IProgressMonitor, int, int, BlockFilter)
 */
public class BlockFilter
{
   private final List tags;

   /**
    * Create a new empty block filter object.
    */
   public BlockFilter()
   {
      tags = new ArrayList();
   }

   /**
    * Add a filter criteria for the user number of a block.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min user number value, inclusive.
    * @param to       the max user number value, exclusive.
    */
   public void filterUid(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_UID,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the supervisor mode property of a block.
    *
    * @param exclude     whether or not to invert the filter criteria.
    * @param supervisor  the supervisor mode value.
    */
   public void filterSupervisor(boolean exclude, int supervisor)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_SUPERVISOR,
            new Integer(supervisor)));
   }

   /**
    * Add a filter criteria for the number of signals attached to a block.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min number of signals attached value, inclusive.
    * @param to       the max number of signals attached value, exclusive.
    */
   public void filterSignalsAttached(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_SIGNALS_ATTACHED,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the error handler address of a block.
    *
    * @param exclude       whether or not to invert the filter criteria.
    * @param errorHandler  the error handler address value.
    */
   public void filterErrorHandler(boolean exclude, int errorHandler)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_ERROR_HANDLER,
            new Integer(errorHandler)));
   }

   /**
    * Add a filter criteria for the maximum signal size, in bytes, that can be
    * allocated by a process in a block.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min maximum signal size value, inclusive.
    * @param to       the max maximum signal size value, exclusive.
    */
   public void filterMaxSigSize(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_MAX_SIG_SIZE,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Add a filter criteria for the ID of the pool used as signal pool by a
    * block.
    *
    * @param exclude    whether or not to invert the filter criteria.
    * @param sigPoolId  the signal pool ID value.
    */
   public void filterSigPoolId(boolean exclude, int sigPoolId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_SIG_POOL_ID,
            new Integer(sigPoolId)));
   }

   /**
    * Add a filter criteria for the ID of the pool used as stack pool by a
    * block.
    *
    * @param exclude      whether or not to invert the filter criteria.
    * @param stackPoolId  the stack pool ID value.
    */
   public void filterStackPoolId(boolean exclude, int stackPoolId)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_STACK_POOL_ID,
            new Integer(stackPoolId)));
   }

   /**
    * Add a filter criteria for the name of a block. Accepts wildcards, where a
    * '*' matches any string of zero or more characters, and a '?' matches any
    * single character.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param name     the block name value.
    */
   public void filterName(boolean exclude, String name)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NAME, name));
   }

   /**
    * Add a filter criteria for the execution unit a block is bound to.
    *
    * @param exclude  whether or not to invert the filter criteria.
    * @param from     the min execution unit value, inclusive.
    * @param to       the max execution unit value, exclusive.
    */
   public void filterExecutionUnit(boolean exclude, int from, int to)
   {
      if (exclude)
      {
         tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_NOT));
      }
      tags.add(new MonitorTag(Monitor.MONITOR_BLOCK_FILTER_TAG_EU_ID,
            new Integer[] {new Integer(from), new Integer(to)}));
   }

   /**
    * Return an array of the OSE monitor protocol tags constituting this block
    * filter.
    *
    * @return an array of the OSE monitor protocol tags constituting this block
    * filter.
    */
   MonitorTag[] getTags()
   {
      return (MonitorTag[]) tags.toArray(new MonitorTag[0]);
   }

   /**
    * Return a string representation of this block filter object.
    *
    * @return a string representation of this block filter object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer("Block Filter:\n");
      for (Iterator i = tags.iterator(); i.hasNext();)
      {
         MonitorTag tag = (MonitorTag) i.next();
         sb.append("Type: " + tag.getType() + ", args: " + tag.getArgs() + "\n");
      }
      return sb.toString();
   }
}
