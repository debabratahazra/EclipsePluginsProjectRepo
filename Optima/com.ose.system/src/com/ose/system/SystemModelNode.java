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

package com.ose.system;

import java.io.IOException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface represents a node in the hierarchical OSE system model tree
 * structure.
 * <p>
 * A system model node has a parent node (unless it is the system model root
 * node) and may have children nodes (unless it is a leaf node).
 * <p>
 * A system model node can be marked as killed. Killed nodes can be recursivly
 * removed with the clean() method. A system model node and its direct children
 * nodes can be updated with the update() method, or the whole branch in the
 * system model tree that is rooted in a specific node can be updated
 * recursivly.
 *
 * @see org.eclipse.core.runtime.IAdaptable
 */
public interface SystemModelNode extends IAdaptable
{
   /**
    * Return the parent node of this node or null if this node is the root node.
    *
    * @return the parent node of this node or null if this node is the root node.
    */
   public SystemModelNode getParent();

   /**
    * Return the children nodes of this node. If this node have no children
    * nodes, an empty array is returned; null is never returned.
    *
    * @return an array of the children nodes of this node or an empty array if
    * this node have no children nodes.
    */
   public SystemModelNode[] getChildren();

   /**
    * Determine whether this node is a leaf node or not.
    *
    * @return true if this node is a leaf node, false otherwise.
    */
   public boolean isLeaf();

   /**
    * Update this node and its children nodes one level down if it is not killed.
    * If recursive is true, also update the children nodes of the children nodes
    * and so on; i.e. recursivly update the branch in the tree that is rooted in
    * this node.
    *
    * @param progressMonitor  the progress monitor used for cancellation.
    * @param recursive        whether or not to recursivly update the whole
    * branch in the tree that is rooted in this node.
    * @throws IOException     if an I/O exception occurred.
    */
   public void update(IProgressMonitor progressMonitor, boolean recursive)
      throws IOException;

   /**
    * Clean this node, i.e. recursivly remove all nodes marked as killed in the
    * branch in the tree that is rooted in this node.
    */
   public void clean();

   /**
    * Determine whether this node is killed or not.
    *
    * @return true if this node is killed, false otherwise.
    */
   public boolean isKilled();

   /**
    * Check the state of this node. If recursive is true, recursivly check the
    * state of all nodes in the branch in the tree that is rooted in this node.
    * <p>
    * This method is primarily intended for testing and debugging.
    *
    * @param recursive  whether or not to recursivly check the state of all
    * nodes in the branch in the tree that is rooted in this node.
    * @throws IllegalStateException  if this node is not in a consistent state
    * or, if recursive is true, if any node in the branch in the tree that is
    * rooted in this node is not in a consistent state.
    */
   public void checkState(boolean recursive) throws IllegalStateException;
}
