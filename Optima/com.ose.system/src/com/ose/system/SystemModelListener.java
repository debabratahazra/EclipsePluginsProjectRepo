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

import java.util.EventListener;

/**
 * SystemModelListener defines the interface for a client object that listens to
 * changes in the hierarchical OSE system model tree structure.
 *
 * @see java.util.EventListener
 */
public interface SystemModelListener extends EventListener
{
   /**
    * Invoked after the properties of a node or a set of sibling nodes have
    * changed in the system model tree.
    *
    * Use event.getParent() to get the parent node of the changed node(s) and
    * event.getChildren() to get the changed node(s).
    *
    * @param event  the system model event
    */
   public void nodesChanged(SystemModelEvent event);

   /**
    * Invoked after a node or a set of sibling nodes have been added to the
    * system model tree.
    *
    * Use event.getParent() to get the parent node of the added node(s) and
    * event.getChildren() to get the added node(s).
    *
    * @param event  the system model event
    */
   public void nodesAdded(SystemModelEvent event);

   /**
    * Invoked after a node or a set of sibling nodes have been removed from the
    * system model tree.
    *
    * Use event.getParent() to get the former parent node of the removed node(s)
    * and event.getChildren() to get the removed node(s).
    *
    * @param event  the system model event
    */
   public void nodesRemoved(SystemModelEvent event);
}
