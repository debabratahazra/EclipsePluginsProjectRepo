package com.zealcore.se.core.importer.lttng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class LttTrace {

	private String pathName; // the pathname of the trace

	private int numCpu;

	private int archType;

	private int archVariant;

	private byte archSize;

	private byte lttMajorVersion;

	private byte lttMinorVersion;

	private char flightRecorder;

	private int freqScale;

	private long startFreq;

	private long startTsc;

	private long startMonotonic;

	private double drift;

	private double offset;

	private long startTime;

	private long startTimeUsec;

	private LttTime startTimeFromTsc;

	private Map<String, MarkerInfo> mData; // marker id/name/fields mapping

	private Map<String, List<LttTraceFile>> traceFiles = new HashMap<String, List<LttTraceFile>>(); // tracefiles

	private List<LttTraceFile> traceFileList = new ArrayList<LttTraceFile>();

	public static final int LTT_MAGIC_NUMBER = 0x00D6B7ED;

	public static final int LTT_REV_MAGIC_NUMBER = 0xEDB7D600;

	private LttProcessState state = new LttProcessState();

	private LttTraceFile lastSeeked = null;

	private static final String METADATA = "metadata";

	/*
	 * Open all the trace files in the given path and have the descriptors
	 * pointing the beginning of file. This is called from LA which is
	 * responsible to initialise everything and have file descriptors pointing
	 * to first event. And every subsequent call to getNextEvent() should return
	 * the next event until null
	 */
	public LttTrace(String path) throws Exception {
		File startingDirectory = new File(path);
		List<File> files = TraceFileList.getFileListing(startingDirectory);
		LttTraceFile ltf;
		setPathName(path);
		setStartFreq(0);
		int i = 0;
		try {
			for (File file : files) {
				ltf = new LttTraceFile();
				// Open trace file
				
				try {
					lttOpenTracefile(file, ltf);
				} catch (Exception e) {
					ltf.getInputStream().close();
					continue;
				}
				getTracefileNameNumber(file, ltf);
				List<LttTraceFile> group = getTraceFiles().get(ltf.getName());
				if (group == null) {
					group = new ArrayList<LttTraceFile>();
				}
				group.add(ltf);
				getTraceFiles().remove(ltf.getName());
				getTraceFiles().put(ltf.getName(), group);
				if (!(METADATA.equals(ltf.getName()))) {
					getTraceFileList().add(ltf);
				}

			}
			setDrift(1.0);
			setOffset(0);
			List<LttTraceFile> metaFiles = getTraceFiles().get(METADATA);

			if (metaFiles == null) {
				System.out.println("No metadata file found");
				throw new Exception(
						"PARSE ERROR : Invalid Trace Folder(No metadata file found)");
			}

			for (i = 0; i < metaFiles.size(); i++) {
				LttTraceFile metaFile = metaFiles.get(i);
				if (i == 0) {
					metaFile.parseTraceHeader(this, 0);
				}
				metaFile.processMetaDataTraceFile(this);
			}
		} catch (Exception e) {
			throw e;
		}
		LttTraceFile ltfTemp = null;

		for (i = 0; i < getTraceFileList().size(); i++) {
			ltfTemp = getTraceFileList().get(i);
			try {
				ltfTemp.moveToNextEvent(this);
			} catch (EndOfRangeException eof) {
				ltfTemp.setEof(true);
				continue;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	public LttEvent hasNextEvent() throws FileNotFoundException, Exception {
		LttEvent event = null;
		LttTraceFile ltfTemp = null;
		LttTime timeStamp = null;
		int i;
		if (lastSeeked != null) {
			try {
				lastSeeked.moveToNextEvent(this);
			} catch (EndOfRangeException e) {
				lastSeeked.setEof(true);
			} catch (Exception e) {
				throw e;
			}
		}
		for (i = 0; i < getTraceFileList().size(); i++) {
			LttTraceFile tf = getTraceFileList().get(i);
			if (tf.isEof()) {
				continue;
			}
			if (timeStamp == null) {
				timeStamp = tf.getEvent().getEventTime();
				ltfTemp = tf;
			} else {
				if (hasOldestTimestamp(tf.getEvent(), timeStamp)) {
					timeStamp = tf.getEvent().getEventTime();
					ltfTemp = tf;
				}
			}
		}
		try {
			if (ltfTemp == null) {
				cleanAll();
				return null;
			}
			event = ltfTemp.getEvent();

		} catch (Exception e) {
			throw new Exception("PARSE ERROR : Error cleaning up memory");
		}

		updateProcessList(event);
		lastSeeked = ltfTemp;
		return event;
	}

	private void updateProcessList(LttEvent event) {
		if (event != null) {

			if (LttProcessEvents.SCHED_SCHEDULE.equals(event.getName())) {
				state.schedChangeEvent(event);
			} else if (LttProcessEvents.PROCESS_FORK.equals(event.getName())) {
				state.processFork(event);
			} else if (LttProcessEvents.PROCESS_STATE.equals(event.getName())) {
				state.processStateEvent(event);
			} else if (LttProcessEvents.PROCESS_EXEC.equals(event.getName())) {
				state.processExec(event);
			}
		}
	}

	private void cleanAll() throws IOException {
		int i;
		for (i = 0; i < getTraceFileList().size(); i++) {
			LttTraceFile tf = getTraceFileList().get(i);
			tf.getInputStream().close();
			tf = null;
		}
		List<LttTraceFile> metaFiles = getTraceFiles().get(METADATA);
		for (i = 0; i < metaFiles.size(); i++) {
			LttTraceFile tf = metaFiles.get(i);
			tf.getInputStream().close();
			tf = null;
		}
		lastSeeked = null;
		TraceState state = TraceState.getTraceState();
		state.clean();
		setTraceFiles(null);
		System.out.println("Done processing all files");

	}

	private boolean hasOldestTimestamp(LttEvent event, LttTime timestamp) {
		if (event.getEventTime().getTvSec() == timestamp.getTvSec()) {
			if (event.getEventTime().getTvNsec() < timestamp.getTvNsec()) {
				return true;
			}
			return false;
		} else if (event.getEventTime().getTvSec() > timestamp.getTvSec()) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Create LttTraceFile class, process header and have file descriptor
	 * pointing to first event. Add LttTracefile to tracefiles map.
	 */
	public void lttOpenTracefile(File fileName, LttTraceFile ltf)
			throws Exception {

		RandomAccessFile seeker = null;
		try {
			seeker = new RandomAccessFile(fileName, "r");
			// fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {

			throw new Exception("PARSE ERROR : Unable to read the trace file"
					+ fileName);
		}
		ltf.setTrace(this);
		ltf.setLongName(fileName.toString());
		ltf.setInputStream(seeker);
		ltf.parseTraceHeader(null, 0);
		ltf.setFileSize(fileName.length());
		ltf.setEventsLost(0);
		ltf.setSubbufCorrupt(0);
		ltf.lttTraceCreateBlockIndex(this);
		ltf.mapBlock(this, 0);
	}

	/*
	 * Get the name & cpu num of file TODO userspace files need to be handled
	 * differently
	 */
	public void getTracefileNameNumber(File path, LttTraceFile ltf) {
		/*
		 * StringTokenizer st = new StringTokenizer(path.toString(), "\\//");
		 * String fileName = null;
		 * 
		 * ; while (st.hasMoreTokens()) { fileName = st.nextToken(); }
		 */
		StringBuffer name = new StringBuffer();
		StringTokenizer st1 = new StringTokenizer(path.getName(), "_");
		name.append(st1.nextToken());
		while (st1.hasMoreTokens()) {
			if (st1.countTokens() > 1) {
				name.append("_" + st1.nextToken());
			} else {
				ltf.setCpuNum(Integer.parseInt(st1.nextToken()));
			}
		}
		ltf.setName(name.toString());

	}

	public Map<String, MarkerInfo> getMData() {
		return mData;
	}

	public void setMData(Map<String, MarkerInfo> data) {
		mData = data;
	}

	public Map<String, List<LttTraceFile>> getTraceFiles() {
		return traceFiles;
	}

	public void setTraceFiles(Map<String, List<LttTraceFile>> tracefiles) {
		this.traceFiles = tracefiles;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathname) {
		this.pathName = pathname;
	}

	public int getNumCpu() {
		return numCpu;
	}

	public void setNumCpu(int numCpu) {
		this.numCpu = numCpu;
	}

	public int getArchType() {
		return archType;
	}

	public void setArchType(int archType) {
		this.archType = archType;
	}

	public int getArchVariant() {
		return archVariant;
	}

	public void setArchVariant(int archVariant) {
		this.archVariant = archVariant;
	}

	public byte getArchSize() {
		return archSize;
	}

	public void setArchSize(byte archSize) {
		this.archSize = archSize;
	}

	public byte getLttMajorVersion() {
		return lttMajorVersion;
	}

	public void setLttMajorVersion(byte lttMajorVersion) {
		this.lttMajorVersion = lttMajorVersion;
	}

	public byte getLttMinorversion() {
		return lttMinorVersion;
	}

	public void setLttMinorVersion(byte lttMinorVersion) {
		this.lttMinorVersion = lttMinorVersion;
	}

	public char getFlightRecorder() {
		return flightRecorder;
	}

	public void setFlightRecorder(char flightRecorder) {
		this.flightRecorder = flightRecorder;
	}

	public int getFreqScale() {
		return freqScale;
	}

	public void setFreqScale(int freqScale) {
		this.freqScale = freqScale;
	}

	public long getStartFreq() {
		return startFreq;
	}

	public void setStartFreq(long startFreq) {
		this.startFreq = startFreq;
	}

	public long getStartTsc() {
		return startTsc;
	}

	public void setStartTsc(long startTsc) {
		this.startTsc = startTsc;
	}

	public long getStartMonotonic() {
		return startMonotonic;
	}

	public void setStartMonotonic(long startMonotonic) {
		this.startMonotonic = startMonotonic;
	}

	public double getDrift() {
		return drift;
	}

	public void setDrift(double drift) {
		this.drift = drift;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public LttTime getStartTimeFromTsc() {
		return startTimeFromTsc;
	}

	public void setStartTimeFromTsc(LttTime startTimeFromTsc) {
		this.startTimeFromTsc = startTimeFromTsc;
	}

	public long getStartTimeUsec() {
		return startTimeUsec;
	}

	public void setStartTimeUsec(long startTimeUsec) {
		this.startTimeUsec = startTimeUsec;
	}

	public List<LttTraceFile> getTraceFileList() {
		return traceFileList;
	}

	public void setTraceFileList(List<LttTraceFile> tracefilelist) {
		this.traceFileList = tracefilelist;
	}

}
