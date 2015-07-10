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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ose.sigdb.CVariable;
import com.ose.sigdb.Member;

/**
 * This class is an abstact class used to implement struct and union types.
 */
public abstract class ComplexType extends CType
{
   private List members;

   /**
    * @param name
    * @param defaultEndian
    */
   public ComplexType(String name, int defaultEndian)
   {
      super(name, defaultEndian);
      members = new ArrayList();
   }

   /**
    * Add a new member to the data type.
    * 
    * @param newMember
    *           the member variable.
    * @param offset
    *           number of bytes from the start.
    */
   public void addMember(CVariable newMember, int offset)
   {
      if (newMember == null)
      {
         throw new RuntimeException("New member can't be " + newMember);
      }
      if (offset < 0)
      {
         throw new RuntimeException("Offset can't be " + offset);
      }

      members.add(new Member(newMember, offset));
   }

   /**
    * @param vname
    * @param offset
    * @return
    * 
    * @throws MalformedVariableName
    */
   public final Member findSubVariable(String vname, int offset)
   {
      for (int i = 0; i < members.size(); i++)
      {
         Member m = (Member) members.get(i);
         Member v = m.getVariable().findSubVariable(vname,
               offset + m.getOffset());

         if (v != null)
         {
            return v;
         }
      }
      return null; // Not found.
   }

   /**
    * Obtain the last member variable in this struct or union.
    * 
    * @return the last member.
    */
   public Member getLastMember()
   {
      if (members.size() > 0)
      {
         return (Member) members.get(members.size() - 1);
      }
      else
      {
         return null;
      }
   }

   /**
    * Get the list iterator for this struct/union. This function returns an
    * iterator for this types members.
    * 
    * @return a list iterator of the members.
    * @see osedbg.api.signal;
    */
   public Iterator getMemberIterator()
   {
      return members.iterator();
   }

   public List getMembers()
   {
      return members;
   }
   
   /**
    * Get a list of all members names.
    * 
    * @return
    */
   public String[] getMemberNames()
   {
      String[] list = new String[members.size()];

      Iterator li = members.iterator();

      for (int i = 0; li.hasNext(); i++)
      {
         Member m = (Member) li.next();

         list[i] = m.getVariable().getName();
      }
      return list;
   }

   /**
    * Get the number of members.
    * 
    * @return the number of members.
    */
   public int getNoMembers()
   {
      return members.size();
   }

   public boolean isComplex()
   {
      return !isPrimitive();
   }

   /**
    * Check if this type is a C primitive type.
    * 
    * @return false is always returned.
    */
   public boolean isPrimitive()
   {
      return false;
   }

   /**
    * @param data
    * @param offset
    * @param endian
    * @param format
    * @return
    */
   public String toString(byte[] data, int offset, int endian, String format)
   {
      StringBuffer result = new StringBuffer(1024);

      result.append(getName());
      result.append(" {\n");

      for (int i = 0; i < members.size(); i++)
      {
         Member m = (Member) members.get(i);

         if (offset + m.getOffset() + m.getSize() > data.length)
         {
            result.append("   -*- Signal data is truncated -*-" + "\n");
            break;
         }
         else
         {
            result.append(m.getVariable().toString(data, offset + m.getOffset(), endian, null));
            
            if (!result.toString().endsWith("\n"))
               result.append('\n');
         }
      }
      result.append('}');

      return result.toString();
   }
}
