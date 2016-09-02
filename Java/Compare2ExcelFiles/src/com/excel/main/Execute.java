package com.excel.main;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.excel.read.ReadXLS;
import com.excel.read.ReadXLSX;
import com.excel.write.WriteXLS;

public class Execute {

    // Default input file location
    private static final String[] args = { "resources\\report.xls", "resources\\result.xls"};

    private static final String XLS_FILE_TYPE = "xls";
    private static final String XLSX_FILE_TYPE = "xlsx";
    
    private static final String OUTPUT_FILENAME = "_output_file";

    public static void main(String[] args) throws Exception {
        // Pass 2 arguments
        // 1 -> Report excel file path
        // 2 -> Result excel file path
    	String outputFilepath = new String();
        if (args.length != 2) {
            System.out
                    .println("All input Excel files are not provided in program arguments.\n");
            args = Execute.args;
        }
        System.out.println("Taking below path locations as input file path : ");
        for (String string : args) {
            System.out.println(string.replace("\\\\", "\\"));
        }

        String path = FilenameUtils.getFullPath(args[0]);
        File outputFile = new File(path + "\\_OUTPUT");
        outputFile.mkdir();
        outputFilepath = outputFile.getAbsolutePath() + "\\" + OUTPUT_FILENAME + ".xls";
        String type_1 = checkInputExcelFileType(args[0]);
        String type_2 = checkInputExcelFileType(args[1]);

        if (type_1.equals(XLS_FILE_TYPE) && type_2.equals(XLS_FILE_TYPE)) {

            ReadXLS read = ReadXLS.getInstance();
            read.setInputFile(args[0], args[1]);
            WriteXLS write = WriteXLS.getInstance();
            write.setOutputFile(outputFilepath);

            read.readAndWrite();
            write.close();
        } else if (type_1.equals(XLSX_FILE_TYPE)
                && type_2.equals(XLSX_FILE_TYPE)) {

            ReadXLSX read = ReadXLSX.getInstance();
            read.setInputFile(args[0], args[1]);
            WriteXLS write = WriteXLS.getInstance();
            write.setOutputFile(outputFilepath);

            read.readAndWrite();
            write.close();
        } else {
            throw new Exception(
                    "Both input excel files are not same extension type.");
        }

        System.out.println("\nAll Done.");
        System.out.println("Output file created: "
                + outputFilepath.replace("\\\\", "\\"));
    }

    private static String checkInputExcelFileType(String filepath)
            throws Exception {

        String extension = FilenameUtils.getExtension(filepath).toLowerCase();
        switch (extension) {
        case XLS_FILE_TYPE:
            return XLS_FILE_TYPE;
        case XLSX_FILE_TYPE:
            return XLSX_FILE_TYPE;
        default:
            throw new Exception("Unknown input file type.");
        }
    }
}
