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

package com.ose.system.ui.views.heap;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.system.HeapBufferInfo;
import com.ose.system.ui.util.ExtendedHeapBufferInfo;
import com.ose.system.ui.util.StringUtils;

public class HeapBufferLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof ExtendedHeapBufferInfo))
      {
         return null;
      }

      ExtendedHeapBufferInfo extBuf = (ExtendedHeapBufferInfo) obj;
      HeapBufferInfo buf = extBuf.getHeapBufferInfo();
      switch (index)
      {
         case HeapView.COLUMN_BROKEN_BUFFER:
            return "";
         case HeapView.COLUMN_OWNER_ID:
            return StringUtils.toHexString(buf.getOwner());
         case HeapView.COLUMN_OWNER_NAME:
            return extBuf.getOwnerName();
         case HeapView.COLUMN_SHARED:
            return buf.isShared() ? "Yes" : "No";
         case HeapView.COLUMN_REQUESTED_SIZE:
            return StringUtils.toU32String(buf.getRequestedSize());
         case HeapView.COLUMN_ACTUAL_SIZE:
            return StringUtils.toU32String(buf.getSize());
         case HeapView.COLUMN_ADDRESS:
            return StringUtils.toHexString(buf.getAddress());
         case HeapView.COLUMN_FILE_NAME:
            return ((buf.getFileName().length() > 0) ?
               buf.getFileName() : StringUtils.N_A);
         case HeapView.COLUMN_LINE_NUMBER:
            return ((buf.getLineNumber() > 0) ?
               StringUtils.toU32String(buf.getLineNumber()) : StringUtils.N_A);
         case HeapView.COLUMN_STATUS:
            return StringUtils.toHeapBufferStatusString(buf.getStatus());
         default:
            return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if (!(obj instanceof ExtendedHeapBufferInfo))
      {
         return null;
      }

      ExtendedHeapBufferInfo buf = (ExtendedHeapBufferInfo) obj;
      if ((index == HeapView.COLUMN_BROKEN_BUFFER) && buf.isEndmarkBroken())
      {
         return PlatformUI.getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_OBJS_WARN_TSK);
      }
      else
      {
         return null;
      }
   }
}
