/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.system.ui.views.system;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.system.Block;
import com.ose.system.Heap;
import com.ose.system.OseObject;
import com.ose.system.Pool;
import com.ose.system.Process;
import com.ose.system.Segment;
import com.ose.system.SystemModelNode;

class SystemSorter extends ViewerSorter
{
   public static final int CRITERIA_NAME = 1;
   public static final int CRITERIA_ID = 2;

   public static final int DIRECTION_ASCENDING = 1;
   public static final int DIRECTION_DESCENDING = -1;

   private int criteria = CRITERIA_NAME;
   private int direction = DIRECTION_ASCENDING;

   public void setCriteria(int criteria)
   {
      if ((criteria != CRITERIA_NAME) && (criteria != CRITERIA_ID))
      {
         throw new IllegalArgumentException();
      }
      this.criteria = criteria;
   }

   public void setDirection(int direction)
   {
      if ((direction != DIRECTION_ASCENDING) &&
          (direction != DIRECTION_DESCENDING))
      {
         throw new IllegalArgumentException();
      }
      this.direction = direction;
   }

   // This method is called per level in the tree, i.e. the tree
   // nodes to be compared are always from the same level in the tree.
   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if ((e1 instanceof OseObject) && (e2 instanceof OseObject))
      {
         OseObject obj1 = (OseObject) e1;
         OseObject obj2 = (OseObject) e2;

         if (obj1.getClass() == obj2.getClass())
         {
            if (criteria == CRITERIA_NAME)
            {
               return compareStrings(getName(obj1), getName(obj2));
            }
            else
            {
               return compareUnsignedInts(obj1.getId(), obj2.getId());
            }
         }
         else
         {
            return compareTypes(obj1, obj2);
         }
      }
      else if ((e1 instanceof SystemModelNode) && (e2 instanceof SystemModelNode))
      {
         if (criteria == CRITERIA_NAME)
         {
            return compareStrings(e1.toString(), e2.toString());
         }
         else
         {
            return 0;
         }
      }
      else
      {
         return 0;
      }
   }

   private int compareStrings(String s1, String s2)
   {
      int result = s1.compareToIgnoreCase(s2);
      if (direction == DIRECTION_DESCENDING)
      {
         result *= DIRECTION_DESCENDING;
      }
      return result;
   }

   private int compareUnsignedInts(int i1, int i2)
   {
      int result;
      long l1 = 0xFFFFFFFFL & i1;
      long l2 = 0xFFFFFFFFL & i2;

      result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DIRECTION_DESCENDING)
      {
         result *= DIRECTION_DESCENDING;
      }
      return result;
   }

   /*
    * Sort types in the following predefined order:
    * - Pool
    * - Heap
    * - Block
    * - Process
    */
   private int compareTypes(OseObject obj1, OseObject obj2)
   {
      if ((obj1 instanceof Pool) && !(obj2 instanceof Pool))
      {
         return -1;
      }
      else if ((obj1 instanceof Heap) && (obj2 instanceof Pool))
      {
         return 1;
      }
      else if ((obj1 instanceof Heap) && !(obj2 instanceof Heap))
      {
         return -1;
      }
      else if ((obj1 instanceof Block) && (obj2 instanceof Process))
      {
         return -1;
      }
      else if ((obj1 instanceof Block) && !(obj2 instanceof Block))
      {
         return 1;
      }
      else if ((obj1 instanceof Process) && !(obj2 instanceof Process))
      {
         return 1;
      }
      else
      {
         return 0;
      }
   }

   private static String getName(OseObject obj)
   {
      if (obj instanceof Segment)
      {
         return ((Segment) obj).getName();
      }
      else if (obj instanceof Block)
      {
         return ((Block) obj).getName();
      }
      else if (obj instanceof Process)
      {
         return ((Process) obj).getName();
      }
      else
      {
         return obj.toString();
      }
   }
}
