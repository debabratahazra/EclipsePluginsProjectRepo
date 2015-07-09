package com.odcgroup.ocs.support.xpatch;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Patch extends DocumentUtils {

	private final File patchfile;
	private final Document patch;

	public Patch(File patchfile) throws SAXException, IOException {
		this.patchfile = patchfile;
		this.patch = parse(patchfile);
	}

	public File getPatchfile() {
		return patchfile;
	}

	/**
	 * Patch XML document with a given patch file.
	 * 
	 * @param configuration
	 *            Original document
	 * 
	 * @return True, if the document was successfully patched
	 * @throws IOException
	 * @throws TransformerException
	 * @throws DOMException
	 */
	@SuppressWarnings("restriction")
	boolean apply(final Document configuration) throws DOMException,
			TransformerException, IOException {
		Element elem = patch.getDocumentElement();

		// Get 'root' node were 'component' will be inserted into
		String xpath = elem.getAttribute("xpath");
		NodeList nodes = com.sun.org.apache.xpath.internal.XPathAPI.selectNodeList(configuration, xpath);

		// Suspend, because the xpath returned not one node
		if (nodes.getLength() != 1) {
			return false;
		}
		Node root = nodes.item(0);

		// Test that 'root' node satisfies 'component' insertion criteria
		String testPath = elem.getAttribute("unless-path");
		if (testPath == null || testPath.length() == 0) {
			// only look for old "unless" attr if unless-path is not present
			testPath = elem.getAttribute("unless");
		}

		if (testPath != null && testPath.length() > 0
				&& com.sun.org.apache.xpath.internal.XPathAPI.selectNodeList(root, testPath).getLength() != 0) {
			return false;
		} else {
			// Test if component wants us to remove a list of nodes first
			xpath = elem.getAttribute("remove");

			if (xpath != null && xpath.length() > 0) {
				nodes = com.sun.org.apache.xpath.internal.XPathAPI.selectNodeList(configuration, xpath);

				for (int i = 0, length = nodes.getLength(); i < length; i++) {
					Node node = nodes.item(i);
					Node parent = node.getParentNode();

					parent.removeChild(node);
				}
			}

			// Test for an attribute that needs to be added to an element
			String name = elem.getAttribute("add-attribute");
			String value = elem.getAttribute("value");

			if (name != null && name.length() > 0) {
				if (value == null) {
					throw new IOException(
							"No attribute value specified for 'add-attribute' "
									+ xpath);
				}

				if (root instanceof Element) {
					((Element) root).setAttribute(name, value);
				}
			}

			// Allow multiple attributes to be added or modified
			if (root instanceof Element) {
				NamedNodeMap attrMap = elem.getAttributes();
				for (int i = 0; i < attrMap.getLength(); ++i) {
					Attr attr = (Attr) attrMap.item(i);
					final String addAttr = "add-attribute-";
					if (attr.getName().startsWith(addAttr)) {
						String key = attr.getName().substring(addAttr.length());
						((Element) root).setAttribute(key, attr.getValue());
					}
				}
			}

			// Test if 'component' provides desired insertion point
			xpath = elem.getAttribute("insert-before");
			Node before = null;

			if (xpath != null && xpath.length() > 0) {
				nodes = com.sun.org.apache.xpath.internal.XPathAPI.selectNodeList(root, xpath);
				if (nodes.getLength() == 0) {
					throw new IOException("XPath (" + xpath
							+ ") returned zero nodes");
				}
				before = nodes.item(0);
			} else {
				xpath = elem.getAttribute("insert-after");
				if (xpath != null && xpath.length() > 0) {
					nodes = com.sun.org.apache.xpath.internal.XPathAPI.selectNodeList(root, xpath);
					if (nodes.getLength() == 0) {
						throw new IOException("XPath (" + xpath
								+ ") zero nodes.");
					}
					before = nodes.item(nodes.getLength() - 1).getNextSibling();
				}
			}

			// Add 'component' data into 'root' node
			NodeList componentNodes = patch.getDocumentElement()
					.getChildNodes();

			insertComment(configuration, root, "Start patching from "
					+ patchfile, before);

			for (int i = 0; i < componentNodes.getLength(); i++) {
				insertNode(configuration, root, componentNodes.item(i), before);
			}

			insertComment(configuration, root,
					"End patching from " + patchfile, before);
			return true;
		}
	}

	private void insertComment(Document owner, Node root, String comment,
			Node before) {
		insertNode(owner, root, owner.createTextNode("\n"), before);
		insertNode(owner, root, owner.createComment(comment), before);
		insertNode(owner, root, owner.createTextNode("\n"), before);
	}

	private void insertNode(Document owner, Node root, Node node, Node before) {
		if (node.getOwnerDocument() != owner) {
			node = owner.importNode(node, true);
		}

		if (before == null) {
			root.appendChild(node);
		} else {
			root.insertBefore(node, before);
		}
	}

}
