package com.henriquenapimo1.obj;

import org.apache.commons.text.WordUtils;

public class Leaderboard {

    public String primeiroLugar;
    public String segundoLugar;

    public int primeiroPorcent;
    public int segundoPorcent;

    public Leaderboard(String f, String s, int fP, int sP) {
        primeiroLugar = f;
        segundoLugar = s;
        primeiroPorcent = fP;
        segundoPorcent = sP;
    }

    public String compare(Leaderboard board) {
        StringBuilder builder = new StringBuilder();

        builder.append(WordUtils.capitalizeFully(primeiroLugar));
        int dif = primeiroPorcent-segundoPorcent;

        if(board==null) {
            return builder.append(" começa as apurações com vantagem de ").append(dif).append("%!").toString();
        }

        if(board.primeiroLugar.equals(primeiroLugar)) {
            builder.append(" está na liderança com ").append(dif).append("% de vantagem.");
        } else {
            builder.append(" ultrapassou seu adversário, ficando com ").append(dif).append("% de vantagem.");
        }

        return builder.toString();
    }
}
