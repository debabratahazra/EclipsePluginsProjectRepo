/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.sigdb.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class holds a table of symbolic signal information. It also has some
 * administration methods.
 */
public class SignalDescriptionTable
{
   /**
    * Comparator for signal descriptions. The comparator compares the signal
    * numbers.
    */
   private class DescriptionComparator implements Comparator
   {
      public final int sortOrder;

      /**
       * 
       * @param sortOrder
       *           the sort order
       * @see #SORT_UNSORTED
       * @see #SORT_ASCENDING
       * @see #SORT_DESCENDING
       */
      public DescriptionComparator(int sortOrder)
      {
         this.sortOrder = sortOrder;
      }

      public int compare(Object o1, Object o2)
      {
         int result = 0;

         SignalDescription signal1 = (SignalDescription) o1;
         SignalDescription signal2 = (SignalDescription) o2;

         if (signal1.getSigNoLong() < signal2.getSigNoLong())
         {
            result = -1;
         }
         else if (signal1.getSigNoLong() > signal2.getSigNoLong())
         {
            result = 1;
         }

         return result * sortOrder;
      }
   }

   /**
    * Constant for undefined order.
    */
   public static final int SORT_UNSORTED = 0;

   /**
    * Constant for ascending sort order;
    */
   public static final int SORT_ASCENDING = 1;

   /**
    * Constant for descending sort order
    */
   public static final int SORT_DESCENDING = -1;

   private Map descriptions;

   private String name;

   public SignalDescriptionTable()
   {
      descriptions = new HashMap(547, 0.75f);
      setName("Unnamed signal description table");
   }

   /**
    * @param description
    */
   public final void addDescription(SignalDescription description)
   {
      if (descriptions != null)
      {
         descriptions.put(new Integer(description.getSigNo()), description);
      }
   }

   /**
    * Look for signals in the table with names starting with <name>
    * 
    * @param name
    * @return The matching signal names. An empty List will be returned if no
    *         matches found.
    */
   public List complete(String name)
   {
      List result = new ArrayList();

      if (descriptions != null)
      {
         String cs = name.trim();

         if (cs.length() > 0)
         {
            Collection values = descriptions.values();
            Iterator iterator = values.iterator();

            while (iterator.hasNext())
            {
               SignalDescription signal = (SignalDescription) iterator.next();

               if (signal.getName().startsWith(cs))
               {
                  result.add(signal.getName());
               }
            }
         }
         else
         {
            Collection values = descriptions.values();
            Iterator iterator = values.iterator();

            while (iterator.hasNext())
            {
               SignalDescription signal = (SignalDescription) iterator.next();
               result.add(signal.getName());
            }
         }
      }

      return result;
   }

   /**
    * Get a signal description with signal number signalNumber
    * 
    * @param signalNumber
    * @return
    */
   public SignalDescription getDescription(int signalNumber)
   {
      if (descriptions != null)
      {
         return (SignalDescription) descriptions.get(new Integer(signalNumber));
      }
      else
      {
         return null;
      }
   }

   /**
    * @param signalName
    *           the name to look for
    * @return the first signal that matches the name criteria
    */
   public SignalDescription getDescription(String signalName)
   {
      SignalDescription result = null;

      if (descriptions != null)
      {
         Iterator iterator = descriptions.values().iterator();
         while (iterator.hasNext())
         {
            SignalDescription signal = (SignalDescription) iterator.next();
            if (signal.getName().equals(signalName))
            {
               result = signal;
               break;
            }
         }
      }
      return result;
   }

   /**
    * Get the list of signals from this table.
    * 
    * @param sortOrder
    * @see #SORT_UNSORTED
    * @see #SORT_ASCENDING
    * @see #SORT_DESCENDING
    * @return list sorted on signal number
    */
   public List getDescriptions(int sortOrder)
   {
      List signalList = new ArrayList();

      signalList.addAll(descriptions.values());
      Object[] objects = signalList.toArray();
      Arrays.sort(objects, new DescriptionComparator(sortOrder));
      List sortedList = Arrays.asList(objects);

      return sortedList;
   }

   /**
    * Get the name of this SignalDescriptionTable
    * 
    * @return name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Sets the name of this signal table. Normaly the name should relate to the
    * source of this signal table.
    * 
    * @param name
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * Get a string representation of this SignalDescriptionTable
    */
   public String toString()
   {
      return getName();
   }
}
