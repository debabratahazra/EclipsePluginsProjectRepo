package com.odcgroup.process.validation.constraints;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.process.model.ComplexGateway;
import com.odcgroup.process.model.EndEvent;
import com.odcgroup.process.model.ExclusiveMerge;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Gateway;
import com.odcgroup.process.model.Pageflow;
import com.odcgroup.process.model.ParallelFork;
import com.odcgroup.process.model.ParallelMerge;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ServiceTask;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Task;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.process.model.impl.ProcessImpl;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author pkk
 *
 */
public class ProcessConstraints {
	
	/**
	 * @param pool
	 * @return
	 */
	public static boolean checkParticipantRefSpecified(Pool pool){
		if ((pool.getAssigneeByService() == null) && (pool.getAssignee() == null || pool.getAssignee().size() ==0)){
			return false;
		}
		return true;
	}
	
	/**
	 * @param process
	 * check for process display name
	 * @return
	 */
	public static boolean checkProcessDisplayName(Process process){
		if((process.getDisplayName()==null || process.getDisplayName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param process
	 * check for process name
	 * @return
	 */
	public static boolean checkProcessName(Process process){
		if((process.getName()==null || process.getName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param pool
	 * check for pool display name
	 * @return
	 */
	public static boolean checkPoolName(Pool pool){
		if((pool.getName()==null || pool.getName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param pool
	 * check for pool id
	 * @return
	 */
	public static boolean checkPoolID(Pool pool){
		if((pool.getID()==null || pool.getID().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param pool
	 * check for pool Service name
	 * @return
	 */
	public static boolean checkPoolServiceName(Pool pool){
		if(pool.getAssigneeByService() != null){
			if(pool.getAssigneeByService().getName() == null || pool.getAssigneeByService().getName().trim().length() == 0)
				return false;
		}
		return true;
	}
	/**
	 * @param UserTask
	 * check for UserTask name
	 * @return
	 */
	public static boolean checkUserTaskName(Task task){
		if((task.getName()==null || task.getName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param UserTask
	 * check for UserTask id
	 * @return
	 */
	public static boolean checkUserTaskID(Task task){
		if((task.getID()==null || task.getID().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param UserTask
	 * check for UserTask's pageflow or rule name 
	 * @return
	 */	public static boolean checkUserTaskRulePageflowName(UserTask userTask){
		 if(userTask.getRule() != null && (userTask.getRule().getName() != null) && (userTask.getRule().getName().trim().length() != 0))
				 return true;
		 if(userTask.getPageflow() != null && (userTask.getPageflow().getURI() != null) && (userTask.getPageflow().getURI().trim().length() != 0))
				 return true;
		return false;
	}
	/**
	 * @param ServiceTask
	 * check for ServiceTask Service name
	 * @return
	 */
	public static boolean checkServiceTaskServiceName(ServiceTask serviceTask){
		if(serviceTask.getService() == null || serviceTask.getService().getName()==null || serviceTask.getService().getName().trim().length() == 0)
			return false;
		return true;
	}
	/**
	 * @param Gateway
	 * check for Gateway name
	 * @return
	 */
	public static boolean checkGatewayName(Gateway gateway){
		if((gateway.getName()==null || gateway.getName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param Gateway
	 * check for Gateway id
	 * @return
	 */
	public static boolean checkGatewayID(Gateway gateway){
		if((gateway.getID()==null || gateway.getID().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param ComplexGateway
	 * check for complexGateway Rule / Script
	 * @return
	 */
	public static boolean checkComplexGatewayRuleScript(ComplexGateway complexGateway){
		if(complexGateway.getRule() != null && complexGateway.getRule().getName() != null && complexGateway.getRule().getName().trim().length() != 0)
			return true;
		if(complexGateway.getScript() != null && complexGateway.getScript().getLanguage().getName() != null && complexGateway.getScript().getLanguage().getName().trim().length() != 0)
			return true;
		if(complexGateway.getService() != null && complexGateway.getService().getName() != null && complexGateway.getService().getName().trim().length() != 0)
			return true;
		return false;
	}
	/**
	 * @param ComplexGateway
	 * check for complexGateway Script value
	 * @return
	 */
	public static boolean checkComplexGatewayScriptValue(ComplexGateway complexGateway){
		if(complexGateway.getScript() != null && complexGateway.getScript().getLanguage().getName().trim().length() != 0)
			if(!(complexGateway.getScript().getValue() != null && complexGateway.getScript().getValue().trim().length() != 0))
				return false;
		return true;
	}	
	/**
	 * @param StartEvent
	 * check for StartEvent display name
	 * @return
	 */
	public static boolean checkStartEventName(StartEvent event){
		if((event.getName()==null || event.getName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param StartEvent
	 * check for StartEvent id
	 * @return
	 */
	public static boolean checkStartEventID(StartEvent event){
		if((event.getID()==null || event.getID().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param Merge
	 * check for Merge out transition
	 * @return
	 */
	public static boolean checkMergeOnlyoneOutFlow(Gateway event){
		if ((event instanceof ParallelMerge || event instanceof ExclusiveMerge)){			
			ProcessImpl process = (ProcessImpl) event.eContainer().eContainer();
			List<?> flows = process.getTransitions();
			int temp = 0;		
			for(Object obj : flows){
				Flow flow =(Flow) obj;
				if(flow.getSource().equals(event))
					temp++;
			}
			if(temp == 1)
				return true;
			else
				return false;
		}		
		return true;
	}
	/**
	 * @param Start event
	 * check for Start event out transition
	 * @return
	 */
	public static boolean checkStartEventOnlyoneOutFlow(StartEvent event){
		if ( event != null){			
			ProcessImpl process = (ProcessImpl) event.eContainer().eContainer();
			List<?> flows = process.getTransitions();
			int temp = 0;		
			for(Object obj : flows){
				Flow flow =(Flow) obj;
				if(flow.getSource().equals(event))
					temp++;
			}
			if(temp == 1)
				return true;
			else
				return false;
		}		
		return true;
	}
	/**
	 * @param Gateway
	 * check for Gateway out transition
	 * @return
	 */
	public static boolean checkComplexGatewayManyOutFlow(ComplexGateway event){
		ProcessImpl process = (ProcessImpl) event.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		int temp1 = 0;
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getSource().equals(event))
				temp++;
			if(flow.getTarget().equals(event))
				temp1++;
		}
		if(temp == 0 || temp1 == 0)
			return false;
		else
			return true;		
	}
	/**
	 * @param Task
	 * check for task out transition
	 * @return
	 */
	public static boolean checkTaskManyOutFlow(Task task){
		ProcessImpl process = (ProcessImpl) task.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getSource().equals(task))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param Gateway
	 * check for Gateway out transition
	 * @return
	 */
	public static boolean checkParallelForkManyOutFlow(ParallelFork event){
		ProcessImpl process = (ProcessImpl) event.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getSource().equals(event))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param EndEvent
	 * check for EndEvent in transition
	 * @return
	 */
	public static boolean checkEndEventInFlow(EndEvent event){
		if (event == null)
			return true;
		ProcessImpl process = (ProcessImpl) event.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getTarget().equals(event))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param ExclusiveMerge
	 * check for ExclusiveMerge in transition
	 * @return
	 */
	public static boolean checkExclusiveMergeInFlow(ExclusiveMerge exclusiveMerge){
		if (exclusiveMerge == null)
			return true;
		ProcessImpl process = (ProcessImpl) exclusiveMerge.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getTarget().equals(exclusiveMerge))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param parallelMerge
	 * check for parallelMerge in transition
	 * @return
	 */
	public static boolean checkParallelMergeInFlow(ParallelMerge parallelMerge){
		if (parallelMerge == null)
			return true;
		ProcessImpl process = (ProcessImpl) parallelMerge.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getTarget().equals(parallelMerge))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param task
	 * check for task in transition
	 * @return
	 */
	public static boolean checkTaskInFlow(Task task){
		if (task == null)
			return true;
		ProcessImpl process = (ProcessImpl) task.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getTarget().equals(task))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param Gateway
	 * check for Gateway in transition
	 * @return
	 */
	public static boolean checkComplexGatewayOnlyOneInFlow(ComplexGateway gatway){
		ProcessImpl process = (ProcessImpl) gatway.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;	
		int temp1 = 0;
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getTarget().equals(gatway))
				temp++;
			if(flow.getSource().equals(gatway))
				temp1++;
		}
		if(temp > 1 && temp1 > 1)
			return false;
		else
			return true;		
	}
	/**
	 * @param Gateway
	 * check for Gateway in transition
	 * @return
	 */
	public static boolean checkParallelForkOnlyOneInFlow(ParallelFork gatway){
		ProcessImpl process = (ProcessImpl) gatway.eContainer().eContainer();
		List<?> flows = process.getTransitions();
		int temp = 0;		
		for(Object obj : flows){
			Flow flow =(Flow) obj;
			if(flow.getTarget().equals(gatway))
				temp++;
		}
		if(temp >= 1)
			return true;
		else
			return false;		
	}
	/**
	 * @param EndEvent
	 * check for EndEvent display name
	 * @return
	 */
	public static boolean checkEndEventName(EndEvent event){
		if((event.getName()==null || event.getName().trim().length() == 0))
			return false;
		return true;
	}
	/**
	 * @param EndEvent
	 * check for EndEvent id
	 * @return
	 */
	public static boolean checkEndEventID(EndEvent event){
		if((event.getID()==null || event.getID().trim().length() == 0))
			return false;
		return true;
	}
	
	/**
	 * @param userTask
	 * @return
	 */
	public static boolean checkReference(UserTask userTask){
		Pageflow pageflow = userTask.getPageflow();
		boolean retVal = true;
		if (pageflow != null){
			URI pageflowUri = URI.createURI(pageflow.getURI());
			if (pageflowUri != null 
					&& pageflowUri.scheme().equals(ModelURIConverter.SCHEME)
					&& pageflowUri.fileExtension().equals("pageflow")){
				
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(userTask.eResource());
				IOfsModelResource modelRef = null;
				try {
					modelRef = ofsProject.getOfsModelResource(pageflowUri);
					List<EObject> model = modelRef.getEMFModel();
					if (model.isEmpty()) {
						retVal = false;
					}
				} catch (ModelNotFoundException e) {
					return false;
				} catch (IOException e) {
					return false;
				} catch (InvalidMetamodelVersionException e) {
					return true;
				}
				
			}
		}
		return retVal;
	}
	
	/**
	 * @param process
	 * @return
	 */
	public static boolean isModelNameDifferentFromFile(Process process) {
		String modelName = process.getName();
		if (modelName == null || modelName.trim().length()== 0){
			return false;
		}
		if (process.eResource() != null) {
			String fileName = process.eResource().getURI().lastSegment();
			fileName = fileName.split("\\.")[0];
			if (modelName.equals(fileName)) {
				return true;
			}
		}
		return false;
	}
}
