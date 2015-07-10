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

/**
 * Objects of the SignalSelect class represent selections of OSE signal numbers
 * that are used to describe which signals are wanted in Gateway.receive()
 * methods.
 * <p>
 * There are three kinds of possible selections:<br>
 * <ul>
 *   <li> MODE_ANY_SIGNAL: Any signal is wanted.
 *   <li> MODE_INCLUDE_SIGNALS: A list of wanted signals.
 *   <li> MODE_EXCLUDE_SIGNALS: A list of unwanted signals; all other signals
 *        are wanted.
 * </ul>
 */
public final class SignalSelect
{
   /** Selection mode representing an any signal type selection. */
   public static final int MODE_ANY_SIGNAL = 0;

   /** Selection mode representing an include signals type selection. */
   public static final int MODE_INCLUDE_SIGNALS = 1;

   /** Selection mode representing an exclude signals type selection. */
   public static final int MODE_EXCLUDE_SIGNALS = 2;

   /** An any signal type selection object that is always available. */
   public static final SignalSelect ANY_SIGNAL = new SignalSelect();

   /**
    * The signal select list. The first element contains the number of
    * subsequent elements, and is positive for an include signals type
    * selection, negative for an exclude signals type selection, and 0
    * for an any signal type selection. The rest of the elements, if any,
    * contain the OSE signal numbers used in the selection.
    */
   int[] sigSelectList;

   /**
    * Create a new SignalSelect object with an any signal type selection.
    */
   public SignalSelect()
   {
      sigSelectList = new int[1];
   }

   /**
    * Create a new SignalSelect object with the given selection mode and an
    * array of OSE signal numbers to be used in the selection.
    *
    * @param mode     one of MODE_ANY_SIGNAL, MODE_INCLUDE_SIGNALS, or
    *                 MODE_EXCLUDE_SIGNALS.
    * @param signals  an array of OSE signal numbers to be used in the
    *                 selection. Not used in MODE_ANY_SIGNAL.
    */
   public SignalSelect(int mode, int[] signals)
   {
      int sigCount;
      int firstElement;

      switch (mode)
      {
      case MODE_ANY_SIGNAL:
         sigCount = 0;
         firstElement = 0;
         break;
      case MODE_INCLUDE_SIGNALS:
         sigCount = signals.length;
         firstElement = sigCount;
         break;
      case MODE_EXCLUDE_SIGNALS:
         sigCount = signals.length;
         firstElement = -sigCount;
         break;
      default:
         throw new IllegalArgumentException("Invalid signal selection mode.");
      }

      sigSelectList = new int[sigCount + 1];
      sigSelectList[0] = firstElement;

      for (int i = 0; i < sigCount; i++)
      {
         sigSelectList[i + 1] = signals[i];
      }
   }

   /**
    * Create a new SignalSelect object with the given selection mode and an
    * array of Signal derived classes to be used in the selection.
    * <p>
    * Note that this constructor is less efficient than the constructor
    * based on OSE signal numbers.
    *
    * @param mode     one of MODE_ANY_SIGNAL, MODE_INCLUDE_SIGNALS, or
    *                 MODE_EXCLUDE_SIGNALS.
    * @param signals  an array of Signal derived classes to be used in
    *                 the selection. Not used in MODE_ANY_SIGNAL.
    */
   public SignalSelect(int mode, Class[] signals)
   {
      int sigCount;
      int firstElement;

      switch (mode)
      {
      case MODE_ANY_SIGNAL:
         sigCount = 0;
         firstElement = 0;
         break;
      case MODE_INCLUDE_SIGNALS:
         sigCount = signals.length;
         firstElement = sigCount;
         break;
      case MODE_EXCLUDE_SIGNALS:
         sigCount = signals.length;
         firstElement = -sigCount;
         break;
      default:
         throw new IllegalArgumentException("Invalid signal selection mode.");
      }

      sigSelectList = new int[sigCount + 1];
      sigSelectList[0] = firstElement;

      for (int i = 0; i < sigCount; i++)
      {
         sigSelectList[i + 1] = SignalRegistry.classToSigNo(signals[i]);
      }
   }
}
