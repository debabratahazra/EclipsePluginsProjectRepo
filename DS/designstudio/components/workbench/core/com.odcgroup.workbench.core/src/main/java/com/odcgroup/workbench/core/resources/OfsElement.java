package com.odcgroup.workbench.core.resources;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.IDependencyManager;
import com.odcgroup.workbench.core.repository.IModelVisitor;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

abstract public class OfsElement implements IOfsElement {
	
	protected final IResource resource;
	protected final IOfsProject ofsProject;
	protected /*final*/ IDependencyManager depMgr; // cannot be final, because OfsModelPackageTest mocks it
	protected final URI uri;
	
	protected int scope;
	
	protected OfsElement(IOfsProject ofsProject, IResource resource) {
		this.ofsProject = ofsProject;
		this.depMgr = OfsCore.getDependencyManager(ofsProject.getProject());
		this.resource = resource;
		if(resource != null) {
			this.uri = ModelURIConverter.createModelURI(resource);
		} else {
			this.uri = null;
		}
	}

	protected OfsElement(IOfsProject ofsProject, IStorage storage) {
		this.ofsProject = ofsProject;
		this.depMgr = OfsCore.getDependencyManager(ofsProject.getProject());
		this.uri = ModelURIConverter.createModelURI(storage);
		if(storage instanceof IResource) {
			this.resource = (IResource) storage;
		} else {
			this.resource = null;
		}
	}

	protected OfsElement(IOfsProject ofsProject, URI uri) {
		this.ofsProject = ofsProject;
		this.depMgr = OfsCore.getDependencyManager(ofsProject.getProject());
		this.uri = uri;
		this.resource = null;
	}

	protected OfsElement(IOfsProject ofsProject, URI uri, IResource iResource) {
		this.ofsProject = ofsProject;
		this.depMgr = OfsCore.getDependencyManager(ofsProject.getProject());
		this.uri = uri;
		this.resource = iResource;
	}

	public IResource getResource() {
		return resource;
	}

	public IOfsProject getOfsProject() {
		return ofsProject;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public String getName() {
		if(uri!=null && uri.lastSegment() != null) {
			return uri.lastSegment();
		}
		if(resource!=null) {
			return resource.getName();
		}
		return null;
	}

	public URI getURI() {
		return uri;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resource == null) ? getOfsProject()==null ? 0 : getOfsProject().hashCode() : resource.hashCode());
		result = prime * result + scope;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OfsElement other = (OfsElement) obj;
		if (getOfsProject() == null) {
			if (other.getOfsProject() != null)
				return false;
		} else if (!getOfsProject().equals(other.getOfsProject()))
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (scope != other.scope)
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	public boolean hasScope(int scope) {
		return (getScope() & scope) > 0;
	}

	abstract public void accept(IModelVisitor visitor);

}
