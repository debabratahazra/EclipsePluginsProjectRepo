# Plugin build specification

PLUGIN_NAME = com.ose.sigdb

PLUGIN_VERSION = 1.1.0

JAR_NAME = sigdb.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))
