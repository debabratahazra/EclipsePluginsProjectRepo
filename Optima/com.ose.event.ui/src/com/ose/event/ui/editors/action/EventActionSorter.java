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
package com.ose.event.ui.editors.action;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import com.ose.xmleditor.validation.ValidationNode;

public class EventActionSorter extends ViewerSorter
{

   private int columnNo;

   private boolean isDescending;

   public EventActionSorter()
   {
      isDescending = true;
      columnNo = -1;
   }

   public int getDirection()
   {
      if (isDescending)
      {
         return SWT.DOWN;
      }
      return SWT.UP;
   }

   public void setSortColumn(int column)
   {
      if (this.columnNo == column)
      {
         isDescending = !isDescending;
      }
      else
      {
         this.columnNo = column;
         isDescending = true;
      }

   }

   public int compare(Viewer viewer, Object object1, Object object2)
   {
      if(columnNo == -1)
      {
         return 0;
      }
      if (object1 instanceof ValidationNode
            && object2 instanceof ValidationNode)
      {
         ValidationNode node1 = (ValidationNode) object1;
         ValidationNode node2 = (ValidationNode) object2;
         String firstAttribute = MasterLabelProvider.getElementText(columnNo,
               node1);
         String secondAttribute = MasterLabelProvider.getElementText(columnNo,
               node2);
         int result = firstAttribute.compareTo(secondAttribute);
         if (!isDescending)
         {
            result *= -1;
         }
         return result;
      }
      return 0;
   }
}
