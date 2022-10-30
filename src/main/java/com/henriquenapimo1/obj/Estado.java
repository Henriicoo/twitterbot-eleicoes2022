package com.henriquenapimo1.obj;

public class Estado {

    public String nm;
    public double votosL;
    public double votosB;
    public String urnasApuradas;

    public Estado(String nm, double lula, double bolso, String urnas) {
        this.nm = nm;
        this.votosL = lula;
        this.votosB = bolso;
        this.urnasApuradas = urnas;
    }
}
