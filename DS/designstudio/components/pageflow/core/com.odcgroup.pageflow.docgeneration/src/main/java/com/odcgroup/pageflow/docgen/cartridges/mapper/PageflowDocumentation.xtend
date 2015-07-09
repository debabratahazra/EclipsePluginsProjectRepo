package com.odcgroup.pageflow.docgen.cartridges.mapper

import com.odcgroup.pageflow.docgen.utils.GenerationUtils
import java.util.List
import com.odcgroup.pageflow.model.Pageflow
import com.odcgroup.pageflow.model.Property
import com.odcgroup.pageflow.model.State
import com.odcgroup.pageflow.model.InitState
import com.odcgroup.pageflow.model.ViewState
import com.odcgroup.pageflow.model.SubPageflowState
import com.odcgroup.pageflow.model.EndState
import com.odcgroup.pageflow.model.DecisionState
import com.odcgroup.pageflow.model.Transition
import com.odcgroup.pageflow.model.Action
import com.odcgroup.pageflow.model.TransitionAction

class PageflowDocumentation {
	
	def htmlOpening ()'''
	<!DOCTYPE html>
	<html lang="en">		
	<body>
	''' 
		
	def htmlClosing ()'''
	</body>
	</html>
	''' 

	def pageflowDescription (Pageflow pageflow)'''
	«htmlOpening()»
	«pageflow.desc»<br><br>
	Model Path: Pageflows<I>«pageflow.eResource.URI.path.substring(0, pageflow.eResource.URI.path.length - 9)»</I>
	«htmlClosing()»
	'''
	
	def pageflowProperties (Pageflow pageflow)'''
	«htmlOpening()»	
	List of pageflow properties: <br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Property</th>
			<th bgcolor="#6495ED">Value</th>
		</tr>
		<tr>
			<td>Pageflow Name</td>
			<td>«pageflow.name»</td>
		</tr>
		<tr>
			<td>Error View URI</td>	
			<td>«pageflow.errorView.url»</td>
		</tr>	
		<tr>
			<td>Target File Name</td>
			<td>«pageflow.fileName»</td>
		</tr>
		«FOR Object pObj : pageflow.property.toList»
			«IF pObj instanceof Property»
			«var Property prop = (pObj as Property)»
		<tr>
			<td>Property name: «prop.name»</td>
			<td>«prop.value»</td>
		</tr>
			«ENDIF»
		«ENDFOR»
	</table> <br>
	«htmlClosing()»		
	'''
	
	def pageflowStates (Pageflow pageflow)'''
	«htmlOpening()»	
	List of pageflow states: <br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Name</th>
			<th bgcolor="#6495ED">Type</th>
			<th bgcolor="#6495ED">Description</th>
			<th bgcolor="#6495ED">Reference</th>
		</tr>
		«var initState = fetchInitState(pageflow.states.toList)»
		«IF initState!=null»
		<tr>
			<td>«initState.name»</td>
			<td>Init State</td>
			<td>«initState.desc»</td>
			<td>None</td>
		</tr>
		«ENDIF»
		«var pageStates = fetchPageStates(pageflow.states.toList)»
		«IF !pageStates.nullOrEmpty»
		«FOR page: pageStates»
		<tr>
			<td>«page.displayName»</td>
			<td>Page</td>
			<td>«page.desc»</td>
			<td>«IF page.view.url.startsWith("resource:")»Page: «page.view.url.substring(11)»«ELSE»Page: «page.view.url»«ENDIF»</td>
		</tr>
		«ENDFOR»
		«ENDIF»
		«var subPageflowStates = fetchSubPageflowStates(pageflow.states.toList)»
		«IF !subPageflowStates.nullOrEmpty»
		«FOR subPfState: subPageflowStates»
		<tr>
			<td>«subPfState.displayName»</td>
			<td>Sub-Pageflow</td>
			<td>«subPfState.desc»</td>
			<td>Pageflow: «subPfState.subPageflow.eResource.URI.toString»</td>
		</tr>
		«ENDFOR»
		«ENDIF»
		«var decisnStates = fetchDecisionStates(pageflow.states.toList)»
		«IF !decisnStates.nullOrEmpty»
		«FOR decideState: decisnStates»
		<tr>
			<td>«decideState.displayName»</td>
			<td>Decision State</td>
			<td>«decideState.desc»</td>
			<td>None</td>
		</tr>
		«ENDFOR»
		«ENDIF»
		«var endStates = fetchEndStates(pageflow.states.toList)»
		«IF !endStates.nullOrEmpty»
		«FOR endState: endStates»
		<tr>
			<td>«endState.displayName»</td>
			<td>End State</td>
			<td>«endState.desc»</td>
			<td>Exit URI: «endState.exitUrl»</td>
		</tr>
		«ENDFOR»
		«ENDIF»
	</table> <br>
	«htmlClosing()»		
	'''
	
