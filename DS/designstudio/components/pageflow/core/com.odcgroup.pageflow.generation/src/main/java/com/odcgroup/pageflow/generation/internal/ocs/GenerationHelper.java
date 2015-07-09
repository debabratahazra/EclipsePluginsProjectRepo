package com.odcgroup.pageflow.generation.internal.ocs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;

import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.workbench.core.helper.StringHelper;

public class GenerationHelper {
	
	private static final String RESOURCE_URL_PREFIX = "resource:///";
	private static final String ACTION_URL_PREFIX = "class:";
	private static final String PAGE_EXTN = ".page";
	private static final String MODULE_EXTN = ".module";
	
	/**
	 * as per OCS-25266
	 * assuming the root directory as WUI-Profile, knock it off from generated URL
	 * 
	 * @param resourceURL
	 * @return
	 */
	public static String resolveDesignStudioPageURIforGeneration(String resourceURL){
		if (resourceURL.startsWith(RESOURCE_URL_PREFIX) && resourceURL.endsWith(PAGE_EXTN)){
			resourceURL = resourceURL.substring(RESOURCE_URL_PREFIX.length(), resourceURL.length()-PAGE_EXTN.length());
			int index = resourceURL.indexOf("/");
			return resourceURL.substring(index, resourceURL.length());
		}
		return resourceURL;
	}
	
	/**
	 * @param resourceURL
	 * @return
	 */
	public static String resolveDesignStudioModuleURIforGeneration(String resourceURL){
		if (resourceURL.startsWith(RESOURCE_URL_PREFIX) && resourceURL.endsWith(MODULE_EXTN)){
			resourceURL = resourceURL.substring(RESOURCE_URL_PREFIX.length(), resourceURL.length()-MODULE_EXTN.length());
			int index = resourceURL.indexOf("/");
			resourceURL = resourceURL.substring(index, resourceURL.length());
			return "/page/common/OneModule?module="+resourceURL;
		}
		return resourceURL;		
	}
	
