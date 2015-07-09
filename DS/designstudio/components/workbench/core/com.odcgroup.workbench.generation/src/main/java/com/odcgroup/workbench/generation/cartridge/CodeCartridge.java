package com.odcgroup.workbench.generation.cartridge;

import com.odcgroup.workbench.generation.GenerationCore;


/** This is the java representation of a cartridge that is defined via the extension point 
 *  com.odcgroup.workbench.generation.m2cCartridge. 
 *  
 * @author Kai Kreuzer
 */
public class CodeCartridge {
	private String id;
	private String name;
	private CodeGenCategory category;
    private Class<?> codeGenClass;
    private boolean enabledByDefault;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public CodeGenCategory getCategory() {
		return category;
	}

	public void setCategory(CodeGenCategory category) {
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isCodeGeneratorDefined() {
		return this.codeGenClass != null;
	}
	
	public boolean isInstanceOf(Class<?> javaClass) {
		return isCodeGeneratorDefined() ? javaClass.isAssignableFrom(this.codeGenClass): false;
	}
	
	public void setCodeGeneratorClass(Class<?> javaClass) {
		this.codeGenClass = javaClass;
	}
	
	public Class<?> getCodeGeneratorClass() {
		return this.codeGenClass;
	}

    public CodeGenerator getCodeGen() {
        try {
        	return (CodeGenerator)this.codeGenClass.newInstance();
		} catch (Exception e) {
			GenerationCore.getDefault().logError(e.getMessage(), e);
			throw new RuntimeException(e);
		}
    }

	public boolean isEnabledByDefault() {
		return enabledByDefault;
	}

	public void setEnabledByDefault(boolean enabledByDefault) {
		this.enabledByDefault = enabledByDefault;
	}
}
