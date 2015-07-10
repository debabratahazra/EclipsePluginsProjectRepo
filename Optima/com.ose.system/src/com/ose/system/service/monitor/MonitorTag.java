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

package com.ose.system.service.monitor;

import java.util.ArrayList;
import java.util.List;

public class MonitorTag
{
   private final short type;
   private final short count;
   private final Object args;

   public MonitorTag(short type)
   {
      this(type, null);
   }

   public MonitorTag(short type, Object args)
   {
      this.args = concatenateArgs(args);
      this.count = validateArgs(this.args);
      this.type = type;
   }

   public short getType()
   {
      return type;
   }

   public short getCount()
   {
      return count;
   }

   public Object getArgs()
   {
      return args;
   }

   private static Object concatenateArgs(Object args)
   {
      Object[] array;
      List list = new ArrayList();
      StringBuffer sb = new StringBuffer();

      if (args instanceof Object[])
      {
         array = (Object[]) args;
      }
      else
      {
         return args;
      }

      for (int i = 0; i < array.length; i++)
      {
         Object arg = array[i];
         if (arg instanceof String)
         {
            if (sb.length() > 0)
            {
               sb.append('\0');
            }
            sb.append((String) arg);
         }
         else
         {
            list.add(arg);
            if (sb.length() > 0)
            {
               list.add(sb.toString());
               sb.setLength(0);
            }
         }
      }
      if (sb.length() > 0)
      {
         list.add(sb.toString());
      }

      return list.toArray();
   }

   private static short validateArgs(Object args)
   {
      short count = 1;

      if (args == null)
      {
         // No argument.
      }
      else if (args instanceof Object[])
      {
         // Array of arguments.
         Object[] array = (Object[]) args;
         for (int i = 0; i < array.length; i++)
         {
            count += validateArg(array[i]);
         }
      }
      else
      {
         // Single argument.
         count += validateArg(args);
      }

      return count;
   }

   private static short validateArg(Object arg)
   {
      if (arg instanceof Integer)
      {
         return 1;
      }
      else if (arg instanceof String)
      {
         String str = (String) arg;
         int length = str.length() + 1;
         int padding = (4 - (length & 3)) % 4;
         return (short) ((length + padding) / 4);
      }
      else if (arg instanceof byte[])
      {
         byte[] bytes = (byte[]) arg;
         int length = bytes.length;
         int padding = (4 - (length & 3)) % 4;
         return (short) ((length + padding) / 4);
      }
      else
      {
         throw new IllegalArgumentException("Invalid monitor tag arguments");
      }
   }
}
