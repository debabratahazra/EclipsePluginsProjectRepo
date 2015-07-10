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

package com.ose.event.ui.preferences;

import java.io.File;

class SymbolDbReference
{
   private File file;

   private boolean active;

   SymbolDbReference(File file, boolean active)
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }
      this.file = file;
      this.active = active;
   }

   public File getFile()
   {
      return file;
   }

   public void setFile(File file)
   {
      if (file == null)
      {
         throw new IllegalArgumentException();
      }
      this.file = file;
   }

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }
}
