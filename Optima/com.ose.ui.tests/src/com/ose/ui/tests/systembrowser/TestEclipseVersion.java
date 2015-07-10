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

package com.ose.ui.tests.systembrowser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ose.ui.tests.AbstractBaseTest;
import com.ose.ui.tests.utils.ResourceUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class TestEclipseVersion extends AbstractBaseTest
{
   static final String VERSION_PATTERN_ECLIPSE = "3\\.8\\.1";
   
   static final String VERSION_PATTERN_CDT = "8\\.1\\.1(\\.\\d+)*";
   
   public void testVersionEclipse() throws Exception
   {
      IUIContext ui = getUI();
      
      // Open the About dialog
      openAboutDialog();
      
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);
      
      // Check the Eclipse version
      Pattern versionPattern = Pattern.compile("Version: " + VERSION_PATTERN_ECLIPSE + "\n");
      Matcher versionMatcher = versionPattern.matcher(text);
      assertTrue("Eclipse version is not correct.", versionMatcher.find());
      
      // Close the About dialog
      closeAboutDialog();
   }
   
   public void testVersionCDT() throws Exception
   {
      IUIContext ui = getUI();
      
      // Open the About dialog
      openAboutDialog();
      
      int eclipseCDTBut = !ResourceUtils.isWindows()? 2:1;
      
      // Open the About Features dialog
      ui.click(new ButtonLocator("", eclipseCDTBut, new SWTWidgetLocator(Composite.class)));
      ui.wait(new ShellShowingCondition("About .* Features"));
      ui.click(new TableItemLocator("Eclipse CDT", 2, new SWTWidgetLocator(
            Table.class)));

      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);
      
      // Check the CDT version
      Pattern versionPattern = Pattern.compile("Version: " + VERSION_PATTERN_CDT + "\n");
      Matcher versionMatcher = versionPattern.matcher(text);
      assertTrue("Eclipse version is not correct.", versionMatcher.find());
      
      // Close the About Features dialog
      ui.click(new ButtonLocator("Close"));
      ui.wait(new ShellDisposedCondition("About .* Features"));
      
      // Close the About dialog
      closeAboutDialog();
   }

   public void openAboutDialog() throws Exception
   {
      IUIContext ui = getUI();
      ui.click(new MenuItemLocator("Help/About .*"));
      ui.wait(new ShellShowingCondition("About .*"));      
   }

   public void closeAboutDialog() throws Exception
   {
      IUIContext ui = getUI();
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("About .* SDK"));      
   }
}
