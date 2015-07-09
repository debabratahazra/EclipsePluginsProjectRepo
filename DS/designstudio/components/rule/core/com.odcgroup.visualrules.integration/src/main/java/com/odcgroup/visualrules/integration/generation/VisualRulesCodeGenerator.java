package com.odcgroup.visualrules.integration.generation;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.cartridge.AbstractCodeGenerator;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IJavaIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.IntegrationException;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.JavaRuleModelEnvironmentConfiguration;
import de.visualrules.integration.model.JavaRulePackageEnvironmentConfiguration;
import de.visualrules.integration.model.RulePackage;

public class VisualRulesCodeGenerator extends AbstractCodeGenerator {

	public final static String CARTRIDGE_ID = "visualrules.generation.m2c";
	
	@Override
	public void doRun(final IProject project, final IContainer outputContainer,
			Collection<IOfsModelResource> modelResources, List<IStatus> nonOkStatuses) {
		IJavaIntegration javaIntegration = IntegrationCore.getJavaIntegration();
		Collection<IOfsModelResource> domainResources = 
			OfsResourceHelper.filter(modelResources, new String[]{"domain", "mml", "rule"});
		
		if (!domainResources.isEmpty()) {
			final IFile ruleFile = RulesIntegrationUtils.getRulesFile(project);
			
			// DS-2097: simply do nothing if rule model is missing
			if(ruleFile==null) return;
			try {
				configureTargetContainer((IFolder) outputContainer);
				configureRulesCodeGenerator(outputContainer, ruleFile);
				configureRootPackageConfiguration(project, ruleFile);
				javaIntegration.generateJavaCode(ruleFile, false, null);
			} catch (IntegrationException e) {
				String errorMsg = "Error while code generating in " + getClass().getName();
				newCoreException(e, nonOkStatuses, errorMsg);
			} catch (CoreException e) {
				String errorMsg = "Error while code generating in " + getClass().getName();
				newCoreException(e, nonOkStatuses, errorMsg);
			}
		}
	}
	
	/**
	 * check if the output container exists, if not create it
	 * 
	 * @param outputFolder
	 * @throws CoreException
	 */
	private void configureTargetContainer(IFolder outputFolder) throws CoreException {
		if(!outputFolder.exists()) {
			outputFolder.create(true, false, null);
		}
	}

	private IJavaIntegration configureRootPackageConfiguration(IProject project, IFile ruleFile) throws CoreException {
		IJavaIntegration javaIntegration = IntegrationCore.getJavaIntegration();

		JavaRulePackageEnvironmentConfiguration iPackageConf = null;
		try {
			IDataModelIntegration dataIntegration = IntegrationCore.getDataModelIntegration(project);
			RulePackage pkg = dataIntegration.getPackage(RulesIntegrationPlugin.getVRBasePath(project));
			EList list = pkg.getRulePackageEnvironmentConfigurations();
			iPackageConf = (JavaRulePackageEnvironmentConfiguration) list.get(0);
		} catch (Exception e) {
			iPackageConf = IntegrationFactory.eINSTANCE
			.createJavaRulePackageEnvironmentConfiguration();
		}
		boolean packageConfChanged = false;
		if(!"rules".equals(iPackageConf.getPackageAlias())) {
			packageConfChanged = true;
			iPackageConf.setPackageAlias("rules");
		}
		if(!iPackageConf.isUseReferences()) {
			packageConfChanged = true;
			iPackageConf.setUseReferences(true);
		}
		try {
			if(packageConfChanged) {
				if(ruleFile.isReadOnly()) {
					ruleFile.setReadOnly(false);
				}
				javaIntegration.configureCodeGenerator(ruleFile, iPackageConf);
				IDataModelIntegration dataIntegration = IntegrationCore.getDataModelIntegration(project);
				dataIntegration.save(RulesIntegrationPlugin.getVRBasePath(project));
			}
		} catch (IntegrationException e) {
			throw newCoreException(e);
		}
		return javaIntegration;
	}

	private void configureRulesCodeGenerator(IContainer outputContainer,
			IFile ruleFile) throws CoreException {
		IJavaIntegration javaIntegration = IntegrationCore.getJavaIntegration();

		JavaRuleModelEnvironmentConfiguration iModelConf = null;
		try {
			// get configuration
			iModelConf = javaIntegration.getRuleModelCodeGeneratorConfiguration(ruleFile);
		} catch (Exception e) {
			// in case of failure, create a new configuration
			iModelConf = IntegrationFactory.eINSTANCE
			.createJavaRuleModelEnvironmentConfiguration();
		}
		if(iModelConf==null) {
			iModelConf = IntegrationFactory.eINSTANCE.createJavaRuleModelEnvironmentConfiguration();
		}
		boolean modelConfChanged = false;
		if(iModelConf.isGenerateStatisticsCode()) {
			iModelConf.setGenerateStatisticsCode(false);
			modelConfChanged = true;
		}
		if(!"internal".equals(iModelConf.getInternalPackageName())) {
			iModelConf.setInternalPackageName("internal");
			modelConfChanged = true;
		}
		if(!"1.6".equals(iModelConf.getJavaVersion())) {
			iModelConf.setJavaVersion("1.6");
			modelConfChanged = true;
		}
		if(!"com.odcgroup".equals(iModelConf.getPackagePrefix())) {
			iModelConf.setPackagePrefix("com.odcgroup");
			modelConfChanged = true;
		}
		if(!outputContainer.getFullPath().toString().equals(iModelConf.getTargetDirectory())) {
			iModelConf.setTargetDirectory(outputContainer.getFullPath()
					.toString());
			modelConfChanged = true;
		}

		try {
			if(modelConfChanged) {
				if(ruleFile.isReadOnly()) {
					ruleFile.setReadOnly(false);
				}
				IDataModelIntegration dataIntegration = IntegrationCore.getDataModelIntegration(ruleFile.getProject());
				javaIntegration.configureCodeGenerator(ruleFile, iModelConf);
				dataIntegration.save(RulesIntegrationPlugin.getVRBasePath(ruleFile.getProject()));
			}
		} catch (IntegrationException e) {
			// ignore this silently and hope that it will work out when generating the code
		}
	}

	protected CoreException newCoreException(Throwable t) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR,
				RulesIntegrationPlugin.PLUGIN_ID, 0, t.toString(), t));
	}
}
