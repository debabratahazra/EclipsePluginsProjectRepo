package com.odcgroup.aaa.connector.internal.metadictreader;


/**
 * The Version of the Meta Dictionary is actually based on
 * the AAA_VERSION (<Letter><number>.<number>)
 * <p> 
 * This could change in the future.
 * </p>s
 *
 * @author atr
 */
public final class TAVersion implements Comparable<TAVersion> {
	
	private String version;
	
	private String  prefix = "";  // ignore this value   
	private Integer major;
	private Integer minor;

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(TAVersion other) {
		if (this.major.equals(other.major)) {
			return this.minor.compareTo(other.minor);
		}
		return this.major.compareTo(other.major);
	}
	
	public String getVersion() {
		return this.version;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return prefix + major + "." + minor;
	}

	/**
	 * 
	 * @param version The AAA_Version string
	 */
	public TAVersion(String version) {
		this.version = version;

		this.prefix = version.substring(0, 1);
		String tmp = version.substring(1);
		String[] segments = tmp.split("\\.");
		if (segments.length != 2) {
			throw new IllegalArgumentException(
					"Argument must be a valid AAA version");
		}
		try {
			this.major = Integer.valueOf(segments[0]);
			this.minor = Integer.valueOf(segments[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
					"Argument must be a valid AAA version", ex);
		}

	}
}
