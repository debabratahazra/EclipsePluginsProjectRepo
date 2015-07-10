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

package com.ose.httpd;

import java.util.EventObject;

public class ProgressEvent extends EventObject
{
   private static final long serialVersionUID = 1L;

   private String file;
   private int progress;

   ProgressEvent(Object source, String file, int progress)
   {
      super(source);
      this.file = file;
      this.progress = progress;
   }

   public String getFile()
   {
      return file;
   }

   public int getProgress()
   {
      return progress;
   }
}
