/* COPYRIGHT-ENEA-SRC-R2 *
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

package com.ose.system.ui.views.pooloptimizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

/**
 * This algorithm eliminates levels (sizes) at minimum cost until it reaches
 * the number of levels specified. The biggest size given by the user cannot
 * be removed. In addition to the LevelOptimization algorithm, the
 * ExplorerLevelOptimization algorithm switches algorithm to an exhaustive
 * exploration with start from a threshold level in percent given by the user.
 */
public class ExplorerLevelOptimization implements PoolOptimizerAlgorithm
{
   private static final int NUM_LEVELS = 8;

   private final int levelsPercent;

   public ExplorerLevelOptimization(int levelsPercent)
   {
      if ((levelsPercent < 0) || (levelsPercent > 100))
      {
         throw new IllegalArgumentException();
      }
      this.levelsPercent = levelsPercent;
   }

   public PoolOptimizerResult suggestBufferSizes(IProgressMonitor monitor,
                                                 Integer[] allocSizes,
                                                 int largestAllocSize,
                                                 int cacheLineSize,
                                                 int adminBlockSize)
   {
      List levels;
      int curValue;
      Level curLevel;
      int requestedMemory = 0;
      int threshold;
      List thresholdLevels;
      int[] newBufferSizes;
      BufferSet result;
      int overheadMemory;
      int availableMemory;
      int slackMemory;

      if ((monitor == null) || (allocSizes == null) ||
          (allocSizes.length == 0) || (largestAllocSize <= 0) ||
          (cacheLineSize <= 0) || (adminBlockSize < 0))
      {
         throw new IllegalArgumentException();
      }

      levels = new LinkedList();

      // Sort the allocated sizes in ascending order.
      Arrays.sort(allocSizes);

      // Add extra level (defined by the user) that cannot be removed.
      // This level has frequency 0, so it does not mess up requested memory.
      curValue = largestAllocSize;
      curLevel = new Level(largestAllocSize + adminBlockSize);
      levels.add(curLevel);

      // Populate the list of unique levels in descending order.
      // Also calculate the amount of requested memory.
      for (int i = allocSizes.length - 1; i >= 0; i--)
      {
         int reqValue = allocSizes[i].intValue();
         requestedMemory += reqValue;

         if (reqValue < curValue)
         {
            int value;
            int integerPart;
            int remainder;

            curValue = reqValue;
            value = reqValue + adminBlockSize;
            integerPart = value / cacheLineSize;
            remainder = value % cacheLineSize;
            value = (integerPart + ((remainder > 0) ? 1 : 0)) * cacheLineSize;
            if (value < curLevel.size)
            {
               curLevel = new Level(value);
               levels.add(curLevel);
            }
         }

         curLevel.frequency++;
      }

      // Handle the case where less unique sizes
      // than NUM_LEVELS have been allocated.
      while (levels.size() < NUM_LEVELS)
      {
         // Add levels of the max size first.
         levels.add(0, new Level(largestAllocSize + adminBlockSize));
      }

      // Calculate the level threshold for the Explorer algorithm.
      threshold = (levels.size() - NUM_LEVELS) * levelsPercent / 100;
      threshold += NUM_LEVELS;

      thresholdLevels = new LinkedList();

      // Invoke the LevelOptimization algorithm that removes the cheapest
      // level each iteration until only NUM_LEVELS levels remain.
      while (levels.size() > NUM_LEVELS)
      {
         if ((thresholdLevels.size() == 0) && (levels.size() == threshold))
         {
            // Find a starting point for the exhaustive exploration
            // taken where there are threshold levels left.
            thresholdLevels = copyLevelList(levels);
         }
         eliminateCheapestLevel(levels);
         if (monitor.isCanceled())
         {
            throw new OperationCanceledException();
         }
      }

      // Invoke the Explorer algorithm if threshold levels is used.
      if (thresholdLevels.size() > NUM_LEVELS)
      {
         Explorer explorer = new Explorer(NUM_LEVELS);
         levels = explorer.explore(monitor, thresholdLevels, levels);
      }

      // Calculate the real result (reverse order and remove size of admin block).
      newBufferSizes = new int[NUM_LEVELS];
      for (int i = 0; i < NUM_LEVELS; i++)
      {
         Level level = (Level) levels.get(i);
         newBufferSizes[NUM_LEVELS - 1 - i] = level.size - adminBlockSize;
      }

      // Deliver the result in a BufferSet.
      result = new BufferSet(newBufferSizes);
      result.setUsedMemory(calcUsedMemory(levels));
      overheadMemory = allocSizes.length * adminBlockSize;
      availableMemory = result.getUsedMemory() - overheadMemory;
      slackMemory = availableMemory - requestedMemory;
      result.setSlackMemory(slackMemory);

      return result;
   }

   private static List eliminateCheapestLevel(List levels)
   {
      int index;
      Level prev;
      Level cur;

      index = findCheapestSlack(levels);
      prev = (Level) levels.get(index - 1);
      cur = (Level) levels.get(index);
      prev.join(cur);
      levels.remove(index);
      return levels;
   }

   /**
    * Return the index of the level that is cheapest to join.
    * The list must be sorted by size in descending order.
    */
   private static int findCheapestSlack(List levels)
   {
      int cheapestValue = Integer.MAX_VALUE;
      int cheapestIndex = -1;
      Iterator i;
      Level cur;

      i = levels.iterator();
      cur = (Level) i.next();

      while (i.hasNext())
      {
         Level prev;
         int value;

         prev = cur;
         cur = (Level) i.next();
         value = cur.slackCost(prev.size);
         if (value < cheapestValue)
         {
            cheapestValue = value;
            cheapestIndex = levels.indexOf(cur);
         }
      }

      return cheapestIndex;
   }

