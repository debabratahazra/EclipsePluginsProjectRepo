package com.ifx.dave.monitor.svd;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class CopyPeripherals {

    // Map<String, String> fieldMap = new HashMap<String, String>();
    private String peripheralName;
    private static final String DERIVED_FROM = "derivedFrom";
    private static String NAME = "name";
    private static String DESCRIPTION = "description";
    private static String PERIPHERALS = "peripherals";
    private static String PERIPHERAL = "peripheral";
    private static String ADDRESSBLOCK = "addressBlock";
    private static String REGISTERS = "registers";
    private static String BASEADDRESS = "baseAddress";
    private static String DIM = "dim";
    private static String DIMINCREMENT = "dimIncrement";
    private static String FIELDS = "fields";
    private static String RESETVALUE = "resetvalue";
    Peripheral peripheral;
    Register register;
    BitField bitfield;
    Map<String, Peripheral> peripheralMap = new HashMap<String, Peripheral>();

    public void processPeripherals(Node peripherals) {
        NodeList childNodes = peripherals.getChildNodes();
        for (int temp = 0; temp < childNodes.getLength(); temp++) {
            Node peripheralNode = childNodes.item(temp);
            if (peripheralNode.getNodeType() == Node.ELEMENT_NODE) {
                if (peripheralNode.hasAttributes()) {
                    processDerivedFromPeripheral(peripheralNode);
                } else if (!(peripheralNode.hasAttributes())) {
                    processPeripheral(peripheralNode);
                }
            }
        }
    }

    private void processDerivedFromPeripheral(Node peripheralNode) {
        // TODO Auto-generated method stub

    }

    public void processPeripheral(Node priphealNode) {
        try {
            peripheral = new Peripheral();
            NodeList peripheralChilds = priphealNode.getChildNodes();
            for (int temp = 0; temp < peripheralChilds.getLength(); temp++) {
                Node node = peripheralChilds.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeName = node.getNodeName();
                    if (nodeName.equalsIgnoreCase(NAME)) {
                        String value = node.getTextContent();
                        int lastIndexOf = value.lastIndexOf("_");
                        if (-1 != lastIndexOf) {
                            peripheralName = value.substring(lastIndexOf + 1,
                                    value.length());
                        } else {
                            peripheralName = value;
                            peripheral.setPeripheralName(peripheralName);
                            String peri = peripheral.getPeripheralName();
                            System.out.println("Peripheral Name=" + peri);
                        }

                    } else if (nodeName.equalsIgnoreCase(REGISTERS)) {
                        NodeList registerNodeList = node.getChildNodes();
                        for (int index = 0; index < registerNodeList
                                .getLength(); index++) {
                            Node rNode = registerNodeList.item(index);
                            if (rNode.getNodeType() == Node.ELEMENT_NODE) {
                                processRegister(peripheral, rNode);
                            }
                        }
                    }

                }

            }
            if (null != peripheralName) {
                peripheralMap.put(peripheralName, peripheral);
            }
            Kryo kryo = new Kryo();
            Output output = new Output(
                    new FileOutputStream("Registerfile.bin"));
            kryo.writeObject(output, peripheralMap);
            output.close();

        } catch (

        Exception e)

        {
            e.printStackTrace();

        }

    }

    private void processRegister(Peripheral copyperipheral, Node registerNode) {
        try {
            if (registerNode.getTextContent().contains("[%s]")) {
                NodeList childNodes1 = registerNode.getChildNodes();
                int dim = 0, dimIncrement = 0;
                for (int temp1 = 0; temp1 < childNodes1.getLength(); temp1++) {
                    Node regNode1 = childNodes1.item(temp1);
                    String nodeName1 = regNode1.getNodeName();
                    String nodeValue1 = regNode1.getTextContent();
                    if (nodeName1.equalsIgnoreCase(DIM)) {
                        dim = Integer.parseInt(nodeValue1, 10);
                    }
                    if (nodeName1.equalsIgnoreCase(DIMINCREMENT)) {
                        dimIncrement = Integer.parseInt(nodeValue1, 10);
                    }
                    if (dim != 0 && dimIncrement != 0) {
                        // processRegister(registerNode, dim, dimIncrement);
                        return;
                    }
                }
            }
            register = new Register();
            NodeList childNodes = registerNode.getChildNodes();

            for (int temp = 0; temp < childNodes.getLength(); temp++) {
                Node regNode = childNodes.item(temp);
                if (regNode.getNodeType() == Node.ELEMENT_NODE) {

                    String nodeName = regNode.getNodeName();
                    String nodeValue = regNode.getTextContent();
                    if (nodeName.equalsIgnoreCase(NAME)) {
                        // regParameter.setName(getRegisterParamName(nodeValue));
                        register.setRegisterName(nodeValue);

                    } else if (nodeName.equalsIgnoreCase(RESETVALUE)) {
                        register.setDefaultValue(nodeValue);
                        register.setResetValue(nodeValue);
                    } else if (nodeName.equalsIgnoreCase(FIELDS)) {
                        NodeList bitfields = regNode.getChildNodes();
                        for (int index = 0; index < bitfields
                                .getLength(); index++) {
                            Node bitFieldNode = bitfields.item(index);
                            if (null != bitFieldNode) {
                                if (bitFieldNode
                                        .getNodeType() == Node.ELEMENT_NODE) {
                                    processFields(register, bitFieldNode);
                                }
                            }

                        }
                    }

                }
                peripheral.reisterMap.put(register.getRegisterName(), register);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void processFields(Register copyregister, Node bitFieldNode) {
        try {
            bitfield = new BitField();
            NodeList childNodes = bitFieldNode.getChildNodes();
            for (int temp = 0; temp < childNodes.getLength(); temp++) {
                Node bitNode = childNodes.item(temp);

                if (bitNode.getNodeType() == Node.ELEMENT_NODE) {
                    String nodeName = bitNode.getNodeName();
                    String nodeValue = bitNode.getTextContent();

                    if (nodeName.equalsIgnoreCase(NAME)) {
                        bitfield.setBitfiledName(nodeValue);
                    }

                }
                register.bitfieldMap.put(bitfield.getBitfiledName(), bitfield);
            }

        } catch (

        Exception e)

        {
            e.printStackTrace();

        }
    }

}
