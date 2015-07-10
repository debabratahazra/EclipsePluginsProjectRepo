package com.ose.ui.tests;

import com.ose.ui.tests.utils.ResourceUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.UITestCaseSWT;
import com.windowtester.runtime.swt.internal.text.KeyMapTextEntryStrategy;
import com.windowtester.runtime.swt.internal.text.TextEntryStrategy;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.swt.locator.eclipse.WorkbenchLocator;

/**
 * AbstractRSEBaseTest class. Class provides basic functionality of opening
 * Remote System Explorer[RSE] perspective for testing RSE work flows.
 * 
 * @author Deepak Ramesh [Dera]
 * @date 20-09-2012
 */
public class AbstractRSEBaseTest extends UITestCaseSWT
{

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();

      WT.setLocaleToCurrent();
      if (!ResourceUtils.isWindows())
      {
         TextEntryStrategy.set(new KeyMapTextEntryStrategy());
      }

      IUIContext ui = getUI();
      ui.ensureThat(new WorkbenchLocator().hasFocus());
      ui.ensureThat(new WorkbenchLocator().isMaximized());
      ui.ensureThat(ViewLocator.forName("Welcome").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("Remote System Explorer")
            .isActive());
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();
   }

}
