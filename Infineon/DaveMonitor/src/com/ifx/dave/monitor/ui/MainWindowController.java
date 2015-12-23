package com.ifx.dave.monitor.ui;

import com.ifx.dave.monitor.elf.model.Variable;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab1;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab2;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab3;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab4;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab5;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab6;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab7;
import com.ifx.dave.monitor.ui.controller.ChannelsControllerTab8;
import com.ifx.dave.monitor.ui.controller.DragableTabController;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab1;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab2;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab3;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab4;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab5;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab6;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab7;
import com.ifx.dave.monitor.ui.controller.LineChartControllerTab8;
import com.ifx.dave.monitor.ui.controller.MenuController;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab1;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab2;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab3;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab4;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab5;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab6;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab7;
import com.ifx.dave.monitor.ui.controller.OscilloscopeControllerTab8;
import com.ifx.dave.monitor.ui.controller.TableViewController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainWindowController {

    @FXML
    private AnchorPane mainAnchorPane;

    /**
     * TableView
     */
    @FXML
    private TabPane variableTabPane;
    @FXML
    private TableView<Variable> tableview;
    @FXML
    private TableColumn<Variable, String> tname;
    @FXML
    private TableColumn<Variable, String> taddress;
    @FXML
    private TableColumn<Variable, String> ttype;
    @FXML
    private TableColumn<Variable, String> tvalue;
    @FXML
    private TableColumn<Variable, String> tscaledvalue;
    @FXML
    private TableColumn<Variable, String> tunit;

    @FXML
    private AnchorPane tableanchorpane;

    @FXML
    private AnchorPane tabPaneAnchorPane;

    @FXML
    private SplitPane splitPane;

    /**
     * LineChart
     */

    @FXML
    private TabPane tabpane;

    @FXML
    private Tab tab1;
    @FXML
    private Tab tab2;
    @FXML
    private Tab tab3;
    @FXML
    private Tab tab4;
    @FXML
    private Tab tab5;
    @FXML
    private Tab tab6;
    @FXML
    private Tab tab7;
    @FXML
    private Tab tab8;

    @FXML
    private StackPane stackpane1;
    @FXML
    private StackPane stackpane2;
    @FXML
    private StackPane stackpane3;
    @FXML
    private StackPane stackpane4;
    @FXML
    private StackPane stackpane5;
    @FXML
    private StackPane stackpane6;
    @FXML
    private StackPane stackpane7;
    @FXML
    private StackPane stackpane8;

    @FXML
    private LineChart<Number, Number> linechart11;
    @FXML
    private LineChart<Number, Number> linechart12;
    @FXML
    private LineChart<Number, Number> linechart13;
    @FXML
    private LineChart<Number, Number> linechart14;

    @FXML
    private LineChart<Number, Number> linechart21;
    @FXML
    private LineChart<Number, Number> linechart22;
    @FXML
    private LineChart<Number, Number> linechart23;
    @FXML
    private LineChart<Number, Number> linechart24;

    @FXML
    private LineChart<Number, Number> linechart31;
    @FXML
    private LineChart<Number, Number> linechart32;
    @FXML
    private LineChart<Number, Number> linechart33;
    @FXML
    private LineChart<Number, Number> linechart34;

    @FXML
    private LineChart<Number, Number> linechart41;
    @FXML
    private LineChart<Number, Number> linechart42;
    @FXML
    private LineChart<Number, Number> linechart43;
    @FXML
    private LineChart<Number, Number> linechart44;

    @FXML
    private LineChart<Number, Number> linechart51;
    @FXML
    private LineChart<Number, Number> linechart52;
    @FXML
    private LineChart<Number, Number> linechart53;
    @FXML
    private LineChart<Number, Number> linechart54;

    @FXML
    private LineChart<Number, Number> linechart61;
    @FXML
    private LineChart<Number, Number> linechart62;
    @FXML
    private LineChart<Number, Number> linechart63;
    @FXML
    private LineChart<Number, Number> linechart64;

    @FXML
    private LineChart<Number, Number> linechart71;
    @FXML
    private LineChart<Number, Number> linechart72;
    @FXML
    private LineChart<Number, Number> linechart73;
    @FXML
    private LineChart<Number, Number> linechart74;

    @FXML
    private LineChart<Number, Number> linechart81;
    @FXML
    private LineChart<Number, Number> linechart82;
    @FXML
    private LineChart<Number, Number> linechart83;
    @FXML
    private LineChart<Number, Number> linechart84;

    /**
     * Upper Toolbar in LineChart - Tab1
     */
    @FXML
    private Button channel1_button_tab1;
    @FXML
    private ComboBox<String> channel1_scale_tab1;
    @FXML
    private Button channel1_up_tab1;
    @FXML
    private Button channel1_down_tab1;

    @FXML
    private Button channel2_button_tab1;
    @FXML
    private ComboBox<String> channel2_scale_tab1;
    @FXML
    private Button channel2_up_tab1;
    @FXML
    private Button channel2_down_tab1;

    @FXML
    private Button channel3_button_tab1;
    @FXML
    private ComboBox<String> channel3_scale_tab1;
    @FXML
    private Button channel3_up_tab1;
    @FXML
    private Button channel3_down_tab1;

    @FXML
    private Button channel4_button_tab1;
    @FXML
    private ComboBox<String> channel4_scale_tab1;
    @FXML
    private Button channel4_up_tab1;
    @FXML
    private Button channel4_down_tab1;

    /**
     * Below Toolbar in LineChart - Tab1
     */
    @FXML
    private Button chartproperties_tab1;
    @FXML
    private ToggleButton runtoggle_tab1;
    @FXML
    private ComboBox<String> sampling_tab1;
    @FXML
    private ComboBox<String> trigger_tab1;
    @FXML
    private ComboBox<String> channelcombobox_tab1;
    @FXML
    private ComboBox<String> enablecombobox_tab1;
    @FXML
    private Button cursor_tab1;
    @FXML
    private Button measure_tab1;

    /**
     * Upper Toolbar in LineChart - Tab2
     */
    @FXML
    private Button channel1_button_tab2;
    @FXML
    private ComboBox<String> channel1_scale_tab2;
    @FXML
    private Button channel1_up_tab2;
    @FXML
    private Button channel1_down_tab2;

    @FXML
    private Button channel2_button_tab2;
    @FXML
    private ComboBox<String> channel2_scale_tab2;
    @FXML
    private Button channel2_up_tab2;
    @FXML
    private Button channel2_down_tab2;

    @FXML
    private Button channel3_button_tab2;
    @FXML
    private ComboBox<String> channel3_scale_tab2;
    @FXML
    private Button channel3_up_tab2;
    @FXML
    private Button channel3_down_tab2;

    @FXML
    private Button channel4_button_tab2;
    @FXML
    private ComboBox<String> channel4_scale_tab2;
    @FXML
    private Button channel4_up_tab2;
    @FXML
    private Button channel4_down_tab2;

    /**
     * Below Toolbar in LineChart - Tab2
     */
    @FXML
    private Button chartproperties_tab2;
    @FXML
    private ToggleButton runtoggle_tab2;
    @FXML
    private ComboBox<String> sampling_tab2;
    @FXML
    private ComboBox<String> trigger_tab2;
    @FXML
    private ComboBox<String> channelcombobox_tab2;
    @FXML
    private ComboBox<String> enablecombobox_tab2;
    @FXML
    private Button cursor_tab2;
    @FXML
    private Button measure_tab2;

    /**
     * Upper Toolbar in LineChart - Tab3
     */
    @FXML
    private Button channel1_button_tab3;
    @FXML
    private ComboBox<String> channel1_scale_tab3;
    @FXML
    private Button channel1_up_tab3;
    @FXML
    private Button channel1_down_tab3;

    @FXML
    private Button channel2_button_tab3;
    @FXML
    private ComboBox<String> channel2_scale_tab3;
    @FXML
    private Button channel2_up_tab3;
    @FXML
    private Button channel2_down_tab3;

    @FXML
    private Button channel3_button_tab3;
    @FXML
    private ComboBox<String> channel3_scale_tab3;
    @FXML
    private Button channel3_up_tab3;
    @FXML
    private Button channel3_down_tab3;

    @FXML
    private Button channel4_button_tab3;
    @FXML
    private ComboBox<String> channel4_scale_tab3;
    @FXML
    private Button channel4_up_tab3;
    @FXML
    private Button channel4_down_tab3;

    /**
     * Below Toolbar in LineChart - Tab3
     */
    @FXML
    private Button chartproperties_tab3;
    @FXML
    private ToggleButton runtoggle_tab3;
    @FXML
    private ComboBox<String> sampling_tab3;
    @FXML
    private ComboBox<String> trigger_tab3;
    @FXML
    private ComboBox<String> channelcombobox_tab3;
    @FXML
    private ComboBox<String> enablecombobox_tab3;
    @FXML
    private Button cursor_tab3;
    @FXML
    private Button measure_tab3;

    /**
     * Upper Toolbar in LineChart - Tab4
     */
    @FXML
    private Button channel1_button_tab4;
    @FXML
    private ComboBox<String> channel1_scale_tab4;
    @FXML
    private Button channel1_up_tab4;
    @FXML
    private Button channel1_down_tab4;

    @FXML
    private Button channel2_button_tab4;
    @FXML
    private ComboBox<String> channel2_scale_tab4;
    @FXML
    private Button channel2_up_tab4;
    @FXML
    private Button channel2_down_tab4;

    @FXML
    private Button channel3_button_tab4;
    @FXML
    private ComboBox<String> channel3_scale_tab4;
    @FXML
    private Button channel3_up_tab4;
    @FXML
    private Button channel3_down_tab4;

    @FXML
    private Button channel4_button_tab4;
    @FXML
    private ComboBox<String> channel4_scale_tab4;
    @FXML
    private Button channel4_up_tab4;
    @FXML
    private Button channel4_down_tab4;

    /**
     * Below Toolbar in LineChart - Tab4
     */
    @FXML
    private Button chartproperties_tab4;
    @FXML
    private ToggleButton runtoggle_tab4;
    @FXML
    private ComboBox<String> sampling_tab4;
    @FXML
    private ComboBox<String> trigger_tab4;
    @FXML
    private ComboBox<String> channelcombobox_tab4;
    @FXML
    private ComboBox<String> enablecombobox_tab4;
    @FXML
    private Button cursor_tab4;
    @FXML
    private Button measure_tab4;

    /**
     * Upper Toolbar in LineChart - Tab5
     */
    @FXML
    private Button channel1_button_tab5;
    @FXML
    private ComboBox<String> channel1_scale_tab5;
    @FXML
    private Button channel1_up_tab5;
    @FXML
    private Button channel1_down_tab5;

    @FXML
    private Button channel2_button_tab5;
    @FXML
    private ComboBox<String> channel2_scale_tab5;
    @FXML
    private Button channel2_up_tab5;
    @FXML
    private Button channel2_down_tab5;

    @FXML
    private Button channel3_button_tab5;
    @FXML
    private ComboBox<String> channel3_scale_tab5;
    @FXML
    private Button channel3_up_tab5;
    @FXML
    private Button channel3_down_tab5;

    @FXML
    private Button channel4_button_tab5;
    @FXML
    private ComboBox<String> channel4_scale_tab5;
    @FXML
    private Button channel4_up_tab5;
    @FXML
    private Button channel4_down_tab5;

    /**
     * Below Toolbar in LineChart - Tab5
     */
    @FXML
    private Button chartproperties_tab5;
    @FXML
    private ToggleButton runtoggle_tab5;
    @FXML
    private ComboBox<String> sampling_tab5;
    @FXML
    private ComboBox<String> trigger_tab5;
    @FXML
    private ComboBox<String> channelcombobox_tab5;
    @FXML
    private ComboBox<String> enablecombobox_tab5;
    @FXML
    private Button cursor_tab5;
    @FXML
    private Button measure_tab5;

    /**
     * Upper Toolbar in LineChart - Tab6
     */
    @FXML
    private Button channel1_button_tab6;
    @FXML
    private ComboBox<String> channel1_scale_tab6;
    @FXML
    private Button channel1_up_tab6;
    @FXML
    private Button channel1_down_tab6;

    @FXML
    private Button channel2_button_tab6;
    @FXML
    private ComboBox<String> channel2_scale_tab6;
    @FXML
    private Button channel2_up_tab6;
    @FXML
    private Button channel2_down_tab6;

    @FXML
    private Button channel3_button_tab6;
    @FXML
    private ComboBox<String> channel3_scale_tab6;
    @FXML
    private Button channel3_up_tab6;
    @FXML
    private Button channel3_down_tab6;

    @FXML
    private Button channel4_button_tab6;
    @FXML
    private ComboBox<String> channel4_scale_tab6;
    @FXML
    private Button channel4_up_tab6;
    @FXML
    private Button channel4_down_tab6;

    /**
     * Below Toolbar in LineChart - Tab6
     */
    @FXML
    private Button chartproperties_tab6;
    @FXML
    private ToggleButton runtoggle_tab6;
    @FXML
    private ComboBox<String> sampling_tab6;
    @FXML
    private ComboBox<String> trigger_tab6;
    @FXML
    private ComboBox<String> channelcombobox_tab6;
    @FXML
    private ComboBox<String> enablecombobox_tab6;
    @FXML
    private Button cursor_tab6;
    @FXML
    private Button measure_tab6;

    /**
     * Upper Toolbar in LineChart - Tab7
     */
    @FXML
    private Button channel1_button_tab7;
    @FXML
    private ComboBox<String> channel1_scale_tab7;
    @FXML
    private Button channel1_up_tab7;
    @FXML
    private Button channel1_down_tab7;

    @FXML
    private Button channel2_button_tab7;
    @FXML
    private ComboBox<String> channel2_scale_tab7;
    @FXML
    private Button channel2_up_tab7;
    @FXML
    private Button channel2_down_tab7;

    @FXML
    private Button channel3_button_tab7;
    @FXML
    private ComboBox<String> channel3_scale_tab7;
    @FXML
    private Button channel3_up_tab7;
    @FXML
    private Button channel3_down_tab7;

    @FXML
    private Button channel4_button_tab7;
    @FXML
    private ComboBox<String> channel4_scale_tab7;
    @FXML
    private Button channel4_up_tab7;
    @FXML
    private Button channel4_down_tab7;

    /**
     * Below Toolbar in LineChart - Tab7
     */
    @FXML
    private Button chartproperties_tab7;
    @FXML
    private ToggleButton runtoggle_tab7;
    @FXML
    private ComboBox<String> sampling_tab7;
    @FXML
    private ComboBox<String> trigger_tab7;
    @FXML
    private ComboBox<String> channelcombobox_tab7;
    @FXML
    private ComboBox<String> enablecombobox_tab7;
    @FXML
    private Button cursor_tab7;
    @FXML
    private Button measure_tab7;

    /**
     * Upper Toolbar in LineChart - Tab8
     */
    @FXML
    private Button channel1_button_tab8;
    @FXML
    private ComboBox<String> channel1_scale_tab8;
    @FXML
    private Button channel1_up_tab8;
    @FXML
    private Button channel1_down_tab8;

    @FXML
    private Button channel2_button_tab8;
    @FXML
    private ComboBox<String> channel2_scale_tab8;
    @FXML
    private Button channel2_up_tab8;
    @FXML
    private Button channel2_down_tab8;

    @FXML
    private Button channel3_button_tab8;
    @FXML
    private ComboBox<String> channel3_scale_tab8;
    @FXML
    private Button channel3_up_tab8;
    @FXML
    private Button channel3_down_tab8;

    @FXML
    private Button channel4_button_tab8;
    @FXML
    private ComboBox<String> channel4_scale_tab8;
    @FXML
    private Button channel4_up_tab8;
    @FXML
    private Button channel4_down_tab8;

    /**
     * Below Toolbar in LineChart - Tab8
     */
    @FXML
    private Button chartproperties_tab8;
    @FXML
    private ToggleButton runtoggle_tab8;
    @FXML
    private ComboBox<String> sampling_tab8;
    @FXML
    private ComboBox<String> trigger_tab8;
    @FXML
    private ComboBox<String> channelcombobox_tab8;
    @FXML
    private ComboBox<String> enablecombobox_tab8;
    @FXML
    private Button cursor_tab8;
    @FXML
    private Button measure_tab8;

    /**
     * Table Toolbar
     */
    @FXML
    private Button tableproperties;
    @FXML
    private Button addvariable;
    @FXML
    private Button removevariable;

    /* Status Bar Information Label of Linechart */
    @FXML
    private Label infolabel;

    /**
     * File Menu
     */
    @FXML
    private MenuItem newmenu;
    @FXML
    private MenuItem openmenu;
    @FXML
    private MenuItem saveasmenu;
    @FXML
    private MenuItem saveSession;
    @FXML
    private MenuItem exitmenu;

    @FXML
    private Menu recentmenu;

    /**
     * Tools Menu
     */
    @FXML
    private MenuItem flashtoolmenu;

    /**
     * Window Menu
     */
    @FXML
    private MenuItem newvariablemenu;
    @FXML
    private MenuItem newoscilloscopemenu;
    @FXML
    private MenuItem newgraphmenu;

    @FXML
    private MenuItem cascademenu;
    @FXML
    private MenuItem tilehorizontalmenu;
    @FXML
    private MenuItem tileverticalmenu;

    @FXML
    private RadioMenuItem whitethememenu;
    @FXML
    private RadioMenuItem bluethememenu;
    @FXML
    private RadioMenuItem blackthememenu;

    /**
     * Help Menu
     */
    @FXML
    private MenuItem helpindexmenu;
    @FXML
    private MenuItem aboutxspymenu;

    /**
     * Toolbar Button
     */
    @FXML
    private ToolBar toolbar;
    @FXML
    private Button playtoolbar;
    @FXML
    private Button stoptoolbar;
    @FXML
    private Button flashtoolbar;

    // LineChartTab
    private LineChartControllerTab1 lineChartControllerTab1;
    private LineChartControllerTab2 lineChartControllerTab2;
    private LineChartControllerTab3 lineChartControllerTab3;
    private LineChartControllerTab4 lineChartControllerTab4;
    private LineChartControllerTab5 lineChartControllerTab5;
    private LineChartControllerTab6 lineChartControllerTab6;
    private LineChartControllerTab7 lineChartControllerTab7;
    private LineChartControllerTab8 lineChartControllerTab8;

    /**
     * Main method to call.
     *
     * @param main
     * @throws Exception
     */
    public void setMain(MainWindow main) throws Exception {
        new TableViewController().tableViewInit(tableview, tname, taddress,
                ttype, tvalue);

        new DragableTabController();
        new MenuController();

        lineChartControllerTab1 = new LineChartControllerTab1();
        new ChannelsControllerTab1();
        new OscilloscopeControllerTab1();
    }

    public void createNewTabInstance(int tabId) throws Exception {

        switch (tabId) {

        case 2:
            if (lineChartControllerTab2 != null) {
                return;
            }
            lineChartControllerTab2 = new LineChartControllerTab2();
            new ChannelsControllerTab2();
            new OscilloscopeControllerTab2();
            break;

        case 3:
            if (lineChartControllerTab3 != null) {
                return;
            }
            lineChartControllerTab3 = new LineChartControllerTab3();
            new ChannelsControllerTab3();
            new OscilloscopeControllerTab3();
            break;

        case 4:
            lineChartControllerTab4 = new LineChartControllerTab4();
            new ChannelsControllerTab4();
            new OscilloscopeControllerTab4();
            break;

        case 5:
            lineChartControllerTab5 = new LineChartControllerTab5();
            new ChannelsControllerTab5();
            new OscilloscopeControllerTab5();
            break;

        case 6:
            lineChartControllerTab6 = new LineChartControllerTab6();
            new ChannelsControllerTab6();
            new OscilloscopeControllerTab6();
            break;

        case 7:
            lineChartControllerTab7 = new LineChartControllerTab7();
            new ChannelsControllerTab7();
            new OscilloscopeControllerTab7();
            break;

        case 8:
            lineChartControllerTab8 = new LineChartControllerTab8();
            new ChannelsControllerTab8();
            new OscilloscopeControllerTab8();
            break;

        default:
            break;
        }
    }

    /**
     * Default initialize method by JavaFX
     */
    public void initialize() {

        SplitPane.setResizableWithParent(tableanchorpane, Boolean.FALSE);
        setStyleSheetLineChart();
        addVariableDialogListener();

        // Set the Model class to TableViewer
        tname.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variableName"));
        taddress.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("address"));
        ttype.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variableType"));
        tvalue.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("value"));
        tscaledvalue.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("scalevalue"));
        tunit.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("unit"));
    }

    /**
     * Set the StyleSheet to each LineChart on all Tabs except Linechart-1 for
     * all Tabs
     */
    private void setStyleSheetLineChart() {

        linechart12.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart13.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart14.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart22.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart23.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart24.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart32.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart33.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart34.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart42.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart43.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart44.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart52.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart53.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart54.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart62.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart63.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart64.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart72.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart73.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart74.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());

        linechart82.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart83.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
        linechart84.getStylesheets().add(
                getClass().getResource("./css/linechart.css").toExternalForm());
    }

    private void addVariableDialogListener() {
        // Add Variable Button
        addvariable.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AddVariableDialogBox.openVariableDialogBox();
            }
        });
    }

    public AnchorPane getMainAnchorPane() {
        return mainAnchorPane;
    }

    public AnchorPane getTabPaneAnchorPane() {
        return tabPaneAnchorPane;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    /**
     * All Setter Methods
     */

    public LineChartControllerTab1 getLineChartControllerTab1() {
        return lineChartControllerTab1;
    }

    public StackPane getStackpane1() {
        return stackpane1;
    }

    public StackPane getStackpane2() {
        return stackpane2;
    }

    public LineChartControllerTab2 getLineChartControllerTab2() {
        return lineChartControllerTab2;
    }

    public TableView<Variable> getTableview() {
        return tableview;
    }

    public TableColumn<Variable, String> getTname() {
        return tname;
    }

    public TableColumn<Variable, String> getTaddress() {
        return taddress;
    }

    public TableColumn<Variable, String> getTtype() {
        return ttype;
    }

    public TableColumn<Variable, String> getTvalue() {
        return tvalue;
    }

    public TableColumn<Variable, String> getTscaledvalue() {
        return tscaledvalue;
    }

    public TableColumn<Variable, String> getTunit() {
        return tunit;
    }

    public AnchorPane getTableanchorpane() {
        return tableanchorpane;
    }

    public TabPane getTabpane() {
        return tabpane;
    }

    public Tab getTab1() {
        return tab1;
    }

    public Tab getTab2() {
        return tab2;
    }

    public Tab getTab3() {
        return tab3;
    }

    public Tab getTab4() {
        return tab4;
    }

    public Tab getTab5() {
        return tab5;
    }

    public Tab getTab6() {
        return tab6;
    }

    public Tab getTab7() {
        return tab7;
    }

    public Tab getTab8() {
        return tab8;
    }

    public StackPane getStackpane3() {
        return stackpane3;
    }

    public StackPane getStackpane4() {
        return stackpane4;
    }

    public StackPane getStackpane5() {
        return stackpane5;
    }

    public StackPane getStackpane6() {
        return stackpane6;
    }

    public StackPane getStackpane7() {
        return stackpane7;
    }

    public StackPane getStackpane8() {
        return stackpane8;
    }

    public LineChart<Number, Number> getLinechart11() {
        return linechart11;
    }

    public LineChart<Number, Number> getLinechart12() {
        return linechart12;
    }

    public LineChart<Number, Number> getLinechart13() {
        return linechart13;
    }

    public LineChart<Number, Number> getLinechart14() {
        return linechart14;
    }

    public LineChart<Number, Number> getLinechart21() {
        return linechart21;
    }

    public LineChart<Number, Number> getLinechart22() {
        return linechart22;
    }

    public LineChart<Number, Number> getLinechart23() {
        return linechart23;
    }

    public LineChart<Number, Number> getLinechart24() {
        return linechart24;
    }

    public LineChart<Number, Number> getLinechart31() {
        return linechart31;
    }

    public LineChart<Number, Number> getLinechart32() {
        return linechart32;
    }

    public LineChart<Number, Number> getLinechart33() {
        return linechart33;
    }

    public LineChart<Number, Number> getLinechart34() {
        return linechart34;
    }

    public LineChart<Number, Number> getLinechart41() {
        return linechart41;
    }

    public LineChart<Number, Number> getLinechart42() {
        return linechart42;
    }

    public LineChart<Number, Number> getLinechart43() {
        return linechart43;
    }

    public LineChart<Number, Number> getLinechart44() {
        return linechart44;
    }

    public LineChart<Number, Number> getLinechart51() {
        return linechart51;
    }

    public LineChart<Number, Number> getLinechart52() {
        return linechart52;
    }

    public LineChart<Number, Number> getLinechart53() {
        return linechart53;
    }

    public LineChart<Number, Number> getLinechart54() {
        return linechart54;
    }

    public LineChart<Number, Number> getLinechart61() {
        return linechart61;
    }

    public LineChart<Number, Number> getLinechart62() {
        return linechart62;
    }

    public LineChart<Number, Number> getLinechart63() {
        return linechart63;
    }

    public LineChart<Number, Number> getLinechart64() {
        return linechart64;
    }

    public LineChart<Number, Number> getLinechart71() {
        return linechart71;
    }

    public LineChart<Number, Number> getLinechart72() {
        return linechart72;
    }

    public LineChart<Number, Number> getLinechart73() {
        return linechart73;
    }

    public LineChart<Number, Number> getLinechart74() {
        return linechart74;
    }

    public LineChart<Number, Number> getLinechart81() {
        return linechart81;
    }

    public LineChart<Number, Number> getLinechart82() {
        return linechart82;
    }

    public LineChart<Number, Number> getLinechart83() {
        return linechart83;
    }

    public LineChart<Number, Number> getLinechart84() {
        return linechart84;
    }

    public Button getChannel1_button_tab1() {
        return channel1_button_tab1;
    }

    public ComboBox<String> getChannel1_scale_tab1() {
        return channel1_scale_tab1;
    }

    public Button getChannel1_up_tab1() {
        return channel1_up_tab1;
    }

    public Button getChannel1_down_tab1() {
        return channel1_down_tab1;
    }

    public Button getChannel2_button_tab1() {
        return channel2_button_tab1;
    }

    public ComboBox<String> getChannel2_scale_tab1() {
        return channel2_scale_tab1;
    }

    public Button getChannel2_up_tab1() {
        return channel2_up_tab1;
    }

    public Button getChannel2_down_tab1() {
        return channel2_down_tab1;
    }

    public Button getChannel3_button_tab1() {
        return channel3_button_tab1;
    }

    public ComboBox<String> getChannel3_scale_tab1() {
        return channel3_scale_tab1;
    }

    public Button getChannel3_up_tab1() {
        return channel3_up_tab1;
    }

    public Button getChannel3_down_tab1() {
        return channel3_down_tab1;
    }

    public Button getChannel4_button_tab1() {
        return channel4_button_tab1;
    }

    public ComboBox<String> getChannel4_scale_tab1() {
        return channel4_scale_tab1;
    }

    public Button getChannel4_up_tab1() {
        return channel4_up_tab1;
    }

    public Button getChannel4_down_tab1() {
        return channel4_down_tab1;
    }

    public Button getChartproperties_tab1() {
        return chartproperties_tab1;
    }

    public ToggleButton getRuntoggle_tab1() {
        return runtoggle_tab1;
    }

    public ComboBox<String> getSampling_tab1() {
        return sampling_tab1;
    }

    public ComboBox<String> getTrigger_tab1() {
        return trigger_tab1;
    }

    public ComboBox<String> getChannelcombobox_tab1() {
        return channelcombobox_tab1;
    }

    public ComboBox<String> getEnablecombobox_tab1() {
        return enablecombobox_tab1;
    }

    public Button getCursor_tab1() {
        return cursor_tab1;
    }

    public Button getMeasure_tab1() {
        return measure_tab1;
    }

    public Button getChannel1_button_tab2() {
        return channel1_button_tab2;
    }

    public ComboBox<String> getChannel1_scale_tab2() {
        return channel1_scale_tab2;
    }

    public Button getChannel1_up_tab2() {
        return channel1_up_tab2;
    }

    public Button getChannel1_down_tab2() {
        return channel1_down_tab2;
    }

    public Button getChannel2_button_tab2() {
        return channel2_button_tab2;
    }

    public ComboBox<String> getChannel2_scale_tab2() {
        return channel2_scale_tab2;
    }

    public Button getChannel2_up_tab2() {
        return channel2_up_tab2;
    }

    public Button getChannel2_down_tab2() {
        return channel2_down_tab2;
    }

    public Button getChannel3_button_tab2() {
        return channel3_button_tab2;
    }

    public ComboBox<String> getChannel3_scale_tab2() {
        return channel3_scale_tab2;
    }

    public Button getChannel3_up_tab2() {
        return channel3_up_tab2;
    }

    public Button getChannel3_down_tab2() {
        return channel3_down_tab2;
    }

    public Button getChannel4_button_tab2() {
        return channel4_button_tab2;
    }

    public ComboBox<String> getChannel4_scale_tab2() {
        return channel4_scale_tab2;
    }

    public Button getChannel4_up_tab2() {
        return channel4_up_tab2;
    }

    public Button getChannel4_down_tab2() {
        return channel4_down_tab2;
    }

    public Button getChartproperties_tab2() {
        return chartproperties_tab2;
    }

    public ToggleButton getRuntoggle_tab2() {
        return runtoggle_tab2;
    }

    public ComboBox<String> getSampling_tab2() {
        return sampling_tab2;
    }

    public ComboBox<String> getTrigger_tab2() {
        return trigger_tab2;
    }

    public ComboBox<String> getChannelcombobox_tab2() {
        return channelcombobox_tab2;
    }

    public ComboBox<String> getEnablecombobox_tab2() {
        return enablecombobox_tab2;
    }

    public Button getCursor_tab2() {
        return cursor_tab2;
    }

    public Button getMeasure_tab2() {
        return measure_tab2;
    }

    public Button getChannel1_button_tab3() {
        return channel1_button_tab3;
    }

    public ComboBox<String> getChannel1_scale_tab3() {
        return channel1_scale_tab3;
    }

    public Button getChannel1_up_tab3() {
        return channel1_up_tab3;
    }

    public Button getChannel1_down_tab3() {
        return channel1_down_tab3;
    }

    public Button getChannel2_button_tab3() {
        return channel2_button_tab3;
    }

    public ComboBox<String> getChannel2_scale_tab3() {
        return channel2_scale_tab3;
    }

    public Button getChannel2_up_tab3() {
        return channel2_up_tab3;
    }

    public Button getChannel2_down_tab3() {
        return channel2_down_tab3;
    }

    public Button getChannel3_button_tab3() {
        return channel3_button_tab3;
    }

    public ComboBox<String> getChannel3_scale_tab3() {
        return channel3_scale_tab3;
    }

    public Button getChannel3_up_tab3() {
        return channel3_up_tab3;
    }

    public Button getChannel3_down_tab3() {
        return channel3_down_tab3;
    }

    public Button getChannel4_button_tab3() {
        return channel4_button_tab3;
    }

    public ComboBox<String> getChannel4_scale_tab3() {
        return channel4_scale_tab3;
    }

    public Button getChannel4_up_tab3() {
        return channel4_up_tab3;
    }

    public Button getChannel4_down_tab3() {
        return channel4_down_tab3;
    }

    public Button getChartproperties_tab3() {
        return chartproperties_tab3;
    }

    public ToggleButton getRuntoggle_tab3() {
        return runtoggle_tab3;
    }

    public ComboBox<String> getSampling_tab3() {
        return sampling_tab3;
    }

    public ComboBox<String> getTrigger_tab3() {
        return trigger_tab3;
    }

    public ComboBox<String> getChannelcombobox_tab3() {
        return channelcombobox_tab3;
    }

    public ComboBox<String> getEnablecombobox_tab3() {
        return enablecombobox_tab3;
    }

    public Button getCursor_tab3() {
        return cursor_tab3;
    }

    public Button getMeasure_tab3() {
        return measure_tab3;
    }

    public Button getChannel1_button_tab4() {
        return channel1_button_tab4;
    }

    public ComboBox<String> getChannel1_scale_tab4() {
        return channel1_scale_tab4;
    }

    public Button getChannel1_up_tab4() {
        return channel1_up_tab4;
    }

    public Button getChannel1_down_tab4() {
        return channel1_down_tab4;
    }

    public Button getChannel2_button_tab4() {
        return channel2_button_tab4;
    }

    public ComboBox<String> getChannel2_scale_tab4() {
        return channel2_scale_tab4;
    }

    public Button getChannel2_up_tab4() {
        return channel2_up_tab4;
    }

    public Button getChannel2_down_tab4() {
        return channel2_down_tab4;
    }

    public Button getChannel3_button_tab4() {
        return channel3_button_tab4;
    }

    public ComboBox<String> getChannel3_scale_tab4() {
        return channel3_scale_tab4;
    }

    public Button getChannel3_up_tab4() {
        return channel3_up_tab4;
    }

    public Button getChannel3_down_tab4() {
        return channel3_down_tab4;
    }

    public Button getChannel4_button_tab4() {
        return channel4_button_tab4;
    }

    public ComboBox<String> getChannel4_scale_tab4() {
        return channel4_scale_tab4;
    }

    public Button getChannel4_up_tab4() {
        return channel4_up_tab4;
    }

    public Button getChannel4_down_tab4() {
        return channel4_down_tab4;
    }

    public Button getChartproperties_tab4() {
        return chartproperties_tab4;
    }

    public ToggleButton getRuntoggle_tab4() {
        return runtoggle_tab4;
    }

    public ComboBox<String> getSampling_tab4() {
        return sampling_tab4;
    }

    public ComboBox<String> getTrigger_tab4() {
        return trigger_tab4;
    }

    public ComboBox<String> getChannelcombobox_tab4() {
        return channelcombobox_tab4;
    }

    public ComboBox<String> getEnablecombobox_tab4() {
        return enablecombobox_tab4;
    }

    public Button getCursor_tab4() {
        return cursor_tab4;
    }

    public Button getMeasure_tab4() {
        return measure_tab4;
    }

    public Button getChannel1_button_tab5() {
        return channel1_button_tab5;
    }

    public ComboBox<String> getChannel1_scale_tab5() {
        return channel1_scale_tab5;
    }

    public Button getChannel1_up_tab5() {
        return channel1_up_tab5;
    }

    public Button getChannel1_down_tab5() {
        return channel1_down_tab5;
    }

    public Button getChannel2_button_tab5() {
        return channel2_button_tab5;
    }

    public ComboBox<String> getChannel2_scale_tab5() {
        return channel2_scale_tab5;
    }

    public Button getChannel2_up_tab5() {
        return channel2_up_tab5;
    }

    public Button getChannel2_down_tab5() {
        return channel2_down_tab5;
    }

    public Button getChannel3_button_tab5() {
        return channel3_button_tab5;
    }

    public ComboBox<String> getChannel3_scale_tab5() {
        return channel3_scale_tab5;
    }

    public Button getChannel3_up_tab5() {
        return channel3_up_tab5;
    }

    public Button getChannel3_down_tab5() {
        return channel3_down_tab5;
    }

    public Button getChannel4_button_tab5() {
        return channel4_button_tab5;
    }

    public ComboBox<String> getChannel4_scale_tab5() {
        return channel4_scale_tab5;
    }

    public Button getChannel4_up_tab5() {
        return channel4_up_tab5;
    }

    public Button getChannel4_down_tab5() {
        return channel4_down_tab5;
    }

    public Button getChartproperties_tab5() {
        return chartproperties_tab5;
    }

    public ToggleButton getRuntoggle_tab5() {
        return runtoggle_tab5;
    }

    public ComboBox<String> getSampling_tab5() {
        return sampling_tab5;
    }

    public ComboBox<String> getTrigger_tab5() {
        return trigger_tab5;
    }

    public ComboBox<String> getChannelcombobox_tab5() {
        return channelcombobox_tab5;
    }

    public ComboBox<String> getEnablecombobox_tab5() {
        return enablecombobox_tab5;
    }

    public Button getCursor_tab5() {
        return cursor_tab5;
    }

    public Button getMeasure_tab5() {
        return measure_tab5;
    }

    public Button getChannel1_button_tab6() {
        return channel1_button_tab6;
    }

    public ComboBox<String> getChannel1_scale_tab6() {
        return channel1_scale_tab6;
    }

    public Button getChannel1_up_tab6() {
        return channel1_up_tab6;
    }

    public Button getChannel1_down_tab6() {
        return channel1_down_tab6;
    }

    public Button getChannel2_button_tab6() {
        return channel2_button_tab6;
    }

    public ComboBox<String> getChannel2_scale_tab6() {
        return channel2_scale_tab6;
    }

    public Button getChannel2_up_tab6() {
        return channel2_up_tab6;
    }

    public Button getChannel2_down_tab6() {
        return channel2_down_tab6;
    }

    public Button getChannel3_button_tab6() {
        return channel3_button_tab6;
    }

    public ComboBox<String> getChannel3_scale_tab6() {
        return channel3_scale_tab6;
    }

    public Button getChannel3_up_tab6() {
        return channel3_up_tab6;
    }

    public Button getChannel3_down_tab6() {
        return channel3_down_tab6;
    }

    public Button getChannel4_button_tab6() {
        return channel4_button_tab6;
    }

    public ComboBox<String> getChannel4_scale_tab6() {
        return channel4_scale_tab6;
    }

    public Button getChannel4_up_tab6() {
        return channel4_up_tab6;
    }

    public Button getChannel4_down_tab6() {
        return channel4_down_tab6;
    }

    public Button getChartproperties_tab6() {
        return chartproperties_tab6;
    }

    public ToggleButton getRuntoggle_tab6() {
        return runtoggle_tab6;
    }

    public ComboBox<String> getSampling_tab6() {
        return sampling_tab6;
    }

    public ComboBox<String> getTrigger_tab6() {
        return trigger_tab6;
    }

    public ComboBox<String> getChannelcombobox_tab6() {
        return channelcombobox_tab6;
    }

    public ComboBox<String> getEnablecombobox_tab6() {
        return enablecombobox_tab6;
    }

    public Button getCursor_tab6() {
        return cursor_tab6;
    }

    public Button getMeasure_tab6() {
        return measure_tab6;
    }

    public Button getChannel1_button_tab7() {
        return channel1_button_tab7;
    }

    public ComboBox<String> getChannel1_scale_tab7() {
        return channel1_scale_tab7;
    }

    public Button getChannel1_up_tab7() {
        return channel1_up_tab7;
    }

    public Button getChannel1_down_tab7() {
        return channel1_down_tab7;
    }

    public Button getChannel2_button_tab7() {
        return channel2_button_tab7;
    }

    public ComboBox<String> getChannel2_scale_tab7() {
        return channel2_scale_tab7;
    }

    public Button getChannel2_up_tab7() {
        return channel2_up_tab7;
    }

    public Button getChannel2_down_tab7() {
        return channel2_down_tab7;
    }

    public Button getChannel3_button_tab7() {
        return channel3_button_tab7;
    }

    public ComboBox<String> getChannel3_scale_tab7() {
        return channel3_scale_tab7;
    }

    public Button getChannel3_up_tab7() {
        return channel3_up_tab7;
    }

    public Button getChannel3_down_tab7() {
        return channel3_down_tab7;
    }

    public Button getChannel4_button_tab7() {
        return channel4_button_tab7;
    }

    public ComboBox<String> getChannel4_scale_tab7() {
        return channel4_scale_tab7;
    }

    public Button getChannel4_up_tab7() {
        return channel4_up_tab7;
    }

    public Button getChannel4_down_tab7() {
        return channel4_down_tab7;
    }

    public Button getChartproperties_tab7() {
        return chartproperties_tab7;
    }

    public ToggleButton getRuntoggle_tab7() {
        return runtoggle_tab7;
    }

    public ComboBox<String> getSampling_tab7() {
        return sampling_tab7;
    }

    public ComboBox<String> getTrigger_tab7() {
        return trigger_tab7;
    }

    public ComboBox<String> getChannelcombobox_tab7() {
        return channelcombobox_tab7;
    }

    public ComboBox<String> getEnablecombobox_tab7() {
        return enablecombobox_tab7;
    }

    public Button getCursor_tab7() {
        return cursor_tab7;
    }

    public Button getMeasure_tab7() {
        return measure_tab7;
    }

    public Button getChannel1_button_tab8() {
        return channel1_button_tab8;
    }

    public ComboBox<String> getChannel1_scale_tab8() {
        return channel1_scale_tab8;
    }

    public Button getChannel1_up_tab8() {
        return channel1_up_tab8;
    }

    public Button getChannel1_down_tab8() {
        return channel1_down_tab8;
    }

    public Button getChannel2_button_tab8() {
        return channel2_button_tab8;
    }

    public ComboBox<String> getChannel2_scale_tab8() {
        return channel2_scale_tab8;
    }

    public Button getChannel2_up_tab8() {
        return channel2_up_tab8;
    }

    public Button getChannel2_down_tab8() {
        return channel2_down_tab8;
    }

    public Button getChannel3_button_tab8() {
        return channel3_button_tab8;
    }

    public ComboBox<String> getChannel3_scale_tab8() {
        return channel3_scale_tab8;
    }

    public Button getChannel3_up_tab8() {
        return channel3_up_tab8;
    }

    public Button getChannel3_down_tab8() {
        return channel3_down_tab8;
    }

    public Button getChannel4_button_tab8() {
        return channel4_button_tab8;
    }

    public ComboBox<String> getChannel4_scale_tab8() {
        return channel4_scale_tab8;
    }

    public Button getChannel4_up_tab8() {
        return channel4_up_tab8;
    }

    public Button getChannel4_down_tab8() {
        return channel4_down_tab8;
    }

    public Button getChartproperties_tab8() {
        return chartproperties_tab8;
    }

    public ToggleButton getRuntoggle_tab8() {
        return runtoggle_tab8;
    }

    public ComboBox<String> getSampling_tab8() {
        return sampling_tab8;
    }

    public ComboBox<String> getTrigger_tab8() {
        return trigger_tab8;
    }

    public ComboBox<String> getChannelcombobox_tab8() {
        return channelcombobox_tab8;
    }

    public ComboBox<String> getEnablecombobox_tab8() {
        return enablecombobox_tab8;
    }

    public Button getCursor_tab8() {
        return cursor_tab8;
    }

    public Button getMeasure_tab8() {
        return measure_tab8;
    }

    public ToolBar getToolbar() {
        return toolbar;
    }

    public Label getInfolabel() {
        return infolabel;
    }

    public Button getTableproperties() {
        return tableproperties;
    }

    public Button getAddvariable() {
        return addvariable;
    }

    public Button getRemovevariable() {
        return removevariable;
    }

    public MenuItem getNewmenu() {
        return newmenu;
    }

    public MenuItem getOpenmenu() {
        return openmenu;
    }

    public MenuItem getSaveasmenu() {
        return saveasmenu;
    }

    public MenuItem getExitmenu() {
        return exitmenu;
    }

    public Menu getRecentmenu() {
        return recentmenu;
    }

    public MenuItem getFlashtoolmenu() {
        return flashtoolmenu;
    }

    public MenuItem getNewvariablemenu() {
        return newvariablemenu;
    }

    public MenuItem getNewoscilloscopemenu() {
        return newoscilloscopemenu;
    }

    public MenuItem getNewgraphmenu() {
        return newgraphmenu;
    }

    public MenuItem getCascademenu() {
        return cascademenu;
    }

    public MenuItem getTilehorizontalmenu() {
        return tilehorizontalmenu;
    }

    public MenuItem getTileverticalmenu() {
        return tileverticalmenu;
    }

    public RadioMenuItem getWhitethememenu() {
        return whitethememenu;
    }

    public RadioMenuItem getBluethememenu() {
        return bluethememenu;
    }

    public RadioMenuItem getBlackthememenu() {
        return blackthememenu;
    }

    public MenuItem getHelpindexmenu() {
        return helpindexmenu;
    }

    public MenuItem getAboutxspymenu() {
        return aboutxspymenu;
    }

    public Button getPlaytoolbar() {
        return playtoolbar;
    }

    public Button getStoptoolbar() {
        return stoptoolbar;
    }

    public Button getFlashtoolbar() {
        return flashtoolbar;
    }

    public LineChartControllerTab3 getLineChartControllerTab3() {
        return lineChartControllerTab3;
    }

    public LineChartControllerTab4 getLineChartControllerTab4() {
        return lineChartControllerTab4;
    }

    public LineChartControllerTab5 getLineChartControllerTab5() {
        return lineChartControllerTab5;
    }

    public LineChartControllerTab6 getLineChartControllerTab6() {
        return lineChartControllerTab6;
    }

    public LineChartControllerTab7 getLineChartControllerTab7() {
        return lineChartControllerTab7;
    }

    public LineChartControllerTab8 getLineChartControllerTab8() {
        return lineChartControllerTab8;
    }

    public TabPane getVariableTabPane() {
        return variableTabPane;
    }

    public MenuItem getSaveSession() {
        return saveSession;
    }
}
