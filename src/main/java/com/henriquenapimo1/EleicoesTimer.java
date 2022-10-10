package com.henriquenapimo1;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.*;

public class EleicoesTimer {

    public EleicoesTimer() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {

                try {
                    apurar();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 60*5*1000);
    }

    private void apurar() throws IOException,InterruptedException {
        List<Candidato> candidatos = getDadosTSE();

        if(candidatos.isEmpty())
            return;

        for (Candidato candidato : candidatos) {
            ImageGenerator.createCandidatoImage(candidato);
        }

        Thread.sleep(3 * 1000);

        ImageGenerator.createFinalImage(Double.parseDouble(urnasTotal.replace(",",".")));

        Thread.sleep(2 * 1000);

        Candidato primeiro = candidatos.stream().filter(c -> c.pos==1).findFirst().get();
        Candidato segundo = candidatos.stream().filter(c -> c.pos==2).findFirst().get();

        String text = "APURAÇÃO DAS URNAS ("+urnasFormat+"% apuradas) - às "+horaUltimaAtt+"\n1º "+primeiro.nome+": "+primeiro.porcent+"% ("+primeiro.votos+" votos)\n"+
                "2º "+segundo.nome+": "+segundo.porcent+"% ("+segundo.votos+" votos)\n#Eleições2022";
        TweetManager.postTweetWithImage(text,Utils.finalImageFile);
    }

    private String urnasTotal;
    private String urnasFormat;
    private String horaUltimaAtt;

    private List<Candidato> getDadosTSE() {
        List<Candidato> resultados = new ArrayList<>();

        String tseResponse = HttpRequest.get(Utils.tse).body();

        JsonObject eleicoes = JsonParser.parseString(tseResponse).getAsJsonObject();

        String urnasAgora = eleicoes.get("pst").getAsString();

        if(urnasAgora.equals(urnasTotal)) {
            return Collections.emptyList();
        }

        urnasTotal = urnasAgora;
        urnasFormat = urnasAgora.split(",")[0];
        horaUltimaAtt = eleicoes.get("ht").getAsString();

        JsonArray cand = eleicoes.getAsJsonArray("cand");

        cand.forEach(r -> {
            JsonObject res = r.getAsJsonObject();

            if(Arrays.asList("LULA","JAIR BOLSONARO").contains(res.get("nm").getAsString()))
                resultados.add(new Candidato(
                    res.get("nm").getAsString(),
                    res.get("n").getAsInt(),
                    res.get("seq").getAsInt(),
                    res.get("vap").getAsLong(),
                    res.get("pvap").getAsString(),
                    res.get("cc").getAsString().split(" ")[0]));
        });

        return resultados;
    }
}
