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

package com.ose.sigdb;

import java.util.ArrayList;
import java.util.List;

import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.ctypes.ComplexType;

/**
 * The member represents a variable member in a struct or union.
 * 
 * @see com.ose.sigdb.ctype.CStruct
 * @see com.ose.sigdb.ctype.CUnion
 * @see com.ose.sigdb.ComplexType#addMember
 * @see com.ose.sigdb.ComplexType
 */
public class Member
{

   private CVariable variable;

   private int offset;

   /**
    * Create a new struct/union member.
    * 
    * @param var
    *           the variable that is the member.
    * @param index
    *           (or offset) into the struct or 0 (zero) if an union.
    */
   public Member(CVariable var, int index)
   {
      variable = var;
      offset = index;
   }

   /**
    * Get the byte offset of this member. The offset is relative to the start of
    * the struct.
    * 
    * @return offset of this member in the struct.
    */
   public final int getOffset()
   {
      return offset;
   }

   /**
    * Get the size of this variable. Any padding in the struct/union is ignored.
    * 
    * @return the size of this members type.
    */
   public final int getSize()
   {
      return variable.getSize();
   }

   /**
    * Get the variable of this member.
    * 
    * @return variable of this member.
    */
   public final CVariable getVariable()
   {
      return variable;
   }

   /**
    * Returns whenever this member's type is of a primitive C type or not.
    * 
    * @return true if the type is a primitive type.
    */
   public final boolean isPrimitive()
   {
      return variable.getType().isPrimitive();
   }
   
   /**
    * Gets the submembers of this member.
    * @return a list with the members. An empty list if it is not a complex type.
    */
   public List getMembers()
   {
      List members = new ArrayList();
      CType type = variable.getType();
      
      if(type instanceof ComplexType)
      {
         ComplexType complex = (ComplexType) type;
         members = complex.getMembers();
      }
      
      return members;
   }
}
