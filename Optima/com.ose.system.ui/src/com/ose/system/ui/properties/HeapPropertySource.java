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

package com.ose.system.ui.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import com.ose.system.Heap;
import com.ose.system.ui.util.StringUtils;

public class HeapPropertySource implements IPropertySource
{
   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_HID = "HID";
   public static final String P_NAME_HID = "Heap ID:";

   public static final String P_ID_OWNER = "OWNER";
   public static final String P_NAME_OWNER = "Owner ID:";

   public static final String P_ID_SID = "SID";
   public static final String P_NAME_SID = "Segment ID:";

   public static final String P_ID_SIZE = "SIZE";
   public static final String P_NAME_SIZE = "Heap Total Size:";

   public static final String P_ID_FREE_SIZE = "FREE_SIZE";
   public static final String P_NAME_FREE_SIZE = "Heap Free Size:";

   public static final String P_ID_USED_SIZE = "USED_SIZE";
   public static final String P_NAME_USED_SIZE = "Heap Used Size:";

   public static final String P_ID_PEAK_USED_SIZE = "PEAK_USED_SIZE";
   public static final String P_NAME_PEAK_USED_SIZE = "Heap Peak Used Size:";

   public static final String P_ID_REQUESTED_SIZE = "REQUESTED_SIZE";
   public static final String P_NAME_REQUESTED_SIZE = "Heap Requested Size:";

   public static final String P_ID_LARGEST_FREE = "LARGEST_FREE";
   public static final String P_NAME_LARGEST_FREE = "Largest Free Heap Buffer:";

   public static final String P_ID_LARGE_THRESHOLD = "LARGE_THRESHOLD";
   public static final String P_NAME_LARGE_THRESHOLD = "Large Threshold:";

   public static final String P_ID_PRIVATE = "PRIVATE";
   public static final String P_NAME_PRIVATE = "Private Heap Buffers:";

   public static final String P_ID_SHARED = "SHARED";
   public static final String P_NAME_SHARED = "Shared Heap Buffers:";

   public static final String P_ID_MALLOC_FAILED = "MALLOC_FAILED";
   public static final String P_NAME_MALLOC_FAILED = "Failed Mallocs:";

   public static final String P_ID_NUM_HEAP_FRAGMENTS = "NUM_HEAP_FRAGMENTS";
   public static final String P_NAME_NUM_HEAP_FRAGMENTS = "Heap Fragments:";

   private static final IPropertyDescriptor[] propertyDescriptors =
      new IPropertyDescriptor[15];

   private Heap heap;

   public HeapPropertySource(Heap heap)
   {
      PropertyDescriptor propDescriptor;

      if (heap == null)
      {
         throw new NullPointerException();
      }
      this.heap = heap;

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propertyDescriptors[0] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_HID, P_NAME_HID);
      propertyDescriptors[1] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_OWNER, P_NAME_OWNER);
      propertyDescriptors[2] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SID, P_NAME_SID);
      propertyDescriptors[3] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SIZE, P_NAME_SIZE);
      propertyDescriptors[4] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_FREE_SIZE, P_NAME_FREE_SIZE);
      propertyDescriptors[5] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_USED_SIZE, P_NAME_USED_SIZE);
      propertyDescriptors[6] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_PEAK_USED_SIZE,
         P_NAME_PEAK_USED_SIZE);
      propertyDescriptors[7] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_REQUESTED_SIZE,
         P_NAME_REQUESTED_SIZE);
      propertyDescriptors[8] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_LARGEST_FREE,
         P_NAME_LARGEST_FREE);
      propertyDescriptors[9] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_LARGE_THRESHOLD,
         P_NAME_LARGE_THRESHOLD);
      propertyDescriptors[10] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_PRIVATE, P_NAME_PRIVATE);
      propertyDescriptors[11] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SHARED, P_NAME_SHARED);
      propertyDescriptors[12] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_MALLOC_FAILED,
         P_NAME_MALLOC_FAILED);
      propertyDescriptors[13] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NUM_HEAP_FRAGMENTS,
         P_NAME_NUM_HEAP_FRAGMENTS);
      propertyDescriptors[14] = propDescriptor;
   }

   public Object getEditableValue()
   {
      return heap;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(heap.isKilled());
      }
      else if (id.equals(P_ID_HID))
      {
         return StringUtils.toHexString(heap.getId());
      }
      else if (id.equals(P_ID_OWNER))
      {
         return StringUtils.toHexString(heap.getOwner());
      }
      else if (id.equals(P_ID_SID))
      {
         return StringUtils.toHexString(heap.getSid());
      }
      else if (id.equals(P_ID_SIZE))
      {
         return Long.toString(heap.getSize());
      }
      else if (id.equals(P_ID_FREE_SIZE))
      {
         return Long.toString(heap.getFreeSize()) + " (" +
            StringUtils.toPercentString(heap.getFreeSize(), heap.getSize()) + ")";
      }
      else if (id.equals(P_ID_USED_SIZE))
      {
         return Long.toString(heap.getUsedSize()) + " (" +
            StringUtils.toPercentString(heap.getUsedSize(), heap.getSize()) + ")";
      }
      else if (id.equals(P_ID_PEAK_USED_SIZE))
      {
         return Long.toString(heap.getPeakUsedSize()) + " (" +
            StringUtils.toPercentString(heap.getPeakUsedSize(), heap.getSize()) + ")";
      }
      else if (id.equals(P_ID_REQUESTED_SIZE))
      {
         return Long.toString(heap.getRequestedSize()) + " (" +
            StringUtils.toPercentString(heap.getRequestedSize(), heap.getSize()) + ")";
      }
      else if (id.equals(P_ID_LARGEST_FREE))
      {
         return Long.toString(heap.getLargestFree());
      }
      else if (id.equals(P_ID_LARGE_THRESHOLD))
      {
         return Long.toString(heap.getLargeThreshold());
      }
      else if (id.equals(P_ID_PRIVATE))
      {
         return Integer.toString(heap.getPrivate());
      }
      else if (id.equals(P_ID_SHARED))
      {
         return Integer.toString(heap.getShared());
      }
      else if (id.equals(P_ID_MALLOC_FAILED))
      {
         return Integer.toString(heap.getMallocFailed());
      }
      else if (id.equals(P_ID_NUM_HEAP_FRAGMENTS))
      {
         return Integer.toString(heap.getHeapFragments().length);
      }
      else
      {
         return null;
      }
   }

   public void setPropertyValue(Object id, Object value)
   {
      // Not supported.
   }

   public void resetPropertyValue(Object id)
   {
      // Not supported.
   }

   public boolean isPropertySet(Object id)
   {
      return false;
   }
}
