/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.dump;

/**
 * This interface provides ID constants for the IFF format.
 */
public class IffId
{
   public static final IffId FORM = new IffId('F', 'O', 'R', 'M');
   public static final IffId CAT_ = new IffId('C', 'A', 'T', ' ');
   public static final IffId LIST = new IffId('L', 'I', 'S', 'T');
   public static final IffId PROP = new IffId('P', 'R', 'O', 'P');
   public static final IffId FILL = new IffId(' ', ' ', ' ', ' ');

   private final long id;

   protected IffId(char a, char b, char c, char d)
   {
      this(((long) a << 24) + ((long) b << 16) + ((long) c << 8) + ((long) d));
   }

   protected IffId(long id)
   {
      this.id = id;
   }

   public long getId()
   {
      return id;
   }

   public String getIdString()
   {
      String s = "";
      for (int i = 0; i < 4; i++)
      {
         long b = (id >> (24 - i * 8)) & 0xFF;
         s += (char) b;
      }
      return s;
   }

   public String toString()
   {
      return "0x" + Long.toHexString(id).toUpperCase() + " (" + getIdString() + ")";
   }

   public boolean equals(Object obj)
   {
      boolean result = false;
      if (obj instanceof IffId)
      {
         IffId other = (IffId) obj;
         result = (id == other.id);
      }
      return result;
   }
}
