package com.odcgroup.page.docgen.cartridges.mapper

import com.odcgroup.page.docgen.utils.GenerationUtils
import com.odcgroup.page.docgen.utils.ModuleUtils
import com.odcgroup.page.metamodel.PropertyTypeConstants
import com.odcgroup.page.metamodel.WidgetTypeConstants
import com.odcgroup.page.model.Model
import com.odcgroup.page.model.Widget
import com.odcgroup.workbench.core.IOfsProject
import java.util.List
import org.apache.commons.lang.WordUtils

class TableMatrixDocumentation {
	
	def htmlOpening ()'''
	<!DOCTYPE html>
	<html lang="en">		
	<body>
	''' 
		
	def htmlClosing ()'''
	</body>
	</html>
	''' 
	
	def tableDescription (Model model,IOfsProject project)'''
	«generalDescription(model,project,WidgetTypeConstants::TABLE_TREE)»
	'''
	
	def matrixDescription (Model model,IOfsProject project)'''
	«var List<Widget> matrixWidgets = newArrayList»
	«var List<Widget> matrixList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX,matrixWidgets)»
	«IF !matrixList.nullOrEmpty»
	«htmlOpening()»
	«FOR Widget matrxWid : matrixList»
			«IF matrxWid.parent.type.name.equals(WidgetTypeConstants::CONDITIONAL_BODY) || matrxWid.parent.type.name.equals(WidgetTypeConstants::TAB)»
			This matrix belongs to the «matrxWid.parent.type.name».<br>
		«ELSEIF  matrxWid.rootWidget.type.name.equals(WidgetTypeConstants::MODULE) || matrxWid.rootWidget.type.name.equals(WidgetTypeConstants::PAGE)»
			This matrix belongs to the «matrxWid.rootWidget.type.name».<br>
			«ENDIF»
			«IF matrxWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION) !=null»
				«var docText = matrxWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
					«IF docText!=null && !docText.nullOrEmpty»
					Description: «docText» <br>
					«ENDIF»  
			«ENDIF»
		List of properties set on the matrix: <br>
		<table border="1">
			<tr>
				<th bgcolor="#6495ED">Property Name</th>
				<th bgcolor="#6495ED">Value</th>
			</tr>
			<tr>
				<td>Dataset reference </td>
				<td>«ModuleUtils::getDatasetName(project,matrxWid)»</td>
			</tr>
			<tr>
				<td>Based on Class </td>
				<td>«ModuleUtils::getDatasetBaseClassName(project,matrxWid)»</td>
			</tr>
		</table>	
	«ENDFOR»
	«htmlClosing()»
	«ENDIF»
	'''
	
	def matrixAxis (Model model,IOfsProject project)'''
	«var List<Widget> matrixAxisWidgets = newArrayList»
	«var List<Widget> matrixAxisList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX_AXIS,matrixAxisWidgets)»
	«IF !matrixAxisList.empty»
	«htmlOpening()»
	Matrix axis: <br><br>
	
		<table border="1">
			<tr>
				<th bgcolor="#6495ED">Axis</th>
				<th bgcolor="#6495ED">Attribute</th>
				<th bgcolor="#6495ED">Sorting</th>
				<th bgcolor="#6495ED">Max Number Of Cells</th>
				<th bgcolor="#6495ED">Description</th>
			</tr>
			«FOR Widget matrxWid : matrixAxisList»
			«var docText = matrxWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION).replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
			<tr>
				<td>«IF matrxWid.getPropertyValue("matrixAxis").equals("x-axis")»X-axis«ELSEIF matrxWid.getPropertyValue("matrixAxis").equals("y-axis")»Y-axis«ENDIF»</td>
				<td>«matrxWid.getPropertyValue("domainAttribute")»</td>
				<td>«matrxWid.getPropertyValue("group-sorting-column-name")» («matrxWid.getPropertyValue("group-sorting-direction")»)</td>
				«IF matrxWid.getPropertyValue("matrixAxis").equals("x-axis")»
				<td>«matrxWid.getPropertyValue("maxRows")»</td>
				«ELSEIF matrxWid.getPropertyValue("matrixAxis").equals("y-axis")»
				<td>«matrxWid.getPropertyValue("maxColumns")»</td>
				«ENDIF»		
				<td>«IF docText!=null && !docText.nullOrEmpty»
				Description: «docText» 
				«ENDIF»</td>
			</tr>
			«ENDFOR»
		</table>	
	«htmlClosing()»	
	«ENDIF»
	'''
	
