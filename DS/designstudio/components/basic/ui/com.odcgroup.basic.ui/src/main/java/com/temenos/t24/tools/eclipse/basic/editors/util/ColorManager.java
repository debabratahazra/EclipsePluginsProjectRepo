package com.temenos.t24.tools.eclipse.basic.editors.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {
    private final static int COLOR_MAP_SIZE = 10;
	private Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(COLOR_MAP_SIZE);

	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
    
    public void dispose() {
        Iterator<Color> e = fColorTable.values().iterator();
        while (e.hasNext())
             ((Color) e.next()).dispose();
    }    
}
