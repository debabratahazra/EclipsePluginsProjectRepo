package com.odcgroup.integrationfwk.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/*
 * Fields class is only used to make XML look good
 */
@XmlSeeAlso({ ApplicationVersionField.class, ComponentServiceField.class,
		Field.class })
public class Fields {
	private List<Field> inputFields = new ArrayList<Field>();

	public boolean addField(Field field) {
		if (inputFields.contains(field)) {
			return false;
		} else {
			inputFields.add(field);
			return true;
		}
	}

	@XmlElement(name = "Field")
	public List<Field> getInputFields() {
		return inputFields;
	}

	public void removeField(Field field) {
		inputFields.remove(field);
	}

	public void setInputFields(List<Field> inputFields) {
		this.inputFields = inputFields;
	}

	@Override
	public String toString() {
		String result = "";
		for (Field f : inputFields) {
			result += " " + f.toString();
		}
		return result;
	}
}
