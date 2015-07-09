/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.odcgroup.process.model.Assignee;
import com.odcgroup.process.model.ComplexGateway;
import com.odcgroup.process.model.EndEvent;
import com.odcgroup.process.model.ExclusiveMerge;
import com.odcgroup.process.model.Flow;
import com.odcgroup.process.model.Pageflow;
import com.odcgroup.process.model.ParallelFork;
import com.odcgroup.process.model.ParallelMerge;
import com.odcgroup.process.model.Pool;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ProcessItem;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Property;
import com.odcgroup.process.model.Rule;
import com.odcgroup.process.model.Script;
import com.odcgroup.process.model.ScriptingLanguage;
import com.odcgroup.process.model.Service;
import com.odcgroup.process.model.ServiceTask;
import com.odcgroup.process.model.StartEvent;
import com.odcgroup.process.model.Translation;
import com.odcgroup.process.model.TranslationKind;
import com.odcgroup.process.model.UserTask;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProcessFactoryImpl extends EFactoryImpl implements ProcessFactory {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProcessFactory init() {
		try {
			ProcessFactory theProcessFactory = (ProcessFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.odcgroup.com/process"); 
			if (theProcessFactory != null) {
				return theProcessFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProcessFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ProcessPackage.PROCESS: return createProcess();
			case ProcessPackage.PROCESS_ITEM: return createProcessItem();
			case ProcessPackage.POOL: return createPool();
			case ProcessPackage.START_EVENT: return createStartEvent();
			case ProcessPackage.END_EVENT: return createEndEvent();
			case ProcessPackage.USER_TASK: return createUserTask();
			case ProcessPackage.SERVICE_TASK: return createServiceTask();
			case ProcessPackage.FLOW: return createFlow();
			case ProcessPackage.COMPLEX_GATEWAY: return createComplexGateway();
			case ProcessPackage.EXCLUSIVE_MERGE: return createExclusiveMerge();
			case ProcessPackage.PARALLEL_FORK: return createParallelFork();
			case ProcessPackage.PARALLEL_MERGE: return createParallelMerge();
			case ProcessPackage.ASSIGNEE: return createAssignee();
			case ProcessPackage.PAGEFLOW: return createPageflow();
			case ProcessPackage.SERVICE: return createService();
			case ProcessPackage.RULE: return createRule();
			case ProcessPackage.SCRIPT: return createScript();
			case ProcessPackage.PROPERTY: return createProperty();
			case ProcessPackage.TRANSLATION: return createTranslation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ProcessPackage.SCRIPTING_LANGUAGE:
				return createScriptingLanguageFromString(eDataType, initialValue);
			case ProcessPackage.TRANSLATION_KIND:
				return createTranslationKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ProcessPackage.SCRIPTING_LANGUAGE:
				return convertScriptingLanguageToString(eDataType, instanceValue);
			case ProcessPackage.TRANSLATION_KIND:
				return convertTranslationKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public com.odcgroup.process.model.Process createProcess() {
		ProcessImpl process = new ProcessImpl();
		return process;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessItem createProcessItem() {
		ProcessItemImpl processItem = new ProcessItemImpl();
		return processItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pool createPool() {
		PoolImpl pool = new PoolImpl();
		return pool;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartEvent createStartEvent() {
		StartEventImpl startEvent = new StartEventImpl();
		return startEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndEvent createEndEvent() {
		EndEventImpl endEvent = new EndEventImpl();
		return endEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserTask createUserTask() {
		UserTaskImpl userTask = new UserTaskImpl();
		return userTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceTask createServiceTask() {
		ServiceTaskImpl serviceTask = new ServiceTaskImpl();
		return serviceTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Flow createFlow() {
		FlowImpl flow = new FlowImpl();
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComplexGateway createComplexGateway() {
		ComplexGatewayImpl complexGateway = new ComplexGatewayImpl();
		return complexGateway;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExclusiveMerge createExclusiveMerge() {
		ExclusiveMergeImpl exclusiveMerge = new ExclusiveMergeImpl();
		return exclusiveMerge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParallelFork createParallelFork() {
		ParallelForkImpl parallelFork = new ParallelForkImpl();
		return parallelFork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParallelMerge createParallelMerge() {
		ParallelMergeImpl parallelMerge = new ParallelMergeImpl();
		return parallelMerge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assignee createAssignee() {
		AssigneeImpl assignee = new AssigneeImpl();
		return assignee;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pageflow createPageflow() {
		PageflowImpl pageflow = new PageflowImpl();
		return pageflow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Service createService() {
		ServiceImpl service = new ServiceImpl();
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule createRule() {
		RuleImpl rule = new RuleImpl();
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Script createScript() {
		ScriptImpl script = new ScriptImpl();
		return script;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Translation createTranslation() {
		TranslationImpl translation = new TranslationImpl();
		return translation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScriptingLanguage createScriptingLanguageFromString(EDataType eDataType, String initialValue) {
		ScriptingLanguage result = ScriptingLanguage.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScriptingLanguageToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TranslationKind createTranslationKindFromString(EDataType eDataType, String initialValue) {
		TranslationKind result = TranslationKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTranslationKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessPackage getProcessPackage() {
		return (ProcessPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static ProcessPackage getPackage() {
		return ProcessPackage.eINSTANCE;
	}

} //ProcessFactoryImpl
