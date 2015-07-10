package com.zealcore.se.core.importer.lttng;

public interface LttExecutionMode {

	int LTT_STATE_MODE_UNKNOWN = 0;
	int LTT_STATE_USER_MODE = 1;
	int LTT_STATE_SYSCALL = 2;
	int LTT_STATE_TRAP = 3;
	int LTT_STATE_IRQ = 4;
	int LTT_STATE_SOFT_IRQ = 5;

}
