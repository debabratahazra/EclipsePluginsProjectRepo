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
import com.ose.system.Block;
import com.ose.system.ui.util.StringUtils;

public class BlockPropertySource implements IPropertySource
{
   // TODO: Add supervisor, numSignalsAttached, and errorHandler when supported.

   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_NAME = "NAME";
   public static final String P_NAME_NAME = "Name:";

   public static final String P_ID_BID = "BID";
   public static final String P_NAME_BID = "Block ID:";

   public static final String P_ID_SID = "SID";
   public static final String P_NAME_SID = "Segment ID:";

   public static final String P_ID_UID = "UID";
   public static final String P_NAME_UID = "User Number:";

   public static final String P_ID_MAX_SIGNAL_SIZE = "MAX_SIGNAL_SIZE";
   public static final String P_NAME_MAX_SIGNAL_SIZE = "Max Signal Size:";

   public static final String P_ID_SIGNAL_POOL_ID = "SIGNAL_POOL_ID";
   public static final String P_NAME_SIGNAL_POOL_ID = "Signal Pool ID:";

   public static final String P_ID_STACK_POOL_ID = "STACK_POOL_ID";
   public static final String P_NAME_STACK_POOL_ID = "Stack Pool ID:";

   public static final String P_ID_EXECUTION_UNIT = "EXECUTION_UNIT";
   public static final String P_NAME_EXECUTION_UNIT = "Execution Unit:";

   private static final IPropertyDescriptor[] propertyDescriptors =
      new IPropertyDescriptor[9];

   private Block block;

   public BlockPropertySource(Block block)
   {
      PropertyDescriptor propDescriptor;

      if (block == null)
      {
         throw new NullPointerException();
      }
      this.block = block;

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propertyDescriptors[0] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NAME, P_NAME_NAME);
      propertyDescriptors[1] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_BID, P_NAME_BID);
      propertyDescriptors[2] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SID, P_NAME_SID);
      propertyDescriptors[3] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_UID, P_NAME_UID);
      propertyDescriptors[4] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_MAX_SIGNAL_SIZE,
            P_NAME_MAX_SIGNAL_SIZE);
      propertyDescriptors[5] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SIGNAL_POOL_ID,
            P_NAME_SIGNAL_POOL_ID);
      propertyDescriptors[6] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_STACK_POOL_ID,
            P_NAME_STACK_POOL_ID);
      propertyDescriptors[7] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_EXECUTION_UNIT,
            P_NAME_EXECUTION_UNIT);
      propertyDescriptors[8] = propDescriptor;
   }

   public Object getEditableValue()
   {
      return block;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(block.isKilled());
      }
      else if (id.equals(P_ID_NAME))
      {
         return block.getName();
      }
      else if (id.equals(P_ID_BID))
      {
         return StringUtils.toHexString(block.getId());
      }
      else if (id.equals(P_ID_SID))
      {
         return StringUtils.toHexString(block.getSid());
      }
      else if (id.equals(P_ID_UID))
      {
         return Integer.toString(block.getUid());
      }
      else if (id.equals(P_ID_MAX_SIGNAL_SIZE))
      {
         return Integer.toString(block.getMaxSignalSize());
      }
      else if (id.equals(P_ID_SIGNAL_POOL_ID))
      {
         return StringUtils.toHexString(block.getSignalPoolId());
      }
      else if (id.equals(P_ID_STACK_POOL_ID))
      {
         return StringUtils.toHexString(block.getStackPoolId());
      }
      else if (id.equals(P_ID_EXECUTION_UNIT))
      {
         return Short.toString(block.getExecutionUnit());
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
