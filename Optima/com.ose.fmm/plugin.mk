# Plugin build specification

PLUGIN_NAME = com.ose.fmm

PLUGIN_VERSION = 1.1.3

JAR_NAME = fmm.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = com.ose.gateway com.ose.system

XDEPS = org.eclipse.core.runtime org.eclipse.equinox.common
