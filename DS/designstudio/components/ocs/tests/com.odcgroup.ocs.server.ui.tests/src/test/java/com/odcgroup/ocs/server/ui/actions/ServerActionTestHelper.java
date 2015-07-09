package com.odcgroup.ocs.server.ui.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;

public class ServerActionTestHelper {

	/**
	 * @return
	 */
	public static ISelectionProvider newMockSelectionProvider() {
		return new ISelectionProvider() {
			public void addSelectionChangedListener(ISelectionChangedListener listener) {
			}
			public ISelection getSelection() {
				return null;
			}
			public void removeSelectionChangedListener(ISelectionChangedListener listener) {
			}
			public void setSelection(ISelection selection) {
			}
		};
	}

}
