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

package com.ose.prof.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.ose.system.CPUBlockReport;
import com.ose.system.CPUBlockReport.CPUBlockLoad;
import com.ose.system.CPUPriorityReport;
import com.ose.system.CPUProcessReport;
import com.ose.system.CPUProcessReport.CPUProcessLoad;
import com.ose.system.Target;
import com.ose.system.TargetReport;

/**
 * This utility class provides support for computing the contribution of each
 * entity (process/block/priority) over an interval of profiling reports.
 * <p>
 * In addition, a file containing an entity name/ID to entity group mapping can
 * be provided for grouping the entities and computing the total contribution
 * from all entities in each group.
 * <p>
 * The Java property file format is used as the file format for the entity
 * grouping files.
 */
public class EntityLoadComputator
{
   private static final String OTHERS_GROUP = "Others";

   private static final String[] PRIORITIES = new String[34];

   private List<? extends TargetReport> reports;

   private List<Integer> idList;

   private Map<Integer, String> idNameMap;

   private int numExecutionUnits;

   private short executionUnit;

   static
   {
      PRIORITIES[0] = "Interrupt";
      for (int i = 1; i < 33; i++)
      {
         PRIORITIES[i] = Integer.toString(i - 1);
      }
      PRIORITIES[33] = "Background";
   }

   /**
    * This method computes the contribution of each process over an interval of
    * CPU process level load reports.
    *
    * @param reports            the interval of CPU process level load reports.
    * @param idNameMap          an optional mapping between process IDs and
    * process names or null if not used.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param executionUnit      the execution unit from where the reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    * @return an array of process entities containing the contribution of each
    * process over the given interval of reports.
    */
   public Entity[] computeCPUProcessEntityLoad(List<CPUProcessReport> reports,
                                               Map<Integer, String> idNameMap,
                                               int numExecutionUnits,
                                               short executionUnit)
   {
      return computeEntityLoad(reports, idNameMap, numExecutionUnits,
            executionUnit);
   }

   /**
    * This method computes the contribution of each block over an interval of
    * CPU block level load reports.
    *
    * @param reports            the interval of CPU block level load reports.
    * @param idNameMap          an optional mapping between block IDs and block
    * names or null if not used.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param executionUnit      the execution unit from where the reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    * @return an array of block entities containing the contribution of each
    * block over the given interval of reports.
    */
   public Entity[] computeCPUBlockEntityLoad(List<CPUBlockReport> reports,
                                             Map<Integer, String> idNameMap,
                                             int numExecutionUnits,
                                             short executionUnit)
   {
      return computeEntityLoad(reports, idNameMap, numExecutionUnits,
            executionUnit);
   }

   /**
    * This method computes the contribution of each priority level over an
    * interval of CPU priority level load reports.
    *
    * @param reports            the interval of CPU priority level load reports.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param executionUnit      the execution unit from where the reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    * @return an array of priority level entities containing the contribution of
    * each priority level over the given interval of reports.
    */
   public Entity[] computeCPUPriorityEntityLoad(List<CPUPriorityReport> reports,
                                                int numExecutionUnits,
                                                short executionUnit)
   {
      return computeEntityLoad(reports, null, numExecutionUnits,
            executionUnit);
   }

   /**
    * This method computes the contribution of each process over an interval of
    * CPU process level load reports and groups the result with respect to the
    * given process grouping property file.
    *
    * @param reports            the interval of CPU process level load reports.
    * @param idNameMap          an optional mapping between process IDs and
    * process names or null if not used.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param executionUnit      the execution unit from where the reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    * @param entityGroupingProperties  the property file containing the process
    * entity name/ID to process entity group mapping.
    * @return an array of process entity groups containing the contribution of
    * each process over the given interval of reports arranged per process
    * entity group.
    */
   public EntityGroup[] computeCPUProcessEntityGroupLoad(
         List<CPUProcessReport> reports,
         Map<Integer, String> idNameMap,
         int numExecutionUnits,
         short executionUnit,
         Properties entityGroupingProperties)
   {
      return computeEntityGroupLoad(reports, idNameMap, numExecutionUnits,
            executionUnit, entityGroupingProperties);
   }

