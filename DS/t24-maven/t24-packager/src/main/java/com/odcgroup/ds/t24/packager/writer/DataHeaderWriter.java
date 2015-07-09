package com.odcgroup.ds.t24.packager.writer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;

import com.odcgroup.ds.t24.packager.data.Cd;
import com.odcgroup.ds.t24.packager.data.DataHeader;
import com.odcgroup.ds.t24.packager.data.Record;
import com.odcgroup.ds.t24.packager.generator.PackageTypeEnum;

/**
 * This class serialize the data header model
 * @author yandenmatten
 */
public class DataHeaderWriter {

	private static final SimpleDateFormat CREATE_MODIFY_DATE_FORMAT = new SimpleDateFormat("yyMMddHHmm");
	private static final SimpleDateFormat SAVED_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat SAVE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	private static final String CRLF =  Character.toString((char)0x0D) + 
			Character.toString((char)0x0A);
	
	private static final Charset WINDOWS_1252_CHARSET = Charset.forName("windows-1252");
	private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
	
	private final String FIELD_SEPARATOR;
	private final String SUB_FIELD_SEPARATOR;
	private PackageTypeEnum type;
	
	public DataHeaderWriter(PackageTypeEnum type) {
		this.type = type;
		FIELD_SEPARATOR = type.getFieldSeparator();
		SUB_FIELD_SEPARATOR = type.getSubFieldSeparator();	
	}

