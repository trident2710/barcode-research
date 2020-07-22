package com.trident.hamming.correction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.trident.hamming.correction.service.HammingCorrectionMeter;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import com.trident.math.io.dto.HammingCodeDto;

import java.net.URL;

public class HammingCorrectionResearch {

    public static void main(String[] args) throws Exception {
        var hammingCode = hammingCode();
        HammingCorrectionMeter.analyzeHammingCode(hammingCode);
    }

    private static HammingCode hammingCode() throws Exception {
        URL file = HammingCorrectionResearch.class.getClassLoader().getResource("hamming-sample.json");
        var dto = objectMapper().readValue(file, HammingCodeDto.class);
        return HammingCodeConverter.fromDto(dto);
    }

    private static ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModules(new GuavaModule());
        return objectMapper;
    }
}