	def matrixCell (Model model,IOfsProject project)'''
	«var List<Widget> matrixCellWidgets = newArrayList»
	«var List<Widget> matrixCellList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX,matrixCellWidgets)»
	«IF !matrixCellList.empty»
	«htmlOpening()»
	Matrix cell content: <br><br>
	
		<table border="1">
			<tr>
				<th bgcolor="#6495ED">Position</th>
				<th bgcolor="#6495ED">Item name</th>
				<th bgcolor="#6495ED">Description</th>
				<th bgcolor="#6495ED">Type</th>
				<th bgcolor="#6495ED">Display Format</th>
				<th bgcolor="#6495ED">Aggregation</th>
				<th bgcolor="#6495ED">Applies On Row</th>
				<th bgcolor="#6495ED">Applies On Column</th>
				<th bgcolor="#6495ED">Applies On Matrix</th>
			</tr>
			«FOR Widget matrxCell : matrixCellList»
			«var List<Widget> matrixContentCellWidgets = newArrayList»
			«var List<Widget> matrixContentCellList = matrxCell.fetchWidgets(WidgetTypeConstants::MATRIX_CONTENTCELLITEM,matrixContentCellWidgets)»
			«var docText = matrxCell.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
			«FOR Widget matrxContentCell : matrixContentCellList»
			<tr>
				<td>«matrixContentCellList.indexOf(matrxContentCell)+1»</td>
				<td>«IF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("domain")»
				«matrxContentCell.getPropertyValue("domainAttribute")»
				«ELSEIF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("Computed")»
				«matrxContentCell.getPropertyValue("column-name")»
				«ENDIF»</td>	
				<td>«IF docText!=null && !docText.nullOrEmpty»
				Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")» 
				«ENDIF»</td>
				<td>«IF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("domain")»
				Attribute
				«ELSEIF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("Computed")»
					«IF matrxContentCell.getPropertyValue("column-computation").equalsIgnoreCase("make-amount")»
					«var String[] strBrk = matrxContentCell.getPropertyValue("column-computation-parameters").split(",")»
					Make Amount «IF strBrk.size > 1»(«strBrk.get(1).toString»)«ENDIF»
					«ELSEIF matrxContentCell.getPropertyValue("column-computation").equalsIgnoreCase("additional column")»
					Aditional Column
					«ELSEIF matrxContentCell.getPropertyValue("column-computation").equalsIgnoreCase("compute-percentage")»
					Compute Percentage
					«ELSE»
					«WordUtils::capitalize(matrxContentCell.getPropertyValue("column-computation"))»
					«ENDIF»	
				«ENDIF»</td>
				<td>«matrxContentCell.getPropertyValue("format")»</td>	
				<td>
					«IF matrxContentCell.getPropertyValue("aggregationType").equalsIgnoreCase("Sum")»
					Sum
					«ELSEIF matrxContentCell.getPropertyValue("aggregationType").equalsIgnoreCase("Max")»
					Max	
					«ELSEIF matrxContentCell.getPropertyValue("aggregationType").equalsIgnoreCase("weighted-mean")»
					Weighted Mean(weight: «matrxContentCell.getPropertyValue("matrixMeanWeight")»)	
					«ENDIF»			
				</td>
				«var matrix = matrxContentCell.getParentWidgetByType(WidgetTypeConstants::MATRIX)»	
				«IF matrix!=null && matrix.typeName.equals(WidgetTypeConstants::MATRIX)»
					<td>«IF matrix.getPropertyValue("displayLastRow")!=null»Yes«ELSE»No«ENDIF»</td>
					<td>«IF matrix.getPropertyValue("displayLastColumn")!=null»Yes«ELSE»No«ENDIF»</td>
					<td>«IF matrix.getPropertyValue("displayLastCell")!=null»Yes«ELSE»No«ENDIF»</td>
				«ELSE»<td></td><td></td><td></td>
				«ENDIF»
			</tr>
			«ENDFOR»
			«ENDFOR»
		</table>	
	«htmlClosing()»	
	«ENDIF»
	'''
	
