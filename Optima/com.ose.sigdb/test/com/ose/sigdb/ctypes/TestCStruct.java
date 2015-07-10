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

import com.ose.sigdb.CVariable;
import com.ose.sigdb.Member;
import com.ose.sigdb.ctypes.CStruct;
import com.ose.sigdb.ctypes.CInt;
import com.ose.sigdb.util.EndianConstants;
import junit.framework.TestCase;

public class TestCStruct extends TestCase
{
   private CStruct struct; 
   
   public TestCStruct(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
      struct = new CStruct("Struct", EndianConstants.BIG_ENDIAN);
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
      struct = null;
   }

   public void testIsPrimitive() throws Exception
   {
      assertFalse(struct.isPrimitive());
   }
   
   public void testGetMemberNames() throws Exception
   {
      CVariable variable1 = new CVariable("Variable 1", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      CVariable variable2 = new CVariable("Variable 2", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      CVariable variable3 = new CVariable("Variable 3", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      struct.addMember(variable1, 0);
      struct.addMember(variable2, 0);
      struct.addMember(variable3, 0);
      
      String[] members = struct.getMemberNames();

      assertEquals(3, struct.getNoMembers());
      assertEquals(3, members.length);
      assertEquals("Variable 1", members[0]);
      assertEquals("Variable 2", members[1]);
      assertEquals("Variable 3", members[2]);
   }
   
   public void testGetLastMember() throws Exception
   {
      CVariable variable1 = new CVariable("Variable 1", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      CVariable variable2 = new CVariable("Variable 2", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      CVariable variable3 = new CVariable("Variable 3", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      struct.addMember(variable1, 0);
      struct.addMember(variable2, 0);
      struct.addMember(variable3, 0);
      
      Member member = struct.getLastMember();
      assertTrue(member.isPrimitive());
      assertEquals("Variable 3", member.getVariable().getName());      
   }
   
   public void testFindSubVariable() throws Exception
   {
      CVariable variable1 = new CVariable("Variable 1", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      CVariable variable2 = new CVariable("Variable 2", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      CVariable variable3 = new CVariable("Variable 3", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      
      CStruct subStruct = new CStruct("SubStruct", EndianConstants.BIG_ENDIAN);
      CVariable subVariable1 = new CVariable("SubVariable 1", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      subStruct.addMember(subVariable1, 0);
      CVariable subVariable2 = new CVariable("SubVariable 2", new CInt(false, 8, EndianConstants.BIG_ENDIAN));
      subStruct.addMember(subVariable2, 0);
      CVariable structVariable = new CVariable("SubStructVariable", subStruct);
      
      struct.addMember(variable1, 0);
      struct.addMember(variable2, 0);
      struct.addMember(structVariable, 0);
      struct.addMember(variable3, 0);
      
      Member member = struct.findSubVariable("SubStructVariable.SubVariable 1", 0);
      assertEquals("SubVariable 1", member.getVariable().getName());
      member = struct.findSubVariable("SubStructVariable.SubVariable 2", 0);
      assertEquals("SubVariable 2", member.getVariable().getName());
   }
}
