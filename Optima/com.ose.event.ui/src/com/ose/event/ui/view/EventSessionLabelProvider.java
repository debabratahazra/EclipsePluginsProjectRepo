/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.event.ui.view;

import java.text.DateFormat;
import java.util.Date;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.util.StringUtils;

class EventSessionLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();

   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof EventSession))
      {
         return null;
      }

      EventSession eventSession = (EventSession) obj;
      switch (index)
      {
         case EventView.COLUMN_EVENT_SESSION_TARGET:
            return eventSession.getTarget().toString();
         case EventView.COLUMN_EVENT_SESSION_SCOPE:
            return StringUtils.toScopeString(eventSession.getScopeType(),
                                             eventSession.getScopeId());
         case EventView.COLUMN_EVENT_SESSION_TIMESTAMP:
            return DATE_FORMAT.format(new Date(eventSession.getTimestamp()));
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if ((index == EventView.COLUMN_EVENT_SESSION_TARGET) &&
          (obj instanceof EventSession))
      {
         EventSession eventSession = (EventSession) obj;
         return SharedImages.get(eventSession.isOpen() ?
            SharedImages.IMG_OBJ_TARGET : SharedImages.IMG_OBJ_TARGET_KILLED);
      }
      else
      {
         return null;
      }
   }
}
