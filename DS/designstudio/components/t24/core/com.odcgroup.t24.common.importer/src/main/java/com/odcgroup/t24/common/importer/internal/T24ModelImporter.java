package com.odcgroup.t24.common.importer.internal;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;

import com.odcgroup.t24.common.importer.IContainerSelector;
import com.odcgroup.t24.common.importer.IServerSelector;
import com.odcgroup.t24.common.importer.IT24ImportModel;
import com.odcgroup.t24.server.external.model.IExternalServer;

public abstract class T24ModelImporter implements IT24ImportModel, IServerSelector, IContainerSelector {

	private String modelName = "";
	private IContainer container;
	private IExternalServer server;
	
	protected void serverChanged() {
	}
	
	protected IProject getProject() {
		return container != null ? container.getProject() : null;
	}
	
	public final IServerSelector getServerSelector() {
		return this;
	}

	public final IContainerSelector getContainerSelector() {
		return this;
	}
	
	
	public final void setModelName(String modelName) {
		this.modelName = modelName;		
	}
	
	@Override
	public final String getModelName() {
		return this.modelName;
	}

	@Override
	public final void setContainer(IContainer container) {
		this.container = container;
	}

	@Override
	public final IContainer getContainer() {
		return this.container;
	}

	@Override
	public final boolean isContainerSet() {
		return container != null;
	}

	@Override
	public final IExternalServer getServer() {
		return server;
	}

	@Override
	public final void setServer(IExternalServer server) {
		if (! server.equals(this.server)) {
			this.server = server; 
			serverChanged();
		}
	}

	@Override
	public final boolean isServerSet() {
		return this.server != null;
	}


}
