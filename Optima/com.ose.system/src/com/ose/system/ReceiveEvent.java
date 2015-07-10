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

package com.ose.system;

/**
 * This class represents a receive event from an OSE target. A receive event is
 * reported when a signal is received by a process.
 *
 * @see com.ose.system.TargetEvent
 */
public class ReceiveEvent extends TargetEvent
{
   private static final long serialVersionUID = 1L;

   private final ProcessInfo senderProcess;
   private final ProcessInfo addresseeProcess;
   private final int signalNumber;
   private final int signalId;
   private final int signalAddress;
   private final int signalSize;
   private final byte[] signalData;
   private String formattedSignalData;
   private final short executionUnit;

   /**
    * Create a new receive event object.
    *
    * @param target           the target on which the event initially occurred.
    * @param tickLength       the tick length of the target in microseconds.
    * @param reference        the reference.
    * @param tick             the tick.
    * @param ntick            the ntick.
    * @param sec              the seconds.
    * @param sectick          the tick since seconds.
    * @param signalNumber     the signal number.
    * @param signalSender     the sender process.
    * @param signalAddressee  the addressee process.
    * @param signalSize       the signal size.
    * @param signalAddress    the signal buffer address.
    * @param signalId         the signal id.
    * @param lineNumber       the line number.
    * @param euId             the execution unit.
    * @param fileName         the filename.
    * @param signalData       the signal data.
    */
   ReceiveEvent(Object target,
                int tickLength,
                int reference,
                int tick,
                int ntick,
                int sec,
                int sectick,
                int signalNumber,
                ProcessInfo signalSender,
                ProcessInfo signalAddressee,
                int signalSize,
                int signalAddress,
                int signalId,
                int lineNumber,
                short euId,
                String fileName,
                byte[] signalData)
   {
      super(target,
            tickLength,
            "Receive Event",
            reference,
            tick,
            ntick,
            sec,
            sectick,
            lineNumber,
            fileName);
      if ((signalSender == null) || (signalAddressee == null))
      {
         throw new NullPointerException();
      }
      this.senderProcess = signalSender;
      this.addresseeProcess = signalAddressee;
      this.signalNumber = signalNumber;
      this.signalId = signalId;
      this.signalAddress = signalAddress;
      this.signalSize = signalSize;
      this.signalData = signalData;
      this.formattedSignalData = null;
      this.executionUnit = euId;
   }

   /**
    * Create a new receive event object for off-line usage.
    * The target can be any object.
    *
    * @param target               the target on which the event initially
    * occurred.
    * @param tickLength           the tick length of the target in microseconds.
    * @param reference            the reference.
    * @param tick                 the tick.
    * @param ntick                the ntick.
    * @param sec                  the seconds.
    * @param sectick              the tick since seconds.
    * @param signalNumber         the signal number.
    * @param signalSender         the sender process.
    * @param signalAddressee      the addressee process.
    * @param signalSize           the signal size.
    * @param signalAddress        the signal buffer address.
    * @param signalId             the signal id.
    * @param lineNumber           the line number.
    * @param euId                 the execution unit.
    * @param fileName             the filename.
    * @param signalData           the signal data.
    * @param formattedSignalData  the formatted signal data or null if not
    * available.
    * @return a new receive event object for off-line usage.
    */
   public static ReceiveEvent getInstance(Object target,
                                          int tickLength,
                                          int reference,
                                          int tick,
                                          int ntick,
                                          int sec,
                                          int sectick,
                                          int signalNumber,
                                          ProcessInfo signalSender,
                                          ProcessInfo signalAddressee,
                                          int signalSize,
                                          int signalAddress,
                                          int signalId,
                                          int lineNumber,
                                          short euId,
                                          String fileName,
                                          byte[] signalData,
                                          String formattedSignalData)
   {
      ReceiveEvent receiveEvent = new ReceiveEvent(target,
                                                   tickLength,
                                                   reference,
                                                   tick,
                                                   ntick,
                                                   sec,
                                                   sectick,
                                                   signalNumber,
                                                   signalSender,
                                                   signalAddressee,
                                                   signalSize,
                                                   signalAddress,
                                                   signalId,
                                                   lineNumber,
                                                   euId,
                                                   fileName,
                                                   signalData);
      receiveEvent.formattedSignalData = formattedSignalData;
      return receiveEvent;
   }

   /**
    * Return the process that sent the signal.
    *
    * @return the process that sent the signal.
    */
   public ProcessInfo getSenderProcess()
   {
      return senderProcess;
   }

   /**
    * Return the process that received the signal.
    *
    * @return the process that received the signal.
    */
   public ProcessInfo getAddresseeProcess()
   {
      return addresseeProcess;
   }

   /**
    * Return the signal number of the signal.
    *
    * @return the signal number of the signal.
    */
   public int getSignalNumber()
   {
      return signalNumber;
   }

   /**
    * Return the id of the signal or 0 if unavailable.
    *
    * @return the id of the signal or 0 if unavailable.
    */
   public int getSignalId()
   {
      return signalId;
   }

   /**
    * Return the address of the signal buffer or 0 if unavailable.
    *
    * @return the address of the signal buffer or 0 if unavailable.
    */
   public int getSignalAddress()
   {
      return signalAddress;
   }

   /**
    * Return the requested size of the signal in bytes or 0 if unavailable.
    *
    * @return the requested size of the signal in bytes or 0 if unavailable.
    */
   public int getSignalSize()
   {
      return signalSize;
   }

   /**
    * Return the data of the signal as an array of bytes. The array must not be
    * modified by a client. If the signal has no data an empty byte array is
    * returned.
    * <p>
    * Note that the signal data may have been truncated. Use getSignalSize() to
    * get the actual signal size including any signal data.
    *
    * @return the data of the signal as an array of bytes.
    */
   public byte[] getSignalData()
   {
      return signalData;
   }

   /**
    * Return the formatted data of the signal or null if not available.
    *
    * @return the formatted data of the signal or null if not available.
    */
   public String getFormattedSignalData()
   {
      return formattedSignalData;
   }

   /**
    * Return the execution unit where the event originated from.
    *
    * @return the execution unit where the event originated from.
    */
   public short getExecutionUnit()
   {
      return executionUnit;
   }

   /**
    * Return a string representation of this receive event object.
    *
    * @return a string representation of this receive event object.
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return (super.toString() +
              ", Sender process: " + senderProcess +
              ", Addressee process: " + addresseeProcess +
              ", Signal number: " + signalNumber +
              ", Signal id: " + signalId +
              ", Signal buffer address: " + signalAddress +
              ", Signal size: " + signalSize +
              ", Execution unit: " + executionUnit);
   }
}
