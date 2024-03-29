package com.trident.hamming.correction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.trident.math.io.dto.HammingCodeDto;

import java.io.File;

public class HammingCodeReader {

    public static HammingCodeDto read(String path) throws Exception {
        File file = new File(path);
        var dto = objectMapper().readValue(file, HammingCodeDto.class);
        return dto;
    }

    private static ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModules(new GuavaModule());
        return objectMapper;
    }
}
