/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

import com.ose.system.service.monitor.MonitorCPUBlockReport;
import com.ose.system.service.monitor.MonitorCPUBlockReport.MonitorCPUBlock;

/**
 * This class represents an OSE CPU block level load report.
 * A CPUBlockReport object is immutable and its content is a snapshot
 * at the time of the retrieving Target.getCPUBlockReports() call.
 *
 * @see com.ose.system.CPUBlockReports#getReports()
 */
public class CPUBlockReport extends TargetReport
{
   private final int sumOther;
   private final CPUBlockLoad[] sumBlocks;

   /**
    * Create a new CPU block level load report object.
    *
    * @param report  the internal CPU block level load report object.
    */
   CPUBlockReport(MonitorCPUBlockReport report)
   {
      super(report.tick, report.ntick, report.interval);
      this.sumOther = report.sumOther;
      this.sumBlocks = new CPUBlockLoad[report.blocksCount];
      for (int i = 0; i < report.blocksCount; i++)
      {
         this.sumBlocks[i] = new CPUBlockLoad(report.blocks[i]);
      }
   }

   /**
    * Create a new CPU block level load report object.
    *
    * @param tick       the tick.
    * @param ntick      the micro tick.
    * @param interval   the measurement interval.
    * @param sumOther   the other sum.
    * @param sumBlocks  the blocks sum.
    */
   CPUBlockReport(int tick,
                  int ntick,
                  int interval,
                  int sumOther,
                  CPUBlockLoad[] sumBlocks)
   {
      super(tick, ntick, interval);
      this.sumOther = sumOther;
      this.sumBlocks = sumBlocks;
   }

   /**
    * Create a new CPU block level load report object for off-line usage.
    *
    * @param tick       the tick.
    * @param ntick      the micro tick.
    * @param interval   the measurement interval.
    * @param sumOther   the other sum.
    * @param sumBlocks  the blocks sum.
    * @return a new CPU block level load report object for off-line usage.
    */
   public static CPUBlockReport getInstance(int tick,
                                            int ntick,
                                            int interval,
                                            int sumOther,
                                            CPUBlockLoad[] sumBlocks)
   {
      return new CPUBlockReport(tick, ntick, interval, sumOther, sumBlocks);
   }

   /**
    * Return the time the CPU was occupied with all other blocks than those
    * returned by getSumBlocks() (unspecified unit).
    *
    * @return the time the CPU was occupied with all other blocks than those
    * returned by getSumBlocks().
    */
   public int getSumOther()
   {
      return sumOther;
   }

   /**
    * Return an array of the time the CPU was occupied per block.
    *
    * Note that some OSE target monitors return all blocks that have executed
    * within the measurement interval, while other OSE target monitors return a
    * fixed-size subset containing the most time-consuming blocks that have
    * executed within the measurement interval.
    *
    * @return an array of the time the CPU was occupied per block.
    */
   public CPUBlockLoad[] getSumBlocks()
   {
      return sumBlocks;
   }

   /**
    * This class contains the time the CPU was occupied with a specific block.
    * A CPUBlockLoad object is immutable and its content is a snapshot at the
    * time of the retrieving Target.getCPUBlockReports() call.
    *
    * @see com.ose.system.CPUBlockReport#getSumBlocks()
    */
   public static class CPUBlockLoad
   {
      private final int id;
      private final int sum;

      /**
       * Create a new CPU block load object.
       *
       * @param block  the internal CPU block load object.
       */
      CPUBlockLoad(MonitorCPUBlock block)
      {
         if (block == null)
         {
            throw new NullPointerException();
         }
         this.id = block.id;
         this.sum = block.sum;
      }

      /**
       * Create a new CPU block load object.
       *
       * @param id   the id.
       * @param sum  the sum.
       */
      CPUBlockLoad(int id, int sum)
      {
         this.id = id;
         this.sum = sum;
      }

      /**
       * Create a new CPU block load object for off-line usage.
       *
       * @param id   the id.
       * @param sum  the sum.
       * @return a new CPU block load object for off-line usage.
       */
      public static CPUBlockLoad getInstance(int id, int sum)
      {
         return new CPUBlockLoad(id, sum);
      }

      /**
       * Return the id of the block.
       *
       * @return the id of the block.
       */
      public int getId()
      {
         return id;
      }

      /**
       * Return the time the CPU was occupied with the block
       * (unspecified unit).
       *
       * @return the time the CPU was occupied with the block.
       */
      public int getSum()
      {
         return sum;
      }
   }
}
