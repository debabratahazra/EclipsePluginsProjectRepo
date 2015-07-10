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

package com.ose.system.service.pm;

public interface ProgramManagerListener
{
   public void exceptionThrown(Throwable e);

   public void programManagerKilled();

   public void installLoadModuleReply(int status, String install_handle);

   public void uninstallLoadModuleReply(int status, String install_handle);

   public void loadModuleInstallHandlesReply(int status,
                                             String[] install_handles);

   public void loadModuleInfoReply(int status,
                                   String install_handle,
                                   long entrypoint,
                                   int options,
                                   long text_base,
                                   long text_size,
                                   long data_base,
                                   long data_size,
                                   int no_of_sections,
                                   int no_of_instances,
                                   String file_format,
                                   String file_name);

   public void createProgramReply(int status,
                                  int domain,
                                  String install_handle,
                                  int progpid,
                                  int main_bid);

   public void startProgramReply(int status, int progpid);

   public void killProgramReply(int status, int progpid);
   
   public void programInfoReply(int status,
                                 String install_handle,
                                 int progpid,
                                 int domain,
                                 int main_block,
                                 int main_process,
                                 long pool_base,
                                 long pool_size,
                                 int uid,
                                 int state,
                                 boolean extended_info,
                                 int segpid,
                                 int stk_poolid,
                                 int sig_poolid,
                                 long sig_pool_base,
                                 long sig_pool_size,
                                 int heap);

   public void getProgramPidReply(int status, int pid, int progpid);
}
