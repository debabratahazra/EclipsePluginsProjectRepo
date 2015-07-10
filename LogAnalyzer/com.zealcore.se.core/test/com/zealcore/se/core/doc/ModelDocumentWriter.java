package com.zealcore.se.core.doc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.model.IProperty;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.ModelTypePackage;

public final class ModelDocumentWriter {

    private static final String TABLE = "</td>";

    private static final String VERTICAL_ALIGN = "<td valign='top'>";

    private static final String NEWLINE = System.getProperty("line.separator");

    private ModelDocumentWriter() {

    }

    public static void main(final String[] args) throws IOException {
        final Map<String, ITypePackage> packages = new HashMap<String, ITypePackage>();
        packages.put("ZealCore Abstract Model", new ModelTypePackage());

        final FileWriter writer = new FileWriter(
                "C:\\ABB_ROBOTICS_DATATYPES_generated.html");

        ModelDocumentWriter.doWrite(writer, packages);
        writer.close();
        System.out.println("Done!");

    }

    private static void doWrite(final Writer wr,
            final Map<String, ITypePackage> packages) throws IOException {

        wr.append("<HTML>");
        for (final Entry<String, ITypePackage> entry : packages.entrySet()) {
            wr.append(ModelDocumentWriter.NEWLINE);
            wr.append("<H2>" + entry.getKey() + "</H2>");
            ModelDocumentWriter.writePackage(wr, entry);
        }
        wr.append("</HTML>");
    }

    private static void writePackage(final Writer wr,
            final Entry<String, ITypePackage> entry) throws IOException {

        for (final IType clazz : entry.getValue().getTypes()) {
            ModelDocumentWriter.writeType(wr, clazz);
        }
        wr.append(ModelDocumentWriter.NEWLINE);
    }

    private static void writeType(final Writer wr, final IType clazz)
            throws IOException {
        wr.write("<hr>");
        wr
                .write("<H3>"
                        + clazz.getName()
                        + " : "
                        + clazz.getId()
                        + "</H3><table style='border: solid 1px black;' width='600px' cellspacing='10px'>");
        wr
                .write("<tr><th width='100'>Property</th><th width='100'>Return</th><th>Description</th></tr>");
        wr.write("<tr><td colspan='3'><hr></td></tr>");
        for (final IProperty method : clazz.getProperties()) {

            wr.write("<tr>");

            wr.write(ModelDocumentWriter.VERTICAL_ALIGN);
            wr.write(method.getName());
            wr.write(ModelDocumentWriter.TABLE);

            wr.write(ModelDocumentWriter.VERTICAL_ALIGN);
            wr.write(method.getReturnType().getSimpleName());
            wr.write(ModelDocumentWriter.TABLE);

            wr.write(ModelDocumentWriter.VERTICAL_ALIGN);
            wr.write(method.getDescription());
            wr.write(ModelDocumentWriter.TABLE);

            wr.write("</tr>");
            wr.write(ModelDocumentWriter.NEWLINE);
        }
        wr.write("</table>");
    }
}
