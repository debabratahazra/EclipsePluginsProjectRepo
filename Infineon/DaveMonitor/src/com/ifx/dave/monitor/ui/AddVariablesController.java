package com.ifx.dave.monitor.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ifx.dave.monitor.MainClass;
import com.ifx.dave.monitor.elf.ElfFileParser;
import com.ifx.dave.monitor.elf.UtilParser;
import com.ifx.dave.monitor.elf.UtilParser.InputStreamDisplay;
import com.ifx.dave.monitor.elf.model.StructVariable;
import com.ifx.dave.monitor.elf.model.Variable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class AddVariablesController {

    @FXML
    private TreeView<String> elftreeview;

    @FXML
    private TreeView<String> svdtreeview;

    private static String PERIPHERALS = "peripherals";
    private static final String TREE_ROOT_NAME = "hiddenRoot";

    private static final String STRUCT_ADDRESS_FILENAME = "StructVarAddress.txt";

    private List<Variable> allVariablesDetails = null;

    @FXML
    private void initialize() {
        elftreeview.getSelectionModel()
                .setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setElfFile() {

        elftreeview.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // allow any transfer mode
                Dragboard db = elftreeview.startDragAndDrop(TransferMode.ANY);

                String variableType = new String();
                String parent = elftreeview.getSelectionModel()
                        .getSelectedItem().getParent().getValue();
                String structVarAddress = new String();
                if (!parent.equals(TREE_ROOT_NAME)) {
                    // Get the parent root name
                    variableType = getVariableType(parent);

                    // Get struct data member address
                    try {
                        structVarAddress = generateStructMemberAddress(
                                elftreeview);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                ClipboardContent content = new ClipboardContent();

                final ObservableList<TreeItem<String>> selectedVariableList = FXCollections
                        .observableArrayList(elftreeview.getSelectionModel()
                                .getSelectedItems());
                List<Variable> listOfVariable = new ArrayList<>();
                for (Iterator<TreeItem<String>> iterator = selectedVariableList
                        .iterator(); iterator.hasNext();) {
                    TreeItem<String> treeItem = (TreeItem<String>) iterator
                            .next();

                    Variable structVariable = new Variable();
                    if (structVarAddress.isEmpty()) {
                        // Not a struct type
                        structVariable = getVariableDetails(
                                treeItem.getValue());

                    } else {
                        // Struct type variable
                        structVariable.setAddress(structVarAddress);
                        String variableType_Name = variableType + "."
                                + treeItem.getValue();
                        int index = variableType_Name.indexOf(" (");
                        if (index != -1) {
                            structVariable.setVariableType(
                                    variableType_Name.substring(0, index));
                            String variableName = variableType_Name
                                    .substring(index);
                            variableName = variableName.replace("(", "")
                                    .replace(")", "").trim();
                            structVariable.setVariableName(variableName);

                            // Get Address for known variable
                            Variable addressVariable = getVariableDetails(
                                    variableName);
                            if (addressVariable != null) {
                                structVariable.setAddress(
                                        addressVariable.getAddress());
                                structVariable.setValue("0"); // Default Value
                            }
                        }
                    }
                    listOfVariable.add(structVariable);
                }
                content.put(DataFormat.PLAIN_TEXT, listOfVariable);
                db.setContent(content);

                event.consume();
            }
        });

        elftreeview.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    try {
                        File saFile = new File(STRUCT_ADDRESS_FILENAME);
                        if (saFile != null && saFile.exists()) {
                            saFile.delete();
                        }
                    } catch (Exception e) {
                    }
                }
                event.consume();
            }
        });

        TreeItem<String> root = new TreeItem<String>(TREE_ROOT_NAME);
        try {
            List<StructVariable> structDetails = MainClass
                    .getGlobalStructVariables();
            List<String> allVariablesname = getVariablesName();
            for (Iterator<StructVariable> iterator = structDetails
                    .iterator(); iterator.hasNext();) {
                StructVariable structVariable = (StructVariable) iterator
                        .next();
                Variable variable = getVariableDetail(
                        structVariable.getStructName() + "_t");
                if (variable != null) {
                    allVariablesname.remove(variable.getVariableName());
                    TreeItem<String> subRoot = new TreeItem<String>(
                            structVariable.getStructName() + " ("
                                    + variable.getVariableName() + ")");
                    String[] structVarTypes = structVariable
                            .getStructVariableType();
                    String[] structVarNames = structVariable
                            .getStructVariableName();
                    if (structVarTypes.length != structVarNames.length) {
                        return;
                    }
                    for (int i = 0; i < structVarTypes.length; i++) {
                        String structVarType = structVarTypes[i];
                        String structVarName = structVarNames[i];
                        TreeItem<String> leafNode = new TreeItem<String>(
                                structVarType + " (" + structVarName + ")");
                        subRoot.getChildren().add(leafNode);

                    }
                    root.getChildren().add(subRoot);
                }

            }
            if (allVariablesname.size() > 0) {
                // Add non structure variables in Tree view
                for (Iterator<String> iterator = allVariablesname
                        .iterator(); iterator.hasNext();) {
                    String variableName = (String) iterator.next();
                    TreeItem<String> leafRoot = new TreeItem<String>(
                            variableName);
                    root.getChildren().add(leafRoot);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        root.setExpanded(true);
        elftreeview.setShowRoot(false);
        elftreeview.setRoot(root);
    }

    private static Variable getVariableDetails(String variablename) {
        List<Variable> variables = MainClass.getGlobalVariables();
        for (Iterator<Variable> iterator = variables.iterator(); iterator
                .hasNext();) {
            Variable variable = (Variable) iterator.next();
            if (variable.getVariableName().equals(variablename)) {
                return variable;
            }
        }
        return null;
    }

    // Get Structure data member address
    protected String generateStructMemberAddress(TreeView<String> treeview)
            throws IOException {
        callStructVaraibleAddressCommand(treeview);
        String structVarAddress = readStructAddressFile();
        if (structVarAddress != null) {
            return structVarAddress;
        }
        return new String();
    }

    private String readStructAddressFile() throws IOException {
        File saFile = new File(STRUCT_ADDRESS_FILENAME);
        if (!saFile.exists()) {
            throw new IOException("StructVarAddress File not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(saFile))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.startsWith("(gdb) $1 =")) {
                    String[] splites = line.split(" ");
                    if (splites.length > 3) {
                        // find the address from console output for struct data
                        // member
                        if (splites[splites.length - 2].startsWith("0x")) {
                            return splites[splites.length - 2];
                        } else
                            if (splites[splites.length - 3].startsWith("0x")) {
                            return splites[splites.length - 3];
                        }

                    }
                }
            }
        }
        return null;
    }

    private void callStructVaraibleAddressCommand(TreeView<String> treeview)
            throws IOException {
        File saFile = new File(STRUCT_ADDRESS_FILENAME);
        UtilParser.writeToFile(false, "", saFile);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c",
                    ElfFileParser.GDB_EXECUTABLE);

            processBuilder.redirectError();
            Process process = processBuilder.start();

            InputStreamDisplay inputStreamDisplay = new InputStreamDisplay(
                    process.getInputStream(), saFile);
            inputStreamDisplay.start();

            BufferedWriter bufferWriter = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()));

            // Load ELF File
            bufferWriter.write("file " + ElfFileParser.ELF_FILENAME);
            bufferWriter.newLine();
            bufferWriter.flush();

            String command = generateCommandForAddress(treeview);
            if (command == null) {
                return;
            }

            // Get struct data member address from GDB
            bufferWriter.write("p &" + command);
            bufferWriter.newLine();
            bufferWriter.flush();

            // Terminate GDB session
            bufferWriter.write("quit");
            bufferWriter.newLine();
            bufferWriter.flush();

            process.waitFor();
            inputStreamDisplay.join();

        } catch (InterruptedException | IOException exp) {
            exp.printStackTrace();
        }
    }

    private String generateCommandForAddress(TreeView<String> treeview) {
        String rootNodeName = treeview.getSelectionModel().getSelectedItem()
                .getParent().getValue();
        String leafNodeName = treeview.getSelectionModel().getSelectedItem()
                .getValue();
        StringBuffer command = new StringBuffer();

        if (rootNodeName != null && !rootNodeName.isEmpty()) {
            int index = rootNodeName.indexOf(" (");
            if (index != -1) {
                String variableName = rootNodeName.substring(index).trim();
                command.append(variableName.replace("(", "").replace(")", ""));
            }
        }
        if (!(command.length() == 0)) {
            command.append(".");
        }
        if (leafNodeName != null && !leafNodeName.isEmpty()) {
            int index = leafNodeName.indexOf(" (");
            if (index != -1) {
                String structVariableName = leafNodeName.substring(index)
                        .trim();
                command.append(
                        structVariableName.replace("(", "").replace(")", ""));
            }
        }
        if (command.length() == 0) {
            return null;
        }
        return command.toString();
    }

    // Extract Variable Type from Tree Selection
    protected String getVariableType(String parent) {
        if (parent == null) {
            return null;
        }
        int index = parent.indexOf(" (");
        if (index == -1) {
            return null;
        }
        String variableType = parent.substring(0, index);
        if (variableType.isEmpty()) {
            return null;
        }
        return variableType;
    }

    // Get all the variables names from ELF file
    private static List<String> getVariablesName() {
        List<String> variableNames = new ArrayList<>();
        List<Variable> variables = MainClass.getGlobalVariables();
        for (Iterator<Variable> iterator = variables.iterator(); iterator
                .hasNext();) {
            Variable variable = (Variable) iterator.next();
            variableNames.add(variable.getVariableName());
        }
        return variableNames;
    }

    // Get the variable detail of Specific Variable Type from ELF file
    private Variable getVariableDetail(String variableType) {
        if (allVariablesDetails == null) {
            allVariablesDetails = new ArrayList<Variable>();
        }
        List<Variable> variables = MainClass.getGlobalVariables();
        for (Iterator<Variable> iterator = variables.iterator(); iterator
                .hasNext();) {
            Variable variable = (Variable) iterator.next();
            allVariablesDetails.add(variable);
        }
        return findVariable(variableType);
    }

    private Variable findVariable(String variableType) {
        if (allVariablesDetails != null) {
            for (Iterator<Variable> iterator = allVariablesDetails
                    .iterator(); iterator.hasNext();) {
                Variable variable = (Variable) iterator.next();
                if (variable.getVariableType().equals(variableType)) {
                    allVariablesDetails.remove(variable);
                    return variable;
                }
            }
        }
        return null;
    }

    // SVD File TreeView for register
    public void svdtreeviewer()
            throws IllegalArgumentException, IllegalAccessException {
        TreeItem<String> peripheralsroot = new TreeItem<String>(PERIPHERALS);

        Map<String, Object> perpheralsMap = MainClass.getPeripheralsMap();
        for (Map.Entry<String, Object> peripheral : perpheralsMap.entrySet()) {
            System.out.println("Peripherals Names are: " + peripheral.getKey());

            TreeItem<String> peripheralroot = new TreeItem<String>(
                    peripheral.getKey());

            peripheralsroot.getChildren().add(peripheralroot);

            Object obj = peripheral.getValue();
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.getName().equals("reisterMap")) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> registers = (Map<String, Object>) field
                            .get(obj);
                    for (Map.Entry<String, Object> register : registers
                            .entrySet()) {
                        if (register.getKey() != null) {
                            System.out.println(
                                    "Regitser Names Are: " + register.getKey());

                            TreeItem<String> registerroot = new TreeItem<String>(
                                    register.getKey());
                            peripheralroot.getChildren().add(registerroot);

                            Object registerObj = register.getValue();
                            for (Field registerfield : registerObj.getClass()
                                    .getDeclaredFields()) {
                                if (registerfield.getName()
                                        .equals("bitfieldMap")) {
                                    @SuppressWarnings("unchecked")
                                    Map<String, Object> bitfield = (Map<String, Object>) registerfield
                                            .get(registerObj);
                                    for (Map.Entry<String, Object> bitfields : bitfield
                                            .entrySet()) {
                                        if (bitfields.getKey() != null) {
                                            registerroot.getChildren()
                                                    .add(new TreeItem<String>(
                                                            bitfields
                                                                    .getKey()));
                                            System.out
                                                    .println("Bitfields Name:="
                                                            + bitfields.getKey()
                                                            + "  And Bit filed Values="
                                                            + bitfields
                                                                    .getValue());

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
        peripheralsroot.setExpanded(true);
        svdtreeview.setShowRoot(true);
        svdtreeview.setRoot(peripheralsroot);

    }

}
