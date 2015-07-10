# Plugin build specification

PLUGIN_NAME = com.ose.pmd

PLUGIN_VERSION = 1.4.0

JAR_NAME = pmd.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

DEPS = com.ose.gateway com.ose.system com.ose.plugin.control
