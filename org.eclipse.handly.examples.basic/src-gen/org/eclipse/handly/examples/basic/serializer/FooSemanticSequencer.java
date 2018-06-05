/*
 * generated by Xtext
 */
package org.eclipse.handly.examples.basic.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.handly.examples.basic.foo.Def;
import org.eclipse.handly.examples.basic.foo.FooPackage;
import org.eclipse.handly.examples.basic.foo.Var;
import org.eclipse.handly.examples.basic.services.FooGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class FooSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private FooGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == FooPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case FooPackage.DEF:
				sequence_Def(context, (Def) semanticObject); 
				return; 
			case FooPackage.MODULE:
				sequence_Module(context, (org.eclipse.handly.examples.basic.foo.Module) semanticObject); 
				return; 
			case FooPackage.VAR:
				sequence_Var(context, (Var) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Contexts:
	 *     Def returns Def
	 *
	 * Constraint:
	 *     (name=ID params+=ID? params+=ID*)
	 */
	protected void sequence_Def(ISerializationContext context, Def semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Module returns Module
	 *
	 * Constraint:
	 *     ((vars+=Var+ defs+=Def+) | defs+=Def+)?
	 */
	protected void sequence_Module(ISerializationContext context, org.eclipse.handly.examples.basic.foo.Module semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Contexts:
	 *     Var returns Var
	 *
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_Var(ISerializationContext context, Var semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, FooPackage.Literals.VAR__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, FooPackage.Literals.VAR__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getVarAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
}
