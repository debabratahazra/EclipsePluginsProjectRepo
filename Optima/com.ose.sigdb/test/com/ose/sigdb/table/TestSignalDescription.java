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

import java.util.Iterator;
import java.util.List;
import com.ose.sigdb.CVariable;
import com.ose.sigdb.Member;
import com.ose.sigdb.ctypes.CInt;
import com.ose.sigdb.ctypes.CLong;
import com.ose.sigdb.ctypes.CStruct;
import com.ose.sigdb.ctypes.CType;
import com.ose.sigdb.util.EndianConstants;
import junit.framework.TestCase;

public class TestSignalDescription extends TestCase
{

   public TestSignalDescription(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testGetName() throws Exception
   {
      final String NAME = "etthundra";
      
      SignalDescription description = new SignalDescription(100, NAME,
            new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      assertEquals(NAME, description.getName());
   }
   
   public void testGetSignalNumber() throws Exception
   {
      final int SIGNAL_NUMBER = 100;
      
      SignalDescription description = new SignalDescription(SIGNAL_NUMBER, "kvastfening",
            new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      assertEquals(SIGNAL_NUMBER, description.getSigNo());
      assertEquals(SIGNAL_NUMBER, description.getSigNoLong());
   }
   
   public void testGetType() throws Exception
   {
      SignalDescription description = new SignalDescription(100, "kvastfening",
            new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      assertTrue(description.getType() instanceof CInt);
   }
   
   public void testGetSigNoType() throws Exception
   {
      SignalDescription description = new SignalDescription(100, "kvastfening",
            new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      assertNull(description.getSignoType());
      
      description = new SignalDescription(new CLong(false, 8, EndianConstants.BIG_ENDIAN), 100, "kvastfening",
            new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      assertTrue(description.getSignoType() instanceof CLong);
   }
   
   public void testFindMember() throws Exception
   {
      CStruct root = new CStruct("struct", EndianConstants.BIG_ENDIAN);
      root.addMember(new CVariable("int_1", new CInt(false, 8, EndianConstants.BIG_ENDIAN)), 0);
      root.addMember(new CVariable("int_2", new CInt(false, 8, EndianConstants.BIG_ENDIAN)), 0);
      root.addMember(new CVariable("int_3", new CInt(false, 8, EndianConstants.BIG_ENDIAN)), 0);
      
      SignalDescription description = new SignalDescription(100, "signal", root);

      Member member = description.findMember("int_1");
      assertNotNull(member);
      
      CVariable variable = member.getVariable();
      assertEquals("int_1", variable.getName());
      
      CType type = variable.getType();
      assertTrue(type instanceof CInt);
      assertEquals("int", type.getName());
      
      member = description.findMember("int_3");
      assertNotNull(member);
      
      variable = member.getVariable();
      assertEquals("int_3", variable.getName());
      
      type = variable.getType();
      assertTrue(type instanceof CInt);
      assertEquals("int", type.getName());
   }
   
   public void testGetMembers() throws Exception
   {
      CStruct root = new CStruct("struct", EndianConstants.BIG_ENDIAN);
      root.addMember(new CVariable("int_1", new CInt(false, 8, EndianConstants.BIG_ENDIAN)), 0);
      root.addMember(new CVariable("int_2", new CInt(false, 8, EndianConstants.BIG_ENDIAN)), 0);
      root.addMember(new CVariable("int_3", new CInt(false, 8, EndianConstants.BIG_ENDIAN)), 0);
      
      SignalDescription description = new SignalDescription(100, "signal", root);
      
      List members = description.getMembers();
      int match = 0;
      for (Iterator iterator = members.iterator(); iterator.hasNext();)
      {
         Member member = (Member) iterator.next();
         CVariable variable = member.getVariable();
         String name = variable.getName();
         if(
               name.equals("int_1") ||
               name.equals("int_2") ||
               name.equals("int_3"))
         {
            match++;
         }
      }
      
      assertEquals(3, match);
   }
}
