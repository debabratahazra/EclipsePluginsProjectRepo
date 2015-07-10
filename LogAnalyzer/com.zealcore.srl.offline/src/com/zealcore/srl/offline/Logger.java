package com.zealcore.srl.offline;

public final class Logger {
    private static boolean verbose;

    private Logger() {

    }

    static void init(final Blackbox box) {
        verbose = box.isVerboseMode();
    }

    static void log(final String line) {

        if (verbose) {
            System.out.println(line);
        }
    }

    public static void log(final Object message) {
        if (verbose) {
            log(message.toString());
        }
    }

    public static void err(final String string) {
        if (verbose) {
            System.err.println(string);
        }
    }
}
