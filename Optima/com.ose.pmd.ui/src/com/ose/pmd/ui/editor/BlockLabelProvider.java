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

package com.ose.pmd.ui.editor;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import com.ose.pmd.dump.AbstractBlock;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.MemoryBlock;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;
import com.ose.pmd.editor.SignalBlockFormatter;
import com.ose.system.ui.util.StringUtils;

public class BlockLabelProvider extends LabelProvider
   implements ITableLabelProvider
{
   public String getColumnText(Object obj, int index)
   {
      if (!(obj instanceof AbstractBlock))
      {
         return null;
      }

      if (obj instanceof ErrorBlock)
      {
         ErrorBlock eb = (ErrorBlock) obj;

         switch (index)
         {
            case DumpForm.COLUMN_BLOCK_STATUS:
               return "";
            case DumpForm.COLUMN_BLOCK_NUMBER:
               return Long.toString(eb.getBlockNo());
            case DumpForm.COLUMN_BLOCK_TYPE:
               return "Error";
            case DumpForm.COLUMN_BLOCK_ADDRESS:
               return "";
            case DumpForm.COLUMN_BLOCK_SIZE:
               return Long.toString(eb.getLength());
            case DumpForm.COLUMN_BLOCK_SIZE_IN_FILE:
               return Long.toString(eb.getLength());
            case DumpForm.COLUMN_BLOCK_DESCRIPTION:
               String[] descriptions = eb.getDescriptions();
               return (descriptions.length > 0) ? descriptions[0].trim() : "";
            default:
               return null;
         }
      }
      else if (obj instanceof TextBlock)
      {
         TextBlock tb = (TextBlock) obj;

         switch (index)
         {
            case DumpForm.COLUMN_BLOCK_STATUS:
               return "";
            case DumpForm.COLUMN_BLOCK_NUMBER:
               return Long.toString(tb.getBlockNo());
            case DumpForm.COLUMN_BLOCK_TYPE:
               return "Text";
            case DumpForm.COLUMN_BLOCK_ADDRESS:
               return "";
            case DumpForm.COLUMN_BLOCK_SIZE:
               return Long.toString(tb.getLength());
            case DumpForm.COLUMN_BLOCK_SIZE_IN_FILE:
               return Long.toString(tb.getLength());
            case DumpForm.COLUMN_BLOCK_DESCRIPTION:
               String[] descriptions = tb.getDescriptions();
               String description =
                  (descriptions.length > 0) ? descriptions[0] : "";
               if (description.length() > 160)
               {
                  description = description.substring(0, 160);
               }
               return description.trim();
            default:
               return null;
         }
      }
      else if (obj instanceof MemoryBlock)
      {
         MemoryBlock mb = (MemoryBlock) obj;

         switch (index)
         {
            case DumpForm.COLUMN_BLOCK_STATUS:
               return "";
            case DumpForm.COLUMN_BLOCK_NUMBER:
               return Long.toString(mb.getBlockNo());
            case DumpForm.COLUMN_BLOCK_TYPE:
               return "Memory";
            case DumpForm.COLUMN_BLOCK_ADDRESS:
               return StringUtils.toHexString(mb.getStartAddress());
            case DumpForm.COLUMN_BLOCK_SIZE:
               return Long.toString(mb.getLength());
            case DumpForm.COLUMN_BLOCK_SIZE_IN_FILE:
               return Long.toString(mb.getCompressedLength());
            case DumpForm.COLUMN_BLOCK_DESCRIPTION:
               String[] descriptions = mb.getDescriptions();
               return (descriptions.length > 0) ? descriptions[0].trim() : "";
            default:
               return null;
         }
      }
      else if (obj instanceof SignalBlock)
      {
         SignalBlock sb = (SignalBlock) obj;

         switch (index)
         {
            case DumpForm.COLUMN_BLOCK_STATUS:
               return "";
            case DumpForm.COLUMN_BLOCK_NUMBER:
               return Long.toString(sb.getBlockNo());
            case DumpForm.COLUMN_BLOCK_TYPE:
               return "Signal";
            case DumpForm.COLUMN_BLOCK_ADDRESS:
               return "";
            case DumpForm.COLUMN_BLOCK_SIZE:
               return Long.toString(sb.getLength());
            case DumpForm.COLUMN_BLOCK_SIZE_IN_FILE:
               return Long.toString(sb.getCompressedLength());
            case DumpForm.COLUMN_BLOCK_DESCRIPTION:
               long reqSigNo = sb.getRequestSigNo();
               long status = sb.getStatus();
               String signalName = SignalBlockFormatter.getSignalName((int) reqSigNo);
               return ((signalName != null) ? signalName + " " : "") +
                  StringUtils.toU32String(reqSigNo) +
                  ((status != 0) ? " Status: " + StringUtils.toHexString(status) : "");
            default:
               return null;
         }
      }
      else
      {
         return null;
      }
   }

   public Image getColumnImage(Object obj, int index)
   {
      if ((index == DumpForm.COLUMN_BLOCK_STATUS) && (obj instanceof SignalBlock))
      {
         SignalBlock sb = (SignalBlock) obj;
         if (sb.getStatus() != 0)
         {
            return PlatformUI.getWorkbench().getSharedImages()
               .getImage(ISharedImages.IMG_OBJS_WARN_TSK);
         }
         else
         {
            return null;
         }
      }
      else
      {
         return null;
      }
   }
}