	def matrixAttributes (Model model,IOfsProject project)'''
	«var List<Widget> matrixAxisWidgets = newArrayList»
	«var List<Widget> matrixAxisList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX_AXIS,matrixAxisWidgets)»
	«IF !matrixAxisList.empty»
	«htmlOpening()»
			<table border="1">
			<tr>
				<th bgcolor="#6495ED">Attribute Name</th>
				<th bgcolor="#6495ED">Type</th>
				<th bgcolor="#6495ED">Description</th>
				<th bgcolor="#6495ED">Applies On Row</th>
				<th bgcolor="#6495ED">Applies On Column</th>
				<th bgcolor="#6495ED">Applies On Matrix</th>
				<th bgcolor="#6495ED">Tooltip</th>
				<th bgcolor="#6495ED">Axis</th>
				<th bgcolor="#6495ED">Axis Sorting</th>
				<th bgcolor="#6495ED">Max Number Of Columns</th>
				<th bgcolor="#6495ED">Computation Parameter1</th>
				<th bgcolor="#6495ED">Computation Parameter2</th>
				<th bgcolor="#6495ED">CSS Class</th>
				<th bgcolor="#6495ED">Display Format</th>
				<th bgcolor="#6495ED">Enable</th>
				<th bgcolor="#6495ED">Horizontal Alignment</th>
				<th bgcolor="#6495ED">Container Description</th>
			</tr>
	«FOR Widget matrxAxsWid : matrixAxisList»
			«var docText = matrxAxsWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
			<tr>
				<td>«matrxAxsWid.getPropertyValue("domainAttribute")»</td>
				<td>Axis</td>
				<td>«IF docText!=null && !docText.nullOrEmpty»
				Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
				«ENDIF»</td>
				<td></td><td></td><td></td>
				<td>«IF matrxAxsWid.toolTips !=null && matrxAxsWid.toolTips.size > 0»
					«FOR tr: matrxAxsWid.toolTips»
						«IF tr.language.equals("en")»
						«tr.message»
						«ENDIF»
					«ENDFOR»
				«ENDIF»</td>
				<td>«IF matrxAxsWid.getPropertyValue("matrixAxis").equals("x-axis")»
				Horizontal (X)
				«ELSEIF matrxAxsWid.getPropertyValue("matrixAxis").equals("y-axis")»
				Vertical (Y)
				«ENDIF»</td>	
				«GenerationUtils::generateEmptyTags(9)»
			</tr>
	«ENDFOR»
	«FOR Widget matrxAxsWid : matrixAxisList»
			«var docText = matrxAxsWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
			<tr>
				<td>«matrxAxsWid.getPropertyValue("group-sorting-column-name")»</td>
				<td>«IF matrxAxsWid.getPropertyValue("matrixAxis").equals("x-axis")»
				X-Sorting
				«ELSEIF matrxAxsWid.getPropertyValue("matrixAxis").equals("y-axis")»
				Y-Sorting
				«ENDIF»</td>	
				<td>«IF docText!=null && !docText.nullOrEmpty»
				Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
				«ENDIF»</td>
				<td></td><td></td><td></td>
								<td>«IF matrxAxsWid.toolTips !=null && matrxAxsWid.toolTips.size > 0»
					«FOR tr: matrxAxsWid.toolTips»
						«IF tr.language.equals("en")»
						«tr.message»
						«ENDIF»
					«ENDFOR»
				«ENDIF»</td>
				<td></td>
				<td>«matrxAxsWid.getPropertyValue("group-sorting-direction")»</td>
				«GenerationUtils::generateEmptyTags(8)»
			</tr>
	«ENDFOR»
	«var List<Widget> matrixCellWidgets = newArrayList»
	«var List<Widget> matrixCellList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX,matrixCellWidgets)»
	«IF !matrixCellList.empty»
		«FOR Widget matrxCell : matrixCellList»
			«var List<Widget> matrixContentCellWidgets = newArrayList»
			«var List<Widget> matrixContentCellList = matrxCell.fetchWidgets(WidgetTypeConstants::MATRIX_CONTENTCELLITEM,matrixContentCellWidgets)»
			«var docText = matrxCell.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
			«FOR Widget matrxContentCell : matrixContentCellList»
			<tr>
				<td>«IF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("domain")»
				«matrxContentCell.getPropertyValue("domainAttribute")»
				«ELSEIF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("Computed")»
				«matrxContentCell.getPropertyValue("column-name")»
				«ENDIF»</td>	
				<td>«IF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("domain")»
				Attribute
				«ELSEIF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("Computed")»
					«IF matrxContentCell.getPropertyValue("column-computation").equalsIgnoreCase("make-amount")»
					Make Amount
					«ELSEIF matrxContentCell.getPropertyValue("column-computation").equalsIgnoreCase("Relative Percent")»
					Relative Percent	
					«ELSEIF matrxContentCell.getPropertyValue("column-computation").equalsIgnoreCase("additional column")»
					Aditional Column	
					«ENDIF»	
				«ENDIF»</td>
				<td>«IF docText!=null && !docText.nullOrEmpty»
				Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
				«ENDIF»</td>	
				«var matrix = matrxContentCell.getParentWidgetByType(WidgetTypeConstants::MATRIX)»	
				«IF matrix!=null && matrix.typeName.equals(WidgetTypeConstants::MATRIX)»
					<td>«IF matrix.getPropertyValue("displayLastRow")!=null»Yes«ELSE»No«ENDIF»</td>
					<td>«IF matrix.getPropertyValue("displayLastColumn")!=null»Yes«ELSE»No«ENDIF»</td>
					<td>«IF matrix.getPropertyValue("displayLastCell")!=null»Yes«ELSE»No«ENDIF»</td>
				«ELSE»<td></td><td></td><td></td>
				«ENDIF»
				<td>«IF matrxContentCell.toolTips !=null && matrxContentCell.toolTips.size > 0»
					«FOR tr: matrxContentCell.toolTips»
						«IF tr.language.equals("en")»
						«tr.message»
						«ENDIF»
					«ENDFOR»
				«ENDIF»</td>
				<td></td>				
				<td></td>
				«var matrixOld = matrxContentCell.rootWidget.getWidgetByType(WidgetTypeConstants::MATRIX_AXIS)»	
				<td>«IF matrixOld!=null && matrixOld.getPropertyValue("matrixAxis").equals("y-axis")»
				«matrixOld.getPropertyValue("maxColumns")»
				«ENDIF»</td>	
				«IF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("Computed")»
					«IF matrxContentCell.getPropertyValue("column-computation-parameters").contains(",")»
					«var String[] arry = matrxContentCell.getPropertyValue("column-computation-parameters").split(",")»
						«IF !arry.nullOrEmpty»
				<td>«IF arry.size > 0»«arry.get(0)»«ENDIF»</td>
				<td>«IF arry.size > 1»«arry.get(1)»«ENDIF»</td>
						«ELSE»
						<td></td><td></td>
						«ENDIF»
					«ELSE»
					<td>«matrxContentCell.getPropertyValue("column-computation-parameters")»</td>
					<td></td>	
					«ENDIF»
				«ELSEIF matrxContentCell.getPropertyValue("matrixCellItemType").equalsIgnoreCase("Domain")»
				<td></td><td></td>
				«ENDIF»
				<td>«matrxContentCell.getPropertyValue("cssClass")»</td>
				<td>«matrxContentCell.getPropertyValue("format")»</td>
				<td>«IF matrxContentCell.getPropertyValue("enabledIsBasedOn")!=null»
				Based on:«matrxContentCell.getPropertyValue("enabledIsBasedOn")»
				«ELSE»
				«matrxContentCell.getPropertyValue("enabled")»
				«ENDIF»</td>
				<td>«matrxContentCell.getPropertyValue("horizontalAlignment")»</td>
				<td>«IF matrixOld!=null && matrixOld.typeName.equals(WidgetTypeConstants::MATRIX) && (matrixOld.parent.type.name.equals(WidgetTypeConstants::CONDITIONAL_BODY) || matrixOld.parent.type.name.equals(WidgetTypeConstants::TAB))»
				«matrixOld.parent.getPropertyValue(PropertyTypeConstants::TECHNICAL_NAME)»
				«ENDIF»</td>
			</tr>
			«ENDFOR»				
				
		«ENDFOR»
	«ENDIF»
	«htmlClosing()»
	«ENDIF»
	'''
	
