/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.custom.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorUtil;

/**
 * @generated
 */
public class WorkflowMigrateDiagramFileAction implements IObjectActionDelegate {

	/**
	 * @generated
	 */
	private IWorkbenchPart targetPart;
	
	private URI domainModelURI;

	/**
	 * @generated
	 */
	private IFile modelFile;

	/**
	 * @generated
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * @generated
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false
				|| selection.isEmpty()) {
			return;
		}
		modelFile = (IFile) ((IStructuredSelection) selection).getFirstElement();		
		action.setEnabled(true);
	}

	/**
	 * @generated
	 */
	public void run(IAction action) {
		IPath location = modelFile.getLocation();
		if (location != null) {
			File file = location.toFile();
			migrateWorkflow(file);
		}
		domainModelURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = null;
		try {
			resource = resourceSet.getResource(domainModelURI, true);
			ProcessDiagramEditorUtil.openDiagram(resource);
		} catch (WrappedException ex) {
			ProcessDiagramEditorPlugin.getInstance().logError(
					"Unable to load resource: " + domainModelURI, ex); //$NON-NLS-1$
		} catch (PartInitException ex) {
			ProcessDiagramEditorPlugin.getInstance().logError(
					"Unable to Open resource: " + domainModelURI, ex); //$NON-NLS-1$
		}
	}

	/**
	 * @param file
	 */
	private void migrateWorkflow(File file) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file.toString());
			NodeList pools = doc.getElementsByTagName("pools");
			NodeList nodes = doc.getFirstChild().getChildNodes();
			Node processNode = getProcessNode(nodes);
			List<Node> transitionNodes = new ArrayList<Node>();
			if (processNode != null){
				for (int ii = 0; ii < pools.getLength();ii++) {
					Node pool = pools.item(ii);
					for (int jj =0; jj < pool.getChildNodes().getLength();jj++){
						Node node = pool.getChildNodes().item(jj);
						if (node.getNodeName().equals("start") 
								|| node.getNodeName().equals("tasks") 
								|| node.getNodeName().equals("gateways") ){
							transitionNodes.addAll(getTransitionNodes(node));
						} 
					}
				}
				// append transitionNodes to processnode
				for (Node transitionNode : transitionNodes) {
					processNode.appendChild(transitionNode);
				}
				
				// remove transitionNodes from processItems
				for (int ii = 0; ii < pools.getLength();ii++) {
					Node pool = pools.item(ii);
					for (int jj =0; jj < pool.getChildNodes().getLength();jj++){
						Node node = pool.getChildNodes().item(jj);
						if (node.getNodeName().equals("start") 
								|| node.getNodeName().equals("tasks") 
								|| node.getNodeName().equals("gateways") ){
							removeTransitionNodes(node);
						} 
					}
				}
			}
			
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			//initialize StreamResult with File object to save to file
			file.createNewFile();
			StreamResult result = new StreamResult(file);
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private void removeTransitionNodes(Node node){
		NodeList childNodes = node.getChildNodes();
		for (int ii = 0; ii < childNodes.getLength();ii++) {
			Node child = childNodes.item(ii);
			if(child.getNodeName().equals("transitions")){
				node.removeChild(child);
			}			
		}
		
	}	
	
	private List<Node> getTransitionNodes(Node node){
		List<Node> transitionNodes = new ArrayList<Node>();
		NodeList childNodes = node.getChildNodes();
		for (int ii = 0; ii < childNodes.getLength();ii++) {
			Node child = childNodes.item(ii);
			if(child.getNodeName().equals("transitions")){
				transitionNodes.add(child);
			}			
		}
		return transitionNodes;
	}
	
	private Node getProcessNode(NodeList nodes) {
		for (int ii = 0; ii < nodes.getLength();ii++) {
			Node node = nodes.item(ii);
			if(node.getNodeName().equals("process:Process")){
				return node;
			}
		}
		return null;
	}
}
