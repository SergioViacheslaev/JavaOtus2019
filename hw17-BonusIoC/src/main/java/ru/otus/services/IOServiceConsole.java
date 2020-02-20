package ru.otus.services;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceConsole implements IOService {
    private final PrintStream out;
    private final Scanner in;

    public IOServiceConsole(PrintStream out, Scanner in) {
        this.out = out;
        this.in = in;
    }


    @Override
    public void out(String message) {
        out.println(message);
    }

    @Override
    public String readLn(String prompt) {
        out(prompt);
        return in.next();
    }

    @Override
    public int readInt(String prompt) {
        out(prompt);
        return in.nextInt();
    }
}
