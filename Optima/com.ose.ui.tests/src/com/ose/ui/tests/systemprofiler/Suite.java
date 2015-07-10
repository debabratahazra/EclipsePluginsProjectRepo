/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
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

package com.ose.ui.tests.systemprofiler;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Suite extends TestCase
{
   public static TestSuite suite()
   {
      TestSuite suite = new TestSuite();

      suite.addTestSuite(TestCPUTotal.class);
      suite.addTestSuite(TestCPUPriority.class);
      suite.addTestSuite(TestCPUBlock.class);
      suite.addTestSuite(TestCPUProcess.class);
      suite.addTestSuite(TestCustomProfiling.class);
      suite.addTestSuite(TestHeapUsage.class);
      suite.addTestSuite(TestProfilerEditor.class);

      return suite;
   }
}
