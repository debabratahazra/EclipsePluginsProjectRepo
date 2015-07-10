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

package com.ose.system.ui.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import com.ose.system.Segment;
import com.ose.system.ui.util.StringUtils;

public class SegmentPropertySource implements IPropertySource
{
   // TODO: Add numSignalsAttached and masMapped when supported.

   public static final String P_ID_KILLED = "KILLED";
   public static final String P_NAME_KILLED = "Killed:";

   public static final String P_ID_NAME = "NAME";
   public static final String P_NAME_NAME = "Name:";

   public static final String P_ID_SID = "SID";
   public static final String P_NAME_SID = "Segment ID:";

   public static final String P_ID_SEGMENT_NUMBER = "SEGMENT_NUMBER";
   public static final String P_NAME_SEGMENT_NUMBER = "Segment Number:";

   private static final IPropertyDescriptor[] propertyDescriptors =
      new IPropertyDescriptor[4];

   private Segment segment;

   public SegmentPropertySource(Segment segment)
   {
      PropertyDescriptor propDescriptor;

      if (segment == null)
      {
         throw new NullPointerException();
      }
      this.segment = segment;

      propDescriptor = new PropertyDescriptor(P_ID_KILLED, P_NAME_KILLED);
      propertyDescriptors[0] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_NAME, P_NAME_NAME);
      propertyDescriptors[1] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SID, P_NAME_SID);
      propertyDescriptors[2] = propDescriptor;

      propDescriptor = new PropertyDescriptor(P_ID_SEGMENT_NUMBER,
            P_NAME_SEGMENT_NUMBER);
      propertyDescriptors[3] = propDescriptor;
   }

   public Object getEditableValue()
   {
      return segment;
   }

   public IPropertyDescriptor[] getPropertyDescriptors()
   {
      return propertyDescriptors;
   }

   public Object getPropertyValue(Object id)
   {
      if (id.equals(P_ID_KILLED))
      {
         return StringUtils.toKilledString(segment.isKilled());
      }
      else if (id.equals(P_ID_NAME))
      {
         return segment.getName();
      }
      else if (id.equals(P_ID_SID))
      {
         return StringUtils.toHexString(segment.getId());
      }
      else if (id.equals(P_ID_SEGMENT_NUMBER))
      {
         return StringUtils.toHexString(segment.getSegmentNumber());
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
