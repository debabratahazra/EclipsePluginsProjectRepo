package com.odcgroup.page.model.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;

/**
 * A widget copier
 * 
 * @author atr
 */
public final class WidgetCopier {

	private WidgetCopier() {
	}

	private static long generateTranslationID() {
		long tid = 0;
		try {
			Thread.sleep(1); // ensure no collision
			tid = System.nanoTime();
		} catch (InterruptedException e) {
			tid = System.nanoTime();
		}
		return tid;
	}

	private static void check(Event event) {
		long tid = event.getTranslationId();
		if (tid > 0) {
			event.setTranslationId(generateTranslationID());
		}
	}

	/**
	 * @param widget
	 */
	private static void check(Widget widget, boolean regenerateID,
			Map<String, String> idmap) {
		long tid = widget.getTranslationId();
		if (tid > 0) {
			widget.setTranslationId(generateTranslationID());
		}

		if (regenerateID && !widget.isDomainWidget()) {
			String oldId = widget.getID();
			UniqueIdGenerator.regenerateID(widget);
			if (widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CELLITEM)) {
				if (idmap != null) {
					idmap.put(oldId, widget.getID());
				}
			}
		}
		for (Event event : widget.getEvents()) {
			check(event);
		}
		for (Widget child : widget.getContents()) {
			check(child, regenerateID, idmap);
			if (child.getTypeName().equals(WidgetTypeConstants.MATRIX_CELLITEM)) {
				String colName = MatrixHelper.getMatrixCellItem(child).getColumnName();
				if(colName == null || colName.startsWith("comp_")) {					
						MatrixHelper.getMatrixCellItem(child).setColumnName(
								"comp_" + child.getID());
				}				
			}
		}
	}

	/**
	 * Make a copy of the given widget and ensure some properties' value are
	 * regenerated if necessary.
	 * 
	 * @param widgetToCopy
	 * @return the new widget
	 */
	public static Widget copy(Widget widgetToCopy) {
		Widget widget = (Widget) EcoreUtil.copy(widgetToCopy);
		check(widget, true, null);
		return widget;
	}

	/**
	 * Make a copy of the given widget and ensure some properties' value are
	 * regenerated if necessary.
	 * 
	 * @param widgetToCopy
	 * @param regenerateID
	 *            if false the value of the widget ID is not regenerated.
	 * @return the new widget
	 */
	public static Widget copy(Widget widgetToCopy, boolean regenerateID) {
		Map<String, String> idmap = new HashMap<String, String>();
		Widget widget = (Widget) EcoreUtil.copy(widgetToCopy);
		check(widget, regenerateID, idmap);
		WidgetUtils.replaceAggregatesForAMatrix(widget, idmap);
		return widget;
	}

	/**
	 * Make a copy of the given event and ensure some properties' value are
	 * regenerated if necessary.
	 * 
	 * @param widgetToCopy
	 * @return the new event
	 */
	public static Event copy(Event eventToCopy) {
		Event event = (Event) EcoreUtil.copy(eventToCopy);
		check(event);
		return event;
	}

}
