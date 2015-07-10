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

package com.ose.system.ui.views.loadmodule;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.LoadModuleInfo;
import com.ose.system.ui.util.StringUtils;

public class LoadModuleSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   LoadModuleSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = 0;
      direction = ASCENDING;
   }

   public void sortByColumn(int column)
   {
      if (this.column == column)
      {
         // Same column, toggle sort direction.
         direction *= DESCENDING;
      }
      else
      {
         // New column, reset sort direction.
         this.column = column;
         direction = ASCENDING;
      }
   }

   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof LoadModuleInfo) && !(e2 instanceof LoadModuleInfo))
      {
         return 0;
      }

      LoadModuleInfo lm1 = (LoadModuleInfo) e1;
      LoadModuleInfo lm2 = (LoadModuleInfo) e2;

      switch (column)
      {
         case LoadModuleView.COLUMN_INSTALL_HANDLE:
            return compareStrings(lm1.getInstallHandle(), lm2.getInstallHandle());
         case LoadModuleView.COLUMN_ENTRYPOINT:
            return compareLongs(lm1.getEntrypointLong(), lm2.getEntrypointLong());
         case LoadModuleView.COLUMN_OPTIONS:
            return compareInts(lm1.getOptions(), lm2.getOptions());
         case LoadModuleView.COLUMN_TEXT_BASE:
            return compareLongs(lm1.getTextBaseLong(), lm2.getTextBaseLong());
         case LoadModuleView.COLUMN_TEXT_SIZE:
            return compareLongs(lm1.getTextSizeLong(), lm2.getTextSizeLong());
         case LoadModuleView.COLUMN_DATA_BASE:
            return compareLongs(lm1.getDataBaseLong(), lm2.getDataBaseLong());
         case LoadModuleView.COLUMN_DATA_SIZE:
            return compareLongs(lm1.getDataSizeLong(), lm2.getDataSizeLong());
         case LoadModuleView.COLUMN_NUM_SECTIONS:
            return compareInts(lm1.getNumSections(), lm2.getNumSections());
         case LoadModuleView.COLUMN_NUM_INSTANCES:
            return compareInts(lm1.getNumInstances(), lm2.getNumInstances());
         case LoadModuleView.COLUMN_FILE_FORMAT:
            return compareStrings(lm1.getFileFormat(), lm2.getFileFormat());
         case LoadModuleView.COLUMN_FILE_NAME:
            return compareStrings(
                  StringUtils.removeNetworkPathPrefix(lm1.getFileName()),
                  StringUtils.removeNetworkPathPrefix(lm2.getFileName()));
         default:
            return 0;
      }
   }

   private int compareLongs(Long l1, Long l2)
   {
      int result = l1.compareTo(l2);
      int returnValue = applyDirection(result);
      return returnValue;
   }

   private int compareStrings(String s1, String s2)
   {
      int result = s1.compareTo(s2);
      int returnValue = applyDirection(result);
      return returnValue;
   }

   private int compareInts(int i1, int i2)
   {
      int result = (i1 < i2) ? -1 : ((i1 > i2) ? 1 : 0);
      result = applyDirection(result);
      return result;
   }
   
   private int applyDirection(int result)
   {
      int returnValue = result;
      if (direction == DESCENDING)
      {
         returnValue = result * DESCENDING;
      }
      return returnValue;
   }
}
