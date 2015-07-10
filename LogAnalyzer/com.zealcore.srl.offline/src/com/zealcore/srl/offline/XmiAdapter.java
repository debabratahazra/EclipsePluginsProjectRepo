package com.zealcore.srl.offline;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmiAdapter extends DefaultHandler {

    private static final String MACHINE = "ownedBehavior";

    private static final String NAME = "name";

    private static final String NODE = "subvertex";

    private static final String TYPE = "xmi:type";

    private static final String ID = "xmi:id";

    private int depth;

    private final Map<String, Integer> idMap = new HashMap<String, Integer>();

    private int idCounter;

    private final Map<String, String> ports = new HashMap<String, String>();

    private final HashMap<String, UmlStateMachine> stateMachineMap;

    private UmlStateMachine currentMachine;

    private UmlState currentState;

    public XmiAdapter(final HashMap<String, UmlStateMachine> stateMachineMap) {
        this.stateMachineMap = stateMachineMap;
    }

    public void parse(final File f) throws ParserConfigurationException,
            SAXException, IOException {

        final SAXParserFactory factory = SAXParserFactory.newInstance();

        final SAXParser sax = factory.newSAXParser();

        sax.parse(f, this);

    }

    @Override
    public void startElement(final String uri, final String localName,
            final String name, final Attributes attributes) throws SAXException {
        final String stateName = attributes.getValue(XmiAdapter.NAME);
        if (XmiAdapter.MACHINE.equals(name)) {
            if ("uml:StateMachine".equals(attributes.getValue(XmiAdapter.TYPE))) {
                // Create new StateMachine

                // System.out.println(stateName);
                currentMachine = new UmlStateMachine(stateName);
                currentMachine.setId(getId(attributes.getValue(XmiAdapter.ID)));
                stateMachineMap.put(stateName, currentMachine);
            }
        } else if (XmiAdapter.NODE.equals(name)) {
            depth++;
            if ("uml:State".equals(attributes.getValue(XmiAdapter.TYPE))) {
                // Create new State

                // for (int i = 0; i < depth; i++) {
                // System.out.print("\t");
                // }
                // System.out.println(stateName);
                currentState = new UmlState(stateName,
                        currentState != null ? currentState : null, depth);
                currentState.setId(getId(attributes.getValue(XmiAdapter.ID)));
                currentMachine.add(currentState);

                ports.put(attributes.getValue(XmiAdapter.ID), stateName);
            }
        } else if (name.equals("transition")) {
            final int sourceID = getId(attributes.getValue("source").trim());
            final int targetID = getId(attributes.getValue("target").trim());
            final UmlTransition umlTransition = new UmlTransition(sourceID,
                    targetID);
            umlTransition.setId(getId(attributes.getValue(XmiAdapter.ID)));
            currentMachine.add(umlTransition);
        }

        super.startElement(uri, localName, name, attributes);
    }

    private int getId(final String key) {
        if (!idMap.containsKey(key)) {
            idMap.put(key, idCounter++);
        }
        return idMap.get(key);
    }

    @Override
    public void endElement(final String uri, final String localName,
            final String name) throws SAXException {

        if (XmiAdapter.MACHINE.equals(name)) {
            depth = 0;
            currentState = null;
            currentMachine = null;
            // ts.clear();
        }
        if (XmiAdapter.NODE.equals(name)) {
            depth--;
            if (currentState != null
                    && currentState.getParent() instanceof UmlState) {
                currentState = (UmlState) currentState.getParent();
            }

        }
        super.endElement(uri, localName, name);
    }

    public void setOut(final PrintStream out) {}
}
