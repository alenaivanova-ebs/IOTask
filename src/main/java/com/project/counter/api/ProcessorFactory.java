package com.project.counter.api;

import java.io.FileNotFoundException;

/**
 * An interface for the construction of {@link FileProcessor}s which limit the amount of data
 * they process.
 */
public interface ProcessorFactory {
    /**
     * Returns a new {@link FileProcessor} which limits input processing as configured.
     *
     * @param fileName the file name to be processed.
     * @return a {@link FileProcessor} which reads orders from its input stream and writes the
     * result of processing said orders out its output stream.
     */
    FileProcessor createProcessor(String fileName) throws FileNotFoundException;
}
