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

import java.io.File;
import java.io.IOException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.ose.ui.tests.utils.ResourceUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.condition.IConditionMonitor;
import com.windowtester.runtime.condition.IHandler;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.UITestCaseSWT;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.internal.text.InsertTextEntryStrategy;
import com.windowtester.runtime.swt.internal.text.TextEntryStrategy;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.windowtester.runtime.swt.locator.eclipse.WorkbenchLocator;

public class AbstractLABaseTest extends UITestCaseSWT
{
   private final String testLogsPath = createTestLogsPath();

   private IConditionMonitor monitor = null;
   
   private IConditionMonitor monitor2 = null;

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
      ui.ensureThat(PerspectiveLocator.forName("Log Analyzer").isActive());

      // Install Optima signal & event databases
      ui.click(new MenuItemLocator("Window/Preferences"));
      ui.wait(new ShellShowingCondition("Preferences"));
      ui.click(new FilteredTreeItemLocator("Optima/Event Database"));
      IWidgetReference[] references = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));
      if (references.length == 0)
      {
         ui.click(new ButtonLocator("Add..."));
         ui.enterText(getLogfilePath() + getSeparator() + "evtdb.o");
         ui.keyClick(WT.CR);
      }
      ui.click(new FilteredTreeItemLocator("Optima/Signal Database"));
      references = (IWidgetReference[]) ui.findAll(new TableItemLocator(".*"));
      if (references.length == 0)
      {
         ui.click(new ButtonLocator("Add..."));
         ui.enterText(getLogfilePath() + getSeparator() + "sigdb.o");
         ui.keyClick(WT.CR);
      }
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Preferences"));

      exceptionHandler(ui);
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
      if (monitor != null)
      {
         monitor.removeAll();
      }
      if(monitor2 != null){
         monitor2.removeAll();
      }
   }

   protected String getLogfilePath()
   {
      return testLogsPath;
   }

   protected String getSeparator()
   {
      return System.getProperty("file.separator");
   }

   private static String createTestLogsPath()
   {
      try
      {
         Bundle bundle = Platform.getBundle("com.ose.ui.tests");
         File pluginDir = FileLocator.getBundleFile(bundle);
         File testlogsDir = new File(pluginDir, "testlogs");
         return testlogsDir.getAbsolutePath();
      }
      catch (IOException e)
      {
         throw new RuntimeException("Error locating the testlogs folder in " +
               "the com.ose.ui.tests plugin", e);
      }
   }

   private void exceptionHandler(IUIContext ui)
   {
      monitor = (IConditionMonitor) getUI().getAdapter(IConditionMonitor.class);
      monitor2 = (IConditionMonitor) getUI().getAdapter(IConditionMonitor.class);
      monitor.add(new ShellShowingCondition("Problem.*"), new IHandler()
      {
         public void handle(IUIContext ui) throws Exception
         {
            ui.click(new ButtonLocator("OK"));
            fail("Error occured.");
         }
      });
      monitor2.add(new ShellShowingCondition("Refactoring.*"), new IHandler()
      {
         public void handle(IUIContext ui) throws Exception
         {
            ui.click(new ButtonLocator("Abort"));
            fail("Error occured.");
         }
      });
   }
}
