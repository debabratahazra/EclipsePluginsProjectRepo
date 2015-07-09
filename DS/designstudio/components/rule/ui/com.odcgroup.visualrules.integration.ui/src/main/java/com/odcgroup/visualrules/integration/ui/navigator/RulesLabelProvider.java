package com.odcgroup.visualrules.integration.ui.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.workbench.ui.OfsUICore;

import de.visualrules.model.Element;
import de.visualrules.model.RuleModel;

public class RulesLabelProvider implements ILabelProvider {
	
	private Image ruleImage;

	public Image getImage(Object element) {

		if(element instanceof IAdaptable) {
			Object adapter = ((IAdaptable) element).getAdapter(Element.class);
			if(adapter instanceof RuleModel) {
				if(ruleImage==null) {
					ImageDescriptor imgDesc = OfsUICore.imageDescriptorFromPlugin(
						RulesIntegrationPlugin.PLUGIN_ID, "/icons/rule.gif");
					ruleImage = imgDesc.createImage();
				}
				return ruleImage;
			}
		}
		return null;
	}

	public String getText(Object element) {
		if(element instanceof IAdaptable) {
			Object adapter = ((IAdaptable) element).getAdapter(Element.class);
			if(adapter instanceof RuleModel) {
				return "Rules";
			}
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		if (ruleImage != null) {
			ruleImage.dispose();
			ruleImage = null;
		}

	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

}
