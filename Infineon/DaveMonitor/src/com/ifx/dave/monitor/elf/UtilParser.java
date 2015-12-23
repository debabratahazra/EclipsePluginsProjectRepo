package com.ifx.dave.monitor.elf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ifx.dave.monitor.MainClass;
import com.ifx.dave.monitor.elf.model.StructVariable;
import com.ifx.dave.monitor.elf.model.Variable;

public class UtilParser {

	private static final String OUTPUT_FILENAME = "Output.txt";
	private static final String VARIABLES_FILENAME = "Variable.txt";
	private static final String ADDRESS_FILENAME = "Address.txt";
	private static final String STRUCT_TYPE_FILENAME = "StructDetails.txt";

	/**
	 * Read ELF file and generate OUTPUT file Generate all Variables names from
	 * ELF file
	 *
	 * @throws IOException
	 */
	public void generateOutputFileFromElf(String GDB_EXECUTABLE,
			String ELF_FILENAME) throws IOException {
		File file = new File(ELF_FILENAME);
		if (file != null && !file.exists()) {
			// Elf file not found
			return;
		}

		File oFile = new File(OUTPUT_FILENAME);
		writeToFile(false, "", oFile);

		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c",
					GDB_EXECUTABLE);

			processBuilder.redirectError();
			Process process = processBuilder.start();

			InputStreamDisplay inputStreamDisplay = new InputStreamDisplay(
					process.getInputStream(), oFile);
			inputStreamDisplay.start();

			BufferedWriter bufferWriter = new BufferedWriter(
					new OutputStreamWriter(process.getOutputStream()));

			// Load ELF File
			bufferWriter.write("file " + ELF_FILENAME);
			bufferWriter.newLine();
			bufferWriter.flush();

			// Display all variables
			bufferWriter.write("info variables");
			bufferWriter.newLine();
			bufferWriter.flush();

			// Terminate GDB session
			bufferWriter.write("quit");
			bufferWriter.newLine();
			bufferWriter.flush();

