package com.odcgroup.server.ui.views;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;

public class ServerTreeLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IDSServer) {
			return getDisplayedServerLabel((IDSServer)element);
		} else {
			return ((IDSProject) element).getName();
		}
	}

	public static String getDisplayedServerLabel(IDSServer server) {
		String label = server.getName(); 
		if (StringUtils.isNotEmpty(server.getListeningPort())) {
			label = label + " on " + server.getListeningPort();
		}
		return label;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {		
	}

	@Override
	public void dispose() {		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {		
	}

}
