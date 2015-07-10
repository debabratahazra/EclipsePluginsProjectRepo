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

package com.ose.sigdb.ctypes;

import java.util.Iterator;

import com.ose.sigdb.Member;

/**
 * This class represents a C union.
 */
public class CUnion extends ComplexType
{

   /**
    * Create a new C union. The union is not complete after creation, instead
    * addMember should be called. The type name should start with the key word
    * union.
    * 
    * @param typeName
    *           the name of the union (e.g. "union Signals").
    * @param endian
    *           the default byte order.
    * @see osedbg.api.signal.ComplexType#addMember
    */
   public CUnion(String typeName, int endian)
   {
      super(typeName, endian);
   }

   /**
    * Calculate the size occupied by this union. Any padding is ignored.
    * 
    * @return the size of the largest member.
    */
   public int getSize()
   {
      int size = 0;

      for (Iterator i = getMemberIterator(); i.hasNext();)
      {
         Member m = (Member) i.next();

         if (m.getSize() > size)
         {
            size = m.getSize();
         }
      }
      return size;
   }

   /**
    * Get the type name.
    * 
    * @return the complete type name including "union".
    */
   public String toString()
   {
      return getName();
   }
}
