/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2008 by Enea Software AB.
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

package com.ose.system.service.tracepoint.signal;

import java.io.IOException;

import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;

public class MonitorTracepointGetAnnotationsReply extends Signal
{

   public static final int SIG_NO = 40772;

   public int tracepointId; // U32
   public int status; // U32
   public int noOfAnnotations; // U32
   public int annotationsSize; // U32
   public String[] annotations; // U32

   public MonitorTracepointGetAnnotationsReply()
   {
      super(SIG_NO);
   }

   protected void read(SignalInputStream in) throws IOException
   {
      tracepointId = in.readS32();
      status = in.readS32();
      noOfAnnotations = in.readS32();
      annotationsSize = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      annotations = new String[noOfAnnotations];
      for (int i = 0; i < noOfAnnotations; i++)
      {
         annotations[i] = in.readString();
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(tracepointId);
      out.writeS32(status);
      out.writeS32(noOfAnnotations);
      out.writeS32(annotationsSize);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      for (int i = 0; i < noOfAnnotations; i++)
      {
         out.writeString(annotations[i]);
      }
   }

}
