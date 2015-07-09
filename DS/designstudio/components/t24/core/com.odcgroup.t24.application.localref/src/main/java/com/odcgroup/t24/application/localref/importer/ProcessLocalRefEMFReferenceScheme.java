package com.odcgroup.t24.application.localref.importer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;

import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.t24.application.internal.localref.LocalRef;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;

public class ProcessLocalRefEMFReferenceScheme implements IImportingStep<LocalRefInfo> {
	
	
	private IImportModelReport report;
	
	public ProcessLocalRefEMFReferenceScheme(IImportModelReport report){
		this.report = report;
	}

	@Override
	public boolean execute(LocalRefInfo model, IProgressMonitor monitor) {
		LocalRef localRef = (LocalRef) model.getModel();
		try {
			if (localRef != null) {
				// converting t24 scheme to name scheme.
				if (monitor != null) {
					monitor.subTask(getTransformingVersionRefMsg(model));
				}
				InternalEObject obj = (InternalEObject)localRef.getApplicationVET();
				if(obj != null) {
					MdfName name = MdfNameURIUtil.getMdfName(obj.eProxyURI());
					URI newURI = MdfNameURIUtil.createURI(name);
					obj.eSetProxyURI(newURI);
				}
			}
		} catch (Exception ex) {
			report.error(model, ex);
			return false;
		}
		return true;
	}

	private String getTransformingVersionRefMsg(LocalRefInfo model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Tranforming Reference LocalReferenceApplication Scheme"); //$NON-NLS-1$
		return b.toString();
		
	}

}

