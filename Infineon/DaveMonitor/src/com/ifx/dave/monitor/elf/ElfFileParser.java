package com.ifx.dave.monitor.elf;

import java.io.IOException;

public class ElfFileParser {

	public static final String GDB_EXECUTABLE = "arm-none-eabi-gdb";
	public static final String ELF_FILENAME = "Sample.elf";

	/**
	 * Parse the ELF file using GDB
	 * 
	 * @throws IOException
	 */
	public static void parser() throws IOException {

		UtilParser parser = new UtilParser();

		parser.generateOutputFileFromElf(GDB_EXECUTABLE, ELF_FILENAME);

		parser.generateVariableFromOutputFile();

		parser.generateAddressFromVariableFile(GDB_EXECUTABLE, ELF_FILENAME);

		parser.getVariableWithAddressFile();

		parser.getStructVariableFile(GDB_EXECUTABLE, ELF_FILENAME);

		parser.cleanAllTempFiles();
	}
}
