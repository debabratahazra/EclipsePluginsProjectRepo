/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;

import com.odcgroup.process.diagram.providers.ProcessElementTypes;

/**
 * @generated
 */
public class ProcessPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createPool1Group());
		paletteRoot.add(createActivities2Group());
		paletteRoot.add(createGateway3Group());
		paletteRoot.add(createConnections4Group());
		paletteRoot.add(createEvents5Group());
		paletteRoot.add(createSpecialGateways6Group());
	}

	/**
	 * Creates "Pool" palette tool group
	 * @generated
	 */
	private PaletteContainer createPool1Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Pool1Group_title);
		paletteContainer.add(createPool1CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Activities" palette tool group
	 * @generated
	 */
	private PaletteContainer createActivities2Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Activities2Group_title);
		paletteContainer.setDescription(Messages.Activities2Group_desc);
		paletteContainer.add(createUserTask1CreationTool());
		paletteContainer.add(createServiceTask2CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Gateway" palette tool group
	 * @generated
	 */
	private PaletteContainer createGateway3Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Gateway3Group_title);
		paletteContainer.setDescription(Messages.Gateway3Group_desc);
		paletteContainer.add(createComplexGateway1CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Connections" palette tool group
	 * @generated
	 */
	private PaletteContainer createConnections4Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Connections4Group_title);
		paletteContainer.setDescription(Messages.Connections4Group_desc);
		paletteContainer.add(createFlow1CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Events" palette tool group
	 * @generated
	 */
	private PaletteContainer createEvents5Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.Events5Group_title);
		paletteContainer.setDescription(Messages.Events5Group_desc);
		paletteContainer.add(createStartEvent1CreationTool());
		paletteContainer.add(createEndEvent2CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Special Gateways" palette tool group
	 * @generated
	 */
	private PaletteContainer createSpecialGateways6Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer(Messages.SpecialGateways6Group_title);
		paletteContainer.setDescription(Messages.SpecialGateways6Group_desc);
		paletteContainer.add(createExclusiveMerge1CreationTool());
		paletteContainer.add(createParallelFork2CreationTool());
		paletteContainer.add(createParallelMerge3CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPool1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.Pool_1001);
		NodeToolEntry entry = new NodeToolEntry(Messages.Pool1CreationTool_title, Messages.Pool1CreationTool_desc,
				types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.Pool_1001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createUserTask1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.UserTask_2001);
		NodeToolEntry entry = new NodeToolEntry(Messages.UserTask1CreationTool_title,
				Messages.UserTask1CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.UserTask_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createServiceTask2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.ServiceTask_2002);
		NodeToolEntry entry = new NodeToolEntry(Messages.ServiceTask2CreationTool_title,
				Messages.ServiceTask2CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.ServiceTask_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createComplexGateway1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.ComplexGateway_2003);
		NodeToolEntry entry = new NodeToolEntry(Messages.ComplexGateway1CreationTool_title,
				Messages.ComplexGateway1CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.ComplexGateway_2003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createParallelFork2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.ParallelFork_2005);
		NodeToolEntry entry = new NodeToolEntry(Messages.ParallelFork2CreationTool_title,
				Messages.ParallelFork2CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.ParallelFork_2005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createFlow1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.Flow_3001);
		LinkToolEntry entry = new LinkToolEntry(Messages.Flow1CreationTool_title, Messages.Flow1CreationTool_desc,
				types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.Flow_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createStartEvent1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.StartEvent_2007);
		NodeToolEntry entry = new NodeToolEntry(Messages.StartEvent1CreationTool_title,
				Messages.StartEvent1CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.StartEvent_2007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createEndEvent2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.EndEvent_2008);
		NodeToolEntry entry = new NodeToolEntry(Messages.EndEvent2CreationTool_title,
				Messages.EndEvent2CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.EndEvent_2008));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createExclusiveMerge1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.ExclusiveMerge_2004);
		NodeToolEntry entry = new NodeToolEntry(Messages.ExclusiveMerge1CreationTool_title,
				Messages.ExclusiveMerge1CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.ExclusiveMerge_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createParallelMerge3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(ProcessElementTypes.ParallelMerge_2006);
		NodeToolEntry entry = new NodeToolEntry(Messages.ParallelMerge3CreationTool_title,
				Messages.ParallelMerge3CreationTool_desc, types);
		entry.setSmallIcon(ProcessElementTypes.getImageDescriptor(ProcessElementTypes.ParallelMerge_2006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

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
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

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
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
