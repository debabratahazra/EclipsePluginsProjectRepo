package com.temenos.t24.tools.eclipse.basic.views.document;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

public class DocumentViewConstants {

    public static final String FOLDER_IMAGE_PATH = "/icons/folder.gif";
    public static final String COMPONENT_IMAGE_PATH = "/icons/component.gif";
    public static final String SUBROUTINE_IMAGE_PATH = "/icons/subroutine0.png";
    // icon for sources in component landscape view
    public static final String SOURCES_IMAGE_PATH = "/icons/subroutine.png";
    public static final String GOSUB_IMAGE_PATH = "/icons/object.png";
    public static final String FILE_IMAGE_PATH = "/icons/tsuite.png";
    public static final String JAVA_IMAGE_PATH = "/icons/javacup.gif";
    // icon path of labels in inline doc view
    public static final String LABELS_IMAGE_PATH = "/icons/label.gif";
    public static final Image T24PRODUCT_IMAGE = EclipseUtil.getImage(FOLDER_IMAGE_PATH);
    public static final Image T24SUBROUTINE_IMAGE = EclipseUtil.getImage(SUBROUTINE_IMAGE_PATH);
    public static final Image T24COMPONENT_IMAGE = EclipseUtil.getImage(COMPONENT_IMAGE_PATH);
    public static final Image T24GOSUB_IMAGE = EclipseUtil.getImage(GOSUB_IMAGE_PATH);
    public static final Image T24FILE_IMAGE = EclipseUtil.getImage(FILE_IMAGE_PATH);
    public static final Image T24JAVA_IMAGE = EclipseUtil.getImage(JAVA_IMAGE_PATH);
    /** image for labels in in-line tree viewer */
    public static final Image T24LABEL_IMAGE = EclipseUtil.getImage(LABELS_IMAGE_PATH);
    // image for sources in component landscape view
    public static final Image T24SOURCES_IMAGE = EclipseUtil.getImage(SOURCES_IMAGE_PATH);
    public static String tableName = "<b>Table Name:</b>";
    public static String description = "<b>Description:</b>";
    public static String type = "<b>Type:</b>";
    public static String classification = "<b>Classification:</b>";
    public static String batchName = "<b>Batch Name:</b>";
    public static String batchDesc = "<b>Batch Description:</b>";
    public static String jobName = "<b>Job Name:</b>";
    public static String jobDesc = "<b>Job Description:</b>";
    public static String overviewTab = "Overview     ";
    public static String tablesTab = "Tables      ";
    public static String cobTab = "COB        ";
    /*
     * public static StringoverviewInfo=
     * "Click on the Product-->Component--->Tables/Batch in Component Landscape to view the relevant information"
     * ;
     */
    public static String overviewInfo = "Click on the Refresh Button to load the Component Documentation";
    public static String COB_NO_AVAILABALE = "COB details not available";
    public static String PRODUCT_XPATH = "//products/";
}
