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

import java.util.EventListener;

/**
 * TargetListener defines the interface for a client object that listens to
 * events occurring on an OSE target system.
 *
 * @see java.util.EventListener
 * @see com.ose.system.TargetEvent
 */
public interface TargetListener extends EventListener
{
   /**
    * Invoked when a signal was sent by a process.
    *
    * @param event  the send event.
    */
   public void signalSent(SendEvent event);

   /**
    * Invoked when a signal was received by a process.
    *
    * @param event  the receive event.
    */
   public void signalReceived(ReceiveEvent event);

   /**
    * Invoked when a context switch occurred, i.e. when a process was swapped
    * out and another swapped in.
    *
    * @param event  the swap event.
    */
   public void processSwapped(SwapEvent event);

   /**
    * Invoked when a new process was created.
    *
    * @param event  the create event.
    */
   public void processCreated(CreateEvent event);

   /**
    * Invoked when a process was killed.
    *
    * @param event  the kill event.
    */
   public void processKilled(KillEvent event);

   /**
    * Invoked when a process triggered an error.
    *
    * @param event  the error event.
    */
   public void errorCalled(ErrorEvent event);

   /**
    * Invoked when the target was resetted.
    *
    * @param event  the reset event.
    */
   public void targetReset(ResetEvent event);

   /**
    * Invoked when a user event was reported.
    *
    * @param event  the user event.
    */
   public void userReported(UserEvent event);

   /**
    * Invoked when a process was bound to a new execution unit, i.e. when a
    * process was migrated from one execution unit to another.
    *
    * @param event  the bind event.
    */
   public void processBound(BindEvent event);

   /**
    * Invoked when a signal, i.e. pool buffer, was allocated by a process.
    *
    * @param event  the alloc event.
    */
   public void signalAllocated(AllocEvent event);

   /**
    * Invoked when a signal, i.e. pool buffer, was freed by a process.
    *
    * @param event  the free event.
    */
   public void signalFreed(FreeEvent event);

   /**
    * Invoked when an OSE system call timed out.
    *
    * @param event  the timeout event.
    */
   public void systemCallTimedout(TimeoutEvent event);

   /**
    * Invoked when the target has thrown events due to flow control. An unknown
    * number of events are missing.
    *
    * @param event  the loss event.
    */
   public void eventsLost(LossEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a signal was sent
    * by a process.
    *
    * @param event  the send event.
    */
   public void scopeIntercepted(SendEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a signal was
    * received by a process.
    *
    * @param event  the receive event.
    */
   public void scopeIntercepted(ReceiveEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a context switch
    * occurred, i.e. when a process was swapped out and another swapped in.
    *
    * @param event  the swap event.
    */
   public void scopeIntercepted(SwapEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a new process was
    * created.
    *
    * @param event  the create event.
    */
   public void scopeIntercepted(CreateEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a process was
    * killed.
    *
    * @param event  the kill event.
    */
   public void scopeIntercepted(KillEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a process
    * triggered an error.
    *
    * @param event  the error event.
    */
   public void scopeIntercepted(ErrorEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a user event was
    * reported.
    *
    * @param event  the user event.
    */
   public void scopeIntercepted(UserEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a process was
    * bound to a new execution unit, i.e. when a process was migrated from one
    * execution unit to another.
    *
    * @param event  the bind event.
    */
   public void scopeIntercepted(BindEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a signal, i.e.
    * pool buffer, was allocated by a process.
    *
    * @param event  the alloc event.
    */
   public void scopeIntercepted(AllocEvent event);

   /**
    * Invoked when the intercept scope was intercepted because a signal, i.e.
    * pool buffer, was freed by a process.
    *
    * @param event  the free event.
    */
   public void scopeIntercepted(FreeEvent event);

   /**
    * Invoked when the intercept scope was intercepted because an OSE system
    * call timed out.
    *
    * @param event  the timeout event.
    */
   public void scopeIntercepted(TimeoutEvent event);
}
