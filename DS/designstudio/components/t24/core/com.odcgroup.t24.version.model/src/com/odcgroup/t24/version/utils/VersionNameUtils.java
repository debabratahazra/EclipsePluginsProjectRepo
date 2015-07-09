package com.odcgroup.t24.version.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.workbench.dsl.xml.NameURISwapperImpl;

public class VersionNameUtils {

    
    /**
     * Returns the QualifiedName for the Version's forApplication Field, so e.g. Domain.Class.
     */
    public static QualifiedName getVersionForApplication(Version version) {
        List<INode> nodes = NodeModelUtils.findNodesForFeature(version, VersionDSLPackage.Literals.VERSION__FOR_APPLICATION);
        if (nodes.size() == 1) {
            INode node = nodes.get(0);
			return QualifiedName.create(node.getText());
        } else if (nodes.size() == 0) {
            MdfClassImpl forApplication = (MdfClassImpl)  version.getForApplication();
            if (forApplication == null)
                return null;
            // This is to handle name:/ URI conversion; @see NameURISwapper
            if (forApplication.eIsProxy()) {
                String crossRefString = NameURISwapperImpl.getNameFromProxy(forApplication);
                if (crossRefString != null) {                	
                    return QualifiedName.create(crossRefString);
                } else {
					throw new IllegalArgumentException(
							"Version EObject doesn't have a forApplication Node so we can't "
									+ "get out the tokenText for the QualifiedName (and it's still a Proxy, but not a name:/ URI), "
									+ "proxy / object : "
									+ EcoreUtil.getURI(forApplication) + " / "
									+ version);
                }
            } else {
              // This will "normally" (with real Xtext resources/objects) never happen,
              // but could happen if the EObject has been programmatically constructed (without Nodes)
              MdfName mdfName = forApplication.getQualifiedName();
              final String mdfDomainName = mdfName.getDomain();
              final String mdfLocalName = mdfName.getLocalName();
              return QualifiedName.create(mdfDomainName, mdfLocalName);
            }
        } else {
            throw new IllegalArgumentException("Version EObject unexpectedly has more than 1 forApplication Nodes: " + version);
        }
    }
    

    
    /**
     * Returns the QualifiedName for the Version's next version name
     */
    public static QualifiedName getNextVersion(Version version) {
    	List<INode> nodes = null;
	    nodes = NodeModelUtils.findNodesForFeature(version, VersionDSLPackage.Literals.VERSION__NEXT_VERSION);
        if (nodes.size() > 0) {
			INode node = nodes.get(0);
			return QualifiedName.create(node.getText());
        }
        else {
            throw new IllegalArgumentException("No next versions available for version : " + version);
        }
    }
    
    /**
     * Returns the QualifiedName for the Version's next version name
     */
    public static List<QualifiedName> getAssociatedVersions(Version version) {
    	List<QualifiedName> qualifedNames = new ArrayList<QualifiedName>();
    	List<INode> nodes = NodeModelUtils.findNodesForFeature(version, VersionDSLPackage.Literals.VERSION__ASSOCIATED_VERSIONS);
        if (nodes.size() > 0) {
			for (INode node : nodes) {
				qualifedNames.add(QualifiedName.create(node.getText()));
			}
        }
		return qualifedNames;
    }
    
    /**
     * Returns the QualifiedName for the Version's associatedVersions name
     */
    public static QualifiedName getVersionForAssociation(Version version, int refId) {
        List<INode> nodes = NodeModelUtils.findNodesForFeature(version, VersionDSLPackage.Literals.VERSION__ASSOCIATED_VERSIONS);
        if (nodes.size() > 0) {
			INode node = nodes.get(refId);
			return QualifiedName.create(node.getText());
        }
        else {
            throw new IllegalArgumentException("No associated versions available for version : " + version);
        }
    }
   
    /**
     * Returns the QualifiedName for the Version's confirm Versions name
     */
    public static QualifiedName getIBVersions(Version version, int key) {
    	List<INode> nodes = null;
    	switch(key){
	    	case 0: nodes = NodeModelUtils.findNodesForFeature(version, VersionDSLPackage.Literals.VERSION__CONFIRM_VERSION);break;
	    	case 1: nodes = NodeModelUtils.findNodesForFeature(version, VersionDSLPackage.Literals.VERSION__PREVIEW_VERSION);break;
	    	default : break;
    	};
        if (nodes.size() > 0) {
			INode node = nodes.get(0);
			return QualifiedName.create(node.getText());
        }
        else {
            throw new IllegalArgumentException("No Internet banking versions available for version : " + version);
        }
    }

}
