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

package com.ose.gateway;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Objects of the SignalRegistry class hold a list of OSE signal numbers and
 * their corresponding signal classes. This list is needed in Gateway.receive()
 * methods to create an object of the correct signal class when an OSE signal
 * is received.
 */
public final class SignalRegistry
{
   /** Contains a mapping from OSE signal numbers to signal classes. */
   private Hashtable sigTable = new Hashtable();

   /** The signal select list, or null if no valid list is available. */
   private int[] sigSelectList = null;

   /**
    * Create a new SignalRegistry object.
    */
   public SignalRegistry() {}

   /**
    * Add a signal class to this signal registry. The class must be declared
    * public, extend Signal, and define a public default constructor.
    *
    * @param sigClass  the signal class to be added.
    */
   public void add(Class sigClass)
   {
      // Old list not valid any more. Delete it.
      sigSelectList = null;

      // sigClass is added to sigTable with sigNo as key.
      int sigNo = classToSigNo(sigClass);
      Object o = sigTable.put(new Integer(sigNo), sigClass);
      if (o != null)
      {
         // Another signal class with the same
         // signal number was already registered.
         throw new RuntimeException("Duplicated signal number in " +
                                    sigClass.getName());
      }
   }

   /**
    * Return the OSE signal number from a Signal derived class.
    *
    * @param sigClass  the signal class.
    * @return the corresponding OSE signal number.
    */
   static int classToSigNo(Class sigClass)
   {
      try
      {
         Signal sig = (Signal) sigClass.newInstance();
         return sig.getSigNo();
      }
      catch (InstantiationException e)
      {
         // The signal class did not have a default constructor.
         throw new IllegalArgumentException("Default constructor missing in " +
                                            sigClass.getName());
      }
      catch (IllegalAccessException e)
      {
         // The signal class did not have a public default constructor.
         throw new IllegalArgumentException("Default constructor not public in " +
                                            sigClass.getName());
      }
      catch (ClassCastException e)
      {
         // The signal class did not extend Signal.
         throw new IllegalArgumentException(sigClass.getName() +
                                            " do not extend com.ose.gateway.Signal");
      }
   }

   /**
    * Get the signal select list for this signal registry.
    *
    * The first element contains the number of subsequent elements. The rest
    * of the elements, if any, contain the OSE signal numbers of all signal
    * classes in this signal registry. The signal select list corresponds to
    * an include signals type selection or, if the signal registry is empty,
    * an any signal type selection.
    *
    * @return the signal select list for this signal registry.
    */
   int[] getSigSelectList()
   {
      if (sigSelectList == null)
      {
         // There is no valid sigSelectList available.
         // Create a new sigSelectList by searching sigTable.
         int sigCount = sigTable.size();
         sigSelectList = new int[sigCount + 1];
         sigSelectList[0] = sigCount;
         int i = 1;
         for (Enumeration e = sigTable.keys(); e.hasMoreElements();)
         {
            sigSelectList[i++] = ((Integer) e.nextElement()).intValue();
         }
      }
      return sigSelectList;
   }

   /**
    * Get the signal class that is registered with the given OSE signal
    * number in this signal registry.
    *
    * @param sigNo  an OSE signal number.
    * @return the corresponding signal class, or null if it has not been
    * registered.
    */
   public Class getSignalClass(int sigNo)
   {
      return (Class) sigTable.get(new Integer(sigNo));
   }
}
