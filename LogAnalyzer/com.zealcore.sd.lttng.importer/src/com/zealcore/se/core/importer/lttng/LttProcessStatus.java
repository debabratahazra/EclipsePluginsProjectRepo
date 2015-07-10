package com.zealcore.se.core.importer.lttng;

public interface LttProcessStatus {

	String LTT_STATE_UNNAMED = "Unamed";
	String LTT_STATE_WAIT_FORK = "Wait-Fork";
	String LTT_STATE_WAIT_CPU = "Wait-Cpu";
	String LTT_STATE_EXIT = "Exit";
	String LTT_STATE_ZOMBIE = "Zombie";
	String LTT_STATE_WAIT = "Wait";
	String LTT_STATE_RUN = "Run";
	String LTT_STATE_DEAD = "Dead";
}
