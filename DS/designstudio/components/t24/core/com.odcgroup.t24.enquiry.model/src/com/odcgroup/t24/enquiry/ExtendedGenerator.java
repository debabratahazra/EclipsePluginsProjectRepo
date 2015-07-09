package com.odcgroup.t24.enquiry;

import org.eclipse.xtext.XtextRuntimeModule;
import org.eclipse.xtext.XtextStandaloneSetup;
import org.eclipse.xtext.generator.Generator;
import org.eclipse.xtext.xtext.ecoreInference.IXtext2EcorePostProcessor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.odcgroup.t24.enquiry.generator.DefaultValueEcoreProcessor;

@SuppressWarnings("restriction")
public class ExtendedGenerator extends Generator {

    public ExtendedGenerator() {
        new XtextStandaloneSetup() {
            @Override
            public Injector createInjector() {
                return Guice.createInjector(new XtextRuntimeModule() {
                    @Override
                    public Class<? extends IXtext2EcorePostProcessor> bindIXtext2EcorePostProcessor() {
                        return DefaultValueEcoreProcessor.class;
                    }
                });
            }
        }.createInjectorAndDoEMFRegistration();
    }
}
