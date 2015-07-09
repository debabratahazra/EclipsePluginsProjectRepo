package com.odcgroup.page.model.validator;


/**
 * @author atr
 */
public class GridColumnWidthPropertyValidator extends AbstractPropertyValidator implements PropertyValidator {

	/**
	 * @see com.odcgroup.page.model.validator.PropertyValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		/*
		Property p = getProperty();
		if (p != null) {
			int width = Integer.parseInt((String)value);
			GridCell cell = new GridCellAdapter(p.getWidget());
			int myColIndex = cell.getColumnIndex();
			Grid grid = cell.getGrid();
			int totalWidths = width;
			int nbColumns = grid.getColumnCount();
			for (int cx = 0; cx < nbColumns; cx++) {
				if (cx != myColIndex) {
					totalWidths += grid.getColumnWidth(cx);
				}
			}
			if (totalWidths > 100) {
				return "the sum of column widths cannot be greater than 100";
			}
		}
		*/
		try {
			int width = Integer.parseInt((String)value);
			if (width < 0) {
				return "negative value specified";
			}
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return null;
	}

}
