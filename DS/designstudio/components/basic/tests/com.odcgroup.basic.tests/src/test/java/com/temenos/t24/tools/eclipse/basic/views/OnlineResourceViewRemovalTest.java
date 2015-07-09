package com.temenos.t24.tools.eclipse.basic.views;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.junit.Assert;
import org.junit.Test;

public class OnlineResourceViewRemovalTest {
	final private String viewId = "com.temenos.t24.tools.eclipse.basic.viewOnlineSrc";

	@Test
	public void testRemoveOnlineResourceView() {
		IViewPart findView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(viewId);
		Assert.assertNull("Online Resource View does not exists!", findView);
	}

}
