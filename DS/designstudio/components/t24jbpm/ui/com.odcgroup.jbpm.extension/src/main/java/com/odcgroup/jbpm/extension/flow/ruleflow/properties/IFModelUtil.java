package com.odcgroup.jbpm.extension.flow.ruleflow.properties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.drools.eclipse.flow.ruleflow.core.HumanTaskNodeWrapper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author gasampong
 *
 */
public class IFModelUtil {	
	
	private Map<String, IFile> ifSchemaMap;
	private Map<String, IFile> ifCommonSchemaMap;
	private String relevantVersion;
	private String relevantMode;
	private ArrayList<String> relevantFlows;
	private HumanTaskNodeWrapper wrapper;	
	
	private static Logger LOGGER = LoggerFactory.getLogger(IFModelUtil.class.getName());
	private boolean successfullyLoadedIFSchemas;
	
	private final String SCHEMA_EXTENSION = "xsd";
	
	
	public IFModelUtil(HumanTaskNodeWrapper wrapper){
		ifSchemaMap = new LinkedHashMap<String, IFile>();
		ifCommonSchemaMap = new LinkedHashMap<String, IFile>();
		relevantFlows = new ArrayList<String>();
		this.wrapper = wrapper;
		setRelevantVersionAndMode();
		loadIFSchemasFromActiveProjects();
	}
	
	public Map<String, IFile> getIFSchemaMap(){
		return ifSchemaMap;
	}
	
	public Map<String, IFile> getIFCommonSchemaMap(){
		return ifCommonSchemaMap;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getSuccessfullyLoadedIfSchemas(){
		return successfullyLoadedIFSchemas;
	}
	
	private void setRelevantVersionAndMode(){
		String[] versionAndMode = JbpmDialogHelper.getVersionAndMode(wrapper);
		if(versionAndMode[0]!=null){
			relevantVersion = versionAndMode[0];
		}
		relevantMode = "";
		if(versionAndMode.length > 1 && versionAndMode[1]!=null){
			relevantMode = versionAndMode[1];
		}		
	}
	
	
	/**
	 * load IF Schemas from active projects in workspace
	 */
	private void loadIFSchemasFromActiveProjects(){		
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		
		successfullyLoadedIFSchemas = true;
		
		for (IProject project : projects) {
			if (project.isOpen()) {				
				try {
					if (project.hasNature("com.temenos.tws.consumer.plugin.natures.TWSConsumerNature")) {
						IFolder eventsFolder = project.getFolder("/events");
						setRelevantFlows(eventsFolder);
						IFolder schemaFolder= project.getFolder("/schemas");
						getSchemasFromFolder(schemaFolder,project);	
					}
				} catch (Exception e) {
					successfullyLoadedIFSchemas = false;
					LOGGER.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}		
	
	/**
	 * 
	 * @param folder
	 * @param project
	 * @throws Exception
	 */
	private void getSchemasFromFolder(IFolder folder,IProject project) throws Exception{
		IResource[] resources = folder.members();		
		for (IResource resource : resources) {			
			int resourceType = resource.getType();
			if(resourceType == IResource.FILE && SCHEMA_EXTENSION.equals(resource.getFileExtension())){
				String fileName = resource.getName();
				fileName = fileName.substring(0, fileName.lastIndexOf(".xsd"));
				if(fileName.contains("Common-")){
					IFile file = project.getFile(resource.getProjectRelativePath());
					ifCommonSchemaMap.put(fileName, file);										
				} else {
					IFile file = project.getFile(resource.getProjectRelativePath());
					if(fileName.contains("-")){
						String[] fileNameParts = fileName.split("-", 2);						
						if(relevantFlows.contains(fileNameParts[1])){
							ifSchemaMap.put(fileName, file);
						}
					}
				}
			} else if(resourceType == IResource.FOLDER){
				getSchemasFromFolder(((IFolder)resource),project);
			}	
		}
	}
	
	/**
	 * 
	 * @param folder
	 * @throws Exception
	 */
	private void setRelevantFlows(IFolder folder) throws Exception{		
		IResource[] resources = folder.members();		
		for (IResource resource : resources) {			
			int resourceType = resource.getType();
			if(resourceType == IResource.FILE){				
				findRelevantFlow((IFile)resource);
			} else if(resourceType == IResource.FOLDER){
				setRelevantFlows(((IFolder)resource));
			}	
		}
	}	
	
	/**
	 * 
	 * @param file
	 */
	private void findRelevantFlow(IFile file){
		boolean relevantFlow = false;
		try {
			Element rootNode = IFModelUtil.getDocumentRootNodeFromXML(file);
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++){
				Node childNode = childNodes.item(i);	
				if(childNode.getNodeName().equals("ExitPointType")){
					NodeList exitPointNodes = childNode.getChildNodes();
					String contract = ""; 
					String exitPoint = "";		
					for(int j = 0; j < exitPointNodes.getLength(); j++){
						Node exitPointNode = exitPointNodes.item(j);
						if(exitPointNode.getNodeName().equals("ContractName")){
							contract = exitPointNode.getTextContent();							
						} else if (exitPointNode.getNodeName().equals("ExitPoint")){
							exitPoint = exitPointNode.getTextContent();	
						}
					}
					if(isVersionRelevant(contract) && isModeRelevant(exitPoint)){
						relevantFlow = true;						
					}
				} else if (childNode.getNodeName().equals("FlowName") && relevantFlow){
					String flowName = childNode.getTextContent();
					relevantFlows.add(flowName);					
				}
			}			
		}catch(Exception e){
			LOGGER.error(e.getLocalizedMessage(), e);			
		}
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Element getDocumentRootNodeFromXML(IFile file) throws Exception{
		DocumentBuilderFactory drocumentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = drocumentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file.getContents());
		document.getDocumentElement().normalize();
		return document.getDocumentElement();
	}
	
	/**
	 * 
	 * @param contract
	 * @return
	 */
	private boolean isVersionRelevant(String contract){
		boolean relatedVersion = false;
		if(relevantVersion.equals(contract)){
			relatedVersion = true;
		} else {
			if(contract.contains(",")){
				if(contract.split(",")[0].equals(relatedVersion)){
					relatedVersion = true;					
				}
			}
		}
		return relatedVersion;
	}
	
	/**
	 * 
	 * @param exitPoint
	 * @return
	 */
	private boolean isModeRelevant(String exitPoint){
		boolean exactMode = false;
		if(exitPoint.equals("AUTH.ROUTINE")&&relevantMode.equals("A")){
			exactMode = true;
		} else if(exitPoint.equals("INPUT.ROUTINE")&&relevantMode.equals("I")){
			exactMode = true;			
		}
		return exactMode;
	}
	
}
