package com.henriquenapimo1.obj;

import org.apache.commons.text.WordUtils;

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
        double dif = primeiroPorcent-segundoPorcent;
        return WordUtils.capitalizeFully(primeiroLugar) +
                " está na liderança com " + dif + "% de vantagem.";
    }
}
