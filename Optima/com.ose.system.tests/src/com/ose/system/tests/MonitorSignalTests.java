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
package com.ose.system.tests;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import com.ose.system.service.monitor.signal.MonitorAllocFreeBufInfoSignal;
import com.ose.system.service.monitor.signal.MonitorAllocNotify;
import com.ose.system.service.monitor.signal.MonitorErrorInfoSignal;
import com.ose.system.service.monitor.signal.MonitorErrorNotify;
import com.ose.system.service.monitor.signal.MonitorExtendedAllocFreeBufInfo;
import com.ose.system.service.monitor.signal.MonitorExtendedErrorInfo;
import com.ose.system.service.monitor.signal.MonitorProcessInfoSignal;
import com.ose.system.service.monitor.signal.MonitorProcessNotify;
import com.ose.system.service.monitor.signal.MonitorSendNotify;
import com.ose.system.service.monitor.signal.MonitorSendReceiveInfoSignal;

public class MonitorSignalTests
{

   @Test
   public void testMonitorProcessInfoSignal() throws IOException
   {
      MonitorProcessInfoSignal before = new MonitorProcessNotify();
      before.pid = 1;
      before.bid = 2;
      before.sid = 3;
      before.type = 4;
      before.entrypoint = 5;
      before.properties = 6;
      before.reserved0 = 7;
      before.name = "String 1";
      
      byte[] buffer = before.javaToOse(true);
      MonitorProcessInfoSignal after = new MonitorProcessNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(before.reserved0, after.reserved0);
   }
   
   @Test
   public void testMonitorProcessInfoSignal32Bit() throws IOException
   {
      MonitorProcessInfoSignal before = new MonitorProcessNotify();
      before.pid = 1;
      before.bid = 2;
      before.sid = 3;
      before.type = 4;
      before.entrypoint = 5;
      before.properties = 6;
      before.name = "String 1";
      
      byte[] buffer = before.javaToOse(true);
      MonitorProcessInfoSignal after = new MonitorProcessNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(0, after.reserved0);
   }
   
   @Test
   public void testMonitorSendReceiveInfoSignal() throws IOException
   {
      MonitorSendReceiveInfoSignal before = new MonitorSendNotify();
      before.signalNumber = 1;
      before.signalSender = 2;
      before.signalAddressee = 3;
      before.signalSize = 4;
      before.signalId = 5;
      before.signalAddress = 6;
      before.lineNumber = 7;
      before.euId = 8;
      before.reserved1 = 9;
      before.fileName = "String 1";
      before.signalData = "Array 1".getBytes();
      
      byte[] buffer = before.javaToOse(true);
      MonitorSendReceiveInfoSignal after = new MonitorSendNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(before.reserved1, after.reserved1);
   }
   
   @Test
   public void testMonitorSendReceiveInfoSignal32Bit() throws IOException
   {
      MonitorSendReceiveInfoSignal before = new MonitorSendNotify();
      before.signalNumber = 1;
      before.signalSender = 2;
      before.signalAddressee = 3;
      before.signalSize = 4;
      before.signalId = 5;
      before.signalAddress = 6;
      before.lineNumber = 7;
      before.euId = 8;
      before.fileName = "String 1";
      before.signalData = "Array 1".getBytes();
      
      byte[] buffer = before.javaToOse(true);
      MonitorSendReceiveInfoSignal after = new MonitorSendNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(0, after.reserved1);
   }
   
   @Test
   public void testMonitorExtendedErrorInfo() throws IOException
   {
      MonitorErrorInfoSignal before = new MonitorErrorNotify();
      before.reference = 1;
      before.tick = 2;
      before.ntick = 3;
      before.sec = 4;
      before.sectick = 5;
      before.from = 6;
      before.exec = true;
      before.error = 7;
      before.extra = 8;
      before.lineNumber = 9;
      before.euId = 10;
      before.fileName = "String 1";
      
      MonitorExtendedErrorInfo extended = new MonitorExtendedErrorInfo();
      extended.error = 11;
      extended.extra = 12;
      
      before.monitorExtendedErrorInfo = extended;
      
      byte[] buffer = before.javaToOse(true);
      MonitorErrorInfoSignal after = new MonitorErrorNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(before.monitorExtendedErrorInfo.error, after.monitorExtendedErrorInfo.error);
      assertEquals(before.monitorExtendedErrorInfo.extra, after.monitorExtendedErrorInfo.extra);
   }
   
   @Test
   public void testMonitorErrorNoExtended() throws IOException
   {
      MonitorErrorInfoSignal before = new MonitorErrorNotify();
      before.reference = 1;
      before.tick = 2;
      before.ntick = 3;
      before.sec = 4;
      before.sectick = 5;
      before.from = 6;
      before.exec = true;
      before.error = 7;
      before.extra = 8;
      before.lineNumber = 9;
      before.euId = 10;
      before.fileName = "String 1";
      
      byte[] buffer = before.javaToOse(true);
      MonitorErrorInfoSignal after = new MonitorErrorNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(null, after.monitorExtendedErrorInfo);
   }
   
   @Test
   public void testExtendedErrorInfoToString()
   {
      MonitorExtendedErrorInfo info = new MonitorExtendedErrorInfo();
      info.error = 1;
      info.extra = 2;
      
      assertEquals("error: 1, extra: 2", info.toString());
   }
   
   @Test
   public void testMonitorExtendedAllocFreeBuf() throws IOException
   {
      MonitorAllocFreeBufInfoSignal before = new MonitorAllocNotify();
      before.reference = 1;
      before.tick = 2;
      before.ntick = 3;
      before.sec = 4;
      before.sectick = 5;
      before.from = 6;
      before.signalNumber = 7;
      before.signalAddress = 8;
      before.signalId = 9;
      before.lineNumber = 10;
      before.euId = 11;
      before.fileName = "String 1";
      
      MonitorExtendedAllocFreeBufInfo extended = new MonitorExtendedAllocFreeBufInfo();
      extended.signalAddress = 12;
      
      before.monitorExtendedAllocFreeBufInfo = extended;
      
      byte[] buffer = before.javaToOse(true);
      MonitorAllocFreeBufInfoSignal after = new MonitorAllocNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(before.monitorExtendedAllocFreeBufInfo.signalAddress, after.monitorExtendedAllocFreeBufInfo.signalAddress);
   }
   
   @Test
   public void testMonitorAllocFreeBufNoExtend() throws IOException
   {
      MonitorAllocFreeBufInfoSignal before = new MonitorAllocNotify();
      before.reference = 1;
      before.tick = 2;
      before.ntick = 3;
      before.sec = 4;
      before.sectick = 5;
      before.from = 6;
      before.signalNumber = 7;
      before.signalAddress = 8;
      before.signalId = 9;
      before.lineNumber = 10;
      before.euId = 11;
      before.fileName = "String 1";
      
      byte[] buffer = before.javaToOse(true);
      MonitorAllocFreeBufInfoSignal after = new MonitorAllocNotify();
      after.oseToJava(buffer, true);
      
      assertEquals(null, after.monitorExtendedAllocFreeBufInfo);
   }
   
   @Test
   public void testExtendedAllocFreeBufInfoToString()
   {
      MonitorExtendedAllocFreeBufInfo info = new MonitorExtendedAllocFreeBufInfo();
      info.signalAddress = 1;
      
      assertEquals("signalAddress: 1", info.toString());
   }
}
