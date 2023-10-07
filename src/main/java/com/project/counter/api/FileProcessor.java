package com.project.counter.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

public interface FileProcessor {
    /**
     * Processes data from the given input stream, sending the result to the provided output stream.
     *
     * @param file The source of data to be processed.

     * @throws IOException Upon failure to read from the source or write to the sink.
     */
    void process(String file, StringWriter output )
            throws IOException;

}
