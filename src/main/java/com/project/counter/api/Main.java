package com.project.counter.api;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.ServiceLoader;

//java -jar Exercise-1.0-SNAPSHOT-jar-with-dependencies.jar input_3.txt
public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.printf("Usage:%n  java -jar  application.jar <fileName> %n");
            System.exit(1);
        }

        String fileName = args[0];
        process(fileName);
    }

    private static void process(String fileName) throws IOException {
        Iterator<ProcessorFactory> factories =
                ServiceLoader.load(ProcessorFactory.class).iterator();
        if (!factories.hasNext()) {
            throw new IllegalStateException("No ProcessorFactory found");
        }

        ProcessorFactory factory = factories.next();
        FileProcessor processor = factory.createProcessor(fileName);
        StringWriter output = new StringWriter();
        processor.process(fileName, output);
    }
}