   /**
    * This method computes the contribution of each block over an interval of
    * CPU block level load reports and groups the result with respect to the
    * given block grouping property file.
    *
    * @param reports            the interval of CPU block level load reports.
    * @param idNameMap          an optional mapping between block IDs and block
    * names or null if not used.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param executionUnit      the execution unit from where the reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    * @param entityGroupingProperties  the property file containing the block
    * entity name/ID to block entity group mapping.
    * @return an array of block entity groups containing the contribution of
    * each block over the given interval of reports arranged per block entity
    * group.
    */
   public EntityGroup[] computeCPUBlockEntityGroupLoad(
         List<CPUBlockReport> reports,
         Map<Integer, String> idNameMap,
         int numExecutionUnits,
         short executionUnit,
         Properties entityGroupingProperties)
   {
      return computeEntityGroupLoad(reports, idNameMap, numExecutionUnits,
            executionUnit, entityGroupingProperties);
   }

   /**
    * This method computes the contribution of each priority level over an
    * interval of CPU priority level load reports and groups the result with
    * respect to the given priority level grouping property file.
    *
    * @param reports            the interval of CPU priority level load reports.
    * @param numExecutionUnits  the number of execution units for the target.
    * @param executionUnit      the execution unit from where the reports were
    * retrieved from or Target.ALL_EXECUTION_UNITS.
    * @param entityGroupingProperties  the property file containing the priority
    * level entity to priority level entity group mapping.
    * @return an array of priority level entity groups containing the
    * contribution of each priority level over the given interval of reports
    * arranged per priority level entity group.
    */
   public EntityGroup[] computeCPUPriorityEntityGroupLoad(
         List<CPUPriorityReport> reports,
         int numExecutionUnits,
         short executionUnit,
         Properties entityGroupingProperties)
   {
      return computeEntityGroupLoad(reports, null, numExecutionUnits,
            executionUnit, entityGroupingProperties);
   }

   private synchronized Entity[] computeEntityLoad(
         List<? extends TargetReport> reports,
         Map<Integer, String> idNameMap,
         int numExecutionUnits,
         short executionUnit)
   {
      if (reports == null)
      {
         throw new IllegalArgumentException();
      }

      if (reports.isEmpty())
      {
         return new Entity[0];
      }

      this.reports = reports;
      this.idList = createIdList(reports);
      this.idNameMap = idNameMap;
      this.numExecutionUnits = numExecutionUnits;
      this.executionUnit = executionUnit;

      List<Entity> entities = new ArrayList<Entity>();
      int seriesCount = (reports.get(0) instanceof CPUPriorityReport) ?
            PRIORITIES.length : idList.size() + 1;
      int itemCount = reports.size();

      for (int i = 0; i < seriesCount; i++)
      {
         double sum = 0;

         for (int j = 0; j < itemCount; j++)
         {
            double value = getEntityValue(i, j);
            if (!Double.isNaN(value))
            {
               sum += value;
            }
         }

         sum = (itemCount > 0) ? (sum / itemCount) : 0;
         String entityName = getEntityName(i);
         if (entityName != null)
         {
            Entity entity = new Entity(entityName, sum);
            entities.add(entity);
         }
      }

      return entities.toArray(new Entity[entities.size()]);
   }

   private EntityGroup[] computeEntityGroupLoad(
         List<? extends TargetReport> reports,
         Map<Integer, String> idNameMap,
         int numExecutionUnits,
         short executionUnit,
         Properties entityGroupingProperties)
   {
      Entity[] entities = computeEntityLoad(reports, idNameMap,
            numExecutionUnits, executionUnit);
      return performEntityGrouping(entities, entityGroupingProperties);
   }

