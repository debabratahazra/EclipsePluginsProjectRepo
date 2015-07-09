package com.odcgroup.workbench.core.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.internal.resources.InternalResourceUtils;
import com.odcgroup.workbench.core.repository.IModelVisitor;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class OfsModelFolder extends OfsElement implements IOfsModelFolder {
	
	//private static Logger logger = LoggerFactory.getLogger(OfsModelFolder.class);

	private List<IOfsModelPackage> packages;
	
	private ReentrantLock packagesLock = new ReentrantLock();
	
	public OfsModelFolder(IOfsProject project, IContainer container) {
		super(project, container);
	}

	public Collection<IOfsModelPackage> getPackages() {
		return getPackages(IOfsProject.SCOPE_PROJECT | IOfsProject.SCOPE_DEPENDENCY);
	}

	public Collection<IOfsModelPackage> getPackages(final int scope) {
		Collection<? extends IOfsElement> scopedPackages = new HashSet<IOfsElement>();
		if((scope & IOfsProject.SCOPE_PROJECT) > 0) {
			scopedPackages = new HashSet<IOfsElement>(getProjectPackages());
		} else {
			scopedPackages = new HashSet<IOfsElement>();
		}
		if((scope & IOfsProject.SCOPE_DEPENDENCY) > 0) {
			scopedPackages = InternalResourceUtils.merge(scopedPackages, getDependencyPackages());
		}
		return /*new HashSet<IOfsModelPackage>(*/(Collection<IOfsModelPackage>) scopedPackages;
	}

	protected Collection<IOfsModelPackage> getProjectPackages() {
		packagesLock.lock();
		try {
			if (packages == null) {
				packages = new ArrayList<IOfsModelPackage>();
				if(getResource() instanceof IFolder) {
					IFolder folder = (IFolder) getResource();
					if(folder.exists()) {
						try {
							for(IResource resource : folder.members()) {
								if(resource instanceof IFolder) {
									packages.add(new OfsModelPackage((IFolder) resource, this, IOfsProject.SCOPE_PROJECT));
								}
							}
						} catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return packages;
		} finally {
			packagesLock.unlock();
		}
	}

	protected Collection<IOfsModelPackage> getDependencyPackages() {
		Collection<IOfsModelPackage> modelPackages = new LinkedList<IOfsModelPackage>();
		Set<IOfsModelContainer> deps = depMgr.getDependencies(getOfsProject());
		
		Set<String> packageNames = new HashSet<String>();
		for(IOfsModelContainer dep : deps) {
			packageNames.addAll(depMgr.getSubDirectories(dep, new Path(getName())));
		}
		
		for(String packageName : packageNames) {
			modelPackages.add(
					new OfsModelPackage(ModelURIConverter.createModelURI(getURI().path()).appendSegment(packageName), 
							this, IOfsProject.SCOPE_DEPENDENCY));
		}
		return modelPackages;
	}
		
	public IOfsElement getOfsElement(URI uri) {
		if(uri.hasEmptyPath()) return null;	
		
		if(!uri.isPlatformResource()) return null;
		
		for(IOfsModelPackage pkg : getPackages()) {
			IOfsModelPackage element = pkg.getOfsPackage(uri);
			if(element!=null) return element;
		}
		return null;
	}

	public boolean acceptFileExtension(String ext) {
		// TODO: Workaround as long as the models for domain do not have the file ext "domain", but "mml".
		if("mml".equals(ext)) ext = "domain";

		return getName().equals(ext);
	}

	public void accept(IModelVisitor visitor) {
    	if(visitor.enter(this)) {
			for(IOfsModelPackage pkg : getPackages(visitor.getScope())) {
	    		pkg.accept(visitor);
	    	}
    	}
		visitor.leave(this);
	}

	@Override
	public final void addPackage(IFolder folder) {
		packagesLock.lock();
		try {
			if (packages == null) {
				packages = new ArrayList<IOfsModelPackage>();
			}
			IOfsModelPackage pkg = new OfsModelPackage(folder, this, IOfsProject.SCOPE_PROJECT);
			packages.add(pkg);
		} finally {
			packagesLock.unlock();
		}
	}
	
	private IOfsModelPackage lookupPackage(String name) {
		packagesLock.lock();
		try {
			IOfsModelPackage result = null;
			if (packages != null) {
				for (IOfsModelPackage pkg : packages) {
					if (pkg.getName().equals(name)) {
						result = pkg;
						break;
					}
				}
			}
			return result;
		} finally {
			packagesLock.unlock();
		}
	}

	@Override
	public final void removePackage(String name) {
		packagesLock.lock();
		try {
			IOfsModelPackage pkg = lookupPackage(name);
			if (pkg != null) {
				packages.remove(pkg);
				pkg.delete();
			}
		} finally {
			packagesLock.unlock();
		}
	}
	
	@Override
	public final IOfsModelPackage getOfsPackage(String name) {
		return lookupPackage(name);
	}
	
	@Override
	public void removeAll() {
		if (packages == null)
			return;
		packagesLock.lock();
		try {
			for (IOfsModelPackage pkg : packages) {
				pkg.delete();
			}
			packages.clear();
			packages = null;
		} finally {
			packagesLock.unlock();
		}
	}

}
