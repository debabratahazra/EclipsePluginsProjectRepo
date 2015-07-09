package com.odcgroup.domain.serializer;

import com.google.inject.Inject;
import com.odcgroup.domain.services.DomainGrammarAccess;
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
public class DomainSyntacticSequencer extends AbstractSyntacticSequencer {

	protected DomainGrammarAccess grammarAccess;
	protected AbstractElementAlias match_MdfAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q;
	protected AbstractElementAlias match_MdfAttribute___DeprecationInfoKeyword_10_0_MdfDeprecationInfoParserRuleCall_10_1__q;
	protected AbstractElementAlias match_MdfBusinessType___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q;
	protected AbstractElementAlias match_MdfClass_SecuredKeyword_2_q;
	protected AbstractElementAlias match_MdfClass___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q;
	protected AbstractElementAlias match_MdfDatasetDerivedProperty___DeprecationInfoKeyword_7_0_MdfDeprecationInfoParserRuleCall_7_1__q;
	protected AbstractElementAlias match_MdfDatasetMappedProperty___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q;
	protected AbstractElementAlias match_MdfDataset___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q;
	protected AbstractElementAlias match_MdfDataset___LeftCurlyBracketKeyword_7_0_RightCurlyBracketKeyword_7_2__q;
	protected AbstractElementAlias match_MdfDomain___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q;
	protected AbstractElementAlias match_MdfEnumValue___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q;
	protected AbstractElementAlias match_MdfEnumeration___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q;
	protected AbstractElementAlias match_MdfReverseAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (DomainGrammarAccess) access;
		match_MdfAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfAssociationAccess().getDeprecationInfoKeyword_11_0()), new TokenAlias(false, false, grammarAccess.getMdfAssociationAccess().getMdfDeprecationInfoParserRuleCall_11_1()));
		match_MdfAttribute___DeprecationInfoKeyword_10_0_MdfDeprecationInfoParserRuleCall_10_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfAttributeAccess().getDeprecationInfoKeyword_10_0()), new TokenAlias(false, false, grammarAccess.getMdfAttributeAccess().getMdfDeprecationInfoParserRuleCall_10_1()));
		match_MdfBusinessType___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfBusinessTypeAccess().getDeprecationInfoKeyword_5_0()), new TokenAlias(false, false, grammarAccess.getMdfBusinessTypeAccess().getMdfDeprecationInfoParserRuleCall_5_1()));
		match_MdfClass_SecuredKeyword_2_q = new TokenAlias(false, true, grammarAccess.getMdfClassAccess().getSecuredKeyword_2());
		match_MdfClass___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfClassAccess().getDeprecationInfoKeyword_6_0()), new TokenAlias(false, false, grammarAccess.getMdfClassAccess().getMdfDeprecationInfoParserRuleCall_6_1()));
		match_MdfDatasetDerivedProperty___DeprecationInfoKeyword_7_0_MdfDeprecationInfoParserRuleCall_7_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfDatasetDerivedPropertyAccess().getDeprecationInfoKeyword_7_0()), new TokenAlias(false, false, grammarAccess.getMdfDatasetDerivedPropertyAccess().getMdfDeprecationInfoParserRuleCall_7_1()));
		match_MdfDatasetMappedProperty___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfDatasetMappedPropertyAccess().getDeprecationInfoKeyword_8_0()), new TokenAlias(false, false, grammarAccess.getMdfDatasetMappedPropertyAccess().getMdfDeprecationInfoParserRuleCall_8_1()));
		match_MdfDataset___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfDatasetAccess().getDeprecationInfoKeyword_6_0()), new TokenAlias(false, false, grammarAccess.getMdfDatasetAccess().getMdfDeprecationInfoParserRuleCall_6_1()));
		match_MdfDataset___LeftCurlyBracketKeyword_7_0_RightCurlyBracketKeyword_7_2__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfDatasetAccess().getLeftCurlyBracketKeyword_7_0()), new TokenAlias(false, false, grammarAccess.getMdfDatasetAccess().getRightCurlyBracketKeyword_7_2()));
		match_MdfDomain___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfDomainAccess().getDeprecationInfoKeyword_8_0()), new TokenAlias(false, false, grammarAccess.getMdfDomainAccess().getMdfDeprecationInfoParserRuleCall_8_1()));
		match_MdfEnumValue___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfEnumValueAccess().getDeprecationInfoKeyword_5_0()), new TokenAlias(false, false, grammarAccess.getMdfEnumValueAccess().getMdfDeprecationInfoParserRuleCall_5_1()));
		match_MdfEnumeration___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfEnumerationAccess().getDeprecationInfoKeyword_6_0()), new TokenAlias(false, false, grammarAccess.getMdfEnumerationAccess().getMdfDeprecationInfoParserRuleCall_6_1()));
		match_MdfReverseAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getMdfReverseAssociationAccess().getDeprecationInfoKeyword_11_0()), new TokenAlias(false, false, grammarAccess.getMdfReverseAssociationAccess().getMdfDeprecationInfoParserRuleCall_11_1()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if(ruleCall.getRule() == grammarAccess.getMdfDeprecationInfoRule())
			return getMdfDeprecationInfoToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * MdfDeprecationInfo:
	 * 	'version=' String_Value 'comment=' String_Value;
	 */
	protected String getMdfDeprecationInfoToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "version=\"\"comment=";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_MdfAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q.equals(syntax))
				emit_MdfAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfAttribute___DeprecationInfoKeyword_10_0_MdfDeprecationInfoParserRuleCall_10_1__q.equals(syntax))
				emit_MdfAttribute___DeprecationInfoKeyword_10_0_MdfDeprecationInfoParserRuleCall_10_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfBusinessType___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q.equals(syntax))
				emit_MdfBusinessType___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfClass_SecuredKeyword_2_q.equals(syntax))
				emit_MdfClass_SecuredKeyword_2_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfClass___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q.equals(syntax))
				emit_MdfClass___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfDatasetDerivedProperty___DeprecationInfoKeyword_7_0_MdfDeprecationInfoParserRuleCall_7_1__q.equals(syntax))
				emit_MdfDatasetDerivedProperty___DeprecationInfoKeyword_7_0_MdfDeprecationInfoParserRuleCall_7_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfDatasetMappedProperty___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q.equals(syntax))
				emit_MdfDatasetMappedProperty___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfDataset___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q.equals(syntax))
				emit_MdfDataset___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfDataset___LeftCurlyBracketKeyword_7_0_RightCurlyBracketKeyword_7_2__q.equals(syntax))
				emit_MdfDataset___LeftCurlyBracketKeyword_7_0_RightCurlyBracketKeyword_7_2__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfDomain___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q.equals(syntax))
				emit_MdfDomain___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfEnumValue___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q.equals(syntax))
				emit_MdfEnumValue___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfEnumeration___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q.equals(syntax))
				emit_MdfEnumeration___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_MdfReverseAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q.equals(syntax))
				emit_MdfReverseAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfAttribute___DeprecationInfoKeyword_10_0_MdfDeprecationInfoParserRuleCall_10_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfBusinessType___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     'secured'?
	 */
	protected void emit_MdfClass_SecuredKeyword_2_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfClass___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfDatasetDerivedProperty___DeprecationInfoKeyword_7_0_MdfDeprecationInfoParserRuleCall_7_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfDatasetMappedProperty___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfDataset___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('{' '}')?
	 */
	protected void emit_MdfDataset___LeftCurlyBracketKeyword_7_0_RightCurlyBracketKeyword_7_2__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfDomain___DeprecationInfoKeyword_8_0_MdfDeprecationInfoParserRuleCall_8_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfEnumValue___DeprecationInfoKeyword_5_0_MdfDeprecationInfoParserRuleCall_5_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfEnumeration___DeprecationInfoKeyword_6_0_MdfDeprecationInfoParserRuleCall_6_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     ('deprecationInfo' MdfDeprecationInfo)?
	 */
	protected void emit_MdfReverseAssociation___DeprecationInfoKeyword_11_0_MdfDeprecationInfoParserRuleCall_11_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
