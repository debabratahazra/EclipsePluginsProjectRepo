package com.odcgroup.workbench.integration.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList.ListElement;
import org.junit.Assert;

@SuppressWarnings("restriction")
public class SWTBotTestUtil {
	public static void selectTabbedPropertyView(final SWTBot viewerBot,
			final String tabeText) {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				try {
					List<? extends Widget> widgets = viewerBot
							.getFinder()
							.findControls(
									WidgetMatcherFactory
											.widgetOfType(TabbedPropertyList.class));
					Assert.assertTrue(widgets.size() > 0);
					TabbedPropertyList tabbedPropertyList = (TabbedPropertyList) widgets
							.get(0);
					int i = 0;
					boolean found = false;
					ListElement currentTab;
					Method selectMethod = TabbedPropertyList.class
							.getDeclaredMethod("select",
									new Class[] { int.class });
					selectMethod.setAccessible(true);
					do {
						currentTab = ((org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList.ListElement) tabbedPropertyList
								.getElementAt(i));
						if (currentTab != null) {
							String label = currentTab.getTabItem().getText();
							if (label.equals(tabeText)) {
								found = true;
								selectMethod.invoke(tabbedPropertyList, i);
							}
						}
						i++;
					} while (currentTab != null && !found);
					if (!found) {
						throw new WidgetNotFoundException(
								"Can't find a tab item with " + tabeText
										+ " label");
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
	}
}