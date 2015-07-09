package com.temenos.t24.tools.eclipse.basic.views.variables;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.utils.DocumentViewerUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;

public class VariablesListBuilderTest {

    private static final String DEFAULT_FILE_NAME = "testfiles\\viewMgrTest_Variables.txt";
    private static IDocument document = null;

    @Before
    public void setUp() {
        String location = "spring/test.plugin.spring.xml";
        ClassPathXmlApplicationContext springApplicationContext = new ClassPathXmlApplicationContext(location);
        T24BasicPlugin.setSpringApplicationContext(springApplicationContext);
        document = getTestDocument();
    }

    @Test
	public void testGetInstance() {
        VariablesListBuilder builder = null;
        assertTrue(builder == null);
        builder = VariablesListBuilder.getInstance();
        assertTrue(builder != null);
    }

    @Test
	public void testGetVariableViewItems() {
        VariablesListBuilder builder = VariablesListBuilder.getInstance();
        ArrayList<IT24ViewItem> variablesList = new ArrayList<IT24ViewItem>();
        variablesList = builder.getVariableViewItems(document);
        assertTrue(variablesList.size() == 26);
        assertTrue(variablesList.get(0).getLabel().equals("record"));
        assertTrue(variablesList.get(10).getLength() == 7);
        assertTrue(variablesList.get(5).getLabel().equals("VM"));
        assertTrue(variablesList.get(5).getOffset() == 244);
        assertTrue(variablesList.get(7).getLabel().equals("key"));
        assertTrue(variablesList.get(13).getLength() == 10);
        assertTrue(variablesList.get(19).getLabel().equals("temperature"));
        assertTrue(variablesList.get(18).getOffset() == 561);
        assertTrue(variablesList.get(19).getLabel().equals(variablesList.get(25).getLabel()));
        assertTrue(variablesList.get(25).getOffset() == 689);
        assertTrue(variablesList.get(24).getLabel().equals(variablesList.get(9).getLabel()));
        assertTrue(variablesList.get(23).getLength() == 4);
    }

    @Test
	public void testGetVariables() {
        VariablesListBuilder builder = VariablesListBuilder.getInstance();
        ArrayList<String> variables = builder.getVariables(document, "t");
        assertTrue(variables.size() == 4);
        assertTrue(variables.get(0).equals("tempKey"));
        assertTrue(variables.get(1).equals("temperature"));
        assertTrue(variables.get(2).equals("tempRecord"));
        assertTrue(variables.get(3).equals("time"));
        variables = builder.getVariables(document, "a");
        assertTrue(variables.size() == 1);
        assertTrue(variables.get(0).equals("average"));
        variables = builder.getVariables(document, "c");
        assertTrue(variables.size() == 1);
        assertTrue(variables.get(0).equals("count"));
        variables = builder.getVariables(document, "V");
        assertTrue(variables.size() == 1);
        assertTrue(variables.get(0).equals("VM"));
        variables = builder.getVariables(document, "r");
        assertTrue(variables.size() == 1);
        assertTrue(variables.get(0).equals("record"));
        variables = builder.getVariables(document, "k");
        assertTrue(variables.size() == 1);
        assertTrue(variables.get(0).equals("key"));
    }

    private static IDocument getTestDocument() {
        IDocument document = new Document();
        document.set(getFileContents(DEFAULT_FILE_NAME));
        return document;
    }

    private static String getFileContents(String filename) {
        StringBuffer sb = new StringBuffer();
        String contents;
        BufferedReader br = null;
        try {
            InputStream is = DocumentViewerUtil.class.getResourceAsStream("/" + filename);
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        contents = sb.toString();
        if (contents == null)
            return "";
        else
            return contents;
    }
}
