package com.zealcore.srl.offline;

public final class Version implements Comparable<Version>{
    private final int major;

    private final int minor;

    private final int revision;

    public static Version valueOf(final int major, final int minor, final int revision) {
        return new Version(major, minor, revision);
    }
    
    private  Version(final int major, final int minor, final int revision) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    @Override
    public String toString() {
        return String.valueOf(major) + "." + String.valueOf(minor) + "-"
                + String.valueOf(revision);
    }

    public int compareTo(final Version other) {
        if(other == null) {
            throw new NullPointerException();
        }
        if(this.major < other.major) {
            return -1;
        }
        if(this.major > other.major) {
            return 1;
        }
        if(this.minor < other.minor) {
            return -1;
        }
        if(this.minor > other.minor) {
            return 1;
        }
        if(this.revision < other.revision) {
            return -1;
        }
        if(this.revision > other.revision) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(final Object other) {
        if(other == null) {
            throw new NullPointerException();
        }
        if (other instanceof Version) {
            Version otherVersion = (Version) other;
            if(this.major != otherVersion.major) {
                return false;
            }
            if(this.minor != otherVersion.minor) {
                return false;
            }
            if(this.revision != otherVersion.revision) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31*major+37*minor+41*revision;
    }
}
