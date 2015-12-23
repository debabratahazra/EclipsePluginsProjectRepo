package com.ifx.dave.monitor.svd;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.ifx.dave.monitor.MainClass;

public class SvdFileParsing {

    private static final String DERIVED_FROM = "derivedFrom";
    private static String NAME = "name";
    private static String DESCRIPTION = "description";
    private static String PERIPHERALS = "peripherals";
    private static String PERIPHERAL = "peripheral";
    private static String ADDRESSBLOCK = "addressBlock";
    private static String REGISTERS = "registers";
    private static String BASEADDRESS = "baseAddress";

    /*
     * public static void main(String[] arg) { SvdFileParsing svdfile = new
     * SvdFileParsing(); svdfile.readSvdfile(); }
     */

    public void readSvdfile() {
        Map<String, Object> perpheralsMap = null;
        Kryo kryo = null;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            System.out.println();
            // 1. Transform
            File registerFile = new File("Registerfile.bin");
            if ((registerFile == null) || (!registerFile.exists())) {
                File transFilePath = getTransformedSvdFile(
                        "XMC1400.svd");
                // Logger log = Logger.getLogger(
                // loggingSample2.class.getName());
                // loggingSample2.log.debug("Hello this is an debug message");
                try {
                    DocumentBuilderFactory documentFactory = DocumentBuilderFactory
                            .newInstance();
                    DocumentBuilder documentBuilder = documentFactory
                            .newDocumentBuilder();
                    Document doc = documentBuilder.parse(transFilePath);
                    doc.getDocumentElement().normalize();
                    Element node = doc.getDocumentElement();
                    processFirstNode(node);
                    // System.out.println(node.getNodeName());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                long diff = System.currentTimeMillis() - currentTimeMillis;
                System.out.println(diff);
                // Delete file
                transFilePath.delete();
            } else {
                kryo = new Kryo();
                Input input = new Input(
                        new FileInputStream("Registerfile.bin"));
                perpheralsMap = kryo.readObject(input, HashMap.class);
                MainClass.setPeripheralsMap(perpheralsMap);
                input.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void processFirstNode(Element node) {
        NodeList childNodes = node.getChildNodes();
        for (int temp = 0; temp < childNodes.getLength(); temp++) {
            Node node1 = childNodes.item(temp);
            if (node1.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node1.getNodeName();
                String textContent = node1.getTextContent();
                System.out.println(nodeName);
                System.out.println(textContent);
                if (nodeName.equalsIgnoreCase(PERIPHERALS)) {
                    CopyPeripherals copyperipherals = new CopyPeripherals();
                    copyperipherals.processPeripherals(node1);
                }
            }
        }
    }

    private File getTransformedSvdFile(String svdFileLocation) {
        File fileLoc = new File(svdFileLocation);
        File transFilePath = new File(
                "C:\\Work\\Workspace\\JavaFx\\DaveMonitor\\" + "Trans_"
                        + (fileLoc.getName()).toString());
        try {
            Map<String, String> KeyValue = new HashMap<String, String>();
            ArrayList<String> srcPeripheral = new ArrayList<String>();
            ArrayList<String> destPeripheral = new ArrayList<String>();
            Node destPeripheral1 = null;
            Node srcPeripheral1 = null;
            Map<String, String> underScoreValue = new HashMap<String, String>();
            Map<String, String> separateValue = new HashMap<String, String>();
            Map<String, String> groupNameValue = new HashMap<String, String>();
            ArrayList<String> PeripheralList = new ArrayList<String>();
            ArrayList<String> Peripheral_List = new ArrayList<String>();
            ArrayList<String> PeripheralsepList = new ArrayList<String>();
            ArrayList<String> PeripheralNewList = new ArrayList<String>();
            ArrayList<String> myList = new ArrayList<String>();
            ArrayList<String> PeripheralupdateList = new ArrayList<String>();
            NodeList nodeList;
            Document doc;
            Node parentNode;
            Node peripheralsNode;

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentFactory
                    .newDocumentBuilder();
            doc = documentBuilder.parse(svdFileLocation);
            doc.getDocumentElement().normalize();
            peripheralsNode = doc.getElementsByTagName(PERIPHERALS).item(0);
            nodeList = doc.getElementsByTagName(PERIPHERAL);

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element peripheral = (Element) node;
                    destPeripheral.add(peripheral.getElementsByTagName(NAME)
                            .item(0).getTextContent());
                    srcPeripheral.add(peripheral.getAttribute(DERIVED_FROM));
                    if (peripheral.getAttribute(DERIVED_FROM) != "") {
                        KeyValue.put(
                                peripheral.getElementsByTagName(NAME).item(0)
                                        .getTextContent(),
                                peripheral.getAttribute(DERIVED_FROM));
                    }
                }
            }

            for (Entry<String, String> entry : KeyValue.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                for (int temp = 0; temp < nodeList.getLength(); temp++) {
                    Node node = nodeList.item(temp);
                    String peripheral_name = get_peripheral_name(node);
                    if (peripheral_name.equals(key)) {
                        destPeripheral1 = node;
                    }
                    if (peripheral_name.equals(value)) {
                        srcPeripheral1 = ((Element) node)
                                .getElementsByTagName(REGISTERS).item(0);
                    }
                }
                destPeripheral1.appendChild(srcPeripheral1.cloneNode(true));
                ((Element) destPeripheral1).removeAttribute(DERIVED_FROM);
            }
            /* ADDED FROM HERE */

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element peripheral = (Element) node;
                    String name = peripheral.getElementsByTagName("name")
                            .item(0).getTextContent();
                    if (!name.contains("_")) {
                        PeripheralList.add(name);
                    } else if (name.contains("_")) {
                        Peripheral_List.add(name);
                    }
                }
            }
            for (String i : Peripheral_List) {
                for (String j : PeripheralList) {
                    if ((i.split("_")[0].equals(j))) {
                        myList.add(i);
                    }
                }
            }
            // createMaps(PeripheralList,Peripheral_List,underScoreValue,doc);
            PeripheralNewList = (ArrayList<String>) Peripheral_List.clone();
            PeripheralNewList.removeAll(myList);
            for (String i : PeripheralList) {
                for (String j : PeripheralNewList) {
                    if ((get_group_from_name(i, nodeList)) != null) {
                        // groupNameValue.put(i,get_group_from_name(i,
                        // nodeList));
                        if (j.contains(get_group_from_name(i, nodeList))) {
                            groupNameValue.put(j, i);
                        }
                    }
                }
            }
            for (String key : groupNameValue.keySet()) {
                for (String j : PeripheralNewList) {
                    if (j.equals(key)) {
                        PeripheralsepList.add(j);
                    }
                }
            }
            PeripheralupdateList = (ArrayList<String>) PeripheralNewList
                    .clone();
            PeripheralupdateList.removeAll(PeripheralsepList);
            /*
             * if ((PeripheralList !=null) && (Peripheral_List !=null)){
             * createMaps(PeripheralList,Peripheral_List,underScoreValue,doc); }
             */
            if (underScoreValue.entrySet() != null) {
                try {
                    createMaps(PeripheralList, Peripheral_List, underScoreValue,
                            doc);
                    processMap(underScoreValue, nodeList, doc, peripheralsNode);
                    cleanDoc(peripheralsNode, underScoreValue.keySet(),
                            nodeList);
                } catch (Exception e) {

                }
            }
            // System.out.println("groupnamevalue: "+groupNameValue);

            if (groupNameValue.entrySet() != null) {
                try {
                    processMap(groupNameValue, nodeList, doc, peripheralsNode);
                    cleanDoc(peripheralsNode, groupNameValue.keySet(),
                            nodeList);
                } catch (Exception e) {

                }
            }

            System.out.println("PeripheralupdateList: " + PeripheralupdateList);
            /*
             * if (PeripheralupdateList !=null){
             * createSeparateMaps(PeripheralupdateList, separateValue);
             */
            if (separateValue.entrySet() != null) {
                try {
                    createSeparateMaps(PeripheralupdateList, separateValue);
                    processMap(separateValue, nodeList, doc, peripheralsNode);
                    cleanDoc(peripheralsNode, separateValue.keySet(), nodeList);
                } catch (Exception e) {
                }
            }
            // }
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(transFilePath);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return transFilePath;
    }

