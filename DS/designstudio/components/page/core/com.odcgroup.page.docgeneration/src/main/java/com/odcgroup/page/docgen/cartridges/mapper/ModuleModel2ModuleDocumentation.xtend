package com.odcgroup.page.docgen.cartridges.mapper

import com.odcgroup.page.docgen.model.ModuleAttribute
import com.odcgroup.page.docgen.utils.GenerationUtils
import com.odcgroup.page.docgen.utils.ModuleUtils
import com.odcgroup.page.metamodel.PropertyTypeConstants
import com.odcgroup.page.metamodel.WidgetTypeConstants
import com.odcgroup.page.model.Event
import com.odcgroup.page.model.Model
import com.odcgroup.page.model.Snippet
import com.odcgroup.page.model.Translation
import com.odcgroup.page.model.Widget
import com.odcgroup.workbench.core.IOfsProject
import java.util.List

class ModuleModel2ModuleDocumentation {
	
	def htmlOpening ()'''
	<!DOCTYPE html>
	<html lang="en">		 
	<body>
	''' 
		
	def htmlClosing ()'''
	</body>
	</html>
	''' 
	
	def moduleDescription (Model model,IOfsProject project)'''
	«generalDescription(model,project,WidgetTypeConstants::MODULE)»
	'''
	
	def fragmentDescription (Model model,IOfsProject project)'''
	«generalDescription(model,project,WidgetTypeConstants::FRAGMENT)»
	'''
		
	
	def moduleIncludes (Model model,IOfsProject project) '''
	«model.generalIncludes(WidgetTypeConstants::MODULE,project)»
	'''
	
	def fragmentIncludes (Model model,IOfsProject project) '''
	«model.generalIncludes(WidgetTypeConstants::FRAGMENT,project)»
	'''

	def moduleContainers(Model model,IOfsProject project) '''
	«var containmentValue = model.widget.fetchModuleInformation(WidgetTypeConstants::MODULE,"containment")»
	«model.widget.tabCondContainer(project,containmentValue)»
	'''
	
	def moduleXContainers(Model model,IOfsProject project) '''
	«var containmentValue = model.widget.fetchModuleInformation(WidgetTypeConstants::MODULE,"containment")»
	«model.widget.tabCondXContainer(project,containmentValue)»
	'''
	
	def fragmentContainers(Model model,IOfsProject project) '''
	«model.widget.tabCondContainer(project,"")»
	'''
	
	def fragmentXContainers(Model model,IOfsProject project) '''
	«model.widget.tabCondXContainer(project,"")»
	'''
	
	def moduleAttributes(Model model,IOfsProject project) '''
	«var List<Widget> allAttributes = newArrayList»
	«model.widget.fetchAllAttributes(allAttributes)»
	«IF !allAttributes.nullOrEmpty»
	«htmlOpening()»
	List of module attributes:
	«coreAttributes(project,allAttributes)»
	«htmlClosing()»
	«allAttributes.clear»
	«ENDIF»
	'''
	
	def fragmentAttributes(Model model,IOfsProject project) '''
	«var List<Widget> allAttributes = newArrayList»
	«model.widget.fetchAllAttributes(allAttributes)»
	«IF !allAttributes.nullOrEmpty»
	«htmlOpening()»
	List of fragment attributes:
	«coreAttributes(project,allAttributes)»
	«htmlClosing()»
	«allAttributes.clear»
	«ENDIF»
	'''
	
	
	def moduleXAttributes(Model model,IOfsProject project) '''
	«var List<Widget> allAttributes = newArrayList»
	«var attributeList = model.widget.fetchAllXAttributes(allAttributes)»
	«IF !attributeList.nullOrEmpty»
	«htmlOpening()»
	Large List of module attributes:
	«coreXAttributes(project,attributeList)»
	«htmlClosing()»
	«attributeList.clear»
	«ENDIF»
	'''
	
	def fragmentXAttributes(Model model,IOfsProject project) '''
	«var List<Widget> allAttributes = newArrayList»
	«var attributeList = model.widget.fetchAllXAttributes(allAttributes)»
	«IF !attributeList.nullOrEmpty»
	«htmlOpening()»
	Large List of fragment attributes:
	«coreXAttributes(project,attributeList)»
	«htmlClosing()»
	«attributeList.clear»
	«ENDIF»
	'''
	
	def checkActionExist(List<Widget> actionList) {
		for( Widget actWid: actionList){
			for (Event eve: actWid.events){
				if(eve!=null) return true
			}	
		}	
		return false
	}
	
	def moduleActions(Model model,IOfsProject project) '''
	«var List<Widget> actionWidgets = newArrayList»
	«var actionList = model.widget.fetchActionWidgets(actionWidgets)»
	«IF !actionList.nullOrEmpty && checkActionExist(actionList)»
	«htmlOpening()»
	List of actions that can be triggered within the module:
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Widget</th>
			<th bgcolor="#6495ED">Event</th>
			<th bgcolor="#6495ED">Label</th>
			<th bgcolor="#6495ED">Navigates To</th>
			<th bgcolor="#6495ED">Target</th>
			<th bgcolor="#6495ED">Contained In</th>
			<th bgcolor="#6495ED">Description</th>
		</tr>
	«FOR Widget actWid: actionList»	
		«FOR Event eve: actWid.events»
		<tr>
			<td>«IF actWid.type.name.equals("TableGroup")»Table group
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) && actWid.getPropertyValue("matrixAxis").equals("y-axis")»Matrix Axis Y
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) && actWid.getPropertyValue("matrixAxis").equals("x-axis")»Matrix Axis X
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_CONTENTCELLITEM)»Matrix Cell Item
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (actWid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-row")»Matrix Last Row Item
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (actWid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-column")»Matrix Last Column Item
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (actWid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-cell")»Matrix Last Cell Item
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_EXTRACOLUMNITEM)»Matrix Additonial Column
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::TABLE_COLUMN_ITEM)»Table Column Item
			«ELSE»«actWid.type.name»«ENDIF»</td>
			<td>«eve.eventType.name +" (" + eve.functionName + ")"»</td>
			<td>«IF actWid.type.name.equals("TableGroup")»
			«actWid.getPropertyValue("group-column-name")»
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_CONTENTCELLITEM) 
				|| ( actWid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) &&  !(actWid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-row")) »
			«actWid.getPropertyValue("column-name")»
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_EXTRACOLUMNITEM)»
			«actWid.fetchTranslationValue(project)»
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::ICON)» «actWid.getPropertyValue("icon")»
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (actWid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-row")»
			«actWid.getPropertyValue("domainAttribute")»
			«ELSEIF !actWid.type.name.equals(WidgetTypeConstants::MATRIX_AXIS)»«actWid.fetchTranslationValue(project)»«ENDIF»</td>
			<td>«IF ( actWid.type.name.equals("TableGroup") || actWid.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) || actWid.type.name.equals(WidgetTypeConstants::MATRIX_CONTENTCELLITEM) 
				|| actWid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) || actWid.type.name.equals(WidgetTypeConstants::MATRIX_EXTRACOLUMNITEM) ) && eve.findParameter("flow-action") != null »
			Continuation on: «eve.findParameter("flow-action").value»
			«ELSEIF (eve.findParameter("call-URI") != null && eve.findParameter("call-URI").value.equals("<pageflow:continuation/>") && eve.findParameter("param")!=null && !eve.findParameter("param").value.nullOrEmpty)»
			Continuation on: «IF eve.findParameter("param").value.startsWith("flow-action=")»«eve.findParameter("param").value.substring("flow-action=".length)»«ENDIF»
			«ELSEIF ( eve.findParameter("call-URI")!=null && !eve.findParameter("call-URI").value.nullOrEmpty && eve.findParameter("call-URI").value.endsWith(".pageflow") ) || (actWid.type.name.equals(WidgetTypeConstants::ICON))»
			«IF eve.findParameter("call-URI") != null »
			«IF eve.findParameter("call-URI").value.startsWith("resource:///")»
			Pageflow: «eve.findParameter("call-URI").value.substring("resource:///".length,eve.findParameter("call-URI").value.lastIndexOf("."))»
			«ELSE»
			Pageflow: «eve.findParameter("call-URI").value»
			«ENDIF»
			«ENDIF»
			«ELSEIF actWid.type.name.equals(WidgetTypeConstants::ICON)» «actWid.getPropertyValue("icon")»
			«ENDIF»</td>
			<td>«IF eve.findParameter("target") != null»«eve.findParameter("target").value»«ENDIF»</td> 
			<td>«actWid.fetchParent(project)»</td> 
			<td>«actWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»	
	«ENDFOR»«actionWidgets.clear»
	</table>
	«htmlClosing()»
	«ENDIF»
	'''
	
