package com.odcgroup.workbench.dsl.ui.validation;

import com.odcgroup.workbench.core.resources.ModelsPartition;
import com.odcgroup.workbench.dsl.validation.IModelsValidator;
import com.odcgroup.workbench.dsl.validation.ModelsPartitionValidator;

public class ModelsPartitionValidatorUI extends ModelsPartitionValidator {

	protected IModelsValidator getModelResourcesValidator(String projectName, String modelType, int nbModels) {
		return new ModelsValidatorUI(projectName, modelType, nbModels);
	}

	public ModelsPartitionValidatorUI(ModelsPartition partition) {
		super(partition);
	}

}
