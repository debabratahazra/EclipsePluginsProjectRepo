package com.odcgroup.t24.common.importer.internal;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportOperation;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.dsl.xml.NameURISwapper;
import com.odcgroup.workbench.dsl.xml.NameURISwapperImpl;

public abstract class T24ModelImportOperation<T extends IModelDetail, E extends IExternalObject> implements IImportOperation {

	private static final Logger logger = LoggerFactory.getLogger(T24ModelImportOperation.class);
	
	private IContainer container;
	private boolean inDebug = false;
	private Set<E> details;

	@Inject
	private ResourceSet resourceSet;	
	@Inject 
	private NameURISwapperImpl nameURISwapper;
	@Inject 
	private EIO eio;
	
	protected final NameURISwapper getNameURISwapper() {
		return nameURISwapper;
	}
	
	protected final EIO getEIO() {
		return this.eio;
	}	
	
	protected final ResourceSet getResourceSet() {
		return this.resourceSet;
	}
	
	protected final IContainer getContainer() {
		return this.container;
	}
	
	protected IFolder getFolder() {
		if (container instanceof IFolder) {
			return (IFolder)container;
		}
		return null;
	}

	public final void setContainer(IContainer container) {
		this.container = container;
	}

	public final void setInDebug(boolean inDebug) {
		this.inDebug = inDebug;
	}
	public final boolean getInDebug() {
		return this.inDebug;
	}
	
	public void setModels(Set<E> details) {
		this.details = details;
	}
	
	protected IImportingStep<T> getInitialStep(IImportModelReport report) {
		return new DoNothingStep<T>();
	}

	protected IImportingStep<T> getFinalStep(IImportModelReport report) {
		return new DoNothingStep<T>();
	}
	
	protected abstract List<IImportingStep<T>> getImportingSteps(IImportModelReport report);
	
	protected abstract T makeModelInfoFromDetail(E detail);
	
	protected abstract String getImportMessage();
	
	@Override
	public void importModels(IImportModelReport report, IProgressMonitor monitor) {
		
		IImportingStep<T> initialStep = getInitialStep(report);
		IImportingStep<T> finalStep = getFinalStep(report);
		List<IImportingStep<T>> importingSteps = getImportingSteps(report);
		final int nbSteps = importingSteps.size();

		// start the import of all selected models
		if (monitor != null) {
			final int nbModels = details.size();
			monitor.beginTask(getImportMessage(), 2+nbModels*nbSteps);
		}
		
		try {
			
			// perform the initial step
			initialStep.execute(null, monitor);
			if (monitor != null) {
				monitor.worked(1);
			}
			
			// perform all the intermediate steps for each model
			if ((monitor == null) || ! monitor.isCanceled()) {
				IMPORTING_LOOP:
				for (E detail : details) {
					
					T info = makeModelInfoFromDetail(detail);
					report.add(info);
	
					try {
					
						int work = nbSteps;
						for (IImportingStep<T> step : importingSteps) {
							if (monitor != null && monitor.isCanceled()) {
								break IMPORTING_LOOP;
							}
							if (step.execute(info, monitor)) {
								work--;
								if (monitor != null) {
									monitor.worked(1);
								}
							} else {
								if (work > 0 && monitor != null) {
									monitor.worked(work);
								}
								// do not execute remaining steps
								break;
							}
						}
						
					} finally {
						Resource res = info.getResource();
						if (res != null && res.isLoaded()) {
							res.unload();
						}
					}
				} // IMPORTING_LOOP
			}
			
			// perform the final step
			if ((monitor == null) || ! monitor.isCanceled()) {
				finalStep.execute(null, monitor);
				if (monitor != null) {
					monitor.worked(1);
				}
			}

		} catch (Exception ex) {
			report.error(ex);
			
		} finally {
			if (monitor != null) monitor.done();
			logger.info(report.getSummaryMessage());
			if (report.hasErrors()) {
				logger.error(report.getErrors());
			}
		}
		
	}
	
}
