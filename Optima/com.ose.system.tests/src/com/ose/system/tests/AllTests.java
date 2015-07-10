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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This is a test suite for SystemModelTests that tests the OSE System Model.
 * The order of the tests matter. In order to be able to run them all, you need
 * to connect to target before anything else.
 */
public class AllTests
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("Test suite for com.ose.system");

      /*
       * TODO: The following methods in Target do not have any tests yet:
       * installLoadModule()
       * uninstallLoadModule()
       * createProgram()
       * killProgram()
       */

      suite.addTest(new SystemModelTests("testAddGate"));
      suite.addTest(new SystemModelTests("testAddTarget"));
      suite.addTest(new SystemModelTests("testConnectToTarget"));
      suite.addTest(new SystemModelTests("testUpdateSystemModel"));
      suite.addTest(new SystemModelTests("testUpdateGate"));
      suite.addTest(new SystemModelTests("testUpdateTarget"));
      suite.addTest(new SystemModelTests("testUpdateSegment"));
      suite.addTest(new SystemModelTests("testUpdatePool"));
      suite.addTest(new SystemModelTests("testUpdateHeap"));
      suite.addTest(new SystemModelTests("testUpdateBlock"));
      suite.addTest(new SystemModelTests("testUpdateProcess"));
      suite.addTest(new SystemModelTests("testUpdateSystemModelNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdateGateNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdateTargetNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdateSegmentNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdatePoolNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdateHeapNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdateBlockNonRecursive"));
      suite.addTest(new SystemModelTests("testUpdateProcessNonRecursive"));
      suite.addTest(new SystemModelTests("testThreadSafety"));
      suite.addTest(new SystemModelTests("testListGates"));
      suite.addTest(new SystemModelTests("testListTargets"));
      suite.addTest(new SystemModelTests("testListSegments"));
      suite.addTest(new SystemModelTests("testListPools"));
      suite.addTest(new SystemModelTests("testListHeaps"));
      suite.addTest(new SystemModelTests("testListBlocks"));
      suite.addTest(new SystemModelTests("testListProcesses"));
      suite.addTest(new SystemModelTests("testListLoadModules"));
      suite.addTest(new SystemModelTests("testCleanSystemModel"));
      suite.addTest(new SystemModelTests("testCleanGate"));
      suite.addTest(new SystemModelTests("testCleanTarget"));
      suite.addTest(new SystemModelTests("testCleanSegment"));
      suite.addTest(new SystemModelTests("testCleanPool"));
      suite.addTest(new SystemModelTests("testCleanHeap"));
      suite.addTest(new SystemModelTests("testCleanBlock"));
      suite.addTest(new SystemModelTests("testCleanProcess"));
      suite.addTest(new SystemModelTests("testAccessingSystemModelProperties"));
      suite.addTest(new SystemModelTests("testAccessingGateProperties"));
      suite.addTest(new SystemModelTests("testAccessingTargetProperties"));
      /* This test is commented because of TOOLSCR-1142 - can not set system
       * parameters on bcm91280. The test will keep failing in TEFO as long as
       * the board will not correctly report that setting system parameters is
       * unsupported.
       */
      //suite.addTest(new SystemModelTests("testSettingTargetSysParam"));
      suite.addTest(new SystemModelTests("testAccessingSegmentProperties"));
      suite.addTest(new SystemModelTests("testAccessingPoolProperties"));
      suite.addTest(new SystemModelTests("testAccessingHeapProperties"));
      suite.addTest(new SystemModelTests("testAccessingBlockProperties"));
      suite.addTest(new SystemModelTests("testAccessingProcessProperties"));
      suite.addTest(new SystemModelTests("testStopProcess"));
      suite.addTest(new SystemModelTests("testStartProcess"));
      suite.addTest(new SystemModelTests("testStopBlock"));
      suite.addTest(new SystemModelTests("testStartBlock"));
      suite.addTest(new SystemModelTests("testStopSegment"));
      suite.addTest(new SystemModelTests("testStartSegment"));
      suite.addTest(new SystemModelTests("testBlockFilter"));
      suite.addTest(new SystemModelTests("testProcessFilter"));
      suite.addTest(new SystemModelTests("testProcessFilterCriteria"));
      suite.addTest(new SystemModelTests("testSignalFilter"));
      suite.addTest(new SystemModelTests("testStackFilter"));
      suite.addTest(new SystemModelTests("testProgramInfo"));
      suite.addTest(new SystemModelTests("testVerifyReceiveStateOnProcess"));
      suite.addTest(new SystemModelTests("testVerifySignalQueueOnProcess"));
      suite.addTest(new SystemModelTests("testVerifySemaphoreOnProcess"));
      suite.addTest(new SystemModelTests("testVerifyEnvVarsOnProcess"));
      suite.addTest(new SystemModelTests("testVerifyBackgroundTypeOnProcess"));
      suite.addTest(new SystemModelTests("testVerifyPhantomTypeOnProcess"));
      suite.addTest(new SystemModelTests("testVerifyEnvVarsOnBlock"));
      suite.addTest(new SystemModelTests("testPingGates"));
      suite.addTest(new SystemModelTests("testTargetEventNotifications"));
      suite.addTest(new SystemModelTests("testTargetEventTrace"));
      suite.addTest(new SystemModelTests("testTargetEventTraceMultiple"));
      suite.addTest(new SystemModelTests("testCPUReports"));
      suite.addTest(new SystemModelTests("testCPUPriorityReports"));
      suite.addTest(new SystemModelTests("testCPUProcessReportPoint"));
      suite.addTest(new SystemModelTests("testCPUProcessReports"));
      suite.addTest(new SystemModelTests("testCPUBlockReports"));
      suite.addTest(new SystemModelTests("testUserReports"));
      suite.addTest(new SystemModelTests("testSystemModelDumpFileWriter"));
      suite.addTest(new SystemModelTests("testTargetDumps"));
      suite.addTest(new SystemModelTests("testGetMemory"));
      suite.addTest(new SystemModelTests("testChangeEventState"));
      suite.addTest(new SystemModelTests("testClearEventActionPoints"));
      suite.addTest(new SystemModelTests("testCleanSystemModelWithKilledProcess"));
      suite.addTest(new SystemModelTests("testCleanGateWithKilledProcess"));
      suite.addTest(new SystemModelTests("testCleanTargetWithKilledProcess"));
      /* We need to add one more process in the alfa block on the target in
       * order to run this test.
       */
      //suite.addTest(new SystemModelTests("testCleanSegmentWithKilledProcess"));
      suite.addTest(new SystemModelTests("testCleanBlockWithKilledProcess"));
      suite.addTest(new SystemModelTests("testCleanProcessWithKilledProcess"));
      suite.addTest(new SystemModelTests("testCleanSystemModelWithKilledBlock"));
      suite.addTest(new SystemModelTests("testCleanGateWithKilledBlock"));
      suite.addTest(new SystemModelTests("testCleanTargetWithKilledBlock"));
      /* We need to add one more block on the target in order to run this test. */
      //suite.addTest(new SystemModelTests("testCleanSegmentWithKilledBlock"));
      suite.addTest(new SystemModelTests("testCleanBlockWithKilledBlock"));
      suite.addTest(new SystemModelTests("testChangeAttachProperties"));
      suite.addTest(new SystemModelTests("testDisconnectFromTarget"));
      return suite;
   }
}
