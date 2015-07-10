/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
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

package com.ose.xmleditor.model;

import org.w3c.dom.Node;

public class DocumentChangeEvent
{
   public static final int CHANGE = 1;

   public static final int ADD = 2;

   public static final int REMOVE = 3;
   
   public static final int REPLACE = 4;

   private final Node node;

   private final int type;

   private final String attributeName;

   private final Object oldValue;

   private final Object newValue;

   public DocumentChangeEvent(int type, Node node)
   {
      this.type = type;
      this.node = node;
      this.attributeName = null;
      this.oldValue = null;
      this.newValue = null;
   }

   public DocumentChangeEvent(int type, Node node, String attributeName, Object oldValue, Object newValue)
   {
      this.type = type;
      this.node = node;
      this.attributeName = attributeName;
      this.oldValue = oldValue;
      this.newValue = newValue;
   }

   public Node getNode()
   {
      return node;
   }

   public int getType()
   {
      return type;
   }

   public String getAttributeName()
   {
      return attributeName;
   }

   public Object getOldValue()
   {
      return oldValue;
   }

   public Object getNewValue()
   {
      return newValue;
   }

}
