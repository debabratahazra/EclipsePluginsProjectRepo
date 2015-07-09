/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.navigator;

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

import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.EndStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.EndStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.InitStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.InitStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.PageflowEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateEditPart;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowElementTypes;
import com.odcgroup.pageflow.editor.diagram.providers.PageflowParserProvider;
import com.odcgroup.pageflow.model.Pageflow;

/**
 * @generated
 */
public class PageflowNavigatorLabelProvider extends LabelProvider implements ICommonLabelProvider,
		ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		PageflowDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		PageflowDiagramEditorPlugin.getInstance().getImageRegistry().put(
				"Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof PageflowNavigatorItem && !isOwnView(((PageflowNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof PageflowNavigatorGroup) {
			PageflowNavigatorGroup group = (PageflowNavigatorGroup) element;
			return PageflowDiagramEditorPlugin.getInstance().getBundledImage(group.getIcon());
		}

		if (element instanceof PageflowNavigatorItem) {
			PageflowNavigatorItem navigatorItem = (PageflowNavigatorItem) element;
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
		switch (PageflowVisualIDRegistry.getVisualID(view)) {
		case PageflowEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?http://www.odcgroup.com/ofs/pageflow?Pageflow", PageflowElementTypes.Pageflow_79); //$NON-NLS-1$
		case InitStateEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.odcgroup.com/ofs/pageflow?InitState", PageflowElementTypes.InitState_1001); //$NON-NLS-1$
		case DecisionStateEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.odcgroup.com/ofs/pageflow?DecisionState", PageflowElementTypes.DecisionState_1002); //$NON-NLS-1$
		case EndStateEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.odcgroup.com/ofs/pageflow?EndState", PageflowElementTypes.EndState_1003); //$NON-NLS-1$
		case ViewStateEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.odcgroup.com/ofs/pageflow?ViewState", PageflowElementTypes.ViewState_1004); //$NON-NLS-1$
		case SubPageflowStateEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?http://www.odcgroup.com/ofs/pageflow?SubPageflowState", PageflowElementTypes.SubPageflowState_1005); //$NON-NLS-1$
		case TransitionEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?http://www.odcgroup.com/ofs/pageflow?Transition", PageflowElementTypes.Transition_3001); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = PageflowDiagramEditorPlugin.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null && PageflowElementTypes.isKnownElementType(elementType)) {
			image = PageflowElementTypes.getImage(elementType);
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
		if (element instanceof PageflowNavigatorGroup) {
			PageflowNavigatorGroup group = (PageflowNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof PageflowNavigatorItem) {
			PageflowNavigatorItem navigatorItem = (PageflowNavigatorItem) element;
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
		switch (PageflowVisualIDRegistry.getVisualID(view)) {
		case PageflowEditPart.VISUAL_ID:
			return getPageflow_79Text(view);
		case InitStateEditPart.VISUAL_ID:
			return getInitState_1001Text(view);
		case DecisionStateEditPart.VISUAL_ID:
			return getDecisionState_1002Text(view);
		case EndStateEditPart.VISUAL_ID:
			return getEndState_1003Text(view);
		case ViewStateEditPart.VISUAL_ID:
			return getViewState_1004Text(view);
		case SubPageflowStateEditPart.VISUAL_ID:
			return getSubPageflowState_1005Text(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_3001Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getPageflow_79Text(View view) {
		Pageflow domainModelElement = (Pageflow) view.getElement();
		if (domainModelElement != null) {
			return domainModelElement.getName();
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("No domain element for view with visualID = " + 79); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getInitState_1001Text(View view) {
		IAdaptable hintAdapter = new PageflowParserProvider.HintAdapter(PageflowElementTypes.InitState_1001, (view
				.getElement() != null ? view.getElement() : view), PageflowVisualIDRegistry
				.getType(InitStateDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getDecisionState_1002Text(View view) {
		IAdaptable hintAdapter = new PageflowParserProvider.HintAdapter(PageflowElementTypes.DecisionState_1002, (view
				.getElement() != null ? view.getElement() : view), PageflowVisualIDRegistry
				.getType(DecisionStateDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getEndState_1003Text(View view) {
		IAdaptable hintAdapter = new PageflowParserProvider.HintAdapter(PageflowElementTypes.EndState_1003, (view
				.getElement() != null ? view.getElement() : view), PageflowVisualIDRegistry
				.getType(EndStateDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getViewState_1004Text(View view) {
		IAdaptable hintAdapter = new PageflowParserProvider.HintAdapter(PageflowElementTypes.ViewState_1004, (view
				.getElement() != null ? view.getElement() : view), PageflowVisualIDRegistry
				.getType(ViewStateDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getSubPageflowState_1005Text(View view) {
		IAdaptable hintAdapter = new PageflowParserProvider.HintAdapter(PageflowElementTypes.SubPageflowState_1005,
				(view.getElement() != null ? view.getElement() : view), PageflowVisualIDRegistry
						.getType(SubPageflowStateDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getTransition_3001Text(View view) {
		IAdaptable hintAdapter = new PageflowParserProvider.HintAdapter(PageflowElementTypes.Transition_3001, (view
				.getElement() != null ? view.getElement() : view), PageflowVisualIDRegistry
				.getType(TransitionDisplayNameEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE.intValue());
		} else {
			PageflowDiagramEditorPlugin.getInstance().logError("Parser was not found for label " + 4008); //$NON-NLS-1$
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
		return PageflowEditPart.MODEL_ID.equals(PageflowVisualIDRegistry.getModelID(view));
	}

}
