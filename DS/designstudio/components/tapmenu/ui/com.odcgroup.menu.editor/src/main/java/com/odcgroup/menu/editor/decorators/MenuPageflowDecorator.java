package com.odcgroup.menu.editor.decorators;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;

import com.odcgroup.menu.model.MenuItem;

/**
 * @author pkk
 *
 */
public class MenuPageflowDecorator extends LabelProvider implements ILightweightLabelDecorator {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object, org.eclipse.jface.viewers.IDecoration)
	 */
	public void decorate(Object element, IDecoration decoration) {
		if (element instanceof MenuItem) {
			MenuItem item  = (MenuItem) element;
			if(!StringUtils.isEmpty(item.getPageflow())) {
				String pageflow = item.getPageflow();
				StringBuffer sb = new StringBuffer();
				String extn = ".pageflow";
				String res = "resource:///";
				if (pageflow.startsWith(res) && pageflow.endsWith(extn)) {
					sb.append("  (Pageflow:");
					sb.append(pageflow.substring(res.length()-1, pageflow.length() - extn.length()));
					sb.append(")");
				} else {
					sb.append("  (URL:"+pageflow+")");
				}
				decoration.addSuffix(sb.toString());
			}
		}
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}
