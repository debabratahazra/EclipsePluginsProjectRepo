package com.odcgroup.aaa.generation.gateway.line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.aaa.generation.gateway.line.value.DATValue;


public class DAT {
	
	private List<DATValue> data = new ArrayList<DATValue>();
	
	public DAT(DATValue... data) {
		this.data.addAll(Arrays.asList(data));
	}
	
	public DAT(List<DATValue> data) {
		this.data.addAll(data);
	}
	
	public List<DATValue> getData() {
		return Collections.unmodifiableList(data);
	}
	
	public void setData(List<DATValue> newData) {
		List<DATValue> newValue = new ArrayList<DATValue>();
		newValue.addAll(newData);
		this.data = newValue;
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("DAT ");
		for (DATValue value: data) {
			result.append(value.toString());
			result.append(" ");
		}
		return StringUtils.removeEnd(result.toString(), " ");
	}

}
