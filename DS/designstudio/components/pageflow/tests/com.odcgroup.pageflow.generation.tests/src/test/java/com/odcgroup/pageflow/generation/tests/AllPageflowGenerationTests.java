package com.odcgroup.pageflow.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.pageflow.generation.tests.ocs.DecisionStateMultipleIncomingTransitionsTest;
import com.odcgroup.pageflow.generation.tests.ocs.DecisionStateTest;
import com.odcgroup.pageflow.generation.tests.ocs.InitTransitionTest;
import com.odcgroup.pageflow.generation.tests.ocs.PageflowGenerationTest;
import com.odcgroup.pageflow.generation.tests.ocs.ProcessTest;
import com.odcgroup.pageflow.generation.tests.ocs.PropertyTest;
import com.odcgroup.pageflow.generation.tests.ocs.StateTest;
import com.odcgroup.pageflow.generation.tests.ocs.StopOnFailureTest;
import com.odcgroup.pageflow.generation.tests.ocs.SubPageflowTest;
import com.odcgroup.pageflow.generation.tests.ocs.TransitionTest;
import com.odcgroup.pageflow.generation.tests.ocs.ViewTest;
import com.odcgroup.pageflow.generation.tests.pms.models.SafetyNetPmsModelsPageflowTest;


@RunWith(Suite.class)
@SuiteClasses( {DecisionStateTest.class,
	InitTransitionTest.class,
	ProcessTest.class,
	PropertyTest.class,
	StateTest.class,
	PropertyTest.class,
	SubPageflowTest.class,
	TransitionTest.class,
	ViewTest.class,
	DecisionStateMultipleIncomingTransitionsTest.class,
	PageflowGenerationTest.class,
	SafetyNetPmsModelsPageflowTest.class, 
	StopOnFailureTest.class } )
public class AllPageflowGenerationTests {

}
