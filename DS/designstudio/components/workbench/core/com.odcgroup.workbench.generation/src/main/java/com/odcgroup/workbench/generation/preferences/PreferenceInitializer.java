package com.odcgroup.workbench.generation.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(GenerationCore.PLUGIN_ID);
		node.put(CodeGenCategory.T24_XML.toString(), "[project]-gen/src/xml-t24i");
		node.put(CodeGenCategory.T24_DATA_FRAMEWORK.toString(), "[project]-gen/src/generated/dataframework");
		node.put(CodeGenCategory.AAA_D.toString(), "[project]-gen/src/aaa");
		node.put(CodeGenCategory.API_DYN_D.toString(), "[project]-gen/src/api/domain");
		node.put(CodeGenCategory.API_D.toString(), "[project]-gen/src/api/domain");
		node.put(CodeGenCategory.API_R.toString(), "[project]-gen/src/api/rules");
		node.put(CodeGenCategory.T24_IRIS_JAVA.toString(), "[project]-gen/src/generated/iris"); // note, this one is duplicated in T24ResourceModelsGenerator 
		node.put(CodeGenCategory.T24_IRIS_METADATA.toString(), "[project]-gen/src/generated/iris");
		node.put(CodeGenCategory.T24_RIM.toString(), "[project]/rim/gen"); // NOTE this one (only) gen. into [project] and NOT [project]-gen/ !!
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JBC_API.toString(), "[project]-gen/src/generated/t24service/JBCAPI");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JBC_IMPL.toString(), "[project]-gen/src/generated/t24service/JBCImpl");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JBC_INSERT.toString(), "[project]-gen/src/generated/t24service/JBCInsert");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JAVA_DATA.toString(), "[project]-gen/src/generated/data/java");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JAVA_WS.toString(), "[project]-gen/src/generated/ws/java");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JAVA_API.toString(), "[project]-gen/src/generated/t24service/javaAPI");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JAVA_PROXY.toString(), "[project]-gen/src/generated/proxyAdaptor/java");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_JAVA_EJB_API.toString(), "[project]-gen/src/generated/ejb");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_CPP_SERVICE.toString(), "[project]-gen/src/generated/t24service/cppAPI");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_CPP_DATA.toString(), "[project]-gen/src/generated/data/cpp");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_CPP_PROXY.toString(), "[project]-gen/src/generated/proxyAdaptor/cpp");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_DOTNET_API.toString(), "[project]-gen/src/generated/t24service/dotnetAPI");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_DOTNET_WS.toString(), "[project]-gen/src/generated/ws/dotnet");
		node.put(CodeGenCategory.T24_COMPONENT_FRAMEWORK_DOTNET_DATA.toString(), "[project]-gen/src/generated/data/dotnet");
		node.put(CodeGenCategory.T24_EDGE.toString(), "[project]-gen/src/generated/edge");
		node.put(CodeGenCategory.IMPL_D.toString(), "[project]-gen/src/impl/domain");
		node.put(CodeGenCategory.IMPL_R.toString(), "[project]-gen/src/impl/rules");
		node.put(CodeGenCategory.EJB_D.toString(), "[project]-gen/src/ejb/domain");
		node.put(CodeGenCategory.EJB_R.toString(), "[project]-gen/src/ejb/rules");
		node.put(CodeGenCategory.WUIBLOCK.toString(), "[project]-gen/src/wui_block");
		node.put(CodeGenCategory.IMPORT.toString(), "[project]-gen/src/import");
		
		node.put(CodeGenCategory.API_D_S.toString(), "[project]-gen/src/api/domain-specific");
		node.put(CodeGenCategory.API_R_S.toString(), "[project]-gen/src/api/rules-specific");
		node.put(CodeGenCategory.IMPL_D_S.toString(), "[project]-gen/src/impl/domain-specific");
		node.put(CodeGenCategory.IMPL_R_S.toString(), "[project]-gen/src/impl/rules-specific");
		node.put(CodeGenCategory.EJB_D_S.toString(), "[project]-gen/src/ejb/domain-specific");
		node.put(CodeGenCategory.EJB_R_S.toString(), "[project]-gen/src/ejb/rules-specific");
		node.put(CodeGenCategory.WUIBLOCK_S.toString(), "[project]-gen/src/wui_block-specific");
		node.put(CodeGenCategory.CONFIG_S.toString(), "[project]-gen/src/config-specific");
		
		for(CodeCartridge cartridge : GenerationCore.getRegisteredCodeCartridges()) {
			node.putBoolean(cartridge.getId(), cartridge.isEnabledByDefault());
		}
	}
}
