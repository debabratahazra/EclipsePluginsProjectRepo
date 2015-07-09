/*
 * Odyssey Financial Technologies
 */
package com.odcgroup.process.diagram.providers;

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

import com.odcgroup.process.diagram.edit.parts.ComplexGatewayNameEditPart;
import com.odcgroup.process.diagram.edit.parts.EndEventNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ExclusiveMergeNameEditPart;
import com.odcgroup.process.diagram.edit.parts.FlowNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelForkNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ParallelMergeNameEditPart;
import com.odcgroup.process.diagram.edit.parts.PoolNameEditPart;
import com.odcgroup.process.diagram.edit.parts.ServiceTaskNameEditPart;
import com.odcgroup.process.diagram.edit.parts.StartEventNameEditPart;
import com.odcgroup.process.diagram.edit.parts.UserTaskNameEditPart;
import com.odcgroup.process.diagram.parsers.MessageFormatParser;
import com.odcgroup.process.diagram.part.ProcessVisualIDRegistry;
import com.odcgroup.process.model.ProcessPackage;

/**
 * @generated
 */
public class ProcessParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser poolName_4009Parser;

	/**
	 * @generated
	 */
	private IParser getPoolName_4009Parser() {
		if (poolName_4009Parser == null) {
			poolName_4009Parser = createPoolName_4009Parser();
		}
		return poolName_4009Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createPoolName_4009Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser userTaskName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getUserTaskName_4001Parser() {
		if (userTaskName_4001Parser == null) {
			userTaskName_4001Parser = createUserTaskName_4001Parser();
		}
		return userTaskName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createUserTaskName_4001Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser serviceTaskName_4002Parser;

	/**
	 * @generated
	 */
	private IParser getServiceTaskName_4002Parser() {
		if (serviceTaskName_4002Parser == null) {
			serviceTaskName_4002Parser = createServiceTaskName_4002Parser();
		}
		return serviceTaskName_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createServiceTaskName_4002Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser complexGatewayName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getComplexGatewayName_4003Parser() {
		if (complexGatewayName_4003Parser == null) {
			complexGatewayName_4003Parser = createComplexGatewayName_4003Parser();
		}
		return complexGatewayName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createComplexGatewayName_4003Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser exclusiveMergeName_4004Parser;

	/**
	 * @generated
	 */
	private IParser getExclusiveMergeName_4004Parser() {
		if (exclusiveMergeName_4004Parser == null) {
			exclusiveMergeName_4004Parser = createExclusiveMergeName_4004Parser();
		}
		return exclusiveMergeName_4004Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createExclusiveMergeName_4004Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser parallelForkName_4005Parser;

	/**
	 * @generated
	 */
	private IParser getParallelForkName_4005Parser() {
		if (parallelForkName_4005Parser == null) {
			parallelForkName_4005Parser = createParallelForkName_4005Parser();
		}
		return parallelForkName_4005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createParallelForkName_4005Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser parallelMergeName_4006Parser;

	/**
	 * @generated
	 */
	private IParser getParallelMergeName_4006Parser() {
		if (parallelMergeName_4006Parser == null) {
			parallelMergeName_4006Parser = createParallelMergeName_4006Parser();
		}
		return parallelMergeName_4006Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createParallelMergeName_4006Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser startEventName_4007Parser;

	/**
	 * @generated
	 */
	private IParser getStartEventName_4007Parser() {
		if (startEventName_4007Parser == null) {
			startEventName_4007Parser = createStartEventName_4007Parser();
		}
		return startEventName_4007Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createStartEventName_4007Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser endEventName_4008Parser;

	/**
	 * @generated
	 */
	private IParser getEndEventName_4008Parser() {
		if (endEventName_4008Parser == null) {
			endEventName_4008Parser = createEndEventName_4008Parser();
		}
		return endEventName_4008Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEndEventName_4008Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getNamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser flowName_4010Parser;

	/**
	 * @generated
	 */
	private IParser getFlowName_4010Parser() {
		if (flowName_4010Parser == null) {
			flowName_4010Parser = createFlowName_4010Parser();
		}
		return flowName_4010Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createFlowName_4010Parser() {
		EAttribute[] features = new EAttribute[] { ProcessPackage.eINSTANCE.getFlow_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case PoolNameEditPart.VISUAL_ID:
			return getPoolName_4009Parser();
		case UserTaskNameEditPart.VISUAL_ID:
			return getUserTaskName_4001Parser();
		case ServiceTaskNameEditPart.VISUAL_ID:
			return getServiceTaskName_4002Parser();
		case ComplexGatewayNameEditPart.VISUAL_ID:
			return getComplexGatewayName_4003Parser();
		case ExclusiveMergeNameEditPart.VISUAL_ID:
			return getExclusiveMergeName_4004Parser();
		case ParallelForkNameEditPart.VISUAL_ID:
			return getParallelForkName_4005Parser();
		case ParallelMergeNameEditPart.VISUAL_ID:
			return getParallelMergeName_4006Parser();
		case StartEventNameEditPart.VISUAL_ID:
			return getStartEventName_4007Parser();
		case EndEventNameEditPart.VISUAL_ID:
			return getEndEventName_4008Parser();
		case FlowNameEditPart.VISUAL_ID:
			return getFlowName_4010Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(ProcessVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(ProcessVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (ProcessElementTypes.getElement(hint) == null) {
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
