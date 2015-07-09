package com.odcgroup.pageflow.editor.diagram.custom.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.Transition;

public class TransitionUtil {
	/**
	 * @param obj
	 * @return
	 */
	public static String getUniqueTransitionName(EObject obj,String argName){
		EObject parent = obj.eContainer();		
		if (parent != null && parent instanceof Pageflow){
			Pageflow pageflow = (Pageflow)parent;
			EList transitions = pageflow.getTransitions();
			List<String> transitionNames = new ArrayList<String>();
			for(int ii =0;ii<transitions.size();ii++){
				transitionNames.add(((Transition)transitions.get(ii)).getName());
			}
			return getUniqueName(transitionNames, transitions.size(),argName);
		}
		return null;
	}
	/*
	 * modified for issue OFSFMK-681
	 */
	private static String getUniqueName(List transitions, int i,String argName){
		String transitionName ;
		if(argName.equalsIgnoreCase("name")){
			transitionName = "transition"+i;
		}else {
			transitionName = "Transition("+i+")";
		}
		boolean exists = false;
		
		for(int ii =0;ii<transitions.size();ii++){
			String state = (String)transitions.get(ii);
			if (state !=null && state.equals(transitionName)) {
				exists = true;
				break;
			}
		}
		if (exists){
			return getUniqueName(transitions, i+1,argName);
		} else {
			return transitionName;
		}
	}
	
}
