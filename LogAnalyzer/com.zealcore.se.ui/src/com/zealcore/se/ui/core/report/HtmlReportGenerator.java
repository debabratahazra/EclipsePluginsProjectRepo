package com.zealcore.se.ui.core.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * A concrete representaion of an AbstractReportGenerator
 * <p>
 * This generator will produce html output of the contained report.
 * <p>
 * Use methods on {@link AbstractReportGenerator} to generate the actual report
 * and then call {@link HtmlReportGenerator#toFile(IPath)} to transform the
 * underlying report to Html
 * 
 * @see AbstractReportGenerator
 * @author daze
 * 
 */
public class HtmlReportGenerator extends AbstractReportGenerator {

    private static final String S_ENDL = System.getProperty("line.separator");

    private static final String S_FILE_PREFIX = "img";

    private static final String S_FILE_EXTENSION = ".jpg";

    private class HtmlTemplateImage {

        private int count;

        private final String template = "<h2>$ImageName</h2>\n<h3>$ImageDescription</h3>\n<img src=\"$ImagePath\" alt=\"$ImageDescription\" border=1</img>\n";

        public String emit(final ImageReportItem imageReportItem) {
            String tableRow = this.template;
            tableRow = tableRow
                    .replace("$ImageName", imageReportItem.getName());
            tableRow = tableRow.replace("$ImageDescription", imageReportItem
                    .getDescription());
            tableRow = tableRow.replace("$ImagePath",
                    HtmlReportGenerator.S_FILE_PREFIX
                            + Integer.toString(this.count++)
                            + HtmlReportGenerator.S_FILE_EXTENSION);
            return tableRow;
        }
    }

    private class HtmlTemplateTree {

        public String emit(final TreeReportItem treeReportItem) {
            String html = "";
            html += HtmlTag.H2 + treeReportItem.getName() + HtmlTag.H2.close()
                    + HtmlReportGenerator.S_ENDL;
            html += HtmlTag.H3 + treeReportItem.getDescription()
                    + HtmlTag.H3.close() + HtmlReportGenerator.S_ENDL;
            for (final TreeReportItem.TreeNode node : treeReportItem.getNodes()) {
                html += emit(node);
            }
            return html;
        }

        private String emit(final TreeReportItem.TreeNode parentNode) {
            String html = "";
            html += "<li class=\"TreeReportItem\">" + parentNode.getValue()
                    + HtmlReportGenerator.S_ENDL;
            html += "<ul>" + HtmlReportGenerator.S_ENDL;
            for (final TreeReportItem.TreeNode childNode : parentNode
                    .getChildren()) {
                html += emit(childNode);
            }
            html += "</ul>" + HtmlReportGenerator.S_ENDL;
            return html;
        }
    }

    private class HtmlTemplateTable {
        public String emit(final TableReportItem tableReportItem) {
            String html = "";
            html += HtmlTag.H2;
            html += tableReportItem.getName();
            html += HtmlTag.H2.close() + HtmlReportGenerator.S_ENDL;
            html += HtmlTag.H3;
            html += tableReportItem.getDescription();
            html += HtmlTag.H3.close() + HtmlReportGenerator.S_ENDL;
            html += "<table class=\"TableReportItemTable\">"
                    + HtmlReportGenerator.S_ENDL;
            html += "<tr class=\"TableReportItemHeader\">"
                    + HtmlReportGenerator.S_ENDL;
            for (final String col : tableReportItem.getHeader().getValues()) {
                html += HtmlTag.TD + col + HtmlTag.TD.close()
                        + HtmlReportGenerator.S_ENDL;
            }
            html += HtmlTag.TR.close() + HtmlReportGenerator.S_ENDL;
            boolean altRow = false;
            for (final TableReportItem.TableRow row : tableReportItem.getRows()) {
                if (altRow) {
                    html += "<tr class=\"TableReportItemRow\">"
                            + HtmlReportGenerator.S_ENDL;
                } else {
                    html += "<tr class=\"TableReportItemRowAlt\">"
                            + HtmlReportGenerator.S_ENDL;
                }
                for (final String col : row.getValues()) {
                    html += HtmlTag.TD + col + HtmlTag.TD.close()
                            + HtmlReportGenerator.S_ENDL;
                }
                html += HtmlTag.TR.close() + HtmlReportGenerator.S_ENDL;
                altRow = !altRow;
            }
            html += "</table>" + HtmlReportGenerator.S_ENDL;
            return html;
        }
    }

    private class HtmlTemplateHeader {
        private final String header = "<html>\n<body>\n<header>\n<link rel=StyleSheet href=\"report.css\" type=\"text/css\"></header>\n<table>\n";

        public String emit() {
            return this.header;
        }
    }

    private class HtmlTemplateFooter {
        private final String footer = "</table>\n</body>\n</html>\n";

        public String emit() {
            return this.footer;
        }
    }

    private static class CompiledReportData {
        private final List<String> imageHtmlElements;

        private final List<String> treeHtmlElements;

        private final List<String> tableHtmlElements;

        public CompiledReportData() {
            this.imageHtmlElements = new ArrayList<String>();
            this.treeHtmlElements = new ArrayList<String>();
            this.tableHtmlElements = new ArrayList<String>();
        }

        public void clear() {
            this.imageHtmlElements.clear();
            this.treeHtmlElements.clear();
            this.tableHtmlElements.clear();
        }
    }

    private final CompiledReportData compiledReportData = new CompiledReportData();

    @Override
    public File toFile(final IPath path) throws IOException {
        final File htmlFile = path.toFile();
        if (!htmlFile.createNewFile()) {
            throw new IllegalArgumentException();
        }
        final FileWriter outFile = new FileWriter(htmlFile);
        outFile.write((new HtmlTemplateHeader()).emit());
        outFile.write(HtmlTag.TR + HtmlReportGenerator.S_ENDL);
        outFile.write(HtmlTag.TD + HtmlReportGenerator.S_ENDL);
        outFile.write(HtmlTag.H1.open());
        outFile.write("Enea Optima Log Analyzer report");
        outFile.write(HtmlTag.H1.close() + HtmlReportGenerator.S_ENDL);
        outFile.write("Report generated: "
                + Calendar.getInstance().getTime().toString()
                + HtmlReportGenerator.S_ENDL);
        outFile.write(HtmlTag.BR + HtmlReportGenerator.S_ENDL);
        outFile.write(HtmlTag.TD.close() + HtmlReportGenerator.S_ENDL);
        outFile.write(HtmlTag.TR.close() + HtmlReportGenerator.S_ENDL);
        for (final String elem : this.compiledReportData.imageHtmlElements) {
            outFile.write(HtmlTag.TR + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.TD + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.BR + HtmlReportGenerator.S_ENDL);
            outFile.write(elem);
            outFile.write(HtmlTag.TD.close() + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.TR.close() + HtmlReportGenerator.S_ENDL);
        }
        for (final String elem : this.compiledReportData.treeHtmlElements) {
            outFile.write(HtmlTag.TR + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.TD + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.BR + HtmlReportGenerator.S_ENDL);
            outFile.write(elem);
            outFile.write(HtmlTag.TD.close() + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.TR.close() + HtmlReportGenerator.S_ENDL);
        }
        for (final String elem : this.compiledReportData.tableHtmlElements) {
            outFile.write(HtmlTag.TR + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.TD + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.BR + HtmlReportGenerator.S_ENDL);
            outFile.write(elem);
            outFile.write(HtmlTag.TD.close() + HtmlReportGenerator.S_ENDL);
            outFile.write(HtmlTag.TR.close() + HtmlReportGenerator.S_ENDL);
        }
        outFile.write((new HtmlTemplateFooter()).emit());
        generateResourceFiles(path.removeLastSegments(1));
        outFile.close();
        return htmlFile;
    }

    private void generateResourceFiles(final IPath path) throws IOException {
        int count = 0;
        final ImageLoader imageLoader = new ImageLoader();
        for (final AbstractReportItem reportItem : getReportData()) {
            if (reportItem instanceof ImageReportItem) {
                final ImageReportItem imageReportItem = (ImageReportItem) reportItem;
                imageLoader.data = new ImageData[] { imageReportItem.getImage() };
                imageLoader.save(path.append(
                        HtmlReportGenerator.S_FILE_PREFIX
                                + Integer.toString(count++)
                                + HtmlReportGenerator.S_FILE_EXTENSION)
                        .toString(), SWT.IMAGE_JPEG);
            }
        }
        generateStyleSheet(path);
    }

    private void generateStyleSheet(final IPath path) throws IOException {
        String out = "";
        out += "table.TableReportItemTable {" + HtmlReportGenerator.S_ENDL;
        out += "    border-style: dotted;" + HtmlReportGenerator.S_ENDL;
        out += "border-width: 1px;" + HtmlReportGenerator.S_ENDL;
        out += "} " + HtmlReportGenerator.S_ENDL;
        out += "tr.TableReportItemHeader td {" + HtmlReportGenerator.S_ENDL;
        out += "color: black; " + HtmlReportGenerator.S_ENDL;
        out += "background-color: gray;" + HtmlReportGenerator.S_ENDL;
        out += "height: 40;" + HtmlReportGenerator.S_ENDL;
        out += "padding: 0 10px 0 10px; " + HtmlReportGenerator.S_ENDL;
        out += "}  " + HtmlReportGenerator.S_ENDL;
        out += "tr.TableReportItemRow td {" + HtmlReportGenerator.S_ENDL;
        out += "color: black;" + HtmlReportGenerator.S_ENDL;
        out += "background-color: lightgray;" + HtmlReportGenerator.S_ENDL;
        out += "padding: 0 10px 0 10px;" + HtmlReportGenerator.S_ENDL;
        out += "}   " + HtmlReportGenerator.S_ENDL;

        out += "tr.TableReportItemRowAlt td {" + HtmlReportGenerator.S_ENDL;
        out += "color: black; background-color: white;"
                + HtmlReportGenerator.S_ENDL;
        out += "padding:0 10px 0 10px; }" + HtmlReportGenerator.S_ENDL;
        out += "li.TreeReportItem { list-style-type: disc; }"
                + HtmlReportGenerator.S_ENDL;
        out += "h2 { text-decoration: underline; }"
                + HtmlReportGenerator.S_ENDL;
        out += "}" + HtmlReportGenerator.S_ENDL;

        final FileWriter outFile = new FileWriter(path.append("report.css")
                .toFile());
        outFile.write(out);
        outFile.close();
    }

    @Override
    public void compileReport() {
        if (getReportData() == null || getReportData().size() == 0) {
            return;
        }
        this.compiledReportData.clear();
        final HtmlTemplateImage imageHtmlTemplate = new HtmlTemplateImage();
        final HtmlTemplateTree treeHtmlTemplate = new HtmlTemplateTree();
        final HtmlTemplateTable tableHtmlTemplate = new HtmlTemplateTable();
        for (final AbstractReportItem reportItem : getReportData()) {
            if (reportItem instanceof ImageReportItem) {
                this.compiledReportData.imageHtmlElements.add(imageHtmlTemplate
                        .emit((ImageReportItem) reportItem));
            } else if (reportItem instanceof TreeReportItem) {
                this.compiledReportData.treeHtmlElements.add(treeHtmlTemplate
                        .emit((TreeReportItem) reportItem));
            } else if (reportItem instanceof TableReportItem) {
                this.compiledReportData.tableHtmlElements.add(tableHtmlTemplate
                        .emit((TableReportItem) reportItem));
            }
        }
    }
}
