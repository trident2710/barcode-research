package com.trident.hamming.correction.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public final class EmptyWriter extends PrintWriter {
    private static final EmptyWriter instance = new EmptyWriter();

    private EmptyWriter() {
        super(new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {

            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public void close() throws IOException {

            }
        });
    }

    public static EmptyWriter getInstance() {
        return instance;
    }
}
