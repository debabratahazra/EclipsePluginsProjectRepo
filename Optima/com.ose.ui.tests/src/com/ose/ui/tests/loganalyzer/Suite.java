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

package com.ose.ui.tests.loganalyzer;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Suite extends TestCase
{
   public static TestSuite suite()
   {
      TestSuite suite = new TestSuite();

      suite.addTestSuite(TestAssertion.class);
      suite.addTestSuite(TestGanttChart.class);
      suite.addTestSuite(TestGanttOverviewChart.class);
      suite.addTestSuite(TestImportDeconfigure.class);
      suite.addTestSuite(TestImporters.class);
      suite.addTestSuite(TestPerspective.class);
      suite.addTestSuite(TestPlot.class);
      suite.addTestSuite(TestProject.class);
      suite.addTestSuite(TestReport.class);
      suite.addTestSuite(TestSearch.class);
      suite.addTestSuite(TestSequenceDiagram.class);
      suite.addTestSuite(TestTextBrowser.class);
      suite.addTestSuite(TestTextImportWizard.class);
      suite.addTestSuite(TestTimelineBrowser.class);
      suite.addTestSuite(TestCTFImporter.class);

      return suite;
   }
}