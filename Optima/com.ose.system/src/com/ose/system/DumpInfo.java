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

package com.ose.system;

/**
 * This class contains information about an OSE dump. A DumpInfo object is
 * immutable and its content is a snapshot at the time of the retrieving call.
 *
 * @see com.ose.system.Target#getDumps(IProgressMonitor, int, int)
 */
public class DumpInfo
{
   /* Target. */
   private final Target target;

   /* Dump info. */
   private final int id;
   private final long size;
   private final long seconds;
   private final int microSeconds;

   /**
    * Create a new dump info object.
    *
    * @param target        the target.
    * @param id            the dump ID.
    * @param size          the dump size.
    * @param seconds       the number of seconds.
    * @param microSeconds  the number of microseconds.
    */
   DumpInfo(Target target,
            int id,
            int size,
            int seconds,
            int microSeconds)
   {
      if (target == null)
      {
         throw new NullPointerException();
      }
      this.target = target;
      this.id = id;
      this.size = 0xFFFFFFFFL & size;
      this.seconds = 0xFFFFFFFFL & seconds;
      this.microSeconds = microSeconds;
   }

   /**
    * Return the parent target object.
    *
    * @return the parent target object.
    */
   public Target getTarget()
   {
      return target;
   }

   /**
    * Return the ID of this dump.
    *
    * @return the ID of this dump.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Return the size of this dump in bytes.
    *
    * @return the size of this dump in bytes.
    */
   public long getSize()
   {
      return size;
   }

   /**
    * Return the time stamp of this dump in seconds since
    * January 1, 1970, 00:00:00.
    *
    * @return the time stamp of this dump.
    */
   public long getSeconds()
   {
      return seconds;
   }

   /**
    * Return the number of microseconds since getSeconds().
    *
    * @return the number of microseconds since getSeconds().
    */
   public int getMicroSeconds()
   {
      return microSeconds;
   }

   /**
    * Return a string representation of this dump info object. The returned
    * string is of the form "&lt;dump-id&gt;", where dump-id is in hexadecimal
    * form.
    *
    * @return a string representation of this dump info object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return ("0x" + Integer.toHexString(id).toUpperCase());
   }
}
