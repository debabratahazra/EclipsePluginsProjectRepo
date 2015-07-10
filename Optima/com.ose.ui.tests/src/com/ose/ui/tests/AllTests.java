package com.ose.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({com.ose.ui.tests.logmanager.Suite.class, 
   com.ose.ui.tests.systemprofiler.Suite.class, 
   com.ose.ui.tests.systembrowser.Suite.class, 
   com.ose.ui.tests.loganalyzer.Suite.class})
public class AllTests
{

}
