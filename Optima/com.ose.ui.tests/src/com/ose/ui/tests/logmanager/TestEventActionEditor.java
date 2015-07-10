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

package com.ose.ui.tests.logmanager;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import com.ose.ui.tests.AbstractBaseTest;
import com.ose.ui.tests.utils.EventActionStrings;

import com.ose.ui.tests.utils.ResourceUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.WidgetSearchException;
import com.windowtester.runtime.condition.IsSelected;
import com.windowtester.runtime.condition.IsSelectedCondition;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.ComboItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.ListItemLocator;
import com.windowtester.runtime.swt.locator.NamedWidgetLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.SectionLocator;
import com.windowtester.runtime.swt.locator.TableCellLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ContributedToolItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestEventActionEditor extends AbstractBaseTest
{

   private static final String PROJECT_NAME = "Logmanager_ActionFile";

   private static final String EVENT_ACTION_FILENAME = "event-action.action";

   private static final String SAVE_BUTTON_ID = "org.eclipse.ui.file.save";

   private static final String PROJECT_EXPLORER_VIEW_ID = "org.eclipse.ui.navigator.ProjectExplorer";

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();

      IUIContext ui = getUI();
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("Log Management").isActive());

      // Create a project
      ResourceUtils.createNewProject(ui, PROJECT_NAME);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   @Override
   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();

      // Remove project
      ResourceUtils.deleteProject(getUI(), PROJECT_NAME);
   }

   public void testCreateNewEventActionFile() throws Exception
   {
      IUIContext ui = getUI();
      createNewEventActionFile(ui, EVENT_ACTION_FILENAME);
   }

   public void testUndoRedoInitalState() throws Exception
   {
      IUIContext ui = getUI();
      ui.click(new LabeledTextLocator("Initial State:"));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText("4");
      ui.keyClick(WT.CTRL, 's');
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Initial State:").hasText("0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Initial State:").hasText("4"));
   }

   /*
    * Validate undo/redo works for check box and combobox.
    */
   public void testUndoRedoDetailsPane() throws Exception
   {
      IUIContext ui = getUI();
	  ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator(EventActionStrings
            .getEventName(EventActionStrings.SEND_EVENT), new SWTWidgetLocator(
            List.class, 0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ListItemLocator(EventActionStrings
            .getActionName(EventActionStrings.TRACE_ACTION),
            new SWTWidgetLocator(List.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      // Validate undo/redo for "Event" combo-box.
/*      ui.click(new ComboItemLocator("Receive", new SWTWidgetLocator(
            Combo.class, 0, new SectionLocator("Details"))));
*/
      selectComboItem(ui, "eventTypeCombo", "Receive");
      undoOperation(ui);
      ui.assertThat(new ComboItemLocator("Details").isVisible(false));
      redoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isVisible(true));
      ui.click(new LabeledTextLocator("Initial State:"));
      // Validate undo/redo for "Do Not Perform Action:" check box.
      ui.click(new ButtonLocator("", 0, new SectionLocator("Details")));
      undoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isEnabled());
      redoOperation(ui);
      ui.assertThat(new ButtonLocator("", 0, new SectionLocator("Details"))
            .isEnabled());
      // remove the added item from the list
      ui.click(new ButtonLocator("Remove"));
      saveEventActionEditor(ui);
   }

   /*
    * Validate undo/redo works for items in the list from table.
    */
   public void testUndoRedoForTableAndDetails() throws Exception
   {
      IUIContext ui = getUI();

      createNewEventAction(ui, EventActionStrings.RECEIVE_EVENT,
            EventActionStrings.NOTIFY_ACTION);

      createNewEventAction(ui, EventActionStrings.SWAP_EVENT,
            EventActionStrings.TRACE_ACTION);

      // Validate undo/redo after adding 2 items in the table list.
      createNewEventAction(ui, EventActionStrings.ERROR_EVENT,
            EventActionStrings.INTERCEPT_ACTION);
      undoOperation(ui);
      validateEventAction(ui, 2, EventActionStrings.SWAP_EVENT,
            EventActionStrings.TRACE_ACTION);
      redoOperation(ui);
      validateEventAction(ui, 3, EventActionStrings.ERROR_EVENT,
            EventActionStrings.INTERCEPT_ACTION);

      // Validate undo/redo for each item after adding to table's list.
      createNewEventAction(ui, EventActionStrings.BIND_EVENT,
            EventActionStrings.ENABLE_TRACE_ACTION);
      undoOperation(ui);
      validateEventAction(ui, 3, EventActionStrings.ERROR_EVENT,
            EventActionStrings.INTERCEPT_ACTION);
      redoOperation(ui);
      validateEventAction(ui, 4, EventActionStrings.BIND_EVENT,
            EventActionStrings.ENABLE_TRACE_ACTION);

      createNewEventAction(ui, EventActionStrings.FREE_EVENT,
            EventActionStrings.DISABLE_TRACE_ACTION);
      undoOperation(ui);
      validateEventAction(ui, 4, EventActionStrings.BIND_EVENT,
            EventActionStrings.ENABLE_TRACE_ACTION);
      redoOperation(ui);
      validateEventAction(ui, 5, EventActionStrings.FREE_EVENT,
            EventActionStrings.DISABLE_TRACE_ACTION);

      createNewEventAction(ui, EventActionStrings.TIMEOUT_EVENT,
            EventActionStrings.ENABLE_HARDWARE_TRACE);
      undoOperation(ui);
      validateEventAction(ui, 5, EventActionStrings.FREE_EVENT,
            EventActionStrings.DISABLE_TRACE_ACTION);
      redoOperation(ui);
      validateEventAction(ui, 6, EventActionStrings.TIMEOUT_EVENT,
            EventActionStrings.ENABLE_HARDWARE_TRACE);

      createNewEventAction(ui, EventActionStrings.USER_EVENT,
            EventActionStrings.SET_STATE_ACTION);
      saveEventActionEditor(ui);

      undoOperation(ui);
      validateEventAction(ui, 6, EventActionStrings.TIMEOUT_EVENT,
            EventActionStrings.ENABLE_HARDWARE_TRACE);
      redoOperation(ui);
      validateEventAction(ui, 7, EventActionStrings.USER_EVENT,
            EventActionStrings.SET_STATE_ACTION);
   }

   public void testUndoRedoForRemoveFromTable() throws Exception
   {
      IUIContext ui = getUI();

      removeEventAction(ui, EventActionStrings.USER_EVENT);
      undoOperation(ui);
      validateEventAction(ui, 7, EventActionStrings.USER_EVENT,
            EventActionStrings.SET_STATE_ACTION);
      redoOperation(ui);
      validateEventAction(ui, 6, EventActionStrings.TIMEOUT_EVENT,
            EventActionStrings.ENABLE_HARDWARE_TRACE);

      removeEventAction(ui, EventActionStrings.TIMEOUT_EVENT);
      undoOperation(ui);
      validateEventAction(ui, 6, EventActionStrings.TIMEOUT_EVENT,
            EventActionStrings.ENABLE_HARDWARE_TRACE);
      redoOperation(ui);
      validateEventAction(ui, 5, EventActionStrings.FREE_EVENT,
            EventActionStrings.DISABLE_TRACE_ACTION);

      saveEventActionEditor(ui);
   }

   public void testEventActionFileResult() throws Exception
   {
      IUIContext ui = getUI();

      closeEventActionEditor(ui);
      openEventActionEditor(ui);
      
      closeEventActionEditor(ui);
   }

   public void testEventActionSendWithSignalFilter() throws Exception
   {
      IUIContext ui = getUI();
      createNewEventActionFile(ui, "send_signal_filter.action");

      createNewEventAction(ui, EventActionStrings.SEND_EVENT,
            EventActionStrings.NOTIFY_ACTION);
      saveEventActionEditor(ui);
      ui.ensureThat(new CTabItemLocator("send_signal_filter.action").isClosed());
   }
   
   public void testEventActionReceiveWithSignalFilter() throws Exception
   {
      IUIContext ui = getUI();
      createNewEventActionFile(ui, "receive_signal_filter.action");

      createNewEventAction(ui, EventActionStrings.RECEIVE_EVENT,
            EventActionStrings.NOTIFY_ACTION);
      saveEventActionEditor(ui);
      ui.ensureThat(new CTabItemLocator("receive_signal_filter.action").isClosed());
   }

   public void testEventActionFreeWithSignalFilter() throws Exception
   {
      IUIContext ui = getUI();
      createNewEventActionFile(ui, "free_signal_filter.action");

      createNewEventAction(ui, EventActionStrings.FREE_EVENT,
            EventActionStrings.NOTIFY_ACTION);
      saveEventActionEditor(ui);
      ui.ensureThat(new CTabItemLocator("free_signal_filter.action").isClosed());
   }

   public void testEventActionUserWithSignalFilter() throws Exception
   {
      IUIContext ui = getUI();
      createNewEventActionFile(ui, "user_signal_filter.action");

      createNewEventAction(ui, EventActionStrings.USER_EVENT,
            EventActionStrings.NOTIFY_ACTION);
      saveEventActionEditor(ui);
      ui.ensureThat(new CTabItemLocator("user_signal_filter.action").isClosed());
   }

  /**
    * Add new Event.
    * 
    * @param ui
    * @param eventID
    * @param actionID
    * @throws Exception
    */
   private void createNewEventAction(IUIContext ui, int eventID, int actionID)
         throws Exception
   {
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator(EventActionStrings.getEventName(eventID),
            new SWTWidgetLocator(List.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      ui.click(new ListItemLocator(EventActionStrings.getActionName(actionID),
            new SWTWidgetLocator(List.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      editDetailsPane(ui, eventID, actionID);
   }

   /**
    * Settings changed for an event.
    * 
    * @param ui
    * @param eventID
    * @param actionID
    * @throws Exception
    */
   private void editDetailsPane(IUIContext ui, int eventID, int actionID)
         throws Exception
   {
      ui.click(new TableItemLocator("0", 0, new SWTWidgetLocator(Table.class)));
      switch (eventID)
      {
         case EventActionStrings.SEND_EVENT:
         case EventActionStrings.RECEIVE_EVENT:
            editEventBlock(ui, eventID, actionID);
            editSignalNumberRange(ui);
            editSignalFilter(ui);
            break;
         case EventActionStrings.SWAP_EVENT:
         case EventActionStrings.CREATE_EVENT:
         case EventActionStrings.KILL_EVENT:
         case EventActionStrings.BIND_EVENT:
         case EventActionStrings.ALLOC_EVENT:
         case EventActionStrings.TIMEOUT_EVENT:
            editEventBlock(ui, eventID, actionID);
            break;
         case EventActionStrings.FREE_EVENT:
            editEventBlock(ui, eventID, actionID);
            editSignalFilter(ui);
            break;
         case EventActionStrings.ERROR_EVENT:
            editErrorEventBlock(ui, eventID, actionID);
            break;
         case EventActionStrings.USER_EVENT:
            editEventBlock(ui, eventID, actionID);
            editNumberRange(ui);
            editSignalFilter(ui);
            break;
         default:
            break;
      }
      editAction(ui, eventID, actionID);
   }

   /**
    * Change Settings of "Action" section.
    * 
    * @param ui
    * @param eventID
    * @param actionID
    * @throws Exception
    */
   private void editAction(IUIContext ui, int eventID, int actionID)
         throws Exception
   {
      // Do Not Perform Action:
      ui.click(new ButtonLocator("", 0, new SectionLocator("Details")));
      undoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isEnabled());
      redoOperation(ui);
      ui.assertThat(new ButtonLocator("", 0, new SectionLocator("Details"))
            .isEnabled());

      // State:
      ui.click(2, new XYLocator(new LabeledTextLocator("State:"), 22, 8));
      ui.enterText(String.valueOf(eventID));
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("State:").hasText("0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("State:").hasText(String
            .valueOf(eventID)));

      switch (actionID)
      {
         case EventActionStrings.INTERCEPT_ACTION:
         case EventActionStrings.ENABLE_TRACE_ACTION:
         case EventActionStrings.DISABLE_TRACE_ACTION:
         case EventActionStrings.ENABLE_HARDWARE_TRACE:
         case EventActionStrings.DISABLE_HARDWARE_TRACE:
            return;
         default:
            break;
      }

      if ((eventID == EventActionStrings.SEND_EVENT
            || eventID == EventActionStrings.RECEIVE_EVENT || eventID == EventActionStrings.USER_EVENT)
            && actionID != EventActionStrings.SET_STATE_ACTION)
      {
         // Include Signal/User Data:
         ui.click(new ButtonLocator("", 1, new SectionLocator("Details")));
         undoOperation(ui);
         ui.assertThat((new ButtonLocator("", 1, new SectionLocator("Details")))
               .isEnabled());
         redoOperation(ui);
         ui.assertThat(new ButtonLocator("", 1, new SectionLocator("Details"))
               .isEnabled());
      }

      if (EventActionStrings.SET_STATE_ACTION == actionID)
      {
         // New State
         ui.click(2, new XYLocator(new LabeledTextLocator("New State:"), 21, 7));
         ui.keyClick(WT.BS);
         ui.enterText(String.valueOf(eventID));
         undoOperation(ui);
         ui.assertThat(new LabeledTextLocator("New State:").hasText("0"));
         redoOperation(ui);
         ui.assertThat(new LabeledTextLocator("New State:").hasText(String
               .valueOf(eventID)));
      }
   }

   /**
    * Validate "State" and "Action" column in that .action file.
    * 
    * @param ui
    * @param index
    * @param eventID
    * @param actionID
    * @throws Exception
    */
   private void validateEventAction(IUIContext ui, int index, int eventID,
         int actionID) throws Exception
   {
      ui.assertThat(new TableCellLocator(index, 1).hasText(String
            .valueOf(eventID)));
      ui.assertThat(new TableCellLocator(index, 3).hasText(EventActionStrings
            .getActionName(actionID)));
   }

   /**
    * Close Event Action file.
    * 
    * @param ui
    * @throws Exception
    */
   private void closeEventActionEditor(IUIContext ui) throws Exception
   {
      ui.ensureThat(new CTabItemLocator(EVENT_ACTION_FILENAME).isClosed());
   }

   /**
    * Open Event Action file.
    * 
    * @param ui
    * @throws Exception
    */
   private void openEventActionEditor(IUIContext ui) throws Exception
   {
      ui.click(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            PROJECT_EXPLORER_VIEW_ID)));
      ui.contextClick(
            new TreeItemLocator(PROJECT_NAME + "/" + EVENT_ACTION_FILENAME,
                  new ViewLocator(PROJECT_EXPLORER_VIEW_ID)),
            "Open With/Event Action Editor");
   }

   /**
    * Remove Event from Event Action file.
    * 
    * @param ui
    * @param eventID
    * @throws Exception
    */
   private void removeEventAction(IUIContext ui, int eventID) throws Exception
   {
      ui.click(new TableItemLocator(String.valueOf(eventID), 0,
            new SWTWidgetLocator(Table.class)));
      ui.click(new ButtonLocator("Remove"));
   }

   /**
    * Setting change for "Error" event.
    * 
    * @param ui
    * @param eventID
    * @param actionID
    * @throws Exception
    */
   private void editErrorEventBlock(IUIContext ui, int eventID, int actionID)
         throws Exception
   {
      // Event:
/*      ui.click(new ComboItemLocator(EventActionStrings.getEventName(eventID),
            new SWTWidgetLocator(Combo.class, 0, new SectionLocator("Details"))));
*/     
      selectComboItem(ui, "eventTypeCombo", EventActionStrings.getEventName(eventID));
      undoOperation(ui);
      ui.assertThat(new ComboItemLocator("Details").isVisible(false));
      redoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isVisible(true));
      // From Scope:
      ui.click(new ComboItemLocator(EventActionStrings.BLOCK,
            new SWTWidgetLocator(Combo.class, 1, new SectionLocator("Details"))));
      undoOperation(ui);
      ui.assertThat(new ComboItemLocator("Details").isVisible(false));
      redoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isVisible(true));
      ui.click(2, new XYLocator(new LabeledTextLocator("From Scope:"), 36, 4));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.FROM_SCOPE_VALUE);
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("From Scope:").hasText("0x0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("From Scope:")
            .hasText(EventActionStrings.FROM_SCOPE_VALUE));

      // Ignore Count:
      ui.click(2, new XYLocator(new LabeledTextLocator("Ignore Count:"), 33, 8));
      ui.enterText(EventActionStrings.IGNORE_COUNT_VALUE);
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Ignore Count:").hasText("0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Ignore Count:")
            .hasText(EventActionStrings.IGNORE_COUNT_VALUE));

      // Error Code Range
      ui.click(2, new XYLocator(new LabeledTextLocator("Error Code Range:"),
            41, 4));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.ERROR_CODE_RANGE_VALUE[0]);
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Error Code Range:").hasText("0x0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Error Code Range:")
            .hasText(EventActionStrings.ERROR_CODE_RANGE_VALUE[0]));
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.ERROR_CODE_RANGE_VALUE[1]);

      // Error Type
      ui.click(new ButtonLocator("Kernel Reported"));
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Error Type:").isEnabled());
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Error Type:").isEnabled());
   }

   /**
    * Change "Signal Number Range" field.
    * 
    * @param ui
    * @throws Exception
    */
   private void editSignalNumberRange(IUIContext ui) throws Exception
   {
      // Signal Number Range:
      ui.click(2, new XYLocator(new LabeledTextLocator("Signal Number Range:"),
            47, 8));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.SIGNAL_NUMBER_RANGE_VALUE[0]);

      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Signal Number Range:")
            .hasText("0x0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Signal Number Range:")
            .hasText(EventActionStrings.SIGNAL_NUMBER_RANGE_VALUE[0]));

      ui.keyClick(WT.TAB);
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.SIGNAL_NUMBER_RANGE_VALUE[1]);

   }

   /**
    * Change "User Number Range" field.
    * 
    * @param ui
    * @throws Exception
    */
   private void editNumberRange(IUIContext ui) throws Exception
   {
      // User Number Range:
      ui.click(2, new XYLocator(new LabeledTextLocator("User Number Range:"),
            47, 8));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.USER_NUMBER_RANGE_VALUE[0]);

      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("User Number Range:").hasText("0x0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("User Number Range:")
            .hasText(EventActionStrings.USER_NUMBER_RANGE_VALUE[0]));

      ui.keyClick(WT.TAB);
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.USER_NUMBER_RANGE_VALUE[1]);

   }

   /**
    * Change Settings of "Event" section.
    * 
    * @param ui
    * @param eventID
    * @param actionID
    * @throws Exception
    */
   private void editEventBlock(IUIContext ui, int eventID, int actionID)
         throws Exception
   {
      // Event:
/*      ui.click(new ComboItemLocator(EventActionStrings.getEventName(eventID),
            new SWTWidgetLocator(Combo.class, 0, new SectionLocator("Details"))));
*/
      selectComboItem(ui, "eventTypeCombo", EventActionStrings.getEventName(eventID));
      undoOperation(ui);
      ui.assertThat(new ComboItemLocator("Details").isVisible(false));
      redoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isVisible(true));

      // From Scope:
      ui.click(new ComboItemLocator(EventActionStrings.BLOCK,
            new SWTWidgetLocator(Combo.class, 1, new SectionLocator("Details"))));
      undoOperation(ui);
      ui.assertThat(new ComboItemLocator("Details").isVisible(false));
      redoOperation(ui);
      ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
            .isVisible(true));
      ui.click(new LabeledTextLocator("Initial State:"));
      ui.click(new XYLocator(new LabeledTextLocator("From Scope:"), 36, 4));
      
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.FROM_SCOPE_VALUE);

      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("From Scope:").hasText("0x0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("From Scope:")
            .hasText(EventActionStrings.FROM_SCOPE_VALUE));

      if (!(eventID >= EventActionStrings.BIND_EVENT && eventID <= EventActionStrings.USER_EVENT))
      {
         // To Scope:
         ui.click(new ComboItemLocator(EventActionStrings.PROCESS,
               new SWTWidgetLocator(Combo.class, 2, new SectionLocator(
                     "Details"))));
         undoOperation(ui);
         ui.assertThat(new ComboItemLocator("Details").isVisible(false));
         redoOperation(ui);
         ui.assertThat((new ButtonLocator("", 0, new SectionLocator("Details")))
               .isVisible(true));
         ui.click(2, new XYLocator(new LabeledTextLocator("To Scope:"), 32, 6));
         ui.keyClick(WT.CTRL, 'a');
         ui.enterText(EventActionStrings.TO_SCOPE_VALUE);

         undoOperation(ui);
         ui.assertThat(new LabeledTextLocator("To Scope:").hasText("0x0"));
         redoOperation(ui);
         ui.assertThat(new LabeledTextLocator("To Scope:")
               .hasText(EventActionStrings.TO_SCOPE_VALUE));
      }

      // Ignore Count:
      ui.click(2, new XYLocator(new LabeledTextLocator("Ignore Count:"), 33, 8));
      ui.enterText(EventActionStrings.IGNORE_COUNT_VALUE);
      undoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Ignore Count:").hasText("0"));
      redoOperation(ui);
      ui.assertThat(new LabeledTextLocator("Ignore Count:")
            .hasText(EventActionStrings.IGNORE_COUNT_VALUE));
   }

   private void editSignalFilter(IUIContext ui) throws Exception
   {
      //1
      ui.click(new NamedWidgetLocator("useDataFilters"));
      //2
      ui.click(new NamedWidgetLocator("offsetText"));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.SIGNAL_FILTER[0]);
      //3
      ui.click(new ComboItemLocator(EventActionStrings.SIGNAL_FILTER[1], new NamedWidgetLocator("sizeCombo")));
      //4
      ui.click(new NamedWidgetLocator("dataRangeMinText"));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.SIGNAL_FILTER[2]);
      //5
      ui.click(new NamedWidgetLocator("dataRangeMaxText"));
      ui.keyClick(WT.CTRL, 'a');
      ui.enterText(EventActionStrings.SIGNAL_FILTER[3]);
      //6
      ui.click(new NamedWidgetLocator("dataRangeNot"));
      undoOperation(ui);
      ui.assertThat(new IsSelectedCondition((IsSelected) new ButtonLocator("Not"), false));
      undoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("dataRangeMaxText").hasText("0xFFFFFFFFFFFFFFFF"));
      undoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("dataRangeMinText").hasText("0x0"));
      undoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("sizeCombo").hasText("1"));
      undoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("offsetText").hasText("0x0"));
      undoOperation(ui);
      ui.assertThat(new IsSelectedCondition((IsSelected) new ButtonLocator("Use Data Filter"), false));
      redoOperation(ui);
      ui.assertThat(new IsSelectedCondition((IsSelected) new ButtonLocator("Use Data Filter"), true));
      redoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("offsetText").hasText(EventActionStrings.SIGNAL_FILTER[0]));
      redoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("sizeCombo").hasText(EventActionStrings.SIGNAL_FILTER[1]));
      redoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("dataRangeMinText").hasText(EventActionStrings.SIGNAL_FILTER[2]));
      redoOperation(ui);
      ui.assertThat(new NamedWidgetLocator("dataRangeMaxText").hasText(EventActionStrings.SIGNAL_FILTER[3]));
      redoOperation(ui);
      ui.assertThat(new IsSelectedCondition((IsSelected) new ButtonLocator("Not"), true));
   }
   
   /**
    * Save Event Action file.
    * 
    * @param ui
    * @throws Exception
    */
   private void saveEventActionEditor(IUIContext ui) throws Exception
   {
      ui.assertThat(new ContributedToolItemLocator(SAVE_BUTTON_ID).isEnabled());
      ui.click(new ContributedToolItemLocator(SAVE_BUTTON_ID));
   }

   /**
    * Create new Event Action file.
    * 
    * @param ui
    * @throws Exception
    */
   private void createNewEventActionFile(IUIContext ui, String filename) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            PROJECT_EXPLORER_VIEW_ID)), "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.assertThat(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(filename);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
   }

   /**
    * Undo the last operation.
    * 
    * @param ui
    * @throws Exception
    */
   private void undoOperation(IUIContext ui) throws Exception
   {
      ui.keyClick(WT.CTRL, 'z');
   }

   /**
    * Redo the last action, ctrl + y for windows, ctrl + shift + z for linux
    * 
    * @param ui
    * @throws Exception
    */
   private void redoOperation(IUIContext ui) throws Exception
   {
      if(ResourceUtils.isWindows())
      {
         ui.keyClick(WT.CTRL, 'y');
      }
      else
      {
         ui.keyClick(WT.SHIFT + WT.CTRL, 'z');
      }
   }
   
   private void selectComboItem(IUIContext ui, String name, final String item)
   {
      NamedWidgetLocator widgetLocator = new NamedWidgetLocator(name);
      final IWidgetReference widgetRef;
		try
		{
		   widgetRef = (IWidgetReference)ui.find(widgetLocator);
		   assertNotNull(widgetRef);
		   ui.click(widgetLocator); 
		   ((Combo)widgetRef.getWidget()).getDisplay().asyncExec(new Runnable() {
		      public void run()
		      {
		         Combo combo = ((Combo)widgetRef.getWidget());
		         int index = 0;
		         for (int i = 0; i < combo.getItemCount(); i++)
               {
                  if (item.equalsIgnoreCase(combo.getItem(i)))
                  {
                     index = i;
                  }
               }
		         combo.select(index);
		      }
		   });
		}
		catch (WidgetSearchException e)
		{
			return;
		}
   
   }
   
}
