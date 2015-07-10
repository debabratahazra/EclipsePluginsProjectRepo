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

import java.util.List;
import junit.framework.TestCase;
import com.ose.sigdb.ctypes.CInt;
import com.ose.sigdb.table.SignalDescription;
import com.ose.sigdb.table.SignalDescriptionTable;
import com.ose.sigdb.util.EndianConstants;

public class TestSignalDescriptionTable extends TestCase
{
   public TestSignalDescriptionTable(String name)
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

   public void testSetAndGetName() throws Exception
   {
      final String NAME = "Abra ka Dabra";
      
      SignalDescriptionTable table = new SignalDescriptionTable();
      table.setName(NAME);
      assertEquals(NAME, table.getName());
   }
   
   public void testSortAscending() throws Exception
   {
      SignalDescriptionTable table = new SignalDescriptionTable();
      table.addDescription(new SignalDescription(2, "descriptionTwo", new CInt(
            false, 8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(1, "descriptionOne", new CInt(
            false, 8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(3, "descriptionThree",
            new CInt(false, 8, EndianConstants.BIG_ENDIAN)));

      List descriptions = table
            .getDescriptions(SignalDescriptionTable.SORT_ASCENDING);
      SignalDescription descriptionOne = (SignalDescription) descriptions
            .get(0);
      SignalDescription descriptionTwo = (SignalDescription) descriptions
            .get(1);
      SignalDescription descriptionThree = (SignalDescription) descriptions
            .get(2);

      assertEquals("descriptionOne", descriptionOne.getName());
      assertEquals("descriptionTwo", descriptionTwo.getName());
      assertEquals("descriptionThree", descriptionThree.getName());
   }

   public void testSortDescending() throws Exception
   {
      SignalDescriptionTable table = new SignalDescriptionTable();
      table.addDescription(new SignalDescription(2, "descriptionTwo", new CInt(
            false, 8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(1, "descriptionOne", new CInt(
            false, 8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(3, "descriptionThree",
            new CInt(false, 8, EndianConstants.BIG_ENDIAN)));

      List descriptions = table
            .getDescriptions(SignalDescriptionTable.SORT_DESCENDING);
      SignalDescription descriptionOne = (SignalDescription) descriptions
            .get(2);
      SignalDescription descriptionTwo = (SignalDescription) descriptions
            .get(1);
      SignalDescription descriptionThree = (SignalDescription) descriptions
            .get(0);

      assertEquals("descriptionOne", descriptionOne.getName());
      assertEquals("descriptionTwo", descriptionTwo.getName());
      assertEquals("descriptionThree", descriptionThree.getName());
   }

   public void testCompleteNameLookUp() throws Exception
   {
      SignalDescriptionTable table = new SignalDescriptionTable();
      table.addDescription(new SignalDescription(1, "knatte", new CInt(false,
            8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(2, "fnatte", new CInt(false,
            8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(3, "tjatte", new CInt(false,
            8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(4, "fnalle", new CInt(false,
            8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(5, "fnurre", new CInt(false,
            8, EndianConstants.BIG_ENDIAN)));
      table.addDescription(new SignalDescription(6, "fnisse", new CInt(false,
            8, EndianConstants.BIG_ENDIAN)));

      List names = table.complete("tja");
      assertEquals(1, names.size());
      int match = 0;
      for (int i = 0; i < names.size(); i++)
      {
         String name = (String) names.get(i);
         if (name.equals("knatte") || name.equals("fnatte")
               || name.equals("tjatte") || name.equals("fnalle")
               || name.equals("fnurre") || name.equals("fnisse"))
         {
            match++;
         }
      }

      names = table.complete("fn");
      assertEquals(4, names.size());
      match = 0;
      for (int i = 0; i < names.size(); i++)
      {
         String name = (String) names.get(i);
         if (name.equals("knatte") || name.equals("fnatte")
               || name.equals("tjatte") || name.equals("fnalle")
               || name.equals("fnurre") || name.equals("fnisse"))
         {
            match++;
         }
      }
      assertEquals(4, match);

      names = table.complete("fna");
      assertEquals(2, names.size());
      match = 0;
      for (int i = 0; i < names.size(); i++)
      {
         String name = (String) names.get(i);
         if (name.equals("knatte") || name.equals("fnatte")
               || name.equals("tjatte") || name.equals("fnalle")
               || name.equals("fnurre") || name.equals("fnisse"))
         {
            match++;
         }
      }
      assertEquals(2, match);
   }
}