	def tableGrouping (Model model,IOfsProject project)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableGrpList = model.widget.fetchWidgets("TableGroup",tableWidgets)»
	«IF !tableGrpList.empty»
	«htmlOpening()»
	Table grouping: 
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Level</th>
				<th bgcolor="#6495ED">Grouping</th>
				<th bgcolor="#6495ED">Sorting</th>
				<th bgcolor="#6495ED">Hierarchy</th>
				<th bgcolor="#6495ED">Collapsed</th>
				<th bgcolor="#6495ED">Used In Dynamic Column</th>
				<th bgcolor="#6495ED">Raw Data</th>
			</tr>
			«FOR Widget tabGrpWid : tableGrpList»
			<tr>
				<td>«tableGrpList.indexOf(tabGrpWid)+1»</td>
				<td>«tabGrpWid.getPropertyValue("group-column-name")»</td>
				<td>«tabGrpWid.getPropertyValue("group-sorting-column-name")» («WordUtils::capitalize(tabGrpWid.getPropertyValue("group-sorting-direction"))»)</td>
				<td>«IF Boolean::valueOf(tabGrpWid.getPropertyValue("hierarchy"))»Yes«ELSE»No«ENDIF»</td>
				<td>«IF Boolean::valueOf(tabGrpWid.getPropertyValue("collapsed"))»Yes«ELSE»No«ENDIF»</td>
				<td>«IF Boolean::valueOf(tabGrpWid.getPropertyValue("group-dynamic-column"))»Yes«ELSE»No«ENDIF»</td>
				«var tableCol = tabGrpWid.getParentWidgetByType("TableColumn")»
				<td>«IF (tableCol!=null && isMoreTableGroupAvailable(tableCol))»No«ELSEIF tabGrpWid.getPropertyValue("aggregateData").equalsIgnoreCase("Aggregate")»No«ELSEIF tabGrpWid.getPropertyValue("aggregateData").equalsIgnoreCase("Raw")»Yes«ENDIF»</td>
			</tr>
			«ENDFOR»
	</table>		
	«htmlClosing()»
	«ENDIF»
	'''
	
	def tableSorting (Model model,IOfsProject project)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableSortList = model.widget.fetchWidgets("TableSort",tableWidgets)»
	«IF !tableSortList.empty»
	«htmlOpening()»
	Table sorting applying on rows (leafs for table tree):
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Rank</th>
				<th bgcolor="#6495ED">Attribute</th>
				<th bgcolor="#6495ED">Direction</th>
			</tr>
			«FOR Widget tabSortWid : tableSortList»
			<tr>
				<td>«tableSortList.indexOf(tabSortWid)+1»</td>
				<td>«tabSortWid.getPropertyValue("sort-column-name")»</td>
				<td>«WordUtils::capitalize(tabSortWid.getPropertyValue("sort-direction"))»</td>
			</tr>
			«ENDFOR»
	</table>		
	«htmlClosing()»
	«ENDIF»
	'''
	
