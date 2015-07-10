# Plugin build specification

PLUGIN_NAME = com.ose.boot

PLUGIN_VERSION = 1.0.3

JAR_NAME = boot.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))
