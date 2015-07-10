/* COPYRIGHT-ENEA-SRC-R1 *
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

package com.ose.system.service.monitor.signal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.ose.gateway.Signal;
import com.ose.gateway.SignalInputStream;
import com.ose.gateway.SignalOutputStream;
import com.ose.system.service.monitor.MonitorTag;

public class MonitorResumeScopeRequest extends Signal
{
   public static final int SIG_NO = 39132;

   public int scopeType;     // U32
   public int scopeId;       // U32
   public MonitorTag[] tags; // MonitorTag[]

   public MonitorResumeScopeRequest()
   {
      super(SIG_NO);
   }

   public MonitorResumeScopeRequest(int scopeType,
                                    int scopeId,
                                    MonitorTag[] tags)
   {
      super(SIG_NO);
      this.scopeType = scopeType;
      this.scopeId = scopeId;
      this.tags = tags;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int tagsCount;

      scopeType = in.readS32();
      scopeId = in.readS32();

      tagsCount = in.readS32();
      if (tagsCount > 0)
      {
         List tagsList = new ArrayList(tagsCount);
         for (int i = 0; i < tagsCount; i++)
         {
            short type = in.readS16();
            short count = in.readS16();
            byte[] args = null;
            if (count > 1)
            {
               args = in.readS8Array((count - 1) * 4);
               i += count - 1;
            }
            tagsList.add(new MonitorTag(type, args));
         }
         tags = (MonitorTag[]) tagsList.toArray(new MonitorTag[0]);
      }
      else
      {
         tags = null;
      }
   }

   protected void write(SignalOutputStream out) throws IOException
   {
      out.writeS32(scopeType);
      out.writeS32(scopeId);

      if ((tags == null) || (tags.length == 0))
      {
         out.writeS32(0); // Zero tagsCount
         out.writeS32(0); // Dummy MonitorTag
      }
      else
      {
         int tagsCount = 0;
         for (int i = 0; i < tags.length; i++)
         {
            tagsCount += tags[i].getCount();
         }
         out.writeS32(tagsCount);
         for (int i = 0; i < tags.length; i++)
         {
            MonitorTag tag = tags[i];
            Object args = tag.getArgs();
            out.writeS16(tag.getType());
            out.writeS16(tag.getCount());
            if (args == null)
            {
               continue;
            }
            else if (args instanceof Object[])
            {
               Object[] array = (Object[]) args;
               for (int j = 0; j < array.length; j++)
               {
                  writeMonitorTagArg(out, array[j]);
               }
            }
            else
            {
               writeMonitorTagArg(out, args);
            }
         }
      }
   }

   private void writeMonitorTagArg(SignalOutputStream out, Object arg)
      throws IOException
   {
      if (arg instanceof Integer)
      {
         Integer num = (Integer) arg;
         out.writeS32(num.intValue());
      }
      else if (arg instanceof String)
      {
         String str = (String) arg;
         out.writeString(str);
         out.align(4);
      }
      else
      {
         throw new IOException("Invalid monitor tag arguments");
      }
   }
}