	def tableFilter (Model model,IOfsProject project)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableList = model.widget.fetchWidgets(WidgetTypeConstants::TABLE_TREE,tableWidgets)»
	«IF !tableList.empty»
	«commonTabMatFilter(tableList)»
	«ENDIF»
	'''
	
	def matrixFilter (Model model,IOfsProject project)'''
	«var List<Widget> matrixWidgets = newArrayList»
	«var List<Widget> matrxList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX,matrixWidgets)»
	«IF !matrxList.empty»
		«commonTabMatFilter(matrxList)»
	«ENDIF»
	'''
	
	def commonTabMatFilter (List<Widget> matrxList)'''
	«FOR Widget tabWid : matrxList»
	«var List<Widget> tableKeepFilterWidgets = newArrayList»
	«var List<Widget> tableFilterList = tabWid.fetchWidgets("TableKeepFilter",tableKeepFilterWidgets)»
	«IF !tableFilterList.empty»
	«htmlOpening()»
	«IF tableFilterList.size > 1»
	List of filters, linked with the logical operator: «tableFilterList.get(0).getParentWidgetByType(WidgetTypeConstants::TABLE_TREE).getPropertyValue("table-keep-filter-logic")»<br>
	«ENDIF»
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Filter Column</th>
				<th bgcolor="#6495ED">Operator</th>
				<th bgcolor="#6495ED">Value</th>
			</tr>
			«FOR Widget tabFilterWid : tableFilterList»
			<tr>
				<td>«tabFilterWid.getPropertyValue("keep-filter-column-name")»</td>
				<td>«tabFilterWid.getPropertyValue("keep-filter-operator")»</td>
				<td>«tabFilterWid.getPropertyValue("keep-filter-operand")»</td>
			</tr>
			«ENDFOR»
	</table><br>		
	«htmlClosing()»
	«ENDIF»
	«ENDFOR»
	'''
	
	def tableExtra (Model model,IOfsProject project)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableList = model.widget.fetchWidgets(WidgetTypeConstants::TABLE_TREE,tableWidgets)»
	«IF !tableList.empty»
		«FOR Widget tabWid : tableList»
		«var List<Widget> tableExtraWidgets = newArrayList»
		«var List<Widget> tableExtraList = tabWid.fetchWidgets("TableExtra",tableExtraWidgets)»
		«var List<Widget> tableAggregateWidgets = newArrayList»
		«var List<Widget> tableAggregateList = tabWid.fetchWidgets("TableAggregate",tableAggregateWidgets)»
			«IF !tableExtraList.empty»
	«htmlOpening()»
				«IF tableExtraList.size > 1»
				List of extra columns: <br>
				«ENDIF»
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Attribute</th>
				<th bgcolor="#6495ED">Aggregate</th>
			</tr>
				«FOR Widget tabExtraWid : tableExtraList»
				<tr>
				«var domAtrName = tabExtraWid.getPropertyValue("domainAttribute")»
				<td>«domAtrName»</td>
				<td>«WordUtils::capitalize(getAggregateFunction(domAtrName,tableAggregateList))»</td>
				</tr>
				«ENDFOR»
	</table><br>		
	«htmlClosing()»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''
	
	def matrixExtra (Model model,IOfsProject project)'''
	«var List<Widget> matrixWidgets = newArrayList»
	«var List<Widget> matrixList = model.widget.fetchWidgets(WidgetTypeConstants::MATRIX,matrixWidgets)»
	«IF !matrixList.empty»
		«FOR Widget tabWid : matrixList»
		«var List<Widget> matrixExtraWidgets = newArrayList»
		«var List<Widget> matrixExtraList = tabWid.fetchWidgets("MatrixExtra",matrixExtraWidgets)»
			«IF !matrixExtraList.empty»
	«htmlOpening()»
				«IF matrixExtraList.size > 1»
				List of extra columns: <br>
				«ENDIF»
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Attribute</th>
				<th bgcolor="#6495ED">Aggregate</th>
			</tr>
				«FOR Widget tabExtraWid : matrixExtraList»
				<tr>
				«var domAtrName = tabExtraWid.getPropertyValue("domainAttribute")»
				<td>«domAtrName»</td>
				<td>«WordUtils::capitalize(tabExtraWid.getPropertyValue("aggregationType"))»</td>
				</tr>
				«ENDFOR»
	</table><br>		
	«htmlClosing()»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''
	
	def tableColumns (Model model,IOfsProject project)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableList = model.widget.fetchWidgets(WidgetTypeConstants::TABLE_TREE,tableWidgets)»
	«IF !tableList.empty»
		«FOR Widget tabWid : tableList»
		«var List<Widget> tableColumnWidgets = newArrayList»
		«var List<Widget> tableColumnList = tabWid.fetchWidgets("TableColumn",tableColumnWidgets)»
			«IF !tableColumnList.empty»
		«htmlOpening()»
				«IF tableColumnList.size > 1»
				List of columns set on the table: <br>
				«ENDIF»
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Position</th>
				<th bgcolor="#6495ED">Column name</th>
				<th bgcolor="#6495ED">Label</th>
				<th bgcolor="#6495ED">Description</th>
				<th bgcolor="#6495ED">Column Type</th>
				<th bgcolor="#6495ED">Format</th>
				<th bgcolor="#6495ED">Is Visible</th>
				<th bgcolor="#6495ED">Sortable</th>
				<th bgcolor="#6495ED">Part of Filter</th>
			</tr>
				«FOR Widget tabColumnWid : tableColumnList»
				<tr>
				<td>«tableColumnList.indexOf(tabColumnWid)+1»</td>
					<td>«IF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("domain")»
					«tabColumnWid.getPropertyValue("domainAttribute")»
					«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Placeholder") || tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed")»
					«tabColumnWid.getPropertyValue("column-name")»
					«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("dynamic")»
					«var par = tabColumnWid.getParentWidgetByType(WidgetTypeConstants::TABLE_TREE)»
					Based on group «IF par!=null && par.getDynamicGroupWidget!=null »[«par.getDynamicGroupWidget.getPropertyValue("group-column-name")»]«ENDIF»	
					«ELSEIF Boolean::valueOf(tabColumnWid.getPropertyValue("column-display-grouping"))»
					Display grouping 
					«ENDIF»</td>
				<td>«fetchTranslationValue(tabColumnWid,project)»</td>
					«var docText = tabColumnWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
					<td>«IF docText!=null && !docText.nullOrEmpty»
					Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")» 
					«ENDIF»</td> 
					<td>«IF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("domain") && tabColumnWid.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE)!=null» Attribute
					«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Placeholder")» Placeholder
					«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed") &&  tabColumnWid.getPropertyValue("column-computation").equalsIgnoreCase("make-amount")» 
					«var String[] strBrk = tabColumnWid.getPropertyValue("column-computation-parameters").split(",")»
					Make Amount «IF strBrk.size > 1»(«strBrk.get(1).toString»)«ENDIF»
					«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed") &&  tabColumnWid.getPropertyValue("column-computation").equalsIgnoreCase("Same")» Same	
					«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("dynamic")» 
					«var par = tabColumnWid.getParentWidgetByType(WidgetTypeConstants::TABLE_TREE)»
					Dynamic «IF par!=null && par.getDynamicGroupWidget!=null» («par.getDynamicGroupWidget.getPropertyValue("group-column-name")»)«ENDIF»				
					«ENDIF»</td>
					«var List<Widget> tabColItemWids = newArrayList»
					«var List<Widget> tabColItemWidList = tabColumnWid.fetchWidgets("TableColumnItem",tabColItemWids)»
					<td>«IF tabColItemWidList.size > 0»
					«IF !tabColItemWidList.get(0).getPropertyValue("format").nullOrEmpty»«tabColItemWidList.get(0).getPropertyValue("format")»«ELSE»«ENDIF»
					«ENDIF»</td>
					<td>«IF Boolean::valueOf(tabColumnWid.getPropertyValue("column-locked"))» Locked	
					«ELSEIF tabColumnWid.getPropertyValue("column-visibility").equalsIgnoreCase("Visible")» By default
					«ELSEIF tabColumnWid.getPropertyValue("column-visibility").equalsIgnoreCase("not-visible")» Not by default
					«ELSEIF tabColumnWid.getPropertyValue("column-visibility").equalsIgnoreCase("never-visible")» Never	
					«ENDIF»</td>	
					<td>«IF Boolean::valueOf(tabColumnWid.getPropertyValue("column-sortable"))» Yes«ELSE» No«ENDIF»</td>
					<td>«IF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Placeholder") || tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed")»No«ELSEIF Boolean::valueOf(tabColumnWid.getPropertyValue("column-is-part-of-filter"))»Yes «ELSE» No «ENDIF»</td>
				</tr>
				«ENDFOR»
	</table><br>		
	«htmlClosing()»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''
	
	def tableXColumns (Model model,IOfsProject project)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableList = model.widget.fetchWidgets(WidgetTypeConstants::TABLE_TREE,tableWidgets)»
	«IF !tableList.empty»
		«FOR Widget tabWid : tableList»
		«var List<Widget> tableColumnWidgets = newArrayList»
		«var List<Widget> tableColumnList = tabWid.fetchWidgets("TableColumn",tableColumnWidgets)»
			«IF !tableColumnList.empty»
		«htmlOpening()»
			«IF tableColumnList.size > 1»
			List of columns set on the table: <br>
			«ENDIF»
	<table border="1">
			<tr>
				<th bgcolor="#6495ED">Translation</th>
				<th bgcolor="#6495ED">Column name</th>
				<th bgcolor="#6495ED">Description</th>
				<th bgcolor="#6495ED">Type</th>
				<th bgcolor="#6495ED">Is Visible</th>
				<th bgcolor="#6495ED">Tooltip</th>
				<th bgcolor="#6495ED">Sortable</th>
				<th bgcolor="#6495ED">Filter</th>
				<th bgcolor="#6495ED">Display Grouping</th>
				<th bgcolor="#6495ED">Grouping</th>
				<th bgcolor="#6495ED">Part of Filter</th>
				<th bgcolor="#6495ED">Filter Logical Operator</th>
				<th bgcolor="#6495ED">Max Char</th>
				<th bgcolor="#6495ED">Wrapped</th>
				<th bgcolor="#6495ED">Width</th>
				<th bgcolor="#6495ED">Item Name</th>
				<th bgcolor="#6495ED">Aggregation</th>
				<th bgcolor="#6495ED">Tooltip</th>
				<th bgcolor="#6495ED">New Line</th>
				<th bgcolor="#6495ED">Width</th>
				<th bgcolor="#6495ED">CSS Class</th>
				<th bgcolor="#6495ED">Display Format</th>
				<th bgcolor="#6495ED">Wrapped</th>
				<th bgcolor="#6495ED">Enable</th>
				<th bgcolor="#6495ED">Condition</th>
				<th bgcolor="#6495ED">Condition Description</th>
				<th bgcolor="#6495ED">Condition Java code</th>
				<th bgcolor="#6495ED">Horizontal Alignment</th>
			</tr>
			«FOR Widget tabColumnWid : tableColumnList»
			<tr>
				<td>«IF !ModuleUtils::getTitle(project,tabColumnWid).nullOrEmpty»«ModuleUtils::getTitle(project,tabColumnWid)»«ELSE»«"None"»«ENDIF»</td>
				«IF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("domain")»
				<td>«tabColumnWid.getPropertyValue("domainAttribute")»</td>
				«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Placeholder") || tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed")»
				<td>«tabColumnWid.getPropertyValue("column-name")»</td>
				«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("dynamic")»
				Based on group [«tabColumnWid.getPropertyValue("column-based-on-group")»]
				«ELSEIF Boolean::valueOf(tabColumnWid.getPropertyValue("column-display-grouping"))»
				<td>Display grouping </td>
				«ELSE»
				<td></td>
				«ENDIF»
				«var docText = tabColumnWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
				<td>«IF docText!=null && !docText.nullOrEmpty»
					Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")»
					«ENDIF» </td> 
				«var List<Widget> tableExtraWidgets = newArrayList»
				«var List<Widget> tableExtraList = tabWid.fetchWidgets("TableExtra",tableExtraWidgets)»
					
				«IF  tabColumnWid.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE)!=null»	
					«IF !tabColumnWid.findProperty(PropertyTypeConstants::DOMAIN_ATTRIBUTE).readonly»
					<td>Editable Attribute</td>
					«ELSE»
					<td>Attribute</td>		
					«ENDIF»
				«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Placeholder")»	
				<td>Placeholder</td>
				«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed") &&  tabColumnWid.getPropertyValue("column-computation").equalsIgnoreCase("Make amount")»	
				<td>Make Amount</td>
				«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("Computed") &&  tabColumnWid.getPropertyValue("column-computation").equalsIgnoreCase("Same")»	
				<td>Same</td>	
				«ELSEIF tabColumnWid.getPropertyValue("column-type").equalsIgnoreCase("dynamic")»
				<td>Dynamic</td>
				«ELSEIF tableExtraList.size > 0»
				<td>«tableExtraList.toString.subSequence(1,tableExtraList.toString.length - 2)»</td>
				«ELSE»
			 	<td></td>			
				«ENDIF»
				«var List<Widget> tabColItemWids = newArrayList»
				«var List<Widget> tabColItemWidList = tabColumnWid.fetchWidgets("TableColumnItem",tabColItemWids)»
				«IF tabColItemWidList.size > 0»
				<td>«tabColItemWidList.get(0).getPropertyValue("format")»</td>	
				«ELSE»
			 	<td></td>		
				«ENDIF»
				«IF Boolean::valueOf(tabColumnWid.getPropertyValue("column-locked"))»	
				<td>Locked</td>	
				«ELSEIF tabColumnWid.getPropertyValue("column-visibility").equalsIgnoreCase("Visible")»	
				<td>By default</td>	
				«ELSEIF tabColumnWid.getPropertyValue("column-visibility").equalsIgnoreCase("Not Visible")»	
				<td>Not by default</td>	
				«ELSEIF tabColumnWid.getPropertyValue("column-visibility").equalsIgnoreCase("Never Visible")»	
				<td>Never</td>
				«ELSE»
			 	<td></td>		
				«ENDIF»
				«IF tabColumnWid.toolTips !=null && tabColumnWid.toolTips.size > 0»
					«FOR tr: tabColumnWid.toolTips»
						«IF tr.language.equals("en")»
						<td>«tr.message»</td>
						«ENDIF»
					«ENDFOR»
				«ELSE»
			 	<td></td> 
				«ENDIF»
				«IF Boolean::valueOf(tabColumnWid.getPropertyValue("column-sortable"))»
				<td>Yes</td>
				«ELSE»
				<td>No</td>					
				«ENDIF»
				«IF Boolean::valueOf(tabWid.getPropertyValue("table-filter-layer"))»
				<td>Yes</td>
				«ELSE»
				<td>No</td>					
				«ENDIF»
				«IF Boolean::valueOf(tabColumnWid.getPropertyValue("column-display-grouping"))»
				<td>Yes</td>
				«ELSE»
				<td>No</td>					
				«ENDIF»
				«var List<Widget> tableGrpWidgets = newArrayList»
				«var List<Widget> tableGrpList = tabWid.fetchWidgets("TableGroup",tableGrpWidgets)»
				«IF !tableGrpList.empty»	
				<td>Level: [number: «FOR tabGrp: tableGrpList»«tabGrp.getPropertyValue("group-rank")» «IF tableGrpList.indexOf(tabGrp)!= tableGrpList.size -1 »,«ENDIF»«ENDFOR»]</td>	
				«ELSE»
			 	<td></td> 		
				«ENDIF»
				«IF Boolean::valueOf(tabColumnWid.getPropertyValue("column-is-part-of-filter"))»
				<td>Yes</td>
				«ELSE»
				<td>No</td>					
				«ENDIF»
				<td>«tabWid.getPropertyValue("table-keep-filter-logic")»</td>
				<td>«tabColumnWid.getPropertyValue("column-max-characters")»</td>
				<td>«tabColumnWid.getPropertyValue("column-wrapped")»</td>
				<td>«tabColumnWid.getPropertyValue("column-width")»</td>
			</tr>
			«ENDFOR»
	</table><br>		
	«htmlClosing()»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''
	
	def getAggregateFunction(String domainAtrName,List<Widget> tableAggregateList) {
		for(wid : tableAggregateList){
			if (wid.getPropertyValue("aggregate-column-name").equals(domainAtrName) ){
					return wid.getPropertyValue("aggregate-computation");
			}
		}
		return "";
	}
	
	def generalDescription (Model model,IOfsProject project,String type)'''
	«var List<Widget> tableWidgets = newArrayList»
	«var List<Widget> tableList = model.widget.fetchWidgets(type,tableWidgets)»
	«IF !tableList.empty»
	«htmlOpening()»
		«FOR Widget tabWid : tableWidgets»
			«IF tabWid.parent.type.name.equals(WidgetTypeConstants::CONDITIONAL_BODY) || tabWid.parent.type.name.equals(WidgetTypeConstants::TAB)»
			This table belongs to the «tabWid.parent.type.name».<br><br>
		«ELSEIF  tabWid.rootWidget.type.name.equals(WidgetTypeConstants::MODULE) || tabWid.rootWidget.type.name.equals(WidgetTypeConstants::PAGE)»
			This table belongs to the «tabWid.rootWidget.type.name».<br><br>
			«ENDIF»
			«IF tabWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION) !=null»
				«var docText = tabWid.getPropertyValue(PropertyTypeConstants::DOCUMENTATION)»
					«IF docText!=null && !docText.nullOrEmpty»
					Description: «docText.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")» <br>
					«ENDIF»
			«ENDIF»
		List of properties set on the table: <br>
		<table border="1">
			<tr>
				<th bgcolor="#6495ED">Property Name</th>
				<th bgcolor="#6495ED">Value</th>
			</tr>
			<tr>
				<td>Rendering mode </td>
				<td>«WordUtils::capitalize(tabWid.getPropertyValue("table-rendering-mode"))»</td>
			</tr>
			<tr>
				<td>Make multiple checkboxes exclusive mode </td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("make-checkbox-exclusive"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			<tr>
				<td>Display checkbox on tree nodes </td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("display-checkbox"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			<tr>
				<td>Show Column Selector</td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("show-column-selector"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			<tr>
				<td>Expand/Collapse all </td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("table-expand-all"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			<tr>
				<td>Filter </td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("table-filter-layer"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			<tr>
				<td>Select/De-select all </td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("table-select-all"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			<tr>
				<td>Highlight row </td>
				<td>«IF Boolean::valueOf(tabWid.getPropertyValue("highlightRow"))»Yes«ELSE»No«ENDIF»</td>
			</tr>
			«IF tabWid.getPropertyValue("table-page-size").toString != null && !tabWid.getPropertyValue("table-page-size").toString.empty »
			<tr>
				<td>Page size </td>
				«IF !tabWid.getPropertyValue("table-page-size").nullOrEmpty && tabWid.getPropertyValue("table-page-size").length > 2 »
				«var pageSize = tabWid.getPropertyValue("table-page-size").toString.substring(2,tabWid.getPropertyValue("table-page-size").toString.length -1)»
				«var String[] strBrk = pageSize.split("-")»
				<td>«strBrk.get(0).toString.toFirstUpper»(«pageSize.length»)
				«ENDIF»
			</tr>
			«ENDIF»
			<tr>
				<td>Dataset reference </td>
				<td>«ModuleUtils::getDatasetName(project,tabWid)»</td>
			</tr>
			<tr>
				<td>Based on Class </td>
				<td>«ModuleUtils::getDatasetBaseClassName(project,tabWid)»</td>
			</tr>
			«ENDFOR»
		</table>
	«htmlClosing()»
	«ENDIF»
	'''
	
	def isCheckBoxAvailable(Widget widget) {
		for(wid : widget.contents){
			if (wid.type.name.equals(WidgetTypeConstants::CHECKBOX) ){
					return true;
			}
		}
		return false;
	}
	
	def isMoreTableGroupAvailable(Widget widget) {
		var List<Widget> tableGrpWidgets = newArrayList()
		for(wid : widget.contents){
			if (wid.type.name.equals("TableGroup") ){
					tableGrpWidgets.add(wid)
			}
		}
		if(tableGrpWidgets.size>1){
			return true	
		}
		return false
	}
	
	def getDynamicGroupWidget(Widget widget) {
		var List<Widget> tableWidgets = newArrayList
		widget.fetchWidgets("TableGroup",tableWidgets)
		for(wid : tableWidgets){
			if (wid.type.name.equals("TableGroup") && Boolean::valueOf(wid.getPropertyValue("group-dynamic-column"))){
					return wid
			}
		}
		return null		
	}
	
	def List<Widget> fetchWidgets(Widget widget,String widgetType,List<Widget> tableWidgets) {
		if (widget.type.name.equals(widgetType)){
			for(wid : widget.parent.contents){
				if (wid.type.name.equals(widgetType)){
					if(wid.model != null && !tableWidgets.contains(wid)) {
						tableWidgets.add(wid)
					}
				}
			}
			return tableWidgets
		}
		else if(!widget.contents.empty) {
			for(wid : widget.contents){
				wid.fetchWidgets(widgetType,tableWidgets)
			}
		}
		return tableWidgets
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
	
	def Widget getParentWidgetByType(Widget widget, String widgetType) {
		if (widget.type.name.equals(widgetType)){
			if(widget.model != null) {
				return widget
			}
		}
		else if(widget!=null && widget.eContainer!=null) {
			return getParentWidgetByType(widget.eContainer as Widget,widgetType)
		}	
	}
	
	def String fetchTranslationValue  (Widget wid,IOfsProject project) {
		if (!ModuleUtils::getTitle(project,wid).nullOrEmpty)
			return ModuleUtils::getTitle(project,wid)
		else if(!ModuleUtils::getTranslation(project,wid).nullOrEmpty)
			return ModuleUtils::getTranslation(project,wid)	
		else
			return ""
	}
	
	
	def tableMatrixIndex(String path , List<String> fileList)'''
	«htmlOpening()»
	<ul>
		«FOR String tableMatrixFile : fileList»
		<li>
			<A HREF="«path + "\\"»«tableMatrixFile»">«GenerationUtils::getTitleForFileName(tableMatrixFile)»</A>
		<br></br>
		</li>
		«ENDFOR»
	</ul>
	«htmlClosing»
	'''
	
}