package com.zealcore.se.core.importer.lttng;
import java.io.IOException;
import java.io.RandomAccessFile;


public class RandomFileRead {

	protected String fileName;

    protected RandomAccessFile seeker;

	
	  /** Constructor: save filename, construct RandomAccessFile */
	  public RandomFileRead(String fname) throws IOException {
	    fileName = fname;
	    seeker = new RandomAccessFile(fname, "r");
	  }

	  /** Read the Offset field, defined to be at location 0 in the file. */
	  public int readOffset() throws IOException {
	    seeker.seek(0); // move to very beginning
	    return seeker.readInt(); // and read the offset
	  }

	  /** Read the message at the given offset */
	  public String readMessage() throws IOException {
	    seeker.seek(readOffset()); // move to the offset
	    return seeker.readLine(); // and read the String
	  }
}
