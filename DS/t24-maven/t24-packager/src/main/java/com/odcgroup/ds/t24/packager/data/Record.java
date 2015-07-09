package com.odcgroup.ds.t24.packager.data;

public class Record {

	private String filename = "";
	private String name = "";
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Record))
			return false;
		
		Record other = (Record)obj;
		if (!equalsNullSafe(other.filename, this.filename))
			return false;
		if (!equalsNullSafe(other.name, this.name))
			return false;

		return true;
	}
	
	private boolean equalsNullSafe(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		else if (obj1 == null && obj2 != null)
			return false;
		else if (obj1 != null && obj2 == null)
			return false;
		else
			return obj1.equals(obj2);
	}
	
	public int hashCode() {
		int hash = 0;
		if (filename != null)
			hash ^= filename.hashCode();
		if (name != null)
			hash ^= filename.hashCode();
		return hash;
	}
	
}
