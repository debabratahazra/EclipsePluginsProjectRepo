package com.odcgroup.page.docgen.cartridges.mapper

import com.odcgroup.page.docgen.utils.GenerationUtils
import com.odcgroup.page.metamodel.PropertyTypeConstants
import com.odcgroup.page.metamodel.WidgetTypeConstants
import com.odcgroup.page.model.Model
import com.odcgroup.page.model.Widget
import java.util.List

class PageModel2PageDocumentation {
	
	def htmlOpening ()'''
	<!DOCTYPE html>
	<html lang="en">		
	<body>
	''' 
		
	def htmlClosing ()'''
	</body>
	</html>
	''' 
		
	def pageDescription (Model model)'''
	«htmlOpening()»
	«var docText = model.widget.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
	«IF docText!=null && !docText.nullOrEmpty»
	Description: «docText» <br>
	«ENDIF»  <br>
	Model Path: <I>«model.eResource.URI.path.substring(0, model.eResource.URI.path.length - 5)»</I>
	«htmlClosing()»
	''' 
	
	def pageIncludes (Model model) '''
	«var List<Widget> includeSources = newArrayList»	
	«model.widget.fetchIncludeSources(WidgetTypeConstants::INCLUDE,includeSources)»
	«var List<Widget> xspInclPaths = newArrayList»	
	«model.widget.fetchIncludeSources("IncludeXSP",xspInclPaths)»
	«var List<Widget> extPageOrImgUrls = newArrayList»	
	«model.widget.fetchIncludeSources(WidgetTypeConstants::EXTERNAL_INCLUDE_WIDGET,extPageOrImgUrls)»
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
			<td>Module: «widSrc.substring(0,widSrc.lastIndexOf("."))»</td>
			<td>«fetchParent(inclWid.parent)»</td>
			<td>«inclWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»		
		«FOR Widget xspInclude : xspInclPaths»
		<tr>			
			<td>XSP Page: «xspInclude.getPropertyValue("xsp-path")»</td>
			<td>«fetchParent(xspInclude.parent)»</td>
			<td>«xspInclude.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»
		«FOR Widget extOrImgUrl : extPageOrImgUrls»
		<tr>			
			<td>External Page/Image: «extOrImgUrl.getPropertyValue(PropertyTypeConstants::URL)»</td>
			<td>«fetchParent(extOrImgUrl.parent)»</td>
			<td>«extOrImgUrl.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</td>
		</tr>
		«ENDFOR»
	</table>
	«htmlClosing()»
	«ENDIF»
	'''
	
