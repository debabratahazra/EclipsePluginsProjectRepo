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

import java.io.File;
import java.io.IOException;
import junit.framework.TestCase;
import com.ose.sigdb.io.BinaryFile;
import com.ose.sigdb.io.ObjectFile;
import com.ose.sigdb.table.DeltaFormatReader;
import com.ose.sigdb.table.SignalDescription;
import com.ose.sigdb.table.SignalDescriptionTable;
import com.ose.sigdb.table.SignalTableReader;
import com.ose.sigdb.util.EndianConstants;

public class TestSignalAttribute extends TestCase
{
   private ObjectFile objectFile;
   
   public TestSignalAttribute(String name)
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

   public void testGetName() throws Exception
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
      
      Attribute attribute = signal.getAttribute("fd");
      assertEquals("fd", attribute.getName());
      
      attribute = signal.getAttribute("buffer");
      assertEquals("buffer", attribute.getName());
      
      attribute = signal.getAttribute("nbytes");
      assertEquals("nbytes", attribute.getName());
   }

   public void testGetValue() throws IOException
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
      
      Attribute attribute = signal.getAttribute("fd");
      assertEquals("4660", attribute.getValue());
      
      attribute = signal.getAttribute("buffer");
      assertEquals("0x5678", attribute.getValue());
      
      attribute = signal.getAttribute("nbytes");
      assertEquals("0x12345678", attribute.getValue());
   }

   public void testGetType() throws Exception
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
      
      Attribute attribute = signal.getAttribute("fd");
      assertEquals("int", attribute.getType().getName());
      
      attribute = signal.getAttribute("buffer");
      assertEquals("char*", attribute.getType().getName());
      
      attribute = signal.getAttribute("nbytes");
      assertEquals("unsigned int", attribute.getType().getName());
   }
   
   public void testGetSize() throws Exception
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
      
      SignalAttribute attribute = (SignalAttribute) signal.getAttribute("fd");
      assertEquals(4, attribute.getSize());
      
      attribute = (SignalAttribute) signal.getAttribute("buffer");
      assertEquals(4, attribute.getSize());
      
      attribute = (SignalAttribute) signal.getAttribute("nbytes");
      assertEquals(4, attribute.getSize());
   }
   
   public void testGetOffset() throws Exception
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
    
      SignalAttribute attribute = (SignalAttribute) signal.getAttribute("fd");
      assertEquals(0, attribute.getOffset());
      
      attribute = (SignalAttribute) signal.getAttribute("buffer");
      assertEquals(4, attribute.getOffset());
      
      attribute = (SignalAttribute) signal.getAttribute("nbytes");
      assertEquals(8, attribute.getOffset());
   }
}