   private static EntityGroup[] performEntityGrouping(
         Entity[] entities,
         Properties entityGroupingProperties)
   {
      if (entityGroupingProperties == null)
      {
         throw new IllegalArgumentException();
      }

      Map<String, EntityGroup> entityGroupMap = new HashMap<String, EntityGroup>();
      EntityGroup entityGroup = new EntityGroup(OTHERS_GROUP);
      entityGroupMap.put(OTHERS_GROUP, entityGroup);

      for (Entity entity : entities)
      {
         // The entity name is of the form "<entity-name> (<entity-id>)" or
         // just "<entity-id>".

         String[] entityNames = entity.getName().split(" ");
         String entityName = (entityNames.length > 1) ? entityNames[0] : null;
         String entityId = (entityNames.length > 1) ?
            entityNames[1].replace("(", "").replace(")", "") : entityNames[0];
         String groupName;

         if (entityName != null)
         {
            groupName = entityGroupingProperties.getProperty(entityName);
            if (groupName == null)
            {
               groupName = entityGroupingProperties.getProperty(entityId);
            }
         }
         else
         {
            groupName = entityGroupingProperties.getProperty(entityId);
         }

         if (groupName != null)
         {
            entityGroup = entityGroupMap.get(groupName);
            if (entityGroup == null)
            {
               entityGroup = new EntityGroup(groupName);
               entityGroupMap.put(groupName, entityGroup);
            }
         }
         else
         {
            entityGroup = entityGroupMap.get(OTHERS_GROUP);
         }

         entityGroup.addEntity(entity);
      }

      return entityGroupMap.values().toArray(new EntityGroup[0]);
   }

   private String getEntityName(int series)
   {
      String entityName = null;

      if (reports.get(0) instanceof CPUPriorityReport)
      {
         if (series < PRIORITIES.length)
         {
            entityName = PRIORITIES[series];
         }
      }
      else
      {
         if (series == 0)
         {
            entityName = "Others";
         }
         else
         {
            series--;

            if (series < idList.size())
            {
               int id = idList.get(series);
               if (idNameMap != null)
               {
                  entityName = idNameMap.get(id);
                  if (entityName != null)
                  {
                     entityName = entityName + " (" + toHexString(id) + ")";
                  }
                  else
                  {
                     entityName = toHexString(id);
                  }
               }
               else
               {
                  entityName = toHexString(id);
               }
            }
         }
      }

      return entityName;
   }

   private double getEntityValue(int series, int item)
   {
      TargetReport report = reports.get(0);

      if (report instanceof CPUProcessReport)
      {
         return getCPUProcessEntityValue(series, item);
      }
      else if (report instanceof CPUBlockReport)
      {
         return getCPUBlockEntityValue(series, item);
      }
      else if (report instanceof CPUPriorityReport)
      {
         return getCPUPriorityEntityValue(series, item);
      }
      else
      {
         return Double.NaN;
      }
   }

   private double getCPUProcessEntityValue(int series, int item)
   {
      if (item < reports.size())
      {
         CPUProcessReport report = (CPUProcessReport) reports.get(item);
         int interval = report.getInterval();

         if (series == 0)
         {
            return toRelativeDouble(report.getSumOther(), interval,
                  numExecutionUnits, executionUnit);
         }
         else
         {
            series--;

            if (series < idList.size())
            {
               int id = idList.get(series).intValue();
               CPUProcessLoad[] sumProcesses = report.getSumProcesses();
               for (int i = 0; i < sumProcesses.length; i++)
               {
                  CPUProcessLoad value = sumProcesses[i];
                  if (value.getId() == id)
                  {
                     return toRelativeDouble(value.getSum(), interval,
                           numExecutionUnits, executionUnit);
                  }
               }
            }
         }
      }

      return Double.NaN;
   }

   private double getCPUBlockEntityValue(int series, int item)
   {
      if (item < reports.size())
      {
         CPUBlockReport report = (CPUBlockReport) reports.get(item);
         int interval = report.getInterval();

         if (series == 0)
         {
            return toRelativeDouble(report.getSumOther(), interval,
                  numExecutionUnits, executionUnit);
         }
         else
         {
            series--;

            if (series < idList.size())
            {
               int id = idList.get(series).intValue();
               CPUBlockLoad[] sumBlocks = report.getSumBlocks();
               for (int i = 0; i < sumBlocks.length; i++)
               {
                  CPUBlockLoad value = sumBlocks[i];
                  if (value.getId() == id)
                  {
                     return toRelativeDouble(value.getSum(), interval,
                           numExecutionUnits, executionUnit);
                  }
               }
            }
         }
      }

      return Double.NaN;
   }