	/**
	 * @return
	 */
	public static Date getCurrentDate(){
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * @return
	 */
	public static String getDSBuildID(){
		String designStudioFeatureID = "com.odcgroup.designstudio";
		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		if (providers != null) {
		    for (int i = 0; i < providers.length; ++i) {
		        IBundleGroup[] bundleGroups = providers[i].getBundleGroups();
		        for (IBundleGroup bg: bundleGroups) {
		        	if (bg.getIdentifier().equals(designStudioFeatureID)) {
		                return bg.getName()+" "+bg.getVersion();
		            }
		        }
		    }
		}
		return "";
	}
	
	/**
	 * @param actionUrl
	 * @return
	 */
	public static String parseClassURL(String actionUrl) {
		if (actionUrl == null || actionUrl.trim().equals("")) {
			return "";
		}
		return actionUrl.startsWith(ACTION_URL_PREFIX) ? actionUrl.substring(ACTION_URL_PREFIX.length()) 
				: StringHelper.toXMLString(actionUrl);
	}
	
	/**
	 * @param viewUrl
	 * @return
	 */
	public static String parseViewURL(String viewUrl, Boolean optimisePageURL) {
		if (viewUrl == null || viewUrl.trim().equals("")) {
			return "";
		}
		if (viewUrl.startsWith(ACTION_URL_PREFIX)) {
			return "url-redirector-class-name=\"" + viewUrl.substring(ACTION_URL_PREFIX.length()) + "\"";
		} else if (viewUrl.startsWith(RESOURCE_URL_PREFIX)) {
			if (viewUrl.endsWith(PAGE_EXTN)) {
				String uri = resolveDesignStudioPageURIforGeneration(viewUrl);
				if (optimisePageURL && uri.startsWith("/activity")) {
					// DS-5369
					uri = StringUtils.replaceOnce(uri, "/activity", "/page");
				}
				return "url=\"" + uri +"\"";
			} else if (viewUrl.endsWith(MODULE_EXTN)) {
				return "url=\"" + resolveDesignStudioModuleURIforGeneration(viewUrl)+"\"";
			}			
		}
		return "url=\"" + StringHelper.toXMLString(viewUrl) + "\"";
	}
	
	/**
	 * @param pageflows
	 * @return
	 */
	public static String getPageflowNamePrefix(List<Pageflow> pageflows) {
		StringBuilder sb = new StringBuilder();
		for (Pageflow pageflow : pageflows) {
			if (pageflows.indexOf(pageflow) != 0)
			sb.append(pageflow.getName()+".");
		}		
		return sb.toString();
	}
	
	/**
	 * @param states
	 * @return
	 */
	public static String getSubPageflowNamePrefix(List<SubPageflowState> states) {
		StringBuilder sb = new StringBuilder();
		for (SubPageflowState st : states) {
			sb.append(st.getName()+".");
		}		
		return sb.toString();
	}
	
	/**
	 * @param states
	 * @param state
	 * @return
	 */
	public static String getSubPageflowNamePrefixByPf(List<SubPageflowState> states, Pageflow pf) {
		for (SubPageflowState st : states) {
			if(st.getSubPageflow().equals(pf)){
				return getSubPageflowNamePrefix(states,st);
			}
		}		
		return "";
	}
	
	/**
	 * @param states
	 * @param state
	 * @return
	 */
	public static String getSubPageflowNamePrefix(List<SubPageflowState> states, SubPageflowState state) {
		int index = states.indexOf(state);
		if (index != -1) {
			return getSubPageflowNamePrefix(states.subList(0, index+1));
		}
		return "";
	}
	
	/**
	 * @param pageflows
	 * @return
	 */
	public static String getPageflowNamePrefix(List<Pageflow> pageflows, Pageflow current) {
		if (pageflows.get(0).equals(current)) {
			return "";
		}
		List<Pageflow> dupe = new ArrayList<Pageflow>();
		dupe.addAll(pageflows);
		dupe.remove(0);
		int index = dupe.lastIndexOf(current);
		StringBuilder sb = new StringBuilder();
		if (index != -1) {
			List<Pageflow> subList = dupe.subList(0, index);
			for (Pageflow pageflow : subList) {
				sb.append(pageflow.getName()+".");
			}
		} else {
			for (Pageflow pageflow : dupe) {
				sb.append(pageflow.getName()+".");
			}			
		}
		return sb.toString();
	}
	
	/**
	 * @param hierarchy
	 * @param current
	 * @return
	 */
	public static Pageflow getParentPageflow(List<Pageflow> hierarchy, Pageflow current) {
		int index = hierarchy.lastIndexOf(current);
		if (index != -1) {
			if(index==0)
				return hierarchy.get(0);
			else
				return hierarchy.get(index-1);
		}
		return null;
	}
	
	
	public static SubPageflowState getSubPageflowState(List<SubPageflowState> states, Pageflow current) {
		for (SubPageflowState state : states) {
			if (state.getSubPageflow().equals(current)) {
				return state;
			}
		}
		return null;
	}
	
	/**
	 * @param hierarchy
	 * @param pageflow
	 * @return
	 */
	public static List<Pageflow> addToList(List<Pageflow> hierarchy, Pageflow pageflow) {
		if (hierarchy.lastIndexOf(pageflow) == -1) {
			hierarchy.add(pageflow);
		}
		return hierarchy;
	}
	
	
	/**
	 * @param states
	 * @param state
	 * @return
	 */
	public static List<SubPageflowState> addToSubPageflowList(List<SubPageflowState> states, SubPageflowState state) {
		if (states.lastIndexOf(state) == -1) {
			states.add(state);
		}
		return states;
	}
	
	/**
	 * @param hierarchy
	 * @param pageflow
	 * @return
	 */
	public static List<SubPageflowState> removeFromSubPageflowList(List<SubPageflowState> states, SubPageflowState state) {
		int index = states.lastIndexOf(state);
		if (index != -1) {
			states.remove(index);
		}
		return states;
	}
	
	/**
	 * @param hierarchy
	 * @param pageflow
	 * @return
	 */
	public static List<Pageflow> removeFromList(List<Pageflow> hierarchy, Pageflow pageflow) {
		int index = hierarchy.lastIndexOf(pageflow);
		if (index != -1) {
			hierarchy.remove(index);
		}
		return hierarchy;
	}
	
	/**
	 * @param hierarchy
	 * @param transition
	 * @return
	 */
	public static List<Transition> getAllEndStateTransitions(List<SubPageflowState> hierarchy, Transition transition) {
		List<Transition> transitions = new ArrayList<Transition>();
		transitions.add(transition);
		collectEndStateTransitions(transitions, hierarchy, transition);
		return transitions;
	}
	
	
	/**
	 * @param transitions
	 * @param hierarchy
	 * @param transition
	 */
	private static void collectEndStateTransitions(List<Transition> transitions, List<SubPageflowState> hierarchy, Transition transition) {
		if (transition.getToState() instanceof EndState) {
			Pageflow pf = (Pageflow) transition.eContainer();
			SubPageflowState state = getSubPageflowState(hierarchy, pf);
			if (state != null) {
				Transition tr = getTransitionFromCallingPageflow(state, transition, pf);
				if (tr != null) {
					transitions.add(tr);
					collectEndStateTransitions(transitions, hierarchy, tr);
				}
			} 
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Transition getTransitionFromCallingPageflow(SubPageflowState sub, Transition transition, Pageflow spf) {	
		Pageflow spflow = sub.getSubPageflow();
		if (spflow.equals(spf)) {
			List<TransitionMapping> mappings = sub.getTransitionMappings();
			for (TransitionMapping tm : mappings) {
				if (tm.getEndState().equals(transition.getToState())) {							
					return tm.getTransition();
				}
			}
		}
		return null;
	}
	
	/**
	 * @param transitions
	 * @param name
	 * @param toState
	 * @return
	 */
	public static List<String> addToTransitions(List<String> transitions, String name, String toState) {
		String uname = name+toState+"-redirector";
		if (!transitions.contains(uname)) {
			transitions.add(uname);
		}
		return transitions;
	}

}
