package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractiontNode;
import com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.model.ComponentInteractionNodeContainer;

public class ComponentInteractionFileManager {

    /** instance of file reader to read the file **/
    private ComponentInteractionPropertiesFileReader fileReader = ComponentInteractionPropertiesFileReader.getInstance();
    /** instance of model builder to build the models **/
    private ComponentInteractionModelBuilder builder = ComponentInteractionModelBuilder.getInstance();
    /** instance of properties file **/
    private Properties properties;

    /**
     * load the properties file and make it ready for reading
     * 
     * @param pFile - properties file to be read
     */
    public void loadFile(File pFile) {
        properties = fileReader.getPropertyFileAsInstance(pFile);
    }

    /**
     * reading the file, retrieve the interaction information about selected and
     * convert it as model with the help of
     * {@link ComponentInteractionPropertiesFileReader} and stored it in
     * {@link ComponentInteractionNodeContainer}
     * 
     * @param selectedComponent - component selected from landscape view
     */
    public void convertAsModels(String selectedComponent) {
        ComponentInteractiontNode parentNode = null;
        HashMap<ComponentInteractiontNode, ArrayList<ComponentInteractiontNode>> childrens = null;
        if (properties != null) {
            for (Object key : properties.keySet()) {
                if (builder.isEligibleToCollectNewLine() && selectedComponent.equalsIgnoreCase(key.toString())) {
                    parentNode = builder.constructParentModel(key.toString());
                    childrens = builder.constructChildModels(fileReader.getValues(key.toString()));
                    ComponentInteractionNodeContainer.getInstance().saveInCollection(parentNode, childrens);
                    break;
                }
            }
        }
    }
}
