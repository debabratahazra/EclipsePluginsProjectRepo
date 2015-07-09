/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.pageflow.editor.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.editor.diagram.edit.parts.DecisionStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.EndStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.InitStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDescEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.SubPageflowStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.TransitionDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDescEditPart;
import com.odcgroup.pageflow.editor.diagram.edit.parts.ViewStateDisplayNameEditPart;
import com.odcgroup.pageflow.editor.diagram.parsers.MessageFormatParser;
import com.odcgroup.pageflow.editor.diagram.part.PageflowVisualIDRegistry;
import com.odcgroup.pageflow.model.PageflowPackage;

/**
 * @generated
 */
public class PageflowParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser initStateDisplayName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getInitStateDisplayName_4001Parser() {
		if (initStateDisplayName_4001Parser == null) {
			initStateDisplayName_4001Parser = createInitStateDisplayName_4001Parser();
		}
		return initStateDisplayName_4001Parser;
	}

	/**
	 * @generated NOT
	 */
	protected IParser createInitStateDisplayName_4001Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_DisplayName(),
				PageflowPackage.eINSTANCE.getState_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser decisionStateDisplayName_4002Parser;

	/**
	 * @generated
	 */
	private IParser getDecisionStateDisplayName_4002Parser() {
		if (decisionStateDisplayName_4002Parser == null) {
			decisionStateDisplayName_4002Parser = createDecisionStateDisplayName_4002Parser();
		}
		return decisionStateDisplayName_4002Parser;
	}

	/**
	 * @generated NOT
	 */
	protected IParser createDecisionStateDisplayName_4002Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_DisplayName(),
				PageflowPackage.eINSTANCE.getState_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser endStateDisplayName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getEndStateDisplayName_4003Parser() {
		if (endStateDisplayName_4003Parser == null) {
			endStateDisplayName_4003Parser = createEndStateDisplayName_4003Parser();
		}
		return endStateDisplayName_4003Parser;
	}

	/**
	 * @generated NOT
	 */
	protected IParser createEndStateDisplayName_4003Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_DisplayName(),
				PageflowPackage.eINSTANCE.getState_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser viewStateDisplayName_4004Parser;

	/**
	 * @generated
	 */
	private IParser getViewStateDisplayName_4004Parser() {
		if (viewStateDisplayName_4004Parser == null) {
			viewStateDisplayName_4004Parser = createViewStateDisplayName_4004Parser();
		}
		return viewStateDisplayName_4004Parser;
	}

	/**
	 * @generated NOT
	 */
	protected IParser createViewStateDisplayName_4004Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_DisplayName(),
				PageflowPackage.eINSTANCE.getState_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser viewStateDesc_4005Parser;

	/**
	 * @generated
	 */
	private IParser getViewStateDesc_4005Parser() {
		if (viewStateDesc_4005Parser == null) {
			viewStateDesc_4005Parser = createViewStateDesc_4005Parser();
		}
		return viewStateDesc_4005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createViewStateDesc_4005Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_Desc(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser subPageflowStateDisplayName_4006Parser;

	/**
	 * @generated
	 */
	private IParser getSubPageflowStateDisplayName_4006Parser() {
		if (subPageflowStateDisplayName_4006Parser == null) {
			subPageflowStateDisplayName_4006Parser = createSubPageflowStateDisplayName_4006Parser();
		}
		return subPageflowStateDisplayName_4006Parser;
	}

	/**
	 * @generated NOT
	 */
	protected IParser createSubPageflowStateDisplayName_4006Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_DisplayName(),
				PageflowPackage.eINSTANCE.getState_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser subPageflowStateDesc_4007Parser;

	/**
	 * @generated
	 */
	private IParser getSubPageflowStateDesc_4007Parser() {
		if (subPageflowStateDesc_4007Parser == null) {
			subPageflowStateDesc_4007Parser = createSubPageflowStateDesc_4007Parser();
		}
		return subPageflowStateDesc_4007Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createSubPageflowStateDesc_4007Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getState_Desc(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser transitionDisplayName_4008Parser;

	/**
	 * @generated
	 */
	private IParser getTransitionDisplayName_4008Parser() {
		if (transitionDisplayName_4008Parser == null) {
			transitionDisplayName_4008Parser = createTransitionDisplayName_4008Parser();
		}
		return transitionDisplayName_4008Parser;
	}

	/**
	 * @generated NOT
	 */
	protected IParser createTransitionDisplayName_4008Parser() {
		EAttribute[] features = new EAttribute[] { PageflowPackage.eINSTANCE.getTransition_DisplayName(),
				PageflowPackage.eINSTANCE.getTransition_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case InitStateDisplayNameEditPart.VISUAL_ID:
			return getInitStateDisplayName_4001Parser();
		case DecisionStateDisplayNameEditPart.VISUAL_ID:
			return getDecisionStateDisplayName_4002Parser();
		case EndStateDisplayNameEditPart.VISUAL_ID:
			return getEndStateDisplayName_4003Parser();
		case ViewStateDisplayNameEditPart.VISUAL_ID:
			return getViewStateDisplayName_4004Parser();
		case ViewStateDescEditPart.VISUAL_ID:
			return getViewStateDesc_4005Parser();
		case SubPageflowStateDisplayNameEditPart.VISUAL_ID:
			return getSubPageflowStateDisplayName_4006Parser();
		case SubPageflowStateDescEditPart.VISUAL_ID:
			return getSubPageflowStateDesc_4007Parser();
		case TransitionDisplayNameEditPart.VISUAL_ID:
			return getTransitionDisplayName_4008Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(PageflowVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(PageflowVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (PageflowElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
