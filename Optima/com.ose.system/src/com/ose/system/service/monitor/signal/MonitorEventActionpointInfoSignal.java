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

public abstract class MonitorEventActionpointInfoSignal extends Signal
{
   public int state;         // U32
   public int fromType;      // U32
   public int fromId;        // U32
   public int toType;        // U32
   public int toId;          // U32
   public boolean not;       // U32
   public int action;        // U32
   public int interceptType; // U32
   public int interceptId;   // U32
   public int parameter;     // U32
   public int client;        // U32
   public int id;            // U32
   public int count;         // U32
   public MonitorTag[] tags; // MonitorTag[]

   public MonitorEventActionpointInfoSignal(int sigNo)
   {
      super(sigNo);
   }

   public MonitorEventActionpointInfoSignal(int sigNo,
                                            int state,
                                            int fromType,
                                            int fromId,
                                            int toType,
                                            int toId,
                                            boolean not,
                                            int action,
                                            int interceptType,
                                            int interceptId,
                                            int parameter,
                                            int client,
                                            int id,
                                            int count,
                                            MonitorTag[] tags)
   {
      super(sigNo);
      this.state = state;
      this.fromType = fromType;
      this.fromId = fromId;
      this.toType = toType;
      this.toId = toId;
      this.not = not;
      this.action = action;
      this.interceptType = interceptType;
      this.interceptId = interceptId;
      this.parameter = parameter;
      this.client = client;
      this.id = id;
      this.count = count;
      this.tags = tags;
   }

   protected void read(SignalInputStream in) throws IOException
   {
      int tagsCount;

      state = in.readS32();
      fromType = in.readS32();
      fromId = in.readS32();
      toType = in.readS32();
      toId = in.readS32();
      not = in.readBoolean();
      action = in.readS32();
      interceptType = in.readS32();
      interceptId = in.readS32();
      parameter = in.readS32();
      client = in.readS32();
      id = in.readS32();
      count = in.readS32();
      in.readS32(); // Unused U32 reserved0
      in.readS32(); // Unused U32 reserved1
      in.readS32(); // Unused U32 reserved2

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
      out.writeS32(state);
      out.writeS32(fromType);
      out.writeS32(fromId);
      out.writeS32(toType);
      out.writeS32(toId);
      out.writeBoolean(not);
      out.writeS32(action);
      out.writeS32(interceptType);
      out.writeS32(interceptId);
      out.writeS32(parameter);
      out.writeS32(client);
      out.writeS32(id);
      out.writeS32(count);
      out.writeS32(0); // Unused U32 reserved0
      out.writeS32(0); // Unused U32 reserved1
      out.writeS32(0); // Unused U32 reserved2

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
