package com.trident.hamming.correction;

import com.trident.hamming.correction.service.EmptyWriter;
import com.trident.hamming.correction.service.HammingCodeReader;
import com.trident.hamming.correction.service.HammingCodeSequentialErrorsProvider;
import com.trident.hamming.correction.service.HammingCorrectionAnalyzer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.PrintWriter;

import static com.trident.hamming.correction.service.HammingCorrectionReportWriter.writeToString;

public class Runner {

    private static final Options OPTIONS = new Options()
            .addOption("l", "errorLevel", true, "Error level")
            .addOption("c", "hammingCode", true, "Hamming code description file path")
            .addOption("v", "verbose", false, "Print details");

    public static void main(String[] args) {
        try {
            var cmd = new DefaultParser().parse(OPTIONS, args);
            run(cmd);
        } catch (Exception ex) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HammingCorrectionResearch", OPTIONS);
        }
    }

    private static void run(CommandLine commandLine) throws Exception {
        int errorLevel = Integer.parseInt(commandLine.getOptionValue("l"));
        boolean verbose = commandLine.hasOption("v");
        String hammingCodePath = commandLine.getOptionValue("c");

        var writer = verbose
                ? new PrintWriter(System.out)
                : EmptyWriter.getInstance();
        var hammingCode = HammingCodeReader.read(hammingCodePath);
        var errorProvider = new HammingCodeSequentialErrorsProvider(errorLevel, hammingCode);
        var analyzer = new HammingCorrectionAnalyzer(errorProvider, writer);
        var result = analyzer.analyzeHammingCodeCorrection(hammingCode);
        System.out.printf("Final result: %s%n", writeToString(result));
    }
}
