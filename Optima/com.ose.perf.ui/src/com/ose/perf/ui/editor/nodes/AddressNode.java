/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.editor.nodes;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import com.ose.perf.ui.SharedImages;

public class AddressNode extends TreeNode
{
   private final long address;
   private final Color color = new Color(Display.getCurrent(), 229, 248, 176);

   public AddressNode(BinaryNode parent, long address, long hitCount, long totalHitCount)
   {
      super(parent);
      this.address = address;
      addHits(hitCount);
      setTotalHitCount(totalHitCount);
   }

   public AddressNode(FunctionNode parent, long address, long hitCount, long totalHitCount)
   {
      super(parent);
      this.address = address;
      addHits(hitCount);
      setTotalHitCount(totalHitCount);
   }

   public AddressNode(LineNode parent, long address, long hitCount, long totalHitCount)
   {
      super(parent);
      this.address = address;
      addHits(hitCount);
      setTotalHitCount(totalHitCount);
   }

   protected void finalize()
   {
      color.dispose();
   }

   public Image getImage()
   {
      return SharedImages.get(SharedImages.IMG_OBJ_ADDRESS);
   }

   public Color getColor()
   {
      return color;
   }

   public String toString()
   {
      return "0x" + Long.toHexString(address).toUpperCase();
   }
}
