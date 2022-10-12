package com.henriquenapimo1;

public class Estado {

    public String nm;
    public String votosL;
    public String votosB;
    public String urnasApuradas;

    public Estado(String nm, String lula, String bolso, String urnas) {
        this.nm = nm;
        this.votosL = lula;
        this.votosB = bolso;
        this.urnasApuradas = urnas;
    }
}
