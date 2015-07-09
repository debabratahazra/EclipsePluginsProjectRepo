package com.odcgroup.page.serializer;

import com.google.inject.Inject;
import com.odcgroup.page.services.PageGrammarAccess;
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
public abstract class AbstractPageSyntacticSequencer extends AbstractSyntacticSequencer {

	protected PageGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Event___LeftCurlyBracketKeyword_9_0_RightCurlyBracketKeyword_9_3__q;
	protected AbstractElementAlias match_Snippet___LeftCurlyBracketKeyword_4_0_RightCurlyBracketKeyword_4_2__q;
	protected AbstractElementAlias match_Widget___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_4__q;

	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (PageGrammarAccess) access;
		match_Event___LeftCurlyBracketKeyword_9_0_RightCurlyBracketKeyword_9_3__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getEventAccess().getLeftCurlyBracketKeyword_9_0()), new TokenAlias(false, false, grammarAccess.getEventAccess().getRightCurlyBracketKeyword_9_3()));
		match_Snippet___LeftCurlyBracketKeyword_4_0_RightCurlyBracketKeyword_4_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getSnippetAccess().getLeftCurlyBracketKeyword_4_0()), new TokenAlias(false, false, grammarAccess.getSnippetAccess().getRightCurlyBracketKeyword_4_2()));
		match_Widget___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_4__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getWidgetAccess().getLeftCurlyBracketKeyword_8_0()), new TokenAlias(false, false, grammarAccess.getWidgetAccess().getRightCurlyBracketKeyword_8_4()));
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
			if(match_Event___LeftCurlyBracketKeyword_9_0_RightCurlyBracketKeyword_9_3__q.equals(syntax))
				emit_Event___LeftCurlyBracketKeyword_9_0_RightCurlyBracketKeyword_9_3__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Snippet___LeftCurlyBracketKeyword_4_0_RightCurlyBracketKeyword_4_2__q.equals(syntax))
				emit_Snippet___LeftCurlyBracketKeyword_4_0_RightCurlyBracketKeyword_4_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Widget___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_4__q.equals(syntax))
				emit_Widget___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_4__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     ('{' '}')?
	 */
	protected void emit_Event___LeftCurlyBracketKeyword_9_0_RightCurlyBracketKeyword_9_3__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}

	/**
	 * Syntax:
	 *     ('{' '}')?
	 */
	protected void emit_Snippet___LeftCurlyBracketKeyword_4_0_RightCurlyBracketKeyword_4_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}

	/**
	 * Syntax:
	 *     ('{' '}')?
	 */
	protected void emit_Widget___LeftCurlyBracketKeyword_8_0_RightCurlyBracketKeyword_8_4__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}

}
