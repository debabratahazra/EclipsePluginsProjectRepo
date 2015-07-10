/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.ui.view;

import java.text.DateFormat;
import java.util.Date;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import com.ose.system.DumpInfo;
import com.ose.system.ui.SharedImages;
import com.ose.system.ui.util.StringUtils;

public class DumpLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance();

   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof DumpInfo))
      {
         return null;
      }

      DumpInfo dump = (DumpInfo) obj;
      switch (index)
      {
         case DumpView.COLUMN_DUMP_ID:
            return StringUtils.toHexString(dump.getId());
         case DumpView.COLUMN_DUMP_SIZE:
            return Long.toString(dump.getSize());
         case DumpView.COLUMN_DUMP_TIMESTAMP:
            return DATE_FORMAT.format(new Date(dump.getSeconds() * 1000)) +
                   " " + dump.getMicroSeconds() + " \u00B5s";
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if ((index == DumpView.COLUMN_DUMP_ID) &&
          (obj instanceof DumpInfo))
      {
         return SharedImages.get(SharedImages.IMG_OBJ_LOAD_MODULE);
      }
      else
      {
         return null;
      }
   }
}
