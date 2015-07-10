/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2011 by Enea Software AB.
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

package com.ose.perf.ui.launch;

import java.io.IOException;
import com.ose.perf.elf.Elf;
import com.ose.system.LoadModuleInfo;

public class BinaryFileContainer
{
   private static final String DELIMITER = "|";

   private String path;
   private String installHandle;
   private LoadModuleInfo loadModule;
   private Elf elf;

   public BinaryFileContainer(String fileAndInstallHandle)
   {
      int splitIndex = fileAndInstallHandle.indexOf(DELIMITER);
      path = fileAndInstallHandle.substring(0, splitIndex);
      installHandle = fileAndInstallHandle.substring(splitIndex + 1);
   }

   public BinaryFileContainer(String path, String installHandle)
   {
      this.path = path;
      this.installHandle = installHandle;
   }

   public String getPath()
   {
      return (path != null) ? path : "";
   }

   public void setPath(String path)
   {
      this.path = path;
   }

   public String getInstallHandle()
   {
      return (installHandle != null) ? installHandle : "";
   }

   public void setInstallHandle(String installHandle)
   {
      this.installHandle = installHandle;
   }

   public LoadModuleInfo getLoadModule()
   {
      return loadModule;
   }

   public void setLoadModule(LoadModuleInfo loadModule)
   {
      this.loadModule = loadModule;
   }

   public Elf getElf() throws IOException
   {
      if (elf == null)
      {
         if ((loadModule != null) &&
             ((loadModule.getOptions() & LoadModuleInfo.LOAD_MODULE_ABSOLUTE) == 0))
         {
            elf = new Elf(path, loadModule.getTextBaseLong() & 0xFFFFFFFFL);
         }
         else
         {
            elf = new Elf(path);
         }
      }
      return elf;
   }

   public String toString()
   {
      return getPath() + DELIMITER + getInstallHandle();
   }
}
