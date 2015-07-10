package com.ose.ui.tests.linux.rse;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Suite class to run Remote System Explorer [RSE] test class.
 * 
 * 
 * Before running these tests update the VM arguments with IP Address, User name
 * and Password in below format.
 * 
 * Format: -Dip=Enter_ipaddress -Dusername=Enter_username
 * -Dpassword=Enter_password
 * 
 * @author Deepak Ramesh [Dera]
 * @date 28-09-2012
 * 
 */
public class Suite extends TestCase
{
   public static TestSuite suite()
   {
      TestSuite suite = new TestSuite();

      suite.addTestSuite(TestTCFTerminalWorkFlow.class);
      suite.addTestSuite(TestRSEProcess.class);

      return suite;
   }
}
