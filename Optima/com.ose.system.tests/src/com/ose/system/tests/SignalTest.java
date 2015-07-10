/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2005-2007 by Enea Software AB.
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

package com.ose.system.tests;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.ose.system.service.pm.LoadModuleSectionInfo;
import com.ose.system.service.pm.signal.PmLoadModuleInfoReply;
import com.ose.system.service.pm.signal.PmLoadModuleSectionInfoReply;
import com.ose.system.service.pm.signal.PmProgramInfoReply;

public class SignalTest
{
   @Test
   public void testPmLoadModuleInfoReply() throws IOException
   {
      PmLoadModuleInfoReply before = new PmLoadModuleInfoReply();
      before.status = 1;
      before.install_handle = "String 1";
      before.entrypoint = 2;
      before.options = 3;
      before.text_base = 4;
      before.text_size = 5;
      before.data_base = 8;
      before.data_size = 9;
      before.no_of_sections = 6;
      before.no_of_instances = 7;
      before.file_format = "String 2";
      before.file_name = "String 3";
      
      byte[] buffer = before.javaToOse(true);
      PmLoadModuleInfoReply after = new PmLoadModuleInfoReply();
      after.oseToJava(buffer, true);
      
      assertEquals(before.entrypoint, after.entrypoint);
      assertEquals(before.text_base, after.text_base);
      assertEquals(before.text_size, after.text_size);
      assertEquals(before.data_base, after.data_base);
      assertEquals(before.data_size, after.data_size);
   }
   
   @Test
   public void testPmLoadModuleSectionReply() throws IOException
   {
      PmLoadModuleSectionInfoReply before = new PmLoadModuleSectionInfoReply();
      before.status = 1;
      before.install_handle = "String 1";
      before.section_interval_start = 1;
      before.section_interval_end = 1;
      LoadModuleSectionInfo[] sections = new LoadModuleSectionInfo[1];
      LoadModuleSectionInfo info = new LoadModuleSectionInfo();
      info.section_attr = 1;
      info.section_base = 1;
      info.section_name = "1";
      info.section_options = 1;
      info.section_size = 5;
      sections[0] = info;
      before.sections = sections;

      byte[] buffer = before.javaToOse(true);
      PmLoadModuleSectionInfoReply after = new PmLoadModuleSectionInfoReply();
      after.oseToJava(buffer, true);
      
      assertEquals(before.sections.length, after.sections.length);
   }
   
   @Test
   public void testPmProgramInfoReply() throws IOException
   {
      PmProgramInfoReply before = new PmProgramInfoReply();
      before.status = 1;
      before.install_handle = "String 1";
      before.progpid = 2;
      before.domain = 3;
      before.main_block = 4;
      before.main_process = 5;
      before.pool_base = 6;
      before.pool_size = 7;
      before.uid = 8;
      before.state = 9;
      before.extended_info = true;
      before.segpid = 10;
      before.stk_poolid = 11;
      before.sig_poolid = 12;
      before.sig_pool_base = 13;
      before.sig_pool_size = 14;
      before.heap = 15;
      
      //before.init(16, 7, 88);
      byte[] buffer = before.javaToOse(true);
      PmProgramInfoReply after = new PmProgramInfoReply();
      after.init(16,17, 88);
      after.oseToJava(buffer, true);
      
      assertEquals(before.pool_base, after.pool_base);
      assertEquals(before.pool_size, after.pool_size);
      assertEquals(before.sig_pool_base, after.sig_pool_base);
      assertEquals(before.sig_pool_size, after.sig_pool_size);
   }
}
