package com.odcgroup.workbench.core.resources;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelContainer;
import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.internal.resources.FileStorage;
import com.odcgroup.workbench.core.internal.resources.InternalResourceUtils;
import com.odcgroup.workbench.core.internal.resources.URLStorage;
import com.odcgroup.workbench.core.repository.IModelVisitor;
import com.odcgroup.workbench.core.repository.ModelResourceSet;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class OfsModelPackage extends OfsElement implements IOfsModelPackage {
	
	//private static Logger logger = LoggerFactory.getLogger(OfsModelPackage.class);

	private IOfsElement parent;
	
	private List<IOfsModelResource> models;
	
	private ReentrantLock modelsLock = new ReentrantLock();

	private List<IOfsModelPackage> subPackages;
	
	private ReentrantLock subPackagesLock = new ReentrantLock();

	public OfsModelPackage(IContainer container, IOfsElement parent, int scope) {
		super(parent.getOfsProject(), container);
		this.parent = parent;
		setScope(scope);
	}

	public OfsModelPackage(URI uri, IOfsElement parent, int scope) {
		super(parent.getOfsProject(), uri);
		this.parent = parent;
		setScope(scope);
	}

	public IOfsElement getParent() {
		return parent;
	}

	public Collection<IOfsModelResource> getModels() {
		return getModels(IOfsProject.SCOPE_PROJECT | IOfsProject.SCOPE_DEPENDENCY);
	}
	

	public Collection<IOfsModelResource> getModels(int scope) {
		Collection<? extends IOfsElement> scopedModels;
		if((scope & IOfsProject.SCOPE_PROJECT) > 0) {
			scopedModels = new HashSet<IOfsElement>(getProjectModels());
		} else {
			scopedModels = new HashSet<IOfsElement>();
		}
		if((scope & IOfsProject.SCOPE_DEPENDENCY) > 0) {
			Collection<IOfsModelResource> depModels = getDependencyModels();
			if (! depModels.isEmpty()) {
				scopedModels = InternalResourceUtils.merge(scopedModels, getDependencyModels());
			}
		}
		return (Collection<IOfsModelResource>) scopedModels;
	}

	protected Collection<IOfsModelResource> getProjectModels() {
		modelsLock.lock();
		try {
			if (models == null) {
				models = new ArrayList<IOfsModelResource>();
				if(getResource() instanceof IFolder) {
					IFolder folder = (IFolder) getResource();
					if(!folder.exists()) return models;
					try {
						IOfsModelFolder modelFolder = getModelFolder();
						ModelResourceSet mrs = getOfsProject().getModelResourceSet();
						for(IResource res : folder.members()) {
							if(res instanceof IFile) {
								IFile file = (IFile) res;
								if(modelFolder.acceptFileExtension(file.getFileExtension())) {
									models.add(mrs.createOfsModelResource((IFile) res, IOfsProject.SCOPE_PROJECT));
								}
							}
						}
					} catch (CoreException e) {
						// folder not accessible, do nothing
					}
				}
			}
			return models;
		} finally {
			modelsLock.unlock();
		}
	}

	protected Collection<IOfsModelResource> getDependencyModels() {
		Collection<IOfsModelResource> models = new LinkedList<IOfsModelResource>();
		Set<IOfsModelContainer> deps = depMgr.getDependencies(getOfsProject());
		
		for(IOfsModelContainer dep : deps) {
			IPath currentPackagePath = new Path(getModelFolder().getName()).append(getURI().path());
			Set<java.net.URI> uris = depMgr.getFileURIs(dep, currentPackagePath);
			for(java.net.URI uri : uris) {
				// first only process files, not jar references
				if(!uri.getScheme().equals("jar")) {
					File file = new File(uri);
					if(file.isFile()) {
						String ext = StringUtils.substringAfterLast(file.getName(), ".");
						if(getModelFolder().acceptFileExtension(ext)) {
							// check if this file belongs to the workspace
							IFile[] iFiles = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI());
							if(iFiles.length!=0) {
								// add the first file to the models
								models.add(getOfsProject().getModelResourceSet().createOfsModelResource(iFiles[0], IOfsProject.SCOPE_DEPENDENCY));
							} else {
								// file is not in the workspace
								models.add(
										getOfsProject().getModelResourceSet().createOfsModelResource(
										new FileStorage(file, getURI().appendSegment(file.getName())), IOfsProject.SCOPE_DEPENDENCY));
							}
						}
					}
				}
			}
			for(java.net.URI uri : uris) {
				// now process the jar references
				if(uri.getScheme().equals("jar")) {
					// URI is pointing into a JARs
					String modelName = StringUtils.substringAfterLast(uri.toString(), "/");
					String ext = StringUtils.substringAfterLast(modelName, ".");
					URI modelURI = getURI().appendSegment(modelName);
					ModelURIConverter converter = new ModelURIConverter(ofsProject);
					modelURI = converter.toPlatformURI(modelURI);
					if(getModelFolder().acceptFileExtension(ext) && !contains(models, modelURI)) {
						try {
							models.add(
								getOfsProject().getModelResourceSet().createOfsModelResource(
									new URLStorage(uri.toURL(), modelURI), IOfsProject.SCOPE_DEPENDENCY));
						} catch (MalformedURLException e) {
							OfsCore.getDefault().logError("Error tranforming model zip URI " + uri, e);
						}
					}						
				}
			}
		}
		return models;
	}
	
	private boolean contains(Collection<IOfsModelResource> models, URI modelURI) {
		for(IOfsModelResource modelResource : models) {
			if(modelResource.getURI().equals(modelURI)) return true;
		}
		return false;
	}

	public Collection<IOfsModelPackage> getSubPackages() {
		return getSubPackages(IOfsProject.SCOPE_PROJECT | IOfsProject.SCOPE_DEPENDENCY);
	}

	public Collection<IOfsModelPackage> getSubPackages(final int scope) {
		Collection<? extends IOfsElement> scopedPackages;
		if((scope & IOfsProject.SCOPE_PROJECT) > 0) {
			scopedPackages =  new HashSet<IOfsElement>(getProjectSubPackages());
		} else {
			scopedPackages = new HashSet<IOfsElement>();
		}
		if((scope & IOfsProject.SCOPE_DEPENDENCY) > 0) {
			scopedPackages = InternalResourceUtils.merge(scopedPackages, getDependencySubPackages());
		}
		return (Collection<IOfsModelPackage>) scopedPackages;
	}

	protected Collection<IOfsModelPackage> getProjectSubPackages() {
		subPackagesLock.lock();
		try {
			if (subPackages == null) {
				subPackages = new ArrayList<IOfsModelPackage>();
				if (getResource() instanceof IFolder) {
					IFolder folder = (IFolder) getResource();
					if(folder.exists()) {
						try {
							for(IResource resource : folder.members()) {
								if(resource instanceof IFolder) {
									subPackages.add(new OfsModelPackage((IFolder) resource, this, IOfsProject.SCOPE_PROJECT));
								}
							}
						} catch (CoreException e) {
							OfsCore.getDefault().logError("Error accessing project files", e);
						}
					}
				}
			}
			return subPackages;
		} finally {
			subPackagesLock.unlock();
		}
	}

	protected Collection<IOfsModelPackage> getDependencySubPackages() {
		Collection<IOfsModelPackage> modelPackages = new LinkedList<IOfsModelPackage>();
		Set<IOfsModelContainer> deps = depMgr.getDependencies(getOfsProject());
		
		Set<String> packageNames = new HashSet<String>();
		for(IOfsModelContainer dep : deps) {
			IPath currentPackagePath = new Path(getModelFolder().getName()).append(getURI().path());
			packageNames.addAll(depMgr.getSubDirectories(dep, currentPackagePath));
		}
		
		for(String packageName : packageNames) {
			modelPackages.add(
					new OfsModelPackage(ModelURIConverter.createModelURI(getURI().path()).appendSegment(packageName), 
							this, IOfsProject.SCOPE_DEPENDENCY));
		}
		return modelPackages;
	}

	public boolean isEmpty() {
		if(getModels().size()>0)      return false;
		if(getSubPackages().size()>0) return false;
		return true;
	}

	public boolean isEmpty(int scope) {
		return isEmpty();
	}

	public IOfsModelPackage getOfsPackage(URI uri) {
		if(uri.hasEmptyPath()) return null;		
		if(!uri.isPlatformResource()) return null;
		
		// check this package itself
		if(uri.equals(getURI())) return this;

		// check if we are on the wrong track
		if(uri.segmentCount()<getURI().segmentCount()) return null;
		
		// search the sub-packages with its name matching the next segment
		URI deresolvedURI = uri.deresolve(getURI().appendSegment(""));
		for(IOfsModelPackage pkg : getSubPackages()) {
			if(pkg.getName().equals(deresolvedURI.segment(0))) {
				return pkg.getOfsPackage(uri);
			}
		}
		
//		URI deresolvedURI = uri.deresolve(getURI().appendSegment(""));
//		if(deresolvedURI.segmentCount()==1) {
//			if(getResource()!=null) {
//				// create an ofsElement with the right resource set
//				IFolder parentFolder = (IFolder) getResource();
//				return new OfsModelPackage(parentFolder.getFolder(deresolvedURI.lastSegment()), this, 0);
//			}
//		}
		
		return null;
	}

	public IOfsModelFolder getModelFolder() {
		if(getParent() instanceof IOfsModelFolder) {
			return (IOfsModelFolder) getParent();
		} else {
			return ((IOfsModelPackage)getParent()).getModelFolder();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OfsModelPackage other = (OfsModelPackage) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	public void accept(IModelVisitor visitor) {
		if(visitor.enter(this)) {
			for(IOfsModelPackage pkg : getSubPackages(visitor.getScope())) {
				pkg.accept(visitor);
			}
			for(IOfsModelResource modelResource : getModels(visitor.getScope())) {
				modelResource.accept(visitor);
			}
		}
		visitor.leave(this);
	}

	@Override
	public void addPackage(IFolder folder) {
		subPackagesLock.lock();
		try {
			if (subPackages == null) {
				subPackages = new ArrayList<IOfsModelPackage>();
			}
			IOfsModelPackage pkg = new OfsModelPackage(folder, this, IOfsProject.SCOPE_PROJECT);
			subPackages.add(pkg);
		} finally {
			subPackagesLock.unlock();
		}
	}

	@Override
	public void add(IOfsModelResource value) {
		modelsLock.lock();
		try {
			if (models == null) {
				models = new ArrayList<IOfsModelResource>();
			}
			if (!models.contains(value)) {
				models.add(value);
			}
		} finally {
			modelsLock.unlock();
		}
	}

	@Override
	public void removeModel(URI uri) {
		modelsLock.lock();
		try {
			if (models != null) {
				IOfsModelResource model = null;
				for (int kx = models.size()-1; kx >=0; kx--) {
					model = models.get(kx);
					if (uri.equals(model.getURI())) {
						models.remove(kx);
						//logger.debug("Removed model "+uri);
						break;
					}
				}
			}
		} finally {
			modelsLock.unlock();
		}
	}
	
	private IOfsModelPackage lookupPackage(String name) {
		subPackagesLock.lock();
		try {
			if (subPackages != null) {
				for (IOfsModelPackage pkg : subPackages) {
					if (pkg.getName().equals(name)) {
						return pkg;
					}
				}
			}
			return null;
		} finally {
			subPackagesLock.unlock();
		}
	}

	@Override
	public void delete() {
		subPackagesLock.lock();
		modelsLock.lock();
		try {
			if (subPackages != null) {
				for (IOfsModelPackage subPackage : subPackages) {
					subPackage.delete();
				}
			}
			if (models != null) {
				models.clear();
			}
		} finally {
			modelsLock.unlock();
			subPackagesLock.unlock();
		}
	}
	
	@Override
	public void removePackage(String name) {
		IOfsModelPackage pkg = lookupPackage(name);
		if (pkg != null) {
			subPackagesLock.lock();
			try {
				subPackages.remove(pkg);
				pkg.delete();
				//logger.debug("Removed sub-package "+name);
			} finally {
				subPackagesLock.unlock();
			}
		}
	}

	@Override
	public IOfsModelPackage getOfsPackage(String name) {
		return lookupPackage(name);
	}
}
