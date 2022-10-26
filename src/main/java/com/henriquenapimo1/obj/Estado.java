package com.henriquenapimo1.obj;

public class Estado {

    public String nm;
    public int votosL;
    public int votosB;
    public String urnasApuradas;
    public String nulos;

    public Estado(String nm, int lula, int bolso, String urnas, String nulos) {
        this.nm = nm;
        this.votosL = lula;
        this.votosB = bolso;
        this.urnasApuradas = urnas;
        this.nulos = nulos;
    }
}
