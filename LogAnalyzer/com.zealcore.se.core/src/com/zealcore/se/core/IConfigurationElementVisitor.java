package com.zealcore.se.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

@Deprecated
interface IConfigurationElementVisitor {

    void visit(IConfigurationElement element) throws CoreException;
}
