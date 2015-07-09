package com.odcgroup.process.diagram.custom.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import com.odcgroup.process.model.EndEvent;
import com.odcgroup.process.model.Gateway;
import com.odcgroup.process.model.NamedElement;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Task;


public class CreateNamedElementCommand extends CreateElementCommand {
	

	/**
	 * The element type to be created.
	 */
	private final IElementType elementType;
	
	final String ID_TYPE = "id";
	private final String NAME_TYPE = "name";
	
	private final String EXCLUSIVE_MERGE_PREFIX="XOR-Join";
	private final String PARALLEL_MERGE_PREFIX="AND-Join";
	private final String PARALLEL_FORK_PREFIX="AND-Split";

	/**
	 * @param request
	 */
	public CreateNamedElementCommand(CreateElementRequest request) {
		super(request);
		this.elementType = request.getElementType();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand#doDefaultElementCreation()
	 */
	protected EObject doDefaultElementCreation() {
		EReference containment = getContainmentFeature();
		EClass eClass = elementType.getEClass();

		if (containment != null) {
			EObject element = getElementToEdit();
			EObject ret = EMFCoreUtil.create(element, containment, eClass);
			if (element != null && element instanceof Process){
				Process process = (Process)element;
				if (ret instanceof Pool){
					Pool pool = (Pool)ret;
					pool.setID(getUniquePoolId(process, eClass, ID_TYPE));
					pool.setName(getUniquePoolId(process, eClass, NAME_TYPE));
				}
			} else if (element != null && element instanceof Pool){
				Pool pool = (Pool)element;
				if (ret instanceof Task){
					Task sTask = (Task)ret;					
					sTask.setID(getUniqueTaskId((Process)pool.eContainer(), eClass, ID_TYPE));
					//localization of the displayname
					/*
					Localizable localizable = (Localizable)(ProcessLocalizableAdapterFactory.INSTANCE.adapt(sTask, Localizable.class));
					LocalizableElement lElement = localizable.getLocalizableElements().get(0);
					lElement.setMessage(getDefaultLocale(), getUniqueTaskId((Process)pool.eContainer(), eClass, NAME_TYPE));
					sTask.setName(lElement.getKey());
					*/		
					sTask.setName(getUniqueTaskId((Process)pool.eContainer(), eClass, NAME_TYPE));
				} else if (ret instanceof StartEvent){
					StartEvent stEvnt = (StartEvent)ret;
					stEvnt.setName(getUniqueStartId((Process)pool.eContainer(), eClass, NAME_TYPE));
					stEvnt.setID(getUniqueStartId((Process)pool.eContainer(), eClass, ID_TYPE));
				} else if (ret instanceof EndEvent){
					EndEvent endEvnt = (EndEvent)ret;
					endEvnt.setName(getUniqueEndID((Process)pool.eContainer(), eClass, NAME_TYPE));
					endEvnt.setID(getUniqueEndID((Process)pool.eContainer(), eClass, ID_TYPE));
				} else if (ret instanceof Gateway){
					Gateway cGate = (Gateway)ret;
					cGate.setName(getUniqueGatewayID((Process)pool.eContainer(), eClass, NAME_TYPE));
					cGate.setID(getUniqueGatewayID((Process)pool.eContainer(), eClass, ID_TYPE));
				} 
			}
			return ret;
		}
		return null;
	}	
	
	/**
	 * @return
	 */
	/*
	private Locale getDefaultLocale() {
		return TranslationServiceFactory.getDefaultLocale();
	}
	*/
	
	/**
	 * @param process
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String getUniqueGatewayID(Process process, EClass eClass, String type){
		List<?> pools = process.getPools();
		List<Gateway> tasks = new ArrayList<Gateway>();
		for (Object object : pools) {
			Pool pool = (Pool) object;
			List<?> gateways = pool.getGateways();
			for (Object obj : gateways) {
				Gateway gtway = (Gateway) obj;
				if (gtway.eClass().equals(eClass)){
					tasks.add(gtway);
				}
			}
		}
		return checkUniqueNamedElement(tasks, tasks.size(), eClass, type);
	}
	
	/**
	 * @param process
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String getUniqueEndID(Process process, EClass eClass, String type){
		List<?> pools = process.getPools();
		List<EndEvent> tasks = new ArrayList<EndEvent>();
		for (Object object : pools) {
			Pool pool = (Pool) object;
			tasks.addAll(pool.getEnd());
		}
		return checkUniqueNamedElement(tasks, tasks.size(), eClass, type);
	}
	/**
	 * @param process
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String getUniqueStartId(Process process, EClass eClass, String type){
		List<?> pools = process.getPools();
		List<StartEvent> tasks = new ArrayList<StartEvent>();
		for (Object object : pools) {
			Pool pool = (Pool) object;
			StartEvent start = pool.getStart();
			if (start != null)
				tasks.add(start);
		}
		return checkUniqueNamedElement(tasks, tasks.size(), eClass, type);
	}
	
	/**
	 * @param process
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String getUniqueTaskId(Process process, EClass eClass, String type){
		List<?> pools = process.getPools();
		List<Task> tasks = new ArrayList<Task>();
		for (Object object : pools) {
			Pool pool = (Pool) object;
			List<?> tskList = pool.getTasks();
			for (Object obj : tskList) {
				Task task = (Task)obj;
				if (task.eClass().equals(eClass)){
					tasks.add(task);
				}
			}
		}
		return checkUniqueNamedElement(tasks, tasks.size(), eClass, type);
	}
	
	/**
	 * @param process
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String getUniquePoolId(Process process, EClass eClass, String type){
		List<?> pools = process.getPools();		
		if (pools.size()>0)
			return checkUniquePool(pools, pools.size(), eClass, type);
		else
			return createPossibleName(eClass, 1, type);
	} 
	
	/**
	 * @param namedElements
	 * @param i
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String checkUniqueNamedElement(List<?> namedElements, int i, EClass eClass, String type){
		String name = createPossibleName(eClass, i, type);		
		boolean exists = false;
		for (Object object : namedElements) {
			NamedElement task = (NamedElement)object;
			String val = null;
			if (task.eClass().equals(eClass)){
				if (type.equalsIgnoreCase(ID_TYPE)){
					val = task.getID();
				}else if (type.equalsIgnoreCase(NAME_TYPE)){
					val = task.getName();
				}
			}
			if (val != null && val.equals(name)){
				exists = true;
				break;
			}
		}
		
		if (exists){
			return checkUniqueNamedElement(namedElements, i+1, eClass, type);
		} else {
			return name;
		}
		
	}
	
	/**
	 * @param pools
	 * @param i
	 * @param eClass
	 * @param type
	 * @return
	 */
	private String checkUniquePool(List<?> pools, int i, EClass eClass, String type){
		String name = createPossibleName(eClass, i, type);		
		boolean exists = false;
		for (Object object : pools) {
			Pool pool = (Pool)object;
			String val = null;
			if (type.equalsIgnoreCase(ID_TYPE)){
				val = pool.getID();
			}else if (type.equalsIgnoreCase(NAME_TYPE)){
				val = pool.getName();
			}
			if (val != null && val.equals(name)){
				exists = true;
				break;
			}
		}
		
		if (exists){
			return checkUniquePool(pools, i+1, eClass, type);
		} else {
			return name;
		}
	}
	
	/**
	 * @param eClass
	 * @param i
	 * @param type
	 * @return
	 */
	private String createPossibleName(EClass eClass, int i, String type){
		String name = null;
		if (type.equalsIgnoreCase(ID_TYPE)){
			if (eClass.getName().equals("ExclusiveMerge")){
				name = EXCLUSIVE_MERGE_PREFIX+i;
			} else if (eClass.getName().equals("ParallelMerge")){
				name = PARALLEL_MERGE_PREFIX+i;
			} else if (eClass.getName().equals("ParallelFork")){
				name = PARALLEL_FORK_PREFIX+i;
			} else {
				name = eClass.getName()+i;				
			}
		}else if (type.equalsIgnoreCase(NAME_TYPE)){
			if (eClass.getName().equals("ExclusiveMerge")){
				name = EXCLUSIVE_MERGE_PREFIX+" ("+i+")";
			} else if (eClass.getName().equals("ParallelMerge")){
				name = PARALLEL_MERGE_PREFIX+" ("+i+")";
			} else if (eClass.getName().equals("ParallelFork")){
				name = PARALLEL_FORK_PREFIX+" ("+i+")";
			} else {
				name = eClass.getName()+" ("+i+")";				
			}
		}
		return name;
	}	
	

}