    private String get_peripheral_name(Node x) {
        NodeList nodelist = x.getChildNodes();
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node node = nodelist.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName() == "name") {
                    return node.getFirstChild().getNodeValue();
                }
            }
        }
        return null;
    }

    private String get_group_from_name(String name, NodeList nodelist) {
        Node n = get_peripheral_from_name(name, nodelist);
        return get_group_name(n);
    }

    private void processMap(Map<String, String> keyValue, NodeList nodeList,
            Node doc, Node peripheralsNode) {
        String suffix = null;
        for (Entry<String, String> entry : keyValue.entrySet()) {
            String derived = entry.getKey();
            int lastIndexOf = derived.lastIndexOf("_");

            if (-1 != lastIndexOf) {
                suffix = derived.substring(lastIndexOf + 1, derived.length());
            } else {
                suffix = derived;
            }

            Node derivedPeripheral = get_peripheral_from_name(derived,
                    nodeList);
            String derivedFrom = entry.getValue();
            Node derivedFromPeripheral = get_peripheral_from_name(derivedFrom,
                    nodeList);
            int deltaBaseAddess = compute_baseAddress(derivedPeripheral,
                    derivedFromPeripheral);
            NodeList registers = ((Element) derivedPeripheral)
                    .getElementsByTagName("register");
            Node derivedFromPeripheral_reg = ((Element) derivedFromPeripheral)
                    .getElementsByTagName("registers").item(0);
            for (int i = 0; i < registers.getLength(); i++) {
                modifyRegister(registers.item(i), deltaBaseAddess, suffix);
                derivedFromPeripheral_reg
                        .appendChild(registers.item(i).cloneNode(true));
            }

        }
    }

    private void cleanDoc(Node perNode, Set<String> nameList,
            NodeList nodeList) {
        for (String s : nameList) {
            perNode.removeChild(get_peripheral_from_name(s, nodeList));
        }
    }

    private Node get_peripheral_from_name(String name, NodeList nodelist) {
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            if (get_peripheral_name(nodelist.item(temp)) == name)
                return nodelist.item(temp);

        }
        return null;
    }

    private Map<String, String> createMaps(ArrayList<String> aList,
            ArrayList<String> bList, Map<String, String> mapName,
            Document doc) {
        for (String i : aList) {
            for (String j : bList) {
                if ((i.equals(j.split("_")[0]))) {
                    mapName.put(j, i);
                }
            }
        }
        return mapName;
    }

    private Map<String, String> createSeparateMaps(ArrayList<String> slist,
            Map<String, String> sMap) {
        String j = slist.get(0);// .replace("_", "");

        slist.remove(slist.get(0));
        for (String i : slist) {
            if (j.split("_")[0].equals(i.split("_")[0])) {
                sMap.put(i, j);
            }
        }
        return sMap;
    }

    private String get_group_name(Node x) {
        NodeList nodelist = x.getChildNodes();
        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node node = nodelist.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getNodeName() == "groupName") {

                    return node.getTextContent();
                }
            }
        }
        return null;
    }

    private int compute_baseAddress(Node a, Node b) {
        String baseAddress1 = getBaseAddress(a);
        String baseAddress2 = getBaseAddress(b);
        if (baseAddress1.startsWith("0x")) {
            baseAddress1 = baseAddress1.replace("0x", "");
        }
        baseAddress1 = baseAddress1.trim();
        if (baseAddress2.startsWith("0x")) {
            baseAddress2 = baseAddress2.replace("0x", "");
        }
        baseAddress2 = baseAddress2.trim();

        int result = (Integer.valueOf(baseAddress1, 16)
                - Integer.valueOf(baseAddress2, 16));
        return result;
    }

    private void modifyRegister(Node item, int deltaBaseAddess, String suffix) {
        // TODO Auto-generated method stub
        String offAdd = getOffsetAddress(item);
        if (offAdd.startsWith("0x")) {
            offAdd = offAdd.replace("0x", "");
        }
        offAdd = offAdd.trim();
        int regBaseAddress = Integer.valueOf(offAdd, 16);
        regBaseAddress += deltaBaseAddess;
        String regName = suffix.concat("_").concat(getName(item));
        // String regName = suffix.concat(getName(item));
        ((Element) item).getElementsByTagName("name").item(0)
                .setTextContent(regName);
        ((Element) item).getElementsByTagName("addressOffset").item(0)
                .setTextContent("0x" + (Integer.toHexString(regBaseAddress)));
    }

    private String getOffsetAddress(Node x) {
        return ((Element) x).getElementsByTagName("addressOffset").item(0)
                .getTextContent();
    }

    private String getBaseAddress(Node x) {
        return ((Element) x).getElementsByTagName("baseAddress").item(0)
                .getTextContent();
    }

    private String getName(Node x) {
        return ((Element) x).getElementsByTagName("name").item(0)
                .getTextContent();
    }

}
