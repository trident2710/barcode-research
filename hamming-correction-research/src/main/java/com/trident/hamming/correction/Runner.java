package com.trident.hamming.correction;

import com.google.common.base.Preconditions;
import com.trident.hamming.correction.report.HammingCorrectionReportWriter;
import com.trident.hamming.correction.service.HammingCodeAnalyzer;
import com.trident.hamming.correction.service.HammingCodeReader;
import com.trident.math.field.GFP;
import com.trident.math.field.GFPElement;
import com.trident.math.field.GFPM;
import com.trident.math.field.GFPMElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import com.trident.math.io.dto.GaloisFieldDto;
import com.trident.math.io.dto.HammingCodeDto;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.File;
import java.io.PrintStream;

public class Runner {

    private static final Options OPTIONS = new Options()
            .addOption("c", "hammingCodes", true, "Directory with hamming codes")
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
        var hammingCodeDir = hammingCodeDir(commandLine);
        boolean verbose = verbose(commandLine);

        run(hammingCodeDir, verbose);
    }

    private static void run(File hammingCodesDir, boolean verbose) throws Exception {
        Preconditions.checkArgument(hammingCodesDir.isDirectory());
        var hammingCodes = hammingCodesDir.listFiles();
        for (File hammingCode : hammingCodes) {
            analyzeHammingCode(hammingCode, verbose);
        }
    }

    private static void analyzeHammingCode(File hammingCode, boolean verbose) throws Exception {
        System.out.println("Analyzing: " + hammingCode.getName());
        var dto = HammingCodeReader.read(hammingCode.getAbsolutePath());
        var fileWriter = writer(hammingCode.getName().replace(".json", ".txt"));
        var analyzer = analyzer(dto, fileWriter, verbose);
        analyzer.analyzeHammingCode();
    }

    private static boolean verbose(CommandLine commandLine) {
        return commandLine.hasOption("v");
    }

    private static File hammingCodeDir(CommandLine commandLine) {
        var path = commandLine.getOptionValue("c");
        return new File(path);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static PrintStream writer(String hammingCodeName) throws Exception {
        var dir = new File("output");
        dir.mkdir();
        var file = new File("output/" + hammingCodeName);
        file.createNewFile();
        return new PrintStream(file);
    }

    private static HammingCodeAnalyzer<?, ?> analyzer(HammingCodeDto hammingCodeDto, PrintStream writer, boolean verbose) {
        return hammingCodeDto.field().type() == GaloisFieldDto.Type.GFP
                ? hammingCodeGFPAnalyzer(HammingCodeConverter.fromDto(hammingCodeDto, GFP.class, new GFPElement[0]), writer, verbose)
                : hammingCodeGFPolyAnalyzer(HammingCodeConverter.fromDto(hammingCodeDto, GFPM.class, new GFPMElement[0]), writer, verbose);
    }

    private static HammingCodeAnalyzer<GFPElement, GFP> hammingCodeGFPAnalyzer(HammingCode<GFPElement, GFP> hammingCode, PrintStream writer, boolean verbose) {
        var reportWriter = new HammingCorrectionReportWriter(writer, verbose);
        return new HammingCodeAnalyzer<>(hammingCode, reportWriter, new GFPElement[0]);
    }

    private static HammingCodeAnalyzer<GFPMElement, GFPM> hammingCodeGFPolyAnalyzer(HammingCode<GFPMElement, GFPM> hammingCode, PrintStream writer, boolean verbose) {
        var reportWriter = new HammingCorrectionReportWriter(writer, verbose);
        return new HammingCodeAnalyzer<>(hammingCode, reportWriter, new GFPMElement[0]);
    }
}
