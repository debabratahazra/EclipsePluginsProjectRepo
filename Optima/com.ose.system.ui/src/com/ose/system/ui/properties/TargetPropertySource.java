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
import com.ose.system.Target;
import com.ose.system.ui.util.StringUtils;

public class TargetPropertySource implements IPropertySource
{
   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_HUNT_PATH = "HUNT_PATH";
   public static final String P_NAME_HUNT_PATH = "Hunt Path:";

   public static final String P_ID_NAME = "NAME";
   public static final String P_NAME_NAME = "Name:";

   public static final String P_ID_BIG_ENDIAN = "BIG_ENDIAN";
   public static final String P_NAME_BIG_ENDIAN = "Byte Order:";

   public static final String P_ID_OS_TYPE = "OS_TYPE";
   public static final String P_NAME_OS_TYPE = "OS:";

   public static final String P_ID_CPU_TYPE = "CPU_TYPE";
   public static final String P_NAME_CPU_TYPE = "CPU:";

   public static final String P_ID_EXECUTION_UNITS = "EXECUTION_UNITS";
   public static final String P_NAME_EXECUTION_UNITS = "Execution Units:";

   public static final String P_ID_TICK_LENGTH = "TICK_LENGTH";
   public static final String P_NAME_TICK_LENGTH = "Tick Length (\u00B5s):";

   public static final String P_ID_NTICK_FREQUENCY = "NTICK_FREQUENCY";
   public static final String P_NAME_NTICK_FREQUENCY = "Micro Tick Frequency (Hz):";

   private static final IPropertyDescriptor[] propertyDescriptors =
      new IPropertyDescriptor[9];

   private Target target;

   public TargetPropertySource(Target target)
   {
      PropertyDescriptor propDescriptor;

      if (target == null)
      {
         throw new NullPointerException();
      }
      this.target = target;

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propertyDescriptors[0] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_HUNT_PATH, P_NAME_HUNT_PATH);
      propertyDescriptors[1] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NAME, P_NAME_NAME);
      propertyDescriptors[2] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_BIG_ENDIAN, P_NAME_BIG_ENDIAN);
      propertyDescriptors[3] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_OS_TYPE, P_NAME_OS_TYPE);
      propertyDescriptors[4] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_CPU_TYPE, P_NAME_CPU_TYPE);
      propertyDescriptors[5] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_EXECUTION_UNITS, P_NAME_EXECUTION_UNITS);
      propertyDescriptors[6] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_TICK_LENGTH, P_NAME_TICK_LENGTH);
      propertyDescriptors[7] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NTICK_FREQUENCY, P_NAME_NTICK_FREQUENCY);
      propertyDescriptors[8] = propDescriptor;
   }

   public Object getEditableValue()
   {
      return target;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(target.isKilled());
      }
      else if (id.equals(P_ID_HUNT_PATH))
      {
         return target.getHuntPath();
      }
      else if (id.equals(P_ID_NAME))
      {
         return target.getName();
      }
      else if (id.equals(P_ID_BIG_ENDIAN))
      {
         return StringUtils.toEndianString(target.isBigEndian());
      }
      else if (id.equals(P_ID_OS_TYPE))
      {
         return StringUtils.toOsTypeString(target.getOsType());
      }
      else if (id.equals(P_ID_CPU_TYPE))
      {
         return StringUtils.toCpuTypeString(target.getCpuType());
      }
      else if (id.equals(P_ID_EXECUTION_UNITS))
      {
         return Short.toString(target.getNumExecutionUnits());
      }
      else if (id.equals(P_ID_TICK_LENGTH))
      {
         return Integer.toString(target.getTickLength());
      }
      else if (id.equals(P_ID_NTICK_FREQUENCY))
      {
         return Integer.toString(target.getNTickFrequency());
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