	def moduleActionFilters(Model model,IOfsProject project) '''
	«var List<Event> actionEvents = newArrayList»
	«model.widget.fetchActionEvents(actionEvents)»
	«IF !actionEvents.nullOrEmpty && checkSearchAction(actionEvents,"FilterSet")»
	«htmlOpening()»
	«var List<Snippet> searchSnipet = newArrayList»
	«FOR Event eve : actionEvents»
	«fetchEventSnipet(eve,searchSnipet,"FilterSet")»
	«IF !searchSnipet.nullOrEmpty»
	List of related filters set on «eve.widget.type.name»: «IF !ModuleUtils::getTitle(project,eve.widget).nullOrEmpty»«ModuleUtils::getTitle(project,eve.widget)»«ELSE»«eve.widget.typeName»«ENDIF» <br></br>
	«FOR Snippet snpet : searchSnipet»
	This filter(s) applies on the dataset: «snpet.properties.get(2).value» (level: «snpet.properties.get(1).value»)<br></br>
		<table border="1">
		<tr>
			<th bgcolor="#6495ED">Attribute</th>
			<th bgcolor="#6495ED">Operator</th>
			<th bgcolor="#6495ED">Value(1)</th>
			<th bgcolor="#6495ED">Value(2)</th>
			<th bgcolor="#6495ED">Mode</th>
		</tr>
		«FOR Snippet childSnipet : snpet.contents»
		<tr>
			<td>«childSnipet.properties.get(0).value»</td>
			<td>«childSnipet.properties.get(2).value»</td>
			<td>«childSnipet.properties.get(1).value»</td>
			<td>«childSnipet.properties.get(3).value»</td>
			<td>«childSnipet.properties.get(4).value»</td>
		</tr>
		«ENDFOR»
		</table>
	«ENDFOR»
	«searchSnipet.clear»	
	«ENDIF»
	«ENDFOR»
	«actionEvents.clear»
	«htmlClosing()»	
	«ENDIF»
	'''
	
	def checkSearchAction(List<Event> actionEvents,String type) {
		for( Event eve : actionEvents){
			var List<Snippet> searchSnipet = newArrayList
			fetchEventSnipet(eve,searchSnipet,type)
			if( !searchSnipet.nullOrEmpty) {
				return true
			}
		}
		return false
	}
		
	def moduleActionSearch(Model model,IOfsProject project) '''
	«var List<Event> actionEvents = newArrayList»
	«model.widget.fetchActionEvents(actionEvents)»
	«IF !actionEvents.nullOrEmpty && checkSearchAction(actionEvents,"Query")»
	«htmlOpening()»
	«var List<Snippet> searchSnipet = newArrayList»
	«FOR Event eve : actionEvents»
	«fetchEventSnipet(eve,searchSnipet,"Query")»
	«IF !searchSnipet.nullOrEmpty»
		«FOR Snippet snpet : searchSnipet»
		Search set on «eve.widget.type.name»: «IF !ModuleUtils::getTitle(project,eve.widget).nullOrEmpty»«ModuleUtils::getTitle(project,eve.widget)»«ELSE»«eve.widget.typeName»«ENDIF» <br></br>
		<table border="1">
		<tr>
			<th bgcolor="#6495ED">Property name</th>
			<th bgcolor="#6495ED">Property value</th>
		</tr>
		<tr>
			<td>Search module </td>
			«val uriPath = snpet.findProperty("queryOutputModule").modelURI»
			<td> «IF uriPath != null» «uriPath.path.substring(0,uriPath.path.indexOf("."))»«ENDIF»</td>
		</tr>
		<tr>
			<td>Attributes Selection </td>
			<td>«snpet.findProperty("queryAttributes").value»</td>
		</tr>
		<tr>
			<td>Attribute mapping  </td>
			<td>«snpet.findProperty("queryMappings").value»</td>
		</tr>
		<tr>
			<td>Tab to display </td>
			<td>«snpet.properties.get(2).value»</td>
		</tr>
		<tr>
			<td>Selection mode </td>
			<td>«snpet.findProperty("selectionMode").value»</td>
		</tr>
			<tr>
			<td>Run at start </td>
			<td>«snpet.findProperty("runAtStart").value»</td>
		</tr>
		</tr>
			<tr>
			<td>Max row count </td>
			<td>«snpet.findProperty("max-rows").value»</td>
		</tr>
		</table> <br></br>
		«ENDFOR» 
		«searchSnipet.clear»
	«ENDIF»
	«ENDFOR»
	«actionEvents.clear»
	«htmlClosing()»
	«ENDIF»
	'''
	
	def moduleAutoCompleteDesign(Model model,IOfsProject project) '''
	«var List<Widget> autoComplDesgns = newArrayList»
	«var av =  model.widget.getListOfWidgetByType(autoComplDesgns,"AutoCompleteDesign")»
	«IF !autoComplDesgns.nullOrEmpty»
	«htmlOpening()»
	«FOR Widget autoComplDes : autoComplDesgns»
		«IF autoComplDes!=null && !autoComplDes.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).nullOrEmpty»
		Description: «autoComplDes.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)» <br></br>
		«ENDIF»
	List of properties set on the auto-complete design: <br></br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Property name</th>
			<th bgcolor="#6495ED">value</th>
		</tr>
		<tr>
			<td>Sorting Attribute</td>
			<td>«autoComplDes.fetchModuleInformation("AutoCompleteDesign","sortAttribute")»</td>
		</tr>
		<tr>
			<td>Max Returned Rows</td>
			<td>«autoComplDes.fetchModuleInformation("AutoCompleteDesign","max-returned")»</td>
		</tr>
		<tr>
			<td>Dataset </td>
			<td>«ModuleUtils::getDatasetName(project,autoComplDes)»</td>
		</tr>
	</table> <br></br>
	List of attributes composing the drop down list of items: <br></br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Line</th>
			<th bgcolor="#6495ED">Attribute 1</th>
			<th bgcolor="#6495ED">Attribute 2</th>
			<th bgcolor="#6495ED">Attribute 3</th>
		</tr>
		«var String title = autoComplDes.fetchModuleInformation("AutoCompleteDesign","titleAttribute")»
		 
		«IF !title.nullOrEmpty»
		<tr>
			<td>Title</td>
			<td>«title»</td>
			<td></td>
			<td></td>
		</tr>
		«ENDIF»
		
		«FOR Snippet lineSnip: autoComplDes.snippets »
		<tr>
		«IF !lineSnip.contents.empty && checkLineItemEnabled(lineSnip.contents)»
			<td>Attribute Line «autoComplDes.snippets.indexOf(lineSnip)+1»</td> 
			«FOR Snippet lineItemSnip: lineSnip.contents »
				«IF Boolean::valueOf(lineItemSnip.findProperty("translation").value)»
				<td>Label: «lineItemSnip.findProperty("lineAttribute").value» (style: «lineItemSnip.findProperty("cssClass").value»)</td>
				«ELSE»
				<td></td>	
				«ENDIF»
			«ENDFOR»
			«IF lineSnip.contents.size == 1»
				<td></td>
				<td></td>
			«ELSEIF lineSnip.contents.size == 2»
				<td></td>
			«ENDIF»	
		«ENDIF»
		</tr>
		«ENDFOR»
		
	</table> <br></br>	
	«ENDFOR»
	«autoComplDesgns.clear»		
	«htmlClosing()»
	«ENDIF»
	'''	
	
	def checkLineItemEnabled(List<Snippet> lineItems){
		for(Snippet lineItemSnipet: lineItems){
			if(Boolean::valueOf(lineItemSnipet.findProperty("translation").value)){
				return true;
			}
		}
		return false;
	}

	def boolean checkLabelExistInWidgets(List<Widget> allWidgets){
		for( Widget wid: allWidgets) {
			if( !wid.labels.empty ) {
				return true
			}
		}
		return false
	}

