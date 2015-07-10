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

package com.ose.pmd.editor;

import java.util.Comparator;
import com.ose.pmd.dump.AbstractBlock;

/**
 * This class is a comparator for PMD blocks (sub-classes of AbstractBlock). It
 * compares two blocks using their block numbers.
 * <p>
 * This class is used internally by the CLI and GUI dump editors and is not
 * intended to be sub-classed.
 */
public class BlockComparator implements Comparator
{
   /**
    * Compare the two specified blocks for order. Return a negative integer,
    * zero, or a positive integer as the first block is less than, equal to,
    * or greater than the second block. The comparison is done using the block
    * number of the blocks.
    *
    * @param o1  the first block to be compared.
    * @param o2  the second block to be compared.
    * @return a negative integer, zero, or a positive integer as the first block
    * is less than, equal to, or greater than the second block.
    * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
    */
   public int compare(Object o1, Object o2)
   {
      AbstractBlock ab1;
      AbstractBlock ab2;
      long blockNo1;
      long blockNo2;

      if (!(o1 instanceof AbstractBlock) || !(o2 instanceof AbstractBlock))
      {
         throw new ClassCastException();
      }

      ab1 = (AbstractBlock) o1;
      ab2 = (AbstractBlock) o2;
      blockNo1 = ab1.getBlockNo();
      blockNo2 = ab2.getBlockNo();
      return ((blockNo1 < blockNo2) ? -1 : ((blockNo1 > blockNo2) ? 1 : 0));
   }
}
