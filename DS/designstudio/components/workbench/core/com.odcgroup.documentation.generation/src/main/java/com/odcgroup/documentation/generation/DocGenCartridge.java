package com.odcgroup.documentation.generation;

import com.odcgroup.documentstion.generation.cartridge.DocGenCategory;


/** This is the java representation of a cartridge that is defined via the extension point 
 *  com.odcgroup.documentation.generation.docGenCartridge.
 *  
 * @author Kai Kreuzer
 *
 */
public class DocGenCartridge {
	private String id;
	private String name;
    private DocGenerator docGen;
	private DocGenCategory category;
    private boolean enabledByDefault;
    private boolean terminal;
	
	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
    public void setCodeGenerator(DocGenerator codeGen) {
        this.docGen = codeGen;
    }

    public DocGenerator getDocGen() {
        return docGen;
    }

    public void setDocGen(DocGenerator docGen) {
        this.docGen = docGen;
    }

	public boolean isEnabledByDefault() {
		return enabledByDefault;
	}

	public void setEnabledByDefault(boolean enabledByDefault) {
		this.enabledByDefault = enabledByDefault;
	}

	public DocGenCategory getCategory() {
		return category;
	}

	public void setCategory(DocGenCategory category) {
		this.category = category;
	}
}
