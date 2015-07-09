package com.odcgroup.menu.generation.tap

import com.odcgroup.menu.model.MenuItem
import com.odcgroup.menu.model.MenuRoot
import com.odcgroup.workbench.core.OfsCore
import com.odcgroup.workbench.core.repository.OfsResourceHelper
import com.odcgroup.workbench.generation.CodeGenerationPreferences
import java.util.Date
import java.util.HashMap
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.expression.Variable
import org.eclipse.xtext.generator.IFileSystemAccess

class MenuConfig implements IMenuGenerator {
	
	
	override void doGenerate(MenuRoot root, IFileSystemAccess fsa, HashMap<String, Variable> globalVars) {
		fsa.generateFile("menu/Menu.xml", renderRootNode(root.rootItem, globalVars));
	}
	
	def renderRootNode(MenuItem item, HashMap<String, Variable> globalVars) ''' 
		<?xml version="1.0" encoding="UTF-8"?>
		
		<!-- 
		*************************************************************************************
		DO NOT MODIFY THIS FILE!

		This file has been generated automatically  by Design Studio. 
		Any change will be lost when regenerated.
		*************************************************************************************		
		Design Studio information of menu model
		Project name     : «globalVars.getVar("projectName")»
		Package          : «globalVars.getVar("packageName")»
		Generation user  : «globalVars.getVar("systemUser")»
		Generation date  : «currentDate»
		DS version       : «DSBuildID»
		*************************************************************************************
		-->
		
		<menu xmlns:cinclude="http://apache.org/cocoon/include/1.0" name="«item.name»"«item.renderPageflow»«item.renderSecurityRole»«item.renderEnabled»«item.renderKeyboardShortcut»«item.renderChildTabs»«item.closetag»
			«FOR submenu: item.submenus»
				«submenu.renderSubMenu»
			«ENDFOR»
		«item.renderEndMenu»
	''' 
	
	def renderEndMenu(MenuItem item) {
		if (!item.submenus.empty) {
			return "</menu>";
		}
	}
	
	def renderPageflow(MenuItem item) {
		if (item.pageflow != null) {
			return ' uri="'+resolveDSPageflowURL(item)+'"';
		} else {
			return "";
		}
	}
	
	def renderSecurityRole(MenuItem item) {
		if (item.securityRole != null) {
			return ' acl="'+item.securityRole+'"';
		} else {
			return "";
		}
	}
	
	def renderEnabled(MenuItem item) {
		if (item.enabled.toString.equals("false")) {
			return ' always-enabled="false" always-disabled="true"';
		} else if (item.enabled.toString.equals("conditional")) {
			return ' always-enabled="false" always-disabled="false"';		
		}
	}

	def renderKeyboardShortcut(MenuItem item) {
		if (item.shortcut != null && !item.shortcut.trim.equals("")) {
			return ' keyboard-shortcut="'+item.shortcut.trim+'"';
		} else {
			return "";
		}
	}
	
	def renderSubMenu(MenuItem item) '''
		<menu name="«item.name»"«item.renderPageflow»«item.renderSecurityRole»«item.renderEnabled»«item.renderChildTabs»«item.renderKeyboardShortcut»«item.closetag»
			«FOR submenu: item.submenus» 
				«submenu.renderSubMenu»
			«ENDFOR»
		«item.renderEndMenu»
	'''
	
	def renderChildTabs(MenuItem item) {
		if (getMenuLevel(item, 0) == 2) {
			return ' children-displayed-as-tabs="'+item.displayTabs+'"';			
		}
	}
	
	def closetag(MenuItem item) {
		if (item.submenus.empty) {
			return "/>";
		} else {
			return ">";
		}
	}	
	
	def resolveDSPageflowURL(MenuItem item) {
		val url = item.pageflow;
		var String rurl = "";
		if (url.startsWith("resource:///") && url.endsWith(".pageflow")) {
			try {
				val ofsProject = OfsResourceHelper::getOfsProject(item.eResource);
				val project = ofsProject.project;
				val mres = ofsProject.getOfsModelResource(URI::createURI(url));
				val list = mres.EMFModel;
				var String targetFileName = "";
				for(model : list) {
					if("Pageflow".equals(model.eClass.name)) {
						targetFileName =  ""+model.eGet(model.eClass().getEStructuralFeature("fileName"));
					}
				}
				var String activity = CodeGenerationPreferences::getPageflowActivityPreference(project);
				rurl = "/activity/" + activity + "/pageflow/" + targetFileName + "/"+ getModelName(url, "pageflow");
			} catch (Exception e) {
				
			}
		} else {
				rurl = url;
		}
		return rurl;
	}
	
	def getModelName(String resourceURL, String extn) {
		var int index = resourceURL.lastIndexOf("/");
		return resourceURL.substring(index + 1,	resourceURL.length() - extn.length() - 1);
	}
	
	/**
	 * @return
	 */
	def Date getCurrentDate() {
		return new Date(System::currentTimeMillis);
	}
	
	/**
	 * 
	 */
	def int getMenuLevel(MenuItem item, int lev) {
		var level = lev;
		if (item.eContainer instanceof MenuRoot) {
			return level;
		} else if (item.eContainer instanceof MenuItem) {				
			var mitem = item.eContainer as MenuItem;	
			level = getMenuLevel(mitem, level+1);	
		}
		return level;
	}
	

	/**
	 * @return
	 */
	def String getDSBuildID() {		
		return OfsCore::versionNumber;
	}
	
	def String getVar(HashMap<String, Variable> globalVars, String str) {
		return ""+globalVars.get(str).value;
	}
	
}