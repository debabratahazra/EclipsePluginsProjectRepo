package com.odcgroup.page.ui.properties.sections.table;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * The class {@code BaseTablePropertySection} provides common behaviors to all
 * section related to Table widget.
 * 
 * @author atr,pkk
 * @since DS 1.40.0
 */
public class BaseTablePropertySection extends AbstractPagePropertySection {

	/** The selected table. */
	private ITable table = null;

	/**
	 * @return the ITable adapter
	 */
	protected final ITable getTable() {
		return this.table;
	}

	/**
	 * Notifies the section that the workbench selection has changed.
	 * 
	 * @param part
	 *            The active workbench part.
	 * @param selection
	 *            The active selection in the workbench part.
	 * 
	 *            Implementation note: the selected table widget is wrapped into
	 *            a ITable adapter
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		Object input = ((IStructuredSelection) selection).getFirstElement();
		Widget widget = (Widget) AdaptableUtils.getAdapter(input, Widget.class);
		if (widget != null) {
			this.table = TableHelper.getTable(widget);
		} else {
			// TODO - manage exception
		}

		super.setInput(part, selection);
	}
}
