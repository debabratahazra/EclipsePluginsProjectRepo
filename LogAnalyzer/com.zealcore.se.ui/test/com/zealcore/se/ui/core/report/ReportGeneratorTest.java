package com.zealcore.se.ui.core.report;

import java.io.File;
import java.util.Arrays;

import junit.framework.Assert;

import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public final class ReportGeneratorTest {
    private static final String S_ITEM_B_DESC = "mockImageReportItemBDesc";

    private static final String S_ITEM_A_DESC = "mockImageReportItemADesc";

    private static final String S_ITEM_B_NAME = "mockImageReportItemBName";

    private static final String S_ITEM_A_NAME = "mockImageReportItemAName";

    private static final int COLOR_DEPTH = 32;

    private static final int IMAGE_SIZE_PIXELS = 100;

    private static final int PALLETTE_MASK = 255;

    // subject
    private HtmlReportGenerator subjectReportGenerator;

    private IPath reportFilePath;

    // mock controls
    private IMocksControl mocksControl;

    // mocks
    private IReportContributor mockReportContributorA;

    private IReportContributor mockReportContributorB;

    @Before
    public void setUp() throws Exception {
        final File tempFile = File.createTempFile("zcHtmlReport", ".html");
        reportFilePath = new Path(tempFile.getAbsolutePath());
        tempFile.delete();

        mocksControl = EasyMock.createControl();
        subjectReportGenerator = new HtmlReportGenerator();
        mockReportContributorA = mocksControl
                .createMock(IReportContributor.class);
        mockReportContributorB = mocksControl
                .createMock(IReportContributor.class);
    }

    @After
    public void tearDown() {
        mocksControl.verify();
    }

    @Test
    public void testSetContributors() throws Exception {
        // expects
        mockReportContributorA.fillReport(subjectReportGenerator);
        mockReportContributorB.fillReport(subjectReportGenerator);

        // replay
        mocksControl.replay();

        // do stuff
        subjectReportGenerator.setContributors(Arrays
                .asList(new IReportContributor[] { mockReportContributorA,
                        mockReportContributorB, }));
        subjectReportGenerator.gatherReportData();
    }

    @Test
    public void testCompileReport() throws Exception {
        // expects
        final ImageReportItem mockImageReportItemA = mocksControl
                .createMock(ImageReportItem.class);
        final ImageReportItem mockImageReportItemB = mocksControl
                .createMock(ImageReportItem.class);

        subjectReportGenerator.addReportData(mockImageReportItemA);
        subjectReportGenerator.addReportData(mockImageReportItemB);

        org.easymock.EasyMock.expect(mockImageReportItemA.getName()).andReturn(
                ReportGeneratorTest.S_ITEM_A_NAME);
        org.easymock.EasyMock.expect(mockImageReportItemB.getName()).andReturn(
                ReportGeneratorTest.S_ITEM_B_NAME);
        org.easymock.EasyMock.expect(mockImageReportItemA.getDescription())
                .andReturn(ReportGeneratorTest.S_ITEM_A_DESC);
        org.easymock.EasyMock.expect(mockImageReportItemB.getDescription())
                .andReturn(ReportGeneratorTest.S_ITEM_B_DESC);

        // replay
        mocksControl.replay();

        // do stuff
        subjectReportGenerator.compileReport();
    }

    @Test
    public void testToFile() throws Exception {
        // expects
        final ImageReportItem mockImageReportItemA = mocksControl
                .createMock(ImageReportItem.class);
        final ImageReportItem mockImageReportItemB = mocksControl
                .createMock(ImageReportItem.class);

        subjectReportGenerator.addReportData(mockImageReportItemA);
        subjectReportGenerator.addReportData(mockImageReportItemB);

        org.easymock.EasyMock.expect(mockImageReportItemA.getName()).andReturn(
                ReportGeneratorTest.S_ITEM_A_NAME);
        org.easymock.EasyMock.expect(mockImageReportItemB.getName()).andReturn(
                ReportGeneratorTest.S_ITEM_B_NAME);
        org.easymock.EasyMock.expect(mockImageReportItemA.getDescription())
                .andReturn(ReportGeneratorTest.S_ITEM_A_DESC);
        org.easymock.EasyMock.expect(mockImageReportItemB.getDescription())
                .andReturn(ReportGeneratorTest.S_ITEM_B_DESC);
        org.easymock.EasyMock.expect(mockImageReportItemA.getImage())
                .andReturn(
                        new ImageData(ReportGeneratorTest.IMAGE_SIZE_PIXELS,
                                ReportGeneratorTest.IMAGE_SIZE_PIXELS,
                                ReportGeneratorTest.COLOR_DEPTH,
                                new PaletteData(
                                        ReportGeneratorTest.PALLETTE_MASK,
                                        ReportGeneratorTest.PALLETTE_MASK,
                                        ReportGeneratorTest.PALLETTE_MASK)));
        org.easymock.EasyMock.expect(mockImageReportItemB.getImage())
                .andReturn(
                        new ImageData(ReportGeneratorTest.IMAGE_SIZE_PIXELS,
                                ReportGeneratorTest.IMAGE_SIZE_PIXELS,
                                ReportGeneratorTest.COLOR_DEPTH,
                                new PaletteData(
                                        ReportGeneratorTest.PALLETTE_MASK,
                                        ReportGeneratorTest.PALLETTE_MASK,
                                        ReportGeneratorTest.PALLETTE_MASK)));

        // replay
        mocksControl.replay();

        // do stuff
        subjectReportGenerator.compileReport();
        subjectReportGenerator.toFile(reportFilePath);
    }

    @Test
    public void testClearReportData() throws Exception {
        // expects
        final ImageReportItem mockImageReportItemA = mocksControl
                .createMock(ImageReportItem.class);
        final ImageReportItem mockImageReportItemB = mocksControl
                .createMock(ImageReportItem.class);

        subjectReportGenerator.addReportData(mockImageReportItemA);
        subjectReportGenerator.addReportData(mockImageReportItemB);

        // replay
        mocksControl.replay();

        // do stuff
        Assert.assertTrue(subjectReportGenerator.getReportData().size() > 0);
        subjectReportGenerator.clearReportData();
        Assert.assertFalse(subjectReportGenerator.getReportData().size() > 0);
    }
}
