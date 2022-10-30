package com.henriquenapimo1.obj;

import org.apache.commons.text.WordUtils;

import java.text.DecimalFormat;

public class Leaderboard {

    public String primeiroLugar;
    public String segundoLugar;

    public double primeiroPorcent;
    public double segundoPorcent;

    public Leaderboard(String f, String s, double fP, double sP) {
        primeiroLugar = f;
        segundoLugar = s;
        primeiroPorcent = fP;
        segundoPorcent = sP;
    }

    public String compare() {
        String dif = new DecimalFormat("0.00").format(primeiroPorcent-segundoPorcent);
        return WordUtils.capitalizeFully(primeiroLugar) +
                " está na liderança com " + dif + "% de vantagem.";
    }
}
