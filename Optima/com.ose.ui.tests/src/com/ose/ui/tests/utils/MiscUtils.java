/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.ui.tests.utils;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.eclipse.JobsCompleteCondition;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class MiscUtils
{
   public static void waitForJobs(IUIContext ui)
   {
      ui.wait(new JobsCompleteCondition(JobsCompleteCondition.DEFAULT_FLAGS));      
   }

   public static String listDataToString(LinkedList<TreeData> list)
   {
      String str = "";
      ListIterator<TreeData> itr = list.listIterator();

      while (itr.hasNext())
      {
         TreeData treeData = (TreeData) itr.next();
         str = str + treeData.getName();
         str = str + " ";
         str = str + treeData.getId();
         str = str + "::";
      }

      return str;
   }

   public static class EntityArraySorter implements Comparator<String[]>
   {
      int column;

      public EntityArraySorter(int column)
      {
         this.column = column;
      }

      public int compare(String[] array1, String[] array2)
      {
         return array1[column].compareTo(array2[column]);
      }
   }
}
