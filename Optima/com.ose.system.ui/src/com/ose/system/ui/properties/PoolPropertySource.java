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

package com.ose.system.ui.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import com.ose.system.Pool;
import com.ose.system.ui.util.StringUtils;

public class PoolPropertySource implements IPropertySource
{
   // TODO: Add sid when supported.

   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_PID = "PID";
   public static final String P_NAME_PID = "Pool ID:";

   public static final String P_ID_TOTAL_SIZE = "TOTAL_SIZE";
   public static final String P_NAME_TOTAL_SIZE = "Pool Total Size:";

   public static final String P_ID_FREE_SIZE = "FREE_SIZE";
   public static final String P_NAME_FREE_SIZE = "Pool Free Size:";

   public static final String P_ID_USED_SIZE = "USED_SIZE";
   public static final String P_NAME_USED_SIZE = "Pool Used Size:";

   public static final String P_ID_NUM_POOL_FRAGMENTS = "NUM_POOL_FRAGMENTS";
   public static final String P_NAME_NUM_POOL_FRAGMENTS = "Pool Fragments:";

   private static final IPropertyDescriptor[] propertyDescriptors =
      new IPropertyDescriptor[6];

   private Pool pool;

   public PoolPropertySource(Pool pool)
   {
      PropertyDescriptor propDescriptor;

      if (pool == null)
      {
         throw new NullPointerException();
      }
      this.pool = pool;

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propertyDescriptors[0] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_PID, P_NAME_PID);
      propertyDescriptors[1] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_TOTAL_SIZE, P_NAME_TOTAL_SIZE);
      propertyDescriptors[2] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_FREE_SIZE, P_NAME_FREE_SIZE);
      propertyDescriptors[3] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_USED_SIZE, P_NAME_USED_SIZE);
      propertyDescriptors[4] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NUM_POOL_FRAGMENTS,
            P_NAME_NUM_POOL_FRAGMENTS);
      propertyDescriptors[5] = propDescriptor;
   }

   public Object getEditableValue()
   {
      return pool;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(pool.isKilled());
      }
      else if (id.equals(P_ID_PID))
      {
         return StringUtils.toHexString(pool.getId());
      }
      else if (id.equals(P_ID_TOTAL_SIZE))
      {
         return Long.toString(pool.getTotalSize());
      }
      else if (id.equals(P_ID_FREE_SIZE))
      {
         return Long.toString(pool.getFreeSize()) + " (" +
               StringUtils.toPercentString(pool.getFreeSize(),
                     pool.getTotalSize()) + ")";
      }
      else if (id.equals(P_ID_USED_SIZE))
      {
         long usedSize = pool.getTotalSize() - pool.getFreeSize();
         return Long.toString(usedSize) + " (" +
               StringUtils.toPercentString(usedSize, pool.getTotalSize()) + ")";
      }
      else if (id.equals(P_ID_NUM_POOL_FRAGMENTS))
      {
         return Integer.toString(pool.getPoolFragments().length);
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
