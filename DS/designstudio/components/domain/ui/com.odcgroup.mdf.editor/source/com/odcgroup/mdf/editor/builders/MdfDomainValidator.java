package com.odcgroup.mdf.editor.builders;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.editor.model.MdfMarkerUtils;
import com.odcgroup.mdf.editor.model.MdfProject;
import com.odcgroup.mdf.editor.model.MdfProjectRepository;
import com.odcgroup.mdf.editor.model.MdfResourceVisitor;
import com.odcgroup.mdf.editor.model.ModelFileVisitor;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.mdf.validation.validator.ValidatorsFactory;


/**
 * The MDF incremental builder for MDF models
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public final class MdfDomainValidator extends IncrementalProjectBuilder {
	private static final Logger LOGGER =
		Logger.getLogger(MdfDomainValidator.class);

    /**
     * Default constructor.
     */
    public MdfDomainValidator() {
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      Map, IProgressMonitor)
     */
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
        throws CoreException {
        IProject currentProject = getProject();

        if ((currentProject == null) || !currentProject.isAccessible()) {
            return new IProject[0];
        }

        MdfProject project =
            MdfProjectRepository.getInstance().getProject(currentProject.getName());

        if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			validate(project, null, monitor);
        } else {
            IResourceDelta delta = getDelta(currentProject);
			validate(project, delta, monitor);
        }

        return new IProject[0];
    }

    private void validate(final MdfProject project, IResourceDelta delta,
        final IProgressMonitor monitor) throws CoreException {

		MdfResourceVisitor visitor = new MdfResourceVisitor(new ModelFileVisitor() {
			public boolean accept(IFile file) {
				doValidate(project, file, new SubProgressMonitor(monitor, 1));
				return true;
			}});

		try {
			String status = "Validating project " + project.getName();
	       	if (delta == null) {
				monitor.beginTask(status, DomainRepositoryUtil.getMdfDomainsSize());
	       		getProject().accept(visitor);
	       	} else {
				monitor.beginTask(status, delta.getAffectedChildren().length);
				delta.accept(visitor);
	       	}
		} finally {
			monitor.done();
		}
    }

	private void doValidate(MdfProject project, IFile file, IProgressMonitor monitor) {
        Resource resource = project.getResource(file, false);

        if (resource != null) {
            List domains = resource.getContents();
            monitor.beginTask("Validating", domains.size());

            try {
                Iterator it = domains.iterator();
                while (it.hasNext()) {
                    MdfDomain domain = (MdfDomain) it.next();
        			monitor.subTask("Validating domain " + domain.getName());

    				ModelVisitor validator = ValidatorsFactory.newInstance(file);
    				new ModelWalker(validator).visit(domain);
    				monitor.worked(1);
                }
            } catch (CoreException e) {
                LOGGER.error(e, e);
                MdfMarkerUtils.createTransientMarker(file, e);
            } finally {
                monitor.done();
            }
		}
	}
}
