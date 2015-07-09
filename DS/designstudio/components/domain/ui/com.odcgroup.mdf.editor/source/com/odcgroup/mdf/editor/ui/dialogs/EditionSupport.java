package com.odcgroup.mdf.editor.ui.dialogs;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ecore.MdfReverseAssociationImpl;
import com.odcgroup.mdf.editor.MdfCore;
import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.model.ModelChangedEvent;
import com.odcgroup.mdf.editor.model.ModelFactory;
import com.odcgroup.mdf.editor.ui.editors.MdfModelEditorInput;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/*
 * DS-1349
 */
public class EditionSupport {
    private static final Logger LOGGER = Logger.getLogger(EditionSupport.class);

    protected IWorkbench workbench;
    protected Shell shell;
    private boolean save = true;

    public EditionSupport() {
        this(MdfPlugin.getDefault().getWorkbench());
    }

    public EditionSupport(Shell shell) {
        this.shell = shell;
        this.workbench = MdfPlugin.getDefault().getWorkbench();
    }

    public EditionSupport(IWorkbench workbench) {
        this.shell = workbench.getActiveWorkbenchWindow().getShell();
        this.workbench = workbench;
    }

    public EditionSupport(IWorkbenchWindow window) {
        this.shell = window.getShell();
        this.workbench = window.getWorkbench();
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public MdfAssociation createAssociation(MdfClass parentClass) {
        MdfAssociation newAssoc =
            ModelFactory.INSTANCE.createMdfAssociation(parentClass);

        WizardDialog dialog = createWizard(parentClass, newAssoc);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            parentClass.getProperties().add(newAssoc);
            return (MdfAssociation) create(newAssoc);
        }

        return null;
    }

    public MdfAttribute createAttribute(MdfClass parentClass) {
        MdfAttribute newAttr =
            ModelFactory.INSTANCE.createMdfAttribute(parentClass);

        WizardDialog dialog = createWizard(parentClass, newAttr);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            parentClass.getProperties().add(newAttr);
            return (MdfAttribute) create(newAttr);
        }

