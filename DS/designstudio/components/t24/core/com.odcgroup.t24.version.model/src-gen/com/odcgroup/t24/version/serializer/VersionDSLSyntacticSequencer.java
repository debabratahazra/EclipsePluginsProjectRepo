package com.odcgroup.t24.version.serializer;

import com.google.inject.Inject;
import com.odcgroup.t24.version.services.VersionDSLGrammarAccess;
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
public class VersionDSLSyntacticSequencer extends AbstractSyntacticSequencer {

	protected VersionDSLGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Field_TranslationsKeyword_4_0_q;
	protected AbstractElementAlias match_Field___APIKeyword_5_0_LeftCurlyBracketKeyword_5_1_RightCurlyBracketKeyword_5_3__q;
	protected AbstractElementAlias match_Field___PresentationKeyword_2_0_LeftCurlyBracketKeyword_2_1_RightCurlyBracketKeyword_2_21__q;
	protected AbstractElementAlias match_Version___APIKeyword_12_0_LeftCurlyBracketKeyword_12_1_RightCurlyBracketKeyword_12_10__q;
	protected AbstractElementAlias match_Version___ConnectKeyword_16_0_LeftCurlyBracketKeyword_16_1_RightCurlyBracketKeyword_16_5__q;
	protected AbstractElementAlias match_Version___IBKeyword_13_0_LeftCurlyBracketKeyword_13_1_RightCurlyBracketKeyword_13_5__q;
	protected AbstractElementAlias match_Version___PresentationKeyword_10_0_LeftCurlyBracketKeyword_10_1_RightCurlyBracketKeyword_10_11__q;
	protected AbstractElementAlias match_Version___RelationshipKeyword_11_0_LeftCurlyBracketKeyword_11_1_RightCurlyBracketKeyword_11_4__q;
	protected AbstractElementAlias match_Version___TransactionFlowKeyword_9_0_LeftCurlyBracketKeyword_9_1_RightCurlyBracketKeyword_9_12__q;
	protected AbstractElementAlias match_Version___WebServicesKeyword_15_0_LeftCurlyBracketKeyword_15_1_RightCurlyBracketKeyword_15_7__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (VersionDSLGrammarAccess) access;
		match_Field_TranslationsKeyword_4_0_q = new TokenAlias(false, true, grammarAccess.getFieldAccess().getTranslationsKeyword_4_0());
		match_Field___APIKeyword_5_0_LeftCurlyBracketKeyword_5_1_RightCurlyBracketKeyword_5_3__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getFieldAccess().getAPIKeyword_5_0()), new TokenAlias(false, false, grammarAccess.getFieldAccess().getLeftCurlyBracketKeyword_5_1()), new TokenAlias(false, false, grammarAccess.getFieldAccess().getRightCurlyBracketKeyword_5_3()));
		match_Field___PresentationKeyword_2_0_LeftCurlyBracketKeyword_2_1_RightCurlyBracketKeyword_2_21__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getFieldAccess().getPresentationKeyword_2_0()), new TokenAlias(false, false, grammarAccess.getFieldAccess().getLeftCurlyBracketKeyword_2_1()), new TokenAlias(false, false, grammarAccess.getFieldAccess().getRightCurlyBracketKeyword_2_21()));
		match_Version___APIKeyword_12_0_LeftCurlyBracketKeyword_12_1_RightCurlyBracketKeyword_12_10__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getAPIKeyword_12_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_12_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_12_10()));
		match_Version___ConnectKeyword_16_0_LeftCurlyBracketKeyword_16_1_RightCurlyBracketKeyword_16_5__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getConnectKeyword_16_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_16_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_16_5()));
		match_Version___IBKeyword_13_0_LeftCurlyBracketKeyword_13_1_RightCurlyBracketKeyword_13_5__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getIBKeyword_13_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_13_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_13_5()));
		match_Version___PresentationKeyword_10_0_LeftCurlyBracketKeyword_10_1_RightCurlyBracketKeyword_10_11__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getPresentationKeyword_10_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_10_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_10_11()));
		match_Version___RelationshipKeyword_11_0_LeftCurlyBracketKeyword_11_1_RightCurlyBracketKeyword_11_4__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getRelationshipKeyword_11_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_11_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_11_4()));
		match_Version___TransactionFlowKeyword_9_0_LeftCurlyBracketKeyword_9_1_RightCurlyBracketKeyword_9_12__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getTransactionFlowKeyword_9_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_9_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_9_12()));
		match_Version___WebServicesKeyword_15_0_LeftCurlyBracketKeyword_15_1_RightCurlyBracketKeyword_15_7__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getVersionAccess().getWebServicesKeyword_15_0()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getLeftCurlyBracketKeyword_15_1()), new TokenAlias(false, false, grammarAccess.getVersionAccess().getRightCurlyBracketKeyword_15_7()));
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
			if(match_Field_TranslationsKeyword_4_0_q.equals(syntax))
				emit_Field_TranslationsKeyword_4_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Field___APIKeyword_5_0_LeftCurlyBracketKeyword_5_1_RightCurlyBracketKeyword_5_3__q.equals(syntax))
				emit_Field___APIKeyword_5_0_LeftCurlyBracketKeyword_5_1_RightCurlyBracketKeyword_5_3__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Field___PresentationKeyword_2_0_LeftCurlyBracketKeyword_2_1_RightCurlyBracketKeyword_2_21__q.equals(syntax))
				emit_Field___PresentationKeyword_2_0_LeftCurlyBracketKeyword_2_1_RightCurlyBracketKeyword_2_21__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___APIKeyword_12_0_LeftCurlyBracketKeyword_12_1_RightCurlyBracketKeyword_12_10__q.equals(syntax))
				emit_Version___APIKeyword_12_0_LeftCurlyBracketKeyword_12_1_RightCurlyBracketKeyword_12_10__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___ConnectKeyword_16_0_LeftCurlyBracketKeyword_16_1_RightCurlyBracketKeyword_16_5__q.equals(syntax))
				emit_Version___ConnectKeyword_16_0_LeftCurlyBracketKeyword_16_1_RightCurlyBracketKeyword_16_5__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___IBKeyword_13_0_LeftCurlyBracketKeyword_13_1_RightCurlyBracketKeyword_13_5__q.equals(syntax))
				emit_Version___IBKeyword_13_0_LeftCurlyBracketKeyword_13_1_RightCurlyBracketKeyword_13_5__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___PresentationKeyword_10_0_LeftCurlyBracketKeyword_10_1_RightCurlyBracketKeyword_10_11__q.equals(syntax))
				emit_Version___PresentationKeyword_10_0_LeftCurlyBracketKeyword_10_1_RightCurlyBracketKeyword_10_11__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___RelationshipKeyword_11_0_LeftCurlyBracketKeyword_11_1_RightCurlyBracketKeyword_11_4__q.equals(syntax))
				emit_Version___RelationshipKeyword_11_0_LeftCurlyBracketKeyword_11_1_RightCurlyBracketKeyword_11_4__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___TransactionFlowKeyword_9_0_LeftCurlyBracketKeyword_9_1_RightCurlyBracketKeyword_9_12__q.equals(syntax))
				emit_Version___TransactionFlowKeyword_9_0_LeftCurlyBracketKeyword_9_1_RightCurlyBracketKeyword_9_12__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_Version___WebServicesKeyword_15_0_LeftCurlyBracketKeyword_15_1_RightCurlyBracketKeyword_15_7__q.equals(syntax))
				emit_Version___WebServicesKeyword_15_0_LeftCurlyBracketKeyword_15_1_RightCurlyBracketKeyword_15_7__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     'Translations:'?
	 */
	protected void emit_Field_TranslationsKeyword_4_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('API' '{' '}')?
	 */
	protected void emit_Field___APIKeyword_5_0_LeftCurlyBracketKeyword_5_1_RightCurlyBracketKeyword_5_3__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('Presentation' '{' '}')?
	 */
	protected void emit_Field___PresentationKeyword_2_0_LeftCurlyBracketKeyword_2_1_RightCurlyBracketKeyword_2_21__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('API' '{' '}')?
	 */
	protected void emit_Version___APIKeyword_12_0_LeftCurlyBracketKeyword_12_1_RightCurlyBracketKeyword_12_10__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('Connect' '{' '}')?
	 */
	protected void emit_Version___ConnectKeyword_16_0_LeftCurlyBracketKeyword_16_1_RightCurlyBracketKeyword_16_5__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('IB' '{' '}')?
	 */
	protected void emit_Version___IBKeyword_13_0_LeftCurlyBracketKeyword_13_1_RightCurlyBracketKeyword_13_5__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('Presentation' '{' '}')?
	 */
	protected void emit_Version___PresentationKeyword_10_0_LeftCurlyBracketKeyword_10_1_RightCurlyBracketKeyword_10_11__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('Relationship' '{' '}')?
	 */
	protected void emit_Version___RelationshipKeyword_11_0_LeftCurlyBracketKeyword_11_1_RightCurlyBracketKeyword_11_4__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('TransactionFlow' '{' '}')?
	 */
	protected void emit_Version___TransactionFlowKeyword_9_0_LeftCurlyBracketKeyword_9_1_RightCurlyBracketKeyword_9_12__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('WebServices' '{' '}')?
	 */
	protected void emit_Version___WebServicesKeyword_15_0_LeftCurlyBracketKeyword_15_1_RightCurlyBracketKeyword_15_7__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
