package com.odcgroup.page.ui.edit.grid;

import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class GridWidgetEditPart extends WidgetEditPart {
	
	protected Grid getGrid() {
		return new GridAdapter(getWidget());
	}

	
}
