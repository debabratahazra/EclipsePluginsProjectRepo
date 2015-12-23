package com.ifx.dave.monitor.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ifx.dave.monitor.ui.MainWindow;
import com.ifx.dave.monitor.ui.utils.DraggableTab;
import com.ifx.dave.monitor.ui.utils.MessageDialog;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;

public class DragableTabController {

    private static final String TAB_NAME = "Oscilloscope - ";
    private static Map<Integer, Node> tabContents = new HashMap<>();
    private static List<Integer> closedTabIds = new ArrayList<Integer>();
    private static Set<Integer> openTabIds = new HashSet<>();

	public DragableTabController() {
	    // Create one Oscilloscope Tab initially
		DraggableTab tab = new DraggableTab(getTabName(1));
		tab.setClosable(false);
		tab.setDetachable(false);
		tab.setId("1");   // First Tab
		openTabIds.add(1);
		tab.setContent(MainWindow.getController().getTab1().getContent());

		// Store the Oscilloscope content in Map
		tabContents.put(1, MainWindow.getController().getTab1().getContent());
		tabContents.put(2, MainWindow.getController().getTab2().getContent());
		tabContents.put(3, MainWindow.getController().getTab3().getContent());
		tabContents.put(4, MainWindow.getController().getTab4().getContent());
		tabContents.put(5, MainWindow.getController().getTab5().getContent());
        tabContents.put(6, MainWindow.getController().getTab6().getContent());
        tabContents.put(7, MainWindow.getController().getTab7().getContent());
        tabContents.put(8, MainWindow.getController().getTab8().getContent());

		// Remove all existing Tabs initially.
		ObservableList<Tab> tabs = MainWindow.getController().getTabpane().getTabs();
		MainWindow.getController().getTabpane().getTabs().removeAll(tabs);

		// Show only One Tab of Oscilloscope initially
		MainWindow.getController().getTabpane().getTabs().add(tab);
	}

    public static Node getTabContents(Integer tabNumber) {
        return tabContents.get(tabNumber);
    }

    public static String getTabName(int tabNumber) {
        return TAB_NAME + tabNumber;
    }

    public static Integer getClosedTabId() {
        if(closedTabIds != null && closedTabIds.size() == 0) {
            return null;
        }
        return closedTabIds.remove(0);
    }

    /**
     * Maintain the closed Oscilloscope Tab information
     * @param closedTabId
     */
    public static void addClosedTabId(Integer closedTabId) {
        DragableTabController.closedTabIds.add(closedTabId);
        Collections.sort(DragableTabController.closedTabIds);
    }

    public static void addClosedTabId(String closedTabId) {
        DragableTabController.closedTabIds.add(Integer.parseInt(closedTabId));
        Collections.sort(DragableTabController.closedTabIds);
    }

    public static List<Integer> getOpenTabIds() {
        List<Integer> tabs = new ArrayList<>();
        tabs.addAll(openTabIds);
        return tabs;
    }

    /**
     * Maintain all opened Oscilloscope Tab information
     * @param detachTabId
     */
    public static void addOpenTabIds(Integer detachTabId) {
        DragableTabController.openTabIds.add(detachTabId);
    }

    public static void removeOpenTabId(Integer detachTabId) {
        DragableTabController.openTabIds.remove(detachTabId);
    }

    public static void removeOpenTabId(String detachTabId) {
        DragableTabController.openTabIds.remove(Integer.parseInt(detachTabId));
    }

    /**
     * Handle Close operation of the Tab from TabPane
     * @param tab
     */
    public static void setOnCloseTab(DraggableTab tab) {
        tab.setOnClosed(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                ButtonType result = MessageDialog.showConfirmation("Are you sure, you want to close this Oscilloscope ?", "Confirmation", null);
                if(!result.equals(ButtonType.OK)) {
                    // Don't close it. Reopen the Tab (As there is no other way to handle it.)
                    int index = MainWindow.getController().getTabpane().getSelectionModel().getSelectedIndex();
                    MainWindow.getController().getTabpane().getTabs().add(++index, tab);
                    MainWindow.getController().getTabpane().getSelectionModel().select(tab);
                }else {
                    // Close it. Clear the stored configuration
                    addClosedTabId(tab.getId());
                    removeOpenTabId(tab.getId());
                }
            }
        });
    }
}
