package com.ifx.dave.monitor.elf.model;

import java.io.Serializable;

public class Variable implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String variableName;
    private String address;
    private String variableType;
    private String value;
    private String scalevalue;
    private String unit;

    public Variable() {
    }

    public Variable(String variableName, String address, String variableType,
            String value) {
        this.variableName = variableName;
        this.address = address;
        this.variableType = variableType;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getValue() {
        if (value == null) {
            return "0";
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScalevalue() {
        if (scalevalue == null) {
            return "0";
        }
        return scalevalue;
    }

    public void setScalevalue(String scalevalue) {
        this.scalevalue = scalevalue;
    }

    public String getUnit() {
        if (unit == null) {
            return "rpm"; // Default RPM set
        }
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
