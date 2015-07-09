/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

import com.odcgroup.process.diagram.edit.parts.ComplexGatewayEditPart;
import com.odcgroup.process.diagram.edit.parts.ComplexGatewayNameEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeNameEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeNameEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ProcessEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskNameEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventNameEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskNameEditPart;
import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;
import com.odcgroup.process.diagram.providers.ProcessElementTypes;
import com.odcgroup.process.diagram.providers.ProcessParserProvider;
import com.odcgroup.process.model.Process;

/**
 * @generated
 */
public class ProcessNavigatorLabelProvider extends LabelProvider implements ICommonLabelProvider,
		ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		ProcessDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		ProcessDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof ProcessNavigatorItem && !isOwnView(((ProcessNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof ProcessNavigatorGroup) {
			ProcessNavigatorGroup group = (ProcessNavigatorGroup) element;
			return ProcessDiagramEditorPlugin.getInstance().getBundledImage(group.getIcon());
		}

		if (element instanceof ProcessNavigatorItem) {
			ProcessNavigatorItem navigatorItem = (ProcessNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (ProcessVisualIDRegistry.getVisualID(view)) {
		case ProcessEditPart.VISUAL_ID:
			return getImage("Navigator?Diagram?http://www.odcgroup.com/process?Process", ProcessElementTypes.Process_79); //$NON-NLS-1$
		case PoolEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.odcgroup.com/process?Pool", ProcessElementTypes.Pool_1001); //$NON-NLS-1$
		case UserTaskEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?UserTask", ProcessElementTypes.UserTask_2001); //$NON-NLS-1$
		case ServiceTaskEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?ServiceTask", ProcessElementTypes.ServiceTask_2002); //$NON-NLS-1$
		case ComplexGatewayEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?ComplexGateway", ProcessElementTypes.ComplexGateway_2003); //$NON-NLS-1$
		case ExclusiveMergeEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?ExclusiveMerge", ProcessElementTypes.ExclusiveMerge_2004); //$NON-NLS-1$
		case ParallelForkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?ParallelFork", ProcessElementTypes.ParallelFork_2005); //$NON-NLS-1$
		case ParallelMergeEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?ParallelMerge", ProcessElementTypes.ParallelMerge_2006); //$NON-NLS-1$
		case StartEventEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?StartEvent", ProcessElementTypes.StartEvent_2007); //$NON-NLS-1$
		case EndEventEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?http://www.odcgroup.com/process?EndEvent", ProcessElementTypes.EndEvent_2008); //$NON-NLS-1$
		case FlowEditPart.VISUAL_ID:
			return getImage("Navigator?Link?http://www.odcgroup.com/process?Flow", ProcessElementTypes.Flow_3001); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = ProcessDiagramEditorPlugin.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null && ProcessElementTypes.isKnownElementType(elementType)) {
			image = ProcessElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof ProcessNavigatorGroup) {
			ProcessNavigatorGroup group = (ProcessNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof ProcessNavigatorItem) {
			ProcessNavigatorItem navigatorItem = (ProcessNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (ProcessVisualIDRegistry.getVisualID(view)) {
		case ProcessEditPart.VISUAL_ID:
			return getProcess_79Text(view);
		case PoolEditPart.VISUAL_ID:
			return getPool_1001Text(view);
		case UserTaskEditPart.VISUAL_ID:
			return getUserTask_2001Text(view);
		case ServiceTaskEditPart.VISUAL_ID:
			return getServiceTask_2002Text(view);
		case ComplexGatewayEditPart.VISUAL_ID:
			return getComplexGateway_2003Text(view);
		case ExclusiveMergeEditPart.VISUAL_ID:
			return getExclusiveMerge_2004Text(view);
		case ParallelForkEditPart.VISUAL_ID:
			return getParallelFork_2005Text(view);
		case ParallelMergeEditPart.VISUAL_ID:
			return getParallelMerge_2006Text(view);
		case StartEventEditPart.VISUAL_ID:
			return getStartEvent_2007Text(view);
		case EndEventEditPart.VISUAL_ID:
			return getEndEvent_2008Text(view);
		case FlowEditPart.VISUAL_ID:
			return getFlow_3001Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getProcess_79Text(View view) {
		Process domainModelElement = (Process) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 79); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPool_1001Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.Pool_1001, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(PoolNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4009); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getUserTask_2001Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.UserTask_2001, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(UserTaskNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getServiceTask_2002Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.ServiceTask_2002, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(ServiceTaskNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getComplexGateway_2003Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.ComplexGateway_2003, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(ComplexGatewayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getExclusiveMerge_2004Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.ExclusiveMerge_2004, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(ExclusiveMergeNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getParallelFork_2005Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.ParallelFork_2005, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(ParallelForkNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getParallelMerge_2006Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.ParallelMerge_2006, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(ParallelMergeNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getStartEvent_2007Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.StartEvent_2007, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(StartEventNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getEndEvent_2008Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.EndEvent_2008, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(EndEventNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4008); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getFlow_3001Text(View view) {
		IAdaptable hintAdapter = new ProcessParserProvider.HintAdapter(ProcessElementTypes.Flow_3001, (view
				.getElement() != null ? view.getElement() : view), ProcessVisualIDRegistry
				.getType(FlowNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			ProcessDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4010); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return ProcessEditPart.MODEL_ID.equals(ProcessVisualIDRegistry.getModelID(view));
	}

}
