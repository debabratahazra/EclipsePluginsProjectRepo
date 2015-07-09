package com.odcgroup.server.ui.views;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.server.util.DSServerUtil;

/**
 *
 * @author pkk
 *
 */
public class ServerTreeColumnLabelProvider extends ColumnLabelProvider {
	
	private String column;
	private static String ICON_SERVER = "icons/dsserver.gif";
	private static String ICON_PROJ = "icons/project.gif";
	
	private Image serverImage;
	private Image projectImage;
	
	/**
	 * @param column
	 */
	public ServerTreeColumnLabelProvider(String column) {
		this.column = column;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof IDSServer) {
			IDSServer server = (IDSServer) element;
			if (column.equals(ServerView.SERVER_COL)) {
				String label = server.getName(); 
				if (StringUtils.isNotEmpty(server.getListeningPort())) {
					label = label + " on " + server.getListeningPort();
				}
				return label;
			} else if (column.equals(ServerView.STATE_COL)) {
				return DSServerUtil.getServerState(server.getState());
			} 
		} else if (element instanceof IDSProject) {
			IDSProject project = (IDSProject) element;
			if (column.equals(ServerView.SERVER_COL)) {
				return project.getName();
			} else {
				return null;
			}
		}
		return super.getText(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof IDSServer) {
			if (column.equals(ServerView.SERVER_COL)) {
				if (serverImage == null) {
					serverImage = getImage(ICON_SERVER);
				}
				return serverImage;
			}
		} else if (element instanceof IDSProject) {
			if (column.equals(ServerView.SERVER_COL)) {
				if (projectImage == null) {
					projectImage = getImage(ICON_PROJ);
				}
				return projectImage;
			}
		}
		return super.getImage(element);
	}
	
	/**
	 * @param path
	 * @return
	 */
	private Image getImage(String path) {
		return ServerUICore.getImageDescriptor(path).createImage();
	}
	
	public void dispose() {
		if (serverImage != null) {
			serverImage.dispose();
			serverImage = null;
		}
		if (projectImage != null) {
			projectImage.dispose();
			projectImage = null;
		}
		super.dispose();
	}

}
