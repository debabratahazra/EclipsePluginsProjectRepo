package com.zealcore.se.core.importer.lttng;

public class LttTime {

	private long tvSec;
	private long tvNsec;

	public LttTime() {

	}

	public LttTime(long tvSec, long tvNsec) {

		this.tvSec = tvSec;
		this.tvNsec = tvNsec;
	}

	public long getTvSec() {
		return tvSec;
	}

	public void setTvSec(long tvSec) {
		this.tvSec = tvSec;
	}

	public long getTvNsec() {
		return tvNsec;
	}

	public void setTvNsec(long tvNsec) {
		this.tvNsec = tvNsec;
	}

}
