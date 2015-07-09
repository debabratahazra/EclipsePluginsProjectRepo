package com.odcgroup.workbench.dsl.validation;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.core.helper.Timer;
import com.odcgroup.workbench.core.resources.ModelsPartition;
import com.odcgroup.workbench.dsl.activator.WorkbenchDSLCore;

public class ModelsPartitionValidator {

		private final ModelsPartition partition;
		
		protected IModelsValidator getModelsValidator(String projectName, String modelType, int nbModels) {
			return new ModelsValidator(projectName, modelType, nbModels);
		}
		
		protected void validate(String projectName, IProgressMonitor monitor) {
			
			SubMonitor progress = SubMonitor.convert(monitor, 100);
			SubMonitor subMonitor = progress.newChild(100).setWorkRemaining(partition.getModelTypeCount());
			
			Map<String, Set<String>> models = partition.getModels(projectName);
			for (String modelType : models.keySet()) {
				Timer timer = new Timer().start();
				Set<String> modelURIs = models.get(modelType);
				int nbModels = modelURIs.size();
				if (monitor.isCanceled())
					return;
				try {
					getModelsValidator(projectName, modelType, nbModels).validate(models.get(modelType), subMonitor.newChild(1));
				} catch (Exception ex) {
					WorkbenchDSLCore.getDefault().logError("Error when validating modelType", ex);
				} finally {
					StringBuilder message = new StringBuilder();
					message.append("Validated ");
					message.append(nbModels);
					message.append(" ");
					message.append((nbModels > 1)
						? StringHelper.toPlural(modelType)
						: modelType.toLowerCase());
					message.append(" in ");
					message.append(timer.stopAndText());
					WorkbenchDSLCore.getDefault().logInfo(message.toString(), null);
				}
			}
		}
		
		public final void validate(IProgressMonitor monitor) {
			
			SubMonitor progress = SubMonitor.convert(monitor, 100);
			int nbProjects = partition.getProjectCount();
			SubMonitor subMonitor = progress.newChild(100).setWorkRemaining(nbProjects);

			Timer totalTimer = new Timer();
			totalTimer.start();

			for (String projectName : partition.getProjectNames()) {
				if (monitor.isCanceled()) break;
				validate(projectName, subMonitor.newChild(1));
			}
			
			StringBuilder message = new StringBuilder();
			message.append("The complete models validation took ");
			message.append(totalTimer.stopAndText());
			WorkbenchDSLCore.getDefault().logInfo(message.toString(), null);
		}
		
		public ModelsPartitionValidator(ModelsPartition partition) {
			this.partition = partition;
		}

}
