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

import java.util.Iterator;
import java.util.List;

import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.table.SignalDescription;

/**
 * Object representation of a signal.
 */
public class Signal extends CompositeAttribute
{
   /**
    * Constructs a new Signal
    * 
    * @param signalDescription
    *           description how to read the data array.
    * @param data
    *           array containing the signal data.
    * @param byteOrder
    *           the data byte order
    * @see #com.ose.sigdb.util.EndianConstants#BIG_ENDIAN
    * @see #com.ose.sigdb.util.EndianConstants#LITTLE_ENDIAN
    */
   public Signal(SignalDescription signalDescription, byte[] data, int byteOrder)
   {
      super();
      name = signalDescription.getName();
      List members = signalDescription.getMembers();

      for (int i = 0; i < members.size(); i++)
      {
         Member member = (Member) members.get(i);
         CVariable variable = (CVariable) member.getVariable();
         CType type = variable.getType();

         if (member.getOffset() + member.getSize() <= data.length)
         {
            if (type.isComplex())
            {
               CompositeAttribute compositeAttribute = new CompositeAttribute();
               compositeAttribute.init(member, data, byteOrder);
               attributes.add(compositeAttribute);
            }
            else
            {
               attributes.add(new SignalAttribute(member, data, byteOrder));
            }
         }
         else
         {
            attributes.add(new TruncatedAttribute());
            break;
         }
      }
   }

   /**
    * Gets the signal attribute with name <code>name</code>.
    * 
    * @param name
    *           the name of wanted attribute
    * @return the attribute with the name <code>name</code> or
    *         <code>null</code> if not found
    */
   public Attribute getAttribute(String name)
   {
      for (Iterator iterator = attributes.iterator(); iterator.hasNext();)
      {
         Attribute attribute = (Attribute) iterator.next();
         if (attribute.getName().equals(name))
         {
            return attribute;
         }
      }

      return null;
   }

   /**
    * Gets the name for this signal.
    * 
    * @return this signal's name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Gets this signal's attributes.
    * 
    * @return this signal's attributes.
    */
   public List getAttributes()
   {
      return attributes;
   }

   /**
    * Gets a string representation of this Signal.
    */
   public String toString()
   {
      String ls = System.getProperty("line.separator", "\n");
      StringBuffer buffer = new StringBuffer();

      buffer.append("name = " + name);

      for (Iterator iterator = attributes.iterator(); iterator.hasNext();)
      {
         buffer.append(ls);
         SignalAttribute attribute = (SignalAttribute) iterator.next();
         buffer.append(attribute.getType() + " " + attribute.getName() + " = "
               + attribute.getValue());
      }

      return buffer.toString();
   }
}
