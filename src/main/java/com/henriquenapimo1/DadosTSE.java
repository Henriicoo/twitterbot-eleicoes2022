package com.henriquenapimo1;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.henriquenapimo1.imagens.ImageGenerator;
import com.henriquenapimo1.obj.Candidato;
import com.henriquenapimo1.obj.Estado;

import java.io.IOException;
import java.util.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class DadosTSE {

    public void apurar() throws IOException {
        List<Candidato> candidatos = getDadosTSE("br");
        List<Estado> estados = getDadosEstados();

        if(candidatos.isEmpty() || estados.isEmpty())
            return;

        for (Candidato candidato : candidatos) {
            ImageGenerator.createCandidatoImage(candidato);
        }

        ImageGenerator.createMapa(estados);
        ImageGenerator.createEstadosChart(estados);

        ImageGenerator.createFinalImage(Double.parseDouble(urnasTotal.replace(",",".")));

        Candidato primeiro = candidatos.stream().filter(c -> c.pos==1).findFirst().get();
        Candidato segundo = candidatos.stream().filter(c -> c.pos==2).findFirst().get();

        String text = "APURAÇÃO DAS URNAS ("+urnasFormat+"% apuradas) - às "+horaUltimaAtt+"\n1º "+primeiro.nome+": "+primeiro.porcent+"% ("+primeiro.votos+" votos)\n"+
                "2º "+segundo.nome+": "+segundo.porcent+"% ("+segundo.votos+" votos)\n#Eleições2022";

        TweetManager.postThread(text,"gen/"+Utils.finalImageFile,"gen/mapa.png","gen/grafico1.png","gen/grafico2.png","gen/grafico3.png");
    }

    private String urnasTotal;
    private String urnasFormat;
    private String horaUltimaAtt;

    private List<Candidato> getDadosTSE(String UF) {
        List<Candidato> resultados = new ArrayList<>();

        String tseResponse = HttpRequest.get(Utils.tse.replace("UF",UF)).body();

        JsonObject eleicoes = JsonParser.parseString(tseResponse).getAsJsonObject();

        String urnasAgora = eleicoes.get("pst").getAsString();

        if(eleicoes.get("st").getAsLong()==0) {
            return Collections.emptyList();
        }

        if(UF.equals("br")) {
            if (urnasAgora.equals(urnasTotal)) {
                return Collections.emptyList();
            }
            urnasTotal = urnasAgora;
        }

        urnasFormat = urnasAgora.split(",")[0];
        horaUltimaAtt = eleicoes.get("ht").getAsString();

        double percentBrancos = parseNum(eleicoes.get("pvb").getAsString());
        double percentNulos = parseNum(eleicoes.get("ptvn").getAsString());
        int percentNulosBrancos = (int) (percentBrancos+percentNulos);

        JsonArray cand = eleicoes.getAsJsonArray("cand");

        cand.forEach(r -> {
            JsonObject res = r.getAsJsonObject();

            if(Arrays.asList("LULA","JAIR BOLSONARO").contains(res.get("nm").getAsString()))
                resultados.add(new Candidato(
                        res.get("nm").getAsString(),
                        res.get("n").getAsInt(),
                        res.get("seq").getAsInt(),
                        res.get("vap").getAsLong(),
                        (int) parseNum(res.get("pvap").getAsString()),
                        res.get("cc").getAsString().split(" ")[0],
                        urnasFormat,
                        percentNulosBrancos
                ));
        });

        return resultados;
    }

    private double parseNum(String st) {
        return Double.parseDouble(st.replace(",","."));
    }

    private List<Estado> getDadosEstados() {
        String UFs = "ac,al,am,ap,ba,ce,df,es,go,ma,mg,ms,mt,pa,pb,pe,pi,pr,rj,rn,ro,rr,rs,sc,se,sp,to";

        List<Estado> estados = new ArrayList<>();

        Arrays.stream(UFs.split(",")).forEach(uf -> {
            List<Candidato> l = getDadosTSE(uf);
            if(l.isEmpty()) return;

            estados.add(new Estado(
                    uf,
                    l.stream().filter(c -> c.nome.equals("LULA")).findFirst().get().porcent,
                    l.stream().filter(c -> c.nome.equals("JAIR BOLSONARO")).findFirst().get().porcent,
                    l.get(0).urnas,
                    String.valueOf(l.get(0).nulos)
            ));
        });

        return estados;
    }
}
