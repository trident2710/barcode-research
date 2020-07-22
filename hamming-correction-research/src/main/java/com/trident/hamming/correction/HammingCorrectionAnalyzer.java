package com.trident.hamming.correction;

import com.trident.hamming.correction.service.HammingCodeReader;
import com.trident.hamming.correction.service.HammingCorrectionAnalyzerService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class HammingCorrectionAnalyzer {

    private static final Options OPTIONS = new Options()
            .addOption("l", "errorLevel", true, "Error level")
            .addOption("c", "hammingCode", true, "Hamming code description file path")
            .addOption("i", "iterations", true, "Number of iterations to perform");

    public static void main(String[] args) throws Exception {
        try {
            var cmd = new DefaultParser().parse(OPTIONS, args);
            run(cmd);
        } catch (ParseException ex) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HammingCorrectionResearch", OPTIONS);
        }
    }

    private static void run(CommandLine commandLine) throws Exception {
        int errorLevel = Integer.parseInt(commandLine.getOptionValue("l"));
        int iterations = Integer.parseInt(commandLine.getOptionValue("i"));
        String hammingCodePath = commandLine.getOptionValue("c");

        var hammingCode = HammingCodeReader.read(hammingCodePath);
        var result = HammingCorrectionAnalyzerService.analyzeHammingCodeCorrection(hammingCode, errorLevel, iterations);
        System.out.println(result);
    }
}
