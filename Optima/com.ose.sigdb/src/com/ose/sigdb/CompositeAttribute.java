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

public class CompositeAttribute extends Attribute
{
   protected List attributes;

   public CompositeAttribute()
   {
      attributes = new ArrayList();
   }

   public void init(Member member, byte[] data, int byteOrder)
   {
      name = member.getVariable().getName();
      type = member.getVariable().getType();

      List members = member.getMembers();
      for (int i = 0; i < members.size(); i++)
      {
         Member subMember = (Member) members.get(i);
         CVariable variable = (CVariable) subMember.getVariable();
         CType type = variable.getType();

         if (member.getOffset() + member.getSize() <= data.length)
         {
            if (type.isComplex())
            {
               CompositeAttribute compositeAttribute = new CompositeAttribute();
               compositeAttribute.init(subMember, data, byteOrder);
               attributes.add(compositeAttribute);
            }
            else
            {

               attributes.add(new SignalAttribute(subMember, data, byteOrder));
            }
         }
         else
         {
            attributes.add(new TruncatedAttribute());
            break;
         }
      }
   }

   public List getAttributes()
   {
      return attributes;
   }
}
