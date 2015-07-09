package com.odcgroup.service.model.serializer;

import com.google.inject.Inject;
import com.odcgroup.service.model.services.ComponentGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class ComponentSyntacticSequencer extends AbstractSyntacticSequencer {

	protected ComponentGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Table___FieldsKeyword_7_0_LeftCurlyBracketKeyword_7_1_RightCurlyBracketKeyword_7_3__q;

	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (ComponentGrammarAccess) access;
		match_Table___FieldsKeyword_7_0_LeftCurlyBracketKeyword_7_1_RightCurlyBracketKeyword_7_3__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getTableAccess().getFieldsKeyword_7_0()), new TokenAlias(false, false, grammarAccess.getTableAccess().getLeftCurlyBracketKeyword_7_1()), new TokenAlias(false, false, grammarAccess.getTableAccess().getRightCurlyBracketKeyword_7_3()));
	}

	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}


	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_Table___FieldsKeyword_7_0_LeftCurlyBracketKeyword_7_1_RightCurlyBracketKeyword_7_3__q.equals(syntax))
				emit_Table___FieldsKeyword_7_0_LeftCurlyBracketKeyword_7_1_RightCurlyBracketKeyword_7_3__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     ('fields:' '{' '}')?
	 */
	protected void emit_Table___FieldsKeyword_7_0_LeftCurlyBracketKeyword_7_1_RightCurlyBracketKeyword_7_3__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}

}
