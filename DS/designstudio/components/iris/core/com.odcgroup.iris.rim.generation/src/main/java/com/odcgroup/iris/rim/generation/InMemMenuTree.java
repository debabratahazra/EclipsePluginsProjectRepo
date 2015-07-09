package com.odcgroup.iris.rim.generation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to maintain the parent menu for each individual menus.
 * Whenever a menu has a code-gen on it, it need to refresh all parents (up to the root)
 * So the embedded server is correctly refreshed.
 * This class is a singleton and thread safe.
 * 
 * @author taubert
 * 
 */
public class InMemMenuTree {

	public final static InMemMenuTree INSTANCE = new InMemMenuTree();
	private final HashMap<String, MenuReference> tree = new HashMap<String, MenuReference>();
	private static final Logger logger = LoggerFactory.getLogger(T24ResourceModelsGenerator.class);

	private boolean isInitialized = false;

	private InMemMenuTree() {
		/*
		 * Private constructor
		 */
	}

	public synchronized void Initialize(File fRimPlace) {
		tree.clear();
		File[] fRims = fRimPlace.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name != null && name.endsWith(".rim");
			}
		});

		final HashMap<String, MenuReference> tmpTree = new HashMap<String, MenuReference>();

		if (fRims != null) {
			// can be null if the 'fRimPlace' does not contain rim files.
			for (File f : fRims) {
				try{
					String text = FileUtils.readFileToString(f, "UTF-8");
					parseFile(f.getName(), text, tmpTree);
				} catch (Exception e) {
					logger.error("Exception when processing: " + f.toString(), e);
				}
			}
		}

		updateTree(tmpTree);

		this.isInitialized = true;
	}

	private void updateTree(HashMap<String, MenuReference> tmpTree) {
		/*
		 * Now we have a hashmap of all rim (key being the reference). All are
		 * containing a list of children. What we want it the opposite (children
		 * -> parent)
		 */
		for (String sMenuRef : tmpTree.keySet()) {
			MenuReference ref = tmpTree.get(sMenuRef);
			for (String sChild : ref.getChildren()) {
				MenuReference childRef = tmpTree.get(sChild);
				if (childRef != null) {
					if (!childRef.parents.contains(ref)) {
						childRef.parents.add(ref);
					}
				}
			}
		}
		/*
		 * And now copy this tmp Hashmap to the real one, using the rimName as
		 * key
		 */
		for (String sMenuRef : tmpTree.keySet()) {
			MenuReference ref = tmpTree.get(sMenuRef);
			tree.put(ref.getRimName(), ref);
		}
	}

	/**
	 * Return all the parent (up to root)
	 * This method is safe if there is a loop (eg A->B->C->A) 
	 * @param sRimName
	 * @return
	 */
	public synchronized List<String> getParents(String sRimName) {
		final ArrayList<String> ret = new ArrayList<String>();
		getParents(sRimName, ret);
		if (ret.size() > 0){ // always the case as we are adding the menu itself
			ret.remove(0); // Remove the first one as it it the menu itself
		}
		return ret;
	}

	private void getParents(String sRimName, ArrayList<String> ret) {
		MenuReference ref = tree.get(sRimName);
		if (ref != null) {
			if (!ret.contains(ref.getRimName())){
				ret.add(ref.getRimName());
				for (MenuReference parent : ref.parents) {
					getParents(parent.getRimName(), ret);
				}
			}
		}
	}

	/**
	 * Whenever a menu is generated, update the tree (@See T24ResourceModelsGenerator)
	 * @param rimName
	 * @param input
	 */
	public synchronized void update(String rimName, String input) {
		HashMap<String, MenuReference> tmpTree = new HashMap<String, MenuReference>();
		try {
			parseFile(rimName, input, tmpTree);
			MenuReference ref = tree.get(rimName); // If it already exsits, there is all the parents. Use this one.
			MenuReference newref = tmpTree.values().toArray(new MenuReference[]{})[0];
			if (ref == null){
				/*
				 * this is a brand new one !
				 */
				ref = newref;
			}else{
				ref.children.clear();
				ref.children.addAll(newref.children);
			}
			for (MenuReference menu : tree.values()){
				if (newref.children.contains(menu.resourceName)){
					if (!menu.parents.contains(ref)){
						if (logger.isDebugEnabled()){
							logger.debug("adding " + rimName + " as parent of " + menu.rimName );
						}
						menu.parents.add(ref);
					}
				}else{
					if(menu.parents.remove(ref)){
						if (logger.isDebugEnabled()){
							logger.debug("removing " + rimName + " as parent of " + menu.rimName );
						}
					}
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void parseFile(String rimName, String input, HashMap<String, MenuReference> tmpTree) throws Exception {
		MenuReference menuRef = null;
		BufferedReader bufR = new BufferedReader(new StringReader(input));
		String sLine = null;
		while ((sLine = bufR.readLine()) != null) {
			sLine = sLine.trim();
			if (menuRef == null && sLine.startsWith("resource ")) {
				int pos = 9;
				while (pos < sLine.length() && Character.isJavaIdentifierPart(sLine.charAt(pos))) {
					pos++;
				}
				String resource = sLine.substring(9, pos);
				menuRef = new MenuReference(resource, rimName);
				tmpTree.put(resource, menuRef);
			}
			if (menuRef != null) {
				sLine = sLine.replace(" ", "");
				sLine = sLine.replace("\"", "");
				sLine = sLine.replace("{", "");
				int pos = sLine.indexOf("GET+->T24.mnu");
				if (pos >= 0) {
					pos = sLine.lastIndexOf(".") + 1;
					int start = pos;
					while (pos < sLine.length() && Character.isJavaIdentifierPart(sLine.charAt(pos))) {
						pos++;
					}
					String sPointer = sLine.substring(start, pos);
					menuRef.addChild(sPointer);
				}
			}
		}
		bufR.close();
	}

	public boolean isInitialized() {
		return this.isInitialized;
	}


	class MenuReference {
		private final String resourceName;
		private final String rimName;
		private List<MenuReference> parents = new ArrayList<MenuReference>();
		private List<String> children = new ArrayList<String>();

		public MenuReference(String resourceName, String rimName) {
			this.resourceName = resourceName;
			this.rimName = rimName != null ? rimName : "";
		}

		public String getResourceName() {
			return this.resourceName;
		}

		public String getRimName() {
			return this.rimName;
		}

		public void addChild(String menu) {
			this.children.add(menu);
		}

		public List<String> getChildren() {
			return this.children;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof MenuReference)) {
				return false;
			}
			return ((MenuReference) o).getRimName().equals(this.rimName);
		}

	}

}
