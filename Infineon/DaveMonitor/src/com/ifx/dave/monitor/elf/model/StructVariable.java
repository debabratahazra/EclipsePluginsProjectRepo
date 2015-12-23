package com.ifx.dave.monitor.elf.model;

import java.util.ArrayList;
import java.util.List;

public class StructVariable {

	private String structName;
	private List<String> structVariableType;
	private List<String> structVariableName;

	public StructVariable(String structName, List<String> structVariableType,
			List<String> structVariableName) {
		this.structName = structName;
		this.structVariableType = structVariableType;
		this.structVariableName = structVariableName;
	}

	public StructVariable() {
		structVariableType = new ArrayList<String>();
		structVariableName = new ArrayList<String>();
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public String[] getStructVariableType() {
		String[] srtuctVarType = new String[structVariableType.size()];
		srtuctVarType = structVariableType.toArray(srtuctVarType);
		return srtuctVarType;
	}

	public void addStructVariableType(String structVariableType) {
		this.structVariableType.add(structVariableType);
	}

	public String[] getStructVariableName() {
		String[] srtuctVarName = new String[structVariableName.size()];
		srtuctVarName = structVariableName.toArray(srtuctVarName);
		return srtuctVarName;
	}

	public void addStructVariableName(String structVariableName) {
		this.structVariableName.add(structVariableName);
	}
}
