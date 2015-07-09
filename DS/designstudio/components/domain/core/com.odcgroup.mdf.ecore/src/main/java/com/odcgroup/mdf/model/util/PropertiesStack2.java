package com.odcgroup.mdf.model.util;

import java.util.Stack;

import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfPropertyImpl;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.PathVisitor;

/**
 * Like com.odcgroup.mdf.utils.PropertiesStack, but using MdfPropertyImpl
 * basicGetType() instead of getType() and basicGetBaseClass() instead of
 * getBaseClass() so that it's not resolving EMF proxies.
 * 
 * @see http://rd.oams.com/browse/DS-7337
 * 
 * @author Michael Vorburger
 */
public class PropertiesStack2 extends PathVisitor {
	
    private final MdfDataset root;
    private MdfClass currentClass;
    private final Stack<MdfProperty> stack = new Stack<MdfProperty>();

    public static Stack<MdfProperty> getStack(MdfDatasetImpl root, String path) {
        PropertiesStack2 visitor = new PropertiesStack2(root);
        PathVisitor.visitPath(path, visitor);
        return visitor.getStack();
    }

    private PropertiesStack2(MdfDatasetImpl root) {
        this.root = root;
        this.currentClass = root.basicGetBaseClass();  // sic, NOT root.getBaseClass() 
    }

    public boolean visit(String name) {
        MdfPropertyImpl prop = (MdfPropertyImpl) currentClass.getProperty(name);

// Commented out, as ModelHelper.getReverseAssociation() in MDF Core will not correctly deal with EMF proxies; this would lead to a StackOverflowError when called from DomainJvmModelInferrer      
//        if (prop == null) {
//            prop = ModelHelper.getReverseAssociation(root.getParentDomain(), currentClass, name);
//        }
        
        if (prop == null){
            throw new RuntimeException("Property '"+name+"' not found for class "+currentClass.toString()); // use toString() instead of getName() as that's clearer err. msg. in case it's a proxy
        }

        stack.push(prop);

        if (prop != null) {
            MdfEntity type = prop.basicGetType(); // sic, NOT prop.getType() 
            if (type instanceof MdfClass) {
                currentClass = (MdfClass) type;
                return true;
            }
        }

        return false;
    }

    public Stack<MdfProperty> getStack() {
        return stack;
    }
}
