package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

public class ProcessSearchRequestor extends SearchRequestor {
	
	/**
	 * types which are concrete <exlude abstract types>
	 */
	private List<SearchMatch> matches = new ArrayList<SearchMatch>();
	
	/**
	 * abstract types
	 */
	private List<SearchMatch> abstractTypes = new ArrayList<SearchMatch>();
	
	/**
	 * concrete types
	 */
	private List<SearchMatch> concreteTypes = new ArrayList<SearchMatch>();

	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {	
		int modifiers = ((IType)match.getElement()).getFlags();
		if (!Flags.isAbstract(modifiers)){
			matches.add(match);
			concreteTypes.add(match);
		} else {
			abstractTypes.add(match);
		}
	}

	
	/**
	 * @return
	 */
	public Object[] getSearchMatches() {
		Object[] objs = new Object[matches.size()];
		for (int i = 0; i < matches.size(); i++) {
			objs[i] =  matches.get(i).getElement();
		}
		return objs;
	}


	/**
	 * @return
	 */
	public List<SearchMatch> getAbstractTypes() {
		return abstractTypes;
	}

	/**
	 * 
	 * @return
	 */
	public List<SearchMatch> getConcreteTypes(){
		return concreteTypes;
	}
}
