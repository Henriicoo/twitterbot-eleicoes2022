package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

import java.io.IOException;
import java.net.URISyntaxException;

public class EleicoesBot {

    private static TwitterClient twtClient;

    public static void main(String[] args) throws IOException {

        System.out.println("Criando imagem LULA");
        ImageGenerator.createCandidatoImage(new Candidato(
                "LULA",13,12039L,"65.55","PT"
        ));

        System.out.println("Criando imagem JAIR BOLSONARO");
        ImageGenerator.createCandidatoImage(new Candidato(
                "JAIR BOLSONARO",22,12831L,"34.45","PL"
        ));

        try {
            System.out.println("THREAD SLEEP");
            Thread.sleep(5 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Criando imagem FINAL");
        ImageGenerator.createFinalImage(47);

         /*twtClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken(Utils.twtAccess)
                .accessTokenSecret(Utils.twtASecret)
                .apiKey(Utils.twtApi)
                .apiSecretKey(Utils.twtApiSecret)
                .build());*/
    }

    public static TwitterClient getTwitter() {
        return twtClient;
    }
}
