package com.odcgroup.iris.rim.generation;

import java.util.ArrayList;

import com.odcgroup.t24.menu.menu.MenuRoot;

/**
 * TODO: Helps to pass status across mappers
 *
 * @author taubert
 *
 */
public class MappingStatus{
	
	public int status = 0;
	
	final ArrayList<MenuRoot> dependencies = new ArrayList<MenuRoot>();

	
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int n){
		this.status = n;
	}
	
	public void addDependency(MenuRoot dependency){
		dependencies.add(dependency);
	}
	
	public ArrayList<MenuRoot> getDependencies(){
		return dependencies;
	}

}