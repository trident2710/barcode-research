package com.trident.hamming.correction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.trident.math.field.GaloisFieldOverPrime;
import com.trident.math.field.GaloisFieldOverPrimeElement;
import com.trident.math.hamming.HammingCode;
import com.trident.math.io.converter.HammingCodeConverter;
import com.trident.math.io.dto.HammingCodeDto;

import java.io.File;

public class HammingCodeReader {

    public static HammingCode<GaloisFieldOverPrimeElement, GaloisFieldOverPrime> read(String path) throws Exception {
        File file = new File(path);
        var dto = objectMapper().readValue(file, HammingCodeDto.class);
        return HammingCodeConverter.fromDto(dto);
    }

    private static ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModules(new GuavaModule());
        return objectMapper;
    }
}