        return null;
    }

    public MdfClass createClass(MdfDomain parentDomain) {
        MdfClass newClass = ModelFactory.INSTANCE.createMdfClass(parentDomain);

        WizardDialog dialog = createWizard(parentDomain, newClass);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            ((MdfDomainImpl) parentDomain).getClasses().add(newClass);
            return (MdfClass) create(newClass);
        }

        return null;
    }

    public MdfDataset createDataset(MdfDomain parentDomain) {
        MdfDataset newDataset =
            ModelFactory.INSTANCE.createMdfDataset(parentDomain);

        WizardDialog dialog = createWizard(parentDomain, newDataset);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            parentDomain.getDatasets().add(newDataset);
            return (MdfDataset) create(newDataset);
        }

        return null;
    }

    public MdfDatasetMappedProperty createDatasetMappedProperty(MdfDataset parentDataset) {
        MdfDatasetMappedProperty newProp =
            ModelFactory.INSTANCE.createMdfDatasetMappedProperty(parentDataset);

        WizardDialog dialog = createWizard(parentDataset, newProp);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            parentDataset.getProperties().add(newProp);
            return (MdfDatasetMappedProperty) create(newProp);
        }

        return null;
    }

    public MdfDatasetDerivedProperty createDatasetDerivedProperty(MdfDataset parentDataset) {
        MdfDatasetDerivedProperty newProp =
            ModelFactory.INSTANCE.createMdfDatasetDerivedProperty(parentDataset);

        WizardDialog dialog = createWizard(parentDataset, newProp);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            parentDataset.getProperties().add(newProp);
            return (MdfDatasetDerivedProperty) create(newProp);
        }

        return null;
    }
    
    
    public MdfDomain createDomain(MdfProject currentProject) {
        MdfDomain domain = ModelFactory.INSTANCE.createMdfDomain();
        MdfElementWizard wizard = new MdfElementWizard(domain);
        wizard.init(workbench, new StructuredSelection(currentProject));

        // Create the wizard dialog
        WizardDialog dialog = new WizardDialog(shell, wizard);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            currentProject.createResource(domain);
            return (MdfDomain) create(domain);
        }

        return null;
    }

    public MdfEnumValue createEnumValue(MdfEnumeration parentEnumeration) {
        MdfEnumValue newValue =
            ModelFactory.INSTANCE.createMdfEnumValue(parentEnumeration);

        WizardDialog dialog = createWizard(parentEnumeration, newValue);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            parentEnumeration.getValues().add(newValue);
            return (MdfEnumValue) create(newValue);
        }

        return null;
    }

    public MdfEnumeration createEnumeration(MdfDomain parentDomain) {
        MdfEnumeration newEnum =
            ModelFactory.INSTANCE.createMdfEnumeration(parentDomain);

        WizardDialog dialog = createWizard(parentDomain, newEnum);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            ((MdfDomainImpl) parentDomain).getEnumerations().add(newEnum);
            return (MdfEnumeration) create(newEnum);
        }

        return null;
    }

    public MdfReverseAssociation createReverseAssociation(
        MdfAssociation parentAssociation) {
        MdfReverseAssociation newReverse =
            ModelFactory.INSTANCE.createMdfReverseAssociation(
                parentAssociation);

        WizardDialog dialog = createWizard(parentAssociation, newReverse);

        // Open the wizard dialog
        if (dialog.open() == Window.OK) {
            ((MdfAssociationImpl) parentAssociation).setReverse(newReverse);

            MdfClassImpl klass = (MdfClassImpl) newReverse.getParentClass();
            klass.getProperties().add(newReverse);
            return (MdfReverseAssociation) create(newReverse);
        }

        return null;
    }
    
    public void delete(EObject owner, List<MdfModelElement> items) {
		boolean confirm =
             MessageDialog.openConfirm(
                 shell, "Warning",
                 "Are you absolutely sure that you want to delete the selected deprecated models?\n\n" +
                 "It is recommended to leave them marked as deprecated instead, " +
                 "as you will otherwise lose backward compatibility. " +
                 "Click \'Cancel\' to leave them marked as deprecated.");
        if (confirm) {
            privateDelete(owner, items);
        }
    }

    public void delete(Object item) {
        boolean confirm =
            MessageDialog.openConfirm(
                shell, "Warning",
                "Are you absolutely sure that you want to delete this item?");

        if (confirm) {
        	 MdfUtility.checkSynchronization(item);
            privateDelete(item);
        }
    }

    public void openModelEditor(MdfModelElement model)
        throws PartInitException {
        IWorkbenchPage p =
            MdfPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow()
                     .getActivePage();

        if (p != null) {
            p.openEditor(
                new MdfModelEditorInput(model), MdfCore.MODEL_EDITOR_ID, true);
        }
    }

    protected MdfModelElement create(final MdfModelElement model) {
        if (!save) return model;

        try {
            MdfDomainImpl domain = (MdfDomainImpl) MdfUtility.getDomain(model);
            final Resource resource = domain.eResource();
            final IFile file = MdfUtility.findResourceFile(resource);
            IResource rule = file;

            while ((rule != null) && !rule.exists()) {
                rule = rule.getParent();
            }

            IWorkspaceRunnable runnable =
                new IWorkspaceRunnable() {
                    public void run(IProgressMonitor monitor)
                        throws CoreException {
                        try {
                            monitor.beginTask("Saving model", 2);
                            MdfUtility.doSave(
                                resource, file,
                                new SubProgressMonitor(monitor, 1));
                            monitor.worked(1);

                            MdfProjectRepository.fireModelChangedEvent(
                                this, model, ModelChangedEvent.ELEMENT_ADDED,
                                new SubProgressMonitor(monitor, 1));
                            monitor.worked(1);
                        } finally {
                            monitor.done();
                        }
                    }
                };

            MdfPlugin.getWorkspace().run(
                runnable, rule, IWorkspace.AVOID_UPDATE, null);
            return model;
        } catch (CoreException e) {
            LOGGER.error(e, e);
            MdfCore.openError(shell, e);
        }

        return null;
    }

    protected WizardDialog createWizard(
        MdfModelElement parent, MdfModelElement child) {
        MdfElementWizard wizard = new MdfElementWizard(child);
        wizard.init(workbench, new StructuredSelection(parent));
        return new WizardDialog(shell, wizard);
    }
    
    protected MdfModelElement update(final MdfModelElement model) {
        if (save) return model;

        try {
            MdfDomainImpl domain = (MdfDomainImpl) MdfUtility.getDomain(model);
            final Resource resource = domain.eResource();
            // final IFile file MdfUtility.findResourceFile(resource);
            final IFile file = OfsResourceHelper.getFile(resource, resource.getURI());
            IResource rule = file;

            while ((rule != null) && !rule.exists()) {
                rule = rule.getParent();
            }

            IWorkspaceRunnable runnable =
                new IWorkspaceRunnable() {
                    public void run(IProgressMonitor monitor)
                        throws CoreException {
                        try {
                            monitor.beginTask("Saving model", 2);
                            MdfUtility.doSave(
                                resource, file,
                                new SubProgressMonitor(monitor, 1));
                            monitor.worked(1);

                            MdfProjectRepository.fireModelChangedEvent(
                                this, model, ModelChangedEvent.ELEMENT_UPDATED,
                                new SubProgressMonitor(monitor, 1));
                            monitor.worked(1);
                        } finally {
                            monitor.done();
                        }
                    }
                };

            MdfPlugin.getWorkspace().run(
                runnable, rule, IWorkspace.AVOID_UPDATE, null);
            return model;
        } catch (CoreException e) {
            LOGGER.error(e, e);
            MdfCore.openError(shell, e);
        }

        return null;
    }

    private void doDelete(Object element, IProgressMonitor monitor)
        throws IOException, CoreException {
        monitor.beginTask("Deleting element", 3);

        try {
            // Synchronize the file system
            if (element instanceof Resource) {
                return;
            } else if (element instanceof MdfDomain) {
                MdfDomainImpl domain = (MdfDomainImpl) element;
                Resource resource = domain.eResource();
                resource.getContents().remove(domain);
                return;
            } else {
                return;
            }

        } finally {
            monitor.done();
        }
    }

    protected void privateDelete(EObject owner, List<MdfModelElement> items) {    	
    	if (owner instanceof MdfClassImpl) {
    		MdfClassImpl klass = (MdfClassImpl) owner;
    		klass.getProperties().removeAll(items);
    		handleCrossReferences(klass);
    	} else if (owner instanceof MdfDatasetImpl) {
    		((MdfDatasetImpl) owner).getProperties().removeAll(items);
    	} else if (owner instanceof MdfEnumerationImpl) {
    		((MdfEnumerationImpl) owner).getValues().removeAll(items);
    	} else if (owner instanceof MdfDomainImpl) {
    		for (MdfModelElement item : items) {
				privateDelete(item);
			}
    	}
    }

    protected void privateDelete(final Object item) {
        // Remove from the model
        if (item instanceof MdfReverseAssociation) {
            MdfReverseAssociationImpl rev = (MdfReverseAssociationImpl) item;
            MdfClassImpl klass = (MdfClassImpl) rev.getParentClass();
            klass.getProperties().remove(rev);
            ((MdfAssociationImpl) rev.getAssociation()).setReverse(null);
            handleCrossReferences(klass);
        } else if (item instanceof MdfProperty) {
            MdfProperty prop = (MdfProperty) item;
            MdfClassImpl klass = (MdfClassImpl) prop.getParentClass();
            klass.getProperties().remove(prop);
            handleCrossReferences(klass);
        } else if (item instanceof MdfEnumValue) {
            MdfEnumValue value = (MdfEnumValue) item;
            ((MdfEnumerationImpl) value.getParentEnumeration()).getValues()
             .remove(value);
        } else if (item instanceof MdfDatasetProperty) {
            MdfDatasetProperty prop = (MdfDatasetProperty) item;
            ((MdfDatasetImpl) prop.getParentDataset()).getProperties().remove(
                prop);
        } else if (item instanceof MdfDataset) {
            MdfDataset dataset = (MdfDataset) item;
            ((MdfDomainImpl) dataset.getParentDomain()).getDatasets()
             .remove(dataset);
        } else if (item instanceof MdfClass) {
            MdfClass klass = (MdfClass) item;
            ((MdfDomainImpl) klass.getParentDomain()).getClasses().remove(klass);
        } else if (item instanceof MdfEnumeration) {
            MdfEnumeration enumeration = (MdfEnumeration) item;
            ((MdfDomainImpl) enumeration.getParentDomain()).getEnumerations().remove(enumeration);
        } else if (item instanceof MdfBusinessType) {
        	MdfBusinessType businessType = (MdfBusinessType) item;
            ((MdfDomainImpl) businessType.getParentDomain()).getBusinessTypes().remove(businessType);
        }

        if (save) {
            try {
                Resource resource = null;
                IFile file = null;
                IResource rule = null;

                if (item instanceof Resource) {
                    resource = (Resource) item;
                    file = MdfUtility.findResourceFile(resource);
                    rule = file.getParent();
                } else {
                    MdfDomainImpl domain =
                        (MdfDomainImpl) MdfUtility.getDomain(item);
                    resource = domain.eResource();
                    file = MdfUtility.findResourceFile(resource);
                    rule = file;
                }

                IWorkspaceRunnable runnable =
                    new IWorkspaceRunnable() {
                        public void run(IProgressMonitor monitor)
                            throws CoreException {
                            try {
                                doDelete(item, monitor);
                            } catch (IOException e) {
                                MdfCore.throwCoreException(e);
                            } finally {
                                monitor.done();
                            }
                        }
                    };

                MdfPlugin.getWorkspace().run(
                    runnable, rule, IWorkspace.AVOID_UPDATE, null);
            } catch (CoreException e) {
                LOGGER.error(e, e);
                MdfCore.openError(shell, e);
            }
        }
    }

    public boolean selectionContainsElements(List<MdfModelElement> items) {
    	return items.size() > 0;
    }

    private void handleCrossReferences(MdfClassImpl mdfClass) {
    	Collection<Setting> refs = EcoreUtil.UsageCrossReferencer.find(mdfClass, mdfClass.eContainer());
    	for (Setting setting : refs) {
    		EObject eObject = setting.getEObject();
			if (eObject instanceof MdfDatasetImpl) {
				MdfDatasetImpl ds = (MdfDatasetImpl) eObject;
				ds.setBaseClass(mdfClass);
			} else if (eObject instanceof MdfClassImpl) {
				MdfClassImpl klass = (MdfClassImpl) eObject;
				klass.setBaseClass(mdfClass);
				handleCrossReferences(klass);
			} else if (eObject instanceof MdfAssociationImpl){
				MdfAssociationImpl assoc = (MdfAssociationImpl) eObject;
				assoc.setType(mdfClass);
			}
		}
    }
    
    public void updateAfterDrop(List<?> items) {
    	// donothing here ( to support DS-2112)
    }
    
    public void notifiyTranslationChangeOnElement(DialogPage source, 
    		MdfModelElement translationModified) {
    	// do nothing here
    }
}