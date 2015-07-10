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
package com.ose.system.service.pm.signal;

public class PmLoadModuleSectionInfoRequest extends
      PmLoadModuleSectionInfoRequestSignal
{
   public static final int SIG_NO = 36218;
   
   public PmLoadModuleSectionInfoRequest()
   {
      super(SIG_NO);
   }

   public PmLoadModuleSectionInfoRequest(String install_handle,
                                         int section_interval_start,
                                         int section_interval_end)
   {
      super(SIG_NO);
      this.install_handle = install_handle;
      this.section_interval_start = section_interval_start;
      this.section_interval_end = section_interval_end;
   }
}
