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

package com.ose.sigdb.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ose.sigdb.CVariable;
import com.ose.sigdb.Member;
import com.ose.sigdb.ctypes.CStruct;
import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.ctypes.CTypeDef;
import com.ose.sigdb.ctypes.ComplexType;
import com.ose.sigdb.util.U32;

/**
 * This class models an OSE signal (symbolic info). Signals are normaly read by
 * SignalTableReader from a target or from an object file. The c language type
 * system is modeled by CType as the root class.
 * 
 * @see com.ose.sigdb.table.SignalTableReader
 * @see com.ose.sigdb.ctypes.CType
 */
public class SignalDescription
{
   /**
    * Message that is used then the size of the signal data is less than the
    * size of the type of the signal.
    */
   public final static String truncatedMessage = "   -*- Signal data is truncated -*-\n";

   private int signo;

   private String name;

   private CType type;

   private CType signoType;

   /**
    * This constructor is used only for temp signals.
    * 
    * @param signoType
    * @param signo
    * @param name
    * @param type
    */
   public SignalDescription(CType signoType, int signo, String name, CType type)
   {
      if (name == null)
         throw new NullPointerException();
      if (type == null)
         throw new NullPointerException();
      this.signoType = signoType;
      this.signo = signo;
      this.name = name;
      this.type = type;
   }

   /**
    * This constructor is normaly used by a signal table reader.
    * 
    * @param signalTable
    *           the signal table this signal is part of.
    * @param signo
    *           the signal number of this signa.
    * @param name
    *           the signal name.
    * @param type
    *           The c type of this signal table.
    */
   public SignalDescription(int signo, String name, CType type)
   {
      if (name == null)
         throw new NullPointerException("name may not be null");
      if (type == null)
         throw new NullPointerException("type may not be null");
      this.signo = signo;
      this.name = name;
      this.type = type;
   }

   /**
    * Find a sub variable by its name. The name must be complete inclusive any
    * array indexes. The variable is returned as a {@link Member, Member} to
    * preserve the offset from the start of the signal.
    * 
    * @param name
    *           fully qualified member name (e.g. "nisse[2].pelle.orvar").
    * @return The found variable as a member or null if not found.
    * 
    * @throws MalformedVariableName
    */
   public Member findMember(String name)
   {
      return new CVariable(this.name, type).findMember(this.name + "."
            + name.trim());
   }

   public List getMembers()
   {
      List subMembers = new ArrayList();

      if (type.isComplex())
      {
         ComplexType complexType = (ComplexType) type;
         Iterator iterator = complexType.getMemberIterator();
         while (iterator.hasNext())
         {
            Member member = (Member) iterator.next();
            subMembers.add(member);
         }
      }

      return subMembers;
   }

   /**
    * Get the name of the signal.
    * 
    * @return
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the signal no of this signal. The signo is represented as a signed int
    * which means that signal numbers that are equal to or greater than 2^31
    * will have a negative representation.
    * 
    * @see #getSigNoLong
    * @return signo
    */
   public int getSigNo()
   {
      return signo;
   }

   /**
    * Get the signal no of this signal. The signo is returned as a long so that
    * the complete signal no range is correctly represented.
    * 
    * @return signo
    */
   public long getSigNoLong()
   {
      return U32.get(signo);
   }

   /**
    * Get the type of this signals signo. The signo type is fetched from the
    * signal table.
    * 
    * @return signo type.
    */
   public CType getSignoType()
   {
      return signoType;
   }

   /**
    * Get the type that describes the signal data.
    * 
    * @return
    */
   public CType getType()
   {
      return type;
   }

   /**
    * @return
    */
   public String toString()
   {
      return getName() + " " + Long.toString(getSigNoLong());
   }

   /**
    * @param data
    * @return
    */
   public String toString(byte[] data)
   {
      return toString(data, type.getDefaultEndian());
   }

   /**
    * @param data
    * @param endian
    * @return
    */
   public String toString(byte[] data, int endian)
   {
      String result;

      CType _type;

      if (type instanceof CTypeDef)
         _type = ((CTypeDef) type).getSubType();
      else
         _type = type;

      if ((_type.getSize() < data.length) && (_type instanceof CStruct))
         result = ((CStruct) _type).toStringVarArray(data, 0, data.length,
               _type.getDefaultEndian(), null);
      else
         result = type.toString(data, 0, endian);

      String sizes;

      if (type.getSize() != data.length)
         sizes = " sizeof(" + type.getName() + ") = "
               + U32.toString(type.getSize()) + " data size = "
               + U32.toString(data.length);
      else
         sizes = " size = " + U32.toString(data.length);

      return toString() + sizes + "\n" + result;
   }
}
