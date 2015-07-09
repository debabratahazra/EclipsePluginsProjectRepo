package com.odcgroup.workbench.integration.tests.util.pageobjects;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a page object which represents a tree view in Eclipse
 * 
 * @author Kai Kreuzer
 *
 */
public class TreeObject {

	private static final Logger logger = LoggerFactory.getLogger(TreeObject.class);
	
	private SWTBotTree tree;

	public TreeObject(SWTBotTree tree) {
		this.tree = tree;
	}
	
	public SWTBotTreeItem selectTreeItem(IPath path) {
		SWTBotTreeItem item = null;
		for(SWTBotTreeItem treeItem : tree.getAllItems()) {
			if(treeItem.getText().contains(path.segment(0))) {
				item = treeItem;
				break;
			}
		}
		if(item!=null) {
			try {
				item.expand();
				path = path.removeFirstSegments(1);
				if(path.segmentCount()>0) {
					String lastSegment = path.lastSegment();
					path = path.removeLastSegments(1);
					for(String segment : path.segments()) {
						item = item.expandNode(segment);
					}
					item = item.getNode(lastSegment);
				}
				return item;
			} catch(RuntimeException e) {
				logger.info("Node '{}' not found. Available nodes are:", path.segment(0));
				System.out.println("Available nodes are:");
				for(String node : item.getNodes()) {
					logger.info(node);
					System.out.println(node);
				}
				throw e;
			}
		} else {
			logger.info("Tree item '{}' not found. Available items are:", path.segment(0));
			System.out.println("Available items are:");
			for(SWTBotTreeItem treeItem : tree.getAllItems()) {
				logger.info(treeItem.getText());
				System.out.println(treeItem.getText());
			}
			throw new WidgetNotFoundException("Tree item '" + path.segment(0) + "' not found.");
		}
	}

}
