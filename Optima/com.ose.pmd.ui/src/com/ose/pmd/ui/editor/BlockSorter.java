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

import java.math.BigInteger;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import com.ose.pmd.dump.AbstractBlock;
import com.ose.pmd.dump.Chunk;
import com.ose.pmd.dump.ErrorBlock;
import com.ose.pmd.dump.MemoryBlock;
import com.ose.pmd.dump.SignalBlock;
import com.ose.pmd.dump.TextBlock;
import com.ose.system.ui.util.StringUtils;

public class BlockSorter extends ViewerSorter
{
   private static final int ASCENDING = 1;
   private static final int DESCENDING = -1;

   private int column;
   private int direction;

   BlockSorter()
   {
      super();
      reset();
   }

   public void reset()
   {
      column = DumpForm.COLUMN_BLOCK_NUMBER;
      direction = ASCENDING;
   }

   public void sortByColumn(int column)
   {
      if (this.column == column)
      {
         // Same column, toggle sort direction.
         direction *= DESCENDING;
      }
      else
      {
         // New column, reset sort direction.
         this.column = column;
         direction = ASCENDING;
      }
   }

   public int compare(Viewer viewer, Object e1, Object e2)
   {
      if (!(e1 instanceof AbstractBlock) && !(e2 instanceof AbstractBlock))
      {
         return 0;
      }

      AbstractBlock block1 = (AbstractBlock) e1;
      AbstractBlock block2 = (AbstractBlock) e2;

      switch (column)
      {
         case DumpForm.COLUMN_BLOCK_STATUS:
            return compareLongs(getStatus(block1), getStatus(block2));
         case DumpForm.COLUMN_BLOCK_NUMBER:
            return compareLongs(block1.getBlockNo(), block2.getBlockNo());
         case DumpForm.COLUMN_BLOCK_TYPE:
            return compareStrings(getType(block1), getType(block2));
         case DumpForm.COLUMN_BLOCK_ADDRESS:
            return compareUnsignedLongs(getAddress(block1), getAddress(block2));
         case DumpForm.COLUMN_BLOCK_SIZE:
            return compareLongs(getSize(block1), getSize(block2));
         case DumpForm.COLUMN_BLOCK_SIZE_IN_FILE:
            return compareLongs(getSizeInFile(block1), getSizeInFile(block2));
         case DumpForm.COLUMN_BLOCK_DESCRIPTION:
            return compareStrings(getDescription(block1), getDescription(block2));
         default:
            return 0;
      }
   }

   private long getStatus(AbstractBlock ab)
   {
      if (ab instanceof SignalBlock)
      {
         return ((SignalBlock) ab).getStatus();
      }
      else
      {
         return 0;
      }
   }

   private String getType(AbstractBlock ab)
   {
      if (ab instanceof ErrorBlock)
      {
         return "Error";
      }
      else if (ab instanceof TextBlock)
      {
         return "Text";
      }
      else if (ab instanceof MemoryBlock)
      {
         return "Memory";
      }
      else if (ab instanceof SignalBlock)
      {
         return "Signal";
      }
      else
      {
         return "";
      }
   }

   private long getAddress(AbstractBlock ab)
   {
      if (ab instanceof MemoryBlock)
      {
         return ((MemoryBlock) ab).getStartAddress();
      }
      else
      {
         return 0;
      }
   }

   private long getSize(AbstractBlock ab)
   {
      if (ab instanceof ErrorBlock)
      {
         return ((ErrorBlock) ab).getLength();
      }
      else if (ab instanceof TextBlock)
      {
         return ((TextBlock) ab).getLength();
      }
      else if (ab instanceof MemoryBlock)
      {
         return ((MemoryBlock) ab).getLength();
      }
      else if (ab instanceof SignalBlock)
      {
         return ((SignalBlock) ab).getLength();
      }
      else
      {
         return 0;
      }
   }

   private long getSizeInFile(AbstractBlock ab)
   {
      if (ab instanceof ErrorBlock)
      {
         return ((ErrorBlock) ab).getLength();
      }
      else if (ab instanceof TextBlock)
      {
         return ((TextBlock) ab).getLength();
      }
      else if (ab instanceof MemoryBlock)
      {
         return ((MemoryBlock) ab).getCompressedLength();
      }
      else if (ab instanceof SignalBlock)
      {
         return ((SignalBlock) ab).getCompressedLength();
      }
      else
      {
         return 0;
      }
   }

   private String getDescription(AbstractBlock ab)
   {
      if (ab instanceof ErrorBlock)
      {
         String[] descriptions = ((ErrorBlock) ab).getDescriptions();
         return (descriptions.length > 0) ? descriptions[0].trim() : "";
      }
      else if (ab instanceof TextBlock)
      {
         String[] descriptions = ((TextBlock) ab).getDescriptions();
         String description = (descriptions.length > 0) ? descriptions[0] : "";
         if (description.length() > 160)
         {
            description = description.substring(0, 160);
         }
         return description.trim();
      }
      else if (ab instanceof MemoryBlock)
      {
         String[] descriptions = ((MemoryBlock) ab).getDescriptions();
         return (descriptions.length > 0) ? descriptions[0].trim() : "";
      }
      else if (ab instanceof SignalBlock)
      {
         return StringUtils.toU32String(((SignalBlock) ab).getRequestSigNo());
      }
      else
      {
         return "";
      }
   }

   private int compareStrings(String s1, String s2)
   {
      int result;

      result = s1.compareTo(s2);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }

      return result;
   }

   private int compareLongs(long l1, long l2)
   {
      int result;

      result = (l1 < l2) ? -1 : ((l1 > l2) ? 1 : 0);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }

      return result;
   }
   
   private int compareUnsignedLongs(long l1, long l2)
   {
      BigInteger big1 = new BigInteger(1, Chunk.longToByteArray(l1));
      BigInteger big2 = new BigInteger(1, Chunk.longToByteArray(l2));
      int result = big1.compareTo(big2);
      if (direction == DESCENDING)
      {
         result *= DESCENDING;
      }
      return result;
   }
}
