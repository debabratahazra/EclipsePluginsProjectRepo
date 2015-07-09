package com.odcgroup.pageflow.validation.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.DecisionAction;
import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionAction;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.pageflow.model.View;
import com.odcgroup.pageflow.model.ViewState;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author pkk
 * @author mka
 * 
 */
public class PageflowConstraints {

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkIncomingTransitions(ViewState view){
		Pageflow pageflow = (Pageflow)view.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj: transitions){
			Transition transition = (Transition)obj;
			if (transition.getToState().equals(view)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param state
	 * @return
	 */
	public static boolean checkforNameUniqueness(State state){
		Pageflow pageflow = (Pageflow)state.eContainer();
		List<?> states = pageflow.getStates();
		String displayName = state.getDisplayName();
		if (displayName == null || displayName.trim().length() == 0){
			return true;
		}
		for (Object obj : states){
			State child = (State)obj;
			if (!state.equals(child) && displayName.equals(child.getDisplayName())){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param transition
	 * @return
	 */
	public static boolean noTransitionFromEndState(Transition transition){
		if (transition.getFromState() instanceof EndState){
			return false;
		}
		return true;
	}

	/**
	 * @param transition
	 * @return
	 */
	public static boolean checkforTransitionNameUniqueness(Transition transition){
		Pageflow pageflow = (Pageflow)transition.eContainer();
		if (transition.getDisplayName() == null || transition.getDisplayName().trim().length() == 0) {
			return true;
		}
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition child = (Transition)obj;
			if (!transition.equals(child) && transition.getFromState().equals(child.getFromState())
					&& transition.getDisplayName().equals(child.getDisplayName())){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param transition
	 * @return
	 */
	public static boolean checkforTransitionIDUniqueness(Transition transition){
		Pageflow pageflow = (Pageflow)transition.eContainer();
		if (transition.getName() == null || transition.getName().trim().length() == 0){
			return true;
		}
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition child = (Transition)obj;
			if (!transition.equals(child)&& transition.getFromState().equals(child.getFromState())
					&& transition.getName().equals(child.getName())){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkIncomingTransitions(DecisionState decision){
		int temp = 0;
		Pageflow pageflow = (Pageflow)decision.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getToState().equals(decision)){
				temp++;
			}
		}
		if (temp == 0)
			return false;
		return true;
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkIncomingTransitions(SubPageflowState subpageflow){
		Pageflow pageflow = (Pageflow)subpageflow.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getToState().equals(subpageflow)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param subpageflow
	 * @return
	 */
	public static boolean checkDuplicateEndStatesForTransitionMappings(SubPageflowState subpageflow){
		List<?> mappings = subpageflow.getTransitionMappings();
		List<EndState> endStates = new ArrayList<EndState>();
		for (Object obj : mappings){
			TransitionMapping mapping = (TransitionMapping)obj;
			if (endStates.contains(mapping.getEndState())){
				return false;
			} 
			endStates.add(mapping.getEndState());
		}
		return true;
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkIncomingTransitions(EndState end){
		Pageflow pageflow = (Pageflow)end.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getToState().equals(end)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkOutgoingTransitions(ViewState view){
		Pageflow pageflow = (Pageflow)view.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getFromState().equals(view)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkOutgoingTransitions(DecisionState decision){
		int temp = 0;
		Pageflow pageflow = (Pageflow)decision.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getFromState().equals(decision)){
				temp++;
			}
		}
		if (temp>=2)
			return true;
		return false;
	}

	/**
	 * @param transition
	 * @return
	 */
	public static boolean isIntResultOutgoingTransition(Transition transition){
		if (transition.getFromState() instanceof DecisionState){
			try {
				getIntegerValue(transition.getName());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param data
	 * @return
	 */
	private static Integer getIntegerValue(String data) throws Exception {
		Integer intVal = 0;
		try {
			intVal = new Integer(data);
		} catch (NumberFormatException nfe){
			throw new Exception(nfe);
		}
		return intVal;
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkOutgoingTransitions(SubPageflowState subpageflow){
		Pageflow pageflow = (Pageflow)subpageflow.eContainer();
		List<?> transitions = pageflow.getTransitions();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getFromState().equals(subpageflow)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param subpageflow
	 * @return
	 */
	public static boolean checkDuplicateOutgoingTransitions(SubPageflowState subpageflow){
		Pageflow pageflow = (Pageflow)subpageflow.eContainer();
		List<?> transitions = pageflow.getTransitions();
		ArrayList<State> list = new ArrayList<State>();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getFromState().equals(subpageflow)){
				if (list.contains(transition.getToState())){
					return false;
				}
				list.add(transition.getToState());
			}
		}
		return true;
	}

	/**
	 * @param subpageflow
	 * @return
	 */
	public static boolean checkSelfReference(SubPageflowState subpageflow) {
		Pageflow pageflow = (Pageflow)subpageflow.eContainer();
		if (subpageflow.getSubPageflow()!= null &&subpageflow.getSubPageflow().equals(pageflow)) {
			return false;
		}
		return true;
	}

	/**
	 * @param subpageflow
	 * @return
	 */
	public static boolean checkCyclicReference(SubPageflowState subpageflow) {
		Pageflow pageflow = (Pageflow)subpageflow.eContainer();
		Pageflow ref = subpageflow.getSubPageflow();
		if (ref != null){
			List<?> states = ref.getStates();
			for(Object obj : states){
				State state = (State)obj;
				if (state instanceof SubPageflowState){
					SubPageflowState sub = (SubPageflowState)state;
					Pageflow subref = sub.getSubPageflow();
					if (subref != null){
						if (subref.getName().equals(pageflow.getName()) 
								&& subref.getId().equals(pageflow.getId())){
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param subpageflow
	 * @return
	 */
	public static boolean checkDuplicateOutgoingTransitions(DecisionState decision){
		Pageflow pageflow = (Pageflow)decision.eContainer();
		List<?> transitions = pageflow.getTransitions();
		ArrayList<State> list = new ArrayList<State>();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getFromState().equals(decision)){
				if (list.contains(transition.getToState())){
					return false;
				}
				list.add(transition.getToState());
			}
		}
		return true;
	}

	/**
	 * @param state
	 * @return
	 */
	public static boolean checkUniqueOutgoingTransitions(State state){
		Pageflow pageflow = (Pageflow)state.eContainer();
		List<?> transitions = pageflow.getTransitions();
		ArrayList<Transition> list = new ArrayList<Transition>();
		for (Object obj : transitions){
			Transition transition = (Transition)obj;
			if (transition.getFromState().equals(state)){
				list.add(transition);
			}
		}
		for(Object obj : list){
			Transition transition = (Transition)obj;
			if (isUniqueName(list, transition)){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param transitions
	 * @param transition
	 * @return
	 */
	public static boolean isUniqueName(List<?> transitions, Transition transition){
		for(Object obj : transitions){
			Transition child = (Transition)obj;
			if (child.getName().equals(transition.getName())
					||child.getDisplayName().equals(transition.getDisplayName())){
				return false;
			}
		}
		return true;
	}

	/**
	 * @param pageflow
	 * @param pattern
	 * @return
	 */
	public static boolean checkforFileNamePattern(Pageflow pageflow, String pattern){
		String filename = pageflow.getFileName();
		if (filename != null && filename.trim().length() > 0) {
			if (!filename.contains(pattern.subSequence(0, pattern.length()))){
				return false;
			} 
		}
		return true;
	}

	/**
	 * added for the issue OCS-11024 to check Model Name starts with upper case
	 * @param pageflow
	 * @return
	 */
	public static boolean checkForModelNameUpper(Pageflow pageflow){
		String name = pageflow.getName();
		if (name != null && name.trim().length() > 0) {
			int i = name.charAt(0);
			if (!(i>=65 && i<=90)){
				return false;
			}
		}
		return true;		
	}

	/**
	 * added for the issue OCS-11024 to check Action Name starts with upper case
	 * @param action
	 * @return
	 */
	public static boolean checkForActionNameUpper(Action action){
		String name = action.getName();
		if (name != null && name.trim().length() > 0) {
			int i = name.charAt(0);
			if (!(i>=65 && i<=90)){
				return false;
			}
		}
		return true;
	}

	/**
	 * added for the issue OCS-11024 
	 * To check State name should not be "Initial", "Abort, "Error"
	 * @param state
	 * @return
	 */
	public static boolean checkForStateNamePatternErr(State state){
		if (!(state instanceof InitState)){	
			String name = state.getDisplayName();
			if (name != null && name.trim().length() > 0) {
				if ( name.equals("Initial") || name.equals("Abort") || name.equals("Error")){				
					return false;	
				}
			}
		}
		return true;
	}

	/**
	 * added for the issue OCS-11024 
	 * To check Transition name should not be "Abort"
	 * @param Transition
	 * @return
	 */
	public static boolean checkForTransitionNamePatternErr(Transition state){
		if (!(state instanceof InitState)){		
			String name = state.getDisplayName();
			if (name != null && name.trim().length() > 0) {
				if ( name.trim().equals("Abort")){			
					return false;	
				}
			}
		}
		return true;		
	}

	/**
	 * added for the issue OCS-11024 
	 * To check Model target name 
	 * @param Pageflow
	 * @return
	 */
	public static boolean checkForModelTargetNameCharErr(Pageflow pageflow){
		String filename = pageflow.getFileName();
		if (filename != null) {
			String name = filename.trim();
			for (int i = 0; i < name.length(); i++) {
				int j = name.charAt(i);
				if (!( (j>=65 && j<=90) || (j>=97 && j<=122) || (j>=48 && j<=57) || (j==45)))	{			
					return false;	
				}	
			}
		}
		return true;
	}

	/**
	 * @param state
	 * @return
	 */
	public static boolean checkforIDUniqueness(State state){
		Pageflow pageflow = (Pageflow)state.eContainer();
		List<?> states = pageflow.getStates();
		if (!(state instanceof InitState)){	
			if (state.getName() == null || state.getName().trim().length() == 0){
				return true;
			}
			for (Object obj : states){
				State child = (State)obj;
				if (!state.equals(child) && state.getName().equals(child.getName())){
					return false;
				}
			}
		}
		return true;		
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean checkForEndStateMapping(SubPageflowState subpageflow){
		List<EndState> endStates = getUnmappedEndStates(subpageflow);
		if (endStates.size()== 0){
			return true;
		}
		return false;
	}

	/**
	 * @param subpageflow
	 * @return
	 */
	private static List<EndState> getUnmappedEndStates(SubPageflowState subpageflow) {
		Pageflow containingPageflow = subpageflow.getSubPageflow();
		List<EndState> endStates = new ArrayList<EndState>();
 		for (Object obj : containingPageflow.getStates()) {
			if (obj instanceof EndState) {
				endStates.add((EndState)obj);
			}
		}
		if (endStates.size() == 0) {
			return Collections.emptyList();
		}
		
		List<EndState> unmappedEndStates = new ArrayList<EndState>();
		List<?> mappings = subpageflow.getTransitionMappings();
		for (EndState endState : endStates) {
			boolean mapped = false;
			for (Object object : mappings) {
				EndState es = ( (TransitionMapping) object).getEndState();
				if (endState.getName().equals(es.getName())) {
					mapped = true;
					break;
				}
			}
			if (!mapped) {
				unmappedEndStates.add(endState);
			}
		}

		return unmappedEndStates;
	}

	/**
	 * @param view
	 * @return
	 */
	public static String getSubPageflowEndStateNames(SubPageflowState subpageflow){
		List<EndState> endStates = getUnmappedEndStates(subpageflow);
		StringBuffer buffer = new StringBuffer();		
		for(EndState s : endStates) {
			buffer.append(s.getName());
			buffer.append(",");
		}
		if(buffer.length() != 0)
			buffer.replace(buffer.length()-1, buffer.length(), " ");

		return buffer.toString();
	}

	/**
	 * @param view
	 * @return
	 */
	public static int getSubPageflowEnd(SubPageflowState subpageflow){
		List<EndState> endStates = getUnmappedEndStates(subpageflow);
		return endStates.size();
	}

	// added for issue OCS-23097

	/**
	 * @param pageflow
	 * to check pageflow name is empty or null
	 * @return
	 */
	public static boolean pageflowNameNotNull(Pageflow pageflow){		
		String name = pageflow.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param pageflow
	 * to check pageflow file name is empty or null
	 * @return
	 */
	public static boolean pageflowFileNameNotNull(Pageflow pageflow){		
		String filename = pageflow.getFileName();
		return filename != null && filename.trim().length() > 0;
	}

	/**
	 * @param pageflow
	 * @return
	 */
	public static boolean isModelNameDifferentFromFile(Pageflow pageflow) {
		String modelName = pageflow.getName();
		if (modelName == null || modelName.trim().length()== 0){
			return false;
		}
		if (pageflow.eResource() != null) {
			String fileName = pageflow.eResource().getURI().lastSegment();
			fileName = fileName.split("\\.")[0];
			if (modelName.equals(fileName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param EndState
	 * to check EndState name is empty or null
	 * @return
	 */
	public static boolean endStateIDNotNull(EndState endState){		
		String name = endState.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param EndState
	 * to check EndState ID is empty or null
	 * @return
	 */
	public static boolean endStateNameNotNull(EndState endState){		
		String displayName = endState.getDisplayName();
		return displayName != null && displayName.trim().length() > 0;
	}

	/**
	 * @param EndState
	 * to check EndState exit URI is empty or null
	 * @return
	 */
	public static boolean endStateExitURINotNull(EndState endState){		
		String exitUrl = endState.getExitUrl();
		return exitUrl != null && exitUrl.trim().length() > 0;
	}

	/**
	 * @param InitState
	 * to check InitState id is empty or null
	 * @return
	 */
	public static boolean initStateIDNotNull(InitState initState){		
		String name = initState.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param InitState
	 * to check InitState name is empty or null
	 * @return
	 */
	public static boolean initStateNameNotNull(InitState initState){		
		String displayName = initState.getDisplayName();
		return displayName != null && displayName.trim().length() > 0;
	}

	/**
	 * @param Action
	 * to check Action name is empty or null
	 * @return
	 */
	public static boolean decisionActionNameNotNull(DecisionState state){
		DecisionAction action = state.getAction();
		if (action != null) {
			String name = action.getName();
			return name != null && name.trim().length() > 0;
		}
		return false;
	}

	/**
	 * @param Action
	 * to check Action URI is empty or null
	 * @return
	 */
	public static boolean decisionActionURIEmpty(DecisionState state){	
		DecisionAction action = state.getAction();
		if (action != null) {
			String uri = action.getUri();
			return uri != null && uri.trim().length() > 0;
		}
		return false;
	}

	/**
	 * @param Action
	 * to check Action name is empty or null
	 * @return
	 */
	public static boolean transActionNameNotNull(Transition transition){
		List<?> list = transition.getActions();
		Action action = null;
		String name = null;
		for (Object obj : list){
			action = (Action)obj;
			name = action.getName();
			if (name == null || name.trim().length() == 0) {
				return false;
			}			
		}
		return true;
	}

	/**
	 * @param Action
	 * to check Action URI is empty or null
	 * @return
	 */
	public static boolean transActionURIEmpty(Transition transition){	
		List<?> list = transition.getActions();
		TransitionAction action = null;
		String uri = null;
		for (Object obj : list){
			action = (TransitionAction)obj;
			if (action.getProblemManagement().getValue() != ProblemManagement.VALIDATION) {
				uri = action.getUri();
				if (uri == null || uri.trim().length() == 0) {
					return false;
				}			
			}
		}
		return true;		
	}

	/**
	 * @param Property
	 * to check property name is empty or null
	 * @return
	 */
	public static boolean propertyNameNotNull(Property property){	
		String name = property.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param Transition
	 * to check Transition id is empty or null
	 * @return
	 */
	public static boolean transitionIDNotNull(Transition transition){		
		String name = transition.getName();
		return (name != null && name.trim().length() > 0);
	}

	/**
	 * @param Transition
	 * to check Transition name is empty or null
	 * @return
	 */
	public static boolean transitionNameNotNull(Transition transition){		
		String displayName = transition.getDisplayName();
		return displayName != null && displayName.trim().length() > 0;
	}

	/**
	 * @param ViewState
	 * to check ViewState id is empty or null
	 * @return
	 */
	public static boolean viewStateIDNotNull(ViewState viewState){		
		String name = viewState.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param ViewState
	 * to check ViewState name is empty or null
	 * @return
	 */
	public static boolean viewStateNameNotNull(ViewState viewState){		
		String displayName = viewState.getDisplayName();
		return (displayName != null && displayName.trim().length() > 0);
	}

	/**
	 * @param DecisionState
	 * to check DecisionState id is empty or null
	 * @return
	 */
	public static boolean decisionStateIDNotNull(DecisionState decisionState){		
		String name = decisionState.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param DecisionState
	 * to check DecisionState name is empty or null
	 * @return
	 */
	public static boolean decisionStateNameNotNull(DecisionState decisionState){		
		String displayName = decisionState.getDisplayName();
		return (displayName != null && displayName.trim().length() > 0);
	}

	/**
	 * @param DecisionState
	 * to check DecisionState action is empty or null
	 * @return
	 */
	public static boolean decisionStateActionNameNotNull(DecisionState decisionState){		
		return decisionState.getAction() != null;
	}

	/**
	 * @param SubPageflowState
	 * to check SubPageflowState id is empty or null
	 * @return
	 */
	public static boolean subPageflowStateIDNotNull(SubPageflowState subPageflowState){		
		String name = subPageflowState.getName();
		return name != null && name.trim().length() > 0;
	}

	/**
	 * @param SubPageflowState
	 * to check SubPageflowState name is empty or null
	 * @return
	 */
	public static boolean subPageflowStateNameNotNull(SubPageflowState subPageflowState){		
		String displayName = subPageflowState.getDisplayName();
		return displayName != null && displayName.trim().length() > 0;
	}

	/**
	 * @param SubPageflowState
	 * to check SubPageflowState subpageflow is empty or null
	 * @return
	 */
	public static boolean subPageflowStatePageflowNotNull(SubPageflowState subPageflowState){
		return subPageflowState.getSubPageflow() != null;
	}

	/**
	 * @param subPageflowState
	 * @return
	 */
	public static boolean validateSubPageflow(SubPageflowState subPageflowState) {
		Pageflow subPageflow = subPageflowState.getSubPageflow();
		if (subPageflow != null) {
			String uri = subPageflowURI(subPageflowState);
			try {
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(subPageflowState.eResource());
				ofsProject.getOfsModelResource(URI.createURI(uri));
			} catch (ModelNotFoundException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param subPageflowState
	 * @return
	 */
	public static String subPageflowURI(SubPageflowState subPageflowState) {
		Pageflow subPageflow = subPageflowState.getSubPageflow();
		if (subPageflow != null) {
			Resource resource = subPageflow.eResource();
			if (resource != null)
				return resource.getURI().toString();
			if (subPageflow.eIsProxy()) {
				InternalEObject eObj = (InternalEObject) subPageflow;
				return eObj.eProxyURI().trimFragment().toString();
			}
		}
		return "";
	}

	/**
	 * @param view
	 * @return
	 */
	public static boolean transitionMappingEndState(SubPageflowState subpageflow){
		SubPageflowState sub = (SubPageflowState)subpageflow;
		Pageflow pageflow  = sub.getSubPageflow();
		if(pageflow == null)
			return true;
		List<?> mappings = subpageflow.getTransitionMappings();	
		if(mappings != null && !mappings.isEmpty()){			
			for(Object obj: mappings){
				TransitionMapping s = (TransitionMapping)obj;
				if(! pageflow.getStates().contains(s.getEndState()))
					return false;
			}			
		}
		return true;
	}

	/**
	 * @param numer of end state and transition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkForEndStateAndTransition(SubPageflowState subpageflow){
		try{
			int i = 0;
			List mappings = subpageflow.getTransitionMappings();
			Pageflow containingPageflow = subpageflow.getSubPageflow(); 
			List<EndState> endStates = new ArrayList<EndState>();
			for (Object obj : containingPageflow.getStates()) {
				if (obj instanceof EndState) {
					endStates.add((EndState)obj);
				}
			}
			for (Object object : mappings) {
				TransitionMapping mapping = (TransitionMapping) object;
				EndState endState = mapping.getEndState();
				for (EndState es : endStates) {
					if (endState.getName().equals(es.getName())) {
						return true;
					}
				}
			}
			return false;			
		}catch (Exception e) {
			return true;
		}
	}

	/**
	 * checks the validity of the page/module reference
	 * 
	 * @param viewState
	 * @return
	 */
	public static boolean checkPageReference(ViewState viewState){
		//load the resource, before checking for reference		
		View view = viewState.getView();
		boolean retVal = true;
		if (view != null){
			String pageUrl = view.getUrl();
			if (pageUrl != null && pageUrl.trim().length()>0 ) {
				if( pageUrl.startsWith("resource:") 
						&& (pageUrl.endsWith(".page") || (pageUrl.endsWith(".module")))){
					// DS pages with resource schema
					IOfsProject ofsProject = OfsResourceHelper.getOfsProject(viewState.eResource());
					IOfsModelResource modelRef = null;
					try {
						modelRef = ofsProject.getOfsModelResource(URI.createURI(pageUrl));
					} catch (ModelNotFoundException e) {
						return false;
					}
					if (modelRef == null || (modelRef.getResource() != null && !modelRef.getResource().exists())) {
						retVal = false;
					}
				} 			
			}
		}
		return retVal;
	}
	
	/**
	 * checks the page reference is not empty.
	 * 
	 * @param viewState
	 * @return {@code true} if a reference is defined, otherwise {@code false}
	 */
	public static boolean checkPageReferenceNotEmpty(ViewState viewState) {
		return viewState.getView() != null;

	}

	/**
	 * DS-4907
	 * Check for cyclic subPageflow
	 * @param subPageflowState
	 * @return
	 */
	public static boolean checkCyclicSubPageflow(SubPageflowState subPageflowState) {
		Pageflow parentPageFlow = (Pageflow)subPageflowState.eContainer();
		Pageflow subPageflow = subPageflowState.getSubPageflow();
		if (subPageflow != null) {
			List<?> states = subPageflow.getStates();
			for (Object obj : states){
				State child = (State)obj;
				if (child instanceof SubPageflowState){
					Pageflow subPageflow1 = ((SubPageflowState)child).getSubPageflow();
					if(parentPageFlow == subPageflow1){
						return false;
					}else{
						return validateCyclicSubPageflow(((SubPageflowState)child),parentPageFlow);
					} 
						
				}
			}
		}
		return true;
	}
	
	/**
	 * DS-4907
	 * recursive block to check cycle
	 * @param subPageflowState
	 * @return
	 */
	public static boolean validateCyclicSubPageflow(SubPageflowState childPageflowState,Pageflow subPageflowState) {
		Pageflow subPageflow = childPageflowState.getSubPageflow();
		if (subPageflow != null) {
			List<?> states = subPageflow.getStates();
			for (Object obj : states){
				State child = (State)obj;
				if (child instanceof SubPageflowState){
					Pageflow subPageflow1 = ((SubPageflowState)child).getSubPageflow();
					if(subPageflowState == subPageflow1){
						return false;
					}else{
						validateCyclicSubPageflow(((SubPageflowState)child),subPageflowState);
					} 
						
				}
			}
		}
		return true;
	}
	
	/**
	 * DS-4907
	 * Check for unique state name in nested SubPageflow
	 * @param state
	 * @return
	 */
	public static boolean checkforNameUniquenessInNestedSubPageFlow(Pageflow pageFlow){
		Set<String> stateInfos = new TreeSet<String>();
		return validateNameUniquenessInNestedSubPageFlowStates(pageFlow,stateInfos);
	}
	
	/**
	 * DS-4907
	 * @param state
	 * @return
	 */
	public static boolean validateNameUniquenessInNestedSubPageFlowStates(Pageflow pageFlow,Set<String> stateInfos){
		List<?> states = pageFlow.getStates();
		for (Object obj : states){
			State child = (State)obj;
			if (!(child instanceof InitState || child instanceof EndState)  && !stateInfos.add(child.getDisplayName())){
				return false;
			}
			else if (child instanceof SubPageflowState){
				Pageflow subPageflow1 = ((SubPageflowState)child).getSubPageflow();
				return validateNameUniquenessInNestedSubPageFlowStates(subPageflow1,stateInfos);
			}
		}
		return true;
	}

	public static List<List<State>> getStronglyConnectedComponents(Pageflow pageflow) {
		PageflowStronglyConnectedComponentsDetector sccDetector = new PageflowStronglyConnectedComponentsDetector(pageflow);
		return sccDetector.getStronglyConnectedComponents();
	}
	
}
