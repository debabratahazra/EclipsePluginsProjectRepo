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

package com.ose.ui.tests;

import com.ose.ui.tests.utils.ResourceUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.UITestCaseSWT;
import com.windowtester.runtime.swt.internal.text.InsertTextEntryStrategy;
import com.windowtester.runtime.swt.internal.text.TextEntryStrategy;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.swt.locator.eclipse.WorkbenchLocator;

public class AbstractBaseTest extends UITestCaseSWT
{      
   public static final String LOAD_MODULE_NAME = "sysmodtest";
   
   @Override
   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();
      // This is needed on a Windows PC with a Swedish keyboard.
      WT.setLocaleToCurrent();
      if(!ResourceUtils.isWindows())
      {
         TextEntryStrategy.set(new InsertTextEntryStrategy());
      }
      IUIContext ui = getUI();
      ui.ensureThat(new WorkbenchLocator().hasFocus());
      ui.ensureThat(new WorkbenchLocator().isMaximized());
      ui.ensureThat(ViewLocator.forName("Welcome").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isActive());
   }
   
   @Override
   protected void setUp() throws Exception
   {
      super.setUp();
   }
   
   @Override
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }
}
