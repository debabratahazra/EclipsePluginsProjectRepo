package com.odcgroup.mdf.editor.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.preferences.MdfPreferencePage;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfContainment;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;


/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini</a>
 */
public class MdfUtility {

    private static final Logger LOGGER = Logger.getLogger(MdfUtility.class);
    final public static MdfPrimitive DEFAULT_ATTR_TYPE = PrimitivesDomain.STRING;
    final public static MdfPrimitive DEFAULT_ENUM_TYPE = PrimitivesDomain.STRING;
    final public static MdfPrimitive DEFAULT_BUSSTYPE_TYPE = PrimitivesDomain.INTEGER;

    /**
     * Constructor for MdfUtility
     */
    private MdfUtility() {
        super();
    }

    public static MdfDomain getDomain(Object object) {
        while ((object != null) && !(object instanceof MdfDomain)) {
            object = getParent(object);
        }

        return (MdfDomain) object;
    }

    public static Image getImage(Object item) {

        if (item instanceof MdfDomain) {
            return MdfPlugin.getImage(MdfCore.ICON_DOMAIN);
        } else if (item instanceof MdfEnumeration) {
            return MdfPlugin.getImage(MdfCore.ICON_ENUM);
        } else if (item instanceof MdfClass) {
        	if (((MdfClass)item).getPrimaryKeys().size() == 0) {
        		return MdfPlugin.getImage(MdfCore.ICON_CLASS);
        	} else {
                return MdfPlugin.getImage(MdfCore.ICON_MAIN_CLASS);
        	}
        } else if (item instanceof MdfDataset) {
            return MdfPlugin.getImage(MdfCore.ICON_DATASET);
        } else if (item instanceof MdfBusinessType) {
            return MdfPlugin.getImage(MdfCore.ICON_BUSINESSTYPE);
        } else if (item instanceof MdfEnumValue) {
            return MdfPlugin.getImage(MdfCore.ICON_NVPAIR);
        } else if (item instanceof MdfAttribute) {
            return MdfPlugin.getImage(MdfCore.ICON_ATTR);
        } else if (item instanceof MdfAssociation) {
        	if (((MdfAssociation) item).getContainment() == MdfContainment.BY_VALUE) {
        		return MdfPlugin.getImage(MdfCore.ICON_VALUE);
        	} else {
        		return MdfPlugin.getImage(MdfCore.ICON_ASSOC);
        	}
        } else if (item instanceof MdfReverseAssociation) {
            return MdfPlugin.getImage(MdfCore.ICON_RASSOC);
        } else if (item instanceof MdfDatasetProperty) {
            return MdfPlugin.getImage(MdfCore.ICON_ATTR);
        } else if (item instanceof MdfDatasetProperty) {
            MdfEntity type = ((MdfDatasetProperty) item).getType();
            Image img = null;
            if (type instanceof MdfPrimitive) {
            	img = MdfPlugin.getImage(MdfCore.ICON_DATASETPROPERTY);
            } else {
            	img = MdfPlugin.getImage(MdfCore.ICON_ASSOC);
            }
            if (item instanceof MdfDatasetDerivedProperty) {
            	img = overlayImage(img, MdfPlugin.getImage(MdfCore.ICON_SUM));
            }
            return img;
        } else if (item instanceof MdfProject) {
            return MdfPlugin.getDefault().getWorkbench().getSharedImages().getImage(
                    SharedImages.IMG_OBJ_PROJECT);
        } else if (item instanceof Resource) {
            return MdfPlugin.getImage(MdfCore.ICON_DOMAIN_FILE);
        } else {
            return MdfPlugin.getDefault().getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJ_ELEMENT);
        }
    }

    public static IFolder getModelsFolder(IProject project) {
        IPreferenceStore store = MdfPlugin.getDefault().getPreferenceStore();
        String models = store.getString(MdfPreferencePage.P_DEFAULT_MODELS_FOLDER);
        return project.getFolder(models);
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * Returns the parent for this element, it can be a MdfModelElement or a
     * MdfProject
     */
    public static Object getParent(Object object) {
        if (object instanceof MdfProjectRepository) {
            return null;
        } else if (object instanceof MdfProject) {
            try {
                MdfProject project = (MdfProject) object;

                if (project.getProject().hasNature(MdfCore.NATURE_ID)) {
                    return MdfProjectRepository.getInstance();
                }
            } catch (CoreException e) {
                LOGGER.error(e, e);
            }

            return null;
        } else if (object instanceof ResourceSet) {
            return null;
        } else if (object instanceof Resource) {
            return ((Resource) object).getResourceSet();
        } else if (object instanceof MdfDomain) {
            return ((MdfDomainImpl) object).eResource();
        } else if (object instanceof MdfEntity) {
            return ((MdfEntity) object).getParentDomain();
        } else if (object instanceof MdfReverseAssociation) {
            return ((MdfReverseAssociation) object).getAssociation();
        } else if (object instanceof MdfProperty) {
            return ((MdfProperty) object).getParentClass();
        } else if (object instanceof MdfEnumValue) {
            return ((MdfEnumValue) object).getParentEnumeration();
        } else if (object instanceof MdfDatasetProperty) {
            return ((MdfDatasetProperty) object).getParentDataset();
        }

        return null;
    }

    public static MdfProject getProject(Object object) {
        while ((object != null) && !(object instanceof MdfProject)) {
            object = getParent(object);
        }

        return (MdfProject) object;
    }

    public static DomainRepository getRepository(Object object) {
        while ((object != null) && !(object instanceof ResourceSet)) {
            object = getParent(object);
        }

        return object == null ? null
                : DomainRepository.getInstance((ResourceSet) object);
    }

    public static IFolder getSourceFolder(IProject project) {
        IPath path = getSourcePath(project);
        return (path == null) ? null
                : MdfPlugin.getWorkspace().getRoot().getFolder(path);
    }

    public static IPath getSourcePath(IProject project) {
        IPath path = null;

        try {
            IJavaProject javaProject = JavaCore.create(project);
            IPackageFragmentRoot[] roots = javaProject.getAllPackageFragmentRoots();

            for (int i = 0; i < roots.length; i++) {
                IPackageFragmentRoot root = roots[i];

                if (root.getKind() == IPackageFragmentRoot.K_SOURCE) {
                    path = root.getPath();
                    break;
                }
            }
        } catch (JavaModelException e) {
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("Could not get source path for project "
                        + project.getName(), e);
        }

        return path;
    }

    public static void doSave(Resource resource, IProgressMonitor monitor)
            throws CoreException {
        doSave(resource, findResourceFile(resource), monitor);
    }

    public static void doSave(Resource resource, IFile file,
            IProgressMonitor monitor) throws CoreException {
        monitor.beginTask("Saving model", 2);

        try {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            resource.save(os, Collections.EMPTY_MAP);
            monitor.worked(1);

            if (file.exists()) {
                file.setContents(new ByteArrayInputStream(os.toByteArray()),
                        false, true, new SubProgressMonitor(monitor, 1));
                monitor.worked(1);
            } else {
                file.create(new ByteArrayInputStream(os.toByteArray()), false,
                        new SubProgressMonitor(monitor, 1));
                monitor.worked(1);
            }
        } catch (IOException e) {
            MdfCore.throwCoreException(e);
        } finally {
            monitor.done();
        }
    }

    public static IFile findResourceFile(MdfModelElement model)
            throws CoreException {
        MdfDomainImpl domain = (MdfDomainImpl) getDomain(model);
        return findResourceFile(domain.eResource());
    }

    public static IFile findResourceFile(Resource resource)
            throws CoreException {
        try {
            IWorkspace workspace = MdfPlugin.getWorkspace();
            java.net.URI locationURI = new java.net.URI(
                    resource.getURI().toString());
            IFile[] files = workspace.getRoot().findFilesForLocationURI(
                    locationURI);

            if (files.length > 0) {
                return files[0];
            }
        } catch (URISyntaxException e) {
            MdfCore.throwCoreException(e);
        }

        return null;
    }
    
    /**
     * @param base
     * @param overlay
     * @return image
     */
    private static Image overlayImage(final Image base, final Image overlay) {
    	CompositeImageDescriptor cid = new CompositeImageDescriptor() {

			@Override
			protected void drawCompositeImage(int width, int height) {
				drawImage(base.getImageData(), 0, 0);
				drawImage(overlay.getImageData(), 0, 0);				
			}

			@Override
			protected Point getSize() {
				return new Point(base.getImageData().width, base.getImageData().height);
			}
    		
    	};
    	return cid.createImage();
    }
    /**
     * show the Message dialog while changing the synchronize dataset. 
     * @return
     */
    public static boolean showDataSetSynchronizeMessage(){
    	Shell shell =PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    	boolean status =MessageDialog
				.openConfirm(
						shell,
						"Dataset Synchronization",
						"Dataset is Synchronized with base class, changes made to this dataset desynchronize with base class. do you want to the uncheck the dataset synchronize attribute.");
    	return status;
    }
    
    public static void checkSysnchronizedDataSetCommands(Command command){
    	if(command instanceof CreateChildCommand){
    		CreateChildCommand childCommand = (CreateChildCommand)command;
			if(childCommand.getLabel().equals("New Class Attribute")){
				Collection<?> results = childCommand.getResult();
				if (results != null && results.size() != 0) {
					MdfDatasetMappedProperty dataSetProperty = (MdfDatasetMappedProperty) results.iterator().next();
					if (dataSetProperty.getParentDataset().isSync()) {
						if (MdfUtility.showDataSetSynchronizeMessage()) {
							((MdfDatasetImpl) dataSetProperty.getParentDataset()).setSync(false);
						}
					}
				}
		}
	  } 
    }

	public static void checkSynchronization(Object property) {
		if(property instanceof MdfDatasetMappedProperty){
			MdfDataset dataSet =((MdfDatasetMappedProperty)property).getParentDataset();
			if(dataSet.isSync() && showDataSetSynchronizeMessage()){
				((MdfDatasetImpl)dataSet).setSync(false);
			}
		}
		
	}
	
	public static IContentAssistProvider getContentAssistProviderForDomainClasses(final ResourceSet rs) {
		IContentAssistProvider contentAssistProvider = new IContentAssistProvider() {
			public MdfModelElement[] getCandidates() {
				return getCandidateSuperClasses(rs);
			}

			public String getDefaultDomainName() {
				return "";
			}
		};
		return contentAssistProvider;
	}
	
	public static IContentAssistProvider getContentAssistProviderForDomainClasses(final Resource resource){
		return getContentAssistProviderForDomainClasses(resource.getResourceSet());
	}
	
	/**
     * @return
     */
	private static MdfModelElement[] getCandidateSuperClasses(ResourceSet rs) {
        List<MdfEntity> mdfClasswithPrimaryKey = DomainRepositoryUtil.getMdfClassesWithPK(rs);
        if(mdfClasswithPrimaryKey.isEmpty()){
        	return mdfClasswithPrimaryKey.toArray(new MdfClass[0]);
        }
        return mdfClasswithPrimaryKey.toArray(new MdfClass[1]);
    }
	
}
