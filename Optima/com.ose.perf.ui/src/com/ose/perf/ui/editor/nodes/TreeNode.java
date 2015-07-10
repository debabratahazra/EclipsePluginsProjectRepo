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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public abstract class TreeNode
{
   private final TreeNode parent;
   private final List children = new ArrayList();
   private long hitCount;
   private long totalHitCount;

   public TreeNode()
   {
      this(null);
   }

   public TreeNode(TreeNode parent)
   {
      this.parent = parent;
      if (this.parent != null)
      {
         this.parent.addChild(this);
      }
   }

   public TreeNode getParent()
   {
      return parent;
   }

   public Boolean hasChildren()
   {
      return !children.isEmpty();
   }

   public Object[] getChildren()
   {
      return children.toArray();
   }

   public Iterator getChildrenIterator()
   {
      return children.iterator();
   }

   public void addChild(TreeNode child)
   {
      children.add(child);
   }

   public long getHitCount()
   {
      return hitCount;
   }

   public double getRelativeHitCount()
   {
      return ((double) hitCount) / ((double) totalHitCount);
   }

   public void addHits(long hits)
   {
      hitCount += hits;
      if (parent != null)
      {
         parent.addHits(hits);
      }
   }

   public void setTotalHitCount(long totalHitCount)
   {
      this.totalHitCount = totalHitCount;
      if (parent != null)
      {
         parent.setTotalHitCount(totalHitCount);
      }
   }

   public abstract Image getImage();

   public abstract Color getColor();

   public int hashCode()
   {
      return toString().hashCode();
   }
}
