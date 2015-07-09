package com.odcgroup.t24.menu.serializer;

import com.google.inject.Inject;
import com.odcgroup.t24.menu.services.MenuGrammarAccess;
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
public class MenuSyntacticSequencer extends AbstractSyntacticSequencer {

	protected MenuGrammarAccess grammarAccess;
	protected AbstractElementAlias match_MenuItem___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_2__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (MenuGrammarAccess) access;
		match_MenuItem___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMenuItemAccess().getLeftCurlyBracketKeyword_8_0()), new TokenAlias(false, false, grammarAccess.getMenuItemAccess().getRightCurlyBracketKeyword_8_2()));
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
			if(match_MenuItem___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_2__q.equals(syntax))
				emit_MenuItem___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     ('{' '}')?
	 */
	protected void emit_MenuItem___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