	def moduleTranslations(Model model,IOfsProject project) '''
	«var List<Widget> allWidgets = newArrayList»
	«model.widget.fetchAllWidgets(allWidgets)»
	«IF !allWidgets.nullOrEmpty && checkLabelExistInWidgets(allWidgets)»
	«htmlOpening()»
	List of translations of the module:<br></br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Widget</th>
			<th bgcolor="#6495ED">Kind</th>
			<th bgcolor="#6495ED">Origin</th>
			<th bgcolor="#6495ED">English</th>
			<th bgcolor="#6495ED">French</th>
			<th bgcolor="#6495ED">German</th>
		</tr>
		«FOR Widget wid: allWidgets»
		«IF !wid.labels.empty»
		<tr>
			<td>«IF wid.type.name.equals(WidgetTypeConstants::LABEL_DOMAIN)»Domain attribute
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::BOX)»Box title
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MODULE)»Module title
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::TABLE_COLUMN_ITEM)»Table item
			«ELSEIF wid.type.name.equals("TableGroup")»Table group
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) && wid.getPropertyValue("matrixAxis").equals("y-axis")»Matrix Axis Y
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) && wid.getPropertyValue("matrixAxis").equals("x-axis")»Matrix Axis X
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_CONTENTCELLITEM)»Matrix Cell Item
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (wid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-row")»Matrix Last Row Item
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (wid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-column")»Matrix Last Column Item
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) && (wid.eContainer as Widget).getPropertyValue("matrixCellType").equals("last-cell")»Matrix Last Cell Item
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::MATRIX_EXTRACOLUMNITEM)»Matrix Additonial Column
			«ELSEIF wid.type.name.equals(WidgetTypeConstants::TABLE_COLUMN)»Table Column
			«ELSE»«wid.typeName»«ENDIF»</td>
			<td>«IF wid.type.translationSupported»«wid.getTransValue»«ENDIF»</td>
			<td>«IF wid.domainWidget»Domain«ELSE»Local«ENDIF»</td>
			<td>«IF getRespectiveTranslation(wid.labels,"en")!=null»«getRespectiveTranslation(wid.labels,"en")»«ELSE»«fetchTranslationValue(wid,project)»«ENDIF»</td>
			<td>«IF getRespectiveTranslation(wid.labels,"fr")!=null»«getRespectiveTranslation(wid.labels,"fr")»«ELSE»«fetchTranslationValue(wid,project)»«ENDIF»</td>
			<td>«IF getRespectiveTranslation(wid.labels,"de")!=null»«getRespectiveTranslation(wid.labels,"de")»«ELSE»«fetchTranslationValue(wid,project)»«ENDIF»</td>
		</tr>	
		«ENDIF»	
		«ENDFOR»	
	</table> <br></br>	
	«allWidgets.clear»
	«htmlClosing()»
	«ENDIF»
	'''	
	
	def String getRespectiveTranslation(List<Translation> translations,String type){
		for( Translation transMessage : translations){
			if(transMessage.language.equals(type)){
				return transMessage.message
			}
		}
		return null
	}
		
	def fetchEventSnipet(Event actionEvent,List<Snippet> snipet,String type){
		for( Snippet spi : actionEvent.snippets){
			if(spi.typeName.equals(type)){
				snipet.add(spi)	
			}
		}
	}
	
	def generalDescription (Model model,IOfsProject project,String type)'''
	«IF model.widget.getPropertyValue(PropertyTypeConstants::DOCUMENTATION) !=null»
	«htmlOpening()»
	«var docText = model.widget.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
	«IF docText!=null && !docText.nullOrEmpty»
	Description: «docText» <br>
	«ENDIF»  <br>
	«IF type.equals(WidgetTypeConstants::MODULE)»
	Model Path: <I>«model.eResource.URI.path.substring(0, model.eResource.URI.path.length - 7)»</I>  <br>
	«ELSEIF type.equals(WidgetTypeConstants::FRAGMENT)»
	Model Path: <I>«model.eResource.URI.path.substring(0, model.eResource.URI.path.length - 9)»</I>  <br>
	«ENDIF»<br>
	List of properties set on the module: <br><br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Property Name</th>
			<th bgcolor="#6495ED">Value</th>
		</tr>
		<tr>
			<td>Dataset </td>
			<td>«ModuleUtils::getDatasetName(project,model.widget)»</td>
		</tr>
		<tr>
			<td>Based on Class </td>
			<td>«ModuleUtils::getDatasetBaseClassName(project,model.widget)»</td>
		</tr>
		«IF type.equals(WidgetTypeConstants::MODULE)»
		<tr>
			<td>Title </td>
			<td>«ModuleUtils::getTitle(project,model.widget)»</td>
		</tr>
		<tr>
			<td>Collapse </td>
			<td>«model.widget.fetchModuleInformation(WidgetTypeConstants::MODULE,"collapsed")»</td>
		</tr>
		<tr>
			<td>Collapsible </td>
			<td>«model.widget.fetchModuleInformation(WidgetTypeConstants::MODULE,"collapsible")»</td>
		</tr>
		«ENDIF»
	«htmlClosing()»
	«ENDIF»
	'''
	
	def generalIncludes  (Model model,String type,IOfsProject project) '''
	«IF model.widget.type.name.equals(type)»
	«var List<Widget> includeSources = newArrayList»	
	«model.widget.getWidgetByType(type).fetchIncludeSources(WidgetTypeConstants::INCLUDE,includeSources,type)»
	«var List<Widget> xspInclPaths = newArrayList»	
	«model.widget.getWidgetByType(type).fetchIncludeSources("IncludeXSP",xspInclPaths,type)»
	«var List<Widget> extPageOrImgUrls = newArrayList»	
	«model.widget.getWidgetByType(type).fetchIncludeSources(WidgetTypeConstants::EXTERNAL_INCLUDE_WIDGET,extPageOrImgUrls,type)»
	«IF ((includeSources!=null && !includeSources.nullOrEmpty ) || (xspInclPaths!=null && !xspInclPaths.nullOrEmpty) || (extPageOrImgUrls!=null && !extPageOrImgUrls.nullOrEmpty) ) »
	«htmlOpening()»
	List of Includes: <br><br>
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Include</th>
			<th bgcolor="#6495ED">Contained In</th>
			<th bgcolor="#6495ED">Description</th>
		</tr>
		«FOR Widget inclWid : includeSources»
		<tr>
		«var widSrc = inclWid.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model.eResource.URI.path»
		«IF type.equals(WidgetTypeConstants::MODULE) && widSrc.endsWith("module") »
			<td>Module: «widSrc.substring(0,widSrc.lastIndexOf("."))»</td>
		«ELSEIF  type.equals(WidgetTypeConstants::FRAGMENT) && widSrc.endsWith("fragment")»	
			<td>Fragment: «widSrc.substring(0,widSrc.lastIndexOf("."))»</td>
		«ENDIF»	
			<td>«fetchParent(inclWid.parent,project)»</td>
			<td>«inclWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»		
		«FOR Widget xspInclude : xspInclPaths»
		<tr>			
			<td>XSP Page: «xspInclude.getPropertyValue("xsp-path")»</td>
			<td>«fetchParent(xspInclude.parent,project)»</td>
			<td>«xspInclude.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»
		«FOR Widget extOrImgUrl : extPageOrImgUrls»
		<tr>			
			<td>External Page/Image: «extOrImgUrl.getPropertyValue(PropertyTypeConstants::URL)»</td>
			<td>«fetchParent(extOrImgUrl.parent,project)»</td>
			<td>«extOrImgUrl.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»
	</table>
	«htmlClosing()»
	«ENDIF»
	«ENDIF»
	'''
	
