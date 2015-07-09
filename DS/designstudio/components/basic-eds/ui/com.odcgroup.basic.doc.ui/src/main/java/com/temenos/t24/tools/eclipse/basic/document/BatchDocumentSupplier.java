package com.temenos.t24.tools.eclipse.basic.document;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

import com.temenos.t24.tools.eclipse.basic.document.parser.BatchInfo;
import com.temenos.t24.tools.eclipse.basic.document.parser.BatchesWrapper;
import com.temenos.t24.tools.eclipse.basic.document.parser.JobInfo;
import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;
import com.temenos.t24.tools.eclipse.basic.views.document.DocumentViewConstants;

public class BatchDocumentSupplier {

    private BatchDocumentSupplier() {
    }

    public static String getBatchFileDocument(String product, String component, String file) {
        Document document = getDocument(product, component);
        if (document == null) {
            return null;
        }
        String xpath = "//batches/batch[@name='" + file + "']";
        Node node = XmlUtil.getSingleNode(document, xpath);
        String name = XmlUtil.getAttributeValue(node, "name");
        String xpathDesc = xpath + "/description";
        String batchDesc = XmlUtil.getText(document, xpathDesc);
        xpath = xpath + "/jobs/job";
        List<Node> jobNodes = XmlUtil.getNodes(document, xpath);
        String jobDetails = "";
        for (Node jobNode : jobNodes) {
            String jobName = XmlUtil.getAttributeValue(jobNode, "name");
            String xpathJob = "description";
            String jobDesc = XmlUtil.getChildNodeValue(jobNode, xpathJob);
            jobDetails = jobDetails + DocumentViewConstants.jobName +" "+ jobName + "\n" + DocumentViewConstants.jobDesc +" "+ jobDesc
                    + "\n\n";
        }
        name = DocumentViewConstants.batchName +" "+ name + "\n" + DocumentViewConstants.batchDesc +" "+ batchDesc + "\n\n" + jobDetails;
        return name;
    }

    @SuppressWarnings("unchecked")
	public static BatchesWrapper getBatches(String product, String component) {
        // String batchDesc = null;
        // String jobDesc = null;
        BatchesWrapper batchesWrapper = new BatchesWrapper(component);
        Document document = getDocument(product, component);
        if (document == null) {
            return null;
        }
        List<Node> list = document.selectNodes("//batch");
        for (Node node : list) {
            String batchName = XmlUtil.getAttributeValue(node, "name");
            String xpathDesc = "description";
            String batchDesc = XmlUtil.getChildNodeValue(node, xpathDesc);
            BatchInfo batchInfo = BatchInfo.newBatch(batchName, batchDesc);
            xpathDesc = "jobs/job";
            List<Node> jobNodeList = XmlUtil.getNodes(node, xpathDesc);
            for (Node jobNode : jobNodeList) {
                String jobName = XmlUtil.getAttributeValue(jobNode, "name");
                xpathDesc = "description";
                String jobDesc = XmlUtil.getChildNodeValue(jobNode, xpathDesc);
                JobInfo jobInfo = JobInfo.newJob(jobName, jobDesc);
                batchInfo.addJob(jobInfo);
            }
            batchesWrapper.addBatch(batchInfo);
        }
        return batchesWrapper;
    }

    private static Document getDocument(String product, String component) {
        String path = DocumentFileUtil.getCobFilePath(product, component);
        return XmlUtil.loadDocument(path);
    }
}
