package de.uni.goettingen;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        TFIDFCalculator calculator = new TFIDFCalculator();

        calculator.readData();
        calculator.calculateTFIDF();
    }
}