   private static List copyLevelList(List levels)
   {
      List list = new LinkedList();
      for (Iterator i = levels.iterator(); i.hasNext();)
      {
         list.add(((Level) i.next()).clone());
      }
      return list;
   }

   private static int calcUsedMemory(List levels)
   {
      int usedMemory = 0;

      for (Iterator i = levels.iterator(); i.hasNext();)
      {
         Level level = (Level) i.next();
         usedMemory += level.size * level.frequency;
      }

      return usedMemory;
   }

   /**
    * A Level is a candidate level for a buffer size to suggest.
    */
   private static class Level implements Cloneable
   {
      private final int size;

      private int frequency;

      public Level(int size)
      {
         this.size = size;
         this.frequency = 0;
      }

      private Level(int size, int frequency)
      {
         this.size = size;
         this.frequency = frequency;
      }

      /**
       * The cost of eliminating this level.
       */
      public int slackCost(int prevSize)
      {
         int diff = prevSize - size;
         return diff * frequency;
      }

      /**
       * Join a lower level with this level.
       */
      public void join(Level level)
      {
         frequency += level.frequency;
      }

      public String toString()
      {
         return "(" + size + ", " + frequency + ")";
      }

      protected Object clone()
      {
         return new Level(size, frequency);
      }
   }

   // FIXME: Add a description of the Explorer algorithm.
   private static class Explorer
   {
      private final int limit;

      private IProgressMonitor monitor;

      private LevelState bestReadyState;

      private int bestCost;

      // Tested solutions, only for debug.
      int solutionCount;

      public Explorer(int limit)
      {
         if (limit <= 0)
         {
            throw new IllegalArgumentException();
         }
         this.limit = limit;
      }

      public List explore(IProgressMonitor monitor,
                          List initLevels,
                          List suggestedLevels)
      {
         LevelState initState;

         if ((monitor == null) || (initLevels == null) ||
             (initLevels.size() == 0) || (suggestedLevels == null) ||
             (suggestedLevels.size() == 0))
         {
            throw new IllegalArgumentException();
         }

         this.monitor = monitor;

         // Only highest chosen.
         initState = new LevelState(initLevels, 1);

         // All chosen will be pointed elsewhere if a better solution is found.
         bestReadyState = new LevelState(suggestedLevels, limit);

         bestCost = bestReadyState.getCost();
         nextIteration(initState);
         return bestReadyState.getLevels();
      }

      private void nextIteration(LevelState levelState)
      {
         List successors = levelState.successors();

         for (Iterator i = successors.iterator(); i.hasNext();)
         {
            LevelState cur = (LevelState) i.next();

            if (monitor.isCanceled())
            {
               throw new OperationCanceledException();
            }

            if (cur.getLevels().size() <= limit)
            {
               // Catch best if done.
               if (cur.getCost() < bestCost)
               {
                  bestReadyState = cur;
                  bestCost = bestReadyState.getCost();
               }
               solutionCount++;
            }
            else
            {
               // Least left.
               if ((cur.getCost() + cur.getLeastLeft()) < bestCost)
               {
                  // Not really depth first because of
                  // the way successors are created.
                  nextIteration(cur);
               }
            }
         }
      }

      private class LevelState
      {
         private final List levels;

         private int choices; // Selections done

         public LevelState(List l, int c)
         {
            levels = l;
            choices = c;
            // Optimizations of the algorithm.
            if (c == limit)
            {
               // All buffer sizes chosen remove the rest.
               removeRest();
            }
            else if (levels.size() == limit)
            {
               // Automatically choose the rest when there is no option.
               choices = limit;
            }
         }

         public List getLevels()
         {
            return levels;
         }

         public int getCost()
         {
            return calcUsedMemory(levels);
         }

         /**
          * Calculate the minimum cost that must (at least)
          * be added to all successors.
          */
         public int getLeastLeft()
         {
            int result = 0;
            int[] costs;

            costs = new int[levels.size() - choices];
            for (int i = choices; i < levels.size() - 1; i++)
            {
               Level prev = (Level) levels.get(i);
               Level cur = (Level) levels.get(i + 1);
               costs[i - choices] = cur.slackCost(prev.size);
            }
            Arrays.sort(costs);

            for (int i = 0; i < costs.length - (limit - choices); i++)
            {
               result += costs[i];
            }
            return result;
         }

         public List successors()
         {
            List list = new ArrayList();
            // Each level is a candidate for removal, level 0 cannot be removed.
            for (int i = choices; i <= limit; i++)
            {
               list.add(new LevelState(levelsWithIndexRemoved(i), i));
            }
            return list;
         }

         private void removeRest()
         {
            while (levels.size() > limit)
            {
               eliminateIndex(levels, limit);
            }
         }

         private List levelsWithIndexRemoved(int index)
         {
            List newLevels = (List) ((LinkedList) levels).clone();
            eliminateIndex(newLevels, index);
            return newLevels;
         }

         private void eliminateIndex(List levels, int index)
         {
            // Do not touch existing objects to not mess up other lists.
            Level prevClone = (Level) ((Level) levels.get(index - 1)).clone();
            Level cur = (Level) levels.get(index);
            prevClone.join(cur);
            levels.remove(index);
            levels.set(index - 1, prevClone);
         }
      }
   }
}