	def void fetchIncludeSources(Widget widget,String widgetType,List<Widget> includeSources) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null) {
				includeSources.add(widget)
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchIncludeSources(widgetType,includeSources)
			}
		}		
	}
	

	
	def String fetchParent(Widget widget) {
		if(widget!=null){
			if(widget.typeName.equals(WidgetTypeConstants::CONDITIONAL_BODY)) {
				var String cond = widget.getPropertyValue("technicalName")
				var String conditional = widget.parent.getPropertyValue("technicalName")
				if(!conditional.nullOrEmpty || !cond.nullOrEmpty){
					return "Condition: "+(if (!conditional.nullOrEmpty)conditional else "") + (if(!conditional.nullOrEmpty && !cond.nullOrEmpty){"."}else{""}) +  (if (!cond.nullOrEmpty)cond else "")		
				}	
				else 
				return ""			
			}
			else if(widget.typeName.equals(WidgetTypeConstants::TAB)) {
				var String cond = widget.parent.getPropertyValue("tab-name")
				if (!cond.nullOrEmpty)
				return "Tab: "+ cond 
				else 
				return ""	
			}else{
				 fetchParent(widget.parent)
			}
		}else{
			return ""
		}
	}
	
	def List<Widget> collectNestedModuleConds(Widget condition){
		var List<Widget> condtnls = newArrayList
		if(condtnls.empty){
				condition.fetchNestedConditionals(WidgetTypeConstants::CONDITIONAL,condtnls)
		}
		return condtnls
	}
	
	def pageContainers(Model model,boolean isLarge) '''
		«var List<Widget> conditionals = newArrayList»
		«model.widget.fetchConditionals(WidgetTypeConstants::CONDITIONAL,conditionals)»
		«IF !(conditionals.empty)»
		«htmlOpening()»
		List of containers:<br>
		«var List<Widget> conditions = newArrayList»
			«FOR Widget cndtnWidget :  conditionals»
				«cndtnWidget.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conditions)»
				«IF isLarge»
				«pageXConditions(cndtnWidget.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME),conditions)»
				«ELSE»
				«pageConditions(cndtnWidget.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME),conditions)»
				«ENDIF»
				«conditions.clear»
			«ENDFOR»
		«htmlClosing()»
		
		«ENDIF»
		«conditionals.clear»
	'''
	
	def void fetchConditionals(Widget widget,String widgetType,List<Widget> conditionals) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null && !conditionals.contains(widget)) {
				conditionals.add(widget)
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchConditionals(widgetType, conditionals)
			}
		}
	}
	
	def void fetchNestedConditionals(Widget widget,String widgetType,List<Widget> condtnls) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null && !condtnls.contains(widget)) {
				condtnls.add(widget)
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchNestedConditionals(widgetType, condtnls)
			}
		}
	}
	
	def void fetchConditions(Widget widget,String widgetType,List<Widget> conditions) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null && !conditions.contains(widget)) {
				conditions.add(widget)
			}
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchConditions(widgetType,conditions)
			}
		}
	}
		
	def pageConditions(String conditonalName, List<Widget> conditions) '''
	Conditional: «conditonalName»
		<ul>
  			«FOR Widget cndtn :  conditions»
  			<li>Condition: «cndtn.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)» 
  				«IF cndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)!=null && !cndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).empty »
  			<p>Description: «cndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</p>
  				«ENDIF»
				«FOR Widget cndtnlWidget :  cndtn.collectNestedModuleConds»
				«var List<Widget> conds = newArrayList()»
				«cndtnlWidget.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conds)»
				<ul>
				<li> Conditional: «cndtnlWidget.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
				<ul>
  					«FOR Widget subCndtn :  conds»
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
  			</li>	
  			«ENDFOR»
		</ul>
	'''
	
	def pageXConditions(String conditonalName, List<Widget> conditions) '''
	Conditional: «conditonalName»
		<ul>
  			«FOR Widget cndtn :  conditions»
  			<li>Condition: «cndtn.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)» 
  			«IF cndtn.getPropertyValue(PropertyTypeConstants::JAVA_CODE)!=null»<p>Code: «cndtn.getPropertyValue(PropertyTypeConstants::JAVA_CODE)»</p>«ENDIF»
  			«IF cndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)!=null && !cndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).empty »
  			<p>Description: «cndtn.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»</p>
  			«ENDIF»
  				«var List<Widget> conds = newArrayList()»
				«FOR Widget cndtnlWidget :  cndtn.collectNestedModuleConds»
				«cndtnlWidget.fetchConditions(WidgetTypeConstants::CONDITIONAL_BODY,conds)»
				<ul>
				<li> Conditional: «cndtnlWidget.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
				<ul>
  					«FOR Widget subCndtn :  conds»
  					<li>Condition: «subCndtn.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)» 
  						«IF subCndtn.getPropertyValue(PropertyTypeConstants::JAVA_CODE)!=null»<p>Code: «subCndtn.getPropertyValue(PropertyTypeConstants::JAVA_CODE)»</p>«ENDIF»
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
  			</li>
  			«ENDFOR»
		</ul>
	'''
	
	def pageIndex(String path , List<String> fileList)'''
	«htmlOpening()»
	<ul>
		«FOR String pageFile : fileList»
		<li>
		    <A HREF="«path + "\\"»«pageFile»">«GenerationUtils::getTitleForFileName(pageFile)»</A>
	    <br></br>
		</li>
		«ENDFOR»
	</ul>
	«htmlClosing»
	'''
}