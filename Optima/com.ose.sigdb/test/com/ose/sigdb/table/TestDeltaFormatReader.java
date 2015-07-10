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

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import com.ose.sigdb.CVariable;
import com.ose.sigdb.Member;
import com.ose.sigdb.Signal;
import com.ose.sigdb.SignalAttribute;
import com.ose.sigdb.io.BinaryFile;
import com.ose.sigdb.io.ObjectFile;
import com.ose.sigdb.util.EndianConstants;

public class TestDeltaFormatReader extends TestCase
{
   private ObjectFile objectFile;
   
   public TestDeltaFormatReader(String name)
   {
      super(name);
      
      File file = new File("test/evtdb.o");
      
      objectFile = new BinaryFile();
      
      try
      {
         objectFile.init(file);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testRead() throws Exception
   {
      SignalTableReader tableReader = new DeltaFormatReader(objectFile);
      SignalDescriptionTable signalTable = tableReader.read("evt");
      
      SignalDescription description = signalTable.getDescription(220);
      assertEquals(220, description.getSigNo());
      assertEquals("CRT_EVENT_EXIT_FGETS", description.getName());
      
      int match = 0;
      List members = description.getMembers();
      for (Iterator iterator = members.iterator(); iterator.hasNext();)
      {
         Member member = (Member) iterator.next();
         CVariable variable = member.getVariable();
         if("result".equals(variable.getName()))
         {
            match++;
         }
      }
      assertEquals(1, match);
      
      printSignalDescriptionTable(signalTable);
   }

   private void printSignalDescriptionTable(SignalDescriptionTable signalTable)
   {
      SignalDescription description;
      List members;
      List descriptions = signalTable.getDescriptions(SignalDescriptionTable.SORT_ASCENDING);
      for (int i = 0; i < descriptions.size(); i++)
      {
         System.out.println("**************************");
         description = (SignalDescription) descriptions.get(i);
         System.out.println(description.getSigNo() + ":" + description.getName());

         members = description.getMembers();
         for (int j = 0; j < members.size(); j++)
         {
            Member member = (Member) members.get(j);
            CVariable variable = (CVariable) member.getVariable();
            System.out.println(" - " + variable.getType() + " ("
                  + variable.getSize() + ") " + variable.getName());
         }
      }
   }

   public void testEventEnterWrite() throws Exception
   {
      final byte[] DATA_WRITE = 
      { 
            0x00, 0x00, 0x12, 0x34, // fd
            0x00, 0x00, 0x56, 0x78, // buffer
            0x12, 0x34, 0x56, 0x78  // nbytes
      };
      
      SignalTableReader tableReader = new DeltaFormatReader(objectFile);
      SignalDescriptionTable signalTable = tableReader.read("evt");

      SignalDescription signalDescription = signalTable.getDescription(0x6a);

      Signal signal = new Signal(signalDescription, DATA_WRITE,
            EndianConstants.BIG_ENDIAN);

      assertEquals("CRT_EVENT_ENTER_WRITE", signal.getName());

      List attributes = signal.getAttributes();
      
      for (int i = 0; i < attributes.size(); i++)
      {
         SignalAttribute attribute = (SignalAttribute) attributes.get(i);

         if (attribute.getName().equals("fd"))
         {
            assertEquals("4660", attribute.getValue());
         }
         else if (attribute.getName().equals("buffer"))
         {
            assertEquals("0x5678", attribute.getValue());
         }
         else if (attribute.getName().equals("nbytes"))
         {
            assertEquals("0x12345678", attribute.getValue());
         }
      }
   }
   
   public void testCreateEventEnterPuts() throws IOException
   {
      final byte[] EVENT_ENTER_PUTS =
      {
            0x50, 0x61, 0x73, 0x73  // 'Pass'
      };
      
      SignalTableReader tableReader = new DeltaFormatReader(objectFile);
      SignalDescriptionTable signalTable = tableReader.read("evt");
      
      SignalDescription signalDescription = signalTable.getDescription(0x77);
      
      Signal signal = new Signal(signalDescription, EVENT_ENTER_PUTS,
            EndianConstants.BIG_ENDIAN);

      assertEquals("CRT_EVENT_ENTER_PUTS", signal.getName());

      List attributes = signal.getAttributes();
      
      for (int i = 0; i < attributes.size(); i++)
      {
         SignalAttribute attribute = (SignalAttribute) attributes.get(i);

         if (attribute.getName().equals("str"))
         {
            assertEquals("0x50617373", attribute.getValue());
         }
      }
   }
   
   public void testCreateEventEnterOpen() throws IOException
   {
      final byte[] EVENT_ENTER_OPEN =
      {
            0x00, 0x00, 0x00, 0x00, // omode
            0x00, 0x00, 0x00, 0x00, // amode
            0x50, 0x61, 0x73, 0x73  // 'Pass'
      };
      
      SignalTableReader tableReader = new DeltaFormatReader(objectFile);
      SignalDescriptionTable signalTable = tableReader.read("evt");
      
      SignalDescription signalDescription = signalTable.getDescription(0x64);
      
      Signal signal = new Signal(signalDescription, EVENT_ENTER_OPEN,
            EndianConstants.BIG_ENDIAN);

      assertEquals("CRT_EVENT_ENTER_OPEN", signal.getName());

      List attributes = signal.getAttributes();
      
      for (int i = 0; i < attributes.size(); i++)
      {
         SignalAttribute attribute = (SignalAttribute) attributes.get(i);

         if (attribute.getName().equals("path"))
         {
            assertTrue(attribute.getValue().trim().endsWith("Pass"));
         }
      }
   }
}