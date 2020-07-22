package com.trident.hamming.correction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trident.hamming.correction.report.HammingCorrectionReport;

public class HammingCorrectionReportWriter {

    public static String writeToString(HammingCorrectionReport report) throws Exception {
        return new ObjectMapper().writeValueAsString(report);
    }
}
