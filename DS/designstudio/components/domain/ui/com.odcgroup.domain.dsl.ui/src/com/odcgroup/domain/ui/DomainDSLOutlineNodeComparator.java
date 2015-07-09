package com.odcgroup.domain.ui;

import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.SortOutlineContribution.DefaultComparator;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

import com.odcgroup.mdf.ecore.MdfPackage;

public class DomainDSLOutlineNodeComparator extends DefaultComparator {
	@Override
    public int getCategory(IOutlineNode node) {
        if (node instanceof EObjectNode) {
        	int id = ((EObjectNode)node).getEClass().getClassifierID(); 
            switch(id) {
            case MdfPackage.MDF_DOMAIN:    
                return -120;
            case MdfPackage.MDF_BUSINESS_TYPE:    
                return -110;
            case MdfPackage.MDF_CLASS:    
                return -100;
            case MdfPackage.MDF_PROPERTY:    
                return -90;
            case MdfPackage.MDF_ASSOCIATION:    
                return -80;
            case MdfPackage.MDF_ATTRIBUTE:    
                return -70;
            case MdfPackage.MDF_REVERSE_ASSOCIATION:    
                return -60;
            case MdfPackage.MDF_DATASET:    
                return -50;
            case MdfPackage.MDF_DATASET_DERIVED_PROPERTY:    
                return -40;
            case MdfPackage.MDF_DATASET_MAPPED_PROPERTY:    
                return -30;
            case MdfPackage.MDF_ENUMERATION:    
                return -20;
            case MdfPackage.MDF_ENUM_VALUE:    
                return -10;
            }
        }
        return Integer.MIN_VALUE;
    }
}
