package com.project.counter.impl;

import com.project.counter.api.FileProcessor;
import com.project.counter.api.ProcessorFactory;
import com.google.auto.service.AutoService;

@AutoService(ProcessorFactory.class)
public final class ProcessorFactoryImpl implements ProcessorFactory {
    @Override
    public FileProcessor createProcessor(String fileName)  {
        return new FileProcessorImpl(fileName);
    }
}