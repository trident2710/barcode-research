package com.trident.barcode.model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Code {

    @Value.Parameter
    List<Integer> data();
}
