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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

/**
 * This class represents an event in the hierarchical OSE system model tree
 * structure, i.e. a notification that one or more nodes have been changed,
 * added, or removed.
 *
 * @see java.util.EventObject
 */
public class SystemModelEvent extends EventObject
{
   private static final long serialVersionUID = 1L;

   private final SystemModelNode parent;
   private final List children;

   /**
    * Create a new system model event object.
    *
    * @param source    the object on which the event initially occurred.
    * @param parent    the parent node of the changed, added, or removed nodes.
    * @param children  a list of the changed, added, or removed nodes.
    */
   SystemModelEvent(Object source, SystemModelNode parent, List children)
   {
      super(source);
      if (children == null)
      {
         throw new NullPointerException();
      }
      this.parent = parent;
      // Defensive unmodifiable copy to be shared between all event listeners.
      this.children = Collections.unmodifiableList(new ArrayList(children));
   }

   /**
    * Return the parent node of the changed, added, or removed nodes.
    *
    * @return the parent node of the changed, added, or removed nodes.
    */
   public SystemModelNode getParent()
   {
      return parent;
   }

   /**
    * Return a list of the changed, added, or removed nodes. The list contains
    * one or more sibling nodes that have been changed, added, or removed, and
    * who are direct descendents of the parent node specified by getParent().
    *
    * @return a list of the changed, added, or removed nodes.
    * @see SystemModelNode
    */
   public List getChildren()
   {
      return children;
   }

   /**
    * Return a string representation of this system model event object.
    * The returned string is of the form
    * "Source: source, Parent: parent, Children: children".
    *
    * @return a string representation of this system model event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("Source: " + source +
              ", Parent: " + parent +
              ", Children: " + children);
   }
}