	/**
	 * Generate the data header contents
	 * 
	 * @return array of bytes (NOT a String; *.d files ARE NOT String, see DS-8075)
	 * @throws CharacterCodingException if something in DataHeader header cannot be turned into a byte[] (which is what a *.d file is..)
	 * @see http://rd.oams.com/browse/DS-8075
	 */
	public byte[] write(DataHeader header) throws CharacterCodingException {
		StringBuilder result = new StringBuilder();
		
		// line 1
		result.append(header.getGbDescription());
		result.append(CRLF);
		
		// line 2
		for (Cd cd : header.getCds()) {
			result.append(cd.getType());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 3
		for (Cd cd : header.getCds()) {
			result.append(cd.getNumber()!=null?cd.getNumber():"");
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 4
		for (Cd cd : header.getCds()) {
			for (Record record: cd.getRecords()) {
				result.append(record.getFilename());
				result.append(SUB_FIELD_SEPARATOR);
			}
			result.setLength(result.length()-1);
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 5
		for (Cd cd : header.getCds()) {
			for (Record record: cd.getRecords()) {
				result.append(record.getName());
				result.append(SUB_FIELD_SEPARATOR);
			}
			result.setLength(result.length()-1);
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 6
		for (Cd cd : header.getCds()) {
			result.append(cd.getReference());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);

		// line 7
		for (Cd cd : header.getCds()) {
			result.append(cd.getProblem());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 8
		for (Cd cd : header.getCds()) {
			result.append(cd.getSymptom());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 9
		for (Cd cd : header.getCds()) {
			result.append(cd.getFixDescription());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 10
		for (Cd cd : header.getCds()) {
			result.append(cd.getPriority());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 11
		for (Cd cd : header.getCds()) {
			result.append(cd.getRestoredFrom());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// line 12
		// Instruction field: always empty
		for (int i=0; i<header.getCds().size(); i++) {
			// Empty values
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);

		// Line 13
		// (reserved for future use)
		result.append(CRLF);
		
		// Line 14
		// (reserved for future use)
		result.append(CRLF);
		
		// Line 15
		// (reserved for future use)
		result.append(CRLF);
		
		// Line 16
		// (reserved for future use)
		result.append(CRLF);
		
		// Line 17
		// (reserved for future use)
		result.append(CRLF);
		
		// Line 18
		// (reserved for future use)
		result.append(CRLF);
		
		// Line 19
		result.append(header.getSavedVersion());
		result.append(CRLF);
		
		// Line 20
		result.append(header.getSavedFrom());
		result.append(CRLF);
		
		// Line 21
		result.append(header.getSavedRelease());
		result.append(CRLF);
		
		// Line 22
		result.append(SAVE_DATE_FORMAT.format(header.getSavedDate()));
		result.append(CRLF);
		
		// Line 23
		result.append(SAVED_TIME_FORMAT.format(header.getSavedDate()));
		result.append(CRLF);
		
		// Line 24
		// (reserved for future use)
		result.append(CRLF);

		// Line 25
		// (reserved for future use)
		result.append(CRLF);

		// Line 26
		// (reserved for future use)
		result.append(CRLF);

		// Line 27
		// (reserved for future use)
		result.append(CRLF);

		// Line 28
		result.append(header.getWorkset());
		result.append(CRLF);
		
		// Line 29
		result.append(header.getProduct());
		result.append(CRLF);
		
		// Line 30
		result.append(header.getUseDimensions());
		result.append(CRLF);
		
		// Line 31
		LinkedHashSet<Record> keptRecords = new LinkedHashSet<Record>();
		for (Cd cd: header.getCds()) {
			for (Record rec: cd.getRecords()) {
				keptRecords.add(rec);
			}
		}
		for (Record rec: keptRecords) {
			result.append(rec.getFilename());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// Line 32
		for (Record rec: keptRecords) {
			result.append(rec.getName());
			result.append(FIELD_SEPARATOR);
		}
		result.setLength(result.length()-1);
		result.append(CRLF);
		
		// Line 33
		result.append("N"); // 'N' to indicate this is a new Package
		result.append(CRLF);

		// Line 34
		// (reserved for future use)
		result.append(CRLF);

		// Line 35
		// (reserved for future use)
		result.append(CRLF);

		// Line 36
		// (reserved for future use)
		result.append(CRLF);

		// Line 37
		// (reserved for future use)
		result.append(CRLF);

		// Line 38
		// (reserved for future use)
		result.append(CRLF);

		// Line 39
		// (reserved for future use)
		result.append(CRLF);

		// Line 40
		// (reserved for future use)
		result.append(CRLF);

		// Line 41
		// (reserved for future use)
		result.append(CRLF);

		// Line 42
		// (reserved for future use)
		result.append(CRLF);

		// Line 43
		// (reserved for future use)
		result.append(CRLF);

		// line 44
		result.append(header.getCurrNo());
		result.append(CRLF);

		// line 45
		result.append(header.getInputtingUser());
		result.append(CRLF);

		// line 46
		result.append(CREATE_MODIFY_DATE_FORMAT.format(header.getCreationModificationDate()));
		result.append(CRLF);

		// line 47
		result.append(header.getAuthorizingUser());
		result.append(CRLF);

		// line 48
		result.append(header.getBranch());
		result.append(CRLF);

		// line 49
		result.append(header.getUserDepartmentCode());
		result.append(CRLF);

		// @see http://rd.oams.com/browse/DS-8075
		//   do NOT use result.toString().getBytes()
		//     because that is PLATFORM SPECIFIC :(
		//   do NOT use result.toString().getBytes("windows-1252")
		//     either, because that silently swallows bad characters, while we want an Exception in case
		//   so do this instead:
		CharsetEncoder encoder;
		if (type == PackageTypeEnum.TAFC) {
			encoder = WINDOWS_1252_CHARSET.newEncoder();
		} else if (type == PackageTypeEnum.TAFJ) {
			encoder = UTF_8_CHARSET.newEncoder();
		} else {
			throw new IllegalArgumentException("Packager type unsupported (" + type + ")");
		}
		encoder.onMalformedInput(CodingErrorAction.REPORT);
		encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
		ByteBuffer bb = encoder.encode(CharBuffer.wrap(result));
		byte[] ba = new byte[bb.limit()];
		bb.get(ba);
		return ba;
	}

}
