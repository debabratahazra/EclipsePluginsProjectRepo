# Plugin build specification

PLUGIN_NAME = com.ose.optima

PLUGIN_VERSION = 2.8.0

JAR_NAME = optima.jar

SRCS = $(shell find src -type f -name "*.java" -print)

BINS = $(patsubst src/%.java,bin/%.class,$(SRCS))

RSRCS = about.gif about.ini about.mappings about.properties helpData.xml introData.xml logo16.gif logo32.png logo48.png plugin.properties plugin_customization.ini splash.bmp intro

XDEPS = org.eclipse.core.commands \
        org.eclipse.core.runtime \
        org.eclipse.equinox.common \
        org.eclipse.jface \
        org.eclipse.ui \
        org.eclipse.ui.intro \
        org.eclipse.ui.workbench
