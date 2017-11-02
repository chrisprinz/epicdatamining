package de.uni.goettingen;

public class WordData {
    private String word;
    private double frequency;
    private String document;

    WordData(String word, double frequency, String document) {
        this.word = word;
        this.frequency = frequency;
        this.document = document;
    }

    String getWord() {
        return word;
    }

    public double getFrequency() {
        return frequency;
    }

    String getDocument() {
        return document;
    }
}
