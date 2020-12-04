package com.fi0x.decrypter.userinteraction;

public class Out
{
    private static final String RESET = "\u001B[0m";
    private static final String WHITE = "\u001B[37m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public static boolean d;
    public static boolean v;
    public static boolean vv;

    private boolean always;
    private boolean debug;
    private boolean verbose;
    private boolean veryVerbose;

    private String text;
    private String color;

    private Out()
    {
        always = false;
    }

    public static Out newBuilder(String message)
    {
        Out out = new Out();
        out.text = message;
        out.color = WHITE;
        return out;
    }

    public Out always(boolean state)
    {
        always = state;
        return this;
    }
    public Out debug(boolean state)
    {
        debug = state;
        return this;
    }
    public Out verbose(boolean state)
    {
        verbose = state;
        return this;
    }
    public Out veryVerbose(boolean state)
    {
        veryVerbose = state;
        return this;
    }

    public Out WARNING()
    {
        color = YELLOW;
        return this;
    }
    public Out ERROR()
    {
        color = RED;
        return this;
    }
    public Out ALERT()
    {
        color = GREEN;
        return this;
    }

    public void print()
    {
        boolean allowed = false;

        if(always) allowed = true;
        if(debug && d) allowed = true;
        if(verbose && (v || vv)) allowed = true;
        if(veryVerbose && vv) allowed = true;

        if(allowed) System.out.println(color + text + RESET);
    }
}