   private double getCPUPriorityEntityValue(int series, int item)
   {
      if ((series < PRIORITIES.length) && (item < reports.size()))
      {
         CPUPriorityReport report = (CPUPriorityReport) reports.get(item);
         int interval = report.getInterval();
         int sum;

         switch (series)
         {
            case 0:
               sum = report.getSumInterrupt();
               return toRelativeDouble(sum, interval, numExecutionUnits,
                     executionUnit);
            case 33:
               sum = report.getSumBackground();
               return toRelativeDouble(sum, interval, numExecutionUnits,
                     executionUnit);
            default:
               int[] sumPrioritized = report.getSumPrioritized();
               if ((series - 1) < sumPrioritized.length)
               {
                  sum = sumPrioritized[series - 1];
                  return toRelativeDouble(sum, interval, numExecutionUnits,
                        executionUnit);
               }
         }
      }

      return Double.NaN;
   }

   private static List<Integer> createIdList(List<? extends TargetReport> reports)
   {
      TargetReport report = reports.get(0);

      if (report instanceof CPUProcessReport)
      {
         return createPidList(reports);
      }
      else if (report instanceof CPUBlockReport)
      {
         return createBidList(reports);
      }
      else
      {
         return null;
      }
   }

   private static List<Integer> createPidList(List<? extends TargetReport> reports)
   {
      List<Integer> pidList = new ArrayList<Integer>();
      Map pidMap = new HashMap();

      for (TargetReport targetReport : reports)
      {
         CPUProcessReport report = (CPUProcessReport) targetReport;
         CPUProcessLoad[] sumProcesses = report.getSumProcesses();

         for (int i = 0; i < sumProcesses.length; i++)
         {
            Integer pid = new Integer(sumProcesses[i].getId());
            if (pidMap.get(pid) == null)
            {
               pidMap.put(pid, Boolean.TRUE);
               pidList.add(pid);
            }
         }
      }

      return pidList;
   }

   private static List<Integer> createBidList(List<? extends TargetReport> reports)
   {
      List<Integer> bidList = new ArrayList<Integer>();
      Map bidMap = new HashMap();

      for (TargetReport targetReport : reports)
      {
         CPUBlockReport report = (CPUBlockReport) targetReport;
         CPUBlockLoad[] sumBlocks = report.getSumBlocks();

         for (int i = 0; i < sumBlocks.length; i++)
         {
            Integer bid = new Integer(sumBlocks[i].getId());
            if (bidMap.get(bid) == null)
            {
               bidMap.put(bid, Boolean.TRUE);
               bidList.add(bid);
            }
         }
      }

      return bidList;
   }

   private static double toRelativeDouble(int sum,
                                          int interval,
                                          int numExecutionUnits,
                                          short executionUnit)
   {
      if ((numExecutionUnits > 1) && (executionUnit == Target.ALL_EXECUTION_UNITS))
      {
         return (100.0 * sum) / (interval * numExecutionUnits);
      }
      else
      {
         return (100.0 * sum) / interval;
      }
   }

   private static String toHexString(int i)
   {
      return "0x" + Integer.toHexString(i).toUpperCase();
   }

   /**
    * This class represents a profiled entity (process/block/priority).
    */
   public static class Entity
   {
      private final String name;

      private final double load;

      Entity(String name, double load)
      {
         this.name = name;
         this.load = load;
      }

      /**
       * Return the name of this entity.
       *
       * @return the name of this entity.
       */
      public String getName()
      {
         return name;
      }

      /**
       * Return the load of this entity.
       *
       * @return the load of this entity.
       */
      public double getLoad()
      {
         return load;
      }
   }

   /**
    * This class represents a group of profiled entities
    * (process/block/priority).
    */
   public static class EntityGroup
   {
      private final String name;

      private final List<Entity> entities = new ArrayList<Entity>();

      EntityGroup(String name)
      {
         this.name = name;
      }

      void addEntity(Entity entity)
      {
         if (entity != null)
         {
            entities.add(entity);
         }
      }

      /**
       * Return the name of this entity group.
       *
       * @return the name of this entity group.
       */
      public String getName()
      {
         return name;
      }

      /**
       * Return the total load of this entity group.
       *
       * @return the total load of this entity group.
       */
      public double getLoad()
      {
         double load = 0;
         for (Entity entity : entities)
         {
            load += entity.getLoad();
         }
         return load;
      }

      /**
       * Return the entities of this entity group.
       *
       * @return the entities of this entity group.
       */
      public Entity[] getEntities()
      {
         return entities.toArray(new Entity[entities.size()]);
      }
   }
}
