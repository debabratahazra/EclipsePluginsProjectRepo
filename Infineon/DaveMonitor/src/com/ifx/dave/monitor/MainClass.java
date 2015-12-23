package com.ifx.dave.monitor;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ifx.dave.monitor.elf.ElfFileParser;
import com.ifx.dave.monitor.elf.model.StructVariable;
import com.ifx.dave.monitor.elf.model.Variable;
import com.ifx.dave.monitor.svd.SvdFileParsing;
import com.ifx.dave.monitor.ui.MainWindow;

public class MainClass {

    private static List<Variable> globalVariables;
    private static List<StructVariable> globalStructVariables;
    private static Map<String, Object> peripheralsMap;
    public static Logger logger = Logger.getLogger(MainClass.class);

    URL logConfigUrl = ClassLoader.getSystemResource("log4j.properties");

    public static Map<String, Object> getPeripheralsMap() {
        return peripheralsMap;
    }

    public static void setPeripheralsMap(Map<String, Object> peripheralsMap) {
        MainClass.peripheralsMap = peripheralsMap;
    }

    static {
        peripheralsMap = new HashMap<String, Object>();
        globalVariables = new ArrayList<Variable>();
        globalStructVariables = new ArrayList<StructVariable>();
    }

    public static List<Variable> getGlobalVariables() {
        return globalVariables;
    }

    public static void setGlobalVariable(Variable globalVariable) {
        MainClass.globalVariables.add(globalVariable);
    }

    public static List<StructVariable> getGlobalStructVariables() {
        return globalStructVariables;
    }

    public static void setGlobalStructVariable(
            StructVariable globalStructVariable) {
        MainClass.globalStructVariables.add(globalStructVariable);
    }

    public static void main(String[] args) throws Exception {
        new SvdFileParsing().readSvdfile();
        ElfFileParser.parser();
        MainWindow.show(args);

    }
}
