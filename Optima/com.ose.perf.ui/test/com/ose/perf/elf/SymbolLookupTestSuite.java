/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2012 by Enea Software AB.
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

package com.ose.perf.elf;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SymbolLookupTestSuite
{
   public static Test suite()
   {
      // TODO: Uncomment tests when implemented.
      TestSuite suite = new TestSuite("Test suite for com.ose.perf.elf");

      suite.addTest(new SymbolLookupTest("test_sc_prof_arm_gcc"));
      //suite.addTest(new SymbolLookupTest("test_sc_prof_arm_rvct"));
      suite.addTest(new SymbolLookupTest("test_sc_prof_mips"));
      suite.addTest(new SymbolLookupTest("test_sc_prof_powerpc"));
      //suite.addTest(new SymbolLookupTest("test_sc_prof_powerpc64"));
      suite.addTest(new SymbolLookupTest("test_sc_prof_x86"));

      suite.addTest(new SymbolLookupTest("test_inline_arm_gcc"));
      //suite.addTest(new SymbolLookupTest("test_inline_arm_rvct"));
      suite.addTest(new SymbolLookupTest("test_inline_mips"));
      suite.addTest(new SymbolLookupTest("test_inline_powerpc"));
      //suite.addTest(new SymbolLookupTest("test_inline_powerpc64"));
      suite.addTest(new SymbolLookupTest("test_inline_x86"));

      return suite;
   }
}
