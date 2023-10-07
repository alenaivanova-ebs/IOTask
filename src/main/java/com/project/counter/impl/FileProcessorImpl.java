package com.project.counter.impl;

import com.project.counter.api.FileProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileProcessorImpl implements FileProcessor {
    private final String file;

    public FileProcessorImpl(String file) {
        this.file = file;
    }

    @Override
    public void process(String file, StringWriter output) throws IOException {
        List<String> listOfWords = convertFileToListOfWords(file);
        int numberOfWords = listOfWords.size();
        Map<String, Long> wordsCounterUnsorted = listOfWords
                .stream()
                .collect(Collectors
                        .groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> wordsCounterSorted = sortMapByValueDescending(wordsCounterUnsorted);

        DecimalFormat df = new DecimalFormat("0.00");
        // try (ICsvListWriter listWriter = new CsvListWriter(new FileWriter("src/main/resources/output.csv"), CsvPreference.STANDARD_PREFERENCE)) {
        try (ICsvListWriter listWriter = new CsvListWriter(output, CsvPreference.STANDARD_PREFERENCE)) {
            listWriter.writeHeader("Слово", "Частота", "Частота (в %)");
            for (Map.Entry<String, Long> entry : wordsCounterSorted.entrySet()) {
                double f = (double) entry.getValue() * 100 / numberOfWords;
                listWriter.write(entry.getKey(), entry.getValue(), df.format(f));
            }
        }

    }

    public List<String> convertFileToListOfWords(String fileName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        List<String> listOfWords = null;
        try (
                InputStream is = classloader.getResourceAsStream(fileName);
                Reader reader = new InputStreamReader(is);) {
            int data = reader.read();
            StringBuilder word = new StringBuilder();
            listOfWords = new ArrayList<>();
            char theChar;
            while (data != -1) {
                theChar = (char) data;
                if (Character.isLetter(theChar)) {
                    word.append(theChar);
                } else if (!word.isEmpty()) {
                    listOfWords.add(word.toString());
                    word = new StringBuilder();
                }
                data = reader.read();
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
        return listOfWords;
    }

    //Sorting values with custom Comparator: https://www.baeldung.com/java-sort-map-descending
    public static <K extends Comparable<? super K>, V extends Comparable<? super V>> Map<K, V> sortMapByValueDescending(Map<K, V> map) {

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<K, V>comparingByValue().reversed().thenComparing(Map.Entry.<K, V>comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
