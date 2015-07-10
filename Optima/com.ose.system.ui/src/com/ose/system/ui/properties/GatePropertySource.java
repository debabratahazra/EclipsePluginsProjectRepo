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
import com.ose.system.Gate;
import com.ose.system.ui.util.StringUtils;

public class GatePropertySource implements IPropertySource
{
   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_NAME = "NAME";
   public static final String P_NAME_NAME = "Name:";

   public static final String P_ID_ADDRESS = "ADDRESS";
   public static final String P_NAME_ADDRESS = "Address:";

   public static final String P_ID_PORT = "PORT";
   public static final String P_NAME_PORT = "Port:";

   private static final IPropertyDescriptor[] propertyDescriptors =
      new IPropertyDescriptor[4];

   private Gate gate;

   public GatePropertySource(Gate gate)
   {
      PropertyDescriptor propDescriptor;

      if (gate == null)
      {
         throw new NullPointerException();
      }
      this.gate = gate;

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propertyDescriptors[0] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NAME, P_NAME_NAME);
      propertyDescriptors[1] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_ADDRESS, P_NAME_ADDRESS);
      propertyDescriptors[2] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_PORT, P_NAME_PORT);
      propertyDescriptors[3] = propDescriptor;
   }

   public Object getEditableValue()
   {
      return gate;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(gate.isKilled());
      }
      else if (id.equals(P_ID_NAME))
      {
         return gate.getName();
      }
      else if (id.equals(P_ID_ADDRESS))
      {
         return gate.getAddress().getHostAddress();
      }
      else if (id.equals(P_ID_PORT))
      {
         return Integer.toString(gate.getPort());
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
