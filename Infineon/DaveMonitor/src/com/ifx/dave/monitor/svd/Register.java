package com.ifx.dave.monitor.svd;

import java.util.HashMap;
import java.util.Map;

public class Register {
    String registerName;
    String resetValue;
    String defaultValue;

    public String getResetValue() {
        return resetValue;
    }

    public void setResetValue(String resetValue) {
        this.resetValue = resetValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerNAme) {
        this.registerName = registerNAme;
    }

    public Map<String, BitField> bitfieldMap = new HashMap<String, BitField>();
}