	def void fetchIncludeSources(Widget widget,String widgetType,List<Widget> includeSources,String type) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null) {
				if(widgetType.equals(WidgetTypeConstants::INCLUDE)){
					var mod = widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
					if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && !mod.eResource.URI.path.nullOrEmpty){
					var widSrc = mod.eResource.URI.path
						if( widSrc.endsWith(type.toLowerCase)){
							includeSources.add(widget)	
						}
					}
				}else{
					includeSources.add(widget)	
				}
				
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchIncludeSources(widgetType,includeSources,type)
			}
		}		
	}
	
	def String fetchParent(Widget widget,IOfsProject project) {
		if(widget!=null){
			if(widget.typeName.equals(WidgetTypeConstants::CONDITIONAL_BODY)) {
				var String cond = widget.getPropertyValue("technicalName")
				var String conditional = widget.parent.getPropertyValue("technicalName")
				if(!conditional.nullOrEmpty || !cond.nullOrEmpty){
					return "Condition: "+ (if (!conditional.nullOrEmpty)conditional else "") + (if(!conditional.nullOrEmpty && !cond.nullOrEmpty){"."}else{""}) + (if (!cond.nullOrEmpty)cond else "")
				}	
				else 
				return ""
			}
			else if(widget.typeName.equals(WidgetTypeConstants::TAB)) {
				var String cond = widget.getPropertyValue("tab-name")
				var String tabPaneName = widget.parent.getPropertyValue("name")
				if (!cond.nullOrEmpty || !tabPaneName.nullOrEmpty)
					return "Tab: "+ (if (!tabPaneName.nullOrEmpty)tabPaneName else "") + (if(!tabPaneName.nullOrEmpty && !cond.nullOrEmpty){"."}else{""}) + (if (!cond.nullOrEmpty)cond else "") 
				else if (!fetchTranslationValue(widget,project).nullOrEmpty)
					return fetchTranslationValue(widget,project)
				else 
					return ""
			}else{
				 fetchParent(widget.parent,project)
			}
		}else{
			return ""
		}
	}
	

	def getListOfWidgetByType(Widget widget, List<Widget> widgets, String widgetType) {
		if (widget.type.name.equals(widgetType)){
			for(wid : widget.parent.contents){
					if(wid.model != null && !widgets.contains(wid)) {
						widgets.add(wid)
				}
			}
			return widgets
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.getListOfWidgetByType(widgets,widgetType)
			}
		}
		return widgets	
	}
	
	
	def Widget getWidgetByType(Widget widget, String widgetType) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null) {
				return widget
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				return wid.getWidgetByType(widgetType)
			}
		}	
	}
	
	def computeAttributes(IOfsProject project,List<Widget> attributeList) {
		var List<ModuleAttribute> modAttributes = newArrayList
		var List<Widget> labelDoms = fetchLabelDomains(attributeList)	
		for( Widget attR: attributeList) {
		var ModuleAttribute modAttr = new ModuleAttribute()
			modAttr.setLabel(fetchRespLabelDomains(attR,labelDoms,project))
			if (attR.type.name.equals(WidgetTypeConstants::INCLUDE)) {
					if (attR.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model.eResource.URI.path.endsWith("fragment")) {
						val domAssociation = attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ASSOCIATION)
						var includeModelAttr =  attR.fetchAttributeFromInclModel
						
						modAttr.setDomainReference(if (!domAssociation.nullOrEmpty) { domAssociation + "."} + if (includeModelAttr != null && !includeModelAttr.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE).nullOrEmpty) {includeModelAttr.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)} else {""})
					}
			}
			else if(attR.type.name.equals(WidgetTypeConstants::TABLE_COLUMN)){
				 modAttr.setModuleAttrTabletype("Table column")
				 modAttr.setDomainReference(if(!attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE).nullOrEmpty){attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)}else {""})
			}
			else if(attR.type.name.equals("TableGroup")){
				 modAttr.setModuleAttrTabletype("Grouping")
				 modAttr.setDomainReference((if(!attR.getPropertyValue("group-column-name").nullOrEmpty) { attR.getPropertyValue("group-column-name") }else {""} ))
				 if(attR.getPropertyValue("group-sorting-column-name").nullOrEmpty){
				 	var ModuleAttribute modAttr1 = new ModuleAttribute()
				 	modAttr1.setLabel(fetchRespLabelDomains(attR,labelDoms,project))
				 	modAttr1.setModuleAttrTabletype("Group sorting")
				 	modAttr1.setDomainReference((if (!attR.getPropertyValue("group-sorting-column-name").nullOrEmpty) {attR.getPropertyValue("group-sorting-column-name")} else {""} ))
				 	modAttr1.setContainedIn(attR.fetchParent(project))
				 	modAttributes.add(modAttr)
				 }
			}	
			else if(attR.type.name.equals("TableSort")){
				 modAttr.setModuleAttrTabletype("Sorting")
				 modAttr.setDomainReference(( if(!attR.getPropertyValue("sort-column-name").nullOrEmpty) { attR.getPropertyValue("sort-column-name") } else {""}))
			}	
			else if(attR.type.name.equals("TableExtra")){
				 modAttr.setModuleAttrTabletype("Extra column")
				 modAttr.setDomainReference((if(!attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE).nullOrEmpty) { attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE) } else {""}))
			}	
			else if(attR.type.name.equals("TableAggregate")){
				 modAttr.setModuleAttrTabletype("Computed column")
				 modAttr.setDomainReference(attR.getPropertyValue("aggregate-column-name"))
			}
			else if(attR.type.name.equals("TableKeepFilter")){
			 	 modAttr.setModuleAttrTabletype("Keep Filter column")
				 modAttr.setDomainReference(attR.getPropertyValue("keep-filter-column-name"))
			}
			else {
			modAttr.setDomainReference((if(!attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE).nullOrEmpty){attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)} else {""}))		
			}
			modAttr.setContainedIn(attR.fetchParent(project))
			modAttributes.add(modAttr)
		}
		
		return modAttributes.filterNull.sortBy(e| if(e.domainReference != null) { e.domainReference });
	}
	
	def computeXAttributes(IOfsProject project,List<Widget> attributeList) {
		var List<ModuleAttribute> modAttributes = newArrayList
		var List<Widget> labelDoms = fetchLabelDomains(attributeList)	
		for( Widget attR: attributeList) {
		var ModuleAttribute modAttr = new ModuleAttribute()
			modAttr.setLabel(fetchRespLabelDomains(attR,labelDoms,project))
			if (attR.type.name.equals(WidgetTypeConstants::INCLUDE)) {
					if (attR.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model.eResource.URI.path.endsWith("fragment")) {
						val domAssociation = attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ASSOCIATION)
						var includeModelAttr =  attR.fetchAttributeFromInclModel
						modAttr.setDomainReference(if (!domAssociation.nullOrEmpty) { domAssociation + "."} + if (includeModelAttr != null) {includeModelAttr.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)})
					}
			}		
			else {
			modAttr.setDomainReference(attR.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE))		
			}
			modAttr.setContainedIn(attR.fetchParent(project))
			modAttr.setDescription(attR.getPropertyValue(PropertyTypeConstants::DOCUMENTATION))
			modAttr.setTranslation(if (!attR.labels.nullOrEmpty) { getRespectiveTranslation(attR.labels,"en") })
			modAttr.setDataType(ModuleUtils::getBusinessTypeName(project,attR))
			modAttr.setType(attR.getPropertyValue(PropertyTypeConstants::TYPE))
			modAttr.setDisplayFormat(attR.getPropertyValue("displayFormat"))
			modAttr.setEditable(attR.getPropertyValue(PropertyTypeConstants::EDITABLE))
			if ( attR.getPropertyValue(PropertyTypeConstants::ENABLED)!=null) {
			modAttr.setEnable(if (Boolean::valueOf(attR.getPropertyValue(PropertyTypeConstants::ENABLED))) {"Yes"} else {"No"})
			} else {
			modAttr.setEnable(attR.getPropertyValue(PropertyTypeConstants::ENABLED_IS_BASED_ON))
			}
			modAttr.setRequired(attR.getPropertyValue(PropertyTypeConstants::REQUIRED))
			modAttr.setReadable(attR.getPropertyValue(PropertyTypeConstants::READABLE))
			modAttr.setMaximumCharacters(attR.getPropertyValue(PropertyTypeConstants::MAX_CHARACTERS))
			modAttr.setWidgetGroup(attR.getPropertyValue(PropertyTypeConstants::WIDGET_GROUP))
			modAttr.setBeanName(attR.getPropertyValue(PropertyTypeConstants::BEAN_NAME))
			modAttr.setBeanProperty(attR.getPropertyValue(PropertyTypeConstants::BEAN_PROPERTY))
			modAttr.setCssClass(attR.getPropertyValue(PropertyTypeConstants::CSS_CLASS))
			var horAlign = attR.getPropertyValue(PropertyTypeConstants::HORIZONTAL_ALIGNMENT)
			modAttr.setHorizontalAlignment(if (horAlign!=null && horAlign.equals("${corporatehalign}")) {"Corporate"} else { horAlign })
			modAttr.setVerticalAlignment(attR.getPropertyValue(PropertyTypeConstants::VERTICAL_ALIGNMENT))
			modAttr.setWidth(attR.getPropertyValue(PropertyTypeConstants::WIDTH))
			modAttr.setPattern(attR.getPropertyValue(PropertyTypeConstants::PATTERN_TYPE))
			modAttr.setAccessKey(attR.getPropertyValue(PropertyTypeConstants::ACCESS_KEY))
			modAttr.setTabIndex(attR.getPropertyValue(PropertyTypeConstants::TAB_INDEX))
			modAttr.setId(attR.getPropertyValue(PropertyTypeConstants::ID))
			if ( attR.type.name.equals(WidgetTypeConstants::LABEL)) {
				modAttr.setHorizontalTextPosition(attR.getPropertyValue(PropertyTypeConstants::HORIZONTAL_TEXT_POSITION))
				modAttr.setVerticalTextPosition(attR.getPropertyValue(PropertyTypeConstants::VERTICAL_TEXT_POSITION))
			} 
			if( attR.type.name.equals(WidgetTypeConstants::LABEL) || attR.type.name.equals(WidgetTypeConstants::RADIO_BUTTON)) {
				modAttr.setWrapped(attR.getPropertyValue("wrapped"))
			}
			if ( attR.type.name.equals(WidgetTypeConstants::ICON)) {
			modAttr.setIcon(attR.getPropertyValue(PropertyTypeConstants::ICON))
			}
			if ( attR.type.name.equals(WidgetTypeConstants::TEXTAREA)) {
			modAttr.setHeight(attR.getPropertyValue(PropertyTypeConstants::HEIGHT))
			modAttr.setRichText(attR.getPropertyValue(PropertyTypeConstants::RICHTEXT))
			}
			if ( attR.type.name.equals(WidgetTypeConstants::SEARCH_FIELD)) {
			modAttr.setSearchType(attR.getPropertyValue(PropertyTypeConstants::SEARCH_TYPE))
			modAttr.setAutoCompleteDesignModule(attR.getPropertyValue("designModuleName"))
			modAttr.setAutoCompleteDelay(attR.getPropertyValue(PropertyTypeConstants::DELAY_TYPE))
			modAttr.setAutoCompleteNumberOfChars(attR.getPropertyValue(PropertyTypeConstants::NB_CHARS))
			}
			if ( attR.type.name.equals("Calendar")) {
			modAttr.setNullValue(attR.getPropertyValue(PropertyTypeConstants::NILLABLE_TYPE))
			modAttr.setFreeDate(attR.getPropertyValue(PropertyTypeConstants::FREE_DATE_TYPE))
			}
			if ( attR.type.name.equals(WidgetTypeConstants::CHECKBOX) || attR.type.name.equals(WidgetTypeConstants::RADIO_BUTTON)) {
			modAttr.setDisplayLabel(attR.getPropertyValue(PropertyTypeConstants::DISPLAY_LABEL))
			modAttr.setSelected(attR.getPropertyValue(PropertyTypeConstants::SELECTED))
			modAttr.setGroup(attR.getPropertyValue(PropertyTypeConstants::GROUP))
			}
			if ( attR.type.name.equals(WidgetTypeConstants::COMBOBOX) || attR.type.name.equals(WidgetTypeConstants::LIST)) {
			modAttr.setSelection(attR.getPropertyValue(PropertyTypeConstants::SELECTION_TYPE))
			modAttr.setSortBy(attR.getPropertyValue(PropertyTypeConstants::SORTBY_TYPE))
			}
			if ( attR.type.name.equals(WidgetTypeConstants::LIST)) {
			modAttr.setNumberOfItems(attR.getPropertyValue("numberOfItems"))
			}
			modAttributes.add(modAttr)
		}
		return modAttributes.filterNull.sortBy(e| if(e.label != null) { e.label } );
	}
	
	def boolean checkEachColumn(List<ModuleAttribute> modAttrs,int index){
		switch(index){
			case 1: for(ModuleAttribute mA : modAttrs) {
						if(!mA.label.nullOrEmpty)
							return true
					}
			case 2: for(ModuleAttribute mA : modAttrs) {
						if(!mA.domainReference.nullOrEmpty)
							return true
					}		
			case 3: for(ModuleAttribute mA : modAttrs) {
						if(!mA.containedIn.nullOrEmpty)
							return true
					}
			case 4: for(ModuleAttribute mA : modAttrs) {
						if(!mA.moduleAttrTabletype.nullOrEmpty)
							return true
					}
		}
		return false
	}

	def coreAttributes(IOfsProject project,List<Widget> attributeList) '''
	«var List<ModuleAttribute> modAttributes = computeAttributes(project,attributeList)»
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Label</th>
			<th bgcolor="#6495ED">Domain Reference</th>
		«IF modAttributes.checkEachColumn(4)»
			<th bgcolor="#6495ED">Type</th>
		«ENDIF»
			<th bgcolor="#6495ED">Contained In</th>
		</tr>
	«FOR ModuleAttribute modAttr: modAttributes»
		«IF !modAttr.getLabel().nullOrEmpty || !modAttr.getDomainReference().nullOrEmpty || !modAttr.getContainedIn().nullOrEmpty»
		<tr>
			<td>«modAttr.getLabel()»</td>
			<td>«modAttr.getDomainReference()»</td>
			«IF modAttributes.checkEachColumn(4)»<td>«modAttr.moduleAttrTabletype»</td>«ENDIF»
			<td>«modAttr.getContainedIn()»</td>
		</tr>
		«ENDIF»
	«ENDFOR»
	</table>
	«modAttributes.clear»
	'''
	
	def List<Widget> fetchLabelDomains(List<Widget> attributeList){
		var List<Widget> labeldoms = newArrayList
		for(wid : attributeList) {
			if (wid.type.name.equals(WidgetTypeConstants::LABEL_DOMAIN)){
				labeldoms.add(wid)
			}	
		}
		attributeList.removeAll(labeldoms)
		return labeldoms
	}
	
	def String fetchRespLabelDomains (Widget domAttr,List<Widget> labelDoms,IOfsProject project){
		if(domAttr.type.name.equals(WidgetTypeConstants::INCLUDE)) {
				if(domAttr.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model.eResource.URI.path.endsWith("fragment")){
					var domAssociation = domAttr.getPropertyValue(PropertyTypeConstants::DOMAIN_ASSOCIATION)
					if (!domAssociation.nullOrEmpty){
						for(wid : labelDoms) {
							var lblDomAttrName = wid.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)
							if(domAssociation.equalsIgnoreCase(lblDomAttrName)){
								return wid.fetchTranslationValue(project)
							}
						}
						
					}	
				}
		}
		else if(domAttr.type.name.equals(WidgetTypeConstants::TABLE_COLUMN)){
			return domAttr.findProperty("column-name").value
		}
		else if(domAttr.type.name.equals("TableGroup") || domAttr.type.name.equals("TableSort") || domAttr.type.name.equals("TableExtra") ||  domAttr.type.name.equals("TableAggregate") || domAttr.type.name.equals("TableKeepFilter")){
			var tabColm =  domAttr.eContainer as Widget
			if(tabColm!=null && getContainerWidget(domAttr.getPropertyValue("domainAttribute"),tabColm)!=null){
				var Widget parCont = getContainerWidget(domAttr.getPropertyValue("domainAttribute"),tabColm).eContainer as Widget
				return parCont.fetchTranslationValue(project)
			}
		}
		else {
			for(wid : labelDoms) {
				var domAttrName = domAttr.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)
				var lblDomAttrName = wid.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)
				if (lblDomAttrName.equals(domAttrName) ){
					return wid.fetchTranslationValue(project)
				}else if(domAttrName.nullOrEmpty){
					return domAttr.fetchTranslationValue(project)
				}
			}
		}
		
		return ""
	} 
	
	def Widget getContainerWidget(String targetValue, Widget parent){
		for(Widget widget: parent.contents){
			var dtext = widget.getPropertyValue("domainAttribute")
			if(dtext!=null && dtext.equals(targetValue)){
					return widget			
			}else{
				getContainerWidget(targetValue,widget)
			}
		}
		return null
	}
	
	def String fetchTranslationValue  (Widget wid,IOfsProject project) {
		if ( !ModuleUtils::getTitle(project,wid).nullOrEmpty)
			return ModuleUtils::getTitle(project,wid) 
		else if( !ModuleUtils::getTranslation(project,wid).nullOrEmpty)
			return ModuleUtils::getTranslation(project,wid)	
		else
			return ""
	}
	
	def coreXAttributes(IOfsProject project,List<Widget> attributeList) '''
	«var List<ModuleAttribute> modXAttributes = computeXAttributes(project,attributeList)»
	<table border="1">
		<tr>
			<th bgcolor="#6495ED">Label</th>
			<th bgcolor="#6495ED">Domain Reference</th>
			<th bgcolor="#6495ED">Contained In</th>
			<th bgcolor="#6495ED">Description</th>
			<th bgcolor="#6495ED">Translation (en)</th>
			<th bgcolor="#6495ED">Data Type</th>
			<th bgcolor="#6495ED">Type</th>
			<th bgcolor="#6495ED">Display Format</th>
			<th bgcolor="#6495ED">Editable</th>
			<th bgcolor="#6495ED">Enable</th>
			<th bgcolor="#6495ED">Required</th>
			<th bgcolor="#6495ED">Readable</th>
			<th bgcolor="#6495ED">Maximum Characters</th>
			<th bgcolor="#6495ED">Widget Group</th>
			<th bgcolor="#6495ED">Bean Name</th>
			<th bgcolor="#6495ED">Bean Property</th>
			<th bgcolor="#6495ED">CSS Class</th>
			<th bgcolor="#6495ED">Horizontal Allignment</th>
			<th bgcolor="#6495ED">Vertical Alignment</th>
			<th bgcolor="#6495ED">Width</th>
			<th bgcolor="#6495ED">Pattern</th>
			<th bgcolor="#6495ED">Access Key</th>
			<th bgcolor="#6495ED">Tab Index</th>
			<th bgcolor="#6495ED">id</th>
			<th bgcolor="#6495ED">Horizontal Text Position</th>
			<th bgcolor="#6495ED">Vertical Text Position</th>
			<th bgcolor="#6495ED">Wrapped</th>
			<th bgcolor="#6495ED">Icon</th>
			<th bgcolor="#6495ED">Height (in characters)</th>
			<th bgcolor="#6495ED">Rich Text</th>
			<th bgcolor="#6495ED">Search Type</th>
			<th bgcolor="#6495ED">Auto-complete design module</th>
			<th bgcolor="#6495ED">Auto-complete delay</th>
			<th bgcolor="#6495ED">Auto-complete number of characters</th>
			<th bgcolor="#6495ED">Null Value</th>
			<th bgcolor="#6495ED">Free Date</th>
			<th bgcolor="#6495ED">Display Label</th>
			<th bgcolor="#6495ED">Selected</th>
			<th bgcolor="#6495ED">Group</th>
			<th bgcolor="#6495ED">Selection</th>
			<th bgcolor="#6495ED">Sort By</th>
			<th bgcolor="#6495ED">Number of items</th>
		</tr>
	«FOR  ModuleAttribute modAttr: modXAttributes»
		<tr>
			<td>«modAttr.getLabel()»</td>
			<td>«modAttr.getDomainReference()»</td>
			<td>«modAttr.containedIn»</td>
			<td>«modAttr.description»</td>
			<td>«modAttr.translation»</td>
			<td>«modAttr.dataType»</td>
			<td>«modAttr.type»</td>
			<td>«modAttr.displayFormat»</td>
			<td>«modAttr.editable»</td>
			<td>«modAttr.enable»</td>
			<td>«modAttr.required»</td>
			<td>«modAttr.readable»</td>
			<td>«modAttr.maximumCharacters»</td>
			<td>«modAttr.widgetGroup»</td>
			<td>«modAttr.beanName»</td>
			<td>«modAttr.beanProperty»</td>
			<td>«modAttr.cssClass»</td>
			<td>«modAttr.horizontalAlignment»</td>
			<td>«modAttr.verticalAlignment»</td>
			<td>«modAttr.width»</td>
			<td>«modAttr.pattern»</td>
			<td>«modAttr.accessKey»</td>
			<td>«modAttr.tabIndex»</td>
			<td>«modAttr.id»</td>
			<td>«modAttr.horizontalTextPosition»</td>
			<td>«modAttr.verticalTextPosition»</td>
			<td>«modAttr.wrapped»</td>
			<td>«modAttr.icon»</td>
			<td>«modAttr.height»</td>
			<td>«modAttr.richText»</td>
			<td>«modAttr.searchType»</td>
			<td>«modAttr.autoCompleteDesignModule»</td>
			<td>«modAttr.autoCompleteDelay»</td>
			<td>«modAttr.autoCompleteNumberOfChars»</td>
			<td>«modAttr.nullValue»</td>
			<td>«modAttr.freeDate»</td>
			<td>«modAttr.displayLabel»</td>
			<td>«modAttr.selected»</td>
			<td>«modAttr.group»</td>
			<td>«modAttr.selection»</td>
			<td>«modAttr.sortBy»</td>
			<td>«modAttr.numberOfItems»</td>
		</tr>
	«ENDFOR»
	</table>
	'''
	
	def String fetchRepAttributeContainer(Widget widget,List<Widget> widgets) {
		var List<String> value = newArrayList()
		for(Widget wid: widgets){
			var String curentD = wid.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)
			var String reDoAt = widget.getPropertyValue(PropertyTypeConstants::DOMAIN_ATTRIBUTE)
			if(!curentD.nullOrEmpty &&  !reDoAt.nullOrEmpty && curentD.equals(reDoAt)) {
				value.add(wid.fetchAttributeContainer)
			}
		}
		return value.toString.substring(1,(value.toString.length-1))
	}
	
	def String fetchAttributeContainer(Widget widget) {
		if (widget.parent.type.name.equals(WidgetTypeConstants::TAB)){
			if(widget.model != null) {
				return "TAB:" + widget.parent.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)
			}
		}
		else if (widget.parent.type.name.equals(WidgetTypeConstants::CONDITIONAL_BODY) && widget.parent.parent.parent.type.name.equals(WidgetTypeConstants::TAB)){
			return "TAB:" + widget.parent.parent.parent.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME) + "->Condition:"+ widget.parent.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)
		}	
		else if (widget.parent.type.name.equals(WidgetTypeConstants::CONDITIONAL_BODY) && !widget.parent.parent.parent.type.name.equals(WidgetTypeConstants::TAB)){
			return "Condition:"+ widget.parent.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)
		}
		else if ((widget.parent.type.name.equals(WidgetTypeConstants::GRID) ||  widget.parent.type.name.equals(WidgetTypeConstants::BOX) || widget.parent.type.name.equals(WidgetTypeConstants::TABLE)) && widget.parent.parent.type.name.equals(WidgetTypeConstants::MODULE)){
			return "Module:"+ widget.parent.parent.getPropertyValue(PropertyTypeConstants::DOMAIN_ENTITY)
		}
		else if ((widget.parent.type.name.equals(WidgetTypeConstants::GRID) ||  widget.parent.type.name.equals(WidgetTypeConstants::BOX) || widget.parent.type.name.equals(WidgetTypeConstants::TABLE)) && widget.parent.parent.type.name.equals(WidgetTypeConstants::FRAGMENT)){
			return "Fragment:"+ widget.parent.parent.getPropertyValue(PropertyTypeConstants::DOMAIN_ENTITY)
		}
		else {
			fetchAttributeContainer(widget.parent)
		}
	}
	
	def String fetchModuleInformation(Widget widget, String widgetType, String propertyType) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null) {
				return widget.getPropertyValue(propertyType)
			}
		}	
	}
	

	def tabCondContainer(Widget widget,IOfsProject project,String containmentValue) '''
		«var List<Widget> tabedCondPanes = newArrayList»
		«var tabbedPanesList = widget.fetchTabCond(tabedCondPanes)»
		«IF !tabbedPanesList.nullOrEmpty»
		«htmlOpening()»
		List of containers:<br>
		«FOR Widget tabPane: tabbedPanesList»
			«IF tabPane.type.name.equals(WidgetTypeConstants::TABBED_PANE)»
				«printForTab(tabPane,project,containmentValue)»
			«ELSEIF tabPane.type.name.equals(WidgetTypeConstants::CONDITIONAL)»
				«printForConditional(tabPane,project,containmentValue)»
			«ENDIF»
		«ENDFOR»
		«tabedCondPanes.clear»
		«htmlClosing()»
		«ENDIF»
	'''
	
	def printForTab(Widget tabPane,IOfsProject project,String containmentValue)'''
		<ul>
	<li>
	Tabbed Pane:«IF tabPane.getPropertyValue("name").nullOrEmpty»«tabPane.getPropertyValue("name")»,«ENDIF»	
		«IF tabPane.getPropertyValue("tabs-filtered-attribute").nullOrEmpty»
			static«IF containmentValue!=null && containmentValue.equalsIgnoreCase("Container")»
			 , with page reload on selection
			 «ELSEIF containmentValue!=null && containmentValue.equalsIgnoreCase("Stand-alone")»
			 ,with no page reload on selection«ELSE». «ENDIF» 
		«ELSE»
			dynamic, filtered on attribute: «tabPane.getPropertyValue("tabs-filtered-attribute")», «IF containmentValue!=null && containmentValue.equalsIgnoreCase("Container")»
			 with page reload on selection
			 «ELSEIF containmentValue!=null && containmentValue.equalsIgnoreCase("Stand-alone")»
			 with no page reload on selection«ELSE» «ENDIF»
		«ENDIF»
		«IF !tabPane.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).nullOrEmpty»description «tabPane.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»«ENDIF»
		«var List<Widget> tabs = newArrayList»
		«var tabInTabedPane = tabPane.fetchTabs(WidgetTypeConstants::TAB,tabs)»
		«FOR Widget taba: tabInTabedPane»
		<ul>
			<li> Tab: «IF !ModuleUtils::getTitle(project,taba).nullOrEmpty»«ModuleUtils::getTitle(project,taba)».«ENDIF»«IF !tabPane.getPropertyValue("tabs-filtered-attribute").nullOrEmpty» Static/filtered on value.«ENDIF» <br>
			«IF !taba.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).nullOrEmpty»description «taba.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»«ENDIF» </li>
			«var List<Widget> tabedCondPanes = newArrayList»
			«var tabCondMenu = taba.fetchTabCond(tabedCondPanes)»
			«FOR Widget tabedCondtnlPane: tabCondMenu»
				«IF tabedCondtnlPane.type.name.equals(WidgetTypeConstants::TABBED_PANE)»
				<p>	«printForTab(tabedCondtnlPane,project,containmentValue)» </p>
				«ELSEIF tabedCondtnlPane.type.name.equals(WidgetTypeConstants::CONDITIONAL)»
				<p>	«printForConditional(tabedCondtnlPane,project,containmentValue)» </p>
				«ENDIF»
			«ENDFOR»
			«tabedCondPanes.clear»
		</ul>
			«FOR Widget tabedPane :  taba.collectNestedModuleConds(WidgetTypeConstants::TABBED_PANE)»
				<p>	«printForTab(tabedPane,project,containmentValue)» </p>
			«ENDFOR»
		«ENDFOR»
		«tabs.clear»
		</li>
			</ul>
	'''
	
	def printForConditional(Widget tabPane,IOfsProject project,String containmentValue)'''
		«var List<Widget> conditions = newArrayList»
		«var condtn = tabPane.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conditions)»
			<ul>
		<li>Conditional: «tabPane.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
		«FOR Widget cndtnl: condtn»
			«moduleConditions(cndtnl,project,containmentValue)»
			«var List<Widget> conds = newArrayList()»
			«FOR Widget cndtnlWidget :  cndtnl.collectNestedModuleConds(WidgetTypeConstants::CONDITIONAL)»
				«var List<Widget> condts = cndtnlWidget.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conds)»
				<ul>
				<li>Conditional: «cndtnlWidget.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
				<ul>
				«FOR Widget subCndtn :  condts»
  					<li>Condition: «subCndtn.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)» 
  						«IF subCndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)!=null && !subCndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).empty »
  					<p>Description: «subCndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</p>
  						«ENDIF»
					</li>			
  				«ENDFOR»
  				«conds.clear»
  				</ul>	
  				</li>		
  				</ul>		
			«ENDFOR»
		«ENDFOR»
		«conditions.clear»
		</li>
		</ul>
	'''
	
	
	def printForXConditional(Widget tabPane,IOfsProject project,String containmentValue)'''
		«var List<Widget> conditions = newArrayList»
		«var condtn = tabPane.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conditions)»
			<ul>
		<li>Conditional: «tabPane.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
		«FOR Widget cndtnl: condtn»
			«moduleXConditions(cndtnl,project,containmentValue)»
			«var List<Widget> conds = newArrayList()»
			«FOR Widget cndtnlWidget :  cndtnl.collectNestedModuleConds(WidgetTypeConstants::CONDITIONAL)»
				«var List<Widget> condts = cndtnlWidget.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conds)»
				<ul>
				<li>Conditional: «cndtnlWidget.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
				<ul>
				«FOR Widget subCndtn :  condts»
					<li>Condition: «subCndtn.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
  						«IF subCndtn.getPropertyValue(PropertyTypeConstants::JAVA_CODE)!=null»<p>Code: «subCndtn.getPropertyValue(PropertyTypeConstants::JAVA_CODE)»</p>«ENDIF» 
  						«IF subCndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)!=null && !subCndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).empty »
					<p>Description: «subCndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</p>
  						«ENDIF»
					</li>			
  				«ENDFOR»
  				</ul>	
  				</li>		
  				</ul>		
			«ENDFOR»
		«ENDFOR»
		«conditions.clear»
		</li>
			</ul>
	'''
	
	def List<Widget> collectNestedModuleConds(Widget condition,String widgetType){
		var List<Widget> condtnls = newArrayList
		for (Widget widget : condition.contents) {
			if(widget.type.name.equals(WidgetTypeConstants::INCLUDE)) {
				var mod = widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
				if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && mod.eResource.URI.path.endsWith("fragment")){
					mod.widget.fetchNestedConditionals(widgetType,condtnls)	
				}
			}	
		}
		if(condtnls.empty){
				condition.fetchNestedConditionals(widgetType,condtnls)
		}
		return condtnls
	}
	
	def void fetchNestedConditionals(Widget widget,String widgetType,List<Widget> condtnls) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null && !condtnls.contains(widget)) {
				condtnls.add(widget)
			}
		}
		else if(widget.type.name.equals(WidgetTypeConstants::INCLUDE)) {
			var mod = widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
			if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && mod.eResource.URI.path.endsWith("fragment")){
				mod.widget.fetchNestedConditionals(widgetType,condtnls)	
			}
		}	
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchNestedConditionals(widgetType, condtnls)
			}
		}
	}
	
	def tabCondXContainer(Widget widget,IOfsProject project,String containmentValue) '''
	«var List<Widget> tabedCondPanes = newArrayList»
	«var tabbedPanesList = widget.fetchTabCond(tabedCondPanes)»
	«IF !tabbedPanesList.nullOrEmpty»
	«htmlOpening()»
	List of containers:<br>
	«FOR Widget tabPane: tabbedPanesList»
		«IF tabPane.type.name.equals(WidgetTypeConstants::TABBED_PANE)»
			«printForTab(tabPane,project,containmentValue)»
		«ELSEIF tabPane.type.name.equals(WidgetTypeConstants::CONDITIONAL)»
			«printForXConditional(tabPane,project,containmentValue)»
		«ENDIF»
	«ENDFOR»
	«tabedCondPanes.clear»
	«htmlClosing()»
	«ENDIF»
	'''
	
	def Widget fetchAttributeFromInclModel(Widget widget) {
		if (widget.type.name.equals(WidgetTypeConstants::ATTRIBUTE) || widget.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE)!=null ){
			if(widget.model != null ) {
				return widget
			}
		}
		else if(widget.type.name.equals(WidgetTypeConstants::INCLUDE)) {
			if(widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model.eResource.URI.path.endsWith("fragment")){
				var prop = widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE)
				prop.model.widget.fetchAttributeFromInclModel()
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				return wid.fetchAttributeFromInclModel
			}
		}
	}
	
	def void fetchAllAttributes(Widget widget,List<Widget> allAttributes) {
		if (widget.type.name.equals(WidgetTypeConstants::ATTRIBUTE) || widget.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE)!=null || widget.type.name.equals(WidgetTypeConstants::INCLUDE) || widget.type.name.equals("TableGroup")  || widget.type.name.equals("TableSort") || widget.type.name.equals("TableAggregate") || widget.type.name.equals("TableKeepFilter")){
			for(wid : widget.parent.contents){
				if (wid.type.name.equals(WidgetTypeConstants::ATTRIBUTE) || wid.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE)!=null || widget.type.name.equals("TableGroup") || widget.type.name.equals("TableSort") || widget.type.name.equals("TableAggregate") || widget.type.name.equals("TableKeepFilter")){
					if(wid.model != null && !allAttributes.contains(wid)) {
						allAttributes.add(wid)
					}
				}
				else if(wid.type.name.equals(WidgetTypeConstants::INCLUDE)) {
					var mod = wid.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
					if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && mod.eResource.URI.path.endsWith("fragment")){
						if(!wid.findProperty(PropertyTypeConstants::DOMAIN_ASSOCIATION).value.nullOrEmpty)
						{
							allAttributes.add(wid)	
						}	
						else
						{
							mod.widget.fetchAllAttributes(allAttributes)
						}
					}
				}
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchAllAttributes(allAttributes)
			}
		}
	}
	
	def fetchAllXAttributes(Widget widget,List<Widget> allAttributes) {
		if (widget.checkAttrGrpWidget || widget.type.name.equals(WidgetTypeConstants::INCLUDE)){
			for(wid : widget.parent.contents){
				if (wid.checkAttrGrpWidget){
					if(wid.model != null && !allAttributes.contains(wid)) {
						allAttributes.add(wid)
					}
				}
				else if(wid.type.name.equals(WidgetTypeConstants::INCLUDE)) {
					var mod = wid.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
					if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && mod.eResource.URI.path.endsWith("fragment")){
						if(!wid.findProperty(PropertyTypeConstants::DOMAIN_ASSOCIATION).value.nullOrEmpty)
						{
							allAttributes.add(wid)	
						}	
						else
						{
							mod.widget.fetchAllXAttributes(allAttributes)
						}
					}
				}
			}
			return allAttributes
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchAllXAttributes(allAttributes)
			}
		}
		return allAttributes
	}
	
	def fetchActionWidgets(Widget widget,List<Widget> actionWidgets) {
		if (widget.checkActionWidgets){
			for(wid : widget.parent.contents){
				if (wid.checkActionWidgets){
					if(wid.model != null && !actionWidgets.contains(wid)) {
						actionWidgets.add(wid)
					}
					if(wid.type.name.equals(WidgetTypeConstants::TAB)){
						for(widw : widget.contents){
							widw.fetchActionWidgets(actionWidgets)
						}
					}
				}
			}
			return actionWidgets
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchActionWidgets(actionWidgets)
			}
		}
		return actionWidgets
	}
	
	def fetchActionEvents(Widget widget,List<Event> actionEve){
		 var List<Widget> actionWidgets = newArrayList
		 widget.fetchActionWidgets(actionWidgets)
		 for (Widget actWid: actionWidgets){	
			for (Event eve: actWid.events) {
				actionEve.add(eve)
			}
		}
	}
	
	def checkAttrGrpWidget(Widget widget) {
		if (widget.type.name.equals(WidgetTypeConstants::ATTRIBUTE) || widget.type.name.equals(WidgetTypeConstants::LABEL) || widget.type.name.equals(WidgetTypeConstants::ICON) ||
			widget.type.name.equals(WidgetTypeConstants::CHECKBOX) || widget.type.name.equals( WidgetTypeConstants::RADIO_BUTTON) || widget.type.name.equals(WidgetTypeConstants::TEXTFIELD) ||
			widget.type.name.equals(WidgetTypeConstants::TEXTAREA) || widget.type.name.equals(WidgetTypeConstants::SEARCH_FIELD)  || widget.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE)!=null)
			{
				return true;
			}
			return false;
	}
	
	def checkActionWidgets(Widget widget) {
		if (widget.type.name.equals(WidgetTypeConstants::BUTTON) || widget.type.name.equals(WidgetTypeConstants::LABEL_DOMAIN) || widget.type.name.equals(WidgetTypeConstants::TABLE_COLUMN_ITEM) ||
			widget.type.name.equals("TableGroup") || widget.type.name.equals(WidgetTypeConstants::TAB) || widget.type.name.equals(WidgetTypeConstants::MATRIX_CONTENTCELLITEM) || widget.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) || widget.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) ||
			widget.type.name.equals(WidgetTypeConstants::MATRIX_EXTRACOLUMNITEM) || widget.type.name.equals(WidgetTypeConstants::ICON) || widget.type.name.equals(WidgetTypeConstants::SEARCH_FIELD)){
				return true
		}
		return false		
	}
	
	def List<Widget> fetchTabCond(Widget widget,List<Widget> tabedCondPanes) {
		if (widget.type.name.equals(WidgetTypeConstants::TABBED_PANE) || widget.type.name.equals(WidgetTypeConstants::CONDITIONAL)){
			for(wid : widget.parent.contents){
				if (wid.type.name.equals(WidgetTypeConstants::TABBED_PANE) || wid.type.name.equals(WidgetTypeConstants::CONDITIONAL)){
					if(wid.model != null && !tabedCondPanes.contains(wid)) {
						tabedCondPanes.add(wid)
					}
				}
			}
			return tabedCondPanes
		}
		else if(widget.type.name.equals(WidgetTypeConstants::INCLUDE)) {
			var mod = widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
			if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && mod.eResource.URI.path.endsWith("fragment")){
				mod.widget.fetchTabCond(tabedCondPanes)	
			}
		}	
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchTabCond(tabedCondPanes)
			}
		}
		return tabedCondPanes
	}
	
	def List<Widget> fetchTabs(Widget widget,String widgetType,List<Widget> tabs) {
		if (widget.type.name.equals(widgetType)){
			for(wid : widget.parent.contents){
				if (wid.type.name.equals(widgetType)){
					if(wid.model != null && wid.getPropertyValue(PropertyTypeConstants::ENABLED).equalsIgnoreCase("True") && !tabs.contains(wid)) {
						tabs.add(wid)
					}
				}
			}
			return tabs
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchTabs(widgetType,tabs)
			}
		}
		return tabs
	}
	
	def List<Widget> fetchConditions(Widget widget,String widgetType,List<Widget> conditions) {
		if (widget.type.name.equals(widgetType)){
			for(wid : widget.parent.contents){
				if (wid.type.name.equals(widgetType)){
					if(wid.model != null && !conditions.contains(wid)) {
						conditions.add(wid)
					}
				}
			}
			return conditions
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchConditions(widgetType,conditions)
			}
		}
		return conditions
	}
		
	def moduleConditions(Widget cndtnl, IOfsProject project,String containmentValue) '''
		<ul>
	<li>Condition: «cndtnl.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)» 
  			«IF !cndtnl.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).nullOrEmpty »
  			<p> Description: «cndtnl.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).replaceAll("(\r\n|\r|\n|\n\r)", "<br>")» </p>
  			«ENDIF»
  			«var List<Widget> tabedCondPanes = newArrayList»
  			«var tabCondMenu = cndtnl.fetchTabCond(tabedCondPanes)»
			«FOR Widget tabedCondtnlPane: tabCondMenu»
				«IF tabedCondtnlPane.type.name.equals(WidgetTypeConstants::TABBED_PANE)»
				<p>	«printForTab(tabedCondtnlPane,project,containmentValue)» </p>
				«ELSEIF tabedCondtnlPane.type.name.equals(WidgetTypeConstants::CONDITIONAL)»
				<p>	«printForConditional(tabedCondtnlPane,project,containmentValue)» </p>
				«ENDIF»
			«ENDFOR»
			«tabedCondPanes.clear»
	</li>
		</ul>
	'''
	
	def moduleXConditions(Widget cndtnl, IOfsProject project,String containmentValue) '''
		<ul>
	<li>Condition: «cndtnl.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)» <br>
  			«IF !cndtnl.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).nullOrEmpty »
	<p>Description: «cndtnl.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»</p>
  			«ENDIF»<br>
	Code: «cndtnl.getPropertyValue(PropertyTypeConstants::JAVA_CODE)»
			«var List<Widget> tabedCondPanes = newArrayList»
  			«var tabCondMenu = cndtnl.fetchTabCond(tabedCondPanes)»
			«FOR Widget tabedCondtnlPane: tabCondMenu»
				«IF tabedCondtnlPane.type.name.equals(WidgetTypeConstants::TABBED_PANE)»
				<p>	«printForTab(tabedCondtnlPane,project,containmentValue)» </p>
				«ELSEIF tabedCondtnlPane.type.name.equals(WidgetTypeConstants::CONDITIONAL)»
				<p>	«printForConditional(tabedCondtnlPane,project,containmentValue)» </p>
				«ENDIF»
			«ENDFOR»
			«tabedCondPanes.clear»
			</li>
		</ul>
	'''
	
	def moduleIndex(String path , List<String> fileList)'''
	«htmlOpening()»
	<ul>
		«FOR String moduleFile : fileList»
		<li>
			<A HREF="«path + "\\"»«moduleFile»">«GenerationUtils::getTitleForFileName(moduleFile)»</A>
		<br></br>
		</li>
		«ENDFOR»
	</ul>
	«htmlClosing»
	'''
	
	def void fetchAllWidgets(Widget widget,List<Widget> allWidgets) {
		if (widget!=null && isValidWidget(widget) ){
			allWidgets.add(widget)
			for(wid : widget.contents){
				wid.fetchAllWidgets(allWidgets)
			}
		}
		else if(widget.type.name.equals(WidgetTypeConstants::INCLUDE)) {
			var mod = widget.findProperty(PropertyTypeConstants::INCLUDE_SOURCE).model
			if(mod!=null && mod.eResource!=null && mod.eResource.URI!=null && mod.eResource.URI.path.endsWith("fragment")){
				mod.widget.fetchAllWidgets(allWidgets)	
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchAllWidgets(allWidgets)
			}
		}
	}
	
	def isValidWidget(Widget widget){
		if(widget.type.name.equals(WidgetTypeConstants::BUTTON) || widget.type.name.equals(WidgetTypeConstants::BOX) || widget.type.name.equals(WidgetTypeConstants::TABLE_COLUMN) 
			|| widget.type.name.equals(WidgetTypeConstants::TABLE_COLUMN_ITEM) || widget.isDomainAttrWidget
			|| widget.type.name.equals("TableGroup") || widget.type.name.equals(WidgetTypeConstants::TAB) || widget.type.name.equals(WidgetTypeConstants::MATRIX_CELL) 
			|| widget.type.name.equals(WidgetTypeConstants::MATRIX_AXIS) || widget.type.name.equals(WidgetTypeConstants::MATRIX_CELLITEM) || widget.type.name.equals(WidgetTypeConstants::MATRIX_EXTRACOLUMNITEM) 
			|| widget.type.name.equals(WidgetTypeConstants::MATRIX_CONTENTCELLITEM) || widget.type.name.equals(WidgetTypeConstants::ICON) || widget.type.name.equals(WidgetTypeConstants::MODULE) ){
				return true
			}	
			return false
	}
	
 	def isDomainAttrWidget(Widget widget){
		if(widget.type.name.equals(WidgetTypeConstants::BUTTON) || widget.type.name.equals("AbstractButton")  || widget.type.name.equals(WidgetTypeConstants::CHECKBOX) || widget.type.name.equals(WidgetTypeConstants::TAB) 
			|| widget.type.name.equals(WidgetTypeConstants::COLUMN) || widget.type.name.equals(WidgetTypeConstants::COLUMN_HEADER) || widget.type.name.equals(WidgetTypeConstants::HIDDEN_FIELD) 
			|| widget.type.name.equals(WidgetTypeConstants::LABEL) || widget.type.name.equals(WidgetTypeConstants::LABEL_DOMAIN) || widget.type.name.equals(WidgetTypeConstants::PAGE) 
			|| widget.type.name.equals(WidgetTypeConstants::RADIO_BUTTON) || widget.type.name.equals(WidgetTypeConstants::TABLE_COLUMN) || widget.type.name.equals("TableExtra") ){
				return true
			}	
			return false
	}
	
	def String getTransValue(Widget widget) {
		switch (widget.type.translationSupport.name()) {
			  case "NAME" : "Label"
			 case "TEXT" : "ToolTip"
//			 case "NAME_AND_TEXT" : "Label/Tooltip"
		}
	}
	
}	
