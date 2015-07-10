package com.zealcore.se.ui.views;

import java.util.ArrayList;
import java.util.List;

public class XYSeries {

    private final List<XYDataItem> data = new ArrayList<XYDataItem>();
    
    private String name;

    public String getName() {
		return name;
	}

	public XYSeries(final String name) {
        this.name = name;
    }

    public List<XYDataItem> getData() {
        return data;
    }

    public void addValue(final Number x, final Number y) {
        XYDataItem item = new XYDataItem(x, y);
        data.add(item);
    }
}