			process.waitFor();
			inputStreamDisplay.join();

		} catch (InterruptedException | IOException exp) {
			exp.printStackTrace();
		}

	}

	/**
	 * Delete all temporary files.
	 */
	public void cleanAllTempFiles() {
		File tempFile = new File(OUTPUT_FILENAME);
		if (tempFile.exists()) {
			tempFile.delete();
		}
		tempFile = new File(VARIABLES_FILENAME);
		if (tempFile.exists()) {
			tempFile.delete();
		}
		tempFile = new File(ADDRESS_FILENAME);
		if (tempFile.exists()) {
			tempFile.delete();
		}
	}

	/**
	 * Get the Variables name with Address
	 */
	public void getVariableWithAddressFile() throws IOException {
		File aFile = new File(ADDRESS_FILENAME);
		if (!aFile.exists()) {
			throw new IOException("Address File not found");
		}

		List<String[]> listVariable = readVariableFile();
		try (BufferedReader br = new BufferedReader(new FileReader(aFile))) {
			int index = 0;
			for (String line; (line = br.readLine()) != null;) {
				if (line.startsWith("(gdb) $")) {
					String[] variableDetals = listVariable.get(index);
					if (line.contains(variableDetals[0])) {
						String[] splits = line.split(" ");
						String address = splits[splits.length - 2];
						index++;
						Variable variable = new Variable(variableDetals[0],
								address, variableDetals[1], null);
						MainClass.setGlobalVariable(variable);
					}
				}
			}
		}
	}

	/**
	 * Get the struct variables detail
	 *
	 * @param variablesAddressTypeFilename
	 * @param gdbExecutable
	 */
	public void getStructVariableFile(String gdbExecutable, String elfFile)
			throws IOException {
		readVariableType(gdbExecutable, elfFile);
		getStructFileDetails();
		cleanStructFile();
	}

	private void cleanStructFile() throws IOException {
		File sFile = new File(STRUCT_TYPE_FILENAME);
		if (!sFile.exists()) {
			return;
		}
		sFile.delete();
	}

	private void getStructFileDetails() throws IOException {
		File sFile = new File(STRUCT_TYPE_FILENAME);
		if (!sFile.exists()) {
			return;
		}
		List<StructVariable> structVariables = new ArrayList<StructVariable>();
		StructVariable structVariable = null;
		try (BufferedReader br = new BufferedReader(new FileReader(sFile))) {
			boolean readVariables = false;
			boolean skipUnionSection = false;
			for (String line; (line = br.readLine()) != null;) {
				if (line.startsWith("(gdb) type =") && line.endsWith("{")) {
					structVariable = new StructVariable();
					readVariables = true;
					String[] splits = line.split(" ");
					if (splits.length >= 6) {
						structVariable.setStructName(splits[splits.length - 2]);
					}
					continue;
				}
				if (readVariables && line.endsWith(";") && !skipUnionSection) {
					String[] splits = line.trim().split(" ");
					if (splits.length >= 2) {
						// Variable Type in Struct
						if (splits[0].equals("const")) {
							structVariable.addStructVariableType(splits[1]);
						} else {
							structVariable.addStructVariableType(splits[0]);
						}
					}
					// struct data members name
					String structVariableName = splits[splits.length - 1];
					structVariableName = modifyStructVariable(structVariableName);
					structVariable.addStructVariableName(structVariableName);
				}

				if (line.trim().length() == 1 && line.trim().equals("}")
						&& readVariables) {
					structVariables.add(structVariable);
					MainClass.setGlobalStructVariable(structVariable);
					readVariables = false;
					continue;
				}

				// Skip Union section inside struct
				if (line.trim().equals("union {")) {
					skipUnionSection = true;
					continue;
				}
				if (skipUnionSection && line.trim().equals("};")) {
					skipUnionSection = false;
					continue;
				}
			}
		}
	}

	private String modifyStructVariable(String variableName) {
		variableName = variableName.replace(";", "");

		if (variableName.contains("[") && variableName.contains("]")) {
			int index = variableName.indexOf("[");
			variableName = variableName.substring(0, index);
		}
		if (variableName.startsWith("*")) {
			variableName = variableName.substring(1);
		}
		return variableName;

	}

	private void readVariableType(String gdbExecutable, String elfFilename)
			throws IOException {
		List<String> variableTypes = new ArrayList<String>();
		List<Variable> variables = MainClass.getGlobalVariables();
		for (Iterator<Variable> iterator = variables.iterator(); iterator
				.hasNext();) {
			Variable variable = (Variable) iterator.next();
			variableTypes.add(variable.getVariableType());
		}
		executeStructVariableCommand(variableTypes, gdbExecutable, elfFilename);
	}

	private void executeStructVariableCommand(List<String> variableTypes,
			String gdbExecutable, String elfFilename) throws IOException {
		File sFile = new File(STRUCT_TYPE_FILENAME);
		writeToFile(false, "", sFile);
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c",
					gdbExecutable);

			processBuilder.redirectError();
			Process process = processBuilder.start();

			InputStreamDisplay inputStreamDisplay = new InputStreamDisplay(
					process.getInputStream(), sFile);
			inputStreamDisplay.start();

			BufferedWriter bufferWriter = new BufferedWriter(
					new OutputStreamWriter(process.getOutputStream()));

			// Load ELF File
			bufferWriter.write("file " + elfFilename);
			bufferWriter.newLine();
			bufferWriter.flush();

			for (Iterator<String> iterator = variableTypes.iterator(); iterator
					.hasNext();) {
				String variableType = (String) iterator.next();
				// Display all struct data members
				bufferWriter.write("ptype " + variableType);
				bufferWriter.newLine();
				bufferWriter.flush();
			}

			// Terminate GDB session
			bufferWriter.write("quit");
			bufferWriter.newLine();
			bufferWriter.flush();

			process.waitFor();
			inputStreamDisplay.join();

		} catch (InterruptedException | IOException exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * Fetch the all the variables names from VARIABLES file and find the
	 * Address of each variables
	 */
	public void generateAddressFromVariableFile(String GDB_EXECUTABLE,
			String ELF_FILENAME) throws IOException {
		File aFile = new File(ADDRESS_FILENAME);
		writeToFile(false, "", aFile);
		List<String[]> varibleNames = readVariableFile();

		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c",
					GDB_EXECUTABLE);

			processBuilder.redirectError();
			Process process = processBuilder.start();

			InputStreamDisplay inputStreamDisplay = new InputStreamDisplay(
					process.getInputStream(), aFile);
			inputStreamDisplay.start();

			BufferedWriter bufferWriter = new BufferedWriter(
					new OutputStreamWriter(process.getOutputStream()));

			// Load ELF File
			bufferWriter.write("file " + ELF_FILENAME);
			bufferWriter.newLine();
			bufferWriter.flush();

			// Get all variables with address from GDB
			for (Iterator<String[]> iterator = varibleNames.iterator(); iterator
					.hasNext();) {
				String[] variable = (String[]) iterator.next();
				if (variable.length == 2) {
					bufferWriter.write("p &" + variable[0]);
					bufferWriter.newLine();
					bufferWriter.flush();
				}
			}

			// Terminate GDB session
			bufferWriter.write("quit");
			bufferWriter.newLine();
			bufferWriter.flush();

			process.waitFor();
			inputStreamDisplay.join();

		} catch (InterruptedException | IOException exp) {
			exp.printStackTrace();
		}

	}

	/**
	 * Get all the Variables name with Variable types from VARIABLES file
	 *
	 * @return
	 * @throws IOException
	 */
	private static List<String[]> readVariableFile() throws IOException {
		File vFile = new File(VARIABLES_FILENAME);
		List<String[]> varNames = new ArrayList<String[]>();
		if (!vFile.exists()) {
			throw new IOException("Variables file not found.");
		}
		try (BufferedReader br = new BufferedReader(new FileReader(vFile))) {
			for (String line; (line = br.readLine()) != null;) {
				if (line.length() != 0) {
					String[] splits = line.split(" ");
					if (splits.length == 2) {
						varNames.add(splits);
					}
				}
			}
		}
		return varNames;
	}

	/**
	 * Read OUTPUT file and extract Variables name and write it to VARIABLES
	 * file
	 *
	 * @throws IOException
	 */
	public void generateVariableFromOutputFile() throws IOException {
		File rFile = new File(OUTPUT_FILENAME);
		if (!rFile.exists()) {
			throw new IOException("OUTPUT file not found.");
		}
		File vFile = new File(VARIABLES_FILENAME);
		writeToFile(false, "", vFile);

		try (BufferedReader br = new BufferedReader(new FileReader(rFile))) {
			boolean flagToRead = false;
			for (String line; (line = br.readLine()) != null;) {
				if (line.startsWith("File", 0) && line.endsWith(":")) {
					flagToRead = true;
					continue;
				}
				if (line.length() == 0) {
					flagToRead = false;
				}

				if (flagToRead) {
					// Read variables and write to file
					String[] variables = modify(line);
					if (variables != null && variables.length == 2) {
						writeToFile(true, variables[0] + " " + variables[1],
								vFile);
					}
				}
			}
		}
	}

	/**
	 * Extract only Variables names from list
	 *
	 * @param line
	 * @return
	 */
	private static String[] modify(String line) {
		line = line.replace(";", "");
		String[] splitStr = line.split(" ");
		if (splitStr.length >= 2) {
			String variableName = splitStr[splitStr.length - 1];
			String variableType = splitStr[splitStr.length - 2];
			if (variableName.contains("[") && variableName.contains("]")) {
				int index = variableName.indexOf("[");
				variableName = variableName.substring(0, index);
			}
			if (variableName.startsWith("*")) {
				variableName = variableName.substring(1);
			}
			return new String[] { variableName, variableType };
		}
		return null;
	}

	/**
	 * Display Console output and write it to txt file
	 *
	 * @author DEBABRATA
	 */
	public static class InputStreamDisplay extends Thread {

		private InputStream is;
		private File file;

		public InputStreamDisplay(InputStream is, File file) {
			this.is = is;
			this.file = file;
		}

		@Override
		public void run() {
			String content = new String();
			try {
				int value = -1;
				while ((value = is.read()) != -1) {
					content += (char) value;
				}
				writeToFile(true, content, file);
			} catch (IOException exp) {
				exp.printStackTrace();
			}

		}
	}

	/**
	 * Write the output console to txt File
	 *
	 * @param isAppend
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(boolean isAppend, String content, File file)
			throws IOException {

		// If file is not append mode and file exist, delete it
		if (!isAppend && file.exists()) {
			file.delete();
		}
		// if not append mode or file does not exists, then create it
		if (!isAppend && !file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile(), isAppend);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.append(content);
		if (content.length() != 0) {
			bw.newLine();
		}
		bw.close();
	}
}