	def pageflowTransitions (Pageflow pageflow)'''
	«htmlOpening()»	
	List of pageflow transitions: <br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">From state</th>
			<th bgcolor="#6495ED">To state</th>
			<th bgcolor="#6495ED">Name</th>
			<th bgcolor="#6495ED">Description</th>
			<th bgcolor="#6495ED">Actions</th>
		</tr>
		«var allTrans = pageflow.transitions.toList»
		«IF allTrans!=null && !allTrans.nullOrEmpty»
			«FOR Object eachTransition: allTrans»
			«var Transition trans = (eachTransition as Transition)»
		<tr>
			<td><img src=«fetchStateImage(trans.fromState)»/>«trans.fromState.displayName»</td>
			<td><img src=«fetchStateImage(trans.toState)»/>«trans.toState.displayName»</td>
			<td>«trans.displayName»</td>
			<td>«trans.desc»</td>
			<td>
			«var actions = trans.actions»
			«IF actions!=null && !actions.nullOrEmpty»
				«FOR actns: actions»
				«var Action action = (actns as Action)»
			«action.name»<br>
				«ENDFOR»
			«ENDIF»
			</td>
		</tr>
			«ENDFOR»
		«ENDIF»
	</table> <br>
	«htmlClosing()»		
	'''
	
	def pageflowXTransitions (Pageflow pageflow)'''
	«htmlOpening()»	
	List of pageflow transitions: <br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Name</th>
			<th bgcolor="#6495ED">From state</th>
			<th bgcolor="#6495ED">To state</th>
			<th bgcolor="#6495ED">Description</th>
			<th bgcolor="#6495ED">Action name</th>
			<th bgcolor="#6495ED">Description</th>
			<th bgcolor="#6495ED">URI</th>
			<th bgcolor="#6495ED">Properties Name</th>
			<th bgcolor="#6495ED">Properties Value</th>
			<th bgcolor="#6495ED">Validation Process</th>
			<th bgcolor="#6495ED">If error Reported</th>
		</tr>
		«var allTrans = pageflow.transitions.toList»
		«IF allTrans!=null && !allTrans.nullOrEmpty»
			«FOR Object eachTransition: allTrans»
			«var Transition trans = (eachTransition as Transition)»
		<tr>
			<td>«trans.displayName»</td>
			<td>«trans.fromState.displayName»</td>
			<td>«trans.toState.displayName»</td>
			<td>«trans.desc»</td>
			«var actions = trans.actions»
			«IF actions!=null && !actions.nullOrEmpty»
				«FOR actns: actions»
				«var Action action = (actns as Action)»
					«IF actions.indexOf(action) != 0»
					<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					«ENDIF»
			<td>«action.name»</td>
			<td>«action.desc»</td>
			<td>«action.uri»</td>
			<td>		«FOR acProp : action.property»
						«var Property acProps = (acProp as Property)»
			«acProps.name»<br>
						«ENDFOR»
			</td>
			<td>		«FOR acProp : action.property»
						«var Property acProps = (acProp as Property)»
			«acProps.value»<br>
						«ENDFOR»
			</td>
						«IF action instanceof TransitionAction»
						«var TransitionAction transActn = (action as TransitionAction)»
			<td>«IF transActn.problemManagement.literal.equals("Validation")»Yes«ELSE»No«ENDIF»</td>
			<td>«IF transActn.problemManagement.literal.equals("Continue")»Continue«ELSEIF transActn.problemManagement.literal.equals("Go back")»Go back«ENDIF»</td>
						«ENDIF»
					
				«ENDFOR»
			«ENDIF»
		</tr>
			«ENDFOR»
		«ENDIF»
	</table> <br>
	«htmlClosing()»		
	'''
	
	def String fetchStateImage(State state) {
		var String imgUrl = null;
		if(state instanceof InitState){
			imgUrl = "\"InitState.jpg\""
		}
		else if(state instanceof ViewState){
			imgUrl = "\"ViewState.jpg\""
		}
		else if(state instanceof DecisionState){
			imgUrl = "\"DecisionState.jpg\""
		}
		else if(state instanceof SubPageflowState){
			imgUrl = "\"SubPageflowState.jpg\""
		}
		else if(state instanceof EndState){
			imgUrl = "\"EndState.jpg\""
		} 
		return imgUrl
	}
	
	
	def InitState fetchInitState(List<State> states) {
		for(state : states){
				if(state instanceof InitState){
					return (state as InitState)
				}
		}
		return null
	}
	
	def List<ViewState> fetchPageStates(List<State> states) {
		var List<ViewState> pageStates = newArrayList
		for(state : states){
				if(state instanceof ViewState){
					pageStates.add((state as ViewState))
				}
		}
		return pageStates
	}
	
	def List<SubPageflowState> fetchSubPageflowStates(List<State> states) {
		var List<SubPageflowState> subPageflowStates = newArrayList
		for(state : states){
				if(state instanceof SubPageflowState){
					subPageflowStates.add((state as SubPageflowState))
				}
		}
		return subPageflowStates
	}
	
	def List<DecisionState> fetchDecisionStates(List<State> states) {
		var List<DecisionState> decisionStates = newArrayList
		for(state : states){
				if(state instanceof DecisionState){
					decisionStates.add((state as DecisionState))
				}
		}
		return decisionStates
	}
	
	def List<EndState> fetchEndStates(List<State> states) {
		var List<EndState> endStates = newArrayList
		for(state : states){
				if(state instanceof EndState){
					endStates.add((state as EndState))
				}
		}
		return endStates
	}
	
	
	def pageflowIndex(String path , List<String> fileList)'''
	«htmlOpening()»
	<ul>
		«FOR String pageflowFile : fileList»
		<li>
			<A HREF="«path + "\\"»«pageflowFile»">«GenerationUtils::getTitleForFileName(pageflowFile)»</A>
		<br></br>
		</li>
		«ENDFOR»
	</ul>
	«htmlClosing»
	'''
}