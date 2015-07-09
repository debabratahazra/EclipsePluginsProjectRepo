package com.odcgroup.integrationfwk.ui.decorators;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IResource;

/**
 * 
 * 
 * This class is used as a manager to organize the resources that need to be
 * decorated.
 * 
 */
public class DecoratorManager {
	/**
	 * The resources that are to be decorated
	 */
	private static List resourcesToBeUpdated_ = new Vector();

	public static void addSuccessResources(IResource resource) {
		resourcesToBeUpdated_.add(resource);
	}

	public static void addSuccessResources(List successResourceList) {
		resourcesToBeUpdated_ = new Vector();
		resourcesToBeUpdated_.addAll(successResourceList);
	}

	public static void appendSuccessResources(List successResourceList) {
		resourcesToBeUpdated_.addAll(successResourceList);
	}

	public static boolean contains(IResource resource) {
		return resourcesToBeUpdated_.contains(resource);
	}

	public static List getSuccessResources() {
		return resourcesToBeUpdated_;
	}

	private static void printSuccessResources() {
		Iterator i = resourcesToBeUpdated_.iterator();
		System.out.println("The resources that are updated ");
		while (i.hasNext()) {
			IResource resource = (IResource) i.next();
			System.out.println(resource.getName());
		}
	}

	public static void removeResource(IResource resource) {
		if (resourcesToBeUpdated_.contains(resource)) {
			resourcesToBeUpdated_.remove(resource);
		}
	}

	/**
	 * Constructor for DemoDecoratorManager.
	 */
	public DecoratorManager() {
	}

}
