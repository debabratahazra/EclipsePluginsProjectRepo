/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import com.odcgroup.pageflow.editor.diagram.custom.palette.ILockableToolEntry;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;

/**
 * @generated
 */
public class PageflowPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createStates1Group());
		paletteRoot.add(createTransitions2Group());
	}

	/**
	 * Creates "States" palette tool group
	 * @generated
	 */
	private PaletteContainer createStates1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.States1Group_title);
		paletteContainer.setDescription(Messages.States1Group_desc);
		paletteContainer.add(createEndState1CreationTool());
		paletteContainer.add(createInitState2CreationTool());
		paletteContainer.add(createViewState3CreationTool());
		paletteContainer.add(createDecisionState4CreationTool());
		paletteContainer.add(createSubPageflow5CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Transitions" palette tool group
	 * @generated
	 */
	private PaletteContainer createTransitions2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Transitions2Group_title);
		paletteContainer.setDescription(Messages.Transitions2Group_desc);
		paletteContainer.add(createTransition1CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createEndState1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PageflowElementTypes.EndState_1003);
		NodeToolEntry entry = new NodeToolEntry(Messages.EndState1CreationTool_title,
				Messages.EndState1CreationTool_desc, types);
		entry.setSmallIcon(PageflowElementTypes.getImageDescriptor(PageflowElementTypes.EndState_1003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInitState2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PageflowElementTypes.InitState_1001);
		NodeToolEntry entry = new NodeToolEntry(Messages.InitState2CreationTool_title,
				Messages.InitState2CreationTool_desc, types);
		entry.setSmallIcon(PageflowElementTypes.getImageDescriptor(PageflowElementTypes.InitState_1001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createViewState3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PageflowElementTypes.ViewState_1004);
		NodeToolEntry entry = new NodeToolEntry(Messages.ViewState3CreationTool_title,
				Messages.ViewState3CreationTool_desc, types);
		entry.setSmallIcon(PageflowElementTypes.getImageDescriptor(PageflowElementTypes.ViewState_1004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createDecisionState4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PageflowElementTypes.DecisionState_1002);
		NodeToolEntry entry = new NodeToolEntry(Messages.DecisionState4CreationTool_title,
				Messages.DecisionState4CreationTool_desc, types);
		entry.setSmallIcon(PageflowElementTypes.getImageDescriptor(PageflowElementTypes.DecisionState_1002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSubPageflow5CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PageflowElementTypes.SubPageflowState_1005);
		NodeToolEntry entry = new NodeToolEntry(Messages.SubPageflow5CreationTool_title,
				Messages.SubPageflow5CreationTool_desc, types);
		entry.setSmallIcon(PageflowElementTypes.getImageDescriptor(PageflowElementTypes.SubPageflowState_1005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createTransition1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(PageflowElementTypes.Transition_3001);
		LinkToolEntry entry = new LinkToolEntry(Messages.Transition1CreationTool_title,
				Messages.Transition1CreationTool_desc, types);
		entry.setSmallIcon(PageflowElementTypes.getImageDescriptor(PageflowElementTypes.Transition_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * OFSFMK-217 Add the possibility to "lock" palette item
	 * implement ILockableToolEntry interface
	 * @generated NOT
	 */
	private static class NodeToolEntry extends ToolEntry implements ILockableToolEntry {

		private boolean lockable = false;

		/**
		 * @generated
		 */
		private final List elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description, List elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * override <handleFinished> so that the current tool will remain active (locked)
		 * @generated NOT
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes) {
				protected void handleFinished() {
					if (isLockable()) {
						reactivate();
					} else {
						super.handleFinished();
					}
				}
			};
			tool.setProperties(getToolProperties());
			return tool;
		}

		public boolean isLockable() {
			return lockable;
		}

		public void setLockable(boolean lockable) {
			this.lockable = lockable;
		}
	}

	/**
	 * OFSFMK-217 Add the possibility to "lock" palette item
	 * implement ILockableToolEntry interface
	 * @generated NOT
	 */
	private static class LinkToolEntry extends ToolEntry implements ILockableToolEntry {

		private boolean lockable = false;

		/**
		 * @generated
		 */
		private final List relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description, List relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * OFSFMK-217 Add the possibility to "lock" palette item
		 * override <handleFinished> so that the current tool will remain active (locked)
		 * @generated NOT
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes) {
				protected void handleFinished() {
					if (isLockable()) {
						reactivate();
					} else {
						super.handleFinished();
					}
				}
			};
			tool.setProperties(getToolProperties());
			return tool;
		}

		public boolean isLockable() {
			return lockable;
		}

		public void setLockable(boolean lockable) {
			this.lockable = lockable;
		}
	}
}
