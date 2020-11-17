package com.trident.hamming.correction.report;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintStream;

public class HammingCorrectionReportWriter {

    private final PrintStream printStream;
    private final boolean verbose;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public HammingCorrectionReportWriter(PrintStream printStream, boolean verbose) {
        this.printStream = printStream;
        this.verbose = verbose;
    }

    public void info(String info) {
        printStream.println(info);
    }

    public void trace(String trace) {
        if (verbose) {
            printStream.println(trace);
        }
    }

    public static String reportToString(HammingCorrectionReport report) {
        try {
            return OBJECT_MAPPER.writeValueAsString(report);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
