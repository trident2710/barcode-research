package com.trident.barcode.research.model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Code {

    @Value.Parameter
    List<Integer> data();
}
