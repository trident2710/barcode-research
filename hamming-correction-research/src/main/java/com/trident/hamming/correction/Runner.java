package com.trident.hamming.correction;

import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.service.HammingCodeReader;
import com.trident.hamming.correction.service.HammingCodeSequentialErrorsProvider;
import com.trident.hamming.correction.service.HammingCorrectionAnalyzer;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.File;
import java.io.PrintStream;

public class Runner {

    private static final Options OPTIONS = new Options()
            .addOption("l", "errorLevel", true, "Error level")
            .addOption("c", "hammingCode", true, "Hamming code description file path")
            .addOption("n", "fileOutputName", true, "File output name")
            .addOption("v", "verbose", false, "Verbose mode");

    public static void main(String[] args) {
        try {
            var cmd = new DefaultParser().parse(OPTIONS, args);
            run(cmd);
        } catch (Exception ex) {
            ex.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("HammingCorrectionResearch", OPTIONS);
        }
    }

    private static void run(CommandLine commandLine) throws Exception {
        int errorLevel = errorLevel(commandLine);
        var hammingCode = hammingCode(commandLine);
        var writer = writer(commandLine);
        boolean verbose = verbose(commandLine);

        var analyzer = analyzer(errorLevel, hammingCode, writer, verbose);
        analyzer.analyzeHammingCodeCorrection(hammingCode);
    }

    private static boolean verbose(CommandLine commandLine) {
        return commandLine.hasOption("v");
    }

    private static int errorLevel(CommandLine commandLine) {
        return Integer.parseInt(commandLine.getOptionValue("l"));
    }

    private static HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode(CommandLine commandLine) throws Exception {
        var path = commandLine.getOptionValue("c");
        return HammingCodeReader.read(path);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static PrintStream writer(CommandLine commandLine) throws Exception {
        var dir = new File("output");
        dir.mkdir();
        var fileName = commandLine.hasOption("n")
                ? commandLine.getOptionValue("n")
                : System.currentTimeMillis();
        var file = new File("output/" + fileName);
        file.createNewFile();
        System.out.println(file.getAbsoluteFile());
        return new PrintStream(file);
    }

    private static HammingCorrectionAnalyzer analyzer(int errorLevel, HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> hammingCode, PrintStream writer, boolean verbose) {
        var errorProvider = new HammingCodeSequentialErrorsProvider(errorLevel, hammingCode);
        var reportWriter = new HammingCorrectionReportWriter(writer, verbose);
        return new HammingCorrectionAnalyzer(errorProvider, reportWriter);
    }
}
