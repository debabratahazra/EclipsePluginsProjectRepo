package com.zealcore.se.core.dl;

import com.zealcore.se.core.model.IType;

public interface ITypeRegistry {

    /**
     * 
     * @param qualifiedName
     *                the qualified name of a class
     * @return the class registered by a typepackage with the qualified name
     * @throws IllegalArgumentException
     *                 if the qualifiedName is not previously registered
     */
    IType getType(String qualifiedName);

    ITypePackage getTypePackage(IType type);

    ITypePackage[] getTypePackages();

}
