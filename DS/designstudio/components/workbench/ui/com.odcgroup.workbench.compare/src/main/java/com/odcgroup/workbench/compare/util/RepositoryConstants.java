package com.odcgroup.workbench.compare.util;

/**
 * Constants for SVN & EGit.
 * 
 * @author vramya
 * 
 */
public interface RepositoryConstants {

	String TEAM_CORE = "org.eclipse.team.core";
	
	String REPOSITORY = "repository";
	
	String EGIT_NATURE = "org.eclipse.egit.core.GitProvider";
	
	String SVN_NATURE = "org.eclipse.team.svn.core.svnnature";
	
	String IRESOURCE = "org.eclipse.egit.ui.resourceContributions";

	String IPROJECT = "org.eclipse.egit.ui.projectContributions";

	String ICONTAINER = "org.eclipse.egit.ui.containerContributions";

	String IFILE = "org.eclipse.egit.ui.fileContributions";

	String POPUP_MENU_PLUGIN_ID = "org.eclipse.ui.popupMenus";

	String PLUGIN_ID = "com.odcgroup.workbench.compare";

	String GROUP_TEAM_MAIN = "team.main";

	String GROUP_TEAM_REMOTE = "team.remote";

	String GROUP_TEAM_SWITCH = "team.switch";

	String GROUP_TEAM_ADVANCED = "team.advanced";

	String GROUP_TEAM_SYNCH = "team.synch";

	String LABEL_REMOTE = "&Remote";

	String LABEL_SWITCH_TO = "&Switch To";

	String LABEL_ADVANCED = "&Advanced";

	String LABEL_SYNCHRONIZE = "&Synchronize";

	String LABEL_FETCH_FROM = "Fetch From...";

	String LABEL_PUSH = "Push...";

	String LABEL_FETCH_GERRIT = "Fetch from Gerrit...";

	String LABEL_PUSH_GERRIT = "Push to Gerrit...";

	String LABEL_CONFIG_FETCH = "Configure Fetch from Upstream...";

	String LABEL_CONFIG_PUSH = "Configure Push to Upstream...";

	String LABEL_NEW_BRANCH = "New Branch...";

	String LABEL_MASTER = "master";

	String LABEL_OTHER = "Other...";

	String LABEL_RENAME_BRANCH = "Rename Branch...";

	String LABEL_DELETE_BRANCH = "Delete Branch...";

	String LABEL_TAG = "Tag...";

	String LABEL_ASSUME = "Assume Unchanged";

	String LABEL_NOASSUME = "No Assume Unchanged";

	String LABEL_FETCH_HEAD = "FETCH_HEAD";

	String LABEL_IRIS_EMBEDED_SERVER = "refs/remotes/origin/iris-embededserver";

	String LABEL_JBPM = "refs/remotes/origin/jbpm";

	String LABEL_SYNCH_MASTER = "refs/remotes/origin/master";

	String LABEL_CUSTOM = "Custom...";

	String GROUP_COMPARE_WITH = "compareWithMenu";

	String GROUP_REPLACE_WITH = "replaceWithMenu";

	String OBJ_CONTRIBUTION = "objectContribution";

	String ID = "id";

	String NAME = "name";

	String VALUE = "value";

	String CLASS_STRING = "class";

	String ACTION = "action";

	String LABEL = "label";

	String ICON = "icon";

	String MENUBAR_PATH = "menubarPath";

	String GROUP1 = "/group1";

	String GROUP2 = "/group2";

	String GROUP3 = "/group3";

	String BRANCHES_ICON = "icons/branches_obj.gif";

	String SYNCH_ICON = "icons/remotespec.gif";

	String FETCH_ICON = "icons/obj16/fetch.gif";

	String PUSH_ICON = "icons/obj16/push.gif";

	String FETCH_GERRIT_ICON = "icons/obj16/gerrit_fetch.gif";

	String PUSH_GERRIT_ICON = "icons/obj16/gerrit_push.gif";

	String NEW_BRANCH_ICON = "icons/obj16/new_branch_obj.gif";

	String RENAME_ICON = "icons/obj16/editconfig.gif";

	String TAG_ICON = "icons/obj16/new_tag_obj.gif";
	
	String ASSUME_ICON = "icons/obj16/assume-unchanged.gif";
	
	String NOASSUME_ICON = "icons/obj16/no-assume-unchanged.gif";
	
	String BRANCH_ICON = "icons/obj16/branch_obj.gif";
}
