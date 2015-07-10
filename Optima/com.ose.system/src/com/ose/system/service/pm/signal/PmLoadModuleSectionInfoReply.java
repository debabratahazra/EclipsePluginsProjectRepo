package com.ose.system.service.pm.signal;

import java.io.IOException;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.pm.LoadModuleSectionInfo;

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
public class PmLoadModuleSectionInfoReply extends
      PmLoadModuleSectionInfoReplySignal
{
   public static final int SIG_NO = 36219;
   
   public PmLoadModuleSectionInfoReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int num_sections;

      status = in.readS32();
      install_handle = in.readString(32);
      section_interval_start = in.readS32();
      section_interval_end = in.readS32();
      num_sections = section_interval_end - section_interval_start + 1;
      if (num_sections > 0)
      {
         sections = new LoadModuleSectionInfo[num_sections];
         for (int i = 0; i < num_sections; i++)
         {
            LoadModuleSectionInfo sectionInfo = new LoadModuleSectionInfo();
            sectionInfo.section_base = in.readS32();
            sectionInfo.section_size = in.readS32();
            sectionInfo.section_attr = in.readS32();
            sectionInfo.section_options = in.readS32();
            sectionInfo.section_name = in.readString(32);
            sections[i] = sectionInfo;
         }
      }
      else
      {
         sections = null;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      int num_sections;

      num_sections = ((sections != null) ? sections.length : 0);
      out.writeS32(status);
      out.writeString(install_handle, 32);
      out.writeS32(section_interval_start);
      out.writeS32(section_interval_end);

      for (int i = 0; i < num_sections; i++)
      {
         LoadModuleSectionInfo sectionInfo = sections[i];
         out.writeS32(PmUtils.longToInt(sectionInfo.section_base));
         out.writeS32(PmUtils.longToInt(sectionInfo.section_size));
         out.writeS32(sectionInfo.section_attr);
         out.writeS32(sectionInfo.section_options);
         out.writeString(sectionInfo.section_name, 32);
      }
   }
}
