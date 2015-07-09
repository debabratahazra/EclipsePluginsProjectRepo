package com.odcgroup.t24.version.importer.internal;

import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

public class ProcessT24Names implements IImportingStep<VersionInfo> {

	private IImportModelReport report;

	private String getTranformingFieldNamesMsg(IModelDetail model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Tranforming Field Names"); //$NON-NLS-1$
		return b.toString();
	}

	/**
	 * @param mvstring
	 * @param field
	 */
	private void setSV(String svstring, Field field) {
		int sv = -1;
		try {
			sv = Integer.parseInt(svstring);
		} catch (Exception e) {
		}
		if (sv != -1) {
			field.setSV(sv);
		}
	}

	/**
	 * @param mvstring
	 * @param field
	 */
	private void setMV(String mvstring, Field field) {
		int mv = -1;
		try {
			mv = Integer.parseInt(mvstring);
		} catch (Exception e) {
		}
		if (mv != -1) {
			field.setMV(mv);
		}
	}

	/**
	 * @param token
	 */
	private void setMultiValueIndex(String mvstring, Field field) {
		if (mvstring.contains(".")) {
			String[] tokens = mvstring.split("\\.");
			if (tokens.length == 2) {
				setMV(tokens[0], field);
				setSV(tokens[1], field);
			}
		} else {
			setMV(mvstring, field);
		}
	}

	/**
	 * transform the incoming field names with attribute names
	 * 
	 * @param version
	 * 
	 */
	private void transformIncomingFieldNames(Version version) {
		EList<Field> fieldList = version.getFields();
		MdfClass clazz = version.getForApplication();
		Map<String, String> t24NametoAttrNameMap = null;
		if (clazz != null) {
			t24NametoAttrNameMap = VersionUtils.getT24NameToAttrNameMap(clazz);
			for (Field field : fieldList) {
				String t24Name = field.getName();
				if (t24Name.contains("-")) {
					String[] tokens = t24Name.split("-");
					t24Name = tokens[0];
					if(t24Name.contains(".")){
						t24Name = t24Name.replace('.', '_');
						field.setName(t24Name);
					}
					if(!t24Name.contains(".")){		// DS-8375
						// Set the Field name where field name has single word
						field.setName(t24Name);
					}
					if (tokens.length == 2) {
						setMultiValueIndex(tokens[1], field);
					}
				}
				String attrName = t24NametoAttrNameMap.get(t24Name);
				if (attrName != null) {
					field.setName(attrName);
				}else{
					if(t24Name.contains(".")){
						t24Name = t24Name.replace('.', '_');
						field.setName(t24Name);
					}
				}
			}
		}
	}

	@Override
	public boolean execute(VersionInfo model, IProgressMonitor monitor) {
		final Resource resource = model.getResource();
		if (resource != null) {
			final Version version = (Version)model.getModel();// model.getResource().getContents().get(0);
			if (version != null) {
				try {
					// converting t24 name to attribute names.
					if (monitor != null) {
						monitor.subTask(getTranformingFieldNamesMsg(model));
					}
					transformIncomingFieldNames(version);
					IWorkspaceRunnable wrunnable = new IWorkspaceRunnable() {
						public void run(IProgressMonitor monitor) throws CoreException {
							try{
							      version.eResource().save(null);
							}catch(Exception e){   
								report.error(e);
							}
						}
					};
					ResourcesPlugin.getWorkspace().run(wrunnable,monitor);
				} catch (Exception ex) {
					report.error(model, ex);
					return false;
				}

			}
		}
		return true;
		
	}

	public ProcessT24Names(IImportModelReport report) {
		this.report = report;
	}

}
